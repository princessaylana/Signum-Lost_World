/**
 * Registers the mod blocks
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import za.lana.signum.Signum;
import za.lana.signum.block.custom.AssemblyStationBlock;
import za.lana.signum.block.custom.ExampleBlock;
import za.lana.signum.block.custom.RazorWireBlock;
import za.lana.signum.block.custom.SkyForgeBlock;
import za.lana.signum.block.custom.crystal.*;
import za.lana.signum.block.custom.modore.ElementZeroOreBlock;
import za.lana.signum.block.custom.plants.*;
import za.lana.signum.block.custom.props.*;
import za.lana.signum.sound.ModSounds;
import za.lana.signum.world.ModConfiguredFeatures;
import za.lana.signum.world.gen.SoulWoodSaplingGenerator;

//obsidian hardness (50.0f, 1200.0f)
//iron hardness (5.0f, 6.0f)
//steel hardness (unknown)
//the names here needs to match the json files

public class ModBlocks {


    public static final Block MANGANESE_ORE = registerBlock("manganese_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(BlockSoundGroup.STONE)
                    .requiresTool(), UniformIntProvider.create(2, 5)));
    public static final Block MOISSANITE_ORE = registerBlock("moissanite_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(BlockSoundGroup.STONE)
                    , UniformIntProvider.create(3, 6)));
    public static final Block ELEMENT_ZERO_ORE = registerBlock("element_zero_ore",
            new ElementZeroOreBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(BlockSoundGroup.STONE)
                    .luminance(state -> state.get (ElementZeroOreBlock.LIT)? 6 : 0), UniformIntProvider.create(3, 9)));
    public static final Block DEEPSLATE_MANGANESE_ORE = registerBlock("deepslate_manganese_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).sounds(BlockSoundGroup.DEEPSLATE)
                    .strength(15.0f, 20.0f).requiresTool(), UniformIntProvider.create(3, 6)));
    public static final Block DEEPSLATE_MOISSANITE_ORE = registerBlock("deepslate_moissanite_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).sounds(BlockSoundGroup.DEEPSLATE),
                    UniformIntProvider.create(3, 9)));
    public static final Block DEEPSLATE_ELEMENT_ZERO_ORE = registerBlock("deepslate_element_zero_ore",
            new ElementZeroOreBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).sounds(BlockSoundGroup.DEEPSLATE)
                    , UniformIntProvider.create(3, 9)));

    public static final Block NETHERRACK_MANGANESE_ORE = registerBlock("netherrack_manganese_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.NETHERRACK).sounds(BlockSoundGroup.NETHER_ORE)
                    , UniformIntProvider.create(3, 6)));
    public static final Block ENDSTONE_MANGANESE_ORE = registerBlock("endstone_manganese_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.END_STONE).sounds(BlockSoundGroup.NETHER_ORE),
                    UniformIntProvider.create(3, 6)));
    //
    public static final Block MOISSANITE_BLOCK = registerBlock("moissanite_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.AMETHYST_BLOCK)));
    public static final Block MANGANESE_BLOCK = registerBlock("manganese_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.NETHERITE)));
    public static final Block MANGANESE_STAIRS = registerBlock("manganese_stairs",
            new StairsBlock(ModBlocks.MANGANESE_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.NETHERITE)));
    public static final Block MANGANESE_SLAB = registerBlock("manganese_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.NETHERITE)));
    public static final Block MANGANESE_BUTTON = registerBlock("manganese_button",
            new ButtonBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.NETHERITE),
                    BlockSetType.IRON, 40,true));
    public static final Block MANGANESE_PRESSURE_PLATE = registerBlock("manganese_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,
                    FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.NETHERITE), BlockSetType.IRON));
    public static final Block MANGANESE_FENCE = registerBlock("manganese_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.NETHERITE)));
    public static final Block MANGANESE_GATE = registerBlock("manganese_gate",
            new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.NETHERITE), WoodType.DARK_OAK));
    public static final Block MANGANESE_WALL = registerBlock("manganese_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.NETHERITE)));
    public static final Block MANGANESE_DOOR = registerBlock("manganese_door",
            new DoorBlock(FabricBlockSettings.copyOf(Blocks.IRON_DOOR).sounds(BlockSoundGroup.NETHERITE), BlockSetType.OAK));
    public static final Block MANGANESE_TRAPDOOR = registerBlock("manganese_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.IRON_TRAPDOOR).sounds(BlockSoundGroup.NETHERITE), BlockSetType.OAK));

    // woods
    public static final Block SOULWOOD_LOG = registerBlock("soulwood_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).sounds(BlockSoundGroup.WOOD).strength(3f)));
    public static final Block SOULWOOD_WOOD = registerBlock("soulwood_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).sounds(BlockSoundGroup.WOOD).strength(3f)));
    public static final Block STRIPPED_SOULWOOD_LOG = registerBlock("stripped_soulwood_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).sounds(BlockSoundGroup.WOOD).strength(3f)));
    public static final Block STRIPPED_SOULWOOD_WOOD = registerBlock("stripped_soulwood_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD).sounds(BlockSoundGroup.WOOD).strength(3f)));

    public static final Block SOULWOOD_PLANKS = registerBlock("soulwood_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).sounds(BlockSoundGroup.WOOD).strength(3f)));
    public static final Block SOULWOOD_LEAVES = registerBlock("soulwood_leaves",
            new SoulWoodLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).sounds(BlockSoundGroup.WOOD).strength(3f).nonOpaque()));
    public static final Block SOULWOOD_SAPLING = registerBlock("soulwood_sapling",
            new SaplingBlock(new SoulWoodSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).strength(1f)));


    //props
    public static final Block BLIGHT_BLOCK = registerBlock("blight_block",
            new BlightBlock(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).mapColor(MapColor.LIME)
                    .sounds(BlockSoundGroup.ROOTED_DIRT).requiresTool(), Blocks.GRASS_BLOCK));
    public static final Block ASH_BLOCK = registerBlock("ash_block",
            new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.SAND)));
    public static final Block ROCK_BLOCK = registerBlock("rock_block",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).sounds(BlockSoundGroup.STONE)));
    public static final Block RAINBOW_MARBLE_BLOCK = registerBlock("rainbow_marble",
            new Block(FabricBlockSettings.copyOf(Blocks.ANDESITE).sounds(BlockSoundGroup.STONE)));

    public static final Block SPIDERWEB_BLOCK = registerBlock("spiderweb_block",
            new SpiderWebBlock(FabricBlockSettings.copyOf(Blocks.COBWEB).sounds(BlockSoundGroup.SOUL_SAND)));





    // huge shrooms
    public static final Block TOXIC_SHROOM_STEM = registerBlock("toxic_shroom_stem",
            new ToxicMushroomBlock(FabricBlockSettings.copyOf(Blocks.MUSHROOM_STEM).mapColor(MapColor.WHITE_GRAY)
                    .instrument(Instrument.BASS).strength(0.2f).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block GENERIC_SHROOM_STEM = registerBlock("generic_shroom_stem",
            new ToxicMushroomBlock(FabricBlockSettings.copyOf(Blocks.MUSHROOM_STEM).mapColor(MapColor.WHITE_GRAY)
                    .instrument(Instrument.BASS).strength(0.2f).sounds(BlockSoundGroup.WOOD).burnable()));

    public static final Block ORANGE_SHROOM_BLOCK = registerBlock("orange_shroom_block",
            new OrangeMushroomBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK)
                    .mapColor(MapColor.ORANGE).instrument(Instrument.BASS).strength(0.2f).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block YELLOW_SHROOM_BLOCK = registerBlock("yellow_shroom_block",
            new YellowMushroomBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK)
                    .mapColor(MapColor.YELLOW).instrument(Instrument.BASS).strength(0.2f).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block TOXIC_SHROOM_BLOCK = registerBlock("toxic_shroom_block",
            new ToxicMushroomBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK)
                    .mapColor(MapColor.LIME).instrument(Instrument.BASS).strength(0.2f).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block BLUE_SHROOM_BLOCK = registerBlock("blue_shroom_block",
            new BlueMushroomBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK)
                    .mapColor(MapColor.BLUE).instrument(Instrument.BASS).strength(0.2f).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block PURPLE_SHROOM_BLOCK = registerBlock("purple_shroom_block",
            new PurpleMushroomBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK)
                    .mapColor(MapColor.PURPLE).instrument(Instrument.BASS).strength(0.2f).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block PINK_SHROOM_BLOCK = registerBlock("pink_shroom_block",
            new PinkMushroomBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK)
                    .mapColor(MapColor.PINK).instrument(Instrument.BASS).strength(0.2f).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block WHITE_SHROOM_BLOCK = registerBlock("white_shroom_block",
            new WhiteMushroomBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK)
                    .mapColor(MapColor.WHITE).instrument(Instrument.BASS).strength(0.2f).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block BLACK_SHROOM_BLOCK = registerBlock("black_shroom_block",
            new BlackMushroomBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK)
                    .mapColor(MapColor.BLACK).instrument(Instrument.BASS).strength(0.2f).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block BROWN_SHROOM_BLOCK = registerBlock("brown_shroom_block",
            new EarthMushroomBlock(FabricBlockSettings.copyOf(Blocks.BROWN_MUSHROOM_BLOCK)
                    .mapColor(MapColor.BROWN).instrument(Instrument.BASS).strength(0.2f).sounds(BlockSoundGroup.WOOD).burnable()));
    public static final Block RED_SHROOM_BLOCK = registerBlock("red_shroom_block",
            new CrimsonMushroomBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK)
                    .mapColor(MapColor.RED).instrument(Instrument.BASS).strength(0.2f).sounds(BlockSoundGroup.WOOD).burnable()));

    // PLANTS
    public static final Block ORANGE_MUSHROOM = registerBlock("orange_mushroom",
            new OrangeMushroomPlantblock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM).mapColor(MapColor.ORANGE)
                    .noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)
                    .postProcess(Blocks::always).pistonBehavior(PistonBehavior.DESTROY), ModConfiguredFeatures.SMALL_ORANGE_MUSHROOM_KEY));
    public static final Block YELLOW_MUSHROOM = registerBlock("yellow_mushroom",
            new YellowMushroomPlantblock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM).mapColor(MapColor.ORANGE)
                    .noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)
                    .postProcess(Blocks::always).pistonBehavior(PistonBehavior.DESTROY), ModConfiguredFeatures.SMALL_YELLOW_MUSHROOM_KEY));
    public static final Block TOXIC_MUSHROOM = registerBlock("toxic_mushroom",
            new ToxicMushroomPlantblock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM).mapColor(MapColor.LIME)
                    .noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)
                    .postProcess(Blocks::always).pistonBehavior(PistonBehavior.DESTROY), ModConfiguredFeatures.SMALL_TOXIC_MUSHROOM_KEY));
    public static final Block BLUE_MUSHROOM = registerBlock("blue_mushroom",
            new BlueMushroomPlantblock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM).mapColor(MapColor.BLUE)
                    .noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)
                    .postProcess(Blocks::always).pistonBehavior(PistonBehavior.DESTROY), ModConfiguredFeatures.SMALL_BLUE_MUSHROOM_KEY));
    public static final Block PURPLE_MUSHROOM = registerBlock("purple_mushroom",
            new PurpleMushroomPlantblock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM).mapColor(MapColor.PURPLE)
                    .noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)
                    .postProcess(Blocks::always).pistonBehavior(PistonBehavior.DESTROY), ModConfiguredFeatures.SMALL_PURPLE_MUSHROOM_KEY));
    public static final Block PINK_MUSHROOM = registerBlock("pink_mushroom",
            new PinkMushroomPlantblock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM).mapColor(MapColor.PINK)
                    .noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)
                    .postProcess(Blocks::always).pistonBehavior(PistonBehavior.DESTROY), ModConfiguredFeatures.SMALL_PINK_MUSHROOM_KEY));
    public static final Block WHITE_MUSHROOM = registerBlock("white_mushroom",
            new WhiteMushroomPlantblock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM).mapColor(MapColor.WHITE)
                    .noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)
                    .postProcess(Blocks::always).pistonBehavior(PistonBehavior.DESTROY), ModConfiguredFeatures.SMALL_WHITE_MUSHROOM_KEY));
    public static final Block BLACK_MUSHROOM = registerBlock("black_mushroom",
            new BlackMushroomPlantblock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM).mapColor(MapColor.BLACK)
                    .noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS)
                    .postProcess(Blocks::always).pistonBehavior(PistonBehavior.DESTROY), ModConfiguredFeatures.SMALL_BLACK_MUSHROOM_KEY));

    // PROPS
    public static final Block SKY_ICE_BLOCK = registerBlock("sky_ice_block",
            new SkyIceBlock(FabricBlockSettings.copyOf(Blocks.ICE).mapColor(MapColor.WHITE)
                    .strength(5.0f, 5.0f)
                    .sounds(BlockSoundGroup.GLASS).requiresTool()));
    public static final Block FROSTED_SKY_ICE_BLOCK = registerBlock("frosted_sky_ice_block",
            new FrostedSkyIceBlock(FabricBlockSettings.copyOf(Blocks.FROSTED_ICE).mapColor(MapColor.WHITE)
                    .sounds(BlockSoundGroup.GLASS).requiresTool()));
    public static final Block TIBERIUM_FIRE = registerBlock("tiberium_fire",
            new TiberiumFireBlock(FabricBlockSettings.copyOf(Blocks.FIRE).mapColor(MapColor.LIME).replaceable().noCollision().breakInstantly()
                    .luminance(state -> 15).sounds(BlockSoundGroup.WOOL).pistonBehavior(PistonBehavior.DESTROY)));
    //tiberium
    public static final Block TIBERIUM_BLOCK = registerBlock("tiberium_block", new
            TiberiumBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).mapColor(MapColor.LIME).sounds(ModSounds.TIBERIUM_BLOCK_SOUNDS).requiresTool()));
    public static final Block BUDDING_TIBERIUM = registerBlock("budding_tiberium", new BuddingTiberiumBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST).mapColor(MapColor.LIME).ticksRandomly().strength(1.5f).sounds(ModSounds.TIBERIUM_BLOCK_SOUNDS).luminance(state -> 3).requiresTool().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block TIBERIUM_CLUSTER = registerBlock("tiberium_cluster", new TiberiumClusterBlock(7, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.LIME).solid().nonOpaque().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f).luminance(state -> 7).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block LARGE_TIBERIUM_BUD = registerBlock("large_tiberium_bud", new TiberiumClusterBlock(5, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 5).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block MEDIUM_TIBERIUM_BUD = registerBlock("medium_tiberium_bud", new TiberiumClusterBlock(4, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 3).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block SMALL_TIBERIUM_BUD = registerBlock("small_tiberium_bud", new TiberiumClusterBlock(3, 4, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 1).pistonBehavior(PistonBehavior.DESTROY)));

    //fire
    public static final Block FIRE_CRYSTAL_BLOCK = registerBlock("fire_crystal_block",
            new FireCrystalBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).mapColor(MapColor.BRIGHT_RED)
                    .sounds(BlockSoundGroup.AMETHYST_CLUSTER).requiresTool()));
    public static final Block BUDDING_FIRE_CRYSTAL = registerBlock("budding_fire_crystal",
            new BuddingFireCrystalBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST).mapColor(MapColor.BRIGHT_RED)
                    .ticksRandomly().strength(1.5f).sounds(ModSounds.TIBERIUM_BLOCK_SOUNDS).luminance(state -> 3)
                    .requiresTool().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block FIRE_CRYSTAL_CLUSTER = registerBlock("fire_crystal_cluster",
            new FireCrystalClusterBlock(7, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.BRIGHT_RED)
                    .solid().nonOpaque().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f).luminance(state -> 7)
                    .pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block LARGE_FIRE_CRYSTAL_BUD = registerBlock("large_fire_crystal_bud",
            new FireCrystalClusterBlock(5, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 5).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block MEDIUM_FIRE_CRYSTAL_BUD = registerBlock("medium_fire_crystal_bud",
            new FireCrystalClusterBlock(4, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 3).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block SMALL_FIRE_CRYSTAL_BUD = registerBlock("small_fire_crystal_bud",
            new FireCrystalClusterBlock(3, 4, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 1).pistonBehavior(PistonBehavior.DESTROY)));
    // ice
    public static final Block ICE_CRYSTAL_BLOCK = registerBlock("ice_crystal_block",
            new IceCrystalBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).mapColor(MapColor.DIAMOND_BLUE)
                    .sounds(BlockSoundGroup.AMETHYST_CLUSTER).requiresTool()));
    public static final Block BUDDING_ICE_CRYSTAL = registerBlock("budding_ice_crystal",
            new BuddingIceCrystalBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST).mapColor(MapColor.DIAMOND_BLUE)
                    .ticksRandomly().strength(1.5f).sounds(ModSounds.TIBERIUM_BLOCK_SOUNDS).luminance(state -> 3)
                    .requiresTool().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block ICE_CRYSTAL_CLUSTER = registerBlock("ice_crystal_cluster",
            new IceCrystalClusterBlock(7, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.DIAMOND_BLUE)
                    .solid().nonOpaque().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f).luminance(state -> 7)
                    .pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block LARGE_ICE_CRYSTAL_BUD = registerBlock("large_ice_crystal_bud",
            new IceCrystalClusterBlock(5, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 5).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block MEDIUM_ICE_CRYSTAL_BUD = registerBlock("medium_ice_crystal_bud",
            new IceCrystalClusterBlock(4, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 3).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block SMALL_ICE_CRYSTAL_BUD = registerBlock("small_ice_crystal_bud",
            new IceCrystalClusterBlock(3, 4, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 1).pistonBehavior(PistonBehavior.DESTROY)));
    //exotic
    public static final Block EXOTIC_CRYSTAL_BLOCK = registerBlock("exotic_crystal_block",
            new ExoticCrystalBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).mapColor(MapColor.GOLD)
                    .sounds(BlockSoundGroup.AMETHYST_CLUSTER).requiresTool()));
    public static final Block BUDDING_EXOTIC_CRYSTAL = registerBlock("budding_exotic_crystal",
            new BuddingExoticCrystalBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST).mapColor(MapColor.GOLD)
                    .ticksRandomly().strength(1.5f).sounds(ModSounds.TIBERIUM_BLOCK_SOUNDS).luminance(state -> 3)
                    .requiresTool().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block EXOTIC_CRYSTAL_CLUSTER = registerBlock("exotic_crystal_cluster",
            new ExoticCrystalClusterBlock(7, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.GOLD)
                    .solid().nonOpaque().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f).luminance(state -> 7)
                    .pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block LARGE_EXOTIC_CRYSTAL_BUD = registerBlock("large_exotic_crystal_bud",
            new ExoticCrystalClusterBlock(5, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 5).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block MEDIUM_EXOTIC_CRYSTAL_BUD = registerBlock("medium_exotic_crystal_bud",
            new ExoticCrystalClusterBlock(4, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 3).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block SMALL_EXOTIC_CRYSTAL_BUD = registerBlock("small_exotic_crystal_bud",
            new ExoticCrystalClusterBlock(3, 4, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 1).pistonBehavior(PistonBehavior.DESTROY)));
    //quartz
    public static final Block QUARTZ_CRYSTAL_BLOCK = registerBlock("quartz_crystal_block",
            new QuartzCrystalBlock(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).mapColor(MapColor.WHITE)
                    .sounds(BlockSoundGroup.AMETHYST_CLUSTER).requiresTool()));
    public static final Block BUDDING_QUARTZ_CRYSTAL = registerBlock("budding_quartz_crystal",
            new BuddingQuartzCrystalBlock(FabricBlockSettings.copyOf(Blocks.BUDDING_AMETHYST).mapColor(MapColor.WHITE)
                    .ticksRandomly().strength(1.5f).sounds(BlockSoundGroup.AMETHYST_CLUSTER).luminance(state -> 3)
                    .requiresTool().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block QUARTZ_CRYSTAL_CLUSTER = registerBlock("quartz_crystal_cluster",
            new QuartzCrystalClusterBlock(7, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.WHITE)
                    .solid().nonOpaque().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f).luminance(state -> 7)
                    .pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block LARGE_QUARTZ_CRYSTAL_BUD = registerBlock("large_quartz_crystal_bud",
            new QuartzCrystalClusterBlock(5, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 5).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block MEDIUM_QUARTZ_CRYSTAL_BUD = registerBlock("medium_quartz_crystal_bud",
            new QuartzCrystalClusterBlock(4, 3, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 3).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block SMALL_QUARTZ_CRYSTAL_BUD = registerBlock("small_quartz_crystal_bud",
            new QuartzCrystalClusterBlock(3, 4, FabricBlockSettings.copyOf(Blocks.AMETHYST_CLUSTER)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).solid().luminance(state -> 1).pistonBehavior(PistonBehavior.DESTROY)));
    // ender crystal

    // water crystal

    public static final Block RAZORWIRE_BLOCK = registerBlock("razorwire_block",
            new RazorWireBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(8.0f,100.0f)));
    public static final Block SKYFORGE = registerBlock("skyforge",
            new SkyForgeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));
    public static final Block ASSEMBLY_STATION_BLOCK = registerBlock("assembly_station_block",
            new AssemblyStationBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    public static final Block EXAMPLE_BLOCK = registerBlock("example_block",
            new ExampleBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(5.0f,20.0f)));

    //registering blocks
    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(Signum.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Signum.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(Signum.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Signum.LOGGER.info("Registering ModBlocks for " + Signum.MOD_ID);
    }
}
