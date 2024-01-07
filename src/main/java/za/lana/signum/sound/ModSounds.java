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

    public static SoundEvent AIRBALLOON_DOWN = registerSoundEvent("airballoon_down");
    // Cursedwolf
    public static SoundEvent CURSEDWOLF_AMBIENT1 = registerSoundEvent("cursedwolf_ambient1");
    public static SoundEvent CURSEDWOLF_AMBIENT2 = registerSoundEvent("cursedwolf_ambient2");
    public static SoundEvent CURSEDWOLF_AMBIENT3 = registerSoundEvent("cursedwolf_ambient3");
    public static SoundEvent CURSEDWOLF_ATTACK1 = registerSoundEvent("cursedwolf_attack1");
    public static SoundEvent CURSEDWOLF_ATTACK2 = registerSoundEvent("cursedwolf_attack2");
    public static SoundEvent CURSEDWOLF_ATTACK3 = registerSoundEvent("cursedwolf_attack3");
    public static SoundEvent CURSEDWOLF_HOWL1 = registerSoundEvent("cursedwolf_howl1");
    public static SoundEvent CURSEDWOLF_HOWL2 = registerSoundEvent("cursedwolf_howl2");
    public static SoundEvent CURSEDWOLF_HOWL3 = registerSoundEvent("cursedwolf_howl3");
    public static SoundEvent CURSEDWOLF_HURT1 = registerSoundEvent("cursedwolf_hurt1");
    public static SoundEvent CURSEDWOLF_HURT2 = registerSoundEvent("cursedwolf_hurt2");
    public static SoundEvent CURSEDWOLF_HURT3 = registerSoundEvent("cursedwolf_hurt3");
    //
    public static SoundEvent FLOATER_AMBIENT = registerSoundEvent("floater_ambient");
    public static SoundEvent FLOATER_DIE = registerSoundEvent("floater_die");
    public static SoundEvent FLOATER_HURT = registerSoundEvent("floater_hurt");
    public static SoundEvent FLOATER_SHOOT = registerSoundEvent("floater_shoot");
    public static SoundEvent FLOATER_TELEPORT = registerSoundEvent("floater_teleport");
    public static SoundEvent FLOATER_WARNS = registerSoundEvent("floater_warns");
    public static SoundEvent GHOST_TELEPORT = registerSoundEvent("ghost_teleport");
    public static SoundEvent GHOST_ANGRY = registerSoundEvent("ghost_angry");
    public static SoundEvent GHOST_ATTACK = registerSoundEvent("ghost_attack");
    public static SoundEvent GHOST_HURT = registerSoundEvent("ghost_hurt");
    public static SoundEvent GHOST_DIE = registerSoundEvent("ghost_die");
    public static SoundEvent GHOST_AMBIENT = registerSoundEvent("ghost_ambient");
    public static SoundEvent PIDGEON_CALL = registerSoundEvent("pidgeon_call");
    public static SoundEvent SKELETON_DEATH1 = registerSoundEvent("skeleton_death1");
    public static SoundEvent SKELETON_DEATH2 = registerSoundEvent("skeleton_death2");
    public static SoundEvent SKELETON_DEATH3 = registerSoundEvent("skeleton_death3");
    public static SoundEvent SKELETON_HURT1 = registerSoundEvent("skeleton_hurt1");
    public static SoundEvent SKELETON_HURT2 = registerSoundEvent("skeleton_hurt2");
    public static SoundEvent SKELETON_HURT3 = registerSoundEvent("skeleton_hurt3");
    public static SoundEvent SKELETON_RAISE = registerSoundEvent("skeleton_raise");
    public static SoundEvent SKELETON_WALK1 = registerSoundEvent("skeleton_walk1");
    public static SoundEvent SKELETON_WALK2 = registerSoundEvent("skeleton_walk2");
    public static SoundEvent SKELETON_WALK3 = registerSoundEvent("skeleton_walk3");
    public static SoundEvent TIBERIUM_AMBIENT = registerSoundEvent("tiberium_ambient");
    public static SoundEvent TIBERIUM_BREAK = registerSoundEvent("tiberium_break");
    public static SoundEvent TIBERIUM_WALK = registerSoundEvent("tiberium_walk");
    public static SoundEvent TIBERIUM_PLACE = registerSoundEvent("tiberium_place");
    public static SoundEvent TIBERIUM_HIT = registerSoundEvent("tiberium_hit");
    public static SoundEvent SNOWY_WIND = registerSoundEvent("snowy_wind");
    public static SoundEvent WIZARD_AMBIENT1 = registerSoundEvent("wizard_ambient1");
    public static SoundEvent WIZARD_AMBIENT2 = registerSoundEvent("wizard_ambient2");
    public static SoundEvent WIZARD_AMBIENT3 = registerSoundEvent("wizard_ambient3");
    public static SoundEvent WIZARD_HELP1 = registerSoundEvent("wizard_help1");
    public static SoundEvent WIZARD_HELP2 = registerSoundEvent("wizard_help2");
    public static SoundEvent WIZARD_HELP3 = registerSoundEvent("wizard_help3");

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
