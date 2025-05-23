package net.liukrast.dd;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.liukrast.dd.content.PackageRewriterPonderScene;
import net.liukrast.dd.registry.RegisterBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.lwjgl.system.NonnullDefault;

@SuppressWarnings("deprecation")
@NonnullDefault
public class DeliveryDirectorPonderPlugin implements PonderPlugin {

    @Override
    public String getModId() {
        return DeliveryDirector.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<Item> HELPER = helper.withKeyFunction(BuiltInRegistries.ITEM::getKey);

        HELPER.forComponents(RegisterBlocks.PACKAGE_REWRITER.get().asItem())
                .addStoryBoard("high_logistics/package_rewriter", PackageRewriterPonderScene::packageRewriter);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        var HELPER = helper.withKeyFunction(BuiltInRegistries.ITEM::getKey);

        HELPER.addToTag(AllCreatePonderTags.HIGH_LOGISTICS)
                .add(RegisterBlocks.PACKAGE_REWRITER.get().asItem());
    }
}
