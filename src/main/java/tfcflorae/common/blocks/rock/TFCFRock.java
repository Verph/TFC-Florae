package tfcflorae.common.blocks.rock;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.common.blocks.SandstoneBlockType;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.*;
import net.dries007.tfc.common.blocks.rock.Rock.BlockType;
import net.dries007.tfc.common.blocks.soil.*;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryRock;
import net.dries007.tfc.world.settings.RockSettings;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.util.registry.TFCFRegistryRock;

public enum TFCFRock implements TFCFRegistryRock
{
    RED_GRANITE(RockCategory.IGNEOUS_INTRUSIVE, MaterialColor.COLOR_RED, SandBlockType.RED),
    BRECCIA(RockCategory.IGNEOUS_INTRUSIVE, MaterialColor.SAND, SandBlockType.WHITE),
    FOIDOLITE(RockCategory.IGNEOUS_INTRUSIVE, MaterialColor.COLOR_PURPLE, SandBlockType.WHITE),
    PORPHYRY(RockCategory.IGNEOUS_EXTRUSIVE, MaterialColor.TERRACOTTA_RED, SandBlockType.RED),
    PERIDOTITE(RockCategory.IGNEOUS_EXTRUSIVE, MaterialColor.STONE, SandBlockType.GREEN),
    BLAIMORITE(RockCategory.IGNEOUS_EXTRUSIVE, MaterialColor.COLOR_GREEN, SandBlockType.GREEN),
    BONINITE(RockCategory.IGNEOUS_EXTRUSIVE, MaterialColor.COLOR_GRAY, SandBlockType.BLACK),
    CARBONATITE(RockCategory.IGNEOUS_EXTRUSIVE, MaterialColor.SAND, SandBlockType.BROWN),
    LATERITE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_ORANGE, SandBlockType.RED),
    MUDSTONE(RockCategory.SEDIMENTARY, MaterialColor.STONE, SandBlockType.WHITE),
    SANDSTONE(RockCategory.SEDIMENTARY, MaterialColor.SAND, SandBlockType.YELLOW),
    SILTSTONE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_WHITE, SandBlockType.PINK),
    ARKOSE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_WHITE, SandBlockType.BROWN),
    JASPILLITE(RockCategory.SEDIMENTARY, MaterialColor.DIRT, SandBlockType.BROWN),
    TRAVERTINE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_WHITE, SandBlockType.WHITE),
    WACKESTONE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_BLACK, SandBlockType.BLACK),
    BLACKBAND_IRONSTONE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_MAGENTA, SandBlockType.BLACK),
    CATACLASITE(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_BROWN, SandBlockType.RED),
    BLUESCHIST(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_LIGHT_BLUE, SandBlockType.WHITE),
    CATLINITE(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_PINK, SandBlockType.PINK),
    GREENSCHIST(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_GREEN, SandBlockType.GREEN),
    NOVACULITE(RockCategory.METAMORPHIC, MaterialColor.QUARTZ, SandBlockType.WHITE),
    SOAPSTONE(RockCategory.METAMORPHIC, MaterialColor.STONE, SandBlockType.WHITE),
    KOMATIITE(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_CYAN, SandBlockType.GREEN),
    MYLONITE(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_WHITE, SandBlockType.YELLOW);

    public static final TFCFRock[] VALUES = values();

    private final String serializedName;
    private final RockCategory category;
    private final MaterialColor color;
    private final SandBlockType sandType;

    TFCFRock(RockCategory category, MaterialColor color, SandBlockType sandType)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.category = category;
        this.color = color;
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
    public MaterialColor color()
    {
        return color;
    }

    @Override
    public Supplier<? extends Block> getBlock(BlockType type)
    {
        return TFCFBlocks.TFCF_ROCK_BLOCKS.get(this).get(type);
    }

    @Override
    public Supplier<? extends Block> getBlockTFC(RegistryRock rock, TFCFBlockType type)
    {
        return TFCFBlocks.ROCK_BLOCKS.get(rock).get(type);
    }

    @Override
    public Supplier<? extends Block> getBlockTFCF(TFCFBlockType type)
    {
        return TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(this).get(type);
    }

    @Override
    public Supplier<? extends Block> getAnvil()
    {
        return null;
    }

    @Override
    public Supplier<? extends SlabBlock> getSlab(BlockType type)
    {
        return TFCFBlocks.TFCF_ROCKTYPE_DECORATIONS.get(this).get(type).slab();
    }

    @Override
    public Supplier<? extends StairBlock> getStair(BlockType type)
    {
        return TFCFBlocks.TFCF_ROCKTYPE_DECORATIONS.get(this).get(type).stair();
    }

    @Override
    public Supplier<? extends WallBlock> getWall(BlockType type)
    {
        return TFCFBlocks.TFCF_ROCKTYPE_DECORATIONS.get(this).get(type).wall();
    }

    @Override
    public Supplier<? extends SlabBlock> getSlabTFC(RegistryRock rock, TFCFBlockType type)
    {
        if (TFCFBlocks.TFC_ROCK_DECORATIONS.get(rock) != null && TFCFBlocks.TFC_ROCK_DECORATIONS.get(rock).get(type) != null)
        {
            return TFCFBlocks.TFC_ROCK_DECORATIONS.get(rock).get(type).slab();
        }
        else
        {
            return TFCFBlocks.TFC_ROCK_DECORATIONS.get(Rock.GRANITE).get(TFCFBlockType.COBBLED_BRICKS).slab();
        }
    }

    @Override
    public Supplier<? extends StairBlock> getStairTFC(RegistryRock rock, TFCFBlockType type)
    {
        if (TFCFBlocks.TFC_ROCK_DECORATIONS.get(rock) != null && TFCFBlocks.TFC_ROCK_DECORATIONS.get(rock).get(type) != null)
        {
            return TFCFBlocks.TFC_ROCK_DECORATIONS.get(rock).get(type).stair();
        }
        else
        {
            return TFCFBlocks.TFC_ROCK_DECORATIONS.get(Rock.GRANITE).get(TFCFBlockType.COBBLED_BRICKS).stair();
        }
    }

    @Override
    public Supplier<? extends WallBlock> getWallTFC(RegistryRock rock, TFCFBlockType type)
    {
        if (TFCFBlocks.TFC_ROCK_DECORATIONS.get(rock) != null && TFCFBlocks.TFC_ROCK_DECORATIONS.get(rock).get(type) != null)
        {
            return TFCFBlocks.TFC_ROCK_DECORATIONS.get(rock).get(type).wall();
        }
        else
        {
            return TFCFBlocks.TFC_ROCK_DECORATIONS.get(Rock.GRANITE).get(TFCFBlockType.COBBLED_BRICKS).wall();
        }
    }

    @Override
    public Supplier<? extends SlabBlock> getSlabTFCF(TFCFBlockType type)
    {
        return TFCFBlocks.TFCF_ROCK_DECORATIONS.get(this).get(type).slab();
    }

    @Override
    public Supplier<? extends StairBlock> getStairTFCF(TFCFBlockType type)
    {
        return TFCFBlocks.TFCF_ROCK_DECORATIONS.get(this).get(type).stair();
    }

    @Override
    public Supplier<? extends WallBlock> getWallTFCF(TFCFBlockType type)
    {
        return TFCFBlocks.TFCF_ROCK_DECORATIONS.get(this).get(type).wall();
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }

    public enum TFCFBlockType implements StringRepresentable
    {
        STONE_TILES((rock, self) -> new Block(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.DEEPSLATE).strength(rock.category().hardness(6.5f), 10).requiresCorrectToolForDrops()),
                    (rock, self) -> new Block(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.DEEPSLATE).strength(rock.category().hardness(6.5f), 10).requiresCorrectToolForDrops()), true),
        COBBLED_BRICKS((rock, self) -> new MossGrowingRotatedPillarBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.DEEPSLATE_BRICKS).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops(), TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(self.mossy())),
                        (rock, self) -> new MossGrowingRotatedPillarBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.DEEPSLATE_BRICKS).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops(), TFCFBlocks.ROCK_BLOCKS.get(rock).get(self.mossy())), true),
        POLISHED_COBBLED_BRICKS((rock, self) -> new Block(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.ANCIENT_DEBRIS).strength(rock.category().hardness(7f), 10).requiresCorrectToolForDrops()),
                                (rock, self) -> new Block(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.ANCIENT_DEBRIS).strength(rock.category().hardness(7f), 10).requiresCorrectToolForDrops()), true),
        FLAGSTONE_BRICKS((rock, self) -> new MossGrowingBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.DEEPSLATE_TILES).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops(), TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(self.mossy())),
                        (rock, self) -> new MossGrowingBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.DEEPSLATE_TILES).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops(), TFCFBlocks.ROCK_BLOCKS.get(rock).get(self.mossy())), true),
        MOSSY_COBBLED_BRICKS((rock, self) -> new MossSpreadingRotatedPillarBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.DEEPSLATE_BRICKS).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops()),
                            (rock, self) -> new MossSpreadingRotatedPillarBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.DEEPSLATE_BRICKS).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops()), true),
        MOSSY_FLAGSTONE_BRICKS((rock, self) -> new MossSpreadingBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.DEEPSLATE_TILES).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops()),
                                (rock, self) -> new MossSpreadingBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.DEEPSLATE_TILES).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops()), true),
        CRACKED_FLAGSTONE_BRICKS((rock, self) -> new Block(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.DEEPSLATE_TILES).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops()),
                                (rock, self) -> new Block(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.DEEPSLATE_TILES).strength(rock.category().hardness(9f), 10).requiresCorrectToolForDrops()), true),
        ROCK_PILE((rock, self) -> new MossGrowingBoulderBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.BASALT).strength(rock.category().hardness(1.5f), 10).requiresCorrectToolForDrops().randomTicks().speedFactor(0.8F).noCollission().hasPostProcess(TFCFBlocks::always).noOcclusion().dynamicShape(), TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(self.mossy())),
                (rock, self) -> new MossGrowingBoulderBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.BASALT).strength(rock.category().hardness(1.5f), 10).requiresCorrectToolForDrops().randomTicks().speedFactor(0.8F).noCollission().hasPostProcess(TFCFBlocks::always).noOcclusion().dynamicShape(), TFCFBlocks.ROCK_BLOCKS.get(rock).get(self.mossy())), false),
        MOSSY_ROCK_PILE((rock, self) -> new MossSpreadingBoulderBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.BASALT).strength(rock.category().hardness(1.5f), 10).requiresCorrectToolForDrops().randomTicks().speedFactor(0.8F).noCollission().hasPostProcess(TFCFBlocks::always).noOcclusion().dynamicShape()),
                        (rock, self) -> new MossSpreadingBoulderBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.BASALT).strength(rock.category().hardness(1.5f), 10).requiresCorrectToolForDrops().randomTicks().speedFactor(0.8F).noCollission().hasPostProcess(TFCFBlocks::always).noOcclusion().dynamicShape()), false);

        public static final TFCFBlockType[] VALUES = TFCFBlockType.values();

        public static TFCFBlockType valueOf(int i)
        {
            return i >= 0 && i < VALUES.length ? VALUES[i] : STONE_TILES;
        }

        private final boolean variants;
        private final BiFunction<RegistryRock, TFCFBlockType, Block> TFCFactory;
        private final BiFunction<TFCFRegistryRock, TFCFBlockType, Block> TFCFFactory;
        private final String serializedName;

        TFCFBlockType(BiFunction<TFCFRegistryRock, TFCFBlockType, Block> TFCFFactory, BiFunction<RegistryRock, TFCFBlockType, Block> TFCFactory, boolean variants)
        {
            this.TFCFFactory = TFCFFactory;
            this.TFCFactory = TFCFactory;
            this.variants = variants;
            this.serializedName = name().toLowerCase(Locale.ROOT);
        }

        TFCFBlockType(BiFunction<TFCFRegistryRock, TFCFBlockType, Block> TFCFFactory, boolean variants)
        {
            this.TFCFFactory = TFCFFactory;
            this.TFCFactory = null;
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

        public Block createTFC(RegistryRock rock)
        {
            return TFCFactory.apply(rock, this);
        }

        public Block createTFCF(TFCFRegistryRock rock)
        {
            return TFCFFactory.apply(rock, this);
        }

        public BiFunction<RegistryRock, TFCFBlockType, Block> getTFCFactory()
        {
            return TFCFactory;
        }

        public BiFunction<TFCFRegistryRock, TFCFBlockType, Block> getTFCFFactory()
        {
            return TFCFFactory;
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
                    case ROCK_PILE, MOSSY_ROCK_PILE -> MOSSY_ROCK_PILE;
                    default -> null;
                };
        }
    }

    public static void registerDefaultRocks()
    {
        for (TFCFRock rock : TFCFRock.values())
        {
            final ResourceLocation id = Helpers.identifier(rock.getSerializedName());
            final RockCategory category = rock.category();
            final Map<Rock.BlockType, RegistryObject<Block>> blocks = TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock);

            RockSettings.register(new RockSettings(
                id,
                blocks.get(Rock.BlockType.RAW).get(),
                blocks.get(Rock.BlockType.HARDENED).get(),
                blocks.get(Rock.BlockType.GRAVEL).get(),
                blocks.get(Rock.BlockType.COBBLE).get(),
                TFCBlocks.SAND.get(rock.getSandType()).get(),
                TFCBlocks.SANDSTONE.get(rock.getSandType()).get(SandstoneBlockType.RAW).get(),
                Optional.of(blocks.get(Rock.BlockType.SPIKE).get()),
                Optional.of(blocks.get(Rock.BlockType.LOOSE).get()),
                category != RockCategory.IGNEOUS_INTRUSIVE,
                true,
                category == RockCategory.IGNEOUS_INTRUSIVE || category == RockCategory.METAMORPHIC
            ));
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