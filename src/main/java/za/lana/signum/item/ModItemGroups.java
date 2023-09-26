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
                        // BUIDLING BLOCKS
                        entries.add(ModBlocks.MANGANESE_BLOCK);
                        entries.add(ModBlocks.MOISSANITE_BLOCK);
                        entries.add(ModBlocks.RAZORWIRE_BLOCK);
                        entries.add(ModBlocks.BLIGHT_BLOCK);
                        //entries.add(ModBlocks.TIBERIUM_FIRE);
                        // ORES
                        entries.add(ModBlocks.MANGANESE_ORE);
                        entries.add(ModBlocks.DEEPSLATE_MANGANESE_ORE);
                        entries.add(ModBlocks.NETHERRACK_MANGANESE_ORE);
                        entries.add(ModBlocks.MOISSANITE_ORE);
                        entries.add(ModBlocks.DEEPSLATE_MOISSANITE_ORE);
                        entries.add(ModBlocks.ENDSTONE_MANGANESE_ORE);
                        entries.add(ModBlocks.ELEMENT_ZERO_ORE);
                        entries.add(ModBlocks.DEEPSLATE_ELEMENT_ZERO_ORE);
                        // MAIN CRYSTAL BLOCKS
                        entries.add(ModBlocks.TIBERIUM_BLOCK);
                        entries.add(ModBlocks.FIRE_CRYSTAL_BLOCK);
                        entries.add(ModBlocks.EXOTIC_CRYSTAL_BLOCK);
                        entries.add(ModBlocks.ICE_CRYSTAL_BLOCK);
                        entries.add(ModBlocks.QUARTZ_CRYSTAL_BLOCK);

                        entries.add(ModBlocks.BUDDING_TIBERIUM);
                        entries.add(ModBlocks.BUDDING_FIRE_CRYSTAL);
                        entries.add(ModBlocks.BUDDING_EXOTIC_CRYSTAL);
                        entries.add(ModBlocks.BUDDING_ICE_CRYSTAL);
                        entries.add(ModBlocks.BUDDING_QUARTZ_CRYSTAL);

                        // BLOCKENTITIES
                        entries.add(ModBlocks.SKYFORGE);
                        entries.add(ModBlocks.ASSEMBLY_STATION_BLOCK);
                        // RAW ITEMS
                        entries.add(ModItems.RAW_ELEMENT_ZERO);
                        entries.add(ModItems.RAW_QUARTZ_CRYSTAL);
                        entries.add(ModItems.RAW_MANGANESE);
                        entries.add(ModItems.RAW_MOISSANITE);
                        // INGOTS AND EQUIVALENT CRYSTALS
                        entries.add(ModItems.MANGANESE_INGOT);
                        entries.add(ModItems.SIGSTEEL_INGOT);
                        entries.add(ModItems.SIGSTAINSTEEL_INGOT);
                        entries.add(ModItems.MANGANESE_NUGGET);
                        entries.add(ModItems.MOISSANITE);
                        entries.add(ModItems.ELEMENT_ZERO);
                        // FUEL ITEMS
                        entries.add(ModItems.COKECOAL);
                        entries.add(ModItems.TIBERIUMCOAL);
                        // CRYSTAL BLOCKS
                        entries.add(ModItems.ELEMENTZEROCOAL);
                        entries.add(ModBlocks.TIBERIUM_CLUSTER);
                        entries.add(ModBlocks.FIRE_CRYSTAL_CLUSTER);
                        entries.add(ModBlocks.EXOTIC_CRYSTAL_CLUSTER);
                        entries.add(ModBlocks.ICE_CRYSTAL_CLUSTER);
                        entries.add(ModBlocks.QUARTZ_CRYSTAL_CLUSTER);

                        entries.add(ModBlocks.LARGE_TIBERIUM_BUD);
                        entries.add(ModBlocks.LARGE_FIRE_CRYSTAL_BUD);
                        entries.add(ModBlocks.LARGE_EXOTIC_CRYSTAL_BUD);
                        entries.add(ModBlocks.LARGE_ICE_CRYSTAL_BUD);
                        entries.add(ModBlocks.LARGE_QUARTZ_CRYSTAL_BUD);

                        entries.add(ModBlocks.MEDIUM_TIBERIUM_BUD);
                        entries.add(ModBlocks.MEDIUM_FIRE_CRYSTAL_BUD);
                        entries.add(ModBlocks.MEDIUM_EXOTIC_CRYSTAL_BUD);
                        entries.add(ModBlocks.MEDIUM_ICE_CRYSTAL_BUD);
                        entries.add(ModBlocks.MEDIUM_QUARTZ_CRYSTAL_BUD);

                        entries.add(ModBlocks.SMALL_TIBERIUM_BUD);
                        entries.add(ModBlocks.SMALL_FIRE_CRYSTAL_BUD);
                        entries.add(ModBlocks.SMALL_EXOTIC_CRYSTAL_BUD);
                        entries.add(ModBlocks.SMALL_ICE_CRYSTAL_BUD);
                        entries.add(ModBlocks.SMALL_QUARTZ_CRYSTAL_BUD);
                        //SHARDS
                        entries.add(ModItems.TIBERIUM_SHARD);
                        entries.add(ModItems.FIRE_CRYSTAL_SHARD);
                        entries.add(ModItems.EXOTIC_CRYSTAL_SHARD);
                        entries.add(ModItems.ICE_CRYSTAL_SHARD);
                        entries.add(ModItems.QUARTZ_CRYSTAL_SHARD);
                        entries.add(ModItems.GLASS_SHARD);
                        // CRYSTALS
                        entries.add(ModItems.TIBERIUM_CRYSTAL);
                        entries.add(ModItems.FIRE_CRYSTAL);
                        entries.add(ModItems.EXOTIC_CRYSTAL);
                        entries.add(ModItems.ICE_CRYSTAL);
                        entries.add(ModItems.QUARTZ_CRYSTAL);
                        entries.add(ModItems.MOISSANITE_CRYSTAL);
                        entries.add(ModItems.ELEMENT_ZERO_CRYSTAL);
                        // DUSTS
                        entries.add(ModItems.MANGANESE_DUST);
                        entries.add(ModItems.TIBERIUM_DUST);
                        entries.add(ModItems.ICE_CRYSTAL_DUST);
                        entries.add(ModItems.FIRE_CRYSTAL_DUST);
                        entries.add(ModItems.EXOTIC_CRYSTAL_DUST);
                        entries.add(ModItems.QUARTZ_CRYSTAL_DUST);
                        entries.add(ModItems.MOISSANITE_DUST);
                        entries.add(ModItems.ELEMENT_ZERO_DUST);
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
                        entries.add(ModItems.FIRE_STAFF);
                        entries.add(ModItems.TRANSMUTE_STAFF);
                        entries.add(ModItems.FREEZE_STAFF);
                        entries.add(ModItems.LIGHTNING_STAFF);
                        entries.add(ModItems.HEALING_STAFF);
                        entries.add(ModItems.TELEPORT_STAFF);
                        entries.add(ModItems.GRAVITY_STAFF);
                        entries.add(ModItems.PETRIFY_STAFF);
                        entries.add(ModItems.WATER_STAFF);
                        // SPAWN EGGS
                        entries.add(ModItems.TIBERIUM_WORM_SPAWN_EGG);
                        entries.add(ModItems.TIBERIUM_SKELETON_SPAWN_EGG);
                        entries.add(ModItems.GHOST_SPAWN_EGG);
                        entries.add(ModItems.AIRDRONE_SPAWN_EGG);
                        entries.add(ModItems.SIGALIEN_SPAWN_EGG);
                        entries.add(ModItems.SKYCAR_SPAWN_EGG);
                        entries.add(ModItems.AIRBALOON_SPAWN_EGG);

                        //TODO: BELOW TO BE REMOVED - FOR TESTING ONLY
                        entries.add(ModBlocks.EXAMPLE_BLOCK);
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
