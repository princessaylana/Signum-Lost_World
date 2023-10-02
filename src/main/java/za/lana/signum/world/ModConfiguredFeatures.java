/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.world;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import za.lana.signum.Signum;
import za.lana.signum.block.ModBlocks;
import za.lana.signum.world.dimension.ModDimensions;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> MANGANESE_ORE_KEY = registerKey("manganese_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MIOSSANITE_ORE_KEY = registerKey("moissanite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ELEMENT_ZERO_ORE_KEY = registerKey("element_zero_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DEEPSLATE_MANGANESE_ORE_KEY = registerKey("deepslate_manganese_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DEEPSLATE_MIOSSANITE_ORE_KEY = registerKey("deepslate_manganese_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DEEPSLATE_ELEMENT_ZERO_ORE_KEY = registerKey("deepslate_manganese_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BUDDING_QUARTZ_CRYSTAL_KEY = registerKey("budding_quartz_crystal");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BUDDING_EXOTIC_CRYSTAL_KEY = registerKey("budding_exotic_crystal");
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

        // NETHER ORES
        List<OreFeatureConfig.Target> netherManganeseOres =
                List.of(OreFeatureConfig.createTarget(netherReplaceables, ModBlocks.NETHERRACK_MANGANESE_ORE.getDefaultState()));

        List<OreFeatureConfig.Target> netherFireCrystals =
                List.of(OreFeatureConfig.createTarget(netherReplaceables, ModBlocks.BUDDING_FIRE_CRYSTAL.getDefaultState()));

        // END ORES
        List<OreFeatureConfig.Target> endManganeseOres =
                List.of(OreFeatureConfig.createTarget(endReplaceables, ModBlocks.ENDSTONE_MANGANESE_ORE.getDefaultState()));

        //register and ore sizes
        register(context, MANGANESE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldManganeseOres, 9));
        register(context, DEEPSLATE_MANGANESE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldManganeseOres, 12));
        register(context, MIOSSANITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldMoissaniteOres, 7));
        register(context, DEEPSLATE_MIOSSANITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldMoissaniteOres, 9));
        register(context, ELEMENT_ZERO_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldElementZeroOres, 7));
        register(context, DEEPSLATE_ELEMENT_ZERO_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldElementZeroOres, 9));
        register(context, BUDDING_EXOTIC_CRYSTAL_KEY, Feature.ORE, new OreFeatureConfig(overworldExoticCrystals, 7));
        register(context, BUDDING_QUARTZ_CRYSTAL_KEY, Feature.ORE, new OreFeatureConfig(overworldQuartzCrystals, 7));

        register(context, NETHERRACK_MANGANESE_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherManganeseOres, 12));
        register(context, BUDDING_FIRE_CRYSTAL_KEY, Feature.ORE, new OreFeatureConfig(netherFireCrystals, 7));

        register(context, ENDSTONE_MANGANESE_ORE_KEY, Feature.ORE, new OreFeatureConfig(endManganeseOres, 12));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(Signum.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
