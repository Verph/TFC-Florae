package tfcflorae.common.blocks.soil;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.*;
import net.dries007.tfc.util.registry.RegistryRock;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.blocks.soil.TFCFSoil.TFCFVariant;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.util.TriFunction;

public enum TFCFRockSoil
{
<<<<<<< Updated upstream
    PEBBLE_COMPACT_DIRT((self, variant, rock) -> new Block(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.8F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops()),
                        (self, variant, rock) -> new Block(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.8F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops())),
    ROCKY_COMPACT_DIRT((self, variant, rock) -> new Block(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.85F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops()),
                        (self, variant, rock) -> new Block(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.85F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops())),
    ROCKIER_COMPACT_DIRT((self, variant, rock) -> new Block(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.9F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops()),
                        (self, variant, rock) -> new Block(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.9F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops())),
    ROCKIEST_COMPACT_DIRT((self, variant, rock) -> new Block(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(1F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops()),
                        (self, variant, rock) -> new Block(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(1F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops())),

    DIRTIEST_STONE_TILES((self, variant, rock) -> new Block(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.25f, 8).sound(SoundType.GRAVEL).requiresCorrectToolForDrops()),
                        (self, variant, rock) -> new Block(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.25f, 8).sound(SoundType.GRAVEL).requiresCorrectToolForDrops())),
    DIRTIER_STONE_TILES((self, variant, rock) -> new Block(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.35f, 9).sound(SoundType.STONE).requiresCorrectToolForDrops()),
                        (self, variant, rock) -> new Block(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.35f, 9).sound(SoundType.STONE).requiresCorrectToolForDrops())),
    DIRTY_STONE_TILES((self, variant, rock) -> new Block(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.5f, 10).sound(SoundType.STONE).requiresCorrectToolForDrops()),
                        (self, variant, rock) -> new Block(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.5f, 10).sound(SoundType.STONE).requiresCorrectToolForDrops()));
=======
    PEBBLE_COMPACT_DIRT((self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.8F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops(), TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCFROCKSOIL.get(self.transform()).get(variant).get(rock), TFCFBlocks.TFCFSOIL.get(TFCFSoil.DIRT).get(variant), null, false),
                        (self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.8F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops(), TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCROCKSOIL.get(self.transform()).get(variant).get(rock), TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant), null, false),
                        (self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.8F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops(), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCFROCKSOIL2.get(self.transform()).get(variant).get(rock), TFCFBlocks.TFCFSOIL.get(TFCFSoil.DIRT).get(variant), null, false),
                        (self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.8F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops(), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCROCKSOIL2.get(self.transform()).get(variant).get(rock), TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant), null, false)),
    ROCKY_COMPACT_DIRT((self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.85F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops(), TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCFROCKSOIL.get(self.transform()).get(variant).get(rock), TFCFBlocks.TFCFROCKSOIL.get(PEBBLE_COMPACT_DIRT).get(variant).get(rock), null, false),
                        (self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.85F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops(), TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCROCKSOIL.get(self.transform()).get(variant).get(rock), TFCFBlocks.TFCROCKSOIL.get(PEBBLE_COMPACT_DIRT).get(variant).get(rock), null, false),
                        (self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.85F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops(), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCFROCKSOIL2.get(self.transform()).get(variant).get(rock), TFCFBlocks.TFCFROCKSOIL2.get(PEBBLE_COMPACT_DIRT).get(variant).get(rock), null, false),
                        (self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.85F).sound(SoundType.GRAVEL).requiresCorrectToolForDrops(), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCROCKSOIL2.get(self.transform()).get(variant).get(rock), TFCFBlocks.TFCROCKSOIL2.get(PEBBLE_COMPACT_DIRT).get(variant).get(rock), null, false)),
    ROCKIER_COMPACT_DIRT((self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.9F).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCFROCKSOIL.get(self.transform()).get(variant).get(rock), TFCFBlocks.TFCFROCKSOIL.get(ROCKY_COMPACT_DIRT).get(variant).get(rock), null, false),
                        (self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.9F).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCROCKSOIL.get(self.transform()).get(variant).get(rock), TFCFBlocks.TFCROCKSOIL.get(ROCKY_COMPACT_DIRT).get(variant).get(rock), null, false),
                        (self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.9F).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCFROCKSOIL2.get(self.transform()).get(variant).get(rock), TFCFBlocks.TFCFROCKSOIL2.get(ROCKY_COMPACT_DIRT).get(variant).get(rock), null, false),
                        (self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.9F).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCROCKSOIL2.get(self.transform()).get(variant).get(rock), TFCFBlocks.TFCROCKSOIL2.get(ROCKY_COMPACT_DIRT).get(variant).get(rock), null, false)),
    ROCKIEST_COMPACT_DIRT((self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(1F).sound(SoundType.BASALT).requiresCorrectToolForDrops(), TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE), TFCFBlocks.TFCFROCKSOIL.get(ROCKIER_COMPACT_DIRT).get(variant).get(rock), TFCFBlocks.TFCFSOIL.get(TFCFSoil.DIRT).get(variant), true),
                        (self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(1F).sound(SoundType.BASALT).requiresCorrectToolForDrops(), TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE), TFCFBlocks.TFCROCKSOIL.get(ROCKIER_COMPACT_DIRT).get(variant).get(rock), TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant), true),
                        (self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(1F).sound(SoundType.BASALT).requiresCorrectToolForDrops(), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE), TFCFBlocks.TFCFROCKSOIL2.get(ROCKIER_COMPACT_DIRT).get(variant).get(rock), TFCFBlocks.TFCFSOIL.get(TFCFSoil.DIRT).get(variant), true),
                        (self, variant, rock) -> new RockySoilBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(1F).sound(SoundType.BASALT).requiresCorrectToolForDrops(), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE), TFCFBlocks.TFCROCKSOIL2.get(ROCKIER_COMPACT_DIRT).get(variant).get(rock), TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant), true)),

    DIRTIEST_STONE_TILES((self, variant, rock) -> new TFCFRockySoilBlock(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.25f, 8).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFItems.SOIL_PILE_TFCF.get(variant), TFCFBlocks.TFCFSOIL.get(TFCFSoil.DIRT).get(variant), null, TFCFBlocks.ROCK_BLOCKS.get(rock).get(TFCFRock.TFCFBlockType.STONE_TILES), true),
                        (self, variant, rock) -> new TFCFRockySoilBlock(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.25f, 8).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFItems.SOIL_PILE_TFC.get(variant), TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant), null, TFCFBlocks.ROCK_BLOCKS.get(rock).get(TFCFRock.TFCFBlockType.STONE_TILES), true),
                        (self, variant, rock) -> new TFCFRockySoilBlock(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.25f, 8).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFItems.SOIL_PILE_TFCF.get(variant), TFCFBlocks.TFCFSOIL.get(TFCFSoil.DIRT).get(variant), null, TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(TFCFRock.TFCFBlockType.STONE_TILES), true),
                        (self, variant, rock) -> new TFCFRockySoilBlock(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.25f, 8).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFItems.SOIL_PILE_TFC.get(variant), TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant), null, TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rock).get(TFCFRock.TFCFBlockType.STONE_TILES), true)),
    DIRTIER_STONE_TILES((self, variant, rock) -> new TFCFRockySoilBlock(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.35f, 9).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFItems.SOIL_PILE_TFCF.get(variant), TFCFBlocks.TFCFROCKSOIL.get(self.transform()).get(variant).get(rock), null, null, false),
                        (self, variant, rock) -> new TFCFRockySoilBlock(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.35f, 9).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFItems.SOIL_PILE_TFC.get(variant), TFCFBlocks.TFCROCKSOIL.get(self.transform()).get(variant).get(rock), null, null, false),
                        (self, variant, rock) -> new TFCFRockySoilBlock(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.35f, 9).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFItems.SOIL_PILE_TFCF.get(variant), TFCFBlocks.TFCFROCKSOIL2.get(self.transform()).get(variant).get(rock), null, null, false),
                        (self, variant, rock) -> new TFCFRockySoilBlock(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.35f, 9).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFItems.SOIL_PILE_TFC.get(variant), TFCFBlocks.TFCROCKSOIL2.get(self.transform()).get(variant).get(rock), null, null, false)),
    DIRTY_STONE_TILES((self, variant, rock) -> new TFCFRockySoilBlock(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.5f, 10).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFItems.SOIL_PILE_TFCF.get(variant), TFCFBlocks.TFCFROCKSOIL.get(self.transform()).get(variant).get(rock), null, null, false),
                        (self, variant, rock) -> new TFCFRockySoilBlock(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.5f, 10).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFItems.SOIL_PILE_TFC.get(variant), TFCFBlocks.TFCROCKSOIL.get(self.transform()).get(variant).get(rock), null, null, false),
                        (self, variant, rock) -> new TFCFRockySoilBlock(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.5f, 10).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFItems.SOIL_PILE_TFCF.get(variant), TFCFBlocks.TFCFROCKSOIL2.get(self.transform()).get(variant).get(rock), null, null, false),
                        (self, variant, rock) -> new TFCFRockySoilBlock(Block.Properties.of(Material.STONE, MaterialColor.DIRT).strength(1.5f, 10).sound(SoundType.TUFF).requiresCorrectToolForDrops(), TFCFItems.SOIL_PILE_TFC.get(variant), TFCFBlocks.TFCROCKSOIL2.get(self.transform()).get(variant).get(rock), null, null, false));
>>>>>>> Stashed changes

    public static final TFCFRockSoil[] VALUES = values();

    public static TFCFRockSoil valueOf(int i)
    {
        return i >= 0 && i < VALUES.length ? VALUES[i] : PEBBLE_COMPACT_DIRT;
    }

    private final TriFunction<TFCFRockSoil, TFCFVariant, RegistryRock, Block> TFCFFactory;
    private final TriFunction<TFCFRockSoil, SoilBlockType.Variant, RegistryRock, Block> TFCFactory;

    TFCFRockSoil(TriFunction<TFCFRockSoil, TFCFVariant, RegistryRock, Block> TFCFFactory, TriFunction<TFCFRockSoil, SoilBlockType.Variant, RegistryRock, Block> TFCFactory)
    {
        this.TFCFFactory = TFCFFactory;
        this.TFCFactory = TFCFactory;
    }

    TFCFRockSoil(TriFunction<TFCFRockSoil, TFCFVariant, RegistryRock, Block> TFCFFactory)
    {
        this.TFCFFactory = TFCFFactory;
        this.TFCFactory = null;
    }

    public Block TFCFCreate(TFCFVariant variant, RegistryRock rock)
    {
        return TFCFFactory.apply(this, variant, rock);
    }

    public Block TFCCreate(SoilBlockType.Variant variant, RegistryRock rock)
    {
        return TFCFactory.apply(this, variant, rock);
    }

    public TriFunction<TFCFRockSoil, SoilBlockType.Variant, RegistryRock, Block> getTFCFactory()
    {
        return TFCFactory;
    }

    public TriFunction<TFCFRockSoil, TFCFVariant, RegistryRock, Block> getTFCFFactory()
    {
        return TFCFFactory;
    }

    /**
     * Gets the transformed state between grass and dirt variants. Used to subvert shitty compiler illegal forward reference errors.
     */
    public TFCFRockSoil transform()
    {
        switch (this)
        {
            case DIRTY_STONE_TILES:
                return DIRTIER_STONE_TILES;
            case DIRTIER_STONE_TILES:
                return DIRTIEST_STONE_TILES;
            case PEBBLE_COMPACT_DIRT:
                return ROCKY_COMPACT_DIRT;
            case ROCKY_COMPACT_DIRT:
                return ROCKIER_COMPACT_DIRT;
            case ROCKIER_COMPACT_DIRT:
                return ROCKIEST_COMPACT_DIRT;
            case DIRTIEST_STONE_TILES:
                return ROCKY_COMPACT_DIRT;
            default:
                return this;
        }
    }
}
