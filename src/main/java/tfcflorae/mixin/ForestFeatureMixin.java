package tfcflorae.mixin;

import java.util.Locale;
import java.util.Random;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.wood.ILeavesBlock;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.feature.tree.ForestConfig;
import net.dries007.tfc.world.feature.tree.ForestFeature;

import tfcflorae.common.TFCFTags;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.util.TFCFHelpers;

@Mixin(ForestFeature.class)
public abstract class ForestFeatureMixin extends Feature<ForestConfig>
{
    public ForestFeatureMixin(Codec<ForestConfig> codec)
    {
        super(codec);
    }

    @Overwrite(remap = false)
    private void placeFallenTree(WorldGenLevel level, Random random, BlockPos chunkBlockPos, ForestConfig config, ChunkData data, BlockPos.MutableBlockPos mutablePos)
    {
        final int chunkX = chunkBlockPos.getX();
        final int chunkZ = chunkBlockPos.getZ();

        mutablePos.set(chunkX + random.nextInt(16), 0, chunkZ + random.nextInt(16));
        mutablePos.setY(level.getHeight(Heightmap.Types.OCEAN_FLOOR, mutablePos.getX(), mutablePos.getZ()));

        mutablePos.move(Direction.DOWN);
        BlockState downState = level.getBlockState(mutablePos);
        mutablePos.move(Direction.UP);
        if (Helpers.isBlock(downState, TFCTags.Blocks.BUSH_PLANTABLE_ON) || Helpers.isBlock(downState, TFCTags.Blocks.SEA_BUSH_PLANTABLE_ON))
        {
            final ForestConfig.Entry entry = getTree(data, random, config, mutablePos);
            if (entry != null)
            {
                final int fallChance = entry.fallenChance();
                if (fallChance > 0 && level.getRandom().nextInt(fallChance) == 0)
                {
                    BlockState log = entry.fallenLog().orElse(null);
                    if (log != null)
                    {
                        final Direction axis = Direction.Plane.HORIZONTAL.getRandomDirection(random);

                        log = Helpers.setProperty(log, TFCBlockStateProperties.NATURAL, false);
                        log = Helpers.setProperty(log, BlockStateProperties.AXIS, axis.getAxis());

                        final int length = 4 + random.nextInt(10);
                        final BlockPos start = mutablePos.immutable();
                        final boolean[] moment = new boolean[length];

                        mutablePos.set(start);
                        int valid = 0;
                        for (; valid < length; valid++)
                        {
                            final BlockState replaceState = level.getBlockState(mutablePos);
                            if (EnvironmentHelpers.isWorldgenReplaceable(replaceState) || replaceState.getBlock() instanceof ILeavesBlock)
                            {
                                mutablePos.move(Direction.DOWN);
                                moment[valid] = level.getBlockState(mutablePos).isFaceSturdy(level, mutablePos, Direction.UP);
                            }
                            else
                            {
                                break;
                            }

                            mutablePos.move(Direction.UP);
                            mutablePos.move(axis);
                        }

                        int left = 0, right = valid - 1;
                        for (; left < moment.length; left++)
                        {
                            if (moment[left]) break;
                        }
                        for (; right >= 0; right--)
                        {
                            if (moment[right]) break;
                        }

                        if (left <= valid / 2 && right >= valid / 2 && valid >= 3)
                        {
                            // Balanced
                            mutablePos.set(start);
                            for (int i = 0; i < length; i++)
                            {
                                level.setBlock(mutablePos, log, Block.UPDATE_ALL);
                                if (random.nextInt(10) == 0)
                                {
                                    BlockState fungiState = Helpers.getRandomElement(ForgeRegistries.BLOCKS, TFCFTags.Blocks.MAGMA_BLOCKS, random).get().defaultBlockState();
                                    if (fungiState.canSurvive(level, mutablePos.above()))
                                    {
                                        level.setBlock(mutablePos.above(), Helpers.getRandomElement(ForgeRegistries.BLOCKS, TFCFTags.Blocks.MAGMA_BLOCKS, random).get().defaultBlockState(), Block.UPDATE_ALL);
                                    }
                                }

                                final int radius = Mth.nextInt(random, 1, 5); // Within 1 to 5 (inclusive)
                                final int radiusSquared = radius * radius;

                                for (int x = mutablePos.getX() - radius; x <= mutablePos.getX() + radius; ++x)
                                {
                                    for (int z = mutablePos.getZ() - radius; z <= mutablePos.getZ() + radius; ++z)
                                    {
                                        final int relX = x - mutablePos.getX();
                                        final int relZ = z - mutablePos.getZ();

                                        if (relX * relX + relZ * relZ <= radiusSquared)
                                        {
                                            int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z) - 1;
                                            mutablePos.set(x, y, z);

                                            final BlockState stateAt = level.getBlockState(mutablePos);

                                            if (Helpers.isBlock(stateAt, BlockTags.DIRT) && level.getBlockState(mutablePos.above()).isAir())
                                            {
                                                if (stateAt.getBlock().getName().toString().toLowerCase(Locale.ROOT).contains("bog_iron"))
                                                {
                                                    level.setBlock(mutablePos, TFCFBlocks.MYCELIUM_BOG_IRON.get().defaultBlockState(), Block.UPDATE_ALL);
                                                }
                                                else if (TFCFHelpers.isVanillaSoilVariant(level, mutablePos))
                                                {
                                                    level.setBlock(mutablePos, TFCFBlocks.TFCSOIL.get(TFCFSoil.MYCELIUM_DIRT).get(TFCFHelpers.getSoilVariant(level, mutablePos)).get().defaultBlockState(), Block.UPDATE_ALL);
                                                }
                                                else
                                                {
                                                    level.setBlock(mutablePos, TFCFBlocks.TFCFSOIL.get(TFCFSoil.MYCELIUM_DIRT).get(TFCFHelpers.getSoilVariant(level, mutablePos)).get().defaultBlockState(), Block.UPDATE_ALL);
                                                }

                                                if (random.nextInt(10) == 0)
                                                {
                                                    BlockState fungiState = Helpers.getRandomElement(ForgeRegistries.BLOCKS, TFCFTags.Blocks.MAGMA_BLOCKS, random).get().defaultBlockState();
                                                    if (fungiState.canSurvive(level, mutablePos))
                                                    {
                                                        level.setBlock(mutablePos, Helpers.getRandomElement(ForgeRegistries.BLOCKS, TFCFTags.Blocks.MAGMA_BLOCKS, random).get().defaultBlockState(), Block.UPDATE_ALL);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }   
                                mutablePos.move(axis);
                            }
                        }
                    }
                }
            }
        }
    }

    @Shadow
    @Nullable
    private ForestConfig.Entry getTree(ChunkData chunkData, Random random, ForestConfig config, BlockPos pos)
    {
        return null;
    }
}
