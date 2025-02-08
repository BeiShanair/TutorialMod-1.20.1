package com.besson.tutorial.datagen;

import com.besson.tutorial.item.ModItems;
import com.besson.tutorial.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModItemTags.SUGAR_INGREDIENTS)
                .add(ModItems.CHEESE)
                .add(ModItems.STRAWBERRY)
                .add(Items.BEETROOT);

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.ICE_ETHER_HELMET, ModItems.ICE_ETHER_CHESTPLATE, ModItems.ICE_ETHER_LEGGINGS, ModItems.ICE_ETHER_BOOTS);
    }
}
