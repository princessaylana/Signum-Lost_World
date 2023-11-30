/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.SpiderNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.entity.ai.AnimalFindHomeGoal;
import za.lana.signum.entity.ai.ESpiderAttackGoal;
import za.lana.signum.entity.ai.MonsterFindHomeGoal;
import za.lana.signum.entity.projectile.SpiderSpitEntity;
import za.lana.signum.item.ModItems;
import za.lana.signum.sound.ModSounds;

import java.util.EnumSet;
import java.util.List;

public class ESpiderEntity extends HostileEntity implements ItemSteerable, Saddleable, RangedAttackMob {
    private int eatingTime;
    public int attackAniTimeout = 0;
    public int spitAniTimeout = 0;
    private int idleAniTimeout = 0;
    private int climbAniTimeout = 0;
    public final AnimationState attackAniState = new AnimationState();
    public final AnimationState idleAniState = new AnimationState();
    public final AnimationState climbAniState = new AnimationState();
    public final AnimationState spitAniState = new AnimationState();
    private static final Ingredient BREEDING_INGREDIENT;
    private static final TrackedData<Integer> BOOST_TIME;
    private static final TrackedData<Boolean> SADDLED;
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(ESpiderEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SPITTING = DataTracker.registerData(ESpiderEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Byte> ESPIDER_FLAGS = DataTracker.registerData(ESpiderEntity.class, TrackedDataHandlerRegistry.BYTE);

    //TODO Saddle needs finishing
    private final SaddledComponent saddledComponent;
    public ESpiderEntity(EntityType<? extends ESpiderEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        this.setCanPickUpLoot(true);
        this.saddledComponent = new SaddledComponent(this.dataTracker, BOOST_TIME, SADDLED);
    }

    private void setupAnimationStates() {
        if (this.idleAniTimeout <= 0) {
            this.idleAniTimeout = this.random.nextInt(40) + 80;
            //this.resetAttackStates();
            this.idleAniState.start(this.age);
        } else {
            --this.idleAniTimeout;
        }
        // ATTACK
        if(this.isAttacking() && attackAniTimeout <= 0) {
            this.spitAniState.stop();
            //
            this.attackAniTimeout = 40; // 2 seconds, length of spider attack
            this.attackAniState.start(this.age);
        } else {
            --this.attackAniTimeout;
        }
        if(!this.isAttacking()) {
            attackAniState.stop();
        }
        // SPITTING
        if(this.isSpitting() && spitAniTimeout <= 0) {
            this.attackAniState.stop();
            this.spitAniTimeout = 40;
            this.spitAniState.start(this.age);
        } else {
            --this.spitAniTimeout;
        }
        if(!this.isSpitting()) {
            this.spitAniState.stop();
        }
        //
    }
    private void resetAttackStates(){
        this.setAttacking(false);
        this.setSpitting(false);
        this.attackAniState.stop();
        this.spitAniState.stop();
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
        if (!this.getWorld().isClient) {
            this.setClimbingWall(this.horizontalCollision);
        }
        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }
    @Override
    protected void initGoals(){

        this.goalSelector.add(1, new ESpiderAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(3, new SearchAndDestroyGoal(this, 10.0f));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new MonsterFindHomeGoal(this, 1.05f, 1));

        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new ESpiderEntity.ProtectHordeGoal());
        // SPIDERS MUST KILL AND EAT VANILLA ZOMBIES
        this.targetSelector.add(3, new ESpiderEntity.TargetGoal<>(this, ZombieEntity.class));
        this.targetSelector.add(4, new ESpiderEntity.TargetGoal<>(this, PlayerEntity.class));
        // TESTING
        this.targetSelector.add(5, new ESpiderEntity.TargetGoal<>(this, SlimeEntity.class));

        this.initCustomGoals();
    }
    protected void initCustomGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new TemptGoal(this, 1.2, Ingredient.ofItems(ModItems.ROTTEN_FLESH_ON_A_STICK), false));
        this.goalSelector.add(2, new TemptGoal(this, 1.2, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new AvoidSunlightGoal(this));
    }

    public static DefaultAttributeContainer.Builder setAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.5);
    }
    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }
    public void setSpitting(boolean spit) {
        this.dataTracker.set(SPITTING, spit);
    }
    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }
    public boolean isSpitting() {
        return this.dataTracker.get(SPITTING);
    }
    @Override
    protected EntityNavigation createNavigation(World world) {
        return new SpiderNavigation(this, world);
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        if (BOOST_TIME.equals(data) && this.getWorld().isClient) {
            this.saddledComponent.boost();
        }
        super.onTrackedDataSet(data);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(SPITTING, false);
        this.dataTracker.startTracking(ESPIDER_FLAGS, (byte)0);
        this.dataTracker.startTracking(BOOST_TIME, 0);
        this.dataTracker.startTracking(SADDLED, false);
    }
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.saddledComponent.writeNbt(nbt);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.saddledComponent.readNbt(nbt);
    }

    // SOUNDS
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }
    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15f, 1.0f);
    }
    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.2f;
    }

    // CLIMBING & MOVEMENT
    private void playClimbAnimation(){
        if(this.isClimbing() && climbAniTimeout <= 0) {
            this.resetAttackStates();
            this.climbAniTimeout = 40;
            this.climbAniState.start(this.age);
        } else {
            --this.climbAniTimeout;
        }
        if(!this.isClimbing()) {
            this.climbAniState.stop();
        }
    }

    @Override
    public boolean isClimbing() {
        return this.isClimbingWall();
    }
    public boolean isClimbingWall() {
        return (this.dataTracker.get(ESPIDER_FLAGS) & 1) != 0;
    }
    public void setClimbingWall(boolean climbing) {
        byte b = this.dataTracker.get(ESPIDER_FLAGS);
        b = climbing ? (byte)(b | 1) : (byte)(b & 0xFFFFFFFE);

        this.dataTracker.set(ESPIDER_FLAGS, b);
        this.playClimbAnimation();

    }

    @Override
    public void slowMovement(BlockState state, Vec3d multiplier) {
        if (!state.isOf(ModBlocks.SPIDERWEB_BLOCK)) {
            super.slowMovement(state, multiplier);
        }
    }
    //public boolean isIdle() {return this.currentPath == null || this.currentPath.isFinished();}

    public void tickMovement() {
        if (!this.getWorld().isClient && this.isAlive() && this.canMoveVoluntarily()) {
            ++this.eatingTime;
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.MAINHAND);
            if (this.canEat(itemStack)) {
                if (this.eatingTime > 600) {
                    ItemStack itemStack2 = itemStack.finishUsing(this.getWorld(), this);
                    if (!itemStack2.isEmpty()) {
                        this.equipStack(EquipmentSlot.MAINHAND, itemStack2);
                    }
                    this.eatingTime = 0;
                } else if (this.eatingTime > 560 && this.random.nextFloat() < 0.1F) {
                    this.playSound(this.getEatSound(itemStack), 1.0F, 1.0F);
                    this.getWorld().sendEntityStatus(this, (byte)45);
                    boolean bl = this.getHealth() < this.getMaxHealth();
                    if (bl) {
                        this.heal(2.0F);
                    }
                }
            }
        }
        super.tickMovement();
    }

    @Nullable
    public ESpiderEntity createChild(ServerWorld server, HostileEntity hostile) {
        return null;
    }
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        boolean bl = this.isBreedingItem(player.getStackInHand(hand));
        if (!bl && this.isAlive() && !this.hasPassengers() && !player.shouldCancelInteraction()) {
            if (!this.getWorld().isClient) {
                player.startRiding(this);
            }
            return ActionResult.success(this.getWorld().isClient);
        }
        return super.interactMob(player, hand);
    }

    // SADDLE
    //TODO Need to fix the saddle feature properly and change from itemsteerable to mount and player controls.
    public boolean isSaddled() {
        return this.saddledComponent.isSaddled();
    }
    public boolean canBeSaddled() {
        return this.isAlive();
    }
    public void saddle(@Nullable SoundCategory sound) {
        this.saddledComponent.setSaddled(true);
        if (sound != null) {
            this.getWorld().playSoundFromEntity(null, this, SoundEvents.ENTITY_STRIDER_SADDLE, sound, 0.5F, 1.0F);
        }
    }
    @Nullable
    public LivingEntity getControllingPassenger() {
        Entity var2 = this.getFirstPassenger();
        if (var2 instanceof PlayerEntity playerEntity) {
            if (playerEntity.getMainHandStack().isOf(ModItems.ROTTEN_FLESH_ON_A_STICK) ||
                    playerEntity.getOffHandStack().isOf(ModItems.ROTTEN_FLESH_ON_A_STICK)) {
                return playerEntity;
            }
        }
        return null;
    }
    public boolean canBeControlledByPlayer() {
        return getControllingPassenger() instanceof PlayerEntity;
    }
    @Override
    public boolean isLogicalSideForUpdatingMovement() {
        if (canBeControlledByPlayer()) return true;
        return this.canMoveVoluntarily();
    }
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Direction direction = this.getMovementDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.updatePassengerForDismount(passenger);
        } else {
            int[][] is = Dismounting.getDismountOffsets(direction);
            BlockPos blockPos = this.getBlockPos();
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (EntityPose entityPose : passenger.getPoses()) {
                Box box = passenger.getBoundingBox(entityPose);
                int var10 = is.length;

                for (int[] js : is) {
                    mutable.set(blockPos.getX() + js[0], blockPos.getY(), blockPos.getZ() + js[1]);
                    double d = this.getWorld().getDismountHeight(mutable);
                    if (Dismounting.canDismountInBlock(d)) {
                        Vec3d vec3d = Vec3d.ofCenter(mutable, d);
                        if (Dismounting.canPlaceEntityAt(this.getWorld(), passenger, box.offset(vec3d))) {
                            passenger.setPose(entityPose);
                            return vec3d;
                        }
                    }
                }
            }
            return super.updatePassengerForDismount(passenger);
        }
    }
    protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
        super.tickControlled(controllingPlayer, movementInput);
        this.setRotation(controllingPlayer.getYaw(), controllingPlayer.getPitch() * 0.5F);
        this.prevYaw = this.bodyYaw = this.headYaw = this.getYaw();
        super.tickControlled(controllingPlayer, movementInput);
    }
    protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
        return new Vec3d(0.0, 0.0, 1.0);
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
    }
    @Override
    public boolean consumeOnAStickItem() {
        return this.saddledComponent.boost(this.getRandom());
    }
    private boolean canEat(ItemStack stack) {
        return stack.getItem().isFood() && this.getTarget() == null && this.isOnGround();
    }
    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        ESpiderEntity eSpider;
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        Entity entity = source.getAttacker();
        this.dropItem(Items.STRING);
    }
    static {
        SADDLED = DataTracker.registerData(ESpiderEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BOOST_TIME = DataTracker.registerData(ESpiderEntity.class, TrackedDataHandlerRegistry.INTEGER);
        BREEDING_INGREDIENT = Ingredient.ofItems(Items.ROTTEN_FLESH);
    }

    // RANGED ATTACK
    public void spitAt(LivingEntity target) {
        World level = this.getEntityWorld();
        SpiderSpitEntity spiderSpit = new SpiderSpitEntity(level, this);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - spiderSpit.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f) * 0.20000000298023224;
        spiderSpit.setVelocity(d, e + g, f, 1.5F, 10.0F);
        if (!this.isSilent()) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.TIBERIUM_HIT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }
        this.getWorld().spawnEntity(spiderSpit);
        // Done in Goal
        //this.setSpitting(true);

    }
    public void shootAt(LivingEntity target, float pullProgress) {
        this.spitAt(target);
    }

    // GOALS
    protected static class SearchAndDestroyGoal
            extends Goal {
        private final ESpiderEntity eSpider;
        private final float squaredDistance;
        public final TargetPredicate closeSpiderPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(8.0).ignoreVisibility().ignoreDistanceScalingFactor();

        public SearchAndDestroyGoal(ESpiderEntity eSpider, float distance) {
            this.eSpider = eSpider;
            this.squaredDistance = distance * distance;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = this.eSpider.getAttacker();
            return this.eSpider.getTarget() != null && !this.eSpider.isAttacking() &&
                    (livingEntity == null || livingEntity.getType() != EntityType.PLAYER);
        }

        @Override
        public void start() {
            super.start();
            this.eSpider.getNavigation().stop();
            List<ESpiderEntity> list = this.eSpider.getWorld().getTargets(ESpiderEntity.class, this.closeSpiderPredicate, this.eSpider, this.eSpider.getBoundingBox().expand(8.0, 8.0, 8.0));
            for (ESpiderEntity eSpider : list) {
                eSpider.setTarget(this.eSpider.getTarget());
            }
        }

        @Override
        public void stop() {
            super.stop();
            LivingEntity livingEntity = this.eSpider.getTarget();
            if (livingEntity != null) {
                List<ESpiderEntity> list = this.eSpider.getWorld().getTargets(ESpiderEntity.class, this.closeSpiderPredicate, this.eSpider,
                        this.eSpider.getBoundingBox().expand(8.0, 8.0, 8.0));
                for (ESpiderEntity eSpider : list) {
                    eSpider.setTarget(livingEntity);
                    eSpider.setAttacking(true);
                }
                this.eSpider.setAttacking(true);
            }
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.eSpider.getTarget();
            if (livingEntity == null) {
                return;
            }
            if (this.eSpider.squaredDistanceTo(livingEntity) > (double)this.squaredDistance) {
                this.eSpider.getLookControl().lookAt(livingEntity, 30.0f, 30.0f);
                if (this.eSpider.random.nextInt(50) == 0) {
                    this.eSpider.playAmbientSound();
                }
            } else {
                this.eSpider.setAttacking(true);
            }
            super.tick();
        }
    }
    static class TargetGoal<T extends LivingEntity>
            extends ActiveTargetGoal<T> {
        public TargetGoal(ESpiderEntity eSpider, Class<T> targetEntityClass) {
            super(eSpider, targetEntityClass, true);

        }

        @Override
        public boolean canStart() {
            float f = this.mob.getBrightnessAtEyes();
            if (f >= 0.5f) {
                return false;
            }
            if (this.target instanceof ESpiderEntity){
                return false;
            }
            return super.canStart();
        }
    }
    class ProtectHordeGoal
            extends ActiveTargetGoal<LivingEntity> {
        public ProtectHordeGoal() {
            super(ESpiderEntity.this, LivingEntity.class, 20, true, true, null);
        }

        @Override
        public boolean canStart() {
            if (super.canStart()) {
                List<ESpiderEntity> list = ESpiderEntity.this.getWorld().getNonSpectatingEntities(ESpiderEntity.class, ESpiderEntity.this.getBoundingBox().expand(16.0, 4.0, 16.0));
                for (ESpiderEntity eSpider : list) {
                    if (eSpider.isAlive() && !eSpider.hasPassengers()) continue;
                    return true;
                }
                if (this.target instanceof ESpiderEntity){
                    return false;
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