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
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.entity.hostile.ESpiderEntity;
import za.lana.signum.item.custom.*;

public class ModItems {

    public static final Item RAW_ELEMENT_ZERO = registerItem("raw_element_zero", new Item(new FabricItemSettings()));
    public static final Item RAW_QUARTZ_CRYSTAL = registerItem("raw_quartz_crystal", new Item(new FabricItemSettings()));
    public static final Item RAW_MANGANESE = registerItem("raw_manganese", new Item(new FabricItemSettings()));
    public static final Item RAW_MOISSANITE = registerItem("raw_moissanite", new Item(new FabricItemSettings()));

    public static final Item COKECOAL = registerItem("cokecoal", new Item(new FabricItemSettings()));
    public static final Item TIBERIUMCOAL = registerItem("tiberiumcoal", new Item(new FabricItemSettings()));
    public static final Item ELEMENTZEROCOAL = registerItem("elementzerocoal", new Item(new FabricItemSettings()));

    public static final Item TIBERIUM_SHARD = registerItem("tiberium_shard", new Item(new FabricItemSettings()));
    public static final Item FIRE_CRYSTAL_SHARD = registerItem("fire_crystal_shard", new Item(new FabricItemSettings()));
    public static final Item ICE_CRYSTAL_SHARD = registerItem("ice_crystal_shard", new Item(new FabricItemSettings()));
    public static final Item EXOTIC_CRYSTAL_SHARD = registerItem("exotic_crystal_shard", new Item(new FabricItemSettings()));
    public static final Item QUARTZ_CRYSTAL_SHARD = registerItem("quartz_crystal_shard", new Item(new FabricItemSettings()));
    public static final Item GLASS_SHARD = registerItem("glass_shard", new Item(new FabricItemSettings()));

    public static final Item MANGANESE_INGOT = registerItem("manganese_ingot", new Item(new FabricItemSettings()));
    public static final Item SIGSTEEL_INGOT = registerItem("sigsteel_ingot", new Item(new FabricItemSettings()));
    public static final Item SIGSTAINSTEEL_INGOT = registerItem("sigstainsteel_ingot", new Item(new FabricItemSettings()));
    public static final Item MANGANESE_NUGGET = registerItem("manganese_nugget", new Item(new FabricItemSettings()));

    public static final Item MOISSANITE = registerItem("moissanite", new Item(new FabricItemSettings()));
    public static final Item ELEMENT_ZERO = registerItem("element_zero", new Item(new FabricItemSettings()));

    public static final Item TIBERIUM_CRYSTAL = registerItem("tiberium_crystal", new Item(new FabricItemSettings()));
    public static final Item FIRE_CRYSTAL = registerItem("fire_crystal", new Item(new FabricItemSettings()));
    public static final Item ICE_CRYSTAL = registerItem("ice_crystal", new Item(new FabricItemSettings()));
    public static final Item EXOTIC_CRYSTAL = registerItem("exotic_crystal", new Item(new FabricItemSettings()));
    public static final Item QUARTZ_CRYSTAL = registerItem("quartz_crystal", new Item(new FabricItemSettings()));
    public static final Item MOISSANITE_CRYSTAL = registerItem("moissanite_crystal", new Item(new FabricItemSettings()));
    public static final Item ELEMENT_ZERO_CRYSTAL = registerItem("element_zero_crystal", new Item(new FabricItemSettings()));

    public static final Item MANGANESE_DUST = registerItem("manganese_dust", new Item(new FabricItemSettings()));
    public static final Item TIBERIUM_DUST = registerItem("tiberium_dust", new Item(new FabricItemSettings()));
    public static final Item FIRE_CRYSTAL_DUST = registerItem("fire_crystal_dust", new Item(new FabricItemSettings()));
    public static final Item EXOTIC_CRYSTAL_DUST = registerItem("exotic_crystal_dust", new Item(new FabricItemSettings()));
    public static final Item ICE_CRYSTAL_DUST = registerItem("ice_crystal_dust", new Item(new FabricItemSettings()));
    public static final Item QUARTZ_CRYSTAL_DUST = registerItem("quartz_crystal_dust", new Item(new FabricItemSettings()));
    public static final Item MOISSANITE_DUST = registerItem("moissanite_dust", new Item(new FabricItemSettings()));
    public static final Item ELEMENT_ZERO_DUST = registerItem("element_zero_dust", new Item(new FabricItemSettings()));

    public static final Item MANGANESE_PICKAXE = registerItem("manganese_pickaxe", new PickaxeItem(ModToolMaterial.MANGANESE, 2,3f, new FabricItemSettings()));
    public static final Item MANGANESE_AXE = registerItem("manganese_axe", new AxeItem(ModToolMaterial.MANGANESE, 3,2.5f, new FabricItemSettings()));
    public static final Item MANGANESE_SHOVEL = registerItem("manganese_shovel", new ShovelItem(ModToolMaterial.MANGANESE, 2,3f, new FabricItemSettings()));
    public static final Item MANGANESE_HOE = registerItem("manganese_hoe", new HoeItem(ModToolMaterial.MANGANESE, 1,3f, new FabricItemSettings()));
    public static final Item MANGANESE_SWORD = registerItem("manganese_sword", new SwordItem(ModToolMaterial.MANGANESE, 7,7f, new FabricItemSettings()));
    public static final Item PLASMA_SWORD = registerItem("plasma_sword", new SwordItem(ModToolMaterial.ELEMENT_ZERO, 10,6f, new FabricItemSettings()));

    public static final Item TIBERIUM_STAFF = registerItem("tiberium_staff", new TiberiumStaff(ModToolMaterial.TIBERIUM, new FabricItemSettings()));
    public static final Item FREEZE_STAFF = registerItem("freeze_staff",
            new IceStaff(ModToolMaterial.ICE_CRYSTAL, new FabricItemSettings()));
    public static final Item TRANSMUTE_STAFF = registerItem("transmute_staff",
            new TransmuteStaff(ModToolMaterial.ICE_CRYSTAL, new FabricItemSettings()));
    public static final Item FIRE_STAFF = registerItem("fire_staff",
            new FireStaff(ModToolMaterial.FIRE_CRYSTAL, new FabricItemSettings()));
    public static final Item LIGHTNING_STAFF = registerItem("lightning_staff",
            new LightningStaff(ModToolMaterial.QUARTZ_CRYSTAL, new FabricItemSettings()));
    public static final Item HEALING_STAFF = registerItem("healing_staff",
            new ToolItem(ModToolMaterial.MOISSANITE_CRYSTAL, new FabricItemSettings()));
    public static final Item TELEPORT_STAFF = registerItem("teleport_staff",
            new Item(new FabricItemSettings()));
    public static final Item GRAVITY_STAFF = registerItem("gravity_staff",
            new ToolItem(ModToolMaterial.ELEMENT_ZERO, new FabricItemSettings()));
    public static final Item PETRIFY_STAFF = registerItem("petrify_staff",
            new Item(new FabricItemSettings()));
    public static final Item WATER_STAFF = registerItem("water_staff",
            new Item(new FabricItemSettings()));

    public static final StewItem TOXIC_SOUP = (StewItem) registerItem("toxic_soup",
            new StewItem(new FabricItemSettings().maxCount(1).food(ModFoodComponents.TOXIC_SOUP)));

    public static final Item TOXICBALL_ITEM = registerItem("toxicball_item", new ToxicBallItem(new FabricItemSettings()));
    public static final Item BPISTOL_ITEM = registerItem("bpistol_item", new BPistolItem(new FabricItemSettings()));
    public static final Item LASERBOLT_ITEM = registerItem("laserbolt_item", new LaserBoltItem(new FabricItemSettings()));
    public static final Item E0ROD = registerItem("e0rod", new E0RodItem(new FabricItemSettings()));

    public static final Item SLAYER_STAFF = registerItem("slayer_staff", new ToolItem(ModToolMaterial.FIRE_CRYSTAL, new FabricItemSettings()));
    public static final ToxicGunItem TOXICGUN = (ToxicGunItem) registerItem("toxicgun", new ToxicGunItem(ModToolMaterial.TIBERIUM, new FabricItemSettings()));
    public static final Item DETECTOR_ITEM = registerItem("detector_item", new OreDetectorItem(new FabricItemSettings().maxDamage(64)));
    //
    public static final Item UNICORN_SPAWN_EGG = registerItem("unicorn_spawn_egg", new SpawnEggItem(
            ModEntities.UNICORN, 0xDCE8E8, 0xEEFC69, new FabricItemSettings()));

    public static final Item GHOST_SPAWN_EGG = registerItem("ghost_spawn_egg", new SpawnEggItem(
            ModEntities.GHOST, 0xD57E36, 0x1CED00, new FabricItemSettings()));
    public static final Item AIRDRONE_SPAWN_EGG = registerItem("airdrone_spawn_egg", new SpawnEggItem(
            ModEntities.AIRDRONE, 0xD5B756, 0x1B0C83, new FabricItemSettings()));
    public static final Item SIGALIEN_SPAWN_EGG = registerItem("sigalien_spawn_egg", new SpawnEggItem(
            ModEntities.SIGALIEN, 0xD54446, 0x1B0C83, new FabricItemSettings()));
    public static final Item SKYCAR_SPAWN_EGG = registerItem("skycar_spawn_egg", new SpawnEggItem(
            ModEntities.SKYCAR, 0xD30E36, 0x1D0550, new FabricItemSettings()));
    public static final Item AIRBALOON_SPAWN_EGG = registerItem("airballoon_spawn_egg", new SpawnEggItem(
            ModEntities.AIRBALLOON, 0xD83E36, 0x1D0550, new FabricItemSettings()));

    // TIBERIUM GROUP
    public static final Item TIBERIUM_WORM_SPAWN_EGG = registerItem("tiberium_worm_spawn_egg", new SpawnEggItem(
            ModEntities.TIBERIUM_WORM, 0x70F427, 0x73FA5B, new FabricItemSettings()));
    public static final Item TIBERIUM_SKELETON_SPAWN_EGG = registerItem("tiberium_skeleton_spawn_egg", new SpawnEggItem(
            ModEntities.TIBERIUM_SKELETON, 0x70F427, 0x363835, new FabricItemSettings()));
    public static final Item TIBERIUM_FLOATER_SPAWN_EGG = registerItem("tiberium_floater_spawn_egg", new SpawnEggItem(
            ModEntities.TIBERIUM_FLOATER, 0x70F427, 0x9BFC14, new FabricItemSettings()));
    public static final Item TTROOPER_SPAWN_EGG = registerItem("ttrooper_spawn_egg", new SpawnEggItem(
            ModEntities.TTROOPER_ENTITY, 0x70F427, 0x292929, new FabricItemSettings()));
    public static final Item TCOMMANDER_SPAWN_EGG = registerItem("tcommander_spawn_egg", new SpawnEggItem(
            ModEntities.TCOMMANDER_ENTITY, 0x70F427, 0x4F4F4F, new FabricItemSettings()));
    public static final Item ESPIDER_SPAWN_EGG = registerItem("espider_spawn_egg", new SpawnEggItem(
            ModEntities.ESPIDER_ENTITY, 0x70F427, 0x4F4F4F, new FabricItemSettings()));

    public static final Item ROTTEN_FLESH_ON_A_STICK = registerItem("rotten_flesh_on_a_stick",
            new OnAStickItem<>(new FabricItemSettings().maxDamage(25), ModEntities.ESPIDER_ENTITY, 7));

    public static void addItemsToIngredientGroup(FabricItemGroupEntries entries) {
        entries.add(RAW_ELEMENT_ZERO);
        entries.add(RAW_QUARTZ_CRYSTAL);
        entries.add(RAW_MANGANESE);
        entries.add(RAW_MOISSANITE);
        entries.add(COKECOAL);
        entries.add(TIBERIUMCOAL);
        entries.add(TIBERIUMCOAL);

        entries.add(TIBERIUM_SHARD);
        entries.add(FIRE_CRYSTAL_SHARD);
        entries.add(EXOTIC_CRYSTAL_SHARD);
        entries.add(ICE_CRYSTAL_SHARD);
        entries.add(QUARTZ_CRYSTAL_SHARD);
        entries.add(GLASS_SHARD);

        entries.add(MANGANESE_INGOT);
        entries.add(SIGSTEEL_INGOT);
        entries.add(SIGSTAINSTEEL_INGOT);
        entries.add(MANGANESE_NUGGET);

        entries.add(MOISSANITE);
        entries.add(ELEMENT_ZERO);

        entries.add(TIBERIUM_CRYSTAL);
        entries.add(ICE_CRYSTAL);
        entries.add(EXOTIC_CRYSTAL);
        entries.add(FIRE_CRYSTAL);
        entries.add(QUARTZ_CRYSTAL);
        entries.add(MOISSANITE_CRYSTAL);
        entries.add(ELEMENT_ZERO_CRYSTAL);

        entries.add(MANGANESE_DUST);
        entries.add(TIBERIUM_DUST);
        entries.add(ICE_CRYSTAL_DUST);
        entries.add(EXOTIC_CRYSTAL_DUST);
        entries.add(FIRE_CRYSTAL_DUST);
        entries.add(QUARTZ_CRYSTAL_DUST);
        entries.add(MOISSANITE_DUST);
        entries.add(ELEMENT_ZERO_DUST);

        entries.add(E0ROD);
        entries.add(SLAYER_STAFF);
        entries.add(PLASMA_SWORD);

        entries.add(TIBERIUM_STAFF);
        entries.add(FREEZE_STAFF);
        entries.add(TRANSMUTE_STAFF);
        entries.add(FIRE_STAFF);
        entries.add(LIGHTNING_STAFF);
        entries.add(HEALING_STAFF);
        entries.add(TELEPORT_STAFF);
        entries.add(GRAVITY_STAFF);
        entries.add(PETRIFY_STAFF);
        entries.add(WATER_STAFF);

        entries.add(TOXIC_SOUP);

        entries.add(TOXICBALL_ITEM);
        entries.add(BPISTOL_ITEM);
        entries.add(LASERBOLT_ITEM);

        entries.add(TOXICGUN);
        entries.add(DETECTOR_ITEM);

        entries.add(ROTTEN_FLESH_ON_A_STICK);

        // ANIMALS
        entries.add(UNICORN_SPAWN_EGG);

        entries.add(ESPIDER_SPAWN_EGG);

        // TIBERIUM
        entries.add(TIBERIUM_WORM_SPAWN_EGG);
        entries.add(TIBERIUM_SKELETON_SPAWN_EGG);
        entries.add(TTROOPER_SPAWN_EGG);
        entries.add(TCOMMANDER_SPAWN_EGG);
        entries.add(TIBERIUM_FLOATER_SPAWN_EGG);

        // OTHER
        entries.add(GHOST_SPAWN_EGG);
        entries.add(AIRDRONE_SPAWN_EGG);
        //entries.add(SKYCAR_SPAWN_EGG);
        //vehicles
        entries.add(AIRBALOON_SPAWN_EGG);


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
