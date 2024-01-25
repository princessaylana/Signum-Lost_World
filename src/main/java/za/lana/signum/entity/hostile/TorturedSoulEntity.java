/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.EntityView;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.entity.ai.TrackSumSkeletonTargetGoal;
import za.lana.signum.entity.ai.TrackTorturedSoulTargetGoal;
import za.lana.signum.entity.control.CargoDroneControl;
import za.lana.signum.entity.control.TorturedSoulControl;
import za.lana.signum.item.ModItems;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

import java.util.List;

public class TorturedSoulEntity extends TameableEntity {
    public int attackAniTimeout = 0;
    private int idleAniTimeout = 0;
    public final AnimationState attackAniState = new AnimationState();
    public final AnimationState idleAniState = new AnimationState();
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(TorturedSoulEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public TorturedSoulEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 3;
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
    }
    public void initGoals(){

        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 6f));
        this.goalSelector.add(3, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));

        this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(1, new AttackWithOwnerGoal(this));
        this.targetSelector.add(2, new TorturedSoulEntity.ProtectHordeGoal());
        this.targetSelector.add(2, new TrackTorturedSoulTargetGoal(this));
        this.targetSelector.add(3, new TorturedSoulEntity.SumSkeletonRevengeGoal());

        this.initCustomGoals();
    }
    protected void initCustomGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new AvoidSunlightGoal(this));
        this.goalSelector.add(3, new EscapeSunlightGoal(this, 1.2));

    }

    public static DefaultAttributeContainer.Builder setAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.34f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.34f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 2.1f;
    }
    private void setupAnimationStates() {
        if (this.idleAniTimeout <= 0) {
            this.idleAniTimeout = this.random.nextInt(40) + 80;
            this.idleAniState.start(this.age);
        } else {
            --this.idleAniTimeout;
        }
        if(this.isAttacking() && attackAniTimeout <= 0) {
            getAttackSound();
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
    public void tick() {
        super.tick();

        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
        //
    }
    //
    public EntityGroup getGroup() {
        return ModEntityGroup.GOLDEN_KINGDOM;
    }
    @Override
    public boolean isTeammate(Entity other) {
        if (super.isTeammate(other)) {
            return true;
        }
        if (other instanceof LivingEntity && ((LivingEntity)other).getGroup() == ModEntityGroup.GOLDEN_KINGDOM) {
            return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
        }
        return false;
    }
    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if (effect.getEffectType() == ModEffects.TRANSMUTE_EFFECT) {
            return false;
        }
        // TODO ADD DEATH EFFECT
        if (effect.getEffectType() == ModEffects.TRANSMUTE_EFFECT) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }

    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }
    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putUuid("Owner", this.getOwnerUuid());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        nbt.getUuid("Owner");
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
    @Override
    public boolean collidesWith(Entity other) {
        return canCollide(this, other);
    }
    public static boolean canCollide(Entity entity, Entity other) {
        return false;
    }
    public boolean isPushable() {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
    public boolean tryAttack(Entity target) {
        boolean attack = target.damage(this.getDamageSources().mobAttack(this), (float)((int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
        if (attack) {
            this.applyDamageEffects(this, target);
        }
        return attack;
    }

    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
            if (target instanceof TorturedSoulEntity torturedSoul) {
                return !torturedSoul.isTamed() || torturedSoul.getOwner() != owner;
            } else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).shouldDamagePlayer((PlayerEntity)target)) {
                return false;
            } else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity)target).isTame()) {
                return false;
            }  else if (target != null && target.getGroup() == ModEntityGroup.GOLDEN_KINGDOM) {
                return false;
            }
            //
            else {
                return !(target instanceof TameableEntity) || !((TameableEntity)target).isTamed();
            }
        } else {
            return false;
        }

    }
    @Override
    public EntityView method_48926() {
        return getWorld();
    }
    // SKELETON SOUNDS
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
    protected SoundEvent getAttackSound() {
        return ModSounds.GHOST_ATTACK;
    }

    //
    class SumSkeletonRevengeGoal
            extends RevengeGoal {
        public SumSkeletonRevengeGoal() {
            super(TorturedSoulEntity.this);
        }
        @Override
        public void start() {
            super.start();
            if (TorturedSoulEntity.this.isAttacking()) {
                this.callSameTypeForRevenge();
                this.stop();
            }
        }
        @Override
        protected void setMobEntityTarget(MobEntity mob, LivingEntity target) {
            if (mob instanceof TorturedSoulEntity) {
                super.setMobEntityTarget(mob, target);
            }
        }
    }
    class ProtectHordeGoal
            extends ActiveTargetGoal<LivingEntity> {
        public ProtectHordeGoal() {
            super(TorturedSoulEntity.this, LivingEntity.class, 20, true, true, null);
        }

        @Override
        public boolean canStart() {
            if (super.canStart()) {
                List<TorturedSoulEntity> list = TorturedSoulEntity.this.getWorld().getNonSpectatingEntities(TorturedSoulEntity.class, TorturedSoulEntity.this.getBoundingBox().expand(16.0, 4.0, 16.0));
                for (TorturedSoulEntity torturedSoul : list) {
                    if (torturedSoul.isTamed()) continue;
                    return true;
                }
            }
            return false;
        }
        @Override
        protected double getFollowRange() {
            return super.getFollowRange() * 1.2f;
        }
    }
}
