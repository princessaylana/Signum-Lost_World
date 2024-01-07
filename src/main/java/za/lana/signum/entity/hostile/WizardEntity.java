/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.entity.ai.MonsterFindHomeGoal;
import za.lana.signum.entity.ai.WizardAttackGoal;
import za.lana.signum.entity.ai.WizardSleepGoal;
import za.lana.signum.entity.projectile.SpellBoltEntity;
import za.lana.signum.entity.variant.WizardEntityVariant;
import za.lana.signum.item.ModItems;
import za.lana.signum.sound.ModSounds;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class WizardEntity extends HostileEntity implements InventoryOwner{
    public int attackAniTimeout = 0;
    private int idleAniTimeout = 0;
    public int spellAniTimeout = 0;
    public int sleepAniTimeout = 0;
    //public float prevSleepAnimation;
    public int sleepingAniTimeout = 0;

    public final AnimationState attackAniState = new AnimationState();
    public final AnimationState idleAniState = new AnimationState();
    public final AnimationState spellAniState = new AnimationState();
    public final AnimationState sleepAniState = new AnimationState();
    private final SimpleInventory inventory = new SimpleInventory(6);
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(WizardEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SPELL_SHOOTING = DataTracker.registerData(WizardEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> SLEEPING = DataTracker.registerData(WizardEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT = DataTracker.registerData(WizardEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public final Random random = Random.create();

    public WizardEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 16.0f);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, -1.0f);
        this.getNavigation().setCanSwim(true);
    }

    public void initGoals(){

        this.goalSelector.add(2, new WizardAttackGoal(this, 1.0D, true));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6f));
        //this.goalSelector.add(4, new MonsterFindHomeGoal(this, 1.05f, 32));
        this.goalSelector.add(5, new WanderAroundGoal(this, 1.0));
        //
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new WizardEntity.ProtectHordeGoal());
        //this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, FrogEntity.class, true));

        this.initCustomGoals();
        this.initCustomTargets();

    }
    //
    protected void initCustomGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new WizardSleepGoal(this, 1.2f, 32));
    }

    protected void initCustomTargets() {
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, true, false,
                entity -> entity instanceof LivingEntity && entity.getGroup() == ModEntityGroup.TEAM_DARK));
    }

    public static DefaultAttributeContainer.Builder setAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.32f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.32f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0);
    }
    // ANIMATIONS
    private void setupAnimationStates() {
        if (this.idleAniTimeout <= 0 && !this.isInSleepingPose()) {
            this.sleepAniState.stop();
            this.idleAniTimeout = this.random.nextInt(80) + 160;
            this.idleAniState.start(this.age);
        } else {
            --this.idleAniTimeout;
        }
        // ATTACK
        if(this.isAttacking() && attackAniTimeout <= 0) {
            this.spellAniState.stop();
            this.idleAniState.stop();
            this.sleepAniState.stop();
            //
            this.attackAniTimeout = 40; // 2 seconds, length of spell attack
            this.attackAniState.start(this.age);
        } else {
            --this.attackAniTimeout;
        }
        if(!this.isAttacking()) {
            attackAniState.stop();
        }
        // SPELLCAST
        if(this.isSpellShooting() && spellAniTimeout <= 0) {
            this.attackAniState.stop();
            this.idleAniState.stop();
            this.sleepAniState.stop();
            //
            this.spellAniTimeout = 40;
            this.spellAniState.start(this.age);
        } else {
            --this.spellAniTimeout;
        }
        if(!this.isSpellShooting()) {
            this.spellAniState.stop();
        }
        // SLEEPING isInSleepingPose() ?
        //
        if (this.isInSleepingPose() && sleepAniTimeout <= 0) {
            this.attackAniState.stop();
            this.spellAniState.stop();
            this.idleAniState.stop();

            this.sleepingAniTimeout = 60;
            this.sleepAniState.start(this.age);
            this.playSound(SoundEvents.ENTITY_CAT_PURR, 0.6F + 0.4F * (this.random.nextFloat() - this.random.nextFloat()), 1.0F);
        } else {
            --this.sleepingAniTimeout;
        }
        if(!this.isInSleepingPose()) {
            this.sleepAniState.stop();
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
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.75f;
    }

    // DATA
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING, false);
        this.dataTracker.startTracking(SPELL_SHOOTING, false);
        this.dataTracker.startTracking(SLEEPING, false);
        this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
    }
    // VARIANT

    public WizardEntityVariant getVariant() {
        return WizardEntityVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(WizardEntityVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    // SLEEP
    public void setInSleepingPose(boolean sleeping) {
        this.dataTracker.set(SLEEPING, sleeping);
    }
    public boolean isInSleepingPose() {
        return this.dataTracker.get(SLEEPING);
    }
    public void setAttacking(boolean attacking) {
        this.dataTracker.set(ATTACKING, attacking);
    }
    public void setSpellShooting(boolean spellShooting) {
        this.dataTracker.set(SPELL_SHOOTING, spellShooting);
    }
    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING);
    }
    public boolean isSpellShooting() {
        return this.dataTracker.get(SPELL_SHOOTING);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.writeInventory(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.readInventory(nbt);
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
    }

    // EQUIPMENT
    @Override
    public SimpleInventory getInventory() {
        return inventory;
    }
    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        //this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModItems.TRANSMUTE_STAFF));
    }
    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        Random random = world.getRandom();
        WizardEntityVariant variant = Util.getRandom(WizardEntityVariant.values(), this.random);
        setVariant(variant);
        this.initEquipment(random, difficulty);
        this.updateEnchantments(random, difficulty);
        this.equipStack(EquipmentSlot.MAINHAND, this.makeInitialWeapon());
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
    private ItemStack makeInitialWeapon() {
        return new ItemStack(Items.IRON_SWORD);
    }
    // MAKE THE MOB DROP OUR COINS, AND SOME OTHER ITEMS
    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        this.dropInventory();
        if ((double)this.random.nextFloat() < 0.75) {
            this.dropItem(Items.BREAD);
        }
        if ((double)this.random.nextFloat() < 0.65) {
            this.dropItem(Items.BONE);
        }
        if ((double)this.random.nextFloat() < 0.55) {
            this.dropItem(Items.LEATHER_HELMET);
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
    // GENERAL
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
    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if (effect.getEffectType() == ModEffects.TRANSMUTE_EFFECT) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }

    // SOUND WIP
    @Override
    protected SoundEvent getAmbientSound() {
        if (!this.isInSleepingPose()){
            return SoundEvents.ENTITY_WITCH_AMBIENT;
        }
        return null;
    }
    //

    public void spellCast(LivingEntity target) {
        if (this.canSee(target)) {
            World level = this.getEntityWorld();
            SpellBoltEntity spellBolt = new SpellBoltEntity(level, this);
            double d = target.getX() - this.getX();
            double e = target.getBodyY(0.3333333333333333) - spellBolt.getY();
            double f = target.getZ() - this.getZ();
            double g = Math.sqrt(d * d + f * f) * 0.20000000298023224;
            spellBolt.setVelocity(d, e + g, f, 1.5F, 10.0F);
            if (!this.isSilent()) {
                this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.TIBERIUM_HIT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            }
            this.getWorld().spawnEntity(spellBolt);
            this.playSound(ModSounds.TIBERIUM_HIT, 0.15F, 1.0F);
        }
    }

    // GOALS
    protected static class NavDoorInteractGoal
            extends LongDoorInteractGoal {
        public NavDoorInteractGoal(WizardEntity wizard) {
            super(wizard, false);
        }
        @Override
        public boolean canStart() {
            //
            return super.canStart();
        }
    }
    class ProtectHordeGoal
            extends ActiveTargetGoal<LivingEntity> {
        public ProtectHordeGoal() {
            super(WizardEntity.this, LivingEntity.class, 20, true, false, Objects::nonNull);
        }

        @Override
        public boolean canStart() {
            if (super.canStart()) {
                if (this.mob.isSleeping()){
                    return false;
                }
                List<WizardEntity> list = WizardEntity.this.getWorld().getNonSpectatingEntities(WizardEntity.class, WizardEntity.this.getBoundingBox().expand(16.0, 4.0, 16.0));
                for (WizardEntity wizard : list) {
                    if (!wizard.isBaby()) continue;
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
        private final WizardEntity wizard;
        private final float squaredDistance;
        public final TargetPredicate rangePredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(8.0).ignoreVisibility().ignoreDistanceScalingFactor();

        public SearchAndDestroyGoal(WizardEntity wizard, float distance) {
            this.wizard = wizard;
            this.squaredDistance = distance * distance;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = this.wizard.getAttacker();
            return this.wizard.getTarget() != null && !this.wizard.isAttacking() &&
                    (livingEntity == null || livingEntity.getType() != EntityType.PLAYER);
        }

        @Override
        public void start() {
            super.start();
            this.wizard.getNavigation().stop();
            List<WizardEntity> list = this.wizard.getWorld().getTargets(WizardEntity.class,
                    this.rangePredicate, this.wizard, this.wizard.getBoundingBox().expand(8.0, 8.0, 8.0));
            for (WizardEntity wizard : list) {
                wizard.setTarget(this.wizard.getTarget());
            }
        }

        @Override
        public void stop() {
            super.stop();
            LivingEntity livingEntity = this.wizard.getTarget();
            if (livingEntity != null) {
                List<WizardEntity> list = this.wizard.getWorld().getTargets(WizardEntity.class, this.rangePredicate, this.wizard,
                        this.wizard.getBoundingBox().expand(8.0, 8.0, 8.0));
                for (WizardEntity wizard : list) {
                    wizard.setTarget(livingEntity);
                    wizard.setAttacking(true);
                }
                this.wizard.setAttacking(true);
            }
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.wizard.getTarget();
            if (livingEntity == null) {
                return;
            }
            if (this.wizard.squaredDistanceTo(livingEntity) > (double)this.squaredDistance) {
                this.wizard.getLookControl().lookAt(livingEntity, 30.0f, 30.0f);
                if (this.wizard.random.nextInt(50) == 0) {
                    this.wizard.playAmbientSound();
                }
            } else {
                this.wizard.setAttacking(true);
            }
            super.tick();
        }
    }
}
