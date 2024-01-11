package za.lana.signum.client.networking;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import za.lana.signum.entity.transport.AirBalloonEntity;

@Environment(EnvType.CLIENT)
public class ABKeyInputPacket {
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client ->{
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null)
                if (player.getVehicle() instanceof AirBalloonEntity airBalloon) {
                    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                    buf.writeBoolean(airBalloon.isFlyUpPressed);
                    buf.writeBoolean(airBalloon.isFlyDownPressed);
                    buf.writeInt(airBalloon.getId());
                    ClientPlayNetworking.send(ABKeyInputC2SPacket.ABKEY_INPUT_PACKET, buf);
                    //client.player.sendMessage(Text.literal("Fly Key was pressed!"), false);
                }
        });
    }
}
