/**
 * Registers the mod recipes
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.recipe;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;

public class ModRecipes {


    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Signum.MOD_ID, SkyForge2Recipe.Serializer.ID),
                SkyForge2Recipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Signum.MOD_ID, SkyForge2Recipe.Type.ID),
                SkyForge2Recipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(Signum.MOD_ID, SkyForgeRecipe.Serializer.ID),
                SkyForgeRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier(Signum.MOD_ID, SkyForgeRecipe.Type.ID),
                SkyForgeRecipe.Type.INSTANCE);





    }
}
