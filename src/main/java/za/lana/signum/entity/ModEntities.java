/**
 * Registers the mod entities
 * SIGNUM
 * MIT License
 * Lana
 * */

package za.lana.signum.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.World;
import software.bernie.example.entity.CoolKidEntity;
import software.bernie.geckolib.GeckoLib;
import za.lana.signum.Signum;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.signum.entity.hostile.AirDroneEntity;
import za.lana.signum.entity.hostile.SigAlienEntity;
import za.lana.signum.entity.hostile.TiberiumWormEntity;
import za.lana.signum.entity.projectile.LaserBoltEntity;
import za.lana.signum.entity.projectile.ToxicBallEntity;
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

    public static void registerModEntities() {
        Signum.LOGGER.info("Registering ModEntities for " + Signum.MOD_ID);
    }

    
}
