package tfcflorae.objects;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;

import net.dries007.tfc.util.loot.ApplySimpleSkill;

import static tfcflorae.TFCFlorae.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class LootTablesTFCF
{
    public static ResourceLocation ANIMALS_SILK_MOTH;

    public static void init()
    {
        ANIMALS_SILK_MOTH = register("animals/silk_moth");
    }

    private static ResourceLocation register(String id)
    {
        return LootTableList.register(new ResourceLocation(MODID, id));
    }

    private static void remove(LootTableLoadEvent event, String tableName, String pool)
    {
        if (tableName.equals(event.getName().toString()))
        {
            event.getTable().removePool(pool);
        }
    }

    private static void remove(LootTableLoadEvent event, String tableName, String poolName, String entry)
    {
        if (tableName.equals(event.getName().toString()))
        {
            LootPool pool = event.getTable().getPool(poolName);
            //noinspection ConstantConditions
            if (pool != null)
            {
                pool.removeEntry(entry);
            }
        }
    }
}
