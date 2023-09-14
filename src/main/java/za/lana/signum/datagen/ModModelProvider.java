/**
 * SIGNUM
 * Datagenerator file
 * MIT License
 * Lana
 * */
package za.lana.signum.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        //blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BLIGHT_BLOCK);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MOISSANITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_MOISSANITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MOISSANITE_BLOCK);

        //BlockStateModelGenerator.BlockTexturePool manganesePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MANGANESE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MANGANESE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MANGANESE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_MANGANESE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHERRACK_MANGANESE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ENDSTONE_MANGANESE_ORE);


        //BlockStateModelGenerator.BlockTexturePool tiberiumPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.TIBERIUM_BLOCK);
        /**
        blockStateModelGenerator.registerAmethyst(ModBlocks.BUDDING_TIBERIUM);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_TIBERIUM_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_TIBERIUM_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_TIBERIUM_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.TIBERIUM_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.TIBERIUM_BLOCK);
         **/
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.COKECOAL, Models.GENERATED);

        itemModelGenerator.register(ModItems.ELEMENT_ZERO_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.ELEMENT_ZERO_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.ELEMENTZEROCOAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.FIRE_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.FIRE_CRYSTAL_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.FIRE_CRYSTAL_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.GLASS_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.MANGANESE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.MANGANESE_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MOISSANITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.MOISSANITE_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.QUARTZ_CRYSTAL_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_MANGANESE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_ELEMENT_ZERO, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_MOISSANITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_QUARTZ_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.SIGSTEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SIGSTAINSTEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIBERIUM_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIBERIUM_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIBERIUM_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIBERIUMCOAL, Models.GENERATED);

    }
}
