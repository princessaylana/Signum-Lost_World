/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.block.entity.VaultBlockEntity;

import java.util.List;
import java.util.Objects;

// TODO WORK IN PROGRESS
public class VaultBlock extends BlockWithEntity implements BlockEntityProvider {
    //public static final String VAULT_TEST = "message.signum.vault_block.test";
    public static final String NON_BREAKABLE = "message.signum.vault_block.break";
    public static final String ACCESS_GRANTED = "message.signum.vault_block.granted";
    public static final String ACCESS_DENIED = "message.signum.vault_block.denied";

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 16);

    public VaultBlock(Settings settings) {
        super(settings);
    }
    // SHAPE AND PLACEMENT
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
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
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof VaultBlockEntity) {
                ItemScatterer.spawn(world, pos, (VaultBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity owner, ItemStack itemStack) {
        PlayerEntity player = (PlayerEntity)owner;
        ((VaultBlockEntity) Objects.requireNonNull(world.getBlockEntity(pos))).setOwnerUUID(player.getUuidAsString());
        ((VaultBlockEntity) Objects.requireNonNull(world.getBlockEntity(pos))).setOwnerName(player.getDisplayName().getString());
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    // BLOCK ENTITY
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //autoOwn(); // keep disabled till bug is fixed
        VaultBlockEntity vault = ((VaultBlockEntity) world.getBlockEntity(pos));
        assert vault != null;
        boolean isOwner = vault.getOwnerUUID().equals(player.getUuidAsString());
        if (player.isSneaking() && isOwner) {
            if (!player.isCreative())
                dropStack(world, pos, new ItemStack(ModBlocks.VAULT_BLOCK));
            world.breakBlock(pos, true);
        }
        else if (player.isSneaking() && !isOwner) {
            if (world.isClient) {
                player.sendMessage(Text.translatable(NON_BREAKABLE).fillStyle(
                        Style.EMPTY.withColor(Formatting.RED)), true);
            }
        } else if (isOwner) {
            if (!world.isClient()) {
                NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }
            }
            if (world.isClient) {
                player.sendMessage(Text.translatable(ACCESS_GRANTED).fillStyle(
                        Style.EMPTY.withColor(Formatting.GREEN)), true);
            }
        } else {
            if (world.isClient) {
                player.sendMessage(Text.translatable(ACCESS_DENIED).fillStyle(
                        Style.EMPTY.withColor(Formatting.RED)), true);
            }
        }
        return ActionResult.SUCCESS;
    }
    // TODO CREATE A OWNER IF ITS NULL SO WE CAN USE THE BLOCkS IN CUSTOM STRUCTURES AS LOOT CHESTS
    // if owner is null (ownerless), it crashes,
    // need to make it so the first player to interact becomes the owner.
    /**
    private void autoOwn(){
        VaultBlockEntity vault = ((VaultBlockEntity) level.getBlockEntity(blockPos));
        assert vault != null;
        assert playerEntity != null;
        assert level != null;
        boolean isNone = vault.getOwnerUUID() == null;
        if (isNone){
            ((VaultBlockEntity) Objects.requireNonNull(level.getBlockEntity(blockPos))).setOwnerUUID(playerEntity.getUuidAsString());
            ((VaultBlockEntity) Objects.requireNonNull(level.getBlockEntity(blockPos))).setOwnerName(playerEntity.getDisplayName().getString());
        }
    }
     **/

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VaultBlockEntity(pos, state);
    }
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("block.signum.vault.tooltip")
                    .fillStyle(Style.EMPTY.withColor(Formatting.GREEN).withBold(true)));
            tooltip.add(Text.translatable("block.signum.vault.tooltip2")
                    .fillStyle(Style.EMPTY.withColor(Formatting.GREEN).withBold(true)));
        }else {
            tooltip.add(Text.translatable("key.signum.shift")
                    .fillStyle(Style.EMPTY.withColor(Formatting.GOLD)));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}

