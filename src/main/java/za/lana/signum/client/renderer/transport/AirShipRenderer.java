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
import za.lana.signum.client.model.AirShipModel;
import za.lana.signum.entity.transport.AirShipEntity;

public class AirShipRenderer extends MobEntityRenderer<AirShipEntity, AirShipModel<AirShipEntity>> {
	private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/transport/airship_texture.png");

	public AirShipRenderer(EntityRendererFactory.Context context) {
		super(context, new AirShipModel<>(context.getPart(ModModelLayers.AIRSHIP)), 1.2f); //entity shadow
	}

	@Override
	public Identifier getTexture(AirShipEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(AirShipEntity mobEntity, float f, float g, MatrixStack matrixStack,
					   VertexConsumerProvider vertexConsumerProvider, int i) {
		if(mobEntity.isBaby()){
			matrixStack.scale(0.5f, 0.5f, 0.5f);
		} else {
			matrixStack.scale(1.0f, 1.0f ,1.0f);
		}
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

	}
}
