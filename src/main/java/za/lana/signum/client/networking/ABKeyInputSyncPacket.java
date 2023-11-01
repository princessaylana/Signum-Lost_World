package za.lana.signum.client.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import za.lana.signum.entity.transport.AirBalloonEntity;
import za.lana.signum.networking.packet.ABKeyInputSyncS2CPacket;

public class ABKeyInputSyncPacket {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(ABKeyInputSyncS2CPacket.ABKEY_INPUT_SYNC_PACKET, (client, handler, buffer, sender) -> {
            boolean isFlyUpKeyPressed = buffer.readBoolean();
            boolean isFlyDownKeyPressed = buffer.readBoolean();
           AirBalloonEntity airBalloon = client.world != null ? (AirBalloonEntity) client.world.getEntityById(buffer.readInt()) : null;
            client.execute(() ->{
                if (airBalloon != null && airBalloon.getControllingPassenger() != client.player) {
                    //airBalloon.isFlyUpKeyPressed() = isFlyUpKeyPressed;
                    //airBalloon.isFlyDownKeyPressed() = isFlyDownKeyPressed;
                    airBalloon.updateInputs(isFlyUpKeyPressed, isFlyDownKeyPressed);
                }
            });
        });
    }
}
