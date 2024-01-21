package za.lana.signum.item;


import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import za.lana.signum.Signum;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    // New armors added below
    STEEL("steel", 42, new int[] { 3, 8, 6, 3 }, 40,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.2f, 0.1f, () ->
            Ingredient.ofItems(ModItems.STEEL_INGOT)),
    BLACK_DIAMOND("black_diamond", 45, new int[] { 4, 9, 7, 4 }, 40,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.5f, 1.2f, () ->
            Ingredient.ofItems(ModItems.BLACK_DIAMOND_CRYSTAL)),
    ELEMENT_ZERO("element_zero", 45, new int[] { 4, 9, 7, 4 }, 40,
    SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.5f, 1.2f, () ->
            Ingredient.ofItems(ModItems.ELEMENT_ZERO_CRYSTAL)),
    EXOTIC("exotic", 45, new int[] { 4, 9, 7, 4 }, 40,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.5f, 1.2f, () ->
            Ingredient.ofItems(ModItems.EXOTIC_CRYSTAL)),
    FIRE("fire", 45, new int[] { 4, 9, 7, 4 }, 40,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.5f, 1.2f, () ->
            Ingredient.ofItems(ModItems.FIRE_CRYSTAL)),
    ICE("ice", 45, new int[] { 4, 9, 7, 4 }, 40,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.5f, 1.2f, () ->
            Ingredient.ofItems(ModItems.ICE_CRYSTAL)),
    MOISSANITE("moissanite", 45, new int[] { 4, 9, 7, 4 }, 40,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.5f, 1.2f, () ->
            Ingredient.ofItems(ModItems.MOISSANITE_CRYSTAL)),
    QUARTZ("quartz", 45, new int[] { 4, 9, 7, 4 }, 40,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.5f, 1.2f, () ->
            Ingredient.ofItems(ModItems.QUARTZ_CRYSTAL)),
    TIBERIUM("tiberium", 45, new int[] { 4, 9, 7, 4 }, 40,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.5f, 1.2f, () ->
            Ingredient.ofItems(ModItems.TIBERIUM_CRYSTAL));


    //
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;
    private static final int[] BASE_DURABILITY = { 11, 16, 15, 13 };

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound,
                      float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }
    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }
    @Override
    public int getProtection(ArmorItem.Type type) {
        return protectionAmounts[type.ordinal()];
    }
    @Override
    public int getEnchantability() {
        return this.enchantability;
    }
    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }
    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
    @Override
    public String getName() {
        return Signum.MOD_ID + ":" + this.name;
    }
    @Override
    public float getToughness() {
        return this.toughness;
    }
    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
