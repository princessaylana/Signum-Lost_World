/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.world.dimension;

import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import za.lana.signum.Signum;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.item.ModItems;

import java.util.OptionalLong;

public class ModDimensions {

    public static final RegistryKey<DimensionOptions> LOSTWORLD_KEY =
            RegistryKey.of(RegistryKeys.DIMENSION,
            new Identifier(Signum.MOD_ID, "lost_world"));
    public static final RegistryKey<World> LOSTWORLD_LEVEL_KEY
            = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(Signum.MOD_ID, "lost_world"));
    public static final RegistryKey<DimensionType> LOSTWORLD_DIM_TYPE
            = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(Signum.MOD_ID, "lost_world_type"));

}