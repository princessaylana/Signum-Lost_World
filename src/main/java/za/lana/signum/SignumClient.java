/**
 * SIGNUM
 * This is the client entry point
 * MIT License
 * Lana
 * */
package za.lana.signum;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.networking.ModMessages;
import za.lana.signum.screen.ModScreenHandlers;
import za.lana.signum.screen.SkyForgeScreen;


public class SignumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.TOXICBALL, (context) ->
                new FlyingItemEntityRenderer(context));

        EntityRendererRegistry.register(ModEntities.LASERBOLT, (context) ->
                new FlyingItemEntityRenderer<>(context));

        ModMessages.registerS2CPackets();
        HandledScreens.register(ModScreenHandlers.SKYFORGE_SCREENHANDLER, SkyForgeScreen::new);



        Signum.LOGGER.info("Client Initialized " + Signum.MOD_ID);
    }
}
