package tfcflorae.world.feature;

import java.util.Optional;
import java.util.Random;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;

import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.settings.RockSettings;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;

public class PointedDripstoneFeature extends Feature<PointedDripstoneConfiguration>
{
   public PointedDripstoneFeature(Codec<PointedDripstoneConfiguration> codec)
   {
      super(codec);
   }

   @Override
   public boolean place(FeaturePlaceContext<PointedDripstoneConfiguration> context)
   {
      final WorldGenLevel level = context.level();
      final BlockPos pos = context.origin();
      final BlockState state = level.getBlockState(pos);
      final Random random = context.random();
      final PointedDripstoneConfiguration config = context.config();

      final ChunkDataProvider provider = ChunkDataProvider.get(context.chunkGenerator());
      final ChunkData data = provider.get(context.level(), pos);
      final RockSettings rock = data.getRockData().getRock(pos);

      Optional<Direction> optional = getTipDirection(level, pos, random);
      if (optional.isEmpty()) {
         return false;
      } else {
         BlockPos pos1 = pos.relative(optional.get().getOpposite());
         createPatchOfDripstoneBlocks(level, random, pos1, config);
         int i = random.nextFloat() < config.chanceOfTallerDripstone && DripstoneUtils.isEmptyOrWater(level.getBlockState(pos.relative(optional.get()))) ? 2 : 1;
         DripstoneUtils.growPointedDripstone(level, pos, optional.get(), i, false);
         return true;
      }
   }

   private static Optional<Direction> getTipDirection(LevelAccessor p_191069_, BlockPos p_191070_, Random p_191071_) {
      boolean flag = DripstoneUtils.isDripstoneBase(p_191069_.getBlockState(p_191070_.above()));
      boolean flag1 = DripstoneUtils.isDripstoneBase(p_191069_.getBlockState(p_191070_.below()));
      if (flag && flag1) {
         return Optional.of(p_191071_.nextBoolean() ? Direction.DOWN : Direction.UP);
      } else if (flag) {
         return Optional.of(Direction.DOWN);
      } else {
         return flag1 ? Optional.of(Direction.UP) : Optional.empty();
      }
   }

   private static void createPatchOfDripstoneBlocks(LevelAccessor p_191073_, Random p_191074_, BlockPos p_191075_, PointedDripstoneConfiguration p_191076_) {
      DripstoneUtils.placeDripstoneBlockIfPossible(p_191073_, p_191075_);

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         if (!(p_191074_.nextFloat() > p_191076_.chanceOfDirectionalSpread)) {
            BlockPos pos = p_191075_.relative(direction);
            DripstoneUtils.placeDripstoneBlockIfPossible(p_191073_, pos);
            if (!(p_191074_.nextFloat() > p_191076_.chanceOfSpreadRadius2)) {
               BlockPos pos1 = pos.relative(Direction.getRandom(p_191074_));
               DripstoneUtils.placeDripstoneBlockIfPossible(p_191073_, pos1);
               if (!(p_191074_.nextFloat() > p_191076_.chanceOfSpreadRadius3)) {
                  BlockPos pos2 = pos1.relative(Direction.getRandom(p_191074_));
                  DripstoneUtils.placeDripstoneBlockIfPossible(p_191073_, pos2);
               }
            }
         }
      }

   }
}