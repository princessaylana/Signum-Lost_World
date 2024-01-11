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
import za.lana.signum.client.networking.AirBalloonInvenC2SPacket;
import za.lana.signum.client.networking.SignumTestC2SPacket;
import za.lana.signum.networking.packet.*;


public class ModMessages {
    public static final Identifier AIRBALLOON_INVENTORY = new Identifier(Signum.MOD_ID, "airballoon_inventory");
    public static final Identifier NBT_CS_SYNCPACKET = new Identifier(Signum.MOD_ID, "nbt_cs_syncpacket");
    public static final Identifier NBT_SC_SYNCPACKET = new Identifier(Signum.MOD_ID, "nbt_sc_syncpacket");
    public static final Identifier SIGNUM_TEST = new Identifier(Signum.MOD_ID, "signum_test");

    // client to server
    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(AIRBALLOON_INVENTORY, AirBalloonInvenC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(NBT_CS_SYNCPACKET, NBTSyncC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(SIGNUM_TEST, SignumTestC2SPacket::recieve);

    }
    // server to client
    public static void registerS2CPackets() {
        //ClientPlayNetworking.registerGlobalReceiver(NBT_SC_SYNCPACKET, NBTSyncS2CPacket::recieve);
        //ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }

}
