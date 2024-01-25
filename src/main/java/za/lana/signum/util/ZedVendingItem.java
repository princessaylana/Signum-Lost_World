package za.lana.signum.util;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Arrays;
import java.util.Iterator;

public class ZedVendingItem {
    public static final ZedVendingItem ZED_BEEF = new ZedVendingItem(Items.COOKED_BEEF, 2, 0.75f);
    public static final ZedVendingItem ZED_BREAD = new ZedVendingItem(Items.BREAD, 3, 0.75f);
    public static final ZedVendingItem ZED_CHICKEN = new ZedVendingItem(Items.COOKED_CHICKEN, 2, 0.75f);
    public static final ZedVendingItem ZED_MUTTON = new ZedVendingItem(Items.COOKED_MUTTON, 3, 0.50f);
    public static final ZedVendingItem ZED_PORKCHOP = new ZedVendingItem(Items.COOKED_PORKCHOP, 2, 0.75f);

    private static Item item;
    private static int value;
    private static float rarity;

    public ZedVendingItem(Item item, int value, float rarity){
        ZedVendingItem.item = item;
        ZedVendingItem.value = value;
        ZedVendingItem.rarity = rarity;
    }
    public Iterator<ZedVendingItem> vendingItemList(){
        ZedVendingItem[] zeditem = {ZED_BEEF, ZED_BREAD, ZED_CHICKEN, ZED_PORKCHOP, ZED_MUTTON};
        for (ZedVendingItem ignored : zeditem) {
            return Arrays.stream(zeditem).iterator();
        }
        return null;
    }


    public static Item getVendingItem(){
        return item;
    }
    public static int getVendingValue(){
        return value;
    }
    public static float getVendingRarity(){
        return rarity;
    }
    public static ZedVendingItem[] getList(){
        //ZedVendingItem[] zedVendingItemList = {ZED_BEEF, ZED_BREAD, ZED_CHICKEN, ZED_PORKCHOP, ZED_MUTTON};
        //return zedVendingItemList;
        return new ZedVendingItem[]{ZED_BEEF, ZED_BREAD, ZED_CHICKEN, ZED_PORKCHOP, ZED_MUTTON};
    }

}
