/**
 * SIGNUM
 * This is the DataGenerator EntryPoint
 * MIT License
 * Lana
 * */
package za.lana.signum;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import za.lana.signum.datagen.ModBlockLootGenerator;
import za.lana.signum.datagen.ModModelProvider;
import za.lana.signum.datagen.ModRecipesGenerator;
import za.lana.signum.datagen.ModWorldGenerator;

public class SignumDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModBlockLootGenerator::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipesGenerator::new);
		//pack.addProvider(ModWorldGenerator::new);

	}
}
