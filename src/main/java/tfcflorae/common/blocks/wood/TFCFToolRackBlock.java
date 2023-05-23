package tfcflorae.common.blocks.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.wood.ToolRackBlock;

import tfcflorae.common.blockentities.TFCFBlockEntities;
import tfcflorae.common.blockentities.TFCFToolRackBlockEntity;

public class TFCFToolRackBlock extends ToolRackBlock
{
    public TFCFToolRackBlock(ExtendedProperties properties)
    {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        TFCFToolRackBlockEntity toolRack = level.getBlockEntity(pos, TFCFBlockEntities.TOOL_RACK.get()).orElse(null);
        if (toolRack != null)
        {
            return toolRack.onRightClick(player, getSlotFromPos(state, hit.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ())));
        }
        return InteractionResult.PASS;
    }
}
