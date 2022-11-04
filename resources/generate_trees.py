import os
from typing import Set, Any, Tuple, NamedTuple, Literal, Union

from nbtlib import nbt
from nbtlib.tag import *
from nbtlib.tag import String as StringTag, Int as IntTag

Tree = NamedTuple('Tree', name=str, feature=Literal['random', 'overlay', 'stacked'], variant=str, count=Union[int, Tuple[int, ...]])

DATA_VERSION = 2970

NORMAL_TREES = [
<<<<<<< Updated upstream
    Tree('bald_cypress', 'random', 'bald_cypress', 22),
    Tree('red_cypress', 'random', 'red_cypress', 22),
    Tree('black_willow', 'random', 'black_willow', 17)
]

LARGE_TREES = [
    Tree('bald_cypress', 'random', 'bald_cypress', 0),
    Tree('red_cypress', 'random', 'red_cypress', 0),
    Tree('black_willow', 'random', 'black_willow', 0)
=======
    Tree('bald_cypress', 'random', 'bald_cypress', 6),
    Tree('red_cypress', 'random', 'red_cypress', 6),
    Tree('black_willow', 'random', 'black_willow', 10),
    Tree('jabuticabeira', 'random', 'jabuticabeira', 18),
    Tree('ghaf', 'random', 'ghaf', 16),
    Tree('laurel', 'random', 'laurel', 21)
]

LARGE_TREES = [
    Tree('black_willow', 'random', 'black_willow_large', 14)
>>>>>>> Stashed changes
]

DEAD_TREES = [
    Tree('bald_cypress', 'random', 'bald_cypress_dead', 13),
    Tree('red_cypress', 'random', 'red_cypress_dead', 13),
<<<<<<< Updated upstream
    Tree('black_willow', 'random', 'dead_stump', 3)
=======
    Tree('black_willow', 'random', 'dead_stump', 3),
    Tree('jabuticabeira', 'random', 'jabuticabeira_dead', 13),
    Tree('ghaf', 'random', 'dead_small', 6),
    Tree('laurel', 'random', 'dead_small', 6)
>>>>>>> Stashed changes
]


class Count:  # global mutable variables that doesn't require using the word "global" :)
    SKIPPED = 0
    NEW = 0
    MODIFIED = 0
    ERRORS = 0


def main():
    print('Verifying tree structures')
<<<<<<< Updated upstream
    verify_center_trunk('bald_cypress', 22)
    verify_center_trunk('red_cypress', 22)
    verify_center_trunk('black_willow', 17)
    verify_center_trunk('bald_cypress_dead', 13)
    verify_center_trunk('red_cypress_dead', 13)
    verify_center_trunk('dead_stump', 3)
=======
    verify_center_trunk('bald_cypress', 6)
    verify_center_trunk('bald_cypress_dead', 13)
    verify_center_trunk('red_cypress', 6)
    verify_center_trunk('red_cypress_dead', 13)
    verify_center_trunk('black_willow', 10)
    verify_center_trunk('black_willow_large', 14)
    verify_center_trunk('dead_stump', 3)
    verify_center_trunk('dead_small', 6)
    verify_center_trunk('jabuticabeira', 18)
    verify_center_trunk('jabuticabeira_dead', 13)
>>>>>>> Stashed changes

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
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:spruce_log':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:birch_log':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:jungle_log':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:acacia_log':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:dark_oak_log':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:log':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:log2':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:oak_wood':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:spruce_wood':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:birch_wood':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:jungle_wood':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:acacia_wood':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:dark_oak_wood':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:stone':
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'minecraft:leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
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
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:spruce_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:birch_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:jungle_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:acacia_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:dark_oak_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'persistent': StringTag('false')})
        elif block['Name'] == 'byg:willow_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'persistent': StringTag('false')})
        elif block['Name'] == 'byg:cypress_leaves':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'persistent': StringTag('false')})
        elif block['Name'] == 'byg:willow_log':
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
        elif block['Name'] == 'byg:cypress_log':
<<<<<<< Updated upstream
            block['Name'] = StringTag('tfcflorae:wood/log/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': prop['axis']})
=======
            block['Name'] = StringTag('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y')})
                
        elif block['Name'] == 'tfc:wood/leaves/bald_cypress':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'persistent': StringTag('false')})
        elif block['Name'] == 'tfc:wood/wood/bald_cypress':
            block['Name'] = String('tfcflorae:wood/wood/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'natural': StringTag('true'),
                'axis': StringTag('y'),
                'lifecycle': StringTag('healthy')})
                
                
>>>>>>> Stashed changes
        elif block['Name'] == 'minecraft:cocoa':
            block['Name'] = StringTag('tfcflorae:wood/leaves/%s' % wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'persistent': StringTag('false')})
        elif block['Name'] == 'minecraft:fern':
            block['Name'] = StringTag('tfc:plant/jungle_vines')
        elif block['Name'] == 'minecraft:vine':
            block['Name'] = StringTag('tfc:plant/jungle_vines')
        elif block['Name'] == 'minecraft:grass':
            block['Name'] = StringTag('tfc:plant/hanging_vines_plant')
            prop = block['Properties']
            block['Properties'] = Compound({
                'age': StringTag('13')})
        elif block['Name'] == 'minecraft:tall_grass':
            block['Name'] = StringTag('tfc:plant/hanging_vines_plant')
            prop = block['Properties']
            block['Properties'] = Compound({
                'age': StringTag('13')})
        elif block['Name'] == 'minecraft:tallgrass':
            block['Name'] = StringTag('tfc:plant/hanging_vines_plant')
            prop = block['Properties']
            block['Properties'] = Compound({
                'age': StringTag('13')})
        elif block['Name'] == 'minecraft:double_plant':
            block['Name'] = StringTag('tfc:plant/hanging_vines_plant')
            prop = block['Properties']
            block['Properties'] = Compound({
                'age': StringTag('13')})
        elif block['Name'] == 'minecraft:air':
            block['Name'] = StringTag('minecraft:structure_void')
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
