package za.lana.signum.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import za.lana.signum.Signum;
import za.lana.signum.entity.transport.AirBalloonEntity;


public class ABKeyInputC2SPacket {
    public static final Identifier ABKEY_INPUT_PACKET = new Identifier(Signum.MOD_ID, "abkey_input");
    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(ABKEY_INPUT_PACKET, (server, player, handler, buffer, sender) -> {
            boolean isFlyUpPressed = buffer.readBoolean();
            boolean isFlyDownPressed = buffer.readBoolean();

            LivingEntity entity = (LivingEntity) player.getServerWorld().getEntityById(buffer.readInt());
            if (entity instanceof AirBalloonEntity airBalloon) {
                airBalloon.isFlyUpPressed = isFlyUpPressed ;
                airBalloon.isFlyDownPressed = isFlyDownPressed;
            }
        });
    }
}

