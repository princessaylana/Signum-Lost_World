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

    public static void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "blue_dust_particle"),
                BlUE_DUST_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Signum.MOD_ID, "tiberium_particle"),
                TIBERIUM_PARTICLE);

        Signum.LOGGER.info("Registering ModParticles for " + Signum.MOD_ID);
    }
}
