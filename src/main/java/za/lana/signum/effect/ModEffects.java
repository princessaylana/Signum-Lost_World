/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.effect;
/**
 * SIGNUM
 * MIT License
 * Lana
 * */
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
    public static final StatusEffect TIBERIUM_POISON = new TiberiumPoison(StatusEffectCategory.HARMFUL,4456006);
    public static final StatusEffect FREEZE_EFFECT = new FreezeEffect(StatusEffectCategory.HARMFUL,4433150);
    public static final StatusEffect TRANSMUTE_EFFECT = new TransmuteEffect(StatusEffectCategory.HARMFUL,16700995);
    public static final StatusEffect BURN_EFFECT = new BurnEffect(StatusEffectCategory.HARMFUL,16684867);

    public static final StatusEffect SHOCK_EFFECT = new ShockEffect(StatusEffectCategory.HARMFUL,13234936);
    // public static final StatusEffect CURE_EFFECT = new CureEffect(StatusEffectCategory.BENEFICIAL,13234936);
    public static final StatusEffect TELEPORT_EFFECT = new TeleportEffect(StatusEffectCategory.NEUTRAL,12796926);
    // public static final StatusEffect GRAVITY_EFFECT = new GravityEffect(StatusEffectCategory.HARMFUL,7334395);
    // public static final StatusEffect PETRIFY_EFFECT = new PetrifyEffect(StatusEffectCategory.HARMFUL,6053985);
    // public static final StatusEffect DROWN_EFFECT = new DrownEffect(StatusEffectCategory.HARMFUL,4159204);


    public static void RegisterEffects() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "tiberium_poison"), TIBERIUM_POISON);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "freeze_effect"), FREEZE_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "teleport_effect"), TELEPORT_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "burn_effect"), BURN_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "transmute_effect"), TRANSMUTE_EFFECT);

        Signum.LOGGER.info("Registering ModEffects for " + Signum.MOD_ID);


    }

}
