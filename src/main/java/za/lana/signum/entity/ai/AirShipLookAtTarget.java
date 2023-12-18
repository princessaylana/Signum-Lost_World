package za.lana.signum.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import za.lana.signum.entity.transport.AirShipEntity;

import java.util.EnumSet;

public class AirShipLookAtTarget extends Goal {
    private final AirShipEntity airship;

    public AirShipLookAtTarget (AirShipEntity airship) {
        this.airship = airship;
        this.setControls(EnumSet.of(Control.LOOK));
    }

    public boolean canStart() {
        return true;
    }

    public boolean shouldRunEveryTick() {
        return true;
    }

    public void tick() {
        if (this.airship.getTarget() == null) {
            Vec3d vec3d = this.airship.getVelocity();
            this.airship.setYaw(-((float) MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776F);
            this.airship.bodyYaw = this.airship.getYaw();

        } else {
            // entity cant target its passenger
            LivingEntity livingEntity = this.airship.getTarget();
            if (!(livingEntity == this.airship.getFirstPassenger())){
                double d = 64.0;
                if (livingEntity.squaredDistanceTo(this.airship) < 4096.0) {
                    double e = livingEntity.getX() - this.airship.getX();
                    double f = livingEntity.getZ() - this.airship.getZ();
                    this.airship.setYaw(-((float)MathHelper.atan2(e, f)) * 57.295776F);
                    this.airship.bodyYaw = this.airship.getYaw();
                }
            }
        }
    }
}
