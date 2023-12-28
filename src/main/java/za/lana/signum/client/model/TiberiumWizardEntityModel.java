// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.client.animation.WizardAnimations;
import za.lana.signum.entity.hostile.TiberiumWizardEntity;

public class TiberiumWizardEntityModel<T extends TiberiumWizardEntity> extends SinglePartEntityModel<T> implements ModelWithArms{
	private final ModelPart mainBody;
	private final ModelPart head;
	private final ModelPart leftArm;
	private final ModelPart rightArm;

	public TiberiumWizardEntityModel(ModelPart root) {
		this.mainBody = root.getChild("mainBody");
		this.head = this.mainBody.getChild("body").getChild("head");
		this.rightArm = this.mainBody.getChild("body").getChild("rightArm");
		this.leftArm = this.mainBody.getChild("body").getChild("leftArm");

	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mainBody = modelPartData.addChild("mainBody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

		ModelPartData body = mainBody.addChild("body", ModelPartBuilder.create().uv(0, 45).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(29, 37).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F))
				.uv(24, 0).cuboid(-1.0F, -5.0F, -5.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F))
				.uv(62, 51).cuboid(-3.0F, -2.5F, -5.1F, 6.0F, 7.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 29).cuboid(-5.0F, -8.0F, -2.0F, 10.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData hat = head.addChild("hat", ModelPartBuilder.create().uv(21, 16).cuboid(-5.0F, -34.05F, -5.0F, 10.0F, 2.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData hat2 = hat.addChild("hat2", ModelPartBuilder.create().uv(31, 0).cuboid(-5.0F, -5.5F, -5.0F, 7.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(1.75F, -32.0F, 2.0F, -0.0524F, 0.0F, 0.0262F));

		ModelPartData hat3 = hat2.addChild("hat3", ModelPartBuilder.create().uv(62, 42).cuboid(-3.25F, -5.5F, -3.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 0.0F, -0.1047F, 0.0F, 0.0524F));

		ModelPartData hat4 = hat3.addChild("hat4", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -4.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.25F)), ModelTransform.of(0.0F, -3.0F, 0.0F, -0.2094F, 0.0F, 0.1047F));

		ModelPartData chestArmor = body.addChild("chestArmor", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -24.0F, -3.0F, 8.0F, 18.0F, 7.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData rightArm = body.addChild("rightArm", ModelPartBuilder.create().uv(29, 56).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

		ModelPartData rightItem = rightArm.addChild("rightItem", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 7.0F, 1.0F));

		ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(56, 8).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

		ModelPartData leftItem = leftArm.addChild("leftItem", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 7.0F, 1.0F));

		ModelPartData rightLeg = body.addChild("rightLeg", ModelPartBuilder.create().uv(46, 56).cuboid(-2.1F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

		ModelPartData leftLeg = body.addChild("leftLeg", ModelPartBuilder.create().uv(58, 25).cuboid(-1.9F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.9F, 12.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(TiberiumWizardEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);
		this.rightArm.pitch = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 2.0f * limbSwingAmount * 0.5f;
		this.rightArm.yaw = 0.0f;
		this.rightArm.roll = 0.0f;
		this.animateMovement(WizardAnimations.WIZARD_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAniState, WizardAnimations.WIZARD_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.attackAniState, WizardAnimations.WIZARD_MELEE, ageInTicks, 1f);
		this.updateAnimation(entity.spellAniState, WizardAnimations.WIZARD_MAGIC, ageInTicks, 1f);
		this.updateAnimation(entity.sleepAniState, WizardAnimations.WIZARD_SLEEP, ageInTicks, 1f);


	}

	public void setHeadAngles(float headYaw, float headPitch){
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		mainBody.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.mainBody;
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

}