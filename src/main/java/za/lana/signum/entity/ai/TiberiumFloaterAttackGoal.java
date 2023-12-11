package za.lana.signum.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;
import za.lana.signum.entity.hostile.TiberiumFloaterEntity;
import za.lana.signum.entity.mob.UnicornEntity;

public class TiberiumFloaterAttackGoal extends MeleeAttackGoal {
    private final TiberiumFloaterEntity entity;
    private int attackDelay = 20;
    private int ticksUntilNextAttack = 20;
    private boolean shouldCountTillNextAttack = false;
    //unicorn attack 1sec 20ticks

    public TiberiumFloaterAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        entity = ((TiberiumFloaterEntity) mob);
    }
    @Override
    public void start() {
        super.start();
        //length of animation
        attackDelay = 20;
        ticksUntilNextAttack = 20;
    }
    @Override
    protected void attack(LivingEntity pTarget) {
        if (isEnemyWithinAttackDistance(pTarget)) {
            shouldCountTillNextAttack = true;

            if(isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);
            }

            if(isTimeToAttack()) {
                this.mob.getLookControl().lookAt(pTarget.getX(), pTarget.getEyeY(), pTarget.getZ());
                performAttack(pTarget);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAniTimeout = 0;
        }
    }
    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy) {
        return this.entity.distanceTo(pEnemy) <= 2f; // TODO
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
    @Override
    public void tick() {
        super.tick();
        if(shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }
    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }
}
