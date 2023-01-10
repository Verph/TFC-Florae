package tfcflorae.common.blocks.plant;

import org.jetbrains.annotations.Nullable;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.WaterLilyBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.*;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.registry.RegistryPlant;

import tfcflorae.common.blockentities.*;
import tfcflorae.common.blockentities.TFCFBlockEntities;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.items.TFCFFood;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.util.climate.TFCFClimateRanges;

import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.*;

public enum TFCFPlant implements RegistryPlant
{
    // Short Grasses and old Florae plants
    CAT_GRASS(BlockType.SHORT_GRASS, 0.8F, false),
    CROWNGRASS(BlockType.SHORT_GRASS, 0.8F, false),
    GOOSEGRASS(BlockType.SHORT_GRASS, 0.8F, false),
    HALFA_GRASS(BlockType.DESERT_SHORT_GRASS, 0.8F, false),
    MARRAM_GRASS(BlockType.DESERT_SHORT_GRASS, 0.8F, false),
    PRAIRIE_JUNEGRASS(BlockType.SHORT_GRASS, 0.8F, new int[] {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0}, false),
    WHEATGRASS(BlockType.SHORT_GRASS, 0.8F, false),
    BUNCH_GRASS(BlockType.TALL_WATER_FRESH, 0.8F, false),
    SAWGRASS(BlockType.TALL_WATER_FRESH, 0.8F, false),

    CINNAMON_FERN(BlockType.TFCF_STANDARD, 0.8F, false),
    LEAF_LITTER(BlockType.SHORT_GRASS, 0.8F, false),
    LOW_UNDERGROWTH(BlockType.SHORT_GRASS, 0.8F, false),
    WOOLLY_BUSH(BlockType.TFCF_STANDARD, 0.8F, false),
    BEARDED_MOSS_PLANT(BlockType.WEEPING, 0.9f, false),
    BEARDED_MOSS(BlockType.WEEPING_TOP, 0.9f, false),
    BEARDED_MOSS_CREEPING(BlockType.CREEPING, 0.9f, false),
    MONSTERA(BlockType.TALL_GRASS, 0.5F, false),
    MONSTERA_EPIPHYTE(BlockType.EPIPHYTE, 0.5F, false),
    DECIDUOUS_SHRUB(BlockType.TALL_GRASS, 0.5F, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}, false),
    CONIFEROUS_SHRUB(BlockType.TALL_GRASS, 0.5F, true),

    // Aesthetics
    BIRD_OF_PARADISE(BlockType.TFCF_STANDARD, 0.6F, new int[] {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}, false),
    BLUEBELL(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 2, 2, 2, 2, 1, 0}, false),
    BONATEA(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false),
    CAMAS(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 0, 1, 2, 2, 2, 3, 3, 2, 2, 1, 0}, false),
    CHIVES(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 0, 1, 2, 3, 3, 3, 2, 2, 2, 1, 0}, false),
    CLOVER(BlockType.CREEPING, 0.9F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false),
    COMMON_REEDS(BlockType.TALL_LAND_WATER_FRESH, 0.5F, new int[] {0, 0, 1, 2, 2, 2, 2, 3, 3, 3, 1, 0}, false),
    CREEPING_CHARLIE(BlockType.CREEPING, 0.9F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false),
    CUP_PLANT(BlockType.TALL_GRASS, 0.5F, new int[] {0, 0, 1, 2, 2, 3, 3, 2, 2, 2, 1, 0}, false), // Age-determined size: small, large
    DAFFODIL(BlockType.SHORT_GRASS, 0.8F, new int[] {0, 3, 3, 2, 2, 2, 2, 2, 2, 2, 1, 0}, false),
    DEVILS_TONGUE(BlockType.TFCF_STANDARD, 0.4F, new int[] {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, false),
    DUNE_GRASS(BlockType.DESERT_SHORT_GRASS, 0.8F, new int[] {0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0}, false),
    EGYPTIAN_AUTUMN_CROCUS(BlockType.SHORT_GRASS, 0.8F, new int[] {2, 2, 0, 0, 0, 0, 0, 0, 1, 1, 1, 2}, false),
    ELEPHANT_GRASS(BlockType.TALL_GRASS, 0.5F, false),
    EUROPEAN_BEDSTRAW(BlockType.TFCF_STANDARD, 0.6F, new int[] {0, 0, 1, 2, 2, 2, 3, 3, 3, 2, 1, 0}, false),
    FIREWEED(BlockType.TALL_GRASS, 0.8F, new int[] {0, 0, 1, 2, 2, 3, 3, 3, 3, 4, 1, 0}, false),
    FREESIA(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 3, 3, 3, 2, 2, 2, 2, 2, 1, 0}, false),
    GARLIC_MUSTARD(BlockType.SHORT_GRASS, 0.8F, new int[] {0, 0, 1, 3, 3, 3, 2, 2, 2, 2, 1, 0}, false),
    GIANT_HOGWEED(BlockType.POISONOUS_TALL_GRASS, 0.6F, new int[] {0, 0, 1, 2, 2, 3, 3, 2, 2, 2, 1, 0}, false),
    PINK_HEATHER(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 0, 1, 1, 1, 1, 2, 2, 1, 1, 1, 0}, false),
    WHITE_HEATHER(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 0, 1, 1, 1, 1, 2, 2, 1, 1, 1, 0}, false),
    IRIS(BlockType.TFCF_STANDARD, 0.9F, new int[] {0, 0, 1, 2, 3, 3, 3, 2, 2, 2, 1, 0}, false),
    JACK_IN_THE_PULPIT(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 2, 2, 2, 2, 1, 0}, false),
    LAVENDER(BlockType.TFCF_STANDARD, 0.7F, new int[] {0, 1, 2, 2, 2, 3, 3, 3, 2, 2, 1, 0}, false),
    LEAFY_LOW_UNDERGROWTH(BlockType.SHORT_GRASS, 0.8F, new int[] {0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0}, false),
    LEAFY_UNDERGROWTH(BlockType.TALL_GRASS, 0.6F, new int[] {0, 0, 1, 2, 2, 2, 2, 2, 3, 3, 1, 0}, false), // Age-determined size: small, medium, large
    LEOPARD_ORCHID(BlockType.TFCF_STANDARD, 0.6F, new int[] {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0}, false),
    LEOPARD_ORCHID_EPIPHYTE(BlockType.EPIPHYTE, 0.6F, new int[] {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0}, false),
    LIANA_VINE(BlockType.VINE, 1.0F, false),
    LOBSTER_CLAWS(BlockType.TFCF_STANDARD, 0.6F, new int[] {0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0}, false),
    ORCHID(BlockType.TFCF_STANDARD, 0.9F, new int[] {0, 1, 2, 3, 3, 3, 2, 2, 2, 2, 1, 0}, false),
    BLUE_LUPINE(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false),
    PURPLE_LUPINE(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false),
    RED_LUPINE(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false),
    YELLOW_LUPINE(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false),
    //MAIDENHAIR_FERN_PLANT(BlockType.TWISTING, 0.6F, new int[] {0, 0, 1, 1, 1, 1, 2, 2, 2, 1, 1, 0}, false),
    //MAIDENHAIR_FERN(BlockType.TWISTING_TOP, 0.6F, new int[] {0, 0, 1, 1, 1, 1, 2, 2, 2, 1, 1, 0}, false),
    MALLOW(BlockType.TALL_GRASS, 0.7F, new int[] {0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 1, 0}, false),
    UNDERGROWTH(BlockType.TALL_GRASS, 0.6F, new int[] {0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0}, false), // Age-determined size: small, medium, large
    AMERICAN_MISTLETOE(BlockType.EPIPHYTE, 0.7F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}, false),
    DESERT_MISTLETOE(BlockType.EPIPHYTE, 0.7F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}, false),
    EUROPEAN_MISTLETOE(BlockType.EPIPHYTE, 0.7F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}, false),
    DWARF_MISTLETOE(BlockType.EPIPHYTE, 0.7F, false),
    NARBON_VETCH(BlockType.TFCF_STANDARD, 0.9F, new int[] {0, 0, 1, 2, 3, 3, 3, 2, 2, 2, 1, 0}, false),
    PAPYRUS(BlockType.TALL_LAND_WATER_FRESH, 0.6F, false),
    PERIWINKLE(BlockType.TFCF_STANDARD, 0.9F, new int[] {0, 0, 1, 2, 2, 3, 3, 3, 3, 3, 3, 0}, false),
    POKEWEED(BlockType.FRUITING_TALL_PLANT, 0.5F, new int[] {0, 0, 1, 2, 2, 2, 2, 3, 3, 4, 1, 0}, TFCFItems.FOOD.get(TFCFFood.DRAGONBERRY), new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FRUITING, HEALTHY, DORMANT, DORMANT}, false, true),
    POND_GRASS(BlockType.SHORT_GRASS, 0.6F, new int[] {0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0}, false),
    PRAIRIE_GRASS(BlockType.TALL_GRASS, 0.5F, new int[] {0, 1, 2, 2, 2, 2, 2, 2, 3, 3, 1, 0}, false), // Age-determined size: small, medium, large
    ROUGH_HORSETAIL(BlockType.TFCF_STANDARD, 0.7F, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false),
    ROYAL_JASMINE(BlockType.TALL_GRASS, 0.7F, new int[] {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0}, false), // Age-determined size: small, large
    SALTWORT(BlockType.SHORT_GRASS, 0.7F, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false), // Age-determined size: small, large
    SUNFLOWER(BlockType.TALL_GRASS, 0.5F, new int[] {0, 0, 1, 2, 2, 3, 3, 3, 3, 2, 1, 0}, false),
    TITAN_ARUM(BlockType.TFCF_STANDARD, 0.4F, new int[] {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, false),
    VOODOO_LILY(BlockType.TFCF_STANDARD, 0.4F, new int[] {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, false),
    WATER_HYACINTH(BlockType.FLOATING_FRESH, 0.5F, new int[] {0, 0, 1, 1, 1, 1, 1, 2, 2, 1, 1, 0}, false), // Age-determined size: small, large
    WATER_PLANTAIN(BlockType.TALL_WATER_FRESH, 0.6F, new int[] {0, 1, 2, 3, 3, 3, 3, 3, 3, 2, 1, 0}, false),
    WOOD_BITTER_VETCH(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 0, 1, 2, 3, 3, 3, 2, 2, 2, 1, 0}, false),
    YARROW(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 0, 1, 2, 2, 3, 3, 3, 3, 2, 1, 0}, false),
    SUNDEW(BlockType.TFCF_STANDARD, 0.8F, false),
    THISTLE(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 2, 3, 3, 3, 3, 4, 1, 1, 0}, false),
    VENUS_FLYTRAP(BlockType.TFCF_STANDARD, 0.8F, false),
    //CACAO(BlockType.CACAO, 0F, new int[] {0, 0, 0, 0, 0, 1, 1, 2, 2, 0, 0, 0}, false),

    // Cacti plants or something
    BARREL_CACTUS(BlockType.FRUITING_CACTUS, 0F, new int[] {0, 0, 0, 0, 1, 2, 2, 2, 2, 3, 3, 0}, TFCFItems.FOOD.get(TFCFFood.BARREL_CACTUS_FRUIT), new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT}, false, true),
    AFRICAN_MILK_BARREL(BlockType.SHORT_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0}, false),
    AFRICAN_MILK_TREE(BlockType.TFCF_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0}, false),
    ALBANIAN_SPURGE(BlockType.SHORT_CACTUS, 0.2F, new int[] {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}, false),
    ANGEL_WING_CACTUS(BlockType.SHORT_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, false),
    BLUE_CEREUS_CACTUS(BlockType.TFCF_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, false),
    CARALLUMA(BlockType.SHORT_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, false),
    OCOTILLO(BlockType.TFCF_CACTUS, 0.2F, new int[] {0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0}, false),
    QUAQUA(BlockType.SHORT_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1}, false),
    PRICKLY_PEAR(BlockType.SHORT_FRUITING_CACTUS, 0F, new int[] {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 0, 0}, TFCFItems.FOOD.get(TFCFFood.PRICKLY_PEAR), new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, FRUITING, DORMANT, DORMANT}, false, true),
    SAGUARO_CACTUS(BlockType.SAGUARO_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, TFCFItems.FOOD.get(TFCFFood.SAGUARO), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}, false, true),
    PITAHAYA(BlockType.PITAHAYA, 0.2F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, TFCFItems.FOOD.get(TFCFFood.PITAHAYA), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, FRUITING, DORMANT, DORMANT, HEALTHY}, false, true),

    // Other vanilla plants
    //GLOW_LICHEN(BlockType.CREEPING_WATER, 0.7F, false),
    //SPORE_BLOSSOM(BlockType.SPORE_BLOSSOM, 0.7F, new int[] {0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0}, false),
    //BIG_DRIPLEAF(BlockType.CREEPING, 0.7F, false),
    //BIG_DRIPLEAF_STEM(BlockType.CREEPING, 0.7F, false),
    //SMALL_DRIPLEAF(BlockType.TALL_WATER_FRESH_SMALL_DRIPLEAF, 0.7F, false),
    //AZALEA(BlockType.CREEPING, 0.7F, false),

    // Unique
    GLOW_VINES_PLANT(BlockType.FRUITING_WEEPING, 1.0F, null, TFCFItems.FOOD.get(TFCFFood.GLOW_BERRY), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FLOWERING, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT}, false, true), // Needs custom block
    GLOW_VINES(BlockType.FRUITING_WEEPING_TOP, 1.0F, null, TFCFItems.FOOD.get(TFCFFood.GLOW_BERRY), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FLOWERING, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT}, false, true), // Needs custom block

    //Bamboo
    BLUE_BAMBOO(BlockType.BAMBOO, 0F, false),
    BLUE_BAMBOO_SAPLING(BlockType.BAMBOO_SAPLING, 0.5F, false),
    DRAGON_BAMBOO(BlockType.BAMBOO, 0F, false),
    DRAGON_BAMBOO_SAPLING(BlockType.BAMBOO_SAPLING, 0.5F, false),
    GOLDEN_BAMBOO(BlockType.BAMBOO, 0F, false),
    GOLDEN_BAMBOO_SAPLING(BlockType.BAMBOO_SAPLING, 0.5F, false),
    RED_BAMBOO(BlockType.BAMBOO, 0F, false),
    RED_BAMBOO_SAPLING(BlockType.BAMBOO_SAPLING, 0.5F, false),

    // Fungi
    AMANITA(BlockType.FUNGI, 0.9F, false),
    ARTISTS_CONK(BlockType.FUNGI_EPIPHYTE_SOLID, 0F, false),
    BEEFSTEAK_FUNGUS(BlockType.FUNGI_EPIPHYTE, 0.9F, false),
    BIRCH_POLYPORE(BlockType.FUNGI_EPIPHYTE, 0.9F, false),
    BITTER_OYSTER(BlockType.FUNGI_EPIPHYTE, 0.9F, false),
    BLACK_POWDERPUFF(BlockType.FUNGI, 0.9F, false),
    BOLETUS(BlockType.FUNGI, 0.9F, false),
    BRIDAL_VEIL_STINKHORN(BlockType.FUNGI, 0.9F, false),
    CHANTERELLE(BlockType.FUNGI, 0.9F, false),
    CHLOROPHOS_FOXFIRE(BlockType.FUNGI, 0.9F, false),
    DEATH_CAP(BlockType.FUNGI, 0.9F, false),
    DEVILS_FINGERS(BlockType.FUNGI, 0.9F, false),
    DRYADS_SADDLE(BlockType.FUNGI_EPIPHYTE_SOLID, 0.9F, false),
    ENTOLOMA(BlockType.FUNGI, 0.9F, false),
    GIANT_CLUB(BlockType.FUNGI, 0.9F, false),
    INDIGO_MILK_CAP(BlockType.FUNGI, 0.9F, false),
    LIONS_MANE(BlockType.FUNGI_EPIPHYTE_HANGING, 0.9F, false),
    PARASOL_MUSHROOM(BlockType.FUNGI, 0.9F, false),
    PORCINI(BlockType.FUNGI, 0.9F, false),
    REISHI(BlockType.FUNGI_EPIPHYTE, 0.9F, false),
    SHAGGY_BRACKET(BlockType.FUNGI_EPIPHYTE, 0.9F, false),
    SHIITAKE(BlockType.FUNGI, 0.9F, false),
    STINKHORN(BlockType.FUNGI, 0.9F, false),
    SULPHUR_SHELF(BlockType.FUNGI_EPIPHYTE_SOLID, 0F, false),
    TURKEY_TAIL(BlockType.FUNGI_EPIPHYTE_SOLID, 0F, false),
    WEEPING_MILK_CAP(BlockType.FUNGI, 0.9F, false),
    WOOD_BLEWIT(BlockType.FUNGI, 0.9F, false),
    WOOLLY_GOMPHUS(BlockType.FUNGI, 0.9F, false);

    //public static final EnumSet<TFCFPlant> SPECIAL_POTTED_PLANTS = EnumSet.of(GLOW_LICHEN);
    public static final EnumSet<TFCFPlant> ITEM_TINTED_PLANTS = EnumSet.of(LEAF_LITTER, CAT_GRASS, CROWNGRASS, GOOSEGRASS, WHEATGRASS, SAWGRASS, CINNAMON_FERN, ELEPHANT_GRASS, LEAF_LITTER, MONSTERA, MONSTERA_EPIPHYTE, POND_GRASS, PRAIRIE_GRASS, WOOLLY_BUSH, CONIFEROUS_SHRUB, DECIDUOUS_SHRUB);

    public final float speedFactor;
    @Nullable private final IntegerProperty property;
    public final int @Nullable[] stagesByMonth;
    public final BlockType type;
    public boolean isSeasonalFruitPlant;
    public Supplier<? extends Item> productItem;
    public Lifecycle[] stages;
    public final boolean conifer;

    TFCFPlant(BlockType type, float speedFactor, boolean conifer)
    {
        this(type, speedFactor, null, conifer);
        this.isSeasonalFruitPlant = false;
    }

    TFCFPlant(BlockType type, float speedFactor, int @Nullable[] stagesByMonth, boolean conifer)
    {
        this.type = type;
        this.speedFactor = speedFactor;
        this.stagesByMonth = stagesByMonth;
        this.isSeasonalFruitPlant = false;
        this.conifer = conifer;

        int maxStage = 0;
        if (stagesByMonth != null)
        {
            maxStage = Arrays.stream(stagesByMonth).max().orElse(0);
        }

        this.property = maxStage > 0 ? TFCBlockStateProperties.getStageProperty(maxStage) : null;
    }

    TFCFPlant(BlockType type, float speedFactor, int @Nullable[] stagesByMonth, Supplier<? extends Item> productItem, Lifecycle[] stages, boolean conifer, boolean isSeasonalFruitPlant)
    {
        this.type = type;
        this.speedFactor = speedFactor;
        this.stagesByMonth = stagesByMonth;
        this.isSeasonalFruitPlant = true;
        this.conifer = conifer;
        this.productItem = productItem;
        this.stages = stages;

        int maxStage = 0;
        if (stagesByMonth != null)
        {
            maxStage = Arrays.stream(stagesByMonth).max().orElse(0);
        }

        this.property = maxStage > 0 ? TFCBlockStateProperties.getStageProperty(maxStage) : null;
    }

    public Block create()
    {
        return type.factory.apply(this, type);
    }

    @Nullable
    public Function<Block, BlockItem> createBlockItem(Item.Properties properties)
    {
        return needsItem() ? block -> type.blockItemFactory.apply(block, properties) : null;
    }

    @Override
    public int stageFor(Month month)
    {
        assert stagesByMonth != null;
        return stagesByMonth.length < month.ordinal() ? 0 : stagesByMonth[month.ordinal()];
    }

    @Override
    @Nullable
    public IntegerProperty getStageProperty()
    {
        return property;
    }

    public boolean needsItem()
    {
        return !BlockType.NO_ITEM_TYPES.contains(type);
    }

    public boolean isFoliage()
    {
        return BlockType.FOLIAGE_TYPES.contains(type);
    }

    public boolean isSeasonal()
    {
        return type == BlockType.VINE;
    }

    public boolean isTallGrass()
    {
        return type == BlockType.TALL_GRASS || type == BlockType.SHORT_GRASS;
    }

    public boolean isBambooSapling()
    {
        return type == BlockType.BAMBOO_SAPLING;
    }

    public boolean isItemTinted()
    {
        return ITEM_TINTED_PLANTS.contains(this);
    }

    public boolean hasFlowerPot()
    {
        return type == BlockType.STANDARD || type == BlockType.DRY || type == BlockType.TFCF_STANDARD || type == BlockType.BAMBOO/* || SPECIAL_POTTED_PLANTS.contains(this)*/;
    }

    public Boolean isSeasonalFruitPlant()
    {
        return isSeasonalFruitPlant;
    }

    @Nullable
    public Supplier<? extends Item> getProductItem()
    {
        return productItem;
    }

    public Lifecycle[] getStages()
    {
        return stages;
    }

    public boolean isConifer()
    {
        return conifer;
    }

    /**
     * Compiler hack to allow forward references to paired plants.
     */
    public Supplier<? extends Block> transform()
    {
        return TFCFBlocks.PLANTS.get(switch (this)
        {
            case BEARDED_MOSS -> BEARDED_MOSS_PLANT;
            case BEARDED_MOSS_PLANT -> BEARDED_MOSS;
            case GLOW_VINES -> GLOW_VINES_PLANT;
            case GLOW_VINES_PLANT -> GLOW_VINES;
            default -> throw new IllegalStateException("Uhh why did you try to transform something that's not a tall plant? </3");
        });
    }

    public Supplier<? extends Block> stem()
    {
        return TFCFBlocks.PLANTS.get(switch (this)
        {
            case BLUE_BAMBOO_SAPLING -> BLUE_BAMBOO;
            case DRAGON_BAMBOO_SAPLING -> DRAGON_BAMBOO;
            case GOLDEN_BAMBOO_SAPLING -> GOLDEN_BAMBOO;
            case RED_BAMBOO_SAPLING -> RED_BAMBOO;
            default -> this;
        });
    }

    public Supplier<? extends Block> sapling()
    {
        return TFCFBlocks.PLANTS.get(switch (this)
        {
            case BLUE_BAMBOO -> BLUE_BAMBOO_SAPLING;
            case DRAGON_BAMBOO -> DRAGON_BAMBOO_SAPLING;
            case GOLDEN_BAMBOO -> GOLDEN_BAMBOO_SAPLING;
            case RED_BAMBOO -> RED_BAMBOO_SAPLING;
            default -> throw new IllegalStateException("Pls fix plant sapling asap </3");
        });
    }

    public Supplier<? extends Block> transformFruiting()
    {
        return TFCFBlocks.FRUITING_PLANTS.get(switch (this)
        {
            case GLOW_VINES -> GLOW_VINES_PLANT;
            case GLOW_VINES_PLANT -> GLOW_VINES;
            default -> null;
        });
    }

    public enum BlockType
    {
        STANDARD((plant, type) -> PlantBlock.create(plant, fire(nonSolid(plant)))),
        CACTUS((plant, type) -> TFCCactusBlock.create(plant, fire(solid().strength(0.25F).sound(SoundType.WOOL)).pathType(BlockPathTypes.DAMAGE_CACTUS))),
        DRY((plant, type) -> DryPlantBlock.create(plant, fire(nonSolid(plant)))),
        CREEPING((plant, type) -> CreepingPlantBlock.create(plant, fire(nonSolid(plant).hasPostProcess(TFCFBlocks::always)))), // Post process ensures shape is updated after world gen
        EPIPHYTE((plant, type) -> EpiphytePlantBlock.create(plant, fire(nonSolid(plant).hasPostProcess(TFCFBlocks::always)))),
        SHORT_GRASS((plant, type) -> ShortGrassBlock.create(plant, fire(nonSolid(plant)))),
        DESERT_SHORT_GRASS((plant, type) -> DesertShortGrassBlock.create(plant, fire(nonSolid(plant)))),
        TALL_GRASS((plant, type) -> TFCTallGrassBlock.create(plant, fire(nonSolid(plant)))),
        VINE((plant, type) -> new TFCVineBlock(fire(nonSolid(plant)))),
        WEEPING((plant, type) -> new BodyPlantBlock(fire(nonSolidTallPlant(plant)), plant.transform(), BodyPlantBlock.BODY_SHAPE, Direction.DOWN)),
        WEEPING_TOP((plant, type) -> new TopPlantBlock(fire(nonSolidTallPlant(plant)), plant.transform(), Direction.DOWN, BodyPlantBlock.WEEPING_SHAPE)),
        TWISTING((plant, type) -> new BodyPlantBlock(fire(nonSolidTallPlant(plant)), plant.transform(), BodyPlantBlock.BODY_SHAPE, Direction.UP)),
        TWISTING_TOP((plant, type) -> new TopPlantBlock(fire(nonSolidTallPlant(plant)), plant.transform(), Direction.UP, BodyPlantBlock.TWISTING_SHAPE)),
        TWISTING_SOLID((plant, type) -> new BodyPlantBlock(fire(solidTallPlant()), plant.transform(), BodyPlantBlock.BODY_SHAPE, Direction.UP)),
        TWISTING_SOLID_TOP((plant, type) -> new TopPlantBlock(fire(solidTallPlant()), plant.transform(), Direction.UP, BodyPlantBlock.TWISTING_SHAPE)),

        // Custom
        TFCF_STANDARD((plant, type) -> TFCFPlantBlock.create(plant, fire(nonSolid(plant)))),
        CREEPING_WATER((plant, type) -> CreepingWaterPlantBlock.create(plant, fire(nonSolid(plant).hasPostProcess(TFCFBlocks::always)))),
        SPORE_BLOSSOM((plant, type) -> SporeBlossomPlantBlock.create(plant, fire(nonSolid(plant).hasPostProcess(TFCFBlocks::always)).sound(SoundType.SPORE_BLOSSOM))),
        FRUITING_WEEPING((plant, type) -> new FruitingBodyPlantBlock(ExtendedProperties.of(nonSolidTallPlant(plant).hasPostProcess(TFCFBlocks::always)).lightLevel(FruitingBodyPlantBlock.emission(14, true)).instabreak().sound(SoundType.CAVE_VINES).randomTicks().blockEntity(TFCFBlockEntities.BERRY_BUSH).serverTicks(FruitTreeBlockEntity::serverTick), plant.transform(), BodyPlantBlock.BODY_SHAPE, Direction.DOWN, plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        FRUITING_WEEPING_TOP((plant, type) -> new FruitingTopPlantBlock(ExtendedProperties.of(nonSolidTallPlant(plant).hasPostProcess(TFCFBlocks::always)).lightLevel(FruitingBodyPlantBlock.emission(14, true)).instabreak().sound(SoundType.CAVE_VINES).randomTicks().blockEntity(TFCFBlockEntities.BERRY_BUSH).serverTicks(FruitTreeBlockEntity::serverTick), plant.transform(), BodyPlantBlock.WEEPING_SHAPE, Direction.DOWN, plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        TALL_LAND_WATER((plant, type) -> TallLandWaterPlantBlock.create(plant, TFCBlockStateProperties.SALT_WATER, nonSolid(plant))),
        TALL_LAND_WATER_FRESH((plant, type) -> TallLandWaterPlantBlock.create(plant, TFCBlockStateProperties.FRESH_WATER, nonSolid(plant))),
        POISONOUS_TALL_GRASS((plant, type) -> TFCFTallGrassBlock.create(plant, fire(nonSolid(plant)))),
        SHORT_CACTUS((plant, type) -> ShortCactusBlock.create(plant, fire(solid().strength(0.25F).sound(SoundType.MOSS)).pathType(BlockPathTypes.DAMAGE_CACTUS))),
        TFCF_CACTUS((plant, type) -> TFCFCactusBlock.create(plant, fire(solid().strength(0.25F).sound(SoundType.MOSS)).pathType(BlockPathTypes.DAMAGE_CACTUS))),
        SAGUARO_CACTUS((plant, type) -> SaguaroCactusBlock.create(plant, fire(solid().strength(0.25F).sound(SoundType.MOSS).hasPostProcess(TFCFBlocks::always)).pathType(BlockPathTypes.DAMAGE_CACTUS).randomTicks().blockEntity(TFCFBlockEntities.BERRY_BUSH).serverTicks(FruitTreeBlockEntity::serverTick), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        PITAHAYA((plant, type) -> PitahayaBlock.create(plant, fire(solid().strength(0.25F).sound(SoundType.NETHER_WART).hasPostProcess(TFCFBlocks::always)).pathType(BlockPathTypes.DAMAGE_CACTUS).randomTicks().blockEntity(TFCFBlockEntities.BERRY_BUSH).serverTicks(FruitTreeBlockEntity::serverTick), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        SHORT_FRUITING_CACTUS((plant, type) -> ShortFruitingCactusBlock.create(plant, fire(solid().strength(0.25F).sound(SoundType.MOSS).hasPostProcess(TFCFBlocks::always)).pathType(BlockPathTypes.DAMAGE_CACTUS).randomTicks().blockEntity(TFCFBlockEntities.BERRY_BUSH).serverTicks(FruitTreeBlockEntity::serverTick), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        FRUITING_CACTUS((plant, type) -> TFCFFruitingCactusBlock.create(plant, fire(solid().strength(0.25F).sound(SoundType.MOSS).hasPostProcess(TFCFBlocks::always)).pathType(BlockPathTypes.DAMAGE_CACTUS).randomTicks().blockEntity(TFCFBlockEntities.BERRY_BUSH).serverTicks(FruitTreeBlockEntity::serverTick), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        FUNGI((plant, type) -> FungiBlock.create(plant, fire(nonSolid(plant)))),
        FUNGI_EPIPHYTE((plant, type) -> FungiEpiphyteBlock.create(plant, fire(nonSolid(plant).hasPostProcess(TFCFBlocks::always)))),
        FUNGI_EPIPHYTE_SOLID((plant, type) -> FungiEpiphyteSolidBlock.create(plant, fire(solid().strength(0.25F).hasPostProcess(TFCFBlocks::always)))),
        FUNGI_EPIPHYTE_HANGING((plant, type) -> FungiEpiphyteCeilingBlock.create(plant, fire(nonSolid(plant).strength(0.25F).hasPostProcess(TFCFBlocks::always)))),
        FRUITING_TALL_PLANT((plant, type) -> FruitingTallPlantBlock.create(plant, fire(nonSolid(plant).hasPostProcess(TFCFBlocks::always)).randomTicks().blockEntity(TFCFBlockEntities.BERRY_BUSH).serverTicks(FruitTreeBlockEntity::serverTick), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        CACAO((plant, type) -> CacaoPodBlock.create(plant, fire(nonSolid(plant).hasPostProcess(TFCFBlocks::always)).randomTicks().strength(0.2F, 3.0F).sound(SoundType.BAMBOO).noOcclusion())),

        BAMBOO((plant, type) -> BambooPlantBlock.create(plant, ExtendedProperties.of(Material.BAMBOO, MaterialColor.PLANT).randomTicks().instabreak().strength(1.0F).sound(SoundType.BAMBOO).noOcclusion().dynamicShape().hasPostProcess(TFCFBlocks::always), plant.stem(), plant.sapling())),
        BAMBOO_SAPLING((plant, type) ->  BambooSaplingPlantBlock.create(plant, ExtendedProperties.of(Material.BAMBOO_SAPLING).randomTicks().instabreak().noCollission().strength(1.0F).sound(SoundType.BAMBOO_SAPLING).hasPostProcess(TFCFBlocks::always), plant.stem())),

        // Water
        KELP((plant, type) -> TFCKelpBlock.create(nonSolidTallPlant(plant).lootFrom(plant.transform()), plant.transform(), Direction.UP, BodyPlantBlock.THIN_BODY_SHAPE, TFCBlockStateProperties.SALT_WATER)),
        KELP_TOP(((plant, type) -> TFCKelpTopBlock.create(nonSolidTallPlant(plant), plant.transform(), Direction.UP, BodyPlantBlock.TWISTING_THIN_SHAPE, TFCBlockStateProperties.SALT_WATER))),
        KELP_TREE((plant, type) -> KelpTreeBlock.create(kelp(plant), TFCBlockStateProperties.SALT_WATER)),
        KELP_TREE_FLOWER((plant, type) -> KelpTreeFlowerBlock.create(kelp(plant), plant.transform())),
        FLOATING((plant, type) -> FloatingWaterPlantBlock.create(plant, TFCFluids.SALT_WATER.source(), nonSolid(plant)), WaterLilyBlockItem::new),
        FLOATING_FRESH((plant, type) -> FloatingWaterPlantBlock.create(plant, () -> Fluids.WATER, nonSolid(plant)), WaterLilyBlockItem::new),
        TALL_WATER((plant, type) -> TallWaterPlantBlock.create(plant, TFCBlockStateProperties.SALT_WATER, nonSolid(plant))),
        TALL_WATER_FRESH((plant, type) -> TallWaterPlantBlock.create(plant, TFCBlockStateProperties.FRESH_WATER, nonSolid(plant))),
        WATER((plant, type) -> WaterPlantBlock.create(plant, TFCBlockStateProperties.SALT_WATER, nonSolid(plant))),
        WATER_FRESH((plant, type) -> WaterPlantBlock.create(plant, TFCBlockStateProperties.FRESH_WATER, nonSolid(plant))),
        GRASS_WATER((plant, type) -> TFCSeagrassBlock.create(plant, TFCBlockStateProperties.SALT_WATER, nonSolid(plant))),
        GRASS_WATER_FRESH((plant, type) -> TFCSeagrassBlock.create(plant, TFCBlockStateProperties.FRESH_WATER, nonSolid(plant))),

        // Custom Water
        TALL_WATER_SMALL_DRIPLEAF((plant, type) -> SmallDripleafPlantBlock.create(plant, TFCBlockStateProperties.SALT_WATER, nonSolid(plant))),
        TALL_WATER_FRESH_SMALL_DRIPLEAF((plant, type) -> SmallDripleafPlantBlock.create(plant, TFCBlockStateProperties.FRESH_WATER, nonSolid(plant)));

        public static final EnumSet<BlockType> NO_ITEM_TYPES = EnumSet.of(WEEPING, TWISTING_SOLID, KELP, KELP_TREE, TWISTING);
        public static final EnumSet<BlockType> FOLIAGE_TYPES = EnumSet.of(WEEPING, WEEPING_TOP, FLOATING_FRESH, FLOATING, WATER_FRESH, GRASS_WATER_FRESH, GRASS_WATER);

        /**
         * Default properties to avoid rewriting them out every time
         */
        public static BlockBehaviour.Properties solid()
        {
            return Block.Properties.of(Material.REPLACEABLE_PLANT).noOcclusion().sound(SoundType.GRASS).randomTicks().hasPostProcess(TFCFBlocks::always);
        }

        public static BlockBehaviour.Properties nonSolid(TFCFPlant plant)
        {
            return solid().instabreak().speedFactor(plant.speedFactor).noCollission().hasPostProcess(TFCFBlocks::always);
        }

        public static BlockBehaviour.Properties solidTallPlant()
        {
            return BlockBehaviour.Properties.of(Material.PLANT, MaterialColor.PLANT).randomTicks().sound(SoundType.WEEPING_VINES).hasPostProcess(TFCFBlocks::always);
        }

        public static BlockBehaviour.Properties nonSolidTallPlant(TFCFPlant plant)
        {
            return solidTallPlant().instabreak().noCollission().speedFactor(plant.speedFactor).hasPostProcess(TFCFBlocks::always);
        }

        public static BlockBehaviour.Properties kelp(TFCFPlant plant)
        {
            return BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.PLANT).noCollission().randomTicks().speedFactor(plant.speedFactor).strength(1.0f).sound(SoundType.WET_GRASS).hasPostProcess(TFCFBlocks::always);
        }

        public static ExtendedProperties fire(BlockBehaviour.Properties properties)
        {
            return ExtendedProperties.of(properties).flammable(60, 30);
        }

        public final BiFunction<TFCFPlant, BlockType, ? extends Block> factory;
        public final BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory;

        BlockType(BiFunction<TFCFPlant, BlockType, ? extends Block> factory)
        {
            this(factory, BlockItem::new);
        }

        BlockType(BiFunction<TFCFPlant, BlockType, ? extends Block> factory, BiFunction<Block, Item.Properties, ? extends BlockItem> blockItemFactory)
        {
            this.factory = factory;
            this.blockItemFactory = blockItemFactory;
        }
    }
}
