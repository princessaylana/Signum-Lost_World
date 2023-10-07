/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;

public class ModParticles {
    public static final DefaultParticleType BlUE_DUST_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType TIBERIUM_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType FREEZE_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType TRANSMUTE_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType FLAME_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType TOXIC_SHROOM_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType BLACK_SHROOM_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType BLUE_SHROOM_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType BROWN_SHROOM_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType ORANGE_SHROOM_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType PINK_SHROOM_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType PURPLE_SHROOM_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType RED_SHROOM_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType WHITE_SHROOM_PARTICLE = FabricParticleTypes.simple();
    public static final DefaultParticleType YELLOW_SHROOM_PARTICLE = FabricParticleTypes.simple();
    public static void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "blue_dust_particle"),
                BlUE_DUST_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "tiberium_particle"),
                TIBERIUM_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "freeze_particle"),
                FREEZE_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "transmute_particle"),
                TRANSMUTE_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "flame_particle"),
                FLAME_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "toxic_shroom_particle"),
                TOXIC_SHROOM_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "black_shroom_particle"),
                BLACK_SHROOM_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "blue_shroom_particle"),
                BLUE_SHROOM_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "brown_shroom_particle"),
                BROWN_SHROOM_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "orange_shroom_particle"),
                ORANGE_SHROOM_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "pink_shroom_particle"),
                PINK_SHROOM_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "purple_shroom_particle"),
                PURPLE_SHROOM_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "red_shroom_particle"),
                RED_SHROOM_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "white_shroom_particle"),
                WHITE_SHROOM_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "yellow_shroom_particle"),
                YELLOW_SHROOM_PARTICLE);


        Signum.LOGGER.info("Registering ModParticles for " + Signum.MOD_ID);
    }
}
