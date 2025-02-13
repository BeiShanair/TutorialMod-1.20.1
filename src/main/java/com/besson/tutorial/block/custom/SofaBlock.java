package com.besson.tutorial.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class SofaBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public static final EnumProperty<Type> TYPE = EnumProperty.of("type", Type.class);

    public SofaBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(TYPE, Type.SINGLE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return this.getRelatedBlockState(state, world, pos, state.get(FACING));
    }

    private BlockState getRelatedBlockState(BlockState state, WorldAccess world, BlockPos pos, Direction direction) {
        boolean left = isRelatedInDirection(world, pos, direction, true);
        boolean right = isRelatedInDirection(world, pos, direction, false);
        if (left && right) {
            return state.with(TYPE, Type.MIDDLE);
        } else if (left) {
            return  state.with(TYPE, Type.LEFT);
        } else if (right) {
            return state.with(TYPE, Type.RIGHT);
        }
        return state.with(TYPE, Type.SINGLE);
    }

    private boolean isRelatedInDirection(WorldAccess world, BlockPos pos, Direction direction, boolean counterClockwise) {
        Direction rotate = counterClockwise ? direction.rotateYCounterclockwise() : direction.rotateYClockwise();
        return this.isRelatedBlock(world, pos, rotate, direction);
    }

    private boolean isRelatedBlock(WorldAccess world, BlockPos pos, Direction rotate, Direction direction) {
        BlockState state = world.getBlockState(pos.offset(rotate));
        if (state.getBlock() == this) {
            Direction direction1 = state.get(FACING);
            return direction1.equals(direction);
        }
        return false;
    }

    public enum Type implements StringIdentifiable {
        SINGLE("single"),
        LEFT("left"),
        RIGHT("right"),
        MIDDLE("middle");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
