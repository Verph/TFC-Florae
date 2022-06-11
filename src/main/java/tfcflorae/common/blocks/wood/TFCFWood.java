package tfcflorae.common.blocks.wood;

import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blockentities.*;
import net.dries007.tfc.common.blocks.*;
import net.dries007.tfc.common.blocks.devices.*;
import net.dries007.tfc.common.blocks.wood.*;
import net.dries007.tfc.common.blocks.wood.Wood.BlockType;
import net.dries007.tfc.common.items.*;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryWood;
import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

import org.jetbrains.annotations.Nullable;

import tfcflorae.common.blocks.TFCFBlocks;

/**
 * Default wood types used for block registration calls.
 *
 * @see RegistryWood for addon support, to use {@link BlockType}.
 */
public enum TFCFWood implements RegistryWood
{
    AFRICAN_PADAUK(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    ALDER(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    ANGELIM(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    //BALD_CYPRESS(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17),
    BAOBAB(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 32),
    BEECH(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    BLACK_WALNUT(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    BUXUS(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    BRAZILWOOD(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    BUTTERNUT(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    CHERRY_BLOSSOM(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    COCOBOLO(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    CYPRESS(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    EBONY(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    EUCALYPTUS(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17),
    COMMON_OAK(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    FEVER(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    GINKGO(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    GREENHEART(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    HAWTHORN(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    HAZEL(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    HEMLOCK(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    HOLLY(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    HORNBEAM(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    IPE(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    IROKO(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    IRONWOOD(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    JACARANDA(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    JOSHUA_TREE(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    JUNIPER(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    KAURI(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    LARCH(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    LIMBA(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    LOCUST(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    LOGWOOD(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    MACLURA(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    MAHOE(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    MAHOGANY(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    //MANGROVE(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17),
    MARBLEWOOD(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    MESSMATE(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    MOUNTAIN_ASH(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17),
    MULBERRY(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    NORDMANN_FIR(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    NORWAY_SPRUCE(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    //PINK_CHERRY(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    PINK_IVORY(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    POPLAR(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    PURPLEHEART(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    RED_CEDAR(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    RED_ELM(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    REDWOOD(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17),
    ROWAN(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    RUBBER_FIG(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    SWEETGUM(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    SYZYGIUM(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    TEAK(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    WALNUT(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    WENGE(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    //WHITE_CHERRY(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    WHITE_ELM(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    WHITEBEAM(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    YELLOW_MERANTI(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    YEW(false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    ZEBRAWOOD(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26);

    public static final TFCFWood[] VALUES = values();

    private final String serializedName;
    private final boolean conifer;
    private final MaterialColor woodColor;
    private final MaterialColor barkColor;
    private final TFCTreeGrower tree;
    private final int maxDecayDistance;
    private final int daysToGrow;

    TFCFWood(boolean conifer, MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.conifer = conifer;
        this.woodColor = woodColor;
        this.barkColor = barkColor;
        this.tree = new TFCTreeGrower(Helpers.identifier("tree/" + serializedName), Helpers.identifier("tree/" + serializedName + "_large"));
        this.maxDecayDistance = maxDecayDistance;
        this.daysToGrow = daysToGrow;
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }

    public boolean isConifer()
    {
        return conifer;
    }

    @Override
    public MaterialColor woodColor()
    {
        return woodColor;
    }

    @Override
    public MaterialColor barkColor()
    {
        return barkColor;
    }

    @Override
    public TFCTreeGrower tree()
    {
        return tree;
    }

    @Override
    public int maxDecayDistance()
    {
        return maxDecayDistance;
    }

    @Override
    public int daysToGrow()
    {
        return daysToGrow;
    }

    public Supplier<Block> getBlock(BlockType type)
    {
        return TFCFBlocks.WOODS.get(this).get(type);
    }
}
