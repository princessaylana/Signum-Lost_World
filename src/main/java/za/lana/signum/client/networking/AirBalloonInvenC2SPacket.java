/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.client.networking;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import za.lana.signum.entity.transport.AirBalloonEntity;

public class AirBalloonInvenC2SPacket {

    public static void send() {

    }
    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                                      PacketByteBuf buf, PacketSender responseSender) {

        // below only happens on the server side
        if (player != null) {
        Entity vehicle = player.getVehicle();
            if (vehicle instanceof AirBalloonEntity airBalloon) {
                World world = player.getWorld();
                BlockPos pos = player.getBlockPos();
                NamedScreenHandlerFactory screenHandlerFactory = ((AirBalloonEntity)player.getVehicle());
                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }else{
                    player.closeHandledScreen();
                }
            }
        }
    }
}
