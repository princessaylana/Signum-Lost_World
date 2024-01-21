package za.lana.signum.networking.packet;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.entity.transport.AirBalloonEntity;

public class AirballoonVec3SyncS2CPacket {
    public static final Identifier AIRBALLOON_POS_SYNC_PACKET = new Identifier(Signum.MOD_ID, "airballoon_pos_sync_packet");

    public static void send(ServerPlayerEntity player, AirBalloonEntity airBalloon) {
        if (airBalloon.hasPassenger(player)) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeVector3f(airBalloon.getPos().toVector3f());
            buf.writeInt(airBalloon.getId());
            ServerPlayNetworking.send(player, AIRBALLOON_POS_SYNC_PACKET, buf);
        }
    }
}
