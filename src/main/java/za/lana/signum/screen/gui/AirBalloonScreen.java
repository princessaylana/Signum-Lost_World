/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.screen.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class AirBalloonScreen extends CottonInventoryScreen<AirBalloonDescription> {
    public AirBalloonScreen(AirBalloonDescription gui, PlayerInventory playerInventory, Text title) {
        super(gui, playerInventory, title);
    }
}
