package tfcflorae.common.blocks.soil;

import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.util.registry.RegistrySoilVariant;

public class CompactDirtBlock extends Block
{
    @Nullable private final Supplier<? extends Block> dirt;
    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

    public CompactDirtBlock(Properties properties, @Nullable Supplier<? extends Block> dirt)
    {
        super(properties);
        this.dirt = dirt;
    }

    CompactDirtBlock(Properties properties, SoilBlockType soil, RegistrySoilVariant variant)
    {
        this(properties, variant.getBlock(soil));
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction action, boolean simulate)
    {
        if (context.getItemInHand().canPerformAction(action))
        {
            if (action == ToolActions.HOE_TILL && dirt != null)
            {
                return dirt.get().defaultBlockState();
            }
        }
        return null;
    }
}
