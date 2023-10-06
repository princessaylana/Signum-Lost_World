/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import za.lana.signum.effect.ModEffects;

public class ModFoodComponents {
    //public static final FoodComponent MORPHINE = new FoodComponent.Builder().hunger(6).saturationModifier(0.9f).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 50, 1), 1.0f).build();

    public static final FoodComponent TOXIC_SOUP = new ModFoodComponents().createStew(6)
            .statusEffect(new StatusEffectInstance(ModEffects.TIBERIUM_POISON, 80), 1.0f).build();

    private static FoodComponent.Builder createStew(int hunger) {
        return new FoodComponent.Builder().hunger(hunger).saturationModifier(0.6f);
    }
}
