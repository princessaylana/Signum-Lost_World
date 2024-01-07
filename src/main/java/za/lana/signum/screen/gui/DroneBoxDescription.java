/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.screen.gui;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.math.NumberUtils;
import za.lana.signum.Signum;
import za.lana.signum.networking.ModMessages;


public class DroneBoxDescription extends SyncedGuiDescription {

	protected static PlayerEntity player;
	private static final int INVENTORY_SIZE = 8;
	public static final WTextField enterX = new WTextField();
	public static final WTextField enterY = new WTextField();
	public static final WTextField enterZ = new WTextField();
	private static final Identifier BUTTON_SEND_DATA = new Identifier(Signum.MOD_ID, "example_button_click");

    public DroneBoxDescription(ScreenHandlerType<DroneBoxDescription> droneboxDescription, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(GuiScreens.DRONEBOX_GUI, syncId, playerInventory, getBlockInventory(context, INVENTORY_SIZE), null);
		assert player != null;
		this.world = playerInventory.player.getWorld();
		player = playerInventory.player;
		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(200, 225);
		root.setInsets(Insets.ROOT_PANEL);

		/**
		// NETWORK RECIEVER
		ScreenNetworking.of(this, NetworkSide.SERVER).receive(BUTTON_SEND_DATA, buf -> {
			//System.out.println(buf.readInt());
			System.out.println(buf.readBlockPos());
			BlockPos recieveBlockPos = buf.readBlockPos();
			System.out.println("Destination Packet Recieved");
		});
		 **/

		WItemSlot slot = WItemSlot.of(blockInventory, 0, 8, 1);
		root.add(slot, 0, 1);
		// BUTTON
		WButton button = new WButton(Text.translatable("example_gui.signum.examplebutton"));
		button.setOnClick(() -> {

			// SPAWN ENTITY
			ClientPlayNetworking.send(ModMessages.NBT_CS_SYNCPACKET, PacketByteBufs.create());
			// SEND A PACKET TO SERVER WITH BLOCKPOSITION

			//System.out.println("Destination Packet Sent");
		});
		root.add(button, 4, 3, 3, 1);
		// TEXT INPUT
		enterX.setSuggestion(Text.literal("X"));
		root.add(enterX, 0, 2, 3, 1);
		enterY.setSuggestion(Text.literal("Y"));
		root.add(enterY, 0, 3, 3, 1);
		enterZ.setSuggestion(Text.literal("Z"));
		root.add(enterZ, 0, 4, 3, 1);
		// PLAYER INVENTORY
		root.add(this.createPlayerInventoryPanel(), 0, 6);
		root.validate(this);
	}

	// BELOW METHODS NEEDED TO CHECK AND CONVERT SCREEN INPUT TO INTEGERS)
	// CHECK
	private static String getXpos(WTextField enterX) {
		String x = DroneBoxDescription.enterX.getText();
		if (x.matches("[0-9.]+")){
			NumberUtils.toInt(x);{
				System.out.println(x);
				return x;
			}
		}else if(!x.matches("[0-9.]+") && player.getWorld().isClient){
			player.sendMessage(Text.literal("Please Input Numbers Only"));
		}
		return null;
	}
	private static String getYpos(WTextField enterY) {
		String y = DroneBoxDescription.enterY.getText();
		if (y.matches("[0-9.]+")){
			NumberUtils.toInt(y);{
				System.out.println(y);
				return y;
			}
		}else if(!y.matches("[0-9.]+") && player.getWorld().isClient){
			player.sendMessage(Text.literal("Please Input Numbers Only"));
		}
		return null;
	}
	private static String getZpos(WTextField enterZ) {
		String enterZText = DroneBoxDescription.enterZ.getText();
		if (enterZText.matches("[0-9.]+")){
			NumberUtils.toInt(enterZText);{
				System.out.println(enterZText);
				return enterZText;
			}
		}else if(!enterZText.matches("[0-9.]+") && player.getWorld().isClient){
			player.sendMessage(Text.literal("Please Input Numbers Only"));
		}
		return null;
	}

}
