package za.lana.signum.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import za.lana.signum.entity.mob.PidgeonEntity;

public class AlertTargetGoal
        extends Goal {
    private final PidgeonEntity pidgeon;
    public int cooldown;

    public AlertTargetGoal(PidgeonEntity pidgeon) {
        this.pidgeon = pidgeon;
    }

    @Override
    public boolean canStart() {
        return this.pidgeon.getTarget() != null;
    }

    @Override
    public void start() {
        World level = pidgeon.getWorld();
        if (level.isNight() && pidgeon.isSleeping()) {
            this.cooldown = 0;
            this.pidgeon.setAlert(true);
        }
    }

    @Override
    public void stop() {
        this.pidgeon.setAlert(false);
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.pidgeon.getTarget();
        if (livingEntity == null) {
            return;
        }
        double d = 64.0;
        if (livingEntity.squaredDistanceTo(this.pidgeon) < 2048.0 && this.pidgeon.canSee(livingEntity)) {
            ++this.cooldown;

            if (this.cooldown == 10 && !this.pidgeon.isSilent()) {
                this.pidgeon.getLookControl().lookAt(livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ());
                // PLAY SOFT WARN
                this.pidgeon.playSound(SoundEvents.BLOCK_SCULK_SHRIEKER_STEP, 0.15f, 1.0f);
            }
            if (this.cooldown == 20) {
                double e = 4.0;
                Vec3d vec3d = this.pidgeon.getRotationVec(1.0f);
                double f = livingEntity.getX() - (this.pidgeon.getX() + vec3d.x * 4.0);
                double g = livingEntity.getBodyY(0.5) - (0.5 + this.pidgeon.getBodyY(0.5));
                double h = livingEntity.getZ() - (this.pidgeon.getZ() + vec3d.z * 4.0);
                if (!this.pidgeon.isSilent()) {
                    // PLAY WARNING SOUND
                    this.pidgeon.playSound(SoundEvents.BLOCK_SCULK_SHRIEKER_FALL, 0.15f, 1.0f);
                }
                // PLAY ALERT SOUND
                this.pidgeon.playSound(SoundEvents.BLOCK_SCULK_SHRIEKER_HIT, 0.15f, 1.0f);
                //
                this.cooldown = -40;
            }
        } else if (this.cooldown > 0) {
            --this.cooldown;
        }
        this.pidgeon.setAlert(this.cooldown > 10);
    }
}