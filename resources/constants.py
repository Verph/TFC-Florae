#  Work under Copyright. Licensed under the EUPL.
#  See the project README.md and LICENSE.txt for more information.
import csv
from typing import Dict, List, NamedTuple, Sequence, Optional, Literal, Tuple, Any

Tier = Literal['stone', 'copper', 'bronze', 'wrought_iron', 'steel', 'black_steel', 'colored_steel']
RockCategory = Literal['sedimentary', 'metamorphic', 'igneous_extrusive', 'igneous_intrusive']
BerryBushType = Literal['stationary', 'spreading', 'waterlogged']
Rock = NamedTuple('Rock', category=RockCategory, sand=str)
Metal = NamedTuple('Metal', tier=int, types=set, heat_capacity=float, melt_temperature=float, melt_metal=Optional[str])
MetalItem = NamedTuple('MetalItem', type=str, smelt_amount=int, parent_model=str, tag=Optional[str], mold=bool)
Ore = NamedTuple('Ore', metal=Optional[str], graded=bool, required_tool=Tier, tag=str)
OreGrade = NamedTuple('OreGrade', weight=int, grind_amount=int)
Vein = NamedTuple('Vein', ore=str, type=str, rarity=int, size=int, min_y=int, max_y=int, density=float, poor=float, normal=float, rich=float, rocks=List[str], spoiler_ore=str, spoiler_rarity=int, spoiler_rocks=List[str], biomes=Optional[str], height=Optional[int], deposits=bool)
Plant = NamedTuple('Plant', clay=bool, min_temp=float, max_temp=float, min_rain=float, max_rain=float, type=str)
Wood = NamedTuple('Wood', temp=float, duration=int)
Berry = NamedTuple('Berry', min_temp=float, max_temp=float, min_rain=float, max_rain=float, type=BerryBushType, min_forest=str, max_forest=str)
Fruit = NamedTuple('Fruit', min_temp=float, max_temp=float, min_rain=float, max_rain=float)
Crop = NamedTuple('Crop', type=str, stages=int)

# Melting Temps
POTTERY_MELT = 1200 - 1

# Heat Capacities
POTTERY_HC = 0.2

HORIZONTAL_DIRECTIONS: List[str] = ['east', 'west', 'north', 'south']

ROCK_CATEGORIES: List[str] = ['sedimentary', 'metamorphic', 'igneous_extrusive', 'igneous_intrusive']
ROCK_CATEGORY_ITEMS: List[str] = ['axe', 'hammer', 'hoe', 'javelin', 'knife', 'shovel']

TOOL_TAGS: Dict[str, str] = {
    # Rock
    'axe': 'axes',
    'hammer': 'hammers',
    'hoe': 'hoes',
    'javelin': 'javelins',
    'knife': 'knives',
    'shovel': 'shovels',
    # Metal Only
    'pickaxe': 'pickaxes',
    'chisel': 'chisels',
    'mace': 'maces',
    'sword': 'swords',
    'saw': 'saws',
    'propick': 'propicks',
    'scythe': 'scythes',
    'shears': 'shears',
    'tuyere': 'tuyeres'
}

ROCKS: Dict[str, Rock] = {
    'granite': Rock('igneous_intrusive', 'white'),
    'diorite': Rock('igneous_intrusive', 'white'),
    'gabbro': Rock('igneous_intrusive', 'black'),
    'shale': Rock('sedimentary', 'black'),
    'claystone': Rock('sedimentary', 'brown'),
    'limestone': Rock('sedimentary', 'white'),
    'conglomerate': Rock('sedimentary', 'green'),
    'dolomite': Rock('sedimentary', 'black'),
    'chert': Rock('sedimentary', 'yellow'),
    'chalk': Rock('sedimentary', 'white'),
    'rhyolite': Rock('igneous_extrusive', 'red'),
    'basalt': Rock('igneous_extrusive', 'red'),
    'andesite': Rock('igneous_extrusive', 'red'),
    'dacite': Rock('igneous_extrusive', 'red'),
    'quartzite': Rock('metamorphic', 'white'),
    'slate': Rock('metamorphic', 'brown'),
    'phyllite': Rock('metamorphic', 'brown'),
    'schist': Rock('metamorphic', 'green'),
    'gneiss': Rock('metamorphic', 'green'),
    'marble': Rock('metamorphic', 'yellow')
}
METALS: Dict[str, Metal] = {
    'bismuth': Metal(1, {'part'}, 0.14, 270, None),
    'bismuth_bronze': Metal(2, {'part', 'tool', 'armor', 'utility'}, 0.35, 985, None),
    'black_bronze': Metal(2, {'part', 'tool', 'armor', 'utility'}, 0.35, 1070, None),
    'bronze': Metal(2, {'part', 'tool', 'armor', 'utility'}, 0.35, 950, None),
    'brass': Metal(2, {'part'}, 0.35, 930, None),
    'copper': Metal(1, {'part', 'tool', 'armor', 'utility'}, 0.35, 1080, None),
    'gold': Metal(1, {'part'}, 0.6, 1060, None),
    'nickel': Metal(1, {'part'}, 0.48, 1453, None),
    'rose_gold': Metal(1, {'part'}, 0.35, 960, None),
    'silver': Metal(1, {'part'}, 0.48, 961, None),
    'tin': Metal(1, {'part'}, 0.14, 230, None),
    'zinc': Metal(1, {'part'}, 0.21, 420, None),
    'sterling_silver': Metal(1, {'part'}, 0.35, 950, None),
    'wrought_iron': Metal(3, {'part', 'tool', 'armor', 'utility'}, 0.35, 1535, 'cast_iron'),
    'cast_iron': Metal(1, {'part'}, 0.35, 1535, None),
    'pig_iron': Metal(3, set(), 0.35, 1535, None),
    'steel': Metal(4, {'part', 'tool', 'armor', 'utility'}, 0.35, 1540, None),
    'black_steel': Metal(5, {'part', 'tool', 'armor', 'utility'}, 0.35, 1485, None),
    'blue_steel': Metal(6, {'part', 'tool', 'armor', 'utility'}, 0.35, 1540, None),
    'red_steel': Metal(6, {'part', 'tool', 'armor', 'utility'}, 0.35, 1540, None),
    'weak_steel': Metal(4, set(), 0.35, 1540, None),
    'weak_blue_steel': Metal(5, set(), 0.35, 1540, None),
    'weak_red_steel': Metal(5, set(), 0.35, 1540, None),
    'high_carbon_steel': Metal(3, set(), 0.35, 1540, 'pig_iron'),
    'high_carbon_black_steel': Metal(4, set(), 0.35, 1540, 'weak_steel'),
    'high_carbon_blue_steel': Metal(5, set(), 0.35, 1540, 'weak_blue_steel'),
    'high_carbon_red_steel': Metal(5, set(), 0.35, 1540, 'weak_red_steel'),
    'unknown': Metal(0, set(), 0.5, 400, None)
}
METAL_BLOCKS: Dict[str, MetalItem] = {
    'anvil': MetalItem('utility', 1400, 'tfcflorae:block/anvil', None, False),
    'chain': MetalItem('utility', 100, 'tfcflorae:block/chain', None, False),
    'lamp': MetalItem('utility', 100, 'tfcflorae:block/lamp', None, False),
    'trapdoor': MetalItem('utility', 200, 'tfcflorae:block/trapdoor', None, False)
}
METAL_ITEMS: Dict[str, MetalItem] = {
    'ingot': MetalItem('all', 100, 'item/generated', 'forge:ingots', True),
    'double_ingot': MetalItem('part', 200, 'item/generated', 'forge:double_ingots', False),
    'sheet': MetalItem('part', 200, 'item/generated', 'forge:sheets', False),
    'double_sheet': MetalItem('part', 400, 'item/generated', 'forge:double_sheets', False),
    'rod': MetalItem('part', 100, 'item/generated', 'forge:rods', False),

    'tuyere': MetalItem('tool', 100, 'item/generated', None, False),
    'fish_hook': MetalItem('tool', 100, 'item/generated', None, False),
    'fishing_rod': MetalItem('tool', 100, 'item/generated', None, False),
    'pickaxe': MetalItem('tool', 100, 'item/handheld', None, False),
    'pickaxe_head': MetalItem('tool', 100, 'item/generated', None, True),
    'shovel': MetalItem('tool', 100, 'item/handheld', None, False),
    'shovel_head': MetalItem('tool', 100, 'item/generated', None, True),
    'axe': MetalItem('tool', 100, 'item/handheld', None, False),
    'axe_head': MetalItem('tool', 100, 'item/generated', None, True),
    'hoe': MetalItem('tool', 100, 'item/handheld', None, False),
    'hoe_head': MetalItem('tool', 100, 'item/generated', None, True),
    'chisel': MetalItem('tool', 100, 'item/handheld', None, False),
    'chisel_head': MetalItem('tool', 100, 'item/generated', None, True),
    'sword': MetalItem('tool', 200, 'item/handheld', None, False),
    'sword_blade': MetalItem('tool', 200, 'item/generated', None, True),
    'mace': MetalItem('tool', 200, 'item/handheld', None, False),
    'mace_head': MetalItem('tool', 200, 'item/generated', None, True),
    'saw': MetalItem('tool', 100, 'item/handheld', None, False),
    'saw_blade': MetalItem('tool', 100, 'item/generated', None, True),
    'javelin': MetalItem('tool', 100, 'item/handheld', None, False),
    'javelin_head': MetalItem('tool', 100, 'item/generated', None, True),
    'hammer': MetalItem('tool', 100, 'item/handheld', None, False),
    'hammer_head': MetalItem('tool', 100, 'item/generated', None, True),
    'propick': MetalItem('tool', 100, 'item/handheld', None, False),
    'propick_head': MetalItem('tool', 100, 'item/generated', None, True),
    'knife': MetalItem('tool', 100, 'tfcflorae:item/handheld_flipped', None, False),
    'knife_blade': MetalItem('tool', 100, 'item/generated', None, True),
    'scythe': MetalItem('tool', 100, 'item/handheld', None, False),
    'scythe_blade': MetalItem('tool', 100, 'item/generated', None, True),
    'shears': MetalItem('tool', 200, 'item/handheld', None, False),

    'unfinished_helmet': MetalItem('armor', 400, 'item/generated', None, False),
    'helmet': MetalItem('armor', 600, 'item/generated', None, False),
    'unfinished_chestplate': MetalItem('armor', 400, 'item/generated', None, False),
    'chestplate': MetalItem('armor', 800, 'item/generated', None, False),
    'unfinished_greaves': MetalItem('armor', 400, 'item/generated', None, False),
    'greaves': MetalItem('armor', 600, 'item/generated', None, False),
    'unfinished_boots': MetalItem('armor', 200, 'item/generated', None, False),
    'boots': MetalItem('armor', 400, 'item/generated', None, False),

    'shield': MetalItem('tool', 400, 'item/handheld', None, False)
}
METAL_ITEMS_AND_BLOCKS = {**METAL_ITEMS, **METAL_BLOCKS}
METAL_TOOL_HEADS = ('chisel', 'hammer', 'hoe', 'javelin', 'knife', 'mace', 'pickaxe', 'propick', 'saw', 'scythe', 'shovel', 'sword', 'axe')
ORES: Dict[str, Ore] = {
    'native_copper': Ore('copper', True, 'copper', 'copper'),
    'native_gold': Ore('gold', True, 'copper', 'gold'),
    'hematite': Ore('cast_iron', True, 'copper', 'iron'),
    'native_silver': Ore('silver', True, 'copper', 'silver'),
    'cassiterite': Ore('tin', True, 'copper', 'tin'),
    'bismuthinite': Ore('bismuth', True, 'copper', 'bismuth'),
    'garnierite': Ore('nickel', True, 'bronze', 'nickel'),
    'malachite': Ore('copper', True, 'copper', 'copper'),
    'magnetite': Ore('cast_iron', True, 'copper', 'iron'),
    'limonite': Ore('cast_iron', True, 'copper', 'iron'),
    'sphalerite': Ore('zinc', True, 'copper', 'zinc'),
    'tetrahedrite': Ore('copper', True, 'copper', 'copper'),
    'bituminous_coal': Ore(None, False, 'copper', 'coal'),
    'lignite': Ore(None, False, 'copper', 'coal'),
    'kaolinite': Ore(None, False, 'copper', 'kaolinite'),
    'gypsum': Ore(None, False, 'copper', 'gypsum'),
    'graphite': Ore(None, False, 'copper', 'graphite'),
    'sulfur': Ore(None, False, 'copper', 'sulfur'),
    'cinnabar': Ore(None, False, 'bronze', 'redstone'),
    'cryolite': Ore(None, False, 'bronze', 'redstone'),
    'saltpeter': Ore(None, False, 'copper', 'saltpeter'),
    'sylvite': Ore(None, False, 'copper', 'sylvite'),
    'borax': Ore(None, False, 'copper', 'borax'),
    'halite': Ore(None, False, 'bronze', 'halite'),
    'amethyst': Ore(None, False, 'steel', 'amethyst'),  # Mohs: 7
    'diamond': Ore(None, False, 'black_steel', 'diamond'),  # Mohs: 10
    'emerald': Ore(None, False, 'steel', 'emerald'),  # Mohs: 7.5-8
    'lapis_lazuli': Ore(None, False, 'wrought_iron', 'lapis'),  # Mohs: 5-6
    'opal': Ore(None, False, 'wrought_iron', 'opal'),  # Mohs: 5.5-6.5
    'pyrite': Ore(None, False, 'copper', 'pyrite'),
    'ruby': Ore(None, False, 'black_steel', 'ruby'),  # Mohs: 9
    'sapphire': Ore(None, False, 'black_steel', 'sapphire'),  # Mohs: 9
    'topaz': Ore(None, False, 'steel', 'topaz')  # Mohs: 8
}
ORE_GRADES: Dict[str, OreGrade] = {
    'normal': OreGrade(50, 5),
    'poor': OreGrade(30, 3),
    'rich': OreGrade(20, 7)
}
DEFAULT_FORGE_ORE_TAGS: List[str] = ['coal', 'diamond', 'emerald', 'gold', 'iron', 'lapis', 'netherite_scrap', 'quartz', 'redstone']


def vein(ore: str, vein_type: str, rarity: int, size: int, min_y: int, max_y: int, density: float, poor: float, normal: float, rich: float, rocks: List[str], spoiler_ore: Optional[str] = None, spoiler_rarity: int = 0, spoiler_rocks: List[str] = None, biomes: str = None, height: int = 0, deposits: bool = False):
    # Factory method to allow default values
    return Vein(ore, vein_type, rarity, size, min_y, max_y, density, poor, normal, rich, rocks, spoiler_ore, spoiler_rarity, spoiler_rocks, biomes, height, deposits)


def preset_vein(ore: str, vein_type: str, rocks: List[str], spoiler_ore: Optional[str] = None, spoiler_rarity: int = 0, spoiler_rocks: List[str] = None, biomes: str = None, height: int = 0, preset: Tuple[int, int, int, int, int, int, int, int] = None, deposits: bool = False):
    assert preset is not None
    return Vein(ore, vein_type, preset[0], preset[1], preset[2], preset[3], preset[4], preset[5], preset[6], preset[7], rocks, spoiler_ore, spoiler_rarity, spoiler_rocks, biomes, height, deposits)


# Default parameters for common ore veins
# rarity, size, min_y, max_y, density, poor, normal, rich
POOR_METAL_ORE = (80, 15, 0, 100, 40, 40, 30, 10)
NORMAL_METAL_ORE = (60, 20, -32, 75, 60, 20, 50, 30)
DEEP_METAL_ORE = (100, 30, -64, 30, 70, 10, 30, 60)
SURFACE_METAL_ORE = (20, 15, 60, 210, 50, 60, 30, 10)

POOR_S_METAL_ORE = (100, 12, 0, 100, 40, 60, 30, 10)
NORMAL_S_METAL_ORE = (70, 15, -32, 60, 60, 20, 50, 30)
DEEP_S_METAL_ORE = (110, 25, -64, 30, 70, 10, 30, 60)

DEEP_MINERAL_ORE = (90, 10, -48, 100, 60, 0, 0, 0)
HIGH_MINERAL_ORE = (90, 10, 0, 210, 60, 0, 0, 0)

ORE_VEINS: Dict[str, Vein] = {
    'normal_native_copper': preset_vein('native_copper', 'cluster', ['igneous_extrusive'], preset=NORMAL_METAL_ORE),
    'surface_native_copper': preset_vein('native_copper', 'cluster', ['igneous_extrusive'], preset=SURFACE_METAL_ORE, deposits=True),
    'normal_native_gold': preset_vein('native_gold', 'cluster', ['igneous_extrusive', 'igneous_intrusive'], 'pyrite', 20, ['igneous_extrusive', 'igneous_intrusive'], preset=NORMAL_S_METAL_ORE),
    'deep_native_gold': preset_vein('native_gold', 'cluster', ['igneous_extrusive', 'igneous_intrusive'], 'pyrite', 10, ['igneous_extrusive', 'igneous_intrusive'], preset=DEEP_S_METAL_ORE),
    'normal_native_silver': preset_vein('native_silver', 'cluster', ['granite', 'gneiss'], preset=NORMAL_METAL_ORE),
    'poor_native_silver': preset_vein('native_silver', 'cluster', ['granite', 'metamorphic'], preset=POOR_METAL_ORE),
    'normal_hematite': preset_vein('hematite', 'cluster', ['igneous_extrusive'], preset=NORMAL_METAL_ORE),
    'deep_hematite': preset_vein('hematite', 'cluster', ['igneous_extrusive'], preset=DEEP_METAL_ORE),
    'normal_cassiterite': preset_vein('cassiterite', 'cluster', ['igneous_intrusive'], 'topaz', 10, ['granite'], preset=NORMAL_METAL_ORE),
    'surface_cassiterite': preset_vein('cassiterite', 'cluster', ['igneous_intrusive'], 'topaz', 20, ['granite'], preset=SURFACE_METAL_ORE, deposits=True),
    'normal_bismuthinite': preset_vein('bismuthinite', 'cluster', ['igneous_intrusive', 'sedimentary'], preset=NORMAL_METAL_ORE),
    'surface_bismuthinite': preset_vein('bismuthinite', 'cluster', ['igneous_intrusive', 'sedimentary'], preset=SURFACE_METAL_ORE),
    'normal_garnierite': preset_vein('garnierite', 'cluster', ['gabbro'], preset=NORMAL_S_METAL_ORE),
    'poor_garnierite': preset_vein('garnierite', 'cluster', ['igneous_intrusive'], preset=POOR_S_METAL_ORE),
    'normal_malachite': preset_vein('malachite', 'cluster', ['marble', 'limestone'], 'gypsum', 10, ['limestone'], preset=NORMAL_METAL_ORE),
    'poor_malachite': preset_vein('malachite', 'cluster', ['marble', 'limestone', 'phyllite', 'chalk', 'dolomite'], 'gypsum', 20, ['limestone'], preset=POOR_METAL_ORE),
    'normal_magnetite': preset_vein('magnetite', 'cluster', ['sedimentary'], preset=NORMAL_METAL_ORE),
    'deep_magnetite': preset_vein('magnetite', 'cluster', ['sedimentary'], preset=DEEP_METAL_ORE),
    'normal_limonite': preset_vein('limonite', 'cluster', ['sedimentary'], 'ruby', 20, ['limestone', 'shale'], preset=NORMAL_METAL_ORE),
    'deep_limonite': preset_vein('limonite', 'cluster', ['sedimentary'], 'ruby', 10, ['limestone', 'shale'], preset=DEEP_METAL_ORE),
    'normal_sphalerite': preset_vein('sphalerite', 'cluster', ['metamorphic'], preset=NORMAL_METAL_ORE),
    'surface_sphalerite': preset_vein('sphalerite', 'cluster', ['metamorphic'], preset=SURFACE_METAL_ORE),
    'normal_tetrahedrite': preset_vein('tetrahedrite', 'cluster', ['metamorphic'], preset=NORMAL_METAL_ORE),
    'surface_tetrahedrite': preset_vein('tetrahedrite', 'cluster', ['metamorphic'], preset=SURFACE_METAL_ORE),

    'bituminous_coal': preset_vein('bituminous_coal', 'cluster', ['sedimentary'], preset=HIGH_MINERAL_ORE),
    'lignite': preset_vein('lignite', 'cluster', ['sedimentary'], preset=DEEP_MINERAL_ORE),
    'kaolinite': preset_vein('kaolinite', 'cluster', ['sedimentary'], preset=HIGH_MINERAL_ORE),
    'graphite': preset_vein('graphite', 'cluster', ['gneiss', 'marble', 'quartzite', 'schist'], preset=DEEP_MINERAL_ORE),
    'cinnabar': preset_vein('cinnabar', 'cluster', ['igneous_extrusive', 'quartzite', 'shale'], 'opal', 10, ['quartzite'], preset=DEEP_MINERAL_ORE),
    'cryolite': preset_vein('cryolite', 'cluster', ['granite'], preset=DEEP_MINERAL_ORE),
    'saltpeter': preset_vein('saltpeter', 'cluster', ['sedimentary'], 'gypsum', 20, ['limestone'], preset=DEEP_MINERAL_ORE),
    'sulfur': preset_vein('sulfur', 'cluster', ['igneous_extrusive'], 'gypsum', 20, ['rhyolite'], preset=HIGH_MINERAL_ORE),
    'sylvite': preset_vein('sylvite', 'cluster', ['shale', 'claystone', 'chert'], preset=HIGH_MINERAL_ORE),
    'borax': preset_vein('borax', 'cluster', ['slate'], preset=HIGH_MINERAL_ORE),
    'gypsum': vein('gypsum', 'disc', 120, 20, 30, 90, 60, 0, 0, 0, ['metamorphic']),
    'lapis_lazuli': preset_vein('lapis_lazuli', 'cluster', ['limestone', 'marble'], preset=DEEP_MINERAL_ORE),
    'halite': vein('halite', 'disc', 120, 30, 30, 90, 80, 0, 0, 0, ['sedimentary']),
    'diamond': vein('diamond', 'pipe', 60, 60, -64, 100, 40, 0, 0, 0, ['gabbro']),
    'emerald': vein('emerald', 'pipe', 80, 60, -64, 100, 40, 0, 0, 0, ['igneous_intrusive']),
    'volcanic_sulfur': vein('sulfur', 'disc', 25, 14, 80, 180, 40, 0, 0, 0, ['igneous_extrusive', 'igneous_intrusive'], biomes='#tfcflorae:is_volcanic', height=6),
    'amethyst': vein('amethyst', 'disc', 14, 8, 40, 60, 20, 0, 0, 0, ['sedimentary', 'metamorphic'], biomes='#tfcflorae:is_river', height=4),
    'opal': vein('opal', 'disc', 14, 8, 40, 60, 20, 0, 0, 0, ['sedimentary', 'igneous_extrusive'], biomes='#tfcflorae:is_river', height=4)
}

DEPOSIT_RARES: Dict[str, str] = {
    'granite': 'topaz',
    'diorite': 'emerald',
    'gabbro': 'diamond',
    'shale': 'borax',
    'claystone': 'amethyst',
    'limestone': 'lapis_lazuli',
    'conglomerate':'lignite',
    'dolomite': 'amethyst',
    'chert': 'ruby',
    'chalk': 'sapphire',
    'rhyolite': 'pyrite',
    'basalt': 'pyrite',
    'andesite': 'pyrite',
    'dacite': 'pyrite',
    'quartzite': 'opal',
    'slate': 'pyrite',
    'phyllite': 'pyrite',
    'schist': 'pyrite',
    'gneiss': 'gypsum',
    'marble': 'lapis_lazuli'
}

ROCK_BLOCK_TYPES = ('raw', 'hardened', 'bricks', 'cobble', 'gravel', 'smooth', 'mossy_cobble', 'mossy_bricks', 'cracked_bricks', 'chiseled', 'spike', 'loose', 'pressure_plate', 'button')
ROCK_BLOCKS_IN_JSON = ('raw', 'hardened', 'cobble', 'gravel', 'spike', 'loose')
CUTTABLE_ROCKS = ('raw', 'bricks', 'cobble', 'smooth', 'mossy_cobble', 'mossy_bricks', 'cracked_bricks')
ROCK_SPIKE_PARTS = ('base', 'middle', 'tip')
SAND_BLOCK_TYPES = ('brown', 'white', 'black', 'red', 'yellow', 'green', 'pink')
SANDSTONE_BLOCK_TYPES = ('raw', 'smooth', 'cut')
SOIL_BLOCK_TYPES = ('dirt', 'grass', 'grass_path', 'clay', 'clay_grass', 'farmland', 'rooted_dirt', 'mud', 'mud_bricks', 'drying_bricks')
SOIL_BLOCK_VARIANTS = ('silt', 'loam', 'sandy_loam', 'silty_loam')
ORE_DEPOSITS = ('native_copper', 'cassiterite', 'native_silver', 'native_gold')

GEMS = ('amethyst', 'diamond', 'emerald', 'lapis_lazuli', 'opal', 'pyrite', 'ruby', 'sapphire', 'topaz')

MISC_GROUNDCOVER = ['bone', 'clam', 'driftwood', 'mollusk', 'mussel', 'pinecone', 'seaweed', 'stick', 'dead_grass', 'feather', 'flint', 'guano', 'humus', 'rotten_flesh', 'salt_lick']

COLORS = ('white', 'orange', 'magenta', 'light_blue', 'yellow', 'lime', 'pink', 'gray', 'light_gray', 'cyan', 'purple', 'blue', 'brown', 'green', 'red', 'black')

SIMPLE_FLUIDS = ('brine', 'curdled_milk', 'limewater', 'lye', 'milk_vinegar', 'olive_oil', 'olive_oil_water', 'tallow', 'tannin', 'vinegar')
ALCOHOLS = ('beer', 'cider', 'rum', 'sake', 'vodka', 'whiskey', 'corn_whiskey', 'rye_whiskey')

WOODS: Dict[str, Wood] = {
    'african_padauk': Wood(745, 1500),
    'alder': Wood(601, 1000),
    'angelim': Wood(773, 1200),
    #'bald_cypress': Wood(770, 1300),
    'baobab': Wood(478, 1000),
    'beech': Wood(703, 1750),
    'black_walnut': Wood(758, 1800),
    'buxus': Wood(683, 1500),
    'brazilwood': Wood(710, 1000),
    'butternut': Wood(758, 1800),
    'cherry_blossom': Wood(795, 1250),
    'cocobolo': Wood(773, 1000),
    #'cypress': Wood(783, 1100),
    'ebony': Wood(795, 1000),
    'eucalyptus': Wood(705, 1000),
    'common_oak': Wood(728, 2250),
    'fever': Wood(590, 1000),
    'ginkgo': Wood(710, 1000),
    'greenheart': Wood(793, 1700),
    'hawthorn': Wood(683, 1500),
    'hazel': Wood(683, 1500),
    'hemlock': Wood(609, 1000),
    'holly': Wood(609, 1000),
    'hornbeam': Wood(728, 2250),
    'ipe': Wood(785, 1200),
    'iroko': Wood(785, 1200),
    'ironwood': Wood(694, 1970),
    'jacaranda': Wood(795, 1250),
    #'joshua_tree': Wood(696, 1250),
    'juniper': Wood(632, 1750),
    'kauri': Wood(730, 1250),
    'larch': Wood(632, 1250),
    'limba': Wood(710, 1000),
    'locust': Wood(653, 1750),
    'logwood': Wood(695, 1000),
    'maclura': Wood(773, 1930),
    'mahoe': Wood(783, 1100),
    'mahogany': Wood(773, 1000),
    #'mangrove': Wood(783, 1100),
    'marblewood': Wood(837, 1200),
    'messmate': Wood(696, 1250),
    'mountain_ash': Wood(696, 1250),
    'mulberry': Wood(705, 1860),
    #'nordmann_fir': Wood(628, 1500),
    #'norway_spruce': Wood(628, 1500),
    'pink_ivory': Wood(773, 1000),
    'poplar': Wood(609, 1000),
    'purpleheart': Wood(793, 1700),
    'red_cedar': Wood(618, 1750),
    'red_elm': Wood(618, 1750),
    'redwood': Wood(618, 1750),
    'rowan': Wood(645, 2000),
    'rubber_fig': Wood(785, 1440),
    'sweetgum': Wood(745, 2000),
    'syzygium': Wood(745, 2000),
    'teak': Wood(695, 1000),
    'walnut': Wood(758, 1800),
    'wenge': Wood(773, 1250),
    'white_elm': Wood(653, 1750),
    'whitebeam': Wood(728, 1750),
    'yellow_meranti': Wood(837, 1200),
    'yew': Wood(813, 2150),
    'zebrawood': Wood(822, 1570)
}

CROPS: Dict[str, Crop] = {
    'barley': Crop('default', 8),
    'oat': Crop('default', 8),
    'rye': Crop('default', 8),
    'maize': Crop('double', 6),
    'wheat': Crop('default', 8),
    'rice': Crop('default', 8),
    'beet': Crop('default', 6),
    'cabbage': Crop('default', 6),
    'carrot': Crop('default', 5),
    'garlic': Crop('default', 5),
    'green_bean': Crop('double_stick', 8),
    'potato': Crop('default', 7),
    'onion': Crop('default', 7),
    'soybean': Crop('default', 7),
    'squash': Crop('default', 8),
    'sugarcane': Crop('double', 8),
    'tomato': Crop('double_stick', 8),
    'jute': Crop('double', 6)
}

PLANTS: Dict[str, Plant] = {
    'athyrium_fern': Plant(True, -10, 14, 270, 500, 'standard'),
    'canna': Plant(True, 10, 40, 270, 500, 'standard'),
    'goldenrod': Plant(True, -16, 6, 75, 310, 'standard'),
    'pampas_grass': Plant(True, 12, 40, 0, 300, 'tall_grass'),
    'perovskia': Plant(True, -6, 12, 0, 270, 'dry'),

    'bluegrass': Plant(False, -4, 12, 110, 280, 'short_grass'),
    'bromegrass': Plant(False, 4, 20, 140, 360, 'short_grass'),
    'fountain_grass': Plant(False, 0, 26, 75, 150, 'short_grass'),
    'manatee_grass': Plant(False, 12, 40, 250, 500, 'grass_water'),
    'orchard_grass': Plant(False, -30, 10, 75, 300, 'short_grass'),
    'ryegrass': Plant(False, -24, 40, 150, 320, 'short_grass'),
    'scutch_grass': Plant(False, 0, 40, 150, 500, 'short_grass'),
    'star_grass': Plant(False, 2, 40, 50, 260, 'grass_water'),
    'timothy_grass': Plant(False, -22, 16, 289, 500, 'short_grass'),
    'raddia_grass': Plant(False, 18, 40, 330, 500, 'short_grass'),

    'allium': Plant(False, -10, -2, 150, 400, 'standard'),
    'anthurium': Plant(False, 12, 40, 290, 500, 'standard'),
    'arrowhead': Plant(False, -10, 22, 180, 500, 'emergent_fresh'),
    'houstonia': Plant(False, -12, 10, 150, 500, 'standard'),
    'badderlocks': Plant(False, -18, 2, 150, 500, 'emergent'),
    'barrel_cactus': Plant(False, 4, 18, 0, 85, 'cactus'),
    'blood_lily': Plant(False, 8, 18, 200, 500, 'standard'),
    'blue_orchid': Plant(False, 10, 40, 250, 390, 'standard'),
    'blue_ginger': Plant(False, 16, 26, 300, 450, 'standard'),
    'cattail': Plant(False, -16, 22, 150, 500, 'emergent_fresh'),
    'calendula': Plant(False, 4, 22, 130, 300, 'standard'),
    'laminaria': Plant(False, -24, -2, 100, 500, 'water'),
    'marigold': Plant(False, -8, 18, 50, 390, 'emergent_fresh'),
    'bur_reed': Plant(False, -16, 4, 250, 400, 'emergent_fresh'),
    'butterfly_milkweed': Plant(False, -16, 18, 75, 300, 'standard'),
    'black_orchid': Plant(False, 14, 40, 290, 410, 'standard'),
    'coontail': Plant(False, 2, 18, 250, 500, 'grass_water_fresh'),
    'dandelion': Plant(False, -22, 40, 120, 400, 'standard'),
    'dead_bush': Plant(False, -12, 40, 0, 120, 'dry'),
    'desert_flame': Plant(False, 0, 20, 40, 170, 'standard'),
    'duckweed': Plant(False, -18, 2, 0, 500, 'floating_fresh'),
    'eel_grass': Plant(False, 6, 40, 200, 500, 'grass_water_fresh'),
    'field_horsetail': Plant(False, -12, 20, 300, 500, 'standard'),
    'foxglove': Plant(False, -8, 16, 150, 300, 'tall_plant'),
    'grape_hyacinth': Plant(False, -10, 10, 150, 250, 'standard'),
    'gutweed': Plant(False, -6, 18, 100, 500, 'water'),
    'heliconia': Plant(False, 14, 40, 320, 500, 'standard'),
    'hibiscus': Plant(False, 10, 24, 260, 450, 'tall_plant'),
    'kangaroo_paw': Plant(False, 14, 40, 100, 300, 'standard'),
    'king_fern': Plant(False, 18, 40, 350, 500, 'tall_plant'),
    'labrador_tea': Plant(False, -18, 0, 200, 380, 'standard'),
    'lady_fern': Plant(False, -10, 8, 200, 500, 'standard'),
    'licorice_fern': Plant(False, 2, 10, 300, 400, 'epiphyte'),
    'lilac': Plant(False, -10, 6, 150, 300, 'tall_plant'),
    'lotus': Plant(False, -4, 18, 0, 500, 'floating_fresh'),
    'meads_milkweed': Plant(False, -10, 2, 130, 380, 'standard'),
    'milfoil': Plant(False, -14, 22, 250, 500, 'water_fresh'),
    'morning_glory': Plant(False, -11, 19, 300, 500, 'creeping'),
    'moss': Plant(False, -7, 30, 250, 450, 'creeping'),
    'nasturtium': Plant(False, 6, 22, 150, 380, 'standard'),
    'ostrich_fern': Plant(False, -14, 6, 290, 470, 'tall_plant'),
    'oxeye_daisy': Plant(False, -14, 10, 120, 300, 'standard'),
    'phragmite': Plant(False, -6, 18, 50, 250, 'emergent_fresh'),
    'pickerelweed': Plant(False, -14, 16, 200, 500, 'emergent_fresh'),
    'pistia': Plant(False, 6, 26, 0, 400, 'floating_fresh'),
    'poppy': Plant(False, -12, 14, 150, 250, 'standard'),
    'primrose': Plant(False, -8, 10, 150, 300, 'standard'),
    'pulsatilla': Plant(False, -10, 2, 50, 200, 'standard'),
    'red_sealing_wax_palm': Plant(False, 18, 40, 280, 500, 'tall_plant'),
    'reindeer_lichen': Plant(False, -24, -8, 50, 470, 'creeping'),
    'rose': Plant(True, -5, 20, 150, 300, 'tall_plant'),
    'sacred_datura': Plant(False, 4, 18, 75, 150, 'standard'),
    'sagebrush': Plant(False, -10, 14, 0, 120, 'dry'),
    'sago': Plant(False, -18, 18, 200, 500, 'water_fresh'),
    'sapphire_tower': Plant(False, 10, 22, 75, 200, 'tall_plant'),
    'sargassum': Plant(False, -10, 16, 0, 500, 'floating'),
    'guzmania': Plant(False, 20, 40, 290, 480, 'epiphyte'),
    'silver_spurflower': Plant(False, 14, 24, 230, 400, 'standard'),
    'snapdragon_pink': Plant(False, 16, 24, 150, 300, 'standard'),
    'snapdragon_red': Plant(False, 12, 20, 150, 300, 'standard'),
    'snapdragon_white': Plant(False, 8, 16, 150, 300, 'standard'),
    'snapdragon_yellow': Plant(False, 6, 24, 150, 300, 'standard'),
    'spanish_moss': Plant(False, 8, 22, 400, 500, 'hanging'),
    'strelitzia': Plant(False, 14, 26, 50, 300, 'standard'),
    'switchgrass': Plant(False, -6, 22, 110, 390, 'tall_grass'),
    'sword_fern': Plant(False, -12, 12, 100, 500, 'standard'),
    'tall_fescue_grass': Plant(False, -10, 10, 280, 430, 'tall_grass'),
    'toquilla_palm': Plant(False, 16, 40, 250, 500, 'tall_plant'),
    'trillium': Plant(False, -10, 8, 250, 500, 'standard'),
    'tropical_milkweed': Plant(False, 8, 24, 120, 300, 'standard'),
    'tulip_orange': Plant(False, 2, 10, 200, 400, 'standard'),
    'tulip_pink': Plant(False, -6, 2, 200, 400, 'standard'),
    'tulip_red': Plant(False, 0, 4, 200, 400, 'standard'),
    'tulip_white': Plant(False, -12, -4, 200, 400, 'standard'),
    'turtle_grass': Plant(False, 14, 40, 240, 500, 'grass_water'),
    'vriesea': Plant(False, 14, 40, 200, 400, 'epiphyte'),
    'water_canna': Plant(True, 0, 36, 150, 500, 'floating_fresh'),
    'water_lily': Plant(False, -12, 40, 0, 500, 'floating_fresh'),
    'water_taro': Plant(False, 12, 40, 260, 500, 'emergent_fresh'),
    'yucca': Plant(False, -4, 22, 0, 75, 'dry'),
}
UNIQUE_PLANTS: List[str] = ['hanging_vines_plant', 'hanging_vines', 'liana_plant', 'liana', 'tree_fern_plant', 'tree_fern', 'arundo_plant', 'arundo', 'dry_phragmite', 'dry_phragmite_plant', 'winged_kelp_plant', 'winged_kelp', 'leafy_kelp_plant', 'leafy_kelp', 'giant_kelp_plant', 'giant_kelp_flower', 'ivy', 'jungle_vines']
SEAWEED: List[str] = ['sago', 'gutweed', 'laminaria', 'milfoil']
CORALS: List[str] = ['tube', 'brain', 'bubble', 'fire', 'horn']
CORAL_BLOCKS: List[str] = ['dead_coral', 'dead_coral', 'dead_coral_fan', 'coral_fan', 'dead_coral_wall_fan', 'coral_wall_fan']

PLANT_COLORS: Dict[str, List[str]] = {
    'white': ['houstonia', 'oxeye_daisy', 'primrose', 'snapdragon_white', 'trillium', 'spanish_moss', 'tulip_white'],
    'orange': ['butterfly_milkweed', 'canna', 'nasturtium', 'strelitzia', 'tulip_orange', 'water_canna'],
    'magenta': ['athyrium_fern', 'morning_glory', 'pulsatilla'],
    'light_blue': ['labrador_tea', 'sapphire_tower'],
    'yellow': ['calendula', 'dandelion', 'meads_milkweed', 'goldenrod', 'snapdragon_yellow'],
    'lime': ['moss'],
    'pink': ['foxglove', 'sacred_datura', 'tulip_pink', 'snapdragon_pink'],
    'light_gray': ['yucca'],
    'purple': ['allium', 'black_orchid', 'perovskia'],
    'blue': ['blue_orchid', 'grape_hyacinth'],
    'brown': ['field_horsetail', 'sargassum'],
    'green': ['barrel_cactus', 'reindeer_lichen'],
    'red': ['guzmania', 'poppy', 'rose', 'snapdragon_red', 'tropical_milkweed', 'tulip_red', 'vriesea']
}

COLOR_COMBOS = [
    ('red', 'yellow', 'orange'),
    ('blue', 'white', 'light_blue'),
    ('purple', 'pink', 'magenta'),
    ('red', 'white', 'pink'),
    ('white', 'gray', 'light_gray'),
    ('white', 'black', 'gray'),
    ('green', 'white', 'lime'),
    ('green', 'blue', 'cyan'),
    ('red', 'blue', 'purple'),
    ('yellow', 'blue', 'green')
]

SIMPLE_BLOCKS = ('peat', 'aggregate', 'fire_bricks', 'fire_clay_block', 'thatch')
SIMPLE_ITEMS = ('alabaster_brick', 'blubber', 'brass_mechanisms', 'burlap_cloth', 'compost', 'daub', 'dirty_jute_net', 'fire_clay', 'firestarter', 'glass_shard', 'glow_arrow', 'glue',
                'halter', 'jute', 'jute_fiber', 'jute_net', 'mortar', 'olive_paste', 'pure_nitrogen', 'pure_phosphorus', 'pure_potassium', 'rotten_compost', 'silk_cloth', 'spindle',
                'stick_bunch', 'stick_bundle', 'straw', 'wool', 'wool_cloth', 'wool_yarn', 'wrought_iron_grill')
GENERIC_POWDERS = ('charcoal', 'coke', 'graphite', 'hematite', 'kaolinite', 'limonite', 'malachite', 'sylvite')
POWDERS = ('flux', 'salt', 'saltpeter', 'sulfur', 'wood_ash')
VANILLA_DYED_ITEMS = ('wool', 'carpet', 'bed', 'terracotta', 'stained_glass', 'stained_glass_pane', 'banner', 'glazed_terracotta')
SIMPLE_POTTERY = ('bowl', 'fire_brick', 'pot', 'spindle_head', 'vessel')
SIMPLE_UNFIRED_POTTERY = ('brick', 'crucible', 'flower_pot', 'jug', 'pan')
VANILLA_TOOL_MATERIALS = ('netherite', 'diamond', 'iron', 'stone', 'wooden', 'golden')
SHORE_DECORATORS = ('driftwood', 'clam', 'mollusk', 'mussel', 'seaweed', 'sticks_shore', 'guano')
FOREST_DECORATORS = ('sticks_forest', 'pinecone', 'salt_lick', 'dead_grass', 'humus', 'rotten_flesh')
OCEAN_PLANT_TYPES = ('grass_water', 'floating', 'water', 'emergent', 'tall_water')
MISC_PLANT_FEATURES = ('hanging_vines', 'hanging_vines_cave', 'ivy', 'jungle_vines', 'liana', 'moss_cover_patch', 'reindeer_lichen_cover_patch', 'morning_glory_cover_patch', 'tree_fern', 'arundo')
SURFACE_GRASS_FEATURES = ('fountain_', 'orchard_', 'rye', 'scutch_', 'timothy_', 'brome', 'blue', 'raddia_')
UNDERGROUND_FEATURES = ('cave_spike', 'large_cave_spike', 'water_spring', 'lava_spring', 'calcite', 'mega_calcite', 'icicle', 'underground_loose_rocks', 'underground_guano_patch', 'hanging_roots_patch')

BERRIES: Dict[str, Berry] = {
    'blackberry': Berry(7, 24, 100, 500, 'spreading', 'edge', 'edge'),
    'raspberry': Berry(5, 25, 100, 500, 'spreading', 'edge', 'edge'),
    'blueberry': Berry(7, 29, 100, 500, 'spreading', 'edge', 'edge'),
    'elderberry': Berry(10, 33, 100, 500, 'spreading', 'edge', 'edge'),
    'bunchberry': Berry(15, 35, 100, 500, 'stationary', 'edge', 'normal'),
    'gooseberry': Berry(5, 27, 100, 500, 'stationary', 'none', 'sparse'),
    'snowberry': Berry(-5, 18, 100, 500, 'stationary', 'normal', 'old_growth'),
    'cloudberry': Berry(3, 17, 80, 500, 'stationary', 'normal', 'old_growth'),
    'strawberry': Berry(5, 28, 100, 500, 'stationary', 'none', 'sparse'),
    'wintergreen_berry': Berry(-5, 17, 100, 500, 'stationary', 'old_growth', 'old_growth'),
    'cranberry': Berry(-5, 17, 250, 500, 'waterlogged', 'edge', 'old_growth')
}

FRUITS: Dict[str, Fruit] = {
    'banana': Fruit(23, 35, 280, 480),
    'cherry': Fruit(5, 21, 100, 350),
    'green_apple': Fruit(8, 25, 110, 280),
    'lemon': Fruit(10, 30, 180, 470),
    'olive': Fruit(13, 30, 150, 380),
    'orange': Fruit(23, 36, 250, 480),
    'peach': Fruit(9, 27, 60, 230),
    'plum': Fruit(18, 31, 250, 400),
    'red_apple': Fruit(9, 25, 100, 280)
}
NORMAL_FRUIT_TREES: List[str] = [k for k in FRUITS.keys() if k != 'banana']

GRAINS = ('barley', 'maize', 'oat', 'rice', 'rye', 'wheat')
GRAIN_SUFFIXES = ('', '_grain', '_flour', '_dough', '_bread')
VEGETABLES = ('beet', 'cabbage', 'carrot', 'garlic', 'green_bean', 'green_bell_pepper', 'onion', 'potato', 'red_bell_pepper', 'soybean', 'squash', 'tomato', 'yellow_bell_pepper', 'cheese', 'cooked_egg', 'dried_seaweed', 'dried_kelp', 'cattail_root', 'taro_root', 'sugarcane')
MEATS = ('beef', 'pork', 'chicken', 'mutton', 'bear', 'horse_meat', 'pheasant', 'venison', 'wolf', 'rabbit', 'hyena', 'duck', 'chevon', 'gran_feline', 'camelidae', 'cod', 'bluegill', 'salmon', 'tropical_fish', 'turtle', 'calamari', 'shellfish')
NUTRIENTS = ('grain', 'fruit', 'vegetables', 'protein', 'dairy')

SPAWN_EGG_ENTITIES = ('isopod', 'lobster', 'crayfish', 'cod', 'pufferfish', 'tropical_fish', 'jellyfish', 'orca', 'dolphin', 'salmon', 'bluegill', 'manatee', 'penguin', 'turtle', 'vulture', 'horseshoe_crab', 'polar_bear', 'grizzly_bear', 'black_bear', 'cougar', 'panther', 'lion', 'sabertooth', 'squid', 'octopoteuthis', 'pig', 'cow', 'alpaca', 'chicken')
BUCKETABLE_FISH = ('cod', 'pufferfish', 'tropical_fish', 'jellyfish', 'salmon', 'bluegill')

BLOCK_ENTITIES = ('log_pile', 'burning_log_pile', 'placed_item', 'pit_kiln', 'charcoal_forge', 'quern', 'scraping', 'crucible', 'bellows', 'composter', 'chest', 'trapped_chest', 'barrel', 'loom', 'sluice', 'tool_rack', 'sign', 'lamp', 'berry_bush', 'crop', 'firepit', 'pot', 'grill', 'pile', 'farmland', 'tick_counter', 'nest_box', 'bloomery', 'bloom', 'anvil', 'ingot_pile', 'sheet_pile', 'blast_furnace', 'large_vessel')
TANNIN_WOOD_TYPES = ('red_cedar', 'syzygium', 'juniper', 'sweetgum', 'beech', 'common_oak', 'hornbeam', 'red_elm', 'white_elm', 'whitebeam', 'redwood')

def spawner(entity: str, weight: int = 1, min_count: int = 1, max_count: int = 4) -> Dict[str, Any]:
    return {
        'type': entity,
        'weight': weight,
        'minCount': min_count,
        'maxCount': max_count
    }

DISABLED_VANILLA_RECIPES = ('flint_and_steel', 'turtle_helmet', 'campfire', 'bucket', 'composter', 'tinted_glass', 'enchanting_table', 'bowl', 'blaze_rod', 'bone_meal', 'flower_pot', 'painting', 'torch', 'soul_torch', 'sticky_piston', 'clock', 'compass', 'wool', 'hay_block', 'anvil', 'wheat', 'lapis_lazuli')
ARMOR_SECTIONS = ('chestplate', 'leggings', 'boots', 'helmet')
VANILLA_ARMOR_TYPES = ('leather', 'golden', 'iron', 'diamond', 'netherite')
VANILLA_TOOLS = ('sword', 'shovel', 'pickaxe', 'axe', 'hoe')

# This is here because it's used all over, and it's easier to import with all constants
def lang(key: str, *args) -> str:
    return ((key % args) if len(args) > 0 else key).replace('_', ' ').replace('/', ' ').title()


def lang_enum(name: str, values: Sequence[str]) -> Dict[str, str]:
    return dict(('tfcflorae.enum.%s.%s' % (name, value), lang(value)) for value in values)


# This is here as it's used only once in a generic lang call by generate_resources.py
DEFAULT_LANG = {
    # Entities
    # 'entity.tfcflorae.cod': 'Cod', # Placeholder for silkmoth
    **{'entity.tfcflorae.boat.%s' % wood : lang('%s boat', wood) for wood in WOODS.keys()}
}


# Automatically Generated by generate_trees.py
def Read_CSV(filename: str) -> Dict[str, float]:
    with open(filename, 'r') as f:
        reader = csv.reader(f)
        next(reader, None)
        return {row[0].strip("'"): float(row[1]) for row in reader}
    
TREE_SAPLING_DROP_CHANCES = Read_CSV("Tree-sapling-drop.csv")
#TREE_SAPLING_DROP_CHANCES = {
#    'african_padauk': 0.0067,
#    'alder': 0.0153,
#    'angelim': 0.0067,
#    'bald_cypress': 0.0388,
#    'baobab': 0.0067,
#    'beech': 0.0153,
#    'black_walnut': 0.0557,
#    'buxus': 0.0388,
#    'brazilwood': 0.0067,
#    'butternut': 0.0557,
#    'cherry_blossom': 0.0153,
#    'cocobolo': 0.0067,
#    'cypress': 0.0170,
#    'ebony': 0.0557,
#    'eucalyptus': 0.0388,
#    'common_oak': 0.0057,
#    'fever': 0.0210,
#    'ginkgo': 0.0067,
#    'greenheart': 0.0067,
#    'hawthorn': 0.0388,
#    'hazel': 0.0388,
#    'hemlock': 0.0651,
#    'holly': 0.0651,
#    'hornbeam': 0.0057,
#    'ipe': 0.0306,
#    'iroko': 0.0306,
#    'ironwood': 0.0153,
#    'jacaranda': 0.0153,
#    'joshua_tree': 0.0651,
#    'juniper': 0.0170,
#    'kauri': 0.0067,
#    'larch': 0.0153,
#    'limba': 0.0067,
#    'locust': 0.0153,
#    'logwood': 0.0057,
#    'maclura': 0.0092,
#    'mahoe': 0.0170,
#    'mahogany': 0.0067,
#    'mangrove': 0.0170,
#    'marblewood': 0.0057,
#    'messmate': 0.0651,
#    'mountain_ash': 0.0651,
#    'mulberry': 0.0306,
#    'nordmann_fir': 0.0388,
#    'norway_spruce': 0.0388,
#    'pink_ivory': 0.0067,
#    'poplar': 0.0651,
#    'purpleheart': 0.0067,
#    'red_cedar': 0.0170,
#    'red_elm': 0.0170,
#    'redwood': 0.0170,
#    'rowan': 0.0057,
#    'rubber_fig': 0.0067,
#    'sweetgum': 0.0067,
#    'syzygium': 0.0067,
#    'teak': 0.0057,
#    'walnut': 0.0557,
#    'wenge': 0.0057,
#    'white_elm': 0.0153,
#    'whitebeam': 0.0153,
#    'yellow_meranti': 0.0057,
#    'yew': 0.0092,
#    'zebrawood': 0.0153
#}
