
package za.lana.signum.entity.ai;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.entity.mob.WizardEntity;

public class FollowWizardGoal
        extends Goal {
    private final MobEntity mob;
    private final Predicate<WizardEntity> targetPredicate;
    @Nullable
    private WizardEntity target;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private final float minDistance;
    private float oldWaterPathFindingPenalty;
    private final float maxDistance;

    public FollowWizardGoal(MobEntity mob, double speed, float minDistance, float maxDistance) {
        this.mob = mob;
        this.targetPredicate = target -> target != null && mob.getClass() != target.getClass();
        this.speed = speed;
        this.navigation = mob.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        if (!(mob.getNavigation() instanceof MobNavigation) && !(mob.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
        }
    }

    @Override
    public boolean canStart() {
        World level = this.mob.getWorld();
        if(level.isNight()){
            List<WizardEntity> list = this.mob.getWorld().getEntitiesByClass(WizardEntity.class, this.mob.getBoundingBox().expand(this.maxDistance), this.targetPredicate);
            if (!list.isEmpty()) {
                for (WizardEntity wizardEntity : list) {
                    if (wizardEntity.isInvisible()) continue;
                    this.target = wizardEntity;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        return this.target != null && !this.navigation.isIdle() && this.mob.squaredDistanceTo(this.target) > (double)(this.minDistance * this.minDistance);
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathFindingPenalty = this.mob.getPathfindingPenalty(PathNodeType.WATER);
        this.mob.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
    }

    @Override
    public void stop() {
        this.target = null;
        this.navigation.stop();
        this.mob.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathFindingPenalty);
    }

    @Override
    public void tick() {
        double f;
        double e;
        if (this.target == null || this.mob.isLeashed()) {
            return;
        }
        this.mob.getLookControl().lookAt(this.target, 10.0f, this.mob.getMaxLookPitchChange());
        if (--this.updateCountdownTicks > 0) {
            return;
        }
        this.updateCountdownTicks = this.getTickCount(10);
        double d = this.mob.getX() - this.target.getX();
        double g = d * d + (e = this.mob.getY() - this.target.getY()) * e + (f = this.mob.getZ() - this.target.getZ()) * f;
        if (g <= (double)(this.minDistance * this.minDistance)) {
            this.navigation.stop();
            LookControl lookControl = this.target.getLookControl();
            if (g <= (double)this.minDistance || lookControl.getLookX() == this.mob.getX() && lookControl.getLookY() == this.mob.getY() && lookControl.getLookZ() == this.mob.getZ()) {
                double h = this.target.getX() - this.mob.getX();
                double i = this.target.getZ() - this.mob.getZ();
                this.navigation.startMovingTo(this.mob.getX() - h, this.mob.getY(), this.mob.getZ() - i, this.speed);
            }
            return;
        }
        this.navigation.startMovingTo(this.target, this.speed);
    }
}

