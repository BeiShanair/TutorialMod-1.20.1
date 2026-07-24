package com.besson.tutorial.blockentity;

import com.besson.tutorial.blockentity.custom.RefiningUnitBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;

public class ModStorages {
    public static void register() {
        ItemStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> 
                blockEntity.getStorage(), ModBlockEntities.PORTABLE_ORIGINIUM_RIG);
        
        ItemStorage.SIDED.registerForBlockEntity((blockEntity, direction) ->
                blockEntity.getStorage(blockEntity.getCachedState(), direction), ModBlockEntities.REFINING_UNIT);
        ItemStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> {
            RefiningUnitBlockEntity parent = blockEntity.getParent();
            if (parent == null) return null;
            return parent.getStorage(parent.getCachedState(), direction);
        }, ModBlockEntities.REFINING_UNIT_SIDE);
    }
}
