/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.Heightmap;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.world.biomes.SignumBioKeys;

public class ModEntityGeneration {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.SIGNUM_MANGROVE), SpawnGroup.MISC,
                ModEntities.TIBERIUM_WORM, 35, 1, 2);

        SpawnRestriction.register(ModEntities.TIBERIUM_WORM, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnIgnoreLightLevel);
    }
}