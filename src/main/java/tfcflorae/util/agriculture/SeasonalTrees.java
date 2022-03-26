package tfcflorae.util.agriculture;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.types.DefaultTrees;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.world.classic.worldgen.WorldGenFruitTrees;

import tfcflorae.objects.blocks.wood.BlockLeavesTFCF.EnumLeafState;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.types.TreesTFCF;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public enum SeasonalTrees
{
    // Fruiting and/or flowering trees
    BAOBAB(() -> ItemsTFCF.BAOBAB_FRUIT, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, 23f, 40f, 10f, 150f, 0.33f, false, false, false, TreesTFCF.BAOBAB_TREE),
    MACLURA(() -> ItemsTFCF.OSAGE_ORANGE, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -1f, 17f, 140f, 400f, 0.33f, false, false, false, TreesTFCF.MACLURA_TREE),
    MAHOGANY(() -> ItemsTFCF.SKY_FRUIT, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, 23f, 42f, 220f, 500f, 0.33f, false, false, false, TreesTFCF.MAHOGANY_TREE),
    PINK_IVORY(() -> ItemsTFCF.PINK_IVORY_DRUPE, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, 18f, 31f, 210f, 500f, 0.33f, false, false, false, TreesTFCF.PINK_IVORY_TREE),
    SYZYGIUM(() -> ItemsTFCF.RIBERRY, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, 13f, 35f, 140f, 360f, 0.33f, false, false, false, TreesTFCF.SYZYGIUM_TREE),
    YEW(() -> ItemsTFCF.YEW_BERRY, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -15f, 11f, 180f, 350f, 0.33f, true, false, false, TreesTFCF.YEW_TREE),
    JOSHUA_TREE(() -> ItemsTFCF.BARREL_CACTUS_FRUIT, new int[] {1, 2, 2, 2, 3, 3, 3, 1, 1, 1, 1, 1}, 13f, 35f, 140f, 360f, 0.33f, false, false, true, TreesTFCF.JOSHUA_TREE_TREE),
    PURPLE_JACARANDA(null, new int[] {0, 0, 1, 1, 2, 1, 1, 1, 2, 4, 4, 0}, 10f, 34f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.JACARANDA_TREE),
    YELLOW_JACARANDA(null, new int[] {0, 0, 1, 1, 2, 1, 1, 1, 2, 4, 4, 0}, 10f, 34f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.JACARANDA_TREE),
    JUNIPER(() -> ItemsTFCF.JUNIPER, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -8f, 20f, 80f, 350f, 0.33f, false, false, false, TreesTFCF.JUNIPER_TREE),
    RED_CEDAR(() -> ItemsTFCF.JUNIPER, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -8f, 17f, 10f, 240f, 0.33f, false, false, false, TreesTFCF.RED_CEDAR_TREE),
    WHITE_CEDAR(() -> ItemsTFCF.JUNIPER, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -8f, 17f, 10f, 240f, 0.33f, false, false, false, TreesTFCF.WHITE_CEDAR_TREE),
    PINK_IPE(null, new int[] {0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 4, 4}, 15f, 32f, 150f, 350f, 0.33f, true, false, false, TreesTFCF.IPE_TREE),
    WHITE_IPE(null, new int[] {0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 4, 4}, 15f, 32f, 150f, 350f, 0.33f, true, false, false, TreesTFCF.IPE_TREE),
    YELLOW_IPE(null, new int[] {0, 0, 0, 1, 1, 2, 2, 2, 1, 1, 4, 4}, 15f, 32f, 150f, 350f, 0.33f, true, false, false, TreesTFCF.IPE_TREE),
    ARGYLE_EUCALYPTUS(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 18f, 39f, 120f, 300f, 0.33f, true, true, false, TreesTFCF.EUCALYPTUS_TREE, true),
    RAINBOW_EUCALYPTUS(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 18f, 39f, 120f, 300f, 0.33f, true, true, false, TreesTFCF.EUCALYPTUS_TREE, true),
    SNOW_GUM_EUCALYPTUS(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 18f, 39f, 120f, 300f, 0.33f, true, true, false, TreesTFCF.EUCALYPTUS_TREE, true),

    // Seasonal trees
    GINKGO(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 6f, 20f, 240f, 550f, 0.33f, true, false, false, TreesTFCF.GINKGO_TREE),
    LARCH(() -> ItemsTFCF.PINECONE, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -12f, 15f, 60f, 400f, 0.33f, true, false, false, TreesTFCF.LARCH_TREE),
    LOCUST(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -6f, 15f, 120f, 290f, 0.33f, true, false, false, TreesTFCF.LOCUST_TREE),
    YELLOW_HAWTHORN(() -> ItemsTFCF.HAWTHORN, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -8f, 14f, 180f, 400f, 0.33f, true, false, false, TreesTFCF.HAWTHORN_TREE),
    ORANGE_HAWTHORN(() -> ItemsTFCF.HAWTHORN, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -8f, 14f, 180f, 400f, 0.33f, true, false, false, TreesTFCF.HAWTHORN_TREE),
    RED_HAWTHORN(() -> ItemsTFCF.HAWTHORN, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -8f, 14f, 180f, 400f, 0.33f, true, false, false, TreesTFCF.HAWTHORN_TREE),
    YELLOW_MULBERRY(() -> ItemsTFCF.HAWTHORN, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -30f, 28f, 140f, 420f, 0.33f, true, false, false, TreesTFCF.MULBERRY_TREE),
    ORANGE_MULBERRY(() -> ItemsTFCF.HAWTHORN, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -30f, 28f, 140f, 420f, 0.33f, true, false, false, TreesTFCF.MULBERRY_TREE),
    RED_MULBERRY(() -> ItemsTFCF.HAWTHORN, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -30f, 28f, 140f, 420f, 0.33f, true, false, false, TreesTFCF.MULBERRY_TREE),
    YELLOW_ROWAN(() -> ItemsTFCF.ROWAN_BERRY, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -15f, 8f, 180f, 400f, 0.33f, true, false, false, TreesTFCF.ROWAN_TREE),
    ORANGE_ROWAN(() -> ItemsTFCF.ROWAN_BERRY, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -15f, 8f, 180f, 400f, 0.33f, true, false, false, TreesTFCF.ROWAN_TREE),
    RED_ROWAN(() -> ItemsTFCF.ROWAN_BERRY, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -15f, 8f, 180f, 400f, 0.33f, true, false, false, TreesTFCF.ROWAN_TREE),
    YELLOW_PINK_CHERRY(() -> ItemFoodTFC.get(Food.CHERRY), new int[] {0, 0, 0, 2, 2, 1, 1, 3, 4, 4, 0, 0}, 0f, 20f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.PINK_CHERRY_TREE),
    ORANGE_PINK_CHERRY(() -> ItemFoodTFC.get(Food.CHERRY), new int[] {0, 0, 0, 2, 2, 1, 1, 3, 4, 4, 0, 0}, 0f, 20f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.PINK_CHERRY_TREE),
    RED_PINK_CHERRY(() -> ItemFoodTFC.get(Food.CHERRY), new int[] {0, 0, 0, 0, 2, 1, 1, 3, 4, 4, 0, 0}, 0f, 20f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.PINK_CHERRY_TREE),
    YELLOW_WHITE_CHERRY(() -> ItemFoodTFC.get(Food.CHERRY), new int[] {0, 0, 0, 2, 2, 1, 1, 3, 4, 4, 0, 0}, 0f, 20f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.WHITE_CHERRY_TREE),
    ORANGE_WHITE_CHERRY(() -> ItemFoodTFC.get(Food.CHERRY), new int[] {0, 0, 0, 2, 2, 1, 1, 3, 4, 4, 0, 0}, 0f, 20f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.WHITE_CHERRY_TREE),
    RED_WHITE_CHERRY(() -> ItemFoodTFC.get(Food.CHERRY), new int[] {0, 0, 0, 0, 2, 1, 1, 3, 4, 4, 0, 0}, 0f, 20f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.WHITE_CHERRY_TREE),
    YELLOW_SWEETGUM(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -2f, 18f, 140f, 360f, 0.33f, true, false, false, TreesTFCF.SWEETGUM_TREE),
    ORANGE_SWEETGUM(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -2f, 18f, 140f, 360f, 0.33f, true, false, false, TreesTFCF.SWEETGUM_TREE),
    RED_SWEETGUM(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -2f, 18f, 140f, 360f, 0.33f, true, false, false, TreesTFCF.SWEETGUM_TREE),
    YELLOW_ALDER(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -4f, 13f, 60f, 400f, 0.33f, true, false, false, TreesTFCF.ALDER_TREE),
    ORANGE_ALDER(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -4f, 13f, 60f, 400f, 0.33f, true, false, false, TreesTFCF.ALDER_TREE),
    RED_ALDER(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -4f, 13f, 60f, 400f, 0.33f, true, false, false, TreesTFCF.ALDER_TREE),
    YELLOW_BEECH(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -15f, 9f, 220f, 300f, 0.33f, true, false, false, TreesTFCF.BEECH_TREE),
    ORANGE_BEECH(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -15f, 9f, 220f, 300f, 0.33f, true, false, false, TreesTFCF.BEECH_TREE),
    RED_BEECH(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -15f, 9f, 220f, 300f, 0.33f, true, false, false, TreesTFCF.BEECH_TREE),
    YELLOW_BLACK_WALNUT(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 16f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.BLACK_WALNUT_TREE),
    ORANGE_BLACK_WALNUT(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 16f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.BLACK_WALNUT_TREE),
    RED_BLACK_WALNUT(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 16f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.BLACK_WALNUT_TREE),
    YELLOW_BUTTERNUT(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -8f, 17f, 180f, 320f, 0.33f, true, false, false, TreesTFCF.BUTTERNUT_TREE),
    ORANGE_BUTTERNUT(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -8f, 17f, 180f, 320f, 0.33f, true, false, false, TreesTFCF.BUTTERNUT_TREE),
    RED_BUTTERNUT(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -8f, 17f, 180f, 320f, 0.33f, true, false, false, TreesTFCF.BUTTERNUT_TREE),
    /*
    *   Cypress trees are coniferous.
    */
    //YELLOW_CYPRESS(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 4f, 33f, 140f, 350f, 0.33f, true, false, false, TreesTFCF.CYPRESS_TREE),
    //ORANGE_CYPRESS(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 4f, 33f, 140f, 350f, 0.33f, true, false, false, TreesTFCF.CYPRESS_TREE),
    //RED_CYPRESS(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 4f, 33f, 140f, 350f, 0.33f, true, false, false, TreesTFCF.CYPRESS_TREE),
    YELLOW_EUROPEAN_OAK(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -8f, 15f, 140f, 430f, 0.33f, true, false, false, TreesTFCF.EUROPEAN_OAK_TREE),
    ORANGE_EUROPEAN_OAK(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -8f, 15f, 140f, 430f, 0.33f, true, false, false, TreesTFCF.EUROPEAN_OAK_TREE),
    RED_EUROPEAN_OAK(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -8f, 15f, 140f, 430f, 0.33f, true, false, false, TreesTFCF.EUROPEAN_OAK_TREE),
    YELLOW_HAZEL(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 14f, 60f, 400f, 0.33f, true, false, false, TreesTFCF.HAZEL_TREE),
    ORANGE_HAZEL(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 14f, 60f, 400f, 0.33f, true, false, false, TreesTFCF.HAZEL_TREE),
    RED_HAZEL(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 14f, 60f, 400f, 0.33f, true, false, false, TreesTFCF.HAZEL_TREE),
    YELLOW_HORNBEAM(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 12f, 140f, 430f, 0.33f, true, false, false, TreesTFCF.HORNBEAM_TREE),
    ORANGE_HORNBEAM(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 12f, 140f, 430f, 0.33f, true, false, false, TreesTFCF.HORNBEAM_TREE),
    RED_HORNBEAM(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 12f, 140f, 430f, 0.33f, true, false, false, TreesTFCF.HORNBEAM_TREE),
    YELLOW_POPLAR(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -7f, 14f, 140f, 400f, 0.33f, true, false, false, TreesTFCF.POPLAR_TREE),
    ORANGE_POPLAR(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -7f, 14f, 140f, 400f, 0.33f, true, false, false, TreesTFCF.POPLAR_TREE),
    RED_POPLAR(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -7f, 14f, 140f, 400f, 0.33f, true, false, false, TreesTFCF.POPLAR_TREE),
    YELLOW_RED_ELM(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 2f, 20f, 60f, 290f, 0.33f, true, false, false, TreesTFCF.RED_ELM_TREE),
    ORANGE_RED_ELM(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 2f, 20f, 60f, 290f, 0.33f, true, false, false, TreesTFCF.RED_ELM_TREE),
    RED_RED_ELM(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 2f, 20f, 60f, 290f, 0.33f, true, false, false, TreesTFCF.RED_ELM_TREE),
    YELLOW_WALNUT(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 16f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.WALNUT_TREE),
    ORANGE_WALNUT(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 16f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.WALNUT_TREE),
    RED_WALNUT(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 16f, 180f, 300f, 0.33f, true, false, false, TreesTFCF.WALNUT_TREE),
    YELLOW_WHITE_ELM(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 2f, 20f, 60f, 290f, 0.33f, true, false, false, TreesTFCF.WHITE_ELM_TREE),
    ORANGE_WHITE_ELM(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 2f, 20f, 60f, 290f, 0.33f, true, false, false, TreesTFCF.WHITE_ELM_TREE),
    RED_WHITE_ELM(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 2f, 20f, 60f, 290f, 0.33f, true, false, false, TreesTFCF.WHITE_ELM_TREE),
    YELLOW_WHITEBEAM(() -> ItemsTFCF.ROWAN_BERRY, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -10f, 12f, 140f, 430f, 0.33f, true, false, false, TreesTFCF.WHITEBEAM_TREE),
    ORANGE_WHITEBEAM(() -> ItemsTFCF.ROWAN_BERRY, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -10f, 12f, 140f, 430f, 0.33f, true, false, false, TreesTFCF.WHITEBEAM_TREE),
    RED_WHITEBEAM(() -> ItemsTFCF.ROWAN_BERRY, new int[] {0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0}, -10f, 12f, 140f, 430f, 0.33f, true, false, false, TreesTFCF.WHITEBEAM_TREE),
    YELLOW_ASH(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -6f, 12f, 60f, 140f, 0.33f, true, false, false, TreesTFCF.ASH_TREE),
    ORANGE_ASH(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -6f, 12f, 60f, 140f, 0.33f, true, false, false, TreesTFCF.ASH_TREE),
    RED_ASH(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -6f, 12f, 60f, 140f, 0.33f, true, false, false, TreesTFCF.ASH_TREE),
    YELLOW_ASPEN(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 16f, 10f, 80f, 0.33f, true, false, false, TreesTFCF.ASPEN_TREE),
    ORANGE_ASPEN(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 16f, 10f, 80f, 0.33f, true, false, false, TreesTFCF.ASPEN_TREE),
    RED_ASPEN(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -10f, 16f, 10f, 80f, 0.33f, true, false, false, TreesTFCF.ASPEN_TREE),
    YELLOW_BIRCH(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -15f, 7f, 20f, 180f, 0.33f, true, false, false, TreesTFCF.BIRCH_TREE),
    ORANGE_BIRCH(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -15f, 7f, 20f, 180f, 0.33f, true, false, false, TreesTFCF.BIRCH_TREE),
    RED_BIRCH(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -15f, 7f, 20f, 180f, 0.33f, true, false, false, TreesTFCF.BIRCH_TREE),
    YELLOW_CHESTNUT(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 11f, 35f, 160f, 320f, 0.33f, true, false, false, TreesTFCF.CHESTNUT_TREE),
    ORANGE_CHESTNUT(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 11f, 35f, 160f, 320f, 0.33f, true, false, false, TreesTFCF.CHESTNUT_TREE),
    RED_CHESTNUT(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 11f, 35f, 160f, 320f, 0.33f, true, false, false, TreesTFCF.CHESTNUT_TREE),
    YELLOW_HICKORY(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 7f, 29f, 80f, 250f, 0.33f, true, false, false, TreesTFCF.HICKORY_TREE),
    ORANGE_HICKORY(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 7f, 29f, 80f, 250f, 0.33f, true, false, false, TreesTFCF.HICKORY_TREE),
    RED_HICKORY(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 7f, 29f, 80f, 250f, 0.33f, true, false, false, TreesTFCF.HICKORY_TREE),
    YELLOW_MAPLE(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 3f, 20f, 140f, 360f, 0.33f, true, false, false, TreesTFCF.MAPLE_TREE),
    ORANGE_MAPLE(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 3f, 20f, 140f, 360f, 0.33f, true, false, false, TreesTFCF.MAPLE_TREE),
    RED_MAPLE(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 3f, 20f, 140f, 360f, 0.33f, true, false, false, TreesTFCF.MAPLE_TREE),
    YELLOW_OAK(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -8f, 12f, 180f, 430f, 0.33f, true, false, false, TreesTFCF.OAK_TREE),
    ORANGE_OAK(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -8f, 12f, 180f, 430f, 0.33f, true, false, false, TreesTFCF.OAK_TREE),
    RED_OAK(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, -8f, 12f, 180f, 430f, 0.33f, true, false, false, TreesTFCF.OAK_TREE),
    YELLOW_SYCAMORE(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 17f, 33f, 120f, 290f, 0.33f, true, false, false, TreesTFCF.SYCAMORE_TREE),
    ORANGE_SYCAMORE(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 17f, 33f, 120f, 290f, 0.33f, true, false, false, TreesTFCF.SYCAMORE_TREE),
    RED_SYCAMORE(null, new int[] {0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0}, 17f, 33f, 120f, 290f, 0.33f, true, false, false, TreesTFCF.SYCAMORE_TREE);

    /*static
    {
        for (FruitTreeTFCF tree : values())
        {
            WorldGenFruitTrees.register(tree);
        }
    }*/

    private final Supplier<Item> fruit;
    private final int[] stages;
    private final int numStages;
    /*private final Month flowerMonthStart;
    private final int floweringMonths;
    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final Month autumnMonthStart;
    private final int autumnMonths;
    private final Month winterMonthStart;
    private final int winterMonths;*/
    private final float growthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;
    public final boolean isNormalTree;
    public final boolean isCustomLog;
    public final boolean hasDeadLeaves;
    public final boolean isLogTree;
    public final boolean isSpecialBlock;
    public final Tree normalTree;

    //FruitTreeTFCF(Supplier<Item> fruit, int[] stages, Month flowerMonthStart, int floweringMonths, Month harvestMonthStart, int harvestingMonths, Month autumnMonthStart, int autumnMonths, Month winterMonthStart, int winterMonths, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime, boolean hasDeadLeaves, boolean isLogTree, Tree normalTree)
    SeasonalTrees(Supplier<Item> fruit, int[] stages, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime, boolean hasDeadLeaves, boolean isLogTree, boolean isSpecialBlock, Tree normalTree)
    {
        this.fruit = fruit;
        this.stages = stages;
        /*this.flowerMonthStart = flowerMonthStart;
        this.floweringMonths = floweringMonths;
        this.harvestMonthStart = harvestMonthStart;
        this.harvestingMonths = harvestingMonths;
        this.autumnMonthStart = autumnMonthStart;
        this.autumnMonths = autumnMonths;
        this.winterMonthStart = winterMonthStart;
        this.winterMonths = winterMonths;*/
        this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minRain = minRain;
        this.maxRain = maxRain;

        this.isCustomLog = false;
        this.isNormalTree = true;
        this.hasDeadLeaves = hasDeadLeaves;
        this.isLogTree = isLogTree;
        this.isSpecialBlock = isSpecialBlock;
        this.normalTree = normalTree;

        HashSet<Integer> hashSet = new HashSet<>();
        for (int stage : stages)
        {
            hashSet.add(stage);
        }
        this.numStages = hashSet.size() <= 1 ? 1 : hashSet.size() - 1;
    }

    //FruitTreeTFCF(Supplier<Item> fruit, Month flowerMonthStart, int floweringMonths, Month harvestMonthStart, int harvestingMonths, Month autumnMonthStart, int autumnMonths, Month winterMonthStart, int winterMonths, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime, boolean hasDeadLeaves, boolean isLogTree, Tree normalTree, boolean customLog)
    SeasonalTrees(Supplier<Item> fruit, int[] stages, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime, boolean hasDeadLeaves, boolean isLogTree, boolean isSpecialBlock, Tree normalTree, boolean customLog)
    {
        this.fruit = fruit;
        this.stages = stages;
        /*this.flowerMonthStart = flowerMonthStart;
        this.floweringMonths = floweringMonths;
        this.harvestMonthStart = harvestMonthStart;
        this.harvestingMonths = harvestingMonths;
        this.autumnMonthStart = autumnMonthStart;
        this.autumnMonths = autumnMonths;
        this.winterMonthStart = winterMonthStart;
        this.winterMonths = winterMonths;*/
        this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minRain = minRain;
        this.maxRain = maxRain;

        this.isCustomLog = customLog;
        this.isNormalTree = true;
        this.hasDeadLeaves = hasDeadLeaves;
        this.isLogTree = isLogTree;
        this.isSpecialBlock = isSpecialBlock;
        this.normalTree = normalTree;

        HashSet<Integer> hashSet = new HashSet<>();
        for (int stage : stages)
        {
            hashSet.add(stage);
        }
        this.numStages = hashSet.size() <= 1 ? 1 : hashSet.size() - 1;
    }

    public int getStageForMonth(Month month)
    {
        return stages[month.ordinal()];
    }

    public int getStageForMonth()
    {
        return getStageForMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear());
    }

    public int getNumStages()
    {
        return numStages;
    }

    public float getGrowthTime()
    {
        return this.growthTime;
    }

    public boolean isValidConditions(float temperature, float rainfall)
    {
        return minTemp - 5 < temperature && temperature < maxTemp + 5 && minRain - 50 < rainfall && rainfall < maxRain + 50;
    }

    public boolean isValidForGrowth(float temperature, float rainfall)
    {
        return minTemp < temperature && temperature < maxTemp && minRain < rainfall && rainfall < maxRain;
    }

    public Item getFoodDrop()
    {
        return this.fruit.get();
    }

    public Supplier<Item> getDrop()
    {
        return fruit;
    }

    public String getName()
    {
        return this.name();
    }

    @SideOnly(Side.CLIENT)
    public void addInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {}
}