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
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.ElveEntityModel;
import za.lana.signum.client.model.TTrooperModel;
import za.lana.signum.entity.hostile.ElveEntity;
import za.lana.signum.entity.hostile.TTrooperEntity;

public class ElveEntityRenderer extends MobEntityRenderer<ElveEntity, ElveEntityModel<ElveEntity>> {
    private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/elve_texture.png");
    public ElveEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ElveEntityModel<>(context.getPart(ModModelLayers.ELVE_ENTITY)), 0.6f); //entity shadow
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
        //this.addFeature(new ArmorFeatureRenderer(this, new ElveEntityModel<>(context.getPart(ModModelLayers.ELVE_ENTITY_OUTER_ARMOR)),new ElveEntityModel<>(context.getPart(ModModelLayers.ELVE_ENTITY_INNER_ARMOR)), context.getModelManager()));


    }

    @Override
    public Identifier getTexture(ElveEntity entity) {
        return TEXTURE;
    }
    @Override
    public void render(ElveEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        //
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
