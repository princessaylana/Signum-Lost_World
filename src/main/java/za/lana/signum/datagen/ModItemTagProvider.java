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
import net.minecraft.registry.tag.ItemTags;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.PLANKS)
                .add(ModBlocks.SOULWOOD_PLANKS.asItem());

        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.SOULWOOD_LOG.asItem())
                .add(ModBlocks.STRIPPED_SOULWOOD_LOG.asItem())
                .add(ModBlocks.SOULWOOD_WOOD.asItem())
                .add(ModBlocks.STRIPPED_SOULWOOD_WOOD.asItem());

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.STEEL_HELMET,ModItems.STEEL_CHESTPLATE,ModItems.STEEL_LEGGINGS,ModItems.STEEL_BOOTS);
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.BLACK_DIAMOND_HELMET,ModItems.BLACK_DIAMOND_CHESTPLATE,ModItems.BLACK_DIAMOND_LEGGINGS,ModItems.BLACK_DIAMOND_BOOTS);
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.ELEMENT_ZERO_HELMET,ModItems.ELEMENT_ZERO_CHESTPLATE,ModItems.ELEMENT_ZERO_LEGGINGS,ModItems.ELEMENT_ZERO_BOOTS);
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.EXOTIC_HELMET,ModItems.EXOTIC_CHESTPLATE,ModItems.EXOTIC_LEGGINGS,ModItems.EXOTIC_BOOTS);
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.FIRE_HELMET,ModItems.FIRE_CHESTPLATE,ModItems.FIRE_LEGGINGS,ModItems.FIRE_BOOTS);
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.ICE_HELMET,ModItems.ICE_CHESTPLATE,ModItems.ICE_LEGGINGS,ModItems.ICE_BOOTS);
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.MOISSANITE_HELMET,ModItems.MOISSANITE_CHESTPLATE,ModItems.MOISSANITE_LEGGINGS,ModItems.MOISSANITE_BOOTS);
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.QUARTZ_HELMET,ModItems.QUARTZ_CHESTPLATE,ModItems.QUARTZ_LEGGINGS,ModItems.QUARTZ_BOOTS);
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.TIBERIUM_HELMET,ModItems.TIBERIUM_CHESTPLATE,ModItems.TIBERIUM_LEGGINGS,ModItems.TIBERIUM_BOOTS);

    }

}
