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

@Mixin(Fluid.class)
public abstract class FluidMixin
{
    /*@Overwrite(remap = true)
    @SuppressWarnings("deprecation")
    protected void randomTick(Level level, BlockPos pos, FluidState state, Random random)
    {
        if (level.isAreaLoaded(pos, 10))
        {
            if (random.nextInt(Config.COMMON.mineralGenFrequency.get()) == 0 && level instanceof ServerLevel serverLevel)
            {
                Fluid type = state.getType();
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
                    ItemStack mineralDeposit = new ItemStack(TFCFItems.MINERALS.get(mineral).get().asItem());

                    BlockPos genPos = pos.offset(random.nextInt(7) - 3, random.nextInt(7) - 3, random.nextInt(7) - 3);
                    Direction face = Direction.getRandom(random);
                    BlockPos posAt = genPos.relative(face);
                    BlockState stateAt = serverLevel.getBlockState(posAt);
                    Direction sheetFace = face.getOpposite();
                    BooleanProperty property = DirectionPropertyBlock.getProperty(sheetFace);

                    TFCFlorae.LOGGER.debug("Trying to generate mineral deposit at XYZ: " + genPos.getX() + " " + genPos.getY() + " " + genPos.getZ());

                    if (Helpers.isBlock(stateAt, TFCFBlocks.MINERAL_SHEET.get()))
                    {
                        if (!stateAt.getValue(property) && MineralSheetBlock.canPlace(serverLevel, genPos, stateAt) && stateAt.isFaceSturdy(serverLevel, genPos, face))
                        {
                            //MineralSheetBlock.addSheet(serverLevel, posAt, stateAt, sheetFace, mineralDeposit);
                            MineralSheetBlock.addSheet(serverLevel, posAt, stateAt, face, mineralDeposit);
                        }
                    }
                    else if (EnvironmentHelpers.isWorldgenReplaceable(serverLevel, posAt) && (stateAt.getMaterial() != Material.LAVA || stateAt.getMaterial() != Material.WATER))
                    {
                        BlockState placingState = TFCFBlocks.MINERAL_SHEET.get().defaultBlockState().setValue(property, true);
                        if (MineralSheetBlock.canPlace(serverLevel, genPos, placingState) && stateAt.isFaceSturdy(serverLevel, genPos, face))
                        {
                            //MineralSheetBlock.addSheet(serverLevel, posAt, placingState, sheetFace, mineralDeposit);
                            MineralSheetBlock.addSheet(serverLevel, posAt, placingState, face, mineralDeposit);
                        }
                    }
                }
            }
        }
    }*/
}
