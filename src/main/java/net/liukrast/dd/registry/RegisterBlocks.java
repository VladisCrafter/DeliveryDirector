package net.liukrast.dd.registry;

import net.liukrast.dd.DeliveryDirector;
import net.liukrast.dd.content.PackageRewriterBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class RegisterBlocks {
    private RegisterBlocks() {}
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DeliveryDirector.MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DeliveryDirector.MOD_ID);

    public static final RegistryObject<PackageRewriterBlock> PACKAGE_REWRITER = registerBlock("package_rewriter", () -> new PackageRewriterBlock(BlockBehaviour.Properties
            .copy(Blocks.GOLD_BLOCK)
            .noOcclusion()
            .isRedstoneConductor(($1, $2, $3) -> false)
            .mapColor(MapColor.TERRACOTTA_BLUE)
            .sound(SoundType.NETHERITE_BLOCK)
    ));

    private static <T extends Block> RegistryObject<T> registerBlock(@SuppressWarnings("SameParameterValue") String name, Supplier<T> block) {
        RegistryObject<T> reg = BLOCKS.register(name, block);
        registerBlockItem(name, reg);
        return reg;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }
}
