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
import za.lana.signum.client.model.FireSkeletonModel;
import za.lana.signum.client.renderer.feature.DarkSkeletonEyesFeatureRenderer;
import za.lana.signum.client.renderer.feature.FireSkeletonEyesFeatureRenderer;
import za.lana.signum.entity.hostile.FireSkeletonEntity;

public class FireSkeletonRenderer extends MobEntityRenderer<FireSkeletonEntity, FireSkeletonModel<FireSkeletonEntity>> {
    private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/skeletons/fireskeleton_texture.png");
    public FireSkeletonRenderer(EntityRendererFactory.Context context) {
        super(context, new FireSkeletonModel<>(context.getPart(ModModelLayers.FIRESKELETON)), 0.4f); //entity shadow
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
        this.addFeature(new FireSkeletonEyesFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(FireSkeletonEntity entity) {
        return TEXTURE;
    }

    protected boolean isShaking(FireSkeletonEntity fireSkeleton) {
        return fireSkeleton.isShaking();
    }


    @Override
    public void render(FireSkeletonEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
