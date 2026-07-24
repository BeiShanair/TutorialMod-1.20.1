package com.besson.tutorial.compat;

import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.compat.category.OreRigCategory;
import com.besson.tutorial.compat.category.RefiningUnitCategory;
import com.besson.tutorial.compat.display.OreRigDisplay;
import com.besson.tutorial.compat.display.RefiningUnitDisplay;
import com.besson.tutorial.recipe.custom.OreRigRecipe;
import com.besson.tutorial.recipe.custom.RefiningUnitRecipe;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;

public class ModREIClientPlugins implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new OreRigCategory());
        registry.addWorkstations(OreRigCategory.ORE_RIG, EntryStacks.of(ModBlocks.PORTABLE_ORIGINIUM_RIG));

        registry.add(new RefiningUnitCategory());
        registry.addWorkstations(RefiningUnitCategory.REFINING_UNIT, EntryStacks.of(ModBlocks.REFINING_UNIT));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(OreRigRecipe.class, OreRigRecipe.Type.INSTANCE, OreRigDisplay::new);
        registry.registerRecipeFiller(RefiningUnitRecipe.class, RefiningUnitRecipe.Type.INSTANCE, RefiningUnitDisplay::new);
    }
}
