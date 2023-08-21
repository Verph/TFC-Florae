package tfcflorae.common.entities;

import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.entities.EntityHelpers;
import net.dries007.tfc.common.entities.Fauna;
import net.dries007.tfc.common.entities.aquatic.*;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.world.chunkdata.ChunkData;

public class TFCFFaunas
{
    public static final FaunaType<Silkmoth> SILK_MOTH = registerAnimal(TFCFEntities.SILKMOTH);
    public static final FaunaType<Frog> FROG = registerAnimal(TFCFEntities.FROG);
    public static final FaunaType<Tadpole> TADPOLE = registerFish(TFCFEntities.TADPOLE);
    public static final FaunaType<TFCFParrot> PARROT = registerFish(TFCFEntities.PARROT);
    public static final Map<Fish, FaunaType<FreshwaterFish>> FRESHWATER_FISH = Helpers.mapOfKeys(Fish.class, fish -> !fish.isCod(), fish -> registerFish(TFCFEntities.FRESHWATER_FISH.get(fish)));
    public static final Map<Fish, FaunaType<FreshwaterCodFish>> FRESHWATER_COD_FISH = Helpers.mapOfKeys(Fish.class, fish -> fish.isCod(), fish -> registerFish(TFCFEntities.FRESHWATER_COD_FISH.get(fish)));

    public static void registerSpawnPlacements()
    {
        registerSpawnPlacement(SILK_MOTH);
        registerSpawnPlacement(FROG);
        registerSpawnPlacement(TADPOLE);
        registerSpawnPlacement(PARROT);
        FRESHWATER_FISH.values().forEach(fish -> registerSpawnPlacement(fish));
        FRESHWATER_COD_FISH.values().forEach(fish -> registerSpawnPlacement(fish));
    }

    private static <E extends Mob> FaunaType<E> registerAnimal(RegistryObject<EntityType<E>> entity)
    {
        return register(entity, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES);
    }

    private static <E extends Mob> FaunaType<E> registerFish(RegistryObject<EntityType<E>> entity)
    {
        return register(entity, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES);
    }

    private static <E extends Mob> FaunaType<E> register(RegistryObject<EntityType<E>> entity, SpawnPlacements.Type spawnPlacement, Heightmap.Types heightmapType)
    {
        final Supplier<Fauna> fauna = Fauna.MANAGER.register(entity.getId());
        return new FaunaType<>(entity, fauna, spawnPlacement, heightmapType);
    }

    private static <E extends Mob> void registerSpawnPlacement(FaunaType<E> type)
    {
        SpawnPlacements.register(type.entity().get(), type.spawnPlacementType(), type.heightmapType(), (mob, level, heightmap, pos, rand) -> {
            final Fauna fauna = type.fauna().get();
            final ChunkGenerator generator = level.getLevel().getChunkSource().getGenerator();
            if (rand.nextInt(fauna.getChance()) != 0)
            {
                return false;
            }

            if (mob instanceof AquaticMob aquaticMob && !aquaticMob.canSpawnIn(level.getFluidState(pos).getType()))
            {
                return false;
            }

            final int seaLevel = generator.getSeaLevel();
            if (fauna.getDistanceBelowSeaLevel() != -1 && pos.getY() > (seaLevel - fauna.getDistanceBelowSeaLevel()))
            {
                return false;
            }

            final ChunkData data = EntityHelpers.getChunkDataForSpawning(level, pos);
            if (!fauna.getClimate().isValid(data, pos, rand))
            {
                return false;
            }

            final BlockPos below = pos.below();
            if (fauna.isSolidGround() && !Helpers.isBlock(level.getBlockState(below), BlockTags.VALID_SPAWN))
            {
                return false;
            }

            /*if (type.entity() instanceof Frog frog)
            {
                float temperature = 10F;
                if (level != null && pos != null && level instanceof ServerLevel serverLevel)
                {
                    temperature = Climate.getTemperature(serverLevel, pos);
                }
                int specialColour = rand.nextInt(8);
                frog.setVariantRandom(specialColour, temperature);
            }*/

            return fauna.getMaxBrightness() == -1 || level.getRawBrightness(pos, 0) <= fauna.getMaxBrightness();
        });
    }

    record FaunaType<E extends Mob>(Supplier<EntityType<E>> entity, Supplier<Fauna> fauna, SpawnPlacements.Type spawnPlacementType, Heightmap.Types heightmapType) {}
}
