package za.lana.signum.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import za.lana.signum.block.ModBlocks;

public class CargoDroneRemember extends CargoDroneLandGoal {
    public CargoDroneRemember(MobEntity mob, double speed, int range) {
        super(mob, speed, range);
    }


}
