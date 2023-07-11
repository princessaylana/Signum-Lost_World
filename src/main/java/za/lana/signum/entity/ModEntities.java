/**
 * Registers the mod entities
 * SIGNUM
 * MIT License
 * Lana
 * */

package za.lana.signum.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnGroup;
import za.lana.signum.Signum;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.signum.entity.projectile.ToxicBallEntity;

public class ModEntities {
    public static final EntityType<ToxicBallEntity> TOXICBALL = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Signum.MOD_ID, "toxicball"),
            FabricEntityTypeBuilder.<ToxicBallEntity>create(SpawnGroup.MISC, ToxicBallEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4)
                    .trackedUpdateRate(10).build());
    

    public static void registerModEntities() {
        Signum.LOGGER.info("Registering Mod Entities for " + Signum.MOD_ID);
    }
    
}
