// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import za.lana.signum.client.animation.CargoDroneAnimations;
import za.lana.signum.entity.transport.CargoDroneEntity;

public class CargoDroneModel<T extends CargoDroneEntity> extends SinglePartEntityModel<T> {
	private final ModelPart cdrone;

	public CargoDroneModel(ModelPart root) {
		this.cdrone = root.getChild("mainBody");
		//this.head = airship.getChild("body").getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mainBody = modelPartData.addChild("mainBody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.5F, 0.0F));

		ModelPartData body = mainBody.addChild("body", ModelPartBuilder.create().uv(0, 12).cuboid(-2.0F, -10.25F, -3.5F, 4.0F, 2.25F, 7.0F, new Dilation(0.0F))
				.uv(33, 33).cuboid(-1.5F, -9.75F, -4.0F, 1.0F, 1.0F, 0.5F, new Dilation(0.0F))
				.uv(0, 5).cuboid(0.5F, -9.75F, -4.0F, 1.0F, 1.0F, 0.5F, new Dilation(0.0F))
				.uv(21, 0).cuboid(-3.25F, -8.25F, -2.5F, 6.5F, 3.5F, 1.0F, new Dilation(0.0F))
				.uv(16, 12).cuboid(-3.25F, -8.25F, 1.5F, 6.5F, 3.5F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));

		ModelPartData rf = body.addChild("rf", ModelPartBuilder.create().uv(15, 27).cuboid(-4.375F, -0.5F, -0.7F, 5.75F, 1.0F, 1.4F, new Dilation(0.0F)), ModelTransform.of(-1.625F, -9.0F, -2.75F, 0.0F, -0.7854F, 0.0F));

		ModelPartData propandshaft = rf.addChild("propandshaft", ModelPartBuilder.create().uv(25, 33).cuboid(-13.75F, -12.5F, -3.75F, 0.5F, 2.5F, 0.5F, new Dilation(0.0F))
				.uv(0, 30).cuboid(-14.0F, -12.0F, -4.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(9.875F, 10.5F, 3.5F));

		ModelPartData prop1 = propandshaft.addChild("prop1", ModelPartBuilder.create().uv(23, 18).cuboid(-4.0F, 0.4F, -0.5F, 8.0F, 0.1F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-13.5F, -12.75F, -3.5F));

		ModelPartData landing = propandshaft.addChild("landing", ModelPartBuilder.create().uv(20, 30).cuboid(-0.25F, -1.75F, -0.25F, 0.5F, 3.5F, 0.5F, new Dilation(0.0F)), ModelTransform.pivot(-13.5F, -7.75F, -3.5F));

		ModelPartData rb = body.addChild("rb", ModelPartBuilder.create().uv(15, 24).cuboid(-4.375F, -0.5F, -0.7F, 5.75F, 1.0F, 1.4F, new Dilation(0.0F)), ModelTransform.of(-1.625F, -9.0F, 2.75F, 0.0F, 0.7854F, 0.0F));

		ModelPartData propandshaft3 = rb.addChild("propandshaft3", ModelPartBuilder.create().uv(32, 8).cuboid(-13.75F, -12.5F, -3.75F, 0.5F, 2.5F, 0.5F, new Dilation(0.0F))
				.uv(0, 12).cuboid(-14.0F, -12.0F, -4.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(9.875F, 10.5F, 3.5F));

		ModelPartData prop3 = propandshaft3.addChild("prop3", ModelPartBuilder.create().uv(0, 22).cuboid(-4.0F, 0.4F, -0.5F, 8.0F, 0.1F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-13.5F, -12.75F, -3.5F));

		ModelPartData landing3 = propandshaft3.addChild("landing3", ModelPartBuilder.create().uv(10, 30).cuboid(-0.25F, -1.75F, -0.25F, 0.5F, 3.5F, 0.5F, new Dilation(0.0F)), ModelTransform.pivot(-13.5F, -7.75F, -3.5F));

		ModelPartData lb = body.addChild("lb", ModelPartBuilder.create().uv(0, 24).cuboid(-1.375F, -0.5F, -0.7F, 5.75F, 1.0F, 1.4F, new Dilation(0.0F)), ModelTransform.of(1.625F, -9.0F, 2.75F, 0.0F, -0.7854F, 0.0F));

		ModelPartData propandshaft4 = lb.addChild("propandshaft4", ModelPartBuilder.create().uv(30, 24).cuboid(13.25F, -12.5F, -3.75F, 0.5F, 2.5F, 0.5F, new Dilation(0.0F))
				.uv(0, 0).cuboid(13.0F, -12.0F, -4.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.875F, 10.5F, 3.5F));

		ModelPartData prop4 = propandshaft4.addChild("prop4", ModelPartBuilder.create().uv(21, 6).cuboid(-4.0F, 0.4F, -0.5F, 8.0F, 0.1F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(13.5F, -12.75F, -3.5F));

		ModelPartData landing4 = propandshaft4.addChild("landing4", ModelPartBuilder.create().uv(5, 30).cuboid(-0.25F, -1.75F, -0.25F, 0.5F, 3.5F, 0.5F, new Dilation(0.0F)), ModelTransform.pivot(13.5F, -7.75F, -3.5F));

		ModelPartData lf = body.addChild("lf", ModelPartBuilder.create().uv(0, 27).cuboid(-1.375F, -0.5F, -0.7F, 5.75F, 1.0F, 1.4F, new Dilation(0.0F)), ModelTransform.of(1.625F, -9.0F, -2.75F, 0.0F, 0.7854F, 0.0F));

		ModelPartData propandshaft2 = lf.addChild("propandshaft2", ModelPartBuilder.create().uv(33, 13).cuboid(13.25F, -12.5F, -3.75F, 0.5F, 2.5F, 0.5F, new Dilation(0.0F))
				.uv(29, 29).cuboid(13.0F, -12.0F, -4.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.875F, 10.5F, 3.5F));

		ModelPartData prop2 = propandshaft2.addChild("prop2", ModelPartBuilder.create().uv(19, 22).cuboid(-4.0F, 0.4F, -0.5F, 8.0F, 0.1F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(13.5F, -12.75F, -3.5F));

		ModelPartData landing2 = propandshaft2.addChild("landing2", ModelPartBuilder.create().uv(15, 30).cuboid(-0.25F, -1.75F, -0.25F, 0.5F, 3.5F, 0.5F, new Dilation(0.0F)), ModelTransform.pivot(13.5F, -7.75F, -3.5F));

		ModelPartData barrel = body.addChild("barrel", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -8.0F, -4.0F, 6.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(CargoDroneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.animateMovement(CargoDroneAnimations.CARGODRONE_FLY, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAniState, CargoDroneAnimations.CARGODRONE_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.landAniState, CargoDroneAnimations.CARGODRONE_LAND, ageInTicks, 1f);
	}

	public void setHeadAngles(float headYaw, float headPitch){
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		cdrone.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return cdrone;
	}


}
