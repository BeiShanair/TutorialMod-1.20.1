package com.besson.tutorial.model;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.blockentity.custom.RefiningUnitBlockEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RefiningUnitModel extends GeoModel<RefiningUnitBlockEntity> {
    @Override
    public Identifier getModelResource(RefiningUnitBlockEntity animatable) {
        return new Identifier(TutorialMod.MOD_ID, "geo/refining_unit.geo.json");
    }

    @Override
    public Identifier getTextureResource(RefiningUnitBlockEntity animatable) {
        return new Identifier(TutorialMod.MOD_ID, "textures/block/refining_unit.png");
    }

    @Override
    public Identifier getAnimationResource(RefiningUnitBlockEntity animatable) {
        return null;
    }
}
