/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.screen.gui;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;

public class GuiScreens {
    public static ScreenHandlerType<ExampleDescription> EXAMPLE_GUI =
            Registry.register(Registries.SCREEN_HANDLER,  new Identifier(Signum.MOD_ID, "example_gui"),
                    new ScreenHandlerType<>((syncId, inventory) -> new ExampleDescription(syncId, inventory, ScreenHandlerContext.EMPTY),
                            FeatureFlags.VANILLA_FEATURES));

    public static void registerGuiScreens(){
        Signum.LOGGER.info("Registering GuiScreens for " + Signum.MOD_ID);
    }
}
