package com.besson.tutorial.compat.category;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.compat.display.RefiningUnitDisplay;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class RefiningUnitCategory implements DisplayCategory<RefiningUnitDisplay> {
    public static final CategoryIdentifier<RefiningUnitDisplay> REFINING_UNIT = 
            CategoryIdentifier.of(TutorialMod.MOD_ID, "refining_unit");
    
    @Override
    public CategoryIdentifier<? extends RefiningUnitDisplay> getCategoryIdentifier() {
        return REFINING_UNIT;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("blockEntity.refining_unit");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.REFINING_UNIT.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(RefiningUnitDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 60, bounds.getCenterY() - 8);
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 50, startPoint.y)));

        if (!display.getInputEntries().isEmpty()) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y))
                    .entries(display.getInputEntries().get(0))
                    .markInput());
        }
        if (!display.getOutputEntries().isEmpty()) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 100, startPoint.y))
                    .entries(display.getOutputEntries().get(0))
                    .markOutput());
        }

        return widgets;
    }
}
