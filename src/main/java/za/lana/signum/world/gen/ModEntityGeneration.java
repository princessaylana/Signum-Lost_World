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
import net.minecraft.world.Heightmap;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.*;
import za.lana.signum.entity.mob.UnicornEntity;
import za.lana.signum.world.biomes.SignumBioKeys;

public class ModEntityGeneration {
    public static void addSpawns() {

        // SIGNUM PLAINS
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.SIGNUM_PLAINS, SignumBioKeys.SIGNUM_FLOWERS), SpawnGroup.CREATURE,
                ModEntities.UNICORN, 5, 2, 6);

        // TIBERIUM WASTELAND
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.SIGNUM_MANGROVE), SpawnGroup.MONSTER,
                ModEntities.TIBERIUM_WORM, 75, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.SIGNUM_MANGROVE), SpawnGroup.MONSTER,
                ModEntities.TIBERIUM_SKELETON, 50, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.SIGNUM_MANGROVE), SpawnGroup.MONSTER,
                ModEntities.TTROOPER_ENTITY, 25, 2, 4);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.SIGNUM_MANGROVE), SpawnGroup.MONSTER,
                ModEntities.TCOMMANDER_ENTITY, 15, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.SIGNUM_MANGROVE), SpawnGroup.MONSTER,
                ModEntities.TIBERIUM_FLOATER, 10, 1, 2);
        // BLACK FOREST
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.SIGNUM_DARK), SpawnGroup.MONSTER,
                ModEntities.ESPIDER_ENTITY, 75, 2, 6);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.SIGNUM_DARK), SpawnGroup.MONSTER,
                ModEntities.GHOST, 50, 2, 4);

        // SPAWN RESTRICTIONS
        // SIGNUM PLAINS
        SpawnRestriction.register(ModEntities.UNICORN, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, UnicornEntity::isValidNaturalSpawn);

        // TIBERIUM WASTELAND
        SpawnRestriction.register(ModEntities.TIBERIUM_WORM, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TiberiumWormEntity::canSpawnInDark);
        SpawnRestriction.register(ModEntities.TIBERIUM_SKELETON, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TiberiumSkeletonEntity::canSpawnInDark);
        SpawnRestriction.register(ModEntities.TTROOPER_ENTITY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TTrooperEntity::canSpawnIgnoreLightLevel);
        SpawnRestriction.register(ModEntities.TCOMMANDER_ENTITY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TCommanderEntity::canSpawnIgnoreLightLevel);
        SpawnRestriction.register(ModEntities.TIBERIUM_FLOATER, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TiberiumFloaterEntity::canSpawn);

        // BLACK FOREST
        SpawnRestriction.register(ModEntities.GHOST, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GhostEntity::canSpawnInDark);
        SpawnRestriction.register(ModEntities.ESPIDER_ENTITY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ESpiderEntity::canSpawnIgnoreLightLevel);

    }
}