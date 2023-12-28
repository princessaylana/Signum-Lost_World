/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.TiberiumFloaterEntity;
import za.lana.signum.entity.hostile.TiberiumWormEntity;
import za.lana.signum.tag.ModBlockTags;

import java.util.EnumSet;


public class TiberiumFloaterRestGoal
        extends MoveToTargetPosGoal {
    private final TiberiumFloaterEntity mob;

    public TiberiumFloaterRestGoal(MobEntity mob, double speed, int range) {
        super((PathAwareEntity) mob, speed, range, 24);
        this.mob = (TiberiumFloaterEntity) mob;
        this.lowestY = -1;
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
    }

    @Override
    protected int getInterval(PathAwareEntity mob) {
        return 40;
    }

    @Override
    public void stop() {
        World level = this.mob.getWorld();
        if (level.isNight() && this.mob.isInSleepingPose() || this.mob.isAttacking() || !(this.mob.getDamageSources() == null)){
            this.mob.setInSleepingPose(false);
            this.mob.wakeUp();
            super.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.hasReached()) {
            this.mob.setInSleepingPose(false);
        } else if (!this.mob.isInSleepingPose()) {
            this.mob.setInSleepingPose(true);
            this.mob.sleep(targetPos);
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

    private void spawnTiberiumWorm(ServerWorld world, BlockPos pos) {
        TiberiumWormEntity tiberiumWormEntity = ModEntities.TIBERIUM_WORM.create(world);
        if (tiberiumWormEntity != null) {
            tiberiumWormEntity.refreshPositionAndAngles((double)pos.getX() + 1.5f, pos.getY(), (double)pos.getZ() + 0.5, 0.0f, 0.0f);
            world.spawnEntity(tiberiumWormEntity);
            //tiberiumWormEntity.playSpawnEffects();
        }
    }
}
