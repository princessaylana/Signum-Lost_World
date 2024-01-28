
package za.lana.signum.entity.ai;

import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ElveGuardWanderPOIGoal
        extends WanderAroundGoal {
    private static final int HORIZONTAL_RANGE = 20;
    private static final int VERTICAL_RANGE = 14;

    public ElveGuardWanderPOIGoal(PathAwareEntity entity, double speed, boolean canDespawn) {
        super(entity, speed, 10, canDespawn);
    }

    @Override
    public boolean canStart() {
        BlockPos blockPos;
        ServerWorld serverWorld = (ServerWorld)this.mob.getWorld();
        if (serverWorld.isNearOccupiedPointOfInterest(this.mob.getBlockPos())) {
            return false;
        }
        World level = this.mob.getWorld();
        if (level.isDay()) {
            return super.canStart();
        }
        return false;
    }

    @Override
    @Nullable
    protected Vec3d getWanderTarget() {
        BlockPos blockPos;
        ChunkSectionPos chunkSectionPos;
        ServerWorld serverWorld = (ServerWorld)this.mob.getWorld();
        ChunkSectionPos chunkSectionPos2 = LookTargetUtil.getPosClosestToOccupiedPointOfInterest(serverWorld, chunkSectionPos = ChunkSectionPos.from(this.mob.getBlockPos()), 2);
        if (chunkSectionPos2 != chunkSectionPos) {
            return NoPenaltyTargeting.findTo(this.mob, HORIZONTAL_RANGE, VERTICAL_RANGE, Vec3d.ofBottomCenter(chunkSectionPos2.getCenterPos()), 1.5707963705062866);
        }
        return null;
    }
}

