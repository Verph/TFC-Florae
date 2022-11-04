package tfcflorae.common.blocks.rock;

import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.dries007.tfc.common.blocks.rock.*;
import net.dries007.tfc.common.blocks.rock.Rock.BlockType;
import net.dries007.tfc.common.blocks.soil.*;
import net.dries007.tfc.util.registry.RegistryRock;

import tfcflorae.common.blocks.TFCFBlocks;

public class TFCFRock implements RegistryRock
{
    public static final TFCFRock[] VALUES = values();

    private final String serializedName;
    private final RockCategory category;
    private final SandBlockType sandType;

    TFCFRock(RockCategory category, SandBlockType sandType)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.category = category;
        this.sandType = sandType;
    }

    private String name()
    {
        return null;
    }

    private static TFCFRock[] values()
    {
        return null;
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
        return TFCFBlocks.ROCK_BLOCKS.get(this).get(type);
    }

    @Override
    public Supplier<? extends Block> getAnvil()
    {
        return null;
    }

    @Override
    public Supplier<? extends SlabBlock> getSlab(BlockType type)
    {
        return TFCFBlocks.ROCK_DECORATIONS.get(this).get(type).slab();
    }

    @Override
    public Supplier<? extends StairBlock> getStair(BlockType type)
    {
        return TFCFBlocks.ROCK_DECORATIONS.get(this).get(type).stair();
    }

    @Override
    public Supplier<? extends WallBlock> getWall(BlockType type)
    {
        return TFCFBlocks.ROCK_DECORATIONS.get(this).get(type).wall();
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }

    public enum TFCFBlockType implements StringRepresentable
    {
        STONE_TILES((self, rock) -> new Block(Block.Properties.of(Material.STONE, MaterialColor.STONE).strength(1.5f, 10).sound(SoundType.STONE).requiresCorrectToolForDrops()), true);

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
    }

    public static Enum<?> getEnum(RockSettings rockSettings)
    {
        Enum<?> selectedEnum = Rock.GRANITE;
        for (Rock rock : Rock.values())
        {
            if (rockSettings.id() == Helpers.identifier(rock.getSerializedName()))
            {
                selectedEnum = rock;
            }
        }
        for (TFCFRock tfcfRock : TFCFRock.values())
        {
            if (rockSettings.id() == Helpers.identifier(tfcfRock.getSerializedName()))
            {
                selectedEnum = tfcfRock;
            }
        }
        return selectedEnum;
    }
}