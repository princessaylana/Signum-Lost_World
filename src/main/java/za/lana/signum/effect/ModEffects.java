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
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.lwjgl.system.linux.Stat;
import za.lana.signum.Signum;

public class ModEffects {
    public static final StatusEffect TIBERIUM_POISON = new TiberiumPoison(StatusEffectCategory.HARMFUL,4456006);
    public static final StatusEffect TELEPORT_EFFECT = new TeleportEffect(StatusEffectCategory.NEUTRAL,7396351);


    public static void RegisterEffects() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "tiberium_poison"), TIBERIUM_POISON);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Signum.MOD_ID, "teleport_effect"), TELEPORT_EFFECT);

        Signum.LOGGER.info("Registering ModEffects for " + Signum.MOD_ID);


    }

}
