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
import za.lana.signum.client.model.FireSkeletonModel;
import za.lana.signum.entity.hostile.FireSkeletonEntity;

@Environment(value=EnvType.CLIENT)
public class FireSkeletonEyesFeatureRenderer<T extends FireSkeletonEntity>
        extends EyesFeatureRenderer<T, FireSkeletonModel<T>> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(Signum.MOD_ID, "textures/entity/hostile/skeletons/fireskeleton_eyes_texture.png"));

    public FireSkeletonEyesFeatureRenderer(FeatureRendererContext<T, FireSkeletonModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}

