import os
from typing import Set, Any, Tuple, NamedTuple, Literal, Union

from nbtlib import nbt
from nbtlib.tag import *
from nbtlib.tag import String as StringTag, Int as IntTag

Tree = NamedTuple('Tree', name=str, feature=Literal['random', 'overlay', 'stacked'], variant=str, count=Union[int, Tuple[int, ...]])

DATA_VERSION = 2970

NORMAL_TREES = [
    #Tree('acacia', 'random', 'acacia', 35),
    #Tree('ash', 'overlay', 'normal', 0),
    #Tree('aspen', 'random', 'aspen', 16),
    #Tree('birch', 'random', 'aspen', 16),
    #Tree('blackwood', 'random', 'blackwood', 10),
    #Tree('chestnut', 'overlay', 'normal', 0),
    #Tree('douglas_fir', 'random', 'fir', 9),
    #Tree('hickory', 'random', 'fir', 9),
    #Tree('kapok', 'random', 'jungle', 17),
    #Tree('maple', 'overlay', 'normal', 0),
    #Tree('oak', 'overlay', 'tall', 0),
    #Tree('palm', 'random', 'tropical', 7),
    #Tree('pine', 'random', 'fir', 9),
    #Tree('rosewood', 'overlay', 'tall', 0),
    #Tree('sequoia', 'random', 'conifer', 9),
    #Tree('spruce', 'random', 'conifer', 9),
    #Tree('sycamore', 'overlay', 'normal', 0),
    #Tree('white_cedar', 'overlay', 'white_cedar', 0),
    #Tree('willow', 'random', 'willow', 7),
    #Tree('baobab', 'random', 'baobab', 2),
    #Tree('bald_cypress', 'random', 'bald_cypress', 13),
    #Tree('mangrove', 'random', 'mangrove', 13),
    #Tree('african_padauk', 'random', 'african_padauk', 11),
    #Tree('alder', 'random', 'alder', 13),
    #Tree('angelim', 'random', 'angelim', 11),
    #Tree('beech', 'random', 'beech', 13),
    #Tree('black_walnut', 'random', 'black_walnut', 15),
    #Tree('brazilwood', 'random', 'brazilwood', 9),
    #Tree('butternut', 'random', 'butternut', 13),
    #Tree('buxus', 'overlay', 'normal', 0),
    #Tree('cocobolo', 'random', 'cocobolo', 11),
    #Tree('common_oak', 'random', 'common_oak', 19),
    #Tree('cypress', 'random', 'cypress', 17),
    #Tree('ebony', 'random', 'ebony', 20),
    #Tree('fever', 'random', 'fever', 11),
    #Tree('ginkgo', 'random', 'ginkgo', 17),
    #Tree('greenheart', 'random', 'greenheart', 11),
    #Tree('hazel', 'random', 'hazel', 19),
    #Tree('hemlock', 'random', 'hemlock', 15),
    #Tree('hornbeam', 'random', 'hornbeam', 19),
    #Tree('iroko', 'random', 'iroko', 11),
    #Tree('ironwood', 'random', 'ironwood', 13),
    #Tree('kauri', 'random', 'kauri', 11),
    #Tree('larch', 'random', 'larch', 18),
    #Tree('limba', 'random', 'limba', 9),
    #Tree('locust', 'random', 'locust', 19),
    #Tree('logwood', 'random', 'logwood', 31),
    #Tree('marblewood', 'random', 'marblewood', 31),
    #Tree('messmate', 'random', 'messmate', 31),
    #Tree('mountain_ash', 'random', 'mountain_ash', 31),
    #Tree('nordmann_fir', 'random', 'nordmann_fir', 9),
    #Tree('norway_spruce', 'random', 'norway_spruce', 9),
    #Tree('poplar', 'random', 'poplar', 17),
    #Tree('purpleheart', 'random', 'purpleheart', 11),
    #Tree('red_elm', 'random', 'red_elm', 19),
    #Tree('redwood', 'random', 'redwood', 24),
    #Tree('rubber_fig', 'random', 'rubber_fig', 25),
    #Tree('sweetgum', 'random', 'sweetgum', 19),
    #Tree('teak', 'random', 'teak', 20),
    #Tree('walnut', 'random', 'walnut', 15),
    #Tree('wenge', 'random', 'wenge', 11),
    #Tree('white_elm', 'random', 'white_elm', 19),
    #Tree('yellow_meranti', 'random', 'yellow_meranti', 11),
    #Tree('zebrawood', 'random', 'zebrawood', 11),
    Tree('argyle_eucalyptus', 'random', 'argyle_eucalyptus', 20),
    Tree('hawthorn', 'random', 'hawthorn', 19),
    Tree('holly', 'random', 'holly', 17),
    Tree('juniper', 'random', 'juniper', 21),
    Tree('maclura', 'random', 'maclura', 31),
    Tree('mahoe', 'random', 'mahoe', 31),
    Tree('mahogany', 'random', 'mahogany', 11),
    Tree('mulberry', 'random', 'mulberry', 19),
    Tree('pink_cherry_blossom', 'random', 'pink_cherry_blossom', 14),
    Tree('pink_ipe', 'random', 'pink_ipe', 9),
    Tree('pink_ivory', 'random', 'pink_ivory', 31),
    Tree('purple_ipe', 'random', 'purple_ipe', 9),
    Tree('purple_jacaranda', 'random', 'purple_jacaranda', 14),
    Tree('rainbow_eucalyptus', 'random', 'rainbow_eucalyptus', 20),
    Tree('red_cedar', 'random', 'red_cedar', 21),
    Tree('rowan', 'random', 'rowan', 19),
    Tree('snow_gum_eucalyptus', 'random', 'snow_gum_eucalyptus', 20),
    Tree('syzygium', 'random', 'syzygium', 31),
    Tree('white_cherry_blossom', 'random', 'white_cherry_blossom', 14),
    Tree('white_ipe', 'random', 'white_ipe', 9),
    Tree('white_jacaranda', 'random', 'white_jacaranda', 14),
    Tree('whitebeam', 'random', 'whitebeam', 11),
    Tree('yellow_ipe', 'random', 'yellow_ipe', 9),
    Tree('yellow_jacaranda', 'random', 'yellow_jacaranda', 14),
    Tree('yew', 'random', 'yew', 35)
]

LARGE_TREES = [
    #Tree('acacia', 'random', 'kapok_large', 6),
    #Tree('ash', 'random', 'normal_large', 5),
    #Tree('blackwood', 'random', 'blackwood_large', 10),
    #Tree('chestnut', 'random', 'normal_large', 5),
    #Tree('douglas_fir', 'random', 'fir_large', 5),
    #Tree('hickory', 'random', 'fir_large', 5),
    #Tree('maple', 'random', 'normal_large', 5),
    #Tree('pine', 'random', 'fir_large', 5),
    #Tree('sequoia', 'stacked', 'conifer_large', (3, 3, 3)),
    #Tree('spruce', 'stacked', 'conifer_large', (3, 3, 3)),
    #Tree('sycamore', 'random', 'normal_large', 5),
    #Tree('white_cedar', 'overlay', 'tall', 0),
    #Tree('willow', 'random', 'willow_large', 14),
    #Tree('african_padauk', 'random', 'african_padauk_large', 13),
    #Tree('alder', 'random', 'alder_large', 4),
    #Tree('angelim', 'random', 'angelim_large', 13),
    #Tree('beech', 'random', 'beech_large', 4),
    #Tree('black_walnut', 'random', 'black_walnut_large', 3),
    #Tree('brazilwood', 'random', 'brazilwood_large', 4),
    #Tree('butternut', 'random', 'butternut_large', 4),
    #Tree('buxus', 'random', 'normal_large', 5),
    #Tree('cocobolo', 'random', 'cocobolo_large', 13),
    #Tree('common_oak', 'random', 'common_oak_large', 7),
    #Tree('cypress', 'random', 'cypress_large', 2),
    #Tree('ebony', 'random', 'ebony_large', 4),
    #Tree('fever', 'random', 'fever_large', 3),
    #Tree('ginkgo', 'random', 'ginkgo_large', 3),
    #Tree('greenheart', 'random', 'greenheart_large', 13),
    #Tree('hazel', 'random', 'hazel_large', 7),
    #Tree('hemlock', 'random', 'hemlock_large', 4),
    #Tree('hornbeam', 'random', 'hornbeam_large', 7),
    #Tree('iroko', 'random', 'iroko_large', 13),
    #Tree('ironwood', 'random', 'ironwood_large', 4),
    #Tree('kauri', 'random', 'kauri_large', 13),
    #Tree('larch', 'random', 'larch_large', 7),
    #Tree('limba', 'random', 'limba_large', 4),
    #Tree('locust', 'random', 'locust_large', 7),
    #Tree('logwood', 'random', 'logwood_large', 7),
    #Tree('marblewood', 'random', 'marblewood_large', 4),
    #Tree('messmate', 'random', 'messmate_large', 4),
    #Tree('mountain_ash', 'random', 'mountain_ash_large', 14),
    #Tree('nordmann_fir', 'random', 'nordmann_fir_large', 4),
    #Tree('norway_spruce', 'random', 'norway_spruce_large', 4),
    #Tree('poplar', 'random', 'poplar_large', 4),
    #Tree('purpleheart', 'random', 'purpleheart_large', 13),
    #Tree('red_elm', 'random', 'red_elm_large', 7),
    #Tree('redwood', 'random', 'redwood_large', 8),
    #Tree('rubber_fig', 'random', 'rubber_fig_large', 5),
    #Tree('sweetgum', 'random', 'sweetgum_large', 7),
    #Tree('teak', 'random', 'teak_large', 4),
    #Tree('walnut', 'random', 'walnut_large', 3),
    #Tree('wenge', 'random', 'wenge_large', 13),
    #Tree('white_elm', 'random', 'white_elm_large', 7),
    #Tree('yellow_meranti', 'random', 'yellow_meranti_large', 13),
    #Tree('zebrawood', 'random', 'zebrawood_large', 13),
    Tree('argyle_eucalyptus', 'random', 'argyle_eucalyptus_large', 4),
    Tree('hawthorn', 'random', 'hawthorn_large', 7),
    Tree('holly', 'random', 'holly_large', 3),
    Tree('juniper', 'random', 'juniper_large', 6),
    Tree('maclura', 'random', 'maclura_large', 7),
    Tree('mahoe', 'random', 'mahoe_large', 7),
    Tree('mahogany', 'random', 'mahogany_large', 13),
    Tree('mulberry', 'random', 'mulberry_large', 7),
    Tree('pink_cherry_blossom', 'random', 'pink_cherry_blossom_large', 3),
    Tree('pink_ipe', 'random', 'pink_ipe_large', 4),
    Tree('pink_ivory', 'random', 'pink_ivory_large', 7),
    Tree('purple_ipe', 'random', 'purple_ipe_large', 4),
    Tree('purple_jacaranda', 'random', 'purple_jacaranda_large', 3),
    Tree('rainbow_eucalyptus', 'random', 'rainbow_eucalyptus_large', 4),
    Tree('red_cedar', 'random', 'red_cedar_large', 6),
    Tree('rowan', 'random', 'rowan_large', 7),
    Tree('snow_gum_eucalyptus', 'random', 'snow_gum_eucalyptus_large', 4),
    Tree('syzygium', 'random', 'syzygium_large', 7),
    Tree('white_cherry_blossom', 'random', 'white_cherry_blossom_large', 3),
    Tree('white_ipe', 'random', 'white_ipe_large', 4),
    Tree('white_jacaranda', 'random', 'white_jacaranda_large', 3),
    Tree('whitebeam', 'random', 'whitebeam_large', 7),
    Tree('yellow_ipe', 'random', 'yellow_ipe_large', 4),
    Tree('yellow_jacaranda', 'random', 'yellow_jacaranda_large', 3),
    Tree('yew', 'random', 'yew_large', 5)
]

DEAD_TREES = [
    ##Tree('acacia', 'random', 'dead_small', 6),
    ##Tree('ash', 'random', 'dead_tall', 6),
    ##Tree('aspen', 'random', 'dead_tall', 6),
    ##Tree('birch', 'random', 'dead_tall', 6),
    ##Tree('blackwood', 'random', 'dead_small', 6),
    ##Tree('chestnut', 'random', 'dead_small', 6),
    ##Tree('douglas_fir', 'random', 'dead_tall', 6),
    ##Tree('hickory', 'random', 'dead_tall', 6),
    ##Tree('kapok', 'random', 'dead_jungle', 4),
    ##Tree('maple', 'random', 'dead_small', 6),
    ##Tree('oak', 'random', 'dead_small', 6),
    ##Tree('palm', 'random', 'dead_stump', 3),
    ##Tree('pine', 'random', 'dead_tall', 6),
    ##Tree('rosewood', 'random', 'dead_tall', 6),
    ##Tree('sequoia', 'random', 'dead_tall', 6),
    ##Tree('spruce', 'random', 'dead_tall', 6),
    ##Tree('sycamore', 'random', 'dead_small', 6),
    ##Tree('white_cedar', 'random', 'dead_tall', 6),
    ##Tree('willow', 'random', 'dead_stump', 3),
    ##Tree('bald_cypress', 'random', 'bald_cypress', 13),
    ##Tree('baobab', 'random', 'baobab', 2),
    ##Tree('mangrove', 'random', 'mangrove', 13),
    #Tree('african_padauk', 'random', 'african_padauk', 11),
    #Tree('alder', 'random', 'alder', 13),
    #Tree('angelim', 'random', 'angelim', 11),
    #Tree('beech', 'random', 'beech', 13),
    #Tree('black_walnut', 'random', 'black_walnut', 15),
    #Tree('brazilwood', 'random', 'brazilwood', 9),
    #Tree('butternut', 'random', 'butternut', 13),
    #Tree('buxus', 'overlay', 'normal', 0),
    #Tree('cocobolo', 'random', 'cocobolo', 11),
    #Tree('common_oak', 'random', 'common_oak', 19),
    #Tree('cypress', 'random', 'cypress', 17),
    #Tree('ebony', 'random', 'ebony', 20),
    #Tree('fever', 'random', 'fever', 11),
    #Tree('ginkgo', 'random', 'ginkgo', 17),
    #Tree('greenheart', 'random', 'greenheart', 11),
    #Tree('hazel', 'random', 'hazel', 19),
    #Tree('hemlock', 'random', 'hemlock', 15),
    #Tree('hornbeam', 'random', 'hornbeam', 19),
    #Tree('iroko', 'random', 'iroko', 11),
    #Tree('ironwood', 'random', 'ironwood', 13),
    #Tree('kauri', 'random', 'kauri', 11),
    #Tree('larch', 'random', 'larch', 18),
    #Tree('limba', 'random', 'limba', 9),
    #Tree('locust', 'random', 'locust', 19),
    #Tree('logwood', 'random', 'logwood', 31),
    #Tree('marblewood', 'random', 'marblewood', 31),
    #Tree('messmate', 'random', 'messmate', 31),
    #Tree('mountain_ash', 'random', 'mountain_ash', 31),
    #Tree('nordmann_fir', 'random', 'nordmann_fir', 9),
    #Tree('norway_spruce', 'random', 'norway_spruce', 9),
    #Tree('poplar', 'random', 'poplar', 17),
    #Tree('purpleheart', 'random', 'purpleheart', 11),
    #Tree('red_elm', 'random', 'red_elm', 19),
    #Tree('redwood', 'random', 'redwood', 24),
    #Tree('rubber_fig', 'random', 'rubber_fig', 25),
    #Tree('sweetgum', 'random', 'sweetgum', 19),
    #Tree('teak', 'random', 'teak', 20),
    #Tree('walnut', 'random', 'walnut', 15),
    #Tree('wenge', 'random', 'wenge', 11),
    #Tree('white_elm', 'random', 'white_elm', 19),
    #Tree('yellow_meranti', 'random', 'yellow_meranti', 11),
    #Tree('zebrawood', 'random', 'zebrawood', 11),
    Tree('argyle_eucalyptus', 'random', 'argyle_eucalyptus', 20),
    Tree('hawthorn', 'random', 'hawthorn', 19),
    Tree('holly', 'random', 'holly', 17),
    Tree('juniper', 'random', 'juniper', 21),
    Tree('maclura', 'random', 'maclura', 31),
    Tree('mahoe', 'random', 'mahoe', 31),
    Tree('mahogany', 'random', 'mahogany', 11),
    Tree('mulberry', 'random', 'mulberry', 19),
    Tree('pink_cherry_blossom', 'random', 'pink_cherry_blossom', 14),
    Tree('pink_ipe', 'random', 'pink_ipe', 9),
    Tree('pink_ivory', 'random', 'pink_ivory', 31),
    Tree('purple_ipe', 'random', 'purple_ipe', 9),
    Tree('purple_jacaranda', 'random', 'purple_jacaranda', 14),
    Tree('rainbow_eucalyptus', 'random', 'rainbow_eucalyptus', 20),
    Tree('red_cedar', 'random', 'red_cedar', 21),
    Tree('rowan', 'random', 'rowan', 19),
    Tree('snow_gum_eucalyptus', 'random', 'snow_gum_eucalyptus', 20),
    Tree('syzygium', 'random', 'syzygium', 31),
    Tree('white_cherry_blossom', 'random', 'white_cherry_blossom', 14),
    Tree('white_ipe', 'random', 'white_ipe', 9),
    Tree('white_jacaranda', 'random', 'white_jacaranda', 14),
    Tree('whitebeam', 'random', 'whitebeam', 19),
    Tree('yellow_ipe', 'random', 'yellow_ipe', 9),
    Tree('yellow_jacaranda', 'random', 'yellow_jacaranda', 14),
    Tree('yew', 'random', 'yew', 35)
]


class Count:  # global mutable variables that doesn't require using the word "global" :)
    SKIPPED = 0
    NEW = 0
    MODIFIED = 0
    ERRORS = 0


def main():
    print('Verifying tree structures')
    #verify_center_trunk('acacia', 35)
    #verify_center_trunk('aspen', 16)
    #verify_center_trunk('blackwood', 10)
    #verify_center_trunk('conifer', 9)
    #verify_center_trunk('fir', 9)
    #verify_center_trunk('jungle', 17)
    #verify_center_trunk('tropical', 7)
    #verify_center_trunk('willow', 7)
    #verify_center_trunk('dead_jungle', 4)
    #verify_center_trunk('dead_stump', 3)
    #verify_center_trunk('dead_small', 6)
    #verify_center_trunk('dead_tall', 6)
    #verify_center_trunk('african_padauk', 11),
    #verify_center_trunk('alder', 13),
    #verify_center_trunk('angelim', 11),
    #verify_center_trunk('beech', 13),
    #verify_center_trunk('black_walnut', 15),
    #verify_center_trunk('brazilwood', 9),
    #verify_center_trunk('butternut', 13),
    #verify_center_trunk('cocobolo', 11),
    #verify_center_trunk('common_oak', 19),
    #verify_center_trunk('cypress', 17),
    #verify_center_trunk('ebony', 20),
    #verify_center_trunk('fever', 11),
    #verify_center_trunk('ginkgo', 17),
    #verify_center_trunk('greenheart', 11),
    #verify_center_trunk('hazel', 19),
    #verify_center_trunk('hemlock', 15),
    #verify_center_trunk('hornbeam', 19),
    #verify_center_trunk('iroko', 11),
    #verify_center_trunk('ironwood', 13),
    #verify_center_trunk('kauri', 11),
    #verify_center_trunk('larch', 18),
    #verify_center_trunk('limba', 9),
    #verify_center_trunk('locust', 19),
    #verify_center_trunk('logwood',  31),
    #verify_center_trunk('marblewood', 31),
    #verify_center_trunk('messmate', 31),
    #verify_center_trunk('mountain_ash', 31),
    #verify_center_trunk('nordmann_fir', 9),
    #verify_center_trunk('norway_spruce', 9),
    #verify_center_trunk('poplar', 17),
    #verify_center_trunk('purpleheart', 11),
    #verify_center_trunk('red_elm', 19),
    #verify_center_trunk('redwood', 24),
    #verify_center_trunk('rubber_fig', 25),
    #verify_center_trunk('sweetgum', 19),
    #verify_center_trunk('teak', 20),
    #verify_center_trunk('walnut', 15),
    #verify_center_trunk('wenge', 11),
    #verify_center_trunk('white_elm', 19),
    #verify_center_trunk('yellow_meranti', 11),
    #verify_center_trunk('zebrawood', 11),
    verify_center_trunk('argyle_eucalyptus', 20),
    verify_center_trunk('hawthorn', 19),
    verify_center_trunk('holly', 17),
    verify_center_trunk('juniper', 21),
    verify_center_trunk('maclura', 31),
    verify_center_trunk('mahoe', 31),
    verify_center_trunk('mahogany', 11),
    verify_center_trunk('mulberry', 19),
    verify_center_trunk('pink_cherry_blossom', 14),
    verify_center_trunk('pink_ipe', 9),
    verify_center_trunk('pink_ivory', 31),
    verify_center_trunk('purple_ipe', 9),
    verify_center_trunk('purple_jacaranda', 14),
    verify_center_trunk('rainbow_eucalyptus', 20),
    verify_center_trunk('red_cedar', 21),
    verify_center_trunk('rowan', 19),
    verify_center_trunk('snow_gum_eucalyptus', 20),
    verify_center_trunk('syzygium', 31),
    verify_center_trunk('white_cherry_blossom', 14),
    verify_center_trunk('white_ipe', 9),
    verify_center_trunk('white_jacaranda', 14),
    verify_center_trunk('whitebeam', 19),
    verify_center_trunk('yellow_ipe', 9),
    verify_center_trunk('yellow_jacaranda', 14),
    verify_center_trunk('yew', 35)

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
            block['Name'] = String('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:spruce_log':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:birch_log':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:jungle_log':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:acacia_log':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:dark_oak_log':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:log':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:log2':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:oak_wood':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:spruce_wood':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:birch_wood':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:jungle_wood':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:acacia_wood':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:dark_oak_wood':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
        elif block['Name'] == 'minecraft:leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'lifecycle': StringTag('healthy'),
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:leaves2':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:oak_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'lifecycle': StringTag('healthy'),
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:spruce_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'lifecycle': StringTag('healthy'),
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:birch_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'lifecycle': StringTag('healthy'),
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:jungle_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'lifecycle': StringTag('healthy'),
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:acacia_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'lifecycle': StringTag('healthy'),
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:dark_oak_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'lifecycle': StringTag('healthy'),
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:cocoa':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'lifecycle': StringTag('healthy'),
                'persistent': StringTag('false')})
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
            print('%s: %.4f,' % (repr(tree.name), 2.5 / (leaves + 300)))


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
    return {i for i, block in enumerate(file['palette']) if block['Name'] == ('minecraft:oak_leaves' or 'minecraft:spruce_leaves' or 'minecraft:birch_leaves' or 'minecraft:jungle_leaves' or 'minecraft:acacia_leaves' or 'minecraft:dark_oak_leaves' or 'minecraft:leaves' or 'minecraft:leaves2')}


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
        if state not in ('minecraft:oak_wood', 'minecraft:spruce_wood', 'minecraft:birch_wood', 'minecraft:jungle_wood', 'minecraft:acacia_wood', 'minecraft:dark_oak_wood', 'minecraft:oak_log', 'minecraft:spruce_log', 'minecraft:birch_log', 'minecraft:jungle_log', 'minecraft:acacia_log', 'minecraft:dark_oak_log'):
            print('Illegal center state, expected log, got: %s, on %s%d' % (state,prefix, i))


if __name__ == '__main__':
    main()
