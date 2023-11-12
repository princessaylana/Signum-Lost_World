
package za.lana.signum.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

class HealingEffect
        extends StatusEffect {
    protected HealingEffect(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        if (entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(1.0f);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 50 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        }
        return true;
    }
}

