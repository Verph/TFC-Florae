package tfcflorae.common.blocks.soil;

import java.awt.*;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.dries007.tfc.common.blocks.soil.TFCSandBlock;

public enum Colors
{
    BLACK(new Color(56, 56, 56).getRGB(), MaterialColor.TERRACOTTA_BLACK, true),
    BLUE(new Color(50, 68, 255).getRGB(), MaterialColor.TERRACOTTA_BLUE, false),
    BROWN(new Color(112, 113, 89).getRGB(), MaterialColor.TERRACOTTA_BROWN, true),
    CYAN(new Color(73, 198, 198).getRGB(), MaterialColor.TERRACOTTA_CYAN, false),
    GRAY(new Color(112, 112, 112).getRGB(), MaterialColor.TERRACOTTA_GRAY, false),
    GREEN(new Color(106, 116, 81).getRGB(), MaterialColor.TERRACOTTA_GREEN, true),
    LIGHT_BLUE(new Color(85, 162, 214).getRGB(), MaterialColor.TERRACOTTA_LIGHT_BLUE, false),
    LIGHT_GRAY(new Color(175, 175, 175).getRGB(), MaterialColor.TERRACOTTA_LIGHT_GRAY, false),
    LIGHT_GREEN(new Color(136, 132, 61).getRGB(), MaterialColor.TERRACOTTA_LIGHT_GREEN, false),
    MAGENTA(new Color(165, 0, 165).getRGB(), MaterialColor.TERRACOTTA_MAGENTA, false),
    ORANGE(new Color(190, 109, 56).getRGB(), MaterialColor.TERRACOTTA_ORANGE, false),
    PINK(new Color(150, 101, 97).getRGB(), MaterialColor.TERRACOTTA_PINK, true),
    PURPLE(new Color(116, 73, 94).getRGB(), MaterialColor.TERRACOTTA_PURPLE, false),
    RED(new Color(125, 99, 84).getRGB(), MaterialColor.TERRACOTTA_RED, true),
    WHITE(new Color(202, 202, 201).getRGB(), MaterialColor.TERRACOTTA_WHITE, true),
    YELLOW(new Color(215, 196, 140).getRGB(), MaterialColor.TERRACOTTA_YELLOW, true);

    private static final Colors[] VALUES = values();

    public static Colors valueOf(int i)
    {
        return i >= 0 && i < VALUES.length ? VALUES[i] : BLACK;
    }

    private final int dustColor;
    private final MaterialColor materialColor;
    private final boolean hasSand;

    Colors(int dustColor, MaterialColor materialColor, boolean hasSand)
    {
        this.dustColor = dustColor;
        this.materialColor = materialColor;
        this.hasSand = hasSand;
    }

    public MaterialColor getMaterialColor()
    {
        return materialColor;
    }

    public Block create()
    {
        return new TFCSandBlock(dustColor, BlockBehaviour.Properties.of(Material.SAND, materialColor).strength(1F).sound(SoundType.ANCIENT_DEBRIS));
    }

    public boolean hasSand()
    {
        return hasSand;
    }
}