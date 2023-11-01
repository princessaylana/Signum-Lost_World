package za.lana.signum.entity.ai;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import za.lana.signum.entity.hostile.TTrooperEntity;

import java.util.EnumSet;

public class TTrooperGoSleepGoal
        extends MoveToTargetPosGoal {
    private final TTrooperEntity tTrooper;

    public TTrooperGoSleepGoal(TTrooperEntity tTrooper, double speed, int range) {
        super(tTrooper, speed, range, 6);
        this.tTrooper = tTrooper;
        this.lowestY = -2;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return this.mob.getWorld().isDay();
    }

    @Override
    public void start() {
        super.start();
        //gameworld time of day
        //this.tTrooper.setInSittingPose(false);
        //this.tTrooper.getEntityWorld().getTimeOfDay();
    }

    @Override
    protected int getInterval(PathAwareEntity mob) {
        return 60;
    }

    @Override
    public void stop() {
        super.stop();
        this.mob.getWorld().isNight();
    }

    @Override
    public void tick() {
        super.tick();
        //this.tTrooper.setInSittingPose(false);
        if (!this.hasReached()) {
            this.tTrooper.setInSleepingPose(false);
        } else if (!this.tTrooper.isInSleepingPose()) {
            this.tTrooper.setInSleepingPose(true);
        }
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.isAir(pos.up()) && world.getBlockState(pos).isIn(BlockTags.BEDS);
    }
}


