/**
 * SIGNUM
 * Mod item Group and Tab
 * MIT License
 * Lana
 * */
package za.lana.signum.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import za.lana.signum.networking.ModMessages;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_SIGNUM = "key.category.signum.modkeys";
    public static final String KEY_OPENINVENTORY_SIGNUM = "key.signum.openinventory";
    public static final String KEY_FLY_UP_SIGNUM = "key.signum.flyup";
    public static final String KEY_FLY_DOWN_SIGNUM = "key.signum.flydown";


    public static KeyBinding inventKey;
    public static KeyBinding flyUpKey;
    public static KeyBinding flyDownkey;


    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (inventKey.wasPressed()) {
                //client.player.sendMessage(Text.literal("InventoryKey was pressed!"), false);
                ClientPlayNetworking.send(ModMessages.AIRBALLOON_INVENTORY, PacketByteBufs.create());


            }
            while (flyUpKey.wasPressed()) {
                //client.player.sendMessage(Text.literal("Fly Up was pressed!"), false);
            }
            while (flyDownkey.wasPressed()) {
                //client.player.sendMessage(Text.literal("Fly Down was pressed!"), false);
            }

        });
    }


    public static void register(){
        inventKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPENINVENTORY_SIGNUM,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                KEY_CATEGORY_SIGNUM));

        flyUpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_FLY_UP_SIGNUM,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                KEY_CATEGORY_SIGNUM));

        flyDownkey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_FLY_DOWN_SIGNUM,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                KEY_CATEGORY_SIGNUM));


        registerKeyInputs();
    }
}

// should Open the screen?
/**
 * client.player.isRiding() && vehicle instanceof AirBalloonEntity
 *
 if (client.player.isRiding()){
 ClientPlayNetworking.send(ModMessages.AIRBALLOON_INVENTORY, PacketByteBufs.create());
 }
 **/