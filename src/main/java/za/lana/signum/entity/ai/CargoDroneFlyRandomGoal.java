/**
 * SIGNUM
 * MIT License
 * Lana
 * 2024
 * */
package za.lana.signum.entity.ai;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import za.lana.signum.entity.transport.CargoDroneEntity;

import java.util.EnumSet;

public  class CargoDroneFlyRandomGoal
        extends Goal {
    private final CargoDroneEntity cargoDrone;
    private final float range;

    public CargoDroneFlyRandomGoal(CargoDroneEntity cargoDrone, float flyrange) {
        this.cargoDrone = cargoDrone;
        this.range = flyrange;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        double f;
        double e;
        MoveControl moveControl = this.cargoDrone.getMoveControl();
        World level = this.cargoDrone.getWorld();
        if (!moveControl.isMoving() && level.isDay()) {
            System.out.println("CDroneTest:Flying Randomly");
            return true;
        }
        double d = moveControl.getTargetX() - this.cargoDrone.getX();
        double g = d * d + (e = moveControl.getTargetY() - this.cargoDrone.getY()) * e + (f = moveControl.getTargetZ() - this.cargoDrone.getZ()) * f;
        return g < 1.0 || g > 7200.0;
    }

    @Override
    public boolean shouldContinue() {
        return false;
    }

    @Override
    public void start() {
        Random random = this.cargoDrone.getRandom();
        double d = this.cargoDrone.getX() + (double)((random.nextFloat() * 2.0f - 1.0f) * this.range);
        double e = this.cargoDrone.getY() + (double)((random.nextFloat() * 2.0f - 1.0f) * this.range);
        double f = this.cargoDrone.getZ() + (double)((random.nextFloat() * 2.0f - 1.0f) * this.range);
        this.cargoDrone.getMoveControl().moveTo(d, e, f, 1.0);
    }
    @Override
    public void stop() {
        World level = this.cargoDrone.getWorld();
        if (level.isNight()){
            super.stop();
        }
    }
}

