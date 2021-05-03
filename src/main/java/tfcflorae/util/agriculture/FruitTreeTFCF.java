package tfcflorae.util.agriculture;

import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.IFruitTree;
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

public enum FruitTreeTFCF implements IFruitTree
{
    // Fruiting and/or flowering trees
    BAOBAB(() -> ItemsTFCF.BAOBAB_FRUIT, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, 23f, 40f, 10f, 150f, 0.33f, false, false, TreesTFCF.BAOBAB_TREE),
    MACLURA(() -> ItemsTFCF.OSAGE_ORANGE, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -1f, 17f, 140f, 400f, 0.33f, false, false, TreesTFCF.MACLURA_TREE),
    MAHOGANY(() -> ItemsTFCF.SKY_FRUIT, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, 23f, 42f, 220f, 500f, 0.33f, false, false, TreesTFCF.MAHOGANY_TREE),
    PINK_IVORY(() -> ItemsTFCF.PINK_IVORY_DRUPE, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, 18f, 31f, 210f, 500f, 0.33f, false, false, TreesTFCF.PINK_IVORY_TREE),
    SYZYGIUM(() -> ItemsTFCF.RIBERRY, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, 13f, 35f, 140f, 360f, 0.33f, false, false, TreesTFCF.SYZYGIUM_TREE),
    YEW(() -> ItemsTFCF.YEW_BERRY, Month.MARCH, 2, Month.JUNE, 4, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -15f, 11f, 180f, 350f, 0.33f, true, false, TreesTFCF.YEW_TREE),
    PURPLE_JACARANDA(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.JANUARY, 1, 10f, 34f, 180f, 300f, 0.33f, true, false, TreesTFCF.JACARANDA_TREE),
    YELLOW_JACARANDA(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.JANUARY, 1, 10f, 34f, 180f, 300f, 0.33f, true, false, TreesTFCF.JACARANDA_TREE),
    JUNIPER(() -> ItemsTFCF.JUNIPER, Month.MAY, 1, Month.AUGUST, 2, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 20f, 80f, 350f, 0.33f, false, false, TreesTFCF.JUNIPER_TREE),
    RED_CEDAR(() -> ItemsTFCF.JUNIPER, Month.MAY, 1, Month.AUGUST, 2, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 17f, 10f, 240f, 0.33f, false, false, TreesTFCF.RED_CEDAR_TREE),
    WHITE_CEDAR(() -> ItemsTFCF.JUNIPER, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 17f, 10f, 240f, 0.33f, false, false, TreesTFCF.WHITE_CEDAR_TREE),
    PINK_IPE(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.JANUARY, 1, 15f, 32f, 150f, 350f, 0.33f, true, false, TreesTFCF.IPE_TREE),
    WHITE_IPE(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.JANUARY, 1, 15f, 32f, 150f, 350f, 0.33f, true, false, TreesTFCF.IPE_TREE),
    YELLOW_IPE(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.JANUARY, 1, 15f, 32f, 150f, 350f, 0.33f, true, false, TreesTFCF.IPE_TREE),
    ARGYLE_EUCALYPTUS(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.JANUARY, 1, 18f, 39f, 120f, 300f, 0.33f, true, true, TreesTFCF.EUCALYPTUS_TREE, true),
    RAINBOW_EUCALYPTUS(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.JANUARY, 1, 18f, 39f, 120f, 300f, 0.33f, true, true, TreesTFCF.EUCALYPTUS_TREE, true),
    SNOW_GUM_EUCALYPTUS(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.JANUARY, 1, 18f, 39f, 120f, 300f, 0.33f, true, true, TreesTFCF.EUCALYPTUS_TREE, true),

    // Seasonal trees
    GINKGO(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 6f, 20f, 240f, 550f, 0.33f, true, false, TreesTFCF.GINKGO_TREE),
    LARCH(() -> ItemsTFCF.PINECONE, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -12f, 15f, 60f, 400f, 0.33f, true, false, TreesTFCF.LARCH_TREE),
    LOCUST(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -6f, 15f, 120f, 290f, 0.33f, true, false, TreesTFCF.LOCUST_TREE),
    YELLOW_HAWTHORN(() -> ItemsTFCF.HAWTHORN, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 14f, 180f, 400f, 0.33f, true, false, TreesTFCF.HAWTHORN_TREE),
    ORANGE_HAWTHORN(() -> ItemsTFCF.HAWTHORN, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 14f, 180f, 400f, 0.33f, true, false, TreesTFCF.HAWTHORN_TREE),
    RED_HAWTHORN(() -> ItemsTFCF.HAWTHORN, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 14f, 180f, 400f, 0.33f, true, false, TreesTFCF.HAWTHORN_TREE),
    YELLOW_ROWAN(() -> ItemsTFCF.ROWAN_BERRY, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -15f, 8f, 180f, 400f, 0.33f, true, false, TreesTFCF.ROWAN_TREE),
    ORANGE_ROWAN(() -> ItemsTFCF.ROWAN_BERRY, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -15f, 8f, 180f, 400f, 0.33f, true, false, TreesTFCF.ROWAN_TREE),
    RED_ROWAN(() -> ItemsTFCF.ROWAN_BERRY, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -15f, 8f, 180f, 400f, 0.33f, true, false, TreesTFCF.ROWAN_TREE),
    YELLOW_PINK_CHERRY(() -> ItemFoodTFC.get(Food.CHERRY), Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 0f, 20f, 180f, 300f, 0.33f, true, false, TreesTFCF.PINK_CHERRY_TREE),
    ORANGE_PINK_CHERRY(() -> ItemFoodTFC.get(Food.CHERRY), Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 0f, 20f, 180f, 300f, 0.33f, true, false, TreesTFCF.PINK_CHERRY_TREE),
    RED_PINK_CHERRY(() -> ItemFoodTFC.get(Food.CHERRY), Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 0f, 20f, 180f, 300f, 0.33f, true, false, TreesTFCF.PINK_CHERRY_TREE),
    YELLOW_WHITE_CHERRY(() -> ItemFoodTFC.get(Food.CHERRY), Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 0f, 20f, 180f, 300f, 0.33f, true, false, TreesTFCF.WHITE_CHERRY_TREE),
    ORANGE_WHITE_CHERRY(() -> ItemFoodTFC.get(Food.CHERRY), Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 0f, 20f, 180f, 300f, 0.33f, true, false, TreesTFCF.WHITE_CHERRY_TREE),
    RED_WHITE_CHERRY(() -> ItemFoodTFC.get(Food.CHERRY), Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 0f, 20f, 180f, 300f, 0.33f, true, false, TreesTFCF.WHITE_CHERRY_TREE),
    YELLOW_SWEETGUM(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -2f, 18f, 140f, 360f, 0.33f, true, false, TreesTFCF.SWEETGUM_TREE),
    ORANGE_SWEETGUM(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -2f, 18f, 140f, 360f, 0.33f, true, false, TreesTFCF.SWEETGUM_TREE),
    RED_SWEETGUM(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -2f, 18f, 140f, 360f, 0.33f, true, false, TreesTFCF.SWEETGUM_TREE),
    YELLOW_ALDER(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -4f, 13f, 60f, 400f, 0.33f, true, false, TreesTFCF.ALDER_TREE),
    ORANGE_ALDER(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -4f, 13f, 60f, 400f, 0.33f, true, false, TreesTFCF.ALDER_TREE),
    RED_ALDER(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -4f, 13f, 60f, 400f, 0.33f, true, false, TreesTFCF.ALDER_TREE),
    YELLOW_BEECH(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -15f, 9f, 220f, 300f, 0.33f, true, false, TreesTFCF.BEECH_TREE),
    ORANGE_BEECH(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -15f, 9f, 220f, 300f, 0.33f, true, false, TreesTFCF.BEECH_TREE),
    RED_BEECH(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -15f, 9f, 220f, 300f, 0.33f, true, false, TreesTFCF.BEECH_TREE),
    YELLOW_BLACK_WALNUT(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 16f, 180f, 300f, 0.33f, true, false, TreesTFCF.BLACK_WALNUT_TREE),
    ORANGE_BLACK_WALNUT(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 16f, 180f, 300f, 0.33f, true, false, TreesTFCF.BLACK_WALNUT_TREE),
    RED_BLACK_WALNUT(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 16f, 180f, 300f, 0.33f, true, false, TreesTFCF.BLACK_WALNUT_TREE),
    YELLOW_BUTTERNUT(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 17f, 180f, 320f, 0.33f, true, false, TreesTFCF.BUTTERNUT_TREE),
    ORANGE_BUTTERNUT(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 17f, 180f, 320f, 0.33f, true, false, TreesTFCF.BUTTERNUT_TREE),
    RED_BUTTERNUT(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 17f, 180f, 320f, 0.33f, true, false, TreesTFCF.BUTTERNUT_TREE),
    YELLOW_CYPRESS(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 4f, 33f, 140f, 350f, 0.33f, true, false, TreesTFCF.CYPRESS_TREE),
    ORANGE_CYPRESS(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 4f, 33f, 140f, 350f, 0.33f, true, false, TreesTFCF.CYPRESS_TREE),
    RED_CYPRESS(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 4f, 33f, 140f, 350f, 0.33f, true, false, TreesTFCF.CYPRESS_TREE),
    YELLOW_EUROPEAN_OAK(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 15f, 140f, 430f, 0.33f, true, false, TreesTFCF.EUROPEAN_OAK_TREE),
    ORANGE_EUROPEAN_OAK(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 15f, 140f, 430f, 0.33f, true, false, TreesTFCF.EUROPEAN_OAK_TREE),
    RED_EUROPEAN_OAK(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 15f, 140f, 430f, 0.33f, true, false, TreesTFCF.EUROPEAN_OAK_TREE),
    YELLOW_HAZEL(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 14f, 60f, 400f, 0.33f, true, false, TreesTFCF.HAZEL_TREE),
    ORANGE_HAZEL(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 14f, 60f, 400f, 0.33f, true, false, TreesTFCF.HAZEL_TREE),
    RED_HAZEL(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 14f, 60f, 400f, 0.33f, true, false, TreesTFCF.HAZEL_TREE),
    YELLOW_HORNBEAM(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 12f, 140f, 430f, 0.33f, true, false, TreesTFCF.HORNBEAM_TREE),
    ORANGE_HORNBEAM(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 12f, 140f, 430f, 0.33f, true, false, TreesTFCF.HORNBEAM_TREE),
    RED_HORNBEAM(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 12f, 140f, 430f, 0.33f, true, false, TreesTFCF.HORNBEAM_TREE),
    YELLOW_POPLAR(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -7f, 14f, 140f, 400f, 0.33f, true, false, TreesTFCF.POPLAR_TREE),
    ORANGE_POPLAR(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -7f, 14f, 140f, 400f, 0.33f, true, false, TreesTFCF.POPLAR_TREE),
    RED_POPLAR(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -7f, 14f, 140f, 400f, 0.33f, true, false, TreesTFCF.POPLAR_TREE),
    YELLOW_RED_ELM(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, 2f, 20f, 60f, 290f, 0.33f, true, false, TreesTFCF.RED_ELM_TREE),
    ORANGE_RED_ELM(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, 2f, 20f, 60f, 290f, 0.33f, true, false, TreesTFCF.RED_ELM_TREE),
    RED_RED_ELM(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, 2f, 20f, 60f, 290f, 0.33f, true, false, TreesTFCF.RED_ELM_TREE),
    YELLOW_WALNUT(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 16f, 180f, 300f, 0.33f, true, false, TreesTFCF.WALNUT_TREE),
    ORANGE_WALNUT(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 16f, 180f, 300f, 0.33f, true, false, TreesTFCF.WALNUT_TREE),
    RED_WALNUT(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 16f, 180f, 300f, 0.33f, true, false, TreesTFCF.WALNUT_TREE),
    YELLOW_WHITE_ELM(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 2f, 20f, 60f, 290f, 0.33f, true, false, TreesTFCF.WHITE_ELM_TREE),
    ORANGE_WHITE_ELM(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 2f, 20f, 60f, 290f, 0.33f, true, false, TreesTFCF.WHITE_ELM_TREE),
    RED_WHITE_ELM(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 2f, 20f, 60f, 290f, 0.33f, true, false, TreesTFCF.WHITE_ELM_TREE),
    YELLOW_WHITEBEAM(() -> ItemsTFCF.ROWAN_BERRY, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 12f, 140f, 430f, 0.33f, true, false, TreesTFCF.WHITEBEAM_TREE),
    ORANGE_WHITEBEAM(() -> ItemsTFCF.ROWAN_BERRY, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 12f, 140f, 430f, 0.33f, true, false, TreesTFCF.WHITEBEAM_TREE),
    RED_WHITEBEAM(() -> ItemsTFCF.ROWAN_BERRY, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 12f, 140f, 430f, 0.33f, true, false, TreesTFCF.WHITEBEAM_TREE),
    YELLOW_ASH(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -6f, 12f, 60f, 140f, 0.33f, true, false, TreesTFCF.ASH_TREE),
    ORANGE_ASH(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -6f, 12f, 60f, 140f, 0.33f, true, false, TreesTFCF.ASH_TREE),
    RED_ASH(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -6f, 12f, 60f, 140f, 0.33f, true, false, TreesTFCF.ASH_TREE),
    YELLOW_ASPEN(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 16f, 10f, 80f, 0.33f, true, false, TreesTFCF.ASPEN_TREE),
    ORANGE_ASPEN(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 16f, 10f, 80f, 0.33f, true, false, TreesTFCF.ASPEN_TREE),
    RED_ASPEN(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -10f, 16f, 10f, 80f, 0.33f, true, false, TreesTFCF.ASPEN_TREE),
    YELLOW_BIRCH(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -15f, 7f, 20f, 180f, 0.33f, true, false, TreesTFCF.BIRCH_TREE),
    ORANGE_BIRCH(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -15f, 7f, 20f, 180f, 0.33f, true, false, TreesTFCF.BIRCH_TREE),
    RED_BIRCH(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -15f, 7f, 20f, 180f, 0.33f, true, false, TreesTFCF.BIRCH_TREE),
    YELLOW_CHESTNUT(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 11f, 35f, 160f, 320f, 0.33f, true, false, TreesTFCF.CHESTNUT_TREE),
    ORANGE_CHESTNUT(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 11f, 35f, 160f, 320f, 0.33f, true, false, TreesTFCF.CHESTNUT_TREE),
    RED_CHESTNUT(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 11f, 35f, 160f, 320f, 0.33f, true, false, TreesTFCF.CHESTNUT_TREE),
    YELLOW_HICKORY(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 7f, 29f, 80f, 250f, 0.33f, true, false, TreesTFCF.HICKORY_TREE),
    ORANGE_HICKORY(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 7f, 29f, 80f, 250f, 0.33f, true, false, TreesTFCF.HICKORY_TREE),
    RED_HICKORY(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 7f, 29f, 80f, 250f, 0.33f, true, false, TreesTFCF.HICKORY_TREE),
    YELLOW_MAPLE(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 3f, 20f, 140f, 360f, 0.33f, true, false, TreesTFCF.MAPLE_TREE),
    ORANGE_MAPLE(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 3f, 20f, 140f, 360f, 0.33f, true, false, TreesTFCF.MAPLE_TREE),
    RED_MAPLE(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 2, 3f, 20f, 140f, 360f, 0.33f, true, false, TreesTFCF.MAPLE_TREE),
    YELLOW_OAK(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 12f, 180f, 430f, 0.33f, true, false, TreesTFCF.OAK_TREE),
    ORANGE_OAK(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 12f, 180f, 430f, 0.33f, true, false, TreesTFCF.OAK_TREE),
    RED_OAK(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 3, -8f, 12f, 180f, 430f, 0.33f, true, false, TreesTFCF.OAK_TREE),
    YELLOW_SYCAMORE(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 1, 17f, 33f, 120f, 290f, 0.33f, true, false, TreesTFCF.SYCAMORE_TREE),
    ORANGE_SYCAMORE(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 1, 17f, 33f, 120f, 290f, 0.33f, true, false, TreesTFCF.SYCAMORE_TREE),
    RED_SYCAMORE(null, Month.APRIL, 1, Month.JULY, 3, Month.SEPTEMBER, 3, Month.DECEMBER, 1, 17f, 33f, 120f, 290f, 0.33f, true, false, TreesTFCF.SYCAMORE_TREE);

    // Fruit Trees
    /*
    ABIU(() -> ItemsTFCF.ABIU, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    AMLA(() -> ItemsTFCF.AMLA, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    APRICOT(() -> ItemsTFCF.APRICOT, Month.FEBRUARY, 3, Month.NOVEMBER, 1, 23f, 36f, 250f, 400f, 0.33f),
    AVOCADO(() -> ItemsTFCF.AVOCADO, Month.APRIL, 2, Month.SEPTEMBER, 1, 9f, 27f, 60f, 230f, 0.33f),
    BAEL(() -> ItemsTFCF.BAEL, Month.MAY, 2, Month.JULY, 3, 18f, 31f, 250f, 400f, 0.33f),
    BAY_LAUREL(() -> ItemsTFCF.BAY_LAUREL, Month.MAY, 2, Month.OCTOBER, 2, 9f, 25f, 100f, 280f, 0.33f),
    BER(() -> ItemsTFCF.BER, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BERGAMOT(() -> ItemsTFCF.BERGAMOT, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    BLACK_CHERRY(() -> ItemsTFCF.BLACK_CHERRY, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BLACK_PEPPER(() -> ItemsTFCF.BLACK_PEPPER, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BLACKCURRANT(() -> ItemsTFCF.BLACKCURRANT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BLACKTHORN(() -> ItemsTFCF.BLACKTHORN, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BUDDHA_HAND(() -> ItemsTFCF.BUDDHA_HAND, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    CACAO(() -> ItemsTFCF.CACAO, Month.JUNE, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    CHERRY_PLUM(() -> ItemsTFCF.CHERRY_PLUM, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    CITRON(() -> ItemsTFCF.CITRON, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    //COCONUT(() -> ItemsTFCF.COCONUT, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    CRABAPPLE(() -> ItemsTFCF.CRABAPPLE, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    DAMSON_PLUM(() -> ItemsTFCF.DAMSON_PLUM, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    DATE(() -> ItemsTFCF.DATE, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    ELDER(() -> ItemsTFCF.ELDER, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    FIG(() -> ItemsTFCF.FIG, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    FINGER_LIME(() -> ItemsTFCF.FINGER_LIME, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    GRAPEFRUIT(() -> ItemsTFCF.GRAPEFRUIT, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    GUAVA(() -> ItemsTFCF.GUAVA, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    ICE_CREAM_BEAN(() -> ItemsTFCF.ICE_CREAM_BEAN, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    JACKFRUIT(() -> ItemsTFCF.JACKFRUIT, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    JUJUBE(() -> ItemsTFCF.JUJUBE, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    JUNIPER(() -> ItemsTFCF.JUNIPER, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    KAKI(() -> ItemsTFCF.KAKI, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    KEY_LIME(() -> ItemsTFCF.KEY_LIME, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    KLUWAK(() -> ItemsTFCF.KLUWAK, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    KUMQUAT(() -> ItemsTFCF.KUMQUAT, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    LONGAN(() -> ItemsTFCF.LONGAN, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    LOQUAT(() -> ItemsTFCF.LOQUAT, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    LYCHEE(() -> ItemsTFCF.LYCHEE, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    MAMEY_SAPOTE(() -> ItemsTFCF.MAMEY_SAPOTE, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    MANDERIN(() -> ItemsTFCF.MANDERIN, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    MANGO(() -> ItemsTFCF.MANGO, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    MANGOSTEEN(() -> ItemsTFCF.MANGOSTEEN, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    NECTARINE(() -> ItemsTFCF.NECTARINE, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    OHIA_AI(() -> ItemsTFCF.OHIA_AI, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    OSAGE_ORANGE(() -> ItemsTFCF.OSAGE_ORANGE, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PAPAYA(() -> ItemsTFCF.PAPAYA, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PASSION_FRUIT(() -> ItemsTFCF.PASSION_FRUIT, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PEAR(() -> ItemsTFCF.PEAR, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PERSIAN_LIME(() -> ItemsTFCF.PERSIAN_LIME, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PERSIMMON(() -> ItemsTFCF.PERSIMMON, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PERUVIAN_PEPPER(() -> ItemsTFCF.PERUVIAN_PEPPER, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    PLANTAIN(() -> ItemsTFCF.PLANTAIN, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    POMEGRANATE(() -> ItemsTFCF.POMEGRANATE, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    POMELO(() -> ItemsTFCF.POMELO, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    QUINCE(() -> ItemsTFCF.QUINCE, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    RAINIER_CHERRY(() -> ItemsTFCF.RAINIER_CHERRY, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    RED_BANANA(() -> ItemsTFCF.RED_BANANA, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    RED_CURRANT(() -> ItemsTFCF.RED_CURRANT, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    SAND_PEAR(() -> ItemsTFCF.SAND_PEAR, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    SAPODILLA(() -> ItemsTFCF.SAPODILLA, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    SATSUMA(() -> ItemsTFCF.SATSUMA, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    SOUR_CHERRY(() -> ItemsTFCF.SOUR_CHERRY, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    SOURSOP(() -> ItemsTFCF.SOURSOP, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    STARFRUIT(() -> ItemsTFCF.STARFRUIT, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    TAMARILLO(() -> ItemsTFCF.TAMARILLO, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    TANGERINE(() -> ItemsTFCF.TANGERINE, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    TROPICAL_APRICOT(() -> ItemsTFCF.TROPICAL_APRICOT, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    WILD_CHERRY(() -> ItemsTFCF.WILD_CHERRY, Month.JUNE, 4, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    */

    // Nut Trees
    /*
    BRAZIL_NUT(() -> ItemsTFCF.BRAZIL_NUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BREADNUT(() -> ItemsTFCF.BREADNUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BUNYA(() -> ItemsTFCF.BUNYA_NUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    BUTTERNUT(() -> ItemsTFCF.BUTTERNUT, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    CANDLENUT(() -> ItemsTFCF.CANDLENUT, Month.APRIL, 1, Month.JUNE, 4, 5f, 21f, 100f, 350f, 0.33f),
    CASHEW(() -> ItemsTFCF.CASHEW, Month.APRIL, 1, Month.JUNE, 4, 5f, 21f, 100f, 350f, 0.33f),
    HEARTNUT(() -> ItemsTFCF.HEARTNUT, Month.APRIL, 1, Month.JUNE, 4, 5f, 21f, 100f, 350f, 0.33f),
    KOLA(() -> ItemsTFCF.KOLA_NUT, Month.APRIL, 1, Month.JUNE, 4, 5f, 21f, 100f, 350f, 0.33f),
    KUKUI(() -> ItemsTFCF.KUKUI_NUT, Month.APRIL, 1, Month.JUNE, 4, 5f, 21f, 100f, 350f, 0.33f),
    MACADAMIA(() -> ItemsTFCF.MACADAMIA, Month.APRIL, 1, Month.JUNE, 4, 5f, 21f, 100f, 350f, 0.33f),
    MONGONGO(() -> ItemsTFCF.MONGONGO, Month.APRIL, 1, Month.JUNE, 4, 5f, 21f, 100f, 350f, 0.33f),
    MONKEY_PUZZLE(() -> ItemsTFCF.MONKEY_PUZZLE_NUT, Month.APRIL, 1, Month.JUNE, 4, 5f, 21f, 100f, 350f, 0.33f),
    NUTMEG(() -> ItemsTFCF.NUTMEG, Month.APRIL, 1, Month.JUNE, 4, 5f, 21f, 100f, 350f, 0.33f),
    PARADISE_NUT(() -> ItemsTFCF.PARADISE_NUT, Month.APRIL, 1, Month.JUNE, 4, 5f, 21f, 100f, 350f, 0.33f),
    PISTACHIO(() -> ItemsTFCF.PISTACHIO, Month.APRIL, 1, Month.JUNE, 4, 5f, 21f, 100f, 350f, 0.33f),
    WALNUT(() -> ItemsTFCF.WALNUT, Month.APRIL, 1, Month.JUNE, 4, 5f, 21f, 100f, 350f, 0.33f);
    */

    static
    {
        for (FruitTreeTFCF tree : values())
        {
            WorldGenFruitTrees.register(tree);
        }
    }

    private final Supplier<Item> fruit;
    private final Month flowerMonthStart;
    private final int floweringMonths;
    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final Month autumnMonthStart;
    private final int autumnMonths;
    private final Month winterMonthStart;
    private final int winterMonths;
    private final float growthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;
    public final boolean isNormalTree;
    public final boolean isCustomLog;
    public final boolean hasDeadLeaves;
    public final boolean isLogTree;
    public final Tree normalTree;

    FruitTreeTFCF(Supplier<Item> fruit, Month flowerMonthStart, int floweringMonths, Month harvestMonthStart, int harvestingMonths, Month autumnMonthStart, int autumnMonths, Month winterMonthStart, int winterMonths, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime, boolean hasDeadLeaves)
    {
        this.fruit = fruit;
        this.flowerMonthStart = flowerMonthStart;
        this.floweringMonths = floweringMonths;
        this.harvestMonthStart = harvestMonthStart;
        this.harvestingMonths = harvestingMonths;
        this.autumnMonthStart = autumnMonthStart;
        this.autumnMonths = autumnMonths;
        this.winterMonthStart = winterMonthStart;
        this.winterMonths = winterMonths;
        this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minRain = minRain;
        this.maxRain = maxRain;

        this.isCustomLog = false;
        this.isNormalTree = false;
        this.hasDeadLeaves = hasDeadLeaves;
        isLogTree = false;
        normalTree = null;
    }
    
    FruitTreeTFCF(Supplier<Item> fruit, Month flowerMonthStart, int floweringMonths, Month harvestMonthStart, int harvestingMonths, Month autumnMonthStart, int autumnMonths, Month winterMonthStart, int winterMonths, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime, boolean hasDeadLeaves, boolean isLogTree, Tree normalTree)
    {
        this.fruit = fruit;
        this.flowerMonthStart = flowerMonthStart;
        this.floweringMonths = floweringMonths;
        this.harvestMonthStart = harvestMonthStart;
        this.harvestingMonths = harvestingMonths;
        this.autumnMonthStart = autumnMonthStart;
        this.autumnMonths = autumnMonths;
        this.winterMonthStart = winterMonthStart;
        this.winterMonths = winterMonths;
        this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minRain = minRain;
        this.maxRain = maxRain;

        this.isCustomLog = false;
        this.isNormalTree = true;
        this.hasDeadLeaves = hasDeadLeaves;
        this.isLogTree = isLogTree;
        this.normalTree = normalTree;
    }
    
    FruitTreeTFCF(Supplier<Item> fruit, Month flowerMonthStart, int floweringMonths, Month harvestMonthStart, int harvestingMonths, Month autumnMonthStart, int autumnMonths, Month winterMonthStart, int winterMonths, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime, boolean hasDeadLeaves, boolean isLogTree, Tree normalTree, boolean customLog)
    {
        this.fruit = fruit;
        this.flowerMonthStart = flowerMonthStart;
        this.floweringMonths = floweringMonths;
        this.harvestMonthStart = harvestMonthStart;
        this.harvestingMonths = harvestingMonths;
        this.autumnMonthStart = autumnMonthStart;
        this.autumnMonths = autumnMonths;
        this.winterMonthStart = winterMonthStart;
        this.winterMonths = winterMonths;
        this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minRain = minRain;
        this.maxRain = maxRain;

        this.isCustomLog = customLog;
        this.isNormalTree = true;
        this.hasDeadLeaves = hasDeadLeaves;
        this.isLogTree = isLogTree;
        this.normalTree = normalTree;
    }

    @Override
    public float getGrowthTime()
    {
        return this.growthTime;
    }

    @Override
    public boolean isFlowerMonth(Month month)
    {
        Month testing = this.flowerMonthStart;
        for (int i = 0; i < this.floweringMonths; i++)
        {
            if (testing.equals(month)) return true;
            testing = testing.next();
        }
        return false;
    }

    @Override
    public boolean isHarvestMonth(Month month)
    {
        Month testing = this.harvestMonthStart;
        for (int i = 0; i < this.harvestingMonths; i++)
        {
            if (testing.equals(month)) return true;
            testing = testing.next();
        }
        return false;
    }

    public boolean isAutumnMonth(Month month)
    {
        Month testing = this.autumnMonthStart;
        for (int i = 0; i < this.autumnMonths; i++)
        {
            if (testing.equals(month)) return true;
            testing = testing.next();
        }
        return false;
    }

    public boolean isWinterMonth(Month month)
    {
        Month testing = this.winterMonthStart;
        for (int i = 0; i < this.winterMonths; i++)
        {
            if (testing.equals(month)) return true;
            testing = testing.next();
        }
        return false;
    }

    @Override
    public boolean isValidConditions(float temperature, float rainfall)
    {
        return minTemp - 5 < temperature && temperature < maxTemp + 5 && minRain - 50 < rainfall && rainfall < maxRain + 50;
    }

    @Override
    public boolean isValidForGrowth(float temperature, float rainfall)
    {
        return minTemp < temperature && temperature < maxTemp && minRain < rainfall && rainfall < maxRain;
    }

    @Override
    public ItemStack getFoodDrop()
    {
        return new ItemStack(this.fruit.get());
    }

    @Override
    public String getName()
    {
        return this.name();
    }
}