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
import za.lana.signum.entity.mob.CursedWolfEntity;
import za.lana.signum.entity.mob.PidgeonEntity;
import za.lana.signum.entity.mob.UnicornEntity;
import za.lana.signum.world.biomes.SignumBioKeys;

public class ModEntityGeneration {
    public static void addSpawns() {
        // SORTED BY BIOMES
        //
        // BLACK FOREST BIOME
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.BLACK_FOREST), SpawnGroup.CREATURE,
                ModEntities.CURSED_WOLF, 50, 2, 4);
        SpawnRestriction.register(ModEntities.CURSED_WOLF, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CursedWolfEntity::isValidNaturalSpawn);
        //
        // DEATH LANDS BIOME
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.DEATH_LANDS), SpawnGroup.MONSTER,
                ModEntities.ESPIDER_ENTITY, 50, 2, 4);
        SpawnRestriction.register(ModEntities.ESPIDER_ENTITY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ESpiderEntity::canSpawnIgnoreLightLevel);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.DEATH_LANDS), SpawnGroup.MONSTER,
                ModEntities.GHOST, 50, 2, 4);
        SpawnRestriction.register(ModEntities.GHOST, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GhostEntity::canSpawnInDark);
        // SKELETONS
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.TIBERIUM_WASTELAND), SpawnGroup.MONSTER,
                ModEntities.TIBSKELETON_ENTITY, 50, 2, 4);
        SpawnRestriction.register(ModEntities.TIBSKELETON_ENTITY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TibSkeletonEntity::canSpawnInDark);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.FROZEN_LANDS), SpawnGroup.MONSTER,
                ModEntities.ICESKELETON_ENTITY, 50, 2, 4);
        SpawnRestriction.register(ModEntities.ICESKELETON_ENTITY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IceSkeletonEntity::canSpawnInDark);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.DEATH_LANDS), SpawnGroup.MONSTER,
                ModEntities.DARKSKELETON_ENTITY, 50, 2, 4);
        SpawnRestriction.register(ModEntities.DARKSKELETON_ENTITY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DarkSkeletonEntity::canSpawnInDark);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.DEATH_LANDS), SpawnGroup.MONSTER,
                ModEntities.FIRESKELETON_ENTITY, 50, 2, 4);
        SpawnRestriction.register(ModEntities.FIRESKELETON_ENTITY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FireSkeletonEntity::canSpawnInDark);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.DEATH_LANDS), SpawnGroup.MONSTER,
                ModEntities.ENDERSKELETON_ENTITY, 50, 2, 4);
        SpawnRestriction.register(ModEntities.ENDERSKELETON_ENTITY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EnderSkeletonEntity::canSpawnInDark);

        // FROZEN LANDS
        //

        // MAGIC FOREST
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.MAGIC_FOREST), SpawnGroup.CREATURE,
                ModEntities.PIDGEON, 50, 2, 4);
        SpawnRestriction.register(ModEntities.PIDGEON, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PidgeonEntity::isValidNaturalSpawn);

        // RAINBOW MUSHROOMS BIOME
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.RAINBOW_MUSHROOMS), SpawnGroup.CREATURE,
                ModEntities.UNICORN, 50, 2, 4);
        SpawnRestriction.register(ModEntities.UNICORN, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, UnicornEntity::isValidNaturalSpawn);

        // GOLDEN KINGDOM BIOME
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.GOLDEN_KINGDOM), SpawnGroup.MONSTER,
                ModEntities.ELVE_GUARD_ENTITY, 50, 2, 4);
        SpawnRestriction.register(ModEntities.ELVE_GUARD_ENTITY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ElveGuardEntity::canSpawnIgnoreLightLevel);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.GOLDEN_KINGDOM), SpawnGroup.MONSTER,
                ModEntities.WIZARD_ENTITY, 50, 2, 4);
        SpawnRestriction.register(ModEntities.WIZARD_ENTITY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WizardEntity::canSpawnIgnoreLightLevel);

        // TIBERIUM WASTELAND BIOME
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.TIBERIUM_WASTELAND), SpawnGroup.MONSTER,
                ModEntities.TIBERIUM_WORM, 50, 2, 4);
        SpawnRestriction.register(ModEntities.TIBERIUM_WORM, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TiberiumWormEntity::canSpawnInDark);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.TIBERIUM_WASTELAND), SpawnGroup.MONSTER,
                ModEntities.TTROOPER_ENTITY, 50, 2, 4);
        SpawnRestriction.register(ModEntities.TTROOPER_ENTITY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TTrooperEntity::canSpawnIgnoreLightLevel);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.TIBERIUM_WASTELAND), SpawnGroup.MONSTER,
                ModEntities.TIBERIUM_WIZARD_ENTITY, 50, 2, 4);
        SpawnRestriction.register(ModEntities.TIBERIUM_WIZARD_ENTITY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TiberiumWizardEntity::canSpawnIgnoreLightLevel);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(SignumBioKeys.TIBERIUM_WASTELAND), SpawnGroup.MONSTER,
                ModEntities.TIBERIUM_FLOATER, 10, 1, 2);
        SpawnRestriction.register(ModEntities.TIBERIUM_FLOATER, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TiberiumFloaterEntity::canSpawn);
    }
}