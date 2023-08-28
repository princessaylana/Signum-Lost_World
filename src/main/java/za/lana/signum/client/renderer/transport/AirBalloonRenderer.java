/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.client.renderer.transport;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import za.lana.signum.Signum;
import za.lana.signum.client.model.AirBalloonModel;
import za.lana.signum.client.model.SkyCarModel;
import za.lana.signum.entity.transport.AirBalloonEntity;
import za.lana.signum.entity.transport.SkyCarEntity;

public class AirBalloonRenderer extends GeoEntityRenderer<AirBalloonEntity> {
	public AirBalloonRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new AirBalloonModel());
	}
	@Override
	public Identifier getTextureLocation(AirBalloonEntity animatable) {
		return new Identifier(Signum.MOD_ID, "textures/transport/airballoon_texture.png");
	}

}
