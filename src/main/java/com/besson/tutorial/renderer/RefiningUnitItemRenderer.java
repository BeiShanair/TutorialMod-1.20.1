package com.besson.tutorial.renderer;

import com.besson.tutorial.item.custom.RefiningUnitItem;
import com.besson.tutorial.model.RefiningUnitItemModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class RefiningUnitItemRenderer extends GeoItemRenderer<RefiningUnitItem> {
    public RefiningUnitItemRenderer() {
        super(new RefiningUnitItemModel());
    }
}
