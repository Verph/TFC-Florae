package tfcflorae.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Locale;
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
import net.minecraft.world.level.material.Material;

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
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        state.getFluidState().randomTick(level, pos, random);
        if (level.isAreaLoaded(pos, 10))
        {
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
                    ItemStack itemMineral = new ItemStack(TFCFItems.MINERALS.get(mineral).get().asItem());

                    BlockPos genPos = pos.offset(random.nextInt(7) - 3, random.nextInt(7) - 3, random.nextInt(7) - 3);
                    Direction face = Direction.getRandom(random);
                    BlockPos posAt = genPos.relative(face);
                    BlockState stateAt = level.getBlockState(posAt);
                    Direction sheetFace = face.getOpposite();
                    BooleanProperty property = DirectionPropertyBlock.getProperty(sheetFace);
                    Boolean canSurvive = TFCFBlocks.MINERAL_SHEET.get().canSurvive(stateAt, level, genPos);

                    if (Helpers.isBlock(stateAt, TFCFBlocks.MINERAL_SHEET.get()) && !stateAt.getValue(property) && canSurvive)
                    {
                        TFCFlorae.LOGGER.debug("Passed is mineral block check");
                        TFCFlorae.LOGGER.debug("Trying to generate mineral deposit at XYZ: " + posAt.getX() + " " + posAt.getY() + " " + posAt.getZ());

                        MineralSheetBlock.addSheet(level, posAt, stateAt, sheetFace, itemMineral);
                    }
                    else if (stateAt.getMaterial().isReplaceable() && (stateAt.getMaterial() != Material.LAVA || stateAt.getMaterial() != Material.WATER) && canSurvive)
                    {
                        TFCFlorae.LOGGER.debug("Passed material/replaceable check");
                        TFCFlorae.LOGGER.debug("Trying to generate new mineral deposit at XYZ: " + posAt.getX() + " " + posAt.getY() + " " + posAt.getZ());

                        BlockState placingState = TFCFBlocks.MINERAL_SHEET.get().defaultBlockState().setValue(property, true);
                        MineralSheetBlock.addSheet(level, posAt, placingState, sheetFace, itemMineral);
                    }
                }
            }
        }
    }
}
