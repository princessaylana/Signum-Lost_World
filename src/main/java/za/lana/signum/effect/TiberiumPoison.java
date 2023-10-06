/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.effect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.TiberiumSkeletonEntity;
import za.lana.signum.entity.hostile.TiberiumWormEntity;
import za.lana.signum.util.ModEntityTypeTags;

import static za.lana.signum.util.ModEntityTypeTags.TIBERIUM_TYPE;

public class TiberiumPoison extends StatusEffect {

    private final int CHANCE = 25; // chance of spread and amplifier
    private final int DURATION_TPOISON = 100; // duration & random ticks
    protected TiberiumPoison(StatusEffectCategory category, int color) {
        super(category, color);
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (this == ModEffects.TIBERIUM_POISON) {
            if (entity.getHealth() > 1.0f && !entity.getType().isIn(ModEntityTypeTags.TIBERIUM_TYPE)) {
                entity.damage(entity.getDamageSources().magic(), 2.0f);

            } else if (entity.getType().isIn(ModEntityTypeTags.TIBERIUM_TYPE)){
                if (entity.getHealth() < entity.getMaxHealth()) {
                    entity.heal(2.0f);
                }
            }
            if (!entity.getWorld().isClient()) {
                World level = entity.getWorld();
                randomInfect(entity, level);
            }
        }
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        if (this == ModEffects.TIBERIUM_POISON) {
            int i = CHANCE >> amplifier;
            if (i > 0) {
                return duration % i == 0;
            }
        }
        return this == ModEffects.TIBERIUM_POISON;

    }
    public void randomInfect(LivingEntity victim, World level){
        if (this == ModEffects.TIBERIUM_POISON) {
            Random random = level.random;
            if (random.nextInt(CHANCE) != 0) {
                return;
            }
            level.getEntitiesByClass(LivingEntity.class, victim.getBoundingBox().expand(3.0), e->true).forEach(e->e
                    .addStatusEffect(new StatusEffectInstance(ModEffects.TIBERIUM_POISON, DURATION_TPOISON, 1), victim));
        }
    }
}
