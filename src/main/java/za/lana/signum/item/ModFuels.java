package za.lana.signum.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.ItemGroups;
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
import za.lana.signum.recipe.ModRecipes;
import za.lana.signum.screen.ModScreenHandlers;
import za.lana.signum.world.biomes.SignumBioKeys;
import za.lana.signum.world.dimension.ModDimensions;
import za.lana.signum.world.gen.ModWorldGeneration;
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
