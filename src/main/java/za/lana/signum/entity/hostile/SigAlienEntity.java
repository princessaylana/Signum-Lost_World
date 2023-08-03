package za.lana.signum.entity.hostile;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.function.Predicate;

public class SigAlienEntity extends HostileEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public SigAlienEntity(EntityType<? extends HostileEntity> type, World level) {
        super(type, level);
    }
    public static DefaultAttributeContainer.Builder setAttributes() {
        return PathAwareEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0);
    }
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new SigAlienEntity.ChasePlayerGoal(this));
        this.goalSelector.add(6, new PounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(7, new MeleeAttackGoal(this, 0.4f, false));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, ZombieEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.6f;
    }

    protected boolean teleportRandomly() {
        if (this.getWorld().isClient() || !this.isAlive()) {
            return false;
        }
        double d = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
        double e = this.getY() + (double)(this.random.nextInt(64) - 32);
        double f = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
        return this.teleportTo(d, e, f);
    }

    boolean teleportTo(Entity entity) {
        Vec3d vec3d = new Vec3d(this.getX() - entity.getX(), this.getBodyY(0.5) - entity.getEyeY(), this.getZ() - entity.getZ());
        vec3d = vec3d.normalize();
        double d = 16.0;
        double e = this.getX() + (this.random.nextDouble() - 0.5) * 8.0 - vec3d.x * 16.0;
        double f = this.getY() + (double)(this.random.nextInt(16) - 8) - vec3d.y * 16.0;
        double g = this.getZ() + (this.random.nextDouble() - 0.5) * 8.0 - vec3d.z * 16.0;
        return this.teleportTo(e, f, g);
    }

    private boolean teleportTo(double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);
        while (mutable.getY() > this.getWorld().getBottomY() && !this.getWorld().getBlockState(mutable).blocksMovement()) {
            mutable.move(Direction.DOWN);
        }
        BlockState blockState = this.getWorld().getBlockState(mutable);
        boolean bl = blockState.blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (!bl || bl2) {
            return false;
        }
        Vec3d vec3d = this.getPos();
        boolean bl3 = this.teleport(x, y, z, true);
        if (bl3) {
            this.getWorld().emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(this));
            if (!this.isSilent()) {
                this.getWorld().playSound(null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0f, 1.0f);
                this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            }
        }
        return bl3;
    }

    // Add our animations
    private PlayState predicate(AnimationState tAnimationState) {
        if(tAnimationState.isMoving()) {
            tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.sigalien.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.sigalien.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
    boolean isPlayerStaring(PlayerEntity player) {
        ItemStack itemStack = player.getInventory().armor.get(3);
        if (itemStack.isOf(Blocks.CARVED_PUMPKIN.asItem())) {
            return false;
        }
        Vec3d vec3d = player.getRotationVec(1.0f).normalize();
        Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
        double d = vec3d2.length();
        double e = vec3d.dotProduct(vec3d2 = vec3d2.normalize());
        if (e > 1.0 - 0.025 / d) {
            return player.canSee(this);
        }
        return false;
    }


    static class TeleportTowardsPlayerGoal
            extends ActiveTargetGoal<PlayerEntity> {
        private final SigAlienEntity sigalien;
        @Nullable
        private PlayerEntity targetPlayer;
        private int lookAtPlayerWarmup;
        private int ticksSinceUnseenTeleport;
        private final TargetPredicate staringPlayerPredicate;
        private final TargetPredicate validTargetPredicate = TargetPredicate.createAttackable().ignoreVisibility();
        private final Predicate<LivingEntity> angerPredicate;

        public TeleportTowardsPlayerGoal(SigAlienEntity sigalien, @Nullable Predicate<LivingEntity> targetPredicate) {
            super(sigalien, PlayerEntity.class, 10, false, false, targetPredicate);
            this.sigalien = sigalien;
            this.angerPredicate = playerEntity -> (sigalien.isPlayerStaring((PlayerEntity)playerEntity) || !sigalien.hasPassengerDeep((Entity)playerEntity));
            this.staringPlayerPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(this.getFollowRange()).setPredicate(this.angerPredicate);
        }

        @Override
        public boolean canStart() {
            this.targetPlayer = this.sigalien.getWorld().getClosestPlayer(this.staringPlayerPredicate, this.sigalien);
            return this.targetPlayer != null;
        }

        @Override
        public void start() {
            this.lookAtPlayerWarmup = this.getTickCount(5);
            this.ticksSinceUnseenTeleport = 0;
        }

        @Override
        public void stop() {
            this.targetPlayer = null;
            super.stop();
        }

        @Override
        public boolean shouldContinue() {
            if (this.targetPlayer != null) {
                if (!this.angerPredicate.test(this.targetPlayer)) {
                    return false;
                }
                this.sigalien.lookAtEntity(this.targetPlayer, 10.0f, 10.0f);
                return true;
            }
            if (this.targetEntity != null) {
                if (this.sigalien.hasPassengerDeep(this.targetEntity)) {
                    return false;
                }
                if (this.validTargetPredicate.test(this.sigalien, this.targetEntity)) {
                    return true;
                }
            }
            return super.shouldContinue();
        }

        @Override
        public void tick() {
            if (this.sigalien.getTarget() == null) {
                super.setTargetEntity(null);
            }
            if (this.targetPlayer != null) {
                if (--this.lookAtPlayerWarmup <= 0) {
                    this.targetEntity = this.targetPlayer;
                    this.targetPlayer = null;
                    super.start();
                }
            } else {
                if (this.targetEntity != null && !this.sigalien.hasVehicle()) {
                    if (this.sigalien.isPlayerStaring((PlayerEntity)this.targetEntity)) {
                        if (this.targetEntity.squaredDistanceTo(this.sigalien) < 16.0) {
                            this.sigalien.teleportRandomly();
                        }
                        this.ticksSinceUnseenTeleport = 0;
                    } else if (this.targetEntity.squaredDistanceTo(this.sigalien) > 256.0 && this.ticksSinceUnseenTeleport++ >= this.getTickCount(30) && this.sigalien.teleportTo(this.targetEntity)) {
                        this.ticksSinceUnseenTeleport = 0;
                    }
                }
                super.tick();
            }
        }
    }
    static class ChasePlayerGoal
            extends Goal {
        private final SigAlienEntity sigalien;
        @Nullable
        private LivingEntity target;

        public ChasePlayerGoal(SigAlienEntity sigalien) {
            this.sigalien = sigalien;
            this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            this.target = this.sigalien.getTarget();
            if (!(this.target instanceof PlayerEntity)) {
                return false;
            }
            double d = this.target.squaredDistanceTo(this.sigalien);
            if (d > 256.0) {
                return false;
            }
            return this.sigalien.isPlayerStaring((PlayerEntity)this.target);
        }

        @Override
        public void start() {
            this.sigalien.getNavigation().stop();
        }

        @Override
        public void tick() {
            this.sigalien.getLookControl().lookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        }
    }
}