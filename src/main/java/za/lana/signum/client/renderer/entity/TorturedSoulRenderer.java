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
import net.minecraft.util.math.BlockPos;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.TorturedSoulEntityModel;
import za.lana.signum.entity.hostile.TorturedSoulEntity;


public class TorturedSoulRenderer extends MobEntityRenderer<TorturedSoulEntity, TorturedSoulEntityModel<TorturedSoulEntity>> {
    public static final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/tortured_soul_texture.png");

    public TorturedSoulRenderer(EntityRendererFactory.Context context) {
        super(context, new TorturedSoulEntityModel<>(context.getPart(ModModelLayers.TORTURED_SOUL_ENTITY)), 0.0f); //entity shadow
    }

    @Override
    protected int getBlockLight(TorturedSoulEntity entity, BlockPos pos) {
        return 15;
    }

    @Override
    public Identifier getTexture(TorturedSoulEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(TorturedSoulEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.scale(0.75f, 0.75f, 0.75f);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
