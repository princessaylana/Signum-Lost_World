/**
 * SIGNUM
 * this is the first gun weapon
 * it shoots toxicballs
 * MIT License
 * Lana
 * */

package za.lana.signum.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import za.lana.signum.entity.itemprojectile.ToxicBallEntity;

import java.util.List;

public class BPistolItem
        extends Item {
    public BPistolItem(Settings settings) {
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
            player.getItemCooldownManager().set(this, 5);


            if (!level.isClient) {
                ToxicBallEntity arrow = new ToxicBallEntity(level, player);
                arrow.age = 35;

                arrow.setVelocity(player, player.getPitch(), player.getYaw(), 0, 3, 1);
                arrow.setDamage();
                arrow.hasNoGravity();

                stack.damage(1, shooter, p -> p.sendToolBreakStatus(shooter.getActiveHand()));
                level.spawnEntity(arrow);

            }
        }
    }
    // Use vanilla animation to 'pull back' the pistol while charging it
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return false;
    }

    //Tooltip Below
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.literal("weapon that shoots toxic pellets").formatted(Formatting.GREEN));
        }else {
            tooltip.add(Text.literal("Press Shift for Info").formatted(Formatting.GOLD));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

}