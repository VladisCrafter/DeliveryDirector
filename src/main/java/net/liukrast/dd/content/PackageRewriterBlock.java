package net.liukrast.dd.content;

import com.simibubi.create.content.logistics.packager.PackagerBlock;
import net.liukrast.dd.registry.RegisterBlockEntityTypes;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class PackageRewriterBlock extends PackagerBlock {

    public PackageRewriterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends PackageRewriterBlockEntity> getBlockEntityType() {
        return RegisterBlockEntityTypes.PACKAGE_REWRITER.get();
    }
}