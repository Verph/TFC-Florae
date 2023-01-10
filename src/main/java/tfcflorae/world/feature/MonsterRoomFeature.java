package tfcflorae.world.feature;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.slf4j.Logger;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.settings.RockSettings;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.util.TFCFDungeonHooks;

public class MonsterRoomFeature extends Feature<RockConfig>
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final EntityType<?>[] MOBS = new EntityType[]{EntityType.SKELETON, EntityType.ZOMBIE, EntityType.SPIDER, EntityType.CAVE_SPIDER, EntityType.WITHER_SKELETON, EntityType.STRIDER, EntityType.HUSK, EntityType.CREEPER};
    private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

    public MonsterRoomFeature(Codec<RockConfig> codec)
    {
        super(codec);
    }

    /**
     * Places the given feature at the given location.
     * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
     * that they can safely generate into.
     * @param context A context object with a reference to the level and the position the feature is being placed at
     */
    @Override
    public boolean place(FeaturePlaceContext<RockConfig> context)
    {
        Predicate<BlockState> predicate = Feature.isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE);
        BlockPos pos = context.origin();
        Random random = context.random();
        WorldGenLevel level = context.level();
        final RockConfig config = context.config();

        final ChunkDataProvider provider = ChunkDataProvider.get(context.chunkGenerator());
        final ChunkData data = provider.get(context.level(), pos);
        final RockSettings rock = data.getRockData().getRock(pos);
        final List<BlockState> states = config.getStates(rock.raw());

        Supplier<BlockState> state;
        if (states.size() == 1)
        {
            final BlockState onlyState = states.get(0);
            state = () -> onlyState;
        }
        else
        {
            state = () -> states.get(random.nextInt(states.size()));
        }

        int i = 3;
        int j = random.nextInt(2) + 2;
        int k = -j - 1;
        int l = j + 1;
        int i1 = -1;
        int j1 = 4;
        int k1 = random.nextInt(2) + 2;
        int l1 = -k1 - 1;
        int i2 = k1 + 1;
        int j2 = 0;

        for (int k2 = k; k2 <= l; ++k2)
        {
            for (int l2 = -1; l2 <= 4; ++l2)
            {
                for (int i3 = l1; i3 <= i2; ++i3)
                {
                    BlockPos pos1 = pos.offset(k2, l2, i3);
                    Material material = level.getBlockState(pos1).getMaterial();
                    boolean flag = material.isSolid();
                    if (l2 == -1 && !flag)
                    {
                        return false;
                    }

                    if (l2 == 4 && !flag)
                    {
                        return false;
                    }

                    if ((k2 == k || k2 == l || i3 == l1 || i3 == i2) && l2 == 0 && level.isEmptyBlock(pos1) && level.isEmptyBlock(pos1.above()))
                    {
                        ++j2;
                    }
                }
            }
        }

        if (j2 >= 1 && j2 <= 5)
        {
            for (int k3 = k; k3 <= l; ++k3)
            {
                for (int i4 = 3; i4 >= -1; --i4)
                {
                    for (int k4 = l1; k4 <= i2; ++k4)
                    {
                        BlockPos pos2 = pos.offset(k3, i4, k4);
                        BlockState blockstate = level.getBlockState(pos2);
                        if (k3 != k && i4 != -1 && k4 != l1 && k3 != l && i4 != 4 && k4 != i2)
                        {
                            if (!blockstate.is(TFCFBlocks.ROCK_CHEST.get()) && !blockstate.is(TFCFBlocks.ROCK_TRAPPED_CHEST.get()) && !blockstate.is(Blocks.SPAWNER))
                            {
                                safeSetBlock(level, pos2, AIR, predicate);
                            }
                        }
                        else if (pos2.getY() >= level.getMinBuildHeight() && !level.getBlockState(pos2.below()).getMaterial().isSolid())
                        {
                            level.setBlock(pos2, AIR, 2);
                        }
                        else if (blockstate.getMaterial().isSolid() && !blockstate.is(TFCFBlocks.ROCK_CHEST.get()) && !blockstate.is(TFCFBlocks.ROCK_TRAPPED_CHEST.get())) 
                        {
                            if (i4 == -1 && random.nextInt(4) != 0)
                            {
                                safeSetBlock(level, pos2, state.get(), predicate);
                            }
                            else
                            {
                                safeSetBlock(level, pos2, state.get(), predicate);
                            }
                            //safeSetBlock(level, pos2, state.get(), predicate);
                        }
                    }
                }
            }

            for (int l3 = 0; l3 < 2; ++l3)
            {
                for (int j4 = 0; j4 < 3; ++j4)
                {
                    int l4 = pos.getX() + random.nextInt(j * 2 + 1) - j;
                    int i5 = pos.getY();
                    int j5 = pos.getZ() + random.nextInt(k1 * 2 + 1) - k1;
                    BlockPos pos3 = new BlockPos(l4, i5, j5);
                    if (level.isEmptyBlock(pos3))
                    {
                        int j3 = 0;

                        for(Direction direction : Direction.Plane.HORIZONTAL)
                        {
                            if (level.getBlockState(pos3.relative(direction)).getMaterial().isSolid())
                            {
                                ++j3;
                            }
                        }
        
                        if (j3 == 1)
                        {
                            if (random.nextInt(2) == 0)
                            {
                                safeSetBlock(level, pos3, StructurePiece.reorient(level, pos3, TFCFBlocks.ROCK_CHEST.get().defaultBlockState()), predicate);
                                RandomizableContainerBlockEntity.setLootTable(level, random, pos3, BuiltInLootTables.SIMPLE_DUNGEON);
                            }
                            else
                            {
                                safeSetBlock(level, pos3, StructurePiece.reorient(level, pos3, TFCFBlocks.ROCK_TRAPPED_CHEST.get().defaultBlockState()), predicate);
                                RandomizableContainerBlockEntity.setLootTable(level, random, pos3, BuiltInLootTables.NETHER_BRIDGE);
                            }
                            break;
                        }
                    }
                }
            }

            safeSetBlock(level, pos, Blocks.SPAWNER.defaultBlockState(), predicate);
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof SpawnerBlockEntity)
            {
                ((SpawnerBlockEntity)blockentity).getSpawner().setEntityId(this.randomEntityId(random));
            }
            else
            {
                LOGGER.error("Failed to fetch mob spawner entity at ({}, {}, {})", pos.getX(), pos.getY(), pos.getZ());
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    private EntityType<?> randomEntityId(Random random)
    {
        return TFCFDungeonHooks.getRandomDungeonMob(random);
    }

    @Override
    public void safeSetBlock(WorldGenLevel level, BlockPos pos, BlockState state, Predicate<BlockState> oldState)
    {
        if (oldState.test(level.getBlockState(pos)))
        {
            level.setBlock(pos, state, 2);
        }
    }
}