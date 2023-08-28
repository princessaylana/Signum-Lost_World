/**
 * SIGNUM
 * MIT License
 * Registers the mod screenhandlers for block entities
 * Lana
 * */

package za.lana.signum.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.screen.gui.ExampleDescription;

public class ModScreenHandlers {
    public static ScreenHandlerType<SkyForgeScreenHandler> SKYFORGE_SCREENHANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier(Signum.MOD_ID, "skyforge"),
                    SkyForgeScreenHandler::new);

    public static void registerScreenHandler() {
        Signum.LOGGER.info("Registering Screen Handlers for " + Signum.MOD_ID);
    }

}
