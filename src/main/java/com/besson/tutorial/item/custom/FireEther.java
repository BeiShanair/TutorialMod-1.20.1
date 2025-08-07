package com.besson.tutorial.item.custom;

import net.minecraft.item.Item;

public class FireEther extends Item implements ModDurabilityItem {
    public FireEther(Settings settings) {
        super(settings.maxDamage(127));
    }
}
