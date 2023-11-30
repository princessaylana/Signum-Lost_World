
package za.lana.signum.client.renderer.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.SpiderSpitModel;
import za.lana.signum.entity.projectile.SpiderSpitEntity;

@Environment(value=EnvType.CLIENT)
public class SpiderSpitRenderer
        extends EntityRenderer<SpiderSpitEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/hostile/spiderspit_texture.png");
    private final SpiderSpitModel<SpiderSpitEntity> model;

    public SpiderSpitRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new SpiderSpitModel<>(context.getPart(ModModelLayers.SPIDER_SPIT));
    }

    @Override
    public void render(SpiderSpitEntity spiderSpit, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(0.0f, 0.15f, 0.0f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, spiderSpit.prevYaw, spiderSpit.getYaw()) - 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, spiderSpit.prevPitch, spiderSpit.getPitch()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.model.getLayer(TEXTURE), false, false);
        this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV, 0.25F, 0.25F, 0.25F, 1.0F);
        matrices.pop();
        super.render(spiderSpit, yaw, tickDelta, matrices, vertexConsumers, light);
    }


    @Override
    public Identifier getTexture(SpiderSpitEntity spiderSpit) {
        return TEXTURE;
    }
}

