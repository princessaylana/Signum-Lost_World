/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import za.lana.signum.block.entity.ExampleBlockEntity;
import za.lana.signum.screen.gui.ExampleDescription;

public class ExampleBlockC2SPacket {

    public static void send() {
    }
    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                                      PacketByteBuf buf, PacketSender responseSender) {
        // below only happens on the server side
        if (player != null) {

            BlockPos blockEntityPos = BlockPos.ofFloored(player.getPos());
            BlockEntity blockEntity = player.getWorld().getBlockEntity(blockEntityPos);

            if (blockEntity instanceof ExampleBlockEntity) {
                System.out.println("Destination Packet Recieved");
                writeData(blockEntity);
            }
        }
    }
    public static void writeData(BlockEntity blockEntity){
        NbtCompound nbtData = new NbtCompound();
        // NbtInt.of(
        nbtData.putInt("destination.x", Integer.parseInt(ExampleDescription.getXdestination("enterX")));
        nbtData.putInt("destination.y", Integer.parseInt(ExampleDescription.getYdestination("enterY")));
        nbtData.putInt("destination.z", Integer.parseInt(ExampleDescription.getZdestination("enterZ")));
        // Testing if its recieved
        System.out.println("Destination Packet Saved");
    }
}
