package tfcflorae.common.entities;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

import net.dries007.tfc.common.entities.predator.FelinePredator;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;

import tfcflorae.client.TFCFSounds;
import tfcflorae.common.blocks.wood.TFCFWood;
import tfcflorae.common.items.TFCFItems;

import static tfcflorae.TFCFlorae.MOD_ID;

@SuppressWarnings("unused")
public class TFCFEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);

    public static final Map<TFCFWood, RegistryObject<EntityType<TFCFBoat>>> BOATS = boatEntityMapper();

    private static Map<TFCFWood, RegistryObject<EntityType<TFCFBoat>>> boatEntityMapper()
    {
        Map<TFCFWood,  RegistryObject<EntityType<TFCFBoat>>> Map = new HashMap<>();
        for (TFCFWood wood : TFCFWood.class.getEnumConstants())
        {
            Map.put(wood, register("boat/" + wood.name(), EntityType.Builder.<TFCFBoat>of((type, level) -> new TFCFBoat(type, level, wood, TFCFItems.BOATS.get(wood)), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)));
        }
        return Map;
    }

    public static final RegistryObject<EntityType<Silkmoth>> SILKMOTH = register("silk_moth", EntityType.Builder.of(Silkmoth::new, MobCategory.CREATURE).sized(0.7F, 0.6F).clientTrackingRange(10));
    public static final RegistryObject<EntityType<Frog>> FROG = register("frog", EntityType.Builder.of(Frog::new, MobCategory.CREATURE).sized(0.5F, 0.5F).clientTrackingRange(10));
    public static final RegistryObject<EntityType<Tadpole>> TADPOLE = register("tadpole", EntityType.Builder.of(Tadpole::new, MobCategory.WATER_AMBIENT).sized(0.4F, 0.3F).clientTrackingRange(10));
    public static final RegistryObject<EntityType<TFCFParrot>> PARROT = register("parrot", EntityType.Builder.of(TFCFParrot::new, MobCategory.CREATURE).sized(0.5F, 0.9F).clientTrackingRange(8));
    public static final Map<Fish, RegistryObject<EntityType<FreshwaterFish>>> FRESHWATER_FISH = Helpers.mapOfKeys(Fish.class, fish -> !fish.isCod(), fish -> register(fish.getSerializedName(), EntityType.Builder.<FreshwaterFish>of((type, level) -> new FreshwaterFish(type, level, TFCFSounds.FRESHWATER_FISHES.get(fish), TFCFItems.FRESHWATER_FISH_BUCKETS.get(fish)), MobCategory.WATER_AMBIENT).sized(fish.getWidth(), fish.getHeight()).clientTrackingRange(4)));
    public static final Map<Fish, RegistryObject<EntityType<FreshwaterCodFish>>> FRESHWATER_COD_FISH = Helpers.mapOfKeys(Fish.class, fish -> fish.isCod(), fish -> register(fish.getSerializedName(), EntityType.Builder.<FreshwaterCodFish>of((type, level) -> new FreshwaterCodFish(type, level, TFCFSounds.FRESHWATER_FISHES.get(fish), TFCFItems.FRESHWATER_FISH_BUCKETS.get(fish)), MobCategory.WATER_AMBIENT).sized(fish.getWidth(), fish.getHeight()).clientTrackingRange(4)));

    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event)
    {
        event.put(SILKMOTH.get(), Silkmoth.createAttributes().build());
        event.put(FROG.get(), Frog.createAttributes().build());
        event.put(TADPOLE.get(), Tadpole.createAttributes().build());
        event.put(PARROT.get(), TFCFParrot.createAttributes().build());
        FRESHWATER_FISH.values().forEach(reg -> event.put(reg.get(), AbstractFish.createAttributes().build()));
        FRESHWATER_COD_FISH.values().forEach(reg -> event.put(reg.get(), AbstractFish.createAttributes().build()));
    }

    public static <E extends Entity> RegistryObject<EntityType<E>> register(String name, EntityType.Builder<E> builder)
    {
        return register(name, builder, true);
    }

    public static <E extends Entity> RegistryObject<EntityType<E>> register(String name, EntityType.Builder<E> builder, boolean serialize)
    {
        final String id = name.toLowerCase(Locale.ROOT);
        return ENTITIES.register(id, () -> {
            if (!serialize) builder.noSave();
            return builder.build(MOD_ID + ":" + id);
        });
    }
}
