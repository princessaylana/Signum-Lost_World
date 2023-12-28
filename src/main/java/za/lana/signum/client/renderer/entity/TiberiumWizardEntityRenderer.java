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
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.TiberiumWizardEntityModel;
import za.lana.signum.entity.hostile.TiberiumWizardEntity;

public class TiberiumWizardEntityRenderer extends MobEntityRenderer<TiberiumWizardEntity, TiberiumWizardEntityModel<TiberiumWizardEntity>> {
    private final Identifier TEXTURE = new Identifier(Signum.MOD_ID, "textures/entity/hostile/wizards/green_wizard_texture.png");
    public TiberiumWizardEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new TiberiumWizardEntityModel<>(context.getPart(ModModelLayers.TIBERIUM_WIZARD_ENTITY)), 0.6f); //entity shadow
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));

    }

    @Override
    public Identifier getTexture(TiberiumWizardEntity entity) {
        return TEXTURE;
    }
    @Override
    public void render(TiberiumWizardEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        //
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
