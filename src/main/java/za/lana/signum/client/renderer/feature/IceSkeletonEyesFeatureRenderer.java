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
import za.lana.signum.client.model.IceSkeletonModel;
import za.lana.signum.entity.hostile.FireSkeletonEntity;
import za.lana.signum.entity.hostile.IceSkeletonEntity;

@Environment(value=EnvType.CLIENT)
public class IceSkeletonEyesFeatureRenderer<T extends IceSkeletonEntity>
        extends EyesFeatureRenderer<T, IceSkeletonModel<T>> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(Signum.MOD_ID, "textures/entity/hostile/skeletons/iceskeleton_eyes_texture.png"));

    public IceSkeletonEyesFeatureRenderer(FeatureRendererContext<T, IceSkeletonModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}

