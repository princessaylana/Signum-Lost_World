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
import za.lana.signum.client.model.CursedWolfEntityModel;
import za.lana.signum.entity.mob.CursedWolfEntity;

public class CursedWolfRenderer extends MobEntityRenderer<CursedWolfEntity, CursedWolfEntityModel<CursedWolfEntity>> {
private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/animal/cursed_wolf_texture.png");

    public CursedWolfRenderer(EntityRendererFactory.Context context) {
        super(context, new CursedWolfEntityModel<>(context.getPart(ModModelLayers.CURSED_WOLF)), 0.6f); //entity shadow
        //this.addFeature(new UnicornEyesFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(CursedWolfEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(CursedWolfEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()){
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1.0f, 1.0f ,1.0f);
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
