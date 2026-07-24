package com.besson.tutorial.renderer;

import com.besson.tutorial.blockentity.custom.RefiningUnitBlockEntity;
import com.besson.tutorial.model.RefiningUnitModel;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class RefiningUnitRenderer extends GeoBlockRenderer<RefiningUnitBlockEntity> {
    public RefiningUnitRenderer(BlockEntityRendererFactory.Context context) {
        super(new RefiningUnitModel());
    }
}
