/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.GhostEntity;
import za.lana.signum.entity.hostile.SumSkeletonEntity;
import za.lana.signum.sound.ModSounds;

import java.beans.XMLDecoder;
import java.util.List;

public class DeathStaff
        extends Item {
    private final ToolMaterial material;
    private float attackDamage = 2.0f;
    public DeathStaff(ToolMaterial material, Settings settings) {
        super(settings.maxDamageIfAbsent(material.getDurability()));
        this.material = material;
        this.attackDamage = attackDamage + material.getAttackDamage();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemstack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.GHOST_AMBIENT, SoundCategory.NEUTRAL,1.5F, 1F);
        user.getItemCooldownManager().set(this, 40);
        if (!world.isClient()) {
            BlockPos BlockPos = user.getBlockPos();
            spawnMonster(world, BlockPos, user);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        // BREAK TOOL
        if (!user.getAbilities().creativeMode) {
            itemstack.damage(1, user, p -> p.sendToolBreakStatus(hand));
        }
        return TypedActionResult.success(itemstack, world.isClient());
    }

    // Spawns and tames the entity
    private void spawnMonster(World world, BlockPos pos, PlayerEntity user) {
        SumSkeletonEntity sskeleton = ModEntities.SSKELETON_ENTITY.create(world);
        if (sskeleton != null) {
            sskeleton.refreshPositionAndAngles((double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5, 0.0f, 0.0f);
            world.spawnEntity(sskeleton);
            sskeleton.setOwner(user);
            //sskeleton.navigation.stop();
            //sskeleton.setTarget(null);
            sskeleton.playSpawnEffects();
        }
    }
    
    // REPAIR ITEM WITH MOD MATERIAL
    public ToolMaterial getMaterial() {
        return this.material;
    }
    @Override
    public int getEnchantability() {
        return this.material.getEnchantability();
    }
    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.signum.death_staff.info"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}

