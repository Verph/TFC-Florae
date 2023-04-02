package tfcflorae.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;

import net.dries007.tfc.common.blocks.DirectionPropertyBlock;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.noise.OpenSimplex2D;

import tfcflorae.Config;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.Mineral;
import tfcflorae.common.blocks.rock.MineralSheetBlock;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.util.TFCFHelpers;

import static net.dries007.tfc.common.blocks.rock.RockCategory.*;

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

                float variantNoiseValue = new OpenSimplex2D(level.getSeed()).octaves(2).spread(0.01f).abs().noise(pos.getX(), pos.getZ()) * 0.9f + random.nextFloat() * 0.1f;
                Boolean categoryIgneousExtrusive = TFCFHelpers.rockType(level, pos).category() == IGNEOUS_EXTRUSIVE;
                Boolean categoryIgneousIntrusive = TFCFHelpers.rockType(level, pos).category() == IGNEOUS_INTRUSIVE;
                Boolean categoryMetamorphic = TFCFHelpers.rockType(level, pos).category() == METAMORPHIC;
                Boolean categorySedimentary = TFCFHelpers.rockType(level, pos).category() == SEDIMENTARY;

                if (type == Fluids.LAVA.getSource() || state.getBlock() == Blocks.LAVA)
                {
                    if (variantNoiseValue > 0.25F)
                    {
                        if (categoryIgneousExtrusive)
                        {
                            mineral = Mineral.GREIGITE;
                        }
                        else if (categoryIgneousIntrusive)
                        {
                            mineral = Mineral.SMITHSONITE;
                        }
                        else if (categoryMetamorphic)
                        {
                            mineral = Mineral.BRIMSTONE;
                        }
                        else if (categorySedimentary)
                        {
                            mineral = Mineral.MAGNESITE;
                        }
                    }
                    else if (variantNoiseValue > -0.75F)
                    {
                        if (categoryIgneousExtrusive)
                        {
                            mineral = Mineral.ZABUYELITE;
                        }
                        else if (categoryIgneousIntrusive)
                        {
                            mineral = Mineral.SPHEROCOBALTITE;
                        }
                        else if (categoryMetamorphic)
                        {
                            mineral = Mineral.ALABANDITE;
                        }
                        else if (categorySedimentary)
                        {
                            mineral = Mineral.GREIGITE;
                        }
                    }
                    else
                    {
                        if (categoryIgneousExtrusive)
                        {
                            mineral = Mineral.BRIMSTONE;
                        }
                        else if (categoryIgneousIntrusive)
                        {
                            mineral = Mineral.BRIMSTONE;
                        }
                        else if (categoryMetamorphic)
                        {
                            mineral = Mineral.BASTNASITE;
                        }
                        else if (categorySedimentary)
                        {
                            mineral = Mineral.APATITE;
                        }
                    }
                }
                if (type == TFCFluids.SPRING_WATER.getSource() || state.getBlock() == TFCBlocks.SPRING_WATER.get())
                {
                    if (categoryIgneousExtrusive)
                    {
                        mineral = Mineral.SALMIAK;
                    }
                    else if (categoryIgneousIntrusive)
                    {
                        mineral = Mineral.SALTPETER;
                    }
                    else if (categoryMetamorphic)
                    {
                        mineral = Mineral.SALT;
                    }
                    else if (categorySedimentary)
                    {
                        mineral = Mineral.CALCITE;
                    }
                }

                if (mineral != null && type != null)
                {
                    ItemStack itemMineral = new ItemStack(TFCFItems.MINERALS.get(mineral).get().asItem());

                    BlockPos genPos = pos.offset(random.nextInt(7) - 3, random.nextInt(7) - 3, random.nextInt(7) - 3);
                    BlockState stateAt = level.getBlockState(genPos);

                    Direction face = Direction.getRandom(random);
                    Direction sheetFace = face.getOpposite();

                    BooleanProperty property = DirectionPropertyBlock.getProperty(face);

                    BlockPos adjacentPos = genPos.relative(face);
                    BlockState adjacentState = level.getBlockState(adjacentPos);

                    if (Helpers.isBlock(stateAt, TFCFBlocks.MINERAL_SHEET.get()))
                    {
                        if (!stateAt.getValue(property) && adjacentState.isFaceSturdy(level, adjacentPos, sheetFace))
                        {
                            MineralSheetBlock.addSheet(level, genPos, stateAt, face, itemMineral);
                        }
                    }
                    else if (stateAt.getMaterial().isReplaceable() && !(stateAt.getMaterial() == Material.LAVA || stateAt.getMaterial() == Material.WATER))
                    {
                        if (adjacentState.isFaceSturdy(level, adjacentPos, sheetFace))
                        {
                            BlockState placingState = TFCFBlocks.MINERAL_SHEET.get().defaultBlockState().setValue(property, true);
                            MineralSheetBlock.addSheet(level, genPos, placingState, face, itemMineral);
                        }
                    }
                }
            }
        }
    }
}
