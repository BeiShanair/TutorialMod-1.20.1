package com.besson.tutorial.recipe;

import net.minecraft.item.ItemConvertible;

public record ItemCountInput(ItemConvertible itemConvertible, int count) {
}
