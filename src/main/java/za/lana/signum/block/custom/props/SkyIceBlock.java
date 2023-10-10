/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.block.custom.props;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TransparentBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.particle.ModParticles;

public class SkyIceBlock extends TransparentBlock {
    public static final int MELT_CHANCE = 3;
    public SkyIceBlock(AbstractBlock.Settings settings) {
        super(settings);
    }
    // block this turns into when it melts
    public static BlockState getMeltedState() {
        return Blocks.POWDER_SNOW.getDefaultState();
    }
    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state,
                           @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) == 0) {
            if (world.getDimension().ultrawarm()) {
                world.removeBlock(pos, false);
                return;
            }
            BlockState blockState = world.getBlockState(pos.down());
            if (blockState.blocksMovement() || blockState.isLiquid()) {
                world.setBlockState(pos, SkyIceBlock.getMeltedState());
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (random.nextInt(MELT_CHANCE) != 0) {
            return;
        }
        if (world.getLightLevel(LightType.BLOCK, pos) > 11 - state.getOpacity(world, pos)) {
            this.melt(state, world, pos);
        }
    }

    // WILL REWRITE THIS TO ONLY MELT IN NON SNOWY BIOMES,HIGHER TEMPERATURE?
    protected void melt(BlockState state, World world, BlockPos pos) {
        BlockPos blockPos2 = pos;
        //if (world.getBiome(pos).isIn(BiomeTags.)) {
        if (world.getDimension().ultrawarm()) {

            world.removeBlock(pos, false);
            return;
        }
        world.setBlockState(pos, SkyIceBlock.getMeltedState());
        world.updateNeighbor(pos, SkyIceBlock.getMeltedState().getBlock(), pos);
    }
    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity) {
            if (world.isClient) {
                boolean bl;
                Random random = world.getRandom();
                bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();
                if (bl && random.nextBoolean()) {
                    world.addParticle(ModParticles.FREEZE_PARTICLE, entity.getX(), pos.getY() + 2, entity.getZ(), MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05f, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f);
                }
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }
}
