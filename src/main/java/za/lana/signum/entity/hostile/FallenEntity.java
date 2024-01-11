/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.entity.ai.FallenAttackGoal;
import za.lana.signum.item.ModItems;
import za.lana.signum.sound.ModSounds;

import java.util.List;

public class FallenEntity extends HostileEntity implements InventoryOwner {
    public int attackAniTimeout = 0;
    private int idleAniTimeout = 0;
    public final AnimationState attackAniState = new AnimationState();
    public final AnimationState idleAniState = new AnimationState();
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(FallenEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final SimpleInventory inventory = new SimpleInventory(6);

    public FallenEntity(EntityType<? extends FallenEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
    }

    public void initGoals(){
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new FallenAttackGoal(this, 1.2D, false));
        //this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, false));
        //this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 6f));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(3, new WanderAroundGoal(this, 1.0));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new FallenEntity.ProtectHordeGoal());
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, true, false,
                entity -> entity instanceof LivingEntity && entity.getGroup() == ModEntityGroup.TEAM_LIGHT));

        //this.initCustomTargets();
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

    // ANIMATIONS

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
            playAttackSound();
        } else {
            --this.attackAniTimeout;
        }
        if(!this.isAttacking()) {
            attackAniState.stop();
            //playFleeSound();
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
        if (effect.getEffectType() == ModEffects.TRANSMUTE_EFFECT) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }
    //

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
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.WOODEN_CLUB));
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
            return new ItemStack(ModItems.SPIKED_CLUB);
        }
        return new ItemStack(ModItems.WOODEN_CLUB);
    }

    @Override
    public SimpleInventory getInventory() {
        return this.inventory;
    }
    @Override
    public boolean canPickupItem(ItemStack stack) {
        return super.canPickupItem(stack);
    }
    // MAKE THE MOB DROP OUR COINS, AND SOME OTHER ITEMS
    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        this.dropInventory();
        if ((double)this.random.nextFloat() < 0.35) {
            this.dropItem(ModItems.IRON_COIN);
        }
        if ((double)this.random.nextFloat() < 0.25) {
            this.dropItem(ModItems.COPPER_COIN);
        }
        if ((double)this.random.nextFloat() < 0.15) {
            this.dropItem(ModItems.GOLD_COIN);
        }
        if ((double)this.random.nextFloat() < 0.10) {
            this.dropItem(ModItems.WOODEN_CLUB);
        }
        if ((double)this.random.nextFloat() < 0.05) {
            this.dropItem(ModItems.SPIKED_CLUB);
        }
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.75f;
    }

    // SOUNDS
    @Override
    protected SoundEvent getAmbientSound() {
        World level = this.getWorld();
        if (level.isDay()){
            if ((double)this.random.nextFloat() < 0.55) {
                return ModSounds.FALLEN_AMBIENT2;
            }
            if ((double)this.random.nextFloat() < 0.25) {
                return ModSounds.FALLEN_AMBIENT3;
            }
            return ModSounds.FALLEN_AMBIENT1;
        }
        if (!level.isDay()){
            if ((double)this.random.nextFloat() < 0.55) {
                return ModSounds.FALLEN_WARCRY2;
            }
            if ((double)this.random.nextFloat() < 0.35) {
                return ModSounds.FALLEN_WARCRY3;
            }
            if ((double)this.random.nextFloat() < 0.25) {
                return ModSounds.FALLEN_WARCRY4;
            }
            return ModSounds.FALLEN_WARCRY1;
        }
        return null;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if ((double)this.random.nextFloat() < 0.55) {
            return ModSounds.FALLEN_HURT2;
        }
        if ((double)this.random.nextFloat() < 0.25) {
            return ModSounds.FALLEN_HURT3;
        }
        return ModSounds.FALLEN_HURT1;
    }
    @Override
    protected SoundEvent getDeathSound() {
        if ((double)this.random.nextFloat() < 0.55) {
            return ModSounds.FALLEN_DEATH2;
        }
        if ((double)this.random.nextFloat() < 0.25) {
            return ModSounds.FALLEN_DEATH3;
        }
        return ModSounds.FALLEN_DEATH1;
    }

    public SoundEvent getAttackSound(){
        if (this.isAttacking()){
            if ((double)this.random.nextFloat() < 0.55) {
                return ModSounds.FALLEN_ATTACK2;
            }
            if ((double)this.random.nextFloat() < 0.25) {
                return ModSounds.FALLEN_ATTACK3;
            }
        }
        return ModSounds.FALLEN_ATTACK1;
    }
    public SoundEvent getFleeSound(){
        if (this.isAttacking()){
            if ((double)this.random.nextFloat() < 0.55) {
                return ModSounds.FALLEN_FLEE2;
            }
            if ((double)this.random.nextFloat() < 0.25) {
                return ModSounds.FALLEN_FLEE3;
            }
        }
        return ModSounds.FALLEN_FLEE1;
    }
    public void playAttackSound() {
        SoundEvent soundEvent = this.getAttackSound();
        if (soundEvent != null) {
            this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
        }
    }
    public void playFleeSound() {
        SoundEvent soundEvent = this.getFleeSound();
        if (soundEvent != null) {
            this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
        }
    }
    // GOALS
    class ProtectHordeGoal
            extends ActiveTargetGoal<LivingEntity> {
        public ProtectHordeGoal() {
            super(FallenEntity.this, LivingEntity.class, 20, true, false, null);
        }

        @Override
        public boolean canStart() {
            if (super.canStart()) {
                if (this.mob.isSleeping()){
                    return false;
                }
                List<FallenEntity> list = FallenEntity.this.getWorld().getNonSpectatingEntities(FallenEntity.class, FallenEntity.this.getBoundingBox().expand(16.0, 4.0, 16.0));
                for (FallenEntity elveGuard : list) {
                    if (!elveGuard.isBaby()) continue;
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
