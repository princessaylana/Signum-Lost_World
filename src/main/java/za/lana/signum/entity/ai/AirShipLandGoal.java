/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.ai;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.entity.transport.AirShipEntity;
import za.lana.signum.tag.ModBlockTags;

import java.util.EnumSet;
import java.util.List;

public class AirShipLandGoal
        extends MoveToTargetPosGoal {
    private final AirShipEntity mob;
    private List<BlockPos> landingLocation = Lists.newArrayList();
    public int landingPosX;
    public int landingPosY;
    public int landingPosZ;
    public BlockPos landingPos;
    private int telePortDelay;

    public AirShipLandGoal(MobEntity mob, double speed, int range) {
        super((PathAwareEntity) mob, speed, range, 24);
        this.mob = (AirShipEntity) mob;
        this.lowestY = -1;
        this.setControls(EnumSet.of(Control.TARGET, Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        World level = this.mob.getWorld();
        if (level.isNight() && !this.mob.isInSleepingPose() && super.canStart()){
            return true;
        }
        //
        if (targetPos == null){
            //BlockPos landingPos = new BlockPos(landingPosX, landingPosY, landingPosZ);
            this.mob.getNavigation().startMovingTo(landingPosX, landingPosY + 1, landingPosZ, 1.25f);
            telePortDelay = 200;
            return true;
        }
        return false;
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
        if (level.isDay() && this.mob.isInSleepingPose() || !(this.mob.getDamageSources() == null)){
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
            // NEED TO TELEPORT BACK TO POS AFTER AMOUNT OF TIME
            telePortDelay --;
            if (this.mob.squaredDistanceTo(landingPosX, landingPosY, landingPosZ) >= 144.0) {
                if (telePortDelay <= 0){
                    this.mob.teleport(landingPosX, landingPosY, landingPosZ);
                }
            }
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
        // WRITE LANDING POSITION TO NBT
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isOf(ModBlocks.AIRSHIP_LANDING_BLOCK)) {
            NbtCompound nbtData = new NbtCompound();
            nbtData.putInt("landingPos.x", (targetPos.getX()));
            nbtData.putInt("landingPos.y", (targetPos.getY()));
            nbtData.putInt("landingPos.z", (targetPos.getZ()));
            return true;
        }
        return blockState.isIn(ModBlockTags.AIRSHIP_LANDING_BLOCKS);
    }
    // GET LANDINGPOS FROM NBT
    public void readCustomDataFromNbt(NbtCompound nbtData) {
        landingPosX = nbtData.getInt("landingPos.x");
        landingPosY = nbtData.getInt("landingPos.y");
        landingPosZ = nbtData.getInt("landingPos.z");
    }
}