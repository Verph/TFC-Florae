package tfcflorae.common.blocks.wood;

import java.util.Locale;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.wood.*;
import net.dries007.tfc.common.blocks.wood.Wood.BlockType;
import net.dries007.tfc.common.items.*;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateRanges;
import net.dries007.tfc.util.registry.RegistryWood;
import net.dries007.tfc.world.feature.tree.TFCTreeGrower;

import org.jetbrains.annotations.Nullable;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.items.TFCFFood;
import tfcflorae.common.items.TFCFItems;

import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.*;

/**
 * Default wood types used for block registration calls.
 *
 * @see RegistryWood for addon support, to use {@link BlockType}.
 */
public enum TFCFWood implements RegistryWood
{
    // Add variants and fruit-bearing trees

    AFRICAN_PADAUK(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 29),
    ALDER(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    ANGELIM(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 29),
    ARGYLE_EUCALYPTUS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17, null, new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    BALD_CYPRESS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17),
    BAOBAB(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 32, TFCFItems.FOOD.get(TFCFFood.BAOBAB_FRUIT), new Lifecycle[] {FLOWERING, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY}),
    BEECH(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    BLACK_WALNUT(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    BLACK_WILLOW(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    BRAZILWOOD(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    BUTTERNUT(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    BUXUS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    COCOBOLO(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 29),
    COMMON_OAK(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    CYPRESS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    EBONY(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    FEVER(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    GINKGO(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    GREENHEART(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 29),
    HAWTHORN(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.HAWTHORN), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    HAZEL(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    HEMLOCK(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    HOLLY(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.HOLLY_BERRY), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}),
    HORNBEAM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    IROKO(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 29),
    IRONWOOD(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    JABUTICABEIRA(false, true, false, false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 23, TFCFItems.FOOD.get(TFCFFood.JABUTICABA), new Lifecycle[] {HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY, HEALTHY}),
    JOSHUA(false, true, false, true, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 8, TFCFItems.FOOD.get(TFCFFood.JOSHUA_FRUIT), new Lifecycle[] {HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    JUNIPER(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.JUNIPER), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}),
    KAURI(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 29),
    LARCH(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    LIMBA(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    LOCUST(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    LOGWOOD(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    MACLURA(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.OSAGE_ORANGE), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT}),
    MAHOE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, null, new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, HEALTHY, HEALTHY, HEALTHY, DORMANT, DORMANT, DORMANT}),
    MAHOGANY(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 29, TFCFItems.FOOD.get(TFCFFood.SKY_FRUIT), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    MARBLEWOOD(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 29),
    MEDLAR(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.MEDLAR), new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}),
    MESSMATE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    MOUNTAIN_ASH(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17),
    MULBERRY(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.MULBERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    NORDMANN_FIR(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    NORWAY_SPRUCE(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    PEAR(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.PEAR), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT}),
    PERSIMMON(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.PERSIMMON), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, HEALTHY, HEALTHY, DORMANT, DORMANT, DORMANT}),
    PINK_CHERRY_BLOSSOM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCItems.FOOD.get(Food.CHERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT}),
    PINK_IPE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, DORMANT, DORMANT, DORMANT}),
    PINK_IVORY(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.PINK_IVORY_DRUPE), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    POPLAR(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    PURPLEHEART(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 29),
    PURPLE_IPE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, DORMANT, DORMANT, DORMANT}),
    PURPLE_JACARANDA(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, null, new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, HEALTHY, HEALTHY, FLOWERING, HEALTHY, DORMANT, DORMANT}),
    QUINCE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.QUINCE), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT}),
    RAINBOW_EUCALYPTUS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17, null, new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    REDWOOD(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17),
    RED_CEDAR(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.JUNIPER), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}),
    RED_CYPRESS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17),
    RED_ELM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    RED_MANGROVE(false, false, true, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, HEALTHY, HEALTHY, DORMANT, DORMANT, DORMANT}),
    ROWAN(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.ROWAN_BERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT}),
    RUBBER_FIG(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    SLOE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.SLOE), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT}),
    SNOW_GUM_EUCALYPTUS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17, null, new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    SORB(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.SORB_APPLE), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT}),
    SWEETGUM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    SYZYGIUM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.OTAHEITE_APPLE), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    TEAK(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    WALNUT(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    WENGE(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 29),
    WHITEBEAM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.ROWAN_BERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    WHITE_CHERRY_BLOSSOM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCItems.FOOD.get(Food.CHERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT}),
    WHITE_ELM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19),
    WHITE_IPE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, DORMANT, DORMANT, DORMANT}),
    WHITE_JACARANDA(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, null, new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, HEALTHY, HEALTHY, FLOWERING, HEALTHY, DORMANT, DORMANT}),
    WHITE_MANGROVE(false, false, true, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, HEALTHY, HEALTHY, DORMANT, DORMANT, DORMANT}),
    YELLOW_IPE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, DORMANT, DORMANT, DORMANT}),
    YELLOW_JACARANDA(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, null, new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, HEALTHY, HEALTHY, FLOWERING, HEALTHY, DORMANT, DORMANT}),
    YELLOW_MERANTI(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 29),
    YEW(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 19, TFCFItems.FOOD.get(TFCFFood.YEW_BERRY), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}),
    ZEBRAWOOD(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 29),

    // Add fruit leaves to TFC trees
    WHITE_CEDAR(true, true, false, false, false, MaterialColor.TERRACOTTA_WHITE, MaterialColor.TERRACOTTA_LIGHT_GRAY, 7, 7, TFCFItems.FOOD.get(TFCFFood.JUNIPER), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT});

    public static final TFCFWood[] VALUES = values();

    public final String serializedName;
    public boolean hasLeavesOnly;
    public final boolean conifer;
    public final boolean mangrove;
    public final boolean joshua;
    public final boolean fruitingLog;
    public final MaterialColor woodColor;
    public final MaterialColor barkColor;
    public final TFCTreeGrower tree;
    public final int maxDecayDistance;
    public final int daysToGrow;
    public Boolean isFruitTree;

    public Supplier<? extends Item> productItem;
    public Lifecycle[] stages;

    TFCFWood(boolean hasLeavesOnly, boolean conifer, boolean mangrove, boolean joshua, boolean fruitingLog, MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow)
    {
        this(conifer, mangrove, joshua, fruitingLog, woodColor, barkColor, maxDecayDistance, daysToGrow);
        this.hasLeavesOnly = hasLeavesOnly;
    }

    TFCFWood(boolean hasLeavesOnly, boolean conifer, boolean mangrove, boolean joshua, boolean fruitingLog, MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow, Supplier<? extends Item> productItem, Lifecycle[] stages)
    {
        this(conifer, mangrove, joshua, fruitingLog, woodColor, barkColor, maxDecayDistance, daysToGrow, productItem, stages);
        this.hasLeavesOnly = hasLeavesOnly;
    }

    TFCFWood(boolean conifer, boolean mangrove, boolean joshua, boolean fruitingLog, MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow, Supplier<? extends Item> productItem, Lifecycle[] stages)
    {
        this(conifer, mangrove, joshua, fruitingLog, woodColor, barkColor, maxDecayDistance, daysToGrow);
        this.productItem = productItem;
        this.stages = stages;
        this.isFruitTree = true;
    }

    TFCFWood(boolean conifer, boolean mangrove, boolean joshua, boolean fruitingLog, MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.conifer = conifer;
        this.mangrove = mangrove;
        this.joshua = joshua;
        this.fruitingLog = fruitingLog;
        this.woodColor = woodColor;
        this.barkColor = barkColor;
        this.tree = new TFCTreeGrower(Helpers.identifier("tree/" + serializedName), Helpers.identifier("tree/" + serializedName + "_large"));
        this.maxDecayDistance = maxDecayDistance;
        this.daysToGrow = daysToGrow;
        this.isFruitTree = false;
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }

    public boolean hasLeavesOnly()
    {
        return hasLeavesOnly;
    }

    public boolean isConifer()
    {
        return conifer;
    }

    public boolean isMangrove()
    {
        return mangrove;
    }

    public boolean isJoshua()
    {
        return joshua;
    }

    public boolean hasFruitingLog()
    {
        return fruitingLog;
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

    public Boolean isFruitTree()
    {
        return isFruitTree;
    }

    @Nullable
    public Supplier<? extends Item> getProductItem()
    {
        return productItem;
    }

    public Lifecycle[] getStages()
    {
        return stages;
    }

    @Nullable
    public static Function<Block, BlockItem> createBlockItem(Item.Properties properties)
    {
        BiFunction<Block, Item.Properties, ? extends BlockItem> blockItem = BlockItem::new;
        return block -> blockItem.apply(block, properties);
    }

    @Override
    public Supplier<Block> getBlock(Wood.BlockType type)
    {
        return TFCFBlocks.WOODS.get(this).get(type);
    }
}
