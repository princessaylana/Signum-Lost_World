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
    public static final RegistryKey<Biome> SIGNUM_BADLANDS = SignumBioKeys.register("signum_badlands");
    public static final RegistryKey<Biome> SIGNUM_BAMBOO = SignumBioKeys.register("signum_bamboo");
    public static final RegistryKey<Biome> SIGNUM_BIRCH = SignumBioKeys.register("signum_birch");
    public static final RegistryKey<Biome> SIGNUM_CHERRY = SignumBioKeys.register("signum_cherry");
    public static final RegistryKey<Biome> SIGNUM_DARK = SignumBioKeys.register("signum_dark");
    public static final RegistryKey<Biome> SIGNUM_DESERT = SignumBioKeys.register("signum_desert");
    public static final RegistryKey<Biome> SIGNUM_FLOWERS = SignumBioKeys.register("signum_flowers");
    public static final RegistryKey<Biome> SIGNUM_FOREST = SignumBioKeys.register("signum_forest");
    public static final RegistryKey<Biome> SIGNUM_JUNGLE = SignumBioKeys.register("signum_jungle");
    public static final RegistryKey<Biome> SIGNUM_MANGROVE = SignumBioKeys.register("signum_mangrove");
    public static final RegistryKey<Biome> SIGNUM_MUSHROOMS = SignumBioKeys.register("signum_mushrooms");
    public static final RegistryKey<Biome> SIGNUM_PEAKS = SignumBioKeys.register("signum_peaks");
    public static final RegistryKey<Biome> SIGNUM_PLAINS = SignumBioKeys.register("signum_plains");
    public static final RegistryKey<Biome> SIGNUM_PLATEAU = SignumBioKeys.register("signum_plateau");
    public static final RegistryKey<Biome> SIGNUM_SAVANNA = SignumBioKeys.register("signum_savanna");
    public static final RegistryKey<Biome> SIGNUM_SNOWY = SignumBioKeys.register("signum_snowy");
    public static final RegistryKey<Biome> SIGNUM_SPIKES = SignumBioKeys.register("signum_spikes");
    public static final RegistryKey<Biome> SIGNUM_STONY = SignumBioKeys.register("signum_stony");
    public static final RegistryKey<Biome> SIGNUM_SUNFLOWERS = SignumBioKeys.register("signum_sunflowers");


    private static RegistryKey<Biome> register(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, new Identifier(Signum.MOD_ID, name));
    }
    public static void registerModBiomes() {
        Signum.LOGGER.debug("Registering ModBiomes for " + Signum.MOD_ID);
    }

}
