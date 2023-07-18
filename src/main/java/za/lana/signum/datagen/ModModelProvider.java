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
    // PLEASE ADD THE VOXEL AND GEO MODELS MANUALLY TO DATA GEN FOLDER,
    // DO NOT ADD THEM HERE
    // ELSE IT WILL BREAK THE MODELS
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MANGANESE_BLOCK);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MANGANESE_ORE);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.DEEPSLATE_MANGANESE_ORE);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.NETHERRACK_MANGANESE_ORE);
        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.ENDSTONE_MANGANESE_ORE);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.RAW_MANGANESE, Models.GENERATED);
        itemModelGenerator.register(ModItems.MANGANESE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.MANGANESE_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.QUARTZ_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.GLASS_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_QUARTZ_CRYSTAL, Models.GENERATED);

    }
}
