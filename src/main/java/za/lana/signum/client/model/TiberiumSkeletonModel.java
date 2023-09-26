/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.client.model;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import za.lana.signum.Signum;
import za.lana.signum.entity.hostile.TiberiumSkeletonEntity;

public class TiberiumSkeletonModel extends GeoModel<TiberiumSkeletonEntity> {
	public TiberiumSkeletonModel() {
		super();
	}
	@Override
	public Identifier getModelResource(TiberiumSkeletonEntity animatable) {
		return new Identifier(Signum.MOD_ID, "geo/entity/tiberium_skeleton.geo.json");
	}
	@Override
	public Identifier getTextureResource(TiberiumSkeletonEntity animatable) {
		return new Identifier(Signum.MOD_ID, "textures/entity/tiberium_skeleton_texture.png");
	}
	@Override
	public Identifier getAnimationResource(TiberiumSkeletonEntity animatable) {
		return new Identifier(Signum.MOD_ID, "animations/entity/tiberium_skeleton.animation.json");
	}

}