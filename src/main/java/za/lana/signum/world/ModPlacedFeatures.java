/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import za.lana.signum.Signum;
import za.lana.signum.block.ModBlocks;

/**IMPORTANT README
 * ores are placed high, so they will spawn on our sky dimension,
 * and possibly very rare in the overworld if a mountain exceeds 144 height,
 * */
public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> SMALL_ORANGE_MUSHROOM_PLACED = registerKey("small_orange_mushroom_placed");
    public static final RegistryKey<PlacedFeature> SMALL_YELLOW_MUSHROOM_PLACED = registerKey("small_yellow_mushroom_placed");
    public static final RegistryKey<PlacedFeature> SMALL_TOXIC_MUSHROOM_PLACED = registerKey("small_toxic_mushroom_placed");
    public static final RegistryKey<PlacedFeature> SMALL_BLUE_MUSHROOM_PLACED = registerKey("small_blue_mushroom_placed");
    public static final RegistryKey<PlacedFeature> SMALL_PURPLE_MUSHROOM_PLACED = registerKey("small_purple_mushroom_placed");
    public static final RegistryKey<PlacedFeature> SMALL_PINK_MUSHROOM_PLACED = registerKey("small_pink_mushroom_placed");
    public static final RegistryKey<PlacedFeature> SOULWOOD_PLACED = registerKey("soulwood_placed");

    public static final RegistryKey<PlacedFeature> MANGANESE_ORE_PLACED_KEY = registerKey("manganese_ore_placed");
    public static final RegistryKey<PlacedFeature> BLACK_DIAMOND_ORE_PLACED_KEY = registerKey("black_diamond_ore_placed");
    public static final RegistryKey<PlacedFeature> MOISSANITE_ORE_PLACED_KEY = registerKey("moissanite_ore_placed");
    public static final RegistryKey<PlacedFeature> ELEMENT_ZERO_ORE_PLACED_KEY = registerKey("element_zero_ore_placed");

    public static final RegistryKey<PlacedFeature> DEEPSLATE_MANGANESE_ORE_PLACED_KEY = registerKey("deepslate_manganese_ore_placed");
    public static final RegistryKey<PlacedFeature> DEEPSLATE_BLACK_DIAMOND_ORE_PLACED_KEY = registerKey("deepslate_black_diamond_ore_placed");
    public static final RegistryKey<PlacedFeature> DEEPSLATE_MOISSANITE_ORE_PLACED_KEY = registerKey("deepslate_moissanite_ore_placed");
    public static final RegistryKey<PlacedFeature> DEEPSLATE_ELEMENT_ZERO_ORE_PLACED_KEY = registerKey("deepslate_moissanite_ore_placed");
    public static final RegistryKey<PlacedFeature> EXOTIC_CRYSTAL_PLACED_KEY = registerKey("exotic_crystal_placed");
    public static final RegistryKey<PlacedFeature> ICE_CRYSTAL_PLACED_KEY = registerKey("ice_crystal_placed");

    public static final RegistryKey<PlacedFeature> NETHERRACK_MANGANESE_ORE_PLACED_KEY = registerKey("netherrack_manganese_ore_placed");
    public static final RegistryKey<PlacedFeature> FIRE_CRYSTAL_PLACED_KEY = registerKey("fire_crystal_placed");
    public static final RegistryKey<PlacedFeature> QUARTZ_CRYSTAL_PLACED_KEY = registerKey("quartz_crystal_placed");
    public static final RegistryKey<PlacedFeature> ENDSTONE_MANGANESE_ORE_PLACED_KEY = registerKey("endstone_manganese_ore_placed");


    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, SMALL_ORANGE_MUSHROOM_PLACED,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SMALL_ORANGE_MUSHROOM_KEY),
                ModPlacedFeatures.mushroomModifiers(64, null));
        register(context, SMALL_YELLOW_MUSHROOM_PLACED,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SMALL_YELLOW_MUSHROOM_KEY),
                ModPlacedFeatures.mushroomModifiers(64, null));
        register(context, SMALL_TOXIC_MUSHROOM_PLACED,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SMALL_TOXIC_MUSHROOM_KEY),
                ModPlacedFeatures.mushroomModifiers(64, null));
        register(context, SMALL_BLUE_MUSHROOM_PLACED,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SMALL_BLUE_MUSHROOM_KEY),
                ModPlacedFeatures.mushroomModifiers(64, null));
        register(context, SMALL_PURPLE_MUSHROOM_PLACED,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SMALL_PURPLE_MUSHROOM_KEY),
                ModPlacedFeatures.mushroomModifiers(64, null));
        register(context, SMALL_PINK_MUSHROOM_PLACED,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SMALL_PINK_MUSHROOM_KEY),
                ModPlacedFeatures.mushroomModifiers(64, null));


        register(context, SOULWOOD_PLACED, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SOULWOOD_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(
                        PlacedFeatures.createCountExtraModifier(1, 0.1f, 2), ModBlocks.SOULWOOD_SAPLING));

        register(context, MANGANESE_ORE_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MANGANESE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(19,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(144), YOffset.fixed(384))));
        register(context, MOISSANITE_ORE_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MIOSSANITE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(17,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(144), YOffset.fixed(384))));
        register(context, BLACK_DIAMOND_ORE_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BLACK_DIAMOND_ORE_KEY),
                ModOrePlacement.modifiersWithCount(17,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(144), YOffset.fixed(384))));
        register(context, ELEMENT_ZERO_ORE_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ELEMENT_ZERO_ORE_KEY),
                ModOrePlacement.modifiersWithCount(17,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(144), YOffset.fixed(384))));
        register(context, EXOTIC_CRYSTAL_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BUDDING_EXOTIC_CRYSTAL_KEY),
                ModOrePlacement.modifiersWithCount(17,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(144), YOffset.fixed(384))));
        register(context, QUARTZ_CRYSTAL_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BUDDING_QUARTZ_CRYSTAL_KEY),
                ModOrePlacement.modifiersWithCount(17,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(144), YOffset.fixed(384))));
        register(context, ICE_CRYSTAL_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BUDDING_ICE_CRYSTAL_KEY),
                ModOrePlacement.modifiersWithCount(17,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(144), YOffset.fixed(384))));

        register(context, DEEPSLATE_MANGANESE_ORE_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.DEEPSLATE_MANGANESE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(22,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(144), YOffset.fixed(292))));
        register(context, DEEPSLATE_MOISSANITE_ORE_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.DEEPSLATE_MIOSSANITE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(19,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(144), YOffset.fixed(292))));
        register(context, DEEPSLATE_BLACK_DIAMOND_ORE_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.DEEPSLATE_BLACK_DIAMOND_ORE_KEY),
                ModOrePlacement.modifiersWithCount(19,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(144), YOffset.fixed(292))));
        register(context, DEEPSLATE_ELEMENT_ZERO_ORE_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ELEMENT_ZERO_ORE_KEY),
                ModOrePlacement.modifiersWithCount(19,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(144), YOffset.fixed(292))));

        register(context, NETHERRACK_MANGANESE_ORE_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.NETHERRACK_MANGANESE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(12,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

        register(context, FIRE_CRYSTAL_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BUDDING_FIRE_CRYSTAL_KEY),
                ModOrePlacement.modifiersWithCount(7,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

        register(context, ENDSTONE_MANGANESE_ORE_PLACED_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.ENDSTONE_MANGANESE_ORE_KEY),
                ModOrePlacement.modifiersWithCount(15,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));

    }
    private static List<PlacementModifier> mushroomModifiers(int chance, @Nullable PlacementModifier modifier) {
        ImmutableList.Builder builder = ImmutableList.builder();
        if (modifier != null) {
            builder.add(modifier);
        }
        if (chance != 0) {
            builder.add(RarityFilterPlacementModifier.of(chance));
        }
        builder.add(SquarePlacementModifier.of());
        builder.add(PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP);
        builder.add(BiomePlacementModifier.of());
        return builder.build();
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(Signum.MOD_ID, name));
    }
    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                                                                   RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                                                                   PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
