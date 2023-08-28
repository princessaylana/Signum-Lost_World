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
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

public class BuddingTiberiumBlock
        extends TiberiumBlock {
    public static final int GROW_CHANCE = 5;


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
    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(new StatusEffectInstance(ModEffects.TIBERIUM_POISON, 20, 3));
            BuddingTiberiumBlock.spawnParticles(world, pos);
            //world.playSound(null, pos, ModSounds.TIBERIUM_WALK, SoundCategory.BLOCKS, 1.0f, 0.5f);
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    private static void spawnParticles(World world, BlockPos pos) {
        double d = 0.5625;
        Random random = world.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos = pos.offset(direction);
            Direction.Axis axis = direction.getAxis();
            double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double) direction.getOffsetX() : (double) random.nextFloat();
            double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double) direction.getOffsetY() : (double) random.nextFloat();
            double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double) direction.getOffsetZ() : (double) random.nextFloat();
            world.addParticle(ModParticles.TIBERIUM_PARTICLE, (double) pos.getX() + e, (double) pos.getY() + f, (double) pos.getZ() + g, 0.5F, 1.5F, 0.5F);

            //world.playSound(null, blockPos, ModSounds.TIBERIUM_AMBIENT, SoundCategory.BLOCKS, 1.0f, 0.5f);
            //world.playSound(null, blockPos, ModSounds.TIBERIUM_AMBIENT, SoundCategory.BLOCKS, 1.0f, 0.5f + world.random.nextFloat() * 0.5f);
        }
    }

}

