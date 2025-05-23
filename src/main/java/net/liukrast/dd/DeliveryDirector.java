package net.liukrast.dd;

import com.mojang.logging.LogUtils;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.content.logistics.packager.PackagerRenderer;
import com.simibubi.create.content.logistics.packager.PackagerVisual;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import net.createmod.ponder.foundation.PonderIndex;
import net.liukrast.dd.content.PackageRewriterBlockEntity;
import net.liukrast.dd.registry.RegisterBlockEntityTypes;
import net.liukrast.dd.registry.RegisterBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

@Mod(DeliveryDirector.MOD_ID)
public class DeliveryDirector {
    public static final String MOD_ID = "delivery_director";
    private static final Logger LOGGER = LogUtils.getLogger();


    public DeliveryDirector(IEventBus modEventBus, ModContainer ignored) {
        RegisterBlocks.register(modEventBus);
        RegisterBlockEntityTypes.register(modEventBus);
        modEventBus.register(this);
    }

    @SubscribeEvent
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey()) {
            event.insertAfter(AllBlocks.REPACKAGER.asStack(), RegisterBlocks.PACKAGE_REWRITER.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @SubscribeEvent
    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        PackageRewriterBlockEntity.registerCapabilities(event);
    }

    @SubscribeEvent
    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(RegisterBlockEntityTypes.PACKAGE_REWRITER.get(), PackagerRenderer::new);
    }

    @SubscribeEvent
    private void fmlClientSetupEvent(FMLClientSetupEvent event) {
        SimpleBlockEntityVisualizer.builder(RegisterBlockEntityTypes.PACKAGE_REWRITER.get())
                .factory(PackagerVisual::new)
                .skipVanillaRender($1 -> false)
                .apply();
        PonderIndex.addPlugin(new DeliveryDirectorPonderPlugin());
    }
}
