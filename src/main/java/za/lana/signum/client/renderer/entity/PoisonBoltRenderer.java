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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.PoisonBoltEntityModel;
import za.lana.signum.client.model.SpellBoltEntityModel;
import za.lana.signum.entity.projectile.PoisonBoltEntity;
import za.lana.signum.entity.projectile.SpellBoltEntity;

public class PoisonBoltRenderer extends EntityRenderer<PoisonBoltEntity> {
    public static final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/projectile/tiberium_spit_texture.png");
    protected PoisonBoltEntityModel model;

    public PoisonBoltRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        model = new PoisonBoltEntityModel(ctx.getPart(ModModelLayers.POISONBOLT_ENTITY));
    }

    @Override
    public void render(PoisonBoltEntity bolt, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(0.0f, 0.15f, 0.0f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(yaw, bolt.prevYaw, bolt.getYaw()) - 90.0f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(yaw, bolt.prevPitch, bolt.getPitch())));
        this.model.setAngles(bolt, yaw, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrices.pop();
        super.render(bolt, yaw, tickDelta, matrices, vertexConsumers, light);
    }


    @Override
    public Identifier getTexture(PoisonBoltEntity entity) {
        return TEXTURE;
    }

}
