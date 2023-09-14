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
                        // RAW ITEMS
                        entries.add(ModItems.RAW_ELEMENT_ZERO);
                        entries.add(ModItems.RAW_QUARTZ_CRYSTAL);
                        entries.add(ModItems.RAW_MANGANESE);
                        entries.add(ModItems.RAW_MOISSANITE);
                        // FUEL ITEMS
                        entries.add(ModItems.COKECOAL);
                        entries.add(ModItems.TIBERIUMCOAL);
                        entries.add(ModItems.ELEMENTZEROCOAL);
                        //SHARDS
                        entries.add(ModItems.TIBERIUM_SHARD);
                        entries.add(ModItems.GLASS_SHARD);
                        // INGOTS AND EQUIVALENT CRYSTALS
                        entries.add(ModItems.MANGANESE_INGOT);
                        entries.add(ModItems.SIGSTEEL_INGOT);
                        entries.add(ModItems.SIGSTAINSTEEL_INGOT);
                        entries.add(ModItems.MANGANESE_NUGGET);
                        entries.add(ModItems.MOISSANITE);
                        entries.add(ModItems.ELEMENT_ZERO);
                        // CRYSTALS
                        entries.add(ModItems.ELEMENT_ZERO_CRYSTAL);
                        entries.add(ModItems.FIRE_CRYSTAL);
                        entries.add(ModItems.MOISSANITE_CRYSTAL);
                        entries.add(ModItems.QUARTZ_CRYSTAL);
                        entries.add(ModItems.TIBERIUM_CRYSTAL);
                        // DUSTS
                        entries.add(ModItems.ELEMENT_ZERO_DUST);
                        entries.add(ModItems.FIRE_CRYSTAL_DUST);
                        entries.add(ModItems.MANGANESE_DUST);
                        entries.add(ModItems.MOISSANITE_DUST);
                        entries.add(ModItems.QUARTZ_CRYSTAL_DUST);
                        entries.add(ModItems.TIBERIUM_DUST);
                        // TOOLS
                        entries.add(ModItems.MANGANESE_AXE);
                        entries.add(ModItems.MANGANESE_HOE);
                        entries.add(ModItems.MANGANESE_PICKAXE);
                        entries.add(ModItems.MANGANESE_SHOVEL);
                        entries.add(ModItems.MANGANESE_SWORD);
                        // WEAPONS
                        entries.add(ModItems.PLASMA_SWORD);
                        // STAFFS
                        entries.add(ModItems.SLAYER_STAFF);
                        // NEW SET
                        entries.add(ModItems.TIBERIUM_STAFF);
                        entries.add(ModItems.FREEZE_STAFF);
                        entries.add(ModItems.TRANSMUTE_STAFF);
                        entries.add(ModItems.FIRE_STAFF);
                        entries.add(ModItems.LIGHTNING_STAFF);
                        entries.add(ModItems.HEALING_STAFF);
                        entries.add(ModItems.TELEPORT_STAFF);
                        entries.add(ModItems.GRAVITY_STAFF);
                        entries.add(ModItems.PETRIFY_STAFF);
                        entries.add(ModItems.WATER_STAFF);
                        // SPAWN EGGS
                        entries.add(ModItems.TIBERIUM_WORM_SPAWN_EGG);
                        entries.add(ModItems.GHOST_SPAWN_EGG);
                        entries.add(ModItems.AIRDRONE_SPAWN_EGG);
                        entries.add(ModItems.SIGALIEN_SPAWN_EGG);
                        entries.add(ModItems.SKYCAR_SPAWN_EGG);
                        entries.add(ModItems.AIRBALOON_SPAWN_EGG);
                        // BLOCKS
                        entries.add(ModBlocks.BLIGHT_BLOCK);
                        entries.add(ModBlocks.TIBERIUM_FIRE);
                        // ORES
                        entries.add(ModBlocks.MANGANESE_ORE);
                        entries.add(ModBlocks.DEEPSLATE_MANGANESE_ORE);
                        entries.add(ModBlocks.NETHERRACK_MANGANESE_ORE);
                        entries.add(ModBlocks.MOISSANITE_ORE);
                        entries.add(ModBlocks.DEEPSLATE_MOISSANITE_ORE);
                        entries.add(ModBlocks.ENDSTONE_MANGANESE_ORE);
                        entries.add(ModBlocks.ELEMENT_ZERO_ORE);
                        entries.add(ModBlocks.DEEPSLATE_ELEMENT_ZERO_ORE);
                        // CRYSTAL BLOCKS
                        entries.add(ModBlocks.TIBERIUM_BLOCK);
                        entries.add(ModBlocks.BUDDING_TIBERIUM);
                        entries.add(ModBlocks.TIBERIUM_CLUSTER);
                        entries.add(ModBlocks.LARGE_TIBERIUM_BUD);
                        entries.add(ModBlocks.MEDIUM_TIBERIUM_BUD);
                        entries.add(ModBlocks.SMALL_TIBERIUM_BUD);

                        entries.add(ModBlocks.FIRE_CRYSTAL_BLOCK);
                        entries.add(ModBlocks.BUDDING_FIRE_CRYSTAL);
                        entries.add(ModBlocks.FIRE_CRYSTAL_CLUSTER);
                        entries.add(ModBlocks.LARGE_FIRE_CRYSTAL_BUD);
                        entries.add(ModBlocks.MEDIUM_FIRE_CRYSTAL_BUD);
                        entries.add(ModBlocks.SMALL_FIRE_CRYSTAL_BUD);
                        // BUIDLING BLOCKS
                        entries.add(ModBlocks.MANGANESE_BLOCK);
                        entries.add(ModBlocks.MOISSANITE_BLOCK);
                        entries.add(ModBlocks.RAZORWIRE_BLOCK);
                        // BLOCKENTITIES
                        entries.add(ModBlocks.SKYFORGE);
                        entries.add(ModBlocks.ASSEMBLY_STATION_BLOCK);
                        entries.add(ModBlocks.EXAMPLE_BLOCK);
                        //TO BE REMOVED - TESTING ONLY
                        entries.add(ModItems.TOXICBALL_ITEM);
                        entries.add(ModItems.BPISTOL_ITEM);
                        entries.add(ModItems.LASERBOLT_ITEM);
                        entries.add(ModItems.E0ROD);
                        entries.add(ModItems.TOXICGUN);
                        entries.add(ModItems.DETECTOR_ITEM);

                    }).build());
    public static void registerItemGroups(){
        Signum.LOGGER.info("Registering ItemGroup for " + Signum.MOD_ID);
    }
}
