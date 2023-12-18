package za.lana.signum.entity.control;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import za.lana.signum.entity.transport.AirShipEntity;

public class AirshipFlightControl
        extends MoveControl {
    private final AirShipEntity airship;
    private int collisionCheckCooldown;
    private final int maxPitchChange;
    private final boolean noGravity;
    public AirshipFlightControl(AirShipEntity airship, int maxPitchChange, boolean noGravity) {
        super(airship);
        this.airship = airship;
        this.maxPitchChange = maxPitchChange;
        this.noGravity = noGravity;
    }
    @Override
    public void tick() {
        if (this.state != State.MOVE_TO) {
            return;
        }
        if (this.collisionCheckCooldown-- <= 0) {
            this.collisionCheckCooldown += this.airship.getRandom().nextInt(5) + 2;
            Vec3d vec3d = new Vec3d(this.targetX - this.airship.getX(), this.targetY - this.airship.getY(), this.targetZ - this.airship.getZ());
            double d = vec3d.length();
            if (this.willCollide(vec3d = vec3d.normalize(), MathHelper.ceil(d))) {
                this.airship.setVelocity(this.airship.getVelocity().add(vec3d.multiply(0.1)));
            } else {
                this.state = State.WAIT;
            }
        };
    }
    private boolean willCollide(Vec3d direction, int steps) {
        Box box = this.airship.getBoundingBox();
        for (int i = 1; i < steps; ++i) {
            box = box.offset(direction);
            if (this.airship.getWorld().isSpaceEmpty(this.airship, box)) continue;
            return false;
        }
        return true;
    }
}
