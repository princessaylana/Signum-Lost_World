
package za.lana.signum.entity.ai;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.mob.PathAwareEntity;

public class AvoidDarknessGoal
        extends Goal {
    private final PathAwareEntity mob;

    public AvoidDarknessGoal(PathAwareEntity mob) {
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        return this.mob.getWorld().isNight() && this.mob.getEquippedStack(EquipmentSlot.HEAD).isEmpty() && NavigationConditions.hasMobNavigation(this.mob);
    }

    @Override
    public void start() {
        ((MobNavigation)this.mob.getNavigation()).setAvoidSunlight(false);
    }

    @Override
    public void stop() {
        if (NavigationConditions.hasMobNavigation(this.mob)) {
            ((MobNavigation)this.mob.getNavigation()).setAvoidSunlight(true);
        }
    }
}

