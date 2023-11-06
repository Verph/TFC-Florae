package tfcflorae.mixin;

import java.util.Random;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import net.dries007.tfc.common.blockentities.IFarmland;
import net.dries007.tfc.common.blockentities.FarmlandBlockEntity.NutrientType;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.soil.ConnectedGrassBlock;
import net.dries007.tfc.common.blocks.soil.IGrassBlock;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;

import tfcflorae.util.TFCFHelpers;

import static net.dries007.tfc.common.blocks.soil.ConnectedGrassBlock.*;

@Mixin(ConnectedGrassBlock.class)
public abstract class ConnectedGrassBlockkMixin extends Block implements IGrassBlock
{
    @Unique private static final BooleanProperty NATURAL = TFCBlockStateProperties.NATURAL;
    @Shadow @Nullable private final Supplier<? extends Block> path;
    @Shadow @Nullable private final Supplier<? extends Block> farmland;

    public ConnectedGrassBlockkMixin(Properties properties, Supplier<? extends Block> dirt, @Nullable Supplier<? extends Block> path, @Nullable Supplier<? extends Block> farmland)
    {
        super(properties);
        this.path = path;
        this.farmland = farmland;
    }

    @Overwrite(remap = false)
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(NORTH, EAST, SOUTH, WEST, SNOWY, NATURAL);
    }

    @Overwrite(remap = false)
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState stateUp = context.getLevel().getBlockState(context.getClickedPos().above());
        return updateStateFromNeighbors(context.getLevel(), context.getClickedPos(), defaultBlockState()).setValue(SNOWY, Helpers.isBlock(stateUp, Blocks.SNOW_BLOCK) || Helpers.isBlock(stateUp, Blocks.SNOW)).setValue(NATURAL, context.getPlayer() == null);
    }

    @Overwrite(remap = false)
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction action, boolean simulate)
    {
        if (context.getItemInHand().canPerformAction(action))
        {
            if (action == ToolActions.SHOVEL_FLATTEN && TFCConfig.SERVER.enableGrassPathCreation.get() && path != null)
            {
                return path.get().defaultBlockState();
            }
            if (action == ToolActions.HOE_TILL && farmland != null && TFCConfig.SERVER.enableFarmlandCreation.get() && HoeItem.onlyIfAirAbove(context))
            {
                BlockState farmlandState = farmland.get().defaultBlockState();
                HoeItem.changeIntoState(farmlandState).accept(context);

                if (context.getLevel() instanceof ServerLevel level)
                {
                    final Random random = level.getRandom();
                    final BlockPos pos = context.getClickedPos();

                    if (state.getValue(NATURAL) && level.getBlockEntity(pos) instanceof IFarmland farmland)
                    {
                        final ChunkData data = ChunkDataProvider.get(level).get(level, pos);

                        float rainfallFactor = ((ClimateModel.MAXIMUM_RAINFALL - data.getRainfall(pos)) / 5F) * 0.01F;

                        float nitrogenAmount = TFCFHelpers.getRockNutrient(TFCFHelpers.rockType(level, pos), NutrientType.NITROGEN, random) * rainfallFactor;
                        float phosphorousAmount = TFCFHelpers.getRockNutrient(TFCFHelpers.rockType(level, pos), NutrientType.PHOSPHOROUS, random) * rainfallFactor;
                        float potassiumAmount = TFCFHelpers.getRockNutrient(TFCFHelpers.rockType(level, pos), NutrientType.POTASSIUM, random) * rainfallFactor;

                        farmland.addNutrient(NutrientType.NITROGEN, nitrogenAmount);
                        farmland.addNutrient(NutrientType.PHOSPHOROUS, phosphorousAmount);
                        farmland.addNutrient(NutrientType.POTASSIUM, potassiumAmount);
                    }
                }
                return farmlandState;
            }
        }
        return null;
    }

    @Shadow
    protected BlockState updateStateFromNeighbors(BlockGetter worldIn, BlockPos pos, BlockState state)
    {
        return state;
    }
}
