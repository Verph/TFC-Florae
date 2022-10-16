package tfcflorae.common.blocks.rock;

import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.Material;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.*;
import net.dries007.tfc.common.blocks.rock.Rock.BlockType;
import net.dries007.tfc.common.blocks.soil.*;
import net.dries007.tfc.util.registry.RegistryRock;

import tfcflorae.common.blocks.TFCFBlocks;

public enum DripstoneRock implements RegistryRock
{
    DRIPSTONE(RockCategory.SEDIMENTARY, SandBlockType.YELLOW);

    public static final DripstoneRock[] VALUES = values();

    private final String serializedName;
    private final RockCategory category;
    private final SandBlockType sandType;

    DripstoneRock(RockCategory category, SandBlockType sandType)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.category = category;
        this.sandType = sandType;
    }

    public SandBlockType getSandType()
    {
        return sandType;
    }

    @Override
    public RockCategory category()
    {
        return category;
    }

    @Override
    public Supplier<? extends Block> getBlock(BlockType type)
    {
        return TFCFBlocks.DRIPSTONE_BLOCKS.get(this).get(type);
    }

    public Supplier<? extends Block> getBlock(TFCFBlockType type)
    {
        return TFCFBlocks.DRIPSTONE_BLOCKS.get(this).get(type);
    }

    @Override
    public Supplier<? extends Block> getAnvil()
    {
        return null;
    }

    @Override
    public Supplier<? extends SlabBlock> getSlab(BlockType type)
    {
        return null;
    }

    @Override
    public Supplier<? extends StairBlock> getStair(BlockType type)
    {
        return null;
    }

    @Override
    public Supplier<? extends WallBlock> getWall(BlockType type)
    {
        return null;
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }

    public enum TFCFBlockType implements StringRepresentable
    {
        STONE_TILES((rock, self) -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.DEEPSLATE).strength(rock.category().hardness(6.5f), 10).requiresCorrectToolForDrops()), true),
        COBBLED_BRICKS((rock, self) -> new MossGrowingRotatedPillarBlock(Block.Properties.of(Material.STONE).sound(SoundType.DEEPSLATE_BRICKS).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops(), TFCFBlocks.ROCK_BLOCKS.get(rock).get(self.mossy())), true),
        FLAGSTONE_BRICKS((rock, self) -> new MossGrowingBlock(Block.Properties.of(Material.STONE).sound(SoundType.DEEPSLATE_TILES).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops(), TFCFBlocks.ROCK_BLOCKS.get(rock).get(self.mossy())), true),
        MOSSY_COBBLED_BRICKS((rock, self) -> new MossSpreadingRotatedPillarBlock(Block.Properties.of(Material.STONE).sound(SoundType.DEEPSLATE_BRICKS).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops()), true),
        MOSSY_FLAGSTONE_BRICKS((rock, self) -> new MossSpreadingBlock(Block.Properties.of(Material.STONE).sound(SoundType.DEEPSLATE_TILES).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops()), true),
        CRACKED_FLAGSTONE_BRICKS((rock, self) -> new Block(Block.Properties.of(Material.STONE).sound(SoundType.DEEPSLATE_TILES).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops()), true);
        //COLUMN((rock, self) -> new RotatedPillarBlock(Block.Properties.of(Material.STONE).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), false),
        //POLISHED_COLUMN((rock, self) -> new RotatedPillarBlock(Block.Properties.of(Material.STONE).sound(SoundType.BASALT).strength(rock.category().hardness(9f), 15).requiresCorrectToolForDrops()), false);

        public static final TFCFBlockType[] VALUES = TFCFBlockType.values();

        public static TFCFBlockType valueOf(int i)
        {
            return i >= 0 && i < VALUES.length ? VALUES[i] : STONE_TILES;
        }

        private final boolean variants;
        private final BiFunction<RegistryRock, TFCFBlockType, Block> blockFactory;
        private final String serializedName;

        TFCFBlockType(BiFunction<RegistryRock, TFCFBlockType, Block> blockFactory, boolean variants)
        {
            this.blockFactory = blockFactory;
            this.variants = variants;
            this.serializedName = name().toLowerCase(Locale.ROOT);
        }

        /**
         * @return if this block type should be given slab, stair and wall variants
         */
        public boolean hasVariants()
        {
            return variants;
        }

        public Block create(RegistryRock rock)
        {
            return blockFactory.apply(rock, this);
        }

        @Override
        public String getSerializedName()
        {
            return serializedName;
        }

        @Nullable
        public TFCFBlockType mossy()
        {
            return switch (this)
                {
                    case COBBLED_BRICKS, MOSSY_COBBLED_BRICKS -> MOSSY_COBBLED_BRICKS;
                    case FLAGSTONE_BRICKS, MOSSY_FLAGSTONE_BRICKS -> MOSSY_FLAGSTONE_BRICKS;
                    default -> null;
                };
        }
    }
}