/**
 * SIGNUM
 * MIT License
 * Lana
 * 2024
 * */
package za.lana.signum.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.entity.transport.CargoDroneEntity;
import za.lana.signum.sound.ModSounds;

import java.util.EnumSet;

import static za.lana.signum.entity.transport.CargoDroneEntity.UNIVERSAL_DRONE_RANGE;

public class CargoDroneLandGoal
        extends MoveToTargetPosGoal {
    private final CargoDroneEntity mob;
    public CargoDroneLandGoal(MobEntity mob, double speed, int range) {
        super((PathAwareEntity) mob, speed, range, UNIVERSAL_DRONE_RANGE);
        this.mob = (CargoDroneEntity) mob;
        this.lowestY = -1;
        this.setControls(EnumSet.of(Control.TARGET, Control.MOVE));
    }

    @Override
    public boolean canStart() {
        World level = this.mob.getWorld();
        if (!level.isDay() && !this.mob.isInLandingPose() && super.canStart()) {
            System.out.println("signum:CDrone:Landing Mode");
            //this.mob.playSound(ModSounds.CARGODRONE_FLYING_TO_LANDING, 1.0f,1.0f);
            return true;
        }
        System.out.println("signum:CDrone:Flying Mode");
        return false;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    protected int getInterval(PathAwareEntity mob) {
        int udr = UNIVERSAL_DRONE_RANGE;
        return udr/2; // 128/2=64
        //return 40;
    }

    @Override
    public void stop() {
        World level = this.mob.getWorld();
        if (level.isDay() && this.mob.isInLandingPose()){
            this.mob.setInLandingPose(false);
            this.mob.velocityDirty = false;
            this.mob.setNoGravity(true);
            System.out.println("signum:CDrone:Stopped - Success");
            //this.mob.playSound(ModSounds.CARGODRONE_LANDED, 1.0f,1.0f);
            super.stop();
        }
    }

    @Override
    public void tick() {
        super.tick();
        World level = this.mob.getWorld();
        if (!level.isDay()) {
            if (!this.hasReached()) {
                System.out.println("signum:CDrone:Flying to Alternate Landing Pos");
                //
            } else if (!this.mob.isInLandingPose() && level.isNight()) {
                this.mob.getNavigation().startMovingTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.2f);
                this.tryToLand(targetPos);
                //
            } else if (this.mob.isInLandingPose()) {
                this.mob.setVelocity(Vec3d.ZERO);
                this.mob.velocityDirty = true;
                this.mob.setNoGravity(false);
                //
                System.out.println("signum:CDrone: Alternate Landing Success");
            }
        }
    }
    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        World level = this.mob.getWorld();
        if (!world.isAir(pos.up())) {
            return false;
        }
        if (level.isDay()){
            return false;
        }
        // GET BLOCKSTATE // CHECK BLOCKS // FUTURE MODBLOCKTAG
        // world.getBlockState(pos.down()).getBlock();
        BlockState blockState = world.getBlockState(pos);
        /**
        if(blockState.isOf(ModBlocks.EXAMPLE_BLOCK)){
            System.out.println("signum:CDrone: Found Station");
            return true;
        }
         **/
        //System.out.println("signum:CDrone:Found Alternate Pos");
        return scanForLandBlocks(world, pos);
        //return false;
    }

    protected boolean scanForLandBlocks(WorldView world, BlockPos pos) {
        World level = this.mob.getWorld();
        if (!world.isAir(pos.up())) {
            return false;
        }
        if (level.isDay()){
            return false;
        }
        // LIST OF LANDING OR NON LANDING
        // world.getBlockState(pos.down()).getBlock();
        BlockState blockState = world.getBlockState(pos);
        if(!blockState.isOf(Blocks.AIR)
                && !blockState.isOf(Blocks.LAVA)
                && !blockState.isOf(Blocks.WATER)
                && !blockState.isOf(Blocks.FIRE)
                && !blockState.isOf(Blocks.POWDER_SNOW)
                && !blockState.isOf(Blocks.TORCH)
                && !blockState.isOf(Blocks.CHEST)
                && !blockState.isOf(Blocks.LADDER)
                && !blockState.isOf(ModBlocks.BUDDING_TIBERIUM)){
            System.out.println("signum:CDrone:Found Alternate Pos");
            return true;
        }
        return false;
    }
    public void tryToLand(BlockPos pos){
        World level = this.mob.getWorld();
         boolean aboveSurface = !level.isAir(pos.down());
         if (aboveSurface) {
             System.out.println("signum:CDrone:Trying to Land");
             this.mob.onLanding();
             if (this.mob.isOnGround() && level.isNight()){
                 this.mob.fallDistance *= 0.0f;
                 this.mob.setInLandingPose(true);
                 // LANDED
                 //this.mob.playSound(ModSounds.CARGODRONE_LANDED, 1.0f,1.0f);
             }else if (this.mob.isOnGround() && level.isDay()){
                 this.mob.setInLandingPose(false);
                 System.out.println("signum:CDrone:Stopping");
             }
         }
    }

    private void scanBlocksBelowDrone(BlockView world, BlockPos pos) {
        //
        World level = this.mob.getWorld();
        BlockState blockState = world.getBlockState(pos);
        //world.getBlockState(pos.down()).getBlock();
    }

    public boolean saveLocation(BlockView world, BlockPos pos){
        World level = this.mob.getWorld();
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isOf(ModBlocks.EXAMPLE_BLOCK)) {
            NbtCompound nbtData = new NbtCompound();
            nbtData.putInt("droneBoxPosX", (targetPos.getX()));
            nbtData.putInt("droneBoxPosY", (targetPos.getY()));
            nbtData.putInt("droneBoxPosZ", (targetPos.getZ()));
            return true;
        }
        //return blockState.isIn(ModBlockTags.AIRSHIP_LANDING_BLOCKS);
        return false;
    }

}