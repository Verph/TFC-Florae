package tfcflorae.mixin;

import java.util.Random;

import javax.annotation.Nullable;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.dries007.tfc.client.particle.TFCParticles;
import net.dries007.tfc.common.blockentities.IFarmland;
import net.dries007.tfc.common.blockentities.FarmlandBlockEntity.NutrientType;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.GroundcoverBlock;
import net.dries007.tfc.common.blocks.wood.FallenLeavesBlock;
import net.dries007.tfc.common.fluids.FluidProperty.FluidKey;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Season;

@Mixin(FallenLeavesBlock.class)
public class FallenLeavesBlockMixin extends GroundcoverBlock
{
    @Unique private static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;
    @Unique private static final VoxelShape[] SHAPES = new VoxelShape[]{Shapes.empty(), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    @Shadow private final ExtendedProperties properties;

    public FallenLeavesBlockMixin(ExtendedProperties properties)
    {
        super(properties, SHAPES[1], null);
        this.properties = properties;
    }

    @Override
    public ExtendedProperties getExtendedProperties()
    {
        return properties;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(LAYERS, getFluidProperty());
    }

    @Overwrite(remap = false)
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
    {
        return SHAPES[state.getValue(LAYERS)];
    }

    /*@Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context)
    {
        return SHAPES[state.getValue(LAYERS)];
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter reader, BlockPos pos)
    {
        return SHAPES[state.getValue(LAYERS)];
    }*/

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
    @SuppressWarnings("deprecation")
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return super.canSurvive(state, level, pos) || level.getBlockEntity(pos.below()) instanceof IFarmland;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (random.nextInt(TFCConfig.SERVER.snowMeltChance.get()) == 0 && Calendars.get(level).getCalendarMonthOfYear().getSeason() != Season.FALL)
        {
            if (level.getBlockEntity(pos.below()) instanceof IFarmland farmland)
            {
                for (NutrientType nutrient : NutrientType.values())
                {
                    if (random.nextBoolean())
                    {
                        float amount = random.nextFloat() * 0.1F;
                        farmland.addNutrient(nutrient, amount);
                        addNutrientParticles(level, pos, nutrient, amount);
                    }
                }
            }
            if (state.getValue(LAYERS) >= 2)
            {
                level.setBlock(pos, state.setValue(LAYERS, state.getValue(LAYERS) - 1), Block.UPDATE_ALL);
            }
            else
            {
                level.removeBlock(pos, false);
            }
            doParticles(level, pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(), 1);
        }
    }

    @Unique
    private void addNutrientParticles(ServerLevel level, BlockPos pos, NutrientType nutrient, float amount)
    {
        if (nutrient == NutrientType.NITROGEN)
        {
            for (int i = 0; i < (int) (amount > 0 ? Mth.clamp(amount * 10, 1, 5) : 0); i++)
            {
                level.sendParticles(TFCParticles.NITROGEN.get(), pos.getX() + level.random.nextFloat(), pos.getY() + level.random.nextFloat() / 5D, pos.getZ() + level.random.nextFloat(), 0, 0D, 0D, 0D, 1D);
            }
        }
        if (nutrient == NutrientType.PHOSPHOROUS)
        {
            for (int i = 0; i < (int) (amount > 0 ? Mth.clamp(amount * 10, 1, 5) : 0); i++)
            {
                level.sendParticles(TFCParticles.PHOSPHORUS.get(), pos.getX() + level.random.nextFloat(), pos.getY() + level.random.nextFloat() / 5D, pos.getZ() + level.random.nextFloat(), 0, 0D, 0D, 0D, 1D);
            }
        }
        if (nutrient == NutrientType.POTASSIUM)
        {
            for (int i = 0; i < (int) (amount > 0 ? Mth.clamp(amount * 10, 1, 5) : 0); i++)
            {
                level.sendParticles(TFCParticles.POTASSIUM.get(), pos.getX() + level.random.nextFloat(), pos.getY() + level.random.nextFloat() / 5D, pos.getZ() + level.random.nextFloat(), 0, 0D, 0D, 0D, 1D);
            }
        }
    }

    @Unique
    private void doParticles(ServerLevel level, double x, double y, double z, int count)
    {
        level.sendParticles(TFCParticles.LEAF.get(), x, y, z, count, Helpers.triangle(level.random), Helpers.triangle(level.random), Helpers.triangle(level.random), 0.3f);
    }

    @Unique
    private void addSandLayer(Level level, BlockState state, BlockPos pos, int extraLayer)
    {
        if (state.getValue(LAYERS) + extraLayer >= 8)
        {
            if (state.getValue(LAYERS) + extraLayer > 8)
            {
                level.setBlock(pos.above(), this.defaultBlockState().setValue(LAYERS, extraLayer), Block.UPDATE_ALL);
            }
            else
            {
                level.setBlock(pos, this.defaultBlockState().setValue(LAYERS, 8), Block.UPDATE_ALL);
            }
        }
        else
        {
            level.setBlock(pos, state.setValue(LAYERS, state.getValue(LAYERS) + extraLayer), Block.UPDATE_ALL);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        final ItemStack stack = player.getItemInHand(hand);
        if (Helpers.isItem(stack, this.asItem()))
        {
            int sandLayers = state.getValue(LAYERS);
            if (sandLayers >= 8 && level.getBlockState(pos.above()).isAir())
            {
                level.setBlockAndUpdate(pos.above(), this.defaultBlockState());
            }
            else
            {
                level.setBlockAndUpdate(pos, state.setValue(LAYERS,  Mth.clamp(sandLayers + 1, 1, 8)));
            }

            if (!player.isCreative())
            {
                stack.shrink(1);
            }

            Helpers.playSound(level, pos, SoundType.AZALEA_LEAVES.getPlaceSound());
            level.scheduleTick(pos, this, Block.UPDATE_ALL);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (state.getBlock().equals(this.defaultBlockState().getBlock()) && state.getValue(LAYERS) >= 1)
        {
            int i = state.getValue(LAYERS);
            return state.setValue(LAYERS, Mth.clamp(i + 1, 1, 8));
        }
        else
        {
            BlockState defaultState = defaultBlockState().setValue(LAYERS, 1);
            FluidState fluidState = level.getFluidState(pos);
            FluidKey fluidKey = getFluidProperty().canContain(fluidState.getType()) ? getFluidProperty().keyFor(fluidState.getType()) : getFluidProperty().keyFor(Fluids.EMPTY);
            return defaultState.setValue(getFluidProperty(), fluidKey);
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
    {
        playerWillDestroy(level, pos, state, player);
        removePileOrSand(level, pos, state);
        return true; // Cause drops and other stuff to occur
    }

    @Unique
    private void removePileOrSand(LevelAccessor level, BlockPos pos, BlockState state)
    {
        removePileOrSand(level, pos, state, -1);
    }

    @Unique
    private void removePileOrSand(LevelAccessor level, BlockPos pos, BlockState state, int expectedLayers)
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
    }
}
