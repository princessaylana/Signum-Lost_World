/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.entity.*;
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
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import za.lana.signum.sound.ModSounds;

import java.util.List;

public class TCommanderEntity extends HostileEntity implements InventoryOwner {
    public int attackAniTimeout = 0;
    private int idleAniTimeout = 0;
    public final AnimationState attackAniState = new AnimationState();
    public final AnimationState idleAniState = new AnimationState();
    private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(TCommanderEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final SimpleInventory inventory = new SimpleInventory(5);

    public TCommanderEntity(EntityType<? extends TCommanderEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }

    public void initGoals(){
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 6f));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(4, new WanderAroundGoal(this, 1.0));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new TCommanderEntity.ProtectHordeGoal());
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, ZombieEntity.class, true));

    }

    public static DefaultAttributeContainer.Builder setAttributes(){
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.25f)
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
        return ModEntityGroup.TIBERIUM;
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
            return new ItemStack(Items.CROSSBOW);
        }
        return new ItemStack(Items.IRON_SWORD);
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

    @Override
    public SimpleInventory getInventory() {
        return this.inventory;
    }
    @Override
    public boolean canPickupItem(ItemStack stack) {
        return super.canPickupItem(stack);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.75f;
    }

   // protected SoundEvent getAmbientSound() {return ModSounds.TCOMMANDER_IDLE;}
    protected SoundEvent getDeathSound() {
        return ModSounds.TCOMMANDER_DIE;
    }
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.TCOMMANDER_HURT2;
    }

    // GOALS
    class ProtectHordeGoal
            extends ActiveTargetGoal<PlayerEntity> {
        public ProtectHordeGoal() {
            super(TCommanderEntity.this, PlayerEntity.class, 20, true, true, null);
        }

        @Override
        public boolean canStart() {
            if (super.canStart()) {
                List<TCommanderEntity> list = TCommanderEntity.this.getWorld().getNonSpectatingEntities(TCommanderEntity.class, TCommanderEntity.this.getBoundingBox().expand(16.0, 4.0, 16.0));
                for (TCommanderEntity tCommanderEntity : list) {
                    if (!tCommanderEntity.isBaby()) continue;
                    //
                    World world = this.mob.getWorld();
                    world.playSound(null, this.mob.getBlockPos(), ModSounds.TCOMMANDER_HURT, null, 1.0f, 1.0f);
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