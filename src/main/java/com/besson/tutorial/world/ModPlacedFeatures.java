package com.besson.tutorial.world;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> ICE_ETHER_TREE_PLACED_KEY = of("ice_ether_tree_placed");

    public static final RegistryKey<PlacedFeature> SIMPLE_FLOWER_PLACED_KEY = of("simple_flower_placed");

    public static final RegistryKey<PlacedFeature> ICE_ETHER_ORE_PLACED_KEY = of("ice_ether_ore_placed");
    public static final RegistryKey<PlacedFeature> NETHER_ICE_ETHER_ORE_PLACED_KEY = of("nether_ice_ether_ore_placed");
    public static final RegistryKey<PlacedFeature> END_ICE_ETHER_ORE_PLACED_KEY = of("end_ice_ether_ore_placed");

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        PlacedFeatures.register(featureRegisterable, ICE_ETHER_TREE_PLACED_KEY,
                registryEntryLookup.getOrThrow(ModConfiguredFeatures.ICE_ETHER_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(2, 0.1f, 2),
                        ModBlocks.ICE_ETHER_TREE_SAPLING
                ));

        PlacedFeatures.register(featureRegisterable, SIMPLE_FLOWER_PLACED_KEY,
                registryEntryLookup.getOrThrow(ModConfiguredFeatures.SIMPLE_FLOWER_KEY),
                RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(),
                PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, ICE_ETHER_ORE_PLACED_KEY,
                registryEntryLookup.getOrThrow(ModConfiguredFeatures.ICE_ETHER_ORE_KEY),
                modifiersWithCount(10,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
        PlacedFeatures.register(featureRegisterable, NETHER_ICE_ETHER_ORE_PLACED_KEY,
                registryEntryLookup.getOrThrow(ModConfiguredFeatures.NETHER_ICE_ETHER_ORE_KEY),
                modifiersWithCount(12,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
        PlacedFeatures.register(featureRegisterable, END_ICE_ETHER_ORE_PLACED_KEY,
                registryEntryLookup.getOrThrow(ModConfiguredFeatures.END_ICE_ETHER_ORE_KEY),
                modifiersWithCount(6,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
    }

    public static RegistryKey<PlacedFeature> of(String id) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(TutorialMod.MOD_ID, id));
    }
    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }
}
