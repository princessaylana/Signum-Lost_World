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
import za.lana.signum.client.model.SkyCarModel;
import za.lana.signum.entity.transport.SkyCarEntity;

public class SkyCarRenderer extends GeoEntityRenderer<SkyCarEntity> {
	public SkyCarRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new SkyCarModel());
	}
	@Override
	public Identifier getTextureLocation(SkyCarEntity animatable) {
		return new Identifier(Signum.MOD_ID, "textures/transport/skycar_texture.png");
	}

}
