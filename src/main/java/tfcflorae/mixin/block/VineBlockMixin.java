package tfcflorae.mixin.block;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.dries007.tfc.util.Helpers;

@Mixin(VineBlock.class)
public abstract class VineBlockMixin
{
    /*public VineBlockMixin(Properties properties)
    {
        super(properties);
    }

    @Overwrite(remap = false)
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        for (Direction direction : Helpers.DIRECTIONS)
        {
            if (state.getValue(VineBlock.PROPERTY_BY_DIRECTION.get(direction)))
            {
                BlockState attachedState = level.getBlockState(pos.relative(direction.getOpposite()));
                Block attachedBlock = attachedState.getBlock();
                BlockState blockstate = level.getBlockState(pos);

                if (this.hasFaces(this.getUpdatedState(state, level, pos)) || Block.isFaceFull(blockstate.getCollisionShape(level, pos), direction.getOpposite()) || Helpers.isBlock(attachedState, BlockTags.LEAVES) || Helpers.isBlock(attachedState.getBlock(), TFCTags.Blocks.GRASS_PLANTABLE_ON) || Helpers.isBlock(attachedState.getBlock(), TFCTags.Blocks.BUSH_PLANTABLE_ON) || attachedBlock instanceof TFCLeavesBlock);
                {
                    return true;
                }
            }
        }
        return this.hasFaces(this.getUpdatedState(state, level, pos));
    }

    @Shadow
    private boolean hasFaces(BlockState state)
    {
        return this.countFaces(state) > 0;
    }

    @Shadow
    private int countFaces(BlockState p_57910_)
    {
        int i = 0;
        for(BooleanProperty booleanproperty : VineBlock.PROPERTY_BY_DIRECTION.values())
        {
            if (p_57910_.getValue(booleanproperty))
            {
                ++i;
            }
        }
        return i;
    }

    @Shadow
    private BlockState getUpdatedState(BlockState state, BlockGetter level, BlockPos pos)
    {
        BlockPos blockpos = pos.above();
        if (state.getValue(VineBlock.UP))
        {
            state = state.setValue(VineBlock.UP, Boolean.valueOf(VineBlock.isAcceptableNeighbour(level, blockpos, Direction.DOWN)));
        }
        BlockState blockstate = null;
        for(Direction direction : Direction.Plane.HORIZONTAL)
        {
            BooleanProperty booleanproperty = VineBlock.getPropertyForFace(direction);
            if (state.getValue(booleanproperty))
            {
                boolean flag = this.canSupportAtFace(level, pos, direction);
                if (!flag)
                {
                    if (blockstate == null)
                    {
                        blockstate = level.getBlockState(blockpos);
                    }
                    flag = blockstate.is(this) && blockstate.getValue(booleanproperty);
                }
                state = state.setValue(booleanproperty, Boolean.valueOf(flag));
            }
        }
        return state;
    }

    @Shadow
    private boolean canSupportAtFace(BlockGetter level, BlockPos pos, Direction direction)
    {
        if (direction == Direction.DOWN)
        {
            return false;
        }
        else
        {
            BlockPos blockpos = pos.relative(direction);
            if (VineBlock.isAcceptableNeighbour(level, blockpos, direction))
            {
                return true;
            }
            else if (direction.getAxis() == Direction.Axis.Y)
            {
                return false;
            }
            else
            {
                BooleanProperty booleanproperty = VineBlock.PROPERTY_BY_DIRECTION.get(direction);
                BlockState blockstate = level.getBlockState(pos.above());
                return blockstate.is(this) && blockstate.getValue(booleanproperty);
            }
        }
    }*/

    @Inject(method = "canSurvive", at = @At("INVOKE"), cancellable = true)
    private void inject$canSurvive(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        for (Direction direction : Helpers.DIRECTIONS)
        {
            if (direction != Direction.DOWN && state.getValue(VineBlock.PROPERTY_BY_DIRECTION.get(direction)))
            {
                BlockState attachedState = level.getBlockState(pos.relative(direction.getOpposite()));
                Block attachedBlock = attachedState.getBlock();
                BlockState blockstate = level.getBlockState(pos);

                if (Block.isFaceFull(blockstate.getCollisionShape(level, pos), direction.getOpposite()) || Helpers.isBlock(attachedState, BlockTags.LEAVES) || Helpers.isBlock(attachedState.getBlock(), TFCTags.Blocks.GRASS_PLANTABLE_ON) || Helpers.isBlock(attachedState.getBlock(), TFCTags.Blocks.BUSH_PLANTABLE_ON) || attachedBlock instanceof TFCLeavesBlock);
                {
                    cir.setReturnValue(true);
                }
            }
        }
    }

    @Inject(method = "isAcceptableNeighbour", at = @At("INVOKE"), cancellable = true)
    private static void inject$isAcceptableNeighbour(BlockGetter level, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir)
    {
        BlockState attachedState = level.getBlockState(pos.relative(direction.getOpposite()));
        Block attachedBlock = attachedState.getBlock();
        BlockState blockstate = level.getBlockState(pos);
        if (Block.isFaceFull(blockstate.getCollisionShape(level, pos), direction.getOpposite()) || Helpers.isBlock(attachedState, BlockTags.LEAVES) || Helpers.isBlock(attachedState.getBlock(), TFCTags.Blocks.GRASS_PLANTABLE_ON) || Helpers.isBlock(attachedState.getBlock(), TFCTags.Blocks.BUSH_PLANTABLE_ON) || attachedBlock instanceof TFCLeavesBlock)
        {
            cir.setReturnValue(true);
        }
    }
}
