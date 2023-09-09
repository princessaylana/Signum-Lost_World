/**
 * SIGNUM
 * MIT License
 * Registers the networking packets
 * Lana
 * */
package za.lana.signum.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.networking.packet.AirBalloonDownC2SPacket;
import za.lana.signum.networking.packet.AirBalloonInvenC2SPacket;
import za.lana.signum.networking.packet.AirBalloonUpC2SPacket;
import za.lana.signum.networking.packet.SignumTestC2SPacket;

import java.time.format.TextStyle;

public class ModMessages {
    public static final Identifier AIRBALLOON_UP = new Identifier(Signum.MOD_ID, "airballoon_up");
    public static final Identifier AIRBALLOON_DOWN = new Identifier(Signum.MOD_ID, "airballoon_down");
    public static final Identifier AIRBALLOON_INVENTORY = new Identifier(Signum.MOD_ID, "airballoon_inventory");


    public static final Identifier SIGNUM_TEST = new Identifier(Signum.MOD_ID, "signum_test");


    // client to server
    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(AIRBALLOON_UP, AirBalloonUpC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(AIRBALLOON_DOWN, AirBalloonDownC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(AIRBALLOON_INVENTORY, AirBalloonInvenC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(SIGNUM_TEST, SignumTestC2SPacket::recieve);

    }
    // server to client
    public static void registerS2CPackets() {
        //ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }

}
