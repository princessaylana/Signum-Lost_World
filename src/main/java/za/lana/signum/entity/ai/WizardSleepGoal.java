package za.lana.signum.entity.ai;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import za.lana.signum.entity.hostile.WizardEntity;

import java.util.EnumSet;


public class WizardSleepGoal
        extends MoveToTargetPosGoal {
    private final WizardEntity mob;

    public WizardSleepGoal(MobEntity mob, double speed, int range) {
        super((PathAwareEntity) mob, speed, range );
        this.mob = (WizardEntity) mob;
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
        return 80;
    }

    @Override
    public void stop() {
        World level = this.mob.getWorld();
        if (level.isNight() || !(this.mob.getAttacker() == null)) {
            super.stop();
            this.mob.setAiDisabled(false);
            this.mob.setInSleepingPose(false);
            this.mob.wakeUp();
            //this.mob.setPose(EntityPose.STANDING);
        }
    }

    @Override
    public void tick() {
        super.tick();
        mob.setAttacking(false);
        mob.setSpellShooting(false);
        if (!this.hasReached()) {
            this.mob.setInSleepingPose(false);
            this.mob.setAiDisabled(false);

        } else if (!this.mob.isInSleepingPose()) {
            this.mob.setInSleepingPose(true);
            this.mob.sleep(targetPos);
            // oveControl.State.MOVE_TO
            this.mob.setAiDisabled(true);
            this.setSleepingStill();
        }
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.isAir(pos.up()) && world.getBlockState(pos).isIn(BlockTags.BEDS);
        //return level.isAir(pos.up()) && !isBedOccupiedByOthers((ServerWorld) level, pos, this.mob)
    }
    private static boolean isBedOccupiedByOthers(ServerWorld world, BlockPos pos, LivingEntity entity) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isIn(BlockTags.BEDS) && blockState.get(BedBlock.OCCUPIED) && !entity.isSleeping();
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

