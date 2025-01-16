package com.besson.tutorial.item;

import com.besson.tutorial.TutorialMod;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> TUTORIAL_GROUP = register("tutorial_group");

    private static RegistryKey<ItemGroup> register(String id) {
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(TutorialMod.MOD_ID, id));
    }

    public static final ItemGroup TUTORIAL_GROUP2 = Registry.register(
            Registries.ITEM_GROUP,
            new Identifier(TutorialMod.MOD_ID, "tutorial_group2"),
            ItemGroup.create(null, -1)
                    .displayName(Text.translatable("itemGroup.tutorial_group2"))
                    .icon(() -> new ItemStack(ModItems.CARDBOARD))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.CARDBOARD);
                        entries.add(Items.DIAMOND);
                        entries.add(Blocks.STONE);
                    }).build());

    public static void registerGroups() {
        Registry.register(
                Registries.ITEM_GROUP,
                TUTORIAL_GROUP,
                ItemGroup.create(ItemGroup.Row.TOP, 7)
                        .displayName(Text.translatable("itemGroup.tutorial_group"))
                        .icon(() -> new ItemStack(ModItems.ICE_ETHER))
                        .entries((displayContext, entries) -> {
                            entries.add(ModItems.ICE_ETHER);
                            entries.add(ModItems.RAW_ICE_ETHER);
                        }).build());
    }
}
