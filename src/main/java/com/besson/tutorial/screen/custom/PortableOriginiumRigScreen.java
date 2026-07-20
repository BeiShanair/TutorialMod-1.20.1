package com.besson.tutorial.screen.custom;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.blockentity.custom.PortableOriginiumRigBlockEntity;
import com.besson.tutorial.network.ModNetworking;
import com.besson.tutorial.screen.SwitchButton;
import com.mojang.blaze3d.systems.RenderSystem;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PortableOriginiumRigScreen extends HandledScreen<PortableOriginiumRigScreenHandler> {
    public static final Identifier TEXTURE = new Identifier(TutorialMod.MOD_ID, "textures/gui/portable_originium_rig.png");
    public final PortableOriginiumRigBlockEntity entity;
    public PortableOriginiumRigScreen(PortableOriginiumRigScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.entity = handler.entity;
    }

    @Override
    protected void init() {
        super.init();
        this.addDrawableChild(new SwitchButton(x + 150, y + 30, textRenderer, handler::isEnable,
                button -> {
                    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                    buf.writeBlockPos(entity.getPos());
                    boolean newEnableState = !handler.isEnable();
                    buf.writeBoolean(newEnableState);
                    ClientPlayNetworking.send(ModNetworking.SWITCH_PACKET_ID, buf);
                }));
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        
        renderProgressArrow(context, x, y);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 68, y + 41, 176, 0, handler.getScaledProgress(), 8);
        }
    }
}
