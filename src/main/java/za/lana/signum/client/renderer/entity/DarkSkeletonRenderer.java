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
import za.lana.signum.client.model.DarkSkeletonModel;
import za.lana.signum.client.renderer.feature.DarkSkeletonEyesFeatureRenderer;
import za.lana.signum.entity.hostile.DarkSkeletonEntity;

public class DarkSkeletonRenderer extends MobEntityRenderer<DarkSkeletonEntity, DarkSkeletonModel<DarkSkeletonEntity>> {
    private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/skeletons/darkskeleton_texture.png");
    public DarkSkeletonRenderer(EntityRendererFactory.Context context) {
        super(context, new DarkSkeletonModel<>(context.getPart(ModModelLayers.DARKSKELETON)), 0.4f); //entity shadow
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
        this.addFeature(new DarkSkeletonEyesFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(DarkSkeletonEntity entity) {
        return TEXTURE;
    }

    protected boolean isShaking(DarkSkeletonEntity iceSkeleton) {
        return iceSkeleton.isShaking();
    }

    @Override
    public void render(DarkSkeletonEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
