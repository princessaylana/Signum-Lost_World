package za.lana.signum.entity.ai;

import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import za.lana.signum.entity.hostile.TiberiumFloaterEntity;
import za.lana.signum.entity.mob.PidgeonEntity;
import za.lana.signum.tag.ModBlockTags;

import java.util.EnumSet;


public class TiberiumFloaterRestGoal
        extends MoveToTargetPosGoal {
    private final TiberiumFloaterEntity mob;

    public TiberiumFloaterRestGoal(MobEntity mob, double speed, int range) {
        super((PathAwareEntity) mob, speed, range, 6);
        this.mob = (TiberiumFloaterEntity) mob;
        this.lowestY = -2;
        this.setControls(EnumSet.of(Control.TARGET, Control.MOVE));
    }

    @Override
    public boolean canStart() {
        World level = this.mob.getWorld();
        return !this.mob.isAttacking() && level.isDay() && !this.mob.isInSleepingPose() && super.canStart();
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
        if (level.isNight()){
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
        }
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        //return world.isAir(pos.up()) && world.getBlockState(pos).isIn(BlockTags.LEAVES);
        return world.isAir(pos.up()) && world.getBlockState(pos).isIn(BlockTags.BEDS);
    }
}

