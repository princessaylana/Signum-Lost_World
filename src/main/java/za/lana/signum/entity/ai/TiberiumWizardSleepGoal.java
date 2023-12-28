/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.ai;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import za.lana.signum.entity.hostile.TiberiumWizardEntity;

import java.util.EnumSet;

public class TiberiumWizardSleepGoal
        extends MoveToTargetPosGoal {
    private final TiberiumWizardEntity mob;

    public TiberiumWizardSleepGoal(MobEntity mob, double speed, int range) {
        super((PathAwareEntity) mob, speed, range, 6);
        this.mob = (TiberiumWizardEntity) mob;
        this.lowestY = 0;
        this.setControls(EnumSet.of(Control.TARGET, Goal.Control.MOVE, Control.JUMP));
    }

    @Override
    public boolean canStart() {
        World level = this.mob.getWorld();
        return !this.mob.isAttacking() && level.isDay() && !this.mob.isInSleepingPose() && super.canStart();
    }

    @Override
    public void start() {
        super.start();
        //this.mob.setAttacking(false);
    }

    @Override
    protected int getInterval(PathAwareEntity mob) {
        return 40;
    }

    @Override
    public void stop() {
        World level = this.mob.getWorld();
        if (level.isNight()){
            super.stop();
            this.mob.setInSleepingPose(false);
            this.mob.wakeUp();
        }
        if (!(this.mob.getAttacker() == null)){
            super.stop();
            this.mob.setInSleepingPose(false);
            this.mob.wakeUp();
            this.mob.setTarget(this.mob.getAttacker());
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.hasReached()) {
            this.mob.setInSleepingPose(false);
        } else if (!this.mob.isInSleepingPose()) {
            this.mob.setInSleepingPose(true);
            //
            this.mob.getNavigation().stop();
            //
            this.mob.sleep(targetPos);
        }
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        if (!world.isAir(pos.up())) {
            return false;
        }
        return world.isAir(pos.up()) && world.getBlockState(pos).isIn(BlockTags.BEDS);
    }
    //
    private void setSleepingStill(BlockPos targetPos){
        if (!this.mob.getWorld().isClient()) {
            double x = this.mob.getX();
            double y = this.mob.getY();
            double z = this.mob.getZ();

            this.mob.teleport(x, y, z);
            this.mob.setVelocity(0, 0, 0);
        }
    }
}
