package com.besson.tutorial;

import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.block.ModFluids;
import com.besson.tutorial.entity.ModBoats;
import com.besson.tutorial.entity.ModEntities;
import com.besson.tutorial.entity.SeatEntityRenderer;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class TutorialModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_ETHER_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_ETHER_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_ETHER_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STRAWBERRY_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORN_CROP, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.SEAT, SeatEntityRenderer::new);

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.OIL, ModFluids.OIL_FLOWING,
                new SimpleFluidRenderHandler(
                        new Identifier("minecraft:block/water_still"),
                        new Identifier("minecraft:block/water_flow"),
                        0x42413b
                ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), ModFluids.OIL, ModFluids.OIL_FLOWING);

        TerraformBoatClientHelper.registerModelLayers(ModBoats.ICE_ETHER_BOAT, false);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_ETHER_TREE_SAPLING, RenderLayer.getCutout());
    }
}
