package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.client.animation.PidgeonAnimations;
import za.lana.signum.entity.mob.PidgeonEntity;

public class PidgeonModel<T extends PidgeonEntity> extends SinglePartEntityModel<T> {
	private final ModelPart pidgeon;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart leftWing;
	private final ModelPart rightWing;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public PidgeonModel(ModelPart root) {
		this.pidgeon = root.getChild("mainBody");
		this.head = pidgeon.getChild("head");
		this.body = pidgeon.getChild("body");

		this.tail = pidgeon.getChild("tail");
		this.leftWing = pidgeon.getChild("left_wing");
		this.rightWing = pidgeon.getChild("right_wing");
		this.leftLeg = pidgeon.getChild("left_leg");
		this.rightLeg = pidgeon.getChild("right_leg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mainBody = modelPartData.addChild("mainBody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData head = mainBody.addChild("head", ModelPartBuilder.create().uv(13, 0).cuboid(-1.25F, -2.0F, -1.5F, 2.5F, 3.5F, 2.25F, new Dilation(0.0F))
				.uv(0, 19).cuboid(-0.75F, -1.0F, -2.0F, 1.5F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(6, 9).cuboid(-0.5F, -0.75F, -2.45F, 1.0F, 1.0F, 1.0F, new Dilation(-0.01F))
				.uv(6, 9).cuboid(-0.2F, -0.55F, -2.7F, 0.5F, 0.5F, 0.25F, new Dilation(-0.01F)), ModelTransform.pivot(0.0F, -8.0F, -0.5F));

		ModelPartData body = mainBody.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, 0.0F, -1.25F, 3.0F, 5.25F, 2.75F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.5F, -1.0F));

		ModelPartData left_wing = mainBody.addChild("left_wing", ModelPartBuilder.create().uv(9, 9).mirrored().cuboid(-0.5F, 0.0F, -1.5F, 0.75F, 6.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(1.5F, -7.1F, -0.8F));

		ModelPartData right_wing = mainBody.addChild("right_wing", ModelPartBuilder.create().uv(0, 9).cuboid(-0.25F, 0.0F, -1.5F, 0.75F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, -7.1F, -0.8F));

		ModelPartData left_leg = mainBody.addChild("left_leg", ModelPartBuilder.create().uv(17, 18).cuboid(-2.25F, -1.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -2.0F, -1.0F));

		ModelPartData right_leg = mainBody.addChild("right_leg", ModelPartBuilder.create().uv(18, 13).cuboid(1.25F, -1.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -2.0F, -1.0F));

		ModelPartData tail = mainBody.addChild("tail", ModelPartBuilder.create().uv(18, 7).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 3.75F, 0.75F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.9F, 1.2F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(PidgeonEntity pidgeon, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);
		this.updateAnimation(pidgeon.sleepAniState, PidgeonAnimations.PIDGEON_STAND, ageInTicks, 1f);
		if (pidgeon.isOnGround()){
			this.updateAnimation(pidgeon.idleAniState , PidgeonAnimations.PIDGEON_IDLE_GROUND, ageInTicks, 1f);
			this.updateAnimation(pidgeon.sleepAniState , PidgeonAnimations.PIDGEON_IDLE_GROUND, ageInTicks, 1f);
			this.animateMovement(PidgeonAnimations.PIDGEON_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		}else {
			this.animateMovement(PidgeonAnimations.PIDGEON_FLY, limbSwing, limbSwingAmount, 2f, 2.5f);
		}
		float sleepAnimation = pidgeon.getSleepAnimation(ageInTicks);
		if (sleepAnimation <= 0.0f) {
			this.head.pitch = 0.0f;
			this.head.roll = 0.0f;
		}
		super.animateModel((T) pidgeon, limbSwing, limbSwingAmount, ageInTicks);



	}

	public void setHeadAngles(float headYaw, float headPitch){
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

			this.head.pitch = headPitch * ((float)Math.PI / 180);
			this.head.yaw = headYaw * ((float)Math.PI / 180);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		pidgeon.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return pidgeon;
	}

	@Override
	public void animateModel(T PidgeonEntity, float f, float g, float h) {


	}


}
