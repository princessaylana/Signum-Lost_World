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

    public CoinItem(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (this == ModItems.COPPER_COIN){
            tooltip.add(Text.literal("1 Credit")
                    .fillStyle(Style.EMPTY.withColor(Formatting.GOLD)));
        }
        if (this == ModItems.IRON_COIN){
            tooltip.add(Text.literal("2 Credits")
                    .fillStyle(Style.EMPTY.withColor(Formatting.GOLD)));
        }
        if (this == ModItems.GOLD_COIN){
            tooltip.add(Text.literal("5 Credits")
                    .fillStyle(Style.EMPTY.withColor(Formatting.GOLD)));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
