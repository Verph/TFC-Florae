package tfcflorae.common.blocks.soil;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrySoilVariant;

public class TFCFRockySoilBlock extends Block
{
    @Nullable private final Supplier<? extends Block> transformsTo;
    @Nullable private final Supplier<? extends Block> transformsFrom;
    @Nullable private final Supplier<? extends Block> soilVariantDrop;
    @Nullable private final Supplier<? extends Item> requiredItem;
    private final Boolean transformsToCobble;

    public TFCFRockySoilBlock(Properties properties, @Nullable Supplier<? extends Item> requiredItem, @Nullable Supplier<? extends Block> transformsTo, @Nullable Supplier<? extends Block> transformsFrom, @Nullable Supplier<? extends Block> soilVariantDrop, Boolean transformsToCobble)
    {
        super(properties);
        this.transformsTo = transformsTo;
        this.transformsFrom = transformsFrom;
        this.soilVariantDrop = soilVariantDrop;
        this.requiredItem = requiredItem;
        this.transformsToCobble = transformsToCobble;
    }

    TFCFRockySoilBlock(Properties properties, @Nullable SoilBlockType dirtType, @Nullable RegistrySoilVariant variant, Boolean transformsToCobble)
    {
        this(properties, variant.getDriedMudBrick(), variant.getBlock(dirtType), variant.getBlock(dirtType), variant.getBlock(dirtType), transformsToCobble);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        final ItemStack stack = player.getItemInHand(hand);
        if (Helpers.isItem(stack, requiredItem.get().asItem()) && transformsTo != null)
        {
            if (transformsToCobble && soilVariantDrop != null)
            {
                stack.shrink(1);
                final BlockState block = transformsTo.get().defaultBlockState();
                level.setBlockAndUpdate(pos, block);
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(soilVariantDrop.get()));
                Helpers.playSound(level, pos, SoundType.STONE.getPlaceSound());
                return InteractionResult.SUCCESS;
            }
            else
            {
                stack.shrink(1);
                final BlockState block = transformsTo.get().defaultBlockState();
                level.setBlockAndUpdate(pos, block);
                Helpers.playSound(level, pos, SoundType.BASALT.getPlaceSound());
                return InteractionResult.SUCCESS;
            }
        }
        else if (player.getItemInHand(hand).canPerformAction(ToolActions.HOE_TILL) && transformsFrom != null)
        {
            final BlockState block = transformsFrom.get().defaultBlockState();
            level.setBlockAndUpdate(pos, block);
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(requiredItem.get().asItem()));
            Helpers.playSound(level, pos, SoundType.GRAVEL.getBreakSound());
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
