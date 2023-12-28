/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.client.renderer.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.IceSkeletonModel;
import za.lana.signum.client.renderer.feature.IceSkeletonEyesFeatureRenderer;
import za.lana.signum.entity.hostile.IceSkeletonEntity;

public class IceSkeletonRenderer extends MobEntityRenderer<IceSkeletonEntity, IceSkeletonModel<IceSkeletonEntity>> {
    public static final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/skeletons/iceskeleton_texture.png");
    public IceSkeletonRenderer(EntityRendererFactory.Context context) {
        super(context, new IceSkeletonModel<>(context.getPart(ModModelLayers.ICESKELETON)), 0.4f); //entity shadow
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
        this.addFeature(new IceSkeletonEyesFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(IceSkeletonEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(IceSkeletonEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        //
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean hasShape = minecraftClient.hasOutline(mobEntity) && mobEntity.isInvisible();
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

}
