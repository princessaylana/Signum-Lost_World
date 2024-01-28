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
import net.minecraft.registry.tag.ItemTags;
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
                    .add(ModBlocks.ELEMENT_ZERO_BLOCK)

                    .add(ModBlocks.BLACK_DIAMOND_ORE)
                    .add(ModBlocks.DEEPSLATE_BLACK_DIAMOND_ORE)
                    .add(ModBlocks.BLACK_DIAMOND_BLOCK)

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

                    .add(ModBlocks.BUDDING_ICE_CRYSTAL)
                    .add(ModBlocks.LARGE_ICE_CRYSTAL_BUD)
                    .add(ModBlocks.MEDIUM_ICE_CRYSTAL_BUD)
                    .add(ModBlocks.SMALL_ICE_CRYSTAL_BUD)
                    .add(ModBlocks.ICE_CRYSTAL_CLUSTER)
                    .add(ModBlocks.ICE_CRYSTAL_BLOCK)

                    .add(ModBlocks.BUDDING_EXOTIC_CRYSTAL)
                    .add(ModBlocks.LARGE_EXOTIC_CRYSTAL_BUD)
                    .add(ModBlocks.MEDIUM_EXOTIC_CRYSTAL_BUD)
                    .add(ModBlocks.SMALL_EXOTIC_CRYSTAL_BUD)
                    .add(ModBlocks.EXOTIC_CRYSTAL_CLUSTER)
                    .add(ModBlocks.EXOTIC_CRYSTAL_BLOCK)

                    .add(ModBlocks.BUDDING_QUARTZ_CRYSTAL)
                    .add(ModBlocks.LARGE_QUARTZ_CRYSTAL_BUD)
                    .add(ModBlocks.MEDIUM_QUARTZ_CRYSTAL_BUD)
                    .add(ModBlocks.SMALL_QUARTZ_CRYSTAL_BUD)
                    .add(ModBlocks.QUARTZ_CRYSTAL_CLUSTER)
                    .add(ModBlocks.QUARTZ_CRYSTAL_BLOCK)

                    .add(ModBlocks.RAINBOW_MARBLE_BLOCK)
                    .add(ModBlocks.AIRSHIP_LANDING_BLOCK)
                    .add(ModBlocks.RAZORWIRE_BLOCK)
                    .add(ModBlocks.SKYFORGE)
                    .add(ModBlocks.VAULT_BLOCK)
                    .add(ModBlocks.BANKER_BLOCK)
                    .add(ModBlocks.ZEDVENDING_BLOCK)
                    .add(ModBlocks.SKY_ICE_BLOCK)
                    .add(ModBlocks.FROSTED_SKY_ICE_BLOCK);


        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.BLIGHT_BLOCK);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ELEMENT_ZERO_ORE)
                .add(ModBlocks.DEEPSLATE_ELEMENT_ZERO_ORE)
                .add(ModBlocks.ELEMENT_ZERO_BLOCK)

                .add(ModBlocks.BLACK_DIAMOND_ORE)
                .add(ModBlocks.DEEPSLATE_BLACK_DIAMOND_ORE)
                .add(ModBlocks.BLACK_DIAMOND_BLOCK)

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

                .add(ModBlocks.BUDDING_ICE_CRYSTAL)
                .add(ModBlocks.LARGE_ICE_CRYSTAL_BUD)
                .add(ModBlocks.MEDIUM_ICE_CRYSTAL_BUD)
                .add(ModBlocks.SMALL_ICE_CRYSTAL_BUD)
                .add(ModBlocks.ICE_CRYSTAL_CLUSTER)
                .add(ModBlocks.ICE_CRYSTAL_BLOCK)

                .add(ModBlocks.BUDDING_EXOTIC_CRYSTAL)
                .add(ModBlocks.LARGE_EXOTIC_CRYSTAL_BUD)
                .add(ModBlocks.MEDIUM_EXOTIC_CRYSTAL_BUD)
                .add(ModBlocks.SMALL_EXOTIC_CRYSTAL_BUD)
                .add(ModBlocks.EXOTIC_CRYSTAL_CLUSTER)
                .add(ModBlocks.EXOTIC_CRYSTAL_BLOCK)

                .add(ModBlocks.BUDDING_QUARTZ_CRYSTAL)
                .add(ModBlocks.LARGE_QUARTZ_CRYSTAL_BUD)
                .add(ModBlocks.MEDIUM_QUARTZ_CRYSTAL_BUD)
                .add(ModBlocks.SMALL_QUARTZ_CRYSTAL_BUD)
                .add(ModBlocks.QUARTZ_CRYSTAL_CLUSTER)
                .add(ModBlocks.QUARTZ_CRYSTAL_BLOCK)

                .add(ModBlocks.RAINBOW_MARBLE_BLOCK)
                .add(ModBlocks.AIRSHIP_LANDING_BLOCK)
                .add(ModBlocks.RAZORWIRE_BLOCK)
                .add(ModBlocks.SKYFORGE)
                .add(ModBlocks.VAULT_BLOCK)
                .add(ModBlocks.BANKER_BLOCK)
                .add(ModBlocks.ZEDVENDING_BLOCK)
                .add(ModBlocks.SKY_ICE_BLOCK)
                .add(ModBlocks.FROSTED_SKY_ICE_BLOCK);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.BLIGHT_BLOCK);

        getOrCreateTagBuilder(BlockTags.FENCES)
                .add(ModBlocks.MANGANESE_FENCE);
        getOrCreateTagBuilder(BlockTags.FENCE_GATES)
                .add(ModBlocks.MANGANESE_GATE);
        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(ModBlocks.MANGANESE_WALL);
        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(ModBlocks.MANGANESE_WALL);

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.SOULWOOD_LOG)
                .add(ModBlocks.STRIPPED_SOULWOOD_LOG)
                .add(ModBlocks.SOULWOOD_WOOD)
                .add(ModBlocks.STRIPPED_SOULWOOD_WOOD);

    }
}
