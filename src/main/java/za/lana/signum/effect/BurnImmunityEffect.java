package za.lana.signum.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

public class BurnImmunityEffect extends StatusEffect {
    private static final int DURATION_RESIST = 100; // duration & random ticks
    private int soundTimer = 0;
    protected BurnImmunityEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        soundTimer--;
        if(entity.isOnFire() || entity.isInLava()){
            entity.setFireTicks(0);
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 100, 1), entity);
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, DURATION_RESIST, 1), entity);
            entity.heal(1.0f);
            World level = entity.getWorld();
            spawnParticles(entity, level);
            if (soundTimer == 0){
                entity.playSound(ModSounds.HEALING_EFFECT, 0.5F, 1.0F);
                soundTimer = DURATION_RESIST;
            }
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
        world.addParticle(ModParticles.FLAME_PARTICLE, victim.getX() + e, victim.getY() + f, victim.getZ() + g,
                0.5f, Math.cos(y * 20) * 0.15d, 0.5F);
    }
}
