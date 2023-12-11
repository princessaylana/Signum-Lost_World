/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.entity.ai.TiberiumFloaterRestGoal;
import za.lana.signum.entity.control.TiberiumFloaterFlightControl;
import za.lana.signum.sound.ModSounds;
import za.lana.signum.tag.ModEntityTypeTags;

import java.util.EnumSet;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

public class TiberiumFloaterEntity
        extends AnimalEntity
        implements Monster, Angerable {

    public int attackAniTimeout = 0;
    private int idleAniTimeout = 0;
    public int spellAniTimeout = 0;
    public int rangeAniTimeout = 0;
    private int prevSleepAnimation;

    public final AnimationState attackAniState = new AnimationState();
    public final AnimationState idleAniState = new AnimationState();
    //public final AnimationState spitAniState = new AnimationState();
    //public final AnimationState rangeAniState = new AnimationState();

    @Nullable
    private UUID angryAt;
    private int angerTime;
    private int ageWhenTargetSet;
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private int lastAngrySoundAge = Integer.MIN_VALUE;
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(TiberiumFloaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> ANGRY = DataTracker.registerData(TiberiumFloaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> PROVOKED = DataTracker.registerData(TiberiumFloaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> NO_GRAVITY = DataTracker.registerData(TiberiumFloaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SLEEPING = DataTracker.registerData(TiberiumFloaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final EntityAttributeModifier ATTACKING_SPEED_BOOST = new EntityAttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", 0.15f, EntityAttributeModifier.Operation.ADDITION);
    public TiberiumFloaterEntity(EntityType<? extends TiberiumFloaterEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 10;
        this.moveControl = new TiberiumFloaterFlightControl(this, 30, false);
        this.tryCheckBlockCollision();
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0f);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0f);
        //this.noClip = true;
        this.setNoGravity(true);
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new LookAtTargetGoal(this));
        this.goalSelector.add(1, new TiberiumFloaterRestGoal(this, 1.2f, 64));
        //this.goalSelector.add(2, new FlyRandomlyGoal(this));
        this.goalSelector.add(3, new TeleportToTargetGoal(this, this::shouldAngerAt));
        //this.goalSelector.add(3, new ShootBoltGoal(this));
        this.goalSelector.add(7, new FlyRandomlyGoal(this));


        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, entity -> Math.abs(entity.getY() - this.getY()) <= 4.0));
        //this.targetSelector.add(2, new TiberiumFloaterEntity.ProtectTroopersGoal());
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(3, new TeleportToTargetGoal(this, this::shouldAngerAt));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, VillagerEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, ZombieEntity.class, true));
        //this.targetSelector.add(6, new ActiveTargetGoal<>(this, WizardEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return FlyingEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0);
    }
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(SLEEPING, false);
        this.dataTracker.startTracking(ANGRY, false);
        this.dataTracker.startTracking(PROVOKED, false);

    }
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }
    //ANIMATIONS
    private void setupAnimationStates() {
        if (this.idleAniTimeout <= 0) {
            this.idleAniTimeout = this.random.nextInt(40) + 80;
            this.idleAniState.start(this.age);
        } else {
            --this.idleAniTimeout;
        }
        if(this.isAttacking() && attackAniTimeout <= 0) {
            attackAniTimeout = 40;
            attackAniState.start(this.age);
        } else {
            --this.attackAniTimeout;
        }
        if(!this.isAttacking()) {
            attackAniState.stop();
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
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }
    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }

    // DATA
    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }
    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }
    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_FLYING_SPEED);
        if (target == null) {
            this.ageWhenTargetSet = 0;
            this.dataTracker.set(ANGRY, false);
            this.dataTracker.set(PROVOKED, false);
            assert entityAttributeInstance != null;
            entityAttributeInstance.removeModifier(ATTACKING_SPEED_BOOST.getId());
        } else {
            this.ageWhenTargetSet = this.age;
            this.dataTracker.set(ANGRY, true);
            assert entityAttributeInstance != null;
            if (!entityAttributeInstance.hasModifier(ATTACKING_SPEED_BOOST)) {
                entityAttributeInstance.addTemporaryModifier(ATTACKING_SPEED_BOOST);
            }
        }
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (ANGRY.equals(data) && this.isProvoked() && this.getWorld().isClient) {
            this.playAngrySound();
        }
        super.onTrackedDataSet(data);
    }
    public void setInSleepingPose(boolean sleeping) {
        this.dataTracker.set(SLEEPING, sleeping);
    }
    public boolean isInSleepingPose() {
        return this.dataTracker.get(SLEEPING);
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeAngerToNbt(nbt);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readAngerFromNbt(this.getWorld(), nbt);
    }

    // ATTRIBUTES
    public EntityGroup getGroup() {
        return ModEntityGroup.TIBERIUM;
    }
    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if (effect.getEffectType() == ModEffects.TIBERIUM_POISON) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }

    // hitbox width must be 2, height must be 3.5
    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 2.5f;
    }
    @Override
    protected SoundEvent getAmbientSound() {
        World level = this.getWorld();
        if (level.isNight()){
            return ModSounds.FLOATER_AMBIENT;
        }
        return  null;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.FLOATER_HURT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.FLOATER_DIE;
    }
    @Override
    protected float getSoundVolume() {
        return 5.0f;
    }
    @Override
    public int getLimitPerChunk() {
        return 1;
    }
    public static boolean canSpawn(EntityType<TiberiumFloaterEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(15) == 0 && TiberiumFloaterEntity.canMobSpawn(type, world, spawnReason, pos, random);
    }

    // ANGER
    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }
    @Override
    public int getAngerTime() {
        return this.angerTime;
    }
    @Override
    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }
    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.angryAt;
    }
    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }
    public boolean shouldAngerAt(LivingEntity entity) {
        if (!this.canTarget(entity)) {
            return false;
        }
        if (entity.getType() == EntityType.PLAYER && this.isUniversallyAngry(entity.getWorld())) {
            return true;
        }
        return entity.getUuid().equals(this.getAngryAt());
    }
    public boolean isAngry() {
        return this.dataTracker.get(ANGRY);
    }
    public boolean isProvoked() {
        return this.dataTracker.get(PROVOKED);
    }
    public void setProvoked() {
        this.dataTracker.set(PROVOKED, true);
    }
    public void playAngrySound() {
        if (this.age >= this.lastAngrySoundAge + 400) {
            this.lastAngrySoundAge = this.age;
            if (!this.isSilent()) {
                this.getWorld().playSound(this.getX(), this.getEyeY(), this.getZ(), ModSounds.FLOATER_WARNS, this.getSoundCategory(), 2.5f, 1.0f, false);
            }
        }
    }
    boolean isPlayerStaring(PlayerEntity player) {
        ItemStack itemStack = player.getInventory().armor.get(3);
        // need to change this to something else
        if (itemStack.isOf(Blocks.CARVED_PUMPKIN.asItem())) {
            return false;
        }
        Vec3d vec3d = player.getRotationVec(1.0f).normalize();
        Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
        double d = vec3d2.length();
        double e = vec3d.dotProduct(vec3d2 = vec3d2.normalize());
        if (e > 1.0 - 0.025 / d) {
            return player.canSee(this);
        }
        return false;
    }
    public boolean tiberiumTarget(){
        return Objects.requireNonNull(this.getTarget()).getType().isIn(ModEntityTypeTags.TIBERIUM_TARGETS);
    }

    // TELEPORTATION
    @Override
    protected void mobTick() {
        float f;
        if (this.getWorld().isDay() && this.age >= this.ageWhenTargetSet + 600 &&
                (f = this.getAngerTime()) > 0.5f
                && this.getWorld().isSkyVisible(this.getBlockPos())
                && this.random.nextFloat() * 30.0f < (f - 0.4f) * 2.0f) {
            this.setTelePortTarget();
            this.teleportRandomly();
        }
        super.mobTick();
    }

    private void setTelePortTarget() {
        LivingEntity target = this.getTarget();
        super.setTarget(target);
        if (target == null) {
            this.ageWhenTargetSet = 0;
        } else {
            this.ageWhenTargetSet = this.age;
        }
    }

    protected void teleportRandomly() {
        if (this.getWorld().isClient() || !this.isAlive()) {
            return;
        }
        double d = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
        double e = this.getY() + (double)(this.random.nextInt(64) - 32);
        double f = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
        this.teleportTo(d, e, f);
    }
    boolean teleportTo(Entity entity) {
        Vec3d vec3d = new Vec3d(this.getX() - entity.getX(), this.getBodyY(0.5) - entity.getEyeY(), this.getZ() - entity.getZ());
        vec3d = vec3d.normalize();
        double d = 16.0;
        double e = this.getX() + (this.random.nextDouble() - 0.5) * 8.0 - vec3d.x * 16.0;
        double f = this.getY() + (double)(this.random.nextInt(16) - 8) - vec3d.y * 16.0;
        double g = this.getZ() + (this.random.nextDouble() - 0.5) * 8.0 - vec3d.z * 16.0;
        return this.teleportTo(e, f, g);
    }
    private boolean teleportTo(double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
        while (mutable.getY() > this.getWorld().getBottomY() && !this.getWorld().getBlockState(mutable).blocksMovement()) {
            mutable.move(Direction.DOWN);
        }
        BlockState blockState = this.getWorld().getBlockState(mutable);
        boolean bl = blockState.blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (!bl || bl2) {
            return false;
        }
        Vec3d vec3d = this.getPos();
        boolean bl3 = this.teleport(x, y, z, true);
        if (bl3) {
            this.getWorld().emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(this));
            if (!this.isSilent()) {
                this.getWorld().playSound(null, this.prevX, this.prevY, this.prevZ, ModSounds.FLOATER_TELEPORT, this.getSoundCategory(), 1.0f, 1.2f);
                this.playSound(ModSounds.FLOATER_TELEPORT, 1.0f, 1.2f);
            }
        }
        return bl3;
    }

    // GOALS
    static class LookAtTargetGoal
            extends Goal {
        private final TiberiumFloaterEntity floater;

        public LookAtTargetGoal(TiberiumFloaterEntity floater) {
            this.floater = floater;
            this.setControls(EnumSet.of(Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.floater.getTarget() == null) {
                Vec3d vec3d = this.floater.getVelocity();
                this.floater.setYaw(-((float)MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776f);
                this.floater.bodyYaw = this.floater.getYaw();
            } else {
                LivingEntity livingEntity = this.floater.getTarget();
                double d = 64.0;
                if (livingEntity.squaredDistanceTo(this.floater) < 4096.0) {
                    double e = livingEntity.getX() - this.floater.getX();
                    double f = livingEntity.getZ() - this.floater.getZ();
                    this.floater.setYaw(-((float)MathHelper.atan2(e, f)) * 57.295776f);
                    this.floater.bodyYaw = this.floater.getYaw();
                }
            }
        }
    }
    static class FlyRandomlyGoal
            extends Goal {
        private final TiberiumFloaterEntity floater;

        public FlyRandomlyGoal(TiberiumFloaterEntity floater) {
            this.floater = floater;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            double f;
            double e;
            MoveControl moveControl = this.floater.getMoveControl();
            if (!moveControl.isMoving()) {
                return true;
            }
            double d = moveControl.getTargetX() - this.floater.getX();
            double g = d * d + (e = moveControl.getTargetY() - this.floater.getY()) * e + (f = moveControl.getTargetZ() - this.floater.getZ()) * f;
            return g < 1.0 || g > 3600.0;


        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void start() {
            Random random = this.floater.getRandom();
            double d = this.floater.getX() + (double)((random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            double e = this.floater.getY() + (double)((random.nextFloat() * 2.0f - 1.0f) * 8.0f);
            double f = this.floater.getZ() + (double)((random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.floater.getMoveControl().moveTo(d, e, f, 1.0);
        }
    }

    static class TeleportToTargetGoal
            extends ActiveTargetGoal<PlayerEntity> {
        private final TiberiumFloaterEntity floater;
        @Nullable
        private PlayerEntity targetPlayer;
        private int lookAtPlayerWarmup;
        private int ticksSinceUnseenTeleport;
        private final TargetPredicate staringPlayerPredicate;
        private final TargetPredicate validTargetPredicate = TargetPredicate.createAttackable().ignoreVisibility();
        private final Predicate<LivingEntity> angerPredicate;

        public TeleportToTargetGoal(TiberiumFloaterEntity floater, @Nullable Predicate<LivingEntity> targetPredicate) {
            super(floater, PlayerEntity.class, 10, false, false, targetPredicate);
            this.floater = floater;
            this.angerPredicate = playerEntity -> (floater.isPlayerStaring((PlayerEntity)playerEntity) || floater.shouldAngerAt(playerEntity)) && !floater.hasPassengerDeep(playerEntity);
            this.staringPlayerPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(this.getFollowRange()).setPredicate(this.angerPredicate);
        }

        @Override
        public boolean canStart() {
            this.targetPlayer = this.floater.getWorld().getClosestPlayer(this.staringPlayerPredicate, this.floater);
            //
            return this.targetPlayer != null;
        }

        @Override
        public void start() {
            this.lookAtPlayerWarmup = this.getTickCount(5);
            this.ticksSinceUnseenTeleport = 0;
            this.floater.setProvoked();
        }

        @Override
        public void stop() {
            this.targetPlayer = null;
            super.stop();
        }

        @Override
        public boolean shouldContinue() {
            if (this.targetPlayer != null) {
                if (!this.angerPredicate.test(this.targetPlayer)) {
                    return false;
                }
                this.floater.lookAtEntity(this.targetPlayer, 12.0f, 12.0f);
                return true;
            }
            if (this.targetEntity != null) {
                if (this.floater.hasPassengerDeep(this.targetEntity)) {
                    return false;
                }
                if (this.validTargetPredicate.test(this.floater, this.targetEntity)) {
                    return true;
                }
            }
            return super.shouldContinue();
        }

        @Override
        public void tick() {
            if (this.floater.getTarget() == null) {
                super.setTargetEntity(null);
            }
            if (this.targetPlayer != null) {
                if (--this.lookAtPlayerWarmup <= 0) {
                    this.targetEntity = this.targetPlayer;
                    this.targetPlayer = null;
                    super.start();
                }
            } else {
                if (this.targetEntity != null && !this.floater.hasVehicle()) {
                    if (this.floater.isPlayerStaring((PlayerEntity)this.targetEntity)) {
                        if (this.targetEntity.squaredDistanceTo(this.floater) < 16.0) {
                            this.floater.teleportRandomly();
                        }
                        this.ticksSinceUnseenTeleport = 0;
                    } else if (this.targetEntity.squaredDistanceTo(this.floater) > 256.0 && this.ticksSinceUnseenTeleport++ >= this.getTickCount(30) && this.floater.teleportTo(this.targetEntity)) {
                        this.ticksSinceUnseenTeleport = 0;
                    }
                }
                super.tick();
            }
        }
    }
}

