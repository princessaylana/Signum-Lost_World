/**
 * SIGNUM
 * MIT License
 * Lana
 * 2023
 * */
package za.lana.signum.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.List;

public class SkyForgeRecipe implements Recipe<SimpleInventory> {
    private final ItemStack output;
    private final List<Ingredient> recipeItems;
    private float experience = 0;
    private int cookTime = 0;

    public SkyForgeRecipe(List<Ingredient> ingredients, ItemStack itemStack) {
        //this.id = id;
        //this.output = output;
        //this.recipeItems = recipeItems;
        this.experience = experience;
        this.cookTime = cookTime;

        this.output = itemStack;
        this.recipeItems = ingredients;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient()) { return false; }
        //checks the slots for first two ingredients
        if(recipeItems.get(0).test(inventory.getStack(1))) {
            return recipeItems.get(1).test(inventory.getStack(2));
        }
        return false;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }
    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return output;
    }

    public float getExperience() {
        return this.experience;
    }
    public int getCookTime() {
        return this.cookTime;
    }
    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(recipeItems);
        return list;
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SkyForgeRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "skyforge";
    }
    public static class Serializer implements RecipeSerializer<SkyForgeRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "skyforge";
        // this is the name given in the json file
        public static final Codec<SkyForgeRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
                validateAmount(Ingredient.DISALLOW_EMPTY_CODEC, 9).fieldOf("ingredients").forGetter(SkyForgeRecipe::getIngredients),
                RecipeCodecs.CRAFTING_RESULT.fieldOf("output").forGetter(r -> r.output)
        ).apply(in, SkyForgeRecipe::new));

        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int max) {
            return Codecs.validate(Codecs.validate(
                    delegate.listOf(), list -> list.size() > max ? DataResult.error(() -> "Recipe has too many ingredients!") : DataResult.success(list)
            ), list -> list.isEmpty() ? DataResult.error(() -> "Recipe has no ingredients!") : DataResult.success(list));
        }
        @Override
        public Codec<SkyForgeRecipe> codec() {
            return CODEC;
        }

        @Override
        public SkyForgeRecipe read(PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }
           // inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));
            ItemStack output = buf.readItemStack();
            return new SkyForgeRecipe(inputs, output);
        }
        @Override
        public void write(PacketByteBuf buf, SkyForgeRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.getResult(null));
        }
    }
    public ItemStack getOutput() {
        return output.copy();
    }
}
