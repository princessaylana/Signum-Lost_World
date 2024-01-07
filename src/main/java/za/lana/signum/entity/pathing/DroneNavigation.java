
package za.lana.signum.entity.pathing;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.BirdPathNodeMaker;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DroneNavigation extends EntityNavigation {
    public DroneNavigation(MobEntity mobEntity, World world) {
        super(mobEntity, world);
    }

    protected PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new BirdPathNodeMaker();
        this.nodeMaker.setCanEnterOpenDoors(true);
        return new PathNodeNavigator(this.nodeMaker, range);
    }

    protected boolean canPathDirectlyThrough(Vec3d origin, Vec3d target) {
        return doesNotCollide(this.entity, origin, target, true);
    }

    protected boolean isAtValidPosition() {
        return this.canSwim() && this.entity.isInFluid() || !this.entity.hasVehicle();
    }

    protected Vec3d getPos() {
        return this.entity.getPos();
    }

    public Path findPathTo(Entity entity, int distance) {
        return this.findPathTo(entity.getBlockPos(), distance);
    }

    public void tick() {
        ++this.tickCount;
        if (this.inRecalculationCooldown) {
            this.recalculatePath();
        }

        if (!this.isIdle()) {
            Vec3d vec3d;
            if (this.isAtValidPosition()) {
                this.continueFollowingPath();
            } else if (this.currentPath != null && !this.currentPath.isFinished()) {
                vec3d = this.currentPath.getNodePosition(this.entity);
                if (this.entity.getBlockX() == MathHelper.floor(vec3d.x) && this.entity.getBlockY() == MathHelper.floor(vec3d.y) && this.entity.getBlockZ() == MathHelper.floor(vec3d.z)) {
                    this.currentPath.next();
                }
            }

            DebugInfoSender.sendPathfindingData(this.world, this.entity, this.currentPath, this.nodeReachProximity);
            if (!this.isIdle()) {
                vec3d = this.currentPath.getNodePosition(this.entity);
                this.entity.getMoveControl().moveTo(vec3d.x, vec3d.y, vec3d.z, this.speed);
            }
        }
    }

    public void setCanPathThroughDoors(boolean canPathThroughDoors) {
        this.nodeMaker.setCanOpenDoors(canPathThroughDoors);
    }

    public boolean canEnterOpenDoors() {
        return this.nodeMaker.canEnterOpenDoors();
    }

    public void setCanEnterOpenDoors(boolean canEnterOpenDoors) {
        this.nodeMaker.setCanEnterOpenDoors(canEnterOpenDoors);
    }

    public boolean method_35129() {
        return this.nodeMaker.canEnterOpenDoors();
    }

    public boolean isValidPosition(BlockPos pos) {
        return this.world.getBlockState(pos).hasSolidTopSurface(this.world, pos, this.entity);
    }
}
