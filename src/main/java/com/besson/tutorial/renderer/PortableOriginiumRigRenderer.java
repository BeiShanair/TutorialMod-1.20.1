package com.besson.tutorial.renderer;

import com.besson.tutorial.blockentity.custom.PortableOriginiumRigBlockEntity;
import com.besson.tutorial.model.PortableOriginiumRigModel;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class PortableOriginiumRigRenderer extends GeoBlockRenderer<PortableOriginiumRigBlockEntity> {
    public PortableOriginiumRigRenderer(BlockEntityRendererFactory.Context context) {
        super(new PortableOriginiumRigModel());
    }
}
