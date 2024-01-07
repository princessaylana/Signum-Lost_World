package za.lana.signum.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import za.lana.signum.block.entity.ExampleBlockEntity;

// SERVER TO CLIENT
public class NBTSyncS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        BlockPos position = buf.readBlockPos();
        assert client.world != null;
        if(client.world.getBlockEntity(position) instanceof ExampleBlockEntity blockEntity) {
            System.out.println("Destination Packet Saved");
        }
    }
}
