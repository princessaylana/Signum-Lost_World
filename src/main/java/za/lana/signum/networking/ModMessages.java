/**
 * SIGNUM
 * MIT License
 * Registers the networking packets
 * Lana
 * */
package za.lana.signum.networking;

import net.minecraft.util.Identifier;
import za.lana.signum.Signum;

public class ModMessages {
    public static final Identifier ITEM_SYNC = new Identifier(Signum.MOD_ID, "item_sync");


    public static void registerS2CPackets() {
        //ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }
}
