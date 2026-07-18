package com.besson.tutorial.compat.category;

import com.besson.tutorial.TutorialMod;
import com.besson.tutorial.block.ModBlocks;
import com.besson.tutorial.compat.display.OreRigDisplay;
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

public class OreRigCategory implements DisplayCategory<OreRigDisplay> {
    public static final CategoryIdentifier<OreRigDisplay> ORE_RIG = 
            CategoryIdentifier.of(TutorialMod.MOD_ID, "ore_rig");
    
    @Override
    public CategoryIdentifier<? extends OreRigDisplay> getCategoryIdentifier() {
        return ORE_RIG;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("blockEntity.porable_originium_rig");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.PORTABLE_ORIGINIUM_RIG.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(OreRigDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 8);
        List<Widget> widgets = new ArrayList<>();
        
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 25, startPoint.y)));
        
        if (!display.getInputEntries().isEmpty()) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y))
                    .entries(display.getInputEntries().get(0))
                    .markInput());
        }
        if (!display.getOutputEntries().isEmpty()) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 55, startPoint.y))
                    .entries(display.getOutputEntries().get(0))
                    .markOutput());
        }
        
        return widgets;
    }
}
