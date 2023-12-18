package za.lana.signum.entity.ai;

import com.google.common.collect.Lists;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestTypes;
import za.lana.signum.entity.transport.AirShipEntity;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class AirShipFindHomeGoal
        extends Goal {
    protected final Random random = Random.create();
    private final AirShipEntity mob;
    private final double speed;
    private BlockPos home;
    private final List<BlockPos> lastHomes = Lists.newArrayList();
    private final int distance;
    private boolean finished;

    public AirShipFindHomeGoal(AirShipEntity mob, double speed, int distance) {
        this.mob = mob;
        this.speed = speed;
        this.distance = distance;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        this.purgeMemory();
        World level = this.mob.getWorld();
        return  this.tryFindHome() && level.isDay() && this.mob.getTarget() == null;
    }

    private boolean tryFindHome() {
        ServerWorld serverWorld = (ServerWorld)this.mob.getWorld();
        BlockPos blockPos = this.mob.getBlockPos();
        Random Random = mob.getRandom();
        Optional<BlockPos> optional = serverWorld.getPointOfInterestStorage().getPosition(registryEntry ->
                registryEntry.matchesKey(PointOfInterestTypes.HOME), this::canLootHome,
                PointOfInterestStorage.OccupationStatus.ANY, blockPos, 48, Random);
        if (optional.isEmpty()) {
            return false;
        }
        this.home = optional.get().toImmutable();
        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (this.mob.getNavigation().isIdle()) {
            return false;
        }
        return this.mob.getTarget() == null && !this.home.isWithinDistance(this.mob.getPos(),
                this.mob.getWidth() + (float)this.distance) && !this.finished;
    }

    @Override
    public void stop() {
        if (this.home.isWithinDistance(this.mob.getPos(), this.distance)) {
            this.lastHomes.add(this.home);
        }
        World level = this.mob.getWorld();
        if (level.isNight()){
            super.stop();
        }

    }

    @Override
    public void start() {
        super.start();
        this.mob.getNavigation().startMovingTo(this.home.getX(), this.home.getY(), this.home.getZ(), this.speed);
        this.finished = false;
    }

    @Override
    public void tick() {
        if (this.mob.getNavigation().isIdle()) {
            Vec3d vec3d = Vec3d.ofBottomCenter(this.home);
            Vec3d vec3d2 = NoPenaltyTargeting.findTo(this.mob, 16, 7, vec3d, 0.3141592741012573);
            if (vec3d2 == null) {
                vec3d2 = NoPenaltyTargeting.findTo(this.mob, 8, 7, vec3d, 1.5707963705062866);
            }
            if (vec3d2 == null) {
                this.finished = true;
                return;
            }
            this.mob.getNavigation().startMovingTo(vec3d2.x, vec3d2.y, vec3d2.z, this.speed);
        }
    }

    private boolean canLootHome(BlockPos pos) {
        return false;
    }

    private void purgeMemory() {
        if (this.lastHomes.size() > 2) {
            this.lastHomes.remove(0);
        }
    }
}
