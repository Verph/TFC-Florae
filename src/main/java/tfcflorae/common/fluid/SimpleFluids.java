package tfcflorae.common.fluid;

import java.util.Locale;

public enum SimpleFluids
{
    ANTHRACITE(0xFF1C171A),
    BITUMINOUS_COAL(0xFF1D1E19),
    CHARCOAL(0xFF171914),
    LIGNITE(0xFF1D2016),
    COKE(0xFF2F3339);

    private final String id;
    private final int color;

    SimpleFluids(int color)
    {
        this.id = name().toLowerCase(Locale.ROOT);
        this.color = color;
    }

    public String getId()
    {
        return id;
    }

    public int getColor()
    {
        return color;
    }
}
