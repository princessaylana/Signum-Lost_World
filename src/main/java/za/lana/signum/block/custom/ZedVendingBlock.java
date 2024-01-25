package za.lana.signum.block.custom;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.block.entity.ZedVendingBlockEntity;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.sound.ModSounds;

public class ZedVendingBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 32, 16);
    public ZedVendingBlock(Settings settings) {
        super(FabricBlockSettings.copy(Blocks.IRON_BLOCK));
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        //player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        return ActionResult.SUCCESS;
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        //return new ZedVendingBlockEntity(pos, state);
        return null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
    //
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    // TODO BLOCKENTITY, SCREENS, ETC

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double d = (double)pos.getX() + 0.5;
        double e = pos.getY();
        double f = (double)pos.getZ() + 0.5;
        if (random.nextDouble() < 0.1) {
            world.playSound(d, e, f, ModSounds.ZEDVENDING_BLOCK, SoundCategory.BLOCKS, 0.5f, 1.0f + random.nextFloat(), false);
        }
        Direction direction = state.get(FACING);
        Direction.Axis axis = direction.getAxis();
        double g = 0.52;
        double h = random.nextDouble() * 0.6 - 0.3;
        double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52 : h;
        double j = random.nextDouble() * 9.0 / 8.0;
        double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52 : h;
        world.addParticle(ModParticles.RED_SHROOM_PARTICLE, d + i, e + j, f + k, 0.0, 0.1, 0.0);
    }
}
