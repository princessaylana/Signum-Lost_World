/**
 * Registers the mod entities
 * SIGNUM
 * MIT License
 * Lana
 * */

package za.lana.signum.client;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import za.lana.signum.Signum;
import za.lana.signum.item.custom.ToxicGunItem;

public class ToxicGunModel extends GeoModel<ToxicGunItem> {

    @Override
    public Identifier getModelResource(ToxicGunItem animatable) {
        return new Identifier(Signum.MOD_ID, "geo/item/toxicgun.geo.json");
    }

    @Override
    public Identifier getTextureResource(ToxicGunItem animatable) {
        return new Identifier(Signum.MOD_ID, "textures/item/toxicgun.png");
    }

    @Override
    public Identifier getAnimationResource(ToxicGunItem animatable) {
        return new Identifier(Signum.MOD_ID, "animations/item/toxicgun.animation.json");
    }
}
