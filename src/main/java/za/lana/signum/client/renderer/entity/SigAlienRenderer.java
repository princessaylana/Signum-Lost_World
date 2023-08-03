/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import za.lana.signum.Signum;
import za.lana.signum.client.model.AirDroneModel;
import za.lana.signum.client.model.SigAlienModel;
import za.lana.signum.client.model.TiberiumWormModel;
import za.lana.signum.entity.hostile.AirDroneEntity;
import za.lana.signum.entity.hostile.SigAlienEntity;
import za.lana.signum.entity.hostile.TiberiumWormEntity;

public class SigAlienRenderer extends GeoEntityRenderer<SigAlienEntity> {
    public SigAlienRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SigAlienModel());
    }

    @Override
    public Identifier getTextureLocation(SigAlienEntity animatable) {
        return new Identifier(Signum.MOD_ID, "textures/entity/sigalien_texture.png");
    }

}