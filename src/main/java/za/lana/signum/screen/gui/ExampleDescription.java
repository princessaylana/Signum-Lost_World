/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.screen.gui;
// this would be equivalent to a normal screen handler

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.math.NumberUtils;
import za.lana.signum.networking.ModMessages;


public class ExampleDescription extends SyncedGuiDescription {
	private static final int INVENTORY_SIZE = 8;
	private static String text = "";


    public ExampleDescription(ScreenHandlerType<ExampleDescription> exampleDescription, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(GuiScreens.EXAMPLE_GUI, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), null);
		this.world = playerInventory.player.getWorld();
		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(200, 225);
		root.setInsets(Insets.ROOT_PANEL);
		// HEADER
		WSprite icon = new WSprite(new Identifier("signum:textures/item/iron_coin.png"));
		root.add(icon, 8, 0, 1, 1);
		// INVENTORY OF BLOCKENTITY
		WItemSlot slot = WItemSlot.of(blockInventory, 0, 8, 1);
		root.add(slot, 0, 1);
		// BUTTON
		WButton button = new WButton(Text.translatable("example_gui.signum.examplebutton"));
		button.setOnClick(() -> {
			// This code runs on the client when you click the button.
			// NEED TO SEND A PACKET TO SERVER TO WRITE NBT INFO INTO THE BLOCKENTITY

			ClientPlayNetworking.send(ModMessages.EXAMPLE_NBT, PacketByteBufs.create());

			System.out.println("Destination Packet Sent");
		});
		root.add(button, 5, 2, 3, 1);
		// TEXT INPUT
		//root.add(new WTextField(Text.literal("PlayerName")).setMaxLength(64), 0, 2, 5, 1);
		root.add(new WTextField(Text.literal("enterX")).setMaxLength(64), 0, 3, 5, 1);
		root.add(new WTextField(Text.literal("enterY")).setMaxLength(64), 0, 4, 5, 1);
		root.add(new WTextField(Text.literal("enterZ")).setMaxLength(64), 0, 5, 5, 1);
		// PLAYER INVENTORY
		root.add(this.createPlayerInventoryPanel(), 0, 6);
		root.validate(this);
	}

	  public static String getXpos(String enterX) {
		if (enterX != null && enterX.matches("[0-9.]+")){
			NumberUtils.toInt(enterX);{
				return enterX;
			}
		}
		return null;
	}
	public static String getYpos(String enterY) {
		if (enterY != null && enterY.matches("[0-9.]+")){
			NumberUtils.toInt(enterY);{
				return enterY;
			}
		}
		return null;
	}
	public static String getZpos(String enterZ) {
		if (enterZ != null && enterZ.matches("[0-9.]+")){
			NumberUtils.toInt(enterZ);{
				return enterZ;
			}
		}
		return null;
	}
	//
}
