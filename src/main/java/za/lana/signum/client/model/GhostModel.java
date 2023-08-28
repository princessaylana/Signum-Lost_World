/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.client.model;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import za.lana.signum.Signum;
import za.lana.signum.entity.hostile.GhostEntity;
import za.lana.signum.entity.hostile.TiberiumWormEntity;

public class GhostModel extends GeoModel<GhostEntity> {
	public GhostModel() {
		super();
	}

	@Override
	public Identifier getModelResource(GhostEntity animatable) {
		return new Identifier(Signum.MOD_ID, "geo/entity/ghost.geo.json");
	}

	@Override
	public Identifier getTextureResource(GhostEntity animatable) {
		return new Identifier(Signum.MOD_ID, "textures/entity/ghost_texture.png");
	}

	@Override
	public Identifier getAnimationResource(GhostEntity animatable) {
		return new Identifier(Signum.MOD_ID, "animations/entity/ghost.animation.json");
	}

}