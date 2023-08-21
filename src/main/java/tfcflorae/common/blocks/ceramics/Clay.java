package tfcflorae.common.blocks.ceramics;

import java.awt.Color;
import java.util.Locale;

import net.minecraft.world.level.material.MaterialColor;

import tfcflorae.util.registry.RegistryClay;

public enum Clay implements RegistryClay
{
    EARTHENWARE(new Color(143, 70, 47).getRGB(), MaterialColor.TERRACOTTA_RED),
    KAOLINITE(new Color(168, 155, 139).getRGB(), MaterialColor.TERRACOTTA_LIGHT_GRAY),
    STONEWARE(new Color(103, 99, 98).getRGB(), MaterialColor.TERRACOTTA_GRAY);

    public static final Clay[] VALUES = values();

    public static Clay valueOf(int i)
    {
        return i >= 0 && i < VALUES.length ? VALUES[i] : EARTHENWARE;
    }

    public final String serializedName;
    public final int dustColor;
    public final MaterialColor materialColor;

    Clay(int dustColor, MaterialColor materialColor)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.dustColor = dustColor;
        this.materialColor = materialColor;
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }

    public MaterialColor getMaterialColor()
    {
        return materialColor;
    }
}
