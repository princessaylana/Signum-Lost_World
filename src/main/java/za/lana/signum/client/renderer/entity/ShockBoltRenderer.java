/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.client.renderer.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.FireBoltEntityModel;
import za.lana.signum.client.model.ShockBoltEntityModel;
import za.lana.signum.entity.projectile.FireBoltEntity;
import za.lana.signum.entity.projectile.ShockBoltEntity;

public class ShockBoltRenderer extends EntityRenderer<ShockBoltEntity> {
    public static final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/projectile/shock_bolt_texture.png");
    protected ShockBoltEntityModel model;

    public ShockBoltRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        model = new ShockBoltEntityModel(ctx.getPart(ModModelLayers.SHOCK_BOLT));
    }

    @Override
    public void render(ShockBoltEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.model.getLayer(TEXTURE), false, false);
        this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(ShockBoltEntity entity) {
        return TEXTURE;
    }

}
