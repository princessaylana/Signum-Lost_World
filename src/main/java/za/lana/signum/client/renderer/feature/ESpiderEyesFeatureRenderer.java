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
import za.lana.signum.client.model.ESpiderModel;
import za.lana.signum.client.model.UnicornEntityModel;
import za.lana.signum.entity.hostile.ESpiderEntity;
import za.lana.signum.entity.mob.UnicornEntity;

@Environment(value=EnvType.CLIENT)
public class ESpiderEyesFeatureRenderer<T extends ESpiderEntity>
        extends EyesFeatureRenderer<T, ESpiderModel<T>> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(Signum.MOD_ID, "textures/entity/hostile/espider_eyes_texture.png"));

    public ESpiderEyesFeatureRenderer(FeatureRendererContext<T, ESpiderModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}

