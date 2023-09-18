package tfcflorae.world.feature;

import java.util.Random;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

import net.dries007.tfc.common.fluids.FluidHelpers;

import tfcflorae.TFCFlorae;
import tfcflorae.common.blocks.rock.TFCFPointedDripstoneBlock;

import static tfcflorae.common.blocks.rock.TFCFPointedDripstoneBlock.*;

public class RootSpikeFeature extends Feature<BlockStateConfiguration>
{
   public RootSpikeFeature(Codec<BlockStateConfiguration> codec)
   {
      super(codec);
   }

   @Override
   public boolean place(FeaturePlaceContext<BlockStateConfiguration> context)
   {
      final WorldGenLevel level = context.level();
      final BlockPos pos = context.origin();
      final Random random = context.random();
      final BlockStateConfiguration config = context.config();

      BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos.getX(), pos.getY(), pos.getZ());

      BlockState inputState = config.state;

      for (int height = 0; height < random.nextInt(5) + 1; ++height) // Up to 5 blocks tall
      {
         int y = pos.getY() + height;
         mutablePos.set(mutablePos.getX(), y, mutablePos.getZ());

         if (inputState.getBlock() instanceof TFCFPointedDripstoneBlock dripstoneBlock)
         {
            inputState.setValue(THICKNESS, dripstoneBlock.calculateDripstoneThickness(level, mutablePos, Direction.UP, inputState.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE)).setValue(TIP_DIRECTION, Direction.UP);
         }

         inputState = FluidHelpers.fillWithFluid(inputState, level.getBlockState(mutablePos).getFluidState().getType());
         level.setBlock(mutablePos, inputState, Block.UPDATE_ALL);
      }
      TFCFlorae.LOGGER.debug("Attempted to place spike at XYZ: " + mutablePos.getX() + " " + mutablePos.getY() + " " + mutablePos.getZ());
      return true;
   }
}