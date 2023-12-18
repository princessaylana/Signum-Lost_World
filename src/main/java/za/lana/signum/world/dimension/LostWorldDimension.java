/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.world.dimension;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import za.lana.signum.Signum;

public class LostWorldDimension implements ModInitializer {

    public static final RegistryKey<DimensionOptions> LOSTWORLD_KEY =
            RegistryKey.of(RegistryKeys.DIMENSION,
            new Identifier(Signum.MOD_ID, "lost_world"));
    public static RegistryKey<World> LOSTWORLD_LEVEL_KEY
            = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(Signum.MOD_ID, "lost_world"));
    public static final RegistryKey<DimensionType> LOSTWORLD_DIM_TYPE
            = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(Signum.MOD_ID, "lost_world_type"));

    @Override
    public void onInitialize() {
        LOSTWORLD_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD, new Identifier(Signum.MOD_ID, "lost world"));
    }
}