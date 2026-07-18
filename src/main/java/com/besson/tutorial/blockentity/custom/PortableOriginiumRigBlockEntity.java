package com.besson.tutorial.blockentity.custom;

import com.besson.tutorial.blockentity.ModBlockEntities;
import com.besson.tutorial.screen.custom.PortableOriginiumRigScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PortableOriginiumRigBlockEntity extends BlockEntity implements GeoBlockEntity, ExtendedScreenHandlerFactory {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    
    public final PropertyDelegate propertyDelegate;
    public int progress = 0;
    public int maxProgress = 60;
    
    protected final SimpleInventory outputInv = new SimpleInventory(1) {
        @Override
        public void markDirty() {
            super.markDirty();
            PortableOriginiumRigBlockEntity.this.markDirty();
        }

        @Override
        public boolean isValid(int slot, ItemStack stack) {
            return false;
        }
    };
    
    protected final InventoryStorage outputStorage = InventoryStorage.of(outputInv, null);
    protected boolean isWorking;
    
    public PortableOriginiumRigBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PORTABLE_ORIGINIUM_RIG, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> PortableOriginiumRigBlockEntity.this.progress;
                    case 1 -> PortableOriginiumRigBlockEntity.this.maxProgress;
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
    
    //region Tick
    public static void tick(World world, BlockPos pos, BlockState state, PortableOriginiumRigBlockEntity be) {
        if (world.isClient()) return;
        
        boolean canProcess = be.hasCorrectRecipe(world);
        
        if (be.isWorking() != canProcess) {
            be.isWorking = canProcess;
            world.updateListeners(pos, state, state, 3);
        }
        
        if (canProcess) {
            be.incrementProgress();
            
            if (be.hasProgressingFinished()) {
                be.craftItem(world);
                be.resetProgress();
            }
        } else {
            be.resetProgress();
        }
        
        be.markDirty();
    }
    
    private void craftItem(World world) {
        ItemStack out = outputInv.getStack(0);
        outputInv.setStack(0, new ItemStack(Items.DIAMOND, out.getCount() + 1));
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
    
    private boolean hasCorrectRecipe(World world) {
        BlockState belowState = world.getBlockState(pos.down());
        return belowState.isOf(Blocks.DIAMOND_ORE) && canOutputAccept(Items.DIAMOND.getDefaultStack());
    }
    
    private boolean canOutputAccept(ItemStack result) {
        ItemStack out = outputInv.getStack(0);
        return (out.isEmpty() || out.getItem() == result.getItem())
                && out.getCount() + result.getCount() <= out.getMaxCount();
    }
    //endregion

    public DefaultedList<ItemStack> getItems() {
        DefaultedList<ItemStack> inv = DefaultedList.ofSize(1, ItemStack.EMPTY);
        inv.set(0, outputInv.getStack(0));
        return inv;
    }
    
    public boolean isWorking() {
        return isWorking;
    }
    
    public SimpleInventory getOutputInv() {
        return outputInv;
    }
    
    @Nullable
    public Storage<ItemVariant> getStorage() {
        return outputStorage;
    }
    
    //region Data

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        NbtCompound outputTag = new NbtCompound();
        Inventories.writeNbt(outputTag, outputInv.stacks);
        nbt.put("output", outputTag);
        nbt.putInt("progress", progress);
        nbt.putBoolean("isWorking", isWorking);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("output")) {
            Inventories.readNbt(nbt.getCompound("output"), outputInv.stacks);
        }
        progress = nbt.getInt("progress");
        isWorking = nbt.getBoolean("isWorking");
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    //endregion

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0,
                state -> this.isWorking() 
                        ? state.setAndContinue(RawAnimation.begin().thenLoop("working"))
                        : state.setAndContinue(RawAnimation.begin().thenLoop("idle"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("blockEntity.porable_originium_rig");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PortableOriginiumRigScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }
}
