package net.liukrast.dd;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.content.logistics.packager.PackagerRenderer;
import com.simibubi.create.content.logistics.packager.PackagerVisual;
import dev.engine_room.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import net.createmod.ponder.foundation.PonderIndex;
import net.liukrast.dd.registry.RegisterBlockEntityTypes;
import net.liukrast.dd.registry.RegisterBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DeliveryDirector.MOD_ID)
public class DeliveryDirector {
    public static final String MOD_ID = "delivery_director";


    public DeliveryDirector() {
        @SuppressWarnings("removal") var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        RegisterBlocks.register(modEventBus);
        RegisterBlockEntityTypes.register(modEventBus);
        modEventBus.register(this);
    }

    @SubscribeEvent
    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey()) {
            event.accept(RegisterBlocks.PACKAGE_REWRITER.get().asItem(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @SubscribeEvent
    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(RegisterBlockEntityTypes.PACKAGE_REWRITER.get(), PackagerRenderer::new);
    }

    @SubscribeEvent
    public void fmlClientSetupEvent(FMLClientSetupEvent event) {
        SimpleBlockEntityVisualizer.builder(RegisterBlockEntityTypes.PACKAGE_REWRITER.get())
                .factory(PackagerVisual::new)
                .skipVanillaRender($1 -> false)
                .apply();
        PonderIndex.addPlugin(new DeliveryDirectorPonderPlugin());
    }
}
