package za.lana.signum.entity.hostile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.EnumSet;

public class SignumWizardEntity extends HostileEntity implements GeoEntity {
    protected SignumWizardEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }
    // BASICS




    // ANIMATIONS
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }
    //GOALS

    static class LookAtTargetGoal
            extends Goal {
        private final GhastEntity ghast;

        public LookAtTargetGoal(GhastEntity ghast) {
            this.ghast = ghast;
            this.setControls(EnumSet.of(Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (this.ghast.getTarget() == null) {
                Vec3d vec3d = this.ghast.getVelocity();
                this.ghast.setYaw(-((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776f);
                this.ghast.bodyYaw = this.ghast.getYaw();
            } else {
                LivingEntity livingEntity = this.ghast.getTarget();
                double d = 64.0;
                if (livingEntity.squaredDistanceTo(this.ghast) < 4096.0) {
                    double e = livingEntity.getX() - this.ghast.getX();
                    double f = livingEntity.getZ() - this.ghast.getZ();
                    this.ghast.setYaw(-((float)MathHelper.atan2(e, f)) * 57.295776f);
                    this.ghast.bodyYaw = this.ghast.getYaw();
                }
            }
        }
    }
    static class ShootFireballGoal
            extends Goal {
        private final GhastEntity ghast;
        public int cooldown;

        public ShootFireballGoal(GhastEntity ghast) {
            this.ghast = ghast;
        }

        @Override
        public boolean canStart() {
            return this.ghast.getTarget() != null;
        }

        @Override
        public void start() {
            this.cooldown = 0;
        }

        @Override
        public void stop() {
            this.ghast.setShooting(false);
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.ghast.getTarget();
            if (livingEntity == null) {
                return;
            }
            double d = 64.0;
            if (livingEntity.squaredDistanceTo(this.ghast) < 4096.0 && this.ghast.canSee(livingEntity)) {
                World world = this.ghast.getWorld();
                ++this.cooldown;
                if (this.cooldown == 10 && !this.ghast.isSilent()) {
                    world.syncWorldEvent(null, WorldEvents.GHAST_WARNS, this.ghast.getBlockPos(), 0);
                }
                if (this.cooldown == 20) {
                    double e = 4.0;
                    Vec3d vec3d = this.ghast.getRotationVec(1.0f);
                    double f = livingEntity.getX() - (this.ghast.getX() + vec3d.x * 4.0);
                    double g = livingEntity.getBodyY(0.5) - (0.5 + this.ghast.getBodyY(0.5));
                    double h = livingEntity.getZ() - (this.ghast.getZ() + vec3d.z * 4.0);
                    if (!this.ghast.isSilent()) {
                        world.syncWorldEvent(null, WorldEvents.GHAST_SHOOTS, this.ghast.getBlockPos(), 0);
                    }
                    FireballEntity fireballEntity = new FireballEntity(world, (LivingEntity)this.ghast, f, g, h, this.ghast.getFireballStrength());
                    fireballEntity.setPosition(this.ghast.getX() + vec3d.x * 4.0, this.ghast.getBodyY(0.5) + 0.5, fireballEntity.getZ() + vec3d.z * 4.0);
                    world.spawnEntity(fireballEntity);
                    this.cooldown = -40;
                }
            } else if (this.cooldown > 0) {
                --this.cooldown;
            }
            this.ghast.setShooting(this.cooldown > 10);
        }
    }
}
