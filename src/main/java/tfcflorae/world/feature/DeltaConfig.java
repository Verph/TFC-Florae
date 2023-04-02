package tfcflorae.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class DeltaConfig implements FeatureConfiguration
{
    public static final Codec<DeltaConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockState.CODEC.fieldOf("contents").forGetter((contents) -> {
            return contents.contents;
        }), BlockState.CODEC.fieldOf("rim").forGetter((rim) -> {
            return rim.rim;
        }), BlockState.CODEC.fieldOf("bottom").forGetter((bottom) -> {
            return bottom.bottom;
        }), IntProvider.codec(0, 16).fieldOf("size").forGetter((size) -> {
            return size.size;
        }), IntProvider.codec(0, 16).fieldOf("rim_size").forGetter((rimSize) -> {
            return rimSize.rimSize;
        })).apply(instance, DeltaConfig::new);
    });
    private final BlockState contents;
    private final BlockState rim;
    private final BlockState bottom;
    private final IntProvider size;
    private final IntProvider rimSize;

    public DeltaConfig(BlockState contents, BlockState rim, BlockState bottom, IntProvider size, IntProvider rimSize)
    {
        this.contents = contents;
        this.rim = rim;
        this.bottom = bottom;
        this.size = size;
        this.rimSize = rimSize;
    }

    public BlockState contents()
    {
        return this.contents;
    }

    public BlockState rim()
    {
        return this.rim;
    }

    public BlockState bottom()
    {
        return this.bottom;
    }

    public IntProvider size()
    {
        return this.size;
    }

    public IntProvider rimSize()
    {
        return this.rimSize;
    }
}
