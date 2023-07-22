/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.gen.GenerationStep;
import za.lana.signum.world.ModPlacedFeatures;

import static za.lana.signum.world.ModPlacedFeatures.MANGANESE_ORE_PLACED_KEY;

public class ModOreGeneration {
    public static void generateOres() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES, MANGANESE_ORE_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.NETHERRACK_MANGANESE_ORE_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(),
                GenerationStep.Feature.UNDERGROUND_ORES, ModPlacedFeatures.ENDSTONE_MANGANESE_ORE_PLACED_KEY);

        //BiomeModifications.addFeature(BiomeSelectors.all(),
        //        GenerationStep.Feature.UNDERGROUND_ORES, MANGANESE_ORE_PLACED_KEY);
    }
}
