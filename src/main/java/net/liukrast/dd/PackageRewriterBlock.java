package net.liukrast.dd;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class PackageRewriterBlock extends Block implements IBE<PackageRewriterBlockEntity> {

    public PackageRewriterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Class<PackageRewriterBlockEntity> getBlockEntityClass() {
        return PackageRewriterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends PackageRewriterBlockEntity> getBlockEntityType() {
        return DeliveryDirector.PRBE.get();
    }
}