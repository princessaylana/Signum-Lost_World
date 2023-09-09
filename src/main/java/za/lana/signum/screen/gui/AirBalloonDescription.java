/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.screen.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.Identifier;

public class AirBalloonDescription extends SyncedGuiDescription {
    private static final int INVENTORY_SIZE = 2;
    private static final int PROPERTY_DELEGATE = 2;
    //protected Inventory blockInventory;

    public AirBalloonDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(GuiScreens.AB_GUI, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context, PROPERTY_DELEGATE));
        this.world = playerInventory.player.getWorld();
        //this.blockInventory = new SimpleInventory(1).readNbtList();
        //this.blockInventory =;
        this.propertyDelegate = new ArrayPropertyDelegate(2);

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(170, 120);
        root.setInsets(Insets.ROOT_PANEL);

        WSprite icon = new WSprite(new Identifier("signum:textures/item/element_zero_crystal.png"));
        root.add(icon, 0, 2, 1, 1);

        //
        WItemSlot fuelSlot = WItemSlot.of(blockInventory, 0);
        //fuelSlot.setFilter(itemStack -> itemStack.isOf(Items.COAL));
        root.add(fuelSlot, 4, 1);

        WItemSlot inventorySlot = WItemSlot.of(blockInventory, 1);
        root.add(inventorySlot, 6, 1);

        root.add(this.createPlayerInventoryPanel(), 0, 3);
        root.validate(this);
    }
}
