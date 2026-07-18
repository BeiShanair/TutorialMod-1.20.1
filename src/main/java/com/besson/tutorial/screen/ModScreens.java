package com.besson.tutorial.screen;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.screen.custom.PortableOriginiumRigScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreens {
    public static final ScreenHandlerType<PortableOriginiumRigScreenHandler> PORTABLE_ORIGINIUM_RIG_SCREEN =
            Registry.register(Registries.SCREEN_HANDLER, new Identifier(TutorialMod.MOD_ID, "portable_originium_rig"),
                    new ExtendedScreenHandlerType<>(PortableOriginiumRigScreenHandler::new));
    
    public static void register() {
        
    }
}
