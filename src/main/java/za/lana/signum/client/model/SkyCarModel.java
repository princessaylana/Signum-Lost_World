/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.client.model;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import za.lana.signum.Signum;
import za.lana.signum.entity.transport.SkyCarEntity;

public class SkyCarModel extends GeoModel<SkyCarEntity> {
	public SkyCarModel() {
		super();
	}
	@Override
	public Identifier getModelResource(SkyCarEntity animatable) {
		return new Identifier(Signum.MOD_ID, "geo/transport/skycar.geo.json");
	}
	@Override
	public Identifier getTextureResource(SkyCarEntity animatable) {
		return new Identifier(Signum.MOD_ID, "textures/transport/skycar_texture.png");
	}
	@Override
	public Identifier getAnimationResource(SkyCarEntity animatable) {
		return new Identifier(Signum.MOD_ID, "animations/transport/skycar.animation.json");
	}

}