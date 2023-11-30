package za.lana.signum.entity.ai;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.goal.FlyGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class PidgeonSitOnTreeGoal extends FlyGoal {

    private static final int SEARCHRANGE = 30 ; // default is 15

    public PidgeonSitOnTreeGoal(PathAwareEntity pathAwareEntity, double d) {
        super(pathAwareEntity, d);
    }

    @Override
    public boolean canStart() {
        World level = this.mob.getWorld();
        return level.isNight();
        //&& this.mob.isOnGround()
    }

    @Override
    public void start() {
        super.start();
    }


    @Nullable
    protected Vec3d getWanderTarget() {
        Vec3d vec3d = null;
        if (this.mob.isTouchingWater()) {
            vec3d = FuzzyTargeting.find(this.mob, SEARCHRANGE, SEARCHRANGE);
        }
        if (this.mob.getRandom().nextFloat() >= this.probability) {
            vec3d = this.locateTree();
        }
        return vec3d == null ? super.getWanderTarget() : vec3d;
    }
    private Vec3d locateTree() {
        BlockPos blockPos = this.mob.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();
        Iterable<BlockPos> iterable = BlockPos.iterate(
                MathHelper.floor(this.mob.getX() - 3.0),
                MathHelper.floor(this.mob.getY() - 6.0),
                MathHelper.floor(this.mob.getZ() - 3.0),
                MathHelper.floor(this.mob.getX() + 3.0),
                MathHelper.floor(this.mob.getY() + 6.0),
                MathHelper.floor(this.mob.getZ() + 3.0));

        Iterator<BlockPos> ditto = iterable.iterator();
        BlockPos tree;
        boolean isTree;
        do {
            do {
                if (!ditto.hasNext()) {
                    return null;
                }
                tree = ditto.next();
            } while(blockPos.equals(tree));
            BlockState blockState = this.mob.getWorld().getBlockState(mutable2.set(tree, Direction.DOWN));
            isTree = blockState.getBlock() instanceof LeavesBlock || blockState.isIn(BlockTags.LOGS);

        } while(!isTree || !this.mob.getWorld().isAir(tree) || !this.mob.getWorld().isAir(mutable.set(tree, Direction.UP)));

        this.mob.getNavigation().startMovingTo(this.mob.getX(),this.mob.getY(), this.mob.getZ(),this.speed);
        this.mob.getPathfindingFavor(tree);
        return Vec3d.ofBottomCenter(tree);
    }

    @Override
    public void stop() {
        World level = this.mob.getWorld();
        if (level.isDay()){
            super.stop();
        }
        super.stop();
    }
}








