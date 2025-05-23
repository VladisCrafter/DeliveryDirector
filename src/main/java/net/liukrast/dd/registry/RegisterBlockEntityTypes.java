package net.liukrast.dd.registry;

import net.liukrast.dd.DeliveryDirector;
import net.liukrast.dd.content.PackageRewriterBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegisterBlockEntityTypes {
    private RegisterBlockEntityTypes() {}

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, DeliveryDirector.MOD_ID);
    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<PackageRewriterBlockEntity>> PACKAGE_REWRITER = BLOCK_ENTITY_TYPES.register("package_rewriter", () -> BlockEntityType.Builder.of(PackageRewriterBlockEntity::new, RegisterBlocks.PACKAGE_REWRITER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
