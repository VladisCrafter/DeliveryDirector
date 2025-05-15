package net.liukrast.dd;

import com.mojang.logging.LogUtils;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllCreativeModeTabs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(DeliveryDirector.MOD_ID)
public class DeliveryDirector {
    public static final String MOD_ID = "delivery_director";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BE = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MOD_ID);
    public static final DeferredBlock<Block> PACKAGE_REWRITER = BLOCKS.register("package_rewriter", () -> new PackageRewriterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PackageRewriterBlockEntity>> PRBE = BE.register("package_rewriter", () -> BlockEntityType.Builder.of(PackageRewriterBlockEntity::new, PACKAGE_REWRITER.get()).build(null));

    static {
        ITEMS.registerSimpleBlockItem("package_rewriter", PACKAGE_REWRITER);
    }

    public DeliveryDirector(IEventBus modEventBus, ModContainer modContainer) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BE.register(modEventBus);
        modEventBus.register(this);
    }

    @SubscribeEvent
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey()) {
            event.insertAfter(AllBlocks.REPACKAGER.asStack(), PACKAGE_REWRITER.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
