package za.lana.signum.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup SIGNUM_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Signum.MOD_ID, "signum_group"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.signum"))
                    .icon(() -> new ItemStack(ModItems.QUARTZ_CRYSTAL)).entries((displayContext, entries) -> {
                        entries.add(ModItems.RAW_QUARTZ_CRYSTAL);
                        entries.add(ModItems.QUARTZ_CRYSTAL);
                        entries.add(ModItems.TOXICBALL_ITEM);

                        entries.add(ModBlocks.RAZORWIRE_BLOCK);


                    }).build());
    public static void registerItemGroups(){
        Signum.LOGGER.info("Registering Itemgroups for " + Signum.MOD_ID);
    }
}
