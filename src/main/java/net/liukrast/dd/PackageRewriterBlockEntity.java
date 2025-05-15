package net.liukrast.dd;

import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class PackageRewriterBlockEntity extends PackagerBlockEntity {
    public PackageRewriterBlockEntity(BlockPos pos, BlockState state) {
        super(DeliveryDirector.PRBE.get(), pos, state);
    }
}
