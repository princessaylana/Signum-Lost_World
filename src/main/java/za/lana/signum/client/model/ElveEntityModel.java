// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.client.animation.ElveAnimations;
import za.lana.signum.client.animation.TTrooperAnimations;
import za.lana.signum.entity.hostile.ElveEntity;
import za.lana.signum.entity.hostile.TTrooperEntity;

public class ElveEntityModel<T extends ElveEntity> extends SinglePartEntityModel<T> implements ModelWithArms {
    private final ModelPart elve;
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    public final ModelPart hat;

    public ElveEntityModel(ModelPart root) {
        this.elve = root.getChild("waist");
        this.head = elve.getChild("body").getChild("head");
        this.rightArm = elve.getChild("body").getChild("rightArm");
        this.leftArm = elve.getChild("body").getChild("leftArm");
        this.hat = elve.getChild("body").getChild("head").getChild("hat");

    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData waist = modelPartData.addChild("waist", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

        ModelPartData body = waist.addChild("body", ModelPartBuilder.create().uv(33, 0).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

        ModelPartData chestarmor = body.addChild("chestarmor", ModelPartBuilder.create().uv(27, 28).cuboid(-4.25F, -23.0F, -3.0F, 8.5F, 11.0F, 5.75F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 17).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leftEar_r1 = head.addChild("leftEar_r1", ModelPartBuilder.create().uv(0, 17).mirrored().cuboid(4.25F, -6.0F, -3.0F, 0.0F, 4.0F, 3.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 17).cuboid(-4.25F, -6.0F, -3.0F, 0.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

        ModelPartData nose_r1 = head.addChild("nose_r1", ModelPartBuilder.create().uv(25, 0).cuboid(-0.5F, -4.5F, -4.75F, 0.75F, 2.5F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        ModelPartData hat = head.addChild("hat", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData rightarm = body.addChild("rightArm", ModelPartBuilder.create().uv(57, 59).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

        ModelPartData rightItem = rightarm.addChild("rightItem", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 7.0F, 1.0F));

        ModelPartData ra_armor = rightarm.addChild("ra_armor", ModelPartBuilder.create().uv(42, 46).cuboid(-8.25F, -24.25F, -2.25F, 4.35F, 10.5F, 4.5F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 22.0F, 0.0F));

        ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(58, 34).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

        ModelPartData leftItem = leftArm.addChild("leftItem", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 7.0F, 1.0F));

        ModelPartData la_armor = leftArm.addChild("la_armor", ModelPartBuilder.create().uv(0, 52).cuboid(-1.1F, -2.25F, -2.25F, 4.35F, 10.5F, 4.5F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leftLeg = body.addChild("leftLeg", ModelPartBuilder.create().uv(58, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        ModelPartData ll_armor = leftLeg.addChild("ll_armor", ModelPartBuilder.create().uv(21, 46).cuboid(-2.45F, -0.2F, -2.5F, 4.55F, 11.7F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData rightLeg = body.addChild("rightLeg", ModelPartBuilder.create().uv(52, 17).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.9F, 12.0F, 0.0F));

        ModelPartData rl_armor = rightLeg.addChild("rl_armor", ModelPartBuilder.create().uv(0, 34).cuboid(-2.1F, -0.2F, -2.5F, 4.6F, 11.7F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }


    @Override
    public void setAngles(ElveEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        //arm
        this.rightArm.pitch = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 2.0f * limbSwingAmount * 0.5f;
        this.rightArm.yaw = 0.0f;
        this.rightArm.roll = 0.0f;

        this.animateMovement(ElveAnimations.ELVE_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAniState, ElveAnimations.ELVE_IDLE, ageInTicks, 1f);
        this.updateAnimation(entity.attackAniState, ElveAnimations.ELVE_ATTACK, ageInTicks, 1f);
    }
    public void setHeadAngles(float headYaw, float headPitch){
        headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
        headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        elve.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    // returns main body part
    @Override
    public ModelPart getPart() {
        return elve;
    }
    private ModelPart getAttackingArm(Arm arm) {
        if (arm == Arm.LEFT) {
            return this.leftArm;
        }
        return this.rightArm;
    }
    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        this.getAttackingArm(arm).rotate(matrices);
    }


}