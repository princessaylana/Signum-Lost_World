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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import za.lana.signum.recipe.SkyForgeRecipe;
import za.lana.signum.screen.SkyForgeScreenHandler;
import za.lana.signum.util.ImplementedInventory;

import java.util.Objects;
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
    private static final int FUEL_SLOT = 0;
    private static final int INPUT_SLOT = 1;
    private static final int INPUT_SLOT2 = 2;
    private static final int OUTPUT_SLOT = 3;

    public SkyForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SKYFORGE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> SkyForgeBlockEntity.this.progress;
                    case 1 -> SkyForgeBlockEntity.this.maxProgress;
                    case 2 -> SkyForgeBlockEntity.this.fuelTime;
                    case 3 -> SkyForgeBlockEntity.this.maxFuelTime;
                    default -> 0;
                };
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

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient){
            return;
        }

        SkyForgeBlockEntity entity = this;
        if(isConsumingFuel(entity)) {
            entity.fuelTime--;
            entity.forceUpdateAllStates();
        }

        if(this.hasRecipe()) {
            if(hasFuelInFuelSlot(entity) && !isConsumingFuel(entity)) {
                entity.consumeFuel();
            }
            if(isConsumingFuel(entity) && isOutputSlotEmptyOrReceivable()) {
                //need to add change of blockstate on block to LIT
                entity.progress++;
                if(entity.progress > entity.maxProgress) {
                    craftItem(entity);
                }
            }
        } else {
            entity.resetProgress();
            markDirty(world, pos, state);

        }
    }
    private static boolean hasFuelInFuelSlot(SkyForgeBlockEntity entity) {
        return !entity.getStack(0).isEmpty();
    }

    private static boolean isConsumingFuel(SkyForgeBlockEntity entity) {
        return entity.fuelTime > 0;
    }


    private boolean hasRecipe() {
        Optional<RecipeEntry<SkyForgeRecipe>> recipe = getCurrentRecipe();

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(recipe.get().value().getResult(null))
                && canInsertItemIntoOutputSlot(recipe.get().value().getResult(null).getItem());
    }
    private Optional<RecipeEntry<SkyForgeRecipe>> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        for(int i = 0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }
        return Objects.requireNonNull(getWorld()).getRecipeManager().getFirstMatch(SkyForgeRecipe.Type.INSTANCE, inv, getWorld());
        //return getWorld().getRecipeManager().getFirstMatch(SkyForgeRecipe.Type.INSTANCE, inv, getWorld());
    }

    private void craftItem(SkyForgeBlockEntity entity) {
        Optional<RecipeEntry<SkyForgeRecipe>> recipe = getCurrentRecipe();

        this.removeStack(1,1);
        this.removeStack(2,1);

        this.setStack(OUTPUT_SLOT, new ItemStack(recipe.get().value().getResult(null).getItem(),
                getStack(OUTPUT_SLOT).getCount() + recipe.get().value().getResult(null).getCount()));
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        //return inventory.getStack(3).getMaxCount() > inventory.getStack(3).getCount();
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
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
