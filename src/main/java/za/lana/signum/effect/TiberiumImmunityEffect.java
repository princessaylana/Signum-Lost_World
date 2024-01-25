package za.lana.signum.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import za.lana.signum.particle.ModParticles;

import java.util.Iterator;

public class TiberiumImmunityEffect extends StatusEffect {

    protected TiberiumImmunityEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        Iterator<StatusEffect> iterator = entity.getActiveStatusEffects().keySet().iterator();
        StatusEffect statusEffect = iterator.next();
        StatusEffectInstance effect = entity.getActiveStatusEffects().get(statusEffect);
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 100, 1), entity);
        //entity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 100, 5), entity);

        if (effect.getEffectType() == ModEffects.TIBERIUM_POISON) {
            entity.heal(1.0f);
            World level = entity.getWorld();
            spawnParticles(entity, level);
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

    private static void spawnParticles(Entity victim, World world){
        double e = victim.getX();
        double f = victim.getY();
        double g = victim.getZ();
        int y = (int) victim.getY();
        world.addParticle(ModParticles.TIBERIUM_PARTICLE, victim.getX() + e, victim.getY() + f, victim.getZ() + g,
                0.5f, Math.cos(y * 20) * 0.15d, 0.5F);
    }
}
