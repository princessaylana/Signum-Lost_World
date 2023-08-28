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
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.TiberiumWormEntity;

import java.util.Map;
import java.util.function.Supplier;

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
        this.regularBlock = regularBlock;
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
        TiberiumWormEntity tiberiumWormEntity = ModEntities.TIBERIUM_WORM.create(world);
        world.spawnEntity(tiberiumWormEntity);
        tiberiumWormEntity.playSpawnEffects();


    }
}

