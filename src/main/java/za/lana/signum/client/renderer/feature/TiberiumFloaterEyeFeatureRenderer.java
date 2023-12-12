/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.client.renderer.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.client.model.TiberiumFloaterEntityModel;
import za.lana.signum.client.model.UnicornEntityModel;
import za.lana.signum.entity.hostile.TiberiumFloaterEntity;
import za.lana.signum.entity.mob.UnicornEntity;

@Environment(value=EnvType.CLIENT)
public class TiberiumFloaterEyeFeatureRenderer<T extends TiberiumFloaterEntity>
        extends EyesFeatureRenderer<T, TiberiumFloaterEntityModel<T>> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(Signum.MOD_ID, "textures/entity/hostile/tiberium/tiberium_floater_glow_texture.png"));

    public TiberiumFloaterEyeFeatureRenderer(FeatureRendererContext<T, TiberiumFloaterEntityModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}

