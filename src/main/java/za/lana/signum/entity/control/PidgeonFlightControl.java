/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.entity.control;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

// PARROT BASED
public class PidgeonFlightControl
        extends MoveControl {
    private final int maxPitchChange;
    private final boolean noGravity;

    public PidgeonFlightControl(MobEntity entity, int maxPitchChange, boolean noGravity) {
        super(entity);
        this.maxPitchChange = maxPitchChange;
        this.noGravity = noGravity;
    }

    @Override
    public void tick() {
        if (this.entity.isAlive()){
            if (this.state == MoveControl.State.MOVE_TO) {
                this.state = MoveControl.State.WAIT;
                this.entity.setNoGravity(true);
                //
                double d = this.targetX - this.entity.getX();
                double e = this.targetY - this.entity.getY();
                double f = this.targetZ - this.entity.getZ();
                double g = d * d + e * e + f * f;
                if (g < 2.500000277905201E-7) {
                    this.entity.setUpwardSpeed(0.0f);
                    this.entity.setForwardSpeed(0.0f);
                    return;
                }
                float h = (float)(MathHelper.atan2(f, d) * 57.2957763671875) - 90.0f;
                this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), h, 90.0f));
                // SET SPEED ground or fly
                float ground = this.entity.isOnGround() ? (float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)) : (float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));
                this.entity.setMovementSpeed(ground);

                double j = Math.sqrt(d * d + f * f);
                if (Math.abs(e) > (double)1.0E-5f || Math.abs(j) > (double)1.0E-5f) {
                    float k = (float)(-(MathHelper.atan2(e, j) * 57.2957763671875));
                    this.entity.setPitch(this.wrapDegrees(this.entity.getPitch(), k, this.maxPitchChange));
                    this.entity.setUpwardSpeed(e > 0.0 ? ground : -ground);
                }
            }
            else {
                if (!this.noGravity) {
                    this.entity.setNoGravity(false);
                    //
                }
                this.entity.setUpwardSpeed(0.0F);
                this.entity.setForwardSpeed(0.0F);
            }
        }
    }
}




