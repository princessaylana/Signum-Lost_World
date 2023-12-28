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

public class ExampleBlockEntity
        extends BlockEntity
        implements ImplementedInventory, NamedScreenHandlerFactory {
    public static final int INVENTORY_SIZE = 8;
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
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    @Override
    public Text getDisplayName() {
        // Using the block name as the screen title
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
        // GuiScreens.EXAMPLE_GUI
        //return new ExampleDescription(syncId, inventory, ScreenHandlerContext.create(world, pos));
        return new ExampleDescription(GuiScreens.EXAMPLE_GUI, syncId, inventory, ScreenHandlerContext.create(world, pos));
    }
}
