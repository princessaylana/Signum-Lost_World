/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

import java.util.List;

public class HealingStaff
        extends Item {
    private final ToolMaterial material;

    public HealingStaff(ToolMaterial material, Settings settings) {
        super(settings.maxDamageIfAbsent(material.getDurability()));
        this.material = material;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemstack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.TIBERIUM_HIT, SoundCategory.NEUTRAL,1.5F, 1F);
        user.getItemCooldownManager().set(this, 40);
        if (!world.isClient()) {
            user.addStatusEffect((new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 20, 3)));
            world.getEntitiesByClass(LivingEntity.class, user.getBoundingBox().expand(8.0), e->true).forEach(e->e.
                    addStatusEffect((new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 60 * 2 , 1 / 4))));
            world.getEntitiesByClass(LivingEntity.class, user.getBoundingBox().expand(8.0), e->true).forEach(e->e.
            addStatusEffect((new StatusEffectInstance(ModEffects.HEALING_EFFECT, 60 * 2 , 1 / 4))));

        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemstack.damage(1, user, p -> p.sendToolBreakStatus(hand));
        }
        if (world.isClient){
            ParticleUtil.spawnParticle(user.getWorld(), BlockPos.ofFloored(user.getX(),user.getY(),user.getZ()).up( 2 ), world.random, ModParticles.PINK_SHROOM_PARTICLE);
        }
        return TypedActionResult.success(itemstack, world.isClient());
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
        tooltip.add(Text.translatable("item.signum.healing_staff.info"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}

