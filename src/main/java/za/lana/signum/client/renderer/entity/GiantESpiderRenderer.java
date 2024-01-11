/**
 * SIGNUM
 * MIT License
 * Lana
 * 2024
 * */
package za.lana.signum.client.renderer.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.GiantESpiderModel;
import za.lana.signum.client.renderer.feature.ESpiderEyesFeatureRenderer;
import za.lana.signum.client.renderer.feature.GiantESpiderEyesFeatureRenderer;
import za.lana.signum.entity.hostile.GiantESpiderEntity;

public class GiantESpiderRenderer extends MobEntityRenderer<GiantESpiderEntity, GiantESpiderModel<GiantESpiderEntity>> {
    private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/espider_texture.png");
    // normal espider is: 2.0f, 1.3f
    // 1.0f * 7.8f = 7.8f

    private final float scale = GiantESpiderEntity.SCALED;
    public GiantESpiderRenderer(EntityRendererFactory.Context context) {
        super(context, new GiantESpiderModel<>(context.getPart(ModModelLayers.GIANTESPIDER)), 2.5f); //entity shadow
        this.addFeature(new GiantESpiderEyesFeatureRenderer<>(this));
        //(context, 6.0f. + 0.5F * scale)
        //this.scale = scale;
    }
    @Override
    public Identifier getTexture(GiantESpiderEntity entity) {
        return TEXTURE;

    }
    protected void scale(GiantESpiderEntity giantESpider, MatrixStack matrixStack, float f) {
        matrixStack.scale(this.scale, this.scale, this.scale);
    }
    @Override
    public void render(GiantESpiderEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
            if(mobEntity.isBaby()){
                //matrixStack.scale(0.5f, 0.5f, 0.5f);
                matrixStack.scale(this.scale/2, this.scale/2, this.scale/2);
            } else {
                //matrixStack.scale(1.0f, 1.0f ,1.0f);
                matrixStack.scale(this.scale, this.scale, this.scale);
            }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

}