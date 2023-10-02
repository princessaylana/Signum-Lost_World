/**
 * SIGNUM
 * MIT License
 * This is the drone, the flying bot
 * the goals must be arragned so it forms a daily schedule
 * this will be the worker drone,
 * its weapon will become a utility laser
 * very small and weak, the same laser
 * that it will be using to chop wood
 * there will be another entity similar to this for attacks and patrols.
 *
 * Lana
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;

public class AirDroneEntity extends FlyingEntity implements GeoEntity {
	private static final TrackedData<Boolean> SHOOTING;

	{
		DataTracker.registerData(AirDroneEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}

	private int fireballStrength = 1;
	//private static BlockStatePredicate CROP_PREDICATE = (BlockTags.CROPS);
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	public AirDroneEntity(EntityType<? extends FlyingEntity> entityType, World world) {
		super(entityType, world);
		this.experiencePoints = 2;
		this.moveControl = new AirDroneMoveControl(this);
	}

	private PlayState predicate(AnimationState tAnimationState) {
		if(tAnimationState.isMoving()) {
			tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.airdrone.fly", Animation.LoopType.LOOP));
			return PlayState.CONTINUE;
		}
		tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.airdrone.idle", Animation.LoopType.LOOP));
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
	// all the entity stuff
	private static boolean isFireballFromPlayer(DamageSource damageSource) {
		return damageSource.getSource() instanceof FireballEntity && damageSource.getAttacker() instanceof PlayerEntity;
	}

	@Override
	public boolean isInvulnerableTo(DamageSource damageSource) {
		return !AirDroneEntity.isFireballFromPlayer(damageSource) && super.isInvulnerableTo(damageSource);
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (AirDroneEntity.isFireballFromPlayer(source)) {
			super.damage(source, 1000.0f);
			return true;
		}
		if (this.isInvulnerableTo(source)) {
			return false;
		}
		return super.damage(source, amount);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SHOOTING, false);
	}


	public static DefaultAttributeContainer.Builder setAttributes() {
		return FlyingEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0f)
				.add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5f)
				.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.5f)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0);
	}


	@Override
	protected void initGoals() {
		this.goalSelector.add(5, new AirDroneEntity.FlyRandomlyGoal(this));
		this.goalSelector.add(7, new AirDroneEntity.LookAtTargetGoal(this));
		this.goalSelector.add(4, new AirDroneEntity.ShootFireballGoal(this));

		this.targetSelector.add(1, new ActiveTargetGoal<>(this, ZombieEntity.class, 10, true, false, entity -> Math.abs(entity.getY() - this.getY()) <= 4.0));
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, entity -> Math.abs(entity.getY() - this.getY()) <= 4.0));
	}

	public boolean isShooting() {
		return (Boolean)this.dataTracker.get(SHOOTING);
	}
	public void setShooting(boolean shooting) {
		this.dataTracker.set(SHOOTING, shooting);
	}
	public int getFireballStrength() {
		return this.fireballStrength;
	}

	//sounds to be added here
	//space for more sounds
	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_BAT_AMBIENT;
		//return MWSounds.CAMOBOT_IDLE;
	}
	@Override
	protected  SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.ENTITY_GENERIC_EXPLODE;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_GENERIC_EXPLODE;
	}
	@Override
	public boolean shouldDropXp() {
		return true;
	}
	protected boolean shouldDropLoot() {
		return false;
	}
	public boolean hurtByWater() {
		return true;
	}
	public static boolean canSpawn(EntityType<AirDroneEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
		return world.getDifficulty() != Difficulty.PEACEFUL && random.nextInt(20) == 0 && canMobSpawn(type, world, spawnReason, pos, random);
	}

	public int getLimitPerChunk() {
		return 5;
	}

	protected float getSoundVolume() {
		return 1.0F;
	}



	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putByte("ExplosionPower", (byte)this.fireballStrength);
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("ExplosionPower", 99)) {
			this.fireballStrength= nbt.getByte("ExplosionPower");
		}

	}
	static {
		SHOOTING = DataTracker.registerData(AirDroneEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}
	@Override
	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.5f;
	}

	// ai flight controls
	private static class AirDroneMoveControl extends MoveControl {
		private final AirDroneEntity airdrone;
		private int collisionCheckCooldown;

		public AirDroneMoveControl(AirDroneEntity airdrone) {
			super(airdrone);
			this.airdrone = airdrone;
		}


		@Override
		public void tick() {
			if (this.state != MoveControl.State.MOVE_TO) {
				return;
			}
			if (this.collisionCheckCooldown-- <= 0) {
				this.collisionCheckCooldown += this.airdrone.getRandom().nextInt(5) + 2;
				Vec3d vec3d = new Vec3d(this.targetX - this.airdrone.getX(), this.targetY - this.airdrone.getY(), this.targetZ - this.airdrone.getZ());
				double d = vec3d.length();
				if (this.willCollide(vec3d = vec3d.normalize(), MathHelper.ceil(d))) {
					this.airdrone.setVelocity(this.airdrone.getVelocity().add(vec3d.multiply(0.1)));
				} else {
					this.state = MoveControl.State.WAIT;
				}
			}
		}


		private boolean willCollide(Vec3d direction, int steps) {
			Box box = this.airdrone.getBoundingBox();
			for (int i = 1; i < steps; ++i) {
				//for (int i = 1; i < steps; ++i) {
				if (this.airdrone.getWorld().isSpaceEmpty(this.airdrone, box = box.offset(direction))) continue;
				return false;
			}
			return true;
		}
	}
	//
	private static class FlyRandomlyGoal extends Goal {
		private final AirDroneEntity airDrone;

		public FlyRandomlyGoal(AirDroneEntity airdrone) {
			this.airDrone = airdrone;
			this.setControls(EnumSet.of(Control.MOVE));
		}

		public boolean canStart() {
			MoveControl moveControl = this.airDrone.getMoveControl();
			if (!moveControl.isMoving()) {
				return true;
			} else {
				double d = moveControl.getTargetX() - this.airDrone.getX();
				double e = moveControl.getTargetY() - this.airDrone.getY();
				double f = moveControl.getTargetZ() - this.airDrone.getZ();
				double g = d * d + e * e + f * f;
				return g < 1.0 || g > 1800.0;
			}
		}

		public boolean shouldContinue() {
			return false;
		}

		public void start() {
			Random random = this.airDrone.getRandom();
			double d = this.airDrone.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 8.0F);
			double e = this.airDrone.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 8.0F);
			double f = this.airDrone.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 8.0F);
			this.airDrone.getMoveControl().moveTo(d, e, f, 1.5);
		}
	}
	//
	private static class LookAtTargetGoal extends Goal {
		private final AirDroneEntity airDrone;

		public LookAtTargetGoal(AirDroneEntity airDrone) {
			this.airDrone = airDrone;
			this.setControls(EnumSet.of(Control.LOOK));
		}

		public boolean canStart() {
			return true;
		}

		public boolean shouldRunEveryTick() {
			return true;
		}

		public void tick() {
			if (this.airDrone.getTarget() == null) {
				Vec3d vec3d = this.airDrone.getVelocity();
				this.airDrone.setYaw(-((float)MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F);
				this.airDrone.bodyYaw = this.airDrone.getYaw();
			} else {
				LivingEntity livingEntity = this.airDrone.getTarget();
				double d = 64.0;
				if (livingEntity.squaredDistanceTo(this.airDrone) < 4096.0) {
					double e = livingEntity.getX() - this.airDrone.getX();
					double f = livingEntity.getZ() - this.airDrone.getZ();
					this.airDrone.setYaw(-((float)MathHelper.atan2(e, f)) * 57.295776F);
					this.airDrone.bodyYaw = this.airDrone.getYaw();
				}
			}

		}
	}

	private static class LandingGoal extends Goal{

		@Override
		public boolean canStart() {
			return false;
		}
	}
	static class ShootFireballGoal
			extends Goal {
		private final AirDroneEntity airDrone;
		public int cooldown;

		public ShootFireballGoal(AirDroneEntity airDrone) {
			this.airDrone= airDrone;
		}

		@Override
		public boolean canStart() {
			return this.airDrone.getTarget() != null;
		}

		@Override
		public void start() {
			this.cooldown = 0;
		}

		@Override
		public void stop() {
			this.airDrone.setShooting(false);
		}

		@Override
		public boolean shouldRunEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			LivingEntity livingEntity = this.airDrone.getTarget();
			if (livingEntity == null) {
				return;
			}
			double d = 64.0;
			if (livingEntity.squaredDistanceTo(this.airDrone) < 4096.0 && this.airDrone.canSee(livingEntity)) {
				World world = this.airDrone.getWorld();
				++this.cooldown;
				//if (this.cooldown == 10 && !this.airDrone.isSilent()) {world.syncWorldEvent(null, WorldEvents.GHAST_WARNS, this.airDrone.getBlockPos(), 0);}
				if (this.cooldown == 20) {
					double e = 4.0;
					Vec3d vec3d = this.airDrone.getRotationVec(1.0f);
					double f = livingEntity.getX() - (this.airDrone.getX() + vec3d.x * 4.0);
					double g = livingEntity.getBodyY(0.5) - (0.5 + this.airDrone.getBodyY(0.5));
					double h = livingEntity.getZ() - (this.airDrone.getZ() + vec3d.z * 4.0);
					//if (!this.airDrone.isSilent()) {world.syncWorldEvent(null, WorldEvents.GHAST_SHOOTS, this.airDrone.getBlockPos(), 0);}
					FireballEntity fireballEntity = new FireballEntity(world, this.airDrone, f, g, h, this.airDrone.getFireballStrength());
					fireballEntity.setPosition(this.airDrone.getX() + vec3d.x * 4.0, this.airDrone.getBodyY(0.5) + 0.5, fireballEntity.getZ() + vec3d.z * 4.0);
					world.spawnEntity(fireballEntity);
					this.cooldown = -40;
				}
			} else if (this.cooldown > 0) {
				--this.cooldown;
			}
			this.airDrone.setShooting(this.cooldown > 10);
		}
	}
}


