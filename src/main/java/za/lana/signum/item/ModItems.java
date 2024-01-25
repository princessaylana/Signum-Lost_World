/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 */

package za.lana.signum.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.entity.ModEntities;
import za.lana.signum.item.custom.*;

public class ModItems {
    private static final int crystalDamage = 6;
    private static final float crystalSpeed = 4.5f;
    //
    public static final Item COPPER_COIN = registerItem("copper_coin", new CoinItem(new FabricItemSettings()));
    public static final Item IRON_COIN = registerItem("iron_coin", new CoinItem(new FabricItemSettings()));
    public static final Item GOLD_COIN = registerItem("gold_coin", new CoinItem(new FabricItemSettings()));
    //
    public static final Item BLACK_DIAMOND_SHARD = registerItem("black_diamond_shard", new Item(new FabricItemSettings()));
    public static final Item BLACK_DIAMOND_CRYSTAL = registerItem("black_diamond_crystal", new Item(new FabricItemSettings()));

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
    public static final Item STEEL_INGOT = registerItem("steel_ingot", new Item(new FabricItemSettings()));
    public static final Item STAINLESS_STEEL_INGOT = registerItem("stainless_steel_ingot", new Item(new FabricItemSettings()));
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

    public static final Item ENDER_CRYSTAL = registerItem("ender_crystal", new EnderCrystalItem(new FabricItemSettings()));

    public static final Item MANGANESE_DUST = registerItem("manganese_dust", new Item(new FabricItemSettings()));
    public static final Item TIBERIUM_DUST = registerItem("tiberium_dust", new Item(new FabricItemSettings()));
    public static final Item FIRE_CRYSTAL_DUST = registerItem("fire_crystal_dust", new Item(new FabricItemSettings()));
    public static final Item EXOTIC_CRYSTAL_DUST = registerItem("exotic_crystal_dust", new Item(new FabricItemSettings()));
    public static final Item ICE_CRYSTAL_DUST = registerItem("ice_crystal_dust", new Item(new FabricItemSettings()));
    public static final Item QUARTZ_CRYSTAL_DUST = registerItem("quartz_crystal_dust", new Item(new FabricItemSettings()));
    public static final Item MOISSANITE_DUST = registerItem("moissanite_dust", new Item(new FabricItemSettings()));
    public static final Item ELEMENT_ZERO_DUST = registerItem("element_zero_dust", new Item(new FabricItemSettings()));

    public static final Item MANGANESE_PICKAXE = registerItem("manganese_pickaxe", new PickaxeItem(ModToolMaterials.MANGANESE, 2,3f, new FabricItemSettings()));
    public static final Item MANGANESE_AXE = registerItem("manganese_axe", new AxeItem(ModToolMaterials.MANGANESE, 3,2.5f, new FabricItemSettings()));
    public static final Item MANGANESE_SHOVEL = registerItem("manganese_shovel", new ShovelItem(ModToolMaterials.MANGANESE, 2,3f, new FabricItemSettings()));
    public static final Item MANGANESE_HOE = registerItem("manganese_hoe", new HoeItem(ModToolMaterials.MANGANESE, 1,3f, new FabricItemSettings()));
    public static final Item MANGANESE_SWORD = registerItem("manganese_sword", new SwordItem(ModToolMaterials.MANGANESE, 7,7f, new FabricItemSettings()));
    public static final Item SPIKED_CLUB = registerItem("spiked_club", new SwordItem(ModToolMaterials.WOOD, 3,3f, new FabricItemSettings()));
    public static final Item WOODEN_CLUB = registerItem("wooden_club", new SwordItem(ModToolMaterials.WOOD, 2,3.5f, new FabricItemSettings()));

    // Armor
    public static final Item STEEL_HELMET = registerItem("steel_helmet",
            new ArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item STEEL_CHESTPLATE = registerItem("steel_chestplate",
            new ArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item STEEL_LEGGINGS = registerItem("steel_leggings",
            new ArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item STEEL_BOOTS = registerItem("steel_boots",
            new ArmorItem(ModArmorMaterials.STEEL, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    //
    public static final Item BLACK_DIAMOND_HELMET = registerItem("black_diamond_helmet",
            new ModArmorItem(ModArmorMaterials.BLACK_DIAMOND, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item BLACK_DIAMOND_CHESTPLATE = registerItem("black_diamond_chestplate",
            new ArmorItem(ModArmorMaterials.BLACK_DIAMOND, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item BLACK_DIAMOND_LEGGINGS = registerItem("black_diamond_leggings",
            new ArmorItem(ModArmorMaterials.BLACK_DIAMOND, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item BLACK_DIAMOND_BOOTS = registerItem("black_diamond_boots",
            new ArmorItem(ModArmorMaterials.BLACK_DIAMOND, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    //
    public static final Item ELEMENT_ZERO_HELMET = registerItem("element_zero_helmet",
            new ModArmorItem(ModArmorMaterials.ELEMENT_ZERO, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item ELEMENT_ZERO_CHESTPLATE = registerItem("element_zero_chestplate",
            new ArmorItem(ModArmorMaterials.ELEMENT_ZERO, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item ELEMENT_ZERO_LEGGINGS = registerItem("element_zero_leggings",
            new ArmorItem(ModArmorMaterials.ELEMENT_ZERO, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item ELEMENT_ZERO_BOOTS = registerItem("element_zero_boots",
            new ArmorItem(ModArmorMaterials.ELEMENT_ZERO, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    //
    public static final Item EXOTIC_HELMET = registerItem("exotic_helmet",
            new ModArmorItem(ModArmorMaterials.EXOTIC, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item EXOTIC_CHESTPLATE = registerItem("exotic_chestplate",
            new ArmorItem(ModArmorMaterials.EXOTIC, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item EXOTIC_LEGGINGS = registerItem("exotic_leggings",
            new ArmorItem(ModArmorMaterials.EXOTIC, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item EXOTIC_BOOTS = registerItem("exotic_boots",
            new ArmorItem(ModArmorMaterials.EXOTIC, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    //
    public static final Item FIRE_HELMET = registerItem("fire_helmet",
            new ModArmorItem(ModArmorMaterials.FIRE, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item FIRE_CHESTPLATE = registerItem("fire_chestplate",
            new ArmorItem(ModArmorMaterials.FIRE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item FIRE_LEGGINGS = registerItem("fire_leggings",
            new ArmorItem(ModArmorMaterials.FIRE, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item FIRE_BOOTS = registerItem("fire_boots",
            new ArmorItem(ModArmorMaterials.FIRE, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    //
    public static final Item ICE_HELMET = registerItem("ice_helmet",
            new ModArmorItem(ModArmorMaterials.ICE, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item ICE_CHESTPLATE = registerItem("ice_chestplate",
            new ArmorItem(ModArmorMaterials.ICE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item ICE_LEGGINGS = registerItem("ice_leggings",
            new ArmorItem(ModArmorMaterials.ICE, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item ICE_BOOTS = registerItem("ice_boots",
            new ArmorItem(ModArmorMaterials.ICE, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    //
    public static final Item MOISSANITE_HELMET = registerItem("moissanite_helmet",
            new ModArmorItem(ModArmorMaterials.MOISSANITE, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item MOISSANITE_CHESTPLATE = registerItem("moissanite_chestplate",
            new ArmorItem(ModArmorMaterials.MOISSANITE, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item MOISSANITE_LEGGINGS = registerItem("moissanite_leggings",
            new ArmorItem(ModArmorMaterials.MOISSANITE, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MOISSANITE_BOOTS = registerItem("moissanite_boots",
            new ArmorItem(ModArmorMaterials.MOISSANITE, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    //
    public static final Item QUARTZ_HELMET = registerItem("quartz_helmet",
            new ModArmorItem(ModArmorMaterials.QUARTZ, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item QUARTZ_CHESTPLATE = registerItem("quartz_chestplate",
            new ArmorItem(ModArmorMaterials.QUARTZ, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item QUARTZ_LEGGINGS = registerItem("quartz_leggings",
            new ArmorItem(ModArmorMaterials.QUARTZ, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item QUARTZ_BOOTS = registerItem("quartz_boots",
            new ArmorItem(ModArmorMaterials.QUARTZ, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    //
    public static final Item TIBERIUM_HELMET = registerItem("tiberium_helmet",
            new ModArmorItem(ModArmorMaterials.TIBERIUM, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item TIBERIUM_CHESTPLATE = registerItem("tiberium_chestplate",
            new ArmorItem(ModArmorMaterials.TIBERIUM, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item TIBERIUM_LEGGINGS = registerItem("tiberium_leggings",
            new ArmorItem(ModArmorMaterials.TIBERIUM, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item TIBERIUM_BOOTS = registerItem("tiberium_boots",
            new ArmorItem(ModArmorMaterials.TIBERIUM, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    //
    public static final Item STEEL_SHIELD = registerItem("steel_shield",
            new ShieldItem(new FabricItemSettings().maxDamage(500)));

    // Weapons

    public static final Item BLACK_DIAMOND_SWORD = registerItem("black_diamond_sword",
            new SSwordItem(ModToolMaterials.BLACK_DIAMOND_CRYSTAL, crystalDamage,crystalSpeed, new FabricItemSettings()));
    public static final Item ELEMENT_ZERO_SWORD = registerItem("element_zero_sword",
            new SSwordItem(ModToolMaterials.ELEMENT_ZERO, crystalDamage,crystalSpeed, new FabricItemSettings()));
    public static final Item EXOTIC_CRYSTAL_SWORD = registerItem("exotic_crystal_sword",
            new SSwordItem(ModToolMaterials.EXOTIC_CRYSTAL, crystalDamage,crystalSpeed, new FabricItemSettings()));
    public static final Item FIRE_CRYSTAL_SWORD = registerItem("fire_crystal_sword",
            new SSwordItem(ModToolMaterials.FIRE_CRYSTAL, crystalDamage,crystalSpeed, new FabricItemSettings()));
    public static final Item ICE_CRYSTAL_SWORD = registerItem("ice_crystal_sword",
            new SSwordItem(ModToolMaterials.ICE_CRYSTAL, crystalDamage,crystalSpeed, new FabricItemSettings()));
    public static final Item MOISSANITE_SWORD = registerItem("moissanite_sword",
            new SSwordItem(ModToolMaterials.MOISSANITE_CRYSTAL, crystalDamage,crystalSpeed, new FabricItemSettings()));
    public static final Item PLASMA_SWORD = registerItem("plasma_sword",
            new SSwordItem(ModToolMaterials.ELEMENT_ZERO, crystalDamage,crystalSpeed, new FabricItemSettings()));
    public static final Item QUARTZ_CRYSTAL_SWORD = registerItem("quartz_crystal_sword",
            new SSwordItem(ModToolMaterials.QUARTZ_CRYSTAL, crystalDamage,crystalSpeed, new FabricItemSettings()));
    public static final Item TIBERIUM_SWORD = registerItem("tiberium_sword",
            new SSwordItem(ModToolMaterials.TIBERIUM_CRYSTAL, crystalDamage,crystalSpeed, new FabricItemSettings()));


    //
    public static final Item TIBERIUM_STAFF = registerItem("tiberium_staff",
            new TiberiumStaff(ModToolMaterials.TIBERIUM_CRYSTAL, new FabricItemSettings()));
    public static final Item FREEZE_STAFF = registerItem("freeze_staff",
            new IceStaff(ModToolMaterials.ICE_CRYSTAL, new FabricItemSettings()));
    public static final Item TRANSMUTE_STAFF = registerItem("transmute_staff",
            new TransmuteStaff(ModToolMaterials.EXOTIC_CRYSTAL, new FabricItemSettings()));
    public static final Item FIRE_STAFF = registerItem("fire_staff",
            new FireStaff(ModToolMaterials.FIRE_CRYSTAL, new FabricItemSettings()));
    public static final Item LIGHTNING_STAFF = registerItem("lightning_staff",
            new LightningStaff(ModToolMaterials.QUARTZ_CRYSTAL, new FabricItemSettings()));
    public static final Item HEALING_STAFF = registerItem("healing_staff",
            new HealingStaff(ModToolMaterials.MOISSANITE_CRYSTAL, new FabricItemSettings()));
    public static final Item DEATH_STAFF = registerItem("death_staff",
            new DeathStaff(ModToolMaterials.BLACK_DIAMOND_CRYSTAL, new FabricItemSettings()));
    public static final Item TELEPORT_STAFF = registerItem("teleport_staff",
            new Item(new FabricItemSettings()));
    public static final Item GRAVITY_STAFF = registerItem("gravity_staff",
            new GravityStaff(ModToolMaterials.ELEMENT_ZERO, new FabricItemSettings()));

    public static final Item WATER_STAFF = registerItem("water_staff",
            new Item(new FabricItemSettings()));

    public static final StewItem TOXIC_SOUP = (StewItem) registerItem("toxic_soup",
            new StewItem(new FabricItemSettings().maxCount(1).food(ModFoodComponents.TOXIC_SOUP)));
    public static final StewItem MIXED_MUSHROOM_STEW = (StewItem) registerItem("mixed_mushroom_stew",
            new StewItem(new FabricItemSettings().maxCount(1).food(ModFoodComponents.MIXED_MUSHROOM_STEW)));

    public static final Item DETECTOR_ITEM = registerItem("detector_item", new OreDetectorItem(new FabricItemSettings().maxDamage(64)));
    //
    public static final Item UNICORN_SPAWN_EGG = registerItem("unicorn_spawn_egg", new SpawnEggItem(
            ModEntities.UNICORN, 0xDCE8E8, 0xEEFC69, new FabricItemSettings()));

    public static final Item CURSED_WOLF_SPAWN_EGG = registerItem("cursed_wolf_spawn_egg", new SpawnEggItem(
            ModEntities.CURSED_WOLF, 0xDCE8E8, 0xEEFC69, new FabricItemSettings()));
    public static final Item PIDGEON_SPAWN_EGG = registerItem("pidgeon_spawn_egg", new SpawnEggItem(
            ModEntities.PIDGEON, 0xDCE8E8, 0xACADAB, new FabricItemSettings()));

    public static final Item GHOST_SPAWN_EGG = registerItem("ghost_spawn_egg", new SpawnEggItem(
            ModEntities.GHOST, 0x444342, 0x857D7C, new FabricItemSettings()));
    public static final Item TORTURED_SOUL_SPAWN_EGG = registerItem("tortured_soul_spawn_egg", new SpawnEggItem(
            ModEntities.TORTURED_SOUL, 0x444342, 0x857D7C, new FabricItemSettings()));
    public static final Item AIRBALOON_SPAWN_EGG = registerItem("airballoon_spawn_egg", new SpawnEggItem(
            ModEntities.AIRBALLOON, 0xD83E36, 0x1D0550, new FabricItemSettings()));

    public static final Item AIRSHIP_SPAWN_EGG = registerItem("airship_spawn_egg", new SpawnEggItem(
            ModEntities.AIRSHIP, 0xD83E36, 0x1D0550, new FabricItemSettings()));

    public static final Item TIBERIUM_WORM_SPAWN_EGG = registerItem("tiberium_worm_spawn_egg", new SpawnEggItem(
            ModEntities.TIBERIUM_WORM, 0x70F427, 0x73FA5B, new FabricItemSettings()));
    public static final Item TIBERIUM_FLOATER_SPAWN_EGG = registerItem("tiberium_floater_spawn_egg", new SpawnEggItem(
            ModEntities.TIBERIUM_FLOATER, 0x70F427, 0xC3F802, new FabricItemSettings()));
    public static final Item TTROOPER_SPAWN_EGG = registerItem("ttrooper_spawn_egg", new SpawnEggItem(
            ModEntities.TTROOPER_ENTITY, 0x70F427, 0xA3BE40, new FabricItemSettings()));
     public static final Item ESPIDER_SPAWN_EGG = registerItem("espider_spawn_egg", new SpawnEggItem(
            ModEntities.ESPIDER_ENTITY, 0x484240, 0xF03C00, new FabricItemSettings()));
    public static final Item GIANT_ESPIDER_SPAWN_EGG = registerItem("giant_espider_spawn_egg", new SpawnEggItem(
            ModEntities.GIANTESPIDER_ENTITY, 0x484240, 0xF03C00, new FabricItemSettings()));
    public static final Item FALLEN_SPAWN_EGG = registerItem("fallen_spawn_egg", new SpawnEggItem(
            ModEntities.FALLEN_ENTITY, 0x484240, 0xD64308, new FabricItemSettings()));

    // SKELETONS
    //public static final Item SUMSKELETON_SPAWN_EGG = registerItem("sskeleton_spawn_egg", new SpawnEggItem(ModEntities.SSKELETON_ENTITY, 0x70F427, 0x4F4F4F, new FabricItemSettings()));
    public static final Item TIBSKELETON_SPAWN_EGG = registerItem("tibskeleton_spawn_egg", new SpawnEggItem(
            ModEntities.TIBSKELETON_ENTITY, 0xCECECE, 0x3ED608, new FabricItemSettings()));
    public static final Item ICESKELETON_SPAWN_EGG = registerItem("iceskeleton_spawn_egg", new SpawnEggItem(
            ModEntities.ICESKELETON_ENTITY, 0xCECECE, 0x089BD6, new FabricItemSettings()));
    public static final Item FIRESKELETON_SPAWN_EGG = registerItem("fireskeleton_spawn_egg", new SpawnEggItem(
            ModEntities.FIRESKELETON_ENTITY, 0xCECECE, 0xD64308, new FabricItemSettings()));
    public static final Item ENDERSKELETON_SPAWN_EGG = registerItem("enderskeleton_spawn_egg", new SpawnEggItem(
            ModEntities.ENDERSKELETON_ENTITY, 0xCECECE, 0xA108D6, new FabricItemSettings()));
    public static final Item DARKSKELETON_SPAWN_EGG = registerItem("darkskeleton_spawn_egg", new SpawnEggItem(
            ModEntities.DARKSKELETON_ENTITY, 0xCECECE, 0x000000, new FabricItemSettings()));
    public static final Item ELVE_GUARD_SPAWN_EGG = registerItem("elve_guard_spawn_egg", new SpawnEggItem(
            ModEntities.ELVE_GUARD_ENTITY, 0xDCE8E8, 0xbf9000, new FabricItemSettings()));
    public static final Item WIZARD_SPAWN_EGG = registerItem("wizard_spawn_egg", new SpawnEggItem(
            ModEntities.WIZARD_ENTITY, 0xDCE8E8, 0xbf9000, new FabricItemSettings()));
    public static final Item TIBERIUM_WIZARD_SPAWN_EGG = registerItem("tiberium_wizard_spawn_egg", new SpawnEggItem(
            ModEntities.TIBERIUM_WIZARD_ENTITY, 0xDCE8E8, 0xbf9000, new FabricItemSettings()));

    public static void addItemsToIngredientGroup(FabricItemGroupEntries entries) {
        entries.add(BLACK_DIAMOND_SHARD);
        entries.add(BLACK_DIAMOND_CRYSTAL);

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
        entries.add(STEEL_INGOT);
        entries.add(STAINLESS_STEEL_INGOT);
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
    }
    public static void addItemsToSpawnEggsGroup(FabricItemGroupEntries entries){

        // ANIMALS
        entries.add(UNICORN_SPAWN_EGG);
        entries.add(PIDGEON_SPAWN_EGG);

        entries.add(ESPIDER_SPAWN_EGG);
        entries.add(GIANT_ESPIDER_SPAWN_EGG);
        entries.add(FALLEN_SPAWN_EGG);

        // TIBERIUM
        entries.add(TIBERIUM_WORM_SPAWN_EGG);
        entries.add(TTROOPER_SPAWN_EGG);
        entries.add(TIBERIUM_FLOATER_SPAWN_EGG);
        entries.add(ELVE_GUARD_SPAWN_EGG);

        // OTHER
        entries.add(GHOST_SPAWN_EGG);
        entries.add(TORTURED_SOUL_SPAWN_EGG);
        //entries.add(SUMSKELETON_SPAWN_EGG);
        entries.add(TIBSKELETON_SPAWN_EGG);
        entries.add(ICESKELETON_SPAWN_EGG);
        entries.add(FIRESKELETON_SPAWN_EGG);
        entries.add(ENDERSKELETON_SPAWN_EGG);
        entries.add(DARKSKELETON_SPAWN_EGG);

        //vehicles
        entries.add(AIRBALOON_SPAWN_EGG);
    }

    public static void addItemsToCombatGroup(FabricItemGroupEntries entries){
        entries.add(STEEL_HELMET);
        entries.add(STEEL_CHESTPLATE);
        entries.add(STEEL_LEGGINGS);
        entries.add(STEEL_BOOTS);

        entries.add(BLACK_DIAMOND_HELMET);
        entries.add(BLACK_DIAMOND_CHESTPLATE);
        entries.add(BLACK_DIAMOND_LEGGINGS);
        entries.add(BLACK_DIAMOND_BOOTS);

        entries.add(ELEMENT_ZERO_HELMET);
        entries.add(ELEMENT_ZERO_CHESTPLATE);
        entries.add(ELEMENT_ZERO_LEGGINGS);
        entries.add(ELEMENT_ZERO_BOOTS);

        entries.add(EXOTIC_HELMET);
        entries.add(EXOTIC_CHESTPLATE);
        entries.add(EXOTIC_LEGGINGS);
        entries.add(EXOTIC_BOOTS);

        entries.add(FIRE_HELMET);
        entries.add(FIRE_CHESTPLATE);
        entries.add(FIRE_LEGGINGS);
        entries.add(FIRE_BOOTS);

        entries.add(ICE_HELMET);
        entries.add(ICE_CHESTPLATE);
        entries.add(ICE_LEGGINGS);
        entries.add(ICE_BOOTS);

        entries.add(MOISSANITE_HELMET);
        entries.add(MOISSANITE_CHESTPLATE);
        entries.add(MOISSANITE_LEGGINGS);
        entries.add(MOISSANITE_BOOTS);

        entries.add(QUARTZ_HELMET);
        entries.add(QUARTZ_CHESTPLATE);
        entries.add(QUARTZ_LEGGINGS);
        entries.add(QUARTZ_BOOTS);

        entries.add(TIBERIUM_HELMET);
        entries.add(TIBERIUM_CHESTPLATE);
        entries.add(TIBERIUM_LEGGINGS);
        entries.add(TIBERIUM_BOOTS);

        entries.add(MANGANESE_SWORD);

        entries.add(BLACK_DIAMOND_SWORD);
        entries.add(ELEMENT_ZERO_SWORD);
        entries.add(EXOTIC_CRYSTAL_SWORD);
        entries.add(FIRE_CRYSTAL_SWORD);
        entries.add(ICE_CRYSTAL_SWORD);
        entries.add(MOISSANITE_SWORD);
        entries.add(QUARTZ_CRYSTAL_SWORD);
        entries.add(PLASMA_SWORD);
        entries.add(TIBERIUM_SWORD);

        entries.add(TIBERIUM_STAFF);
        entries.add(FREEZE_STAFF);
        entries.add(TRANSMUTE_STAFF);
        entries.add(FIRE_STAFF);
        entries.add(LIGHTNING_STAFF);
        entries.add(HEALING_STAFF);
        entries.add(DEATH_STAFF);
        entries.add(TELEPORT_STAFF);
        entries.add(GRAVITY_STAFF);

        entries.add(WATER_STAFF);

    }

    public static void addItemsToToolGroup(FabricItemGroupEntries entries){
        entries.add(DETECTOR_ITEM);
        entries.add(MANGANESE_AXE);
        entries.add(MANGANESE_HOE);
        entries.add(MANGANESE_PICKAXE);
        entries.add(MANGANESE_SHOVEL);
    }

    public static void addItemsToFoodGroup(FabricItemGroupEntries entries){
        entries.add(TOXIC_SOUP);
        entries.add(MIXED_MUSHROOM_STEW);
    }
    public static void addItemsToNaturalGroup(FabricItemGroupEntries entries){
        // ORES
        entries.add(ModBlocks.BLACK_DIAMOND_ORE);
        entries.add(ModBlocks.DEEPSLATE_BLACK_DIAMOND_ORE);
        entries.add(ModBlocks.ELEMENT_ZERO_ORE);
        entries.add(ModBlocks.DEEPSLATE_ELEMENT_ZERO_ORE);
        entries.add(ModBlocks.MANGANESE_ORE);
        entries.add(ModBlocks.DEEPSLATE_MANGANESE_ORE);
        entries.add(ModBlocks.NETHERRACK_MANGANESE_ORE);
        entries.add(ModBlocks.MOISSANITE_ORE);
        entries.add(ModBlocks.DEEPSLATE_MOISSANITE_ORE);
        entries.add(ModBlocks.ENDSTONE_MANGANESE_ORE);

        entries.add(ModBlocks.BLIGHT_BLOCK);
        entries.add(ModBlocks.SKY_ICE_BLOCK);
        entries.add(ModBlocks.FROSTED_SKY_ICE_BLOCK);
        entries.add(ModBlocks.ASH_BLOCK);
        entries.add(ModBlocks.ROCK_BLOCK);
        //
        entries.add(ModBlocks.BUDDING_TIBERIUM);
        entries.add(ModBlocks.BUDDING_FIRE_CRYSTAL);
        entries.add(ModBlocks.BUDDING_EXOTIC_CRYSTAL);
        entries.add(ModBlocks.BUDDING_ICE_CRYSTAL);
        entries.add(ModBlocks.BUDDING_QUARTZ_CRYSTAL);
        // PLANTBLOCKS
        entries.add(ModBlocks.ORANGE_SHROOM_BLOCK);
        entries.add(ModBlocks.YELLOW_SHROOM_BLOCK);
        entries.add(ModBlocks.TOXIC_SHROOM_BLOCK);
        entries.add(ModBlocks.BLUE_SHROOM_BLOCK);
        entries.add(ModBlocks.PURPLE_SHROOM_BLOCK);
        entries.add(ModBlocks.PINK_SHROOM_BLOCK);
        entries.add(ModBlocks.RED_SHROOM_BLOCK);
        entries.add(ModBlocks.BROWN_SHROOM_BLOCK);

        entries.add(ModBlocks.TOXIC_SHROOM_STEM);
        entries.add(ModBlocks.GENERIC_SHROOM_STEM);

        entries.add(ModBlocks.SOULWOOD_LOG);
        entries.add(ModBlocks.STRIPPED_SOULWOOD_LOG);
        entries.add(ModBlocks.SOULWOOD_WOOD);
        entries.add(ModBlocks.STRIPPED_SOULWOOD_WOOD);
        entries.add(ModBlocks.SOULWOOD_PLANKS);
        entries.add(ModBlocks.SOULWOOD_LEAVES);
        entries.add(ModBlocks.SOULWOOD_SAPLING);
    }
    public static void addItemsToBuildingBlocksGroup(FabricItemGroupEntries entries){
        entries.add(ModBlocks.RAZORWIRE_BLOCK);
        entries.add(ModBlocks.RAINBOW_MARBLE_BLOCK);
        entries.add(ModBlocks.AIRSHIP_LANDING_BLOCK);
        entries.add(ModBlocks.SPIDERWEB_BLOCK);
        //
        // MAIN BLOCKS
        entries.add(ModBlocks.BLACK_DIAMOND_BLOCK);
        entries.add(ModBlocks.ELEMENT_ZERO_BLOCK);
        entries.add(ModBlocks.EXOTIC_CRYSTAL_BLOCK);
        entries.add(ModBlocks.FIRE_CRYSTAL_BLOCK);
        entries.add(ModBlocks.ICE_CRYSTAL_BLOCK);
        entries.add(ModBlocks.MANGANESE_BLOCK);
        entries.add(ModBlocks.MOISSANITE_BLOCK);
        entries.add(ModBlocks.QUARTZ_CRYSTAL_BLOCK);
        entries.add(ModBlocks.TIBERIUM_BLOCK);
        // DECORATION
        entries.add(ModBlocks.MANGANESE_STAIRS);
        entries.add(ModBlocks.MANGANESE_WALL);
        entries.add(ModBlocks.MANGANESE_SLAB);
        entries.add(ModBlocks.MANGANESE_FENCE);
        entries.add(ModBlocks.MANGANESE_GATE);
        entries.add(ModBlocks.MANGANESE_DOOR);
        entries.add(ModBlocks.MANGANESE_TRAPDOOR);
        entries.add(ModBlocks.MANGANESE_BUTTON);
        entries.add(ModBlocks.MANGANESE_PRESSURE_PLATE);
    }
    public static void addItemsToFunctionalGroup(FabricItemGroupEntries entries){
        // BLOCKENTITIES
        entries.add(ModBlocks.SKYFORGE);
        entries.add(ModBlocks.EXAMPLE_BLOCK);
    }

    //add new items above
    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(Signum.MOD_ID, name), item);

    }
    public static void registerModItems(){
        Signum.LOGGER.info("Registering ModItems for " + Signum.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(ModItems::addItemsToSpawnEggsGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemsToCombatGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToToolGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::addItemsToFoodGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(ModItems::addItemsToNaturalGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(ModItems::addItemsToBuildingBlocksGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(ModItems::addItemsToFunctionalGroup);
    }
}
