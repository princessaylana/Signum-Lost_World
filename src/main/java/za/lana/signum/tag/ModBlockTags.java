package za.lana.signum.tag;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import za.lana.signum.Signum;

public final class ModBlockTags {

    public static final TagKey<Block> FLOATER_LANDING_BLOCKS = register(RegistryKeys.BLOCK,"floater_landing_blocks");
    public static final TagKey<Block> AIRSHIP_LANDING_BLOCKS = register(RegistryKeys.BLOCK,"airship_landing_blocks");
    //public static final TagKey<Block> CARGODRONE_LANDING_BLOCKS = register(RegistryKeys.BLOCK,"cargodrone_landing_blocks");
    public static final TagKey<Item> COIN_ITEMS = register(RegistryKeys.ITEM,"coin_items");


    private static<T> TagKey<T> register(RegistryKey<? extends Registry<T>> registryKey, String id) {
        return TagKey.of(registryKey, new Identifier(Signum.MOD_ID ,id));
    }
    public static void registerModBlockTags() {
        Signum.LOGGER.info("Registering Tags for  " + Signum.MOD_ID);
    }
}
