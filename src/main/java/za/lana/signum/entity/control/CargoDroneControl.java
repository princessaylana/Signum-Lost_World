package za.lana.signum.entity.control;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;
import za.lana.signum.entity.transport.CargoDroneEntity;

public class CargoDroneControl
        extends MoveControl {
    private final CargoDroneEntity cdrone;
    private int collisionCheckCooldown;
    private final int maxPitchChange;
    private final boolean noGravity;
    private WorldView world;
    private BlockPos pos;
    private int hoverDuration;

    public CargoDroneControl(CargoDroneEntity cdrone, int maxPitchChange, boolean noGravity) {
        super(cdrone);
        this.cdrone = cdrone;
        this.maxPitchChange = maxPitchChange;
        this.noGravity = noGravity;
    }

    @Override
    public void tick() {
        if (this.state != State.MOVE_TO) {
            return;
        }
        if (this.hoverDuration-- <= 0) {
            this.hoverDuration += this.cdrone.getRandom().nextInt(5) + 2;
            Vec3d route = new Vec3d(this.targetX - this.cdrone.getX(), this.targetY - this.cdrone.getY(), this.targetZ - this.cdrone.getZ());

            double distance = route.length();
            if (this.willBump(route = route.normalize(), MathHelper.ceil(distance))) {
                this.cdrone.setVelocity(this.cdrone.getVelocity().add(route.multiply(0.1)));
            } else {
                this.state = State.WAIT;
            }
        }
    }
    private boolean willBump(Vec3d direction, int steps) {
        Box body = this.cdrone.getBoundingBox();
        for (int move = 1; move < steps; ++move) {
            body = body.offset(direction);
            if (this.cdrone.getWorld().isSpaceEmpty(this.cdrone, body)) continue;
            return false;
        }
        return !this.cdrone.isInLandingPose();
    }
}
