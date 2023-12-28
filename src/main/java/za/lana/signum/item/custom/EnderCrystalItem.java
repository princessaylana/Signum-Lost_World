package za.lana.signum.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import za.lana.signum.world.dimension.LostWorldDimension;

import java.util.Objects;

public class EnderCrystalItem extends Item {
    public EnderCrystalItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient()) {
            RegistryKey<World> registryKey = context.getWorld().getRegistryKey() == LostWorldDimension.LOSTWORLD_LEVEL_KEY ? World.OVERWORLD : LostWorldDimension.LOSTWORLD_LEVEL_KEY;
            ServerWorld serverWorld = (Objects.requireNonNull(context.getWorld().getServer()).getWorld(registryKey));
            if (serverWorld == null) {
                return null;
            }
            PlayerEntity user = context.getPlayer();
            assert user != null;
            user.moveToWorld(serverWorld);
        }
        /**
        context.getStack().damage(1, context.getPlayer(),
                playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));
         **/

        return ActionResult.SUCCESS;
    }
}
