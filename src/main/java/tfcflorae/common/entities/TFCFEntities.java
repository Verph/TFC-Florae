package tfcflorae.common.entities;

import java.util.Locale;
import java.util.Map;

import net.dries007.tfc.common.entities.predator.FelinePredator;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;

import tfcflorae.common.blocks.wood.TFCFWood;
import tfcflorae.common.items.TFCFItems;

import static tfcflorae.TFCFlorae.MOD_ID;

@SuppressWarnings("unused")
public class TFCFEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);

    // Misc
    public static final Map<TFCFWood, RegistryObject<EntityType<TFCFBoat>>> BOATS = Helpers.mapOfKeys(TFCFWood.class, wood ->
        register("boat/" + wood.name(), EntityType.Builder.<TFCFBoat>of((type, level) -> new TFCFBoat(type, level, TFCFItems.BOATS.get(wood)), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10))
    );

    public static <E extends Entity> RegistryObject<EntityType<E>> register(String name, EntityType.Builder<E> builder)
    {
        return register(name, builder, true);
    }

    public static <E extends Entity> RegistryObject<EntityType<E>> register(String name, EntityType.Builder<E> builder, boolean serialize)
    {
        final String id = name.toLowerCase(Locale.ROOT);
        return ENTITIES.register(id, () -> builder.build(MOD_ID + ":" + id));
    }

    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event)
    {
    }
}
