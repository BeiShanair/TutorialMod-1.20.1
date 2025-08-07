package com.besson.tutorial.mixin;

import com.besson.tutorial.item.custom.ModDurabilityItem;
import com.google.common.collect.Lists;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RepairItemRecipe;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeMixin {
    @Inject(method = "matches(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/world/World;)Z",
    at = @At("RETURN"), cancellable = true)
    private void preventRepair(RecipeInputInventory recipeInputInventory, World world, CallbackInfoReturnable<Boolean> cir)
    {
        List<ItemStack> list = Lists.<ItemStack>newArrayList();

        for (int i = 0; i < recipeInputInventory.size(); i++) {
            ItemStack itemStack = recipeInputInventory.getStack(i);
            if (!itemStack.isEmpty()) {
                list.add(itemStack);
                if (list.size() > 1) {
                    ItemStack itemStack1 = (ItemStack) list.get(0);
                    if (itemStack.getItem() instanceof ModDurabilityItem && itemStack1.getItem() instanceof ModDurabilityItem) {
                        cir.setReturnValue(false);
                        return;
                    }
                }
            }
        }
    }
}
