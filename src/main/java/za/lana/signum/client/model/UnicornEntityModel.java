// Made with Blockbench 4.8.3
package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.client.animation.UnicornAnimations;
import za.lana.signum.entity.mob.UnicornEntity;

public class UnicornEntityModel<T extends UnicornEntity> extends SinglePartEntityModel<T> {
	private final ModelPart unicorn;
	private final ModelPart head;

	public UnicornEntityModel(ModelPart root) {
		this.unicorn = root.getChild("mainbody");
		this.head = unicorn.getChild("neck").getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mainbody = modelPartData.addChild("mainbody", ModelPartBuilder.create().uv(0, 32).cuboid(-5.0F, -8.0F, -20.0F, 10.0F, 10.0F, 22.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 11.0F, 9.0F));

		ModelPartData tail = mainbody.addChild("tail", ModelPartBuilder.create().uv(42, 36).cuboid(-1.5F, 0.0F, -2.0F, 3.0F, 14.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.0F, 2.0F, 0.4363F, 0.0F, 0.0F));

		ModelPartData legbl = mainbody.addChild("legbl", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.0F, 2.0F, 0.0F));

		ModelPartData legbr = mainbody.addChild("legbr", ModelPartBuilder.create().uv(48, 21).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 2.0F, 0.0F));

		ModelPartData legfl = mainbody.addChild("legfl", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.0F, 2.0F, -18.0F));

		ModelPartData legfr = mainbody.addChild("legfr", ModelPartBuilder.create().uv(48, 21).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 2.0F, -18.0F));

		ModelPartData neck = mainbody.addChild("neck", ModelPartBuilder.create().uv(0, 35).cuboid(-2.0F, -11.0F, -3.0F, 4.0F, 12.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, -17.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(0, 13).cuboid(-3.0F, -5.0F, 0.0F, 6.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.0F, -3.0F));

		ModelPartData muzzle = head.addChild("muzzle", ModelPartBuilder.create().uv(0, 25).cuboid(-2.0F, -5.0F, -5.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData earl = head.addChild("earl", ModelPartBuilder.create().uv(19, 16).mirrored().cuboid(-0.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 11.0F, 3.0F, 0.0F, 0.0F, 0.0873F));

		ModelPartData earr = head.addChild("earr", ModelPartBuilder.create().uv(19, 16).cuboid(-1.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.0F, 3.0F, 0.0F, 0.0F, -0.0873F));

		ModelPartData horn = head.addChild("horn", ModelPartBuilder.create().uv(5, 40).cuboid(-1.0F, -7.0F, -3.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(5, 40).cuboid(-0.75F, -9.0F, -2.75F, 1.5F, 2.0F, 1.5F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 6.5F));

		ModelPartData mane = neck.addChild("mane", ModelPartBuilder.create().uv(56, 36).cuboid(-1.0F, -16.0F, 4.05F, 2.0F, 16.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(0.0F, -16.0F, 6.0F, 0.0F, 16.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(UnicornEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(UnicornAnimations.UNICORN_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAniState, UnicornAnimations.UNICORN_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.attackAniState, UnicornAnimations.UNICORN_ATTACK, ageInTicks, 1f);
	}
	public void setHeadAngles(float headYaw, float headPitch){
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}


	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		unicorn.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return unicorn;
	}
}
