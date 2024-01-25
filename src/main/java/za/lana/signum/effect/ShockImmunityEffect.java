package za.lana.signum.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import za.lana.signum.particle.ModParticles;

import java.util.Iterator;

public class ShockImmunityEffect extends StatusEffect {
    private LightningEntity lightning;

    protected ShockImmunityEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        Iterator<StatusEffect> iterator = entity.getActiveStatusEffects().keySet().iterator();
        StatusEffect statusEffect = iterator.next();
        StatusEffectInstance effect = entity.getActiveStatusEffects().get(statusEffect);
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 100, 1), entity);
        if (effect.getEffectType() == ModEffects.SHOCK_EFFECT
                || effect.getEffectType() == StatusEffects.CONDUIT_POWER
                || isStrucKByLightning(entity)) {
            //
            entity.heal(1.0f);
            World level1 = entity.getWorld();
            spawnParticles(entity, level1);
        }
        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 50 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        }
        return true;
    }

    private boolean isStrucKByLightning(LivingEntity entity){
        ServerWorld level = (ServerWorld) entity.getWorld();
        LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, level);;
        entity.onStruckByLightning(level, lightningBolt);
        return true;
    }

    private static void spawnParticles(Entity victim, World world){
        double e = victim.getX();
        double f = victim.getY();
        double g = victim.getZ();
        int y = (int) victim.getY();
        world.addParticle(ModParticles.WHITE_SHROOM_PARTICLE, victim.getX() + e, victim.getY() + f, victim.getZ() + g,
                0.5f, Math.cos(y * 20) * 0.15d, 0.5F);
    }
}
