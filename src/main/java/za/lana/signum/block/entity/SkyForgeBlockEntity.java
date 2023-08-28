/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.block.entity;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.recipe.SkyForgeRecipe;
import za.lana.signum.screen.SkyForgeScreenHandler;
import za.lana.signum.util.ImplementedInventory;

import java.util.Optional;

import static za.lana.signum.block.custom.SkyForgeBlock.LIT;

public class SkyForgeBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    //Amount of inventory slots
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int fuelTime = 0;
    private int maxFuelTime = 4;
    private float experience = 0;
    private int cookTime = 0;

    public SkyForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SKYFORGE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return SkyForgeBlockEntity.this.progress;
                    case 1: return SkyForgeBlockEntity.this.maxProgress;
                    case 2: return SkyForgeBlockEntity.this.fuelTime;
                    case 3: return SkyForgeBlockEntity.this.maxFuelTime;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: SkyForgeBlockEntity.this.progress = value; break;
                    case 1: SkyForgeBlockEntity.this.maxProgress = value; break;
                    case 2: SkyForgeBlockEntity.this.fuelTime = value; break;
                    case 3: SkyForgeBlockEntity.this.maxFuelTime = value; break;
                }
            }

            public int size() {
                return 4;
            }
        };
    }


    public void forceUpdateAllStates() {
        assert world != null;
        BlockState state = world.getBlockState(pos);
        if (state.get(Properties.LIT) != maxFuelTime > 0) {
            world.setBlockState(pos, state.with(Properties.LIT, maxFuelTime > 0), 3);
            if (state.get(Properties.LIT) != maxFuelTime < 0) {
                world.setBlockState(pos, state.cycle (LIT));}
        }
    }


    @Override
    public Text getDisplayName() {
        return Text.literal("Sky Forge");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new SkyForgeScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("skyforge.progress", progress);
        nbt.putInt("skyforge.fuelTime", fuelTime);
        nbt.putInt("skyforge.maxFuelTime", maxFuelTime);
        nbt.putInt("skyforge.cookTime", cookTime);
        nbt.putInt("skyforge.experience", (int) experience);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
        progress = nbt.getInt("skyforge.progress");
        fuelTime = nbt.getInt("skyforge.fuelTime");
        maxFuelTime = nbt.getInt("skyforge.maxFuelTime");
        cookTime = nbt.getInt("skyforge.cookTime");
        experience = nbt.getInt("skyforge.experience");

    }
    private void consumeFuel() {
        if(!getStack(0).isEmpty()) {
            this.fuelTime = FuelRegistry.INSTANCE.get(this.removeStack(0, 1).getItem());
            this.maxFuelTime = this.fuelTime;
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, SkyForgeBlockEntity entity) {
        if(isConsumingFuel(entity)) {
            entity.fuelTime--;
            entity.forceUpdateAllStates();
        }

        if(hasRecipe(entity)) {
            if(hasFuelInFuelSlot(entity) && !isConsumingFuel(entity)) {
                entity.consumeFuel();
            }
            if(isConsumingFuel(entity)) {
                //need to add change of blockstate on block to LIT
                entity.progress++;
                if(entity.progress > entity.maxProgress) {
                    craftItem(entity);
                }
            }
        } else {
            entity.resetProgress();

        }
    }
    private static boolean hasFuelInFuelSlot(SkyForgeBlockEntity entity) {
        return !entity.getStack(0).isEmpty();
    }

    private static boolean isConsumingFuel(SkyForgeBlockEntity entity) {
        return entity.fuelTime > 0;
    }

    private static boolean hasRecipe(SkyForgeBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<SkyForgeRecipe> match = world.getRecipeManager()
                .getFirstMatch(SkyForgeRecipe.Type.INSTANCE, inventory, world);

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput());
    }

    private static void craftItem(SkyForgeBlockEntity entity) {
        World world = entity.world;
        SimpleInventory inventory = new SimpleInventory(entity.inventory.size());
        for (int i = 0; i < entity.inventory.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }
        Optional<SkyForgeRecipe> match = world.getRecipeManager()
                .getFirstMatch(SkyForgeRecipe.Type.INSTANCE, inventory, world);
        if(match.isPresent()) {
            entity.removeStack(1,1);
            entity.removeStack(2,1);
            entity.setStack(3, new ItemStack(match.get().getOutput().getItem(),
                    entity.getStack(3).getCount() + 1));
            entity.resetProgress();

        }
    }

    private void resetProgress() {
        this.progress = 0;
    }
    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, ItemStack output) {
        return inventory.getStack(3).getItem() == output.getItem() || inventory.getStack(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(3).getMaxCount() > inventory.getStack(3).getCount();
    }
}
