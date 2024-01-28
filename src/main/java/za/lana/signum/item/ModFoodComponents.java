/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import za.lana.signum.effect.ModEffects;

public class ModFoodComponents {
    //public static final FoodComponent MORPHINE = new FoodComponent.Builder().hunger(6).saturationModifier(0.9f).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 50, 1), 1.0f).build();
    public static final FoodComponent TOXIC_SOUP = createStew()
            .statusEffect(new StatusEffectInstance(ModEffects.TIBERIUM_POISON, 80), 1.0f).build();
    public static final FoodComponent MIXED_MUSHROOM_STEW = createStew()
            .statusEffect(new StatusEffectInstance(ModEffects.HEALING_EFFECT, 80), 1.0f).build();
    public static final FoodComponent YELLOW_JELLY = new FoodComponent.Builder().hunger(2).saturationModifier(0.25f).statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 40, 1), 1.0f).build();
    public static final FoodComponent WAFFLES = new FoodComponent.Builder().hunger(2).saturationModifier(0.25f).statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 40, 1), 1.0f).build();
    public static final FoodComponent CINNAMON_ROLL = new FoodComponent.Builder().hunger(3).saturationModifier(0.25f).statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 40, 1), 1.0f).build();

    public static final FoodComponent RED_JELLY = new FoodComponent.Builder().hunger(3).saturationModifier(0.25f).statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 80, 1), 1.0f).build();
    public static final FoodComponent STRAWBERRY_WAFFLES = new FoodComponent.Builder().hunger(3).saturationModifier(0.25f).statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 80, 1), 1.0f).build();
    public static final FoodComponent GLAZED_CINNAMON_ROLL = new FoodComponent.Builder().hunger(4).saturationModifier(0.25f).statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 80, 1), 1.0f).build();

    public static final FoodComponent PURPLE_JELLY = new FoodComponent.Builder().hunger(4).saturationModifier(0.25f).statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 160, 1), 1.0f).build();
    public static final FoodComponent ICE_CREAM_WAFFLES = new FoodComponent.Builder().hunger(4).saturationModifier(0.25f).statusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 160, 1), 1.0f).build();
    public static final FoodComponent CHOCOLATE_SWISS_ROLL = new FoodComponent.Builder().hunger(5).saturationModifier(0.25f).statusEffect(new StatusEffectInstance(StatusEffects.LUCK, 160, 1), 1.0f).build();
    public static final FoodComponent CROISSANT_SANDWICH = new FoodComponent.Builder().hunger(5).saturationModifier(0.25f).statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 160, 1), 1.0f).build();
    public static final FoodComponent RAINBOW_CAKE = new FoodComponent.Builder().hunger(5).saturationModifier(0.25f).statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 160, 1), 1.0f).build();




    private static FoodComponent.Builder createStew() {
        return new FoodComponent.Builder().hunger(6).saturationModifier(0.6f);
    }
    // FUTURE USE
    private static FoodComponent.Builder createSpecialFood() {
        return new FoodComponent.Builder().hunger(2).saturationModifier(0.8f);
    }
}
