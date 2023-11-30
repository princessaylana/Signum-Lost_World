/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.mob;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ai.AnimalFindHomeGoal;
import za.lana.signum.entity.ai.PidgeonFlyGoal;
import za.lana.signum.entity.ai.PidgeonSleepGoal;
import za.lana.signum.entity.ai.PidgeonSitOnTreeGoal;
import za.lana.signum.entity.control.PidgeonFlightControl;
import za.lana.signum.sound.ModSounds;

import java.util.List;

public class PidgeonEntity extends AnimalEntity {
    private int idleAniTimeout = 0;
    private int sleepAniTimeout = 0;
    private int prevSleepAnimation;

    public final AnimationState idleAniState = new AnimationState();
    public final AnimationState sleepAniState = new AnimationState();
    private float sleepAnimation;
    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    private float flapSpeed = 1.0F;
    private float field_28640 = 1.0F;
    private static final TrackedData<Boolean> CHASING = DataTracker.registerData(PidgeonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SLEEPING = DataTracker.registerData(PidgeonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public PidgeonEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 2;
        this.moveControl = new PidgeonFlightControl(this, 30, false);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0F);
    }
    private void setupAnimationStates() {
        if (this.isOnGround() && this.idleAniTimeout <= 0) {
            this.idleAniTimeout = this.random.nextInt(40) + 80;
            this.idleAniState.start(this.age);
        } else {
            --this.idleAniTimeout;
        }
        if ((this.isInSleepingPose() && getWorld().isNight() || this.age % 5 == 0)) {
            this.sleepAniState.start(this.age);
        }
        if (getWorld().isDay()){
            this.sleepAniState.stop();
        }else {
            --this.sleepAniTimeout;
        }
        this.updateSleepAnimation();
    }
    private void updateSleepAnimation() {
        if (this.isInSleepingPose()) {
            this.prevSleepAnimation = (int) this.sleepAnimation;
            this.sleepAnimation = Math.min(1.0F, this.sleepAnimation + 0.15F);
        } else {
            this.sleepAnimation = Math.max(0.0F, this.sleepAnimation - 0.22F);
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

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new JumpChasingGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0f, false));
        this.goalSelector.add(4, new PidgeonFlyGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));
        this.goalSelector.add(7, new AnimalFindHomeGoal(this, 1.05f, 1));
        //this.goalSelector.add(5, new PidgeonSitOnTreeGoal(this, 1.0));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new PidgeonEntity.ProtectHordeGoal());

        initCustomGoals();
    }
    protected void initCustomGoals(){
        this.goalSelector.add(0, new PidgeonSleepGoal(this, 1.1, 16));
        this.goalSelector.add(1, new TemptGoal(this, 1.2, Ingredient.ofItems(Items.WHEAT_SEEDS), false));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0f, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, CatEntity.class, 6.0f, 1.0, 1.2));
    }

    public static DefaultAttributeContainer.Builder setAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.48)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.32f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0);

    }

    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }
    private void flapWings() {
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation += (float)(!this.isOnGround() && !this.hasVehicle() ? 4 : -1) * 0.3F;
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0F, 1.0F);
        if (!this.isOnGround() && this.flapSpeed < 1.0F) {
            this.flapSpeed = 1.0F;
        }
        this.flapSpeed *= 0.9F;
        Vec3d vec3d = this.getVelocity();
        if (!this.isOnGround() && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }
        this.flapProgress += this.flapSpeed * 2.0F;
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.6F;
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (entityData == null) {
            entityData = new PassiveEntity.PassiveData(false);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
    public void setChasing(boolean chasing) {
        this.dataTracker.set(CHASING, chasing);
    }

    public boolean isChasing() {
        return this.dataTracker.get(CHASING);
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CHASING, false);
        this.dataTracker.startTracking(SLEEPING, false);
    }
    public void setInSleepingPose(boolean sleeping) {
        this.dataTracker.set(SLEEPING, sleeping);
    }

    public boolean isInSleepingPose() {
        return this.dataTracker.get(SLEEPING);
    }
    public float getSleepAnimation(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.prevSleepAnimation, this.sleepAnimation);
    }

    //
    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }

    public void tickMovement() {
        super.tickMovement();
        this.flapWings();
    }
    public boolean tryAttack(Entity target) {
        return target.damage(this.getDamageSources().mobAttack(this), 3.0F);
    }

    public boolean isFlappingWings() {
        return this.speed > this.field_28640;
    }
    protected void addFlapEffects() {
        this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15F, 1.0F);
        this.field_28640 = this.speed + this.maxWingDeviation / 2.0F;
    }
    public boolean isPushable() {
        return true;
    }

    protected void pushAway(Entity entity) {
        if (!(entity instanceof PlayerEntity)) {
            super.pushAway(entity);
        }
    }
    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if (effect.getEffectType() == ModEffects.GRAVITY_EFFECT) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }

    @Nullable
    public SoundEvent getAmbientSound() {
        World world = this.getWorld();
        if (world.isDay()){
            return ModSounds.PIDGEON_CALL;
        }
        return null;
    }
    public float getSoundPitch() {
        return getSoundPitch(this.random);
    }
    public static float getSoundPitch(Random random) {
        return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
    }
    public boolean isInAir() {
        return !this.isOnGround();
    }
    public boolean isFlying() {
        return this.isFlappingWings();
    }

    public static boolean canJumpChase(PidgeonEntity pidgeon, LivingEntity target) {
        double d = target.getZ() - pidgeon.getZ();
        double e = target.getX() - pidgeon.getX();
        double f = d / e;
        boolean i = true;

        for(int j = 0; j < 6; ++j) {
            double g = f == 0.0 ? 0.0 : d * (double)((float)j / 6.0F);
            double h = f == 0.0 ? e * (double)((float)j / 6.0F) : g / f;
            for(int k = 1; k < 4; ++k) {
                if (!pidgeon.getWorld().getBlockState(BlockPos.ofFloored(
                        pidgeon.getX() + h,
                        pidgeon.getY() + (double)k,
                        pidgeon.getZ() + g)).isReplaceable()) {
                    return false;
                }
            }
        }
        return true;
    }

    public class JumpChasingGoal extends DiveJumpingGoal {
        public JumpChasingGoal(PidgeonEntity pidgeon) {
            this.pidgeon = pidgeon;
        }
        private final PidgeonEntity pidgeon;

        public boolean canStart() {
            if (!this.pidgeon.isFlying() && !this.pidgeon.isOnGround()) {
                return false;
            } else {
                LivingEntity target = this.pidgeon.getTarget();
                if (target!= null && target.isAlive()) {
                    if (target.getMovementDirection() != target.getHorizontalFacing()) {
                        return false;
                    } else {
                        boolean bl = canJumpChase(this.pidgeon, target);
                        if (!bl) {
                            PidgeonEntity.this.getNavigation().findPathTo(target, 0);
                        }
                        return bl;
                    }
                } else {
                    return false;
                }
            }
        }

        public boolean shouldContinue() {
            LivingEntity target = PidgeonEntity.this.getTarget();
            if (target != null && target.isAlive()) {
                double d = this.pidgeon.getVelocity().y;
                return (!(d * d < 0.05000000074505806) || !(Math.abs(this.pidgeon.getPitch()) < 15.0F) || !this.pidgeon.isOnGround());
            } else {
                return false;
            }
        }

        public boolean canStop() {
            return false;
        }

        public void start() {
            pidgeon.setJumping(true);
            this.pidgeon.setChasing(true);
            LivingEntity livingEntity = this.pidgeon.getTarget();
            if (livingEntity != null) {
                this.pidgeon.getLookControl().lookAt(livingEntity, 60.0F, 30.0F);
                Vec3d vec3d = (new Vec3d(
                        livingEntity.getX() - this.pidgeon.getX(),
                        livingEntity.getY() - this.pidgeon.getY(),
                        livingEntity.getZ() - this.pidgeon.getZ())).normalize();
                this.pidgeon.setVelocity(this.pidgeon.getVelocity().add(vec3d.x * 0.8, 0.9, vec3d.z * 0.8));
            }
            this.pidgeon.getNavigation().stop();
        }
        public void stop() {
            this.pidgeon.setChasing(false);
            super.stop();
        }

        public void tick() {
            LivingEntity livingEntity = this.pidgeon.getTarget();
            if (livingEntity != null) {
                this.pidgeon.getLookControl().lookAt(livingEntity, 60.0F, 30.0F);
            }

            if (!this.pidgeon.isOnGround()) {
                Vec3d vec3d = this.pidgeon.getVelocity();
                if (vec3d.y * vec3d.y < 0.029999999329447746 && this.pidgeon.getPitch() != 0.0F) {
                    this.pidgeon.setPitch(MathHelper.lerpAngleDegrees(0.2F, this.pidgeon.getPitch(), 0.0F));
                } else {
                    double d = vec3d.horizontalLength();
                    double e = Math.signum(-vec3d.y) * Math.acos(d / vec3d.length()) * 57.2957763671875;
                    this.pidgeon.setPitch((float)e);
                }
            }

            if (livingEntity != null && this.pidgeon.distanceTo(livingEntity) <= 2.0F) {
                this.pidgeon.tryAttack(livingEntity);

            } else if (this.pidgeon.getPitch() > 0.0F && this.pidgeon.isOnGround() &&
                    (float)this.pidgeon.getVelocity().y != 0.0F && this.pidgeon.getWorld()
                    .getBlockState(this.pidgeon.getBlockPos()).isOf(Blocks.SNOW)) {
                this.pidgeon.setPitch(60.0F);
                this.pidgeon.setTarget(null);
                //this.pidgeon.setWalking(true);
            }

        }
    }

    class ProtectHordeGoal
            extends ActiveTargetGoal<LivingEntity> {
        public ProtectHordeGoal() {
            super(PidgeonEntity.this, LivingEntity.class, 20, true, true, null);
        }

        @Override
        public boolean canStart() {
            if (super.canStart()) {
                List<PidgeonEntity> list =
                        PidgeonEntity.this.getWorld().getNonSpectatingEntities(PidgeonEntity.class, PidgeonEntity.this.getBoundingBox().expand(32.0, 8.0, 32.0));
                for (PidgeonEntity pidgeon : list) {
                    if (!pidgeon.isBaby()) continue;
                    return true;
                }
            }
            return false;
        }

        @Override
        protected double getFollowRange() {
            return super.getFollowRange() * 0.5;
        }
    }

}
