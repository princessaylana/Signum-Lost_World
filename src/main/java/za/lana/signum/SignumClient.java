/**
 * SIGNUM
 * This is the client entry point
 * MIT License
 * Lana
 * */
package za.lana.signum;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.client.renderer.entity.AirDroneRenderer;
import za.lana.signum.client.renderer.entity.GhostRenderer;
import za.lana.signum.client.renderer.entity.SigAlienRenderer;
import za.lana.signum.client.renderer.entity.TiberiumWormRenderer;
import za.lana.signum.client.renderer.transport.AirBalloonRenderer;
import za.lana.signum.client.renderer.transport.SkyCarRenderer;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.event.KeyInputHandler;
import za.lana.signum.networking.ModMessages;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.particle.custom.BlueDustParticle;
import za.lana.signum.particle.custom.TiberiumParticle;
import za.lana.signum.screen.AirBalloonVScreen;
import za.lana.signum.screen.ModScreenHandlers;
import za.lana.signum.screen.SkyForgeScreen;
import za.lana.signum.screen.gui.*;


public class SignumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        KeyInputHandler.register();
        ModMessages.registerS2CPackets();

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TIBERIUM_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BUDDING_TIBERIUM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_TIBERIUM_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_TIBERIUM_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_TIBERIUM_BUD, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXAMPLE_BLOCK, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.TOXICBALL, (context) ->
                new FlyingItemEntityRenderer<>(context));
        EntityRendererRegistry.register(ModEntities.LASERBOLT, FlyingItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.TIBERIUM_WORM, TiberiumWormRenderer::new);
        EntityRendererRegistry.register(ModEntities.GHOST, GhostRenderer::new);
        EntityRendererRegistry.register(ModEntities.SIGALIEN, SigAlienRenderer::new);
        EntityRendererRegistry.register(ModEntities.AIRDRONE, AirDroneRenderer::new);
        EntityRendererRegistry.register(ModEntities.SKYCAR, SkyCarRenderer::new);
        EntityRendererRegistry.register(ModEntities.AIRBALLOON, AirBalloonRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.BlUE_DUST_PARTICLE, BlueDustParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.TIBERIUM_PARTICLE, TiberiumParticle.Factory::new);
        // Network packets
        ModMessages.registerS2CPackets();
        // Fabric screen
        HandledScreens.register(ModScreenHandlers.SKYFORGE_SCREENHANDLER, SkyForgeScreen::new);
        HandledScreens.register(ModScreenHandlers.AIRBALLOON_SCREENHANDLER, AirBalloonVScreen::new);
        // LibGui screens
        HandledScreens.<ExampleDescription, CottonInventoryScreen<ExampleDescription>>register(
                GuiScreens.EXAMPLE_GUI, ExampleBlockScreen::new);
        HandledScreens.<AirBalloonDescription, CottonInventoryScreen<AirBalloonDescription>>register(
                GuiScreens.AB_GUI, AirBalloonScreen::new);

        Signum.LOGGER.info("Client Initialized " + Signum.MOD_ID);
    }
}
