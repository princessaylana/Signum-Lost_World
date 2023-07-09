package za.lana.signum;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.example.registry.BlockRegistry;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.block.custom.RazorWireBlock;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.item.ModItemGroups;
import za.lana.signum.item.ModItems;

public class Signum implements ModInitializer {
	public static final String MOD_ID = "signum";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();

		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();


		LOGGER.info("Signum Loaded");
	}
}