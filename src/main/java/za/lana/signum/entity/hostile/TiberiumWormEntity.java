/**
 * SIGNUM
 * MIT License
 * The first animated entity for this mod
 * these worms should be addicted to tiberium
 * they are mutations from tiberium and should go into a
 * frenzy when get close to tiberium
 *
 * Lana
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import za.lana.signum.entity.ModEntities;

public class TiberiumWormEntity extends AnimalEntity implements GeoEntity {
	private AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public TiberiumWormEntity(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	public static DefaultAttributeContainer.Builder setAttributes() {
		return AnimalEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0f)
				.add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new SwimGoal(this));
		this.goalSelector.add(2, new MeleeAttackGoal(this, 1.2D, false));
		this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.75f, 1));

		this.goalSelector.add(4, new LookAroundGoal(this));

		this.targetSelector.add(2, new ActiveTargetGoal<>(this, ZombieEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
		this.targetSelector.add(3, new ActiveTargetGoal<>(this, ChickenEntity.class, true));
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
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
		controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}
}
