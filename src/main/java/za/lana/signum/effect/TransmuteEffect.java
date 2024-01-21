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
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

public class TransmuteEffect extends StatusEffect {
    public TransmuteEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (this == ModEffects.TRANSMUTE_EFFECT) {
            int i = (int)(proximity * (double)(6 << amplifier) + 0.5);
            target.damage(target.getDamageSources().magic(), i);
            World level = target.getWorld();
            spawnParticles(target, level);
            target.playSound(ModSounds.TRANSMUTE_EFFECT, 2F, 2F);
        }
    }
    private static void spawnParticles(Entity victim, World world){
        double e = victim.getX();
        double f = victim.getY();
        double g = victim.getZ();
        int y = (int) victim.getY();
        world.addParticle(ModParticles.TRANSMUTE_PARTICLE, victim.getX() + e, victim.getY() + f, victim.getZ() + g,
                0.5f, Math.cos(y * 20) * 0.15d, 0.5F);
    }
}
