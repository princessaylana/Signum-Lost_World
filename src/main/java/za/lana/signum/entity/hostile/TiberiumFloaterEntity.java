/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.entity.ai.FloaterAttackGoal;
import za.lana.signum.entity.ai.TiberiumFloaterRestGoal;
import za.lana.signum.entity.ai.WizardSleepGoal;
import za.lana.signum.entity.control.TiberiumFloaterFlightControl;
import za.lana.signum.entity.projectile.TiberiumSpitEntity;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

import java.util.EnumSet;

public class TiberiumFloaterEntity
        extends AnimalEntity {

    public int attackAniTimeout = 0;
    private int idleAniTimeout = 0;
    public int spitAniTimeout = 0;
    private int prevSleepAnimation;

    public final AnimationState attackAniState = new AnimationState();
    public final AnimationState idleAniState = new AnimationState();
    public final AnimationState spitAniState = new AnimationState();

    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(TiberiumFloaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SPITTING = DataTracker.registerData(TiberiumFloaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> NO_GRAVITY = DataTracker.registerData(TiberiumFloaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SLEEPING = DataTracker.registerData(TiberiumFloaterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public final Random random = Random.create();
    public TiberiumFloaterEntity(EntityType<? extends TiberiumFloaterEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 10;
        this.moveControl = new TiberiumFloaterFlightControl(this, 30, false);
        this.tryCheckBlockCollision();
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0f);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0f);
        this.setNoGravity(true);
    }
    @Override
    protected void initGoals() {

        this.goalSelector.add(2, new FloaterAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new TiberiumFloaterEntity.LookAtTargetGoal(this));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 24f));
        this.goalSelector.add(4, new FlyRandomlyGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this));
        //this.targetSelector.add(1, new WizardEntity.ProtectHordeGoal());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, ZombieEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));

        this.initCustomGoals();
    }
    //
    protected void initCustomGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new TiberiumFloaterRestGoal(this, 1.2f, 256));
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
        this.dataTracker.startTracking(SPITTING, false);

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
        if (this.idleAniTimeout <= 0 && !this.isInSleepingPose()) {
            //this.sleepAniState.stop();
            this.idleAniTimeout = this.random.nextInt(80) + 160;
            this.idleAniState.start(this.age);
        } else {
            --this.idleAniTimeout;
        }
        // ATTACK
        if(this.isAttacking() && attackAniTimeout <= 0) {
            this.spitAniState.stop();
            this.idleAniState.stop();
            //this.sleepAniState.stop();
            //
            this.attackAniTimeout = 40; // 2 seconds, length of spell attack
            this.attackAniState.start(this.age);
        } else {
            --this.attackAniTimeout;
        }
        if(!this.isAttacking()) {
            attackAniState.stop();
        }
        // SPIT
        if(this.isSpitting() && spitAniTimeout <= 0) {
            this.attackAniState.stop();
            this.idleAniState.stop();
            //this.sleepAniState.stop();
            //
            this.spitAniTimeout = 40;
            this.spitAniState.start(this.age);
        } else {
            --this.spitAniTimeout;
        }
        if(!this.isSpitting()) {
            this.spitAniState.stop();
        }
        // SLEEPING isInSleepingPose() ?
        //
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

    @Override
    public void tickMovement() {
        World level = this.getWorld();
        if (level.isClient && level.isNight()) {
            for (int i = 0; i < 2; ++i) {
                this.getWorld().addParticle(ModParticles.TIBERIUM_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY() - 0.25, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
            }
        }
        super.tickMovement();
    }

    // DATA
    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }
    public void setInSleepingPose(boolean sleeping) {
        this.dataTracker.set(SLEEPING, sleeping);
    }
    public void setSpitting(boolean spitting) {
        this.dataTracker.set(SPITTING, spitting);
    }
    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }
    public boolean isSpitting() {
        return this.dataTracker.get(SPITTING);
    }
    public boolean isInSleepingPose() {
        return this.dataTracker.get(SLEEPING);
    }

    // NBT
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    //RANGE ATTACK
    public void spit(LivingEntity target) {
        if (this.canSee(target)) {
            World level = this.getEntityWorld();
            TiberiumSpitEntity tiberiumSpit = new TiberiumSpitEntity(level, this);
            double d = target.getX() - this.getX();
            double e = target.getBodyY(0.3333333333333333) - tiberiumSpit.getY();
            double f = target.getZ() - this.getZ();
            double g = Math.sqrt(d * d + f * f) * 0.20000000298023224;

            tiberiumSpit.setVelocity(d, e + g, f, 1.5F, 10.0F);
            if (!this.isSilent()) {
                this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.TIBERIUM_HIT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            }
            this.getWorld().spawnEntity(tiberiumSpit);
            this.playSound(ModSounds.FLOATER_SHOOT, 0.15F, 1.0F);
        }
    }
    // ATTRIBUTES
    public EntityGroup getGroup() {
        return ModEntityGroup.TIBERIUM;
    }
    @Override
    public boolean isTeammate(Entity other) {
        if (super.isTeammate(other)) {
            return true;
        }
        if (other instanceof LivingEntity && ((LivingEntity)other).getGroup() == ModEntityGroup.TIBERIUM) {
            return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
        }
        return false;
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

    // SOUNDS
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
    //
    @Override
    public int getLimitPerChunk() {
        return 1;
    }
    public static boolean canSpawn(EntityType<TiberiumFloaterEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(15) == 0 && TiberiumFloaterEntity.canMobSpawn(type, world, spawnReason, pos, random);
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

}

