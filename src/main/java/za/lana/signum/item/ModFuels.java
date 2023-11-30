/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.item;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import za.lana.signum.Signum;

public class ModFuels {

    public static void registerModFuels() {
        Signum.LOGGER.info("Registering ModFuels for " + Signum.MOD_ID);
        FuelRegistry registry = FuelRegistry.INSTANCE;

        // for reference, a bucket of lava would be 20K ticks or 1000 seconds
        registry.add(ModItems.COKECOAL, 15000);
        registry.add(ModItems.TIBERIUMCOAL, 20000);
        registry.add(ModItems.ELEMENTZEROCOAL, 30000);

    }
}
