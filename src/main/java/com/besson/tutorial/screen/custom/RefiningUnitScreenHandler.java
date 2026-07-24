package com.besson.tutorial.screen.custom;

import com.besson.tutorial.blockentity.custom.RefiningUnitBlockEntity;
import com.besson.tutorial.screen.ModScreens;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class RefiningUnitScreenHandler extends ScreenHandler {
    private final SimpleInventory inputInv;
    private final SimpleInventory outputInv;
    private final PropertyDelegate propertyDelegate;
    public RefiningUnitBlockEntity entity;
    
    public RefiningUnitScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, Objects.requireNonNull(getEntity(playerInventory, buf)),
                new ArrayPropertyDelegate(2));
    }
    public RefiningUnitScreenHandler(int syncId, PlayerInventory playerInventory, RefiningUnitBlockEntity blockEntity, PropertyDelegate propertyDelegate) {
        super(ModScreens.REFINING_UNIT_SCREEN, syncId);
        checkSize(playerInventory, 2);
        this.inputInv = blockEntity.getInputInv();
        this.outputInv = blockEntity.getOutputInv();
        this.propertyDelegate = propertyDelegate;
        this.entity = blockEntity;
        
        this.addSlot(new Slot(inputInv, 0, 80, 11));
        this.addSlot(new Slot(outputInv, 0, 80, 59) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
        
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addProperties(propertyDelegate);
    }

    @Environment(EnvType.CLIENT)
    private static RefiningUnitBlockEntity getEntity(PlayerInventory playerInventory, PacketByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        BlockEntity be = playerInventory.player.getWorld().getBlockEntity(pos);
        return be instanceof RefiningUnitBlockEntity e ? e : null;
    }


    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.outputInv.size()) {
                if (!this.insertItem(originalStack, this.outputInv.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.outputInv.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.entity != null
                && this.entity.getWorld() != null
                && this.entity.getPos().isWithinDistance(player.getBlockPos(), 8);
    }
    
    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }
    
    public int getScaledProgress() {
        int progress = propertyDelegate.get(0);
        int maxProgress = propertyDelegate.get(1);
        int progressArrowSize = 26;
        
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
}
