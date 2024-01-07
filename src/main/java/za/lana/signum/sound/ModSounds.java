/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;

public class ModSounds {
    public static SoundEvent SNOWY_WIND = registerSoundEvent("snowy_wind");
    public static SoundEvent PIDGEON_CALL = registerSoundEvent("pidgeon_call");
    public static SoundEvent AIRBALLOON_DOWN = registerSoundEvent("airballoon_down");
    public static SoundEvent TIBERIUM_AMBIENT = registerSoundEvent("tiberium_ambient");
    public static SoundEvent TIBERIUM_BREAK = registerSoundEvent("tiberium_break");
    public static SoundEvent TIBERIUM_WALK = registerSoundEvent("tiberium_walk");
    public static SoundEvent TIBERIUM_PLACE = registerSoundEvent("tiberium_place");
    public static SoundEvent TIBERIUM_HIT = registerSoundEvent("tiberium_hit");
    public static SoundEvent GHOST_TELEPORT = registerSoundEvent("ghost_teleport");
    public static SoundEvent GHOST_ANGRY = registerSoundEvent("ghost_angry");
    public static SoundEvent GHOST_ATTACK = registerSoundEvent("ghost_attack");
    public static SoundEvent GHOST_HURT = registerSoundEvent("ghost_hurt");
    public static SoundEvent GHOST_DIE = registerSoundEvent("ghost_die");
    public static SoundEvent GHOST_AMBIENT = registerSoundEvent("ghost_ambient");
    public static SoundEvent FLOATER_AMBIENT = registerSoundEvent("floater_ambient");
    public static SoundEvent FLOATER_DIE = registerSoundEvent("floater_die");
    public static SoundEvent FLOATER_HURT = registerSoundEvent("floater_hurt");
    public static SoundEvent FLOATER_SHOOT = registerSoundEvent("floater_shoot");
    public static SoundEvent FLOATER_TELEPORT = registerSoundEvent("floater_teleport");
    public static SoundEvent FLOATER_WARNS = registerSoundEvent("floater_warns");

    public static SoundEvent CARGODRONE_FLY= registerSoundEvent("cargodrone_fly");
    public static SoundEvent CARGODRONE_HURT = registerSoundEvent("cargodrone_hurt");
    public static SoundEvent CARGODRONE_IDLE = registerSoundEvent("cargodrone_idle");
    //
    public static SoundEvent CARGODRONE_CANT_LAND = registerSoundEvent("cargodrone_cant_land");
    public static SoundEvent CARGODRONE_FLYING_TO_LANDING = registerSoundEvent("cargodrone_flying_to_landing");
    public static SoundEvent CARGODRONE_FOUND_STATION = registerSoundEvent("cargodrone_found_station");
    public static SoundEvent CARGODRONE_FOUND_TARGETPOS = registerSoundEvent("cargodrone_found_targetpos");
    public static SoundEvent CARGODRONE_LANDED = registerSoundEvent("cargodrone_landed");
    public static SoundEvent CARGODRONE_TAKEOFF = registerSoundEvent("cargodrone_takeoff");
    public static SoundEvent CARGODRONE_TRYING_LAND = registerSoundEvent("cargodrone_trying_land");
    //

    public static final BlockSoundGroup TIBERIUM_BLOCK_SOUNDS = new BlockSoundGroup(1f, 1f,
            ModSounds.TIBERIUM_BREAK, ModSounds.TIBERIUM_WALK, ModSounds.TIBERIUM_PLACE,
            ModSounds.TIBERIUM_HIT, ModSounds.TIBERIUM_WALK);

    private static SoundEvent registerSoundEvent(String name){
        Identifier id = new Identifier(Signum.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
    public static void registerModSounds() {
        Signum.LOGGER.info("Registering ModSounds for " + Signum.MOD_ID);
    }


}
