/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.client.model;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import za.lana.signum.Signum;
import za.lana.signum.entity.transport.AirBalloonEntity;
import za.lana.signum.entity.transport.SkyCarEntity;

public class AirBalloonModel extends GeoModel<AirBalloonEntity> {
	public AirBalloonModel() {
		super();
	}
	@Override
	public Identifier getModelResource(AirBalloonEntity animatable) {
		return new Identifier(Signum.MOD_ID, "geo/transport/airballoon.geo.json");
	}
	@Override
	public Identifier getTextureResource(AirBalloonEntity animatable) {
		return new Identifier(Signum.MOD_ID, "textures/transport/airballoon_texture.png");
	}
	@Override
	public Identifier getAnimationResource(AirBalloonEntity animatable) {
		return new Identifier(Signum.MOD_ID, "animations/transport/airballoon.animation.json");
	}

}