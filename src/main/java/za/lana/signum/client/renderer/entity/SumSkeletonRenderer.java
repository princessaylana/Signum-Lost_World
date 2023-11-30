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
import za.lana.signum.client.model.SumSkeletonModel;
import za.lana.signum.client.renderer.feature.SumSkeletonEyesFeatureRenderer;
import za.lana.signum.entity.hostile.SumSkeletonEntity;

public class SumSkeletonRenderer extends MobEntityRenderer<SumSkeletonEntity, SumSkeletonModel<SumSkeletonEntity>> {
    private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/skeletons/sumskeleton_texture.png");
    public SumSkeletonRenderer(EntityRendererFactory.Context context) {
        super(context, new SumSkeletonModel<>(context.getPart(ModModelLayers.SSKELETON)), 0.4f); //entity shadow
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
        this.addFeature(new SumSkeletonEyesFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(SumSkeletonEntity entity) {
        return TEXTURE;
    }
    protected boolean isShaking(SumSkeletonEntity sumSkeleton) {
        return sumSkeleton.isShaking();
    }
    @Override
    public void render(SumSkeletonEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
