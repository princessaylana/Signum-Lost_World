/**
 * Registers the mod entities
 * SIGNUM
 * MIT License
 * Lana
 * */

package za.lana.signum.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.SpawnGroup;
import za.lana.signum.Signum;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.signum.entity.hostile.*;
import za.lana.signum.entity.projectile.*;
import za.lana.signum.entity.transport.AirBalloonEntity;
import za.lana.signum.entity.transport.SkyCarEntity;

public class ModEntities {
    //projectiles
    public static final EntityType<ToxicBallEntity> TOXICBALL = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Signum.MOD_ID, "toxicball"),
            FabricEntityTypeBuilder.<ToxicBallEntity>create(SpawnGroup.MISC, ToxicBallEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4)
                    .trackedUpdateRate(10).build());
    public static final EntityType<LaserBoltEntity> LASERBOLT = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Signum.MOD_ID, "laserbolt"),
            FabricEntityTypeBuilder.<LaserBoltEntity>create(SpawnGroup.MISC, LaserBoltEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4)
                    .trackedUpdateRate(10).build());
    //hostiles
    public static final EntityType<TiberiumWormEntity> TIBERIUM_WORM = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "tiberium_worm"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, TiberiumWormEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<TiberiumSkeletonEntity> TIBERIUM_SKELETON = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "tiberium_skeleton"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, TiberiumSkeletonEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.0f)).build());
    public static final EntityType<GhostEntity> GHOST = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "ghost"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, GhostEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.0f)).build());
    public static final EntityType<SigAlienEntity> SIGALIEN = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "sig_alien_worm"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SigAlienEntity::new)
                    .dimensions(EntityDimensions.fixed(1.8f, 1.8f)).build());
    public static final EntityType<AirDroneEntity> AIRDRONE= Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "airdrone"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, AirDroneEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.0f)).build());
    //vechicles
    public static final EntityType<SkyCarEntity> SKYCAR = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "skycar"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SkyCarEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.0f)).build());
    public static final EntityType<AirBalloonEntity> AIRBALLOON = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "airballoon"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, AirBalloonEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.0f)).build());
    public static final EntityType<TiberiumBoltEntity> TIBERIUM_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Signum.MOD_ID, "tiberium_projectile"),
            FabricEntityTypeBuilder.<TiberiumBoltEntity>create(SpawnGroup.MISC, TiberiumBoltEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4)
                    .trackedUpdateRate(10).build());
    public static final EntityType<IceBoltEntity> ICE_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Signum.MOD_ID, "ice_projectile"),
            FabricEntityTypeBuilder.<IceBoltEntity>create(SpawnGroup.MISC, IceBoltEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4)
                    .trackedUpdateRate(10).build());
    public static final EntityType<TransmuteBoltEntity> TRANSMUTE_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Signum.MOD_ID, "transmute_projectile"),
            FabricEntityTypeBuilder.<TransmuteBoltEntity>create(SpawnGroup.MISC, TransmuteBoltEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4)
                    .trackedUpdateRate(10).build());
    public static final EntityType<FireBoltEntity> FIRE_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Signum.MOD_ID, "fire_projectile"),
            FabricEntityTypeBuilder.<FireBoltEntity>create(SpawnGroup.MISC, FireBoltEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4)
                    .trackedUpdateRate(10).build());

    public static void registerModEntities() {
        Signum.LOGGER.info("Registering ModEntities for " + Signum.MOD_ID);
    }

    
}
