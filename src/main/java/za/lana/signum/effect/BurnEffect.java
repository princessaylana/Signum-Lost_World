/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import za.lana.signum.particle.ModParticles;

public class BurnEffect extends StatusEffect {
    private final int SEC = 2; // seconds = burntime
    private final int SIZE = 2; // block size of spread
    //public static final int GROW_CHANCE = 5;
    private final int DURATION_BURN = 100; // duration & random ticks
    public BurnEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.getWorld().isClient()) {
            double x = pLivingEntity.getX();
            double y = pLivingEntity.getY();
            double z = pLivingEntity.getZ();

            World level = pLivingEntity.getWorld();
            pLivingEntity.setOnFireFor(SEC);
            // is below needed?
            // pLivingEntity.damage(pLivingEntity.getDamageSources().inFire(), 2.0f);
            spreadBurn(pLivingEntity, level);
            level.addParticle(ModParticles.FLAME_PARTICLE, x, y, z, 0.0F, 0.5F, 0.0F);
        }
        super.applyUpdateEffect(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
    public void spreadBurn(LivingEntity victim, World level){
        if (this == ModEffects.BURN_EFFECT) {
            Random random = level.random;
            if (random.nextInt(DURATION_BURN) != 0) {
                return;
            }
            level.getEntitiesByClass(LivingEntity.class, victim.getBoundingBox().expand(SIZE), e->true).forEach(e->e
                    .addStatusEffect(new StatusEffectInstance(ModEffects.BURN_EFFECT, DURATION_BURN, 1), victim));
        }
    }
}
