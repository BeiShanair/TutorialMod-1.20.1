package com.besson.tutorial.datagen;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.item.ModItems;
import com.besson.tutorial.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipesProvider extends FabricRecipeProvider {
    public ModRecipesProvider(FabricDataOutput output) {
        super(output);
    }

    public static final List<ItemConvertible> ICE_ETHER = List.of(ModItems.RAW_ICE_ETHER, ModBlocks.ICE_ETHER_ORE);
    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.ICE_ETHER,
                RecipeCategory.BUILDING_BLOCKS, ModBlocks.ICE_ETHER_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.RAW_ICE_ETHER,
                RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_ICE_ETHER_BLOCK);

        offerSmelting(exporter, ICE_ETHER, RecipeCategory.MISC, ModItems.ICE_ETHER, 0.7f, 200, "ice_ether");
        offerBlasting(exporter, ICE_ETHER, RecipeCategory.MISC, ModItems.ICE_ETHER, 0.7f, 100, "ice_ether");

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.ICE_ETHER_ORE, 1)
                .input(ModItems.RAW_ICE_ETHER)
                .input(Items.STONE)
                .criterion(hasItem(ModItems.RAW_ICE_ETHER), conditionsFromItem(ModItems.RAW_ICE_ETHER))
                .offerTo(exporter, new Identifier(TutorialMod.MOD_ID, "ice_ether_ore"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, Items.SUGAR, 3)
                .pattern("###")
                .input('#', ModItemTags.SUGAR_INGREDIENTS)
                .criterion(hasItem(Items.BEETROOT), conditionsFromTag(ModItemTags.SUGAR_INGREDIENTS))
                .offerTo(exporter, new Identifier(TutorialMod.MOD_ID, "sugar_from_beetroot"));

        offerFoodCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING,
                600, ModItems.RAW_ICE_ETHER, ModItems.ICE_ETHER, 0.35f);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ANTHRACITE, 1)
                .input(Items.COAL)
                .input(ModItems.FIRE_ETHER)
                .criterion("has_item", conditionsFromItem(Items.COAL))
                .offerTo(exporter, new Identifier(TutorialMod.MOD_ID, "anthracite_from_coal"));
    }
}
