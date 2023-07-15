/**
 * SIGNUM
 * Register the Mod Items
 * MIT License
 * Lana
 *
 */

package za.lana.signum.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.item.custom.BPistolItem;
import za.lana.signum.item.custom.LaserBoltItem;
import za.lana.signum.item.custom.ToxicBallItem;
import za.lana.signum.item.custom.ToxicGunItem;

public class ModItems {
    public static final Item QUARTZ_CRYSTAL = registerItem("quartz_crystal", new Item(new FabricItemSettings()));
    public static final Item RAW_QUARTZ_CRYSTAL = registerItem("raw_quartz_crystal", new Item(new FabricItemSettings()));

    public static final Item RAW_MANGANESE = registerItem("raw_manganese", new Item(new FabricItemSettings()));
    public static final Item MANGANESE_INGOT = registerItem("manganese_ingot", new Item(new FabricItemSettings()));
    public static final Item MANGANESE_NUGGET = registerItem("manganese_nugget", new Item(new FabricItemSettings()));

    public static final Item TOXICBALL_ITEM = registerItem("toxicball_item", new ToxicBallItem(new FabricItemSettings()));
    public static final Item BPISTOL_ITEM = registerItem("bpistol_item", new BPistolItem(new FabricItemSettings()));
    public static final Item LASERBOLT_ITEM = registerItem("laserbolt_item", new LaserBoltItem(new FabricItemSettings()));
    public static final ToxicGunItem TOXICGUN = (ToxicGunItem) registerItem("toxicgun", new ToxicGunItem());




    public static void addItemsToIngredientGroup(FabricItemGroupEntries entries) {
        entries.add(QUARTZ_CRYSTAL);
        entries.add(RAW_QUARTZ_CRYSTAL);
        entries.add(TOXICBALL_ITEM);
        entries.add(BPISTOL_ITEM);
        entries.add(LASERBOLT_ITEM);
        entries.add(TOXICGUN);
        entries.add(MANGANESE_INGOT);
        entries.add(MANGANESE_NUGGET);

    }

    //add new items above
    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(Signum.MOD_ID, name), item);

    }
    public static void registerModItems(){
        Signum.LOGGER.info("Registering ModItems for " + Signum.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientGroup);
    }
}
