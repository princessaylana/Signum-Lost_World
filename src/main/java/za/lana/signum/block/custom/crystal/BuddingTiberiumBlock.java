/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.block.custom.crystal;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.particle.ModParticles;

public class BuddingTiberiumBlock
        extends TiberiumBlock {
    public static final int GROW_CHANCE = 5;
    public static final int DURATION_EFFECT = 100;
    public static final int DISPLAY_CHANCE = 16;

    public BuddingTiberiumBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    //default random is 5
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (random.nextInt(5) != 0) {
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
            BlockState blockState2 = block.getDefaultState().with(TiberiumClusterBlock.FACING, direction)
                    .with(TiberiumClusterBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
            world.setBlockState(blockPos, blockState2);
        }
        for(int x = 0; x < 2; ++x) {
            for(int y = 0; y < 24; ++y) {
                world.addParticle(ModParticles.TIBERIUM_PARTICLE, pos.getX(), pos.getY(), pos.getZ(),
                        Math.cos(x*20) * 0.15d, Math.cos(y*20) * 0.15d, Math.sin(x*20) * 0.15d);}
        }
    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(new StatusEffectInstance(ModEffects.TIBERIUM_POISON, 20, 3));
            // world.playSound(null, pos, ModSounds.TIBERIUM_WALK, SoundCategory.BLOCKS, 1.0f, 0.5f);
        }
        super.onSteppedOn(world, pos, state, entity);
    }
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        //super.randomDisplayTick(state, world, pos, random);
        Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = null;
        if (random.nextInt(DISPLAY_CHANCE) == 0) {
            BuddingExoticCrystalBlock.canGrowIn(blockState);
            world.addParticle(ModParticles.TIBERIUM_PARTICLE, (double)pos.getX() + 0.5, (double)pos.getY() + 2.0, (double)pos.getZ() + 0.5,
                    (double)((float)blockPos.getX() + random.nextFloat()) - 0.5,
                    (float)blockPos.getY() - random.nextFloat() - 1.0f,
                    (double)((float)blockPos.getZ() + random.nextFloat()) - 0.5);
        }
    }

}

