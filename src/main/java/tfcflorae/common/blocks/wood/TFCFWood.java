package tfcflorae.common.blocks.wood;

import java.awt.Color;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;

import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.wood.*;
import net.dries007.tfc.common.blocks.wood.Wood.BlockType;
import net.dries007.tfc.common.items.*;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryWood;
import tfcflorae.Config;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.items.TFCFFood;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.world.feature.tree.TFCFTreeGrower;

import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.*;

/**
 * Default wood types used for block registration calls.
 *
 * @see RegistryWood for addon support, to use {@link BlockType}.
 */
public enum TFCFWood implements RegistryWood
{
    // Add variants and fruit-bearing trees

    AFRICAN_PADAUK(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 29),
    ALDER(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    ANGELIM(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 29),
    ARGYLE_EUCALYPTUS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 17, null, new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}, new Color(255, 255, 239).getRGB()),
    BALD_CYPRESS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 17),
    BAMBOO(false, false, false, false, false, MaterialColor.COLOR_LIGHT_GREEN, MaterialColor.COLOR_LIGHT_GREEN, 10, 9),
    BAOBAB(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 32, TFCFItems.FOOD.get(TFCFFood.BAOBAB_FRUIT), new Lifecycle[] {FLOWERING, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY}, new Color(255, 255, 247).getRGB()),
    BEECH(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    BLACK_WALNUT(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    BLACK_WILLOW(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    BRAZILWOOD(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    BUTTERNUT(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    BUXUS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    COCOBOLO(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 29),
    COMMON_OAK(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    CYPRESS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    EBONY(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    FEVER(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    GHAF(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    GINKGO(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    GREENHEART(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 29),
    HAWTHORN(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.HAWTHORN), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}, new Color(245, 232, 241).getRGB()),
    HAZEL(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    HEMLOCK(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    HOLLY(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.HOLLY_BERRY), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}, new Color(232, 211, 229).getRGB()),
    HORNBEAM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    IROKO(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 29),
    IRONWOOD(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    JABUTICABEIRA(false, true, false, false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 23, TFCFItems.FOOD.get(TFCFFood.JABUTICABA), new Lifecycle[] {HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY, HEALTHY}, new Color(255, 213, 213).getRGB()),
    JOSHUA(false, true, false, true, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 8, TFCFItems.FOOD.get(TFCFFood.JOSHUA_FRUIT), new Lifecycle[] {HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}, new Color(237, 249, 238).getRGB()),
    JUNIPER(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.JUNIPER), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}, new Color(255, 255, 249).getRGB()),
    KAURI(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 29),
    LARCH(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    LAUREL(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    LIMBA(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    LOCUST(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    LOGWOOD(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    MACLURA(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.OSAGE_ORANGE), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT}, new Color(192, 209, 121).getRGB()),
    MAHOE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, null, new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, HEALTHY, HEALTHY, HEALTHY, DORMANT, DORMANT, DORMANT}, new Color(241, 190, 61).getRGB()),
    MAHOGANY(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 29, TFCFItems.FOOD.get(TFCFFood.SKY_FRUIT), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}, new Color(255, 163, 208).getRGB()),
    MARBLEWOOD(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 29),
    MEDLAR(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.MEDLAR), new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}, new Color(214, 232, 232).getRGB()),
    MESSMATE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    MOUNTAIN_ASH(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 17),
    MULBERRY(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.MULBERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}, new Color(255, 162, 221).getRGB()),
    NORDMANN_FIR(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    NORWAY_SPRUCE(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    PEAR(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.PEAR), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT}, new Color(255, 255, 255).getRGB()),
    PERSIMMON(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.PERSIMMON), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, HEALTHY, HEALTHY, DORMANT, DORMANT, DORMANT}, new Color(238, 232, 174).getRGB()),
    PINK_CHERRY_BLOSSOM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCItems.FOOD.get(Food.CHERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT}, new Color(252, 203, 231).getRGB()),
    PINK_IPE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, DORMANT, DORMANT, DORMANT}, new Color(241, 160, 213).getRGB()),
    PINK_IVORY(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.PINK_IVORY_DRUPE), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}, new Color(255, 255, 237).getRGB()),
    POPLAR(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    PURPLEHEART(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 29),
    PURPLE_IPE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, DORMANT, DORMANT, DORMANT}, new Color(135, 88, 216).getRGB()),
    PURPLE_JACARANDA(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, null, new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, HEALTHY, HEALTHY, FLOWERING, HEALTHY, DORMANT, DORMANT}, new Color(200, 146, 218).getRGB()),
    QUINCE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.QUINCE), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT}, new Color(249, 82, 38).getRGB()),
    RAINBOW_EUCALYPTUS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 17, null, new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}, new Color(255, 163, 213).getRGB()),
    REDWOOD(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 17),
    RED_CEDAR(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.JUNIPER), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}, new Color(255, 255, 237).getRGB()),
    RED_CYPRESS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 17),
    RED_ELM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    RED_MANGROVE(false, false, true, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 17, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, HEALTHY, HEALTHY, DORMANT, DORMANT, DORMANT}, new Color(235, 114, 179).getRGB()),
    ROWAN(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.ROWAN_BERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT}, new Color(245, 232, 241).getRGB()),
    RUBBER_FIG(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    SLOE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.SLOE), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT}, new Color(228, 239, 243).getRGB()),
    SNOW_GUM_EUCALYPTUS(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 17, null, new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}, new Color(255, 255, 237).getRGB()),
    SORB(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.SORB_APPLE), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT}, new Color(255, 238, 158).getRGB()),
    SWEETGUM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    SYZYGIUM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.OTAHEITE_APPLE), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}, new Color(235, 114, 179).getRGB()),
    TEAK(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    WALNUT(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    WENGE(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 29),
    WHITEBEAM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.ROWAN_BERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}, new Color(255, 213, 213).getRGB()),
    WHITE_CHERRY_BLOSSOM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCItems.FOOD.get(Food.CHERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT}, new Color(237, 237, 239).getRGB()),
    WHITE_ELM(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19),
    WHITE_IPE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, DORMANT, DORMANT, DORMANT}, new Color(236, 214, 222).getRGB()),
    WHITE_JACARANDA(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, null, new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, HEALTHY, HEALTHY, FLOWERING, HEALTHY, DORMANT, DORMANT}, new Color(241, 235, 238).getRGB()),
    WHITE_MANGROVE(false, false, true, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 17, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, HEALTHY, HEALTHY, DORMANT, DORMANT, DORMANT}, new Color(241, 251, 251).getRGB()),
    YELLOW_IPE(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, DORMANT, DORMANT, DORMANT}, new Color(250, 222, 63).getRGB()),
    YELLOW_JACARANDA(false, false, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, null, new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, HEALTHY, HEALTHY, FLOWERING, HEALTHY, DORMANT, DORMANT}, new Color(241, 167, 0).getRGB()),
    YELLOW_MERANTI(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 29),
    YEW(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 19, TFCFItems.FOOD.get(TFCFFood.YEW_BERRY), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}, new Color(244, 205, 205).getRGB()),
    ZEBRAWOOD(false, true, false, false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 10, 29),

    // Add fruit leaves to TFC trees
    WHITE_CEDAR(true, true, false, false, false, MaterialColor.TERRACOTTA_WHITE, MaterialColor.TERRACOTTA_LIGHT_GRAY, 10, 7, TFCFItems.FOOD.get(TFCFFood.JUNIPER), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}, new Color(255, 255, 249).getRGB());

    public static final TFCFWood[] VALUES = values();

    public final String serializedName;
    public boolean hasLeavesOnly;
    public final boolean conifer;
    public final boolean mangrove;
    public final boolean joshua;
    public final boolean fruitingLog;
    public final MaterialColor woodColor;
    public final MaterialColor barkColor;
    public final TFCFTreeGrower tree;
    public final int maxDecayDistance;
    public final int daysToGrow;
    public int floweringLeavesColor;
    public Boolean isFruitTree;

    public Supplier<? extends Item> productItem;
    public Lifecycle[] stages;

    TFCFWood(boolean hasLeavesOnly, boolean conifer, boolean mangrove, boolean joshua, boolean fruitingLog, MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow)
    {
        this(conifer, mangrove, joshua, fruitingLog, woodColor, barkColor, maxDecayDistance, daysToGrow);
        this.hasLeavesOnly = hasLeavesOnly;
        this.floweringLeavesColor = -1;
    }

    TFCFWood(boolean hasLeavesOnly, boolean conifer, boolean mangrove, boolean joshua, boolean fruitingLog, MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow, Supplier<? extends Item> productItem, Lifecycle[] stages, int floweringLeavesColor)
    {
        this(conifer, mangrove, joshua, fruitingLog, woodColor, barkColor, maxDecayDistance, daysToGrow, productItem, stages, floweringLeavesColor);
        this.hasLeavesOnly = hasLeavesOnly;
        this.floweringLeavesColor = floweringLeavesColor;
    }

    TFCFWood(boolean conifer, boolean mangrove, boolean joshua, boolean fruitingLog, MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow, Supplier<? extends Item> productItem, Lifecycle[] stages, int floweringLeavesColor)
    {
        this(conifer, mangrove, joshua, fruitingLog, woodColor, barkColor, maxDecayDistance, daysToGrow);
        this.productItem = productItem;
        this.stages = stages;
        this.isFruitTree = true;
        this.floweringLeavesColor = floweringLeavesColor;
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
        this.tree = new TFCFTreeGrower(this, Helpers.identifier("tree/" + serializedName), Helpers.identifier("tree/" + serializedName + "_large"));
        this.maxDecayDistance = maxDecayDistance;
        this.daysToGrow = daysToGrow;
        this.isFruitTree = false;
        this.floweringLeavesColor = -1;
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
    public TFCFTreeGrower tree()
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
        return Config.COMMON.saplingGrowthDays.get(this).get();
    }

    public int defaultDaysToGrow()
    {
        return daysToGrow;
    }

    public Boolean isFruitTree()
    {
        return isFruitTree;
    }

    public int getFloweringLeavesColor()
    {
        return floweringLeavesColor;
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
