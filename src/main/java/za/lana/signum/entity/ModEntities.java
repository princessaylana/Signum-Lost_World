/**
 * SIGNUM
 * MIT License
 * 2023
 * Lana
 * */

package za.lana.signum.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.GiantEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.entity.hostile.*;
import za.lana.signum.entity.mob.CursedWolfEntity;
import za.lana.signum.entity.mob.PidgeonEntity;
import za.lana.signum.entity.mob.UnicornEntity;
import za.lana.signum.entity.projectile.*;
import za.lana.signum.entity.transport.AirBalloonEntity;
import za.lana.signum.entity.transport.AirShipEntity;
import za.lana.signum.entity.transport.CargoDroneEntity;

public class ModEntities {
    public static final EntityType<AirBalloonEntity> AIRBALLOON = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "airballoon"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, AirBalloonEntity::new)
                    .dimensions(EntityDimensions.fixed(3.0f, 3.0f)).build());
    public static final EntityType<AirShipEntity> AIRSHIP = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "airship"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, AirShipEntity::new)
                    .dimensions(EntityDimensions.fixed(3.0f, 3.0f)).build());
    public static final EntityType<CargoDroneEntity> CARGODRONE = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "cargodrone"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, CargoDroneEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());

    public static final EntityType<UnicornEntity> UNICORN = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "unicorn"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, UnicornEntity::new)
                    .dimensions(EntityDimensions.fixed(1.5f, 1.5f)).build());
    public static final EntityType<CursedWolfEntity> CURSED_WOLF = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "cursed_wolf"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CursedWolfEntity::new)
                    .dimensions(EntityDimensions.fixed(1.2f, 1.2f)).build());

    public static final EntityType<PidgeonEntity> PIDGEON = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "pidgeon"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PidgeonEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.0f)).build());
    public static final EntityType<FallenEntity> FALLEN_ENTITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "fallen_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FallenEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.9f)).build());
    public static final EntityType<ESpiderEntity> ESPIDER_ENTITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "espider_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ESpiderEntity::new)
                    .dimensions(EntityDimensions.fixed(2.0f, 1.3f)).build());
    //mutliply box by 3.0: Giant Scaled 3.0f
    public static final EntityType<GiantESpiderEntity> GIANTESPIDER_ENTITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "giantespider_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GiantESpiderEntity::new)
                    .dimensions(EntityDimensions.fixed(2.0f * GiantESpiderEntity.SCALED, 1.3f * GiantESpiderEntity.SCALED)).build());
    public static final EntityType<ElveGuardEntity> ELVE_GUARD_ENTITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "elve_guard_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ElveGuardEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.9f)).build());
    public static final EntityType<WizardEntity> WIZARD_ENTITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "wizard_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WizardEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.9f)).build());
    public static final EntityType<SpellBoltEntity> SPELLBOLT_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Signum.MOD_ID, "spellbolt_entity"),
            FabricEntityTypeBuilder.<SpellBoltEntity>create(SpawnGroup.MISC, SpellBoltEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4)
                    .trackedUpdateRate(10).build());
    public static final EntityType<TTrooperEntity> TTROOPER_ENTITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "ttrooper_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TTrooperEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.9f)).build());
    public static final EntityType<TiberiumWizardEntity> TIBERIUM_WIZARD_ENTITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "tiberium_wizard_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TiberiumWizardEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.9f)).build());
    public static final EntityType<PoisonBoltEntity> POISONBOLT_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Signum.MOD_ID, "poisonbolt_entity"),
            FabricEntityTypeBuilder.<PoisonBoltEntity>create(SpawnGroup.MISC, PoisonBoltEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4)
                    .trackedUpdateRate(10).build());

    public static final EntityType<TiberiumFloaterEntity> TIBERIUM_FLOATER = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "tiberium_floater"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TiberiumFloaterEntity::new)
                    .dimensions(EntityDimensions.fixed(2.0f, 3.0f)).build());
    public static final EntityType<TiberiumWormEntity> TIBERIUM_WORM = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "tiberium_worm"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TiberiumWormEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<GhostEntity> GHOST = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "ghost"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GhostEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 2.0f)).build());
    // SKELETONS
    public static final EntityType<TibSkeletonEntity> TIBSKELETON_ENTITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "tibskeleton_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TibSkeletonEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.9f)).build());
    public static final EntityType<IceSkeletonEntity> ICESKELETON_ENTITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "iceskeleton_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, IceSkeletonEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.9f)).build());
    public static final EntityType<DarkSkeletonEntity> DARKSKELETON_ENTITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "darkskeleton_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, DarkSkeletonEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.9f)).build());
    public static final EntityType<EnderSkeletonEntity> ENDERSKELETON_ENTITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "enderskeleton_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EnderSkeletonEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.9f)).build());
    public static final EntityType<FireSkeletonEntity> FIRESKELETON_ENTITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "fireskeleton_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FireSkeletonEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.9f)).build());
    public static final EntityType<SumSkeletonEntity> SSKELETON_ENTITY = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(Signum.MOD_ID, "sskeleton_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SumSkeletonEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.9f)).build());
    // PROJECTILES
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
    public static final EntityType<ShockBoltEntity> SHOCK_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Signum.MOD_ID, "shock_projectile"),
            FabricEntityTypeBuilder.<ShockBoltEntity>create(SpawnGroup.MISC, ShockBoltEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4)
                    .trackedUpdateRate(10).build());
    public static final EntityType<GravityBoltEntity> GRAVITY_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Signum.MOD_ID, "gravity_projectile"),
            FabricEntityTypeBuilder.<GravityBoltEntity>create(SpawnGroup.MISC, GravityBoltEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4)
                    .trackedUpdateRate(10).build());

    public static final EntityType<SpiderSpitEntity> SPIDERSPIT_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Signum.MOD_ID, "spiderspit_projectile"),
            FabricEntityTypeBuilder.<SpiderSpitEntity>create(SpawnGroup.MISC, SpiderSpitEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4)
                    .trackedUpdateRate(10).build());
    public static final EntityType<TiberiumSpitEntity> TIBERIUMSPIT_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Signum.MOD_ID, "tiberiumspit_projectile"),
            FabricEntityTypeBuilder.<TiberiumSpitEntity>create(SpawnGroup.MISC, TiberiumSpitEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4)
                    .trackedUpdateRate(10).build());

    public static void registerModEntities() {
        Signum.LOGGER.info("Registering ModEntities for " + Signum.MOD_ID);
    }

}
