/**
 * SIGNUM
 * Datagenerator file
 * MIT License
 * Lana
 * */
package za.lana.signum.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.item.ModItems;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.BLACK_DIAMOND_BLOCK);
        addDrop(ModBlocks.ELEMENT_ZERO_BLOCK);
        addDrop(ModBlocks.BLIGHT_BLOCK);
        addDrop(ModBlocks.MANGANESE_BLOCK);
        addDrop(ModBlocks.MOISSANITE_BLOCK);
        addDrop(ModBlocks.TIBERIUM_BLOCK);
        addDrop(ModBlocks.BUDDING_TIBERIUM);
        addDrop(ModBlocks.FIRE_CRYSTAL_BLOCK);
        addDrop(ModBlocks.BUDDING_FIRE_CRYSTAL);
        addDrop(ModBlocks.ICE_CRYSTAL_BLOCK);
        addDrop(ModBlocks.BUDDING_ICE_CRYSTAL);
        addDrop(ModBlocks.EXOTIC_CRYSTAL_BLOCK);
        addDrop(ModBlocks.BUDDING_EXOTIC_CRYSTAL);
        addDrop(ModBlocks.QUARTZ_CRYSTAL_BLOCK);
        addDrop(ModBlocks.BUDDING_QUARTZ_CRYSTAL);

        addDrop(ModBlocks.ASSEMBLY_STATION_BLOCK);
        addDrop(ModBlocks.RAZORWIRE_BLOCK);
        addDrop(ModBlocks.SKYFORGE);

        //silk touch ad normal drop
        addDrop(ModBlocks.ELEMENT_ZERO_ORE, uncommonOreDrops(ModBlocks.ELEMENT_ZERO_ORE, ModItems.RAW_ELEMENT_ZERO));
        addDrop(ModBlocks.DEEPSLATE_ELEMENT_ZERO_ORE, uncommonOreDrops(ModBlocks.DEEPSLATE_ELEMENT_ZERO_ORE, ModItems.RAW_ELEMENT_ZERO));

        addDrop(ModBlocks.BLACK_DIAMOND_ORE, uncommonOreDrops(ModBlocks.BLACK_DIAMOND_ORE, ModItems.BLACK_DIAMOND_SHARD));
        addDrop(ModBlocks.DEEPSLATE_BLACK_DIAMOND_ORE, commonOreDrops(ModBlocks.DEEPSLATE_BLACK_DIAMOND_ORE, ModItems.BLACK_DIAMOND_SHARD));

        addDrop(ModBlocks.MANGANESE_ORE, lesscommonOreDrops(ModBlocks.MANGANESE_ORE, ModItems.RAW_MANGANESE));
        addDrop(ModBlocks.DEEPSLATE_MANGANESE_ORE, lesscommonOreDrops(ModBlocks.DEEPSLATE_MANGANESE_ORE, ModItems.RAW_MANGANESE));
        addDrop(ModBlocks.NETHERRACK_MANGANESE_ORE, commonOreDrops(ModBlocks.NETHERRACK_MANGANESE_ORE, ModItems.RAW_MANGANESE));
        addDrop(ModBlocks.ENDSTONE_MANGANESE_ORE, commonOreDrops(ModBlocks.ENDSTONE_MANGANESE_ORE, ModItems.RAW_MANGANESE));

        addDrop(ModBlocks.MOISSANITE_ORE, rareOreDrops(ModBlocks.MOISSANITE_ORE, ModItems.RAW_MOISSANITE));
        addDrop(ModBlocks.DEEPSLATE_MOISSANITE_ORE, uncommonOreDrops(ModBlocks.DEEPSLATE_MOISSANITE_ORE, ModItems.RAW_MOISSANITE));

        addDrop(ModBlocks.SMALL_TIBERIUM_BUD, rareOreDrops(ModBlocks.SMALL_TIBERIUM_BUD, ModItems.TIBERIUM_SHARD));
        addDrop(ModBlocks.MEDIUM_TIBERIUM_BUD, rareOreDrops(ModBlocks.MEDIUM_TIBERIUM_BUD, ModItems.TIBERIUM_SHARD));
        addDrop(ModBlocks.LARGE_TIBERIUM_BUD, lesscommonOreDrops(ModBlocks.LARGE_TIBERIUM_BUD, ModItems.TIBERIUM_SHARD));
        addDrop(ModBlocks.TIBERIUM_CLUSTER, commonOreDrops(ModBlocks.TIBERIUM_CLUSTER, ModItems.TIBERIUM_SHARD));

        addDrop(ModBlocks.SMALL_FIRE_CRYSTAL_BUD, rareOreDrops(ModBlocks.SMALL_FIRE_CRYSTAL_BUD, ModItems.FIRE_CRYSTAL_SHARD));
        addDrop(ModBlocks.MEDIUM_FIRE_CRYSTAL_BUD, rareOreDrops(ModBlocks.MEDIUM_FIRE_CRYSTAL_BUD, ModItems.FIRE_CRYSTAL_SHARD));
        addDrop(ModBlocks.LARGE_FIRE_CRYSTAL_BUD, lesscommonOreDrops(ModBlocks.LARGE_FIRE_CRYSTAL_BUD, ModItems.FIRE_CRYSTAL_SHARD));
        addDrop(ModBlocks.FIRE_CRYSTAL_CLUSTER, commonOreDrops(ModBlocks.FIRE_CRYSTAL_CLUSTER, ModItems.FIRE_CRYSTAL_SHARD));

        addDrop(ModBlocks.SMALL_ICE_CRYSTAL_BUD, rareOreDrops(ModBlocks.SMALL_ICE_CRYSTAL_BUD, ModItems.ICE_CRYSTAL_SHARD));
        addDrop(ModBlocks.MEDIUM_ICE_CRYSTAL_BUD, rareOreDrops(ModBlocks.MEDIUM_ICE_CRYSTAL_BUD, ModItems.ICE_CRYSTAL_SHARD));
        addDrop(ModBlocks.LARGE_ICE_CRYSTAL_BUD, lesscommonOreDrops(ModBlocks.LARGE_ICE_CRYSTAL_BUD, ModItems.ICE_CRYSTAL_SHARD));
        addDrop(ModBlocks.ICE_CRYSTAL_CLUSTER, commonOreDrops(ModBlocks.ICE_CRYSTAL_CLUSTER, ModItems.ICE_CRYSTAL_SHARD));

        addDrop(ModBlocks.SMALL_EXOTIC_CRYSTAL_BUD, rareOreDrops(ModBlocks.SMALL_EXOTIC_CRYSTAL_BUD, ModItems.EXOTIC_CRYSTAL_SHARD));
        addDrop(ModBlocks.MEDIUM_FIRE_CRYSTAL_BUD, rareOreDrops(ModBlocks.MEDIUM_ICE_CRYSTAL_BUD, ModItems.EXOTIC_CRYSTAL_SHARD));
        addDrop(ModBlocks.LARGE_FIRE_CRYSTAL_BUD, lesscommonOreDrops(ModBlocks.MEDIUM_EXOTIC_CRYSTAL_BUD, ModItems.EXOTIC_CRYSTAL_SHARD));
        addDrop(ModBlocks.FIRE_CRYSTAL_CLUSTER, commonOreDrops(ModBlocks.EXOTIC_CRYSTAL_CLUSTER, ModItems.EXOTIC_CRYSTAL_SHARD));

        addDrop(ModBlocks.SMALL_QUARTZ_CRYSTAL_BUD, rareOreDrops(ModBlocks.SMALL_QUARTZ_CRYSTAL_BUD, ModItems.QUARTZ_CRYSTAL_SHARD));
        addDrop(ModBlocks.MEDIUM_QUARTZ_CRYSTAL_BUD, rareOreDrops(ModBlocks.MEDIUM_QUARTZ_CRYSTAL_BUD, ModItems.QUARTZ_CRYSTAL_SHARD));
        addDrop(ModBlocks.LARGE_QUARTZ_CRYSTAL_BUD, lesscommonOreDrops(ModBlocks.LARGE_QUARTZ_CRYSTAL_BUD, ModItems.QUARTZ_CRYSTAL_SHARD));
        addDrop(ModBlocks.QUARTZ_CRYSTAL_CLUSTER, commonOreDrops(ModBlocks.QUARTZ_CRYSTAL_CLUSTER, ModItems.QUARTZ_CRYSTAL_SHARD));

        addDrop(ModBlocks.MANGANESE_STAIRS);
        addDrop(ModBlocks.MANGANESE_FENCE);
        addDrop(ModBlocks.MANGANESE_GATE);
        addDrop(ModBlocks.MANGANESE_TRAPDOOR);
        addDrop(ModBlocks.MANGANESE_WALL);
        addDrop(ModBlocks.MANGANESE_PRESSURE_PLATE);
        addDrop(ModBlocks.MANGANESE_BUTTON);
        addDrop(ModBlocks.MANGANESE_DOOR, doorDrops(ModBlocks.MANGANESE_DOOR));
        addDrop(ModBlocks.MANGANESE_DOOR, slabDrops(ModBlocks.MANGANESE_SLAB));

        addDrop(ModBlocks.ORANGE_SHROOM_BLOCK, mushroomBlockDrops(ModBlocks.ORANGE_SHROOM_BLOCK, ModBlocks.ORANGE_MUSHROOM));
        addDrop(ModBlocks.YELLOW_SHROOM_BLOCK, mushroomBlockDrops(ModBlocks.YELLOW_SHROOM_BLOCK, ModBlocks.YELLOW_MUSHROOM));
        addDrop(ModBlocks.TOXIC_SHROOM_BLOCK, mushroomBlockDrops(ModBlocks.TOXIC_SHROOM_BLOCK, ModBlocks.TOXIC_MUSHROOM));
        addDrop(ModBlocks.BLUE_SHROOM_BLOCK, mushroomBlockDrops(ModBlocks.BLUE_SHROOM_BLOCK, ModBlocks.BLUE_MUSHROOM));
        addDrop(ModBlocks.PURPLE_SHROOM_BLOCK, mushroomBlockDrops(ModBlocks.PURPLE_SHROOM_BLOCK, ModBlocks.PURPLE_MUSHROOM));
        addDrop(ModBlocks.PINK_SHROOM_BLOCK, mushroomBlockDrops(ModBlocks.PINK_SHROOM_BLOCK, ModBlocks.PINK_MUSHROOM));
        addDrop(ModBlocks.WHITE_SHROOM_BLOCK, mushroomBlockDrops(ModBlocks.WHITE_SHROOM_BLOCK, ModBlocks.WHITE_MUSHROOM));
        addDrop(ModBlocks.BLACK_SHROOM_BLOCK, mushroomBlockDrops(ModBlocks.BLACK_SHROOM_BLOCK, ModBlocks.BLACK_MUSHROOM));
        addDrop(ModBlocks.RED_SHROOM_BLOCK, mushroomBlockDrops(ModBlocks.RED_SHROOM_BLOCK, Blocks.RED_MUSHROOM));
        addDrop(ModBlocks.BROWN_SHROOM_BLOCK, mushroomBlockDrops(ModBlocks.BROWN_SHROOM_BLOCK, Blocks.BROWN_MUSHROOM));
        addDrop(ModBlocks.GENERIC_SHROOM_STEM, mushroomBlockDrops(ModBlocks.GENERIC_SHROOM_STEM, ModBlocks.GENERIC_SHROOM_STEM));
        addDrop(ModBlocks.TOXIC_SHROOM_STEM, mushroomBlockDrops(ModBlocks.TOXIC_SHROOM_STEM, ModBlocks.TOXIC_SHROOM_STEM));

        addDrop(ModBlocks.SOULWOOD_LOG);
        addDrop(ModBlocks.STRIPPED_SOULWOOD_LOG);
        addDrop(ModBlocks.SOULWOOD_WOOD);
        addDrop(ModBlocks.STRIPPED_SOULWOOD_WOOD);
        addDrop(ModBlocks.SOULWOOD_PLANKS);
        addDrop(ModBlocks.SOULWOOD_SAPLING);
        addDrop(ModBlocks.SOULWOOD_LEAVES, leavesDrops(ModBlocks.SOULWOOD_LEAVES, ModBlocks.SOULWOOD_SAPLING, 0.0025f));

        addDrop(ModBlocks.SPIDERWEB_BLOCK);
        addDrop(ModBlocks.ASH_BLOCK);
        addDrop(ModBlocks.ROCK_BLOCK);
        addDrop(ModBlocks.RAINBOW_MARBLE_BLOCK);


    }
    public LootTable.Builder commonOreDrops(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, (LootPoolEntry.Builder)
                this.applyExplosionDecay(drop, ((LeafEntry.Builder)
                        ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider
                                .create(5.0f, 7.0f)))).apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }
    public LootTable.Builder lesscommonOreDrops(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, (LootPoolEntry.Builder)
                this.applyExplosionDecay(drop, ((LeafEntry.Builder)
                        ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider
                                .create(4.0f, 6.0f)))).apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }
    public LootTable.Builder uncommonOreDrops(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, (LootPoolEntry.Builder)
                this.applyExplosionDecay(drop, ((LeafEntry.Builder)
                        ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider
                                .create(3.0f, 5.0f)))).apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }
    public LootTable.Builder rareOreDrops(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop, (LootPoolEntry.Builder)
                this.applyExplosionDecay(drop, ((LeafEntry.Builder)
                        ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider
                                .create(2.0f, 4.0f)))).apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }
}
