/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.screen.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class ExampleBlockScreen extends CottonInventoryScreen<ExampleDescription> {
    public ExampleBlockScreen(ExampleDescription gui, PlayerInventory player, Text title) {
        super(gui, player, title);
    }
}