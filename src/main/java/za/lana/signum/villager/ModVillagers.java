package za.lana.signum.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import za.lana.signum.Signum;
import za.lana.signum.block.ModBlocks;

// ADD TO MODPOITAGPROVIDER AND DO DATAGEN
// ADD CUSTOM TRADES IN MODCUSTOMTRADES
public class ModVillagers {
    // ONLY ONE POI PER BLOCK TYPE

    // BAKER
    public static final RegistryKey<PointOfInterestType> BAKER_POI_KEY = poiKey("baker");
    public static final PointOfInterestType BAKER_POI = registerPoi("baker", ModBlocks.TORGUEBAKER_BLOCK);
    public static final VillagerProfession BAKER = registerZedProfession("baker", BAKER_POI_KEY);

    // BANKER
    public static final RegistryKey<PointOfInterestType> BANKER_POI_KEY = poiKey("banker");
    public static final PointOfInterestType BANKER_POI = registerPoi("banker", ModBlocks.BANKER_BLOCK);
    public static final VillagerProfession BANKER = registerZedProfession("banker", BANKER_POI_KEY);


    // DR ZED
    public static final RegistryKey<PointOfInterestType> ZED_VENDING_POI_KEY = poiKey("zed_vending");
    public static final PointOfInterestType ZED_VENDING_POI = registerPoi("zed_vending", ModBlocks.ZEDVENDING_BLOCK);
    public static final VillagerProfession DR_ZED = registerZedProfession("dr_zed", ZED_VENDING_POI_KEY);

    // REGISTER
    private static VillagerProfession registerZedProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, new Identifier(Signum.MOD_ID, name),
                new VillagerProfession(name, entry -> entry.matchesKey(type), entry -> entry.matchesKey(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_CLERIC));


    }

    // WORKSTATION
    // ticket:  amount of villagers that can use the station
    // search: how far away they have to be to get the profession
    private static PointOfInterestType registerPoi(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier(Signum.MOD_ID, name), 1, 1, block);
    }

    private static RegistryKey<PointOfInterestType> poiKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, new Identifier(Signum.MOD_ID, name));
    }

    public static void registerVillagers() {
        Signum.LOGGER.info("Registering Villagers " + Signum.MOD_ID);
    }
}
