package za.lana.signum.entity.ai;

import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PidgeonFlyGoal
        extends WanderAroundGoal {
    public PidgeonFlyGoal(PathAwareEntity pathAwareEntity, double d) {
        super(pathAwareEntity, d);
    }

    @Override
    public boolean canStart() {
        World level = this.mob.getWorld();
        return !this.mob.isAttacking() && level.isDay() && super.canStart();
    }

    @Override
    @Nullable
    protected Vec3d getWanderTarget() {
        Vec3d vec3d = this.mob.getRotationVec(0.0f);
        int i = 8;
        Vec3d vec3d2 = AboveGroundTargeting.find(this.mob, 8, 7, vec3d.x, vec3d.z, 1.5707964f, 3, 1);
        if (vec3d2 != null) {
            return vec3d2;
        }
        return NoPenaltySolidTargeting.find(this.mob, 8, 4, -2, vec3d.x, vec3d.z, 1.5707963705062866);
    }
    @Override
    public void stop() {
        World level = this.mob.getWorld();
        if (level.isNight()){
            super.stop();
        }
    }
}
