package tfcflorae.mixin.world.chunkdata;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.dries007.tfc.world.chunkdata.RockData;
import net.dries007.tfc.world.settings.RockSettings;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.RandomSource;
import tfcflorae.util.TFCFHelpers;

import static net.dries007.tfc.world.TFCChunkGenerator.*;

@Mixin(RockData.class)
public class RockDataMixin
{
    @Shadow private static final int SIZE = 16 * 16;

    @Shadow
    private static int index(int x, int z)
    {
        return (x & 15) | ((z & 15) << 4);
    }

    @Shadow private final RockSettings[] bottomLayer;
    @Shadow private final RockSettings[] middleLayer;
    @Shadow private final RockSettings[] topLayer;
    @Shadow private final int[] rockLayerHeight;

    @Shadow private int @Nullable [] surfaceHeight;

    public RockDataMixin(RockSettings[] bottomLayer, RockSettings[] middleLayer, RockSettings[] topLayer, int[] rockLayerHeight)
    {
        this.bottomLayer = bottomLayer;
        this.middleLayer = middleLayer;
        this.topLayer = topLayer;
        this.rockLayerHeight = rockLayerHeight;
        this.surfaceHeight = null;
    }

    @Overwrite(remap = false)
    public RockSettings getRock(int x, int y, int z)
    {
        assert surfaceHeight != null;

        final RandomSource random = TFCFHelpers.randomSource(x, y, z);

        final int i = index(x, z);
        final int sh = surfaceHeight[i];
        final int rh = rockLayerHeight[i];
        final int gaussSpread = (int) (Mth.abs((float) random.nextGaussian()) * Mth.sqrt(sh * 0.5F));

        if (y > (int) ((SEA_LEVEL_Y + SEA_LEVEL_Y - 0.2 * sh + rh) + (gaussSpread * 0.2F)))
        {
            return topLayer[i];
        }
        else if (y > (int) ((SEA_LEVEL_Y + 32 - 0.2 * (sh + gaussSpread) + rh) + (gaussSpread * 0.5F)))
        {
            return middleLayer[i];
        }
        else if (y > (int) ((SEA_LEVEL_Y - 0.2 * sh + rh) + (gaussSpread * 0.25F)))
        {
            return bottomLayer[i];
        }
        else if (y > (int) ((SEA_LEVEL_Y - 16 - 0.2 * (sh + (gaussSpread * 0.25F)) + rh) + (gaussSpread * 0.25F)))
        {
            return topLayer[i];
        }
        else if (y > (int) ((SEA_LEVEL_Y - 48 - 0.3 * (sh + (gaussSpread * 0.25F)) + (rh + gaussSpread)) + (gaussSpread * 0.8F)))
        {
            return middleLayer[i];
        }
        else if (y > (int) ((SEA_LEVEL_Y - 96 - 0.3 * sh + rh + gaussSpread)))
        {
            return bottomLayer[i];
        }
        else if (y > (int) ((SEA_LEVEL_Y - 156 - 0.2 * sh + rh) + (gaussSpread * 0.7F)))
        {
            return middleLayer[i];
        }
        else if (y > (int) ((SEA_LEVEL_Y - 176 - 0.2 * sh + rh) + (gaussSpread * 0.8F)))
        {
            return topLayer[i];
        }
        else
        {
            return bottomLayer[i];
        }
    }
}
