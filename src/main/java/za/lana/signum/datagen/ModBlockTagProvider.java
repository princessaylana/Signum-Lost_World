/**
 * SIGNUM
 * Datagenerator file
 * MIT License
 * Lana
 * */
package za.lana.signum.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import za.lana.signum.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                    .add(ModBlocks.ELEMENT_ZERO_ORE)
                    .add(ModBlocks.DEEPSLATE_ELEMENT_ZERO_ORE)

                    .add(ModBlocks.MANGANESE_ORE)
                    .add(ModBlocks.DEEPSLATE_MANGANESE_ORE)
                    .add(ModBlocks.NETHERRACK_MANGANESE_ORE)
                    .add(ModBlocks.ENDSTONE_MANGANESE_ORE)
                    .add(ModBlocks.MANGANESE_BLOCK)

                    .add(ModBlocks.MOISSANITE_ORE)
                    .add(ModBlocks.DEEPSLATE_MOISSANITE_ORE)
                    .add(ModBlocks.MOISSANITE_BLOCK)

                    .add(ModBlocks.BUDDING_TIBERIUM)
                    .add(ModBlocks.LARGE_TIBERIUM_BUD)
                    .add(ModBlocks.MEDIUM_TIBERIUM_BUD)
                    .add(ModBlocks.SMALL_TIBERIUM_BUD)
                    .add(ModBlocks.TIBERIUM_CLUSTER)
                    .add(ModBlocks.TIBERIUM_BLOCK)

                    .add(ModBlocks.BUDDING_FIRE_CRYSTAL)
                    .add(ModBlocks.LARGE_FIRE_CRYSTAL_BUD)
                    .add(ModBlocks.MEDIUM_FIRE_CRYSTAL_BUD)
                    .add(ModBlocks.SMALL_FIRE_CRYSTAL_BUD)
                    .add(ModBlocks.FIRE_CRYSTAL_CLUSTER)
                    .add(ModBlocks.FIRE_CRYSTAL_BLOCK)

                    .add(ModBlocks.ASSEMBLY_STATION_BLOCK)
                    .add(ModBlocks.RAZORWIRE_BLOCK)
                    .add(ModBlocks.SKYFORGE);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.BLIGHT_BLOCK);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ELEMENT_ZERO_ORE)
                .add(ModBlocks.DEEPSLATE_ELEMENT_ZERO_ORE)

                .add(ModBlocks.MANGANESE_ORE)
                .add(ModBlocks.DEEPSLATE_MANGANESE_ORE)
                .add(ModBlocks.NETHERRACK_MANGANESE_ORE)
                .add(ModBlocks.ENDSTONE_MANGANESE_ORE)
                .add(ModBlocks.MANGANESE_BLOCK)

                .add(ModBlocks.MOISSANITE_ORE)
                .add(ModBlocks.DEEPSLATE_MOISSANITE_ORE)
                .add(ModBlocks.MOISSANITE_BLOCK)

                .add(ModBlocks.BUDDING_TIBERIUM)
                .add(ModBlocks.LARGE_TIBERIUM_BUD)
                .add(ModBlocks.MEDIUM_TIBERIUM_BUD)
                .add(ModBlocks.SMALL_TIBERIUM_BUD)
                .add(ModBlocks.TIBERIUM_CLUSTER)
                .add(ModBlocks.TIBERIUM_BLOCK)

                .add(ModBlocks.BUDDING_FIRE_CRYSTAL)
                .add(ModBlocks.LARGE_FIRE_CRYSTAL_BUD)
                .add(ModBlocks.MEDIUM_FIRE_CRYSTAL_BUD)
                .add(ModBlocks.SMALL_FIRE_CRYSTAL_BUD)
                .add(ModBlocks.FIRE_CRYSTAL_CLUSTER)
                .add(ModBlocks.FIRE_CRYSTAL_BLOCK)

                .add(ModBlocks.ASSEMBLY_STATION_BLOCK)
                .add(ModBlocks.RAZORWIRE_BLOCK)
                .add(ModBlocks.SKYFORGE);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.BLIGHT_BLOCK);

    }
}
