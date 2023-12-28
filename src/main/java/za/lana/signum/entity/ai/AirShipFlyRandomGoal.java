package za.lana.signum.entity.ai;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import za.lana.signum.entity.transport.AirShipEntity;

import java.util.EnumSet;

public  class AirShipFlyRandomGoal
        extends Goal {
    private final AirShipEntity airship;

    public AirShipFlyRandomGoal(AirShipEntity airship) {
        this.airship = airship;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        double f;
        double e;
        MoveControl moveControl = this.airship.getMoveControl();
        World level = this.airship.getWorld();
        if (!moveControl.isMoving() && this.airship.getWorld().isDay()) {
            return true;
        }
        double d = moveControl.getTargetX() - this.airship.getX();
        double g = d * d + (e = moveControl.getTargetY() - this.airship.getY()) * e + (f = moveControl.getTargetZ() - this.airship.getZ()) * f;
        return g < 1.0 || g > 7200.0;
    }

    @Override
    public boolean shouldContinue() {
        return false;
    }

    @Override
    public void start() {
        Random random = this.airship.getRandom();
        double d = this.airship.getX() + (double)((random.nextFloat() * 2.0f - 1.0f) * 64.0f);
        double e = this.airship.getY() + (double)((random.nextFloat() * 2.0f - 1.0f) * 24.0f);
        double f = this.airship.getZ() + (double)((random.nextFloat() * 2.0f - 1.0f) * 64.0f);
        this.airship.getMoveControl().moveTo(d, e, f, 1.0);
    }
    @Override
    public void stop() {
        World level = this.airship.getWorld();
        if (level.isNight()){
            super.stop();
        }
    }
}

