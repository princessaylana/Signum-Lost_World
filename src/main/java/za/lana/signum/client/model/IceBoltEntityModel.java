// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import za.lana.signum.client.animation.BoltAnimations;
import za.lana.signum.entity.projectile.GravityBoltEntity;
import za.lana.signum.entity.projectile.IceBoltEntity;

public class IceBoltEntityModel extends SinglePartEntityModel<IceBoltEntity> {

	private final ModelPart bolt;
	public IceBoltEntityModel(ModelPart root) {
		this.bolt = root.getChild("mainBody");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mainBody = modelPartData.addChild("mainBody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 15.5F, -0.5F));

		ModelPartData center = mainBody.addChild("center", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = center.addChild("cube_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		ModelPartData cube_r2 = center.addChild("cube_r2", ModelPartBuilder.create().uv(0, 11).cuboid(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		ModelPartData cube_r3 = center.addChild("cube_r3", ModelPartBuilder.create().uv(16, 17).cuboid(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(IceBoltEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(BoltAnimations.BOLT_MOVE, limbSwing, limbSwingAmount, 2f, 2.5f);
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		bolt.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.bolt;
	}
}