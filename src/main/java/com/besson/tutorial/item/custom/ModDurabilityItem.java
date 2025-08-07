package com.besson.tutorial.item.custom;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.item.ItemStack;

public interface ModDurabilityItem extends FabricItem {
    @Override
    default ItemStack getRecipeRemainder(ItemStack stack) {
        if (stack.getDamage() < stack.getMaxDamage() - 1){
            ItemStack itemStack = stack.copy();
            itemStack.setDamage(itemStack.getDamage() + 1);
            return itemStack;
        }
        return ItemStack.EMPTY;
    }
}
