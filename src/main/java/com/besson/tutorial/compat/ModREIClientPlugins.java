package com.besson.tutorial.compat;

import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.compat.category.OreRigCategory;
import com.besson.tutorial.compat.display.OreRigDisplay;
import com.besson.tutorial.recipe.custom.OreRigRecipe;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class ModREIClientPlugins implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new OreRigCategory());
        registry.addWorkstations(OreRigCategory.ORE_RIG, EntryStacks.of(ModBlocks.PORTABLE_ORIGINIUM_RIG));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(OreRigRecipe.class, OreRigRecipe.Type.INSTANCE, OreRigDisplay::new);
    }
}
