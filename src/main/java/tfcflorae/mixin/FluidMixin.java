package tfcflorae.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import net.dries007.tfc.common.blocks.DirectionPropertyBlock;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;

import tfcflorae.Config;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.Mineral;
import tfcflorae.common.blocks.rock.MineralSheetBlock;
import tfcflorae.common.items.TFCFItems;

@Mixin(Fluid.class)
public abstract class FluidMixin
{
    @Overwrite(remap = true)
    protected void randomTick(Level level, BlockPos pos, FluidState state, Random random)
    {
        if (random.nextInt(Config.COMMON.mineralGenFrequency.get()) == 0 && level instanceof ServerLevel serverLevel)
        {
            Fluid type = state.getType();
            Mineral mineral = null;

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
                final ItemStack mineralDeposit = new ItemStack(TFCFItems.MINERALS.get(mineral).get());

                final Direction genFace = Direction.getRandom(random);
                final Direction sheetFace = genFace.getOpposite();

                final BlockPos genPos = new BlockPos(random.nextInt(7) - 3, random.nextInt(7) - 3, random.nextInt(7) - 3);
                final BlockPos relativePos = genPos.relative(genFace);

                final BlockState genState = serverLevel.getBlockState(genPos);
                final BlockState relativeState = serverLevel.getBlockState(relativePos);

                final BooleanProperty property = DirectionPropertyBlock.getProperty(sheetFace);

                if (Helpers.isBlock(relativeState, TFCFBlocks.MINERAL_SHEET.get()))
                {
                    if (!relativeState.getValue(property) && MineralSheetBlock.canPlace(serverLevel, genPos, genState) && genState.isFaceSturdy(serverLevel, genPos, genFace))
                    {
                        MineralSheetBlock.addSheet(serverLevel, relativePos, relativeState, sheetFace, mineralDeposit);
                    }
                }
                else if (EnvironmentHelpers.isWorldgenReplaceable(serverLevel, relativePos))
                {
                    final BlockState placingState = TFCFBlocks.MINERAL_SHEET.get().defaultBlockState().setValue(property, true);
                    if (MineralSheetBlock.canPlace(serverLevel, genPos, placingState) && genState.isFaceSturdy(serverLevel, genPos, genFace))
                    {
                        MineralSheetBlock.addSheet(serverLevel, relativePos, placingState, sheetFace, mineralDeposit);
                    }
                }
            }
        }
    }
}
