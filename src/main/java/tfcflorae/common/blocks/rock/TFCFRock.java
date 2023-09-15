package tfcflorae.common.blocks.rock;

import java.util.Locale;
import java.util.Map;
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

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.SandstoneBlockType;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.*;
import net.dries007.tfc.common.blocks.rock.Rock.BlockType;
import net.dries007.tfc.common.blocks.soil.*;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryRock;
import net.dries007.tfc.world.settings.RockSettings;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.soil.Colors;
import tfcflorae.common.blocks.soil.SandLayerBlock;
import tfcflorae.util.registry.TFCFRegistryRock;

public enum TFCFRock implements TFCFRegistryRock
{
    RED_GRANITE(RockCategory.IGNEOUS_INTRUSIVE, MaterialColor.TERRACOTTA_RED, "RED"),
    BRECCIA(RockCategory.IGNEOUS_INTRUSIVE, MaterialColor.TERRACOTTA_WHITE, "WHITE"),
    FOIDOLITE(RockCategory.IGNEOUS_INTRUSIVE, MaterialColor.TERRACOTTA_BLUE, "BLUE"),
    PORPHYRY(RockCategory.IGNEOUS_EXTRUSIVE, MaterialColor.TERRACOTTA_RED, "RED"),
    PERIDOTITE(RockCategory.IGNEOUS_EXTRUSIVE, MaterialColor.TERRACOTTA_CYAN, "CYAN"),
    BLAIMORITE(RockCategory.IGNEOUS_EXTRUSIVE, MaterialColor.TERRACOTTA_LIGHT_GREEN, "LIGHT_GREEN"),
    BONINITE(RockCategory.IGNEOUS_EXTRUSIVE, MaterialColor.TERRACOTTA_GRAY, "GRAY"),
    CARBONATITE(RockCategory.IGNEOUS_EXTRUSIVE, MaterialColor.TERRACOTTA_YELLOW, "YELLOW"),
    LATERITE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_ORANGE, "ORANGE"),
    MUDSTONE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_LIGHT_GRAY, "LIGHT_GRAY"),
    SANDSTONE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_YELLOW, "YELLOW"),
    SILTSTONE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_LIGHT_GRAY, "LIGHT_GRAY"),
    ARKOSE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_BROWN, "BROWN"),
    JASPILLITE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_RED, "RED"),
    TRAVERTINE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_WHITE, "WHITE"),
    WACKESTONE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_BLACK, "BLACK"),
    BLACKBAND_IRONSTONE(RockCategory.SEDIMENTARY, MaterialColor.TERRACOTTA_PURPLE, "PURPLE"),
    CATACLASITE(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_GRAY, "GRAY"),
    BLUESCHIST(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_LIGHT_BLUE, "LIGHT_BLUE"),
    CATLINITE(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_PINK, "PINK"),
    GREENSCHIST(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_GREEN, "GREEN"),
    NOVACULITE(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_WHITE, "WHITE"),
    SOAPSTONE(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_CYAN, "CYAN"),
    KOMATIITE(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_LIGHT_BLUE, "LIGHT_BLUE"),
    MYLONITE(RockCategory.METAMORPHIC, MaterialColor.TERRACOTTA_LIGHT_GRAY, "LIGHT_GRAY");

    public static final TFCFRock[] VALUES = values();

    public final String serializedName;
    public final RockCategory category;
    public final MaterialColor color;
    public final String sandType;

    TFCFRock(RockCategory category, MaterialColor color, String sandType)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.category = category;
        this.color = color;
        this.sandType = sandType.toUpperCase(Locale.ROOT);
    }

    public Supplier<? extends Block> getSandType()
    {
        switch (sandType)
        {
            case "BLACK":
                return TFCBlocks.SAND.get(SandBlockType.BLACK);
            case "BLUE":
                return TFCFBlocks.SAND.get(Colors.BLUE);
            case "BROWN":
                return TFCBlocks.SAND.get(SandBlockType.BROWN);
            case "CYAN":
                return TFCFBlocks.SAND.get(Colors.CYAN);
            case "GRAY":
                return TFCFBlocks.SAND.get(Colors.GRAY);
            case "GREEN":
                return TFCBlocks.SAND.get(SandBlockType.GREEN);
            case "LIGHT_BLUE":
                return TFCFBlocks.SAND.get(Colors.LIGHT_BLUE);
            case "LIGHT_GRAY":
                return TFCFBlocks.SAND.get(Colors.LIGHT_GRAY);
            case "LIGHT_GREEN":
                return TFCFBlocks.SAND.get(Colors.LIGHT_GREEN);
            case "MAGENTA":
                return TFCFBlocks.SAND.get(Colors.MAGENTA);
            case "ORANGE":
                return TFCFBlocks.SAND.get(Colors.ORANGE);
            case "PINK":
                return TFCBlocks.SAND.get(SandBlockType.PINK);
            case "PURPLE":
                return TFCFBlocks.SAND.get(Colors.PURPLE);
            case "RED":
                return TFCBlocks.SAND.get(SandBlockType.RED);
            case "WHITE":
                return TFCBlocks.SAND.get(SandBlockType.WHITE);
            case "YELLOW":
                return TFCBlocks.SAND.get(SandBlockType.YELLOW);
            default:
                return TFCBlocks.SAND.get(SandBlockType.YELLOW);
        }
    }

    public Supplier<? extends Block> getSandstone()
    {
        switch (sandType)
        {
            case "BLACK":
                return TFCBlocks.SANDSTONE.get(SandBlockType.BLACK).get(SandstoneBlockType.RAW);
            case "BLUE":
                return TFCBlocks.SANDSTONE.get(SandBlockType.WHITE).get(SandstoneBlockType.RAW);
            case "BROWN":
                return TFCBlocks.SANDSTONE.get(SandBlockType.BROWN).get(SandstoneBlockType.RAW);
            case "CYAN":
                return TFCBlocks.SANDSTONE.get(SandBlockType.GREEN).get(SandstoneBlockType.RAW);
            case "GRAY":
                return TFCBlocks.SANDSTONE.get(SandBlockType.BLACK).get(SandstoneBlockType.RAW);
            case "GREEN":
                return TFCBlocks.SANDSTONE.get(SandBlockType.GREEN).get(SandstoneBlockType.RAW);
            case "LIGHT_BLUE":
                return TFCBlocks.SANDSTONE.get(SandBlockType.WHITE).get(SandstoneBlockType.RAW);
            case "LIGHT_GRAY":
                return TFCBlocks.SANDSTONE.get(SandBlockType.WHITE).get(SandstoneBlockType.RAW);
            case "LIGHT_GREEN":
                return TFCBlocks.SANDSTONE.get(SandBlockType.GREEN).get(SandstoneBlockType.RAW);
            case "MAGENTA":
                return TFCBlocks.SANDSTONE.get(SandBlockType.PINK).get(SandstoneBlockType.RAW);
            case "ORANGE":
                return TFCBlocks.SANDSTONE.get(SandBlockType.RED).get(SandstoneBlockType.RAW);
            case "PINK":
                return TFCBlocks.SANDSTONE.get(SandBlockType.PINK).get(SandstoneBlockType.RAW);
            case "PURPLE":
                return TFCBlocks.SANDSTONE.get(SandBlockType.PINK).get(SandstoneBlockType.RAW);
            case "RED":
                return TFCBlocks.SANDSTONE.get(SandBlockType.RED).get(SandstoneBlockType.RAW);
            case "WHITE":
                return TFCBlocks.SANDSTONE.get(SandBlockType.WHITE).get(SandstoneBlockType.RAW);
            case "YELLOW":
                return TFCBlocks.SANDSTONE.get(SandBlockType.YELLOW).get(SandstoneBlockType.RAW);
            default:
                return TFCBlocks.SANDSTONE.get(SandBlockType.YELLOW).get(SandstoneBlockType.RAW);
        }
    }

    public boolean isSandTFC(String sandType)
    {
        switch (sandType)
        {
            case "BLUE":
            case "CYAN":
            case "GRAY":
            case "LIGHT_BLUE":
            case "LIGHT_GRAY":
            case "LIGHT_GREEN":
            case "MAGENTA":
            case "ORANGE":
            case "PURPLE":
                return false;
            case "BLACK":
            case "BROWN":
            case "GREEN":
            case "PINK":
            case "RED":
            case "WHITE":
            case "YELLOW":
                return true;
            default:
                return true;
        }
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
        return TFCFBlocks.ROCK_ANVILS.get(this);
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
                        (rock, self) -> new MossSpreadingBoulderBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.BASALT).strength(rock.category().hardness(1.5f), 10).requiresCorrectToolForDrops().randomTicks().speedFactor(0.8F).noCollission().hasPostProcess(TFCFBlocks::always).noOcclusion().dynamicShape()), false),
        MORTAR_AND_COBBLE((rock, self) -> new MossGrowingBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.STONE).strength(rock.category().hardness(5.5f), 10).requiresCorrectToolForDrops(), TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(self.mossy())),
                        (rock, self) -> new MossGrowingBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.STONE).strength(rock.category().hardness(5.5f), 10).requiresCorrectToolForDrops(), TFCFBlocks.ROCK_BLOCKS.get(rock).get(self.mossy())), true),
        MOSSY_MORTAR_AND_COBBLE((rock, self) -> new MossSpreadingBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.STONE).strength(rock.category().hardness(5.5f), 10).requiresCorrectToolForDrops()),
                                (rock, self) -> new MossSpreadingBlock(Block.Properties.of(Material.STONE, rock.color()).sound(SoundType.STONE).strength(rock.category().hardness(5.5f), 10).requiresCorrectToolForDrops()), true),
        GRAVEL_LAYER((rock, self) -> new SandLayerBlock(rock.color().col, ExtendedProperties.of(Material.SAND, rock.color()).strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.GRAVEL), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL)),
                    (rock, self) -> new SandLayerBlock(rock.color().col, ExtendedProperties.of(Material.SAND, rock.color()).strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.GRAVEL), TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL)), false);

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
                    case MORTAR_AND_COBBLE -> MOSSY_MORTAR_AND_COBBLE;
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
                rock.getSandType().get(),
                rock.getSandstone().get(),
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