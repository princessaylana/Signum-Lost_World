/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.block.custom.props;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import za.lana.signum.Signum;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.TiberiumWormEntity;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static net.minecraft.block.SnowyBlock.SNOWY;

public class BlightBlock
        extends Block {
    private Block regularBlock = this;
    private static final Map<Block, Block> REGULAR_TO_INFESTED_BLOCK = Maps.newIdentityHashMap();
    private static final Map<BlockState, BlockState> REGULAR_TO_INFESTED_STATE = Maps.newIdentityHashMap();
    private static final Map<BlockState, BlockState> INFESTED_TO_REGULAR_STATE = Maps.newIdentityHashMap();

    /**
     * Creates an infested block
     *
     * @param settings block settings
     */
    public BlightBlock(Settings settings) {
        super(settings);
        // .hardness(regularBlock.getHardness() / 2.0f).resistance(0.75f);
        REGULAR_TO_INFESTED_BLOCK.put(regularBlock, this);
    }

    public Block getRegularBlock() {

        return this.regularBlock;
    }

    public static boolean isInfestable(BlockState block) {
        return REGULAR_TO_INFESTED_BLOCK.containsKey(block.getBlock());
    }

    private void spawnTiberiumWorm(ServerWorld world, BlockPos pos) {
        TiberiumWormEntity tiberiumWormEntity = ModEntities.TIBERIUM_WORM.create(world);
        if (tiberiumWormEntity != null) {
            tiberiumWormEntity.refreshPositionAndAngles((double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5, 0.0f, 0.0f);
            world.spawnEntity(tiberiumWormEntity);
            tiberiumWormEntity.playSpawnEffects();
        }
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);
        if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) == 0) {
            this.spawnTiberiumWorm(world, pos);
        }
    }

    public static BlockState fromRegularState(BlockState regularState) {
        return BlightBlock.copyProperties(REGULAR_TO_INFESTED_STATE, regularState, () ->
                REGULAR_TO_INFESTED_BLOCK.get(regularState.getBlock()).getDefaultState());
    }

    public BlockState toRegularState(BlockState infestedState) {
        return BlightBlock.copyProperties(INFESTED_TO_REGULAR_STATE, infestedState, () ->
                this.getRegularBlock().getDefaultState());
    }

    private static BlockState copyProperties(Map<BlockState, BlockState> stateMap, BlockState fromState, Supplier<BlockState> toStateSupplier) {
        return stateMap.computeIfAbsent(fromState, infestedState -> {
            BlockState blockState = toStateSupplier.get();
            for (Property<?> property : infestedState.getProperties()) {
                //blockState = blockState.contains(property) ? blockState.with(property, infestedState.get(property)) : blockState;
            }
            return blockState;
        });
    }
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(5) != 0) {
            return;
        }
        Direction direction = Direction.UP;
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = null;
        if (BlightBlock.canSpawnWorm(blockState)) {
            TiberiumWormEntity tiberiumWormEntity = new TiberiumWormEntity(ModEntities.TIBERIUM_WORM, world);
            world.spawnEntity(tiberiumWormEntity);
            tiberiumWormEntity.playSpawnEffects();
        }
        if (world.getLightLevel(pos.up()) >= 9) {
            BlockState blockState2 = this.getDefaultState();
            for (int i = 0; i < 4; ++i) {
                BlockPos blockPos2 = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                if (!world.getBlockState(blockPos2).isOf(Blocks.DIRT) || !BlightBlock.canSpread(blockState2, world, blockPos2)) continue;
                world.setBlockState(blockPos2, blockState2.with(SNOWY, world.getBlockState(blockPos2.up()).isOf(Blocks.SNOW)));
            }
        }
    }

    private static boolean canSpread(BlockState blockState2, ServerWorld world, BlockPos blockPos2) {
        //
        return false;
    }

    public static boolean canSpawnWorm(BlockState state) {
        return state.isAir() || state.isOf(ModBlocks.BLIGHT_BLOCK);
    }
    @Override
    public void appendTooltip(ItemStack stack, BlockView blockGetter, List<Text> tooltip, TooltipContext tooltipFlag) {
        tooltip.add(Text.translatable("block." + Signum.MOD_ID + ".blight_block.tooltip"));

        super.appendTooltip(stack, blockGetter, tooltip, tooltipFlag);
    }
}

