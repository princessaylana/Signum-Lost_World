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
import za.lana.signum.client.model.DarkSkeletonModel;
import za.lana.signum.client.model.SumSkeletonModel;
import za.lana.signum.entity.hostile.DarkSkeletonEntity;
import za.lana.signum.entity.hostile.SumSkeletonEntity;

@Environment(value=EnvType.CLIENT)
public class SumSkeletonEyesFeatureRenderer<T extends SumSkeletonEntity>
        extends EyesFeatureRenderer<T, SumSkeletonModel<T>> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(Signum.MOD_ID, "textures/entity/hostile/skeletons/sumskeleton_eyes_texture.png"));

    public SumSkeletonEyesFeatureRenderer(FeatureRendererContext<T, SumSkeletonModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}

