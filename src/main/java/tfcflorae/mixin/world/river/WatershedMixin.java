package tfcflorae.mixin.world.river;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import net.dries007.tfc.world.FastConcurrentCache;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.LerpFloatLayer;
import net.dries007.tfc.world.layer.Plate;
import net.dries007.tfc.world.layer.framework.TypedArea;
import net.dries007.tfc.world.layer.framework.TypedAreaFactory;
import net.dries007.tfc.world.river.*;

@Mixin(Watershed.class)
public abstract class WatershedMixin
{
    @Shadow @Mutable @Final public static final float RIVER_WIDTH = 0.013f * 20f;

    @Mixin(Watershed.Context.class)
    public static class ContextMixin
    {
        /**
         * Parameters that are tweaked for best performance.
         */
        @Shadow private static final int PARTITION_BITS = 5;
        @Shadow private static final int ZOOM_BITS = 7;

        @Shadow private static final int WATERSHED_CACHE_BITS = 8;
        @Shadow private static final int PARTITION_CACHE_BITS = 10;

        /**
         * The diagonal from the center of a unit cell, to the corner.
         * Scale is partition coordinates.
         */
        @Shadow private static final float PARTITION_RADIUS = (float) Math.sqrt(2) / 2f;
        @Shadow private static final int PARTITION_TO_ZOOM_BITS = ZOOM_BITS - PARTITION_BITS;

        @Shadow private final ThreadLocal<TypedArea<Plate>> plates;
        @Shadow private final FastConcurrentCache<Watershed> watershedCache;
        @Shadow private final FastConcurrentCache<List<MidpointFractal>> partitionCache;

        @Shadow private final long seed;
        @Shadow private final float sourceChance;
        @Shadow private final float length;
        @Shadow private final int depth;
        @Shadow private final float feather;
        @Unique @Final private ChunkData chunkData;
        @Nullable @Unique @Final private LerpFloatLayer rainfallLayer;

        public ContextMixin(TypedAreaFactory<Plate> plates, long seed, float sourceChance, float length, int depth, float feather)
        {
            this.plates = ThreadLocal.withInitial(plates);
            this.watershedCache = new FastConcurrentCache<>(1 << WATERSHED_CACHE_BITS);
            this.partitionCache = new FastConcurrentCache<>(1 << PARTITION_CACHE_BITS);
            this.seed = seed;
            this.sourceChance = sourceChance;
            this.length = length;
            this.depth = depth;
            this.feather = feather;
        }

        /**
         * Input coordinates are biome quart positions.
         * Partition coordinates are quart positions shifted by {@link #PARTITION_BITS}.
         * Watershed coordinates are quart positions shifted by {@link #ZOOM_BITS}. (Based on the total amount of zoom layers used between plate layers and the final biome area.)
         * In order to compute the partition, we query the four adjacent watersheds, which may overlap the partition area.
         */
        @Overwrite(remap = false)
        public List<MidpointFractal> getFractalsByPartition(int x, int z)
        {
            final int px = x >> PARTITION_BITS, pz = z >> PARTITION_BITS;
            List<MidpointFractal> partition = partitionCache.getIfPresent(px, pz);
            if (partition == null)
            {
                // Locate the four closest adjacent watersheds.
                final Set<Watershed> nearbySheds = new ObjectOpenHashSet<>(2);
                final float watershedScale = 1f / (1 << ZOOM_BITS);
                final float x0 = x * watershedScale, z0 = z * watershedScale;

                nearbySheds.add(create(x0 - 0.5f, z0 - 0.5f));
                nearbySheds.add(create(x0 + 0.5f, z0 - 0.5f));
                nearbySheds.add(create(x0 + 0.5f, z0 + 0.5f));
                nearbySheds.add(create(x0 - 0.5f, z0 + 0.5f));

                // Then, we iterate all sheds, all rivers, and all fractals, and partition out only those fractals which come within a minimum distance of the partition area.
                // We define that minimum distance as a circular radius from the center of the partition region, with a radius s.t. the entire region is encompassed (effectively, an inscribed square in the circle).
                final float partitionCenterX = px + 0.5f, partitionCenterZ = pz + 0.5f;
                final float partitionToWatershedScale = 1f / (1 << PARTITION_TO_ZOOM_BITS);

                float rainfall = 1f;
                if (rainfallLayer != null)
                {
                    rainfall = rainfallLayer.getValue(((int) z0 & 15) / 16f, 1 - (((int) x0 & 15) / 16f)) / 65f;
                }
                else if (chunkData != null)
                {
                    rainfall = chunkData.getRainfall((int) x0, (int) z0) / 65f;
                }

                final float x1 = partitionToWatershedScale * partitionCenterX, z1 = partitionToWatershedScale * partitionCenterZ;
                final float radius = partitionToWatershedScale * (PARTITION_RADIUS + 2 * (RIVER_WIDTH * rainfall));

                partition = new ArrayList<>(32);
                for (Watershed shed : nearbySheds)
                {
                    for (RiverFractal river : shed.getRivers())
                    {
                        for (MidpointFractal fractal : river.getFractals())
                        {
                            if (fractal.maybeIntersect(x1, z1, radius))
                            {
                                partition.add(fractal);
                            }
                        }
                    }
                }

                // Enter the resulting partition in the cache
                partitionCache.set(px, pz, partition);
            }
            return partition;
        }

        @Shadow
        public Watershed create(float x, float z)
        {
            return create(RiverHelpers.floor(x), RiverHelpers.floor(z));
        }

        @Shadow
        public Watershed create(int x, int z)
        {
            Watershed shed = watershedCache.getIfPresent(x, z);
            if (shed == null)
            {
                shed = Watershed.create(plates.get(), x, z, seed, sourceChance, length, depth, feather);
                watershedCache.set(x, z, shed);
            }
            return shed;
        }
    }
}
