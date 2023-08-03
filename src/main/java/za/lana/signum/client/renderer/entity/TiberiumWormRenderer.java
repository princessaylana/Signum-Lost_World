/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import za.lana.signum.Signum;
import za.lana.signum.client.model.TiberiumWormModel;
import za.lana.signum.entity.hostile.TiberiumWormEntity;

public class TiberiumWormRenderer extends GeoEntityRenderer<TiberiumWormEntity> {
	public TiberiumWormRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new TiberiumWormModel());
	}

	@Override
	public Identifier getTextureLocation(TiberiumWormEntity animatable) {
		return new Identifier(Signum.MOD_ID, "textures/entity/tiberium_worm_texture.png");
	}

}
