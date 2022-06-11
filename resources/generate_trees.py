import os, csv
from typing import Set, Any, Tuple, NamedTuple, Literal, Union

from nbtlib import nbt
from nbtlib.tag import String as StringTag, Int as IntTag

Tree = NamedTuple('Tree', name=str, feature=Literal['random', 'overlay', 'stacked'], variant=str, count=Union[int, Tuple[int, ...]])

DATA_VERSION = 2970

NORMAL_TREES = [
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
    # Tree('mangrove', 'random', 'mangrove', 13),
    Tree('marblewood', 'random', 'marblewood', 24),
    Tree('messmate', 'random', 'messmate', 18),
    Tree('mountain_ash', 'random', 'mountain_ash', 10),
    Tree('rubber_fig', 'random', 'willow', 7),
    Tree('teak', 'random', 'teak', 13),
    Tree('red_cedar', 'random', 'red_cedar', 8),
    Tree('yew', 'random', 'yew', 19),
    Tree('juniper', 'random', 'juniper', 17),
    Tree('larch', 'random', 'larch', 10),
    Tree('buxus', 'overlay', 'normal', 3),
    Tree('holly', 'random', 'holly', 16),
    # Tree('bald_cypress', 'random', 'bald_cypress', 13),
    #Tree('cypress', 'random', 'cypress', 9),
    Tree('hemlock', 'random', 'hemlock', 9),
    #Tree('nordmann_fir', 'random', 'nordmann_fir', 9),
    #Tree('norway_spruce', 'random', 'norway_spruce', 7),
    Tree('redwood', 'random', 'redwood', 22),
]

LARGE_TREES = [
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
    #Tree('norway_spruce', 'stacked', 'conifer_large', (3, 3, 3)),
]


class Count:  # global mutable variables that doesn't require using the word "global" :)
    SKIPPED = 0
    NEW = 0
    MODIFIED = 0
    ERRORS = 0


def main():
    print('Verifying tree structures')

    print('Tree sapling drop chances:')
    analyze_tree_leaves(NORMAL_TREES)

    print('Making tree structures')
    for tree in NORMAL_TREES:
        make_tree_structures(tree, False)

    for tree in LARGE_TREES:
        make_tree_structures(tree, True)

    print('New = %d, Modified = %d, Unchanged = %d, Errors = %d' % (Count.NEW, Count.MODIFIED, Count.SKIPPED, Count.ERRORS))


def gen_drop_rate():
    analyze_tree_leaves(NORMAL_TREES)


def make_tree_structures(tree: Tree, large: bool):
    result = tree.name + '_large' if large else tree.name
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
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'minecraft:oak_wood':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            block['Properties']['natural'] = StringTag('true')
        elif block['Name'] == 'minecraft:oak_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
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

    result_dir = '../src/main/resources/data/tfcflorae/structures/%s/' % wood_dir
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


def analyze_tree_leaves(treeList):
    with open("Tree-sapling-drop.csv", "w", newline='') as file:
        csvFile = csv.writer(file)
        csvFile.writerow(["Tree_Name", "Sappling_Drop_Rate"])

        for tree in treeList:
            if tree.feature == 'random':
                leaves = count_leaves_in_random_tree(tree.variant, tree.count)
            elif tree.feature == 'overlay':
                leaves = count_leaves_in_overlay_tree(tree.variant)
            else:
                raise NotImplementedError
            
            csvFile.writerow([repr(tree.name), 2.5 / 0.1 + leaves])
            # Every tree results in 2.5 saplings, on average, if every leaf was broken
            print('%s: %.4f,' % (repr(tree.name), 2.5 / 0.1 + leaves))


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
