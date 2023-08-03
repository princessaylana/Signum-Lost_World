/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.client.model;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import za.lana.signum.Signum;
import za.lana.signum.entity.hostile.AirDroneEntity;

public class AirDroneModel extends GeoModel<AirDroneEntity> {
    @Override
    public Identifier getModelResource(AirDroneEntity object) {
        return new Identifier(Signum.MOD_ID, "geo/entity/airdrone.geo.json");
    }

    @Override
    public Identifier getTextureResource(AirDroneEntity object) {
        return new Identifier(Signum.MOD_ID, "textures/entity/airdrone_texture.png");
    }

    @Override
    public Identifier getAnimationResource(AirDroneEntity animatable) {
        return new Identifier(Signum.MOD_ID, "animations/entity/airdrone.animation.json");
    }
}
