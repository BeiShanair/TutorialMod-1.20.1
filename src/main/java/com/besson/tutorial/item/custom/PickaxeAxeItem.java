package com.besson.tutorial.item.custom;

import com.besson.tutorial.tag.ModBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class PickaxeAxeItem extends AxeItem {
    public PickaxeAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return state.isIn(ModBlockTags.PICKAXE_AXE) ? this.miningSpeed : 1.0F;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isIn(ModBlockTags.PICKAXE_AXE);
    }
}
