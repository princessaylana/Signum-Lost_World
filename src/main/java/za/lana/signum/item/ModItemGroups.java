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
                    .icon(() -> new ItemStack(ModItems.ELEMENT_ZERO_CRYSTAL)).entries((displayContext, entries) -> {

                        entries.add(ModItems.RAW_ELEMENT_ZERO);
                        entries.add(ModItems.RAW_QUARTZ_CRYSTAL);
                        entries.add(ModItems.RAW_MANGANESE);
                        entries.add(ModItems.RAW_MOISSANITE);

                        entries.add(ModItems.COKECOAL);
                        entries.add(ModItems.TIBERIUMCOAL);
                        entries.add(ModItems.ELEMENTZEROCOAL);

                        entries.add(ModItems.TIBERIUM_SHARD);
                        entries.add(ModItems.GLASS_SHARD);

                        entries.add(ModItems.MANGANESE_INGOT);
                        entries.add(ModItems.SIGSTEEL_INGOT);
                        entries.add(ModItems.SIGSTAINSTEEL_INGOT);
                        entries.add(ModItems.MANGANESE_NUGGET);

                        entries.add(ModItems.MOISSANITE);
                        entries.add(ModItems.ELEMENT_ZERO);

                        entries.add(ModItems.ELEMENT_ZERO_CRYSTAL);
                        entries.add(ModItems.FIRE_CRYSTAL);
                        entries.add(ModItems.MOISSANITE_CRYSTAL);
                        entries.add(ModItems.QUARTZ_CRYSTAL);
                        entries.add(ModItems.TIBERIUM_CRYSTAL);

                        entries.add(ModItems.ELEMENT_ZERO_DUST);
                        entries.add(ModItems.FIRE_CRYSTAL_DUST);
                        entries.add(ModItems.MANGANESE_DUST);
                        entries.add(ModItems.MOISSANITE_DUST);
                        entries.add(ModItems.QUARTZ_CRYSTAL_DUST);
                        entries.add(ModItems.TIBERIUM_DUST);

                        entries.add(ModItems.MANGANESE_AXE);
                        entries.add(ModItems.MANGANESE_HOE);
                        entries.add(ModItems.MANGANESE_PICKAXE);
                        entries.add(ModItems.MANGANESE_SHOVEL);
                        entries.add(ModItems.MANGANESE_SWORD);

                        entries.add(ModItems.TOXICBALL_ITEM);
                        entries.add(ModItems.BPISTOL_ITEM);
                        entries.add(ModItems.LASERBOLT_ITEM);
                        entries.add(ModItems.E0ROD);
                        entries.add(ModItems.FIRESTAFF);
                        entries.add(ModItems.SLAYER_STAFF);
                        entries.add(ModItems.PLASMA_SWORD);
                        entries.add(ModItems.TOXICGUN);

                        entries.add(ModItems.DETECTOR_ITEM);
                        entries.add(ModItems.TIBERIUM_WORM_SPAWN_EGG);
                        entries.add(ModItems.GHOST_SPAWN_EGG);
                        entries.add(ModItems.AIRDRONE_SPAWN_EGG);
                        entries.add(ModItems.SIGALIEN_SPAWN_EGG);
                        entries.add(ModItems.SKYCAR_SPAWN_EGG);
                        entries.add(ModItems.AIRBALOON_SPAWN_EGG);

                        //blocks
                        entries.add(ModBlocks.BLIGHT_BLOCK);
                        entries.add(ModBlocks.MANGANESE_ORE);
                        entries.add(ModBlocks.DEEPSLATE_MANGANESE_ORE);
                        entries.add(ModBlocks.NETHERRACK_MANGANESE_ORE);
                        entries.add(ModBlocks.MOISSANITE_ORE);
                        entries.add(ModBlocks.DEEPSLATE_MOISSANITE_ORE);
                        entries.add(ModBlocks.ENDSTONE_MANGANESE_ORE);
                        entries.add(ModBlocks.ELEMENT_ZERO_ORE);
                        entries.add(ModBlocks.DEEPSLATE_ELEMENT_ZERO_ORE);

                        entries.add(ModBlocks.TIBERIUM_BLOCK);
                        entries.add(ModBlocks.BUDDING_TIBERIUM);
                        entries.add(ModBlocks.TIBERIUM_CLUSTER);
                        entries.add(ModBlocks.LARGE_TIBERIUM_BUD);
                        entries.add(ModBlocks.MEDIUM_TIBERIUM_BUD);
                        entries.add(ModBlocks.SMALL_TIBERIUM_BUD);

                        entries.add(ModBlocks.MANGANESE_BLOCK);
                        entries.add(ModBlocks.MOISSANITE_BLOCK);
                        entries.add(ModBlocks.RAZORWIRE_BLOCK);

                        entries.add(ModBlocks.SKYFORGE);
                        entries.add(ModBlocks.ASSEMBLY_STATION_BLOCK);
                        entries.add(ModBlocks.EXAMPLE_BLOCK);

                    }).build());
    public static void registerItemGroups(){
        Signum.LOGGER.info("Registering ItemGroup for " + Signum.MOD_ID);
    }
}
