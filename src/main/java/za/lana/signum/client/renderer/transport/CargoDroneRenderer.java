/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.client.renderer.transport;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.CargoDroneModel;
import za.lana.signum.entity.transport.CargoDroneEntity;

public class CargoDroneRenderer extends MobEntityRenderer<CargoDroneEntity, CargoDroneModel<CargoDroneEntity>> {
	private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/transport/cargo_drone_texture.png");

	public CargoDroneRenderer(EntityRendererFactory.Context context) {
		super(context, new CargoDroneModel<>(context.getPart(ModModelLayers.CARGODRONE)), 0.25f); //entity shadow
	}

	@Override
	public Identifier getTexture(CargoDroneEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(CargoDroneEntity mobEntity, float f, float g, MatrixStack matrixStack,
					   VertexConsumerProvider vertexConsumerProvider, int i) {
		if(mobEntity.isBaby()){
			matrixStack.scale(0.5f, 0.5f, 0.5f);
		} else {
			matrixStack.scale(1.0f, 1.0f ,1.0f);
		}
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

	}
}
