/**
 * SIGNUM
 * Mod item Group and Tab
 * MIT License
 * Lana
 * */

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
                        //items
                        entries.add(ModItems.RAW_QUARTZ_CRYSTAL);
                        entries.add(ModItems.RAW_MANGANESE);
                        entries.add(ModItems.RAW_MOISSANITE);

                        entries.add(ModItems.MANGANESE_INGOT);
                        entries.add(ModItems.MANGANESE_NUGGET);

                        entries.add(ModItems.MOISSANITE);
                        entries.add(ModItems.QUARTZ_CRYSTAL);
                        entries.add(ModItems.GLASS_SHARD);
                        entries.add(ModItems.TIBERIUM_SHARD);

                        entries.add(ModItems.LASERBOLT_ITEM);
                        entries.add(ModItems.TOXICBALL_ITEM);
                        entries.add(ModItems.TOXICGUN);

                        entries.add(ModItems.BPISTOL_ITEM);
                        entries.add(ModItems.DETECTOR_ITEM);

                        //blocks
                        entries.add(ModBlocks.MANGANESE_ORE);
                        entries.add(ModBlocks.DEEPSLATE_MANGANESE_ORE);
                        entries.add(ModBlocks.NETHERRACK_MANGANESE_ORE);
                        entries.add(ModBlocks.MOISSANITE_ORE);
                        entries.add(ModBlocks.DEEPSLATE_MOISSANITE_ORE);
                        entries.add(ModBlocks.ENDSTONE_MANGANESE_ORE);

                        entries.add(ModBlocks.TIBERIUM_BLOCK);
                        entries.add(ModBlocks.BUDDING_TIBERIUM);
                        entries.add(ModBlocks.TIBERIUM_CLUSTER);
                        entries.add(ModBlocks.LARGE_TIBERIUM_BUD);
                        entries.add(ModBlocks.MEDIUM_TIBERIUM_BUD);
                        entries.add(ModBlocks.SMALL_TIBERIUM_BUD);


                        entries.add(ModBlocks.MANGANESE_BLOCK);
                        entries.add(ModBlocks.MOISSANITE_BLOCK);
                        entries.add(ModBlocks.RAZORWIRE_BLOCK);

                        entries.add(ModBlocks.SKYFORGE2);
                        entries.add(ModBlocks.ASSEMBLY_STATION_BLOCK);

                    }).build());
    public static void registerItemGroups(){
        Signum.LOGGER.info("Registering ItemGroup for " + Signum.MOD_ID);
    }
}
