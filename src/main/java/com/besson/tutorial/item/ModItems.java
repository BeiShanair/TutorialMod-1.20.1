package com.besson.tutorial.item;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.item.custom.ModArmorItem;
import com.besson.tutorial.item.custom.PickaxeAxeItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item ICE_ETHER = registerItem("ice_ether", new Item(new Item.Settings()));
    public static final Item RAW_ICE_ETHER = registerItem("raw_ice_ether", new Item(new Item.Settings()));
    public static final Item CARDBOARD = registerItem("material/cardboard", new Item(new Item.Settings()));

    public static final Item CORN = registerItems("corn", new Item(new Item.Settings().food(ModFoodComponents.CORN)));
    public static final Item STRAWBERRY = registerItems("strawberry", new Item(new Item.Settings().food(ModFoodComponents.STRAWBERRY)));
    public static final Item CHEESE = registerItems("cheese", new Item(new Item.Settings().food(ModFoodComponents.CHEESE)));

    public static final Item ANTHRACITE = registerItems("anthracite", new Item(new Item.Settings()));

    public static final Item FIRE_ETHER = registerItems("fire_ether", new Item(new Item.Settings()));

    public static final Item FIRE_ETHER_SWORD = registerItems("fire_ether_sword", new SwordItem(ModToolMaterials.FIRE_ETHER,
            3, -2.0F, new Item.Settings()));
    public static final Item FIRE_ETHER_SHOVEL = registerItems("fire_ether_shovel", new ShovelItem(ModToolMaterials.FIRE_ETHER,
            1.5f, -3.0F, new Item.Settings()));
    public static final Item FIRE_ETHER_PICKAXE = registerItems("fire_ether_pickaxe", new PickaxeItem(ModToolMaterials.FIRE_ETHER,
            2, -2.8F, new Item.Settings()));
    public static final Item FIRE_ETHER_AXE = registerItems("fire_ether_axe", new AxeItem(ModToolMaterials.FIRE_ETHER,
            6.0f, -3.2F, new Item.Settings()));
    public static final Item FIRE_ETHER_HOE = registerItems("fire_ether_hoe", new HoeItem(ModToolMaterials.FIRE_ETHER,
            -4, 0.0F, new Item.Settings()));

    public static final Item PICKAXE_AXE = registerItems("pickaxe_axe", new PickaxeAxeItem(
            ModToolMaterials.FIRE_ETHER, 6.0F, -2.8F, new Item.Settings().fireproof()));

    public static final Item ICE_ETHER_HELMET = registerItems("ice_ether_helmet",
            new ModArmorItem(ModArmorMaterials.ICE_ETHER, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item ICE_ETHER_CHESTPLATE = registerItems("ice_ether_chestplate",
            new ArmorItem(ModArmorMaterials.ICE_ETHER, ArmorItem.Type.CHESTPLATE, new Item.Settings()));
    public static final Item ICE_ETHER_LEGGINGS = registerItems("ice_ether_leggings",
            new ArmorItem(ModArmorMaterials.ICE_ETHER, ArmorItem.Type.LEGGINGS, new Item.Settings()));
    public static final Item ICE_ETHER_BOOTS = registerItems("ice_ether_boots",
            new ArmorItem(ModArmorMaterials.ICE_ETHER, ArmorItem.Type.BOOTS, new Item.Settings()));

    public static final Item ICE_ETHER_HORSE_ARMOR = registerItems("ice_ether_horse_armor",
            new HorseArmorItem(11 , "ice_ether", new Item.Settings().maxCount(1)));

    public static final Item STRAWBERRY_SEEDS = registerItems("strawberry_seeds",
            new AliasedBlockItem(ModBlocks.STRAWBERRY_CROP, new Item.Settings()));

    public static Item registerItems(String id, Item item) {
        return Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), new Identifier(TutorialMod.MOD_ID, id)), item);
    }

    public static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(TutorialMod.MOD_ID, id), item);
    }


    public static Item register(String id, Item item) {
        return register(new Identifier(TutorialMod.MOD_ID, id), item);
    }

    public static Item register(Identifier id, Item item) {
        return register(RegistryKey.of(Registries.ITEM.getKey(), id), item);
    }

    public static Item register(RegistryKey<Item> key, Item item) {
        if (item instanceof BlockItem) {
            ((BlockItem)item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, key, item);
    }

    private static void addItemToItemGroup(FabricItemGroupEntries entries) {
        entries.add(ICE_ETHER);
        entries.add(RAW_ICE_ETHER);
    }

    private static void addItemToItemGroup2(FabricItemGroupEntries entries) {
        entries.add(CARDBOARD);
    }

    public static void registerItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(ModItems::addItemToItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemToItemGroup2);
    }
}
