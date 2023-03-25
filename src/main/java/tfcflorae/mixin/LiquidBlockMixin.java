package tfcflorae.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import net.dries007.tfc.common.blocks.DirectionPropertyBlock;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;

import tfcflorae.Config;
import tfcflorae.TFCFlorae;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.Mineral;
import tfcflorae.common.blocks.rock.MineralSheetBlock;
import tfcflorae.common.items.TFCFItems;

@Mixin(LiquidBlock.class)
public abstract class LiquidBlockMixin
{
    @Overwrite(remap = true)
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        state.getFluidState().randomTick(level, pos, random);
        if (random.nextInt(Config.COMMON.mineralGenFrequency.get()) == 0)
        {
            Fluid type = level.getFluidState(pos).getType();
            Mineral mineral = null;

            TFCFlorae.LOGGER.debug("Current fluid type is: " + type.toString());

            if (type == Fluids.LAVA)
            {
                mineral = Mineral.BRIMSTONE;
            }
            else if (type == TFCFluids.SPRING_WATER.getSource())
            {
                mineral = Mineral.SALMIAK;
            }
            else if (type == TFCFluids.SALT_WATER.getSource())
            {
                mineral = Mineral.SALT;
            }
            else
            {
                mineral = Mineral.SPHEROCOBALTITE;
            }

            if (mineral != null)
            {
                final ItemStack mineralDeposit = new ItemStack(TFCFItems.MINERALS.get(mineral).get().asItem());

                final Direction genFace = Direction.getRandom(random);
                final Direction sheetFace = genFace.getOpposite();

                final BlockPos genPos = pos.offset(random.nextInt(7) - 3, random.nextInt(7) - 3, random.nextInt(7) - 3);
                final BlockPos relativePos = genPos.relative(genFace);

                final BlockState genState = level.getBlockState(genPos);
                final BlockState relativeState = level.getBlockState(relativePos);

                final BooleanProperty property = DirectionPropertyBlock.getProperty(sheetFace);

                TFCFlorae.LOGGER.debug("Trying to generate mineral deposit at XYZ: " + genPos.getX() + ", " + genPos.getY() + ", " + genPos.getZ());

                if (Helpers.isBlock(relativeState, TFCFBlocks.MINERAL_SHEET.get()))
                {
                    if (!relativeState.getValue(property) && MineralSheetBlock.canPlace(level, genPos, genState) && genState.isFaceSturdy(level, genPos, genFace))
                    {
                        MineralSheetBlock.addSheet(level, relativePos, relativeState, sheetFace, mineralDeposit);
                    }
                }
                else if (EnvironmentHelpers.isWorldgenReplaceable(level, relativePos) && level.getFluidState(genPos).getType() == Fluids.EMPTY)
                {
                    final BlockState placingState = TFCFBlocks.MINERAL_SHEET.get().defaultBlockState().setValue(property, true);
                    if (MineralSheetBlock.canPlace(level, genPos, placingState) && genState.isFaceSturdy(level, genPos, genFace))
                    {
                        MineralSheetBlock.addSheet(level, relativePos, placingState, sheetFace, mineralDeposit);
                    }
                }
            }
        }
    }
}
