/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.block.custom.crystal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.particle.ModParticles;

public class BuddingIceCrystalBlock
        extends IceCrystalBlock {
    public static final int GROW_CHANCE = 5;
    public static final int DURATION_EFFECT = 100;
    public static final int DISPLAY_CHANCE = 16;


    public BuddingIceCrystalBlock(Settings settings) {
        super(settings);
    }

    //default random is 5
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (random.nextInt(GROW_CHANCE) != 0) {
            return;
        }
        Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = null;

        if (BuddingIceCrystalBlock.canGrowIn(blockState)) {
            block = ModBlocks.SMALL_ICE_CRYSTAL_BUD;
        } else if (blockState.isOf(ModBlocks.SMALL_ICE_CRYSTAL_BUD) && blockState.get(FireCrystalClusterBlock.FACING) == direction) {
            block = ModBlocks.MEDIUM_ICE_CRYSTAL_BUD;
        } else if (blockState.isOf(ModBlocks.MEDIUM_ICE_CRYSTAL_BUD) && blockState.get(FireCrystalClusterBlock.FACING) == direction) {
            block = ModBlocks.LARGE_ICE_CRYSTAL_BUD;
        } else if (blockState.isOf(ModBlocks.LARGE_ICE_CRYSTAL_BUD) && blockState.get(FireCrystalClusterBlock.FACING) == direction) {
            block = ModBlocks.ICE_CRYSTAL_CLUSTER;
        }
        if (block != null) {
            BlockState blockState2 = block.getDefaultState().with(FireCrystalClusterBlock.FACING, direction)
                    .with(FireCrystalClusterBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
            world.setBlockState(blockPos, blockState2);
        }
    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(new StatusEffectInstance(ModEffects.FREEZE_EFFECT, 20, 3));

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
            world.addParticle(ModParticles.FREEZE_PARTICLE, (double)pos.getX() + 0.5, (double)pos.getY() + 2.0, (double)pos.getZ() + 0.5,
                    (double)((float)blockPos.getX() + random.nextFloat()) - 0.5,
                    (float)blockPos.getY() - random.nextFloat() - 1.0f,
                    (double)((float)blockPos.getZ() + random.nextFloat()) - 0.5);
        }
    }

}

