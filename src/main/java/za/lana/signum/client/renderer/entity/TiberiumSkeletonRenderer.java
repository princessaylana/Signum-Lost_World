/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import za.lana.signum.Signum;
import za.lana.signum.client.model.TiberiumSkeletonModel;
import za.lana.signum.entity.hostile.TiberiumSkeletonEntity;

public class TiberiumSkeletonRenderer extends GeoEntityRenderer<TiberiumSkeletonEntity> {
	public TiberiumSkeletonRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new TiberiumSkeletonModel());
		addRenderLayer(new AutoGlowingGeoLayer<>(this));
	}

	@Override
	public Identifier getTextureLocation(TiberiumSkeletonEntity animatable) {
		return new Identifier(Signum.MOD_ID, "textures/entity/tiberium_skeleton_texture.png");
	}

}
