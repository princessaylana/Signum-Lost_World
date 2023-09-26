/**
 * SIGNUM
 * MIT License
 * Lana
 * item used to test world generation of custom ores
 * */
package za.lana.signum.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.block.ModBlocks;

import java.util.List;

public class OreDetectorItem extends Item {
    public OreDetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient()) {
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;

            for(int i = 0; i <= positionClicked.getY() + 64; i++) {
                BlockState state = context.getWorld().getBlockState(positionClicked.down(i));

                if(isValuableBlock(state)) {
                    outputValuableCoordinates(positionClicked.down(i), player, state.getBlock());
                    foundBlock = true;
                    break;
                }
            }
            if(!foundBlock) {
                player.sendMessage(Text.literal("No Ore Found!"));
            }
        }
        context.getStack().damage(1, context.getPlayer(),
                playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));

        return ActionResult.SUCCESS;
    }
    private void outputValuableCoordinates(BlockPos blockPos, PlayerEntity player, Block block) {
        player.sendMessage(Text.literal("Found " + block.asItem().getName().getString() + " at " +
                "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")"), false);
    }
    //list of oreblocks to check for
    private boolean isValuableBlock(BlockState state) {
        return
                state.isOf(ModBlocks.ELEMENT_ZERO_ORE) ||
                state.isOf(ModBlocks.MOISSANITE_ORE) ||
                state.isOf(ModBlocks.DEEPSLATE_MOISSANITE_ORE) ||
                state.isOf(ModBlocks.DEEPSLATE_MANGANESE_ORE) ||
                state.isOf(ModBlocks.DEEPSLATE_ELEMENT_ZERO_ORE) ||
                state.isOf(ModBlocks.MANGANESE_ORE) ||
                state.isOf(ModBlocks.BUDDING_QUARTZ_CRYSTAL) ||
                state.isOf(ModBlocks.BUDDING_FIRE_CRYSTAL) ||
                state.isOf(ModBlocks.NETHERRACK_MANGANESE_ORE);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item.signum.detector_item.info"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}

