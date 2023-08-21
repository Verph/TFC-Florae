package tfcflorae.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blockentities.AbstractFirepitBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.devices.FirepitBlock;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.advancements.TFCAdvancements;

import tfcflorae.common.TFCFTags;

@Mixin(FirepitBlock.class)
public abstract class FirepitBlockMixin //extends BottomSupportedDeviceBlock implements IGhostBlockHandler, IBellowsConsumer
{
    @Shadow public static final BooleanProperty LIT = BlockStateProperties.LIT;
    @Shadow public static final IntegerProperty SMOKE_LEVEL = TFCBlockStateProperties.SMOKE_LEVEL;
    /*@Shadow public static final VoxelShape BASE_SHAPE = Shapes.or(box(0, 0, 0, 16, 2, 16), box(2, 2, 2, 14, 6, 14));

    public FirepitBlockMixin(ExtendedProperties properties)
    {
        this(properties, BASE_SHAPE);
    }*/

    @Overwrite(remap = false)
    public BlockState getStateToDraw(Level level, Player player, BlockState lookState, Direction direction, BlockPos pos, double x, double y, double z, ItemStack item)
    {
        if (Helpers.isItem(item, TFCFTags.Items.POTS))
        {
            return TFCBlocks.POT.get().defaultBlockState().setValue(LIT, lookState.getValue(LIT));
        }
        else if (Helpers.isItem(item, TFCItems.WROUGHT_IRON_GRILL.get()))
        {
            return TFCBlocks.GRILL.get().defaultBlockState().setValue(LIT, lookState.getValue(LIT));
        }
        return null;
    }

    @Overwrite(remap = true)
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        final AbstractFirepitBlockEntity<?> firepit = level.getBlockEntity(pos, TFCBlockEntities.FIREPIT.get()).orElse(null);
        if (firepit != null)
        {
            final ItemStack stack = player.getItemInHand(hand);
            if (Helpers.isItem(stack.getItem(), TFCFTags.Items.POTS) || stack.getItem() == TFCItems.WROUGHT_IRON_GRILL.get())
            {
                if (!level.isClientSide)
                {
                    final Block newBlock = Helpers.isItem(stack.getItem(), TFCFTags.Items.POTS) ? TFCBlocks.POT.get() : TFCBlocks.GRILL.get();
                    AbstractFirepitBlockEntity.convertTo(level, pos, state, firepit, newBlock);
                    if (player instanceof ServerPlayer serverPlayer)
                    {
                        TFCAdvancements.FIREPIT_CREATED.trigger(serverPlayer, newBlock.defaultBlockState());
                    }
                    if (!player.isCreative()) stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
            else if (Helpers.isItem(stack.getItem(), TFCTags.Items.EXTINGUISHER) && state.getValue(LIT))
            {
                firepit.extinguish(state);
                return InteractionResult.SUCCESS;
            }
            else
            {
                if (player instanceof ServerPlayer serverPlayer)
                {
                    Helpers.openScreen(serverPlayer, firepit, pos);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}