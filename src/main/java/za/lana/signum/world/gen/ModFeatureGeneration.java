package za.lana.signum.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.SeagrassFeature;
import static za.lana.signum.world.ModPlacedFeatures.MANGANESE_ORE_PLACED_KEY;

public class ModFeatureGeneration {
    public static void placeFeatures(){
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.valueOf(String.valueOf(SeagrassFeature.class)), MANGANESE_ORE_PLACED_KEY);
    }
}
