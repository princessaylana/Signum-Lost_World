// Made with Blockbench 4.8.3
package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.CamelAnimations;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.client.animation.AirBalloonAnimations;
import za.lana.signum.client.animation.UnicornAnimations;
import za.lana.signum.entity.mob.UnicornEntity;
import za.lana.signum.entity.transport.AirBalloonEntity;

public class AirBalloonModel<T extends AirBalloonEntity> extends SinglePartEntityModel<T> {
	private final ModelPart airballoon;
	private final ModelPart mount;
	private final ModelPart p_passenger;


	public AirBalloonModel(ModelPart root) {
		this.airballoon = root.getChild("MainBody");
		this.mount = this.airballoon.getChild("seating").getChild("mount");
		this.p_passenger = this.airballoon.getChild("seating").getChild("p_passenger");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData MainBody = modelPartData.addChild("MainBody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 15.0F, 16.0F));

		ModelPartData panels = MainBody.addChild("panels", ModelPartBuilder.create().uv(0, 240).cuboid(-0.05F, -88.0F, 24.0F, 0.0F, 31.0F, 43.0F, new Dilation(0.0F))
				.uv(230, 199).mirrored().cuboid(-0.05F, -88.0F, -76.0F, 0.0F, 32.0F, 38.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData balloon = MainBody.addChild("balloon", ModelPartBuilder.create().uv(0, 154).cuboid(-16.0F, -48.5F, -24.0F, 32.0F, 8.0F, 32.0F, new Dilation(0.0F))
				.uv(129, 159).cuboid(-16.0F, -96.45F, -24.0F, 32.0F, 4.0F, 32.0F, new Dilation(0.0F))
				.uv(0, 97).cuboid(-24.0F, -56.5F, -32.0F, 48.0F, 8.0F, 48.0F, new Dilation(0.0F))
				.uv(145, 106).cuboid(-24.0F, -92.5F, -32.0F, 48.0F, 4.0F, 48.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-32.0F, -88.5F, -40.0F, 64.0F, 32.0F, 64.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData frameB = balloon.addChild("frameB", ModelPartBuilder.create().uv(145, 126).cuboid(-8.0F, -34.0F, -17.0F, 16.0F, 10.0F, 2.0F, new Dilation(0.0F))
				.uv(145, 97).cuboid(8.0F, -34.0F, -17.0F, 2.0F, 10.0F, 18.0F, new Dilation(0.0F))
				.uv(26, 0).cuboid(-8.0F, -34.0F, -1.0F, 16.0F, 10.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 97).cuboid(-10.0F, -34.0F, -17.0F, 2.0F, 10.0F, 18.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.0F, 0.0F));

		ModelPartData frame = MainBody.addChild("frame", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData frameL = frame.addChild("frameL", ModelPartBuilder.create().uv(0, 97).cuboid(8.0F, -4.0F, -25.35F, 1.75F, 5.0F, 3.35F, new Dilation(0.0F))
				.uv(11, 11).cuboid(8.0F, -4.0F, 5.9F, 1.75F, 5.0F, 3.35F, new Dilation(0.0F))
				.uv(14, 195).cuboid(8.25F, -26.0F, -9.0F, 1.25F, 25.75F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData meas_r1 = frameL.addChild("meas_r1", ModelPartBuilder.create().uv(186, 106).cuboid(-0.75F, -26.0F, -1.0F, 1.25F, 27.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -2.0F, -24.0F, -0.3491F, 0.0F, 0.0F));

		ModelPartData meas_r2 = frameL.addChild("meas_r2", ModelPartBuilder.create().uv(0, 195).cuboid(-0.75F, -26.0F, -1.0F, 1.25F, 27.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -2.0F, 8.0F, 0.3491F, 0.0F, 0.0F));

		ModelPartData prophandle = frameL.addChild("prophandle", ModelPartBuilder.create().uv(18, 0).cuboid(13.35F, -0.65F, 0.5F, 1.0F, 1.0F, 1.5F, new Dilation(0.0F))
				.uv(97, 180).cuboid(-0.65F, -0.9F, -0.5F, 16.0F, 1.5F, 1.5F, new Dilation(0.0F)), ModelTransform.of(24.4F, -51.35F, -8.5F, 0.0F, 0.0F, 0.7854F));

		ModelPartData propellor = prophandle.addChild("propellor", ModelPartBuilder.create().uv(168, 97).cuboid(-9.0F, -0.75F, -0.25F, 18.0F, 1.5F, 0.5F, new Dilation(0.0F)), ModelTransform.pivot(13.85F, -0.15F, 1.5F));

		ModelPartData frameR = frame.addChild("frameR", ModelPartBuilder.create().uv(48, 55).cuboid(-9.75F, -4.0F, -25.35F, 1.75F, 5.0F, 3.35F, new Dilation(0.0F))
				.uv(0, 11).cuboid(-9.75F, -4.0F, 5.9F, 1.75F, 5.0F, 3.35F, new Dilation(0.0F))
				.uv(7, 195).cuboid(-9.5F, -26.0F, -9.0F, 1.25F, 25.75F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData meas_r3 = frameR.addChild("meas_r3", ModelPartBuilder.create().uv(51, 25).cuboid(-0.5F, -26.0F, -1.0F, 1.25F, 27.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-9.0F, -2.0F, -24.0F, -0.3491F, 0.0F, 0.0F));

		ModelPartData meas_r4 = frameR.addChild("meas_r4", ModelPartBuilder.create().uv(41, 97).cuboid(-0.5F, -26.0F, -1.0F, 1.25F, 27.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-9.0F, -2.0F, 8.0F, 0.3491F, 0.0F, 0.0F));

		ModelPartData prophandle2 = frameR.addChild("prophandle2", ModelPartBuilder.create().uv(0, 0).cuboid(-14.35F, -0.65F, 0.5F, 1.0F, 1.0F, 1.5F, new Dilation(0.0F))
				.uv(168, 101).cuboid(-15.35F, -0.9F, -0.5F, 16.0F, 1.5F, 1.5F, new Dilation(0.0F)), ModelTransform.of(-24.4F, -51.35F, -8.5F, 0.0F, 0.0F, -0.7854F));

		ModelPartData propellor2 = prophandle2.addChild("propellor2", ModelPartBuilder.create().uv(145, 139).cuboid(-9.0F, -0.75F, -0.25F, 18.0F, 1.5F, 0.5F, new Dilation(0.0F)), ModelTransform.pivot(-13.85F, -0.15F, 1.5F));

		ModelPartData frameC = frame.addChild("frameC", ModelPartBuilder.create().uv(26, 13).cuboid(-8.0F, -27.0F, -17.0F, 16.0F, 3.0F, 2.0F, new Dilation(0.0F))
				.uv(65, 195).cuboid(8.0F, -27.0F, -17.0F, 3.0F, 3.0F, 18.0F, new Dilation(0.0F))
				.uv(0, 126).cuboid(-1.0F, -25.75F, -16.5F, 2.0F, 1.25F, 17.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-2.5F, -26.0F, -11.0F, 5.0F, 2.5F, 7.0F, new Dilation(0.0F))
				.uv(26, 19).cuboid(-8.0F, -27.0F, -1.0F, 16.0F, 3.0F, 2.0F, new Dilation(0.0F))
				.uv(193, 34).cuboid(-11.0F, -27.0F, -17.0F, 3.0F, 3.0F, 18.0F, new Dilation(0.0F))
				.uv(106, 154).cuboid(-9.5F, -40.5F, -14.5F, 1.0F, 16.0F, 2.5F, new Dilation(0.0F))
				.uv(97, 154).cuboid(-9.5F, -40.5F, -4.0F, 1.0F, 16.0F, 2.5F, new Dilation(0.0F))
				.uv(108, 195).cuboid(8.5F, -40.5F, -14.5F, 1.0F, 16.0F, 2.5F, new Dilation(0.0F))
				.uv(21, 195).cuboid(8.5F, -40.5F, -4.0F, 1.0F, 16.0F, 2.5F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData seating = MainBody.addChild("seating", ModelPartBuilder.create().uv(193, 56).cuboid(-8.0F, -3.0F, 8.0F, 16.0F, 3.0F, 1.0F, new Dilation(0.0F))
				.uv(57, 235).cuboid(-9.0F, -3.0F, -22.0F, 1.0F, 3.0F, 28.0F, new Dilation(0.0F))
				.uv(229, 69).cuboid(8.0F, -3.0F, -22.0F, 1.0F, 3.0F, 28.0F, new Dilation(0.0F))
				.uv(0, 58).cuboid(-8.0F, -3.0F, -25.0F, 17.0F, 3.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 195).cuboid(-8.0F, -1.0F, -24.0F, 16.0F, 1.0F, 32.0F, new Dilation(0.0F))
				.uv(193, 0).cuboid(-8.0F, 0.0F, -24.0F, 16.0F, 1.0F, 32.0F, new Dilation(0.0F))
				.uv(226, 159).cuboid(-4.0F, 3.0F, -22.0F, 8.0F, 1.0F, 28.0F, new Dilation(0.0F))
				.uv(97, 154).cuboid(-2.0F, 4.0F, -20.0F, 4.0F, 1.0F, 24.0F, new Dilation(0.0F))
				.uv(191, 196).cuboid(-4.0F, 1.0F, -23.0F, 8.0F, 2.0F, 30.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData mount = seating.addChild("mount", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.5F, -15.25F));

		ModelPartData p_passenger = seating.addChild("p_passenger", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -1.5F, 0.75F));

		ModelPartData landinggearL = MainBody.addChild("landinggearL", ModelPartBuilder.create().uv(168, 196).cuboid(4.0F, 1.0F, -14.0F, 4.0F, 3.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData frleg2 = landinggearL.addChild("frleg2", ModelPartBuilder.create().uv(193, 0).cuboid(-1.5F, -1.0F, -11.0F, 3.0F, 2.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, 2.0F, -12.0F));

		ModelPartData brleg2 = landinggearL.addChild("brleg2", ModelPartBuilder.create().uv(0, 168).cuboid(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, 2.0F, -4.0F));

		ModelPartData landinggearL2 = MainBody.addChild("landinggearL2", ModelPartBuilder.create().uv(121, 196).cuboid(-8.0F, 1.0F, -14.0F, 4.0F, 3.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData frleg3 = landinggearL2.addChild("frleg3", ModelPartBuilder.create().uv(130, 159).cuboid(-1.5F, -1.0F, -11.0F, 3.0F, 2.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.5F, 2.0F, -12.0F));

		ModelPartData brleg3 = landinggearL2.addChild("brleg3", ModelPartBuilder.create().uv(0, 154).cuboid(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.5F, 2.0F, -4.0F));
		return TexturedModelData.of(modelData, 512, 512);
	}

	@Override
	public void setAngles(AirBalloonEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		//this.setHeadAngles(netHeadYaw, headPitch);
		if (entity.isBurningFuel()) {
			this.animateMovement(AirBalloonAnimations.AIRBALLOON_FLY, limbSwing, limbSwingAmount, 1f, 1f);
		}
		this.updateAnimation(entity.floatingFuelEmptyAniState, AirBalloonAnimations.AIRBALLOON_FLOATINGEMPTY, ageInTicks, 1f);
		this.updateAnimation(entity.floatingFuelAniState, AirBalloonAnimations.AIRBALLOON_FLOATINGFUEL, ageInTicks, 1f);

		this.updateAnimation(entity.landAniState, AirBalloonAnimations.AIRBALLOON_LANDPOSE, ageInTicks, 1f);
		this.updateAnimation(entity.flyUpAniState, AirBalloonAnimations.AIRBALLOON_FLY, ageInTicks, 1f);
		this.updateAnimation(entity.flyDownAniState, AirBalloonAnimations.AIRBALLOON_DROP, ageInTicks, 1f);


	}
	public void setHeadAngles(float headYaw, float headPitch){
		//
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		airballoon.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return airballoon;
	}


}
