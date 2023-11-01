package za.lana.signum.networking.packet;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.entity.transport.AirBalloonEntity;


public class ABKeyInputSyncS2CPacket {
    public static final Identifier ABKEY_INPUT_SYNC_PACKET = new Identifier(Signum.MOD_ID, "abkey_input_sync_packet");

    public static void send(ServerPlayerEntity player, AirBalloonEntity airBalloon) {
        if (airBalloon.hasPassengers()) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeBoolean(airBalloon.isFlyUpKeyPressed());
            buf.writeBoolean(airBalloon.isFlyDownKeyPressed());

            buf.writeInt(airBalloon.getId());
            ServerPlayNetworking.send(player, ABKEY_INPUT_SYNC_PACKET, buf);
        }
    }
}
