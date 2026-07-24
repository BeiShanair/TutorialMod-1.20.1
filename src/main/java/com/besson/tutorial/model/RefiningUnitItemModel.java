package com.besson.tutorial.model;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.item.custom.RefiningUnitItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RefiningUnitItemModel extends GeoModel<RefiningUnitItem> {
    @Override
    public Identifier getModelResource(RefiningUnitItem animatable) {
        return new Identifier(TutorialMod.MOD_ID, "geo/refining_unit.geo.json");
    }

    @Override
    public Identifier getTextureResource(RefiningUnitItem animatable) {
        return new Identifier(TutorialMod.MOD_ID, "textures/block/refining_unit.png");
    }

    @Override
    public Identifier getAnimationResource(RefiningUnitItem animatable) {
        return null;
    }
}
