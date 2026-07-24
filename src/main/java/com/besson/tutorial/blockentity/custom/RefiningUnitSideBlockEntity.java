package com.besson.tutorial.blockentity.custom;

import com.besson.tutorial.blockentity.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class RefiningUnitSideBlockEntity extends BlockEntity {
    private BlockPos parentPos;
    public RefiningUnitSideBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REFINING_UNIT_SIDE, pos, state);
    }
    
    public void setParentPos(BlockPos parentPos) {
        this.parentPos = parentPos;
        markDirty();
    }
    
    @Nullable
    public RefiningUnitBlockEntity getParent() {
        if (parentPos == null || world == null) return null;
        
        BlockEntity entity = world.getBlockEntity(parentPos);
        if (entity instanceof RefiningUnitBlockEntity e) {
            return e;
        }
        return null;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (parentPos != null) {
            nbt.putLong("parentPos", parentPos.asLong());
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("parentPos")) {
            parentPos = BlockPos.fromLong(nbt.getLong("parentPos"));
        }
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
