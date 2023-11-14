/**
 * SIGNUM
 * This is the main file, the entry point
 * MIT License
 * Lana
 * */

package za.lana.signum;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.block.entity.ExampleBlockEntity;
import za.lana.signum.block.entity.ModBlockEntities;
import za.lana.signum.effect.ModEffects;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.*;
import za.lana.signum.entity.mob.UnicornEntity;
import za.lana.signum.entity.transport.AirBalloonEntity;
import za.lana.signum.entity.transport.SkyCarEntity;
import za.lana.signum.item.ModFuels;
import za.lana.signum.item.ModItemGroups;
import za.lana.signum.item.ModItems;
import za.lana.signum.networking.ModMessages;
import za.lana.signum.networking.packet.ABKeyInputC2SPacket;
import za.lana.signum.particle.ModParticles;
import za.lana.signum.recipe.ModRecipes;
import za.lana.signum.runinit.RunInitMain;
import za.lana.signum.screen.ModScreenHandlers;
import za.lana.signum.screen.gui.GuiScreens;
import za.lana.signum.sound.ModSounds;
import za.lana.signum.world.biomes.SignumBioKeys;
import za.lana.signum.world.dimension.ModDimensions;
import za.lana.signum.world.gen.ModWorldGeneration;

public class Signum implements ModInitializer {
	public static final String MOD_ID = "signum";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {
		RunInitMain.registerInits();

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();

		ModRecipes.registerRecipes();
		ModScreenHandlers.registerScreenHandler();

		ModWorldGeneration.generateModWorldGeneration();
		SignumBioKeys.registerModBiomes();

		ModEntities.registerModEntities();
		ModParticles.registerParticles();

		ModEffects.RegisterEffects();
		ModSounds.registerModSounds();

		ModMessages.registerC2SPackets();
		GuiScreens.registerGuiScreens();

		ABKeyInputC2SPacket.init();

		LOGGER.info("Signum Loaded");
	}
}
