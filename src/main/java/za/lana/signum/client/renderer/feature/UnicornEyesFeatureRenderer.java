
package za.lana.signum.client.renderer.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.client.model.UnicornEntityModel;
import za.lana.signum.entity.mob.UnicornEntity;

@Environment(value=EnvType.CLIENT)
public class UnicornEyesFeatureRenderer<T extends UnicornEntity>
        extends EyesFeatureRenderer<T, UnicornEntityModel<T>> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(Signum.MOD_ID, "textures/entity/animal/unicorn_eyes.png"));

    public UnicornEyesFeatureRenderer(FeatureRendererContext<T, UnicornEntityModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}

