package tfcflorae.world.placement;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static tfcflorae.TFCFlorae.*;

@SuppressWarnings("unused")
public final class TFCFPlacements
{
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Registry.PLACEMENT_MODIFIER_REGISTRY, MOD_ID);

    public static final RegistryObject<PlacementModifierType<ShallowWaterPlacement>> SHALLOW_WATER = register("shallow_water", () -> ShallowWaterPlacement.CODEC);

    private static <C extends PlacementModifier> RegistryObject<PlacementModifierType<C>> register(String name, PlacementModifierType<C> codec)
    {
        return PLACEMENT_MODIFIERS.register(name, () -> codec);
    }
}