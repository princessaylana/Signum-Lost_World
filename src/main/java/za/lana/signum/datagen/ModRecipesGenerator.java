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
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.item.ModItems;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipesGenerator extends FabricRecipeProvider {
    public ModRecipesGenerator(FabricDataOutput output) {
        super(output);
    }

    //SOME CUSTOM RECIPES DOESNT WANT TO DATAGEN, AND SOME ARE GIVING
    // A ERROR WHEN SIMILIAR, LIKE INGOT TO NUGGET, INGOT TO BLOCK
    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter, List.of(ModItems.RAW_MANGANESE), RecipeCategory.MISC, ModItems.RAW_MANGANESE,
                0.7F,400,"manganese");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.MANGANESE_INGOT,
                RecipeCategory.DECORATIONS, ModBlocks.MANGANESE_BLOCK);

        //THIS SERVES AS AN EXAMPLE ONLY BELOW
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_MANGANESE)
                .pattern("SSS")
                .pattern("SCS")
                .pattern("SSS")
                .input('S', Items.STONE)
                .input('C', ModItems.RAW_MANGANESE)
                .criterion(FabricRecipeProvider.hasItem(Items.STONE),
                        FabricRecipeProvider.conditionsFromItem(Items.STONE))
                .criterion(FabricRecipeProvider.hasItem(ModItems.RAW_MANGANESE),
                        FabricRecipeProvider.conditionsFromItem(ModItems.RAW_MANGANESE))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.RAW_MANGANESE)));

    }
}
