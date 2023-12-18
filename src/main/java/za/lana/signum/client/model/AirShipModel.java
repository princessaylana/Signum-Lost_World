// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.client.animation.AirShipAnimations;
import za.lana.signum.entity.transport.AirShipEntity;

public class AirShipModel<T extends AirShipEntity> extends SinglePartEntityModel<T> {
	private final ModelPart airship;
	//private final ModelPart head;

	public AirShipModel(ModelPart root) {
		this.airship = root.getChild("mainBody");
		//this.head = airship.getChild("body").getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mainBody = modelPartData.addChild("mainBody", ModelPartBuilder.create(), ModelTransform.pivot(-0.5F, 22.0F, 10.0F));

		ModelPartData body = mainBody.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData balloon = body.addChild("balloon", ModelPartBuilder.create().uv(28, 28).cuboid(-30.0F, -47.0F, -50.0F, 60.0F, 8.0F, 104.0F, new Dilation(0.0F))
				.uv(12, 12).cuboid(-38.0F, -55.0F, -54.0F, 76.0F, 8.0F, 120.0F, new Dilation(0.0F))
				.uv(50, 170).cuboid(-22.0F, -39.0F, -34.0F, 44.0F, 8.0F, 79.0F, new Dilation(0.0F))
				.uv(28, 28).cuboid(-30.0F, -87.0F, -50.0F, 60.0F, 8.0F, 104.0F, new Dilation(0.0F))
				.uv(12, 12).cuboid(-38.0F, -79.0F, -54.0F, 76.0F, 8.0F, 120.0F, new Dilation(0.0F))
				.uv(50, 170).cuboid(-22.0F, -95.0F, -34.0F, 44.0F, 8.0F, 79.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-45.25F, -71.0F, -58.0F, 91.0F, 16.0F, 132.0F, new Dilation(0.0F))
				.uv(269, 72).cuboid(-18.0F, -31.0F, -10.0F, 36.0F, 8.0F, 36.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -26.0F, -8.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -10.0F, -8.0F));

		ModelPartData seat = head.addChild("seat", ModelPartBuilder.create().uv(0, 110).cuboid(-7.0F, -2.0F, -9.0F, 16.0F, 1.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 11.0F, 0.0F));

		ModelPartData mount = seat.addChild("mount", ModelPartBuilder.create(), ModelTransform.pivot(0.5F, -2.5F, -1.25F));

		ModelPartData p_passenger = seat.addChild("p_passenger", ModelPartBuilder.create(), ModelTransform.pivot(0.5F, -2.5F, 14.75F));

		ModelPartData barrel = head.addChild("barrel", ModelPartBuilder.create().uv(45, 0).cuboid(-7.0F, -17.0F, 7.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 11.0F, 0.0F));

		ModelPartData motor = head.addChild("motor", ModelPartBuilder.create().uv(159, 306).cuboid(5.5F, -38.0F, -35.0F, 3.0F, 4.0F, 67.0F, new Dilation(0.0F))
				.uv(0, 306).cuboid(-7.5F, -38.0F, -35.0F, 3.0F, 4.0F, 67.0F, new Dilation(0.0F))
				.uv(74, 306).cuboid(-17.0F, -24.0F, -18.0F, 12.0F, 1.0F, 36.0F, new Dilation(0.0F))
				.uv(100, 332).cuboid(-5.0F, -24.0F, -18.0F, 12.0F, 1.0F, 10.0F, new Dilation(0.0F))
				.uv(74, 306).cuboid(7.0F, -24.0F, -18.0F, 12.0F, 1.0F, 36.0F, new Dilation(0.0F))
				.uv(31, 149).cuboid(15.0F, -40.0F, -19.0F, 5.0F, 18.0F, 9.0F, new Dilation(0.0F))
				.uv(40, 39).cuboid(-19.0F, -40.0F, -19.0F, 5.0F, 18.0F, 9.0F, new Dilation(0.0F))
				.uv(0, 39).cuboid(-19.0F, -40.0F, 10.0F, 5.0F, 18.0F, 9.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(15.0F, -40.0F, 10.0F, 5.0F, 18.0F, 9.0F, new Dilation(0.0F))
				.uv(80, 33).cuboid(-7.0F, -29.0F, -5.0F, 15.0F, 8.0F, 9.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-1.0F, -26.0F, -19.0F, 4.0F, 2.0F, 36.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 8.0F));

		ModelPartData engineframe = head.addChild("engineframe", ModelPartBuilder.create().uv(52, 95).cuboid(-9.5F, -36.0F, -32.0F, 18.0F, 8.0F, 15.0F, new Dilation(0.0F))
				.uv(0, 80).cuboid(-8.5F, -34.0F, 18.0F, 16.0F, 12.0F, 17.0F, new Dilation(0.0F))
				.uv(245, 277).cuboid(-34.5F, -29.0F, -36.0F, 68.0F, 4.0F, 6.0F, new Dilation(0.0F))
				.uv(245, 266).cuboid(-34.5F, -29.0F, 31.0F, 68.0F, 4.0F, 6.0F, new Dilation(0.0F))
				.uv(60, 39).cuboid(17.5F, -28.0F, 37.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F))
				.uv(20, 39).cuboid(-20.5F, -28.0F, 37.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F))
				.uv(20, 0).cuboid(-24.5F, -28.0F, -30.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 28).cuboid(21.5F, -28.0F, -30.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -1.0F, 7.0F));

		ModelPartData frame = head.addChild("frame", ModelPartBuilder.create().uv(80, 56).cuboid(-7.0F, -3.0F, 23.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(40, 42).cuboid(9.0F, -3.0F, -10.0F, 2.0F, 2.0F, 35.0F, new Dilation(0.0F))
				.uv(80, 51).cuboid(-7.0F, -3.0F, -11.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 39).cuboid(-9.0F, -3.0F, -10.0F, 2.0F, 2.0F, 35.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

		ModelPartData frame3 = frame.addChild("frame3", ModelPartBuilder.create().uv(45, 0).cuboid(8.0F, -5.0F, 1.0F, 1.0F, 7.0F, 4.0F, new Dilation(0.0F))
				.uv(13, 306).cuboid(8.0F, -31.0F, 29.0F, 1.0F, 33.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 181).cuboid(8.0F, -3.0F, 5.0F, 1.0F, 3.0F, 28.0F, new Dilation(0.0F))
				.uv(104, 92).cuboid(7.0F, -4.0F, 26.0F, 3.0F, 6.0F, 8.0F, new Dilation(0.0F))
				.uv(103, 61).cuboid(7.0F, -4.0F, 14.0F, 3.0F, 6.0F, 8.0F, new Dilation(0.0F))
				.uv(18, 31).cuboid(10.0F, -2.0F, 17.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(29, 17).cuboid(10.0F, -2.0F, 29.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(29, 12).cuboid(9.0F, -2.0F, 2.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(1, 306).cuboid(8.0F, -31.0F, 16.0F, 1.0F, 33.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -5.0F, -9.0F));

		ModelPartData frame2 = frame.addChild("frame2", ModelPartBuilder.create().uv(25, 24).cuboid(-9.0F, -5.0F, 1.0F, 1.0F, 7.0F, 4.0F, new Dilation(0.0F))
				.uv(25, 306).cuboid(-9.0F, -31.0F, 29.0F, 1.0F, 33.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 149).cuboid(-9.0F, -3.0F, 5.0F, 1.0F, 3.0F, 28.0F, new Dilation(0.0F))
				.uv(94, 0).cuboid(-10.0F, -4.0F, 26.0F, 3.0F, 6.0F, 8.0F, new Dilation(0.0F))
				.uv(80, 61).cuboid(-10.0F, -4.0F, 14.0F, 3.0F, 6.0F, 8.0F, new Dilation(0.0F))
				.uv(29, 7).cuboid(-11.0F, -2.0F, 17.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(13, 28).cuboid(-11.0F, -2.0F, 29.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-10.0F, -2.0F, 2.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(38, 306).cuboid(-9.0F, -31.0F, 16.0F, 1.0F, 33.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, -9.0F));

		ModelPartData propellors = head.addChild("propellors", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData prop1 = propellors.addChild("prop1", ModelPartBuilder.create().uv(50, 86).cuboid(-17.0F, -2.0F, -0.5F, 34.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(19.5F, -28.0F, 45.5F));

		ModelPartData prop2 = propellors.addChild("prop2", ModelPartBuilder.create().uv(50, 80).cuboid(-17.0F, -2.0F, -0.5F, 34.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-18.5F, -28.0F, 45.5F));

		ModelPartData prop3 = propellors.addChild("prop3", ModelPartBuilder.create().uv(65, 125).cuboid(-13.0F, -2.0F, -0.5F, 26.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(23.5F, -28.0F, -21.5F));

		ModelPartData prop4 = propellors.addChild("prop4", ModelPartBuilder.create().uv(49, 119).cuboid(-13.0F, -2.0F, -1.0F, 26.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-22.5F, -28.0F, -21.0F));
		return TexturedModelData.of(modelData, 512, 512);
	}

	@Override
	public void setAngles(AirShipEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		//
		//this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(AirShipAnimations.AIRSHIP_FLY, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAniState, AirShipAnimations.AIRSHIP_IDLE, ageInTicks, 1f);
	}

	public void setHeadAngles(float headYaw, float headPitch){
		//headYaw = this.airship.yaw;
		//headPitch = this.airship.pitch;

		//this.head.yaw = headYaw;
		//this.head.pitch = headPitch;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		airship.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return airship;
	}


}
