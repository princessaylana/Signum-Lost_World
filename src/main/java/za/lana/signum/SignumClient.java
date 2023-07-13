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
import software.bernie.geckolib.renderer.GeoItemRenderer;
import za.lana.signum.client.ToxicGunRenderer;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.item.ModItems;


public class SignumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.TOXICBALL, (context) ->
                new FlyingItemEntityRenderer(context));

        EntityRendererRegistry.register(ModEntities.LASERBOLT, (context) ->
                new FlyingItemEntityRenderer<>(context));

        //below is not working
        //GeoItemRenderer.registerItemRenderer(ModItems.TOXICGUN, new ToxicGunRenderer());


        Signum.LOGGER.info("Client Initialized " + Signum.MOD_ID);
    }
}
