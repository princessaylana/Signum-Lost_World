/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * projectile test item
 * */
package za.lana.signum.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.projectile.SpiderSpitEntity;

public class E0RodItem extends Item {
    public E0RodItem(Settings settings) {
        super(new Settings().maxCount(1).maxDamage(201));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
        // get position
        BlockPos frontOfPlayer = user.getBlockPos().offset(user.getHorizontalFacing(), 10);
        //entity to spawn
        SpiderSpitEntity lightningBolt = new SpiderSpitEntity(ModEntities.SPIDERSPIT_PROJECTILE, world);
        lightningBolt.setPosition(frontOfPlayer.toCenterPos());


        world.spawnEntity(lightningBolt);
        user.getItemCooldownManager().set(this, 30);
        return TypedActionResult.success(user.getStackInHand(hand));
    }

}
