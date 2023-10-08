/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.world;

import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import za.lana.signum.Signum;
import za.lana.signum.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_ORANGE_MUSHROOM_KEY = registerKey("huge_orange_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_ORANGE_MUSHROOM_KEY = registerKey("patch_small_orange_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_YELLOW_MUSHROOM_KEY = registerKey("huge_yellow_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_YELLOW_MUSHROOM_KEY = registerKey("patch_small_yellow_mushroom");
   public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_TOXIC_MUSHROOM_KEY = registerKey("huge_toxic_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_TOXIC_MUSHROOM_KEY = registerKey("patch_small_toxic_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_BLUE_MUSHROOM_KEY = registerKey("huge_blue_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BLUE_MUSHROOM_KEY = registerKey("patch_small_blue_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_PURPLE_MUSHROOM_KEY = registerKey("huge_purple_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_PURPLE_MUSHROOM_KEY = registerKey("patch_small_purple_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_PINK_MUSHROOM_KEY = registerKey("huge_pink_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_PINK_MUSHROOM_KEY = registerKey("patch_small_pink_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_WHITE_MUSHROOM_KEY = registerKey("huge_white_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_WHITE_MUSHROOM_KEY = registerKey("patch_small_pink_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_BLACK_MUSHROOM_KEY = registerKey("huge_black_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SMALL_BLACK_MUSHROOM_KEY = registerKey("patch_small_black_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_SRED_MUSHROOM_KEY = registerKey("huge_sred_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> HUGE_SBROWN_MUSHROOM_KEY = registerKey("huge_sbrown_mushroom");



    public static final RegistryKey<ConfiguredFeature<?, ?>> MANGANESE_ORE_KEY = registerKey("manganese_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MIOSSANITE_ORE_KEY = registerKey("moissanite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ELEMENT_ZERO_ORE_KEY = registerKey("element_zero_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DEEPSLATE_MANGANESE_ORE_KEY = registerKey("deepslate_manganese_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DEEPSLATE_MIOSSANITE_ORE_KEY = registerKey("deepslate_manganese_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DEEPSLATE_ELEMENT_ZERO_ORE_KEY = registerKey("deepslate_manganese_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BUDDING_QUARTZ_CRYSTAL_KEY = registerKey("budding_quartz_crystal");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BUDDING_EXOTIC_CRYSTAL_KEY = registerKey("budding_exotic_crystal");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BUDDING_ICE_CRYSTAL_KEY = registerKey("budding_ice_crystal");
    // NETHER ORES
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHERRACK_MANGANESE_ORE_KEY = registerKey("netherrack_manganese_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BUDDING_FIRE_CRYSTAL_KEY = registerKey("budding_fire_crystal");
    //  END ORES
    public static final RegistryKey<ConfiguredFeature<?, ?>> ENDSTONE_MANGANESE_ORE_KEY = registerKey("endstone_manganese_ore");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceables = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest endReplaceables = new BlockMatchRuleTest(Blocks.END_STONE);



        //replace ores with dimension blocks
        List<OreFeatureConfig.Target> overworldManganeseOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.MANGANESE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_MANGANESE_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> overworldMoissaniteOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.MOISSANITE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_MOISSANITE_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> overworldElementZeroOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.ELEMENT_ZERO_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_ELEMENT_ZERO_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> overworldExoticCrystals =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.BUDDING_EXOTIC_CRYSTAL.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.BUDDING_EXOTIC_CRYSTAL.getDefaultState()));
        List<OreFeatureConfig.Target> overworldQuartzCrystals =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.BUDDING_QUARTZ_CRYSTAL.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.BUDDING_QUARTZ_CRYSTAL.getDefaultState()));
        List<OreFeatureConfig.Target> overworldIceCrystals =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.BUDDING_ICE_CRYSTAL.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.BUDDING_ICE_CRYSTAL.getDefaultState()));

        // NETHER ORES
        List<OreFeatureConfig.Target> netherManganeseOres =
                List.of(OreFeatureConfig.createTarget(netherReplaceables, ModBlocks.NETHERRACK_MANGANESE_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> netherFireCrystals =
                List.of(OreFeatureConfig.createTarget(netherReplaceables, ModBlocks.BUDDING_FIRE_CRYSTAL.getDefaultState()));

        // END ORES
        List<OreFeatureConfig.Target> endManganeseOres =
                List.of(OreFeatureConfig.createTarget(endReplaceables, ModBlocks.ENDSTONE_MANGANESE_ORE.getDefaultState()));

        //register and ore sizes
        register(context, MANGANESE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldManganeseOres, 19));
        register(context, DEEPSLATE_MANGANESE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldManganeseOres, 22));
        register(context, MIOSSANITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldMoissaniteOres, 17));
        register(context, DEEPSLATE_MIOSSANITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldMoissaniteOres, 19));
        register(context, ELEMENT_ZERO_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldElementZeroOres, 17));
        register(context, DEEPSLATE_ELEMENT_ZERO_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldElementZeroOres, 19));
        register(context, BUDDING_EXOTIC_CRYSTAL_KEY, Feature.ORE, new OreFeatureConfig(overworldExoticCrystals, 17));
        register(context, BUDDING_QUARTZ_CRYSTAL_KEY, Feature.ORE, new OreFeatureConfig(overworldQuartzCrystals, 17));
        register(context, BUDDING_ICE_CRYSTAL_KEY, Feature.ORE, new OreFeatureConfig(overworldIceCrystals, 17));

        register(context, NETHERRACK_MANGANESE_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherManganeseOres, 12));
        register(context, BUDDING_FIRE_CRYSTAL_KEY, Feature.ORE, new OreFeatureConfig(netherFireCrystals, 7));

        register(context, ENDSTONE_MANGANESE_ORE_KEY, Feature.ORE, new OreFeatureConfig(endManganeseOres, 12));

        // register mushrooms
        register(context, SMALL_ORANGE_MUSHROOM_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.ORANGE_MUSHROOM))));
        register(context, HUGE_ORANGE_MUSHROOM_KEY, Feature.HUGE_BROWN_MUSHROOM,
                new HugeMushroomFeatureConfig(BlockStateProvider.of(ModBlocks.ORANGE_SHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)),
                        BlockStateProvider.of(ModBlocks.GENERIC_SHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false)
                                .with(MushroomBlock.DOWN, false)), 3));

        register(context, SMALL_YELLOW_MUSHROOM_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.YELLOW_MUSHROOM))));
        register(context, HUGE_YELLOW_MUSHROOM_KEY, Feature.HUGE_RED_MUSHROOM,
                new HugeMushroomFeatureConfig(BlockStateProvider.of(ModBlocks.ORANGE_SHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)),
                        BlockStateProvider.of(ModBlocks.GENERIC_SHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false)
                                .with(MushroomBlock.DOWN, false)), 2));

        register(context, SMALL_TOXIC_MUSHROOM_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.TOXIC_MUSHROOM))));
        register(context, HUGE_TOXIC_MUSHROOM_KEY, Feature.HUGE_BROWN_MUSHROOM,
                new HugeMushroomFeatureConfig(BlockStateProvider.of(ModBlocks.TOXIC_SHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)),
                        BlockStateProvider.of(ModBlocks.TOXIC_SHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false)
                                .with(MushroomBlock.DOWN, false)), 3));

        register(context, SMALL_BLUE_MUSHROOM_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.BLUE_MUSHROOM))));
        register(context, HUGE_BLUE_MUSHROOM_KEY, Feature.HUGE_RED_MUSHROOM,
                new HugeMushroomFeatureConfig(BlockStateProvider.of(ModBlocks.BLUE_SHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)),
                        BlockStateProvider.of(ModBlocks.GENERIC_SHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false)
                                .with(MushroomBlock.DOWN, false)), 2));

        register(context, SMALL_PURPLE_MUSHROOM_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.PURPLE_MUSHROOM))));
        register(context, HUGE_PURPLE_MUSHROOM_KEY, Feature.HUGE_RED_MUSHROOM,
                new HugeMushroomFeatureConfig(BlockStateProvider.of(ModBlocks.PINK_SHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)),
                        BlockStateProvider.of(ModBlocks.GENERIC_SHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false)
                                .with(MushroomBlock.DOWN, false)), 2));

        register(context, SMALL_PINK_MUSHROOM_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.PINK_MUSHROOM))));
        register(context, HUGE_PINK_MUSHROOM_KEY, Feature.HUGE_RED_MUSHROOM,
                new HugeMushroomFeatureConfig(BlockStateProvider.of(ModBlocks.PINK_SHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)),
                        BlockStateProvider.of(ModBlocks.GENERIC_SHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false)
                                .with(MushroomBlock.DOWN, false)), 2));

        register(context, SMALL_WHITE_MUSHROOM_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.WHITE_MUSHROOM))));
        register(context, HUGE_WHITE_MUSHROOM_KEY, Feature.HUGE_BROWN_MUSHROOM,
                new HugeMushroomFeatureConfig(BlockStateProvider.of(ModBlocks.WHITE_SHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)),
                        BlockStateProvider.of(ModBlocks.GENERIC_SHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false)
                                .with(MushroomBlock.DOWN, false)), 3));

        register(context, SMALL_BLACK_MUSHROOM_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.BLACK_MUSHROOM))));
        register(context, HUGE_BLACK_MUSHROOM_KEY, Feature.HUGE_RED_MUSHROOM,
                new HugeMushroomFeatureConfig(BlockStateProvider.of(ModBlocks.BLACK_SHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)),
                        BlockStateProvider.of(ModBlocks.GENERIC_SHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false)
                                .with(MushroomBlock.DOWN, false)), 2));

        new HugeMushroomFeatureConfig(BlockStateProvider.of(ModBlocks.BROWN_SHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)),
                BlockStateProvider.of(ModBlocks.GENERIC_SHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false)
                        .with(MushroomBlock.DOWN, false)), 3);
        new HugeMushroomFeatureConfig(BlockStateProvider.of(ModBlocks.RED_SHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false)),
                BlockStateProvider.of(ModBlocks.GENERIC_SHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false)
                        .with(MushroomBlock.DOWN, false)), 3);



    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(Signum.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
