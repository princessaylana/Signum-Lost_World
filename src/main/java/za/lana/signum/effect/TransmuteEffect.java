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
import org.jetbrains.annotations.Nullable;

public class TransmuteEffect extends StatusEffect {
    public TransmuteEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (this == ModEffects.TRANSMUTE_EFFECT) {
            int i = (int)(proximity * (double)(6 << amplifier) + 0.5);
            target.damage(target.getDamageSources().magic(), i);
        }
    }
}
