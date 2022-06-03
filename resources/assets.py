#  Work under Copyright. Licensed under the EUPL.
#  See the project README.md and LICENSE.txt for more information.

import itertools

from mcresources import ResourceManager, ItemContext, utils, block_states, loot_tables

from constants import *


def generate(rm: ResourceManager):
    # Wood Blocks
    for wood in WOODS.keys():
        # Logs
        for variant in ('log', 'stripped_log', 'wood', 'stripped_wood'):
            block = rm.blockstate(('wood', variant, wood), variants={
                'axis=y': {'model': 'tfcflorae:block/wood/%s/%s' % (variant, wood)},
                'axis=z': {'model': 'tfcflorae:block/wood/%s/%s' % (variant, wood), 'x': 90},
                'axis=x': {'model': 'tfcflorae:block/wood/%s/%s' % (variant, wood), 'x': 90, 'y': 90}
            }, use_default_model=False)

            stick_with_hammer = {
                'name': 'minecraft:stick',
                'conditions': [match_tag('tfcflorae:hammers')],
                'functions': [loot_tables.set_count(1, 4)]
            }
            if variant == 'wood' or variant == 'stripped_wood':
                block.with_block_loot((
                    stick_with_hammer,
                    {  # wood blocks will only drop themselves if non-natural
                        'name': 'tfcflorae:wood/%s/%s' % (variant, wood),
                        'conditions': loot_tables.block_state_property('tfcflorae:wood/%s/%s[natural=false]' % (variant, wood))
                    },
                    'tfcflorae:wood/%s/%s' % (variant.replace('wood', 'log'), wood)
                ))
            else:
                block.with_block_loot((
                    stick_with_hammer,
                    'tfcflorae:wood/%s/%s' % (variant, wood)  # logs drop themselves always
                ))

            if variant != 'log':
                block.with_item_model()
            else:
                rm.item_model(('wood', variant, wood), 'tfcflorae:item/wood/log/' + wood)
            end = 'tfcflorae:block/wood/%s/%s' % (variant.replace('log', 'log_top').replace('wood', 'log'), wood)
            side = 'tfcflorae:block/wood/%s/%s' % (variant.replace('wood', 'log'), wood)
            block.with_block_model({'end': end, 'side': side}, parent='block/cube_column')
            if 'stripped' in variant:
                block.with_lang(lang(variant.replace('_', ' ' + wood + ' ')))
            else:
                block.with_lang(lang('%s %s', wood, variant))
            if variant == 'log':
                block.with_tag('minecraft:logs')
        rm.item_model(('wood', 'lumber', wood)).with_lang(lang('%s Lumber', wood))
        rm.item_model(('wood', 'sign', wood)).with_lang(lang('%s Sign', wood)).with_tag('minecraft:signs')
        rm.item_model(('wood', 'boat', wood)).with_lang(lang('%s Boat', wood))

        # Groundcover
        for variant in ('twig', 'fallen_leaves'):
            block = rm.blockstate('wood/%s/%s' % (variant, wood), variants={"": four_ways('tfcflorae:block/wood/%s/%s' % (variant, wood))}, use_default_model=False)
            block.with_lang(lang('%s %s', wood, variant))

            if variant == 'twig':
                block.with_block_model({'side': 'tfcflorae:block/wood/log/%s' % wood, 'top': 'tfcflorae:block/wood/log_top/%s' % wood}, parent='tfcflorae:block/groundcover/%s' % variant)
                rm.item_model('wood/%s/%s' % (variant, wood), 'tfcflorae:item/wood/twig/%s' % wood)
                block.with_block_loot('tfcflorae:wood/twig/%s' % wood)
            elif variant == 'fallen_leaves':
                block.with_block_model('tfcflorae:block/wood/leaves/%s' % wood, parent='tfcflorae:block/groundcover/%s' % variant)
                rm.item_model('wood/%s/%s' % (variant, wood), 'tfcflorae:item/groundcover/fallen_leaves')
                block.with_block_loot('tfcflorae:wood/%s/%s' % (variant, wood))
            else:
                block.with_item_model()

            block.with_tag('can_be_snow_piled')

        # Leaves
        block = rm.blockstate(('wood', 'leaves', wood), model='tfcflorae:block/wood/leaves/%s' % wood)
        block.with_block_model('tfcflorae:block/wood/leaves/%s' % wood, parent='block/leaves')
        block.with_item_model()
        block.with_tag('minecraft:leaves')
        block.with_block_loot(({
            'name': 'tfcflorae:wood/leaves/%s' % wood,
            'conditions': [loot_tables.or_condition(match_tag('forge:shears'), loot_tables.silk_touch())]
        }, {
            'name': 'tfcflorae:wood/sapling/%s' % wood,
            'conditions': ['minecraft:survives_explosion', condition_chance(TREE_SAPLING_DROP_CHANCES[wood])]
        }), ({
            'name': 'minecraft:stick',
            'conditions': [match_tag('tfcflorae:sharp_tools'), condition_chance(0.2)],
            'functions': [loot_tables.set_count(1, 2)]
        }, {
            'name': 'minecraft:stick',
            'conditions': [condition_chance(0.05)],
            'functions': [loot_tables.set_count(1, 2)]
        }))

        # Sapling
        block = rm.blockstate(('wood', 'sapling', wood), 'tfcflorae:block/wood/sapling/%s' % wood)
        block.with_block_model({'cross': 'tfcflorae:block/wood/sapling/%s' % wood}, 'block/cross')
        block.with_block_loot('tfcflorae:wood/sapling/%s' % wood)
        rm.item_model(('wood', 'sapling', wood), 'tfcflorae:block/wood/sapling/%s' % wood)

        # Planks and variant blocks
        block = rm.block(('wood', 'planks', wood))
        block.with_blockstate()
        block.with_block_model()
        block.with_item_model()
        block.with_block_loot('tfcflorae:wood/planks/%s' % wood)
        block.with_lang(lang('%s planks', wood))
        block.make_slab()
        block.make_stairs()
        block.make_button()
        block.make_door()
        block.make_pressure_plate()
        block.make_trapdoor()
        block.make_fence()
        block.make_fence_gate()

        for block_type in ('bookshelf', 'button', 'fence', 'fence_gate', 'pressure_plate', 'slab', 'stairs', 'trapdoor'):
            rm.block_loot('wood/planks/%s_%s' % (wood, block_type), 'tfcflorae:wood/planks/%s_%s' % (wood, block_type))

        # Tool Rack
        rack_namespace = 'tfcflorae:wood/planks/%s_tool_rack' % wood
        block = rm.blockstate(rack_namespace, model='tfcflorae:block/wood/planks/%s_tool_rack' % wood, variants=four_rotations('tfcflorae:block/wood/planks/%s_tool_rack' % wood, (270, 180, None, 90)))
        block.with_block_model(textures={'texture': 'tfcflorae:block/wood/planks/%s' % wood, 'particle': 'tfcflorae:block/wood/planks/%s' % wood}, parent='tfcflorae:block/tool_rack')
        block.with_lang(lang('%s Tool Rack', wood)).with_block_loot(rack_namespace).with_item_model()

        # Loom
        block = rm.blockstate('tfcflorae:wood/planks/%s_loom' % wood, model='tfcflorae:block/wood/planks/%s_loom' % wood, variants=four_rotations('tfcflorae:block/wood/planks/%s_loom' % wood, (270, 180, None, 90)))
        block.with_block_model(textures={'texture': 'tfcflorae:block/wood/planks/%s' % wood, 'particle': 'tfcflorae:block/wood/planks/%s' % wood}, parent='tfcflorae:block/loom')
        block.with_item_model().with_lang(lang('%s loom', wood)).with_block_loot('tfcflorae:wood/planks/%s_loom' % wood).with_tag('minecraft:mineable/axe')

        # Bookshelf
        block = rm.blockstate('tfcflorae:wood/planks/%s_bookshelf' % wood).with_item_model().with_lang(lang('%s bookshelf', wood))
        block.with_block_model({'end': 'tfcflorae:block/wood/planks/%s' % wood, 'side': 'tfcflorae:block/wood/planks/%s_bookshelf' % wood}, parent='minecraft:block/bookshelf')

        # Workbench
        rm.blockstate(('wood', 'planks', '%s_workbench' % wood)).with_block_model(parent='minecraft:block/cube', textures={
            'particle': 'tfcflorae:block/wood/planks/%s_workbench_front' % wood,
            'north': 'tfcflorae:block/wood/planks/%s_workbench_front' % wood,
            'south': 'tfcflorae:block/wood/planks/%s_workbench_side' % wood,
            'east': 'tfcflorae:block/wood/planks/%s_workbench_side' % wood,
            'west': 'tfcflorae:block/wood/planks/%s_workbench_front' % wood,
            'up': 'tfcflorae:block/wood/planks/%s_workbench_top' % wood,
            'down': 'tfcflorae:block/wood/planks/%s' % wood
        }).with_item_model().with_lang(lang('%s Workbench', wood)).with_tag('tfcflorae:workbench').with_block_loot('tfcflorae:wood/planks/%s_workbench' % wood)

        # Doors
        rm.item_model('tfcflorae:wood/planks/%s_door' % wood, 'tfcflorae:item/wood/planks/%s_door' % wood)
        rm.block_loot('wood/planks/%s_door' % wood, {'name': 'tfcflorae:wood/planks/%s_door' % wood, 'conditions': [loot_tables.block_state_property('tfcflorae:wood/planks/%s_door[half=lower]' % wood)]})

        # Log Fences
        log_fence_namespace = 'tfcflorae:wood/planks/' + wood + '_log_fence'
        rm.blockstate_multipart(log_fence_namespace, *block_states.fence_multipart('tfcflorae:block/wood/planks/' + wood + '_log_fence_post', 'tfcflorae:block/wood/planks/' + wood + '_log_fence_side'))
        rm.block_model(log_fence_namespace + '_post', textures={'texture': 'tfcflorae:block/wood/log/' + wood}, parent='block/fence_post')
        rm.block_model(log_fence_namespace + '_side', textures={'texture': 'tfcflorae:block/wood/planks/' + wood}, parent='block/fence_side')
        rm.block_model(log_fence_namespace + '_inventory', textures={'log': 'tfcflorae:block/wood/log/' + wood, 'planks': 'tfcflorae:block/wood/planks/' + wood}, parent='tfcflorae:block/log_fence_inventory')
        rm.item_model('tfcflorae:wood/planks/' + wood + '_log_fence', parent='tfcflorae:block/wood/planks/' + wood + '_log_fence_inventory', no_textures=True)
        rm.block_loot(log_fence_namespace, log_fence_namespace)

        texture = 'tfcflorae:block/wood/sheet/%s' % wood
        connection = 'tfcflorae:block/wood/support/%s_connection' % wood
        rm.blockstate_multipart(('wood', 'vertical_support', wood),
            {'model': 'tfcflorae:block/wood/support/%s_vertical' % wood},
            ({'north': True}, {'model': connection, 'y': 270}),
            ({'east': True}, {'model': connection}),
            ({'south': True}, {'model': connection, 'y': 90}),
            ({'west': True}, {'model': connection, 'y': 180}),
        ).with_tag('tfcflorae:support_beam').with_lang(lang('%s Support', wood)).with_block_loot('tfcflorae:wood/support/' + wood)
        rm.blockstate_multipart(('wood', 'horizontal_support', wood),
            {'model': 'tfcflorae:block/wood/support/%s_horizontal' % wood},
            ({'north': True}, {'model': connection, 'y': 270}),
            ({'east': True}, {'model': connection}),
            ({'south': True}, {'model': connection, 'y': 90}),
            ({'west': True}, {'model': connection, 'y': 180}),
        ).with_tag('tfcflorae:support_beam').with_lang(lang('%s Support', wood)).with_block_loot('tfcflorae:wood/support/' + wood)

        rm.block_model('tfcflorae:wood/support/%s_inventory' % wood, textures={'texture': texture}, parent='tfcflorae:block/wood/support/inventory')
        rm.block_model('tfcflorae:wood/support/%s_vertical' % wood, textures={'texture': texture, 'particle': texture}, parent='tfcflorae:block/wood/support/vertical')
        rm.block_model('tfcflorae:wood/support/%s_connection' % wood, textures={'texture': texture, 'particle': texture}, parent='tfcflorae:block/wood/support/connection')
        rm.block_model('tfcflorae:wood/support/%s_horizontal' % wood, textures={'texture': texture, 'particle': texture}, parent='tfcflorae:block/wood/support/horizontal')
        rm.item_model(('wood', 'support', wood), no_textures=True, parent='tfcflorae:block/wood/support/%s_inventory' % wood).with_lang(lang('%s Support', wood))

        for chest in ('chest', 'trapped_chest'):
            rm.blockstate(('wood', chest, wood), model='tfcflorae:block/wood/%s/%s' % (chest, wood)).with_lang(lang('%s %s', wood, chest)).with_tag('minecraft:features_cannot_replace').with_tag('forge:chests/wooden').with_tag('minecraft:lava_pool_stone_cannot_replace')
            rm.block_model(('wood', chest, wood), textures={'particle': 'tfcflorae:block/wood/planks/%s' % wood}, parent=None)
            rm.item_model(('wood', chest, wood), {'particle': 'tfcflorae:block/wood/planks/%s' % wood}, parent='minecraft:item/chest')
            rm.block_loot(('wood', chest, wood), {'name': 'tfcflorae:wood/%s/%s'%(chest,wood)})

        rm.block_model('wood/sluice/%s_upper' % wood, textures={'texture': 'tfcflorae:block/wood/sheet/%s' % wood}, parent='tfcflorae:block/sluice_upper')
        rm.block_model('wood/sluice/%s_lower' % wood, textures={'texture': 'tfcflorae:block/wood/sheet/%s' % wood}, parent='tfcflorae:block/sluice_lower')
        block = rm.blockstate(('wood', 'sluice', wood), variants={**four_rotations('tfcflorae:block/wood/sluice/%s_upper' % wood, (90, 0, 180, 270), suffix=',upper=true'), **four_rotations('tfcflorae:block/wood/sluice/%s_lower' % wood, (90, 0, 180, 270), suffix=',upper=false')}).with_lang(lang('%s sluice', wood))
        block.with_block_loot({'name': 'tfcflorae:wood/sluice/%s' % wood, 'conditions': [loot_tables.block_state_property('tfcflorae:wood/sluice/%s[upper=true]' % wood)]})
        rm.item_model(('wood', 'sluice', wood), parent='tfcflorae:block/wood/sluice/%s_lower' % wood, no_textures=True)

        rm.blockstate(('wood', 'planks', '%s_sign' % wood), model='tfcflorae:block/wood/planks/%s_sign' % wood).with_lang(lang('%s Sign', wood)).with_block_model({'particle': 'tfcflorae:block/wood/planks/%s' % wood}, parent=None).with_block_loot('tfcflorae:wood/sign/%s' % wood).with_tag('minecraft:standing_sings')
        rm.blockstate(('wood', 'planks', '%s_wall_sign' % wood), model='tfcflorae:block/wood/planks/%s_sign' % wood).with_lang(lang('%s Sign', wood)).with_lang(lang('%s Sign', wood)).with_tag('minecraft:wall_signs')

        # Barrels
        texture = 'tfcflorae:block/wood/planks/%s' % wood
        textures = {'particle': texture, 'planks': texture, 'sheet': 'tfcflorae:block/wood/sheet/%s' % wood, 'hoop': 'tfcflorae:block/barrel_hoop'}
        block = rm.blockstate(('wood', 'barrel', wood), variants={
            'sealed=true': {'model': 'tfcflorae:block/wood/barrel_sealed/%s' % wood},
            'sealed=false': {'model': 'tfcflorae:block/wood/barrel/%s' % wood}
        })
        item_model_property(rm, ('wood', 'barrel', wood), [{'predicate': {'tfcflorae:sealed': 1.0}, 'model': 'tfcflorae:block/wood/barrel_sealed/%s' % wood}], {'parent': 'tfcflorae:block/wood/barrel/%s' % wood})
        block.with_block_model(textures, 'tfcflorae:block/barrel')
        rm.block_model(('wood', 'barrel_sealed', wood), textures, 'tfcflorae:block/barrel_sealed')
        block.with_lang(lang('%s barrel', wood))
        block.with_tag('tfcflorae:barrels').with_tag('minecraft:mineable/axe')
        block.with_block_loot(({
            'name': 'tfcflorae:wood/barrel/%s' % wood,
            'functions': [loot_tables.copy_block_entity_name(), loot_tables.copy_block_entity_nbt()],
            'conditions': [loot_tables.block_state_property('tfcflorae:wood/barrel/%s[sealed=true]' % wood)]
        }, 'tfcflorae:wood/barrel/%s' % wood))

        # Lecterns
        block = rm.blockstate('tfcflorae:wood/lectern/%s' % wood, variants=four_rotations('tfcflorae:block/wood/lectern/%s' % wood, (90, None, 180, 270)))
        block.with_block_model(textures={'bottom': 'tfcflorae:block/wood/planks/%s' % wood, 'base': 'tfcflorae:block/wood/lectern/%s/base' % wood, 'front': 'tfcflorae:block/wood/lectern/%s/front' % wood, 'sides': 'tfcflorae:block/wood/lectern/%s/sides' % wood, 'top': 'tfcflorae:block/wood/lectern/%s/top' % wood, 'particle': 'tfcflorae:block/wood/lectern/%s/sides' % wood}, parent='minecraft:block/lectern')
        block.with_item_model()
        block.with_lang(lang("%s lectern" % wood))
        block.with_block_loot('tfcflorae:wood/lectern/%s' % wood)
        block.with_tag('minecraft:mineable/axe')

        # Scribing Table
        block = rm.blockstate('tfcflorae:wood/scribing_table/%s' % wood, variants=four_rotations('tfcflorae:block/wood/scribing_table/%s' % wood, (90, None, 180, 270)))
        block.with_block_model(textures={'top': 'tfcflorae:block/wood/scribing_table/%s' % wood, 'leg' : 'tfcflorae:block/wood/log/%s' % wood, 'side' : 'tfcflorae:block/wood/planks/%s' % wood, 'misc': 'tfcflorae:block/wood/scribing_table/scribing_paraphernalia', 'particle': 'tfcflorae:block/wood/planks/%s' % wood}, parent='tfcflorae:block/scribing_table')
        block.with_item_model()
        block.with_lang(lang("%s scribing table" % wood))
        block.with_block_loot('tfcflorae:wood/lectern/%s' % wood)
        block.with_tag('minecraft:mineable/axe')

        # Candles
        for color in [None, *COLORS]:
            namespace = 'tfcflorae:candle' + ('/'+color if color else '')
            candle = '%s_candle' % color if color else 'candle'
            block = rm.blockstate(namespace, variants={
                "candles=1,lit=false": {"model": "minecraft:block/%s_one_candle" % candle},
                "candles=1,lit=true": {"model": "minecraft:block/%s_one_candle_lit" % candle},
                "candles=2,lit=false": {"model": "minecraft:block/%s_two_candles" % candle},
                "candles=2,lit=true": {"model": "minecraft:block/%s_two_candles_lit" % candle},
                "candles=3,lit=false": {"model": "minecraft:block/%s_three_candles" % candle},
                "candles=3,lit=true": {"model": "minecraft:block/%s_three_candles_lit" % candle},
                "candles=4,lit=false": {"model": "minecraft:block/%s_four_candles" % candle},
                "candles=4,lit=true": {"model": "minecraft:block/%s_four_candles_lit" % candle}
            })
            block.with_lang(lang('%s candle' % color if color else 'candle'))
            block.with_block_loot(*[{'name': namespace, 'functions': [loot_tables.set_count(i)], 'conditions': [loot_tables.block_state_property('%s[candles=%s]' % (namespace, i))]} for i in range(1,5)])
            rm.item_model(namespace, parent='minecraft:item/%s' % candle, no_textures=True)
            if color: rm.item_tag('tfcflorae:colored_candles', namespace)

        # Tags
        for fence_namespace in ('tfcflorae:wood/planks/' + wood + '_fence', log_fence_namespace):
            rm.block_tag('minecraft:wooden_fences', fence_namespace)
            rm.block_tag('minecraft:fences', fence_namespace)
            rm.block_tag('forge:fences', fence_namespace)
            rm.block_tag('forge:fences/wooden', fence_namespace)

        fence_gate_namespace = 'tfcflorae:wood/planks/' + wood + '_fence_gate'
        rm.block_tag('forge:fence_gates/wooden', fence_gate_namespace)
        rm.block_tag('forge:fence_gates', fence_gate_namespace)
        rm.block_tag('minecraft:doors', 'tfcflorae:wood/planks/' + wood + '_door')
        rm.block_tag('minecraft:buttons', 'tfcflorae:wood/planks/' + wood + '_button')
        rm.block_tag('minecraft:wooden_buttons', 'tfcflorae:wood/planks/' + wood + '_button')
        rm.block_tag('minecraft:wooden_pressure_plates', 'tfcflorae:wood/planks/' + wood + '_pressure_plate')
        rm.block_tag('minecraft:wooden_slabs', 'tfcflorae:wood/planks/' + wood + '_slab')
        rm.block_tag('minecraft:wooden_stairs', 'tfcflorae:wood/planks/' + wood + '_stairs')

        for variant in ('log', 'stripped_log', 'wood', 'stripped_wood'):
            if variant != 'log':
                rm.block_tag('minecraft:logs', 'tfcflorae:wood/' + variant + '/' + wood)
            rm.block_tag('tfcflorae:' + wood + '_logs', 'tfcflorae:wood/' + variant + '/' + wood)
            rm.block_tag('tfcflorae:creeping_plantable_on', 'tfcflorae:wood/' + variant + '/' + wood)

        # Lang
        for variant in ('door', 'trapdoor', 'fence', 'log_fence', 'fence_gate', 'button', 'pressure_plate', 'slab', 'stairs'):
            rm.lang('block.tfcflorae.wood.planks.' + wood + '_' + variant, lang('%s %s', wood, variant))
        for variant in ('sapling', 'leaves'):
            rm.lang('block.tfcflorae.wood.' + variant + '.' + wood, lang('%s %s', wood, variant))

    rm.blockstate('light', variants={'level=%s' % i: {'model': 'minecraft:block/light_%s' % i if i >= 10 else 'minecraft:block/light_0%s' % i} for i in range(0, 15 + 1)}).with_lang(lang('Light'))
    rm.item_model('light', no_textures=True, parent='minecraft:item/light')


def item_model_property(rm: ResourceManager, name_parts: utils.ResourceIdentifier, overrides: utils.Json, data: Dict[str, Any]) -> ItemContext:
    res = utils.resource_location(rm.domain, name_parts)
    rm.write((*rm.resource_dir, 'assets', res.domain, 'models', 'item', res.path), {
        **data,
        'overrides': overrides
    })
    return ItemContext(rm, res)


def four_ways(model: str) -> List[Dict[str, Any]]:
    return [
        {'model': model, 'y': 90},
        {'model': model},
        {'model': model, 'y': 180},
        {'model': model, 'y': 270}
    ]


def four_rotations(model: str, rots: Tuple[Any, Any, Any, Any], suffix: str = '', prefix: str = '') -> Dict[str, Dict[str, Any]]:
    return {
        '%sfacing=east%s' % (prefix, suffix): {'model': model, 'y': rots[0]},
        '%sfacing=north%s' % (prefix, suffix): {'model': model, 'y': rots[1]},
        '%sfacing=south%s' % (prefix, suffix): {'model': model, 'y': rots[2]},
        '%sfacing=west%s' % (prefix, suffix): {'model': model, 'y': rots[3]}
    }

def match_tag(tag: str) -> Dict[str, Any]:
    return {
        'condition': 'minecraft:match_tool',
        'predicate': {'tag': tag}
    }


def fortune_table(chances: List[float]) -> Dict[str, Any]:
    return {
        'condition': 'minecraft:table_bonus',
        'enchantment': 'minecraft:fortune',
        'chances': chances
    }


def condition_chance(chance: float) -> Dict[str, Any]:
    return {
        'condition': 'minecraft:random_chance',
        'chance': chance
    }


def crop_yield(lo: int, hi: Tuple[int, int]) -> utils.Json:
    return {
        'function': 'minecraft:set_count',
        'count': {
            'type': 'tfcflorae:crop_yield_uniform',
            'min': lo,
            'max': {
                'type': 'minecraft:uniform',
                'min': hi[0],
                'max': hi[1]
            }
        }
    }
