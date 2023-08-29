/**
 * SIGNUM
 * MIT License
 * Registers the mod screenhandlers for block entities
 * Lana
 * */

package za.lana.signum.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;

public class ModScreenHandlers {

    // need to update this as its depreciated
    public static ScreenHandlerType<SkyForgeScreenHandler> SKYFORGE_SCREENHANDLER =
            ScreenHandlerRegistry.registerSimple(new Identifier(Signum.MOD_ID, "skyforge"),
                    SkyForgeScreenHandler::new);

    public static void registerScreenHandler() {
        Signum.LOGGER.info("Registering Screen Handlers for " + Signum.MOD_ID);
    }

}
