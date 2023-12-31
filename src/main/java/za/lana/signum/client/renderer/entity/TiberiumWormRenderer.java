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
import za.lana.signum.client.model.TiberiumWormEntityModel;
import za.lana.signum.entity.hostile.TiberiumWormEntity;

public class TiberiumWormRenderer extends MobEntityRenderer<TiberiumWormEntity, TiberiumWormEntityModel<TiberiumWormEntity>> {
private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/tiberium/tiberium_worm_texture.png");

    public TiberiumWormRenderer(EntityRendererFactory.Context context) {
        super(context, new TiberiumWormEntityModel<>(context.getPart(ModModelLayers.TIBERIUM_WORM)), 0.1f); //entity shadow
    }

    @Override
    public Identifier getTexture(TiberiumWormEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(TiberiumWormEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()){
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1.0f, 1.0f ,1.0f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);

    }
}
