package com.besson.tutorial.item.custom;

import com.besson.tutorial.renderer.RefiningUnitItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class RefiningUnitItem extends BlockItem implements GeoItem {
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    public RefiningUnitItem(Block block, Settings settings) {
        super(block, settings);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final RefiningUnitItemRenderer renderer = new RefiningUnitItemRenderer();
            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public ActionResult place(ItemPlacementContext context) {
        World world = context.getWorld();
        if (!world.isClient()) {
            BlockPos pos = context.getBlockPos();
            for (BlockPos p : BlockPos.iterate(pos.add(1, 0, 1), pos.add(-1, 0, -1))) {
                if (!world.getBlockState(p).isReplaceable()) {
                    PlayerEntity player = context.getPlayer();
                    if (player != null) {
                        player.sendMessage(Text.translatable("message.no_enough_area"), false);
                    }
                    return ActionResult.FAIL;
                }
            }
        }
        return super.place(context);
    }
}
