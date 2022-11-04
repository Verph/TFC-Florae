package tfcflorae.common.recipes;

import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;

import com.mojang.datafixers.util.Either;
import net.dries007.tfc.common.capabilities.player.PlayerDataCapability;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.recipes.RecipeSerializerImpl;
import net.dries007.tfc.common.recipes.SimpleBlockRecipe;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredients;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.JsonHelpers;
import net.dries007.tfc.util.collections.IndirectHashCollection;
import org.jetbrains.annotations.Nullable;

import tfcflorae.common.TFCFTags;

public class BrushingRecipe extends SimpleBlockRecipe
{
    /**
     * In a sentence, this method returns "Either" a BlockState, which the caller must handle, or an InteractionResult to be returned
     */
    public static Either<BlockState, InteractionResult> computeResult(Player player, BlockState state, BlockHitResult hit, boolean informWhy)
    {
        ItemStack held = player.getMainHandItem();
        if (Helpers.isItem(held, TFCFTags.Items.BRUSHES))
        {
            BlockPos pos = hit.getBlockPos();
            return player.getCapability(PlayerDataCapability.CAPABILITY).map(cap -> {
                final BrushingRecipe recipe = BrushingRecipe.getRecipe(state, held);
                if (recipe == null)
                {
                    if (informWhy) complain(player, "no_recipe");
                    return Either.<BlockState, InteractionResult>right(InteractionResult.PASS);
                }
                else
                {
                    BlockState brushed = recipe.getBlockCraftingResult(state);
                    brushed = brushed.getBlock().getStateForPlacement(new BlockPlaceContext(player, InteractionHand.MAIN_HAND, new ItemStack(brushed.getBlock()), hit));
                    if (brushed == null)
                    {
                        if (informWhy) complain(player, "cannot_place");
                        return Either.<BlockState, InteractionResult>right(InteractionResult.FAIL);
                    }
                    else
                    {
                        // covers case where a waterlogged block is brushed and the new block can't take the fluid contained
                        brushed = FluidHelpers.fillWithFluid(brushed, player.level.getFluidState(pos).getType());
                        if (brushed == null)
                        {
                            if (informWhy) complain(player, "bad_fluid");
                            return Either.<BlockState, InteractionResult>right(InteractionResult.FAIL);
                        }
                        else
                        {
                            return Either.<BlockState, InteractionResult>left(brushed);
                        }
                    }
                }
            }).orElse(Either.right(InteractionResult.PASS));
        }
        return Either.right(InteractionResult.PASS);
    }

    private static void complain(Player player, String message)
    {
        player.displayClientMessage(Helpers.translatable("tfcflorae.brushing." + message), true);
    }

    public static final IndirectHashCollection<Block, BrushingRecipe> CACHE = IndirectHashCollection.createForRecipe(recipe -> recipe.getBlockIngredient().getValidBlocks(), TFCFRecipeTypes.BRUSHING);

    @Nullable
    public static BrushingRecipe getRecipe(BlockState state, ItemStack held)
    {
        for (BrushingRecipe recipe : CACHE.getAll(state.getBlock()))
        {
            if (recipe.matches(state, held))
            {
                return recipe;
            }
        }
        return null;
    }

    @Nullable
    private final Ingredient itemIngredient;
    private final ItemStackProvider extraDrop;

    public BrushingRecipe(ResourceLocation id, BlockIngredient ingredient, BlockState outputState, @Nullable Ingredient itemIngredient, ItemStackProvider extraDrop)
    {
        super(id, ingredient, outputState, false);
        this.itemIngredient = itemIngredient;
        this.extraDrop = extraDrop;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return TFCFRecipeSerializers.BRUSHING.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return TFCFRecipeTypes.BRUSHING.get();
    }

    public boolean matches(BlockState state, ItemStack stack)
    {
        if (itemIngredient != null && !itemIngredient.test(stack))
        {
            return false;
        }
        return matches(state);
    }

    @Nullable
    public Ingredient getItemIngredient()
    {
        return itemIngredient;
    }

    public ItemStack getExtraDrop(ItemStack chisel)
    {
        return extraDrop.getSingleStack(chisel);
    }

    public static class Serializer extends RecipeSerializerImpl<BrushingRecipe>
    {
        @Override
        public BrushingRecipe fromJson(ResourceLocation recipeId, JsonObject json)
        {
            BlockIngredient ingredient = BlockIngredients.fromJson(JsonHelpers.get(json, "ingredient"));
            BlockState state = JsonHelpers.getBlockState(GsonHelper.getAsString(json, "result"));
            Ingredient itemIngredient = json.has("item_ingredient") ? Ingredient.fromJson(json.get("item_ingredient")) : null;
            ItemStackProvider drop = json.has("extra_drop") ? ItemStackProvider.fromJson(JsonHelpers.getAsJsonObject(json, "extra_drop")) : ItemStackProvider.empty();
            return new BrushingRecipe(recipeId, ingredient, state, itemIngredient, drop);
        }

        @Nullable
        @Override
        public BrushingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
        {
            final BlockIngredient ingredient = BlockIngredients.fromNetwork(buffer);
            final BlockState state = buffer.readRegistryIdUnsafe(ForgeRegistries.BLOCKS).defaultBlockState();
            final Ingredient itemIngredient = Helpers.decodeNullable(buffer, Ingredient::fromNetwork);
            final ItemStackProvider drop = ItemStackProvider.fromNetwork(buffer);
            return new BrushingRecipe(recipeId, ingredient, state, itemIngredient, drop);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, BrushingRecipe recipe)
        {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeRegistryIdUnsafe(ForgeRegistries.BLOCKS, recipe.outputState.getBlock());
            Helpers.encodeNullable(recipe.itemIngredient, buffer, Ingredient::toNetwork);
            recipe.extraDrop.toNetwork(buffer);
        }
    }
}