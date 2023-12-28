/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.block.custom.props.BlightBlock;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntityGroup;
import za.lana.signum.entity.ai.TiberiumWormAttackGoal;
import za.lana.signum.item.ModItems;

import java.util.EnumSet;
import java.util.List;

public class TiberiumWormEntity extends HostileEntity implements InventoryOwner{

	private int eatingTime;
	public int attackAniTimeout = 0;
	private int idleAniTimeout = 0;
	public final net.minecraft.entity.AnimationState attackAniState = new net.minecraft.entity.AnimationState();
	public final net.minecraft.entity.AnimationState idleAniState = new AnimationState();
	private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(TiberiumWormEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public TiberiumWormEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.experiencePoints = 3;
		this.setCanPickUpLoot(true);
		((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
	}

	public static DefaultAttributeContainer.Builder setAttributes() {
		return AnimalEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0f)
				.add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.250);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new SwimGoal(this));
		this.goalSelector.add(2, new AvoidSunlightGoal(this));
		this.goalSelector.add(3, new TiberiumWormAttackGoal(this, 1.0D, true));
		this.goalSelector.add(4, new WanderAroundGoal(this, 1.0));
		this.goalSelector.add(5, new LookAroundGoal(this));
		this.goalSelector.add(6, new WanderAndInfestGoal(this));

		this.targetSelector.add(1, new TiberiumWormEntity.TiberiumWormRevengeGoal());
		this.targetSelector.add(2, new TiberiumWormEntity.ProtectHordeGoal());
		this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(4, new ActiveTargetGoal<>(this, FrogEntity.class, true));
		this.targetSelector.add(5, new ActiveTargetGoal<>(this, SlimeEntity.class, true));

		this.initCustomTargets();
	}

	protected void initCustomTargets() {
		this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, true, false,
				entity -> entity instanceof LivingEntity && entity.getGroup() == ModEntityGroup.TEAM_LIGHT));
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
	// EATING
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
	private boolean canEat(ItemStack stack) {
		return stack.getItem().isFood() && this.getTarget() == null && this.isOnGround();
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
		if (effect.getEffectType() == ModEffects.HEALING_EFFECT) {
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
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ATTACKING, false);
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
			this.dropItem(ModItems.TIBERIUM_DUST);
		}
		this.dropItem(Items.ROTTEN_FLESH);
	}
	//
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_ENDERMITE_AMBIENT;
	}
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ENDERMITE_DEATH;
	}
	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_ENDERMITE_HURT;
	}
	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 1.85f;
	}

	@Override
	public SimpleInventory getInventory() {
		return null;
	}

	static class WanderAndInfestGoal
			extends WanderAroundGoal {
		@Nullable
		private Direction direction;
		private boolean canInfest;

		public WanderAndInfestGoal(TiberiumWormEntity tiberiumWorm) {
			super(tiberiumWorm, 1.0, 10);
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}

		@Override
		public boolean canStart() {
			if (this.mob.getTarget() != null) {
				return false;
			}
			if (!this.mob.getNavigation().isIdle()) {
				return false;
			}
			Random random = this.mob.getRandom();
			if (this.mob.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && random.nextInt(TiberiumWormEntity.WanderAndInfestGoal.toGoalTicks(10)) == 0) {
				this.direction = Direction.random(random);
				BlockPos blockPos = BlockPos.ofFloored(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()).offset(this.direction);
				BlockState blockState = this.mob.getWorld().getBlockState(blockPos);
				if (BlightBlock.isInfestable(blockState)) {
					this.canInfest = true;
					return true;
				}
			}
			this.canInfest = false;
			return super.canStart();
		}

		@Override
		public boolean shouldContinue() {
			if (this.canInfest) {
				return false;
			}
			return super.shouldContinue();
		}

		@Override
		public void start() {
			BlockPos blockPos;
			if (!this.canInfest) {
				super.start();
				return;
			}
			World worldAccess = this.mob.getWorld();
			BlockState blockState = worldAccess.getBlockState(blockPos = BlockPos.ofFloored(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()).offset(this.direction));
			if (BlightBlock.isInfestable(blockState)) {
				worldAccess.setBlockState(blockPos, BlightBlock.fromRegularState(blockState), Block.NOTIFY_ALL);
				this.mob.playSpawnEffects();
				this.mob.discard();
			}
		}
	}

	class TiberiumWormRevengeGoal
			extends RevengeGoal {
		public TiberiumWormRevengeGoal() {
			super(TiberiumWormEntity.this, new Class[0]);
		}

		@Override
		public void start() {
			super.start();
			if (TiberiumWormEntity.this.isAttacking()) {
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
			extends ActiveTargetGoal<PlayerEntity> {
		public ProtectHordeGoal() {
			super(TiberiumWormEntity.this, PlayerEntity.class, 20, true, true, null);
		}

		@Override
		public boolean canStart() {
			if (super.canStart()) {
				List<TiberiumWormEntity> list = TiberiumWormEntity.this.getWorld().getNonSpectatingEntities(TiberiumWormEntity.class, TiberiumWormEntity.this.getBoundingBox().expand(8.0, 4.0, 8.0));
				for (TiberiumWormEntity tiberiumWormEntity : list) {
					if (!tiberiumWormEntity.isBaby()) continue;
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
