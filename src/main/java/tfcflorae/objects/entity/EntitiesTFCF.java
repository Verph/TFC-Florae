package tfcflorae.objects.entity;

import javax.annotation.Nonnull;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.DataSerializerEntry;

import net.dries007.tfc.util.Helpers;

import tfcflorae.TFCFlorae;
import tfcflorae.objects.entity.animal.EntitySilkMoth;

import static tfcflorae.TFCFlorae.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class EntitiesTFCF
{
    @GameRegistry.ObjectHolder(MODID + ":long")
    public static final DataSerializerEntry LONG_DATA_SERIALIZER_ENTRY = Helpers.getNull();

    private static final DataSerializer<Long> LONG_DATA_SERIALIZER = new DataSerializer<Long>()
    {
        public void write(PacketBuffer buf, @Nonnull Long value)
        {
            buf.writeLong(value);
        }

        public Long read(PacketBuffer buf)
        {
            return buf.readLong();
        }

        public DataParameter<Long> createKey(int id)
        {
            return new DataParameter<>(id, this);
        }

        @Nonnull
        public Long copyValue(@Nonnull Long value)
        {
            return value;
        }
    };

    private static int id = 1; // don't use id 0, it's easier to debug if something goes wrong

    @SuppressWarnings("unchecked")
    public static DataSerializer<Long> getLongDataSerializer()
    {
        return (DataSerializer<Long>) LONG_DATA_SERIALIZER_ENTRY.getSerializer();
    }

    @SubscribeEvent
    public static void registerDataSerializers(RegistryEvent.Register<DataSerializerEntry> event)
    {
        event.getRegistry().register(new DataSerializerEntry(LONG_DATA_SERIALIZER).setRegistryName("long"));
    }

    public static void preInit()
    {
        register("fruit_boat", EntityBoatTFCF.class);
        registerLiving("silkmothtfcf", EntitySilkMoth.class, 0xDBDBD8, 0xF8F8F3);
    }

    private static void register(String name, Class<? extends Entity> cls)
    {
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, name), cls, name, id++, TFCFlorae.getInstance(), 160, 20, true);
    }

    private static void registerLiving(String name, Class<? extends Entity> cls, int eggPrimaryColor, int eggSecondaryColor)
    {
        //Register entity and create a spawn egg for creative
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, name), cls, name, id++, TFCFlorae.getInstance(), 80, 3, true, eggPrimaryColor, eggSecondaryColor);
    }
}
