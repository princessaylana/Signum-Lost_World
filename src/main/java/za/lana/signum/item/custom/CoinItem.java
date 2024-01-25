/**
 * SIGNUM
 * MIT License
 * Lana
 * 2024
 * */
package za.lana.signum.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.item.ModItems;

import java.util.List;

public class CoinItem extends Item {
    public static int COPPER_VALUE = 1;
    public static int IRON_VALUE = 2;
    public static int GOLD_VALUE = 5;

    public CoinItem(Settings settings) {
        super(settings);
    }
    // RETURNS THE COIN TYPE VALUES
    public final int getCoinValue(ItemStack stack){
        //ItemStack stack = ItemStack(this);
        if (stack.isOf(ModItems.COPPER_COIN)){
            return COPPER_VALUE;
        }
        if (stack.isOf(ModItems.IRON_COIN)){
            return IRON_VALUE;
        }
        if (stack.isOf(ModItems.GOLD_COIN)){
            return GOLD_VALUE;
        }
        return 0;
    }
    // RETURNS THE TOTAL COIN TYPE VALUES
    public static int getTotalCoinValue(ItemStack stack){
        int stackAmount = stack.getCount();
        if (stack.isOf(ModItems.COPPER_COIN)){
            return stackAmount * COPPER_VALUE;
        }
        if (stack.isOf(ModItems.IRON_COIN)){
            return stackAmount * IRON_VALUE;
        }
        if (stack.isOf(ModItems.GOLD_COIN)){
            return stackAmount * GOLD_VALUE;
        }
        return 0;
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.isOf(ModItems.COPPER_COIN)){
            tooltip.add(Text.literal("Credits " + stack.getCount() * COPPER_VALUE)
                    .fillStyle(Style.EMPTY.withColor(Formatting.GOLD)));
        }
        if (stack.isOf(ModItems.IRON_COIN)){
            tooltip.add(Text.literal("Credits " + stack.getCount() * IRON_VALUE)
                    .fillStyle(Style.EMPTY.withColor(Formatting.GOLD)));
        }
        if (stack.isOf(ModItems.GOLD_COIN)){
            tooltip.add(Text.literal("Credits " + stack.getCount() * GOLD_VALUE)
                    .fillStyle(Style.EMPTY.withColor(Formatting.GOLD)));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
