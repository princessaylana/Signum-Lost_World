/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import za.lana.signum.entity.projectile.IceBoltEntity;
import za.lana.signum.entity.projectile.TransmuteBoltEntity;

public class TransmuteBoltEntityModel extends EntityModel<TransmuteBoltEntity> {
	private final ModelPart mainbody;
	public TransmuteBoltEntityModel(ModelPart root) {
		this.mainbody = root.getChild("mainbody");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mainbody = modelPartData.addChild("mainbody", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -10.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
		.uv(6, 8).cuboid(-1.5F, -10.25F, -1.5F, 3.0F, 0.25F, 3.0F, new Dilation(0.0F))
		.uv(12, 11).cuboid(-1.5F, -9.5F, -2.25F, 3.0F, 3.0F, 0.25F, new Dilation(0.0F))
		.uv(12, 0).cuboid(-1.5F, -9.5F, 2.0F, 3.0F, 3.0F, 0.5F, new Dilation(0.0F))
		.uv(0, 14).cuboid(-1.25F, -9.25F, 2.5F, 2.5F, 2.5F, 0.5F, new Dilation(0.0F))
		.uv(8, 14).cuboid(-1.0F, -9.0F, 3.0F, 2.0F, 2.0F, 0.5F, new Dilation(0.0F))
		.uv(14, 14).cuboid(-0.75F, -8.75F, 3.5F, 1.5F, 1.5F, 0.5F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-0.5F, -8.5F, 4.0F, 1.0F, 1.0F, 0.5F, new Dilation(0.0F))
		.uv(0, 2).cuboid(-0.25F, -8.25F, 4.5F, 0.5F, 0.5F, 0.5F, new Dilation(0.0F))
		.uv(6, 8).cuboid(-2.25F, -9.5F, -1.5F, 0.25F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 8).cuboid(2.0F, -9.5F, -1.5F, 0.25F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 8).cuboid(-1.5F, -6.0F, -1.5F, 3.0F, 0.25F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(TransmuteBoltEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		mainbody.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}