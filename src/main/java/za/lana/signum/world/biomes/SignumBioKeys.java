/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.world.biomes;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import za.lana.signum.Signum;

public class SignumBioKeys extends BiomeKeys {

    public static final RegistryKey<Biome> FROZEN_LANDS = SignumBioKeys.register("frozen_lands");
    public static final RegistryKey<Biome> MAGIC_FOREST = SignumBioKeys.register("magic_forest");
    public static final RegistryKey<Biome> GOLDEN_KINGDOM = SignumBioKeys.register("golden_kingdom");
    public static final RegistryKey<Biome> BLACK_FOREST = SignumBioKeys.register("black_forest");
    public static final RegistryKey<Biome> TIBERIUM_WASTELAND = SignumBioKeys.register("tiberium_wasteland");
    public static final RegistryKey<Biome> RAINBOW_MUSHROOMS = SignumBioKeys.register("rainbow_mushrooms");
    public static final RegistryKey<Biome> DEATH_LANDS = SignumBioKeys.register("death_lands");

    //public static final RegistryKey<Biome> SIGNUM_SUNFLOWERS = SignumBioKeys.register("signum_sunflowers");

    private static RegistryKey<Biome> register(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(Signum.MOD_ID, name));
    }
    public static void registerModBiomes() {
        Signum.LOGGER.debug("Registering ModBiomes for " + Signum.MOD_ID);
    }

}
