const fs = require('fs')

function getJson(metal)
{
    return {
        "forge_marker": 1,
        "defaults": {
          "textures": {
            "texture": `tfc:blocks/trapdoor/${metal}`
          }
        },
        "variants": {
          "facing=north,half=bottom,open=false": {"model": "minecraft:iron_trapdoor_bottom"},
          "facing=south,half=bottom,open=false": {"model": "minecraft:iron_trapdoor_bottom"},
          "facing=east,half=bottom,open=false": {"model": "minecraft:iron_trapdoor_bottom"},
          "facing=west,half=bottom,open=false": {"model": "minecraft:iron_trapdoor_bottom"},
          "facing=north,half=top,open=false": {"model": "minecraft:iron_trapdoor_top"},
          "facing=south,half=top,open=false": {"model": "minecraft:iron_trapdoor_top"},
          "facing=east,half=top,open=false": {"model": "minecraft:iron_trapdoor_top"},
          "facing=west,half=top,open=false": {"model": "minecraft:iron_trapdoor_top"},
          "facing=north,half=bottom,open=true": {"model": "minecraft:iron_trapdoor_open"},
          "facing=south,half=bottom,open=true": {"model": "minecraft:iron_trapdoor_open", "y": 180},
          "facing=east,half=bottom,open=true": {"model": "minecraft:iron_trapdoor_open", "y": 90},
          "facing=west,half=bottom,open=true": {"model": "minecraft:iron_trapdoor_open", "y": 270},
          "facing=north,half=top,open=true": {"model": "minecraft:iron_trapdoor_open"},
          "facing=south,half=top,open=true": {"model": "minecraft:iron_trapdoor_open", "y": 180},
          "facing=east,half=top,open=true": {"model": "minecraft:iron_trapdoor_open", "y": 90},
          "facing=west,half=top,open=true": {"model": "minecraft:iron_trapdoor_open", "y": 270}
        }
    }
}

let METAL_TYPES = [
	'soulforged_steel',
	'signalum',
	'lumium',
	'enderium',
	'refined_obsidian',
	'refined_glowstone',
	'thaumium',
	'void_metal'
]

for(let metal_type of METAL_TYPES)
{
    let json = getJson(metal_type)
    fs.writeFileSync(`./src/main/resources/assets/tfc/blockstates/trapdoor/${metal_type}.json`, JSON.stringify(json, null, 2))
}