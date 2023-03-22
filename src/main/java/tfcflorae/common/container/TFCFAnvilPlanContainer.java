package tfcflorae.common.container;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;

import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.ButtonHandlerContainer;
import net.dries007.tfc.common.recipes.AnvilRecipe;
import net.dries007.tfc.common.recipes.TFCRecipeTypes;
import net.dries007.tfc.util.Helpers;
import org.jetbrains.annotations.Nullable;

import tfcflorae.common.blockentities.TFCFAnvilBlockEntity;

public class TFCFAnvilPlanContainer extends BlockEntityContainer<TFCFAnvilBlockEntity> implements ButtonHandlerContainer
{
    public static TFCFAnvilPlanContainer create(TFCFAnvilBlockEntity anvil, Inventory playerInventory, int windowId)
    {
        return new TFCFAnvilPlanContainer(windowId, anvil).init(playerInventory, 0);
    }

    protected TFCFAnvilPlanContainer(int windowId, TFCFAnvilBlockEntity anvil)
    {
        super(TFCFContainerTypes.ANVIL_PLAN.get(), windowId, anvil);
    }

    @Override
    public void onButtonPress(int buttonID, @Nullable CompoundTag extraNBT)
    {
        if (extraNBT != null && player != null)
        {
            final ResourceLocation recipeId = new ResourceLocation(extraNBT.getString("recipe"));
            final AnvilRecipe recipe = Helpers.getRecipes(player.level, TFCRecipeTypes.ANVIL).get(recipeId);

            blockEntity.chooseRecipe(recipe);

            if (player instanceof ServerPlayer serverPlayer)
            {
                Helpers.openScreen(serverPlayer, blockEntity.anvilProvider(), blockEntity.getBlockPos());
            }
        }
    }
}
