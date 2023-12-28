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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.ESpiderModel;
import za.lana.signum.client.renderer.feature.ESpiderEyesFeatureRenderer;
import za.lana.signum.entity.hostile.ESpiderEntity;

public class ESpiderRenderer extends MobEntityRenderer<ESpiderEntity, ESpiderModel<ESpiderEntity>> {
    private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/espider_texture.png");
    public ESpiderRenderer(EntityRendererFactory.Context context) {
        super(context, new ESpiderModel<>(context.getPart(ModModelLayers.ESPIDER)), 1.1f); //entity shadow
        this.addFeature(new ESpiderEyesFeatureRenderer<>(this));
    }
    @Override
    public Identifier getTexture(ESpiderEntity entity) {
        return TEXTURE;

    }
        @Override
    public void render(ESpiderEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
            if(mobEntity.isBaby()){
                matrixStack.scale(0.5f, 0.5f, 0.5f);
            } else {
                matrixStack.scale(1.0f, 1.0f ,1.0f);
            }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

}