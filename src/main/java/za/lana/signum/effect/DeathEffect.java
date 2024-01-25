/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.effect;


import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.Signum;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.TorturedSoulEntity;
import za.lana.signum.particle.ModParticles;

import static za.lana.signum.Signum.LOGGER;

public class DeathEffect extends StatusEffect {
    public DeathEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (this == ModEffects.DEATH_EFFECT) {
            assert attacker != null;
            World level = target.getWorld();
            ServerWorld slevel = (ServerWorld)target.getWorld();
            TorturedSoulEntity torturedSoul = ModEntities.TORTURED_SOUL.spawn(slevel, target.getBlockPos(), SpawnReason.TRIGGERED);
            assert torturedSoul != null;
            if (target instanceof LivingEntity && attacker instanceof PlayerEntity) {
                target.damage(level.getDamageSources().magic(), 5);
                //spawnParticles(target, level);
                //torturedSoul.setOwner((PlayerEntity) attacker);
                //level.spawnEntity(torturedSoul);
                //target.discard();
            }
            if(target instanceof PlayerEntity || target instanceof EnderDragonEntity || target instanceof WitherEntity){
                target.damage(level.getDamageSources().magic(), 5);
            }
            if (level.isClient) {
                spawnParticles(target, level);
                //torturedSoul.playSpawnEffects();
            }
        }
    }
    private static void spawnParticles(Entity victim, World world){
        double e = victim.getX();
        double f = victim.getY();
        double g = victim.getZ();
        int y = (int) victim.getY();
        world.addParticle(ModParticles.BLACK_SHROOM_PARTICLE, victim.getX() + e, victim.getY() + f, victim.getZ() + g,
                0.5f, Math.cos(y * 20) * 0.15d, 0.5F);
    }
}
