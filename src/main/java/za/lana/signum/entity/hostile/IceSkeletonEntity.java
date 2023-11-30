/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.item.ModItems;
import za.lana.signum.particle.ModParticles;

import java.util.EnumSet;
import java.util.List;

public class IceSkeletonEntity extends HostileEntity implements InventoryOwner {
    public int attackAniTimeout = 0;
    private int idleAniTimeout = 0;
    public final AnimationState attackAniState = new AnimationState();
    public final AnimationState idleAniState = new AnimationState();
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(IceSkeletonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final SimpleInventory inventory = new SimpleInventory(6);

    public IceSkeletonEntity(EntityType<? extends IceSkeletonEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }

    public void initGoals(){
        this.goalSelector.add(2, new AvoidSunlightGoal(this));
        this.goalSelector.add(3, new EscapeSunlightGoal(this, 1.0));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.add(4, new WanderAroundGoal(this, 1.0));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));
        //this.goalSelector.add(5, new TibSkeletonEntity.SearchAndDestroyGoal(this, 10.0f));

        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new IceSkeletonEntity.ProtectHordeGoal());
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));

        this.initCustomGoals();
    }
    protected void initCustomGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        //this.targetSelector.add(4, new ActiveTargetGoal<>(this, SumSkeletonEntity.class, true));
        // TESTING
        //this.targetSelector.add(4, new ActiveTargetGoal<>(this, SkeletonEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder setAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5);
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
    }
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeInventory(nbt);
    }
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
    public void tick() {
        super.tick();
        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }

    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }
    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }

    public EntityGroup getGroup() {
        return ModEntityGroup.SSKELETONS;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readInventory(nbt);
        this.setCanPickUpLoot(true);
    }
    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
    }
    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        Random random = world.getRandom();
        this.initEquipment(random, difficulty);
        this.updateEnchantments(random, difficulty);
        this.equipStack(EquipmentSlot.MAINHAND, this.makeInitialWeapon());
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
    private ItemStack makeInitialWeapon() {
        if ((double)this.random.nextFloat() < 0.5) {
            return new ItemStack(Items.IRON_SWORD);
        }
        return new ItemStack(ModItems.TIBERIUM_SWORD);
    }

    @Override
    public boolean isTeammate(Entity other) {
        if (super.isTeammate(other)) {
            return true;
        }
        if (other instanceof LivingEntity && ((LivingEntity)other).getGroup() == ModEntityGroup.SSKELETONS) {
            return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
        }
        return false;
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if (effect.getEffectType() == ModEffects.FREEZE_EFFECT) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }

    // BURNS IN DAYTIME
    @Override
    public void tickMovement() {
        if (this.getWorld().isClient) {
            for (int i = 0; i < 2; ++i) {
                this.getWorld().addParticle(ModParticles.BLUE_SHROOM_PARTICLE, this.getParticleX(0.5), this.getRandomBodyY() - 0.50, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
            }
        }
        if (this.isAlive() && this.isAffectedByDaylight()) {
            this.setOnFireFor(8);
        }
        super.tickMovement();
    }
    @Override
    public SimpleInventory getInventory() {
        return this.inventory;
    }
    @Override
    public boolean canPickupItem(ItemStack stack) {
        return super.canPickupItem(stack);
    }
    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        IceSkeletonEntity iceSkeleton;
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        //Entity entity = source.getAttacker();
        this.dropInventory();
        this.dropItem(ModItems.ICE_CRYSTAL_DUST);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.75f;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 0.15f, 1.0f);
    }
    SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SKELETON_STEP;
    }

    public boolean isShaking() {
        return this.isFrozen();
    }

    // GOALS
    class SumSkeletonRevengeGoal
            extends RevengeGoal {
        public SumSkeletonRevengeGoal() {
            super(IceSkeletonEntity.this);
        }
        @Override
        public void start() {
            super.start();
            if (IceSkeletonEntity.this.isAttacking()) {
                this.callSameTypeForRevenge();
                this.stop();
            }
        }
        @Override
        protected void setMobEntityTarget(MobEntity mob, LivingEntity target) {
            if (mob instanceof SkeletonEntity) {
                super.setMobEntityTarget(mob, target);
            }
        }
    }
    class ProtectHordeGoal
            extends ActiveTargetGoal<LivingEntity> {
        public ProtectHordeGoal() {
            super(IceSkeletonEntity.this, LivingEntity.class, 20, true, true, null);
        }

        @Override
        public boolean canStart() {
            if (super.canStart()) {
                List<IceSkeletonEntity> list = IceSkeletonEntity.this.getWorld().getNonSpectatingEntities(IceSkeletonEntity.class, IceSkeletonEntity.this.getBoundingBox().expand(16.0, 4.0, 16.0));
                for (IceSkeletonEntity iceSkeleton : list) {
                    if (!iceSkeleton.isBaby()) continue;
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
    protected static class SearchAndDestroyGoal
            extends Goal {
        private final IceSkeletonEntity iceSkeleton;
        private final float squaredDistance;
        public final TargetPredicate rangePredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(8.0).ignoreVisibility().ignoreDistanceScalingFactor();

        public SearchAndDestroyGoal(IceSkeletonEntity iceSkeleton, float distance) {
            this.iceSkeleton = iceSkeleton;
            this.squaredDistance = distance * distance;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = this.iceSkeleton.getAttacker();
            return this.iceSkeleton.getTarget() != null && !this.iceSkeleton.isAttacking() &&
                    (livingEntity == null || livingEntity.getType() != EntityType.PLAYER);
        }

        @Override
        public void start() {
            super.start();
            this.iceSkeleton.getNavigation().stop();
            List<IceSkeletonEntity> list = this.iceSkeleton.getWorld().getTargets(IceSkeletonEntity.class,
                    this.rangePredicate, this.iceSkeleton, this.iceSkeleton.getBoundingBox().expand(8.0, 8.0, 8.0));
            for (IceSkeletonEntity iceSkeleton : list) {
                iceSkeleton.setTarget(this.iceSkeleton.getTarget());
            }
        }

        @Override
        public void stop() {
            super.stop();
            LivingEntity livingEntity = this.iceSkeleton.getTarget();
            if (livingEntity != null) {
                List<IceSkeletonEntity> list = this.iceSkeleton.getWorld().getTargets(IceSkeletonEntity.class, this.rangePredicate, this.iceSkeleton,
                        this.iceSkeleton.getBoundingBox().expand(8.0, 8.0, 8.0));
                for (IceSkeletonEntity iceSkeleton : list) {
                    iceSkeleton.setTarget(livingEntity);
                    iceSkeleton.setAttacking(true);
                }
                this.iceSkeleton.setAttacking(true);
            }
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.iceSkeleton.getTarget();
            if (livingEntity == null) {
                return;
            }
            if (this.iceSkeleton.squaredDistanceTo(livingEntity) > (double)this.squaredDistance) {
                this.iceSkeleton.getLookControl().lookAt(livingEntity, 30.0f, 30.0f);
                if (this.iceSkeleton.random.nextInt(50) == 0) {
                    this.iceSkeleton.playAmbientSound();
                }
            } else {
                this.iceSkeleton.setAttacking(true);
            }
            super.tick();
        }
    }

}
