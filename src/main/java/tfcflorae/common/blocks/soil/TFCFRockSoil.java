package tfcflorae.common.blocks.soil;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.dries007.tfc.common.blocks.soil.*;
import net.dries007.tfc.util.registry.RegistryRock;

import tfcflorae.common.blocks.soil.TFCFSoil.TFCFVariant;
import tfcflorae.util.TriFunction;

public enum TFCFRockSoil
{
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
            default:
                return this;
        }
    }
}
