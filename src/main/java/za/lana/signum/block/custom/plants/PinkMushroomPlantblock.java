/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.block.custom.plants;

import net.minecraft.block.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import za.lana.signum.world.ModConfiguredFeatures;

import java.util.Optional;

// this is the small mushroom plant block
public class PinkMushroomPlantblock extends PlantBlock implements Fertilizable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
    private final RegistryKey<ConfiguredFeature<?, ?>> featureKey = ModConfiguredFeatures.HUGE_TOXIC_MUSHROOM_KEY;

    public PinkMushroomPlantblock(Settings settings, RegistryKey<ConfiguredFeature<?, ?>> hugeToxicMushroomKey) {
        super(settings);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(25) == 0) {
            int i = 5;
            int j = 4;
            for (BlockPos blockPos : BlockPos.iterate(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
                if (!world.getBlockState(blockPos).isOf(this) || --i > 0) continue;
                return;
            }
            BlockPos blockPos2 = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            for (int k = 0; k < 4; ++k) {
                if (world.isAir(blockPos2) && state.canPlaceAt(world, blockPos2)) {
                    pos = blockPos2;
                }
                blockPos2 = pos.add(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
            }
            if (world.isAir(blockPos2) && state.canPlaceAt(world, blockPos2)) {
                world.setBlockState(blockPos2, state, Block.NOTIFY_LISTENERS);
            }
        }
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOpaqueFullCube(world, pos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isIn(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return true;
        }
        // need to change the light level equal to nighttime?
        return world.getBaseLightLevel(pos, 0) < 13 && this.canPlantOnTop(blockState, world, blockPos);
    }

    public boolean trySpawningBigMushroom(ServerWorld world, BlockPos pos, BlockState state, Random random) {
        Optional<RegistryEntry.Reference<ConfiguredFeature<?, ?>>> optional = world.getRegistryManager().get(RegistryKeys.CONFIGURED_FEATURE).getEntry(this.featureKey);
        if (optional.isEmpty()) {
            return false;
        }
        world.removeBlock(pos, false);
        if (((ConfiguredFeature<?, ?>)((RegistryEntry<?>)optional.get()).value()).generate(world, world.getChunkManager().getChunkGenerator(), random, pos)) {
            return true;
        }
        world.setBlockState(pos, state, Block.NOTIFY_ALL);
        return false;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return (double)random.nextFloat() < 0.4;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.trySpawningBigMushroom(world, pos, state, random);

    }
}
