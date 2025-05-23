package net.liukrast.dd.content;

import com.simibubi.create.AllDataComponents;
import com.simibubi.create.content.equipment.clipboard.ClipboardBlockEntity;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.crate.BottomlessItemHandler;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.content.logistics.packager.PackagerItemHandler;
import com.simibubi.create.content.logistics.packager.PackagingRequest;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.data.Pair;
import net.liukrast.dd.registry.RegisterBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.List;

public class PackageRewriterBlockEntity extends PackagerBlockEntity {
    public PackageRewriterBlockEntity(BlockPos pos, BlockState state) {
        super(RegisterBlockEntityTypes.PACKAGE_REWRITER.get(), pos, state);
    }

    protected Pair<String, String> getRegexInfo() {
        for(Direction side : Direction.values()) {
            assert level != null;
            BlockEntity blockEntity = level.getBlockEntity(worldPosition.relative(side));
            if(blockEntity instanceof SignBlockEntity sign) {
                for (boolean front : Iterate.trueAndFalse) {
                    SignText text = sign.getText(front);
                    String regex = text.getMessages(false)[0].getString();
                    String replacement = text.getMessages(false)[1].getString();
                    if (!regex.isBlank())
                        return Pair.of(regex, replacement);
                }
            } else if(blockEntity instanceof ClipboardBlockEntity clipboard) {
                var data = clipboard.dataContainer.get(AllDataComponents.CLIPBOARD_PAGES);
                if(data == null) continue;
                var firstPage = data.getFirst();
                if(firstPage == null) continue;
                if(firstPage.isEmpty()) continue;
                var regex = firstPage.getFirst().text.getString();
                var replacement = firstPage.size() > 1 ? firstPage.get(1).text.getString() : "";
                if (!regex.isBlank())
                    return Pair.of(regex, replacement);
            }
        }
        return null;
    }

    @Override
    public boolean unwrapBox(ItemStack box, boolean simulate) {
        if(!getBlockState().getValue(PackageRewriterBlock.POWERED)) return false;
        if (animationTicks > 0)
            return false;

        IItemHandler targetInv = targetInventory.getInventory();
        if (targetInv == null || targetInv instanceof PackagerItemHandler)
            return false;

        boolean targetIsCreativeCrate = targetInv instanceof BottomlessItemHandler;
        boolean anySpace = false;

        for (int slot = 0; slot < targetInv.getSlots(); slot++) {
            ItemStack remainder = targetInv.insertItem(slot, box, simulate);
            if (!remainder.isEmpty())
                continue;
            anySpace = true;
            break;
        }

        if (!targetIsCreativeCrate && !anySpace)
            return false;
        if (simulate)
            return true;

        String current = PackageItem.getAddress(box);
        var regex = getRegexInfo();
        if(regex != null) PackageItem.addAddress(box, current.replaceAll(regex.getFirst(), regex.getSecond()));
        notifyUpdate();

        previouslyUnwrapped = box;
        animationInward = true;
        animationTicks = CYCLE;
        notifyUpdate();
        return true;
    }

    @Override
    public void attemptToSend(List<PackagingRequest> queuedRequests) {
        if (!heldBox.isEmpty() || animationTicks != 0 || buttonCooldown > 0)
            return;
        if (!queuedExitingPackages.isEmpty())
            return;

        IItemHandler targetInv = targetInventory.getInventory();
        if (targetInv == null || targetInv instanceof PackagerItemHandler)
            return;

        for(int slot = 0; slot < targetInv.getSlots(); slot++) {
            ItemStack extracted = targetInv.extractItem(slot, 1, true);
            if(extracted.isEmpty() || !PackageItem.isPackage(extracted))
                continue;

            targetInv.extractItem(slot, 1, false);
            heldBox = extracted.copy();
            animationInward = false;
            animationTicks = CYCLE;
            notifyUpdate();
            break;
        }

        if(heldBox.isEmpty())
            return;

        String current = PackageItem.getAddress(heldBox);
        var regex = getRegexInfo();
        if(regex != null) PackageItem.addAddress(heldBox, current.replaceAll(regex.getFirst(), regex.getSecond()));
        notifyUpdate();
    }

    @Override
    public void recheckIfLinksPresent() {}

    @Override
    public boolean redstoneModeActive() {
        return true;
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                RegisterBlockEntityTypes.PACKAGE_REWRITER.get(),
                (be, context) -> be.inventory
        );
    }
}
