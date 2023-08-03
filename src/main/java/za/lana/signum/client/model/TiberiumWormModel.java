/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.client.model;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import za.lana.signum.Signum;
import za.lana.signum.entity.hostile.TiberiumWormEntity;

public class TiberiumWormModel extends GeoModel<TiberiumWormEntity> {
	public TiberiumWormModel() {
		super();
	}
	@Override
	public Identifier getModelResource(TiberiumWormEntity animatable) {
		return new Identifier(Signum.MOD_ID, "geo/entity/tiberium_worm.geo.json");
	}
	@Override
	public Identifier getTextureResource(TiberiumWormEntity animatable) {
		return new Identifier(Signum.MOD_ID, "textures/entity/tiberium_worm_texture.png");
	}
	@Override
	public Identifier getAnimationResource(TiberiumWormEntity animatable) {
		return new Identifier(Signum.MOD_ID, "animations/entity/tworm.animation.json");
	}

}