package tfcflorae.common.blocks.rock;

import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.dries007.tfc.common.blocks.rock.*;
import net.dries007.tfc.common.blocks.rock.Rock.BlockType;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.util.registry.RegistryRock;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.soil.TFCFSand;

/**
 * Default wood types used for block registration calls.
 *
 * @see RegistryRock for addon support, to use {@link BlockType}.
 */
public enum TFCFRock implements RegistryRock
{
    BRECCIA(RockCategory.IGNEOUS_INTRUSIVE, SandBlockType.WHITE),
    FOIDOLITE(RockCategory.IGNEOUS_INTRUSIVE, SandBlockType.BLACK),
    PORPHYRY(RockCategory.IGNEOUS_INTRUSIVE, SandBlockType.RED),
    PERIDOTITE(RockCategory.IGNEOUS_EXTRUSIVE, SandBlockType.GREEN),
    BLAIMORITE(RockCategory.IGNEOUS_EXTRUSIVE, SandBlockType.GREEN),
    BONINITE(RockCategory.IGNEOUS_EXTRUSIVE, SandBlockType.BLACK),
    CARBONATITE(RockCategory.IGNEOUS_EXTRUSIVE, SandBlockType.BROWN),
    MUDSTONE(RockCategory.SEDIMENTARY, SandBlockType.WHITE),
    SANDSTONE(RockCategory.SEDIMENTARY, SandBlockType.YELLOW),
    SILTSTONE(RockCategory.SEDIMENTARY, SandBlockType.PINK),
    ARKOSE(RockCategory.SEDIMENTARY, SandBlockType.BROWN),
    JASPILLITE(RockCategory.SEDIMENTARY, SandBlockType.PINK),
    TRAVERTINE(RockCategory.SEDIMENTARY, SandBlockType.WHITE),
    WACKESTONE(RockCategory.SEDIMENTARY, SandBlockType.BLACK),
    BLACKBAND_IRONSTONE(RockCategory.SEDIMENTARY, SandBlockType.BLACK),
    BLUESCHIST(RockCategory.METAMORPHIC, SandBlockType.BROWN/*, TFCFSand.BLUE*/),
    CATLINITE(RockCategory.METAMORPHIC, SandBlockType.RED),
    GREENSCHIST(RockCategory.METAMORPHIC, SandBlockType.GREEN),
    NOVACULITE(RockCategory.METAMORPHIC, SandBlockType.WHITE),
    SOAPSTONE(RockCategory.METAMORPHIC, SandBlockType.WHITE),
    KOMATIITE(RockCategory.METAMORPHIC, SandBlockType.GREEN),
    CATACLASITE(RockCategory.METAMORPHIC, SandBlockType.RED),
    MYLONITE(RockCategory.METAMORPHIC, SandBlockType.YELLOW);

    public static final TFCFRock[] VALUES = values();

    private final String serializedName;
    private final RockCategory category;
    private final SandBlockType sandType;
    //private final TFCFSand sandTypeTFCF;

    TFCFRock(RockCategory category, SandBlockType sandType)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.category = category;
        this.sandType = sandType;
        //this.sandTypeTFCF = null;
    }

    /*TFCFRock(RockCategory category, TFCFSand sandTypeTFCF)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.category = category;
        this.sandTypeTFCF = sandTypeTFCF;
        this.sandType = null;
    }*/

    public SandBlockType getSandType()
    {
        return sandType;
    }

    /*public TFCFSand getSandTypeTFCF()
    {
        return sandTypeTFCF;
    }*/

    @Override
    public RockCategory category()
    {
        return category;
    }

    @Override
    public Supplier<Block> getAnvil()
    {
        return TFCFBlocks.ROCK_ANVILS.get(this);
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }

    public Supplier<Block> getBlock(BlockType type)
    {
        return TFCFBlocks.ROCK_BLOCKS.get(this).get(type);
    }

    public SlabBlock createSlab(Rock.BlockType type)
    {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5f, 10).requiresCorrectToolForDrops();
        if (mossy(type) == type)
        {
            return new MossSpreadingSlabBlock(properties);
        }
        else if (mossy(type) != null)
        {
            return new MossGrowingSlabBlock(properties, TFCFBlocks.ROCK_DECORATIONS.get(this).get(mossy(type)).slab());
        }
        return new SlabBlock(properties);
    }

    public StairBlock createStairs(Rock.BlockType type)
    {
        Supplier<BlockState> state = () -> TFCFBlocks.ROCK_BLOCKS.get(this).get(type).get().defaultBlockState();
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5f, 10).requiresCorrectToolForDrops();
        if (mossy(type) == type)
        {
            return new MossSpreadingStairBlock(state, properties);
        }
        else if (mossy(type) != null)
        {
            return new MossGrowingStairsBlock(state, properties, TFCFBlocks.ROCK_DECORATIONS.get(this).get(mossy(type)).stair());
        }
        return new StairBlock(state, properties);
    }

    public WallBlock createWall(Rock.BlockType type)
    {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(1.5f, 10).requiresCorrectToolForDrops();
        if (mossy(type) == type)
        {
            return new MossSpreadingWallBlock(properties);
        }
        else if (mossy(type) != null)
        {
            return new MossGrowingWallBlock(properties, TFCFBlocks.ROCK_DECORATIONS.get(this).get(mossy(type)).wall());
        }
        return new WallBlock(properties);
    }

    @Nullable
    private BlockType mossy(Rock.BlockType type)
    {
        switch (type)
        {
            case COBBLE:
            case MOSSY_COBBLE:
                return Rock.BlockType.MOSSY_COBBLE;
            case BRICKS:
            case MOSSY_BRICKS:
                return Rock.BlockType.MOSSY_BRICKS;
            default:
                return null;
        }
    }
}
