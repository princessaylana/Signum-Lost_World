// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.client.animation.CursedWolfAnimations;
import za.lana.signum.entity.mob.CursedWolfEntity;

public class CursedWolfEntityModel<T extends CursedWolfEntity> extends SinglePartEntityModel<T> {
	private final ModelPart cwolf;
	private final ModelPart head;

	public CursedWolfEntityModel(ModelPart root) {
		this.cwolf = root.getChild("mainBody");
		this.head = cwolf.getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mainBody = modelPartData.addChild("mainBody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData head = mainBody.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 10).cuboid(-0.5F, -0.02F, -5.0F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -10.5F, -7.0F));

		ModelPartData earR = head.addChild("earR", ModelPartBuilder.create().uv(16, 14).cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -3.0F, 0.5F));

		ModelPartData earL = head.addChild("earL", ModelPartBuilder.create().uv(16, 14).cuboid(-1.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -3.0F, 0.5F));

		ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create().uv(38, 15).cuboid(-0.5F, -0.52F, -3.0F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.5F, -2.0F));

		ModelPartData body = mainBody.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -10.0F, 2.0F));

		ModelPartData body_rotation = body.addChild("body_rotation", ModelPartBuilder.create().uv(18, 14).cuboid(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		ModelPartData scruff = mainBody.addChild("scruff", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, -10.0F, 2.0F));

		ModelPartData mane_rotation = scruff.addChild("mane_rotation", ModelPartBuilder.create().uv(21, 0).cuboid(-4.0F, -5.5F, -0.5F, 8.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 2.5F, -2.5F, 1.5708F, 0.0F, 0.0F));

		ModelPartData leg1 = mainBody.addChild("leg1", ModelPartBuilder.create().uv(0, 18).cuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -8.0F, 7.0F));

		ModelPartData leg2 = mainBody.addChild("leg2", ModelPartBuilder.create().uv(0, 18).cuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, -8.0F, 7.0F));

		ModelPartData leg3 = mainBody.addChild("leg3", ModelPartBuilder.create().uv(0, 18).cuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -8.0F, -4.0F));

		ModelPartData leg4 = mainBody.addChild("leg4", ModelPartBuilder.create().uv(0, 18).cuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, -8.0F, -4.0F));

		ModelPartData tail = mainBody.addChild("tail", ModelPartBuilder.create().uv(9, 18).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.0F, 10.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void setAngles(CursedWolfEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(CursedWolfAnimations.WOLF_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAniState, CursedWolfAnimations.WOLF_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.attackAniState, CursedWolfAnimations.WOLF_ATTACK, ageInTicks, 1f);
	}
	public void setHeadAngles(float headYaw, float headPitch){
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		cwolf.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return cwolf;
	}


}
