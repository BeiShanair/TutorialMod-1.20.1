package com.besson.tutorial.world.biome;

import com.besson.tutorial.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModMaterialRules {
    public static final MaterialRules.MaterialRule DIRT = makeStateRules(Blocks.DIRT);
    public static final MaterialRules.MaterialRule GRASS_BLOCK = makeStateRules(Blocks.GRASS_BLOCK);
    public static final MaterialRules.MaterialRule ICE_ETHER_BLOCK = makeStateRules(ModBlocks.ICE_ETHER_BLOCK);
    public static final MaterialRules.MaterialRule DIAMOND_BLOCK = makeStateRules(Blocks.DIAMOND_BLOCK);
    
    public static MaterialRules.MaterialRule makeRules() {
        MaterialRules.MaterialCondition iSAtOrAboveWaterLevel = MaterialRules.water(-1, 0);
        
        MaterialRules.MaterialRule grass = MaterialRules.condition(
                MaterialRules.surface(),
                MaterialRules.condition(iSAtOrAboveWaterLevel, GRASS_BLOCK));
        
        MaterialRules.MaterialRule diamond = MaterialRules.condition(
                MaterialRules.biome(ModBiomes.DIAMOND_BIOME),
                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, DIAMOND_BLOCK));

        MaterialRules.MaterialRule iceEther = MaterialRules.condition(
                MaterialRules.STONE_DEPTH_CEILING, ICE_ETHER_BLOCK);
        
        MaterialRules.MaterialRule dirt = MaterialRules.condition(
                MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, DIRT);
        
        return MaterialRules.sequence(grass, diamond, dirt, iceEther);
    }
    
    private static MaterialRules.MaterialRule makeStateRules(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
