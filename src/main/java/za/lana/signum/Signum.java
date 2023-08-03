/**
 * SIGNUM
 * This is the main file, the entry point
 * MIT License
 * Lana
 * */

package za.lana.signum;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.example.registry.EntityRegistry;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.block.entity.ModBlockEntities;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.AirDroneEntity;
import za.lana.signum.entity.hostile.SigAlienEntity;
import za.lana.signum.entity.hostile.TiberiumWormEntity;
import za.lana.signum.entity.transport.SkyCarEntity;
import za.lana.signum.item.ModFuels;
import za.lana.signum.item.ModItemGroups;
import za.lana.signum.item.ModItems;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.recipe.ModRecipes;
import za.lana.signum.screen.ModScreenHandlers;
import za.lana.signum.world.biomes.SignumBioKeys;
import za.lana.signum.world.dimension.ModDimensions;
import za.lana.signum.world.gen.ModWorldGeneration;

public class Signum implements ModInitializer {
	public static final String MOD_ID = "signum";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();

		ModRecipes.registerRecipes();
		ModScreenHandlers.registerScreenHandler();

		ModDimensions.register();
		ModWorldGeneration.generateModWorldGeneration();

		SignumBioKeys.registerModBiomes();
		ModEntities.registerModEntities();

		ModFuels.registerModFuels();
		ModParticles.registerParticles();

		FabricDefaultAttributeRegistry.register(ModEntities.TIBERIUM_WORM, TiberiumWormEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.AIRDRONE, AirDroneEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.SIGALIEN, SigAlienEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.SKYCAR, SkyCarEntity.setAttributes());


		LOGGER.info("Signum Loaded");
	}
}
