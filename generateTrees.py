import os

from nbtlib import nbt
from nbtlib.tag import *


def tree(origin, wood, nameout):
    f = nbt.load(origin + '.nbt')
    for block in f.root['palette']:

        if block['Name'] == 'minecraft:air':
            block['Name'] = String('minecraft:vine')

        if block['Name'] == 'minecraft:log':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': prop['axis']
            })

        if block['Name'] == 'minecraft:log2':
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
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'minecraft:planks':  # Planks indicate bark blocks
            block['Name'] = String('tfc:wood/log/' + wood)
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'minecraft:leaves':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'minecraft:leaves2':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'minecraft:oak_leaves':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'minecraft:spruce_leaves':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'minecraft:birch_leaves':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'minecraft:jungle_leaves':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'minecraft:acacia_leaves':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'minecraft:dark_oak_leaves':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'byg:baobableaves':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })
            
        if block['Name'] == 'byg:structurecheckblock':
            block['Name'] = String('tfc:wood/log/' + wood)
            
        if block['Name'] == 'minecraft:cocoa':
            block['Name'] = String('minecraft:air')

        #Conquest Structures
        
        if block['Name'] == 'conquest:vine_jungle':
            block['Name'] = String('minecraft:vine')

        if block['Name'] == 'conquest:leaves_fullbiome_1':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_fullbiome_2':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_fullbiome_3':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_fullbiome_4':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_fullbiome_5':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_fullbiome_6':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_fullbiome_7':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_fullbiome_8':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_fullbiome_9':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_fullbiome_10':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_full_1':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_full_2':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_full_3':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_full_4':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_full_5':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_full_6':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_full_7':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_full_8':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_full_9':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:leaves_full_10':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:plants_nocollision_1':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:plants_nocollision_2':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:plants_nocollision_3':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:plants_nocollision_4':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:plants_nocollision_5':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:plants_nocollision_6':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:plants_nocollision_7':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:plants_nocollision_8':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:plants_nocollision_9':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:plants_nocollision_10':
            block['Name'] = String('tfcflorae:wood/leaves/' + wood)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })

        if block['Name'] == 'conquest:wood_fence_1':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_fence_2':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_fence_3':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_fence_4':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_fence_5':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_fence_6':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_fence_7':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_fence_8':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_fence_9':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_fence_10':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_wall_1':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_wall_2':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_wall_3':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_wall_4':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_wall_5':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_wall_6':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_wall_7':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_wall_8':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_wall_9':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_wall_10':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_smallpillar_1':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_smallpillar_2':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_smallpillar_3':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_smallpillar_4':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_smallpillar_5':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_smallpillar_6':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_smallpillar_7':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_smallpillar_8':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_smallpillar_9':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_smallpillar_10':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_pillar_1':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_pillar_2':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_pillar_3':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_pillar_4':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_pillar_5':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_pillar_6':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_pillar_7':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_pillar_8':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_pillar_9':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_pillar_10':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_full_1':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_full_2':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_full_3':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_full_4':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_full_5':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_full_6':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_full_7':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_full_8':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_full_9':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_full_10':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })
            
        if block['Name'] == 'conquest:wood_stairs_1':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_stairs_2':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_stairs_3':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_stairs_4':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_stairs_5':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_stairs_6':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_stairs_7':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_stairs_8':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_stairs_9':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_stairs_10':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })
            
        if block['Name'] == 'conquest:wood_verticalslab_1':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_verticalslab_2':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_verticalslab_3':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_verticalslab_4':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_verticalslab_5':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_verticalslab_6':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_verticalslab_7':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_verticalslab_8':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_verticalslab_9':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_verticalslab_10':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_verticalslab_15':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

        if block['Name'] == 'conquest:wood_verticalslab_28':
            block['Name'] = String('tfc:wood/log/' + wood)
            prop = block['Properties']
            block['Properties'] = Compound({
                'small': String('false'),
                'placed': String('false'),
                'axis': String('none')
            })

    if not os.path.exists('src_trees/main/resources/assets/tfc/structures/' + wood):
        os.makedirs('src_trees/main/resources/assets/tfc/structures/' + wood)
    f.save('src_trees/main/resources/assets/tfc/structures/' + wood + '/' + nameout + '.nbt')


def fruit_tree(ftree):
    f = nbt.load('structure_templates/fruit_tree_base.nbt')
    for block in f.root['palette']:
        if block['Name'] == 'tfc:fruit_trees/branch/peach':
            block['Name'] = String('tfc:fruit_trees/branch/' + ftree)
        elif block['Name'] == 'tfc:fruit_trees/leaves/peach':
            block['Name'] = String('tfc:fruit_trees/leaves/' + ftree)
        elif block['Name'] == 'tfc:fruit_trees/trunk/peach':
            block['Name'] = String('tfc:fruit_trees/trunk/' + ftree)
            
        if block['Name'] == 'minecraft:leaves':
            block['Name'] = String('tfc:fruit_trees/leaves/' + ftree)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })
        elif block['Name'] == 'minecraft:log':
            block['Name'] = String('tfc:fruit_trees/trunk/' + ftree)
            prop = block['Properties']
            block['Properties'] = Compound({
                'east': String('false'),
                'north': String('false'),
                'south': String('false'),
                'west': String('false')
            })
        elif block['Name'] == 'minecraft:planks':
            block['Name'] = String('tfc:fruit_trees/branch/' + ftree)
            prop = block['Properties']
            block['Properties'] = Compound({
                'facing': String('down')
            })
            
        if block['Name'] == 'minecraft:oak_leaves':
            block['Name'] = String('tfc:fruit_trees/leaves/' + ftree)
            block['Properties'] = Compound({
                'decayable': String('true'),
                'harvestable': String('true'),
                'state': String('normal')
            })
        elif block['Name'] == 'minecraft:oak_log':
            block['Name'] = String('tfc:fruit_trees/trunk/' + ftree)
            prop = block['Properties']
            block['Properties'] = Compound({
                'east': String('false'),
                'north': String('false'),
                'south': String('false'),
                'west': String('false')
            })
        elif block['Name'] == 'minecraft:oak_planks':
            block['Name'] = String('tfc:fruit_trees/branch/' + ftree)
            prop = block['Properties']
            block['Properties'] = Compound({
                'facing': String('down')
            })

    if not os.path.exists('src_trees/main/resources/assets/tfc/structures/fruit_trees'):
        os.makedirs('src_trees/main/resources/assets/tfc/structures/fruit_trees')
    f.save('src_trees/main/resources/assets/tfc/structures/fruit_trees/' + ftree + '.nbt')


WOOD_TYPES = {
    'ash': 'normal',
    'aspen': 'aspen',
    'birch': 'normal',
    'chestnut': 'normal',
    'hickory': 'normal',
    'maple': 'normal',
    'oak': 'tallXL',
    'sycamore': 'normal',
    'white_cedar': 'tall',
    'african_padauk': 'african_padauk',
    'alder': 'alder',
    'angelim': 'angelim',
    'baobab': 'baobab',
    'beech': 'beech',
    'black_walnut': 'black_walnut',
    'box': 'tall',
    'brazilwood': 'brazilwood',
    'butternut': 'butternut',
    'citrus': 'citrus',
    'cocobolo': 'cocobolo',
    'cypress': 'cypress',
    'ebony': 'ebony',
    'elder': 'elder',
    'eucalyptus': 'eucalyptus',
    'european_oak': 'european_oak',
    'fever': 'fever',
    'fruitwood': 'fruitwood',
    'giganteum': 'giganteum',
    'ginkgo': 'ginkgo',
    'greenheart': 'greenheart',
    'hawthorn': 'hawthorn',
    'hazel': 'hazel',
    'hemlock': 'hemlock',
    'holly': 'holly',
    'hornbeam': 'hornbeam',
    'ipe': 'ipe',
    'pink_ipe': 'pink_ipe',
    'white_ipe': 'white_ipe',
    'yellow_ipe': 'yellow_ipe',
    'iroko': 'iroko',
    'ironwood': 'ironwood',
    'jacaranda': 'jacaranda',
    'juniper': 'juniper',
    'kauri': 'kauri',
    'larch': 'larch',
    'limba': 'limba',
    'lime': 'lime',
    'locust': 'locust',
    'logwood': 'logwood',
    'maclura': 'maclura',
    'mahoe': 'mahoe',
    'mahogany': 'mahogany',
    'marblewood': 'marblewood',
    'messmate': 'messmate',
    'mountain_ash': 'mountain_ash',
    'nordmann_fir': 'nordmann_fir',
    'norway_spruce': 'norway_spruce',
    'papaya': 'papaya',
    'persimmon': 'persimmon',
    'pink_cherry': 'pink_cherry',
    'pink_ivory': 'pink_ivory',
    'plum': 'plum',
    'poplar': 'poplar',
    'purpleheart': 'purpleheart',
    'red_cedar': 'red_cedar',
    'red_elm': 'red_elm',
    'redwood': 'redwood',
    'rowan': 'rowan',
    'rubber_fig': 'rubber_fig',
    'sweetgum': 'sweetgum',
    'yellow_sweetgum': 'yellow_sweetgum',
    'orange_sweetgum': 'orange_sweetgum',
    'red_sweetgum': 'red_sweetgum',
    'syzygium': 'syzygium',
    'teak': 'teak',
    'walnut': 'walnut',
    'wenge': 'wenge',
    'white_cherry': 'white_cherry',
    'white_elm': 'white_elm',
    'whitebeam': 'whitebeam',
    'yellow_meranti': 'yellow_meranti',
    'yew': 'yew',
    'zebrawood': 'zebrawood'
}

for wood, key in WOOD_TYPES.items():

    # aspen
    if key == 'aspen':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9']:
            tree('structure_templates/aspen/aspen_' + s, wood, s)

    # normal (vanilla oak)
    if key == 'normal':
        tree('structure_templates/normal', wood, 'base_3')
        tree('structure_templates/normal_overlay', wood, 'overlay_3')

    # tall (tfc douglas fir, but smaller)
    if key == 'tall':
        tree('structure_templates/tall', wood, 'base_3')
        tree('structure_templates/tall_overlay', wood, 'overlay_3')

    # tallXL (tfc douglas fir, full size-ish)
    if key == 'tallXL':
        tree('structure_templates/tall2', wood, 'base_3')
        tree('structure_templates/tall2_overlay', wood, 'overlay_3')

    # arrow_bamboo
    if key == 'arrow_bamboo':
        for s in ['1', '2', '3', '4']:
            tree('structure_templates/arrow_bamboo/arrow_bamboo_' + s, wood, s)

    # african_padauk
    if key == 'african_padauk':
        for s in ['1', '2', '3', '4', '5', '6']:
            tree('structure_templates/african_padauk/african_padauk_' + s, wood, s)

    # alder
    if key == 'alder':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10']:
            tree('structure_templates/alder/alder_' + s, wood, s)

    # angelim
    if key == 'angelim':
        for s in ['1', '2', '3', '4', '5', '6']:
            tree('structure_templates/angelim/angelim_' + s, wood, s)

    # baobab
    if key == 'baobab':
        for s in ['1', '2']:
            tree('structure_templates/baobab/baobab_' + s, wood, s)

    # beech
    if key == 'beech':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11']:
            tree('structure_templates/beech/beech_' + s, wood, s)

    # black_walnut
    if key == 'black_walnut':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15']:
            tree('structure_templates/black_walnut/black_walnut_' + s, wood, s)

    # brazilwood
    if key == 'brazilwood':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14']:
            tree('structure_templates/brazilwood/brazilwood_' + s, wood, s)

    # butternut
    if key == 'butternut':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15']:
            tree('structure_templates/butternut/butternut_' + s, wood, s)

    # cocobolo
    if key == 'cocobolo':
        for s in ['1', '2', '3', '4', '5', '6']:
            tree('structure_templates/cocobolo/cocobolo_' + s, wood, s)

    # cypress
    if key == 'cypress':
        for s in ['1', '2', '3', '4', '5']:
            tree('structure_templates/cypress/cypress_' + s, wood, s)

    # ebony
    if key == 'ebony':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20']:
            tree('structure_templates/ebony/ebony_' + s, wood, s)

    # elder
    if key == 'elder':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15']:
            tree('structure_templates/elder/elder_' + s, wood, s)

    # eucalyptus
    if key == 'eucalyptus':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16']:
            tree('structure_templates/eucalyptus/eucalyptus_' + s, wood, s)

    # european_oak
    if key == 'european_oak':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15']:
            tree('structure_templates/european_oak/european_oak_' + s, wood, s)

    # fever
    if key == 'fever':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13']:
            tree('structure_templates/fever/fever_' + s, wood, s)

    # fruitwood
    if key == 'fruitwood':
        for s in ['1', '2', '3']:
            tree('structure_templates/fruitwood/fruitwood_' + s, wood, s)

    # ginkgo
    if key == 'ginkgo':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20']:
            tree('structure_templates/ginkgo/ginkgo_' + s, wood, s)

    # greenheart
    if key == 'greenheart':
        for s in ['1', '2', '3', '4', '5', '6']:
            tree('structure_templates/greenheart/greenheart_' + s, wood, s)

    # hawthorn
    if key == 'hawthorn':
        for s in ['1', '2', '3', '4', '5']:
            tree('structure_templates/hawthorn/hawthorn_' + s, wood, s)

    # hazel
    if key == 'hazel':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15']:
            tree('structure_templates/hazel/hazel_' + s, wood, s)

    # hemlock
    if key == 'hemlock':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16']:
            tree('structure_templates/hemlock/hemlock_' + s, wood, s)

    # holly
    if key == 'holly':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16']:
            tree('structure_templates/holly/holly_' + s, wood, s)

    # hornbeam
    if key == 'hornbeam':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13']:
            tree('structure_templates/hornbeam/hornbeam_' + s, wood, s)

    # ipe
    if key == 'ipe':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22']:
            tree('structure_templates/ipe/ipe_' + s, wood, s)

    # pink_ipe
    if key == 'pink_ipe':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22']:
            tree('structure_templates/ipe/ipe_' + s, wood, s)

    # white_ipe
    if key == 'white_ipe':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22']:
            tree('structure_templates/ipe/ipe_' + s, wood, s)

    # yellow_ipe
    if key == 'yellow_ipe':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22']:
            tree('structure_templates/ipe/ipe_' + s, wood, s)

    # iroko
    if key == 'iroko':
        for s in ['1', '2', '3', '4', '5', '6']:
            tree('structure_templates/iroko/iroko_' + s, wood, s)

    # ironwood
    if key == 'ironwood':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16']:
            tree('structure_templates/ironwood/ironwood_' + s, wood, s)

    # jacaranda
    if key == 'jacaranda':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15']:
            tree('structure_templates/jacaranda/jacaranda_' + s, wood, s)

    # juniper
    if key == 'juniper':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17']:
            tree('structure_templates/juniper/juniper_' + s, wood, s)

    # kauri
    if key == 'kauri':
        for s in ['1', '2', '3', '4', '5', '6']:
            tree('structure_templates/kauri/kauri_' + s, wood, s)

    # larch
    if key == 'larch':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10']:
            tree('structure_templates/larch/larch_' + s, wood, s)

    # limba
    if key == 'limba':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13']:
            tree('structure_templates/limba/limba_' + s, wood, s)

    # locust
    if key == 'locust':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15']:
            tree('structure_templates/locust/locust_' + s, wood, s)

    # logwood
    if key == 'logwood':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10']:
            tree('structure_templates/logwood/logwood_' + s, wood, s)

    # maclura
    if key == 'maclura':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15']:
            tree('structure_templates/maclura/maclura_' + s, wood, s)

    # mahoe
    if key == 'mahoe':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15']:
            tree('structure_templates/mahoe/mahoe_' + s, wood, s)

    # mahogany
    if key == 'mahogany':
        for s in ['1', '2', '3', '4', '5', '6']:
            tree('structure_templates/mahogany/mahogany_' + s, wood, s)

    # marblewood
    if key == 'marblewood':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24']:
            tree('structure_templates/marblewood/marblewood_' + s, wood, s)

    # messmate
    if key == 'messmate':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18']:
            tree('structure_templates/messmate/messmate_' + s, wood, s)

    # mountain_ash
    if key == 'mountain_ash':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20']:
            tree('structure_templates/mountain_ash/mountain_ash_' + s, wood, s)

    # nordmann_fir
    if key == 'nordmann_fir':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26']:
            tree('structure_templates/nordmann_fir/nordmann_fir_' + s, wood, s)

    # norway_spruce
    if key == 'norway_spruce':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16']:
            tree('structure_templates/norway_spruce/norway_spruce_' + s, wood, s)

    # persimmon
    if key == 'persimmon':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13']:
            tree('structure_templates/persimmon/persimmon_' + s, wood, s)

    # pink_cherry
    if key == 'pink_cherry':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15']:
            tree('structure_templates/pink_cherry/pink_cherry_' + s, wood, s)

    # pink_ivory
    if key == 'pink_ivory':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19']:
            tree('structure_templates/pink_ivory/pink_ivory_' + s, wood, s)

    # poplar
    if key == 'poplar':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17']:
            tree('structure_templates/poplar/poplar_' + s, wood, s)

    # purpleheart
    if key == 'purpleheart':
        for s in ['1', '2', '3', '4', '5', '6']:
            tree('structure_templates/purpleheart/purpleheart_' + s, wood, s)

    # red_cedar
    if key == 'red_cedar':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25']:
            tree('structure_templates/red_cedar/red_cedar_' + s, wood, s)

    # red_elm
    if key == 'red_elm':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20']:
            tree('structure_templates/red_elm/red_elm_' + s, wood, s)

    # redwood
    if key == 'redwood':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22']:
            tree('structure_templates/redwood/redwood_' + s, wood, s)

    # rowan
    if key == 'rowan':
        for s in ['1', '2', '3', '4', '5', '6']:
            tree('structure_templates/rowan/rowan_' + s, wood, s)

    # rubber_fig
    if key == 'rubber_fig':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9']:
            tree('structure_templates/rubber_fig/rubber_fig_' + s, wood, s)

    # sweetgum
    if key == 'sweetgum':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20']:
            tree('structure_templates/sweetgum/sweetgum_' + s, wood, s)

    # yellow_sweetgum
    if key == 'yellow_sweetgum':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20']:
            tree('structure_templates/sweetgum/sweetgum_' + s, wood, s)

    # orange_sweetgum
    if key == 'orange_sweetgum':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20']:
            tree('structure_templates/sweetgum/sweetgum_' + s, wood, s)

    # red_sweetgum
    if key == 'red_sweetgum':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20']:
            tree('structure_templates/sweetgum/sweetgum_' + s, wood, s)

    # syzygium
    if key == 'syzygium':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14']:
            tree('structure_templates/syzygium/syzygium_' + s, wood, s)

    # teak
    if key == 'teak':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13']:
            tree('structure_templates/teak/teak_' + s, wood, s)

    # walnut
    if key == 'walnut':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15']:
            tree('structure_templates/walnut/walnut_' + s, wood, s)

    # wenge
    if key == 'wenge':
        for s in ['1', '2', '3', '4', '5', '6']:
            tree('structure_templates/wenge/wenge_' + s, wood, s)

    # white_cherry
    if key == 'white_cherry':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15']:
            tree('structure_templates/white_cherry/white_cherry_' + s, wood, s)

    # white_elm
    if key == 'white_elm':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20']:
            tree('structure_templates/white_elm/white_elm_' + s, wood, s)

    # whitebeam
    if key == 'whitebeam':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14']:
            tree('structure_templates/whitebeam/whitebeam_' + s, wood, s)

    # yellow_meranti
    if key == 'yellow_meranti':
        for s in ['1', '2', '3', '4', '5', '6']:
            tree('structure_templates/yellow_meranti/yellow_meranti_' + s, wood, s)

    # yew
    if key == 'yew':
        for s in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19']:
            tree('structure_templates/yew/yew_' + s, wood, s)

    # zebrawood
    if key == 'zebrawood':
        for s in ['1', '2', '3', '4', '5', '6']:
            tree('structure_templates/zebrawood/zebrawood_' + s, wood, s)

FRUIT_TREES = [
    'abiu'
]

for tree in FRUIT_TREES:
    fruit_tree(tree)
