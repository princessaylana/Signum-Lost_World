package za.lana.signum.client.renderer.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.TCommanderModel;
import za.lana.signum.entity.hostile.TCommanderEntity;

public class TCommanderRenderer extends MobEntityRenderer<TCommanderEntity, TCommanderModel<TCommanderEntity>> {
    private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/tiberium/tcommander_texture.png");
    public TCommanderRenderer(EntityRendererFactory.Context context) {
        super(context, new TCommanderModel<>(context.getPart(ModModelLayers.TCOMMANDER)), 0.6f); //entity shadow
    }
    @Override
    public Identifier getTexture(TCommanderEntity entity) {
        return TEXTURE;

    }
        @Override
    public void render(TCommanderEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        //
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

}