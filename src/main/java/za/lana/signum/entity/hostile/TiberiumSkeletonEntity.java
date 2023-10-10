/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.hostile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import za.lana.signum.constant.SignumAnimations;
import za.lana.signum.effect.ModEffects;

import java.util.List;

public class TiberiumSkeletonEntity extends HostileEntity implements GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public TiberiumSkeletonEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 5;
    }
    // BASICS
    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f);
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new AvoidSunlightGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(5, new WanderAroundGoal(this, 1.0));
        //this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));

        this.targetSelector.add(1, new TiberiumSkeletonRevengeGoal());
        this.targetSelector.add(2, new ProtectHordeGoal());
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(4, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
        this.targetSelector.add(5, new ActiveTargetGoal<>(this, VillagerEntity.class, true));
        this.targetSelector.add(8, new ActiveTargetGoal<>(this, CreeperEntity.class, true));

    }

    //ANIMATIONS
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "walk",1, this::walkController));
        controllerRegistrar.add(new AnimationController<>(this, "idle", 10, this::idleController));
        controllerRegistrar.add(new AnimationController<>(this, "attack",3, this::attackController));
    }
    private PlayState walkController(AnimationState<TiberiumSkeletonEntity> tsAnimationState) {
        if (tsAnimationState.isMoving()) {
            return tsAnimationState.setAndContinue(SignumAnimations.TIBERIUM_SKELETON_WALK);
        }
        return PlayState.STOP;
    }
    private PlayState idleController(AnimationState<TiberiumSkeletonEntity> tsAnimationState) {
        return tsAnimationState.setAndContinue(tsAnimationState.isMoving() ? SignumAnimations.TIBERIUM_SKELETON_WALK : SignumAnimations.TIBERIUM_SKELETON_IDLE);
    }

    private PlayState attackController(AnimationState<TiberiumSkeletonEntity> tsAnimationState) {
        if (tsAnimationState.getAnimatable().handSwinging
                && tsAnimationState.getController().getAnimationState().equals(AnimationController.State.STOPPED)){
            return tsAnimationState.setAndContinue(SignumAnimations.TIBERIUM_SKELETON_ATTACK);
        }
        tsAnimationState.getController().forceAnimationReset();
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    // IMUMME TO TIBERIUM
    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if (effect.getEffectType() == ModEffects.TIBERIUM_POISON) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }

    // GOALS
    class TiberiumSkeletonRevengeGoal
            extends RevengeGoal {
        public TiberiumSkeletonRevengeGoal() {
            super(TiberiumSkeletonEntity.this);
        }

        @Override
        public void start() {
            super.start();
            if (TiberiumSkeletonEntity.this.isAttacking()) {
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
            super(TiberiumSkeletonEntity.this, PlayerEntity.class, 20, true, true, null);
        }

        @Override
        public boolean canStart() {
            if (super.canStart()) {
                List<TiberiumSkeletonEntity> list = TiberiumSkeletonEntity.this.getWorld().getNonSpectatingEntities(TiberiumSkeletonEntity.class, TiberiumSkeletonEntity.this.getBoundingBox().expand(16.0, 4.0, 16.0));
                for (TiberiumSkeletonEntity tiberiumSkeletonEntity : list) {
                    if (!tiberiumSkeletonEntity.isBaby()) continue;
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
