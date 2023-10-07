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

        BlockStateModelGenerator.BlockTexturePool manganesePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MANGANESE_BLOCK);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MOISSANITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_MOISSANITE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MOISSANITE_BLOCK);

        //blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MANGANESE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.MANGANESE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_MANGANESE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHERRACK_MANGANESE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ENDSTONE_MANGANESE_ORE);

        manganesePool.stairs(ModBlocks.MANGANESE_STAIRS);
        manganesePool.fence(ModBlocks.MANGANESE_FENCE);
        manganesePool.fenceGate(ModBlocks.MANGANESE_GATE);
        manganesePool.wall(ModBlocks.MANGANESE_WALL);
        manganesePool.pressurePlate(ModBlocks.MANGANESE_PRESSURE_PLATE);
        manganesePool.slab(ModBlocks.MANGANESE_SLAB);
        manganesePool.button(ModBlocks.MANGANESE_BUTTON);
        blockStateModelGenerator.registerDoor(ModBlocks.MANGANESE_DOOR);
        blockStateModelGenerator.registerTrapdoor(ModBlocks.MANGANESE_TRAPDOOR);

        blockStateModelGenerator.registerMushroomBlock(ModBlocks.ORANGE_SHROOM_BLOCK);
        blockStateModelGenerator.registerMushroomBlock(ModBlocks.YELLOW_SHROOM_BLOCK);
        blockStateModelGenerator.registerMushroomBlock(ModBlocks.TOXIC_SHROOM_BLOCK);
        blockStateModelGenerator.registerMushroomBlock(ModBlocks.BLUE_SHROOM_BLOCK);
        blockStateModelGenerator.registerMushroomBlock(ModBlocks.PURPLE_SHROOM_BLOCK);
        blockStateModelGenerator.registerMushroomBlock(ModBlocks.PINK_SHROOM_BLOCK);

        blockStateModelGenerator.registerMushroomBlock(ModBlocks.GENERIC_SHROOM_STEM);
        blockStateModelGenerator.registerMushroomBlock(ModBlocks.TOXIC_SHROOM_STEM);



    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.ELEMENTZEROCOAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIBERIUMCOAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.COKECOAL, Models.GENERATED);

        itemModelGenerator.register(ModItems.GLASS_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.MANGANESE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.MANGANESE_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MANGANESE_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.MOISSANITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.MOISSANITE_DUST, Models.GENERATED);

        itemModelGenerator.register(ModItems.RAW_MANGANESE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_ELEMENT_ZERO, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_MOISSANITE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_QUARTZ_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.SIGSTEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SIGSTAINSTEEL_INGOT, Models.GENERATED);

        itemModelGenerator.register(ModItems.TIBERIUM_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIBERIUM_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIBERIUM_DUST, Models.GENERATED);

        itemModelGenerator.register(ModItems.FIRE_CRYSTAL_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.FIRE_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.FIRE_CRYSTAL_DUST, Models.GENERATED);

        itemModelGenerator.register(ModItems.ICE_CRYSTAL_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.ICE_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.ICE_CRYSTAL_DUST, Models.GENERATED);

        itemModelGenerator.register(ModItems.EXOTIC_CRYSTAL_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.EXOTIC_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.EXOTIC_CRYSTAL_DUST, Models.GENERATED);

        itemModelGenerator.register(ModItems.QUARTZ_CRYSTAL_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.QUARTZ_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.QUARTZ_CRYSTAL_DUST, Models.GENERATED);

        itemModelGenerator.register(ModItems.MOISSANITE_CRYSTAL, Models.GENERATED);

        itemModelGenerator.register(ModItems.ELEMENT_ZERO_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.ELEMENT_ZERO_DUST, Models.GENERATED);



    }
}
