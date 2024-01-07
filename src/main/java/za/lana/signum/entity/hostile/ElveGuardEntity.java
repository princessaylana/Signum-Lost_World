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
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.item.ModItems;

import java.util.List;

public class ElveGuardEntity extends HostileEntity implements InventoryOwner {
    public int attackAniTimeout = 0;
    private int idleAniTimeout = 0;
    public final AnimationState attackAniState = new AnimationState();
    public final AnimationState idleAniState = new AnimationState();
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(ElveGuardEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final SimpleInventory inventory = new SimpleInventory(6);

    public ElveGuardEntity(EntityType<? extends ElveGuardEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
        ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
    }

    public void initGoals(){
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, false));
        //this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 6f));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(3, new WanderAroundGoal(this, 1.0));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ElveGuardEntity.ProtectHordeGoal());
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, true, false,
                entity -> entity instanceof LivingEntity && entity.getGroup() == ModEntityGroup.TEAM_DARK));

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
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));

        this.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        this.equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
        this.equipStack(EquipmentSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
        this.equipStack(EquipmentSlot.FEET, new ItemStack(Items.IRON_BOOTS));
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        Random random = world.getRandom();
        this.initEquipment(random, difficulty);
        this.updateEnchantments(random, difficulty);
        this.equipStack(EquipmentSlot.MAINHAND, this.makeInitialWeapon());
        this.armorDropChances[EquipmentSlot.HEAD.getEntitySlotId()] = 0.0f;
        this.armorDropChances[EquipmentSlot.CHEST.getEntitySlotId()] = 0.0f;
        this.armorDropChances[EquipmentSlot.LEGS.getEntitySlotId()] = 0.0f;;
        this.armorDropChances[EquipmentSlot.FEET.getEntitySlotId()] = 0.0f;

        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    private ItemStack makeInitialWeapon() {
        if ((double)this.random.nextFloat() < 0.5) {
            return new ItemStack(Items.IRON_SWORD);
        }
        return new ItemStack(ModItems.TIBERIUM_SWORD);
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
        if ((double)this.random.nextFloat() < 0.75) {
            this.dropItem(Items.BREAD);
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

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_EVOKER_AMBIENT;
    }
    // GOALS
    class ProtectHordeGoal
            extends ActiveTargetGoal<LivingEntity> {
        public ProtectHordeGoal() {
            super(ElveGuardEntity.this, LivingEntity.class, 20, true, false, null);
        }

        @Override
        public boolean canStart() {
            if (super.canStart()) {
                if (this.mob.isSleeping()){
                    return false;
                }
                List<ElveGuardEntity> list = ElveGuardEntity.this.getWorld().getNonSpectatingEntities(ElveGuardEntity.class, ElveGuardEntity.this.getBoundingBox().expand(16.0, 4.0, 16.0));
                for (ElveGuardEntity elveGuard : list) {
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
