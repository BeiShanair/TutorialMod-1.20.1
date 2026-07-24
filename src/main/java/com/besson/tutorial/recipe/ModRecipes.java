package com.besson.tutorial.recipe;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.recipe.custom.OreRigRecipe;
import com.besson.tutorial.recipe.custom.RefiningUnitRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(TutorialMod.MOD_ID, OreRigRecipe.Serializer.ID),
                OreRigRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(TutorialMod.MOD_ID, OreRigRecipe.Type.ID),
                OreRigRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(TutorialMod.MOD_ID, RefiningUnitRecipe.Serializer.ID),
                RefiningUnitRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(TutorialMod.MOD_ID, RefiningUnitRecipe.Type.ID),
                RefiningUnitRecipe.Type.INSTANCE);
    }
}
