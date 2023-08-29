/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;

public class ModSounds {
    //public static final RegistryEntry.Reference<SoundEvent> AMBIENT_CAVE = SoundEvents.registerReference("ambient.cave");
    public static SoundEvent TIBERIUM_AMBIENT = registerSoundEvent("tiberium_ambient");
    public static SoundEvent TIBERIUM_BREAK = registerSoundEvent("tiberium_break");
    public static SoundEvent TIBERIUM_WALK = registerSoundEvent("tiberium_walk");
    public static SoundEvent TIBERIUM_PLACE = registerSoundEvent("tiberium_place");
    public static SoundEvent TIBERIUM_HIT = registerSoundEvent("tiberium_hit");

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
