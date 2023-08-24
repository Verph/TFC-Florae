package tfcflorae.common.fluid;

import java.awt.*;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.fluids.FlowingFluidRegistryObject;
import net.dries007.tfc.common.fluids.FluidType;
import net.dries007.tfc.common.fluids.MoltenFluid;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistrationHelpers;

import tfcflorae.TFCFlorae;
import tfcflorae.common.blocks.TFCFBlocks;

public class TFCFFluids
{
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, TFCFlorae.MOD_ID);

    public static final ResourceLocation WATER_STILL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOW = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY = new ResourceLocation("block/water_overlay");

    public static final ResourceLocation MOLTEN_STILL = Helpers.identifier("block/molten_still");
    public static final ResourceLocation MOLTEN_FLOW = Helpers.identifier("block/molten_flow");

    public static final int ALPHA_MASK = 0xFF000000;

    public static final Map<SimpleFluids, FlowingFluidRegistryObject<ForgeFlowingFluid>> SIMPLE_FLUIDS = Helpers.mapOfKeys(SimpleFluids.class, fluid -> register(
        "ore/" + fluid.getId(),
        "ore/flowing_" + fluid.getId(),
        properties -> properties.block(TFCFBlocks.SIMPLE_FLUIDS.get(fluid)).bucket(TFCItems.FLUID_BUCKETS.get(FluidType.asType(fluid))).explosionResistance(100),
        FluidAttributes.builder(MOLTEN_STILL, MOLTEN_FLOW)
            .translationKey("fluid.tfcflorae.ore." + fluid.getId())
            .color(ALPHA_MASK | fluid.getColor())
            .luminosity(15)
            .density(3000)
            .viscosity(6000)
            .temperature(1300)
            .sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA),
        MoltenFluid.Source::new,
        MoltenFluid.Flowing::new
    ));

    public static int dyeColorToInt(DyeColor dye)
    {
        float[] colors = dye.getTextureDiffuseColors();
        return new Color(colors[0], colors[1], colors[2]).getRGB();
    }

    private static FlowingFluidRegistryObject<ForgeFlowingFluid> register(String sourceName, String flowingName, Consumer<ForgeFlowingFluid.Properties> builder, FluidAttributes.Builder attributes)
    {
        return RegistrationHelpers.registerFluid(FLUIDS, sourceName, flowingName, builder, attributes);
    }

    private static <F extends FlowingFluid> FlowingFluidRegistryObject<F> register(String sourceName, String flowingName, Consumer<ForgeFlowingFluid.Properties> builder, FluidAttributes.Builder attributes, Function<ForgeFlowingFluid.Properties, F> sourceFactory, Function<ForgeFlowingFluid.Properties, F> flowingFactory)
    {
        return RegistrationHelpers.registerFluid(FLUIDS, sourceName, flowingName, builder, attributes, sourceFactory, flowingFactory);
    }

    private static <F extends Fluid> RegistryObject<F> register(String name, Supplier<F> factory)
    {
        return FLUIDS.register(name, factory);
    }
}
