/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.client.renderer.transport;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SpiderEyesFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.AirBalloonModel;
import za.lana.signum.client.model.UnicornEntityModel;
import za.lana.signum.client.renderer.feature.UnicornEyesFeatureRenderer;
import za.lana.signum.entity.mob.UnicornEntity;
import za.lana.signum.entity.transport.AirBalloonEntity;

public class AirBalloonRenderer extends MobEntityRenderer<AirBalloonEntity, AirBalloonModel<AirBalloonEntity>> {
	private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/transport/airballoon_texture.png");

	public AirBalloonRenderer(EntityRendererFactory.Context context) {
		super(context, new AirBalloonModel<>(context.getPart(ModModelLayers.AIRBALLOON)), 0.6f); //entity shadow
		//this.addFeature(new UnicornEyesFeatureRenderer<>(this));
	}

	@Override
	public Identifier getTexture(AirBalloonEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(AirBalloonEntity mobEntity, float f, float g, MatrixStack matrixStack,
					   VertexConsumerProvider vertexConsumerProvider, int i) {
		if(mobEntity.isBaby()){
			matrixStack.scale(0.5f, 0.5f, 0.5f);
		} else {
			matrixStack.scale(1.0f, 1.0f ,1.0f);
		}

		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

	}
}
