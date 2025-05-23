package net.liukrast.dd.content;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.equipment.clipboard.ClipboardBlock;
import com.simibubi.create.content.logistics.box.PackageStyles;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import com.simibubi.create.infrastructure.ponder.scenes.highLogistics.PonderHilo;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.AttachFace;

public class PackageRewriterPonderScene {
    public static void packageRewriter(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("package_rewriter", "Using the Package Re-Writer to change package addresses");
        scene.configureBasePlate(0, 0, 7);
        scene.scaleSceneView(0.825f);
        scene.setSceneOffsetY(-0.5f);
        scene.world().showIndependentSection(util.select().fromTo(7, 0,0, 0, 0, 6), Direction.UP);
        scene.idle(10);
        scene.world().showSection(util.select().fromTo(0, 1, 3, 6, 3, 6), Direction.NORTH);
        scene.world().showSection(util.select().fromTo(6, 1, 7, 5, 0, 7), Direction.NORTH);
        scene.idle(10);

        scene.overlay()
                .showText(60)
                .text("Package Re-Writers allow to change a package address")
                .placeNearTarget()
                .pointAt(new BlockPos(3,2,3).getCenter());
        scene.idle(70);

        scene.world().showSection(util.select().position(3, 2, 2), Direction.SOUTH);
        scene.idle(10);
        scene.world().multiplyKineticSpeed(util.select().everywhere(), 1/32f);
        ItemStack box = PackageStyles.getDefaultBox().copy();
        scene.world().createItemOnBelt(util.grid().at(6, 1, 3), Direction.EAST, box);
        scene.idle(20);
        scene.overlay()
                .showText(40)
                .text("My Factory")
                .colored(PonderPalette.INPUT)
                .placeNearTarget()
                .pointAt(util.grid().at(6, 2, 3).getCenter());
        scene.idle(50);
        scene.overlay()
                .showText(90)
                .text("The rewriter will search for texts matching the first line of the attached sign...")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.grid().at(3, 2, 2).getCenter());
        scene.idle(100);
        scene.overlay()
                .showText(90)
                .text("...and replace the occurrences with the text in the second line of the sign.")
                .placeNearTarget()
                .pointAt(util.grid().at(3, 2, 2).getCenter());
        scene.idle(110);
        scene.overlay()
                .showText(40)
                .text("§cFactory\n§aWarehouse")
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(util.grid().at(3, 2, 2).getCenter());
        scene.idle(50);


        scene.world().multiplyKineticSpeed(util.select().everywhere(), 32f);
        scene.idle(30);
        scene.world().removeItemsFromBelt(util.grid().at(4, 1, 3));
        PonderHilo.packagerUnpack(scene, util.grid().at(3, 2, 3), box);
        scene.idle(10);
        scene.world().createItemOnBelt(util.grid().at(2, 1, 3), Direction.EAST, box);
        scene.idle(10);

        scene.world().multiplyKineticSpeed(util.select().everywhere(), 1/32f);
        scene.overlay()
                .showText(40)
                .text("My Warehouse")
                .colored(PonderPalette.OUTPUT)
                .placeNearTarget()
                .pointAt(util.grid().at(2, 1, 3).getCenter().add(0, 0.5, 0));
        scene.idle(50);
        scene.world().multiplyKineticSpeed(util.select().everywhere(), 32f);
        scene.idle(30);
        scene.overlay()
                .showText(40)
                .text("Regex expressions can be used for complex modifications")
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(util.grid().at(3, 2, 2).getCenter());
        scene.idle(50);

        scene.world().setBlock(util.grid().at(3, 2, 2),
                AllBlocks.CLIPBOARD.getDefaultState().setValue(ClipboardBlock.FACING, Direction.NORTH).setValue(ClipboardBlock.FACE, AttachFace.WALL),
                true);
        scene.idle(30);
        scene.overlay()
                .showText(70)
                .text("Clipboards can be used for longer strings")
                .placeNearTarget()
                .attachKeyFrame()
                .pointAt(util.grid().at(3, 2, 2).getCenter());
        scene.idle(80);
    }
}
