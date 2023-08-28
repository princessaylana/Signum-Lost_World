/**
 * SIGNUM
 * This is the main file, the entry point
 * MIT License
 * Lana
 * */

package za.lana.signum;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.block.custom.ExampleBlock;
import za.lana.signum.block.entity.ExampleBlockEntity;
import za.lana.signum.block.entity.ModBlockEntities;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.AirDroneEntity;
import za.lana.signum.entity.hostile.GhostEntity;
import za.lana.signum.entity.hostile.SigAlienEntity;
import za.lana.signum.entity.hostile.TiberiumWormEntity;
import za.lana.signum.entity.transport.AirBalloonEntity;
import za.lana.signum.entity.transport.SkyCarEntity;
import za.lana.signum.item.ModFuels;
import za.lana.signum.item.ModItemGroups;
import za.lana.signum.item.ModItems;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.recipe.ModRecipes;
import za.lana.signum.screen.ModScreenHandlers;
import za.lana.signum.screen.gui.ExampleDescription;
import za.lana.signum.sound.ModSounds;
import za.lana.signum.world.biomes.SignumBioKeys;
import za.lana.signum.world.dimension.ModDimensions;
import za.lana.signum.world.gen.ModWorldGeneration;

public class Signum implements ModInitializer {
	public static final String MOD_ID = "signum";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static BlockEntityType<ExampleBlockEntity> EXAMPLE_BLOCK_ENTITY;
	public static ScreenHandlerType<ExampleDescription> EXAMPLE_GUI;


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

		ModEffects.RegisterEffects();
		ModSounds.registerModSounds();

		FabricDefaultAttributeRegistry.register(ModEntities.TIBERIUM_WORM, TiberiumWormEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.GHOST, GhostEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.AIRDRONE, AirDroneEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.SIGALIEN, SigAlienEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.SKYCAR, SkyCarEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.AIRBALLOON, AirBalloonEntity.setAttributes());

		EXAMPLE_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(ExampleBlockEntity::new, ModBlocks.EXAMPLE_BLOCK).build(null);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "example_block_entity"), EXAMPLE_BLOCK_ENTITY);

		EXAMPLE_GUI = Registry.register(Registries.SCREEN_HANDLER, Signum.MOD_ID,
				new ScreenHandlerType<>((syncId, inventory) -> new ExampleDescription(syncId, inventory, ScreenHandlerContext.EMPTY),
						FeatureFlags.VANILLA_FEATURES));




		LOGGER.info("Signum Loaded");
	}
}
