/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class VaultScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private static final int INVENTORY_SIZE = 27;
    // has
    public VaultScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(27));
    }
    public VaultScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlers.VAULT_SCREENHANDLER, syncId);
        checkSize(inventory, 27);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        // BLOCK ENTITY INVENTORY
        int k;
        int l;
        for(k = 0; k < 3; ++k) {
            for(l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + k * 9, 8 + l * 18, 18 + k * 18));
            }
        }
        // PLAYER INVENTORY
        for(k = 0; k < 3; ++k) {
            for(l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + k * 9 + 9, 8 + l * 18, 84 + k * 18));
            }
        }
        // PLAYER HOTBAR
        for(k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

    }

    //
    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
    // shiftclick move
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }
    //
    /**
    private void addInventorySlotsPanel(Inventory inventory){
        for (int a = 0; a < 3; ++a) {
            for (int c = 0; c < 9; ++c) {
                this.addSlot(new Slot(inventory,
                        c + a * 9 + 9,
                        8 + c * 18,
                        18 + a * 18));
            }
        }
    }
    //
    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory,
                        l + i * 9 + 9,
                        8 + l * 18,
                        84 + i * 18));
            }
        }
    }
    //
    private void addPlayerHotbar(PlayerInventory hotBar) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(hotBar, i, 8 + i * 18, 142));
        }
    }
     **/
}