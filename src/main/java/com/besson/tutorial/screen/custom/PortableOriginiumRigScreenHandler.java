package com.besson.tutorial.screen.custom;

import com.besson.tutorial.blockentity.custom.PortableOriginiumRigBlockEntity;
import com.besson.tutorial.screen.ModScreens;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class PortableOriginiumRigScreenHandler extends ScreenHandler {
    public final SimpleInventory outputInv;
    public final PropertyDelegate propertyDelegate;
    public final PortableOriginiumRigBlockEntity entity;
    
    public PortableOriginiumRigScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, Objects.requireNonNull(getBlockEntity(playerInventory, buf)),
                new ArrayPropertyDelegate(2));
    }
    
    public PortableOriginiumRigScreenHandler(int syncId, PlayerInventory playerInventory, PortableOriginiumRigBlockEntity entity, PropertyDelegate propertyDelegate) {
        super(ModScreens.PORTABLE_ORIGINIUM_RIG_SCREEN, syncId);
        checkSize(playerInventory, 1);
        this.outputInv = entity.getOutputInv();
        this.propertyDelegate = propertyDelegate;
        this.entity = entity;
        
        this.addSlot(new Slot(outputInv, 0, 104, 37));
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        
        addProperties(propertyDelegate);
    }

    @Environment(EnvType.CLIENT)
    private static PortableOriginiumRigBlockEntity getBlockEntity(PlayerInventory playerInventory, PacketByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        BlockEntity be = playerInventory.player.getWorld().getBlockEntity(pos);
        return be instanceof PortableOriginiumRigBlockEntity e ? e : null;
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
