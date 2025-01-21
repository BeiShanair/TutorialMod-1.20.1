package com.besson.tutorial.datagen;

import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.ICE_ETHER_BLOCK);
        addDrop(ModBlocks.RAW_ICE_ETHER_BLOCK);

//        addDrop(ModBlocks.ICE_ETHER_ORE, oreDrops(ModBlocks.ICE_ETHER_ORE, ModItems.RAW_ICE_ETHER));
        addDrop(ModBlocks.ICE_ETHER_ORE, likeCopperOreDrops(ModBlocks.ICE_ETHER_ORE, ModItems.RAW_ICE_ETHER, 2.0f, 5.0f));
    }

    public LootTable.Builder likeCopperOreDrops(Block drop, Item item, float min, float max) {
        return dropsWithSilkTouch(
                drop,
                (LootPoolEntry.Builder<?>)this.applyExplosionDecay(
                        drop,
                        ItemEntry.builder(item)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)))
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))
                )
        );
    }
}
