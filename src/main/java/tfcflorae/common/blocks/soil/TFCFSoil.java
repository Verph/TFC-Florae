package tfcflorae.common.blocks.soil;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.devices.DryingBricksBlock;
import net.dries007.tfc.common.blocks.soil.*;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.items.TFCFItems;

public enum TFCFSoil
{
    //Only TFCFVariants
    GRASS_PATH((self, variant) -> new PathBlock(Block.Properties.of(Material.DIRT).strength(0.65F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant))),
    DIRT((self, variant) -> new DirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant))),
    FARMLAND((self, variant) -> new FarmlandBlock(ExtendedProperties.of(BlockBehaviour.Properties.of(Material.DIRT).strength(0.6f).sound(SoundType.GRAVEL).isViewBlocking(TFCBlocks::always).isSuffocating(TFCBlocks::always)).blockEntity(TFCBlockEntities.FARMLAND), TFCFBlocks.TFCFSOIL.get(DIRT).get(variant))),
    GRASS((self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(FARMLAND).get(variant))),
    CLAY((self, variant) -> new DirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant))),
    CLAY_GRASS((self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(FARMLAND).get(variant))),
    ROOTED_DIRT((self, variant) -> new TFCRootedDirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.ROOTED_DIRT), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant))),
    MUD((self, variant) -> new MudBlock(mudProperties())),
    MUD_BRICKS((self, variant) -> new Block(mudProperties())),
    DRYING_BRICKS((self, variant) -> new DryingBricksBlock(ExtendedProperties.of(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().noOcclusion().instabreak().sound(SoundType.STEM).randomTicks()).blockEntity(TFCBlockEntities.TICK_COUNTER), variant.dried())),

    //Both TFCFVariants and TFCVariants of soil types
    COARSE_DIRT((self, variant) -> new TFCRootedDirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)), 
                (self, variant) -> new TFCRootedDirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant))),
    PODZOL((self, variant) -> new PodzolBlock(Block.Properties.of(Material.DIRT, MaterialColor.PODZOL).randomTicks().strength(0.5F).sound(SoundType.GRAVEL), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(FARMLAND).get(variant)),
           (self, variant) -> new PodzolBlock(Block.Properties.of(Material.DIRT, MaterialColor.PODZOL).randomTicks().strength(0.5F).sound(SoundType.GRAVEL), self.findSoil(self.transform(), SoilBlockType.DIRT, variant), TFCBlocks.SOIL.get(SoilBlockType.GRASS_PATH).get(variant), TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(variant))),
    DRY_GRASS_PATH((self, variant) -> new PathBlock(Block.Properties.of(Material.DIRT).strength(0.65F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                   (self, variant) -> new PathBlock(Block.Properties.of(Material.DIRT).strength(0.65F).sound(SoundType.GRASS), TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant))),
    DRY_GRASS((self, variant) -> new TFCFConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(DRY_GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(FARMLAND).get(variant)),
              (self, variant) -> new TFCFConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant), TFCFBlocks.TFCSOIL.get(DRY_GRASS_PATH).get(variant), TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant))),
    SPARSE_GRASS_PATH((self, variant) -> new PathBlock(Block.Properties.of(Material.DIRT).strength(0.65F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                      (self, variant) -> new PathBlock(Block.Properties.of(Material.DIRT).strength(0.65F).sound(SoundType.GRASS), TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant))),
    SPARSE_GRASS((self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(SPARSE_GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(FARMLAND).get(variant)),
                 (self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant), TFCFBlocks.TFCSOIL.get(SPARSE_GRASS_PATH).get(variant), TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant))),

    EARTHENWARE_CLAY((self, variant) -> new DirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                     (self, variant) -> new DirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant), TFCBlocks.SOIL.get(SoilBlockType.GRASS_PATH).get(variant), TFCBlocks.SOIL.get(SoilBlockType.FARMLAND).get(variant))),
    EARTHENWARE_CLAY_GRASS((self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                           (self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant), TFCBlocks.SOIL.get(SoilBlockType.GRASS_PATH).get(variant), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant))),
    DRY_EARTHENWARE_CLAY_GRASS((self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(DRY_GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                           (self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCSOIL.get(DRY_GRASS_PATH).get(variant), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant))),
    SPARSE_EARTHENWARE_CLAY_GRASS((self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(SPARSE_GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                           (self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCSOIL.get(SPARSE_GRASS_PATH).get(variant), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant))),

    KAOLINITE_CLAY((self, variant) -> new DirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                     (self, variant) -> new DirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant), TFCBlocks.SOIL.get(SoilBlockType.GRASS_PATH).get(variant), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant))),
    KAOLINITE_CLAY_GRASS((self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                         (self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant), TFCBlocks.SOIL.get(SoilBlockType.GRASS_PATH).get(variant), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant))),
    DRY_KAOLINITE_CLAY_GRASS((self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(DRY_GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                           (self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCSOIL.get(DRY_GRASS_PATH).get(variant), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant))),
    SPARSE_KAOLINITE_CLAY_GRASS((self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(SPARSE_GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                           (self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCSOIL.get(SPARSE_GRASS_PATH).get(variant), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant))),

    STONEWARE_CLAY((self, variant) -> new DirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                   (self, variant) -> new DirtBlock(Block.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant), TFCBlocks.SOIL.get(SoilBlockType.GRASS_PATH).get(variant), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant))),
    STONEWARE_CLAY_GRASS((self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                         (self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant), TFCBlocks.SOIL.get(SoilBlockType.GRASS_PATH).get(variant), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant))),
    DRY_STONEWARE_CLAY_GRASS((self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(DRY_GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                           (self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCSOIL.get(DRY_GRASS_PATH).get(variant), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant))),
    SPARSE_STONEWARE_CLAY_GRASS((self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCFSOIL.get(SPARSE_GRASS_PATH).get(variant), TFCFBlocks.TFCFSOIL.get(self.transform()).get(variant)),
                           (self, variant) -> new ConnectedGrassBlock(Block.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant), TFCFBlocks.TFCSOIL.get(SPARSE_GRASS_PATH).get(variant), TFCFBlocks.TFCSOIL.get(self.transform()).get(variant)));

    public static final TFCFSoil[] VALUES = values();

    public static TFCFSoil valueOf(int i)
    {
        return i >= 0 && i < VALUES.length ? VALUES[i] : DRY_GRASS;
    }

    public static BlockBehaviour.Properties mudProperties()
    {
        // todo: 1.19. correct sound
        return BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.DIRT).strength(1.0f).sound(SoundType.SHROOMLIGHT);
    }

    public final BiFunction<TFCFSoil, TFCFVariant, Block> TFCFFactory;
    public final BiFunction<TFCFSoil, SoilBlockType.Variant, Block> TFCFactory;

    TFCFSoil(BiFunction<TFCFSoil, TFCFVariant, Block> TFCFFactory, BiFunction<TFCFSoil, SoilBlockType.Variant, Block> TFCFactory)
    {
        this.TFCFFactory = TFCFFactory;
        this.TFCFactory = TFCFactory;
    }

    TFCFSoil(BiFunction<TFCFSoil, TFCFVariant, Block> TFCFFactory)
    {
        this.TFCFFactory = TFCFFactory;
        this.TFCFactory = null;
    }

    public Block TFCFCreate(TFCFVariant variant)
    {
        return TFCFFactory.apply(this, variant);
    }

    public Block TFCCreate(SoilBlockType.Variant variant)
    {
        return TFCFactory.apply(this, variant);
    }

    private Supplier<Block> findSoil(TFCFSoil soil, SoilBlockType altSoil, SoilBlockType.Variant variant)
    {
        if (TFCFBlocks.TFCSOIL.get(soil) != null)
            return TFCFBlocks.TFCSOIL.get(soil).get(variant);
        else
            return TFCBlocks.SOIL.get(altSoil).get(variant);
    }

    /**
     * Gets the transformed state between grass and dirt variants. Used to subvert shitty compiler illegal forward reference errors.
     */
    public TFCFSoil transform()
    {
        switch (this)
        {
            case DIRT:
                return GRASS;
            case GRASS:
            case GRASS_PATH:
            case FARMLAND:
            case ROOTED_DIRT:
            case MUD:
            case MUD_BRICKS:
            case DRYING_BRICKS:
                return DIRT;
            case CLAY:
                return CLAY_GRASS;
            case CLAY_GRASS:
                return CLAY;
            case EARTHENWARE_CLAY:
                return EARTHENWARE_CLAY_GRASS;
            case EARTHENWARE_CLAY_GRASS:
            case DRY_EARTHENWARE_CLAY_GRASS:
            case SPARSE_EARTHENWARE_CLAY_GRASS:
                return EARTHENWARE_CLAY;
            case KAOLINITE_CLAY:
                return KAOLINITE_CLAY_GRASS;
            case KAOLINITE_CLAY_GRASS:
            case DRY_KAOLINITE_CLAY_GRASS:
            case SPARSE_KAOLINITE_CLAY_GRASS:
                return KAOLINITE_CLAY;
            case STONEWARE_CLAY:
                return STONEWARE_CLAY_GRASS;
            case STONEWARE_CLAY_GRASS:
            case DRY_STONEWARE_CLAY_GRASS:
            case SPARSE_STONEWARE_CLAY_GRASS:
                return STONEWARE_CLAY;
            case PODZOL:
            case DRY_GRASS:
            case DRY_GRASS_PATH:
            case SPARSE_GRASS:
            case SPARSE_GRASS_PATH:
                return DIRT;
            default:
                return this;
        }
    }

    public enum TFCFVariant
    {
        HUMUS;

        public static final TFCFVariant[] VALUES = values();

        public static TFCFVariant valueOf(int i)
        {
            return i >= 0 && i < VALUES.length ? VALUES[i] : HUMUS;
        }

        public Supplier<? extends Item> dried()
        {
            return switch (this)
            {
                case HUMUS -> TFCFItems.HUMUS_MUD_BRICK;
                default -> null;
            };
        }
    }
}
