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
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.TiberiumFloaterEntity;
import za.lana.signum.world.biomes.SignumBioKeys;

public class ModEntityGeneration {
    public static void addSpawns() {

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.SIGNUM_MUSHROOMS, SignumBioKeys.SIGNUM_SUNFLOWERS), SpawnGroup.CREATURE,
                ModEntities.UNICORN, 5, 2, 6);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.SIGNUM_MANGROVE), SpawnGroup.MONSTER,
                ModEntities.TIBERIUM_WORM, 75, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.SIGNUM_MANGROVE), SpawnGroup.MONSTER,
                ModEntities.TIBERIUM_SKELETON, 50, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.SIGNUM_MANGROVE), SpawnGroup.MONSTER,
                ModEntities.TIBERIUM_FLOATER, 30, 1, 2);


        SpawnRestriction.register(ModEntities.UNICORN, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);

        SpawnRestriction.register(ModEntities.TIBERIUM_WORM, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnIgnoreLightLevel);
        SpawnRestriction.register(ModEntities.TIBERIUM_SKELETON, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnIgnoreLightLevel);
        SpawnRestriction.register(ModEntities.TIBERIUM_FLOATER, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TiberiumFloaterEntity::canSpawn);

    }
}