package tfcflorae.mixin.world.biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.dries007.tfc.world.biome.VolcanoNoise;
import net.dries007.tfc.world.noise.Cellular2D;
import net.dries007.tfc.world.noise.Noise2D;
import net.dries007.tfc.world.noise.OpenSimplex2D;

import static net.dries007.tfc.world.TFCChunkGenerator.SEA_LEVEL_Y;

@Mixin(VolcanoNoise.class)
public abstract class VolcanoNoiseMixin
{
    @Shadow
    private static float calculateEasing(float f1)
    {
        return Mth.map(f1, 0, 0.23f, 1, 0);
    }

    @Shadow
    private static float calculateClampedEasing(float f1)
    {
        return Mth.clamp(calculateEasing(f1), 0, 1);
    }

    @Shadow
    private static float calculateShape(float t)
    {
        if (t > 0.025f)
        {
            return (5f / (9f * t + 1) - 0.5f) * 0.279173646008f;
        }
        else
        {
            float a = (t * 9f + 0.05f);
            return (8f * a * a + 2.97663265306f) * 0.279173646008f;
        }
    }

    @Shadow private final Cellular2D cellNoise;
    @Shadow private final Noise2D jitterNoise;

    public VolcanoNoiseMixin(long seed)
    {
        cellNoise = new Cellular2D(seed).spread(0.009f);
        jitterNoise = new OpenSimplex2D(seed + 1234123L).octaves(2).scaled(-0.0016f, 0.0016f).spread(0.128f);
    }

    @Overwrite(remap = false)
    public float modifyHeight(float x, float z, float baseHeight, int rarity, int baseVolcanoHeight, int scaleVolcanoHeight)
    {
        final Cellular2D.Cell cell = sampleCell(x, z, rarity);
        if (cell != null)
        {
            int volcanoBase = baseVolcanoHeight <= -15 && scaleVolcanoHeight >= 50 ? SEA_LEVEL_Y - 93 : SEA_LEVEL_Y; // For guyot and seamount biomes
            volcanoBase = baseVolcanoHeight <= -15 && scaleVolcanoHeight <= 10 ? SEA_LEVEL_Y - 263 : SEA_LEVEL_Y; // Volcanoes in the caverns

            final float easing = Mth.clamp(VolcanoNoiseMixin.calculateEasing(cell.f1()) + jitterNoise.noise(x, z), 0, 1);
            final float shape = VolcanoNoiseMixin.calculateShape(1 - easing);
            final float volcanoHeight = volcanoBase + baseVolcanoHeight + shape * scaleVolcanoHeight;
            final float volcanoAdditionalHeight = shape * scaleVolcanoHeight;
            return Mth.lerp(easing, baseHeight, 0.5f * (volcanoHeight + Math.max(volcanoHeight, baseHeight + 0.4f * volcanoAdditionalHeight)));
        }
        return baseHeight;
    }

    @Shadow
    public float calculateEasing(int x, int z, int rarity)
    {
        final Cellular2D.Cell cell = sampleCell(x, z, rarity);
        if (cell != null)
        {
            return calculateClampedEasing(cell.f1());
        }
        return 0;
    }

    @Shadow
    @Nullable
    public BlockPos calculateCenter(int x, int y, int z, int rarity)
    {
        final Cellular2D.Cell cell = sampleCell(x, z, rarity);
        if (cell != null)
        {
            return new BlockPos((int) cell.x(), y, (int) cell.y());
        }
        return null;
    }

    @Shadow
    @Nullable
    private Cellular2D.Cell sampleCell(float x, float z, int rarity)
    {
        final Cellular2D.Cell cell = cellNoise.cell(x, z);
        if (Math.abs(cell.noise()) <= 1f / rarity)
        {
            return cell;
        }
        return null;
    }
}
