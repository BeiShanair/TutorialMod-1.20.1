package com.besson.tutorial.villager;

import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;

public class ModTrades {
    public static void registerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> {
            factories.add(new TradeOffers.BuyForOneEmeraldFactory(ModItems.CORN, 2, 12, 5));
            factories.add(new TradeOffers.SellItemFactory(ModItems.STRAWBERRY_SEEDS.getDefaultStack(), 1, 12, 5, 2, 0.5f));
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(ModItems.STRAWBERRY, 5),
                    new ItemStack(Items.EMERALD, 6),
                    2, 7, 0.5f
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2, factories -> {
            factories.add(new TradeOffers.ProcessItemFactory(Items.MILK_BUCKET, 1, 2, ModItems.CHEESE, 3, 16, 5));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(ModItems.ICE_ETHER, 16),
                    EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(Enchantments.SHARPNESS, 2)),
                    3, 12, 0.5F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.ICE_ETHER_MASTER, 1, factories -> {
            factories.add(new TradeOffers.BuyForOneEmeraldFactory(ModItems.ICE_ETHER, 2, 12, 5));
            factories.add(new TradeOffers.SellItemFactory(ModItems.FIRE_ETHER_AXE.getDefaultStack(), 1, 1, 5, 2, 0.5f));
        });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.ICE_ETHER_MASTER, 2, factories -> {
            factories.add(new TradeOffers.BuyForOneEmeraldFactory(ModItems.FIRE_ETHER, 2, 12, 5));
            factories.add(new TradeOffers.SellItemFactory(ModBlocks.ICE_ETHER_BLOCK.asItem().getDefaultStack(), 1, 12, 5, 2, 0.5f));
        });
    }
}
