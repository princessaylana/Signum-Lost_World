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
import net.minecraft.entity.AreaEffectCloudEntity;
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
import org.jetbrains.annotations.NotNull;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.block.custom.modore.ElementZeroOreBlock;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

public class BuddingTiberiumBlock
        extends TiberiumBlock {
    public static final int GROW_CHANCE = 5;
    public static final int DURATION_EFFECT = 100;
    public static final int DISPLAY_CHANCE = GROW_CHANCE * DURATION_EFFECT;

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
            // BuddingTiberiumBlock.spawnParticles(world, pos);
            // world.playSound(null, pos, ModSounds.TIBERIUM_WALK, SoundCategory.BLOCKS, 1.0f, 0.5f);
        }
        super.onSteppedOn(world, pos, state, entity);
    }
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        //super.randomDisplayTick(state, world, pos, random);
        if (random.nextInt(DISPLAY_CHANCE) != 0) {
            double d = (double) pos.getX() + random.nextDouble();
            double e = (double) pos.getY() + 1.5f; // 0.8f
            double f = (double) pos.getZ() + random.nextDouble();
            world.addParticle(ModParticles.TIBERIUM_PARTICLE, d, e, f, 0.0F, 2.5F, 0.0F);
            //world.playSound(d, e, f, ModSounds.TIBERIUM_AMBIENT, SoundCategory.BLOCKS, 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false);
            //world.playSound(d, e, f, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false);
        }
        /**
         * // spawn cloud
         while (random.nextInt(50) != 0) {
         double d = (double) pos.getX() + random.nextDouble();
         double e = (double) pos.getY() + 2.5f ; // 0.8f
         double f = (double) pos.getZ() + random.nextDouble();
         AreaEffectCloudEntity areaEffectCloudEntity = getAreaEffectCloudEntity(world, pos);
         world.spawnEntity(areaEffectCloudEntity);
         world.playSound(d, e, f, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 0.2f + random.nextFloat() * 0.2f, 0.9f + random.nextFloat() * 0.15f, false);}
         **/
    }

}

