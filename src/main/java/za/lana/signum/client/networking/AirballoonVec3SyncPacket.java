package za.lana.signum.client.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.math.Vec3d;
import za.lana.signum.entity.transport.AirBalloonEntity;
import za.lana.signum.networking.packet.AirballoonVec3SyncS2CPacket;

public class AirballoonVec3SyncPacket {
    public static void recieve() {
        ClientPlayNetworking.registerGlobalReceiver(AirballoonVec3SyncS2CPacket.AIRBALLOON_POS_SYNC_PACKET, (client, handler, buffer, sender) -> {
            Vec3d pos = new Vec3d(buffer.readVector3f());
            assert client.world != null;
            AirBalloonEntity airBalloon = (AirBalloonEntity) client.world.getEntityById(buffer.readInt());
            client.execute(() ->{
                if (airBalloon != null && airBalloon.getControllingPassenger() != client.player) airBalloon.setPosition(pos);
            });
        });
    }
}
