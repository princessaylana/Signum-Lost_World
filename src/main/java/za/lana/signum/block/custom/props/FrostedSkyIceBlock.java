/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */

package za.lana.signum.block.custom.props;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import za.lana.signum.particle.ModParticles;

public class FrostedSkyIceBlock extends SkyIceBlock{
    public static final int MAX_AGE = 3;
    public static final IntProperty AGE = Properties.AGE_3;
    private static final int NEIGHBORS_CHECKED_ON_SCHEDULED_TICK = 4;
    private static final int NEIGHBORS_CHECKED_ON_NEIGHBOR_UPDATE = 2;
    public FrostedSkyIceBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.scheduledTick(state, world, pos, random);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if ((random.nextInt(5) == 0 || this.canMelt(world, pos)) && world.getLightLevel(pos) >
                11 - state.get(AGE) - state.getOpacity(world, pos) && this.increaseAge(state, world, pos)) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (Direction direction : Direction.values()) {
                mutable.set(pos, direction);
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isOf(this) || this.increaseAge(blockState, world, mutable)) continue;
                world.scheduleBlockTick(mutable, this, MathHelper.nextInt(random, 60, 120));
            }
            return;
        }
        world.scheduleBlockTick(pos, this, MathHelper.nextInt(random, 60, 120));
    }
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            if (world.isClient) {
                boolean bl;
                Random random = world.getRandom();
                bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
                if (bl && random.nextBoolean()) {
                    world.addParticle(ModParticles.FREEZE_PARTICLE, entity.getX(), pos.getY() + 2, entity.getZ(), MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05f, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f);
                }
            }

            BlockPos.Mutable mutable = new BlockPos.Mutable();
            this.increaseAge(state, world, mutable);
        }
        super.onSteppedOn(world, pos, state, entity);
    }
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            if (world.isClient) {
                boolean bl;
                Random random = world.getRandom();
                bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
                if (bl && random.nextBoolean()) {
                    world.addParticle(ModParticles.FREEZE_PARTICLE, entity.getX(), pos.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05f, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f);
                }
            }
        }
    }

    private boolean increaseAge(BlockState state, World world, BlockPos pos) {
        int i = state.get(AGE);
        if (i < 3) {
            world.setBlockState(pos, state.with(AGE, i + 1), Block.NOTIFY_LISTENERS);
            return false;
        }
        this.melt(state, world, pos);
        return true;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (sourceBlock.getDefaultState().isOf(this) && this.canMelt(world, pos)) {
            this.melt(state, world, pos);
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    private boolean canMelt(BlockView world, BlockPos pos) {
        int i = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : Direction.values()) {

            mutable.set(pos, direction);
            if (!world.getBlockState(mutable).isOf(this) || ++i < 4) continue;
            return false;
        }
        return true;
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }



}
