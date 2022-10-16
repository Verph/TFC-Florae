package tfcflorae.common.blocks.plant;

import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.TFCCactusBlock;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryPlant;

public abstract class TFCFCactusBlock extends TFCCactusBlock
{
    public static TFCFCactusBlock create(RegistryPlant plant, ExtendedProperties properties)
    {
        return new TFCFCactusBlock(properties)
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }
        };
    }

    protected static final VoxelShape COLLISION_SHAPE = box(1, 0, 1, 15, 15, 15);
    protected static final VoxelShape OUTLINE_SHAPE = box(1, 0, 1, 15, 16, 15);

    protected TFCFCactusBlock(ExtendedProperties properties)
    {
        super(properties);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (Direction direction : Direction.Plane.HORIZONTAL)
        {
            mutable.setWithOffset(pos, direction);
            BlockState stateAt = level.getBlockState(mutable);
            if (stateAt.getMaterial().isSolid() || Helpers.isFluid(level.getFluidState(mutable), FluidTags.LAVA))
            {
                return false;
            }
        }

        mutable.setWithOffset(pos, 0, -1, 0);
        BlockState belowState = level.getBlockState(mutable);
        if (state.getValue(PART) == Part.LOWER)
        {
            return Helpers.isBlock(belowState, BlockTags.SAND) || Helpers.isBlock(state.getBlock(), TFCTags.Blocks.GRASS_PLANTABLE_ON) || Helpers.isBlock(state.getBlock(), TFCTags.Blocks.BUSH_PLANTABLE_ON);
        }
        else
        {
            if (state.getBlock() != this)
            {
                return Helpers.isBlock(belowState, BlockTags.SAND) || Helpers.isBlock(state.getBlock(), TFCTags.Blocks.GRASS_PLANTABLE_ON) || Helpers.isBlock(state.getBlock(), TFCTags.Blocks.BUSH_PLANTABLE_ON);
            }
            return belowState.getBlock() == this && belowState.getValue(PART) == Part.LOWER;
        }
    }
}
