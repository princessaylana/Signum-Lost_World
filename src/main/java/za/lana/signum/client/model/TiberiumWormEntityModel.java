// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.client.animation.TiberiumWormAnimations;
import za.lana.signum.entity.hostile.TiberiumWormEntity;


public class TiberiumWormEntityModel<T extends TiberiumWormEntity> extends SinglePartEntityModel<T> {
	private final ModelPart worm;
	private final ModelPart head;

	public TiberiumWormEntityModel(ModelPart root) {
		super(RenderLayer::getEntityTranslucent);
		this.worm = root.getChild("mainBody");
		this.head = worm.getChild("head");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mainBody = modelPartData.addChild("mainBody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData head = mainBody.addChild("head", ModelPartBuilder.create().uv(7, 11).cuboid(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(5, 15).cuboid(0.5F, -1.25F, -0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 15).cuboid(-1.5F, -1.25F, -0.25F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 4.0F));

		ModelPartData mouth_r1 = head.addChild("mouth_r1", ModelPartBuilder.create().uv(13, 4).cuboid(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -0.25F, 1.5708F, -0.7854F, -1.5708F));

		ModelPartData horn_r1 = head.addChild("horn_r1", ModelPartBuilder.create().uv(0, 11).cuboid(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -0.25F, 0.0F, 0.0F, 0.7854F));

		ModelPartData stinger = mainBody.addChild("stinger", ModelPartBuilder.create().uv(7, 11).cuboid(-1.0F, -1.0F, -8.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 3.0F));

		ModelPartData stinger2_r1 = stinger.addChild("stinger2_r1", ModelPartBuilder.create().uv(13, 4).cuboid(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -8.25F, 1.5708F, -0.7854F, -1.5708F));

		ModelPartData stinger1_r1 = stinger.addChild("stinger1_r1", ModelPartBuilder.create().uv(13, 16).cuboid(0.0F, -2.25F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -0.5F, -8.75F, 0.7854F, 0.0F, 0.0F));

		ModelPartData body = mainBody.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -7.5F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 3.0F));

		ModelPartData sides_r1 = body.addChild("sides_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 4).cuboid(-1.0F, -1.0F, 1.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(13, 0).cuboid(-1.0F, -1.0F, 5.25F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(14, 11).cuboid(-1.0F, -1.0F, 3.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -6.75F, 0.0F, 0.0F, 0.7854F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(TiberiumWormEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(TiberiumWormAnimations.TIBERIUM_WORM_MOVE, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAniState, TiberiumWormAnimations.TIBERIUM_WORM_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.attackAniState, TiberiumWormAnimations.TIBERIUM_WORM_ATTACK, ageInTicks, 1f);
	}
	public void setHeadAngles(float headYaw, float headPitch){
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		worm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return worm;
	}
}
