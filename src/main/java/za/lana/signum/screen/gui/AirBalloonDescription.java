/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.screen.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import za.lana.signum.entity.transport.AirBalloonEntity;
import za.lana.signum.item.ModFuels;
import za.lana.signum.item.ModItems;

public class AirBalloonDescription extends SyncedGuiDescription {
    private static final int INVENTORY_SIZE = 1;
    private static final Ingredient ACCEPTABLE_FUEL = Ingredient.ofItems(
            Items.CHARCOAL,
            Items.COAL,
            ModItems.COKECOAL,
            ModItems.ELEMENTZEROCOAL,
            ModItems.TIBERIUMCOAL);

    public AirBalloonDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(GuiScreens.AB_GUI, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context));
        this.world = playerInventory.player.getWorld();

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(260, 200);
        root.setInsets(Insets.ROOT_PANEL);

        WSprite icon = new WSprite(new Identifier("signum:textures/item/element_zero_crystal.png"));
        root.add(icon, 0, 2, 1, 1);

        //
        WItemSlot fuelSlot = WItemSlot.of(blockInventory, 0);
        fuelSlot.setFilter(itemStack -> itemStack.isOf(ModItems.COKECOAL));
        fuelSlot.setFilter(itemStack -> itemStack.isOf(ModItems.ELEMENTZEROCOAL));
        fuelSlot.setFilter(itemStack -> itemStack.isOf(ModItems.TIBERIUMCOAL));
        fuelSlot.setFilter(itemStack -> itemStack.isOf(Items.COAL));
        root.add(fuelSlot, 4, 1);


        root.add(this.createPlayerInventoryPanel(), 0, 3);
        root.validate(this);
    }
}
