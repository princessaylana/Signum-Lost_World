package za.lana.signum.compat;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Arrow;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.block.ModBlocks;

import java.util.LinkedList;
import java.util.List;

public class SkyForgeCategory implements DisplayCategory<BasicDisplay> {

     public static final Identifier TEXTURE =
             new Identifier(Signum.MOD_ID, "textures/gui/skyforge_gui.png");

     public static final CategoryIdentifier<SkyForgeDisplay> SKYFORGE =
             CategoryIdentifier.of(Signum.MOD_ID, "skyforge");


    @Override
    public CategoryIdentifier<? extends BasicDisplay> getCategoryIdentifier() {
        return SKYFORGE;
    }

    @Override
    public Text getTitle() {
        return Text.literal("SkyForge");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.SKYFORGE.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(BasicDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 88, bounds.getCenterY() -41);
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createTexturedWidget(TEXTURE, new Rectangle(startPoint.x, startPoint.y, 175, 83)));

        widgets.add(Widgets.createArrow(new Point(startPoint.x + 79, startPoint.y + 34)));
        widgets.add(Widgets.createBurningFire(new Point(startPoint.x + 56, startPoint.y + 36)));
        // below should be the fuel slot
        //widgets.add(Widgets.createSlot(new Point(startPoint.x + 56, startPoint.y + 53)).entries(display.getInputEntries().get(0)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 47, startPoint.y + 17))
                .entries(display.getInputEntries().get(0)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 65, startPoint.y + 17))
                .entries(display.getInputEntries().get(1)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 119, startPoint.y + 35))
                .markOutput().entries(display.getOutputEntries().get(0)));
        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 83;
    }
}
