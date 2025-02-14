package com.besson.tutorial.datagen;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.block.ModBlockFamilies;
import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.block.custom.CornCropBlock;
import com.besson.tutorial.block.custom.SofaBlock;
import com.besson.tutorial.block.custom.StrawberryCropBlock;
import com.besson.tutorial.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.ArmorItem;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ModModelsProvider extends FabricModelProvider {
    public ModModelsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        ModBlockFamilies.getFamilies()
                .filter(BlockFamily::shouldGenerateModels)
                .forEach(family ->
                        blockStateModelGenerator.registerCubeAllModelTexturePool(family.getBaseBlock()).family(family));

//        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ICE_ETHER_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_ICE_ETHER_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ICE_ETHER_ORE);

        blockStateModelGenerator.registerCrop(ModBlocks.STRAWBERRY_CROP, StrawberryCropBlock.AGE, 0, 1, 2, 3, 4, 5);

        blockStateModelGenerator.blockStateCollector
                .accept(VariantsBlockStateSupplier.create(ModBlocks.CORN_CROP)
                                .coordinate(BlockStateVariantMap.create(CornCropBlock.AGE)
                                                .register(stage -> BlockStateVariant.create()
                                                                .put(VariantSettings.MODEL,
                                                                        blockStateModelGenerator.createSubModel(ModBlocks.CORN_CROP, "_stage" + stage,
                                                                                Models.CROSS, TextureMap::cross))
                                                )
                                )
                );

        blockStateModelGenerator.registerSimpleState(ModBlocks.ORANGE_NIGHTSTAND);

        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.SIMPLE_ORANGE_CLOCK);

        Identifier left = new Identifier(TutorialMod.MOD_ID, "block/sofa_left");
        Identifier right = new Identifier(TutorialMod.MOD_ID, "block/sofa_right");
        Identifier middle = new Identifier(TutorialMod.MOD_ID, "block/sofa_middle");
        Identifier single = new Identifier(TutorialMod.MOD_ID, "block/sofa");
        blockStateModelGenerator.blockStateCollector
                .accept(createSofaBlockState(ModBlocks.SOFA, left, right, middle, single));

        blockStateModelGenerator.registerSimpleState(ModBlocks.LAMP_BLOCK);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.BED);
    }

    public static BlockStateSupplier createSofaBlockState(Block block, Identifier left, Identifier right, Identifier middle, Identifier single) {
        return VariantsBlockStateSupplier.create(block)
                .coordinate(
                        BlockStateVariantMap.create(SofaBlock.TYPE, Properties.HORIZONTAL_FACING)
                                .register(SofaBlock.Type.LEFT, Direction.EAST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, left).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                .register(SofaBlock.Type.LEFT, Direction.SOUTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, left).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(SofaBlock.Type.LEFT, Direction.WEST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, left).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                                .register(SofaBlock.Type.LEFT, Direction.NORTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, left))

                                .register(SofaBlock.Type.RIGHT, Direction.EAST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, right).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                .register(SofaBlock.Type.RIGHT, Direction.SOUTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, right).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(SofaBlock.Type.RIGHT, Direction.WEST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, right).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                                .register(SofaBlock.Type.RIGHT, Direction.NORTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, right))

                                .register(SofaBlock.Type.MIDDLE, Direction.EAST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, middle).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                .register(SofaBlock.Type.MIDDLE, Direction.SOUTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, middle).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(SofaBlock.Type.MIDDLE, Direction.WEST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, middle).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                                .register(SofaBlock.Type.MIDDLE, Direction.NORTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, middle))

                                .register(SofaBlock.Type.SINGLE, Direction.EAST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, single).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                .register(SofaBlock.Type.SINGLE, Direction.SOUTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, single).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(SofaBlock.Type.SINGLE, Direction.WEST,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, single).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                                .register(SofaBlock.Type.SINGLE, Direction.NORTH,
                                        BlockStateVariant.create().put(VariantSettings.MODEL, single))
                );
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.ICE_ETHER, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_ICE_ETHER, Models.GENERATED);
        itemModelGenerator.register(ModItems.CARDBOARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.CORN, Models.GENERATED);
        itemModelGenerator.register(ModItems.STRAWBERRY, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHEESE, Models.GENERATED);
        itemModelGenerator.register(ModItems.ANTHRACITE, Models.GENERATED);

        itemModelGenerator.register(ModItems.FIRE_ETHER, Models.GENERATED);
        itemModelGenerator.register(ModItems.FIRE_ETHER_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FIRE_ETHER_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FIRE_ETHER_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FIRE_ETHER_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.FIRE_ETHER_HOE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PICKAXE_AXE, Models.HANDHELD);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.ICE_ETHER_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.ICE_ETHER_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.ICE_ETHER_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.ICE_ETHER_BOOTS);

        itemModelGenerator.register(ModItems.ICE_ETHER_HORSE_ARMOR, Models.GENERATED);
        itemModelGenerator.register(ModItems.A_MOMENT_APART_MUSIC_DISC, Models.GENERATED);
    }
}
