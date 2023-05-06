package tfcflorae.world.feature;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

public class LargeDripstoneFeature extends Feature<LargeDripstoneConfig>
{
   public LargeDripstoneFeature(Codec<LargeDripstoneConfig> codec)
   {
      super(codec);
   }

   @Override
   public boolean place(FeaturePlaceContext<LargeDripstoneConfig> context)
   {
      final WorldGenLevel level = context.level();
      final BlockPos pos = context.origin();
      final Random random = context.random();
      final LargeDripstoneConfig config = context.config();

      final Boolean hasSurface = config.hasSurface;
      final BlockState inputState = config.state.getState(random, pos);
      final BlockState inputSurfaceState = config.surfaceState.getState(random, pos);

      if (!DripstoneUtils.isEmptyOrWater(level, pos))
      {
         return false;
      }
      else
      {
         Optional<Column> optional = Column.scan(level, pos, config.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, (state) -> {return DripstoneUtils.isDripstoneBaseOrLava(state, inputSurfaceState);});
         if (optional.isPresent() && optional.get() instanceof Column.Range)
         {
            Column.Range column$range = (Column.Range)optional.get();
            if (column$range.height() < 4)
            {
               return false;
            }
            else
            {
               int i = (int)((float)column$range.height() * config.maxColumnRadiusToCaveHeightRatio);
               int j = Mth.clamp(i, config.columnRadius.getMinValue(), config.columnRadius.getMaxValue());
               int k = Mth.randomBetweenInclusive(random, config.columnRadius.getMinValue(), j);
               LargeDripstoneFeature.LargeDripstone largedripstonefeature$largedripstone = makeDripstone(pos.atY(column$range.ceiling() - 1), false, random, k, config.stalactiteBluntness, config.heightScale);
               LargeDripstoneFeature.LargeDripstone largedripstonefeature$largedripstone1 = makeDripstone(pos.atY(column$range.floor() + 1), true, random, k, config.stalagmiteBluntness, config.heightScale);
               LargeDripstoneFeature.WindOffsetter largedripstonefeature$windoffsetter;
               if (largedripstonefeature$largedripstone.isSuitableForWind(config) && largedripstonefeature$largedripstone1.isSuitableForWind(config))
               {
                  largedripstonefeature$windoffsetter = new LargeDripstoneFeature.WindOffsetter(pos.getY(), random, config.windSpeed);
               }
               else
               {
                  largedripstonefeature$windoffsetter = LargeDripstoneFeature.WindOffsetter.noWind();
               }

               boolean flag = largedripstonefeature$largedripstone.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(level, largedripstonefeature$windoffsetter);
               boolean flag1 = largedripstonefeature$largedripstone1.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(level, largedripstonefeature$windoffsetter);
               if (flag)
               {
                  largedripstonefeature$largedripstone.placeBlocks(level, random, largedripstonefeature$windoffsetter, config);
               }

               if (flag1 && pos.atY(column$range.floor() + 1).getY() < level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ()) - 8)
               {
                  largedripstonefeature$largedripstone1.placeBlocks(level, random, largedripstonefeature$windoffsetter, config);
               }

               return true;
            }
         }
         else
         {
            return false;
         }
      }
   }

   private static LargeDripstoneFeature.LargeDripstone makeDripstone(BlockPos root, boolean pointingUp, Random random, int radius, FloatProvider bluntnessBase, FloatProvider scaleBase)
   {
      return new LargeDripstoneFeature.LargeDripstone(root, pointingUp, radius, (double)bluntnessBase.sample(random), (double)scaleBase.sample(random));
   }

   private void placeDebugMarkers(WorldGenLevel level, BlockPos pPos, Column.Range pRange, LargeDripstoneFeature.WindOffsetter pWindOffsetter, LargeDripstoneConfig config)
   {
      level.setBlock(pWindOffsetter.offset(pPos.atY(pRange.ceiling() - 1)), Blocks.DIAMOND_BLOCK.defaultBlockState(), 2);
      level.setBlock(pWindOffsetter.offset(pPos.atY(pRange.floor() + 1)), Blocks.GOLD_BLOCK.defaultBlockState(), 2);

      for(BlockPos.MutableBlockPos pos$mutablepos = pPos.atY(pRange.floor() + 2).mutable(); pos$mutablepos.getY() < pRange.ceiling() - 1; pos$mutablepos.move(Direction.UP))
      {
         BlockPos pos = pWindOffsetter.offset(pos$mutablepos);
         if (DripstoneUtils.isEmptyOrWater(level, pPos) || level.getBlockState(pos) == config.surfaceState.getState(level.getRandom(), pos))
         {
            level.setBlock(pos, Blocks.CREEPER_HEAD.defaultBlockState(), 2);
         }
      }
   }

   static final class LargeDripstone
   {
      private BlockPos root;
      private final boolean pointingUp;
      private int radius;
      private final double bluntness;
      private final double scale;

      LargeDripstone(BlockPos root, boolean pointingUp, int radius, double pBluntness, double pScale)
      {
         this.root = root;
         this.pointingUp = pointingUp;
         this.radius = radius;
         this.bluntness = pBluntness;
         this.scale = pScale;
      }

      private int getHeight()
      {
         return this.getHeightAtRadius(0.0F);
      }

      private int getMinY()
      {
         return this.pointingUp ? this.root.getY() : this.root.getY() - this.getHeight();
      }

      private int getMaxY()
      {
         return !this.pointingUp ? this.root.getY() : this.root.getY() + this.getHeight();
      }

      boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel level, LargeDripstoneFeature.WindOffsetter pWindOffsetter)
      {
         while(this.radius > 1)
         {
            BlockPos.MutableBlockPos pos$mutablepos = this.root.mutable();
            int i = Math.min(10, this.getHeight());

            for(int j = 0; j < i; ++j)
            {
               if (level.getBlockState(pos$mutablepos).is(Blocks.LAVA))
               {
                  return false;
               }
               if (DripstoneUtils.isCircleMostlyEmbeddedInStone(level, pWindOffsetter.offset(pos$mutablepos), this.radius))
               {
                  this.root = pos$mutablepos;
                  return true;
               }
               pos$mutablepos.move(this.pointingUp ? Direction.DOWN : Direction.UP);
            }
            this.radius /= 2;
         }
         return false;
      }

      private int getHeightAtRadius(float radius)
      {
         return (int)DripstoneUtils.getDripstoneHeight((double)radius, (double)this.radius, this.scale, this.bluntness);
      }

      void placeBlocks(WorldGenLevel level, Random random, LargeDripstoneFeature.WindOffsetter pWindOffsetter, LargeDripstoneConfig config)
      {
         for(int i = -this.radius; i <= this.radius; ++i)
         {
            for(int j = -this.radius; j <= this.radius; ++j)
            {
               float f = Mth.sqrt((float)(i * i + j * j));
               if (!(f > (float)this.radius))
               {
                  int k = this.getHeightAtRadius(f);
                  if (k > 0)
                  {
                     if ((double)random.nextFloat() < 0.2D)
                     {
                        k = (int)((float)k * Mth.randomBetween(random, 0.8F, 1.0F));
                     }

                     BlockPos.MutableBlockPos pos$mutablepos = this.root.offset(i, 0, j).mutable();
                     boolean flag = false;
                     int l = this.pointingUp ? /*level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, pos$mutablepos.getX(), pos$mutablepos.getZ())*/ level.getMaxBuildHeight() : Integer.MAX_VALUE;

                     for(int i1 = 0; i1 < k && pos$mutablepos.getY() < l; ++i1)
                     {
                        BlockPos pos = pWindOffsetter.offset(pos$mutablepos);
                        if (DripstoneUtils.isEmptyOrWaterOrLava(level, pos))
                        {
                           flag = true;
                           level.setBlock(pos, config.surfaceState.getState(random, pos), 2);
                        }
                        else if (flag && level.getBlockState(pos).is(BlockTags.BASE_STONE_OVERWORLD))
                        {
                           break;
                        }
                        pos$mutablepos.move(this.pointingUp ? Direction.UP : Direction.DOWN);
                     }
                  }
               }
            }
         }
      }
      boolean isSuitableForWind(LargeDripstoneConfig pConfig)
      {
         return this.radius >= pConfig.minRadiusForWind && this.bluntness >= (double)pConfig.minBluntnessForWind;
      }
   }

   static final class WindOffsetter
   {
      private final int originY;
      @Nullable
      private final Vec3 windSpeed;

      WindOffsetter(int pOriginY, Random random, FloatProvider pMagnitude)
      {
         this.originY = pOriginY;
         float f = pMagnitude.sample(random);
         float f1 = Mth.randomBetween(random, 0.0F, (float)Math.PI);
         this.windSpeed = new Vec3((double)(Mth.cos(f1) * f), 0.0D, (double)(Mth.sin(f1) * f));
      }

      private WindOffsetter()
      {
         this.originY = 0;
         this.windSpeed = null;
      }

      static LargeDripstoneFeature.WindOffsetter noWind()
      {
         return new LargeDripstoneFeature.WindOffsetter();
      }

      BlockPos offset(BlockPos pPos)
      {
         if (this.windSpeed == null)
         {
            return pPos;
         }
         else
         {
            int i = this.originY - pPos.getY();
            Vec3 vec3 = this.windSpeed.scale((double)i);
            return pPos.offset(vec3.x, 0.0D, vec3.z);
         }
      }
   }
}
