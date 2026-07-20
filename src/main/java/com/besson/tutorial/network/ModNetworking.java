package com.besson.tutorial.network;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.blockentity.custom.PortableOriginiumRigBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModNetworking {
    public static final Identifier SWITCH_PACKET_ID = new Identifier(TutorialMod.MOD_ID, "switch_packet");
    
    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(SWITCH_PACKET_ID, ((server, player, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            boolean enable = buf.readBoolean();
            server.execute(() -> {
                BlockEntity be = player.getWorld().getBlockEntity(pos);
                if (be instanceof PortableOriginiumRigBlockEntity rig) {
                    rig.setEnable(enable);
                }
            });
        }));
    }
}
