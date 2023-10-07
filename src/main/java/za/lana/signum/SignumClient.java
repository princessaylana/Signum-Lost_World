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
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.FireBoltEntityModel;
import za.lana.signum.client.model.IceBoltEntityModel;
import za.lana.signum.client.model.ShockBoltEntityModel;
import za.lana.signum.client.model.TiberiumBoltEntityModel;
import za.lana.signum.client.renderer.entity.*;
import za.lana.signum.client.renderer.transport.AirBalloonRenderer;
import za.lana.signum.client.renderer.transport.SkyCarRenderer;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.projectile.FireBoltEntity;
import za.lana.signum.event.KeyInputHandler;
import za.lana.signum.networking.ModMessages;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.particle.custom.*;
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
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TIBERIUM_FIRE, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ICE_CRYSTAL_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BUDDING_ICE_CRYSTAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_ICE_CRYSTAL_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_ICE_CRYSTAL_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_ICE_CRYSTAL_BUD, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXOTIC_CRYSTAL_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BUDDING_EXOTIC_CRYSTAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_EXOTIC_CRYSTAL_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_EXOTIC_CRYSTAL_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_EXOTIC_CRYSTAL_BUD, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FIRE_CRYSTAL_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BUDDING_FIRE_CRYSTAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_FIRE_CRYSTAL_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_FIRE_CRYSTAL_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_FIRE_CRYSTAL_BUD, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.QUARTZ_CRYSTAL_CLUSTER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BUDDING_QUARTZ_CRYSTAL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LARGE_QUARTZ_CRYSTAL_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MEDIUM_QUARTZ_CRYSTAL_BUD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SMALL_QUARTZ_CRYSTAL_BUD, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGANESE_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGANESE_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ORANGE_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.YELLOW_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TOXIC_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLE_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PINK_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WHITE_MUSHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACK_MUSHROOM, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SKY_ICE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROSTED_SKY_ICE_BLOCK, RenderLayer.getTranslucent());


        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EXAMPLE_BLOCK, RenderLayer.getCutout());
        // PROJECTILES
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.TIBERIUM_BOLT, TiberiumBoltEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.TIBERIUM_PROJECTILE, TiberiumBoltRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.ICE_BOLT, IceBoltEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ICE_PROJECTILE, IceBoltRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.TRANSMUTE_BOLT, IceBoltEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.TRANSMUTE_PROJECTILE, TransmuteBoltRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.FIRE_BOLT, FireBoltEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.FIRE_PROJECTILE, FireBoltRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.SHOCK_BOLT, ShockBoltEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SHOCK_PROJECTILE, ShockBoltRenderer::new);
        //
        EntityRendererRegistry.register(ModEntities.TOXICBALL, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.LASERBOLT, FlyingItemEntityRenderer::new);
        // MOBS
        EntityRendererRegistry.register(ModEntities.TIBERIUM_WORM, TiberiumWormRenderer::new);
        EntityRendererRegistry.register(ModEntities.TIBERIUM_SKELETON, TiberiumSkeletonRenderer::new);

        EntityRendererRegistry.register(ModEntities.GHOST, GhostRenderer::new);
        EntityRendererRegistry.register(ModEntities.SIGALIEN, SigAlienRenderer::new);
        EntityRendererRegistry.register(ModEntities.AIRDRONE, AirDroneRenderer::new);
        // VEHICLES
        EntityRendererRegistry.register(ModEntities.SKYCAR, SkyCarRenderer::new);
        EntityRendererRegistry.register(ModEntities.AIRBALLOON, AirBalloonRenderer::new);
        // PARTICLES
        ParticleFactoryRegistry.getInstance().register(ModParticles.BlUE_DUST_PARTICLE, BlueDustParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.TIBERIUM_PARTICLE, TiberiumParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FREEZE_PARTICLE, FreezeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.TRANSMUTE_PARTICLE, TransmuteParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FLAME_PARTICLE, FlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.TOXIC_SHROOM_PARTICLE, ToxicShroomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BLACK_SHROOM_PARTICLE, ToxicShroomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BLUE_SHROOM_PARTICLE, ToxicShroomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.BROWN_SHROOM_PARTICLE, ToxicShroomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.ORANGE_SHROOM_PARTICLE, ToxicShroomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.PINK_SHROOM_PARTICLE, ToxicShroomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.PURPLE_SHROOM_PARTICLE, ToxicShroomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.RED_SHROOM_PARTICLE, ToxicShroomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.WHITE_SHROOM_PARTICLE, ToxicShroomParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.YELLOW_SHROOM_PARTICLE, ToxicShroomParticle.Factory::new);
        // NETWORK
        ModMessages.registerS2CPackets();
        // VANILLA/FABRIC SCREENS
        HandledScreens.register(ModScreenHandlers.SKYFORGE_SCREENHANDLER, SkyForgeScreen::new);
        HandledScreens.register(ModScreenHandlers.AIRBALLOON_SCREENHANDLER, AirBalloonVScreen::new);
        // LIBGUI SCREENS
        HandledScreens.<ExampleDescription, CottonInventoryScreen<ExampleDescription>>register(
                GuiScreens.EXAMPLE_GUI, ExampleBlockScreen::new);
        HandledScreens.<AirBalloonDescription, CottonInventoryScreen<AirBalloonDescription>>register(
                GuiScreens.AB_GUI, AirBalloonScreen::new);

        Signum.LOGGER.info("Client Initialized " + Signum.MOD_ID);
    }
}
