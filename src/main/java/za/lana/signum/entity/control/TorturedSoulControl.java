package za.lana.signum.entity.control;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;
import za.lana.signum.entity.hostile.TorturedSoulEntity;

public class TorturedSoulControl
        extends MoveControl {
    private final TorturedSoulEntity torturedSoul;
    private int collisionCheckCooldown;
    private final int maxPitchChange;
    private final boolean noGravity;
    private WorldView world;
    private BlockPos pos;
    private int hoverDuration;

    public TorturedSoulControl(TorturedSoulEntity torturedSoul, int maxPitchChange, boolean noGravity) {
        super(torturedSoul);
        this.torturedSoul = torturedSoul;
        this.maxPitchChange = maxPitchChange;
        this.noGravity = noGravity;
    }

    @Override
    public void tick() {
        if (this.state != State.MOVE_TO) {
            return;
        }
        if (this.hoverDuration-- <= 0) {
            this.hoverDuration += this.torturedSoul.getRandom().nextInt(5) + 2;
            Vec3d route = new Vec3d(this.targetX - this.torturedSoul.getX(), this.targetY - this.torturedSoul.getY(), this.targetZ - this.torturedSoul.getZ());

            double distance = route.length();
            if (this.willBump(route = route.normalize(), MathHelper.ceil(distance))) {
                this.torturedSoul.setVelocity(this.torturedSoul.getVelocity().add(route.multiply(0.1)));
            } else {
                this.state = State.WAIT;
            }
        }
    }
    private boolean willBump(Vec3d direction, int steps) {
        Box body = this.torturedSoul.getBoundingBox();
        for (int move = 1; move < steps; ++move) {
            body = body.offset(direction);
            if (this.torturedSoul.getWorld().isSpaceEmpty(this.torturedSoul, body)) continue;
            return false;
        }
        return true;
    }
}
