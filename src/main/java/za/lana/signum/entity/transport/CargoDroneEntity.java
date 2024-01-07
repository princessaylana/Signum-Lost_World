/**
 * SIGNUM
 * MIT License
 * Lana
 * 2024
 * */
package za.lana.signum.entity.transport;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.entity.ai.CargoDroneFlyRandomGoal;
import za.lana.signum.entity.control.CargoDroneControl;
import za.lana.signum.entity.pathing.DroneNavigation;
import za.lana.signum.sound.ModSounds;
import za.lana.signum.util.ImplementedInventory;

public class CargoDroneEntity extends AnimalEntity implements ImplementedInventory {
    public final AnimationState idleAniState= new AnimationState();
    public final AnimationState landAniState= new AnimationState();
    private int idleAniTimeout = 0;
    private int landTimeout = 0;
    public static int UNIVERSAL_DRONE_RANGE = 128;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(8, ItemStack.EMPTY);
    private static final TrackedData<Boolean> LANDING = DataTracker.registerData(CargoDroneEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> SYNCED = DataTracker.registerData(CargoDroneEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public CargoDroneEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.tryCheckBlockCollision();
        this.moveControl = new CargoDroneControl(this, 30, false);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0f);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0f);
        this.setNoGravity(true);
    }
    public static DefaultAttributeContainer setAttributes() {
        return PathAwareEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25F)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.31F)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, UNIVERSAL_DRONE_RANGE)
                .build();
    }
    private void setupAnimationStates() {
        if (this.idleAniTimeout <= 0) {
            landAniState.stop();

            this.idleAniTimeout = this.random.nextInt(40) + 80;
            this.idleAniState.start(this.age);
        } else {
            --this.idleAniTimeout;
        }
        //
        if(this.isInLandingPose() && landTimeout <= 0) {
            idleAniState.stop();
            landTimeout = 40;
            landAniState.start(this.age);
        } else {
            --this.landTimeout;
        }
        if(!this.isInLandingPose()) {
            landAniState.stop();
        }
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

        //this.goalSelector.add(2, new CargoDroneLandOnDroneBoxGoal(this, 1.20, (int) UNIVERSAL_DRONE_RANGE));
        this.goalSelector.add(4, new CargoDroneFlyRandomGoal(this, 24.0f));
        //this.goalSelector.add(5, new CargoDroneLandGoal(this, 1.20, (int) UNIVERSAL_DRONE_RANGE));

    }
    protected EntityNavigation createNavigation(World world) {
        DroneNavigation droneNavigation = new DroneNavigation(this, world);
        droneNavigation.setCanPathThroughDoors(true);
        droneNavigation.setCanSwim(true);
        droneNavigation.setCanEnterOpenDoors(true);
        return droneNavigation;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
    public void playSpawnEffects() {
       // writeCustomDataToNbt(this);

        if (this.getWorld().isClient) {
            for(int i = 0; i < 20; ++i) {
                double d = this.random.nextGaussian() * 0.02;
                double e = this.random.nextGaussian() * 0.02;
                double f = this.random.nextGaussian() * 0.02;
                double g = 10.0;
                this.getWorld().addParticle(ParticleTypes.POOF, this.offsetX(1.0) - d * 10.0, this.getRandomBodyY() - e * 10.0, this.getParticleZ(1.0) - f * 10.0, d, e, f);
            }
        } else {
            this.getWorld().sendEntityStatus(this, (byte)20);
        }
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(LANDING, false);
        this.dataTracker.startTracking(SYNCED, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbtData) {
        super.writeCustomDataToNbt(nbtData);
        Inventories.writeNbt(nbtData, inventory);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }
    public void setInLandingPose(boolean landing) {
        //EntityPose.STANDING;
        this.dataTracker.set(LANDING, landing);
    }
    public boolean isInLandingPose() {
        return this.dataTracker.get(LANDING);
    }

    public void setSyncedWithDroneBox(boolean synced) {
        this.dataTracker.set(SYNCED, synced);
    }
    public boolean isSyncedWithDroneBox() {
        return this.dataTracker.get(SYNCED);
    }
    protected void playStepSound(BlockPos pos, BlockState state) {
        //this.playSound(this.getStepSound(), 0.15f, 1.0f+ this.random.nextFloat() * 1.2F);
        this.playSound(this.getStepSound(), 0.15f, 1.0f + this.random.nextFloat() * 1.1F);
    }

    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }
    // SOUNDS
    SoundEvent getStepSound() {return null;}
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }
    protected SoundEvent getDeathSound() {
        return null;
    }

    /**
    // if this is not alive:
    private void disConnectFromDroneBox(){
        World level = this.getWorld();
        BlockPos droneBoxPos = new BlockPos(
                CargoDroneLandOnDroneBoxGoal.droneBoxX,
                CargoDroneLandOnDroneBoxGoal.droneBoxY,
                CargoDroneLandOnDroneBoxGoal.droneBoxZ);
        //
        DroneBoxBlock.switchSync(level, droneBoxPos);
        this.setSyncedWithDroneBox(false);
        System.out.println("signum:CDrone:Disconnected from DroneBox");
    }
     **/

    @Override
    public boolean cannotDespawn() {
        return this.isAlive();
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
}
