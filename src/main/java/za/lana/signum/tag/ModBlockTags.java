package za.lana.signum.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;

public class ModBlockTags {
    public static final TagKey<Block> FLOATER_LANDING_BLOCKS = ModBlockTags.of("floater_landing_blocks");


    private ModBlockTags() {
    }

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(Signum.MOD_ID));
    }
}
