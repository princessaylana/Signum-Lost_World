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
import za.lana.signum.client.model.TiberiumFloaterEntityModel;
import za.lana.signum.client.renderer.feature.TiberiumFloaterEyeFeatureRenderer;
import za.lana.signum.entity.hostile.TiberiumFloaterEntity;

public class TiberiumFloaterEntityRenderer extends MobEntityRenderer<TiberiumFloaterEntity, TiberiumFloaterEntityModel<TiberiumFloaterEntity>> {
private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/tiberium/tiberium_floater_texture.png");

    public TiberiumFloaterEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TiberiumFloaterEntityModel<>(context.getPart(ModModelLayers.TIBERIUM_FLOATER)), 0.6f); //entity shadow
        this.addFeature(new TiberiumFloaterEyeFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(TiberiumFloaterEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(TiberiumFloaterEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()){
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1.0f, 1.0f ,1.0f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
