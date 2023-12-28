package za.lana.signum.entity.mob;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.entity.ai.CursedWolfAttackGoal;
import za.lana.signum.item.ModItems;

public class CursedWolfEntity extends AnimalEntity {

    public int attackAniTimeout = 0;
    private int idleAniTimeout = 0;
    public final AnimationState attackAniState = new AnimationState();
    public final AnimationState idleAniState = new AnimationState();
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(CursedWolfEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public CursedWolfEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 3;
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

    @Override
    protected void initGoals(){
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(0, new CursedWolfEscapeDangerGoal(1.3f));
        this.goalSelector.add(1, new CursedWolfAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1D));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 6f));

        this.targetSelector.add(1, new RevengeGoal(this));

        this.initCustomTargets();
    }
    protected void initCustomTargets() {
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, MobEntity.class, 5, true, false,
                entity -> entity instanceof LivingEntity && entity.getGroup() == ModEntityGroup.TEAM_DARK));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }
    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }
    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
    }
    public EntityGroup getGroup() {
        return ModEntityGroup.TEAM_LIGHT;
    }
    @Override
    public boolean isTeammate(Entity other) {
        if (super.isTeammate(other)) {
            return true;
        }
        if (other instanceof LivingEntity && ((LivingEntity)other).getGroup() == ModEntityGroup.TEAM_LIGHT) {
            return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
        }
        return false;
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.8F;
    }
    // SOUND
    protected SoundEvent getAmbientSound() {
        if (this.isAttacking()) {
            return SoundEvents.ENTITY_WOLF_GROWL;
        } else if (this.random.nextInt(3) == 0) {
            return this.getHealth() < 10.0F ? SoundEvents.ENTITY_WOLF_WHINE : SoundEvents.ENTITY_WOLF_PANT;
        } else {
            return SoundEvents.ENTITY_WOLF_AMBIENT;
        }
    }
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        this.dropInventory();
        if ((double)this.random.nextFloat() < 0.75) {
            this.dropItem(ModItems.GOLD_COIN);
        }
        if ((double)this.random.nextFloat() < 0.65) {
            this.dropItem(ModItems.IRON_COIN);
        }
        if ((double)this.random.nextFloat() < 0.55) {
            this.dropItem(ModItems.COPPER_COIN);
        }
        if ((double)this.random.nextFloat() < 0.35) {
            this.dropItem(Items.BONE);
        }
        this.dropItem(Items.ROTTEN_FLESH);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
    // GOALS
    class CursedWolfEscapeDangerGoal extends EscapeDangerGoal {
        public CursedWolfEscapeDangerGoal(double speed) {
            super(CursedWolfEntity.this, speed);
        }
        protected boolean isInDanger() {
            return this.mob.shouldEscapePowderSnow() || this.mob.isOnFire();
        }
    }
}
