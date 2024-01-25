/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.lwjgl.system.linux.Stat;
import za.lana.signum.Signum;

public class ModEffects {
    public static final StatusEffect BURN_EFFECT = new BurnEffect(StatusEffectCategory.HARMFUL,16684867);
    public static final StatusEffect BURN_IMMUNE_EFFECT = new BurnImmunityEffect(StatusEffectCategory.BENEFICIAL,16684867);
    public static final StatusEffect FREEZE_EFFECT = new FreezeEffect(StatusEffectCategory.HARMFUL,4433150);
    public static final StatusEffect FREEZE_IMMUNE_EFFECT = new FreezeImmunityEffect(StatusEffectCategory.BENEFICIAL,4433150);
    public static final StatusEffect GRAVITY_EFFECT = new GravityEffect(StatusEffectCategory.HARMFUL,7334395);
    public static final StatusEffect GRAVITY_IMMUNE_EFFECT = new GravityImmunityEffect(StatusEffectCategory.BENEFICIAL,7334395);
    public static final StatusEffect SHOCK_EFFECT = new ShockEffect(StatusEffectCategory.HARMFUL,13234936);
    public static final StatusEffect SHOCK_IMMUNE_EFFECT = new ShockImmunityEffect(StatusEffectCategory.BENEFICIAL,13234936);

    public static final StatusEffect TIBERIUM_POISON = new TiberiumPoison(StatusEffectCategory.HARMFUL,4456006);
    public static final StatusEffect TIBERIUM_POISON_IMMUNE = new TiberiumImmunityEffect(StatusEffectCategory.BENEFICIAL,4456006);

    public static final StatusEffect TRANSMUTE_EFFECT = new TransmuteEffect(StatusEffectCategory.HARMFUL,16700995);
    public static final StatusEffect TRANSMUTE_IMMUNE_EFFECT = new TransmuteImmunityEffect(StatusEffectCategory.BENEFICIAL,16700995);


    public static final StatusEffect HEALING_EFFECT = new HealingEffect(StatusEffectCategory.BENEFICIAL,13234936);
    public static final StatusEffect DEATH_EFFECT = new DeathEffect(StatusEffectCategory.HARMFUL,6053985);
    public static final StatusEffect TELEPORT_EFFECT = new TeleportEffect(StatusEffectCategory.NEUTRAL,12796926);

    // public static final StatusEffect PETRIFY_EFFECT = new PetrifyEffect(StatusEffectCategory.HARMFUL,6053985);
    // public static final StatusEffect DROWN_EFFECT = new DrownEffect(StatusEffectCategory.HARMFUL,4159204);


    public static void RegisterEffects() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "burn_effect"), BURN_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "burn_immune_effect"), BURN_IMMUNE_EFFECT);

        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "freeze_effect"), FREEZE_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "freeze_immune_effect"), FREEZE_IMMUNE_EFFECT);

        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "gravity_effect"), GRAVITY_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "gravity_immune_effect"), GRAVITY_IMMUNE_EFFECT);

        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "shock_effect"), SHOCK_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "shock_immune_effect"), SHOCK_IMMUNE_EFFECT);

        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "tiberium_poison"), TIBERIUM_POISON);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "tiberium_poison_immune"), TIBERIUM_POISON_IMMUNE);

        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "transmute_effect"), TRANSMUTE_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "transmute_immune_effect"), TRANSMUTE_IMMUNE_EFFECT);

        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "healing_effect"), HEALING_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "death_effect"), DEATH_EFFECT);

        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "teleport_effect"), TELEPORT_EFFECT);

        Signum.LOGGER.info("Registering ModEffects for " + Signum.MOD_ID);


    }

}
