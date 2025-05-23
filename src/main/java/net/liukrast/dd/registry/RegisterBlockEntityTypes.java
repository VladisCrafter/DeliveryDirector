package net.liukrast.dd.registry;

import net.liukrast.dd.DeliveryDirector;
import net.liukrast.dd.content.PackageRewriterBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RegisterBlockEntityTypes {
    private RegisterBlockEntityTypes() {}

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, DeliveryDirector.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PackageRewriterBlockEntity>> PACKAGE_REWRITER = BLOCK_ENTITY_TYPES.register("package_rewriter", () -> BlockEntityType.Builder.of(PackageRewriterBlockEntity::new, RegisterBlocks.PACKAGE_REWRITER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
