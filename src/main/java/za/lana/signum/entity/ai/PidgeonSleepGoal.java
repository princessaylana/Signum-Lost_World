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
import za.lana.signum.entity.mob.PidgeonEntity;

import java.util.EnumSet;


public class PidgeonSleepGoal
        extends MoveToTargetPosGoal {
    private final PidgeonEntity mob;

    public PidgeonSleepGoal(MobEntity mob, double speed, int range) {
        super((PathAwareEntity) mob, speed, range, 12);
        this.mob = (PidgeonEntity) mob;
        this.lowestY = -2;
        this.setControls(EnumSet.of(Control.TARGET, Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        World level = this.mob.getWorld();
        return !this.mob.isAttacking() && level.isNight() && !this.mob.isInSleepingPose() && super.canStart();
    }

    @Override
    public void start() {
        super.start();
        //this.mob.setInSittingPose(false);
    }

    @Override
    protected int getInterval(PathAwareEntity mob) {
        return 40;
    }

    @Override
    public void stop() {
        World level = this.mob.getWorld();
        if (level.isDay()){
        super.stop();
        this.mob.setInSleepingPose(false);
        this.mob.wakeUp();
        }
        if (this.mob.isAttacking() || !(this.mob.getAttacker() == null)){
            super.stop();
            this.mob.setInSleepingPose(false);
            this.mob.wakeUp();
        }
    }

    @Override
    public void tick() {
        super.tick();
        //this.mob.setInSittingPose(false);
        if (!this.hasReached()) {
            this.mob.setInSleepingPose(false);
        } else if (!this.mob.isInSleepingPose()) {
            this.mob.setInSleepingPose(true);
            this.mob.sleep(targetPos);
        }
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.isAir(pos.up()) && world.getBlockState(pos).isIn(BlockTags.LEAVES);
    }
}

