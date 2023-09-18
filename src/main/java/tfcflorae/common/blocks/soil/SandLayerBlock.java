package tfcflorae.common.blocks.soil;

import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.stream.Stream;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.util.Mth;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.IForgeBlockExtension;
import net.dries007.tfc.common.blocks.soil.TFCSandBlock;
import net.dries007.tfc.common.fluids.FluidHelpers;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.common.fluids.FluidProperty.FluidKey;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.TFCChunkGenerator;

import tfcflorae.common.blockentities.TFCFBlockEntities;

public class SandLayerBlock extends TFCSandBlock implements IFluidLoggable, IForgeBlockExtension//, EntityBlockExtension
{
   public static final FluidProperty ALL_WATER_AND_LAVA = FluidProperty.create("fluid", Stream.of(Fluids.EMPTY, Fluids.FLOWING_WATER, Fluids.WATER, TFCFluids.SALT_WATER, TFCFluids.SPRING_WATER, TFCFluids.RIVER_WATER, Fluids.LAVA, Fluids.FLOWING_LAVA));
   public static final FluidProperty FLUID = ALL_WATER_AND_LAVA;
   public static final Boolean SNOWY = false;

   public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;
   protected static final VoxelShape[] SHAPES = new VoxelShape[]{Shapes.empty(), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

   private final Supplier<? extends Block> transformsInto;
   private final ExtendedProperties properties;

   public SandLayerBlock(int dustColorIn, ExtendedProperties properties, Supplier<? extends Block> transformsInto)
   {
      super(dustColorIn, properties.properties());
      this.properties = properties;
      this.transformsInto = transformsInto;
      this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, Integer.valueOf(1)).setValue(this.getFluidProperty(), this.getFluidProperty().keyFor(Fluids.EMPTY)));
   }

   @Override
   public ExtendedProperties getExtendedProperties()
   {
      return properties;
   }

   @Override
   public boolean canPlaceLiquid(BlockGetter level, BlockPos pos, BlockState state, Fluid fluid)
   {
      if (fluid instanceof FlowingFluid && !getFluidProperty().canContain(fluid))
      {
         return true;
      }
      return IFluidLoggable.super.canPlaceLiquid(level, pos, state, fluid);
   }

   @Override
   public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidStateIn)
   {
      if (fluidStateIn.getType() instanceof FlowingFluid && !getFluidProperty().canContain(fluidStateIn.getType()))
      {
         return true;
      }
      return IFluidLoggable.super.placeLiquid(level, pos, state, fluidStateIn);
   }

   @Override
   public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type)
   {
      switch(type)
      {
         case LAND:
            return state.getValue(LAYERS) < 5;
         case WATER:
            return false;
         case AIR:
            return false;
         default:
            return false;
      }
   }

   @Override
   public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
   {
      return SHAPES[state.getValue(LAYERS)];
   }

   @Override
   public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
   {
      return SHAPES[state.getValue(LAYERS)];
   }

   @Override
   public VoxelShape getBlockSupportShape(BlockState state, BlockGetter reader, BlockPos pos)
   {
      return SHAPES[state.getValue(LAYERS)];
   }

   @Override
   public VoxelShape getVisualShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context)
   {
      return SHAPES[state.getValue(LAYERS)];
   }

   @Override
   public boolean useShapeForLightOcclusion(BlockState state)
   {
      return true;
   }

   @Override
   public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos)
   {
      BlockState blockstate = worldIn.getBlockState(pos.below());
      if (!blockstate.is(Blocks.ICE) && !blockstate.is(Blocks.PACKED_ICE) && !blockstate.is(Blocks.BARRIER))
      {
         if (!blockstate.is(Blocks.HONEY_BLOCK) && !blockstate.is(Blocks.SOUL_SAND))
         {
            return Block.isFaceFull(blockstate.getCollisionShape(worldIn, pos.below()), Direction.UP) || (blockstate.getBlock() instanceof SandLayerBlock && blockstate.getValue(LAYERS) == 8);
         }
         else
         {
            return true;
         }
      }
      else
      {
         return false;
      }
   }

   public void addSandLayer(Level level, BlockState state, BlockPos pos, int extraLayer)
   {
      if (state.getValue(LAYERS) + extraLayer >= 8)
      {
         if (state.getValue(LAYERS) + extraLayer > 8)
         {
            level.setBlock(pos.above(), this.defaultBlockState().setValue(LAYERS, extraLayer), Block.UPDATE_ALL);
         }
         else
         {
            level.setBlock(pos, transformsInto.get().defaultBlockState(), Block.UPDATE_ALL);
         }
      }
      else
      {
         level.setBlock(pos, state.setValue(LAYERS, state.getValue(LAYERS) + extraLayer), Block.UPDATE_ALL);
      }
   }

   @Override
   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
   {
      FluidHelpers.tickFluid(level, currentPos, state);
      if (state.getValue(LAYERS) > 7 && transformsInto != null)
      {
         level.setBlock(currentPos, transformsInto.get().defaultBlockState(), Block.UPDATE_ALL);
      }
      return state.canSurvive(level, currentPos) ? super.updateShape(state, facing, facingState, level, currentPos, facingPos) : state.getFluidState().createLegacyBlock();
   }

   @Override
   public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext)
   {
      int i = state.getValue(LAYERS);
      if (useContext.getItemInHand().is(this.asItem()) && i < 8)
      {
         if (useContext.replacingClickedOnBlock())
         {
            return useContext.getClickedFace() == Direction.UP;
         }
         else
         {
            return true;
         }
      }
      else
      {
         return i == 1;
      }
   }

   @Override
   @SuppressWarnings("deprecation")
   public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
   {
      final ItemStack stack = player.getItemInHand(hand);
      if (Helpers.isItem(stack, this.asItem()) && transformsInto != null)
      {
         int sandLayers = state.getValue(LAYERS);
         if (sandLayers >= 8)
            level.setBlockAndUpdate(pos.above(), this.defaultBlockState());
         else if (sandLayers == 7)
            level.setBlockAndUpdate(pos, transformsInto.get().defaultBlockState());
         else
            level.setBlockAndUpdate(pos, state.setValue(LAYERS,  Mth.clamp(sandLayers + 1, 1, 8)));

         if (!player.isCreative())
            stack.shrink(1);

         Helpers.playSound(level, pos, SoundType.SAND.getPlaceSound());
         level.scheduleTick(pos, this, 2);
         return InteractionResult.SUCCESS;
      }
      return InteractionResult.PASS;
   }

   @Override
   @SuppressWarnings("deprecation")
   public void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
   {
      if (pos.getY() <= TFCChunkGenerator.SEA_LEVEL_Y)
      {
         FluidHelpers.tickFluid(level, pos, state);
      }
      //level.scheduleTick(pos, this, 1);
      super.neighborChanged(state, level, fromPos, blockIn, fromPos, isMoving);
   }

   @Override
   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context)
   {
      Level level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      BlockState state = level.getBlockState(pos);
      if (state.is(this) || (state.getBlock() instanceof SandLayerBlock && state.getValue(LAYERS) >= 1))
      {
         int i = state.getValue(LAYERS);
         return state.setValue(LAYERS, Mth.clamp(i + 1, 1, 8));
      }
      else
      {
         BlockState defaultState = defaultBlockState();
         FluidState fluidState = level.getFluidState(pos);
         FluidKey fluidKey = getFluidProperty().canContain(fluidState.getType()) ? getFluidProperty().keyFor(fluidState.getType()) : getFluidProperty().keyFor(Fluids.EMPTY);
         return defaultState.setValue(getFluidProperty(), fluidKey);
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
   {
      builder.add(getFluidProperty(), LAYERS);
   }

   @Override
   @SuppressWarnings("deprecation")
   public FluidState getFluidState(BlockState state)
   {
      return IFluidLoggable.super.getFluidState(state);
   }

   @Override
   public FluidProperty getFluidProperty()
   {
      return FLUID;
   }

   @Override
   public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
   {
      playerWillDestroy(level, pos, state, player);
      removePileOrSand(level, pos, state);
      return true; // Cause drops and other stuff to occur
   }

   public boolean canPlaceSandPile(LevelAccessor level, BlockPos pos, BlockState state)
   {
      return Helpers.isBlock(state.getBlock(), TFCTags.Blocks.CAN_BE_SNOW_PILED) && defaultBlockState().canSurvive(level, pos);
   }

   public static boolean canPlaceSandPileStatic(LevelAccessor level, BlockPos pos, BlockState state, BlockState sandLayer)
   {
      return Helpers.isBlock(state.getBlock(), TFCTags.Blocks.CAN_BE_SNOW_PILED) && sandLayer.canSurvive(level, pos);
   }

   public void placeSandPile(LevelAccessor level, BlockPos pos, BlockState state, boolean byPlayer)
   {
      // Create a sand pile block, accounting for double piles.
      final BlockPos posAbove = pos.above();
      final BlockState aboveState = level.getBlockState(posAbove);
      final BlockState savedAboveState = Helpers.isBlock(aboveState.getBlock(), TFCTags.Blocks.CAN_BE_SNOW_PILED) ? aboveState : null;
      final BlockState sandPile = defaultBlockState();

      FluidState fluidState = level.getFluidState(pos);
      level.setBlock(pos, sandPile.setValue(getFluidProperty(), getFluidProperty().canContain(fluidState.getType()) ? getFluidProperty().keyFor(fluidState.getType()) : getFluidProperty().keyFor(Fluids.EMPTY)), Block.UPDATE_ALL);

      if (state.hasBlockEntity())
      {
         level.getBlockEntity(pos, TFCFBlockEntities.SAND_PILE.get()).ifPresent(entity -> entity.setHiddenStates(state, savedAboveState, byPlayer));
      }

      if (savedAboveState != null)
      {
         Helpers.removeBlock(level, posAbove, Block.UPDATE_ALL);
      }

      // Then cause block updates
      level.blockUpdated(pos, defaultBlockState().getBlock());
      if (savedAboveState != null)
      {
         level.blockUpdated(posAbove, Blocks.AIR);
      }
   }

   public void placeSandPile(LevelAccessor level, BlockPos pos, BlockState state, int layers, boolean byPlayer)
   {
      // Create a sand pile block, accounting for double piles.
      final BlockPos posAbove = pos.above();
      final BlockState aboveState = level.getBlockState(posAbove);
      final BlockState savedAboveState = Helpers.isBlock(aboveState.getBlock(), TFCTags.Blocks.CAN_BE_SNOW_PILED) ? aboveState : null;
      final BlockState sandPile = defaultBlockState();

      FluidState fluidState = level.getFluidState(pos);
      level.setBlock(pos, sandPile.setValue(SandLayerBlock.LAYERS, layers).setValue(getFluidProperty(), getFluidProperty().canContain(fluidState.getType()) ? getFluidProperty().keyFor(fluidState.getType()) : getFluidProperty().keyFor(Fluids.EMPTY)), Block.UPDATE_ALL);

      if (state.hasBlockEntity())
      {
         level.getBlockEntity(pos, TFCFBlockEntities.SAND_PILE.get()).ifPresent(entity -> entity.setHiddenStates(state, savedAboveState, byPlayer));
      }

      if (savedAboveState != null)
      {
         Helpers.removeBlock(level, posAbove, Block.UPDATE_ALL);
      }

      // Then cause block updates
      level.blockUpdated(pos, defaultBlockState().getBlock());
      if (savedAboveState != null)
      {
         level.blockUpdated(posAbove, Blocks.AIR);
      }
   }

   public void placeSandPile(WorldGenLevel level, BlockPos pos, BlockState state, int layers, boolean byPlayer)
   {
      // Create a sand pile block, accounting for double piles.
      final BlockPos posAbove = pos.above();
      final BlockState aboveState = level.getBlockState(posAbove);
      final BlockState savedAboveState = Helpers.isBlock(aboveState.getBlock(), TFCTags.Blocks.CAN_BE_SNOW_PILED) ? aboveState : null;
      final BlockState sandPile = defaultBlockState();

      FluidState fluidState = level.getFluidState(pos);
      level.setBlock(pos, sandPile.setValue(SandLayerBlock.LAYERS, layers).setValue(getFluidProperty(), getFluidProperty().canContain(fluidState.getType()) ? getFluidProperty().keyFor(fluidState.getType()) : getFluidProperty().keyFor(Fluids.EMPTY)), Block.UPDATE_ALL);

      if (state.hasBlockEntity())
      {
         level.getBlockEntity(pos, TFCFBlockEntities.SAND_PILE.get()).ifPresent(entity -> entity.setHiddenStates(state, savedAboveState, byPlayer));
      }

      if (savedAboveState != null)
      {
         Helpers.removeBlock(level, posAbove, Block.UPDATE_ALL);
      }

      // Then cause block updates
      level.blockUpdated(pos, defaultBlockState().getBlock());
      if (savedAboveState != null)
      {
         level.blockUpdated(posAbove, Blocks.AIR);
      }
   }

   public static void placeSandPileStatic(WorldGenLevel level, BlockState sandPile, BlockPos pos, BlockState state, int layers, boolean byPlayer, boolean shouldFluidLog)
   {
      // Create a sand pile block, accounting for double piles.
      final BlockPos posAbove = pos.above();
      final BlockState aboveState = level.getBlockState(posAbove);
      final BlockState savedAboveState = Helpers.isBlock(aboveState.getBlock(), TFCTags.Blocks.CAN_BE_SNOW_PILED) ? aboveState : null;

      if (shouldFluidLog)
      {
         level.setBlock(pos, FluidHelpers.fillWithFluid(sandPile, level.getFluidState(pos).getType()).setValue(SandLayerBlock.LAYERS, layers), Block.UPDATE_ALL);
      }
      else if (!shouldFluidLog)
      {
         level.setBlock(pos, sandPile.setValue(SandLayerBlock.LAYERS, layers), Block.UPDATE_ALL);
      }
      if (sandPile.hasBlockEntity())
      {
         level.getBlockEntity(pos, TFCFBlockEntities.SAND_PILE.get()).ifPresent(entity -> entity.setHiddenStates(state, savedAboveState, byPlayer));
      }

      if (savedAboveState != null)
      {
         Helpers.removeBlock(level, posAbove, Block.UPDATE_ALL);
      }

      // Then cause block updates
      level.blockUpdated(pos, sandPile.getBlock());
      if (savedAboveState != null)
      {
         level.blockUpdated(posAbove, Blocks.AIR);
      }
   }

   public void removePileOrSand(LevelAccessor level, BlockPos pos, BlockState state)
   {
      removePileOrSand(level, pos, state, -1);
   }

   public void removePileOrSand(LevelAccessor level, BlockPos pos, BlockState state, boolean removeAllLayers)
   {
      removePileOrSand(level, pos, state, removeAllLayers ? 0 : -1);
   }

   public void removePileOrSand(LevelAccessor level, BlockPos pos, BlockState state, int expectedLayers)
   {
      final int layers = state.getValue(LAYERS);
      if (expectedLayers >= layers)
      {
         // If we expect more layers than actually exist, don't remove anything
         return;
      }
      if (layers > 1 && expectedLayers != 0)
      {
         // Remove layers, but keep the sand block intact
         level.setBlock(pos, state.setValue(LAYERS, expectedLayers == -1 ? layers - 1 : expectedLayers), Block.UPDATE_ALL);
      }
      else if (state.is(this))
      {
         // Remove a single sand layer block
         level.removeBlock(pos, false);
      }
      else if (state.hasBlockEntity())
      {
         // Otherwise, remove a sand pile, restoring the internal states
         level.getBlockEntity(pos, TFCBlockEntities.PILE.get()).ifPresent(pile ->{
            if (!level.isClientSide())
            {
               final BlockPos above = pos.above();
               level.setBlock(pos, pile.getInternalState(), Block.UPDATE_ALL);
               if (pile.getAboveState() != null && level.isEmptyBlock(above))
               {
                  level.setBlock(above, pile.getAboveState(), Block.UPDATE_ALL);
               }

               // Update neighbors shapes from the bottom block (this is important to get grass blocks to adjust to sandy/non-sandy states)
               pile.getInternalState().updateNeighbourShapes(level, pos, Block.UPDATE_CLIENTS);
               level.getBlockState(above).updateNeighbourShapes(level, above, Block.UPDATE_CLIENTS);

               // Block ticks after both blocks are placed
               level.blockUpdated(pos, pile.getInternalState().getBlock());
               if (pile.getAboveState() != null)
               {
                  level.blockUpdated(above, pile.getAboveState().getBlock());
               }
            }
         });
      }
   }
}