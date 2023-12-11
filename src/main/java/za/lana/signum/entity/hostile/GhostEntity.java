/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

import java.util.EnumSet;
import java.util.UUID;
import java.util.function.Predicate;

public class GhostEntity extends HostileEntity implements Angerable {
    public final AnimationState attackAniState = new AnimationState();
    public final AnimationState idleAniState = new AnimationState();
    public final AnimationState teleportAniState = new AnimationState();
    public int attackAniTimeout = 0;
    private int idleAniTimeout = 0;
    public int teleportAniTimeout = 0;

    @Nullable
    private UUID angryAt;
    private int angerTime;
    private int ageWhenTargetSet;
    private int lastAngrySoundAge = Integer.MIN_VALUE;
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    static final TrackedData<Boolean> ANGRY = DataTracker.registerData(GhostEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> PROVOKED = DataTracker.registerData(GhostEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(GhostEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> TELEPORTING = DataTracker.registerData(GhostEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public GhostEntity(EntityType<? extends HostileEntity> type, World level) {
        super(type, level);
        this.experiencePoints = 3;
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new AvoidSunlightGoal(this));
        this.goalSelector.add(2, new ChasePlayerGoal(this));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.75f, 1));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.add(5, new LookAroundGoal(this));

        this.targetSelector.add(1, new GhostEntity.TeleportTowardsPlayerGoal(this, this::shouldAngerAt));
        this.targetSelector.add(1, new GhostEntity.TeleportTowardsEntityGoal(this, this::shouldAngerAt));
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, ZombieEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }
    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(TELEPORTING, false);
        this.dataTracker.startTracking(ANGRY, false);
        this.dataTracker.startTracking(PROVOKED, false);
    }

    // ANIMATIONS
    private void setupAnimationStates() {

        if (this.idleAniTimeout <= 0 ) {
            this.idleAniTimeout = this.random.nextInt(80) + 160;
            this.idleAniState.start(this.age);
        } else {
            --this.idleAniTimeout;
        }
        if(this.isAttacking() && attackAniTimeout <= 0) {
            this.idleAniState.stop();
            this.attackAniTimeout = 40; // 2 seconds, length of spell attack
            this.attackAniState.start(this.age);
        } else {
            --this.attackAniTimeout;
        }
        if(!this.isAttacking()) {
            attackAniState.stop();
        }
        // TELEPORT
        if(this.isTeleporting() && teleportAniTimeout <= 0) {
            this.attackAniState.stop();
            this.idleAniState.stop();
            this.teleportAniTimeout = 40;
            this.teleportAniState.start(this.age);
        } else {
            --this.teleportAniTimeout;
        }
        if(!this.isTeleporting()) {
            this.teleportAniState.stop();
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

    public EntityGroup getGroup() {
        return ModEntityGroup.DEATH_LANDS;
    }
    @Override
    public boolean isTeammate(Entity other) {
        if (super.isTeammate(other)) {
            return true;
        }
        if (other instanceof LivingEntity && ((LivingEntity)other).getGroup() == ModEntityGroup.DEATH_LANDS) {
            return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
        }
        return false;
    }
    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.75f;
    }
    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }
    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    public void setTeleporting(boolean teleporting) {
        this.dataTracker.set(TELEPORTING, teleporting);
    }
    public boolean isTeleporting() {
        return this.dataTracker.get(TELEPORTING);
    }

    // ANGER
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
    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }
    public boolean shouldAngerAt(LivingEntity entity) {
        if (!this.canTarget(entity)) {
            return false;
        }
        if (entity.getType() == EntityType.PLAYER || !(entity.getGroup() == ModEntityGroup.DEATH_LANDS) && this.isUniversallyAngry(entity.getWorld())) {
            return true;
        }
        return entity.getUuid().equals(this.getAngryAt());
    }

    boolean isPlayerStaring(PlayerEntity player) {
        ItemStack itemStack = player.getInventory().armor.get(3);
        if (itemStack.isOf(Blocks.CARVED_PUMPKIN.asItem())) {
            return false;
        }
        Vec3d vec3d = player.getRotationVec(1.0f).normalize();
        Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
        double d = vec3d2.length();
        double e = vec3d.dotProduct(vec3d2.normalize());
        if (e > 1.0 - 0.025 / d) {
            return player.canSee(this);
        }
        return false;
    }
    boolean isEntityStaring(LivingEntity entity) {
        Vec3d vec3d = entity.getRotationVec(1.0f).normalize();
        Vec3d vec3d2 = new Vec3d(this.getX() - entity.getX(), this.getEyeY() - entity.getEyeY(), this.getZ() - entity.getZ());
        double d = vec3d2.length();
        double e = vec3d.dotProduct(vec3d2.normalize());
        if (e > 1.0 - 0.025 / d) {
            return entity.canSee(this);
        }
        return false;
    }

    public boolean isProvoked() {
        return this.dataTracker.get(PROVOKED);
    }
    public void playAngrySound() {
        if (this.age >= this.lastAngrySoundAge + 400) {
            this.lastAngrySoundAge = this.age;
            if (!this.isSilent()) {
                this.getWorld().playSound(this.getX(), this.getEyeY(), this.getZ(), ModSounds.GHOST_ANGRY, this.getSoundCategory(), 2.5f, 1.0f, false);
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
    // SOUND
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.GHOST_AMBIENT;
    }
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.GHOST_HURT;
    }
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.GHOST_DIE;
    }
    @Override
    public void tickMovement() {
        if (this.getWorld().isClient) {
            for (int i = 0; i < 2; ++i) {
                this.getWorld().addParticle(ModParticles.BLACK_SHROOM_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY() - 0.50, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
            }
        }
        if (this.isAlive() && this.isAffectedByDaylight()) {
            this.discard();
        }
        super.tickMovement();
    }

    // TELEPORT
    @Override
    protected void mobTick() {
        float f;
        // this.getWorld().isDay() &&
        if (this.age >= this.ageWhenTargetSet + 600 && (f = this.getBrightnessAtEyes()) > 0.5f &&
                this.getWorld().isSkyVisible(this.getBlockPos()) && this.random.nextFloat() * 30.0f < (f - 0.4f) * 2.0f) {
            this.setTarget(null);
            this.teleportRandomly();
        }
        super.mobTick();
    }
    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        if (target == null) {
            this.ageWhenTargetSet = 0;
        } else {
            this.ageWhenTargetSet = this.age;
        }
    }
    protected boolean teleportRandomly() {
        if (this.getWorld().isClient() || !this.isAlive()) {
            return false;
        }
        double d = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
        double e = this.getY() + (double)(this.random.nextInt(64) - 32);
        double f = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
        return this.teleportTo(d, e, f);
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
        boolean movingBlock = blockState.blocksMovement();
        boolean fluidBlock = blockState.getFluidState().isIn(FluidTags.WATER);
        if (!movingBlock || fluidBlock) {
            return false;

        }
        Vec3d here = this.getPos();
        boolean teleportHere = this.teleport(x, y, z, true);
        if (teleportHere) {
            //
            this.teleportAniState.start(this.age);
            this.setTeleporting(true);
            this.getWorld().emitGameEvent(GameEvent.TELEPORT, here, GameEvent.Emitter.of(this));
            if (!this.isSilent()) {
                this.getWorld().playSound(null, this.prevX, this.prevY, this.prevZ, ModSounds.GHOST_TELEPORT, this.getSoundCategory(), 1.0f, 1.0f);
                this.playSound(ModSounds.GHOST_TELEPORT, 1.0f, 1.0f);
            }
        } else {
            this.setTeleporting(false);
            return false;
        }
        return true;
    }

    // GOALS
    static class TeleportTowardsPlayerGoal
            extends ActiveTargetGoal<PlayerEntity> {
        private final GhostEntity ghost;
        @Nullable
        private PlayerEntity targetPlayer;
        private int lookAtPlayerWarmup;
        private int ticksSinceUnseenTeleport;
        private final TargetPredicate staringPlayerPredicate;
        private final TargetPredicate validTargetPredicate = TargetPredicate.createAttackable().ignoreVisibility();
        private final Predicate<LivingEntity> angerPredicate;

        public TeleportTowardsPlayerGoal(GhostEntity ghost, @Nullable Predicate<LivingEntity> targetPredicate) {
            super(ghost, PlayerEntity.class, 10, false, false, targetPredicate);
            this.ghost = ghost;
            this.angerPredicate = playerEntity -> (!ghost.hasPassengerDeep(playerEntity));
            this.staringPlayerPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(this.getFollowRange()).setPredicate(this.angerPredicate);
        }

        @Override
        public boolean canStart() {
            this.targetPlayer = this.ghost.getWorld().getClosestPlayer(this.staringPlayerPredicate, this.ghost);
            return this.targetPlayer != null;
        }

        @Override
        public void start() {
            this.lookAtPlayerWarmup = this.getTickCount(5);
            this.ticksSinceUnseenTeleport = 0;
            //this.ghost.setProvoked();
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
                this.ghost.lookAtEntity(this.targetPlayer, 10.0f, 10.0f);
                return true;
            }
            if (this.targetEntity != null) {
                if (this.ghost.hasPassengerDeep(this.targetEntity)) {
                    return false;
                }
                if (this.validTargetPredicate.test(this.ghost, this.targetEntity)) {
                    return true;
                }
            }
            return super.shouldContinue();
        }

        @Override
        public void tick() {
            if (this.ghost.getTarget() == null) {
                super.setTargetEntity(null);
            }
            if (this.targetPlayer != null) {
                if (--this.lookAtPlayerWarmup <= 0) {
                    this.targetEntity = this.targetPlayer;
                    this.targetPlayer = null;
                    super.start();
                }
            } else {
                if (this.targetEntity != null && !this.ghost.hasVehicle()) {
                    if (this.ghost.isPlayerStaring((PlayerEntity)this.targetEntity)) {
                        if (this.targetEntity.squaredDistanceTo(this.ghost) < 16.0) {
                            this.ghost.teleportRandomly();
                        }
                        this.ticksSinceUnseenTeleport = 0;
                    } else if (this.targetEntity.squaredDistanceTo(this.ghost) > 256.0 && this.ticksSinceUnseenTeleport++ >=
                            this.getTickCount(30) && this.ghost.teleportTo(this.targetEntity)) {
                        this.ticksSinceUnseenTeleport = 0;
                    }
                }
                super.tick();
            }
        }
    }
    static class TeleportTowardsEntityGoal
            extends ActiveTargetGoal<LivingEntity> {
        private final GhostEntity ghost;
        @Nullable
        private LivingEntity targetPlayer;
        private int lookAtPlayerWarmup;
        private int lookAtEntityWarmup;
        private int ticksSinceUnseenTeleport;
        private final TargetPredicate staringEntityPredicate;
        private final TargetPredicate validTargetPredicate = TargetPredicate.createAttackable().ignoreVisibility();
        private final Predicate<LivingEntity> angerPredicate;

        public TeleportTowardsEntityGoal(GhostEntity ghost, @Nullable Predicate<LivingEntity> targetPredicate) {
            super(ghost, LivingEntity.class, 10, false, false, targetPredicate);
            this.ghost = ghost;
            this.angerPredicate = LivingEntity -> (!ghost.hasPassengerDeep(LivingEntity));
            this.staringEntityPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(this.getFollowRange()).setPredicate(this.angerPredicate);
        }

        @Override
        public boolean canStart() {
            //this.targetPlayer = this.ghost.getWorld().getClosestPlayer(this.staringPlayerPredicate, this.ghost);
            this.targetPlayer = this.ghost.getTarget();
            return this.targetPlayer != null;
        }

        @Override
        public void start() {
            this.lookAtPlayerWarmup = this.getTickCount(5);
            this.ticksSinceUnseenTeleport = 0;
            //this.ghost.setProvoked();
        }

        @Override
        public void stop() {
            this.targetPlayer = null;
            super.stop();
        }

        @Override
        public boolean shouldContinue() {
            if (this.targetPlayer != null) {
                if (!this.staringEntityPredicate.test(this.targetPlayer, target)) {
                    return false;
                }
                this.ghost.lookAtEntity(this.targetPlayer, 10.0f, 10.0f);
                return true;
            }
            if (this.targetEntity != null) {
                if (this.ghost.hasPassengerDeep(this.targetEntity)) {
                    return false;
                }
                if (this.validTargetPredicate.test(this.ghost, this.targetEntity)) {
                    return true;
                }
            }
            return super.shouldContinue();
        }

        @Override
        public void tick() {
            if (this.ghost.getTarget() == null) {
                super.setTargetEntity(null);
            }
            if (this.targetPlayer != null) {
                if (--this.lookAtEntityWarmup <= 0) {
                    this.targetEntity = this.targetPlayer;
                    this.targetPlayer = null;
                    super.start();
                }
            } else {
                if (this.targetEntity != null && !this.ghost.hasVehicle()) {
                    if (this.ghost.isEntityStaring(this.targetEntity)) {
                        if (this.targetEntity.squaredDistanceTo(this.ghost) < 16.0) {
                            this.ghost.teleportRandomly();
                        }
                        this.ticksSinceUnseenTeleport = 0;
                    } else if (this.targetEntity.squaredDistanceTo(this.ghost) > 256.0 &&
                            this.ticksSinceUnseenTeleport++ >=
                            this.getTickCount(30) &&
                            this.ghost.teleportTo(this.targetEntity)) {
                        this.ticksSinceUnseenTeleport = 0;
                    }
                }
                super.tick();
            }
        }
    }

    static class ChasePlayerGoal
            extends Goal {
        private final GhostEntity ghost;
        @Nullable
        private LivingEntity target;

        public ChasePlayerGoal(GhostEntity ghost) {
            this.ghost = ghost;
            this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            this.target = this.ghost.getTarget();
            if (!(this.target instanceof PlayerEntity)) {
                return false;
            }
            double d = this.target.squaredDistanceTo(this.ghost);
            if (d > 256.0) {
                return false;
            }
            return this.ghost.isPlayerStaring((PlayerEntity)this.target);
        }

        @Override
        public void start() {
            this.ghost.getNavigation().stop();
        }

        @Override
        public void tick() {
            assert this.target != null;
            this.ghost.getLookControl().lookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        }
    }

}