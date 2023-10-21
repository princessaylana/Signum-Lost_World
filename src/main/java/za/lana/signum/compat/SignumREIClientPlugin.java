package za.lana.signum.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.recipe.SkyForgeRecipe;
import za.lana.signum.screen.SkyForgeScreen;

public class SignumREIClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories (CategoryRegistry registry){
      registry.add(new SkyForgeCategory());
      registry.addWorkstations(SkyForgeCategory.SKYFORGE, EntryStacks.of(ModBlocks.SKYFORGE));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
     registry.registerRecipeFiller(SkyForgeRecipe.class, SkyForgeRecipe.Type.INSTANCE, SkyForgeDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
       registry.registerClickArea(screen -> new Rectangle(75, 30, 25, 30),
               SkyForgeScreen.class, SkyForgeCategory.SKYFORGE);
    }
}
