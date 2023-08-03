/**
 * SIGNUM
 * Datagenerator file
 * MIT License
 * Lana
 * */
package za.lana.signum.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.item.ModItems;

public class ModBlockLootGenerator extends FabricBlockLootTableProvider {
    public ModBlockLootGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.MANGANESE_BLOCK);
        addDrop(ModBlocks.RAZORWIRE_BLOCK);
        addDrop(ModBlocks.SKYFORGE);

        addDrop(ModBlocks.MANGANESE_ORE, oreDrops(ModBlocks.MANGANESE_ORE, ModItems.RAW_MANGANESE));
        addDrop(ModBlocks.DEEPSLATE_MANGANESE_ORE, oreDrops(ModBlocks.DEEPSLATE_MANGANESE_ORE, ModItems.RAW_MANGANESE));
        addDrop(ModBlocks.NETHERRACK_MANGANESE_ORE, oreDrops(ModBlocks.NETHERRACK_MANGANESE_ORE, ModItems.RAW_MANGANESE));
        addDrop(ModBlocks.ENDSTONE_MANGANESE_ORE, oreDrops(ModBlocks.ENDSTONE_MANGANESE_ORE, ModItems.RAW_MANGANESE));

    }
}
