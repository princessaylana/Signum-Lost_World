/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import za.lana.signum.block.entity.ExampleBlockEntity;
import za.lana.signum.block.entity.ModBlockEntities;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.transport.CargoDroneEntity;

import java.util.Objects;

public class NBTSyncC2SPacket {
    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // ONLY SERVER SIDE:
        BlockPos pos = player.getBlockPos();
        ServerWorld level = player.getServerWorld();
        BlockEntity blockEntity = level.getBlockEntity(pos);
        BlockState blockEntityState = level.getBlockState(pos);

        // tells BlockEntity to write NBT
        ModEntities.CARGODRONE.spawn(level, pos, SpawnReason.TRIGGERED);
        //ModEntities.CARGODRONE.spawn(level, pos, SpawnReason.TRIGGERED).setSyncedWithDroneBox(true);
        //Objects.requireNonNull(ModEntities.CARGODRONE.spawn(level, pos, SpawnReason.TRIGGERED)).setSyncedWithDroneBox(true);


        /**
        CargoDroneEntity entity = (CargoDroneEntity) level.;
        level.getEntitiesByClass(CargoDroneEntity.class, entity.getBoundingBox().expand(5), e->true).forEach(e->e
                .setSyncedWithDroneBox(true));



        /**
         if (synced != level.isReceivingRedstonePower(pos)) {
         if (synced) {
         level.scheduleBlockTick(pos, , 4);
         } else {
         level.setBlockState(pos, state.cycle(LIT), 2);
         }
         }

         **/
        System.out.println("Destination Packet Recieved");
        //
        // PING BACK TO CLIENT ?
    }
}