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
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ShieldItem;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        BlockStateModelGenerator.BlockTexturePool manganesePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.MANGANESE_BLOCK);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BLACK_DIAMOND_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_BLACK_DIAMOND_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BLACK_DIAMOND_BLOCK);

        //blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ELEMENT_ZERO_ORE);
        //blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_ELEMENT_ZERO_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ELEMENT_ZERO_BLOCK);

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
        blockStateModelGenerator.registerMushroomBlock(ModBlocks.WHITE_SHROOM_BLOCK);
        blockStateModelGenerator.registerMushroomBlock(ModBlocks.BLACK_SHROOM_BLOCK);
        blockStateModelGenerator.registerMushroomBlock(ModBlocks.BROWN_SHROOM_BLOCK);
        blockStateModelGenerator.registerMushroomBlock(ModBlocks.RED_SHROOM_BLOCK);

        blockStateModelGenerator.registerMushroomBlock(ModBlocks.GENERIC_SHROOM_STEM);
        blockStateModelGenerator.registerMushroomBlock(ModBlocks.TOXIC_SHROOM_STEM);

        blockStateModelGenerator.registerLog(ModBlocks.SOULWOOD_LOG).log(ModBlocks.SOULWOOD_LOG).wood(ModBlocks.SOULWOOD_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_SOULWOOD_LOG).log(ModBlocks.STRIPPED_SOULWOOD_LOG).wood(ModBlocks.STRIPPED_SOULWOOD_WOOD);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SOULWOOD_LEAVES);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SOULWOOD_PLANKS);
        blockStateModelGenerator.registerTintableCrossBlockState(ModBlocks.SOULWOOD_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAINBOW_MARBLE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.AIRSHIP_LANDING_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ASH_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ROCK_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SPIDERWEB_BLOCK);


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
        itemModelGenerator.register(ModItems.STEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.STAINLESS_STEEL_INGOT, Models.GENERATED);

        itemModelGenerator.register(ModItems.BLACK_DIAMOND_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.BLACK_DIAMOND_CRYSTAL, Models.GENERATED);

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

        itemModelGenerator.registerArmor(((ArmorItem)ModItems.STEEL_HELMET));
        itemModelGenerator.registerArmor(((ArmorItem)ModItems.STEEL_CHESTPLATE));
        itemModelGenerator.registerArmor(((ArmorItem)ModItems.STEEL_LEGGINGS));
        itemModelGenerator.registerArmor(((ArmorItem)ModItems.STEEL_BOOTS));



    }
}
