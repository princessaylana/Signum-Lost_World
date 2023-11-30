/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.client.renderer.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.EnderSkeletonModel;
import za.lana.signum.client.model.IceSkeletonModel;
import za.lana.signum.client.renderer.feature.DarkSkeletonEyesFeatureRenderer;
import za.lana.signum.client.renderer.feature.EnderSkeletonEyesFeatureRenderer;
import za.lana.signum.entity.hostile.EnderSkeletonEntity;
import za.lana.signum.entity.hostile.IceSkeletonEntity;

public class EnderSkeletonRenderer extends MobEntityRenderer<EnderSkeletonEntity, EnderSkeletonModel<EnderSkeletonEntity>> {
    private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/skeletons/enderskeleton_texture.png");
    public EnderSkeletonRenderer(EntityRendererFactory.Context context) {
        super(context, new EnderSkeletonModel<>(context.getPart(ModModelLayers.ENDERSKELETON)), 0.4f); //entity shadow
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
        this.addFeature(new EnderSkeletonEyesFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(EnderSkeletonEntity entity) {
        return TEXTURE;
    }

    protected boolean isShaking(EnderSkeletonEntity enderSkeleton) {
        return enderSkeleton.isShaking();
    }

    @Override
    public void render(EnderSkeletonEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
