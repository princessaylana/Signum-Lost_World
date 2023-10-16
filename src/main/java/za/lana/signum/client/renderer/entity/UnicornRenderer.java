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
import net.minecraft.client.render.entity.feature.SpiderEyesFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.UnicornEntityModel;
import za.lana.signum.client.renderer.feature.UnicornEyesFeatureRenderer;
import za.lana.signum.entity.mob.UnicornEntity;

public class UnicornRenderer extends MobEntityRenderer<UnicornEntity, UnicornEntityModel<UnicornEntity>> {
private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/animal/unicorn_texture.png");

    public UnicornRenderer(EntityRendererFactory.Context context) {
        super(context, new UnicornEntityModel<>(context.getPart(ModModelLayers.UNICORN)), 0.6f); //entity shadow
        this.addFeature(new UnicornEyesFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(UnicornEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(UnicornEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()){
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1.0f, 1.0f ,1.0f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
