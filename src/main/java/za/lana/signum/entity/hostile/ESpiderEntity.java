/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
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
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.entity.ai.ESpiderAttackGoal;
import za.lana.signum.entity.ai.MonsterFindHomeGoal;
import za.lana.signum.entity.projectile.SpiderSpitEntity;
import za.lana.signum.item.ModItems;
import za.lana.signum.sound.ModSounds;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ESpiderEntity extends TameableEntity implements Mount{
    private int eatingTime;
    @Nullable
    private UUID ownerUuid;

    public int attackAniTimeout = 0;
    public int spitAniTimeout = 0;
    private int idleAniTimeout = 0;
    private int climbAniTimeout = 0;
    public final AnimationState attackAniState = new AnimationState();
    public final AnimationState idleAniState = new AnimationState();
    public final AnimationState climbAniState = new AnimationState();
    public final AnimationState spitAniState = new AnimationState();
    private static final Ingredient BREEDING_INGREDIENT;
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(ESpiderEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SPITTING = DataTracker.registerData(ESpiderEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Byte> ESPIDER_FLAGS = DataTracker.registerData(ESpiderEntity.class, TrackedDataHandlerRegistry.BYTE);

    public ESpiderEntity(EntityType<? extends ESpiderEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        this.setCanPickUpLoot(true);
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
        if (effect.getEffectType() == ModEffects.TIBERIUM_POISON) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }
    //
    public static DefaultAttributeContainer.Builder setAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.5);
    }
    @Override
    protected void initGoals(){
        World level = this.getWorld();

        this.goalSelector.add(1, new ESpiderAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));

        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new ESpiderEntity.ProtectHordeGoal());
        // SPIDERS MUST KILL AND EAT VANILLA ZOMBIES
        this.targetSelector.add(3, new ESpiderEntity.TargetGoal<>(this, ZombieEntity.class));

        if (level.isNight() && !this.isTamed()) {
            this.goalSelector.add(3, new MonsterFindHomeGoal(this, 1.05f, 32));
            this.goalSelector.add(4, new SearchAndDestroyGoal(this, 10.0f));
            this.targetSelector.add(4, new ESpiderEntity.TargetGoal<>(this, PlayerEntity.class));
        }

        this.initCustomGoals();
        this.initCustomTargets();
    }
    protected void initCustomGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new TemptGoal(this, 1.2, BREEDING_INGREDIENT, false));
        this.goalSelector.add(2, new AvoidSunlightGoal(this));
    }
    protected void initCustomTargets() {
        if (!this.isTamed()) {
            this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, true, false,
                    entity -> entity instanceof LivingEntity && entity.getGroup() == ModEntityGroup.TEAM_LIGHT));
        }
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

        super.onTrackedDataSet(data);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(SPITTING, false);
        this.dataTracker.startTracking(ESPIDER_FLAGS, (byte)0);
    }
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putUuid("Owner", this.getOwnerUuid());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        nbt.getUuid("Owner");
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

    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }
    @Nullable
    public ESpiderEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        ESpiderEntity eSpider = ModEntities.ESPIDER_ENTITY.create(serverWorld);
        if (eSpider != null) {
            UUID uUID = this.getOwnerUuid();
            if (uUID != null) {
                eSpider.setOwnerUuid(uUID);
                eSpider.setTamed(true);
            }
        }
        return eSpider;
    }

    private boolean canEat(ItemStack stack) {
        return stack.getItem().isFood() && this.getTarget() == null && this.isOnGround();
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (this.getWorld().isClient) {
            boolean tryEatItem = this.isOwner(player) || this.isTamed() || itemStack.isOf(Items.ROTTEN_FLESH) && !this.isTamed();
            return tryEatItem ? ActionResult.CONSUME : ActionResult.PASS;
        } else {
            ActionResult actionResult;
            {
                if (this.isTamed()) {
                    if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                        if (!player.getAbilities().creativeMode) {
                            itemStack.decrement(1);
                        }
                        this.heal((float) Objects.requireNonNull(item.getFoodComponent()).getHunger());
                        return ActionResult.SUCCESS;
                    }
                    if(this.isOwner(player) && !player.isSneaking() && this.getAttacker() == null) {
                        setRiding(player);
                        return ActionResult.SUCCESS;
                    }
                    actionResult = super.interactMob(player, hand);
                    if (this.isOwner(player) && player.isSneaking() && !actionResult.isAccepted() || this.isBaby()) {
                        this.setSitting(!this.isSitting());
                    }
                    return actionResult;

                } else if (itemStack.isOf(Items.ROTTEN_FLESH)) {
                    if (!player.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                    }
                    if (this.random.nextInt(3) == 0) {
                        this.setOwner(player);
                        this.navigation.stop();
                        this.setTarget(null);
                        this.setSitting(true);
                        this.getWorld().sendEntityStatus(this, (byte)7);
                    } else {
                        this.getWorld().sendEntityStatus(this, (byte)6);
                    }

                    return ActionResult.SUCCESS;
                }
                return super.interactMob(player, hand);
            }
        }
    }

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return (LivingEntity) this.getFirstPassenger();
    }

    private void setRiding(PlayerEntity pPlayer) {
        //this.setInSittingPose(false);
        pPlayer.setYaw(this.getYaw());
        pPlayer.setPitch(this.getPitch());
        pPlayer.startRiding(this);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if(this.hasPassengers() && getControllingPassenger() instanceof PlayerEntity) {
            // TODO ADD JUMPING
            LivingEntity livingentity = this.getControllingPassenger();
            this.setYaw(livingentity.getYaw());
            this.prevYaw = this.getYaw();
            this.setPitch(livingentity.getPitch() * 0.5F);
            this.setRotation(this.getYaw(), this.getPitch());
            this.bodyYaw = this.getYaw();
            this.headYaw = this.bodyYaw;
            float f = livingentity.sidewaysSpeed * 0.5F;
            float f1 = livingentity.forwardSpeed;
            if (f1 <= 0.0F) {
                f1 *= 0.25F;
            }
            if (this.isLogicalSideForUpdatingMovement()) {
                float newSpeed = (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                if(MinecraftClient.getInstance().options.sprintKey.isPressed()) {
                    newSpeed *= 2; // Change this to ~1.5 or so
                }
                this.setMovementSpeed(newSpeed);
                super.travel(new Vec3d(f, movementInput.y, f1));
            }
        } else {
            super.travel(movementInput);
        }
    }
    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Direction direction = this.getMovementDirection();
        if (direction.getAxis() == Direction.Axis.Y) {
            return super.updatePassengerForDismount(passenger);
        }
        int[][] is = Dismounting.getDismountOffsets(direction);
        BlockPos blockPos = this.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (EntityPose entityPose : passenger.getPoses()) {
            Box box = passenger.getBoundingBox(entityPose);
            for (int[] js : is) {
                mutable.set(blockPos.getX() + js[0], blockPos.getY(), blockPos.getZ() + js[1]);
                double d = this.getWorld().getDismountHeight(mutable);
                if (!Dismounting.canDismountInBlock(d)) continue;
                Vec3d vec3d = Vec3d.ofCenter(mutable, d);
                if (!Dismounting.canPlaceEntityAt(this.getWorld(), passenger, box.offset(vec3d))) continue;
                passenger.setPose(entityPose);
                return vec3d;
            }
        }
        return super.updatePassengerForDismount(passenger);
    }
    //
    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        this.dropInventory();
        if ((double)this.random.nextFloat() < 0.45) {
            this.dropItem(ModBlocks.SPIDERWEB_BLOCK);
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

    static {
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
    }

    @Nullable
    public UUID getOwnerUuid() {
        return this.ownerUuid;
    }

    public void setOwnerUuid(@Nullable UUID ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    @Override
    public EntityView method_48926() {
        return this.getWorld();
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