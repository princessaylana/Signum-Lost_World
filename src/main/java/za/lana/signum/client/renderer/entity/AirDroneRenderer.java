/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import za.lana.signum.Signum;
import za.lana.signum.client.model.AirDroneModel;
import za.lana.signum.entity.hostile.AirDroneEntity;

public class AirDroneRenderer extends GeoEntityRenderer<AirDroneEntity> {
    public AirDroneRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new AirDroneModel());
    }

    @Override
    public Identifier getTextureLocation(AirDroneEntity animatable) {
        return new Identifier(Signum.MOD_ID, "textures/entity/airdrone_texture.png");
    }

}
