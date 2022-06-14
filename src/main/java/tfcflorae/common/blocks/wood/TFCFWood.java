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

    AFRICAN_PADAUK(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    ALDER(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    ANGELIM(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    BALD_CYPRESS(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17),
    BAOBAB(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 32, TFCFItems.FOOD.get(TFCFFood.BAOBAB_FRUIT), new Lifecycle[] {FLOWERING, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY}),
    BEECH(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    BLACK_WALNUT(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    BUXUS(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    BRAZILWOOD(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    BUTTERNUT(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    PINK_CHERRY_BLOSSOM(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, TFCItems.FOOD.get(Food.CHERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}), // Fix food
    WHITE_CHERRY_BLOSSOM(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, TFCItems.FOOD.get(Food.CHERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}), // Fix food
    COCOBOLO(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    CYPRESS(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    EBONY(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    ARGYLE_EUCALYPTUS(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17, null, new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, HEALTHY, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    RAINBOW_EUCALYPTUS(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17, null, new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, HEALTHY, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    SNOW_GUM_EUCALYPTUS(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17, null, new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, HEALTHY, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    COMMON_OAK(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    FEVER(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    GINKGO(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    GREENHEART(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    HAWTHORN(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, TFCFItems.FOOD.get(TFCFFood.HAWTHORN), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    HAZEL(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    HEMLOCK(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    HOLLY(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, TFCFItems.FOOD.get(TFCFFood.HOLLY_BERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}),
    HORNBEAM(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    PINK_IPE(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, DORMANT, DORMANT, DORMANT}),
    PURPLE_IPE(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, DORMANT, DORMANT, DORMANT}),
    WHITE_IPE(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, DORMANT, DORMANT, DORMANT}),
    YELLOW_IPE(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, null, new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, HEALTHY, DORMANT, DORMANT, DORMANT}),
    IROKO(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    IRONWOOD(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    PURPLE_JACARANDA(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, null, new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, HEALTHY, HEALTHY, FLOWERING, HEALTHY, DORMANT, DORMANT}),
    WHITE_JACARANDA(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, null, new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, HEALTHY, HEALTHY, FLOWERING, HEALTHY, DORMANT, DORMANT}),
    YELLOW_JACARANDA(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, null, new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, HEALTHY, HEALTHY, FLOWERING, HEALTHY, DORMANT, DORMANT}),
    JOSHUA_TREE(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    JUNIPER(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, TFCFItems.FOOD.get(TFCFFood.JUNIPER), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}),
    KAURI(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    LARCH(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    LIMBA(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    LOCUST(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    LOGWOOD(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    MACLURA(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, TFCFItems.FOOD.get(TFCFFood.OSAGE_ORANGE), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}),
    MAHOE(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, null, new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, HEALTHY, HEALTHY, HEALTHY, DORMANT, DORMANT, DORMANT}),
    MAHOGANY(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26, TFCFItems.FOOD.get(TFCFFood.SKY_FRUIT), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY, HEALTHY}),
    MANGROVE(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17),
    MARBLEWOOD(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    MESSMATE(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    MOUNTAIN_ASH(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17),
    MULBERRY(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, TFCFItems.FOOD.get(TFCFFood.MULBERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    NORDMANN_FIR(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    NORWAY_SPRUCE(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    PINK_IVORY(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, TFCFItems.FOOD.get(TFCFFood.PINK_IVORY_DRUPE), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    POPLAR(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    PURPLEHEART(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    RED_CEDAR(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, TFCFItems.FOOD.get(TFCFFood.JUNIPER), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}),
    RED_ELM(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    REDWOOD(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 17),
    ROWAN(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, TFCFItems.FOOD.get(TFCFFood.ROWAN_BERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    RUBBER_FIG(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    SWEETGUM(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    SYZYGIUM(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, TFCFItems.FOOD.get(TFCFFood.RIBERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    TEAK(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    WALNUT(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    WENGE(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    WHITE_ELM(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11),
    WHITEBEAM(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, TFCFItems.FOOD.get(TFCFFood.ROWAN_BERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}),
    YELLOW_MERANTI(false, true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),
    YEW(false, false, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 11, TFCFItems.FOOD.get(TFCFFood.YEW_BERRY), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}),
    ZEBRAWOOD(true, MaterialColor.COLOR_GREEN, MaterialColor.COLOR_GREEN, 7, 26),

    // Add fruit leaves to TFC trees
    WHITE_CEDAR(true, true, MaterialColor.TERRACOTTA_WHITE, MaterialColor.TERRACOTTA_LIGHT_GRAY, 7, 7, TFCFItems.FOOD.get(TFCFFood.JUNIPER), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY});

    public static final TFCFWood[] VALUES = values();

    public final String serializedName;
    public boolean hasLeavesOnly;
    public final boolean conifer;
    public final MaterialColor woodColor;
    public final MaterialColor barkColor;
    public final TFCTreeGrower tree;
    public final int maxDecayDistance;
    public final int daysToGrow;
    public Boolean isFruitTree;

    public Supplier<? extends Item> productItem;
    public Lifecycle[] stages;

    TFCFWood(boolean hasLeavesOnly, boolean conifer, MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow)
    {
        this(conifer, woodColor, barkColor, maxDecayDistance, daysToGrow);
        this.hasLeavesOnly = hasLeavesOnly;
    }

    TFCFWood(boolean hasLeavesOnly, boolean conifer, MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow, Supplier<? extends Item> productItem, Lifecycle[] stages)
    {
        this(conifer, woodColor, barkColor, maxDecayDistance, daysToGrow, productItem, stages);
        this.hasLeavesOnly = hasLeavesOnly;
    }

    TFCFWood(boolean conifer, MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow, Supplier<? extends Item> productItem, Lifecycle[] stages)
    {
        this(conifer, woodColor, barkColor, maxDecayDistance, daysToGrow);
        this.productItem = productItem;
        this.stages = stages;
        this.isFruitTree = true;
    }

    TFCFWood(boolean conifer, MaterialColor woodColor, MaterialColor barkColor, int maxDecayDistance, int daysToGrow)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.conifer = conifer;
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
