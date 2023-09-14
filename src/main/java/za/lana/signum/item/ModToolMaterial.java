/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public enum ModToolMaterial implements ToolMaterial {
    MANGANESE(4, 650, 5.0f, 3.5f, 26,
            () -> Ingredient.ofItems(ModItems.MANGANESE_INGOT)),
    SIGSTEEL(4, 1200, 8.5f, 3.5f, 26,
            () -> Ingredient.ofItems(ModItems.SIGSTEEL_INGOT)),
    SIGSTAINSTEEL(4, 2200, 9f, 3.5f, 26,
            () -> Ingredient.ofItems(ModItems.SIGSTAINSTEEL_INGOT)),
    // Staffs materials:
    TIBERIUM(4, 750, 5.5f, 3.5f, 26,
            () -> Ingredient.ofItems(ModItems.TIBERIUM_CRYSTAL)),
    ICE_CRYSTAL(4, 750, 5.5f, 3.5f, 26,
            () -> Ingredient.ofItems(ModItems.ICE_CRYSTAL)),
    FIRE_CRYSTAL(4, 750, 5.5f, 3.5f, 26,
            () -> Ingredient.ofItems(ModItems.FIRE_CRYSTAL)),
    QUARTZ_CRYSTAL(4, 750, 5.5f, 3.5f, 26,
            () -> Ingredient.ofItems(ModItems.QUARTZ_CRYSTAL)),
    MOISSANITE_CRYSTAL(4, 750, 5.5f, 3.5f, 26,
            () -> Ingredient.ofItems(ModItems.MOISSANITE_CRYSTAL)),
    ELEMENT_ZERO(4, 800, 7f, 3.5f, 26,
            () -> Ingredient.ofItems(ModItems.ELEMENT_ZERO_CRYSTAL));








    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attckDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attckDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
