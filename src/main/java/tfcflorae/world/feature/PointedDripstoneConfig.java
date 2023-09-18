package tfcflorae.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class PointedDripstoneConfig implements FeatureConfiguration
{
    public static final Codec<PointedDripstoneConfig> CODEC = RecordCodecBuilder.create((p_191286_) -> {
        return p_191286_.group(
            Codec.BOOL.optionalFieldOf("has_surface", false).forGetter(c -> c.hasSurface),
            BlockStateProvider.CODEC.fieldOf("state").forGetter(c -> c.state),
            BlockStateProvider.CODEC.fieldOf("surface_state").forGetter(c -> c.surfaceState),
            Codec.intRange(0, 16).fieldOf("height").orElse(2).forGetter((p_191294_) -> {
            return p_191294_.height;
            }), 
            Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_dripstone").orElse(0.2F).forGetter((p_191294_) -> {
            return p_191294_.chanceOfTallerDripstone;
            }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_directional_spread").orElse(0.7F).forGetter((p_191292_) -> {
            return p_191292_.chanceOfDirectionalSpread;
            }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius2").orElse(0.5F).forGetter((p_191290_) -> {
            return p_191290_.chanceOfSpreadRadius2;
            }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius3").orElse(0.5F).forGetter((p_191288_) -> {
            return p_191288_.chanceOfSpreadRadius3;
            })).apply(p_191286_, PointedDripstoneConfig::new);
    });

    public final Boolean hasSurface;
    public final BlockStateProvider state;
    public final BlockStateProvider surfaceState;
    public final int height;
    public final float chanceOfTallerDripstone;
    public final float chanceOfDirectionalSpread;
    public final float chanceOfSpreadRadius2;
    public final float chanceOfSpreadRadius3;

    public PointedDripstoneConfig(Boolean hasSurface, BlockStateProvider state, BlockStateProvider surfaceState, int height, float chanceOfTallerDripstone, float chanceOfDirectionalSpread, float chanceOfSpreadRadius2, float chanceOfSpreadRadius3)
    {
        this.hasSurface = hasSurface;
        this.state = state;
        this.surfaceState = surfaceState;
        this.height = height;
        this.chanceOfTallerDripstone = chanceOfTallerDripstone;
        this.chanceOfDirectionalSpread = chanceOfDirectionalSpread;
        this.chanceOfSpreadRadius2 = chanceOfSpreadRadius2;
        this.chanceOfSpreadRadius3 = chanceOfSpreadRadius3;
    }
}