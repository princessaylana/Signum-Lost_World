/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * firestaff test item wip
 * */
package za.lana.signum.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.sound.Sound;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.projectile.ToxicBallEntity;
import za.lana.signum.mixin.ModEntityDataSaverMixin;

import java.util.List;

public class FireStaffItem extends Item {
    public FireStaffItem(Settings settings) {
        super(new Settings().maxCount(1).maxDamage(201));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.setCurrentHand(hand);
        return TypedActionResult.consume(player.getStackInHand(hand));
        //need to add a custom sound
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World level, LivingEntity shooter, int ticksRemaining) {
        if (shooter instanceof PlayerEntity player) {
            if (stack.getDamage() >= stack.getMaxDamage() - 1)
                return;
            // Add a cooldown so you can't fire rapidly
            player.getItemCooldownManager().set(this, 30);


            if (!level.isClient) {
                ToxicBallEntity arrow = new ToxicBallEntity(level, player);
                //FireballEntity arrow = new FireballEntity(level, player);
                arrow.age = 240;

                arrow.setVelocity(player, player.getPitch(), player.getYaw(), 0, 3, 1);
                arrow.setDamage();
                arrow.hasNoGravity();

                stack.damage(1, shooter, p -> p.sendToolBreakStatus(shooter.getActiveHand()));
                level.spawnEntity(arrow);

            }
        }
    }
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.literal("Very Powerful Staff").formatted(Formatting.GREEN));
        }else {
            tooltip.add(Text.literal("Press Shift for Info").formatted(Formatting.GOLD));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}