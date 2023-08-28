/**
 * SIGNUM
 * This is the DataGenerator EntryPoint
 * Always first make a backup of the genetrated and the original assets
 * and data folder before this is run.
 * datagen breaks geo models and some worldgen.
 * MIT License
 * Lana
 * */
package za.lana.signum;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import za.lana.signum.datagen.*;
import za.lana.signum.world.ModConfiguredFeatures;
import za.lana.signum.world.ModPlacedFeatures;

public class SignumDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModLootTableProvider::new);

		// disabled below
		//pack.addProvider(ModModelProvider::new);
		//pack.addProvider(ModRecipeProvider::new);
		//pack.addProvider(ModWorldGenerator::new);

	}

	/**
	 * DATAGEN KEEPS FAILING IF THIS IS ENABLED
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
	***/
}
