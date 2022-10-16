package tfcflorae.world.feature;

import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class TFCFDeferredRegistry<T extends IForgeRegistryEntry<T>> extends TFCFAbstractDeferredRegistry<T>
{
    private final DeferredRegister<T> deferredRegistry;
    
    public TFCFDeferredRegistry(Registry<T> registry, String modId)
    {
        super(registry, modId);
        this.deferredRegistry = DeferredRegister.create(registry.key(), modId);
    }

    @SuppressWarnings("all")
    public static <T> TFCFAbstractDeferredRegistry<T> create(Registry<T> key, String modId)
    {
        return new TFCFDeferredRegistry(key, modId);
    }
    
    public <E extends T> Supplier<E> register(String key, Supplier<E> entry)
    {
        return this.deferredRegistry.register(key, entry);
    }

    public void register(IEventBus bus)
    {
        this.deferredRegistry.register(bus);
    }
}
