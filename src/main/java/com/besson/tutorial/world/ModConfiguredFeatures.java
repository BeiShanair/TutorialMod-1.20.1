package com.besson.tutorial.world;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> ICE_ETHER_TREE_KEY = of("ice_ether_tree");

    public static final RegistryKey<ConfiguredFeature<?, ?>> SIMPLE_FLOWER_KEY = of("simple_flower");

    public static final RegistryKey<ConfiguredFeature<?, ?>> ICE_ETHER_ORE_KEY = of("ice_ether_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_ICE_ETHER_ORE_KEY = of("nether_ice_ether_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> END_ICE_ETHER_ORE_KEY = of("end_ice_ether_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeatures.register(featureRegisterable, ICE_ETHER_TREE_KEY, Feature.TREE,
                new TreeFeatureConfig.Builder(
                        BlockStateProvider.of(ModBlocks.ICE_ETHER_LOG),
                        new StraightTrunkPlacer(4,2,1),
                        BlockStateProvider.of(ModBlocks.ICE_ETHER_LEAVES),
                        new BlobFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(2), 3),
                        new TwoLayersFeatureSize(1, 0, 2)
                ).build());

        ConfiguredFeatures.register(featureRegisterable, SIMPLE_FLOWER_KEY, Feature.FLOWER,
                new RandomPatchFeatureConfig(20, 4, 3,
                        PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SIMPLE_FLOWER)))));

        RuleTest stoneReplace = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplace = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplace = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest endReplace = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overWorldTarget = List.of(
                OreFeatureConfig.createTarget(stoneReplace, ModBlocks.ICE_ETHER_ORE.getDefaultState()),
                OreFeatureConfig.createTarget(deepslateReplace, ModBlocks.ICE_ETHER_ORE.getDefaultState())
        );
        List<OreFeatureConfig.Target> netherTarget = List.of(
                OreFeatureConfig.createTarget(netherReplace, ModBlocks.ICE_ETHER_ORE.getDefaultState())
        );
        List<OreFeatureConfig.Target> endTarget = List.of(
                OreFeatureConfig.createTarget(endReplace, ModBlocks.ICE_ETHER_ORE.getDefaultState())
        );

        ConfiguredFeatures.register(featureRegisterable, ICE_ETHER_ORE_KEY, Feature.ORE,
                new OreFeatureConfig(overWorldTarget, 9));
        ConfiguredFeatures.register(featureRegisterable, NETHER_ICE_ETHER_ORE_KEY, Feature.ORE,
                new OreFeatureConfig(netherTarget, 12));
        ConfiguredFeatures.register(featureRegisterable, END_ICE_ETHER_ORE_KEY, Feature.ORE,
                new OreFeatureConfig(endTarget, 10));

    }

    public static RegistryKey<ConfiguredFeature<?, ?>> of(String id) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(TutorialMod.MOD_ID, id));
    }
}
