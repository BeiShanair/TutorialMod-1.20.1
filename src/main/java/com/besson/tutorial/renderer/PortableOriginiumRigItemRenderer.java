package com.besson.tutorial.renderer;

import com.besson.tutorial.item.custom.PortableOriginiumRigItem;
import com.besson.tutorial.model.PortableOriginiumRigItemModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PortableOriginiumRigItemRenderer extends GeoItemRenderer<PortableOriginiumRigItem> {
    public PortableOriginiumRigItemRenderer() {
        super(new PortableOriginiumRigItemModel());
    }
}
