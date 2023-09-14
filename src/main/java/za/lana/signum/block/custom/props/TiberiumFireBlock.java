/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.block.custom.props;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import za.lana.signum.block.ModBlocks;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TiberiumFireBlock
        extends AbstractFireBlock {
    public static final IntProperty AGE = Properties.AGE_15;
    public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    public static final BooleanProperty EAST = ConnectingBlock.EAST;
    public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    public static final BooleanProperty WEST = ConnectingBlock.WEST;
    public static final BooleanProperty UP = ConnectingBlock.UP;
    private static final Map<Direction, BooleanProperty> DIRECTION_PROPERTIES =
            ConnectingBlock.FACING_PROPERTIES.entrySet().stream().filter(entry -> entry.getKey() != Direction.DOWN).collect(Util.toMap());
    private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private final Map<BlockState, VoxelShape> shapesByState;
    private final Object2IntMap<Block> burnChances = new Object2IntOpenHashMap<>();
    private final Object2IntMap<Block> spreadChances = new Object2IntOpenHashMap<>();

    public TiberiumFireBlock(AbstractBlock.Settings settings) {
        super(settings, 1.0f);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0).with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false));
        this.shapesByState = ImmutableMap.copyOf(this.stateManager.getStates().stream().filter(state -> state.get(AGE) == 0).collect(Collectors.toMap(Function.identity(), TiberiumFireBlock::getShapeForState)));
    }

    private static VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelShape = VoxelShapes.empty();
        if (state.get(UP)) {
            voxelShape = UP_SHAPE;
        }
        if (state.get(NORTH)) {
            voxelShape = VoxelShapes.union(voxelShape, NORTH_SHAPE);
        }
        if (state.get(SOUTH)) {
            voxelShape = VoxelShapes.union(voxelShape, SOUTH_SHAPE);
        }
        if (state.get(EAST)) {
            voxelShape = VoxelShapes.union(voxelShape, EAST_SHAPE);
        }
        if (state.get(WEST)) {
            voxelShape = VoxelShapes.union(voxelShape, WEST_SHAPE);
        }
        return voxelShape.isEmpty() ? BASE_SHAPE : voxelShape;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, NORTH, EAST, SOUTH, WEST, UP);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (this.canPlaceAt(state, world, pos)) {
            return this.getStateWithAge(world, pos, state.get(AGE));
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.shapesByState.get(state.with(AGE, 0));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getStateForPosition(ctx.getWorld(), ctx.getBlockPos());
    }

    protected BlockState getStateForPosition(BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        if (this.isFlammable(blockState) || blockState.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
            return this.getDefaultState();
        }
        BlockState blockState2 = this.getDefaultState();
        for (Direction direction : Direction.values()) {
            BooleanProperty booleanProperty = DIRECTION_PROPERTIES.get(direction);
            if (booleanProperty == null) continue;
            blockState2 = blockState2.with(booleanProperty, this.isFlammable(world.getBlockState(pos.offset(direction))));
        }
        return blockState2;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP) || this.areBlocksAroundFlammable(world, pos);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean bl2;
        world.scheduleBlockTick(pos, this, TiberiumFireBlock.getFireTickDelay(world.random));
        if (!world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
            return;
        }
        if (!state.canPlaceAt(world, pos)) {
            world.removeBlock(pos, false);
        }
        BlockState blockState = world.getBlockState(pos.down());
        boolean bl = blockState.isIn(world.getDimension().infiniburn());
        int i = state.get(AGE);
        if (!bl && world.isRaining() && this.isRainingAround(world, pos) && random.nextFloat() < 0.2f + (float)i * 0.03f) {
            world.removeBlock(pos, false);
            return;
        }
        int j = Math.min(15, i + random.nextInt(3) / 2);
        if (i != j) {
            state = state.with(AGE, j);
            world.setBlockState(pos, state, Block.NO_REDRAW);
        }
        if (!bl) {
            if (!this.areBlocksAroundFlammable(world, pos)) {
                BlockPos blockPos = pos.down();
                if (!world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP) || i > 3) {
                    world.removeBlock(pos, false);
                }
                return;
            }
            if (i == 15 && random.nextInt(4) == 0 && !this.isFlammable(world.getBlockState(pos.down()))) {
                world.removeBlock(pos, false);
                return;
            }
        }
        int k = (bl2 = world.getBiome(pos).isIn(BiomeTags.INCREASED_FIRE_BURNOUT)) ? -50 : 0;
        this.trySpreadingFire(world, pos.east(), 300 + k, random, i);
        this.trySpreadingFire(world, pos.west(), 300 + k, random, i);
        this.trySpreadingFire(world, pos.down(), 250 + k, random, i);
        this.trySpreadingFire(world, pos.up(), 250 + k, random, i);
        this.trySpreadingFire(world, pos.north(), 300 + k, random, i);
        this.trySpreadingFire(world, pos.south(), 300 + k, random, i);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int l = -1; l <= 1; ++l) {
            for (int m = -1; m <= 1; ++m) {
                for (int n = -1; n <= 4; ++n) {
                    if (l == 0 && n == 0 && m == 0) continue;
                    int o = 100;
                    if (n > 1) {
                        o += (n - 1) * 100;
                    }
                    mutable.set(pos, l, n, m);
                    int p = this.getBurnChance(world, mutable);
                    if (p <= 0) continue;
                    int q = (p + 40 + world.getDifficulty().getId() * 7) / (i + 30);
                    if (bl2) {
                        q /= 2;
                    }
                    if (q <= 0 || random.nextInt(o) > q || world.isRaining() && this.isRainingAround(world, mutable)) continue;
                    int r = Math.min(15, i + random.nextInt(5) / 4);
                    world.setBlockState(mutable, this.getStateWithAge(world, mutable, r), Block.NOTIFY_ALL);
                }
            }
        }this.onRandomTick(world, pos, state, random);
    }

    private void onRandomTick(World world, BlockPos pos, BlockState state, Random random) {
        if (!world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
            return;
        }
        int i = random.nextInt(3);
        if (i > 0) {
            BlockPos blockPos = pos;
            for (int j = 0; j < i; ++j) {
                if (!world.canSetBlock(blockPos = blockPos.add(random.nextInt(3) - 1, 1, random.nextInt(3) - 1))) {
                    return;
                }
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.isAir()) {
                    if (!this.canLightFire(world, blockPos)) continue;
                    world.setBlockState(blockPos, TiberiumFireBlock.getState(world, blockPos));
                    return;
                }
            }
        } else {
            for (int k = 0; k < 3; ++k) {
                BlockPos blockPos2 = pos.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
                if (!world.canSetBlock(blockPos2)) {
                    return;
                }
                if (!world.isAir(blockPos2.up()) || !this.hasBurnableBlock(world, blockPos2)) continue;
                world.setBlockState(blockPos2.up(), TiberiumFireBlock.getState(world, blockPos2));
            }
        }
    }

    private boolean canLightFire(WorldView world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (!this.hasBurnableBlock(world, pos.offset(direction))) continue;
            return true;
        }
        return false;
    }

    private boolean hasBurnableBlock(WorldView world, BlockPos pos) {
        if (pos.getY() >= world.getBottomY() && pos.getY() < world.getTopY()) {
            return false;
        }
        return world.getBlockState(pos).isBurnable();
    }

    protected boolean isRainingAround(World world, BlockPos pos) {
        return world.hasRain(pos) || world.hasRain(pos.west()) || world.hasRain(pos.east()) || world.hasRain(pos.north()) || world.hasRain(pos.south());
    }

    private int getSpreadChance(BlockState state) {
        if (state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED)) {
            return 0;
        }
        return this.spreadChances.getInt(state.getBlock());
    }

    private void trySpreadingFire(World world, BlockPos pos, int spreadFactor, Random random, int currentAge) {
        int i = this.getSpreadChance(world.getBlockState(pos));
        if (random.nextInt(spreadFactor) < i) {
            BlockState blockState = world.getBlockState(pos);
            if (random.nextInt(currentAge + 10) < 5 && !world.hasRain(pos)) {
                int j = Math.min(currentAge + random.nextInt(5) / 4, 15);
                world.setBlockState(pos, this.getStateWithAge(world, pos, j), Block.NOTIFY_ALL);
            } else {
                world.removeBlock(pos, false);
            }
            Block block = blockState.getBlock();
            if (block instanceof TntBlock) {
                TntBlock.primeTnt(world, pos);
            }
        }
    }

    private BlockState getStateWithAge(WorldAccess world, BlockPos pos, int age) {
        BlockState blockState = TiberiumFireBlock.getState(world, pos);
        if (blockState.isOf(ModBlocks.TIBERIUM_FIRE)) {
            return blockState.with(AGE, age);
        }
        return blockState;
    }

    private boolean areBlocksAroundFlammable(BlockView world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (!this.isFlammable(world.getBlockState(pos.offset(direction)))) continue;
            return true;
        }
        return false;
    }

    private int getBurnChance(WorldView world, BlockPos pos) {
        if (!world.isAir(pos)) {
            return 0;
        }
        int i = 0;
        for (Direction direction : Direction.values()) {
            BlockState blockState = world.getBlockState(pos.offset(direction));
            i = Math.max(this.getSpreadChance(blockState), i);
        }
        return i;
    }

    @Override
    protected boolean isFlammable(BlockState state) {
        return this.getSpreadChance(state) > 0;
        //return true;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        world.scheduleBlockTick(pos, this, TiberiumFireBlock.getFireTickDelay(world.random));
    }

    private static int getFireTickDelay(Random random) {
        return 30 + random.nextInt(10);
    }
}

