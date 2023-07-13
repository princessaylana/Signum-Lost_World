/**
 * SIGNUM
 * MIT License
 * Lana
 * */

package za.lana.signum.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import za.lana.signum.entity.projectile.LaserBoltEntity;
import za.lana.signum.entity.projectile.ToxicBallEntity;

public class LaserBoltItem
        extends Item {
    public LaserBoltItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL,
                0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClient) {
            LaserBoltEntity laserBoltEntity = new LaserBoltEntity(world, user);
            laserBoltEntity.setItem(itemStack);
            laserBoltEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 2f, 1.0f);
            world.spawnEntity(laserBoltEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }
}