package za.lana.signum.client.renderer.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.TTrooperModel;
import za.lana.signum.entity.hostile.TTrooperEntity;

public class TTrooperRenderer extends MobEntityRenderer<TTrooperEntity, TTrooperModel<TTrooperEntity>> {
    private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/ttrooper_texture.png");
    public TTrooperRenderer(EntityRendererFactory.Context context) {
        super(context, new TTrooperModel<>(context.getPart(ModModelLayers.TTROOPER)), 0.6f); //entity shadow
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(TTrooperEntity entity) {
        return TEXTURE;
    }
    @Override
    public void render(TTrooperEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        //
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
