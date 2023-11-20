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
import za.lana.signum.entity.hostile.ESpiderEntity;

public class ESpiderSpitAttackGoal extends MeleeAttackGoal {
    private final ESpiderEntity entity;
    //wait 20ticks till attack happens, 1sec into spit animation
    private int spitDelay = 20;
    private int ticksTillNextSpit = 20;
    private int spitTime = ticksTillNextSpit + 2;
    private boolean shouldCountTillNextSpitAttack = false;

    public ESpiderSpitAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        entity = ((ESpiderEntity) mob);
    }
    @Override
    public void start() {
        super.start();
        //length of animation
        spitDelay = 20;
        ticksTillNextSpit = 20;
    }

    @Override
    public boolean shouldContinue() {
        float f = this.mob.getBrightnessAtEyes();
        if (f >= 0.5f && this.mob.getRandom().nextInt(100) == 0) {
            this.mob.setTarget(null);
            return false;
        }
        return super.shouldContinue();
    }

    @Override
    protected void attack(LivingEntity pTarget) {

        if (isEnemyinSpitRange(pTarget)) {
            shouldCountTillNextSpitAttack = true;

            if(isTimeToStartSpitAnimation()) {
                entity.setSpit(true);
            }
            if(isTimeToAttack()) {
                this.mob.getLookControl().lookAt(pTarget.getX(), pTarget.getEyeY(), pTarget.getZ());
                performSpitAttack(pTarget);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextSpitAttack = false;
            entity.setSpit(false);
            entity.spitAniTimeout = 0;
        }
    }

    private boolean isEnemyinSpitRange(LivingEntity pEnemy) {
        boolean spitrange = this.entity.distanceTo(pEnemy) >= 2f + entity.getWidth();
        return spitrange;
    }

    protected void resetAttackCooldown() {
        this.ticksTillNextSpit = this.getTickCount(spitDelay * 2); // 40 ticks
    }
    protected boolean isTimeToStartSpitAnimation() {
        return this.ticksTillNextSpit <= spitDelay;
    }
    protected boolean isTimeToAttack() {
        return this.ticksTillNextSpit <= 0;
    }

    protected void performSpitAttack(LivingEntity pEnemy) {
        this.entity.spitAt(pEnemy);
        this.resetAttackCooldown();
    }

    @Override
    public void tick() {
        super.tick();
        // countdown to next attack
        if(shouldCountTillNextSpitAttack) {
            this.ticksTillNextSpit = Math.max(this.ticksTillNextSpit - 1, 0);
        }
    }
    @Override
    public void stop() {
        entity.setSpit(false);
        super.stop();
    }
}