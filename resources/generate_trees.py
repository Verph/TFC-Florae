import os
from typing import Set, Any, Tuple, NamedTuple, Literal, Union

from nbtlib import nbt
from nbtlib.tag import String as StringTag, Int as IntTag

Tree = NamedTuple('Tree', name=str, feature=Literal['random', 'overlay', 'stacked'], variant=str, count=Union[int, Tuple[int, ...]])

DATA_VERSION = 2970

NORMAL_TREES = [
    Tree('acacia', 'random', 'acacia', 35),
    Tree('ash', 'overlay', 'normal', 0),
    Tree('aspen', 'random', 'aspen', 16),
    Tree('birch', 'random', 'aspen', 16),
    Tree('blackwood', 'random', 'blackwood', 10),
    Tree('chestnut', 'overlay', 'normal', 0),
    Tree('douglas_fir', 'random', 'fir', 9),
    Tree('hickory', 'random', 'fir', 9),
    Tree('kapok', 'random', 'jungle', 17),
    Tree('maple', 'overlay', 'normal', 0),
    Tree('oak', 'overlay', 'tall', 0),
    Tree('palm', 'random', 'tropical', 7),
    Tree('pine', 'random', 'fir', 9),
    Tree('rosewood', 'overlay', 'tall', 0),
    Tree('sequoia', 'random', 'conifer', 9),
    Tree('spruce', 'random', 'conifer', 9),
    Tree('sycamore', 'overlay', 'normal', 0),
    Tree('white_cedar', 'overlay', 'white_cedar', 0),
    Tree('willow', 'random', 'willow', 7),
    Tree('african_padauk', 'random', 'african_padauk', 9),
    Tree('angelim', 'random', 'angelim', 9),
    Tree('cocobolo', 'random', 'cocobolo', 9),
    Tree('greenheart', 'random', 'greenheart', 9),
    Tree('iroko', 'random', 'iroko', 9),
    Tree('kauri', 'random', 'kauri', 9),
    Tree('mahogany', 'random', 'mahogany', 9),
    Tree('purpleheart', 'random', 'purpleheart', 9),
    Tree('wenge', 'random', 'wenge', 9),
    Tree('yellow_meranti', 'random', 'yellow_meranti', 9),
    Tree('zebrawood', 'random', 'zebrawood', 9),
    Tree('baobab', 'random', 'baobab', 2),
    Tree('eucalyptus', 'random', 'eucalyptus', 12),
    Tree('hawthorn', 'random', 'hawthorn', 5),
    Tree('mulberry', 'random', 'mulberry', 5),
    Tree('maclura', 'random', 'maclura', 5),
    Tree('pink_ivory', 'random', 'pink_ivory', 19),
    Tree('rowan', 'random', 'rowan', 6),
    Tree('syzygium', 'random', 'syzygium', 14),
    Tree('jacaranda', 'random', 'jacaranda', 30),
    Tree('ipe', 'random', 'ipe', 66),
    Tree('cherry_blossom', 'random', 'cherry_blossom', 30),
    Tree('sweetgum', 'random', 'sweetgum', 20),
    Tree('alder', 'random', 'alder', 10),
    Tree('beech', 'random', 'beech', 11),
    Tree('black_walnut', 'random', 'black_walnut', 15),
    Tree('butternut', 'random', 'butternut', 15),
    Tree('common_oak', 'random', 'common_oak', 15),
    Tree('ginkgo', 'random', 'ginkgo', 20),
    Tree('hazel', 'random', 'hazel', 15),
    Tree('hornbeam', 'random', 'hornbeam', 13),
    Tree('locust', 'random', 'locust', 15),
    Tree('poplar', 'random', 'poplar', 17),
    Tree('red_elm', 'random', 'red_elm', 20),
    Tree('walnut', 'random', 'walnut', 15),
    Tree('white_elm', 'random', 'white_elm', 20),
    Tree('whitebeam', 'random', 'whitebeam', 14),
    Tree('brazilwood', 'random', 'brazilwood', 14),
    Tree('ebony', 'random', 'ebony', 20),
    Tree('fever', 'random', 'fever', 13),
    Tree('ironwood', 'random', 'ironwood', 16),
    Tree('limba', 'random', 'limba', 13),
    Tree('logwood', 'random', 'logwood', 10),
    Tree('mahoe', 'random', 'mahoe', 15),
    Tree('mangrove', 'random', 'mangrove', 13),
    Tree('marblewood', 'random', 'marblewood', 24),
    Tree('messmate', 'random', 'messmate', 18),
    Tree('mountain_ash', 'random', 'mountain_ash', 10),
    Tree('rubber_fig', 'random', 'willow', 7),
    Tree('teak', 'random', 'teak', 13),
    Tree('red_cedar', 'random', 'red_cedar', 8),
    Tree('yew', 'random', 'yew', 19),
    Tree('juniper', 'random', 'juniper', 17),
    Tree('larch', 'random', 'larch', 10),
    Tree('buxus', 'overlay', 'normal', 0),
    Tree('holly', 'random', 'holly', 16),
    Tree('bald_cypress', 'random', 'bald_cypress', 13),
    Tree('cypress', 'random', 'cypress', 5),
    Tree('hemlock', 'random', 'hemlock', 9),
    Tree('nordmann_fir', 'random', 'nordmann_fir', 26),
    Tree('norway_spruce', 'random', 'norway_spruce', 16),
    Tree('redwood', 'random', 'redwood', 22)
]

LARGE_TREES = [
    Tree('acacia', 'random', 'kapok_large', 6),
    Tree('ash', 'random', 'normal_large', 5),
    Tree('blackwood', 'random', 'blackwood_large', 10),
    Tree('chestnut', 'random', 'normal_large', 5),
    Tree('douglas_fir', 'random', 'fir_large', 5),
    Tree('hickory', 'random', 'fir_large', 5),
    Tree('maple', 'random', 'normal_large', 5),
    Tree('pine', 'random', 'fir_large', 5),
    Tree('sequoia', 'stacked', 'conifer_large', (3, 3, 3)),
    Tree('spruce', 'stacked', 'conifer_large', (3, 3, 3)),
    Tree('sycamore', 'random', 'normal_large', 5),
    Tree('white_cedar', 'overlay', 'tall', 0),
    Tree('willow', 'random', 'willow_large', 14),
    Tree('african_padauk', 'random', 'african_padauk_large', 12),
    Tree('angelim', 'random', 'angelim_large', 12),
    Tree('cocobolo', 'random', 'cocobolo_large', 12),
    Tree('greenheart', 'random', 'greenheart_large', 12),
    Tree('iroko', 'random', 'iroko_large', 12),
    Tree('kauri', 'random', 'kauri_large', 12),
    Tree('mahogany', 'random', 'mahogany_large', 12),
    Tree('purpleheart', 'random', 'purpleheart_large', 12),
    Tree('wenge', 'random', 'wenge_large', 12),
    Tree('yellow_meranti', 'random', 'yellow_meranti_large', 12),
    Tree('zebrawood', 'random', 'zebrawood_large', 12),
    Tree('eucalyptus', 'random', 'eucalyptus_large', 4),
    Tree('mountain_ash', 'random', 'mountain_ash_large', 10),
    Tree('rubber_fig', 'random', 'willow_large', 14),
    Tree('red_cedar', 'random', 'red_cedar_large', 17),
    Tree('buxus', 'random', 'normal_large', 5),
    #Tree('cypress', 'random', 'cypress_large', 5),
    Tree('hemlock', 'random', 'hemlock_large', 7),
    #Tree('nordmann_fir', 'random', 'nordmann_fir_large', 5),
    #Tree('norway_spruce', 'stacked', 'conifer_large', (3, 3, 3))
]

DEAD_TREES = [
    Tree('acacia', 'random', 'dead_small', 6),
    Tree('ash', 'random', 'dead_tall', 6),
    Tree('aspen', 'random', 'dead_tall', 6),
    Tree('birch', 'random', 'dead_tall', 6),
    Tree('blackwood', 'random', 'dead_small', 6),
    Tree('chestnut', 'random', 'dead_small', 6),
    Tree('douglas_fir', 'random', 'dead_tall', 6),
    Tree('hickory', 'random', 'dead_tall', 6),
    Tree('kapok', 'random', 'dead_jungle', 4),
    Tree('maple', 'random', 'dead_small', 6),
    Tree('oak', 'random', 'dead_small', 6),
    Tree('palm', 'random', 'dead_stump', 3),
    Tree('pine', 'random', 'dead_tall', 6),
    Tree('rosewood', 'random', 'dead_tall', 6),
    Tree('sequoia', 'random', 'dead_tall', 6),
    Tree('spruce', 'random', 'dead_tall', 6),
    Tree('sycamore', 'random', 'dead_small', 6),
    Tree('white_cedar', 'random', 'dead_tall', 6),
    Tree('willow', 'random', 'dead_stump', 3),
    Tree('african_padauk', 'random', 'dead_jungle', 4),
    Tree('angelim', 'random', 'dead_jungle', 4),
    Tree('cocobolo', 'random', 'dead_jungle', 4),
    Tree('greenheart', 'random', 'dead_jungle', 4),
    Tree('iroko', 'random', 'dead_jungle', 4),
    Tree('kauri', 'random', 'dead_jungle', 4),
    Tree('mahogany', 'random', 'dead_jungle', 4),
    Tree('purpleheart', 'random', 'dead_jungle', 4),
    Tree('wenge', 'random', 'dead_jungle', 4),
    Tree('yellow_meranti', 'random', 'dead_jungle', 4),
    Tree('zebrawood', 'random', 'dead_jungle', 4),
    Tree('baobab', 'random', 'dead_stump', 3),
    Tree('eucalyptus', 'random', 'dead_tall', 6),
    Tree('hawthorn', 'random', 'dead_small', 6),
    Tree('mulberry', 'random', 'dead_small', 6),
    Tree('maclura', 'random', 'dead_small', 6),
    Tree('pink_ivory', 'random', 'dead_tall', 6),
    Tree('rowan', 'random', 'dead_small', 6),
    Tree('syzygium', 'random', 'dead_stump', 3),
    Tree('jacaranda', 'random', 'dead_small', 6),
    Tree('ipe', 'random', 'dead_tall', 6),
    Tree('cherry_blossom', 'random', 'dead_small', 6),
    Tree('sweetgum', 'random', 'dead_tall', 6),
    Tree('alder', 'random', 'dead_tall', 6),
    Tree('beech', 'random', 'dead_tall', 6),
    Tree('black_walnut', 'random', 'dead_tall', 6),
    Tree('butternut', 'random', 'dead_tall', 6),
    Tree('common_oak', 'random', 'dead_tall', 6),
    Tree('ginkgo', 'random', 'dead_tall', 6),
    Tree('hazel', 'random', 'dead_small', 6),
    Tree('hornbeam', 'random', 'dead_tall', 6),
    Tree('locust', 'random', 'dead_small', 6),
    Tree('poplar', 'random', 'dead_tall', 6),
    Tree('red_elm', 'random', 'dead_tall', 6),
    Tree('walnut', 'random', 'dead_tall', 6),
    Tree('white_elm', 'random', 'dead_tall', 6),
    Tree('whitebeam', 'random', 'dead_tall', 6),
    Tree('brazilwood', 'random', 'dead_tall', 6),
    Tree('ebony', 'random', 'dead_tall', 6),
    Tree('fever', 'random', 'dead_small', 6),
    Tree('ironwood', 'random', 'dead_tall', 6),
    Tree('limba', 'random', 'dead_small', 6),
    Tree('logwood', 'random', 'dead_small', 6),
    Tree('mahoe', 'random', 'dead_tall', 6),
    Tree('mangrove', 'random', 'dead_tall', 6),
    Tree('marblewood', 'random', 'dead_tall', 6),
    Tree('messmate', 'random', 'dead_small', 6),
    Tree('mountain_ash', 'random', 'dead_tall', 6),
    Tree('rubber_fig', 'random', 'dead_stump', 3),
    Tree('teak', 'random', 'dead_tall', 6),
    Tree('red_cedar', 'random', 'dead_tall', 6),
    Tree('yew', 'random', 'dead_tall', 6),
    Tree('juniper', 'random', 'dead_tall', 6),
    Tree('larch', 'random', 'dead_tall', 6),
    Tree('buxus', 'random', 'dead_small', 6),
    Tree('holly', 'random', 'dead_small', 6),
    Tree('bald_cypress', 'random', 'dead_tall', 6),
    Tree('cypress', 'random', 'dead_tall', 6),
    Tree('hemlock', 'random', 'dead_small', 6),
    Tree('nordmann_fir', 'random', 'dead_tall', 6),
    Tree('norway_spruce', 'random', 'dead_tall', 6),
    Tree('redwood', 'random', 'dead_tall', 6)
]


class Count:  # global mutable variables that doesn't require using the word "global" :)
    SKIPPED = 0
    NEW = 0
    MODIFIED = 0
    ERRORS = 0


def main():
    print('Verifying tree structures')
    verify_center_trunk('acacia', 35)
    verify_center_trunk('aspen', 16)
    verify_center_trunk('blackwood', 10)
    verify_center_trunk('conifer', 9)
    verify_center_trunk('fir', 9)
    verify_center_trunk('jungle', 17)
    verify_center_trunk('tropical', 7)
    verify_center_trunk('willow', 7)
    verify_center_trunk('dead_jungle', 4)
    verify_center_trunk('dead_stump', 3)
    verify_center_trunk('dead_small', 6)
    verify_center_trunk('dead_tall', 6)
    verify_center_trunk('african_padauk', 9)
    verify_center_trunk('angelim', 9)
    verify_center_trunk('cocobolo', 9)
    verify_center_trunk('greenheart', 9)
    verify_center_trunk('iroko', 9)
    verify_center_trunk('kauri', 9)
    verify_center_trunk('mahogany', 9)
    verify_center_trunk('purpleheart', 9)
    verify_center_trunk('wenge', 9)
    verify_center_trunk('yellow_meranti', 9)
    verify_center_trunk('zebrawood', 9)
    verify_center_trunk('baobab', 2)
    verify_center_trunk('eucalyptus', 12)
    verify_center_trunk('hawthorn', 5)
    verify_center_trunk('mulberry', 5)
    verify_center_trunk('maclura', 5)
    verify_center_trunk('pink_ivory', 19)
    verify_center_trunk('rowan', 6)
    verify_center_trunk('syzygium', 14)
    verify_center_trunk('jacaranda', 30)
    verify_center_trunk('ipe', 66)
    verify_center_trunk('cherry_blossom', 30)
    verify_center_trunk('sweetgum', 20)
    verify_center_trunk('alder', 10)
    verify_center_trunk('beech', 11)
    verify_center_trunk('black_walnut', 15)
    verify_center_trunk('butternut', 15)
    verify_center_trunk('common_oak', 15)
    verify_center_trunk('ginkgo', 20)
    verify_center_trunk('hazel', 15)
    verify_center_trunk('hornbeam', 13)
    verify_center_trunk('locust', 15)
    verify_center_trunk('poplar', 17)
    verify_center_trunk('red_elm', 20)
    verify_center_trunk('walnut', 15)
    verify_center_trunk('white_elm', 20)
    verify_center_trunk('whitebeam', 14)
    verify_center_trunk('brazilwood', 14)
    verify_center_trunk('ebony', 20)
    verify_center_trunk('fever', 13)
    verify_center_trunk('ironwood', 16)
    verify_center_trunk('limba', 13)
    verify_center_trunk('logwood', 10)
    verify_center_trunk('mahoe', 15)
    verify_center_trunk('mangrove', 13)
    verify_center_trunk('marblewood', 24)
    verify_center_trunk('messmate', 18)
    verify_center_trunk('mountain_ash', 10)
    verify_center_trunk('rubber_fig', 7)
    verify_center_trunk('teak', 13)
    verify_center_trunk('red_cedar', 8)
    verify_center_trunk('yew', 19)
    verify_center_trunk('juniper', 17)
    verify_center_trunk('larch', 10)
    verify_center_trunk('holly', 16)
    verify_center_trunk('bald_cypress', 13)
    verify_center_trunk('cypress', 5)
    verify_center_trunk('hemlock', 9)
    verify_center_trunk('nordmann_fir', 26)
    verify_center_trunk('norway_spruce', 16)
    verify_center_trunk('redwood', 22)

    print('Tree sapling drop chances:')
    for tree in NORMAL_TREES:
        analyze_tree_leaves(tree)

    print('Making tree structures')
    for tree in NORMAL_TREES:
        make_tree_structures(tree)

    for tree in LARGE_TREES:
        make_tree_structures(tree, '_large')

    for tree in DEAD_TREES:
        make_tree_structures(tree, '_dead')

    print('New = %d, Modified = %d, Unchanged = %d, Errors = %d' % (Count.NEW, Count.MODIFIED, Count.SKIPPED, Count.ERRORS))


def make_tree_structures(tree: Tree, suffix: str = ''):
    result = tree.name + suffix
    if tree.feature == 'random':
        for i in range(1, 1 + tree.count):
            make_tree_structure(tree.variant + str(i), tree.name, str(i), result)
    elif tree.feature == 'overlay':
        make_tree_structure(tree.variant, tree.name, 'base', result)
        make_tree_structure(tree.variant + '_overlay', tree.name, 'overlay', result)
    elif tree.feature == 'stacked':
        for j, c in zip(range(1, 1 + len(tree.count)), tree.count):
            for i in range(1, 1 + c):
                make_tree_structure('%s_layer%d_%d' % (tree.variant, j, i), tree.name, 'layer%d_%d' % (j, i), result)


def make_tree_structure(template: str, wood: str, dest: str, wood_dir: str):
    f = nbt.load('./structure_templates/%s.nbt' % template)
    for block in f['palette']:
        if block['Name'] == 'minecraft:oak_log':
            block['Name'] = StringTag('tfc:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'minecraft:oak_wood':
            block['Name'] = StringTag('tfc:wood/wood/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'minecraft:oak_leaves':
            block['Name'] = StringTag('tfc:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/log/african_padauk':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/alder':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/angelim':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/bald_cypress':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/baobab':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/beech':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/black_walnut':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/box':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/brazilwood':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/butternut':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/cocobolo':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/cypress':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/ebony':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/eucalyptus':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/european_oak':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/fever':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/fruitwood':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/ginkgo':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/greenheart':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/hawthorn':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/hazel':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/hemlock':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/holly':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/hornbeam':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/ipe':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/iroko':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/ironwood':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/jacaranda':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/joshua_tree':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/juniper':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/kauri':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/larch':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/limba':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/locust':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/logwood':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/maclura':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/mahoe':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/mahogany':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/mangrove':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/marblewood':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/messmate':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/mountain_ash':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/mulberry':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/nordmann_fir':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/norway_spruce':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/pink_cherry':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/pink_ivory':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/poplar':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/purpleheart':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/red_cedar':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/red_elm':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/redwood':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/rowan':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/rubber_fig':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/sweetgum':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/syzygium':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/teak':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/walnut':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/wenge':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/white_cherry':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/white_elm':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/whitebeam':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/yellow_meranti':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/yew':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/log/zebrawood':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'tfc:wood/leaves/african_padauk':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/alder':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/angelim':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/bald_cypress':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/baobab':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/beech':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/black_walnut':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/box':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/brazilwood':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/butternut':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/cocobolo':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/cypress':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/ebony':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/eucalyptus':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/european_oak':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/fever':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/fruitwood':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/ginkgo':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/greenheart':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/hawthorn':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/hazel':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/hemlock':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/holly':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/hornbeam':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/ipe':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/iroko':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/ironwood':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/jacaranda':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/joshua_tree':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/juniper':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/kauri':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/larch':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/limba':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/locust':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/logwood':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/maclura':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/mahoe':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/mahogany':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/mangrove':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/marblewood':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/messmate':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/mountain_ash':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/mulberry':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/nordmann_fir':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/norway_spruce':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/pink_cherry':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/pink_ivory':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/poplar':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/purpleheart':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/red_cedar':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/red_elm':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/redwood':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/rowan':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/rubber_fig':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/sweetgum':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/syzygium':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/teak':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/walnut':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/wenge':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/white_cherry':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/white_elm':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/whitebeam':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/yellow_meranti':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/yew':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        elif block['Name'] == 'tfc:wood/leaves/zebrawood':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            block['Properties']['persistent'] = StringTag('false')
        else:
            print('Structure: %s has an invalid block state \'%s\'' % (template, block['Name']))

    # Hack the data version, to avoid needing to run DFU on anything
    f['DataVersion'] = IntTag(DATA_VERSION)

    result_dir = '../src/main/resources/data/tfc/structures/%s/' % wood_dir
    os.makedirs(result_dir, exist_ok=True)

    file_name = result_dir + dest + '.nbt'
    try:
        if os.path.isfile(file_name):
            # Load and diff the original file - do not overwrite if source identical to avoid unnecessary git diffs due to gzip inconsistencies.
            original = nbt.load(file_name)
            if original == f:
                Count.SKIPPED += 1
                return
            else:
                Count.MODIFIED += 1
        else:
            Count.NEW += 1
        f.save(result_dir + dest + '.nbt')
    except:
        Count.ERRORS += 1


def analyze_tree_leaves(tree: Tree):
    #with open("Tree-sapling-drop.csv", "w", newline='') as file:
        #csvFile = csv.writer(file)
        #csvFile.writerow(["Tree_Name", "Sappling_Drop_Rate"])

        #for tree in Tree:
            if tree.feature == 'random':
                leaves = count_leaves_in_random_tree(tree.variant, tree.count)
            elif tree.feature == 'overlay':
                leaves = count_leaves_in_overlay_tree(tree.variant)
            else:
                raise NotImplementedError

            #csvFile.writerow([repr(tree.name), 2.5 / leaves])
            # Every tree results in 2.5 saplings, on average, if every leaf was broken
            print('%s: %.4f,' % (repr(tree.name), 2.5 / (leaves + 0.1)))


def count_leaves_in_random_tree(base_name: str, count: int) -> float:
    counts = [count_leaves_in_structure(base_name + str(i)) for i in range(1, 1 + count)]
    return sum(counts) / len(counts)


def count_leaves_in_overlay_tree(base_name: str) -> float:
    base = nbt.load('./structure_templates/%s.nbt' % base_name)
    overlay = nbt.load('./structure_templates/%s_overlay.nbt' % base_name)

    base_leaves = leaf_ids(base)
    leaves = set(pos_key(block) for block in base['blocks'] if block['state'] in base_leaves)
    count = len(leaves)

    for block in overlay['blocks']:
        if block['state'] in base_leaves and pos_key(block) not in leaves:
            count += 0.5
        elif pos_key(block) in leaves:
            count -= 0.5

    return count


def count_leaves_in_structure(file_name: str):
    file = nbt.load('./structure_templates/%s.nbt' % file_name)
    leaves = leaf_ids(file)
    return sum(block['state'] in leaves for block in file['blocks'])


def leaf_ids(file: nbt.File) -> Set[int]:
    return {i for i, block in enumerate(file['palette']) if block['Name'] == 'minecraft:oak_leaves'}


def pos_key(tag: Any, key: str = 'pos') -> Tuple[int, int, int]:
    pos = tag[key]
    return int(pos[0]), int(pos[1]), int(pos[2])


def verify_center_trunk(prefix: str, count: int):
    for i in range(1, 1 + count):
        root = nbt.load('./structure_templates/%s%d.nbt' % (prefix, i))
        sx, sy, sz = pos_key(root, 'size')
        if sx % 2 != 1 or sz % 2 != 1:
            print('Non-odd dimensions: %d x %d x %d on %s%d' % (sx, sy, sz, prefix, i))
            continue

        center = sx // 2, 0, sz // 2
        center_state = None
        for block in root['blocks']:
            if pos_key(block) == center:
                center_state = int(block['state'])
                break

        if center_state is None:
            print('Cannot find center trunk state on %s%d' % (prefix, i))
            continue

        state = str(root['palette'][center_state]['Name'])
        if state not in ('minecraft:oak_wood', 'minecraft:oak_log'):
            print('Illegal center state, expected log, got: %s, on %s%d' % (state,prefix, i))


if __name__ == '__main__':
    main()
