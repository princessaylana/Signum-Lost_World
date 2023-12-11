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
import za.lana.signum.client.model.TiberiumBoltEntityModel;
import za.lana.signum.client.model.TiberiumSpitEntityModel;
import za.lana.signum.entity.projectile.TiberiumBoltEntity;
import za.lana.signum.entity.projectile.TiberiumSpitEntity;

public class TiberiumSpitRenderer extends EntityRenderer<TiberiumSpitEntity> {
    public static final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/projectile/tiberium_spit_texture.png");
    protected TiberiumSpitEntityModel model;

    public TiberiumSpitRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        model = new TiberiumSpitEntityModel(ctx.getPart(ModModelLayers.TIBERIUM_SPIT));
    }

    @Override
    public void render(TiberiumSpitEntity bolt, float yaw, float tickDelta, MatrixStack matrices,
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
    public Identifier getTexture(TiberiumSpitEntity entity) {
        return TEXTURE;
    }

}
