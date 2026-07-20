package com.besson.tutorial.screen;

import com.besson.tutorial.TutorialMod;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class SwitchButton extends PressableWidget {
    public static final Identifier SWITCH_ENABLE = new Identifier(TutorialMod.MOD_ID, "textures/gui/button/switch_enable.png");
    public static final Identifier SWITCH_DISABLE = new Identifier(TutorialMod.MOD_ID, "textures/gui/button/switch_disable.png");
    
    private final BooleanSupplier booleanSupplier;
    private final Consumer<Boolean> onToggle;
    private final TextRenderer textRenderer;
    public SwitchButton(int i, int j, TextRenderer textRenderer, BooleanSupplier booleanSupplier, Consumer<Boolean> onToggle) {
        super(i, j, 16, 16, Text.empty());
        this.textRenderer = textRenderer;
        this.booleanSupplier = booleanSupplier;
        this.onToggle = onToggle;
    }

    @Override
    public void onPress() {
        boolean newState = !this.booleanSupplier.getAsBoolean();
        this.onToggle.accept(newState);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        Identifier texture = this.booleanSupplier.getAsBoolean() ? SWITCH_ENABLE : SWITCH_DISABLE;
        context.drawTexture(texture, getX(), getY(), 0, 0, width, height, 16, 16);
        
        if (isHovered()) {
            context.fill(getX(), getY(), getX() + this.width, getY() + this.height, 0x40FFFFFF);
            
            context.drawTooltip(textRenderer, 
                    Text.translatable("switch_button.tooltip",
                            booleanSupplier.getAsBoolean()
                                ? Text.translatable("switch_button.enable")
                                : Text.translatable("switch_button.disable")),
                    mouseX, mouseY);
        }
    }
}
