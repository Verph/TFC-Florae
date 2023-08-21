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
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.plant.*;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.fluids.TFCFluids;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.registry.RegistryPlant;

import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.items.TFCFFood;
import tfcflorae.common.items.TFCFItems;
import tfcflorae.util.climate.TFCFClimateRanges;

import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.*;

public enum TFCFPlant implements RegistryPlant
{
    // Short Grasses and old Florae plants
    CAT_GRASS(BlockType.SHORT_GRASS, 0.8F, false, false, false),
    CROWNGRASS(BlockType.SHORT_GRASS, 0.8F, false, false, false),
    GOOSEGRASS(BlockType.SHORT_GRASS, 0.8F, false, false, false),
    HALFA_GRASS(BlockType.DESERT_SHORT_GRASS, 0.8F, false, false, false),
    MARRAM_GRASS(BlockType.DESERT_SHORT_GRASS, 0.8F, false, false, false),
    PRAIRIE_JUNEGRASS(BlockType.SHORT_GRASS, 0.8F, new int[] {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0}, false, false, false),
    WHEATGRASS(BlockType.SHORT_GRASS, 0.8F, false, false, false),
    BUNCH_GRASS(BlockType.TALL_WATER_FRESH, 0.8F, false, false, false),
    SAWGRASS(BlockType.TALL_WATER_FRESH, 0.8F, false, false, false),

    CINNAMON_FERN(BlockType.TFCF_STANDARD, 0.8F, false, false, true),
    LEAF_LITTER(BlockType.SHORT_GRASS, 0.8F, false, false, false),
    LOW_UNDERGROWTH(BlockType.SHORT_GRASS, 0.8F, false, false, true),
    PIERIS(BlockType.TFCF_STANDARD, 0.5F, new int[] {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0}, false, false, false),
    WOOLLY_BUSH(BlockType.TFCF_STANDARD, 0.8F, false, false, false),
    BEARDED_MOSS_PLANT(BlockType.WEEPING, 0.9f, false, false, false),
    BEARDED_MOSS(BlockType.WEEPING_TOP, 0.9f, false, false, false),
    BEARDED_MOSS_CREEPING(BlockType.CREEPING, 0.9f, false, false, false),
    MONSTERA(BlockType.TALL_GRASS, 0.5F, false, false, false),
    MONSTERA_EPIPHYTE(BlockType.EPIPHYTE, 0.5F, false, false, false),
    DECIDUOUS_SHRUB(BlockType.TALL_GRASS, 0.5F, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0}, false, false, true),
    CONIFEROUS_SHRUB(BlockType.TALL_GRASS, 0.5F, true, false, false),
    LARGE_RED_SEALING_WAX_PALM(BlockType.TALL_GRASS, 0.2F, false, false, false),
    //FLOWER_CHERRY(BlockType.SHORT_GRASS, 1.0F, new int[] {0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0}, false, false, false), TFC has this implemented natively.
    SHIELD_FERN(BlockType.SHORT_GRASS, 0.7F, true, false, false),
    LICORICE_FERN(BlockType.SHORT_GRASS, 0.7F, true, false, false),

    // Aesthetics
    BIRD_OF_PARADISE(BlockType.TFCF_STANDARD, 0.6F, new int[] {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, false),
    BLUEBELL(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 2, 2, 2, 2, 1, 0}, false, false, false),
    BONATEA(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false, false, false),
    CAMAS(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 0, 1, 2, 2, 2, 3, 3, 2, 2, 1, 0}, false, false, false),
    CHIVES(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 0, 1, 2, 3, 3, 3, 2, 2, 2, 1, 0}, false, false, false),
    CLOVER(BlockType.CREEPING, 0.9F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false, false, false),
    COMMON_REEDS(BlockType.TALL_LAND_WATER_FRESH, 0.5F, new int[] {0, 0, 1, 2, 2, 2, 2, 3, 3, 3, 1, 0}, false, false, false),
    CREEPING_CHARLIE(BlockType.CREEPING, 0.9F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false, false, false),
    CUP_PLANT(BlockType.TALL_GRASS, 0.5F, new int[] {0, 0, 1, 2, 2, 3, 3, 2, 2, 2, 1, 0}, false, false, false), // Age-determined size: small, large
    DAFFODIL(BlockType.SHORT_GRASS, 0.8F, new int[] {0, 3, 3, 2, 2, 2, 2, 2, 2, 2, 1, 0}, false, false, false),
    DEVILS_TONGUE(BlockType.TFCF_STANDARD, 0.4F, new int[] {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, false, false, false),
    DUNE_GRASS(BlockType.DESERT_SHORT_GRASS, 0.8F, new int[] {0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0}, false, false, false),
    EGYPTIAN_AUTUMN_CROCUS(BlockType.SHORT_GRASS, 0.8F, new int[] {2, 2, 0, 0, 0, 0, 0, 0, 1, 1, 1, 2}, false, false, false),
    ELEPHANT_GRASS(BlockType.TALL_GRASS, 0.5F, false, false, false),
    EUROPEAN_BEDSTRAW(BlockType.TFCF_STANDARD, 0.6F, new int[] {0, 0, 1, 2, 2, 2, 3, 3, 3, 2, 1, 0}, false, false, false),
    FIREWEED(BlockType.TALL_GRASS, 0.8F, new int[] {0, 0, 1, 2, 2, 3, 3, 3, 3, 4, 1, 0}, false, false, false),
    FREESIA(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 3, 3, 3, 2, 2, 2, 2, 2, 1, 0}, false, false, false),
    GARLIC_MUSTARD(BlockType.SHORT_GRASS, 0.8F, new int[] {0, 0, 1, 3, 3, 3, 2, 2, 2, 2, 1, 0}, false, false, false),
    GIANT_HOGWEED(BlockType.POISONOUS_TALL_GRASS, 0.6F, new int[] {0, 0, 1, 2, 2, 3, 3, 2, 2, 2, 1, 0}, false, false, false),
    PINK_HEATHER(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 0, 1, 1, 1, 1, 2, 2, 1, 1, 1, 0}, false, false, false),
    WHITE_HEATHER(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 0, 1, 1, 1, 1, 2, 2, 1, 1, 1, 0}, false, false, false),
    IRIS(BlockType.TFCF_STANDARD, 0.9F, new int[] {0, 0, 1, 2, 3, 3, 3, 2, 2, 2, 1, 0}, false, false, false),
    JACK_IN_THE_PULPIT(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 2, 2, 2, 2, 1, 0}, false, false, false),
    LAVENDER(BlockType.TFCF_STANDARD, 0.7F, new int[] {0, 1, 2, 2, 2, 3, 3, 3, 2, 2, 1, 0}, false, false, false),
    LEAFY_LOW_UNDERGROWTH(BlockType.SHORT_GRASS, 0.8F, new int[] {0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0}, false, false, true),
    LEAFY_UNDERGROWTH(BlockType.TALL_GRASS, 0.6F, new int[] {0, 0, 1, 2, 2, 2, 2, 2, 3, 3, 1, 0}, false, false, true), // Age-determined size: small, medium, large
    LEOPARD_ORCHID(BlockType.TFCF_STANDARD, 0.6F, new int[] {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0}, false, false, false),
    LEOPARD_ORCHID_EPIPHYTE(BlockType.EPIPHYTE, 0.6F, new int[] {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0}, false, false, false),
    LIANA_VINE(BlockType.VINE, 1.0F, false, false, false),
    LOBSTER_CLAWS(BlockType.TFCF_STANDARD, 0.6F, new int[] {0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0}, false, false, false),
    ORCHID(BlockType.TFCF_STANDARD, 0.9F, new int[] {0, 1, 2, 3, 3, 3, 2, 2, 2, 2, 1, 0}, false, false, false),
    BLUE_LUPINE(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false, false, false),
    PURPLE_LUPINE(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false, false, false),
    RED_LUPINE(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false, false, false),
    YELLOW_LUPINE(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 3, 3, 3, 3, 2, 2, 2, 1, 0}, false, false, false),
    //MAIDENHAIR_FERN_PLANT(BlockType.TWISTING, 0.6F, new int[] {0, 0, 1, 1, 1, 1, 2, 2, 2, 1, 1, 0}, false, false, false),
    //MAIDENHAIR_FERN(BlockType.TWISTING_TOP, 0.6F, new int[] {0, 0, 1, 1, 1, 1, 2, 2, 2, 1, 1, 0}, false, false, false),
    MALLOW(BlockType.TALL_GRASS, 0.7F, new int[] {0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 1, 0}, false, false, false),
    UNDERGROWTH(BlockType.TALL_GRASS, 0.6F, new int[] {0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 1, 0}, false, false, true), // Age-determined size: small, medium, large
    AMERICAN_MISTLETOE(BlockType.EPIPHYTE, 0.7F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}, false, false, false),
    DESERT_MISTLETOE(BlockType.EPIPHYTE, 0.7F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}, false, false, false),
    EUROPEAN_MISTLETOE(BlockType.EPIPHYTE, 0.7F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}, false, false, false),
    DWARF_MISTLETOE(BlockType.EPIPHYTE, 0.7F, false, false, false),
    NARBON_VETCH(BlockType.TFCF_STANDARD, 0.9F, new int[] {0, 0, 1, 2, 3, 3, 3, 2, 2, 2, 1, 0}, false, false, false),
    PAPYRUS(BlockType.TALL_LAND_WATER_FRESH, 0.6F, false, false, false),
    PERIWINKLE(BlockType.TFCF_STANDARD, 0.9F, new int[] {0, 0, 1, 2, 2, 3, 3, 3, 3, 3, 3, 0}, false, false, false),
    POKEWEED(BlockType.FRUITING_TALL_PLANT, 0.5F, new int[] {0, 0, 1, 2, 2, 2, 2, 3, 3, 4, 1, 0}, TFCFItems.FOOD.get(TFCFFood.DRAGONBERRY), new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FRUITING, HEALTHY, DORMANT, DORMANT}, false, false, true, false),
    POND_GRASS(BlockType.SHORT_GRASS, 0.6F, new int[] {0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0}, false, false, false),
    PRAIRIE_GRASS(BlockType.TALL_GRASS, 0.5F, new int[] {0, 1, 2, 2, 2, 2, 2, 2, 3, 3, 1, 0}, false, false, false), // Age-determined size: small, medium, large
    ROUGH_HORSETAIL(BlockType.TFCF_STANDARD, 0.7F, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, false),
    ROYAL_JASMINE(BlockType.TALL_GRASS, 0.7F, new int[] {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0}, false, false, false), // Age-determined size: small, large
    SALTWORT(BlockType.SHORT_GRASS, 0.7F, new int[] {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0}, false, false, false), // Age-determined size: small, large
    SUNFLOWER(BlockType.TALL_GRASS, 0.5F, new int[] {0, 0, 1, 2, 2, 3, 3, 3, 3, 2, 1, 0}, false, false, false),
    TITAN_ARUM(BlockType.TFCF_STANDARD, 0.4F, new int[] {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}, false, false, false),
    VOODOO_LILY(BlockType.TFCF_STANDARD, 0.4F, new int[] {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, false, false, false),
    WATER_HYACINTH(BlockType.FLOATING_FRESH, 0.5F, new int[] {0, 0, 1, 1, 1, 1, 1, 2, 2, 1, 1, 0}, false, false, false), // Age-determined size: small, large
    WATER_PLANTAIN(BlockType.TALL_WATER_FRESH, 0.6F, new int[] {0, 1, 2, 3, 3, 3, 3, 3, 3, 2, 1, 0}, false, false, false),
    WOOD_BITTER_VETCH(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 0, 1, 2, 3, 3, 3, 2, 2, 2, 1, 0}, false, false, false),
    YARROW(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 0, 1, 2, 2, 3, 3, 3, 3, 2, 1, 0}, false, false, false),
    SUNDEW(BlockType.TFCF_STANDARD, 0.8F, false, false, false),
    THISTLE(BlockType.TFCF_STANDARD, 0.8F, new int[] {0, 1, 2, 2, 3, 3, 3, 3, 4, 1, 1, 0}, false, false, false),
    VENUS_FLYTRAP(BlockType.TFCF_STANDARD, 0.8F, false, false, false),
    //CACAO(BlockType.CACAO, 0F, new int[] {0, 0, 0, 0, 0, 1, 1, 2, 2, 0, 0, 0}, false, false, false),

    // Cacti plants or something
    BARREL_CACTUS(BlockType.FRUITING_CACTUS, 0F, new int[] {0, 0, 0, 0, 1, 2, 2, 2, 2, 3, 3, 0}, TFCFItems.FOOD.get(TFCFFood.BARREL_CACTUS_FRUIT), new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT}, false, false, true, false),
    AFRICAN_MILK_BARREL(BlockType.SHORT_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0}, false, false, false),
    AFRICAN_MILK_TREE(BlockType.TFCF_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0}, false, false, false),
    ALBANIAN_SPURGE(BlockType.SHORT_CACTUS, 0.2F, new int[] {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, false),
    ANGEL_WING_CACTUS(BlockType.SHORT_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, false, false, false),
    BLUE_CEREUS_CACTUS(BlockType.TFCF_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, false, false, false),
    CARALLUMA(BlockType.SHORT_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0}, false, false, false),
    OCOTILLO(BlockType.TFCF_CACTUS, 0.2F, new int[] {0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0}, false, false, false),
    QUAQUA(BlockType.SHORT_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1}, false, false, false),
    PRICKLY_PEAR(BlockType.SHORT_FRUITING_CACTUS, 0F, new int[] {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 0, 0}, TFCFItems.FOOD.get(TFCFFood.PRICKLY_PEAR), new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, FRUITING, DORMANT, DORMANT}, false, false, true, false),
    SAGUARO_CACTUS(BlockType.SAGUARO_CACTUS, 0.2F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, TFCFItems.FOOD.get(TFCFFood.SAGUARO), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}, false, false, true, false),
    PITAHAYA(BlockType.PITAHAYA, 0.2F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, TFCFItems.FOOD.get(TFCFFood.PITAHAYA), new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, FRUITING, DORMANT, DORMANT, HEALTHY}, false, false, true, false),

    // Other vanilla plants
    //GLOW_LICHEN(BlockType.CREEPING_WATER, 0.7F, false),
    //SPORE_BLOSSOM(BlockType.SPORE_BLOSSOM, 0.7F, new int[] {0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0}, false),
    //BIG_DRIPLEAF(BlockType.CREEPING, 0.7F, false),
    //BIG_DRIPLEAF_STEM(BlockType.CREEPING, 0.7F, false),
    //SMALL_DRIPLEAF(BlockType.TALL_WATER_FRESH_SMALL_DRIPLEAF, 0.7F, false),
    //AZALEA(BlockType.CREEPING, 0.7F, false),

    // Unique
    GLOW_VINES_PLANT(BlockType.FRUITING_WEEPING, 1.0F, null, TFCFItems.FOOD.get(TFCFFood.GLOW_BERRY), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FLOWERING, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT}, false, false, true, false), // Needs custom block
    GLOW_VINES(BlockType.FRUITING_WEEPING_TOP, 1.0F, null, TFCFItems.FOOD.get(TFCFFood.GLOW_BERRY), new Lifecycle[] {DORMANT, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FLOWERING, FRUITING, HEALTHY, DORMANT, DORMANT, DORMANT}, false, false, true, false), // Needs custom block

    //Bamboo
    BLUE_BAMBOO(BlockType.BAMBOO, 0F, false, false, false),
    BLUE_BAMBOO_SAPLING(BlockType.BAMBOO_SAPLING, 0.5F, false, false, false),
    DRAGON_BAMBOO(BlockType.BAMBOO, 0F, false, false, false),
    DRAGON_BAMBOO_SAPLING(BlockType.BAMBOO_SAPLING, 0.5F, false, false, false),
    GOLDEN_BAMBOO(BlockType.BAMBOO, 0F, false, false, false),
    GOLDEN_BAMBOO_SAPLING(BlockType.BAMBOO_SAPLING, 0.5F, false, false, false),
    RED_BAMBOO(BlockType.BAMBOO, 0F, false, false, false),
    RED_BAMBOO_SAPLING(BlockType.BAMBOO_SAPLING, 0.5F, false, false, false),

    // Fungi
    AMANITA(BlockType.FUNGI, 0.9F, false, false, false),
    ARTISTS_CONK(BlockType.FUNGI_EPIPHYTE_SOLID, 0F, false, false, false),
    BEEFSTEAK_FUNGUS(BlockType.FUNGI_EPIPHYTE, 0.9F, false, false, false),
    BIRCH_POLYPORE(BlockType.FUNGI_EPIPHYTE, 0.9F, false, false, false),
    BITTER_OYSTER(BlockType.FUNGI_EPIPHYTE, 0.9F, false, false, false),
    BLACK_POWDERPUFF(BlockType.FUNGI, 0.9F, false, false, false),
    BOLETUS(BlockType.FUNGI, 0.9F, false, false, false),
    BRIDAL_VEIL_STINKHORN(BlockType.FUNGI, 0.9F, false, false, false),
    CHANTERELLE(BlockType.FUNGI, 0.9F, false, false, false),
    CHLOROPHOS_FOXFIRE(BlockType.FUNGI, 0.9F, false, false, false),
    DEATH_CAP(BlockType.FUNGI, 0.9F, false, false, false),
    DEVILS_FINGERS(BlockType.FUNGI, 0.9F, false, false, false),
    DRYADS_SADDLE(BlockType.FUNGI_EPIPHYTE_SOLID, 0.9F, false, false, false),
    ENTOLOMA(BlockType.FUNGI, 0.9F, false, false, false),
    GIANT_CLUB(BlockType.FUNGI, 0.9F, false, false, false),
    INDIGO_MILK_CAP(BlockType.FUNGI, 0.9F, false, false, false),
    LIONS_MANE(BlockType.FUNGI_EPIPHYTE_HANGING, 0.9F, false, false, false),
    PARASOL_MUSHROOM(BlockType.FUNGI, 0.9F, false, false, false),
    PORCINI(BlockType.FUNGI, 0.9F, false, false, false),
    REISHI(BlockType.FUNGI_EPIPHYTE, 0.9F, false, false, false),
    SHAGGY_BRACKET(BlockType.FUNGI_EPIPHYTE, 0.9F, false, false, false),
    SHIITAKE(BlockType.FUNGI, 0.9F, false, false, false),
    STINKHORN(BlockType.FUNGI, 0.9F, false, false, false),
    SULPHUR_SHELF(BlockType.FUNGI_EPIPHYTE_SOLID, 0F, false, false, false),
    TURKEY_TAIL(BlockType.FUNGI_EPIPHYTE_SOLID, 0F, false, false, false),
    WEEPING_MILK_CAP(BlockType.FUNGI, 0.9F, false, false, false),
    WOOD_BLEWIT(BlockType.FUNGI, 0.9F, false, false, false),
    WOOLLY_GOMPHUS(BlockType.FUNGI, 0.9F, false, false, false),

    // Vines
    BLUE_SKYFLOWER(BlockType.VINE_TFCF, 0.8F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}, false, true, false),
    JADE_VINE(BlockType.VINE_TFCF, 0.8F, new int[] {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0}, false, true, false),
    JAPANESE_IVY(BlockType.VINE_TFCF, 0.8F, false, true, false),
    MADEIRA_VINE(BlockType.VINE_TFCF, 0.8F, new int[] {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1}, false, true, false),
    MYSORE_TRUMPETVINE(BlockType.VINE_TFCF, 0.8F, new int[] {0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0}, false, true, false),
    SILVERVEIN_CREEPER(BlockType.VINE_TFCF, 0.8F, false, true, false),
    SWEDISH_IVY(BlockType.VINE_TFCF, 0.8F, false, true, false),
    VARIEGATED_PERSIAN_IVY(BlockType.VINE_TFCF, 0.8F, false, true, false),
    WISTERIA(BlockType.VINE_TFCF, 0.8F, new int[] {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0}, false, true, false),

    // Hanging vines
    HANGING_BLUE_SKYFLOWER_PLANT(BlockType.WEEPING_TFCF, 0.8F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}, false, true, false),
    HANGING_BLUE_SKYFLOWER(BlockType.WEEPING_TOP_TFCF, 0.8F, new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}, false, true, false),
    HANGING_JADE_VINE_PLANT(BlockType.WEEPING_TFCF, 0.8F, new int[] {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0}, false, true, false),
    HANGING_JADE_VINE(BlockType.WEEPING_TOP_TFCF, 0.8F, new int[] {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0}, false, true, false),
    HANGING_JAPANESE_IVY_PLANT(BlockType.WEEPING_TFCF, 0.8F, false, true, false),
    HANGING_JAPANESE_IVY(BlockType.WEEPING_TOP_TFCF, 0.8F, false, true, false),
    HANGING_MADEIRA_VINE_PLANT(BlockType.WEEPING_TFCF, 0.8F, new int[] {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1}, false, true, false),
    HANGING_MADEIRA_VINE(BlockType.WEEPING_TOP_TFCF, 0.8F, new int[] {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1}, false, true, false),
    HANGING_MYSORE_TRUMPETVINE_PLANT(BlockType.WEEPING_TFCF, 0.8F, new int[] {0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0}, false, true, false),
    HANGING_MYSORE_TRUMPETVINE(BlockType.WEEPING_TOP_TFCF, 0.8F, new int[] {0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0}, false, true, false),
    HANGING_SILVERVEIN_CREEPER_PLANT(BlockType.WEEPING_TFCF, 0.8F, false, true, false),
    HANGING_SILVERVEIN_CREEPER(BlockType.WEEPING_TOP_TFCF, 0.8F, false, true, false),
    HANGING_SWEDISH_IVY_PLANT(BlockType.WEEPING_TFCF, 0.8F, false, true, false),
    HANGING_SWEDISH_IVY(BlockType.WEEPING_TOP_TFCF, 0.8F, false, true, false),
    HANGING_VARIEGATED_PERSIAN_IVY_PLANT(BlockType.WEEPING_TFCF, 0.8F, false, true, false),
    HANGING_VARIEGATED_PERSIAN_IVY(BlockType.WEEPING_TOP_TFCF, 0.8F, false, true, false),
    HANGING_WISTERIA_PLANT(BlockType.WEEPING_TFCF, 0.8F, new int[] {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0}, false, true, false),
    HANGING_WISTERIA(BlockType.WEEPING_TOP_TFCF, 0.8F, new int[] {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0}, false, true, false),

    // Water Plants
    PONDWEED_PLANT(BlockType.KELP_FRESH, 0.6F, false, false, false),
    PONDWEED(BlockType.KELP_TOP_FRESH, 0.8F, false, false, false),
    WHITESTEM_PONDWEED(BlockType.WATER_FRESH, 0.8F, false, false, false),
    HORNWORT(BlockType.WATER_FRESH, 0.9F, false, false, false),
    RED_ALGAE(BlockType.WATER, 0.7F, false, false, false),
    RED_SEA_WHIP(BlockType.WATER, 0.7F, false, false, false),
    SEA_ANEMONE(BlockType.WATER, 0.7F, false, false, false),
    SEAGRASS(BlockType.GROWING_PLANT_WATER, 0.6F, false, false, false),
    SEAWEED(BlockType.WATER, 0.7F, false, false, false),

    // 1.20 Flora
    BEACHGRASS(BlockType.BEACH_GRASS, 0.8f, false, false, false),
    SEA_PALM(BlockType.DRY, 0.8f, false, false, false),
    COBBLESTONE_LICHEN(BlockType.CREEPING_STONE, 1f, false, false, false),
    FLOATING_GREEN_ALGAE(BlockType.FLOATING_FRESH, 0.9F, false, false, false),
    FLOATING_RED_ALGAE(BlockType.FLOATING, 0.7F, false, false, false);

    //public static final EnumSet<TFCFPlant> SPECIAL_POTTED_PLANTS = EnumSet.of(GLOW_LICHEN);
    public static final EnumSet<TFCFPlant> ITEM_TINTED_PLANTS = EnumSet.of(SHIELD_FERN, LICORICE_FERN, LEAF_LITTER, CAT_GRASS, CROWNGRASS, GOOSEGRASS, WHEATGRASS, SAWGRASS, CINNAMON_FERN, ELEPHANT_GRASS, LEAF_LITTER, MONSTERA, MONSTERA_EPIPHYTE, POND_GRASS, PRAIRIE_GRASS, WOOLLY_BUSH, CONIFEROUS_SHRUB, DECIDUOUS_SHRUB, HORNWORT);

    public final float speedFactor;
    @Nullable private final IntegerProperty property;
    public final int @Nullable[] stagesByMonth;
    public final BlockType type;
    public boolean isSeasonalFruitPlant;
    public Supplier<? extends Item> productItem;
    public Lifecycle[] stages;
    public final boolean conifer;
    public final boolean vine;
    public boolean isSeasonal;

    TFCFPlant(BlockType type, float speedFactor, boolean conifer, boolean vine, boolean isSeasonal)
    {
        this(type, speedFactor, null, conifer, vine, isSeasonal);
        this.isSeasonalFruitPlant = false;
        this.isSeasonal = isSeasonal;
    }

    TFCFPlant(BlockType type, float speedFactor, int @Nullable[] stagesByMonth, boolean conifer, boolean vine, boolean isSeasonal)
    {
        this.type = type;
        this.speedFactor = speedFactor;
        this.stagesByMonth = stagesByMonth;
        this.isSeasonalFruitPlant = false;
        this.isSeasonal = isSeasonal;
        this.conifer = conifer;
        this.vine = vine;

        int maxStage = 0;
        if (stagesByMonth != null)
        {
            maxStage = Arrays.stream(stagesByMonth).max().orElse(0);
        }

        this.property = maxStage > 0 ? TFCBlockStateProperties.getStageProperty(maxStage) : null;
    }

    TFCFPlant(BlockType type, float speedFactor, int @Nullable[] stagesByMonth, Supplier<? extends Item> productItem, Lifecycle[] stages, boolean conifer, boolean vine, boolean isSeasonalFruitPlant, boolean isSeasonal)
    {
        this.type = type;
        this.speedFactor = speedFactor;
        this.stagesByMonth = stagesByMonth;
        this.isSeasonalFruitPlant = true;
        this.isSeasonal = isSeasonal;
        this.conifer = conifer;
        this.productItem = productItem;
        this.stages = stages;
        this.vine = vine;

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
        return type == BlockType.VINE || isSeasonal;
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

    public boolean isVine()
    {
        return vine;
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
            case HANGING_BLUE_SKYFLOWER_PLANT -> HANGING_BLUE_SKYFLOWER;
            case HANGING_BLUE_SKYFLOWER -> HANGING_BLUE_SKYFLOWER_PLANT;
            case HANGING_JADE_VINE_PLANT -> HANGING_JADE_VINE;
            case HANGING_JADE_VINE -> HANGING_JADE_VINE_PLANT;
            case HANGING_JAPANESE_IVY_PLANT -> HANGING_JAPANESE_IVY;
            case HANGING_JAPANESE_IVY -> HANGING_JAPANESE_IVY_PLANT;
            case HANGING_MADEIRA_VINE_PLANT -> HANGING_MADEIRA_VINE;
            case HANGING_MADEIRA_VINE -> HANGING_MADEIRA_VINE_PLANT;
            case HANGING_MYSORE_TRUMPETVINE_PLANT -> HANGING_MYSORE_TRUMPETVINE;
            case HANGING_MYSORE_TRUMPETVINE -> HANGING_MYSORE_TRUMPETVINE_PLANT;
            case HANGING_SILVERVEIN_CREEPER_PLANT -> HANGING_SILVERVEIN_CREEPER;
            case HANGING_SILVERVEIN_CREEPER -> HANGING_SILVERVEIN_CREEPER_PLANT;
            case HANGING_SWEDISH_IVY_PLANT -> HANGING_SWEDISH_IVY;
            case HANGING_SWEDISH_IVY -> HANGING_SWEDISH_IVY_PLANT;
            case HANGING_VARIEGATED_PERSIAN_IVY_PLANT -> HANGING_VARIEGATED_PERSIAN_IVY;
            case HANGING_VARIEGATED_PERSIAN_IVY -> HANGING_VARIEGATED_PERSIAN_IVY_PLANT;
            case HANGING_WISTERIA_PLANT -> HANGING_WISTERIA;
            case HANGING_WISTERIA -> HANGING_WISTERIA_PLANT;
            case PONDWEED_PLANT -> PONDWEED;
            case PONDWEED -> PONDWEED_PLANT;
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
        VINE_TFCF((plant, type) -> TFCFVineBlock.create(plant, fire(nonSolid(plant)))),
        WEEPING_TFCF((plant, type) -> TFCFBodyPlantBlock.create(plant, fire(nonSolidTallPlant(plant)), plant.transform(), BodyPlantBlock.BODY_SHAPE, Direction.DOWN)),
        WEEPING_TOP_TFCF((plant, type) -> TFCFTopPlantBlock.create(plant, fire(nonSolidTallPlant(plant)), plant.transform(), Direction.DOWN, BodyPlantBlock.WEEPING_SHAPE)),
        TFCF_STANDARD((plant, type) -> TFCFPlantBlock.create(plant, fire(nonSolid(plant)))),
        CREEPING_WATER((plant, type) -> CreepingWaterPlantBlock.create(plant, fire(nonSolid(plant).hasPostProcess(TFCFBlocks::always)))),
        SPORE_BLOSSOM((plant, type) -> SporeBlossomPlantBlock.create(plant, fire(nonSolid(plant).hasPostProcess(TFCFBlocks::always)).sound(SoundType.SPORE_BLOSSOM))),
        FRUITING_WEEPING((plant, type) -> new FruitingBodyPlantBlock(ExtendedProperties.of(nonSolidTallPlant(plant).hasPostProcess(TFCFBlocks::always)).lightLevel(FruitingBodyPlantBlock.emission(14, true)).instabreak().sound(SoundType.CAVE_VINES).randomTicks(), plant.transform(), BodyPlantBlock.BODY_SHAPE, Direction.DOWN, plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        FRUITING_WEEPING_TOP((plant, type) -> new FruitingTopPlantBlock(ExtendedProperties.of(nonSolidTallPlant(plant).hasPostProcess(TFCFBlocks::always)).lightLevel(FruitingBodyPlantBlock.emission(14, true)).instabreak().sound(SoundType.CAVE_VINES).randomTicks(), plant.transform(), BodyPlantBlock.WEEPING_SHAPE, Direction.DOWN, plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        TALL_LAND_WATER((plant, type) -> TallLandWaterPlantBlock.create(plant, TFCBlockStateProperties.SALT_WATER, nonSolid(plant))),
        TALL_LAND_WATER_FRESH((plant, type) -> TallLandWaterPlantBlock.create(plant, TFCBlockStateProperties.FRESH_WATER, nonSolid(plant))),
        POISONOUS_TALL_GRASS((plant, type) -> TFCFTallGrassBlock.create(plant, fire(nonSolid(plant)))),
        SHORT_CACTUS((plant, type) -> ShortCactusBlock.create(plant, fire(solid().strength(0.25F).sound(SoundType.MOSS)).pathType(BlockPathTypes.DAMAGE_CACTUS))),
        TFCF_CACTUS((plant, type) -> TFCFCactusBlock.create(plant, fire(solid().strength(0.25F).sound(SoundType.MOSS)).pathType(BlockPathTypes.DAMAGE_CACTUS))),
        SAGUARO_CACTUS((plant, type) -> SaguaroCactusBlock.create(plant, fire(solid().strength(0.25F).sound(SoundType.MOSS).hasPostProcess(TFCFBlocks::always)).pathType(BlockPathTypes.DAMAGE_CACTUS).randomTicks(), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        PITAHAYA((plant, type) -> PitahayaBlock.create(plant, fire(solid().strength(0.25F).sound(SoundType.NETHER_WART).hasPostProcess(TFCFBlocks::always)).pathType(BlockPathTypes.DAMAGE_CACTUS).randomTicks(), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        SHORT_FRUITING_CACTUS((plant, type) -> ShortFruitingCactusBlock.create(plant, fire(solid().strength(0.25F).sound(SoundType.MOSS).hasPostProcess(TFCFBlocks::always)).pathType(BlockPathTypes.DAMAGE_CACTUS).randomTicks(), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        FRUITING_CACTUS((plant, type) -> TFCFFruitingCactusBlock.create(plant, fire(solid().strength(0.25F).sound(SoundType.MOSS).hasPostProcess(TFCFBlocks::always)).pathType(BlockPathTypes.DAMAGE_CACTUS).randomTicks(), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        FUNGI((plant, type) -> FungiBlock.create(plant, fire(nonSolid(plant)))),
        FUNGI_EPIPHYTE((plant, type) -> FungiEpiphyteBlock.create(plant, fire(nonSolid(plant).hasPostProcess(TFCFBlocks::always)))),
        FUNGI_EPIPHYTE_SOLID((plant, type) -> FungiEpiphyteSolidBlock.create(plant, fire(solid().strength(0.25F).hasPostProcess(TFCFBlocks::always)))),
        FUNGI_EPIPHYTE_HANGING((plant, type) -> FungiEpiphyteCeilingBlock.create(plant, fire(nonSolid(plant).strength(0.25F).hasPostProcess(TFCFBlocks::always)))),
        FRUITING_TALL_PLANT((plant, type) -> FruitingTallPlantBlock.create(plant, fire(nonSolid(plant).hasPostProcess(TFCFBlocks::always)).randomTicks(), plant.getProductItem(), plant.getStages(), TFCFClimateRanges.SEASONAL_PLANT.get(plant))),
        CACAO((plant, type) -> CacaoPodBlock.create(plant, fire(nonSolid(plant).hasPostProcess(TFCFBlocks::always)).randomTicks().strength(0.2F, 3.0F).sound(SoundType.BAMBOO).noOcclusion())),

        BAMBOO((plant, type) -> BambooPlantBlock.create(plant, ExtendedProperties.of(Material.BAMBOO, MaterialColor.PLANT).randomTicks().instabreak().strength(1.0F).sound(SoundType.BAMBOO).noOcclusion().dynamicShape().hasPostProcess(TFCFBlocks::always), plant.stem(), plant.sapling())),
        BAMBOO_SAPLING((plant, type) ->  BambooSaplingPlantBlock.create(plant, ExtendedProperties.of(Material.BAMBOO_SAPLING).randomTicks().instabreak().noCollission().strength(1.0F).sound(SoundType.BAMBOO_SAPLING).hasPostProcess(TFCFBlocks::always), plant.stem())),

        // Water
        KELP((plant, type) -> TFCKelpBlock.create(nonSolidTallPlant(plant).lootFrom(plant.transform()).sound(SoundType.WET_GRASS), plant.transform(), Direction.UP, BodyPlantBlock.THIN_BODY_SHAPE, TFCBlockStateProperties.SALT_WATER)),
        KELP_TOP(((plant, type) -> TFCKelpTopBlock.create(nonSolidTallPlant(plant).sound(SoundType.WET_GRASS), plant.transform(), Direction.UP, BodyPlantBlock.TWISTING_THIN_SHAPE, TFCBlockStateProperties.SALT_WATER))),
        KELP_TREE((plant, type) -> KelpTreeBlock.create(kelp(plant), TFCBlockStateProperties.SALT_WATER)),
        KELP_TREE_FLOWER((plant, type) -> KelpTreeFlowerBlock.create(kelp(plant), plant.transform())),
        FLOATING((plant, type) -> FloatingWaterPlantBlock.create(plant, TFCFluids.SALT_WATER.source(), nonSolid(plant)), WaterLilyBlockItem::new),
        FLOATING_FRESH((plant, type) -> FloatingWaterPlantBlock.create(plant, () -> Fluids.WATER, nonSolid(plant)), WaterLilyBlockItem::new),
        TALL_WATER((plant, type) -> TallWaterPlantBlock.create(plant, TFCBlockStateProperties.SALT_WATER, nonSolid(plant))),
        TALL_WATER_FRESH((plant, type) -> TallWaterPlantBlock.create(plant, TFCBlockStateProperties.FRESH_WATER, nonSolid(plant))),
        WATER((plant, type) -> WaterPlantBlock.create(plant, TFCBlockStateProperties.SALT_WATER, nonSolid(plant).sound(SoundType.WET_GRASS))),
        WATER_FRESH((plant, type) -> WaterPlantBlock.create(plant, TFCBlockStateProperties.FRESH_WATER, nonSolid(plant).sound(SoundType.WET_GRASS))),
        GRASS_WATER((plant, type) -> TFCSeagrassBlock.create(plant, TFCBlockStateProperties.SALT_WATER, nonSolid(plant).sound(SoundType.WET_GRASS))),
        GRASS_WATER_FRESH((plant, type) -> TFCSeagrassBlock.create(plant, TFCBlockStateProperties.FRESH_WATER, nonSolid(plant).sound(SoundType.WET_GRASS))),
        KELP_FRESH((plant, type) -> TFCKelpBlock.create(nonSolidTallPlant(plant).lootFrom(plant.transform()).sound(SoundType.WET_GRASS), plant.transform(), Direction.UP, BodyPlantBlock.THIN_BODY_SHAPE, TFCBlockStateProperties.FRESH_WATER)),
        KELP_TOP_FRESH(((plant, type) -> TFCKelpTopBlock.create(nonSolidTallPlant(plant).sound(SoundType.WET_GRASS), plant.transform(), Direction.UP, BodyPlantBlock.TWISTING_THIN_SHAPE, TFCBlockStateProperties.FRESH_WATER))),
        GROWING_PLANT_WATER_FRESH((plant, type) -> TFCFSeagrassBlock.create(plant, TFCBlockStateProperties.FRESH_WATER, nonSolid(plant).randomTicks().sound(SoundType.WET_GRASS))),
        GROWING_PLANT_WATER((plant, type) -> TFCFSeagrassBlock.create(plant, TFCBlockStateProperties.SALT_WATER, nonSolid(plant).randomTicks().sound(SoundType.WET_GRASS))),

        // Custom Water
        TALL_WATER_SMALL_DRIPLEAF((plant, type) -> SmallDripleafPlantBlock.create(plant, TFCBlockStateProperties.SALT_WATER, nonSolid(plant))),
        TALL_WATER_FRESH_SMALL_DRIPLEAF((plant, type) -> SmallDripleafPlantBlock.create(plant, TFCBlockStateProperties.FRESH_WATER, nonSolid(plant))),

        // 1.20
        BEACH_GRASS((plant, type) -> DesertShortGrassBlock.createBeachGrass(plant, fire(nonSolid(plant)))),
        CREEPING_STONE((plant, type) -> CreepingStonePlantBlock.createStone(plant, fire(nonSolid(plant).hasPostProcess(TFCBlocks::always))));

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
