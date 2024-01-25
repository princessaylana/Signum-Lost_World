package za.lana.signum.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.item.ModItems;
import za.lana.signum.item.custom.CoinItem;
import za.lana.signum.tag.ModBlockTags;
import za.lana.signum.util.ImplementedInventory;
import za.lana.signum.util.ZedVendingItem;

import java.util.List;


// TODO README BELOW - LANA
/**
 Check if input is empty
 Take coin Item if its inserted and add value, display in screen
 refund the amount in copper coins by output
 Generates pricelist (item, value, rarity) by randomly choosing from a pricelist
 rarity will be the chance it has to be chosen
 checks which the player selects and subtracts it when player push button
 dispenses chosen item
 resets or restocks pricelist every midnight
 countdown till next restock
 sounds, input, purchase, change, dispense
 Basically similar to the vending Machines from Borderlands
 lana
**/

public class ZedVendingBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private static final int INPUT_SLOT = 0;
    private static final int CHANGE_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;
    private int slotCredits;
    private final int itemPrice = ZedVendingItem.getVendingValue();
    private final float rarity = ZedVendingItem.getVendingRarity();
    private static int changeAmount;
    private final DefaultedList<ItemStack> inventory;

    public ZedVendingBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ZED_VENDING_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(8, ItemStack.EMPTY);
        slotCredits = getInputSlotValue();
        Item selectedItem = ZedVendingItem.getVendingItem();
        List<ZedVendingItem> itemList = List.of(ZedVendingItem.getList());
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.signum.zed_block.screen " + "Credit:" + slotCredits);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        // TODO
        return null;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("zedvending.slotcredits", slotCredits);
        nbt.putInt("zedvending.changeamount", changeAmount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        slotCredits = nbt.getInt("zedvending.slotcredits");
        changeAmount = nbt.getInt("zedvending.changeamount");
    }
    // STOCK ITEMS
    private void stockItems(){
        // check list
        // random choose items
        // insert into product slots
        // check the time
        // slots 3-8 is dispensing

        // need to randomly iterate the items
        ItemStack item1 = new ItemStack((ItemConvertible) ZedVendingItem.ZED_BEEF);
        int price1 = itemPrice;
        ItemStack item2 = new ItemStack((ItemConvertible) ZedVendingItem.ZED_BREAD);
        int price2 = itemPrice;
        ItemStack item3 = new ItemStack((ItemConvertible) ZedVendingItem.ZED_CHICKEN);
        int price3 = itemPrice;
        ItemStack item4 = new ItemStack((ItemConvertible) ZedVendingItem.ZED_MUTTON);
        int price4 = itemPrice;
        ItemStack item5 = new ItemStack((ItemConvertible) ZedVendingItem.ZED_PORKCHOP);
        int price5 = itemPrice;
        //
        setStack(3, item1);
        setStack(4, item2);
        setStack(5, item3);
        setStack(6, item4);
        setStack(7, item5);
        //display the itemPrice in screen

    }

    // GET THE COIN AMOUNT OF INPUT STACK // COPPER COIN = 1 Credit // IRON COIN = 2 Credits // GOLD COIN = 5 Credits
    private int getInputSlotValue(){
        ItemStack itemStack = this.getStack(INPUT_SLOT);
        if (itemStack.isIn(ModBlockTags.COIN_ITEMS)){
            // TAKES THE MONEY
            this.removeStack(INPUT_SLOT,1);
            return CoinItem.getTotalCoinValue(itemStack);
        } else if (!itemStack.isIn(ModBlockTags.COIN_ITEMS)) {
            // NEED TO ADD MESSAGE HERE TO CLIENT TO INSERT COIN ONLY
            return 0;
        }
        return 0;
    }

    private boolean isOutputSlotEmpty() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean thereIsChange(){
        return slotCredits - itemPrice >= 0;
    }

    private void giveChange(){
        ItemStack changeStack = new ItemStack(ModItems.COPPER_COIN, changeAmount);
        this.setStack(CHANGE_SLOT, changeStack);
    }

    private void refundCredits(){
        this.removeStack(INPUT_SLOT,1);
        //CHANGE GIVEN
        ItemStack refundStack = new ItemStack(ModItems.COPPER_COIN, slotCredits);
        this.setStack(CHANGE_SLOT, refundStack);
    }

    private void dispenseItem() {
        ItemStack outputStack =  new ItemStack(ZedVendingItem.getVendingItem(), itemPrice);
        this.setStack(OUTPUT_SLOT, outputStack);
    }
    public void tick(World world, BlockPos pos, BlockState state) {
        // TODO MAIN
        if (world.isClient) {
            return;
        }
        if (world.getTimeOfDay() == 24000L) {
            stockItems();
        }
        if (this.getStack(INPUT_SLOT).isIn(ModBlockTags.COIN_ITEMS)) {
            // get coins and save amount
            getInputSlotValue();
            // select product

            //dispense product
            if (slotCredits >= itemPrice) {
                if(thereIsChange()){
                    giveChange();
                    //
                    if (isOutputSlotEmpty()){
                        dispenseItem();
                    }
                }
                if (!thereIsChange() && isOutputSlotEmpty()){
                    dispenseItem();
                }
            }
        }
        // restock items once a day
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

}
