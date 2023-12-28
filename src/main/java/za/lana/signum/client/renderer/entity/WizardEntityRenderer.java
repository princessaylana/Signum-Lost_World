/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.client.renderer.entity;

import com.google.common.collect.Maps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import za.lana.signum.Signum;
import za.lana.signum.client.layer.ModModelLayers;
import za.lana.signum.client.model.WizardEntityModel;
import za.lana.signum.entity.hostile.WizardEntity;
import za.lana.signum.entity.variant.WizardEntityVariant;

import java.util.Map;

public class WizardEntityRenderer extends MobEntityRenderer<WizardEntity, WizardEntityModel<WizardEntity>> {
    private static final Map<WizardEntityVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(WizardEntityVariant.class), map -> {
                map.put(WizardEntityVariant.DEFAULT,
                        new Identifier(Signum.MOD_ID, "textures/entity/hostile/wizards/orange_wizard_texture.png"));
                map.put(WizardEntityVariant.BLACK,
                        new Identifier(Signum.MOD_ID, "textures/entity/hostile/wizards/black_wizard_texture.png"));
                map.put(WizardEntityVariant.BLUE,
                        new Identifier(Signum.MOD_ID, "textures/entity/hostile/wizards/blue_wizard_texture.png"));
                map.put(WizardEntityVariant.CYAN,
                        new Identifier(Signum.MOD_ID, "textures/entity/hostile/wizards/cyan_wizard_texture.png"));
                map.put(WizardEntityVariant.PINK,
                        new Identifier(Signum.MOD_ID, "textures/entity/hostile/wizards/pink_wizard_texture.png"));
                map.put(WizardEntityVariant.PURPLE,
                        new Identifier(Signum.MOD_ID, "textures/entity/hostile/wizards/purple_wizard_texture.png"));
                map.put(WizardEntityVariant.RED,
                        new Identifier(Signum.MOD_ID, "textures/entity/hostile/wizards/red_wizard_texture.png"));
                map.put(WizardEntityVariant.WHITE,
                        new Identifier(Signum.MOD_ID, "textures/entity/hostile/wizards/white_wizard_texture.png"));
                map.put(WizardEntityVariant.YELLOW,
                        new Identifier(Signum.MOD_ID, "textures/entity/hostile/wizards/yellow_wizard_texture.png"));
            });

    public WizardEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new WizardEntityModel<>(context.getPart(ModModelLayers.WIZARD_ENTITY)), 0.6f); //entity shadow
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));

    }

    @Override
    public Identifier getTexture(WizardEntity entity) {
        return LOCATION_BY_VARIANT.get(entity.getVariant());
    }
    @Override
    public void render(WizardEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        //
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
