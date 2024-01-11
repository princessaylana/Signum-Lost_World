// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
// lana 2024
package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.client.animation.FallenAnimations;
import za.lana.signum.entity.hostile.FallenEntity;


public class FallenEntityModel<T extends FallenEntity> extends SinglePartEntityModel<T> implements ModelWithArms {
	private final ModelPart fallen;
	private final ModelPart head;
	private final ModelPart rightArm;
	private final ModelPart leftArm;

	public FallenEntityModel(ModelPart root) {
		this.fallen = root.getChild("mainBody");
		this.rightArm = this.fallen.getChild("body").getChild("rightArm");
		this.leftArm = this.fallen.getChild("body").getChild("leftArm");
		this.head = root.getChild("mainBody").getChild("body").getChild("head");

	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mainBody = modelPartData.addChild("mainBody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = mainBody.addChild("body", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -24.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 7.0F, 8.0F, new Dilation(0.0F))
				.uv(21, 43).cuboid(-3.0F, -10.0F, -3.0F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F))
				.uv(0, 35).cuboid(-4.0F, -2.0F, 0.0F, 8.0F, 2.0F, 4.0F, new Dilation(0.0F))
				.uv(40, 39).cuboid(-1.0F, -5.0F, -5.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
				.uv(35, 7).cuboid(3.0F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F))
				.uv(35, 1).cuboid(-4.0F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData jaw = head.addChild("jaw", ModelPartBuilder.create().uv(0, 42).cuboid(-3.0F, -1.0F, -4.0F, 6.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

		ModelPartData tooth_r1 = jaw.addChild("tooth_r1", ModelPartBuilder.create().uv(0, 16).cuboid(-2.25F, -21.9038F, 14.3419F, 1.0F, 2.25F, 1.0F, new Dilation(0.0F))
				.uv(23, 16).cuboid(1.25F, -21.9038F, 14.3419F, 1.0F, 2.25F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 25.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData horns = head.addChild("horns", ModelPartBuilder.create().uv(29, 30).cuboid(1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
				.uv(39, 30).cuboid(-3.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -10.0F, -5.0F));

		ModelPartData ears = head.addChild("ears", ModelPartBuilder.create().uv(26, 0).cuboid(-5.0F, -4.0F, -1.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(4.0F, -4.0F, -1.0F, 1.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -6.0F, 0.0F));

		ModelPartData leftLeg = body.addChild("leftLeg", ModelPartBuilder.create().uv(48, 36).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 12.0F, 0.0F));

		ModelPartData rightLeg = body.addChild("rightLeg", ModelPartBuilder.create().uv(30, 13).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));

		ModelPartData rightArm = body.addChild("rightArm", ModelPartBuilder.create().uv(48, 18).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

		ModelPartData rightItem = rightArm.addChild("rightItem", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 7.0F, 1.0F));

		ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(48, 0).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

		ModelPartData leftItem = leftArm.addChild("leftItem", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 7.0F, 1.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(FallenEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		//arms
		this.rightArm.pitch = MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 2.0f * limbSwingAmount * 0.5f;
		this.rightArm.yaw = 0.0f;
		this.rightArm.roll = 0.0f;
		this.leftArm.pitch = MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 2.0f * limbSwingAmount * 0.5f;
		this.leftArm.yaw = 0.0f;
		this.leftArm.roll = 0.0f;

		this.animateMovement(FallenAnimations.FALLEN_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAniState, FallenAnimations.FALLEN_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.attackAniState, FallenAnimations.FALLEN_ATTACK, ageInTicks, 1f);
	}

	public void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}

	private ModelPart getAttackingArm(Arm arm) {
		if (arm == Arm.LEFT) {
			return this.leftArm;
		}
		return this.rightArm;
	}

	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		float f = arm == Arm.RIGHT ? 1.0f : -1.0f;
		ModelPart modelPart = this.getAttackingArm(arm);
		modelPart.pivotX += f;
		modelPart.rotate(matrices);
		modelPart.pivotX -= f;
	}

	@Override
	public ModelPart getPart() {
		return fallen;
	}
}