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
import net.dries007.tfc.common.entities.land.DairyAnimal;
import net.dries007.tfc.common.entities.land.Mammal;
import net.dries007.tfc.common.entities.aquatic.*;
import net.dries007.tfc.common.entities.land.OviparousAnimal;
import net.dries007.tfc.common.entities.land.WoolyAnimal;
import net.dries007.tfc.common.entities.predator.Predator;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;

import tfcflorae.common.blocks.wood.TFCFWood;
import tfcflorae.common.items.Items;

import static tfcflorae.TFCFlorae.MOD_ID;

/**
 * For reference, each living entity needs:
 * - A registered entity renderer
 * - A registered FaunaType for spawn placement
 * - A spawn egg item (and a bucket item if it's bucketable)
 * - Entity attributes, set in this class below
 * - In datagen, a json entry for fauna
 * - In datagen, an entry in biome spawners
 *
 * When making an entity, some rules:
 * - Each synced data parameter and any variable that needs to persist should be serialized
 * - Use Brain or Goals when appropriate, and do not mix the two
 * - Avoid creating unnecessary classes. See the anonymous constructors at the bottom of this class.
 */
@SuppressWarnings("unused")
public class TFCFEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);

    // Misc
    public static final Map<TFCFWood, RegistryObject<EntityType<TFCFBoat>>> BOATS = Helpers.mapOfKeys(TFCFWood.class, wood ->
        register("boat/" + wood.name(), EntityType.Builder.<TFCFBoat>of((type, level) -> new TFCFBoat(type, level, Items.BOATS.get(wood)), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10))
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
