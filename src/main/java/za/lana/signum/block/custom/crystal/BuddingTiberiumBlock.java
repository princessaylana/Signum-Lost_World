/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.block.custom.crystal;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import za.lana.signum.block.ModBlocks;

import java.util.List;
import java.util.Optional;
import java.util.function.ToIntFunction;

public class BuddingTiberiumBlock
        extends TiberiumBlock {
    public static final int GROW_CHANCE = 5;
    private static final Direction[] DIRECTIONS = Direction.values();

    public BuddingTiberiumBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    //default random is 5
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (random.nextInt(3) != 0) {
            return;
        }
        Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = null;
        if (BuddingTiberiumBlock.canGrowIn(blockState)) {
            block = ModBlocks.SMALL_TIBERIUM_BUD;
        } else if (blockState.isOf(ModBlocks.SMALL_TIBERIUM_BUD) && blockState.get(TiberiumClusterBlock.FACING) == direction) {
            block = ModBlocks.MEDIUM_TIBERIUM_BUD;
        } else if (blockState.isOf(ModBlocks.MEDIUM_TIBERIUM_BUD) && blockState.get(TiberiumClusterBlock.FACING) == direction) {
            block = ModBlocks.LARGE_TIBERIUM_BUD;
        } else if (blockState.isOf(ModBlocks.LARGE_TIBERIUM_BUD) && blockState.get(TiberiumClusterBlock.FACING) == direction) {
            block = ModBlocks.TIBERIUM_CLUSTER;
        }
        if (block != null) {
            BlockState blockState2 = (BlockState)((BlockState)block.getDefaultState().with(TiberiumClusterBlock.FACING, direction)).with(TiberiumClusterBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
            world.setBlockState(blockPos, blockState2);
        }
    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8;
    }
}

