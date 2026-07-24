package com.besson.tutorial.recipe.builder;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.recipe.ItemCountInput;
import com.besson.tutorial.recipe.custom.RefiningUnitRecipe;
import com.google.gson.JsonObject;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class RefiningUnitRecipeBuilder {
    private ItemCountInput input;
    private ItemConvertible output;
    private final int outputCount;
    
    private RefiningUnitRecipeBuilder(ItemCountInput input, ItemConvertible output, int outputCount) {
        this.input = input;
        this.output = output;
        this.outputCount = outputCount;
    }
    
    public static RefiningUnitRecipeBuilder create(ItemCountInput input, ItemConvertible output, int outputCount) {
        return new RefiningUnitRecipeBuilder(input, output, outputCount);
    }

    public static RefiningUnitRecipeBuilder create(ItemConvertible input, ItemConvertible output) {
        return new RefiningUnitRecipeBuilder(new ItemCountInput(input, 1), output, 1);
    }

    public static RefiningUnitRecipeBuilder create(ItemCountInput input, ItemConvertible output) {
        return new RefiningUnitRecipeBuilder(input, output, 1);
    }
    
    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier id) {
        exporter.accept(new RecipeJsonProvider() {
            @Override
            public void serialize(JsonObject json) {
                json.addProperty("type", TutorialMod.MOD_ID + ":refining_unit");
                JsonObject inputJson = new JsonObject();
                inputJson.addProperty("item", Registries.ITEM.getId(input.itemConvertible().asItem()).toString());
                inputJson.addProperty("count", input.count());
                json.add("input", inputJson);
                
                JsonObject outputJson = new JsonObject();
                outputJson.addProperty("item", Registries.ITEM.getId(output.asItem()).toString());
                outputJson.addProperty("count", outputCount);
                json.add("output", outputJson);
            }

            @Override
            public Identifier getRecipeId() {
                return id;
            }

            @Override
            public RecipeSerializer<?> getSerializer() {
                return RefiningUnitRecipe.Serializer.INSTANCE;
            }

            @Override
            public @Nullable JsonObject toAdvancementJson() {
                return null;
            }

            @Override
            public @Nullable Identifier getAdvancementId() {
                return null;
            }
        });
    }
}
