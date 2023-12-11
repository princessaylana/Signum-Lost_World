/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;
import za.lana.signum.entity.hostile.WizardEntity;

public class WizardAttackGoal extends MeleeAttackGoal {
    private final WizardEntity entity;
    //wait 20ticks till attack happens, 1sec into attack animation
    private int attackDelay = 20;
    private int ticksUntilNextAttack = 20;
    //
    private boolean shouldCountTillNextAttack = false;
    //protected float range = 16;
    protected float attackDistance = 1.0f;
    protected float maxSpellDistance = 24.0f;

    public WizardAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        entity = ((WizardEntity) mob);
    }
    @Override
    public void start() {
        super.start();
        //length of animation
        attackDelay = 20;
        ticksUntilNextAttack = 20;

    }
    @Override
    public boolean shouldContinue() {
        float f = this.mob.getHealth();
        if (f >= 0.25f && this.mob.getRandom().nextInt(100) == 0) {
            this.mob.setTarget(null);
            return false;
        }
        return super.shouldContinue();
    }
    @Override
    protected void attack(LivingEntity pTarget) {
        // MELEE ATTACK WHEN TARGET IN CLOSE RANGE
        if (isEnemyWithinAttackDistance(pTarget)) {

            shouldCountTillNextAttack = true;
            if(isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);

            }
            if(isTimeToAttack()) {
                this.mob.getLookControl().lookAt(pTarget.getX(), pTarget.getEyeY(), pTarget.getZ());
                performAttack(pTarget);
            }
        }
        // RANGED ATTACK WHEN TARGET OUT OF RANGE
        if (isEnemyWithinSpellCastRange(pTarget)) {
            shouldCountTillNextAttack = true;
            if(isTimeToStartAttackAnimation()) {
                entity.setSpellShooting(true);
            }
            if(isTimeToAttack()) {
                this.mob.getLookControl().lookAt(pTarget.getX(), pTarget.getEyeY(), pTarget.getZ());
                performSpelltAttack(pTarget);
            }
        }
        // RESET
        else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.setSpellShooting(false);
            entity.attackAniTimeout = 0;
            entity.spellAniTimeout = 0;
        }
    }
    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy) {
        // entity distance is less than or equal to 2.0f
        if (this.entity.canSee(pEnemy)) {
            return this.entity.distanceTo(pEnemy) <= attackDistance + entity.getWidth();
        }
        return false;
    }
    private boolean maxSpellRange(LivingEntity pEnemy){
        // entity distance is less than or equal to 16.0f
        return this.entity.distanceTo(pEnemy) <= maxSpellDistance + entity.getWidth();
    }
    private boolean isEnemyWithinSpellCastRange(LivingEntity pEnemy) {
        // distance not less than 2.0f or less or equal to 16.0f
        return !isEnemyWithinAttackDistance(pEnemy) && this.entity.canSee(pEnemy) || maxSpellRange(pEnemy);
    }
    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.getTickCount(attackDelay * 2); // 40 ticks
    }
    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }
    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }
    protected void performAttack(LivingEntity pEnemy) {
        this.resetAttackCooldown();
        this.mob.swingHand(Hand.MAIN_HAND);
        this.mob.tryAttack(pEnemy);
    }
    protected void performSpelltAttack(LivingEntity pEnemy) {
        //this.entity.getNavigation().stop();
        this.entity.spellCast(pEnemy);
        this.resetAttackCooldown();
    }
    @Override
    public void tick() {
        super.tick();
        // countdown to next attack
        if(shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }
    @Override
    public void stop() {
        entity.setAttacking(false);
        entity.setSpellShooting(false);
        super.stop();
    }
}