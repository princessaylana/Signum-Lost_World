/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import za.lana.signum.Signum;
import za.lana.signum.client.model.GhostModel;
import za.lana.signum.client.model.TiberiumFloaterModel;
import za.lana.signum.entity.hostile.GhostEntity;
import za.lana.signum.entity.hostile.TiberiumFloaterEntity;

public class TiberiumFloaterRenderer extends GeoEntityRenderer<TiberiumFloaterEntity> {
	public TiberiumFloaterRenderer(EntityRendererFactory.Context renderManager) {
		super(renderManager, new TiberiumFloaterModel());
		//addRenderLayer(new AutoGlowingGeoLayer<>(this));
	}

	@Override
	public Identifier getTextureLocation(TiberiumFloaterEntity animatable) {
		return new Identifier(Signum.MOD_ID, "textures/entity/tiberium_floater_texture.png");
	}

}
