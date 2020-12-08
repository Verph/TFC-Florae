const fs = require('fs');

let WOOD_TYPES = {
    'acacia': 'acacia',
    'ash': 'normal',
    'aspen': 'normal',
    'birch': 'normal',
    'blackwood': 'tall',
    'chestnut': 'normal',
    'douglas_fir': 'tallXL',
    'hickory': 'normal',
    'maple': 'normal',
    'oak': 'tallXL',
    'palm': 'tropical',
    'pine': 'conifer',
    'rosewood': 'tall',
    'sequoia': 'sequoia',
    'spruce': 'conifer',
    'sycamore': 'normal',
    'white_cedar': 'tall',
    'willow': 'willow',
    'kapok': 'jungle',
    'hevea': ''
}


for(let woodType of Object.keys(WOOD_TYPES))
{
    genChairModel(woodType)
    genTableModel(woodType)
    generateRecipes(woodType)
	//console.log(`tile.rustic.chair_${woodType}.name=${getName(woodType)} Chair`)
	//console.log(`tile.rustic.table_${woodType}.name=${getName(woodType)} Table`)
}

function generateRecipes(woodType)
{
    let chairJSON = {
        "type": "minecraft:crafting_shaped",
        "pattern": [
            "A  ",
            "AAA",
            "B B"
        ],
        "key": {
            "A": {
                "item": `tfc:wood/planks/${woodType}`,
            },
            "B": {
                "type": "forge:ore_dict",
                "ore": "stickWood"
            }
        },
        "result": {
            "item": `tfcompat:chair_${woodType}`,
            "count": 4
        }
    }
    let tableJSON = {
        "type": "minecraft:crafting_shaped",
        "pattern": [
            "AAA",
            "B B"
        ],
        "key": {
            "A": {
                "item": `tfc:wood/planks/${woodType}`,
            },
            "B": {
                "type": "forge:ore_dict",
                "ore": "stickWood"
            }
        },
        "result": {
            "item": `tfcompat:table_${woodType}`,
            "count": 2
        }
    }
    let food_shelfJSON = {
		"type": "minecraft:crafting_shaped",
		"pattern": [
			"PPP",
			"LLL",
			"PPP"
		],
		"key": {
			"P": {
				"item": `tfc:wood/planks/${woodType}`
			},
			"L": {
				"item": `tfc:wood/lumber/${woodType}`
			}
		},
		"result": {
			"item": `tfcstorage:wood/food_shelf/${woodType}`,
			"count": 1
		}
	}
    let hangerJSON = {
		"type": "minecraft:crafting_shaped",
		"pattern": [
			"PPP",
			" S ",
			" S "
		],
		"key": {
			"P": {
				"item": `tfc:wood/planks/${woodType}`
			},
			"S": {
				"type": "forge:ore_dict",
				"ore": "string"
			}

		},
		"result": {
			"item": `tfcstorage:wood/hanger/${woodType}`,
			"count": 1
		}
	}
    fs.writeFileSync(`./src/main/resources/assets/tfcompat/recipes/rustic/chair/${woodType}.json`, JSON.stringify(chairJSON, null, 2))
    fs.writeFileSync(`./src/main/resources/assets/tfcompat/recipes/rustic/table/${woodType}.json`, JSON.stringify(tableJSON, null, 2))
    fs.writeFileSync(`./src/main/resources/assets/tfcompat/recipes/wood/food_shelf/${woodType}.json`, JSON.stringify(food_shelfJSON, null, 2))
    fs.writeFileSync(`./src/main/resources/assets/tfcompat/recipes/wood/hanger/${woodType}.json`, JSON.stringify(hangerJSON, null, 2))
}

function getName(woodType)
{
	if(woodType.includes("_"))
	{
		let types = woodType.split("_");
		let res = ""
		for(let wood of types)
		{
			res = res + " " + wood[0].toUpperCase() + wood.substring(1)
		}
		return res;
	}
	else {
		return woodType[0].toUpperCase() + woodType.substring(1)
	}
}

function genTableModel(woodType)
{
    const name = `table_${woodType}`
    const folder = "./src/main/resources/assets/tfcompat"
    const tfcWood = `tfc:blocks/wood/planks/${woodType}`
    const blockInventoryModel = getInventoryModel(tfcWood)
    const blockLegModel = getLegModel(tfcWood)
    const blockTopModel = getTopModel(tfcWood)
    const itemTableModel = getTableItemModel(name)
    const blockStateModel = getBlockStateModel(name)

    fs.writeFileSync(`${folder}/models/block/${name}_inventory.json`, JSON.stringify(blockInventoryModel, null, 2))
    fs.writeFileSync(`${folder}/models/block/${name}_leg_nw.json`, JSON.stringify(blockLegModel, null, 2))
    fs.writeFileSync(`${folder}/models/block/${name}_top.json`, JSON.stringify(blockTopModel, null, 2))
    fs.writeFileSync(`${folder}/models/item/${name}.json`, JSON.stringify(itemTableModel, null, 2))
    fs.writeFileSync(`${folder}/blockstates/${name}.json`, JSON.stringify(blockStateModel, null, 2))
}

function genChairModel(woodType='oak')
{
    const name = `chair_${woodType}`
    const folder = "./src/main/resources/assets/tfcompat"
    const tfcWood = `tfc:blocks/wood/planks/${woodType}`
    const blockmodel = getBlockModel(tfcWood)
    const itemModel = getItemModel(name)
    genBlockstates(name, folder)

    fs.writeFileSync(`${folder}/models/block/${name}.json`, JSON.stringify(blockmodel, null, 2))
    fs.writeFileSync(`${folder}/models/item/${name}.json`, JSON.stringify(itemModel, null, 2))
}

function genBlockstates(name, path)
{
    const model = getBlock(name)

    fs.writeFileSync(`${path}/blockstates/${name}.json`, JSON.stringify(model, null, 2))
}

function getBlockStateModel(name)
{
    return {
        "multipart": [
            { "apply": {"model": `tfcompat:${name}_top`} },
            { "when": {"nw": "true"},
              "apply": {"model": `tfcompat:${name}_leg_nw`} },
            { "when": {"ne": "true"},
              "apply": {"model": `tfcompat:${name}_leg_nw`, "y": 90} },
            { "when": {"se": "true"},
              "apply": {"model": `tfcompat:${name}_leg_nw`, "y": 180} },
            { "when": {"sw": "true"},
              "apply": {"model": `tfcompat:${name}_leg_nw`, "y": 270} }
        ]
    }
}

function getTopModel(loc)
{
    return {
        "__comment": "Model generated using MrCrayfish's Model Creator (http://mrcrayfish.com/modelcreator/)",
        "textures": {
            "0": loc,
            "particle": loc
        },
        "elements": [
            {
                "name": "Cube",
                "from": [ 0.0, 14.0, 0.0 ], 
                "to": [ 16.0, 16.0, 16.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 16.0, 2.0 ] },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 16.0, 2.0 ] },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 16.0, 2.0 ] },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 16.0, 2.0 ] },
                    "up": { "texture": "#0", "uv": [ 0.0, 0.0, 16.0, 16.0 ] },
                    "down": { "texture": "#0", "uv": [ 0.0, 0.0, 16.0, 16.0 ] }
                }
            }
        ]
    }
}

function getLegModel(loc)
{
    return {
        "__comment": "Model generated using MrCrayfish's Model Creator (http://mrcrayfish.com/modelcreator/)",
        "textures": {
            "0": loc
        },
        "elements": [
            {
                "name": "Cube",
                "from": [ 0.0, 0.0, 0.0 ], 
                "to": [ 2.0, 14.0, 2.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "up": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "down": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] }
                }
            }
        ]
    }
}

function getInventoryModel(loc)
{
    return {
        "__comment": "Model generated using MrCrayfish's Model Creator (http://mrcrayfish.com/modelcreator/)",
        "textures": {
            "0": loc
        },
        "elements": [
            {
                "name": "Cube",
                "from": [ 0.0, 0.0, 0.0 ], 
                "to": [ 2.0, 14.0, 2.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "up": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "down": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] }
                }
            },
            {
                "name": "Cube",
                "from": [ 14.0, 0.0, 0.0 ], 
                "to": [ 16.0, 14.0, 2.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "up": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "down": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] }
                }
            },
            {
                "name": "Cube",
                "from": [ 14.0, 0.0, 14.0 ], 
                "to": [ 16.0, 14.0, 16.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "up": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "down": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] }
                }
            },
            {
                "name": "Cube",
                "from": [ 0.0, 0.0, 14.0 ], 
                "to": [ 2.0, 14.0, 16.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 14.0, 2.0 ], "rotation": 90 },
                    "up": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "down": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] }
                }
            },
            {
                "name": "Cube",
                "from": [ 0.0, 14.0, 0.0 ], 
                "to": [ 16.0, 16.0, 16.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 16.0, 2.0 ] },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 16.0, 2.0 ] },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 16.0, 2.0 ] },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 16.0, 2.0 ] },
                    "up": { "texture": "#0", "uv": [ 0.0, 0.0, 16.0, 16.0 ] },
                    "down": { "texture": "#0", "uv": [ 0.0, 0.0, 16.0, 16.0 ] }
                }
            }
        ],
        "display": {
    
            "gui": {
    
                "rotation": [ 30, 225, 0 ],
    
                "translation": [ 0, 0, 0],
    
                "scale":[ 0.625, 0.625, 0.625 ]
    
            },
            "ground": {
    
                "rotation": [ 0, 0, 0 ],
    
                "translation": [ 0, 3, 0],
    
                "scale":[ 0.25, 0.25, 0.25 ]
    
            },
    
            "fixed": {
    
                "rotation": [ 0, 0, 0 ],
    
                "translation": [ 0, 0, 0],
    
                "scale":[ 0.5, 0.5, 0.5 ]
    
            },
    
            "thirdperson_righthand": {
    
                "rotation": [ 75, 45, 0 ],
    
                "translation": [ 0, 2.5, 0],
    
                "scale": [ 0.375, 0.375, 0.375 ]
    
            },
    
            "firstperson_righthand": {
    
                "rotation": [ 0, 45, 0 ],
    
                "translation": [ 0, 0, 0 ],
    
                "scale": [ 0.40, 0.40, 0.40 ]
    
            },
    
            "firstperson_lefthand": {
    
                "rotation": [ 0, 225, 0 ],
    
                "translation": [ 0, 0, 0 ],
    
                "scale": [ 0.40, 0.40, 0.40 ]
    
            }
    
        }
    }
}

function getBlock(name)
{
    return {
        "variants": {
            "facing=north":	{ "model": `tfcompat:${name}` },
            "facing=east":	{ "model": `tfcompat:${name}`, "y": 90},
            "facing=south":	{ "model": `tfcompat:${name}`, "y": 180 },
            "facing=west":	{ "model": `tfcompat:${name}`, "y": 270 }
        }
    }
}

function getItemModel(name) {
    return {	"parent": `tfcompat:block/${name}`	}
}
function getTableItemModel(name) {
    return {	"parent": `tfcompat:block/${name}_inventory`	}
}

function getBlockModel(woodLoc)
{
    return {
        "__comment": "Model generated using MrCrayfish's Model Creator (http://mrcrayfish.com/modelcreator/)",
        "textures": {
            "0": woodLoc,
            "particle": woodLoc
        },
        "elements": [
            {
                "name": "Cube",
                "from": [ 2.0, 0.0, 2.0 ], 
                "to": [ 4.0, 8.0, 4.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "up": { "texture": "#-1", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "down": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] }
                }
            },
            {
                "name": "Cube",
                "from": [ 12.0, 0.0, 2.0 ], 
                "to": [ 14.0, 8.0, 4.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "up": { "texture": "#-1", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "down": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] }
                }
            },
            {
                "name": "Cube",
                "from": [ 12.0, 0.0, 12.0 ], 
                "to": [ 14.0, 8.0, 14.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "up": { "texture": "#-1", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "down": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] }
                }
            },
            {
                "name": "Cube",
                "from": [ 2.0, 0.0, 12.0 ], 
                "to": [ 4.0, 8.0, 14.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 8.0, 2.0 ], "rotation": 90 },
                    "up": { "texture": "#-1", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "down": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] }
                }
            },
            {
                "name": "Cube",
                "from": [ 2.0, 8.0, 2.0 ], 
                "to": [ 14.0, 10.0, 14.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 12.0, 2.0 ] },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 12.0, 2.0 ] },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 12.0, 2.0 ] },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 12.0, 2.0 ] },
                    "up": { "texture": "#0", "uv": [ 0.0, 3.0, 12.0, 15.0 ] },
                    "down": { "texture": "#0", "uv": [ 0.0, 3.0, 12.0, 15.0 ] }
                }
            },
            {
                "name": "Cube",
                "from": [ 2.0, 10.0, 12.0 ], 
                "to": [ 4.0, 16.0, 14.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 6.0, 2.0 ], "rotation": 90 },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 6.0, 2.0 ], "rotation": 90 },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 6.0, 2.0 ], "rotation": 90 },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 6.0, 2.0 ], "rotation": 90 },
                    "up": { "texture": "#-1", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "down": { "texture": "#-1", "uv": [ 0.0, 0.0, 2.0, 2.0 ] }
                }
            },
            {
                "name": "Cube",
                "from": [ 7.0, 10.0, 12.0 ], 
                "to": [ 9.0, 16.0, 14.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 6.0, 2.0 ], "rotation": 90 },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 6.0, 2.0 ], "rotation": 90 },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 6.0, 2.0 ], "rotation": 90 },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 6.0, 2.0 ], "rotation": 90 },
                    "up": { "texture": "#-1", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "down": { "texture": "#-1", "uv": [ 0.0, 0.0, 2.0, 2.0 ] }
                }
            },
            {
                "name": "Cube",
                "from": [ 12.0, 10.0, 12.0 ], 
                "to": [ 14.0, 16.0, 14.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 6.0, 2.0 ], "rotation": 90 },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 6.0, 2.0 ], "rotation": 90 },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 6.0, 2.0 ], "rotation": 90 },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 6.0, 2.0 ], "rotation": 90 },
                    "up": { "texture": "#-1", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "down": { "texture": "#-1", "uv": [ 0.0, 0.0, 2.0, 2.0 ] }
                }
            },
            {
                "name": "Cube",
                "from": [ 2.0, 16.0, 12.0 ], 
                "to": [ 14.0, 18.0, 14.0 ], 
                "faces": {
                    "north": { "texture": "#0", "uv": [ 0.0, 0.0, 12.0, 2.0 ] },
                    "east": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "south": { "texture": "#0", "uv": [ 0.0, 0.0, 12.0, 2.0 ] },
                    "west": { "texture": "#0", "uv": [ 0.0, 0.0, 2.0, 2.0 ] },
                    "up": { "texture": "#0", "uv": [ 0.0, 0.0, 12.0, 2.0 ] },
                    "down": { "texture": "#0", "uv": [ 0.0, 0.0, 12.0, 2.0 ] }
                }
            }
        ],
        "display": {
    
            "gui": {
    
                "rotation": [ 30, 225, 0 ],
    
                "translation": [ 0, 0, 0],
    
                "scale":[ 0.625, 0.625, 0.625 ]
    
            },
            "ground": {
    
                "rotation": [ 0, 0, 0 ],
    
                "translation": [ 0, 3, 0],
    
                "scale":[ 0.25, 0.25, 0.25 ]
    
            },
    
            "fixed": {
    
                "rotation": [ 0, 0, 0 ],
    
                "translation": [ 0, 0, 0],
    
                "scale":[ 0.5, 0.5, 0.5 ]
    
            },
    
            "thirdperson_righthand": {
    
                "rotation": [ 75, 45, 0 ],
    
                "translation": [ 0, 2.5, 0],
    
                "scale": [ 0.375, 0.375, 0.375 ]
    
            },
    
            "firstperson_righthand": {
    
                "rotation": [ 0, 45, 0 ],
    
                "translation": [ 0, 0, 0 ],
    
                "scale": [ 0.40, 0.40, 0.40 ]
    
            },
    
            "firstperson_lefthand": {
    
                "rotation": [ 0, 225, 0 ],
    
                "translation": [ 0, 0, 0 ],
    
                "scale": [ 0.40, 0.40, 0.40 ]
    
            }
    
        }
    }
}