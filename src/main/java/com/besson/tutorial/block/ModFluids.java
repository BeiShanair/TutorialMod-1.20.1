package com.besson.tutorial.block;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.block.custom.OilFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFluids {
    public static final FlowableFluid OIL = register("oil", new OilFluid.Still());
    public static final FlowableFluid OIL_FLOWING = register("oil_flowing", new OilFluid.Flowing());

    private static <T extends Fluid> T register(String id, T value) {
        return Registry.register(Registries.FLUID,
                new Identifier(TutorialMod.MOD_ID, id), value);
    }

    static {
        for (Fluid fluid : Registries.FLUID) {
            for (FluidState fluidState : fluid.getStateManager().getStates()) {
                Fluid.STATE_IDS.add(fluidState);
            }
        }
    }
    public static void registerModFluids(){

    }
}
