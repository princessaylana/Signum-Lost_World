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
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.PidgeonModel;
import za.lana.signum.entity.mob.PidgeonEntity;

public class PidgeonRenderer extends MobEntityRenderer<PidgeonEntity, PidgeonModel<PidgeonEntity>> {
private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/animal/pidgeon_texture.png");

    public PidgeonRenderer(EntityRendererFactory.Context context) {
        super(context, new PidgeonModel<>(context.getPart(ModModelLayers.PIDGEON)), 0.3f); //entity shadow
    }

    @Override
    public Identifier getTexture(PidgeonEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(PidgeonEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()){
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1.0f, 1.0f ,1.0f);
        }
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
    @Override
    public float getAnimationProgress(PidgeonEntity pidgeon, float f) {
        float g = MathHelper.lerp(f, pidgeon.prevFlapProgress, pidgeon.flapProgress);
        float h = MathHelper.lerp(f, pidgeon.prevMaxWingDeviation, pidgeon.maxWingDeviation);
        return (MathHelper.sin(g) + 1.0f) * h;
    }
}
