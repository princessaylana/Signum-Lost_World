/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import za.lana.signum.sound.ModSounds;

public class ShockEffect extends StatusEffect {

    private final int CHANCE = 10; // chance of spread and amplifier
    private final int DURATION_SHOCK = 20; // duration & random ticks
    protected ShockEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public void applyUpdateEffect(LivingEntity victim, int amplifier) {
        if (this == ModEffects.SHOCK_EFFECT) {
            if (victim.getHealth() > 1.0f) {
                victim.damage(victim.getDamageSources().magic(), 2.0f);
                World level = victim.getWorld();
                spawnParticles(victim, level);
                victim.playSound(ModSounds.TIBERIUM_HIT, 2F, 2F);
            }
            if (!victim.getWorld().isClient()) {
                World level = victim.getWorld();
                randomShock(victim, level);
            }
        }
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        if (this == ModEffects.SHOCK_EFFECT) {
            int i = CHANCE >> amplifier;
            if (i > 0) {
                return duration % i == 0;
            }
        }
        return this == ModEffects.SHOCK_EFFECT;

    }
    public void randomShock(LivingEntity victim, World level){
        if (this == ModEffects.SHOCK_EFFECT) {
            Random random = level.random;
            if (random.nextInt(CHANCE) != 0) {
                return;
            }
            level.getEntitiesByClass(LivingEntity.class, victim.getBoundingBox().expand(3.0), e->true).forEach(e->e
                    .addStatusEffect(new StatusEffectInstance(ModEffects.SHOCK_EFFECT, DURATION_SHOCK, 1), victim));
            spawnParticles(victim, level);
        }
    }
    private static void spawnParticles(Entity victim, World world){
        double e = victim.getX();
        double f = victim.getY();
        double g = victim.getZ();
        int y = (int) victim.getY();
        world.addParticle(ParticleTypes.ELECTRIC_SPARK, victim.getX() + e, victim.getY() + f, victim.getZ() + g,
                0.5f, Math.cos(y * 20) * 0.15d, 0.5F);
    }
}
