/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.transport;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.entity.ai.AirShipFindHomeGoal;
import za.lana.signum.entity.ai.AirShipFlyRandomGoal;
import za.lana.signum.entity.ai.AirShipLandGoal;
import za.lana.signum.entity.ai.AirShipLookAtTarget;
import za.lana.signum.entity.control.AirshipFlightControl;

import java.util.List;

public class AirShipEntity extends AnimalEntity {

    private int idleAniTimeout = 0;
    //private int sleepingAniTimeout = 0;
    public final AnimationState idleAniState= new AnimationState();
    //public final AnimationState sleepAniState= new AnimationState();
    private static final TrackedData<Boolean> SLEEPING = DataTracker.registerData(AirShipEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public AirShipEntity(EntityType<? extends AirShipEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 8;
        this.moveControl = new AirshipFlightControl(this, 30, false);
        //
        this.tryCheckBlockCollision();
        //
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0f);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0f);
        this.setNoGravity(true);

    }
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(false);
        return birdNavigation;
    }

    private void setupAnimationStates() {
        if (this.idleAniTimeout <= 0) {
            this.idleAniTimeout = this.random.nextInt(40) + 80;
            this.idleAniState.start(this.age);
        } else {
            --this.idleAniTimeout;
        }
        //
        // Landing isInSleepingPose()
    }

    protected void updateLimbs(float posDelta) {
        float f;
        if (this.getPose() == EntityPose.STANDING) {
            f = Math.min(posDelta * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }
        this.limbAnimator.updateLimbs(f, 0.2F);
    }
    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void initGoals(){
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.2f));

        this.goalSelector.add(1, new AirShipLandGoal(this, 1.2f, 256));
        this.goalSelector.add(2, new AirShipLookAtTarget(this));
        this.goalSelector.add(3, new AirShipFindHomeGoal(this, 1.2f, 256));
        this.goalSelector.add(4, new AirShipFlyRandomGoal(this));

        // todo Future Attack - drops grief friendly bombs onto targets
        //this.targetSelector.add(1, new RevengeGoal(this));
    }

    public static DefaultAttributeContainer.Builder setAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SLEEPING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbtData) {
        super.writeCustomDataToNbt(nbtData);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbtData) {
        super.readCustomDataFromNbt(nbtData);
    }
    public void setInSleepingPose(boolean sleeping) {
        this.dataTracker.set(SLEEPING, sleeping);
    }
    public boolean isInSleepingPose() {
        return this.dataTracker.get(SLEEPING);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if (effect.getEffectType() == ModEffects.TRANSMUTE_EFFECT) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }
    //
    public EntityGroup getGroup() {
        return ModEntityGroup.TEAM_LIGHT;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.25f;
    }

    // TRAVEL
    @Override
    public boolean isLogicalSideForUpdatingMovement() {
        return true;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.hasPassengers()) {
            player.startRiding(this);
        }
        return super.interactMob(player, hand);
    }
    @Override
    public void updatePassengerPosition(Entity entity, PositionUpdater moveFunction) {
        super.updatePassengerPosition(entity, moveFunction);
        if (entity instanceof LivingEntity passenger) {
            moveFunction.accept(entity, getX(), getY() - 0.25f, getZ());
            this.prevPitch = passenger.prevPitch;
        }
    }
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d vec3d = getPassengerDismountOffset(this.getWidth() * MathHelper.SQUARE_ROOT_OF_TWO, passenger.getWidth(), passenger.getYaw());
        double d = this.getX() + vec3d.x;
        double e = this.getZ() + vec3d.z;
        BlockPos blockPos = BlockPos.ofFloored(d, this.getBoundingBox().maxY, e);
        BlockPos blockPos2 = blockPos.down();
        if (!this.getWorld().isWater(blockPos2)) {
            List<Vec3d> list = Lists.newArrayList();
            double f = this.getWorld().getDismountHeight(blockPos);
            if (Dismounting.canDismountInBlock(f)) {
                list.add(new Vec3d(d, (double)blockPos.getY() + f, e));
            }
            double g = this.getWorld().getDismountHeight(blockPos2);
            if (Dismounting.canDismountInBlock(g)) {
                list.add(new Vec3d(d, (double)blockPos2.getY() + g, e));
            }
            for (EntityPose entityPose : passenger.getPoses()) {
                for (Vec3d vec3d2 : list) {
                    if (Dismounting.canPlaceEntityAt(this.getWorld(), vec3d2, passenger, entityPose)) {
                        passenger.setPose(entityPose);
                        return vec3d2;
                    }
                }
            }
        }

        return super.updatePassengerForDismount(passenger);
    }
    protected void clampPassengerYaw(Entity passenger) {
        passenger.setBodyYaw(this.getYaw());
        float f = MathHelper.wrapDegrees(passenger.getYaw() - this.getYaw());
        float g = MathHelper.clamp(f, -105.0F, 105.0F);
        passenger.prevYaw += g - f;
        passenger.setYaw(passenger.getYaw() + g - f);
        passenger.setHeadYaw(passenger.getYaw());
    }

    public void onPassengerLookAround(Entity passenger) {
        this.clampPassengerYaw(passenger);
    }
    //
    @Override
    protected SoundEvent getAmbientSound() {
        if (!this.isOnGround()) {
            return SoundEvents.BLOCK_FIRE_AMBIENT;
        }
        return null;
    }

}
