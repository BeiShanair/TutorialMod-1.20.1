package com.besson.tutorial.block;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.block.custom.*;
import com.besson.tutorial.block.custom.ModPillarBlock;
import com.besson.tutorial.sound.ModSoundEvents;
import com.besson.tutorial.world.tree.IceEtherTreeGenerator;
import com.terraformersmc.terraform.sign.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block ICE_ETHER_BLOCK = register("ice_ether_block", new Block(AbstractBlock.Settings.copy(Blocks.STONE).sounds(ModSoundEvents.BLOCK_SOUND_GROUP)));
    public static final Block RAW_ICE_ETHER_BLOCK = register("raw_ice_ether_block", new Block(AbstractBlock.Settings.create().strength(0.2f, 0.2f)));
    public static final Block ICE_ETHER_ORE = register("ice_ether_ore", new Block(AbstractBlock.Settings.create().requiresTool().strength(3.0f, 3.0f)));

    public static final Block ICE_ETHER_STAIRS = register("ice_ether_stairs",
            new StairsBlock(ICE_ETHER_BLOCK.getDefaultState(), AbstractBlock.Settings.copy(ICE_ETHER_BLOCK)));
    public static final Block ICE_ETHER_SLAB = register("ice_ether_slab",
            new SlabBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK)));
    public static final Block ICE_ETHER_BUTTON = register("ice_ether_button",
            new ButtonBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK), BlockSetType.STONE, 60, false));
    public static final Block ICE_ETHER_PRESSURE_PLATE = register("ice_ether_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.copy(ICE_ETHER_BLOCK), BlockSetType.STONE));
    public static final Block ICE_ETHER_FENCE = register("ice_ether_fence",
            new FenceBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK)));
    public static final Block ICE_ETHER_FENCE_GATE = register("ice_ether_fence_gate",
            new FenceGateBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK), WoodType.OAK));
    public static final Block ICE_ETHER_WALL = register("ice_ether_wall",
            new WallBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK)));
    public static final Block ICE_ETHER_DOOR = register("ice_ether_door",
            new DoorBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK), BlockSetType.IRON));
    public static final Block ICE_ETHER_TRAPDOOR = register("ice_ether_trapdoor",
            new TrapdoorBlock(AbstractBlock.Settings.copy(ICE_ETHER_BLOCK).nonOpaque(), BlockSetType.STONE));

    public static final Block STRAWBERRY_CROP = Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, "strawberry_crop"),
            new StrawberryCropBlock(AbstractBlock.Settings.create().noCollision().ticksRandomly().breakInstantly().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block CORN_CROP = Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, "corn_crop"),
            new CornCropBlock(AbstractBlock.Settings.create().noCollision().ticksRandomly().breakInstantly().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block ORANGE_NIGHTSTAND = register("orange_nightstand",
            new Block(AbstractBlock.Settings.create().strength(2.0f, 6.0f).nonOpaque()));
    public static final Block SIMPLE_ORANGE_CLOCK = register("simple_orange_clock",
            new SimpleOrangeClock(AbstractBlock.Settings.create().strength(2.0f, 6.0f)));

    public static final Block SOFA = register("sofa",
            new SofaBlock(AbstractBlock.Settings.create().strength(2.0f, 6.0f).nonOpaque()));

    public static final Block LAMP_BLOCK = register("lamp_block",
            new LampBlock(AbstractBlock.Settings.create().strength(2.0f, 6.0f).nonOpaque()));

    public static final Block BED = register("bed",
            new ModBedBlock(DyeColor.BLACK, AbstractBlock.Settings.create().strength(2.0f, 6.0f).nonOpaque()));

    public static final Block PILLAR = register("pillar",
            new ModPillarBlock(AbstractBlock.Settings.create().strength(2.0f, 6.0f).nonOpaque()));

    public static final Block FENCE = register("fence",
            new ModFenceBlock(AbstractBlock.Settings.create().strength(2.0f, 6.0f).nonOpaque()));
    public static final Block SIMPLE_CABINET = register("simple_cabinet",
            new SimpleCabinet(AbstractBlock.Settings.create().strength(2.0f, 6.0f).nonOpaque(), () -> ModBlockEntities.SIMPLE_CABINET));

    public static final Block OIL = Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, "oil"),
            new FluidBlock(ModFluids.OIL, AbstractBlock.Settings.copy(Blocks.WATER)));

    public static final Block ICE_ETHER_LOG = register("ice_ether_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)));
    public static final Block ICE_ETHER_WOOD = register("ice_ether_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)));
    public static final Block STRIPPED_ICE_ETHER_LOG = register("stripped_ice_ether_log",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)));
    public static final Block STRIPPED_ICE_ETHER_WOOD = register("stripped_ice_ether_wood",
            new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)));
    public static final Block ICE_ETHER_LEAVES = register("ice_ether_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
    public static final Block ICE_ETHER_PLANKS = register("ice_ether_planks",
            new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)));

    public static final Identifier ICE_ETHER_SIGN_TEXTURE = new Identifier(TutorialMod.MOD_ID, "entity/signs/ice_ether");
    public static final Identifier ICE_ETHER_HANGING_SIGN_TEXTURE = new Identifier(TutorialMod.MOD_ID, "entity/signs/hanging/ice_ether");
    public static final Identifier ICE_ETHER_HANGING_SIGN_GUI = new Identifier(TutorialMod.MOD_ID, "textures/gui/hanging_signs/ice_ether");

    public static final Block ICE_ETHER_SIGN = Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, "ice_ether_sign"),
            new TerraformSignBlock(ICE_ETHER_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_SIGN)));
    public static final Block ICE_ETHER_WALL_SIGN = Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, "ice_ether_wall_sign"),
            new TerraformWallSignBlock(ICE_ETHER_SIGN_TEXTURE, AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN)));
    public static final Block ICE_ETHER_HANGING_SIGN = Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, "ice_ether_hanging_sign"),
            new TerraformHangingSignBlock(ICE_ETHER_HANGING_SIGN_TEXTURE, ICE_ETHER_HANGING_SIGN_GUI, AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN)));
    public static final Block ICE_ETHER_WALL_HANGING_SIGN = Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, "ice_ether_wall_hanging_sign"),
            new TerraformWallHangingSignBlock(ICE_ETHER_HANGING_SIGN_TEXTURE, ICE_ETHER_HANGING_SIGN_GUI, AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN)));

    public static final Block ICE_ETHER_TREE_SAPLING = register("ice_ether_tree_sapling",
            new SaplingBlock(new IceEtherTreeGenerator(), AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)));

    public static Block register(String id, Block block) {
        registerBlockItems(id, block);
        return Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, id), block);
    }

    public static void registerBlockItems(String id, Block block) {
        Registry.register(Registries.ITEM, new Identifier(TutorialMod.MOD_ID, id),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {

    }
}
