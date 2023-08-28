/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.world.dimension;

import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionOptions;
import za.lana.signum.Signum;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.item.ModItems;

public class ModDimensions {
    public static final String PATH = "signum_dim";
    public static final RegistryKey<DimensionOptions> DIMENSION_KEY =
            RegistryKey.of(RegistryKeys.DIMENSION, new Identifier(Signum.MOD_ID, PATH));

    public static RegistryKey<World> WORLD_KEY =
            RegistryKey.of(RegistryKeys.WORLD, DIMENSION_KEY.getValue());

    //.setReturnPortalSearchYRange(256, 320)

    public static void register() {
        WORLD_KEY = RegistryKey.of(RegistryKeys.WORLD, new Identifier(Signum.MOD_ID, PATH));
        Signum.LOGGER.debug("Registering ModDimensions for " + Signum.MOD_ID);
        CustomPortalBuilder.beginPortal()
                .setPortalSearchYRange(256, 320)
                .frameBlock(ModBlocks.MANGANESE_BLOCK)
                .destDimID(WORLD_KEY.getValue())
                .tintColor(240, 255, 240)
                .lightWithItem(ModItems.QUARTZ_CRYSTAL)
                .registerPortal();
    }
}