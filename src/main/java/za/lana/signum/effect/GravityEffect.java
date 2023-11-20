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
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.World;
import za.lana.signum.tag.ModEntityTypeTags;

public class GravityEffect extends StatusEffect {
    public GravityEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }


    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (this == ModEffects.GRAVITY_EFFECT) {
            if (entity.getHealth() > 1.0f && !entity.getType().isIn(ModEntityTypeTags.TIBERIUM_TYPE)) {
                entity.damage(entity.getDamageSources().magic(), 2.0f);
                entity.addStatusEffect((new StatusEffectInstance(StatusEffects.LEVITATION, 60, 1/2)));

            } else if (entity.getType().isIn(ModEntityTypeTags.TIBERIUM_TYPE)){
                if (entity.getHealth() < entity.getMaxHealth()) {
                    entity.heal(1.0f);
                    entity.heal(Math.max(4 << amplifier, 0));
                }
            }
            if (!entity.getWorld().isClient()) {
                World level = entity.getWorld();
            }
        }
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        if (this == ModEffects.GRAVITY_EFFECT) {
            // chance of spread and amplifier
            int CHANCE = 20;
            int i = CHANCE >> amplifier;
            if (i > 0) {
                return duration % i == 0;
            }
        }
        return this == ModEffects.GRAVITY_EFFECT;

    }
    /**
     public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
     if (this == ModEffects.GRAVITY_EFFECT) {
     int i = (int)(proximity * (double)(6 << amplifier) + 0.5);
     assert attacker != null;

     // TODO WORK IN PROGRESS GRAVITY FORCE
     target.damage(target.getDamageSources().magic(), i);
     target.addStatusEffect((new StatusEffectInstance(StatusEffects.LEVITATION, 60, 1 / 4)));

     }
     }
     **/
}
