#  Work under Copyright. Licensed under the EUPL.
#  See the project README.md and LICENSE.txt for more information.

from enum import Enum, auto

from mcresources import ResourceManager, utils, loot_tables

from constants import *
from recipes import fluid_ingredient


class Size(Enum):
    tiny = auto()
    very_small = auto()
    small = auto()
    normal = auto()
    large = auto()
    very_large = auto()
    huge = auto()


class Weight(Enum):
    very_light = auto()
    light = auto()
    medium = auto()
    heavy = auto()
    very_heavy = auto()


class Category(Enum):
    fruit = auto()
    vegetable = auto()
    grain = auto()
    bread = auto()
    dairy = auto()
    meat = auto()
    cooked_meat = auto()
    other = auto()


def generate(rm: ResourceManager):
    # Metal Items

    for metal, metal_data in METALS.items():

        # Metal
        rm.data(('tfcflorae', 'metals', metal), {
            'tier': metal_data.tier,
            'fluid': 'tfcflorae:metal/%s' % metal,
            'melt_temperature': metal_data.melt_temperature,
            'heat_capacity': metal_data.heat_capacity,
            'ingots': utils.ingredient('#forge:ingots/%s' % metal),
            'sheets': utils.ingredient('#forge:sheets/%s' % metal)
        })

        # Metal Items and Blocks
        for item, item_data in METAL_ITEMS_AND_BLOCKS.items():
            if item_data.type in metal_data.types or item_data.type == 'all':
                if item_data.tag is not None:
                    rm.item_tag(item_data.tag + '/' + metal, 'tfcflorae:metal/%s/%s' % (item, metal))
                    ingredient = utils.item_stack('#%s/%s' % (item_data.tag, metal))
                else:
                    ingredient = utils.item_stack('tfcflorae:metal/%s/%s' % (item, metal))

                item_heat(rm, ('metal', metal + '_' + item), ingredient, metal_data.heat_capacity, metal_data.melt_temperature)
                if 'tool' in metal_data.types and item == 'fishing_rod':
                    rm.item_tag('forge:fishing_rods', 'tfcflorae:metal/%s/%s' % (item, metal))

    for ore, ore_data in ORES.items():
        if ore_data.metal and ore_data.graded:
            metal_data = METALS[ore_data.metal]
            item_heat(rm, ('ore', ore), ['tfcflorae:ore/small_%s' % ore, 'tfcflorae:ore/normal_%s' % ore, 'tfcflorae:ore/poor_%s' % ore, 'tfcflorae:ore/rich_%s' % ore], metal_data.heat_capacity, int(metal_data.melt_temperature))

    rm.entity_tag('turtle_friends', 'minecraft:player', 'tfcflorae:dolphin')
    rm.entity_tag('spawns_on_cold_blocks', 'tfcflorae:penguin', 'tfcflorae:polar_bear')
    rm.entity_tag('destroys_floating_plants', 'minecraft:boat', *['tfcflorae:boat/%s' % wood for wood in WOODS.keys()])
    rm.entity_tag('bubble_column_immune', *['tfcflorae:%s' % entity for entity in OCEAN_CREATURES.keys()], *['tfcflorae:%s' % entity for entity in UNDERGROUND_WATER_CREATURES.keys()], *['tfcflorae:%s' % entity for entity in OCEAN_AMBIENT.keys()])
    rm.entity_tag('needs_large_fishing_bait', 'tfcflorae:dolphin', 'tfcflorae:orca')

    # Item Heats

    item_heat(rm, 'wrought_iron_grill', 'tfcflorae:wrought_iron_grill', 0.35, 1535)
    item_heat(rm, 'stick', '#forge:rods/wooden', 0.3)
    item_heat(rm, 'stick_bunch', 'tfcflorae:stick_bunch', 0.05)
    item_heat(rm, 'glass_shard', 'tfcflorae:glass_shard', 1)
    item_heat(rm, 'sand', '#forge:sand', 0.8)
    item_heat(rm, 'ceramic_unfired_brick', 'tfcflorae:ceramic/unfired_brick', POTTERY_HC)
    item_heat(rm, 'ceramic_unfired_flower_pot', 'tfcflorae:ceramic/unfired_flower_pot', POTTERY_HC)
    item_heat(rm, 'ceramic_unfired_jug', 'tfcflorae:ceramic/unfired_jug', POTTERY_HC)
    item_heat(rm, 'ceramic_unfired_pan', 'tfcflorae:ceramic/unfired_pan', POTTERY_HC)
    item_heat(rm, 'terracotta', ['minecraft:terracotta', *['minecraft:%s_terracotta' % color for color in COLORS]], 0.8)
    item_heat(rm, 'dough', ['tfcflorae:food/%s_dough' % grain for grain in GRAINS], 1)
    item_heat(rm, 'meat', ['tfcflorae:food/%s' % meat for meat in MEATS], 1)
    item_heat(rm, 'edible_plants', ['tfcflorae:plant/%s' % plant for plant in SEAWEED] + ['tfcflorae:plant/giant_kelp_flower', 'tfcflorae:groundcover/seaweed'], 1)
    item_heat(rm, 'blooms', '#tfcflorae:blooms', 0.35, METALS['wrought_iron'].melt_temperature)

    for pottery in SIMPLE_POTTERY:
        item_heat(rm, 'unfired_' + pottery, 'tfcflorae:ceramic/unfired_' + pottery, POTTERY_HC)

    for color in COLORS:
        item_heat(rm, 'unfired_%s_vessel' % color, 'tfcflorae:ceramic/%s_unfired_vessel' % color, POTTERY_HC)
        item_heat(rm, 'unfired_large_vessel_%s' % color, 'tfcflorae:ceramic/unfired_large_vessel/%s' % color, POTTERY_HC)

    for item, item_data in METAL_ITEMS.items():
        if item_data.mold:
            item_heat(rm, 'unfired_%s_mold' % item, 'tfcflorae:ceramic/unfired_%s_mold' % item, POTTERY_HC)
            # No need to do fired molds, as they have their own capability implementation

    # Supports

    for wood in WOODS:
        rm.data(('tfcflorae', 'supports', wood), {
            'ingredient': 'tfcflorae:wood/horizontal_support/%s' % wood,
            'support_up': 1,
            'support_down': 1,
            'support_horizontal': 4
        })

    # Fuels

    for wood, wood_data in WOODS.items():
        fuel_item(rm, wood + '_log', ['tfcflorae:wood/log/' + wood, 'tfcflorae:wood/wood/' + wood], wood_data.duration, wood_data.temp)

    fuel_item(rm, 'coal', ['minecraft:coal', 'tfcflorae:ore/bituminous_coal'], 2200, 1415)
    fuel_item(rm, 'lignite', 'tfcflorae:ore/lignite', 2200, 1350)
    fuel_item(rm, 'charcoal', 'minecraft:charcoal', 1800, 1350)
    fuel_item(rm, 'peat', 'tfcflorae:peat', 2500, 600)
    fuel_item(rm, 'stick_bundle', 'tfcflorae:stick_bundle', 600, 900)

    # =========
    # ITEM TAGS
    # =========

    rm.item_tag('forge:ingots/cast_iron', 'minecraft:iron_ingot')
    rm.item_tag('firepit_sticks', '#forge:rods/wooden')
    rm.item_tag('firepit_kindling', 'tfcflorae:straw', 'minecraft:paper', 'minecraft:book', 'tfcflorae:groundcover/pinecone')
    rm.item_tag('starts_fires_with_durability', 'minecraft:flint_and_steel')
    rm.item_tag('starts_fires_with_items', 'minecraft:fire_charge')
    rm.item_tag('handstone', 'tfcflorae:handstone')
    rm.item_tag('high_quality_cloth', 'tfcflorae:silk_cloth', 'tfcflorae:wool_cloth')
    rm.item_tag('minecraft:stone_pressure_plates', 'minecraft:stone_pressure_plate', 'minecraft:polished_blackstone_pressure_plate')
    rm.item_tag('axes_that_log', '#tfcflorae:axes')
    rm.item_tag('extinguisher', '#tfcflorae:shovels')
    rm.item_tag('forge:shears', '#tfcflorae:shears')  # forge tag includes TFC shears
    rm.item_tag('minecraft:coals', 'tfcflorae:ore/bituminous_coal', 'tfcflorae:ore/lignite')
    rm.item_tag('forge_fuel', '#minecraft:coals')
    rm.item_tag('firepit_fuel', '#minecraft:logs', 'tfcflorae:peat', 'tfcflorae:peat_grass', 'tfcflorae:stick_bundle')
    rm.item_tag('bloomery_fuel', 'minecraft:charcoal')
    rm.item_tag('blast_furnace_fuel', 'minecraft:charcoal')
    rm.item_tag('log_pile_logs', 'tfcflorae:stick_bundle')
    rm.item_tag('pit_kiln_straw', 'tfcflorae:straw')
    rm.item_tag('firepit_logs', '#minecraft:logs')
    rm.item_tag('log_pile_logs', '#minecraft:logs')
    rm.item_tag('pit_kiln_logs', '#minecraft:logs')
    rm.item_tag('can_be_lit_on_torch', '#forge:rods/wooden')
    rm.item_tag('wattle_sticks', 'tfcflorae:stick_bunch')
    rm.item_tag('mortar', 'tfcflorae:mortar')
    rm.item_tag('flux', 'tfcflorae:powder/flux')
    rm.item_tag('thatch_bed_hides', 'tfcflorae:large_raw_hide', 'tfcflorae:large_sheepskin_hide')
    rm.item_tag('scrapable', 'tfcflorae:large_soaked_hide', 'tfcflorae:medium_soaked_hide', 'tfcflorae:small_soaked_hide')
    rm.item_tag('clay_knapping', 'minecraft:clay_ball')
    rm.item_tag('fire_clay_knapping', 'tfcflorae:fire_clay')
    rm.item_tag('leather_knapping', '#forge:leather')
    rm.item_tag('knapping_any', '#tfcflorae:clay_knapping', '#tfcflorae:fire_clay_knapping', '#tfcflorae:leather_knapping', '#tfcflorae:rock_knapping')
    rm.item_tag('forge:gems/diamond', 'tfcflorae:gem/diamond')
    rm.item_tag('forge:gems/lapis', 'tfcflorae:gem/lapis_lazuli')
    rm.item_tag('forge:gems/emerald', 'tfcflorae:gem/emerald')
    rm.item_tag('bush_cutting_tools', '#forge:shears', '#tfcflorae:knives')
    rm.item_tag('minecraft:fishes', 'tfcflorae:food/cod', 'tfcflorae:food/cooked_cod', 'tfcflorae:food/salmon', 'tfcflorae:food/cooked_salmon', 'tfcflorae:food/tropical_fish', 'tfcflorae:food/cooked_tropical_fish', 'tfcflorae:food/bluegill', 'tfcflorae:food/cooked_bluegill', 'tfcflorae:food/shellfish', 'tfcflorae:food/cooked_shellfish')
    rm.item_tag('small_fishing_bait', 'tfcflorae:food/shellfish', '#tfcflorae:seeds')
    rm.item_tag('large_fishing_bait', 'tfcflorae:food/cod', 'tfcflorae:food/salmon', 'tfcflorae:food/tropical_fish', 'tfcflorae:food/bluegill')
    rm.item_tag('holds_small_fishing_bait', *['tfcflorae:metal/fishing_rod/%s' % metal for metal, data in METALS.items() if 'tool' in data.types])
    rm.item_tag('holds_large_fishing_bait', *['tfcflorae:metal/fishing_rod/%s' % metal for metal in ('wrought_iron', 'red_steel', 'blue_steel', 'black_steel', 'steel')])
    rm.item_tag('forge:string', 'tfcflorae:wool_yarn')
    rm.item_tag('usable_on_tool_rack', 'tfcflorae:firestarter', 'minecraft:bow', 'minecraft:crossbow', 'minecraft:flint_and_steel')

    rm.item_tag('pig_food', '#tfcflorae:foods')
    rm.item_tag('cow_food', '#tfcflorae:foods/grains')
    rm.item_tag('chicken_food', '#tfcflorae:foods/grains', '#tfcflorae:foods/fruits', '#tfcflorae:foods/vegetables', '#tfcflorae:seeds')
    rm.item_tag('alpaca_food', '#tfcflorae:foods/grains', '#tfcflorae:foods/fruits')

    rm.item_tag('foods/can_be_salted', '#tfcflorae:foods/raw_meats')
    rm.item_tag('tfcflorae:foods/grains', *['tfcflorae:food/%s_grain' % grain for grain in GRAINS])
    rm.item_tag('tfcflorae:compost_greens', '#tfcflorae:plants', *['tfcflorae:food/%s' % v for v in VEGETABLES], *['tfcflorae:food/%s' % m for m in FRUITS], *['tfcflorae:food/%s_bread' % grain for grain in GRAINS])
    rm.item_tag('tfcflorae:compost_browns', 'tfcflorae:groundcover/humus', 'tfcflorae:groundcover/dead_grass', 'tfcflorae:groundcover/driftwood', 'tfcflorae:groundcover/pinecone', 'minecraft:paper')
    rm.item_tag('tfcflorae:compost_poisons', *['tfcflorae:food/%s' % m for m in MEATS], *['tfcflorae:food/cooked_%s' % m for m in MEATS], 'minecraft:bone')
    rm.item_tag('forge:double_sheets/any_bronze', *['#forge:double_sheets/%sbronze' % b for b in ('bismuth_', 'black_', '')])
    rm.item_tag('tfcflorae:bronze_anvils', *['tfcflorae:metal/anvil/%sbronze' % b for b in ('bismuth_', 'black_', '')])
    block_and_item_tag(rm, 'tfcflorae:anvils', *['tfcflorae:metal/anvil/%s' % metal for metal, data in METALS.items() if 'utility' in data.types])
    rm.item_tag('fluxstone', 'tfcflorae:food/shellfish', 'tfcflorae:groundcover/mollusk', 'tfcflorae:groundcover/clam', 'minecraft:scute')
    rm.item_tag('minecraft:arrows', 'tfcflorae:glow_arrow')
    rm.item_tag('foods/apples', 'tfcflorae:food/green_apple', 'tfcflorae:food/red_apple')
    rm.item_tag('foods/usable_in_soup', '#tfcflorae:foods/fruits', '#tfcflorae:foods/vegetables', '#tfcflorae:foods/meats', '#tfcflorae:foods/cooked_meats')
    rm.item_tag('foods/usable_in_salad', '#tfcflorae:foods/fruits', '#tfcflorae:foods/vegetables', '#tfcflorae:foods/cooked_meats')
    rm.item_tag('foods/usable_in_sandwich', '#tfcflorae:foods/vegetables', '#tfcflorae:foods/cooked_meats', '#tfcflorae:foods/dairy')
    rm.item_tag('sandwich_bread', *['tfcflorae:food/%s_bread' % grain for grain in GRAINS])
    rm.item_tag('bowls', 'tfcflorae:ceramic/bowl', 'minecraft:bowl')
    rm.item_tag('soup_bowls', '#tfcflorae:bowls')
    rm.item_tag('salad_bowls', '#tfcflorae:bowls')
    rm.item_tag('scribing_ink', 'minecraft:black_dye')
    rm.item_tag('vessels', 'tfcflorae:ceramic/unfired_vessel', 'tfcflorae:ceramic/vessel')

    for color in COLORS:
        rm.item_tag('vessels', 'tfcflorae:ceramic/%s_unfired_vessel' % color, 'tfcflorae:ceramic/%s_glazed_vessel' % color)
        rm.item_tag('dyes', 'minecraft:%s_dye' % color)

        if color != 'white':
            for variant in VANILLA_DYED_ITEMS:
                rm.item_tag('colored_%s' % variant, 'minecraft:%s_%s' % (color, variant))
            for variant in ('raw_alabaster', 'alabaster_bricks', 'polished_alabaster'):
                rm.item_tag('colored_%s' % variant, 'tfcflorae:alabaster/stained/%s_%s' % (color, variant))
        rm.item_tag('colored_shulker_boxes', 'minecraft:%s_shulker_box' % color)
        rm.item_tag('colored_concrete_powder', 'minecraft:%s_concrete_powder' % color)
    for gem in GEMS:
        rm.item_tag('forge:gems', 'tfcflorae:gem/' + gem)

    for wood in WOODS.keys():
        rm.item_tag('minecraft:logs', 'tfcflorae:wood/log/%s' % wood, 'tfcflorae:wood/wood/%s' % wood, 'tfcflorae:wood/stripped_log/%s' % wood, 'tfcflorae:wood/stripped_wood/%s' % wood)
        rm.item_tag('twigs', 'tfcflorae:wood/twig/%s' % wood)
        rm.item_tag('lumber', 'tfcflorae:wood/lumber/%s' % wood)
        rm.item_tag('sluices', 'tfcflorae:wood/sluice/%s' % wood)
        rm.item_tag('looms', 'tfcflorae:wood/planks/%s_loom' % wood)
        if wood in TANNIN_WOOD_TYPES:
            rm.item_tag('makes_tannin', 'tfcflorae:wood/log/%s' % wood, 'tfcflorae:wood/wood/%s' % wood)

    for category in ROCK_CATEGORIES:  # Rock (Category) Tools
        for tool in ROCK_CATEGORY_ITEMS:
            rm.item_tag(TOOL_TAGS[tool], 'tfcflorae:stone/%s/%s' % (tool, category))
            rm.item_tag("usable_on_tool_rack", 'tfcflorae:stone/%s/%s' % (tool, category))

    for metal, metal_data in METALS.items():  # Metal Tools
        if 'tool' in metal_data.types:
            for tool_type, tool_tag in TOOL_TAGS.items():
                rm.item_tag(tool_tag, 'tfcflorae:metal/%s/%s' % (tool_type, metal))
                rm.item_tag("usable_on_tool_rack", 'tfcflorae:metal/%s/%s' % (tool_type, metal))
            rm.item_tag("usable_on_tool_rack", 'tfcflorae:metal/fishing_rod/%s' % metal, 'tfcflorae:metal/tuyere/%s' % metal)
        

    # Blocks and Items
    block_and_item_tag(rm, 'forge:sand', '#minecraft:sand')  # Forge doesn't reference the vanilla tag for some reason

    for wood in WOODS.keys():
        block_and_item_tag(rm, 'tool_racks', 'tfcflorae:wood/planks/%s_tool_rack' % wood)
        rm.block_tag('single_block_replaceable', 'tfcflorae:wood/twig/%s' % wood, 'tfcflorae:wood/fallen_leaves/%s' % wood)

    for plant in PLANTS.keys():
        block_and_item_tag(rm, 'plants', 'tfcflorae:plant/%s' % plant)
    for plant in UNIQUE_PLANTS:
        rm.block_tag('plants', 'tfcflorae:plant/%s' % plant)

    # Sand
    for color in SAND_BLOCK_TYPES:
        block_and_item_tag(rm, 'minecraft:sand', 'tfcflorae:sand/%s' % color)

    # Metal Ingots / Sheets, for Ingot/Sheet Piles
    for metal in METALS.keys():
        rm.item_tag('forge:ingots/%s' % metal)
        rm.item_tag('forge:sheets/%s' % metal)
        rm.item_tag('tfcflorae:pileable_ingots', '#forge:ingots/%s' % metal)
        rm.item_tag('tfcflorae:pileable_sheets', '#forge:sheets/%s' % metal)

    # ==========
    # BLOCK TAGS
    # ==========

    rm.block_tag('tree_grows_on', 'minecraft:grass_block', '#minecraft:dirt', '#tfcflorae:grass', '#tfcflorae:mud')
    rm.block_tag('supports_landslide', 'minecraft:dirt_path', *['tfcflorae:grass_path/%s' % v for v in SOIL_BLOCK_VARIANTS], *['tfcflorae:farmland/%s' % v for v in SOIL_BLOCK_VARIANTS])
    rm.block_tag('bush_plantable_on', 'minecraft:grass_block', '#minecraft:dirt', '#tfcflorae:grass', '#tfcflorae:farmland')
    block_and_item_tag(rm, 'mud', *['tfcflorae:mud/%s' % v for v in SOIL_BLOCK_VARIANTS])
    rm.block_tag('grass_plantable_on', '#tfcflorae:bush_plantable_on', 'tfcflorae:peat', '#tfcflorae:mud')
    rm.block_tag('small_spike', 'tfcflorae:calcite')
    rm.block_tag('sea_bush_plantable_on', '#minecraft:dirt', '#minecraft:sand', '#forge:gravel', '#tfcflorae:mud')
    rm.block_tag('creeping_plantable_on', 'minecraft:grass_block', '#tfcflorae:grass', '#minecraft:base_stone_overworld', '#minecraft:logs')
    rm.block_tag('minecraft:bamboo_plantable_on', '#tfcflorae:grass')
    rm.block_tag('minecraft:climbable', 'tfcflorae:plant/hanging_vines', 'tfcflorae:plant/hanging_vines_plant', 'tfcflorae:plant/liana', 'tfcflorae:plant/liana_plant')
    rm.block_tag('kelp_tree', 'tfcflorae:plant/giant_kelp_flower', 'tfcflorae:plant/giant_kelp_plant')
    rm.block_tag('kelp_flower', 'tfcflorae:plant/giant_kelp_flower')
    rm.block_tag('kelp_branch', 'tfcflorae:plant/giant_kelp_plant')
    rm.block_tag('lit_by_dropped_torch', 'tfcflorae:log_pile', 'tfcflorae:thatch', 'tfcflorae:pit_kiln')
    rm.block_tag('charcoal_cover_whitelist', 'tfcflorae:log_pile', 'tfcflorae:charcoal_pile', 'tfcflorae:burning_log_pile')
    rm.block_tag('forge_invisible_whitelist', 'tfcflorae:crucible')
    rm.block_tag('any_spreading_bush', '#tfcflorae:spreading_bush')
    rm.block_tag('thorny_bushes', 'tfcflorae:plant/blackberry_bush', 'tfcflorae:plant/raspberry_bush')
    rm.block_tag('logs_that_log', '#minecraft:logs')
    rm.block_tag('scraping_surface', '#minecraft:logs')
    rm.block_tag('forge:sand', '#minecraft:sand')  # Forge doesn't reference the vanilla tag
    rm.block_tag('forge:concrete', *['minecraft:%s_concrete' % c for c in COLORS])
    rm.block_tag('thatch_bed_thatch', 'tfcflorae:thatch')
    rm.block_tag('snow', 'minecraft:snow', 'minecraft:snow_block', 'tfcflorae:snow_pile')
    rm.block_tag('tfcflorae:forge_insulation', '#forge:stone', '#forge:cobblestone', '#forge:stone_bricks', '#forge:smooth_stone')
    rm.block_tag('tfcflorae:bloomery_insulation', '#forge:stone', '#forge:cobblestone', '#forge:stone_bricks', '#forge:smooth_stone', 'minecraft:bricks', 'tfcflorae:fire_bricks', '#forge:concrete')
    rm.block_tag('tfcflorae:blast_furnace_insulation', 'tfcflorae:fire_bricks')
    rm.block_tag('minecraft:valid_spawn', *['tfcflorae:grass/%s' % v for v in SOIL_BLOCK_VARIANTS], *['tfcflorae:sand/%s' % c for c in SAND_BLOCK_TYPES], *['tfcflorae:rock/raw/%s' % r for r in ROCKS.keys()])  # Valid spawn tag - grass, sand, or raw rock
    block_and_item_tag(rm, 'minecraft:dirt', *['tfcflorae:dirt/%s' % v for v in SOIL_BLOCK_VARIANTS], *['tfcflorae:rooted_dirt/%s' % v for v in SOIL_BLOCK_VARIANTS])
    rm.block_tag('minecraft:geode_invalid_blocks', 'tfcflorae:sea_ice', 'tfcflorae:fluid/salt_water', 'tfcflorae:fluid/river_water', 'tfcflorae:fluid/spring_water')
    rm.block_tag('wild_crop_grows_on', '#tfcflorae:bush_plantable_on')
    rm.block_tag('plants', *['tfcflorae:wild_crop/%s' % crop for crop in CROPS.keys()])
    rm.block_tag('single_block_replaceable', 'tfcflorae:groundcover/humus', 'tfcflorae:groundcover/dead_grass')
    rm.block_tag('powder_snow_replaceable', '#minecraft:dirt', '#forge:gravel', '#tfcflorae:grass', 'minecraft:snow')
    rm.item_tag('usable_on_tool_rack', 'tfcflorae:firestarter', 'minecraft:bow', 'minecraft:crossbow', 'minecraft:flint_and_steel')
    rm.block_tag('creates_downward_bubbles', 'minecraft:soul_sand')
    rm.block_tag('minecraft:infiniburn_overworld', 'tfcflorae:pit_kiln')

    for ore, ore_data in ORES.items():
        for rock in ROCKS.keys():
            if ore_data.graded:
                for grade in ORE_GRADES.keys():
                    rm.block_tag('prospectable', 'tfcflorae:ore/%s_%s/%s' % (grade, ore, rock))
                    rm.block('tfcflorae:ore/%s_%s/%s/prospected' % (grade, ore, rock)).with_lang(lang(ore))
            else:
                rm.block_tag('prospectable', 'tfcflorae:ore/%s/%s' % (ore, rock))
                rm.block('tfcflorae:ore/%s/%s/prospected' % (ore, rock)).with_lang(lang(ore))

    for wood in WOODS.keys():
        rm.block_tag('lit_by_dropped_torch', 'tfcflorae:wood/fallen_leaves/' + wood)
        rm.block_tag('converts_to_humus', 'tfcflorae:wood/fallen_leaves/' + wood)

    for plant, data in PLANTS.items():  # Plants
        block_and_item_tag(rm, 'plants', 'tfcflorae:plant/%s' % plant)
        if data.type in ('standard', 'short_grass', 'dry', 'grass_water', 'water'):
            rm.block_tag('single_block_replaceable', 'tfcflorae:plant/%s' % plant)
        if data.type in ('standard', 'tall_plant', 'short_grass', 'tall_grass', 'creeping'):
            rm.block_tag('can_be_snow_piled', 'tfcflorae:plant/%s' % plant)
        if data.type in ('emergent', 'emergent_fresh', 'floating', 'floating_fresh', 'creeping'):
            rm.block_tag('can_be_ice_piled', 'tfcflorae:plant/%s' % plant)

    # Rocks
    for rock, rock_data in ROCKS.items():
        def block(block_type: str):
            return 'tfcflorae:rock/%s/%s' % (block_type, rock)

        block_and_item_tag(rm, 'forge:gravel', 'tfcflorae:rock/gravel/%s' % rock)
        block_and_item_tag(rm, 'forge:stone', block('raw'))
        rm.block_tag('forge:stone', block('hardened'))
        block_and_item_tag(rm, 'forge:cobblestone', block('cobble'), block('mossy_cobble'))
        rm.block_tag('minecraft:base_stone_overworld', block('raw'), block('hardened'))
        block_and_item_tag(rm, 'forge:stone_bricks', block('bricks'), block('mossy_bricks'), block('cracked_bricks'))
        block_and_item_tag(rm, 'forge:smooth_stone', block('smooth'))
        rm.block_tag('tfcflorae:breaks_when_isolated', block('raw'))
        block_and_item_tag(rm, 'minecraft:stone_pressure_plates', block('pressure_plate'))
        block_and_item_tag(rm, 'forge:smooth_stone_slab', 'tfcflorae:rock/smooth/%s_slab' % rock)
        rm.item_tag('tfcflorae:rock_knapping', block('loose'))
        rm.item_tag('tfcflorae:%s_rock' % rock_data.category, block('loose'))
        if rock_data.category == 'igneous_extrusive' or rock_data.category == 'igneous_intrusive':
            rm.block_tag('creates_upward_bubbles', block('magma'))

        if rock in ['chalk', 'dolomite', 'limestone', 'marble']:
            rm.item_tag('tfcflorae:fluxstone', block('loose'))

        for ore in ORE_DEPOSITS:
            block_and_item_tag(rm, 'forge:gravel', 'tfcflorae:deposit/%s/%s' % (ore, rock))
            rm.block_tag('can_be_panned', 'tfcflorae:deposit/%s/%s' % (ore, rock))

    # Ore tags
    for ore, data in ORES.items():
        if data.tag not in DEFAULT_FORGE_ORE_TAGS:
            rm.block_tag('forge:ores', '#forge:ores/%s' % data.tag)
        if data.graded:  # graded ores -> each grade is declared as a TFC tag, then added to the forge tag
            rm.block_tag('forge:ores/%s' % data.tag, '#tfcflorae:ores/%s/poor' % data.tag, '#tfcflorae:ores/%s/normal' % data.tag, '#tfcflorae:ores/%s/rich' % data.tag)
        for rock in ROCKS.keys():
            if data.graded:
                rm.block_tag('ores/%s/poor' % data.tag, 'tfcflorae:ore/poor_%s/%s' % (ore, rock))
                rm.block_tag('ores/%s/normal' % data.tag, 'tfcflorae:ore/normal_%s/%s' % (ore, rock))
                rm.block_tag('ores/%s/rich' % data.tag, 'tfcflorae:ore/rich_%s/%s' % (ore, rock))
            else:
                rm.block_tag('forge:ores/%s' % data.tag, 'tfcflorae:ore/%s/%s' % (ore, rock))

    # can_carve Tag
    for rock in ROCKS.keys():
        for variant in ('raw', 'hardened', 'gravel', 'cobble'):
            rm.block_tag('can_carve', 'tfcflorae:rock/%s/%s' % (variant, rock))
    for sand in SAND_BLOCK_TYPES:
        rm.block_tag('can_carve', 'tfcflorae:sand/%s' % sand, 'tfcflorae:raw_sandstone/%s' % sand)
    for soil in SOIL_BLOCK_VARIANTS:
        rm.block_tag('can_carve', 'tfcflorae:dirt/%s' % soil, 'tfcflorae:grass/%s' % soil, 'tfcflorae:mud/%s' % soil, 'tfcflorae:rooted_dirt/%s' % soil)
    rm.block_tag('can_carve', 'minecraft:powder_snow')

    # Soil / Standard blocks are toughness 0 - dirt destroys charcoal
    rm.block_tag('toughness_1', 'tfcflorae:charcoal_pile', 'tfcflorae:charcoal_forge')  # Charcoal is toughness 1 - resistant against destruction from soil
    rm.block_tag('toughness_2', *[
        'tfcflorae:rock/%s/%s' % (variant, rock) for variant in ('raw', 'cobble', 'mossy_cobble') for rock in ROCKS.keys()
    ])  # Stone type blocks are toughness 2
    rm.block_tag('toughness_3', 'minecraft:bedrock')  # Used as a top level 'everything goes'

    # Harvest Tool + Level Tags
    # Note: since we sort our tools *above* the vanilla equivalents (since there's no way we can make them 'exactly equal' because Forge's tool BS doesn't support that ENTIRELY REASONABLE feature), our tools need to effectively define empty tags for blocks that are exclusive to that tool only.
    # In other words, our tools are strictly better than vanilla tools, so our blocks need to not require them

    rm.block_tag('needs_stone_tool')
    rm.block_tag('needs_copper_tool')
    rm.block_tag('needs_wrought_iron_tool')
    rm.block_tag('needs_steel_tool')
    rm.block_tag('needs_colored_steel_tool')

    def needs_tool(_tool: str) -> str:
        return {
            'wood': 'forge:needs_wood_tool', 'stone': 'forge:needs_wood_tool',
            'copper': 'minecraft:needs_stone_tool',
            'bronze': 'minecraft:needs_iron_tool',
            'iron': 'minecraft:needs_iron_tool', 'wrought_iron': 'minecraft:needs_iron_tool',
            'diamond': 'minecraft:needs_diamond_tool', 'steel': 'minecraft:needs_diamond_tool',
            'netherite': 'forge:needs_netherite_tool', 'black_steel': 'forge:needs_netherite_tool',
            'colored_steel': 'tfcflorae:needs_colored_steel_tool'
        }[_tool]

    rm.block_tag('minecraft:mineable/hoe', '#tfcflorae:mineable_with_sharp_tool')
    rm.block_tag('tfcflorae:mineable_with_knife', '#tfcflorae:mineable_with_sharp_tool')
    rm.block_tag('tfcflorae:mineable_with_scythe', '#tfcflorae:mineable_with_sharp_tool')
    rm.block_tag('tfcflorae:mineable_with_hammer', '#tfcflorae:mineable_with_blunt_tool')
    rm.item_tag('tfcflorae:sharp_tools', '#tfcflorae:hoes', '#tfcflorae:knives', '#tfcflorae:scythes')

    rm.block_tag('forge:needs_wood_tool')
    rm.block_tag('forge:needs_netherite_tool')

    for ore, data in ORES.items():
        for rock in ROCKS.keys():
            if data.graded:
                rm.block_tag(needs_tool(data.required_tool), 'tfcflorae:ore/poor_%s/%s' % (ore, rock), 'tfcflorae:ore/normal_%s/%s' % (ore, rock), 'tfcflorae:ore/rich_%s/%s' % (ore, rock))
            else:
                rm.block_tag(needs_tool(data.required_tool), 'tfcflorae:ore/%s/%s' % (ore, rock))

    rm.block_tag('minecraft:mineable/shovel', *[
        *['tfcflorae:%s/%s' % (soil, variant) for soil in SOIL_BLOCK_TYPES for variant in SOIL_BLOCK_VARIANTS],
        'tfcflorae:peat',
        'tfcflorae:peat_grass',
        *['tfcflorae:sand/%s' % sand for sand in SAND_BLOCK_TYPES],
        'tfcflorae:snow_pile',
        *['tfcflorae:rock/gravel/%s' % rock for rock in ROCKS.keys()],
        *['tfcflorae:deposit/%s/%s' % (ore, rock) for ore in ORE_DEPOSITS for rock in ROCKS.keys()],
        'tfcflorae:aggregate',
        'tfcflorae:fire_clay_block',
        'tfcflorae:charcoal_pile',
        'tfcflorae:charcoal_forge'
    ])
    rm.block_tag('minecraft:mineable/pickaxe', *[
        *['tfcflorae:%s_sandstone/%s' % (variant, sand) for variant in SANDSTONE_BLOCK_TYPES for sand in SAND_BLOCK_TYPES],
        *['tfcflorae:%s_sandstone/%s_%s' % (variant, sand, suffix) for variant in SANDSTONE_BLOCK_TYPES for sand in SAND_BLOCK_TYPES for suffix in ('slab', 'stairs', 'wall')],
        'tfcflorae:icicle',
        'tfcflorae:sea_ice',
        'tfcflorae:ice_pile',
        'tfcflorae:calcite',
        *['tfcflorae:ore/%s/%s' % (ore, rock) for ore, ore_data in ORES.items() for rock in ROCKS.keys() if not ore_data.graded],
        *['tfcflorae:ore/%s_%s/%s' % (grade, ore, rock) for ore, ore_data in ORES.items() for rock in ROCKS.keys() for grade in ORE_GRADES.keys() if ore_data.graded],
        *['tfcflorae:ore/small_%s' % ore for ore, ore_data in ORES.items() if ore_data.graded],
        *['tfcflorae:rock/%s/%s' % (variant, rock) for variant in ('raw', 'hardened', 'smooth', 'cobble', 'bricks', 'spike', 'cracked_bricks', 'mossy_bricks', 'mossy_cobble', 'chiseled', 'loose', 'pressure_plate', 'button') for rock in ROCKS.keys()],
        *['tfcflorae:rock/%s/%s_%s' % (variant, rock, suffix) for variant in ('raw', 'smooth', 'cobble', 'bricks', 'cracked_bricks', 'mossy_bricks', 'mossy_cobble') for rock in ROCKS.keys() for suffix in ('slab', 'stairs', 'wall')],
        *['tfcflorae:rock/anvil/%s' % rock for rock, rock_data in ROCKS.items() if rock_data.category == 'igneous_intrusive' or rock_data.category == 'igneous_extrusive'],
        *['tfcflorae:rock/magma/%s' % rock for rock, rock_data in ROCKS.items() if rock_data.category == 'igneous_intrusive' or rock_data.category == 'igneous_extrusive'],
        *['tfcflorae:metal/%s/%s' % (variant, metal) for variant, variant_data in METAL_BLOCKS.items() for metal, metal_data in METALS.items() if variant_data.type in metal_data.types],
        *['tfcflorae:coral/%s_%s' % (color, variant) for color in CORALS for variant in CORAL_BLOCKS],
        'tfcflorae:alabaster/raw/alabaster',
        'tfcflorae:alabaster/raw/alabaster_bricks',
        'tfcflorae:alabaster/raw/polished_alabaster',
        *['tfcflorae:alabaster/stained/%s%s' % (color, variant) for color in COLORS for variant in ('_raw_alabaster', '_alabaster_bricks', '_polished_alabaster', '_alabaster_bricks_slab', '_alabaster_bricks_stairs', '_alabaster_bricks_wall', '_polished_alabaster_slab', '_polished_alabaster_stairs', '_polished_alabaster_wall')],
        *['tfcflorae:groundcover/%s' % gc for gc in MISC_GROUNDCOVER],
        'tfcflorae:fire_bricks',
        'tfcflorae:quern',
        'tfcflorae:crucible',
        'tfcflorae:bloomery',
        'tfcflorae:bloom',
        'tfcflorae:pot',
        'tfcflorae:grill',
        'tfcflorae:firepit',
        'tfcflorae:ingot_pile',
        'tfcflorae:sheet_pile',
        'tfcflorae:blast_furnace'
    ])
    rm.block_tag('minecraft:mineable/axe', *[
        *['tfcflorae:wood/%s/%s' % (variant, wood) for variant in ('log', 'stripped_log', 'wood', 'stripped_wood', 'planks', 'twig', 'vertical_support', 'horizontal_support', 'sluice', 'chest', 'trapped_chest') for wood in WOODS.keys()],
        *['tfcflorae:wood/planks/%s_%s' % (wood, variant) for variant in ('bookshelf', 'door', 'trapdoor', 'fence', 'log_fence', 'fence_gate', 'button', 'pressure_plate', 'slab', 'stairs', 'tool_rack', 'workbench', 'sign') for wood in WOODS.keys()],
        *['tfcflorae:plant/%s_branch' % tree for tree in NORMAL_FRUIT_TREES],
        *['tfcflorae:plant/%s_growing_branch' % tree for tree in NORMAL_FRUIT_TREES],
        *['tfcflorae:wattle/%s' % color for color in COLORS],
        'tfcflorae:wattle',
        'tfcflorae:wattle/unstained',
        'tfcflorae:plant/banana_plant',
        'tfcflorae:plant/dead_banana_plant',
        'tfcflorae:log_pile',
        'tfcflorae:burning_log_pile',
        'tfcflorae:composter',
        'tfcflorae:nest_box'
    ])
    rm.block_tag('tfcflorae:mineable_with_sharp_tool', *[
        *['tfcflorae:wood/%s/%s' % (variant, wood) for variant in ('leaves', 'sapling', 'fallen_leaves') for wood in WOODS.keys()],
        *['tfcflorae:plant/%s' % plant for plant in PLANTS.keys()],
        *['tfcflorae:plant/%s' % plant for plant in UNIQUE_PLANTS],
        *['tfcflorae:wild_crop/%s' % plant for plant in CROPS.keys()],
        *['tfcflorae:dead_crop/%s' % plant for plant in CROPS.keys()],
        *['tfcflorae:crop/%s' % plant for plant in CROPS.keys()],
        'tfcflorae:sea_pickle',
        *['tfcflorae:plant/%s_bush' % bush for bush in ('snowberry', 'bunchberry', 'gooseberry', 'cloudberry', 'strawberry', 'wintergreen_berry')],
        *['tfcflorae:plant/%s_bush%s' % (bush, suffix) for bush in ('blackberry', 'raspberry', 'blueberry', 'elderberry') for suffix in ('', '_cane')],
        'tfcflorae:plant/cranberry_bush',
        'tfcflorae:plant/dead_berry_bush',
        'tfcflorae:plant/dead_cane',
        *['tfcflorae:plant/%s_leaves' % tree for tree in NORMAL_FRUIT_TREES],
        *['tfcflorae:plant/%s_sapling' % tree for tree in NORMAL_FRUIT_TREES],
        'tfcflorae:plant/banana_sapling',
        'tfcflorae:thatch',
        'tfcflorae:thatch_bed'
    ])
    rm.block_tag('tfcflorae:mineable_with_blunt_tool',
        *['tfcflorae:wood/%s/%s' % (variant, wood) for variant in ('log', 'stripped_log', 'wood', 'stripped_wood') for wood in WOODS.keys()]
    )

    # ==========
    # FLUID TAGS
    # ==========

    rm.fluid_tag('fluid_ingredients', 'minecraft:water', 'tfcflorae:salt_water', 'tfcflorae:spring_water', '#tfcflorae:alcohols', '#tfcflorae:dye_fluids', *['tfcflorae:%s' % fluid for fluid in SIMPLE_FLUIDS], '#tfcflorae:milks')
    rm.fluid_tag('drinkables', 'minecraft:water', 'tfcflorae:salt_water', 'tfcflorae:river_water', '#tfcflorae:alcohols', '#tfcflorae:milks')
    rm.fluid_tag('hydrating', 'minecraft:water', 'tfcflorae:river_water', '#tfcflorae:milks')
    rm.fluid_tag('milks', 'minecraft:milk')

    rm.fluid_tag('usable_in_pot', '#tfcflorae:fluid_ingredients')
    rm.fluid_tag('usable_in_jug', '#tfcflorae:drinkables')
    rm.fluid_tag('usable_in_wooden_bucket', '#tfcflorae:fluid_ingredients', '#tfcflorae:drinkables')
    rm.fluid_tag('usable_in_barrel', '#tfcflorae:fluid_ingredients', '#tfcflorae:drinkables')
    rm.fluid_tag('scribing_ink', 'tfcflorae:black_dye')
    rm.fluid_tag('usable_in_sluice', '#minecraft:water')

    # Item Sizes

    item_size(rm, 'logs', '#minecraft:logs', Size.very_large, Weight.medium)
    item_size(rm, 'quern', 'tfcflorae:quern', Size.very_large, Weight.very_heavy)
    item_size(rm, 'tool_racks', '#tfcflorae:tool_racks', Size.large, Weight.very_heavy)
    item_size(rm, 'chests', '#forge:chests', Size.large, Weight.light)
    # todo: add tfcflorae (non-wooden) slabs to minecraft slab tag
    item_size(rm, 'slabs', '#minecraft:slabs', Size.small, Weight.very_light)
    item_size(rm, 'vessels', '#tfcflorae:vessels', Size.normal, Weight.very_heavy)
    item_size(rm, 'doors', '#minecraft:doors', Size.very_large, Weight.heavy)
    item_size(rm, 'mortar', '#tfcflorae:mortar', Size.tiny, Weight.very_light)
    item_size(rm, 'halter', 'tfcflorae:halter', Size.small, Weight.light)
    item_size(rm, 'stick_bunch', 'tfcflorae:stick_bunch', Size.normal, Weight.light)
    item_size(rm, 'stick_bundle', 'tfcflorae:stick_bundle', Size.very_large, Weight.medium)
    item_size(rm, 'jute_fiber', 'tfcflorae:jute_fiber', Size.small, Weight.very_light)
    item_size(rm, 'burlap_cloth', 'tfcflorae:burlap_cloth', Size.small, Weight.very_light)
    item_size(rm, 'straw', 'tfcflorae:straw', Size.small, Weight.very_light)
    item_size(rm, 'wool', 'tfcflorae:wool', Size.small, Weight.light)
    item_size(rm, 'wool_cloth', 'tfcflorae:wool_cloth', Size.small, Weight.light)
    item_size(rm, 'silk_cloth', 'tfcflorae:silk_cloth', Size.small, Weight.light)
    item_size(rm, 'alabaster_brick', 'tfcflorae:alabaster_brick', Size.small, Weight.light)
    item_size(rm, 'glue', 'tfcflorae:glue', Size.tiny, Weight.light)
    item_size(rm, 'brass_mechanisms', 'tfcflorae:brass_mechanisms', Size.normal, Weight.light)
    item_size(rm, 'wrought_iron_grill', 'tfcflorae:wrought_iron_grill', Size.large, Weight.heavy)
    item_size(rm, 'dyes', '#tfcflorae:dyes', Size.tiny, Weight.light)
    item_size(rm, 'foods', '#tfcflorae:foods', Size.small, Weight.very_light)
    item_size(rm, 'plants', '#tfcflorae:plants', Size.tiny, Weight.very_light)
    item_size(rm, 'jute', 'tfcflorae:jute', Size.small, Weight.very_light)
    item_size(rm, 'bloomery', 'tfcflorae:bloomery', Size.large, Weight.very_heavy)
    item_size(rm, 'sluice', '#tfcflorae:sluices', Size.very_large, Weight.very_heavy)
    item_size(rm, 'lamps', '#tfcflorae:lamps', Size.normal, Weight.very_heavy)
    item_size(rm, 'signs', '#minecraft:signs', Size.very_small, Weight.heavy)
    item_size(rm, 'soups', '#tfcflorae:soups', Size.very_small, Weight.very_heavy)
    item_size(rm, 'salads', '#tfcflorae:salads', Size.very_small, Weight.very_heavy)

    # unimplemented
    # item_size(rm, 'loom', 'tfcflorae:loom', Size.large, Weight.very_heavy)

    # Food

    food_item(rm, 'banana', 'tfcflorae:food/banana', Category.fruit, 4, 0.2, 0, 2, fruit=1)
    food_item(rm, 'blackberry', 'tfcflorae:food/blackberry', Category.fruit, 4, 0.2, 5, 4.9, fruit=0.75)
    food_item(rm, 'blueberry', 'tfcflorae:food/blueberry', Category.fruit, 4, 0.2, 5, 4.9, fruit=0.75)
    food_item(rm, 'bunchberry', 'tfcflorae:food/bunchberry', Category.fruit, 4, 0.5, 5, 4.9, fruit=0.75)
    food_item(rm, 'cherry', 'tfcflorae:food/cherry', Category.fruit, 4, 0.2, 5, 4, fruit=1)
    food_item(rm, 'cloudberry', 'tfcflorae:food/cloudberry', Category.fruit, 4, 0.5, 5, 4.9, fruit=0.75)
    food_item(rm, 'cranberry', 'tfcflorae:food/cranberry', Category.fruit, 4, 0.2, 5, 1.8, fruit=1)
    food_item(rm, 'elderberry', 'tfcflorae:food/elderberry', Category.fruit, 4, 0.2, 5, 4.9, fruit=1)
    food_item(rm, 'gooseberry', 'tfcflorae:food/gooseberry', Category.fruit, 4, 0.5, 5, 4.9, fruit=0.75)
    food_item(rm, 'green_apple', 'tfcflorae:food/green_apple', Category.fruit, 4, 0.5, 0, 2.5, fruit=1)
    food_item(rm, 'lemon', 'tfcflorae:food/lemon', Category.fruit, 4, 0.2, 5, 2, fruit=0.75)
    food_item(rm, 'olive', 'tfcflorae:food/olive', Category.fruit, 4, 0.2, 0, 1.6, fruit=1)
    food_item(rm, 'orange', 'tfcflorae:food/orange', Category.fruit, 4, 0.5, 10, 2.2, fruit=0.5)
    food_item(rm, 'peach', 'tfcflorae:food/peach', Category.fruit, 4, 0.5, 10, 2.8, fruit=0.5)
    food_item(rm, 'plum', 'tfcflorae:food/plum', Category.fruit, 4, 0.5, 5, 2.8, fruit=0.75)
    food_item(rm, 'raspberry', 'tfcflorae:food/raspberry', Category.fruit, 4, 0.5, 5, 4.9, fruit=0.75)
    food_item(rm, 'red_apple', 'tfcflorae:food/red_apple', Category.fruit, 4, 0.5, 0, 1.7, fruit=1)
    food_item(rm, 'snowberry', 'tfcflorae:food/snowberry', Category.fruit, 4, 0.2, 5, 4.9, fruit=1)
    food_item(rm, 'strawberry', 'tfcflorae:food/strawberry', Category.fruit, 4, 0.5, 10, 4.9, fruit=0.5)
    food_item(rm, 'wintergreen_berry', 'tfcflorae:food/wintergreen_berry', Category.fruit, 4, 0.2, 5, 4.9, fruit=1)
    food_item(rm, 'barley', 'tfcflorae:food/barley', Category.grain, 4, 0, 0, 2)
    food_item(rm, 'barley_grain', 'tfcflorae:food/barley_grain', Category.grain, 4, 0, 0, 0.25)
    food_item(rm, 'barley_flour', 'tfcflorae:food/barley_flour', Category.grain, 4, 0, 0, 0.5)
    food_item(rm, 'barley_dough', 'tfcflorae:food/barley_dough', Category.grain, 4, 0, 0, 3)
    food_item(rm, 'barley_bread', 'tfcflorae:food/barley_bread', Category.bread, 4, 1, 0, 1, grain=1.5)
    food_item(rm, 'maize', 'tfcflorae:food/maize', Category.grain, 4, 0, 0, 2)
    food_item(rm, 'maize_grain', 'tfcflorae:food/maize_grain', Category.grain, 4, 0.5, 0, 0.25)
    food_item(rm, 'maize_flour', 'tfcflorae:food/maize_flour', Category.grain, 4, 0, 0, 0.5)
    food_item(rm, 'maize_dough', 'tfcflorae:food/maize_dough', Category.grain, 4, 0, 0, 3)
    food_item(rm, 'maize_bread', 'tfcflorae:food/maize_bread', Category.bread, 4, 1, 0, 1, grain=1)
    food_item(rm, 'oat', 'tfcflorae:food/oat', Category.grain, 4, 0, 0, 2)
    food_item(rm, 'oat_grain', 'tfcflorae:food/oat_grain', Category.grain, 4, 0.5, 0, 0.25)
    food_item(rm, 'oat_flour', 'tfcflorae:food/oat_flour', Category.grain, 4, 0, 0, 0.5)
    food_item(rm, 'oat_dough', 'tfcflorae:food/oat_dough', Category.grain, 4, 0, 0, 3)
    food_item(rm, 'oat_bread', 'tfcflorae:food/oat_bread', Category.bread, 4, 1, 0, 1, grain=1)
    # todo: figure out what to do with rice. thinking rice -> grain -> cooked rice in a pot recipe? so remove flour/dough/bread for this one
    food_item(rm, 'rice', 'tfcflorae:food/rice', Category.grain, 4, 0, 0, 2)
    food_item(rm, 'rice_grain', 'tfcflorae:food/rice_grain', Category.grain, 4, 0.5, 0, 0.25)
    food_item(rm, 'rice_flour', 'tfcflorae:food/rice_flour', Category.grain, 4, 0, 0, 0.5)
    food_item(rm, 'rice_dough', 'tfcflorae:food/rice_dough', Category.grain, 4, 0, 0, 3)
    food_item(rm, 'rice_bread', 'tfcflorae:food/rice_bread', Category.bread, 4, 1, 0, 1, grain=1.5)
    food_item(rm, 'rye', 'tfcflorae:food/rye', Category.grain, 4, 0, 0, 2)
    food_item(rm, 'rye_grain', 'tfcflorae:food/rye_grain', Category.grain, 4, 0.5, 0, 0.25)
    food_item(rm, 'rye_flour', 'tfcflorae:food/rye_flour', Category.grain, 4, 0, 0, 0.5)
    food_item(rm, 'rye_dough', 'tfcflorae:food/rye_dough', Category.grain, 4, 0, 0, 3)
    food_item(rm, 'rye_bread', 'tfcflorae:food/rye_bread', Category.bread, 4, 1, 0, 1, grain=1.5)
    food_item(rm, 'wheat', 'tfcflorae:food/wheat', Category.grain, 4, 0, 0, 2)
    food_item(rm, 'wheat_grain', 'tfcflorae:food/wheat_grain', Category.grain, 4, 0.5, 0, 0.25)
    food_item(rm, 'wheat_flour', 'tfcflorae:food/wheat_flour', Category.grain, 4, 0, 0, 0.5)
    food_item(rm, 'wheat_dough', 'tfcflorae:food/wheat_dough', Category.grain, 4, 0, 0, 3)
    food_item(rm, 'wheat_bread', 'tfcflorae:food/wheat_bread', Category.bread, 4, 1, 0, 1, grain=1)
    food_item(rm, 'beet', 'tfcflorae:food/beet', Category.vegetable, 4, 2, 0, 0.7, veg=1)
    food_item(rm, 'cabbage', 'tfcflorae:food/cabbage', Category.vegetable, 4, 0.5, 0, 1.2, veg=1)
    food_item(rm, 'carrot', 'tfcflorae:food/carrot', Category.vegetable, 4, 2, 0, 0.7, veg=1)
    food_item(rm, 'garlic', 'tfcflorae:food/garlic', Category.vegetable, 4, 0.5, 0, 0.4, veg=2)
    food_item(rm, 'green_bean', 'tfcflorae:food/green_bean', Category.vegetable, 4, 0.5, 0, 3.5, veg=1)
    food_item(rm, 'green_bell_pepper', 'tfcflorae:food/green_bell_pepper', Category.vegetable, 4, 0.5, 0, 2.7, veg=1)
    food_item(rm, 'onion', 'tfcflorae:food/onion', Category.vegetable, 4, 0.5, 0, 0.5, veg=1)
    food_item(rm, 'potato', 'tfcflorae:food/potato', Category.vegetable, 4, 2, 0, 0.666, veg=1.5)
    food_item(rm, 'red_bell_pepper', 'tfcflorae:food/red_bell_pepper', Category.vegetable, 4, 1, 0, 2.5, veg=1)
    food_item(rm, 'dried_seaweed', 'tfcflorae:food/dried_seaweed', Category.vegetable, 2, 1, 0, 2.5, veg=0.5)
    food_item(rm, 'dried_kelp', 'tfcflorae:food/dried_kelp', Category.vegetable, 2, 1, 0, 2.5, veg=0.5)
    food_item(rm, 'cattail_root', 'tfcflorae:food/cattail_root', Category.vegetable, 2, 1, 0, 2.5, grain=0.5)
    food_item(rm, 'taro_root', 'tfcflorae:food/taro_root', Category.vegetable, 2, 1, 0, 2.5, grain=0.5)
    food_item(rm, 'soybean', 'tfcflorae:food/soybean', Category.vegetable, 4, 2, 0, 2.5, veg=0.5, protein=1)
    food_item(rm, 'squash', 'tfcflorae:food/squash', Category.vegetable, 4, 1, 0, 1.67, veg=1.5)
    food_item(rm, 'sugarcane', 'tfcflorae:food/sugarcane', Category.vegetable, 4, 0, 0, 0.5)
    food_item(rm, 'tomato', 'tfcflorae:food/tomato', Category.vegetable, 4, 0.5, 5, 3.5, veg=1.5)
    food_item(rm, 'yellow_bell_pepper', 'tfcflorae:food/yellow_bell_pepper', Category.vegetable, 4, 1, 0, 2.5, veg=1)
    food_item(rm, 'cheese', 'tfcflorae:food/cheese', Category.dairy, 4, 2, 0, 0.3, dairy=3)
    food_item(rm, 'cooked_egg', 'tfcflorae:food/cooked_egg', Category.other, 4, 0.5, 0, 4, protein=1.5, dairy=0.25)
    # todo: figure out what to do with sugarcane, do we need a different plant? or item or something? or modify the vanilla one
    # food_item(rm, 'sugarcane', 'tfcflorae:food/sugarcane', Category.grain, 4, 0, 0, 1.6, grain=0.5)
    food_item(rm, 'beef', 'tfcflorae:food/beef', Category.meat, 4, 0, 0, 2, protein=2)
    food_item(rm, 'pork', 'tfcflorae:food/pork', Category.meat, 4, 0, 0, 2, protein=1.5)
    food_item(rm, 'chicken', 'tfcflorae:food/chicken', Category.meat, 4, 0, 0, 3, protein=1.5)
    food_item(rm, 'mutton', 'tfcflorae:food/mutton', Category.meat, 4, 0, 0, 3, protein=1.5)
    food_item(rm, 'bluegill', 'tfcflorae:food/bluegill', Category.meat, 4, 0, 0, 3, protein=1)
    food_item(rm, 'shellfish', 'tfcflorae:food/shellfish', Category.meat, 2, 0, 0, 2, protein=0.5)
    food_item(rm, 'cod', 'tfcflorae:food/cod', Category.meat, 4, 0, 0, 3, protein=1)
    food_item(rm, 'salmon', 'tfcflorae:food/salmon', Category.meat, 4, 0, 0, 3, protein=1)
    food_item(rm, 'tropical_fish', 'tfcflorae:food/tropical_fish', Category.meat, 4, 0, 0, 3, protein=1)
    food_item(rm, 'bear', 'tfcflorae:food/bear', Category.meat, 4, 0, 0, 2, protein=1.5)
    food_item(rm, 'calamari', 'tfcflorae:food/calamari', Category.meat, 4, 0, 0, 3, protein=0.5)
    food_item(rm, 'horse_meat', 'tfcflorae:food/horse_meat', Category.meat, 4, 0, 0, 2, protein=1.5)
    food_item(rm, 'turtle', 'tfcflorae:food/turtle', Category.meat, 4, 0, 0, 2, protein=1.5)
    food_item(rm, 'pheasant', 'tfcflorae:food/pheasant', Category.meat, 4, 0, 0, 3, protein=1.5)
    food_item(rm, 'venison', 'tfcflorae:food/venison', Category.meat, 4, 0, 0, 2, protein=1)
    food_item(rm, 'wolf', 'tfcflorae:food/wolf', Category.meat, 4, 0, 0, 3, protein=0.5)
    food_item(rm, 'rabbit', 'tfcflorae:food/rabbit', Category.meat, 4, 0, 0, 3, protein=0.5)
    food_item(rm, 'hyena', 'tfcflorae:food/hyena', Category.meat, 4, 0, 0, 3, protein=0.5)
    food_item(rm, 'duck', 'tfcflorae:food/duck', Category.meat, 4, 0, 0, 3, protein=0.5)
    food_item(rm, 'chevon', 'tfcflorae:food/chevon', Category.meat, 4, 0, 0, 3, protein=0.5)
    food_item(rm, 'gran_feline', 'tfcflorae:food/gran_feline', Category.meat, 4, 0, 0, 3, protein=0.5)
    food_item(rm, 'camelidae', 'tfcflorae:food/camelidae', Category.meat, 4, 0, 0, 3, protein=0.5)
    food_item(rm, 'cooked_beef', 'tfcflorae:food/cooked_beef', Category.cooked_meat, 4, 2, 0, 1.5, protein=2.5)
    food_item(rm, 'cooked_pork', 'tfcflorae:food/cooked_pork', Category.cooked_meat, 4, 2, 0, 1.5, protein=2.5)
    food_item(rm, 'cooked_chicken', 'tfcflorae:food/cooked_chicken', Category.cooked_meat, 4, 2, 0, 2.25, protein=2.5)
    food_item(rm, 'cooked_mutton', 'tfcflorae:food/cooked_mutton', Category.cooked_meat, 4, 2, 0, 2.25, protein=2.5)
    food_item(rm, 'cooked_shellfish', 'tfcflorae:food/cooked_shellfish', Category.cooked_meat, 2, 2, 0, 2.25, protein=1.5)
    food_item(rm, 'cooked_cod', 'tfcflorae:food/cooked_cod', Category.cooked_meat, 4, 1, 0, 2.25, protein=2)
    food_item(rm, 'cooked_tropical_fish', 'tfcflorae:food/cooked_tropical_fish', Category.cooked_meat, 4, 1, 0, 1.5, protein=2)
    food_item(rm, 'cooked_salmon', 'tfcflorae:food/cooked_salmon', Category.cooked_meat, 4, 1, 0, 2.25, protein=2)
    food_item(rm, 'cooked_bluegill', 'tfcflorae:food/cooked_bluegill', Category.cooked_meat, 4, 1, 0, 2.25, protein=2)
    food_item(rm, 'cooked_bear', 'tfcflorae:food/cooked_bear', Category.cooked_meat, 4, 1, 0, 1.5, protein=2.5)
    food_item(rm, 'cooked_calamari', 'tfcflorae:food/cooked_calamari', Category.cooked_meat, 4, 1, 0, 2.25, protein=1.5)
    food_item(rm, 'cooked_horse_meat', 'tfcflorae:food/cooked_horse_meat', Category.cooked_meat, 4, 2, 0, 1.5, protein=2.5)
    food_item(rm, 'cooked_turtle', 'tfcflorae:food/cooked_turtle', Category.meat, 4, 0, 0, 2, protein=2.5)
    food_item(rm, 'cooked_pheasant', 'tfcflorae:food/cooked_pheasant', Category.cooked_meat, 4, 1, 0, 2.25, protein=2.5)
    food_item(rm, 'cooked_venison', 'tfcflorae:food/cooked_venison', Category.cooked_meat, 4, 1, 0, 1.5, protein=2)
    food_item(rm, 'cooked_wolf', 'tfcflorae:food/cooked_wolf', Category.cooked_meat, 4, 1, 0, 2.25, protein=1.5)
    food_item(rm, 'cooked_rabbit', 'tfcflorae:food/cooked_rabbit', Category.cooked_meat, 4, 1, 0, 2.25, protein=1.5)
    food_item(rm, 'cooked_hyena', 'tfcflorae:food/cooked_hyena', Category.cooked_meat, 4, 1, 0, 2.25, protein=1.5)
    food_item(rm, 'cooked_duck', 'tfcflorae:food/cooked_duck', Category.cooked_meat, 4, 1, 0, 2.25, protein=1.5)
    food_item(rm, 'cooked_chevon', 'tfcflorae:food/cooked_chevon', Category.cooked_meat, 4, 1, 0, 2.25, protein=2)
    food_item(rm, 'cooked_gran_feline', 'tfcflorae:food/cooked_gran_feline', Category.cooked_meat, 4, 2, 0, 2.25, protein=2.5)
    food_item(rm, 'cooked_camelidae', 'tfcflorae:food/cooked_camelidae', Category.cooked_meat, 4, 2, 0, 2.25, protein=2.5)

    # Drinkables

    drinkable(rm, 'fresh_water', ['minecraft:water', 'tfcflorae:river_water'], thirst=10)
    drinkable(rm, 'salt_water', 'tfcflorae:salt_water', thirst=-1)
    drinkable(rm, 'alcohol', '#tfcflorae:alcohols', thirst=10, intoxication=4000)
    drinkable(rm, 'milk', '#tfcflorae:milks', thirst=10)

    # Climate Ranges

    for berry, data in BERRIES.items():
        climate_range(rm, 'plant/%s_bush' % berry, hydration=(hydration_from_rainfall(data.min_rain), 100, 0), temperature=(data.min_temp, data.max_temp, 0))
    for fruit, data in FRUITS.items():
        climate_range(rm, 'plant/%s_tree' % fruit, hydration=(hydration_from_rainfall(data.min_rain), 100, 0), temperature=(data.min_temp - 7, data.max_temp + 7, 0))

    # Crops
    for crop, data in CROPS.items():
        # todo: values
        climate_range(rm, 'crop/%s' % crop, hydration=(40, 100, 30), temperature=(5, 25, 5))

    # Fertilizer
    rm.data(('tfcflorae', 'fertilizers', 'sylvite'), fertilizer('tfcflorae:powder/sylvite', p=0.5))
    rm.data(('tfcflorae', 'fertilizers', 'wood_ash'), fertilizer('tfcflorae:powder/wood_ash', p=0.1, k=0.3))
    rm.data(('tfcflorae', 'fertilizers', 'guano'), fertilizer('tfcflorae:groundcover/guano', n=0.8, p=0.5, k=0.1))
    rm.data(('tfcflorae', 'fertilizers', 'saltpeter'), fertilizer('tfcflorae:powder/saltpeter', n=0.1, k=0.4))
    rm.data(('tfcflorae', 'fertilizers', 'bone_meal'), fertilizer('minecraft:bone_meal', p=0.1))
    rm.data(('tfcflorae', 'fertilizers', 'compost'), fertilizer('tfcflorae:compost', n=0.4, p=0.2, k=0.4))
    rm.data(('tfcflorae', 'fertilizers', 'pure_nitrogen'), fertilizer('tfcflorae:pure_nitrogen', n=0.1))
    rm.data(('tfcflorae', 'fertilizers', 'pure_phosphorus'), fertilizer('tfcflorae:pure_phosphorus', p=0.1))
    rm.data(('tfcflorae', 'fertilizers', 'pure_potassium'), fertilizer('tfcflorae:pure_potassium', k=0.1))

    # Entities
    rm.data(('tfcflorae', 'fauna', 'isopod'), fauna(distance_below_sea_level=20, climate=climate_config(max_temp=14)))
    rm.data(('tfcflorae', 'fauna', 'crayfish'), fauna(distance_below_sea_level=20, climate=climate_config(min_temp=5, min_rain=125)))
    rm.data(('tfcflorae', 'fauna', 'lobster'), fauna(distance_below_sea_level=1, climate=climate_config(max_temp=21)))
    rm.data(('tfcflorae', 'fauna', 'horseshoe_crab'), fauna(distance_below_sea_level=10, climate=climate_config(min_temp=10, max_temp=21, max_rain=400)))
    rm.data(('tfcflorae', 'fauna', 'cod'), fauna(climate=climate_config(max_temp=18), distance_below_sea_level=5))
    rm.data(('tfcflorae', 'fauna', 'pufferfish'), fauna(climate=climate_config(min_temp=10), distance_below_sea_level=3))
    rm.data(('tfcflorae', 'fauna', 'tropical_fish'), fauna(climate=climate_config(min_temp=18), distance_below_sea_level=3))
    rm.data(('tfcflorae', 'fauna', 'jellyfish'), fauna(climate=climate_config(min_temp=18), distance_below_sea_level=3))
    rm.data(('tfcflorae', 'fauna', 'orca'), fauna(distance_below_sea_level=35, climate=climate_config(max_temp=19, min_rain=100), chance=10))
    rm.data(('tfcflorae', 'fauna', 'dolphin'), fauna(distance_below_sea_level=20, climate=climate_config(min_temp=10, min_rain=200), chance=10))
    rm.data(('tfcflorae', 'fauna', 'manatee'), fauna(distance_below_sea_level=3, climate=climate_config(min_temp=20, min_rain=300), chance=10))
    rm.data(('tfcflorae', 'fauna', 'salmon'), fauna(climate=climate_config(min_temp=-5)))
    rm.data(('tfcflorae', 'fauna', 'bluegill'), fauna(climate=climate_config(min_temp=-10, max_temp=26)))
    rm.data(('tfcflorae', 'fauna', 'penguin'), fauna(climate=climate_config(max_temp=-14, min_rain=75)))
    rm.data(('tfcflorae', 'fauna', 'turtle'), fauna(climate=climate_config(min_temp=21, min_rain=250)))
    rm.data(('tfcflorae', 'fauna', 'polar_bear'), fauna(climate=climate_config(max_temp=-10, min_rain=100)))
    rm.data(('tfcflorae', 'fauna', 'grizzly_bear'), fauna(climate=climate_config(min_forest='edge', max_temp=15, min_temp=-15, min_rain=200)))
    rm.data(('tfcflorae', 'fauna', 'black_bear'), fauna(climate=climate_config(min_forest='edge', max_temp=20, min_temp=5, min_rain=250)))
    rm.data(('tfcflorae', 'fauna', 'cougar'), fauna(climate=climate_config(min_temp=-10, max_temp=21, min_rain=150)))
    rm.data(('tfcflorae', 'fauna', 'panther'), fauna(climate=climate_config(min_temp=-10, max_temp=21, min_rain=150)))
    rm.data(('tfcflorae', 'fauna', 'lion'), fauna(climate=climate_config(max_forest='edge', min_temp=16, min_rain=50, max_rain=300)))
    rm.data(('tfcflorae', 'fauna', 'sabertooth'), fauna(climate=climate_config(max_temp=0, min_rain=250)))
    rm.data(('tfcflorae', 'fauna', 'squid'), fauna(distance_below_sea_level=15))
    rm.data(('tfcflorae', 'fauna', 'octopoteuthis'), fauna(max_brightness=0, distance_below_sea_level=33))
    rm.data(('tfcflorae', 'fauna', 'pig'), fauna(climate=climate_config(min_temp=0, max_temp=25, min_rain=100)))
    rm.data(('tfcflorae', 'fauna', 'cow'), fauna(climate=climate_config(min_temp=0, max_temp=25, min_rain=100)))
    rm.data(('tfcflorae', 'fauna', 'alpaca'), fauna(climate=climate_config(min_temp=0, max_temp=25, min_rain=100)))
    rm.data(('tfcflorae', 'fauna', 'chicken'), fauna(climate=climate_config(min_temp=0, max_temp=25, min_rain=100)))

    # Lamp Fuel - burn rate = ticks / mB. 8000 ticks @ 250mB ~ 83 days ~ the 1.12 length of olive oil burning
    rm.data(('tfcflorae', 'lamp_fuels', 'olive_oil'), lamp_fuel('tfcflorae:olive_oil', 8000))
    rm.data(('tfcflorae', 'lamp_fuels', 'tallow'), lamp_fuel('tfcflorae:tallow', 1800))
    rm.data(('tfcflorae', 'lamp_fuels', 'lava'), lamp_fuel('minecraft:lava', -1, 'tfcflorae:metal/lamp/blue_steel'))

    # Misc Block Loot
    rm.block_loot('minecraft:glass', {'name': 'tfcflorae:glass_shard', 'conditions': [loot_invert(loot_tables.silk_touch())]}, {'name': 'minecraft:glass', 'conditions': [loot_tables.silk_touch()]})

    # Entity Loot

    for mob in ('cod', 'bluegill', 'tropical_fish', 'salmon'):
        mob_loot(rm, mob, 'tfcflorae:food/%s' % mob)
    mob_loot(rm, 'pufferfish', 'minecraft:pufferfish')
    mob_loot(rm, 'squid', 'minecraft:ink_sac', max_amount=10, extra_pool={'name': 'tfcflorae:food/calamari'})
    mob_loot(rm, 'octopoteuthis', 'minecraft:glow_ink_sac', max_amount=10, extra_pool={'name': 'tfcflorae:food/calamari'})
    for mob in ('isopod', 'lobster', 'horseshoe_crab', 'crayfish'):
        mob_loot(rm, mob, 'tfcflorae:food/shellfish')
    for mob in ('orca', 'dolphin', 'manatee'):
        mob_loot(rm, mob, 'tfcflorae:blubber', min_amount=0, max_amount=2, bones=4)
    mob_loot(rm, 'penguin', 'minecraft:feather', max_amount=3, hide_size='small', hide_chance=0.5, bones=2)
    mob_loot(rm, 'turtle', 'minecraft:scute')
    mob_loot(rm, 'polar_bear', 'tfcflorae:large_raw_hide', bones=6)
    mob_loot(rm, 'grizzly_bear', 'tfcflorae:large_raw_hide', bones=6)
    mob_loot(rm, 'black_bear', 'tfcflorae:large_raw_hide', bones=6)
    mob_loot(rm, 'cougar', 'tfcflorae:large_raw_hide', bones=6)
    mob_loot(rm, 'panther', 'tfcflorae:large_raw_hide', bones=6)
    mob_loot(rm, 'lion', 'tfcflorae:large_raw_hide', bones=6)
    mob_loot(rm, 'sabertooth', 'tfcflorae:large_raw_hide', bones=8)
    mob_loot(rm, 'pig', 'tfcflorae:food/pork', 1, 4, 'medium', bones=3)
    mob_loot(rm, 'cow', 'tfcflorae:food/beef', 1, 4, 'large', bones=4)
    mob_loot(rm, 'alpaca', 'tfcflorae:food/camelidae', 1, 4, 'medium', bones=4, extra_pool={'name': 'tfcflorae:wool'})
    mob_loot(rm, 'chicken', 'tfcflorae:food/chicken', extra_pool={'name': 'minecraft:feather', 'functions': [loot_tables.set_count(1, 4)]})

    global_loot_modifiers(rm, 'tfcflorae:reset_decay')
    global_loot_modifier(rm, 'reset_decay', 'tfcflorae:reset_decay', {'condition': 'tfcflorae:always_true'})

def mob_loot(rm: ResourceManager, name: str, drop: str, min_amount: int = 1, max_amount: int = None, hide_size: str = None, hide_chance: float = 1, bones: int = 0, extra_pool: Dict[str, Any] = None):
    func = None if max_amount is None else loot_tables.set_count(min_amount, max_amount)
    pools = [{'name': drop, 'functions': func}]
    if hide_size is not None:
        func = None if hide_chance == 1 else loot_tables.random_chance(hide_chance)
        pools.append({'name': 'tfcflorae:%s_raw_hide' % hide_size, 'conditions': func})
    if bones != 0:
        pools.append({'name': 'minecraft:bone', 'functions': loot_tables.set_count(1, bones)})
    if extra_pool is not None:
        pools.append(extra_pool)
    rm.entity_loot(name, *pools)

def loot_invert(condition: utils.JsonObject):
    return {
        'condition': 'minecraft:inverted',
        'term': condition
    }

def global_loot_modifier(rm: ResourceManager, name: str, mod_type: str, *conditions: utils.Json):
    rm.write((*rm.resource_dir, 'data', rm.domain, 'loot_modifiers', name), {
        'type': mod_type,
        'conditions': [c for c in conditions]
    })

# note for the mcresources dev: these work exactly the same as tags so if you implement this, do it like that
def global_loot_modifiers(rm: ResourceManager, *modifiers: str):
    rm.write((*rm.resource_dir, 'data', 'forge', 'loot_modifiers', 'global_loot_modifiers'), {
        'replace': False,
        'entries': [m for m in modifiers]
    })

def lamp_fuel(fluid: str, burn_rate: int, valid_lamps: str = '#tfcflorae:lamps'):
    return {
        'fluid': fluid,
        'burn_rate': burn_rate,
        'valid_lamps': {'type': 'tfcflorae:tag', 'tag': valid_lamps.replace('#', '')} if '#' in valid_lamps else valid_lamps
    }

def fertilizer(ingredient: str, n: float = None, p: float = None, k: float = None):
    return {
        'ingredient': utils.ingredient(ingredient),
        'nitrogen': n,
        'potassium': k,
        'phosphorus': p
    }

def climate_config(min_temp: Optional[float] = None, max_temp: Optional[float] = None, min_rain: Optional[float] = None, max_rain: Optional[float] = None, needs_forest: Optional[bool] = False, fuzzy: Optional[bool] = None, min_forest: Optional[str] = None, max_forest: Optional[str] = None) -> Dict[str, Any]:
    return {
        'min_temperature': min_temp,
        'max_temperature': max_temp,
        'min_rainfall': min_rain,
        'max_rainfall': max_rain,
        'min_forest': 'normal' if needs_forest else min_forest,
        'max_forest': max_forest,
        'fuzzy': fuzzy
    }


def fauna(chance: int = None, distance_below_sea_level: int = None, climate: Dict[str, Any] = None, solid_ground: bool = None, max_brightness: int = None) -> Dict[str, Any]:
    return {
        'chance': chance,
        'distance_below_sea_level': distance_below_sea_level,
        'climate': climate,
        'solid_ground': solid_ground,
        'max_brightness': max_brightness
    }


def food_item(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, category: Category, hunger: int, saturation: float, water: int, decay: float, fruit: Optional[float] = None, veg: Optional[float] = None, protein: Optional[float] = None, grain: Optional[float] = None, dairy: Optional[float] = None):
    rm.item_tag('tfcflorae:foods', ingredient)
    rm.data(('tfcflorae', 'food_items', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'category': category.name,
        'hunger': hunger,
        'saturation': saturation,
        'water': water if water != 0 else None,
        'decay': decay,
        'fruit': fruit,
        'vegetables': veg,
        'protein': protein,
        'grain': grain,
        'dairy': dairy
    })
    rm.item_tag('foods', ingredient)
    if category in (Category.fruit, Category.vegetable):
        rm.item_tag('foods/%ss' % category.name.lower(), ingredient)
    if category in (Category.meat, Category.cooked_meat):
        rm.item_tag('foods/meats', ingredient)
        if category == Category.cooked_meat:
            rm.item_tag('foods/cooked_meats', ingredient)
        else:
            rm.item_tag('foods/raw_meats', ingredient)
    if category == Category.dairy:
        rm.item_tag('foods/dairy', ingredient)


def drinkable(rm: ResourceManager, name_parts: utils.ResourceIdentifier, fluid: utils.Json, thirst: Optional[int] = None, intoxication: Optional[int] = None):
    rm.data(('tfcflorae', 'drinkables', name_parts), {
        'ingredient': fluid_ingredient(fluid),
        'thirst': thirst,
        'intoxication': intoxication
        # todo: effects
        # todo: milk effects
    })


def item_size(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, size: Size, weight: Weight):
    rm.data(('tfcflorae', 'item_sizes', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'size': size.name,
        'weight': weight.name
    })


def item_heat(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, heat_capacity: float, melt_temperature: Optional[float] = None):
    if melt_temperature is not None:
        forging_temperature = melt_temperature * 0.6
        welding_temperature = melt_temperature * 0.8
    else:
        forging_temperature = welding_temperature = None
    rm.data(('tfcflorae', 'item_heats', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'heat_capacity': heat_capacity,
        'forging_temperature': forging_temperature,
        'welding_temperature': welding_temperature
    })


def fuel_item(rm: ResourceManager, name_parts: utils.ResourceIdentifier, ingredient: utils.Json, duration: int, temperature: float):
    rm.data(('tfcflorae', 'fuels', name_parts), {
        'ingredient': utils.ingredient(ingredient),
        'duration': duration,
        'temperature': temperature
    })


def climate_range(rm: ResourceManager, name_parts: utils.ResourceIdentifier, hydration: Tuple[int, int, int] = None, temperature: Tuple[float, float, float] = None):
    data = {}
    if hydration is not None:
        data.update({'min_hydration': hydration[0], 'max_hydration': hydration[1], 'hydration_wiggle_range': hydration[2]})
    if temperature is not None:
        data.update({'min_temperature': temperature[0], 'max_temperature': temperature[1], 'temperature_wiggle_range': temperature[2]})
    rm.data(('tfcflorae', 'climate_ranges', name_parts), data)


def hydration_from_rainfall(rainfall: int) -> int:
    return rainfall * 60 // 500


def block_and_item_tag(rm: ResourceManager, name_parts: utils.ResourceIdentifier, *values: utils.ResourceIdentifier, replace: bool = False):
    rm.block_tag(name_parts, *values, replace=replace)
    rm.item_tag(name_parts, *values, replace=replace)
