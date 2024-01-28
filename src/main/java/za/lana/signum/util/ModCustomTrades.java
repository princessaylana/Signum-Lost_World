package za.lana.signum.util;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import za.lana.signum.item.ModItems;
import za.lana.signum.villager.ModVillagers;

/** All villagers and trades has three levels for a start
 * level 1 - copper
 * level 3 - iron
 * level 5 - gold
 */

public class ModCustomTrades {
    public static void registerCustomTrades() {
        // BAKER
        // BANKER
        TradeOfferHelper.registerVillagerOffers(ModVillagers.BAKER, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.COPPER_COIN, 1),
                            new ItemStack(ModItems.YELLOW_JELLY, 2),
                            32, 8, 0.075f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.COPPER_COIN, 1),
                            new ItemStack(ModItems.WAFFLES, 2),
                            32, 8, 0.075f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.COPPER_COIN, 1),
                            new ItemStack(ModItems.CINNAMON_ROLL, 1),
                            32, 8, 0.075f));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.BAKER, 3,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.IRON_COIN, 1),
                            new ItemStack(ModItems.RED_JELLY, 2),
                            32, 10, 0.075f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.IRON_COIN, 1),
                            new ItemStack(ModItems.STRAWBERRY_WAFFLES, 2),
                            32, 10, 0.075f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.IRON_COIN, 1),
                            new ItemStack(ModItems.GLAZED_CINNAMON_ROLL, 1),
                            32, 10, 0.075f));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.BAKER, 5,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GOLD_COIN, 1),
                            new ItemStack(ModItems.PURPLE_JELLY, 2),
                            32, 12, 0.075f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GOLD_COIN, 1),
                            new ItemStack(ModItems.ICE_CREAM_WAFFLES, 2),
                            32, 12, 0.075f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GOLD_COIN, 1),
                            new ItemStack(ModItems.CHOCOLATE_SWISS_ROLL, 1),
                            32, 12, 0.075f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GOLD_COIN, 1),
                            new ItemStack(ModItems.CROISSANT_SANDWICH, 1),
                            32, 12, 0.075f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GOLD_COIN, 1),
                            new ItemStack(ModItems.RAINBOW_CAKE, 1),
                            32, 12, 0.075f));
                });

        // BANKER
        TradeOfferHelper.registerVillagerOffers(ModVillagers.BANKER, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.COPPER_INGOT, 1),
                            new ItemStack(ModItems.COPPER_COIN, 9),
                            16, 8, 0.075f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.COPPER_COIN, 9),
                            new ItemStack(Items.COPPER_INGOT, 1),
                            16, 8, 0.075f));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.BANKER, 3,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.IRON_INGOT, 1),
                            new ItemStack(ModItems.IRON_COIN, 9),
                            16, 10, 0.075f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.IRON_COIN, 9),
                            new ItemStack(Items.IRON_INGOT, 1),
                            16, 10, 0.075f));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.BANKER, 5,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.GOLD_INGOT, 1),
                            new ItemStack(ModItems.GOLD_COIN, 9),
                            16, 24, 0.075f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.COPPER_COIN,5),
                            new ItemStack(ModItems.GOLD_COIN, 1),
                            16, 12, 0.075f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GOLD_COIN, 9),
                            new ItemStack(Items.GOLD_INGOT, 1),
                            16, 6, 0.075f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GOLD_COIN, 9),
                            new ItemStack(ModItems.COPPER_COIN, 5),
                            16, 12, 0.075f));
                });
        // DR ZED
        TradeOfferHelper.registerVillagerOffers(ModVillagers.DR_ZED, 1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.COPPER_COIN, 5),
                            new ItemStack(Items.BREAD, 2),
                            16, 8, 0.075f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.COPPER_COIN, 6),
                            new ItemStack(Items.COOKED_CHICKEN, 3),
                            16, 8, 0.075f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.COPPER_COIN, 6),
                            new ItemStack(Items.COOKED_PORKCHOP, 2),
                            16, 8, 0.075f));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.DR_ZED, 3,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.IRON_COIN, 2),
                            new ItemStack(Items.BREAD, 3),
                            16, 10, 0.075f));
                    factories.add((entity, random) -> new TradeOffer(

                            new ItemStack(ModItems.IRON_COIN, 2),
                            new ItemStack(Items.COOKED_CHICKEN, 3),
                            16, 10, 0.075f));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.IRON_COIN, 3),
                            new ItemStack(Items.COOKED_BEEF, 2),
                            16, 10, 0.075f));
                });
        TradeOfferHelper.registerVillagerOffers(ModVillagers.DR_ZED, 5,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GOLD_COIN, 1),
                            new ItemStack(Items.COOKIE, 8),
                            32, 12, 0.075f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GOLD_COIN, 1),
                            new ItemStack(Items.BREAD, 4),
                            32, 12, 0.075f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GOLD_COIN, 1),
                            new ItemStack(Items.COOKED_BEEF, 3),
                            32, 12, 0.075f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.GOLD_COIN, 1),
                            new ItemStack(Items.COOKED_MUTTON, 3),
                            32, 12, 0.075f));
                });


        //
    }
}
