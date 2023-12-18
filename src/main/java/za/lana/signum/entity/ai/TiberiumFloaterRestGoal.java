package za.lana.signum.entity.ai;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.entity.hostile.TiberiumFloaterEntity;
import za.lana.signum.tag.ModBlockTags;

import java.util.EnumSet;


public class TiberiumFloaterRestGoal
        extends MoveToTargetPosGoal {
    private final TiberiumFloaterEntity mob;

    public TiberiumFloaterRestGoal(MobEntity mob, double speed, int range) {
        super((PathAwareEntity) mob, speed, range);
        this.mob = (TiberiumFloaterEntity) mob;
        //this.lowestY = -2;
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
    }

    @Override
    protected int getInterval(PathAwareEntity mob) {
        return 40;
    }

    @Override
    public void stop() {
        World level = this.mob.getWorld();
        if (level.isNight() || !(this.mob.getAttacker() == null)){
            super.stop();
            this.mob.setInSleepingPose(false);
            this.mob.wakeUp();
        }
    }

    @Override
    public void tick() {
        super.tick();
        mob.setAttacking(false);
        mob.setSpitting(false);

        if (!this.hasReached()) {
            this.mob.setInSleepingPose(false);

        } else if (this.mob.isInSleepingPose()) {
            this.mob.sleep(targetPos);
            this.mob.setInSleepingPose(true);
            this.setSleepingStill();
        }
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        if (!world.isAir(pos.up())) {
            return false;
        }
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isOf(ModBlocks.TOXIC_SHROOM_BLOCK)) {
            return true;
        }
        return blockState.isIn(ModBlockTags.FLOATER_LANDING_BLOCKS);
        //return world.isAir(pos.up()) && world.getBlockState(pos).isIn(BlockTags.BEDS);
    }

    private void setSleepingStill(){
        if (!this.mob.getWorld().isClient()) {
            double x = this.mob.getX();
            double y = this.mob.getY();
            double z = this.mob.getZ();

            this.mob.teleport(x, y, z);
            this.mob.setVelocity(0, 0, 0);
        }
    }
}

