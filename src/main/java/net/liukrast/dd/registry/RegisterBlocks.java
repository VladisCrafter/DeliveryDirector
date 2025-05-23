package net.liukrast.dd.registry;

import net.liukrast.dd.DeliveryDirector;
import net.liukrast.dd.content.PackageRewriterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegisterBlocks {
    private RegisterBlocks() {}
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DeliveryDirector.MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DeliveryDirector.MOD_ID);

    public static final DeferredBlock<PackageRewriterBlock> PACKAGE_REWRITER = registerBlock("package_rewriter", () -> new PackageRewriterBlock(BlockBehaviour.Properties
            .ofFullCopy(Blocks.GOLD_BLOCK)
            .noOcclusion()
            .isRedstoneConductor(($1, $2, $3) -> false)
            .mapColor(MapColor.TERRACOTTA_BLUE)
            .sound(SoundType.NETHERITE_BLOCK)
    ));

    private static <T extends Block> DeferredBlock<T> registerBlock(@SuppressWarnings("SameParameterValue") String name, Supplier<T> block) {
        DeferredBlock<T> reg = BLOCKS.register(name, block);
        registerBlockItem(name, reg);
        return reg;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ITEMS.registerSimpleBlockItem(name, block);
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }
}
