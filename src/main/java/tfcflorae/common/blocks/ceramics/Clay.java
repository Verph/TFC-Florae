package tfcflorae.common.blocks.ceramics;

import java.util.Locale;

import tfcflorae.util.registry.RegistryClay;

public enum Clay implements RegistryClay
{
    EARTHENWARE,
    KAOLINITE,
    STONEWARE;

    public static final Clay[] VALUES = values();

    private final String serializedName;

    Clay()
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }
}
