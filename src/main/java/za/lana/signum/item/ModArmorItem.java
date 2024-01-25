package za.lana.signum.item;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import za.lana.signum.effect.ModEffects;

import java.util.Map;

// ONLY WORKS FOR CUSTOM ARMORS, AND NOT VANILLA TYPES
public class ModArmorItem extends ArmorItem {
    private static final Map<ArmorMaterial, StatusEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, StatusEffectInstance>())
                    .put(ModArmorMaterials.BLACK_DIAMOND, new StatusEffectInstance(StatusEffects.HASTE, 400, 1,
                            false, false, true))
                    .put(ModArmorMaterials.ELEMENT_ZERO, new StatusEffectInstance(ModEffects.GRAVITY_IMMUNE_EFFECT, 400, 1,
                            false, false, true))
                    .put(ModArmorMaterials.EXOTIC, new StatusEffectInstance(ModEffects.TRANSMUTE_IMMUNE_EFFECT, 400, 1,
                            false, false, true))
                    .put(ModArmorMaterials.FIRE, new StatusEffectInstance(ModEffects.BURN_IMMUNE_EFFECT, 400, 1,
                            false, false, true))
                    .put(ModArmorMaterials.ICE, new StatusEffectInstance(ModEffects.FREEZE_IMMUNE_EFFECT, 400, 1,
                            false, false, true))
                    .put(ModArmorMaterials.MOISSANITE, new StatusEffectInstance(ModEffects.HEALING_EFFECT, 400, 1,
                            false, false, true))
                    .put(ModArmorMaterials.QUARTZ, new StatusEffectInstance(ModEffects.SHOCK_IMMUNE_EFFECT, 400, 1,
                            false, false, true))
                    .put(ModArmorMaterials.TIBERIUM, new StatusEffectInstance(ModEffects.TIBERIUM_POISON_IMMUNE, 400, 1,
                            false, false, true))

                    .build();

    public ModArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()) {
            if(entity instanceof PlayerEntity player && hasFullSuitOfArmorOn(player)) {
                evaluateArmorEffects(player);
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void evaluateArmorEffects(PlayerEntity player) {
        for (Map.Entry<ArmorMaterial, StatusEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            StatusEffectInstance mapStatusEffect = entry.getValue();

            if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
            }
        }
    }

    private void addStatusEffectForMaterial(PlayerEntity player, ArmorMaterial mapArmorMaterial, StatusEffectInstance mapStatusEffect) {
        boolean hasPlayerEffect = player.hasStatusEffect(mapStatusEffect.getEffectType());

        if(hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
            player.addStatusEffect(new StatusEffectInstance(mapStatusEffect));
        }
    }

    private boolean hasFullSuitOfArmorOn(PlayerEntity player) {
        ItemStack boots = player.getInventory().getArmorStack(0);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack breastplate = player.getInventory().getArmorStack(2);
        ItemStack helmet = player.getInventory().getArmorStack(3);

        return !helmet.isEmpty() && !breastplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }

    // LOOP BELOW IS NEEDED, ELSE IT WILL CRASH
    private boolean hasCorrectArmorOn(ArmorMaterial material, PlayerEntity player) {
        for (ItemStack armorStack: player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem)player.getInventory().getArmorStack(0).getItem());
        ArmorItem leggings = ((ArmorItem)player.getInventory().getArmorStack(1).getItem());
        ArmorItem breastplate = ((ArmorItem)player.getInventory().getArmorStack(2).getItem());
        ArmorItem helmet = ((ArmorItem)player.getInventory().getArmorStack(3).getItem());

        return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                leggings.getMaterial() == material && boots.getMaterial() == material;
    }
}
