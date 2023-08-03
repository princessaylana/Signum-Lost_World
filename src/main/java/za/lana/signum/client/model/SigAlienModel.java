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
import za.lana.signum.entity.hostile.SigAlienEntity;

public class SigAlienModel extends GeoModel<SigAlienEntity> {
    public SigAlienModel() {
        super();
    }

    @Override
    public Identifier getModelResource(SigAlienEntity animatable) {
        return new Identifier(Signum.MOD_ID, "geo/entity/sigalien.geo.json");
    }

    @Override
    public Identifier getTextureResource(SigAlienEntity animatable) {
        return new Identifier(Signum.MOD_ID, "textures/entity/sigalien_texture.png");
    }

    @Override
    public Identifier getAnimationResource(SigAlienEntity animatable) {
        return new Identifier(Signum.MOD_ID, "animations/entity/sigalien.animation.json");
    }
}
