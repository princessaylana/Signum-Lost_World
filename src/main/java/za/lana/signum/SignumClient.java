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
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import software.bernie.example.client.renderer.entity.ParasiteRenderer;
import software.bernie.example.registry.EntityRegistry;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.client.renderer.entity.AirDroneRenderer;
import za.lana.signum.client.renderer.entity.GhostRenderer;
import za.lana.signum.client.renderer.entity.SigAlienRenderer;
import za.lana.signum.client.renderer.entity.TiberiumWormRenderer;
import za.lana.signum.client.renderer.transport.AirBalloonRenderer;
import za.lana.signum.client.renderer.transport.SkyCarRenderer;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.networking.ModMessages;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.particle.custom.BlueDustParticle;
import za.lana.signum.particle.custom.TiberiumParticle;
import za.lana.signum.screen.ModScreenHandlers;
import za.lana.signum.screen.SkyForgeScreen;
import za.lana.signum.screen.gui.ExampleBlockScreen;
import za.lana.signum.screen.gui.ExampleDescription;


public class SignumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TIBERIUM_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BUDDING_TIBERIUM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_TIBERIUM_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_TIBERIUM_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_TIBERIUM_BUD, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.TOXICBALL, (context) ->
                new FlyingItemEntityRenderer<>(context));
        EntityRendererRegistry.register(ModEntities.LASERBOLT, (context) ->
                new FlyingItemEntityRenderer<>(context));

        EntityRendererRegistry.register(ModEntities.TIBERIUM_WORM, TiberiumWormRenderer::new);
        EntityRendererRegistry.register(ModEntities.GHOST, GhostRenderer::new);
        EntityRendererRegistry.register(ModEntities.SIGALIEN, SigAlienRenderer::new);
        EntityRendererRegistry.register(ModEntities.AIRDRONE, AirDroneRenderer::new);
        EntityRendererRegistry.register(ModEntities.SKYCAR, SkyCarRenderer::new);
        EntityRendererRegistry.register(ModEntities.AIRBALLOON, AirBalloonRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.BlUE_DUST_PARTICLE, BlueDustParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.TIBERIUM_PARTICLE, TiberiumParticle.Factory::new);

        ModMessages.registerS2CPackets();
        HandledScreens.register(ModScreenHandlers.SKYFORGE_SCREENHANDLER, SkyForgeScreen::new);

        HandledScreens.<ExampleDescription, CottonInventoryScreen<ExampleDescription>>register(
               Signum.EXAMPLE_GUI, CottonInventoryScreen::new);

       // HandledScreens.<ExampleDescription, ExampleBlockScreen>register(Signum.EXAMPLE_GUI, (gui, inventory, title) ->
       //         new ExampleBlockScreen(gui, inventory.player, title));

        Signum.LOGGER.info("Client Initialized " + Signum.MOD_ID);
    }
}
