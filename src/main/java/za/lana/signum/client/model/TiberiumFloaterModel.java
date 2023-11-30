package za.lana.signum.client.model;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import za.lana.signum.Signum;
import za.lana.signum.entity.hostile.TiberiumFloaterEntity;

public class TiberiumFloaterModel extends GeoModel<TiberiumFloaterEntity> {
    @Override
    public Identifier getModelResource(TiberiumFloaterEntity animatable) {
        return new Identifier(Signum.MOD_ID, "geo/entity/tiberium_floater.geo.json");
    }

    @Override
    public Identifier getTextureResource(TiberiumFloaterEntity animatable) {
        return new Identifier(Signum.MOD_ID, "textures/entity/tiberium_floater_texture.png");
    }

    @Override
    public Identifier getAnimationResource(TiberiumFloaterEntity animatable) {
        return new Identifier(Signum.MOD_ID, "animations/entity/tfloater.animation.json");
    }
}
