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
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.entity.ai.EnderSkeletonAttackGoal;
import za.lana.signum.item.ModItems;
import za.lana.signum.sound.ModSounds;

import java.util.EnumSet;
import java.util.List;

public class EnderSkeletonEntity extends HostileEntity implements InventoryOwner {
    public int attackAniTimeout = 0;
    private int idleAniTimeout = 0;
    private int ageTarget;
    public int cooldown;
    public final AnimationState attackAniState = new AnimationState();
    public final AnimationState idleAniState = new AnimationState();
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(EnderSkeletonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> TELEPORTING = DataTracker.registerData(EnderSkeletonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final SimpleInventory inventory = new SimpleInventory(6);

    public EnderSkeletonEntity(EntityType<? extends EnderSkeletonEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
    }

    public void initGoals(){
        this.goalSelector.add(2, new AvoidSunlightGoal(this));
        this.goalSelector.add(3, new EscapeSunlightGoal(this, 1.0));
        this.goalSelector.add(4, new EnderSkeletonAttackGoal(this, 1.2D, false));
        this.goalSelector.add(4, new WanderAroundGoal(this, 1.0));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(5, new EnderSkeletonEntity.EnderSkeletonRevengeGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new EnderSkeletonEntity.ProtectHordeGoal());
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        //this.targetSelector.add(3, new ActiveTargetGoal<>(this, VillagerEntity.class, true));
        //
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, true, false,
                entity -> entity instanceof LivingEntity && entity.getGroup() == ModEntityGroup.TEAM_LIGHT));

        this.initCustomGoals();
        //this.initCustomTargets();
    }
    protected void initCustomGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
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
        this.dataTracker.startTracking(TELEPORTING, false);
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
    //
    public EntityGroup getGroup() {
        return ModEntityGroup.TEAM_DARK;
    }
    @Override
    public boolean isTeammate(Entity other) {
        if (super.isTeammate(other)) {
            return true;
        }
        if (other instanceof LivingEntity && ((LivingEntity)other).getGroup() == ModEntityGroup.TEAM_DARK) {
            return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
        }
        return false;
    }
    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if (effect.getEffectType() == ModEffects.GRAVITY_EFFECT) {
            return false;
        }
        if (effect.getEffectType() == ModEffects.HEALING_EFFECT) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }
    //

    public void setTeleporting(boolean teleport) {
        this.dataTracker.set(TELEPORTING, teleport);
    }
    public boolean isTeleporting() {
        return this.dataTracker.get(TELEPORTING);
    }

    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }
    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
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
            return new ItemStack(ModItems.PLASMA_SWORD);
        }
        return new ItemStack(Items.IRON_SWORD);
    }

    // BURNS IN DAYTIME
    @Override
    public void tickMovement() {
        if (this.getWorld().isClient) {
            for (int i = 0; i < 2; ++i) {
                this.getWorld().addParticle(ParticleTypes.PORTAL,
                        this.getParticleX(0.5),
                        this.getRandomBodyY() - 0.50,
                        this.getParticleZ(0.5),
                        (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(),
                        (this.random.nextDouble() - 0.5) * 2.0);
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
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        this.dropInventory();
        if ((double)this.random.nextFloat() < 0.75) {
            this.dropItem(ModItems.ELEMENT_ZERO_DUST);
        }
        if ((double)this.random.nextFloat() < 0.65) {
            this.dropItem(Items.BONE);
        }
        if ((double)this.random.nextFloat() < 0.55) {
            this.dropItem(Items.IRON_SWORD);
        }
        if ((double)this.random.nextFloat() < 0.35) {
            this.dropItem(ModItems.IRON_COIN);
        }
        if ((double)this.random.nextFloat() < 0.25) {
            this.dropItem(ModItems.COPPER_COIN);
        }
        if ((double)this.random.nextFloat() < 0.15) {
            this.dropItem(ModItems.GOLD_COIN);
        }
        //this.dropItem(Items.ROTTEN_FLESH);
    }
    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.75f;
    }

    // SKELETON SOUNDS
    @Override
    protected SoundEvent getAmbientSound() {
        //return SoundEvents.ENTITY_SKELETON_AMBIENT;
        return null;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if ((double)this.random.nextFloat() < 0.55) {
            return ModSounds.SKELETON_HURT2;
        }
        if ((double)this.random.nextFloat() < 0.25) {
            return ModSounds.SKELETON_HURT3;
        }
        return ModSounds.SKELETON_HURT1;
    }
    @Override
    protected SoundEvent getDeathSound() {
        if ((double)this.random.nextFloat() < 0.55) {
            return ModSounds.SKELETON_DEATH2;
        }
        if ((double)this.random.nextFloat() < 0.25) {
            return ModSounds.SKELETON_DEATH3;
        }
        return ModSounds.SKELETON_DEATH1;
    }
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 0.15f, 1.0f);
    }
    SoundEvent getStepSound() {
        if ((double)this.random.nextFloat() < 0.55) {
            return ModSounds.SKELETON_WALK2;
        }
        if ((double)this.random.nextFloat() < 0.25) {
            return ModSounds.SKELETON_WALK3;
        }
        return ModSounds.SKELETON_WALK1;
    }
    //

    public boolean isShaking() {
        return this.isFrozen();
    }

    // TELEPORT
    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        boolean damage = super.damage(source, amount);
        if (!this.getWorld().isClient() && !(source.getAttacker() instanceof LivingEntity) && this.random.nextInt(10) != 0) {
            this.teleportRandomly();
        }
        return damage;
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        if (target == null) {
            this.ageTarget = 0;
        }
        else {
            this.ageTarget = this.age;
            //this.teleportToPos(target);
            //entity.setTeleporting(true);
        }
    }

    @Override
    protected void mobTick() {
        if (this.getWorld().isDay() && this.age >= this.ageTarget + 600 && this.getWorld().isSkyVisible(this.getBlockPos())) {
            this.setTarget(null);
            this.teleportRandomly();
        }
        super.mobTick();
    }
    protected void teleportRandomly() {
        if (this.getWorld().isClient() || !this.isAlive()) {
            return;
        }
        double d = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
        double e = this.getY() + (double)(this.random.nextInt(64) - 32);
        double f = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
        this.teleportToPos(d, e, f);
    }
    public void teleportToPos(Entity entity) {


        Vec3d vec3d = new Vec3d(this.getX() - entity.getX(), this.getBodyY(0.5) - entity.getEyeY(), this.getZ() - entity.getZ());
        vec3d = vec3d.normalize();
        double d = 16.0;
        double e = this.getX() + (this.random.nextDouble() - 0.5) * 8.0 - vec3d.x * 16.0;
        double f = this.getY() + (double)(this.random.nextInt(16) - 8) - vec3d.y * 16.0;
        double g = this.getZ() + (this.random.nextDouble() - 0.5) * 8.0 - vec3d.z * 16.0;
        this.teleportToPos(e, f, g);
    }
    private void teleportToPos(double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
        while (mutable.getY() > this.getWorld().getBottomY() && !this.getWorld().getBlockState(mutable).blocksMovement()) {
            mutable.move(Direction.DOWN);
        }
        BlockState blockState = this.getWorld().getBlockState(mutable);
        boolean bl = blockState.blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (!bl || bl2) {
            return;
        }
        Vec3d vec3d = this.getPos();
        boolean bl3 = this.teleport(x, y, z, true);
        if (bl3) {
            this.getWorld().emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(this));
            if (!this.isSilent()) {
                this.getWorld().playSound(null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0f, 1.0f);
                this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
        }
    }

    // GOALS
    class EnderSkeletonRevengeGoal
            extends RevengeGoal {

        public EnderSkeletonRevengeGoal(EnderSkeletonEntity enderSkeleton) {
            super(EnderSkeletonEntity.this);
        }

        @Override
        public void start() {
            super.start();
            if (EnderSkeletonEntity.this.isAttacking()) {
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
            super(EnderSkeletonEntity.this, LivingEntity.class, 20, true, true, null);
        }

        @Override
        public boolean canStart() {
            if (super.canStart()) {
                List<EnderSkeletonEntity> list = EnderSkeletonEntity.this.getWorld().getNonSpectatingEntities(EnderSkeletonEntity.class, EnderSkeletonEntity.this.getBoundingBox().expand(16.0, 4.0, 16.0));
                for (EnderSkeletonEntity enderSkeleton : list) {
                    if (!enderSkeleton.isBaby()) continue;
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

    // WORK IN PROGRESS
    protected static class SearchAndDestroyGoal
            extends Goal {
        private final EnderSkeletonEntity enderSkeleton;
        private final float squaredDistance;
        public final TargetPredicate rangePredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(8.0).ignoreVisibility().ignoreDistanceScalingFactor();

        public SearchAndDestroyGoal(EnderSkeletonEntity enderSkeleton, float distance) {
            this.enderSkeleton = enderSkeleton;
            this.squaredDistance = distance * distance;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = this.enderSkeleton.getAttacker();
            return this.enderSkeleton.getTarget() != null && !this.enderSkeleton.isAttacking() &&
                    (livingEntity == null || livingEntity.getType() != EntityType.PLAYER);
        }

        @Override
        public void start() {
            super.start();
            this.enderSkeleton.getNavigation().stop();
            List<EnderSkeletonEntity> list = this.enderSkeleton.getWorld().getTargets(EnderSkeletonEntity.class,
                    this.rangePredicate, this.enderSkeleton, this.enderSkeleton.getBoundingBox().expand(8.0, 8.0, 8.0));
            for (EnderSkeletonEntity enderSkeleton : list) {
                enderSkeleton.setTarget(this.enderSkeleton.getTarget());
            }
        }

        @Override
        public void stop() {
            super.stop();
            LivingEntity livingEntity = this.enderSkeleton.getTarget();
            if (livingEntity != null) {
                List<EnderSkeletonEntity> list = this.enderSkeleton.getWorld().getTargets(EnderSkeletonEntity.class, this.rangePredicate, this.enderSkeleton,
                        this.enderSkeleton.getBoundingBox().expand(8.0, 8.0, 8.0));
                for (EnderSkeletonEntity enderSkeleton : list) {
                    enderSkeleton.setTarget(livingEntity);
                    enderSkeleton.setAttacking(true);
                }
                this.enderSkeleton.setAttacking(true);
            }
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.enderSkeleton.getTarget();
            if (livingEntity == null) {
                return;
            }
            if (this.enderSkeleton.squaredDistanceTo(livingEntity) > (double)this.squaredDistance) {
                this.enderSkeleton.getLookControl().lookAt(livingEntity, 30.0f, 30.0f);
                if (this.enderSkeleton.random.nextInt(50) == 0) {
                    this.enderSkeleton.playAmbientSound();
                }
            } else {
                this.enderSkeleton.setAttacking(true);
            }
            super.tick();
        }
    }

}
