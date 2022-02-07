package tfcflorae.util;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.ItemHeatHandler;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.wood.ItemWoodenBucket;
import net.dries007.tfc.types.DefaultPlants;

import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.wood.BlockLogTFCF;
import tfcflorae.objects.fluids.FluidsTFCF;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.types.TreesTFCF;

public class CapabilityHeatHandler
{
    public static void init()
    {
        /*ItemStack woodenBucket = new ItemStack(ItemsTFC.WOODEN_BUCKET);
        IFluidHandler woodenBucketSaltWater = woodenBucket.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        woodenBucketSaltWater.fill(new FluidStack(FluidsTFC.SALT_WATER.get(), Fluid.BUCKET_VOLUME), true);
        IFluidHandler woodenBucketSweetSap = woodenBucket.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        woodenBucketSweetSap.fill(new FluidStack(FluidsTFCF.SWEET_SAP.get(), Fluid.BUCKET_VOLUME), true);
        IFluidHandler woodenBucketSweetSyrup = woodenBucket.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        woodenBucketSweetSyrup.fill(new FluidStack(FluidsTFCF.SWEET_SYRUP.get(), Fluid.BUCKET_VOLUME), true);*/

        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ARTISTS_CONK))), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SULPHUR_SHELF))), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TURKEY_TAIL))), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.PORCINI))), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.AMANITA))), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BLACK_POWDERPUFF))), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CHANTERELLE))), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.DEATH_CAP))), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GIANT_CLUB))), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PARASOL_MUSHROOM))), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.STINKHORN))), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WEEPING_MILK_CAP))), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WOOD_BLEWIT))), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WOOLLY_GOMPHUS))), () -> new ItemHeatHandler(null, 1, 480));

        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFC.WOODEN_BUCKET), () -> new ItemHeatHandler(null, 1.76f, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.WOODEN_BUCKET_SALT), () -> new ItemHeatHandler(null, 3.993f, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.WOODEN_BUCKET_SUGAR), () -> new ItemHeatHandler(null, 1.255f, 480));
        /*CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of((Item) woodenBucketSaltWater), () -> new ItemHeatHandler(null, 3.993f, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of((Item) woodenBucketSweetSap), () -> new ItemHeatHandler(null, 4.01f, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of((Item) woodenBucketSweetSyrup), () -> new ItemHeatHandler(null, 2.1f, 480));*/

        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(Items.CLAY_BALL), () -> new ItemHeatHandler(null, 1, 1599));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.EARTHENWARE_CLAY), () -> new ItemHeatHandler(null, 1, 1599));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.KAOLINITE_CLAY), () -> new ItemHeatHandler(null, 1, 1599));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.STONEWARE_CLAY), () -> new ItemHeatHandler(null, 1, 1599));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockLogTFCF.get(TFCRegistries.TREES.getValue(TreesTFCF.EUCALYPTUS))), () -> new ItemHeatHandler(null, 1, 1599));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFC.STRAW), () -> new ItemHeatHandler(null, 1, 30));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.TWIG), () -> new ItemHeatHandler(null, 1, 50));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.DRIFTWOOD), () -> new ItemHeatHandler(null, 1, 60));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.BONES), () -> new ItemHeatHandler(null, 1, 425));

        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.BLACK_TEA), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.GREEN_TEA), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.WHITE_TEA), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CANNABIS_BUD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CANNABIS_LEAF), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.COCA_LEAF), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.OPIUM_POPPY_BULB), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.PEYOTE), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.TOBACCO_LEAF), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_COFFEA_CHERRIES), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CHAMOMILE_HEAD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DANDELION_HEAD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.LABRADOR_TEA_HEAD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.SUNFLOWER_HEAD), () -> new ItemHeatHandler(null, 1, 480));

        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_BLACK_TEA), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_GREEN_TEA), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_WHITE_TEA), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_BUD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_LEAF), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_COCA_LEAF), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_OPIUM_POPPY_BULB), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_PEYOTE), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_TOBACCO_LEAF), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CHAMOMILE_HEAD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_DANDELION_HEAD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_SUNFLOWER_HEAD), () -> new ItemHeatHandler(null, 1, 480));
    }
}
