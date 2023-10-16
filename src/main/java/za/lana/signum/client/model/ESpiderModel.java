// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.client.animation.ESpiderAnimations;
import za.lana.signum.entity.hostile.ESpiderEntity;

public class ESpiderModel<T extends ESpiderEntity> extends SinglePartEntityModel<T> {
	private final ModelPart spider;
	private final ModelPart head;

	public ESpiderModel(ModelPart root) {
		this.spider = root.getChild("mainbody");
		this.head = spider.getChild("body").getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData mainbody = modelPartData.addChild("mainbody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 21.0F, 0.0F));

		ModelPartData body = mainbody.addChild("body", ModelPartBuilder.create().uv(0, 38).cuboid(-4.0F, -8.5F, -7.0F, 8.0F, 4.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -1.0F));

		ModelPartData legs_r = body.addChild("legs_r", ModelPartBuilder.create().uv(38, 0).cuboid(-5.0F, -3.0F, -2.5F, 1.0F, 2.0F, 11.5F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.5F, -4.0F));

		ModelPartData back_r = legs_r.addChild("back_r", ModelPartBuilder.create().uv(54, 39).cuboid(-8.0F, -1.5F, -1.75F, 7.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -2.0F, 8.25F));

		ModelPartData mid = back_r.addChild("mid", ModelPartBuilder.create().uv(49, 60).cuboid(-6.0F, -1.0F, -1.25F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, 0.0F, 0.0F));

		ModelPartData tip = mid.addChild("tip", ModelPartBuilder.create().uv(0, 31).cuboid(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(63, 62).cuboid(-11.0F, -0.75F, -0.75F, 6.0F, 1.5F, 1.5F, new Dilation(0.0F))
		.uv(67, 16).cuboid(-3.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(40, 64).cuboid(-5.0F, -1.25F, -1.25F, 2.0F, 2.5F, 2.5F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 0.0F, -0.25F, 0.0F, 0.0F, -0.2182F));

		ModelPartData endtip = tip.addChild("endtip", ModelPartBuilder.create().uv(22, 68).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(50, 64).cuboid(-5.0F, -0.75F, -0.75F, 4.0F, 1.5F, 1.5F, new Dilation(0.0F)), ModelTransform.of(-12.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		ModelPartData tiptip = endtip.addChild("tiptip", ModelPartBuilder.create().uv(52, 0).cuboid(-14.0F, -0.5F, -0.5F, 14.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2269F));

		ModelPartData mid_r = legs_r.addChild("mid_r", ModelPartBuilder.create().uv(17, 60).cuboid(-5.75F, -1.5F, -1.75F, 5.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.25F, -2.0F, 5.25F));

		ModelPartData mid2 = mid_r.addChild("mid2", ModelPartBuilder.create().uv(0, 4).cuboid(-5.0F, -1.0F, -1.25F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.75F, 0.0F, 0.0F));

		ModelPartData tip2 = mid2.addChild("tip2", ModelPartBuilder.create().uv(32, 31).cuboid(-1.7322F, -1.6623F, -1.5F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(64, 34).cuboid(-9.7322F, -0.9123F, -0.75F, 4.0F, 1.5F, 1.5F, new Dilation(0.0F))
		.uv(67, 12).cuboid(-3.7322F, -1.1623F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(30, 64).cuboid(-5.7322F, -1.4123F, -1.25F, 2.0F, 2.5F, 2.5F, new Dilation(0.0F)), ModelTransform.of(-5.25F, 0.0F, -0.25F, 0.0F, 0.0F, -0.2182F));

		ModelPartData endtip2 = tip2.addChild("endtip2", ModelPartBuilder.create().uv(60, 66).cuboid(-1.0F, -1.25F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(64, 30).cuboid(-4.75F, -1.0F, -0.75F, 4.0F, 1.5F, 1.5F, new Dilation(0.0F)), ModelTransform.of(-10.7255F, 0.0265F, 0.0F, 0.0F, 0.0F, 0.2182F));

		ModelPartData tiptip2 = endtip2.addChild("tiptip2", ModelPartBuilder.create().uv(52, 10).cuboid(-8.9694F, -0.5186F, -0.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2269F));

		ModelPartData fmid_r = legs_r.addChild("fmid_r", ModelPartBuilder.create().uv(54, 45).cuboid(-5.75F, -1.5F, -1.75F, 5.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.25F, -2.0F, 2.25F));

		ModelPartData mid3 = fmid_r.addChild("mid3", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -1.0F, -1.25F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.75F, 0.0F, 0.0F));

		ModelPartData tip3 = mid3.addChild("tip3", ModelPartBuilder.create().uv(32, 25).cuboid(-1.7322F, -1.6623F, -1.5F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(64, 26).cuboid(-9.7322F, -0.9123F, -0.75F, 4.0F, 1.5F, 1.5F, new Dilation(0.0F))
		.uv(66, 51).cuboid(-3.7322F, -1.1623F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 42).cuboid(-5.7322F, -1.4123F, -1.25F, 2.0F, 2.5F, 2.5F, new Dilation(0.0F)), ModelTransform.of(-5.25F, 0.0F, -0.25F, 0.0F, 0.0F, -0.2182F));

		ModelPartData endtip3 = tip3.addChild("endtip3", ModelPartBuilder.create().uv(16, 66).cuboid(-1.0F, -1.25F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(38, 6).cuboid(-4.75F, -1.0F, -0.75F, 4.0F, 1.5F, 1.5F, new Dilation(0.0F)), ModelTransform.of(-10.7255F, 0.0265F, 0.0F, 0.0F, 0.0F, 0.2182F));

		ModelPartData tiptip3 = endtip3.addChild("tiptip3", ModelPartBuilder.create().uv(28, 48).cuboid(-8.9694F, -0.5186F, -0.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2269F));

		ModelPartData front_r = legs_r.addChild("front_r", ModelPartBuilder.create().uv(20, 54).cuboid(-8.0F, -1.5F, -1.75F, 7.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -2.0F, -0.75F));

		ModelPartData mid4 = front_r.addChild("mid4", ModelPartBuilder.create().uv(33, 60).cuboid(-6.0F, -1.0F, -1.25F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, 0.0F, 0.0F));

		ModelPartData tip4 = mid4.addChild("tip4", ModelPartBuilder.create().uv(0, 25).cuboid(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 61).cuboid(-11.0F, -0.75F, -0.75F, 6.0F, 1.5F, 1.5F, new Dilation(0.0F))
		.uv(8, 65).cuboid(-3.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(38, 0).cuboid(-5.0F, -1.25F, -1.25F, 2.0F, 2.5F, 2.5F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 0.0F, -0.25F, 0.0F, 0.0F, -0.2182F));

		ModelPartData endtip4 = tip4.addChild("endtip4", ModelPartBuilder.create().uv(64, 58).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 38).cuboid(-5.0F, -0.75F, -0.75F, 4.0F, 1.5F, 1.5F, new Dilation(0.0F)), ModelTransform.of(-12.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		ModelPartData tiptip4 = endtip4.addChild("tiptip4", ModelPartBuilder.create().uv(51, 24).cuboid(-14.0F, -0.5F, -0.5F, 14.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2269F));

		ModelPartData legs_l = body.addChild("legs_l", ModelPartBuilder.create().uv(38, 0).mirrored().cuboid(4.0F, -3.0F, -2.5F, 1.0F, 2.0F, 11.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, -4.5F, -4.0F));

		ModelPartData back_l = legs_l.addChild("back_l", ModelPartBuilder.create().uv(54, 39).mirrored().cuboid(1.0F, -1.5F, -1.75F, 7.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(4.0F, -2.0F, 8.25F));

		ModelPartData mid5 = back_l.addChild("mid5", ModelPartBuilder.create().uv(49, 60).mirrored().cuboid(0.0F, -1.0F, -1.25F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(8.0F, 0.0F, 0.0F));

		ModelPartData tip5 = mid5.addChild("tip5", ModelPartBuilder.create().uv(0, 31).mirrored().cuboid(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(63, 62).mirrored().cuboid(5.0F, -0.75F, -0.75F, 6.0F, 1.5F, 1.5F, new Dilation(0.0F)).mirrored(false)
		.uv(67, 16).mirrored().cuboid(1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(40, 64).mirrored().cuboid(3.0F, -1.25F, -1.25F, 2.0F, 2.5F, 2.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(7.0F, 0.0F, -0.25F, 0.0F, 0.0F, 0.2182F));

		ModelPartData endtip5 = tip5.addChild("endtip5", ModelPartBuilder.create().uv(22, 68).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(50, 64).mirrored().cuboid(1.0F, -0.75F, -0.75F, 4.0F, 1.5F, 1.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(12.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

		ModelPartData tiptip5 = endtip5.addChild("tiptip5", ModelPartBuilder.create().uv(52, 0).mirrored().cuboid(0.0F, -0.5F, -0.5F, 14.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2269F));

		ModelPartData mid_l = legs_l.addChild("mid_l", ModelPartBuilder.create().uv(17, 60).mirrored().cuboid(0.75F, -1.5F, -1.75F, 5.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(4.25F, -2.0F, 5.25F));

		ModelPartData mid6 = mid_l.addChild("mid6", ModelPartBuilder.create().uv(0, 4).mirrored().cuboid(0.0F, -1.0F, -1.25F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(5.75F, 0.0F, 0.0F));

		ModelPartData tip6 = mid6.addChild("tip6", ModelPartBuilder.create().uv(32, 31).mirrored().cuboid(-0.2678F, -1.6623F, -1.5F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(64, 34).mirrored().cuboid(5.7322F, -0.9123F, -0.75F, 4.0F, 1.5F, 1.5F, new Dilation(0.0F)).mirrored(false)
		.uv(67, 12).mirrored().cuboid(1.7322F, -1.1623F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(30, 64).mirrored().cuboid(3.7322F, -1.4123F, -1.25F, 2.0F, 2.5F, 2.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.25F, 0.0F, -0.25F, 0.0F, 0.0F, 0.2182F));

		ModelPartData endtip6 = tip6.addChild("endtip6", ModelPartBuilder.create().uv(60, 66).mirrored().cuboid(-1.0F, -1.25F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(64, 30).mirrored().cuboid(0.75F, -1.0F, -0.75F, 4.0F, 1.5F, 1.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(10.7255F, 0.0265F, 0.0F, 0.0F, 0.0F, -0.2182F));

		ModelPartData tiptip6 = endtip6.addChild("tiptip6", ModelPartBuilder.create().uv(52, 10).mirrored().cuboid(-1.0306F, -0.5186F, -0.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2269F));

		ModelPartData fmid_l = legs_l.addChild("fmid_l", ModelPartBuilder.create().uv(54, 45).mirrored().cuboid(0.75F, -1.5F, -1.75F, 5.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(4.25F, -2.0F, 2.25F));

		ModelPartData mid7 = fmid_l.addChild("mid7", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(0.0F, -1.0F, -1.25F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(5.75F, 0.0F, 0.0F));

		ModelPartData tip7 = mid7.addChild("tip7", ModelPartBuilder.create().uv(32, 25).mirrored().cuboid(-0.2678F, -1.6623F, -1.5F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(64, 26).mirrored().cuboid(5.7322F, -0.9123F, -0.75F, 4.0F, 1.5F, 1.5F, new Dilation(0.0F)).mirrored(false)
		.uv(66, 51).mirrored().cuboid(1.7322F, -1.1623F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 42).mirrored().cuboid(3.7322F, -1.4123F, -1.25F, 2.0F, 2.5F, 2.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.25F, 0.0F, -0.25F, 0.0F, 0.0F, 0.2182F));

		ModelPartData endtip7 = tip7.addChild("endtip7", ModelPartBuilder.create().uv(16, 66).mirrored().cuboid(-1.0F, -1.25F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(38, 6).mirrored().cuboid(0.75F, -1.0F, -0.75F, 4.0F, 1.5F, 1.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(10.7255F, 0.0265F, 0.0F, 0.0F, 0.0F, -0.2182F));

		ModelPartData tiptip7 = endtip7.addChild("tiptip7", ModelPartBuilder.create().uv(28, 48).mirrored().cuboid(-1.0306F, -0.5186F, -0.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2269F));

		ModelPartData front_l = legs_l.addChild("front_l", ModelPartBuilder.create().uv(20, 54).mirrored().cuboid(1.0F, -1.5F, -1.75F, 7.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(4.0F, -2.0F, -0.75F));

		ModelPartData mid8 = front_l.addChild("mid8", ModelPartBuilder.create().uv(33, 60).mirrored().cuboid(0.0F, -1.0F, -1.25F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(8.0F, 0.0F, 0.0F));

		ModelPartData tip8 = mid8.addChild("tip8", ModelPartBuilder.create().uv(0, 25).mirrored().cuboid(-1.0F, -1.5F, -1.5F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 61).mirrored().cuboid(5.0F, -0.75F, -0.75F, 6.0F, 1.5F, 1.5F, new Dilation(0.0F)).mirrored(false)
		.uv(8, 65).mirrored().cuboid(1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(38, 0).mirrored().cuboid(3.0F, -1.25F, -1.25F, 2.0F, 2.5F, 2.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(7.0F, 0.0F, -0.25F, 0.0F, 0.0F, 0.2182F));

		ModelPartData endtip8 = tip8.addChild("endtip8", ModelPartBuilder.create().uv(64, 58).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 38).mirrored().cuboid(1.0F, -0.75F, -0.75F, 4.0F, 1.5F, 1.5F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(12.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

		ModelPartData tiptip8 = endtip8.addChild("tiptip8", ModelPartBuilder.create().uv(51, 24).mirrored().cuboid(0.0F, -0.5F, -0.5F, 14.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2269F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(52, 14).cuboid(-2.0F, -6.0F, -12.0F, 4.0F, 2.0F, 7.0F, new Dilation(0.0F))
		.uv(0, 8).cuboid(-2.75F, -3.5F, -1.0F, 5.5F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.5F, 6.0F));

		ModelPartData eyes = head.addChild("eyes", ModelPartBuilder.create().uv(7, 25).cuboid(1.75F, -5.75F, 2.5F, 0.5F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(38, 10).cuboid(0.75F, -5.75F, 1.5F, 1.0F, 1.0F, 0.5F, new Dilation(0.0F))
		.uv(39, 25).cuboid(-1.75F, -5.75F, 1.5F, 1.0F, 1.0F, 0.5F, new Dilation(0.0F))
		.uv(0, 12).cuboid(-2.25F, -5.75F, 2.5F, 0.5F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 12).cuboid(1.75F, -5.75F, 5.5F, 0.5F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(4, 12).cuboid(-2.25F, -5.75F, 5.5F, 0.5F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(7, 31).cuboid(-0.5F, -5.75F, 1.5F, 1.0F, 1.0F, 0.5F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -14.0F));

		ModelPartData fangs = head.addChild("fangs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -14.0F));

		ModelPartData left = fangs.addChild("left", ModelPartBuilder.create().uv(0, 65).cuboid(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -0.5F, 0.0F));

		ModelPartData fangbot_r1 = left.addChild("fangbot_r1", ModelPartBuilder.create().uv(50, 68).cuboid(-2.5F, 0.0F, -1.0F, 1.0F, 2.75F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 0.5F, 0.0F, 0.6109F, 0.0F, 0.0F));

		ModelPartData right = fangs.addChild("right", ModelPartBuilder.create().uv(0, 65).mirrored().cuboid(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.0F, -0.5F, 0.0F));

		ModelPartData fangbot_r2 = right.addChild("fangbot_r2", ModelPartBuilder.create().uv(50, 68).mirrored().cuboid(1.5F, 0.0F, -1.0F, 1.0F, 2.75F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.0F, 0.5F, 0.0F, 0.6109F, 0.0F, 0.0F));

		ModelPartData abdomen = body.addChild("abdomen", ModelPartBuilder.create().uv(40, 39).cuboid(-7.0F, -6.0F, 1.75F, 1.0F, 9.0F, 12.0F, new Dilation(0.0F))
		.uv(40, 39).mirrored().cuboid(6.0F, -6.0F, 1.75F, 1.0F, 9.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
		.uv(52, 2).cuboid(-5.5F, -4.0F, -0.25F, 11.0F, 7.0F, 1.0F, new Dilation(0.0F))
		.uv(28, 39).cuboid(-5.0F, -4.0F, 14.75F, 10.0F, 7.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 54).cuboid(-4.0F, -3.0F, 16.75F, 8.0F, 5.0F, 2.0F, new Dilation(0.0F))
		.uv(32, 26).cuboid(-5.0F, -8.0F, 1.75F, 10.0F, 1.0F, 12.0F, new Dilation(0.0F))
		.uv(0, 25).cuboid(-5.0F, 4.0F, 1.75F, 10.0F, 1.0F, 12.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-6.0F, -7.0F, 0.75F, 12.0F, 11.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -6.0F, 6.25F));

		ModelPartData cube_r1 = abdomen.addChild("cube_r1", ModelPartBuilder.create().uv(52, 14).cuboid(-0.75F, 10.0F, 18.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, -3.25F, 0.5672F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void setAngles(ESpiderEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(ESpiderAnimations.BSPIDER_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAniState, ESpiderAnimations.BSPIDER_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.attackAniState, ESpiderAnimations.BSPIDER_ATTACK, ageInTicks, 1f);
	}
	public void setHeadAngles(float headYaw, float headPitch){
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		// spider
		this.head.yaw = headYaw * ((float)Math.PI / 180);
		this.head.pitch = headPitch * ((float)Math.PI / 180);
		// camel
		//this.head.yaw = headYaw * 0.017453292F;
		//this.head.pitch = headPitch * 0.017453292F;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		spider.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
	@Override
	public ModelPart getPart() {
		return spider;
	}

}