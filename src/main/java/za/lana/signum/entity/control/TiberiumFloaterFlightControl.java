package za.lana.signum.entity.control;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import za.lana.signum.entity.hostile.TiberiumFloaterEntity;

public class TiberiumFloaterFlightControl
        extends MoveControl {
    private final TiberiumFloaterEntity floater;
    private int collisionCheckCooldown;
    private final int maxPitchChange;
    private final boolean noGravity;
    public TiberiumFloaterFlightControl(TiberiumFloaterEntity floater, int maxPitchChange, boolean noGravity) {
        super(floater);
        this.floater = floater;
        this.maxPitchChange = maxPitchChange;
        this.noGravity = noGravity;
    }
    @Override
    public void tick() {
        if (this.state != MoveControl.State.MOVE_TO) {
            return;
        }
        if (this.collisionCheckCooldown-- <= 0) {
            this.collisionCheckCooldown += this.floater.getRandom().nextInt(5) + 2;
            Vec3d vec3d = new Vec3d(this.targetX - this.floater.getX(), this.targetY - this.floater.getY(), this.targetZ - this.floater.getZ());
            double d = vec3d.length();
            if (this.willCollide(vec3d = vec3d.normalize(), MathHelper.ceil(d))) {
                this.floater.setVelocity(this.floater.getVelocity().add(vec3d.multiply(0.1)));
            } else {
                this.state = MoveControl.State.WAIT;
            }
        };
    }
    private boolean willCollide(Vec3d direction, int steps) {
        Box box = this.floater.getBoundingBox();
        for (int i = 1; i < steps; ++i) {
            box = box.offset(direction);
            if (this.floater.getWorld().isSpaceEmpty(this.floater, box)) continue;
            return false;
        }
        return true;
    }
}
