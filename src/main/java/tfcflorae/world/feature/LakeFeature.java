package tfcflorae.world.feature;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Material;

import com.mojang.serialization.Codec;

public class LakeFeature extends Feature<LakeConfig>
{
    private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

    public LakeFeature(Codec<LakeConfig> codec)
    {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<LakeConfig> context)
    {
        final WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        final Random random = context.random();
        final LakeConfig config = context.config();

        if (pos.getY() <= level.getMinBuildHeight() + 4)
        {
            return false;
        }
        else
        {
            pos = pos.below(4);
            boolean[] aBoolean = new boolean[2048];
            int i = random.nextInt(4) + 4;

            for(int j = 0; j < i; ++j)
            {
                double d0 = random.nextDouble() * 6.0D + 3.0D;
                double d1 = random.nextDouble() * 4.0D + 2.0D;
                double d2 = random.nextDouble() * 6.0D + 3.0D;
                double d3 = random.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = random.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = random.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for(int l = 1; l < 15; ++l)
                {
                    for(int i1 = 1; i1 < 15; ++i1)
                    {
                        for(int j1 = 1; j1 < 7; ++j1)
                        {
                            double d6 = ((double)l - d3) / (d0 / 2.0D);
                            double d7 = ((double)j1 - d4) / (d1 / 2.0D);
                            double d8 = ((double)i1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                            if (d9 < 1.0D)
                            {
                                aBoolean[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }
            BlockState blockstate1 = config.fluid().getState(random, pos);
            for(int k1 = 0; k1 < 16; ++k1)
            {
                for(int k = 0; k < 16; ++k)
                {
                    for(int l2 = 0; l2 < 8; ++l2)
                    {
                        boolean flag = !aBoolean[(k1 * 16 + k) * 8 + l2] && (k1 < 15 && aBoolean[((k1 + 1) * 16 + k) * 8 + l2] || k1 > 0 && aBoolean[((k1 - 1) * 16 + k) * 8 + l2] || k < 15 && aBoolean[(k1 * 16 + k + 1) * 8 + l2] || k > 0 && aBoolean[(k1 * 16 + (k - 1)) * 8 + l2] || l2 < 7 && aBoolean[(k1 * 16 + k) * 8 + l2 + 1] || l2 > 0 && aBoolean[(k1 * 16 + k) * 8 + (l2 - 1)]);
                        if (flag)
                        {
                            Material material = level.getBlockState(pos.offset(k1, l2, k)).getMaterial();
                            if (l2 >= 4 && material.isLiquid())
                            {
                                return false;
                            }
                            if (l2 < 4 && !material.isSolid() && level.getBlockState(pos.offset(k1, l2, k)) != blockstate1)
                            {
                                return false;
                            }
                        }
                    }
                }
            }
            for(int l1 = 0; l1 < 16; ++l1)
            {
                for(int i2 = 0; i2 < 16; ++i2)
                {
                    for(int i3 = 0; i3 < 8; ++i3)
                    {
                        if (aBoolean[(l1 * 16 + i2) * 8 + i3])
                        {
                        BlockPos pos1 = pos.offset(l1, i3, i2);
                        if (this.canReplaceBlock(level.getBlockState(pos1)))
                        {
                            boolean flag1 = i3 >= 4;
                            level.setBlock(pos1, flag1 ? AIR : blockstate1, 2);
                            if (flag1)
                            {
                                level.scheduleTick(pos1, AIR.getBlock(), 0);
                                this.markAboveForPostProcessing(level, pos1);
                            }
                        }
                        }
                    }
                }
            }
            BlockState blockstate2 = config.state().getState(random, pos);
            if (!blockstate2.isAir())
            {
                for(int j2 = 0; j2 < 16; ++j2)
                {
                    for(int j3 = 0; j3 < 16; ++j3)
                    {
                        for(int l3 = 0; l3 < 8; ++l3)
                        {
                        boolean flag2 = !aBoolean[(j2 * 16 + j3) * 8 + l3] && (j2 < 15 && aBoolean[((j2 + 1) * 16 + j3) * 8 + l3] || j2 > 0 && aBoolean[((j2 - 1) * 16 + j3) * 8 + l3] || j3 < 15 && aBoolean[(j2 * 16 + j3 + 1) * 8 + l3] || j3 > 0 && aBoolean[(j2 * 16 + (j3 - 1)) * 8 + l3] || l3 < 7 && aBoolean[(j2 * 16 + j3) * 8 + l3 + 1] || l3 > 0 && aBoolean[(j2 * 16 + j3) * 8 + (l3 - 1)]);
                        if (flag2 && (l3 < 4 || random.nextInt(2) != 0))
                        {
                            BlockState blockstate = level.getBlockState(pos.offset(j2, l3, j3));
                            if (blockstate.getMaterial().isSolid() && !blockstate.is(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE))
                            {
                                BlockPos pos3 = pos.offset(j2, l3, j3);
                                level.setBlock(pos3, blockstate2, 2);
                                this.markAboveForPostProcessing(level, pos3);
                            }
                        }
                        }
                    }
                }
            }
            if (blockstate1.getFluidState().is(FluidTags.WATER))
            {
                for(int k2 = 0; k2 < 16; ++k2)
                {
                    for(int k3 = 0; k3 < 16; ++k3)
                    {
                        int i4 = 4;
                        BlockPos pos2 = pos.offset(k2, 4, k3);
                        if (level.getBiome(pos2).value().shouldFreeze(level, pos2, false) && this.canReplaceBlock(level.getBlockState(pos2)))
                        {
                            level.setBlock(pos2, Blocks.ICE.defaultBlockState(), 2);
                        }
                    }
                }
            }
            return true;
        }
    }

    public boolean canReplaceBlock(BlockState state)
    {
        return !state.is(BlockTags.FEATURES_CANNOT_REPLACE);
    }
}
