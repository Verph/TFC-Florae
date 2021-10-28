import os

from nbtlib import nbt
from nbtlib.tag import *

Color = [
    "blue",
    "cyan",
    "green",
    "light_blue",
    "lime",
    "magenta",
    "orange",
    "pink",
    "purple",
    "red",
    "white",
    "yellow"]

CORAL_TYPES = {
    'brain': 'brain',
    'bubble': 'bubble',
    'fire': 'fire',
    'horn': 'horn',
    'tube': 'tube'
}

def coral(origin, fileId, coralType, nameout, color):
    f = nbt.load(origin + str((fileId % 15) + 1) + '.nbt')
    for block in f.root['palette']:

        if block['Name'] == 'minecraft:stone':
            block['Name'] = String('tfcflorae:coral/block/' + coralType + '/' + color)
        if block['Name'] == 'minecraft:air':
            block['Name'] = String('minecraft:structure_void')

    if not os.path.exists('src_trees/tfcflorae/structures/' + coralType):
        os.makedirs('src_trees/tfcflorae/structures/' + coralType)
    f.save('src_trees/tfcflorae/structures/' + coralType + '/' + nameout + '.nbt')

for coralType, key in CORAL_TYPES.items():

    i = -1;
    for s in range(len(Color) * 15):
        if s % 15 == 0:
            i += 1
        coral('structure_templates/coral_structures/coral_', s, coralType, str(s + 1), Color[i])