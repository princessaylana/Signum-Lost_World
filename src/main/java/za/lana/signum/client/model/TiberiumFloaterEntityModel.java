// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.client.animation.TiberiumFloaterEntityAnimations;
import za.lana.signum.entity.hostile.TiberiumFloaterEntity;


public class TiberiumFloaterEntityModel<T extends TiberiumFloaterEntity> extends SinglePartEntityModel<T>{
	private final ModelPart floater;
	private final ModelPart head;

	public TiberiumFloaterEntityModel(ModelPart root) {
		this.floater = root.getChild("main");
		this.head = this.floater.getChild("body").getChild("eye");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -6.0F, -3.0F));

		ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(69, 0).cuboid(-14.0F, -20.0F, -10.0F, 28.0F, 16.0F, 24.0F, new Dilation(0.0F))
		.uv(35, 0).cuboid(-12.0F, -18.0F, -12.0F, 24.0F, 12.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 96).cuboid(-12.0F, -22.0F, -14.0F, 24.0F, 2.0F, 26.0F, new Dilation(0.0F))
		.uv(43, 67).cuboid(-12.0F, -4.0F, -14.0F, 24.0F, 2.0F, 26.0F, new Dilation(0.0F))
		.uv(75, 96).cuboid(-10.0F, -24.0F, -12.0F, 20.0F, 2.0F, 22.0F, new Dilation(0.0F))
		.uv(69, 41).cuboid(-10.0F, -2.0F, -12.0F, 20.0F, 2.0F, 22.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(0.0F, -32.0F, -9.5F, 0.0F, 58.0F, 34.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 3.0F));

		ModelPartData eye = body.addChild("eye", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -6.0F, -5.0F, 12.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, -11.0F));

		ModelPartData tentacles = body.addChild("tentacles", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.0F, 0.0F));

		ModelPartData r1 = tentacles.addChild("r1", ModelPartBuilder.create().uv(0, 104).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(6.5F, 1.0F, -8.5F));

		ModelPartData bone = r1.addChild("bone", ModelPartBuilder.create().uv(118, 78).cuboid(-2.5F, -3.0F, -2.5F, 5.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.5F, 0.0F));

		ModelPartData bone2 = bone.addChild("bone2", ModelPartBuilder.create().uv(17, 125).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));

		ModelPartData bone3 = bone2.addChild("bone3", ModelPartBuilder.create().uv(47, 125).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 6.0F, 0.0F));

		ModelPartData bone4 = bone3.addChild("bone4", ModelPartBuilder.create().uv(69, 125).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, 0.0F));

		ModelPartData l1 = tentacles.addChild("l1", ModelPartBuilder.create().uv(0, 93).cuboid(-3.0F, -3.5F, -12.0F, 6.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.5F, 1.0F, 0.5F));

		ModelPartData bone9 = l1.addChild("bone9", ModelPartBuilder.create().uv(118, 66).cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.5F, -9.0F));

		ModelPartData bone10 = bone9.addChild("bone10", ModelPartBuilder.create().uv(0, 125).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 6.0F, 0.0F));

		ModelPartData bone11 = bone10.addChild("bone11", ModelPartBuilder.create().uv(34, 125).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 6.0F, 0.0F));

		ModelPartData bone12 = bone11.addChild("bone12", ModelPartBuilder.create().uv(60, 125).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, 0.0F));

		ModelPartData r2 = tentacles.addChild("r2", ModelPartBuilder.create().uv(35, 15).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(6.5F, 1.0F, 6.5F));

		ModelPartData bone5 = r2.addChild("bone5", ModelPartBuilder.create().uv(75, 96).cuboid(-2.5F, -3.0F, -2.5F, 5.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.5F, 0.0F));

		ModelPartData bone6 = bone5.addChild("bone6", ModelPartBuilder.create().uv(114, 121).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));

		ModelPartData bone7 = bone6.addChild("bone7", ModelPartBuilder.create().uv(75, 108).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 6.0F, 0.0F));

		ModelPartData bone8 = bone7.addChild("bone8", ModelPartBuilder.create().uv(60, 15).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, 0.0F));

		ModelPartData l2 = tentacles.addChild("l2", ModelPartBuilder.create().uv(0, 17).cuboid(-3.0F, -3.5F, -12.0F, 6.0F, 4.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.5F, 1.0F, 15.5F));

		ModelPartData bone13 = l2.addChild("bone13", ModelPartBuilder.create().uv(69, 41).cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.5F, -9.0F));

		ModelPartData bone14 = bone13.addChild("bone14", ModelPartBuilder.create().uv(97, 121).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 6.0F, 0.0F));

		ModelPartData bone15 = bone14.addChild("bone15", ModelPartBuilder.create().uv(69, 53).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 6.0F, 0.0F));

		ModelPartData bone16 = bone15.addChild("bone16", ModelPartBuilder.create().uv(25, 17).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, 0.0F));
		return TexturedModelData.of(modelData, 256, 256);
	}
	@Override
	public void setAngles(TiberiumFloaterEntity floater, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(TiberiumFloaterEntityAnimations.TFLOATER_FLY, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(floater.idleAniState, TiberiumFloaterEntityAnimations.TFLOATER_IDLE, ageInTicks, 1f);
		this.updateAnimation(floater.attackAniState, TiberiumFloaterEntityAnimations.TFLOATER_MELEE_ATTACK, ageInTicks, 1f);
		this.updateAnimation(floater.spitAniState, TiberiumFloaterEntityAnimations.TFLOATER_RANGE_ATTACK, ageInTicks, 1f);

	}
	public void setHeadAngles(float headYaw, float headPitch){
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -30.0f, 30.0f);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		this.floater.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return this.floater;
	}
}
