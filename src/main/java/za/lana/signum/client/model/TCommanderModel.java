// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package za.lana.signum.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.client.animation.TCommanderAnimations;
import za.lana.signum.client.animation.TTrooperAnimations;
import za.lana.signum.client.animation.UnicornAnimations;
import za.lana.signum.entity.hostile.TCommanderEntity;
import za.lana.signum.entity.hostile.TTrooperEntity;
import za.lana.signum.entity.mob.UnicornEntity;
public class TCommanderModel<T extends TCommanderEntity> extends SinglePartEntityModel<T> {
    private final ModelPart waist;
    private final ModelPart head;


    public TCommanderModel(ModelPart root) {
        this.waist = root.getChild("waist");
        this.head = root.getChild("waist").getChild("body").getChild("head");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData waist = modelPartData.addChild("waist", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 12.0F, 0.0F));

        ModelPartData body = waist.addChild("body", ModelPartBuilder.create().uv(33, 0).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

        ModelPartData breast_r1 = body.addChild("breast_r1", ModelPartBuilder.create().uv(27, 40).cuboid(-3.5F, -24.0F, -2.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(76, 0).cuboid(-7.5F, -24.0F, -2.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 21.5F, 10.75F, 0.5672F, 0.0F, 0.0F));

        ModelPartData chestarmor = body.addChild("chestarmor", ModelPartBuilder.create().uv(75, 0).cuboid(-4.1F, -24.1F, -2.1F, 4.1F, 10.1F, 5.35F, new Dilation(0.0F))
                .uv(75, 0).cuboid(-0.1F, -23.0F, -3.1F, 0.1F, 11.0F, 5.35F, new Dilation(0.0F))
                .uv(27, 28).cuboid(-4.25F, -23.0F, -3.0F, 8.5F, 11.0F, 5.75F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 17).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(5, 74).cuboid(-5.0F, -7.0F, -5.0F, 5.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(19, 64).cuboid(-5.0F, -9.0F, -2.0F, 5.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData rightarm = body.addChild("rightarm", ModelPartBuilder.create().uv(38, 63).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F))
                .uv(25, 17).cuboid(-1.5F, 3.0F, -1.75F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
                .uv(25, 17).cuboid(-1.5F, 3.0F, 0.75F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
                .uv(34, 73).cuboid(-3.0F, 8.0F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F))
                .uv(35, 74).cuboid(-2.25F, 10.0F, -1.25F, 2.5F, 2.0F, 2.5F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

        ModelPartData rightItem = rightarm.addChild("rightItem", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 11.0F, 0.0F));

        ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(58, 34).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

        ModelPartData leftItem = leftArm.addChild("leftItem", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 7.0F, 1.0F));

        ModelPartData la_armor = leftArm.addChild("la_armor", ModelPartBuilder.create().uv(0, 52).cuboid(-1.1F, -2.25F, -2.25F, 4.35F, 10.5F, 4.5F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leftLeg = body.addChild("leftLeg", ModelPartBuilder.create().uv(53, 68).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        ModelPartData leftleglow = leftLeg.addChild("leftleglow", ModelPartBuilder.create().uv(54, 70).cuboid(-1.5833F, 4.9379F, -1.9176F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F))
                .uv(57, 70).cuboid(-2.0833F, -0.0621F, -0.9176F, 1.5F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(57, 69).cuboid(0.1667F, -0.0621F, -0.9176F, 1.5F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-0.4167F, 5.0621F, -0.0824F));

        ModelPartData rightLeg = body.addChild("rightLeg", ModelPartBuilder.create().uv(52, 17).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.9F, 12.0F, 0.0F));

        ModelPartData rl_armor = rightLeg.addChild("rl_armor", ModelPartBuilder.create().uv(0, 34).cuboid(-2.1F, -0.2F, -2.5F, 4.6F, 11.7F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }
    @Override
    public void setAngles(TCommanderEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(TCommanderAnimations.TCOMMANDER_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAniState, TCommanderAnimations.TCOMMANDER_IDLE, ageInTicks, 1f);
        this.updateAnimation(entity.attackAniState, TCommanderAnimations.TCOMMANDER_ATTACK, ageInTicks, 1f);
    }
    public void setHeadAngles(float headYaw, float headPitch){
        headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
        headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        waist.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return waist;
    }
}