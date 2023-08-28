/**
 * SIGNUM
 * Datagenerator file
 * MIT License
 * Lana
 * */
package za.lana.signum.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.item.ModItems;
import za.lana.signum.recipe.ModRecipes;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> MANGANESE_SMELTABLES = List.of(ModItems.RAW_MANGANESE,
            ModBlocks.MANGANESE_ORE, ModBlocks.DEEPSLATE_MANGANESE_ORE, ModBlocks.NETHERRACK_MANGANESE_ORE, ModBlocks.ENDSTONE_MANGANESE_ORE);

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter, MANGANESE_SMELTABLES, RecipeCategory.MISC, ModItems.MANGANESE_INGOT,
                0.7f, 300, "manganese");
        offerBlasting(exporter, MANGANESE_SMELTABLES, RecipeCategory.MISC, ModItems.MANGANESE_INGOT,
                0.7f, 150, "manganese");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.MANGANESE_INGOT, RecipeCategory.DECORATIONS,
                ModBlocks.MANGANESE_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.MANGANESE_NUGGET, RecipeCategory.MISC,
                ModItems.MANGANESE_INGOT);

    }
}

/**
 *         offerSmelting(exporter, List.of(ModItems.RAW_MANGANESE), RecipeCategory.MISC, ModItems.RAW_MANGANESE,
 *                 0.7F,400,"manganese");
 *
 *         offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.MANGANESE_INGOT,
 *                 RecipeCategory.DECORATIONS, ModBlocks.MANGANESE_BLOCK);
 *
 *         //THIS SERVES AS AN EXAMPLE ONLY BELOW
 *         ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_MANGANESE)
 *                 .pattern("SSS")
 *                 .pattern("SCS")
 *                 .pattern("SSS")
 *                 .input('S', Items.STONE)
 *                 .input('C', ModItems.RAW_MANGANESE)
 *                 .criterion(FabricRecipeProvider.hasItem(Items.STONE),
 *                         FabricRecipeProvider.conditionsFromItem(Items.STONE))
 *                 .criterion(FabricRecipeProvider.hasItem(ModItems.RAW_MANGANESE),
 *                         FabricRecipeProvider.conditionsFromItem(ModItems.RAW_MANGANESE))
 *                 .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.RAW_MANGANESE)));
 */
