package tfcflorae.world.feature;

import net.minecraft.core.Registry;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.function.Supplier;

public abstract class TFCFAbstractDeferredRegistry<T>
{
    protected final Registry<T> registry;
    protected final String modId;

    public TFCFAbstractDeferredRegistry(Registry<T> registry, String modId)
    {
        this.registry = registry;
        this.modId = modId;
    }

    public abstract <E extends T> Supplier<E> register(String key, Supplier<E> entry);
    public abstract void register(IEventBus bus);
}
