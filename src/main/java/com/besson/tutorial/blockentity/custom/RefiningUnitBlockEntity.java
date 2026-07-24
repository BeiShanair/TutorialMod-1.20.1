package com.besson.tutorial.blockentity.custom;

import com.besson.tutorial.block.ModBlockEntityWithFacing;
import com.besson.tutorial.blockentity.ModBlockEntities;
import com.besson.tutorial.recipe.InputEntry;
import com.besson.tutorial.recipe.custom.RefiningUnitRecipe;
import com.besson.tutorial.screen.custom.RefiningUnitScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class RefiningUnitBlockEntity extends BlockEntity implements GeoBlockEntity, ExtendedScreenHandlerFactory {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    
    public int progress = 0;
    public int maxProgress = 60;
    public final PropertyDelegate propertyDelegate;
    protected final SimpleInventory inputInv = new SimpleInventory(1) {
        @Override
        public void markDirty() {
            super.markDirty();
            RefiningUnitBlockEntity.this.markDirty();
        }

        @Override
        public boolean isValid(int slot, ItemStack stack) {
            if (stack == null || stack.isEmpty()) return false;
            ItemStack current = this.getStack(slot);
            if (current.isEmpty()) {
                return true;
            }
            return ItemStack.canCombine(current, stack) && current.getCount() < current.getMaxCount();
        }
    };
    
    protected final SimpleInventory outputInv = new SimpleInventory(1) {
        @Override
        public void markDirty() {
            super.markDirty();
            RefiningUnitBlockEntity.this.markDirty();
        }

        @Override
        public boolean isValid(int slot, ItemStack stack) {
            return false;
        }
    };
    
    protected final InventoryStorage inputStorage = InventoryStorage.of(inputInv, null);
    protected final InventoryStorage outputStorage = InventoryStorage.of(outputInv, null);
    
    public RefiningUnitBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.REFINING_UNIT, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> RefiningUnitBlockEntity.this.progress;
                    case 1 -> RefiningUnitBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {

            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public SimpleInventory getInputInv() {
        return inputInv;
    }

    public SimpleInventory getOutputInv() {
        return outputInv;
    }
    
    private Direction getFacing(BlockState state) {
        return state.get(ModBlockEntityWithFacing.FACING);
    }
    
    @Nullable
    public Storage<ItemVariant> getStorage(BlockState state, Direction direction) {
        Direction facing = getFacing(state);
        
        if (direction == facing) {
            return inputStorage;
        } else if (direction == facing.getOpposite()) {
            return outputStorage;
        }
        return null;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }
    
    public static void tick(World world, BlockPos pos, BlockState state, RefiningUnitBlockEntity be) {
        if (world.isClient()) return;
        
        if (be.isOutputSlotAvailable()) {
            boolean hasRecipe = be.hasCorrectRecipe(world);
            
            if (hasRecipe) {
                be.incrementProgress();
                if (be.hasProgressingFinished()) {
                    be.craftItem(world);
                    be.resetProgress();
                }
            } else {
                be.resetProgress();
            }
        } else {
            be.resetProgress();
        }
        
        be.markDirty();
    }
    
    private Optional<RefiningUnitRecipe> getMathRecipe(World world) {
        SimpleInventory inv = new SimpleInventory(1);
        inv.setStack(0, inputInv.getStack(0));
        return world.getRecipeManager().getFirstMatch(RefiningUnitRecipe.Type.INSTANCE, inv, world);
    }
    
    private void craftItem(World world) {
        getMathRecipe(world).ifPresent(recipe -> {
            ItemStack result = recipe.getOutput(world.getRegistryManager());
            ItemStack out = outputInv.getStack(0);
            outputInv.setStack(0, new ItemStack(result.getItem(), out.getCount() + result.getCount()));

            InputEntry recipeInput = recipe.getInput();
            ItemStack stack = inputInv.getStack(0);
            if (recipeInput.ingredient().test(stack) && stack.getCount() >= recipeInput.count()) {
                inputInv.removeStack(0, recipeInput.count());
            }
        });
    }
    
    private boolean hasCorrectRecipe(World world) {
        Optional<RefiningUnitRecipe> match = getMathRecipe(world);
        
        if (match.isPresent()) {
            RefiningUnitRecipe recipe = match.get();
            
            InputEntry recipeInput = recipe.getInput();
            boolean matched = false;
            ItemStack stack = inputInv.getStack(0);
            if (recipeInput.ingredient().test(stack) && stack.getCount() >= recipeInput.count()) {
                matched = true;
            }
            if (!matched) return false;
            
            ItemStack result = recipe.getOutput(world.getRegistryManager());
            return canOutputAccept(result);
        }
        
        return false;
    }

    private boolean canOutputAccept(ItemStack result) {
        ItemStack out = outputInv.getStack(0);
        return (out.isEmpty() || out.getItem() == result.getItem())
                && out.getCount() + result.getCount() <= out.getMaxCount();
    }
    
    private boolean isOutputSlotAvailable() {
        return outputInv.getStack(0).isEmpty() || outputInv.getStack(0).getCount() < outputInv.getStack(0).getMaxCount();
    }

    private void resetProgress() {
        progress = 0;
    }

    private void incrementProgress() {
        progress++;
    }
    private boolean hasProgressingFinished() {
        return progress >= maxProgress;
    }
    
    public DefaultedList<ItemStack> getItems() {
        DefaultedList<ItemStack> combined = DefaultedList.ofSize(inputInv.size() + outputInv.size(), ItemStack.EMPTY);
        for (int i = 0; i < inputInv.size(); i++) {
            combined.set(i, inputInv.getStack(i));
        }
        for (int i = 0; i < outputInv.size(); i++) {
            combined.set(i + inputInv.size(), outputInv.getStack(i));
        }
        return combined;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("blockEntity.refining_unit");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new RefiningUnitScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        NbtCompound inputTag = new NbtCompound();
        Inventories.writeNbt(inputTag, inputInv.stacks);
        nbt.put("input", inputTag);
        
        NbtCompound outputTag = new NbtCompound();
        Inventories.writeNbt(outputTag, outputInv.stacks);
        nbt.put("output", outputTag);
        nbt.putInt("progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("input")) {
            Inventories.readNbt(nbt.getCompound("input"), inputInv.stacks);
        }
        if (nbt.contains("output")) {
            Inventories.readNbt(nbt.getCompound("output"), outputInv.stacks);
        }
        progress = nbt.getInt("progress");
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
