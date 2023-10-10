/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package za.lana.signum.entity.hostile;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.*;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import za.lana.signum.constant.SignumAnimations;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.projectile.TiberiumBoltEntity;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;
import za.lana.signum.tag.ModEntityTypeTags;

import java.util.EnumSet;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

//todo NEED TO ADD TELEPORTATION and  tiberium particles?
public class TiberiumFloaterEntity
        extends FlyingEntity
        implements GeoEntity, Monster, Angerable {

    @Nullable
    private UUID angryAt;
    private int angerTime;
    private int ageWhenTargetSet;
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final TrackedData<Boolean> SHOOTING = DataTracker.registerData(TiberiumFloaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int boltStrength = 1;

    public TiberiumFloaterEntity(EntityType<? extends TiberiumFloaterEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        this.moveControl = new FloaterMoveControl(this);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.noClip = true;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new LookAtTargetGoal(this));
        this.goalSelector.add(5, new FlyRandomlyGoal(this));
        this.goalSelector.add(7, new TeleportToTargetGoal(this, this::shouldAngerAt));
        this.goalSelector.add(8, new ShootBoltGoal(this));



        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, entity -> Math.abs(entity.getY() - this.getY()) <= 4.0));
        this.targetSelector.add(2, new TeleportToTargetGoal(this, this::shouldAngerAt));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, ZombieEntity.class, true));

    }

    public int getBoltStrength() {
        return this.boltStrength;
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SHOOTING, false);
    }
    public boolean isShooting() {
        return this.dataTracker.get(SHOOTING);
    }
    public void setShooting(boolean shooting) {
        this.dataTracker.set(SHOOTING, shooting);
    }
    @Override
    protected boolean isDisallowedInPeaceful() {
        return true;
    }
    // IMUMME TO TIBERIUM
    private static boolean isBoltFromPlayer(DamageSource damageSource) {
        return damageSource.getSource() instanceof TiberiumBoltEntity && damageSource.getAttacker() instanceof PlayerEntity;
    }
    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return !TiberiumFloaterEntity.isBoltFromPlayer(damageSource) && super.isInvulnerableTo(damageSource);
    }
    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if (effect.getEffectType() == ModEffects.TIBERIUM_POISON) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }
    @Override
    public boolean damage(DamageSource source, float amount) {
        if (TiberiumFloaterEntity.isBoltFromPlayer(source)) {
            super.damage(source, 0.5f);
           // super.damage(source, 1000.0f);
            return true;
        }
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        return super.damage(source, amount);
    }
    // ATTRIBUTES
    public static DefaultAttributeContainer.Builder setAttributes() {
        return FlyingEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0);
    }
    // hitbox width must be 2, height must be 3.5
    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 2.5f;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.FLOATER_AMBIENT;
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

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte("ExplosionPower", (byte)this.boltStrength);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("ExplosionPower", NbtElement.NUMBER_TYPE)) {
            this.boltStrength = nbt.getByte("ExplosionPower");
        }
    }
    public static boolean canSpawn(EntityType<TiberiumFloaterEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(15) == 0 && TiberiumFloaterEntity.canMobSpawn(type, world, spawnReason, pos, random);
    }


    //ANIMATIONS
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "fly",2, this::flyController));
        controllerRegistrar.add(new AnimationController<>(this, "idle", 10, this::idleController));
        //controllerRegistrar.add(new AnimationController<>(this, "attack",4, this::attackController));
    }
    private PlayState flyController(AnimationState<TiberiumFloaterEntity> tsAnimationState) {
        if (tsAnimationState.isMoving()) {
            if (this.getEntityWorld().isClient){
                ParticleUtil.spawnParticle(getWorld(), BlockPos.ofFloored(getPos()), random, ModParticles.TOXIC_SHROOM_PARTICLE);
            }
            return tsAnimationState.setAndContinue(SignumAnimations.TIBERIUM_FLOATER_FLY);
        }
        return PlayState.STOP;
    }
    private PlayState idleController(AnimationState<TiberiumFloaterEntity> tsAnimationState) {
        if (this.getEntityWorld().isClient){
            ParticleUtil.spawnParticle(getWorld(), BlockPos.ofFloored(getPos()), random, ModParticles.BLACK_SHROOM_PARTICLE);
        }
        return tsAnimationState.setAndContinue(tsAnimationState.isMoving() ? SignumAnimations.TIBERIUM_FLOATER_FLY : SignumAnimations.TIBERIUM_FLOATER_IDLE);
    }

    private PlayState attackController(AnimationState<TiberiumFloaterEntity> tsAnimationState) {
        if (tsAnimationState.getAnimatable().handSwinging
                && tsAnimationState.getController().getAnimationState().equals(AnimationController.State.STOPPED)){
            if (this.getEntityWorld().isClient){
                ParticleUtil.spawnParticle(getWorld(), BlockPos.ofFloored(getPos()), random, ModParticles.TIBERIUM_PARTICLE);
            }
            return tsAnimationState.setAndContinue(SignumAnimations.TIBERIUM_SKELETON_ATTACK);
        }
        tsAnimationState.getController().forceAnimationReset();
        return PlayState.CONTINUE;
    }


    // CONTROL
    static class FloaterMoveControl
            extends MoveControl {
        private final TiberiumFloaterEntity floater;
        private int collisionCheckCooldown;

        public FloaterMoveControl(TiberiumFloaterEntity floater) {
            super(floater);
            this.floater = floater;
        }

        @Override
        public void tick() {
            if (this.state != MoveControl.State.MOVE_TO) {
                return;
            }
            if (this.collisionCheckCooldown-- <= 0) {
                this.collisionCheckCooldown += this.floater.getRandom().nextInt(5) + 2;
                Vec3d vec3d = new Vec3d(this.targetX - this.floater.getX(), this.targetY - this.floater.getY(), this.targetZ - this.floater.getZ());
                double d = vec3d.length();
                if (this.willCollide(vec3d = vec3d.normalize(), MathHelper.ceil(d))) {
                    this.floater.setVelocity(this.floater.getVelocity().add(vec3d.multiply(0.1)));
                } else {
                    this.state = MoveControl.State.WAIT;
                }
            }
        }

        private boolean willCollide(Vec3d direction, int steps) {
            Box box = this.floater.getBoundingBox();
            for (int i = 1; i < steps; ++i) {
                box = box.offset(direction);
                if (this.floater.getWorld().isSpaceEmpty(this.floater, box)) continue;
                return false;
            }
            return true;
        }
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
            this.setTarget(null);
            this.teleportRandomly();
        }
        super.mobTick();
    }
    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        if (target == null) {
            // todo teleport towards anger target
            this.ageWhenTargetSet = 0;

        } else {
            //todo find closest budding tiberium block
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



    // GOALS
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
            return this.targetPlayer != null;
        }

        @Override
        public void start() {
            this.lookAtPlayerWarmup = this.getTickCount(5);
            this.ticksSinceUnseenTeleport = 0;
            //this.floater.setProvoked();
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
            //return g < 1.0 || g > 3600.0;
            return g < 1.0 || g > 1800.0;


        }

        @Override
        public boolean shouldContinue() {
            return false;
        }

        @Override
        public void start() {
            Random random = this.floater.getRandom();
            double d = this.floater.getX() + (double)((random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            double e = this.floater.getY() + (double)((random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            double f = this.floater.getZ() + (double)((random.nextFloat() * 2.0f - 1.0f) * 16.0f);
            this.floater.getMoveControl().moveTo(d, e, f, 1.0);
        }
    }
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
    static class ShootBoltGoal
            extends Goal {
        private final TiberiumFloaterEntity floater;
        public int cooldown;

        public ShootBoltGoal(TiberiumFloaterEntity floater) {
            this.floater = floater;
        }

        @Override
        public boolean canStart() {
            return this.floater.getTarget() != null;
        }

        @Override
        public void start() {
            this.cooldown = 0;
        }

        @Override
        public void stop() {
            this.floater.setShooting(false);
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingTarget = this.floater.getTarget();
            if (livingTarget == null) {
                return;
            }
            double d = 64.0;
            if (livingTarget.squaredDistanceTo(this.floater) < 4096.0 && this.floater.canSee(livingTarget)) {
                World world = this.floater.getWorld();
                ++this.cooldown;
                if (this.cooldown == 10 && !this.floater.isSilent()) {
                    //

                    world.playSound(null, livingTarget.getX(), livingTarget.getY(), livingTarget.getZ(),
                            ModSounds.FLOATER_WARNS, SoundCategory.NEUTRAL,
                            0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
                    //world.syncWorldEvent(null, WorldEvents.GHAST_WARNS, this.floater.getBlockPos(), 0);
                }
                if (this.cooldown == 20) {
                    double e = 4.0;
                    Vec3d vec3d = this.floater.getRotationVec(1.0f);
                    double f = livingTarget.getX() - (this.floater.getX() + vec3d.x * 4.0);
                    double g = livingTarget.getBodyY(0.5) - (0.5 + this.floater.getBodyY(0.5));
                    double h = livingTarget.getZ() - (this.floater.getZ() + vec3d.z * 4.0);
                    if (!this.floater.isSilent()) {
                        //
                        world.playSound(null, livingTarget.getX(), livingTarget.getY(), livingTarget.getZ(),
                                ModSounds.FLOATER_SHOOT, SoundCategory.NEUTRAL,
                                0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
                    }
                    FireballEntity fireballEntity = new FireballEntity(world, this.floater, f, g, h, this.floater.getBoltStrength());
                    fireballEntity.setPosition(this.floater.getX() + vec3d.x * 4.0, this.floater.getBodyY(0.5) + 0.5, fireballEntity.getZ() + vec3d.z * 4.0);
                    world.spawnEntity(fireballEntity);
                    this.cooldown = -40;
                }
            } else if (this.cooldown > 0) {
                --this.cooldown;
            }
            this.floater.setShooting(this.cooldown > 10);
        }


    }

}

