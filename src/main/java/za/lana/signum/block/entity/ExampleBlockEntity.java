/**
 * SIGNUM
 * MIT License
 * Lana
 * */
package za.lana.signum.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import za.lana.signum.screen.gui.ExampleDescription;
import za.lana.signum.screen.gui.GuiScreens;
import za.lana.signum.util.ImplementedInventory;

import static za.lana.signum.screen.gui.ExampleDescription.*;

public class ExampleBlockEntity
        extends BlockEntity
        implements ImplementedInventory, NamedScreenHandlerFactory {
    public static final int INVENTORY_SIZE = 8;
    public int destinationX;
    public int destinationY;
    public int destinationZ;
    private NbtCompound nbtData;
    private final DefaultedList<ItemStack> inventory =  DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
    public ExampleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EXAMPLE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        //distance player has access to the inventory
        return pos.isWithinDistance(player.getBlockPos(), 4.5);
        //return ImplementedInventory.super.canPlayerUse(player);
    }
    @Override
    public void writeNbt(NbtCompound nbtData) {
        super.writeNbt(nbtData);
        Inventories.writeNbt(nbtData, inventory);
        //nbtData.putInt("destination.x", Integer.parseInt(ExampleDescription.getXpos("enterX")));
        //nbtData.putInt("destination.y", Integer.parseInt(ExampleDescription.getYpos("enterY")));
        //nbtData.putInt("destination.z", Integer.parseInt(ExampleDescription.getZpos("enterZ")));
    }

    @Override
    public void readNbt(NbtCompound nbtData) {
        super.readNbt(nbtData);
        Inventories.readNbt(nbtData, inventory);
        destinationX = nbtData.getInt("destination.x");
        destinationY = nbtData.getInt("destination.y");
        destinationZ = nbtData.getInt("destination.z");
    }

    @Override
    public Text getDisplayName() {
        // Using the block name as the screen title
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        return new ExampleDescription(GuiScreens.EXAMPLE_GUI, syncId, inventory, ScreenHandlerContext.create(world, pos));
    }

    /**
    public static void writeNbtBlockPos() {
        NbtCompound nbtData = new NbtCompound();
        nbtData.putInt("destination.x", ExampleDescription.convertXToInt(enterX));
        nbtData.putInt("destination.y", ExampleDescription.convertYToInt(enterY));
        nbtData.putInt("destination.z", ExampleDescription.convertZToInt(enterZ));
        // TESTING
        System.out.println("Entity NBT Written");
    }
     **/
}
