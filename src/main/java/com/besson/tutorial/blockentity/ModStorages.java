package com.besson.tutorial.blockentity;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;

public class ModStorages {
    public static void register() {
        ItemStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> 
                blockEntity.getStorage(), ModBlockEntities.PORTABLE_ORIGINIUM_RIG);
    }
}
