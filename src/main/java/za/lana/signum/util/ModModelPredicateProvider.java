package za.lana.signum.util;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import za.lana.signum.item.ModItems;

public class ModModelPredicateProvider {
    public static void registerModModels() {

        ModelPredicateProviderRegistry.register(ModItems.STEEL_SHIELD, new Identifier("blocking"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
    }

}
