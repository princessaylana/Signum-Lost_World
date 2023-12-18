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
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
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

public class TiberiumWormEntity extends HostileEntity {

	public int attackAniTimeout = 0;
	private int idleAniTimeout = 0;
	public final net.minecraft.entity.AnimationState attackAniState = new net.minecraft.entity.AnimationState();
	public final net.minecraft.entity.AnimationState idleAniState = new AnimationState();
	private static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(TiberiumWormEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public TiberiumWormEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.experiencePoints = 3;
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

		this.initCustomTargets();
	}

	protected void initCustomTargets() {
		// BLACK FOREST
		this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, false, false, entity -> entity instanceof Monster && !(entity instanceof CreeperEntity) && entity.getGroup() == ModEntityGroup.BLACK_FOREST));
		// DEATHLANDS
		this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, false, false, entity -> entity instanceof Monster && !(entity instanceof CreeperEntity) && entity.getGroup() == ModEntityGroup.DEATH_LANDS));
		// FROZEN LANDS
		this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, false, false, entity -> entity instanceof Monster && !(entity instanceof CreeperEntity) && entity.getGroup() == ModEntityGroup.FROZEN_LANDS));
		// GOLDEN KINGDOM
		this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, false, false, entity -> entity instanceof Monster && !(entity instanceof CreeperEntity) && entity.getGroup() == ModEntityGroup.GOLDEN_KINGDOM));
		// MAGIC FOREST
		this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, false, false, entity -> entity instanceof Monster && !(entity instanceof CreeperEntity) && entity.getGroup() == ModEntityGroup.GOLDEN_KINGDOM));
		// RAINBOW MUSHROOMS
		this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, false, false, entity -> entity instanceof Monster && !(entity instanceof CreeperEntity) && entity.getGroup() == ModEntityGroup.GOLDEN_KINGDOM));
		// TIBERIUM WASTELAND
		//this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, false, false, entity -> entity instanceof Monster && !(entity instanceof CreeperEntity) && entity.getGroup() == ModEntityGroup.TIBERIUM_WASTELAND));

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
	//
	public EntityGroup getGroup() {
		return ModEntityGroup.TIBERIUM_WASTELAND;
	}
	@Override
	public boolean isTeammate(Entity other) {
		if (super.isTeammate(other)) {
			return true;
		}
		if (other instanceof LivingEntity && ((LivingEntity)other).getGroup() == ModEntityGroup.TIBERIUM_WASTELAND) {
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

	@Override
	protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
		TiberiumWormEntity tiberiumWormEntity;
		super.dropEquipment(source, lootingMultiplier, allowDrops);
		Entity entity = source.getAttacker();
		this.dropItem(ModItems.TIBERIUM_DUST);
	}

}
