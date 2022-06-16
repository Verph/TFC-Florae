package tfcflorae.common.blocks.rock;

import java.awt.*;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.dries007.tfc.common.blocks.soil.TFCSandBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public enum TFCFSand
{
    BLUE(new Color(150, 101, 97).getRGB(), MaterialColor.TERRACOTTA_BLUE);

    private static final TFCFSand[] VALUES = values();

    public static TFCFSand valueOf(int i)
    {
        return i >= 0 && i < VALUES.length ? VALUES[i] : BLUE;
    }

    private final int dustColor;
    private final MaterialColor materialColor;

    TFCFSand(int dustColor, MaterialColor materialColor)
    {
        this.dustColor = dustColor;
        this.materialColor = materialColor;
    }

    public MaterialColor getMaterialColor()
    {
        return materialColor;
    }

    public Block create()
    {
        return new TFCSandBlock(dustColor, BlockBehaviour.Properties.of(Material.SAND, materialColor).strength(0.5F).sound(SoundType.SAND));
    }
}
