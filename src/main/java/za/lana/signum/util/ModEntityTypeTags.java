package za.lana.signum.util;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModEntityTypeTags {
    public static final TagKey<EntityType<?>> TIBERIUM_TYPE = ModEntityTypeTags.of("tiberium_type");
    public static final TagKey<EntityType<?>> FREEZE_TYPE = ModEntityTypeTags.of("freeze_type");


    private ModEntityTypeTags() {
    }

    private static TagKey<EntityType<?>> of(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(id));
    }
}
