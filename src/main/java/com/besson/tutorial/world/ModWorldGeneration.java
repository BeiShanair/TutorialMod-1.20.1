package com.besson.tutorial.world;

import com.besson.tutorial.world.flower.ModFlowerGeneration;
import com.besson.tutorial.world.tree.ModTreeGeneration;

public class ModWorldGeneration {
    public static void register(){
        ModTreeGeneration.registerTrees();
        ModFlowerGeneration.registerFlowers();
    }
}
