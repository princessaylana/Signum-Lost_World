/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.BlockPos;
import za.lana.signum.sound.ModSounds;

public class TiberiumPoison extends StatusEffect {
    protected TiberiumPoison(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (this == ModEffects.TIBERIUM_POISON) {
            if (entity.getHealth() > 1.0f) {
                entity.damage(entity.getDamageSources().magic(), 2.0f);
                entity.playSound(ModSounds.TIBERIUM_AMBIENT, 1f, 1f);
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        if (this == ModEffects.TIBERIUM_POISON) {
            int i = 25 >> amplifier;
            if (i > 0) {
                return duration % i == 0;
            }
        }
        return this == ModEffects.TIBERIUM_POISON;
    }
}
