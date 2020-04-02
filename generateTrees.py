import os

from nbtlib import nbt
from nbtlib.tag import *


def tree(origin, wood, nameout):
    f = nbt.load(origin + '.nbt')
    for block in f.root['palette']:

        if block['Name'] == 'minecraft:log':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': prop['axis']
            })
            
        if block['Name'] == 'minecraft:oak_log':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': prop['axis']
            })
            
        if block['Name'] == 'minecraft:spruce_log':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': prop['axis']
            })
            
        if block['Name'] == 'minecraft:birch_log':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': prop['axis']
            })
            
        if block['Name'] == 'minecraft:jungle_log':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': prop['axis']
            })
            
        if block['Name'] == 'minecraft:acacia_log':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': prop['axis']
            })
            
        if block['Name'] == 'minecraft:dark_oak_log':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': prop['axis']
            })
            
        if block['Name'] == 'byg:baobablog':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })
            
        if block['Name'] == 'byg:baobabfruitblock':
            block['Name'] = String('tfc:wood/leaves/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'minecraft:planks':  # Planks indicate bark blocks
            block['Name'] = String('tfc:wood/log/' + wood)
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'minecraft:leaves':
            block['Name'] = String('tfc:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true')
            })

        if block['Name'] == 'minecraft:oak_leaves':
            block['Name'] = String('tfc:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true')
            })

        if block['Name'] == 'minecraft:spruce_leaves':
            block['Name'] = String('tfc:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true')
            })

        if block['Name'] == 'minecraft:birch_leaves':
            block['Name'] = String('tfc:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true')
            })

        if block['Name'] == 'minecraft:jungle_leaves':
            block['Name'] = String('tfc:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true')
            })

        if block['Name'] == 'minecraft:acacia_leaves':
            block['Name'] = String('tfc:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true')
            })

        if block['Name'] == 'minecraft:dark_oak_leaves':
            block['Name'] = String('tfc:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true')
            })

        if block['Name'] == 'byg:baobableaves':
            block['Name'] = String('tfc:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true')
            })
            
        if block['Name'] == 'byg:structurecheckblock':
            block['Name'] = String('tfc:wood/log/' + wood)
            
        if block['Name'] == 'minecraft:cocoa':
            block['Name'] = String('minecraft:air' + wood)

    if not os.path.exists('src/main/resources/assets/tfc/structures/' + wood):
        os.makedirs('src/main/resources/assets/tfc/structures/' + wood)
    f.save('src/main/resources/assets/tfc/structures/' + wood + '/' + nameout + '.nbt')


def fruit_tree(ftree):
    f = nbt.load('structure_templates/fruit_tree_base.nbt')
    for block in f.root['palette']:
        if block['Name'] == 'tfc:fruit_trees/branch/peach':
            block['Name'] = String('tfc:fruit_trees/branch/' + ftree)
        elif block['Name'] == 'tfc:fruit_trees/leaves/peach':
            block['Name'] = String('tfc:fruit_trees/leaves/' + ftree)
        elif block['Name'] == 'tfc:fruit_trees/trunk/peach':
            block['Name'] = String('tfc:fruit_trees/trunk/' + ftree)

    if not os.path.exists('src/main/resources/assets/tfc/structures/fruit_trees'):
        os.makedirs('src/main/resources/assets/tfc/structures/fruit_trees')
    f.save('src/main/resources/assets/tfc/structures/fruit_trees/' + ftree + '.nbt')


WOOD_TYPES = {
    'alder': 'alder',
    'bamboo': 'bamboo',
    'baobab': 'baobab',
    'beech': 'beech',
    'cinnamon': 'cinnamon',
    'ebony': 'ebony',
    'eucalyptus': 'eucalyptus',
    'fever': 'fever',
    'fruitwood': 'fruitwood',
    'ginkgo': 'ginkgo',
    'larch': 'larch',
    'hawthorn': 'hawthorn',
    'juniper': 'juniper',
    'mahogany': 'mahogany',
    'norway_spruce': 'norway_spruce',
    'poplar': 'poplar',
    'redwood': 'redwood',
    'rowan': 'rowan',
    'teak': 'teak',
    'white_elm': 'white_elm',
    'yew': 'yew'
}

for wood, key in WOOD_TYPES.items():

    # alder
    if key == 'alder':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8']:
            tree('structure_templates/alder/alder_' + s, wood, s)

    # baobab
    if key == 'baobab':
        for s in ['1', '2']:
            tree('structure_templates/baobab/baobab_' + s, wood, s)

    # beech
    if key == 'beech':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9']:
            tree('structure_templates/beech/beech_' + s, wood, s)

    # cinnamon
    if key == 'cinnamon':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9']:
            tree('structure_templates/cinnamon/cinnamon_' + s, wood, s)

    # ebony
    if key == 'ebony':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19']:
            tree('structure_templates/ebony/ebony_' + s, wood, s)

    # eucalyptus
    if key == 'eucalyptus':
        for s in ['1', '2', '3', '4', '5', '6', '7']:
            tree('structure_templates/eucalyptus/eucalyptus_' + s, wood, s)

    # fever
    if key == 'fever':
        for s in ['1', '2', '3', '4', '5', '6']:
            tree('structure_templates/fever/fever_' + s, wood, s)

    # fruitwood
    if key == 'fruitwood':
        for s in ['1', '2', '3']:
            tree('structure_templates/fruitwood/fruitwood_' + s, wood, s)

    # ginkgo
    if key == 'ginkgo':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']:
            tree('structure_templates/ginkgo/ginkgo_' + s, wood, s)

    # hawthorn
    if key == 'hawthorn':
        for s in ['1', '2', '3', '4', '5']:
            tree('structure_templates/hawthorn/hawthorn_' + s, wood, s)

    # juniper
    if key == 'juniper':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11']:
            tree('structure_templates/juniper/juniper_' + s, wood, s)

    # larch
    if key == 'larch':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9']:
            tree('structure_templates/larch/larch_' + s, wood, s)

    # limba
    if key == 'limba':
        for s in ['1', '2', '3', '4', '5', '6', '7']:
            tree('structure_templates/limba/limba_' + s, wood, s)

    # mahogany
    if key == 'mahogany':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13']:
            tree('structure_templates/mahogany/mahogany_' + s, wood, s)

    # norway_spruce
    if key == 'norway_spruce':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9']:
            tree('structure_templates/norway_spruce/norway_spruce_' + s, wood, s)

    # poplar
    if key == 'poplar':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8']:
            tree('structure_templates/poplar/poplar_' + s, wood, s)

    # redwood
    if key == 'redwood':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10']:
            tree('structure_templates/redwood/redwood_' + s, wood, s)

    # rowan
    if key == 'rowan':
        for s in ['1', '2', '3', '4', '5']:
            tree('structure_templates/rowan/rowan_' + s, wood, s)

    # teak
    if key == 'teak':
        for s in ['1', '2', '3', '4', '5', '6', '7']:
            tree('structure_templates/teak/teak_' + s, wood, s)

    # white_elm
    if key == 'white_elm':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14']:
            tree('structure_templates/white_elm/white_elm_' + s, wood, s)

    # yew
    if key == 'yew':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17']:
            tree('structure_templates/yew/yew_' + s, wood, s)

FRUIT_TREES = [
    'cacao',
    'coffea',
    'date',
    'papaya',
    'almond',
    'cashew',
    'coconut',
    'hazelnut',
    'macadamia',
    'pistachio'
]

for tree in FRUIT_TREES:
    fruit_tree(tree)
