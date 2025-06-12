package com.besson.tutorial;

import com.besson.tutorial.block.ModBlockEntities;
import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.block.ModFluids;
import com.besson.tutorial.entity.ModEntities;
import com.besson.tutorial.item.ModItemGroups;
import com.besson.tutorial.item.ModItems;
import com.besson.tutorial.sound.ModSoundEvents;
import com.besson.tutorial.villager.ModTrades;
import com.besson.tutorial.villager.ModVillagers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialMod implements ModInitializer {
	public static final String MOD_ID = "tutorial-mod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModItems.registerItems();
		ModItemGroups.registerGroups();
		ModBlocks.registerModBlocks();

		ModTrades.registerTrades();
		ModVillagers.registerModVillagers();

		ModSoundEvents.registerSounds();

		ModEntities.registerEntities();
		ModBlockEntities.registerBlockEntities();

		ModFluids.registerModFluids();

//		FuelRegistry.INSTANCE.add(ModItems.ANTHRACITE, 1600);
		LOGGER.info("Hello Fabric world!");
	}
}