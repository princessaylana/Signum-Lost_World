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
import za.lana.signum.client.model.GhostModel;
import za.lana.signum.client.model.TiberiumWormModel;
import za.lana.signum.entity.hostile.GhostEntity;
import za.lana.signum.entity.hostile.TiberiumWormEntity;

public class GhostRenderer extends GeoEntityRenderer<GhostEntity> {
	public GhostRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new GhostModel());
	}

	@Override
	public Identifier getTextureLocation(GhostEntity animatable) {
		return new Identifier(Signum.MOD_ID, "textures/entity/ghost_texture.png");
	}

}
