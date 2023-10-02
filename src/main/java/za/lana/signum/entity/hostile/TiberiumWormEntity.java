/**
 * SIGNUM
 * MIT License
 * The first animated entity for this mod
 * these worms should be addicted to tiberium
 * they are mutations from tiberium and should go into a
 * frenzy when get close to tiberium
 * they infest blightblocks in tiberium biome
 *
 * Lana
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import za.lana.signum.block.custom.props.BlightBlock;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.item.ModItems;

import java.util.EnumSet;
import java.util.List;

public class TiberiumWormEntity extends HostileEntity implements GeoEntity {
	private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public TiberiumWormEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
		this.experiencePoints = 5;
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
		this.goalSelector.add(3, new MeleeAttackGoal(this, 1.2D, false));
		this.goalSelector.add(4, new WanderAroundGoal(this, 1.0));
		this.goalSelector.add(5, new WanderAndInfestGoal(this));
		this.goalSelector.add(6, new LookAroundGoal(this));

		this.targetSelector.add(1, new TiberiumWormEntity.TiberiumWormRevengeGoal());
		this.targetSelector.add(2, new TiberiumWormEntity.ProtectHordeGoal());
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, ZombieEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(3, new ActiveTargetGoal<>(this, ChickenEntity.class, true));
	}

	public TiberiumWormEntity createChild(ServerWorld world, PassiveEntity entity) {
		return ModEntities.TIBERIUM_WORM.create(world);
	}

	private PlayState predicate(AnimationState tAnimationState) {
		if(tAnimationState.isMoving()) {
			tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.tworm.walk", Animation.LoopType.LOOP));
			return PlayState.CONTINUE;
		}

		tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.tworm.idle", Animation.LoopType.LOOP));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
		controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
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
