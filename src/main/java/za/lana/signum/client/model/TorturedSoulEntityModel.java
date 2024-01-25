// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.client.animation.GhostAnimations;
import za.lana.signum.client.animation.TorturedSoulAnimations;
import za.lana.signum.entity.hostile.GhostEntity;
import za.lana.signum.entity.hostile.TorturedSoulEntity;


public class TorturedSoulEntityModel<T extends TorturedSoulEntity> extends SinglePartEntityModel<T> implements ModelWithArms {

    private final ModelPart torturedSoul;
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

	public TorturedSoulEntityModel(ModelPart root) {
        super(RenderLayer::getEntityTranslucent);
        torturedSoul = root.getChild("mainBody");
        this.head = torturedSoul.getChild("body").getChild("head");
        this.rightArm = torturedSoul.getChild("body").getChild("rightArm");
        this.leftArm = torturedSoul.getChild("body").getChild("leftArm");
        ModelPart body = torturedSoul.getChild("body");
	}

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData mainBody = modelPartData.addChild("mainBody", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

        ModelPartData body = mainBody.addChild("body", ModelPartBuilder.create().uv(0, 34).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

        ModelPartData robe = body.addChild("robe", ModelPartBuilder.create().uv(0, 0).cuboid(-4.25F, -24.0F, -3.0F, 8.5F, 22.0F, 5.75F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 23.9F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(31, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData hat = head.addChild("hat", ModelPartBuilder.create().uv(23, 21).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData nose = head.addChild("nose", ModelPartBuilder.create().uv(48, 17).cuboid(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

        ModelPartData rightArm = body.addChild("rightArm", ModelPartBuilder.create().uv(17, 55).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

        ModelPartData rightItem = rightArm.addChild("rightItem", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 7.0F, 1.0F));

        ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(0, 51).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

        ModelPartData leftItem = leftArm.addChild("leftItem", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 7.0F, 1.0F));

        ModelPartData leftLeg = body.addChild("leftLeg", ModelPartBuilder.create().uv(42, 38).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        ModelPartData rightLeg = body.addChild("rightLeg", ModelPartBuilder.create().uv(25, 38).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.9F, 12.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    public void setHeadAngles(float headYaw, float headPitch){
        headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
        headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

	@Override
	public void setAngles(TorturedSoulEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        //arms
        this.rightArm.pitch = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 2.0f * limbSwingAmount * 0.5f;
        this.rightArm.yaw = 0.0f;
        this.rightArm.roll = 0.0f;
        this.leftArm.pitch = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 2.0f * limbSwingAmount * 0.5f;
        this.leftArm.yaw = 0.0f;
        this.leftArm.roll = 0.0f;

        this.animateMovement(TorturedSoulAnimations.TORTURED_SOUL_MOVE, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAniState, TorturedSoulAnimations.TORTURED_SOUL_IDLE, ageInTicks, 1f);
        this.updateAnimation(entity.attackAniState, TorturedSoulAnimations.TORTURED_SOUL_MELEE, ageInTicks, 1f);

    }

    @Override
    public ModelPart getPart() {
        return torturedSoul;
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
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        // Render Transparent
        torturedSoul.render(matrices, vertexConsumer, light, overlay, red, green, blue, 0.25F);
    }
}