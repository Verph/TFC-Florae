package tfcflorae.common.recipes;

import org.jetbrains.annotations.Nullable;
import java.util.function.Supplier;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

import net.dries007.tfc.common.recipes.ISimpleRecipe;
import net.dries007.tfc.common.recipes.TypedRecipeSerializer;
import net.dries007.tfc.util.KnappingPattern;

import tfcflorae.common.container.TFCFKnappingContainer;

public class TFCFKnappingRecipe implements ISimpleRecipe<TFCFKnappingContainer>
{
    protected final ResourceLocation id;
    private final KnappingPattern pattern;
    protected final ItemStack result;
    protected final TypedRecipeSerializer<?> serializer;

    public TFCFKnappingRecipe(ResourceLocation id, KnappingPattern pattern, ItemStack result, TypedRecipeSerializer<?> serializer)
    {
        this.id = id;
        this.pattern = pattern;
        this.result = result;
        this.serializer = serializer;
    }

    @Override
    public boolean matches(TFCFKnappingContainer container, Level level)
    {
        return container.getPattern().matches(getPattern());
    }

    @Override
    public ItemStack getResultItem()
    {
        return result;
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return serializer;
    }

    @Override
    public RecipeType<?> getType()
    {
        return serializer.getRecipeType();
    }

    public KnappingPattern getPattern()
    {
        return pattern;
    }

    public static class Serializer extends TypedRecipeSerializer<TFCFKnappingRecipe>
    {
        private final Supplier<RecipeType<TFCFKnappingRecipe>> type;

        public Serializer(Supplier<RecipeType<TFCFKnappingRecipe>> type)
        {
            this.type = type;
        }

        @Override
        public TFCFKnappingRecipe fromJson(ResourceLocation id, JsonObject json)
        {
            final ItemStack stack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new TFCFKnappingRecipe(id, KnappingPattern.fromJson(json), stack, this);
        }

        @Nullable
        @Override
        public TFCFKnappingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer)
        {
            final KnappingPattern pattern = KnappingPattern.fromNetwork(buffer);
            final ItemStack stack = buffer.readItem();
            return new TFCFKnappingRecipe(id, pattern, stack, this);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, TFCFKnappingRecipe recipe)
        {
            recipe.getPattern().toNetwork(buffer);
            buffer.writeItem(recipe.getResultItem());
        }

        @Override
        public RecipeType<?> getRecipeType()
        {
            return type.get();
        }
    }
}