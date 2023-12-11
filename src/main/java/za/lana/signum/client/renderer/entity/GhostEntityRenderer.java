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
import za.lana.signum.client.model.GhostEntityModel;
import za.lana.signum.entity.hostile.GhostEntity;

public class GhostEntityRenderer extends MobEntityRenderer<GhostEntity, GhostEntityModel<GhostEntity>> {
    private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/ghost_texture.png");
    public GhostEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GhostEntityModel(context.getPart(ModModelLayers.GHOST_ENTITY)), 0.6f); //entity shadow
        //this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));

    }

    @Override
    public Identifier getTexture(GhostEntity entity) {
        return TEXTURE;
    }
    @Override
    public void render(GhostEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        //
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
