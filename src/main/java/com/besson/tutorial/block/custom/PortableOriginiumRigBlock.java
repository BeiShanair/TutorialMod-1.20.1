package com.besson.tutorial.block.custom;

import com.besson.tutorial.blockentity.custom.PortableOriginiumRigBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PortableOriginiumRigBlock extends BlockWithEntity {
    public PortableOriginiumRigBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PortableOriginiumRigBlockEntity(pos, state);
    }
}
