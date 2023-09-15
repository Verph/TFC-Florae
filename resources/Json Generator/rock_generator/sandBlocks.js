const fs = require('fs');
const fse = require('fs-extra')
const getDirName = require('path').dirname;
const mkdirp = require('mkdirp');

let ROCK_TYPES = {
  'granite': 'granite',
  'diorite': 'diorite',
  'gabbro': 'gabbro',
  'shale': 'shale',
  'claystone': 'claystone',
  'limestone': 'limestone',
  'conglomerate': 'conglomerate',
  'dolomite': 'dolomite',
  'chert': 'chert',
  'chalk': 'chalk',
  'rhyolite': 'rhyolite',
  'basalt': 'basalt',
  'andesite': 'andesite',
  'dacite': 'dacite',
  'quartzite': 'quartzite',
  'slate': 'slate',
  'phyllite': 'phyllite',
  'schist': 'schist',
  'gneiss': 'gneiss',
  'marble': 'marble',
  'cataclasite': 'cataclasite',
  'porphyry': 'porphyry',
  'red_granite': 'red_granite',
  'laterite': 'laterite',
  //'dripstone': 'dripstone',
  'breccia': 'breccia',
  'foidolite': 'foidolite',
  'peridotite': 'peridotite',
  'blaimorite': 'blaimorite',
  'boninite': 'boninite',
  'carbonatite': 'carbonatite',
  'mudstone': 'mudstone',
  'sandstone': 'sandstone',
  'siltstone': 'siltstone',
  'arkose': 'arkose',
  'jaspillite': 'jaspillite',
  'travertine': 'travertine',
  'wackestone': 'wackestone',
  'blackband_ironstone': 'blackband_ironstone',
  'blueschist': 'blueschist',
  'catlinite': 'catlinite',
  'greenschist': 'greenschist',
  'novaculite': 'novaculite',
  'soapstone': 'soapstone',
  'komatiite': 'komatiite',
  'mylonite': 'mylonite'
}

let ROCK_TYPES_TFCF = {
  'cataclasite': 'cataclasite',
  'porphyry': 'porphyry',
  'red_granite': 'red_granite',
  'laterite': 'laterite',
  //'dripstone': 'dripstone',
  'breccia': 'breccia',
  'foidolite': 'foidolite',
  'peridotite': 'peridotite',
  'blaimorite': 'blaimorite',
  'boninite': 'boninite',
  'carbonatite': 'carbonatite',
  'mudstone': 'mudstone',
  'sandstone': 'sandstone',
  'siltstone': 'siltstone',
  'arkose': 'arkose',
  'jaspillite': 'jaspillite',
  'travertine': 'travertine',
  'wackestone': 'wackestone',
  'blackband_ironstone': 'blackband_ironstone',
  'blueschist': 'blueschist',
  'catlinite': 'catlinite',
  'greenschist': 'greenschist',
  'novaculite': 'novaculite',
  'soapstone': 'soapstone',
  'komatiite': 'komatiite',
  'mylonite': 'mylonite'
}

let ROCK_NAMESPACES = {
  'granite': 'tfc',
  'diorite': 'tfc',
  'gabbro': 'tfc',
  'shale': 'tfc',
  'claystone': 'tfc',
  'limestone': 'tfc',
  'conglomerate': 'tfc',
  'dolomite': 'tfc',
  'chert': 'tfc',
  'chalk': 'tfc',
  'rhyolite': 'tfc',
  'basalt': 'tfc',
  'andesite': 'tfc',
  'dacite': 'tfc',
  'quartzite': 'tfc',
  'slate': 'tfc',
  'phyllite': 'tfc',
  'schist': 'tfc',
  'gneiss': 'tfc',
  'marble': 'tfc',
  'cataclasite': 'tfcflorae',
  'porphyry': 'tfcflorae',
  'red_granite': 'tfcflorae',
  'laterite': 'tfcflorae',
  //'dripstone': 'tfcflorae',
  'breccia': 'tfcflorae',
  'foidolite': 'tfcflorae',
  'peridotite': 'tfcflorae',
  'blaimorite': 'tfcflorae',
  'boninite': 'tfcflorae',
  'carbonatite': 'tfcflorae',
  'mudstone': 'tfcflorae',
  'sandstone': 'tfcflorae',
  'siltstone': 'tfcflorae',
  'arkose': 'tfcflorae',
  'jaspillite': 'tfcflorae',
  'travertine': 'tfcflorae',
  'wackestone': 'tfcflorae',
  'blackband_ironstone': 'tfcflorae',
  'blueschist': 'tfcflorae',
  'catlinite': 'tfcflorae',
  'greenschist': 'tfcflorae',
  'novaculite': 'tfcflorae',
  'soapstone': 'tfcflorae',
  'komatiite': 'tfcflorae',
  'mylonite': 'tfcflorae'
}

let sandColors = {
  'black': 'black',
  'blue': 'blue',
  'brown': 'brown',
  'gray': 'gray',
  'green': 'green',
  'light_green': 'light_green',
  'orange': 'orange',
  'pink': 'pink',
  'purple': 'purple',
  'red': 'red',
  'white': 'white',
  'yellow': 'yellow'
}

let allColors = {
  'black': 'black',
  'blue': 'blue',
  'brown': 'brown',
  'cyan': 'cyan',
  'gray': 'gray',
  'green': 'green',
  'light_blue': 'light_blue',
  'light_gray': 'light_gray',
  'light_green': 'light_green',
  'magenta': 'magenta',
  'orange': 'orange',
  'pink': 'pink',
  'purple': 'purple',
  'red': 'red',
  'white': 'white',
  'yellow': 'yellow'
}

let allColorsNotTFC = {
  'blue': 'blue',
  'cyan': 'cyan',
  'gray': 'gray',
  'light_blue': 'light_blue',
  'light_gray': 'light_gray',
  'light_green': 'light_green',
  'magenta': 'magenta',
  'orange': 'orange',
  'purple': 'purple'
}

let SAND_NAMESPACES = {
  'black': 'tfc',
  'blue': 'tfcflorae',
  'brown': 'tfc',
  'cyan': 'tfcflorae',
  'gray': 'tfcflorae',
  'green': 'tfc',
  'light_blue': 'tfcflorae',
  'light_gray': 'tfcflorae',
  'light_green': 'tfcflorae',
  'magenta': 'tfcflorae',
  'orange': 'tfcflorae',
  'pink': 'tfc',
  'purple': 'tfcflorae',
  'red': 'tfc',
  'white': 'tfc',
  'yellow': 'tfc'
}

let SAND_NAMESPACES_EXTRA = {
  'black': 'tfc:sand/',
  'blue': 'tfcflorae:sand/sand/',
  'brown': 'tfc:sand/',
  'cyan': 'tfcflorae:sand/sand/',
  'gray': 'tfcflorae:sand/sand/',
  'green': 'tfc:sand/',
  'light_blue': 'tfcflorae:sand/sand/',
  'light_gray': 'tfcflorae:sand/sand/',
  'light_green': 'tfcflorae:sand/sand/',
  'magenta': 'tfcflorae:sand/sand/',
  'orange': 'tfcflorae:sand/sand/',
  'pink': 'tfc:sand/',
  'purple': 'tfcflorae:sand/sand/',
  'red': 'tfc:sand/',
  'white': 'tfc:sand/',
  'yellow': 'tfc:sand/'
}

let SAND_NAMESPACES_EXTRA_MODEL = {
  'black': 'tfc:block/sand/',
  'blue': 'tfcflorae:block/sand/sand/',
  'brown': 'tfc:block/sand/',
  'cyan': 'tfcflorae:block/sand/sand/',
  'gray': 'tfcflorae:block/sand/sand/',
  'green': 'tfc:block/sand/',
  'light_blue': 'tfcflorae:block/sand/sand/',
  'light_gray': 'tfcflorae:block/sand/sand/',
  'light_green': 'tfcflorae:block/sand/sand/',
  'magenta': 'tfcflorae:block/sand/sand/',
  'orange': 'tfcflorae:block/sand/sand/',
  'pink': 'tfc:block/sand/',
  'purple': 'tfcflorae:block/sand/sand/',
  'red': 'tfc:block/sand/',
  'white': 'tfc:block/sand/',
  'yellow': 'tfc:block/sand/'
}

let numbers = {
  '2': '2',
  '4': '4',
  '6': '6',
  '8': '8',
  '10': '10',
  '12': '12',
  '14': '14'
}

let sandColorsTFC = {
  'black': 'black',
  'brown': 'brown',
  'green': 'green',
  'pink': 'pink',
  'red': 'red',
  'white': 'white',
  'yellow': 'yellow'
}

let decoTypes = {
  'slab': 'slab',
  'stairs': 'stairs',
  'wall': 'wall'
}

let sandyRockTileTypes = {
  'sandiest_tiles': 'sandiest_tiles',
  'sandier_tiles': 'sandier_tiles',
  'sandy_tiles': 'sandy_tiles'
}

let sandyRockTypes = {
  'pebble': 'pebble',
  'rocky': 'rocky',
  'rockier': 'rockier',
  'rockiest': 'rockiest'
}

let sandRockTypes = {
  'sandiest_tiles': 'sandiest_tiles',
  'sandier_tiles': 'sandier_tiles',
  'sandy_tiles': 'sandy_tiles',
  'pebble': 'pebble',
  'rocky': 'rocky',
  'rockier': 'rockier',
  'rockiest': 'rockiest'
}

let grassTypes = {
  'grass': 'grass',
  'dense_grass': 'dense_grass',
  'sparse_grass': 'sparse_grass'
}

let GRASS_NAMESPACES = {
  'grass': 'tfc',
  'dense_grass': 'tfcflorae',
  'sparse_grass': 'tfcflorae'
}

for(let rockType of Object.keys(ROCK_TYPES))
{
    generateJSON(rockType)
    console.log("generating for " + rockType.toString())
}

function generateJSON(rockType)
{
  // Sand Stuff
  for(let color in allColorsNotTFC)
  {
    let lootTableSand = {
      "type": "minecraft:block",
      "pools": [
        {
          "name": "loot_pool",
          "rolls": 1,
          "entries": [
            {
              "type": "minecraft:item",
              "name": `tfcflorae:sand/sand/${color}`
            }
          ],
          "conditions": [
            {
              "condition": "minecraft:survives_explosion"
            }
          ]
        }
      ]
    }
    fse.ensureDir(`./data/loot_tables/blocks/sand/sand`)
    fs.writeFileSync(`./data/loot_tables/blocks/sand/sand/${color}.json`, JSON.stringify(lootTableSand, null, 2))
  }

  for(let color in allColors)
  {
    let blockstateSandLayer = {
      "variants": {
          "layers=1":  { "model": `tfcflorae:block/sand/sand_layer/2/${color}` },
          "layers=2":  { "model": `tfcflorae:block/sand/sand_layer/4/${color}` },
          "layers=3":  { "model": `tfcflorae:block/sand/sand_layer/6/${color}` },
          "layers=4":  { "model": `tfcflorae:block/sand/sand_layer/8/${color}` },
          "layers=5":  { "model": `tfcflorae:block/sand/sand_layer/10/${color}` },
          "layers=6":  { "model": `tfcflorae:block/sand/sand_layer/12/${color}` },
          "layers=7":  { "model": `tfcflorae:block/sand/sand_layer/14/${color}` },
          "layers=8":  { "model": `${SAND_NAMESPACES_EXTRA_MODEL[color]}${color}` }
      }
    }
    fse.ensureDir(`./assets/blockstates/sand/sand_layer`)
    fs.writeFileSync(`./assets/blockstates/sand/sand_layer/${color}.json`, JSON.stringify(blockstateSandLayer, null, 2))

    for(let number in numbers)
    {
      let modelSandLayer = {
        "parent": `tfcflorae:block/sand/sand_height${number}`,
        "textures": {
          "sand": `${SAND_NAMESPACES[color]}:block/sand/${color}`
        }
      }
      fse.ensureDir(`./assets/models/block/sand/sand_layer/${number}`)
      fs.writeFileSync(`./assets/models/block/sand/sand_layer/${number}/${color}.json`, JSON.stringify(modelSandLayer, null, 2))
    }

    let modelItemSandLayer = {
      "parent": "item/generated",
      "textures": {
        "layer0": `tfcflorae:item/sand/sand_layer/${color}`
      }
    }
    fse.ensureDir(`./assets/models/item/sand/sand_layer`)
    fs.writeFileSync(`./assets/models/item/sand/sand_layer/${color}.json`, JSON.stringify(modelItemSandLayer, null, 2))

    let lootableSandLayer = {
      "type": "minecraft:block",
      "pools": [
        {
          "name": "loot_pool",
          "rolls": 1,
          "entries": [
            {
              "type": "minecraft:item",
              "name": `tfcflorae:sand/sand_layer/${color}`
            }
          ],
          "conditions": [
            {
              "condition": "minecraft:survives_explosion"
            }
          ]
        }
      ]
    }
    fse.ensureDir(`./data/loot_tables/blocks/sand/sand_layer`)
    fs.writeFileSync(`./data/loot_tables/blocks/sand/sand_layer/${color}.json`, JSON.stringify(lootableSandLayer, null, 2))

    let landslideSandLayer = {
      "type": "tfc:landslide",
      "ingredient": [
        `tfcflorae:sand/sand_layer/${color}`
      ],
      "result": `tfcflorae:sand/sand_layer/${color}`
    }
    fse.ensureDir(`./data/recipes/landslide/sand/sand_layer`)
    fs.writeFileSync(`./data/recipes/landslide/sand/sand_layer/${color}.json`, JSON.stringify(landslideSandLayer, null, 2))

    let craftingSandLayer = {
      "type": "minecraft:crafting_shapeless",
      "ingredients": [
        {
          "item": `tfcflorae:sand/sand_layer/${color}`
        },
        {
          "item": `tfcflorae:sand/sand_layer/${color}`
        },
        {
          "item": `tfcflorae:sand/sand_layer/${color}`
        },
        {
          "item": `tfcflorae:sand/sand_layer/${color}`
        },
        {
          "item": `tfcflorae:sand/sand_layer/${color}`
        },
        {
          "item": `tfcflorae:sand/sand_layer/${color}`
        },
        {
          "item": `tfcflorae:sand/sand_layer/${color}`
        },
        {
          "item": `tfcflorae:sand/sand_layer/${color}`
        }
      ],
      "result": {
        "item": `${SAND_NAMESPACES_EXTRA[color]}${color}`,
        "count": 1
      }
    }
    fse.ensureDir(`./data/recipes/crafting/sand/sand_layer`)
    fs.writeFileSync(`./data/recipes/crafting/sand/sand_layer/${color}.json`, JSON.stringify(craftingSandLayer, null, 2))

    let craftingSandPile = {
      "type": "minecraft:crafting_shapeless",
      "ingredients": [
        {
          "item": `${SAND_NAMESPACES_EXTRA[color]}${color}`
        }
      ],
      "result": {
        "item": `tfcflorae:sand/sand_layer/${color}`,
        "count": 8
      }
    }
    fse.ensureDir(`./data/recipes/crafting/sand/sand_pile`)
    fs.writeFileSync(`./data/recipes/crafting/sand/sand_pile/${color}.json`, JSON.stringify(craftingSandPile, null, 2))

    // Blockstates
    let blockstateWeatheredTerracotta = {
      "variants": {
        "": {
          "model": `tfcflorae:block/sand/weathered_terracotta/${color}`
        }
      }
    }
    fse.ensureDir(`./assets/blockstates/sand/weathered_terracotta`)
    fs.writeFileSync(`./assets/blockstates/sand/weathered_terracotta/${color}.json`, JSON.stringify(blockstateWeatheredTerracotta, null, 2))

    // Models
    let modelWeatheredTerracotta = {
      "parent": "block/cube_all",
      "textures": {
        "all": `tfcflorae:block/sand/weathered_terracotta/${color}`
      }
    }
    fse.ensureDir(`./assets/models/block/sand/weathered_terracotta`)
    fs.writeFileSync(`./assets/models/block/sand/weathered_terracotta/${color}.json`, JSON.stringify(modelWeatheredTerracotta, null, 2))


    // Models (Items)
    let modelItemWeatheredTerracotta = {
      "parent": `tfcflorae:block/sand/weathered_terracotta/${color}`
    }
    fse.ensureDir(`./assets/models/item/sand/weathered_terracotta`)
    fs.writeFileSync(`./assets/models/item/sand/weathered_terracotta/${color}.json`, JSON.stringify(modelItemWeatheredTerracotta, null, 2))

    let landslideWeatheredTerracotta = {
      "type": "tfc:landslide",
      "ingredient": [
        `tfcflorae:sand/weathered_terracotta/${color}`
      ],
      "result": `tfcflorae:sand/weathered_terracotta/${color}`
    }
    fse.ensureDir(`./data/recipes/landslide/sand/weathered_terracotta`)
    fs.writeFileSync(`./data/recipes/landslide/sand/weathered_terracotta/${color}.json`, JSON.stringify(landslideWeatheredTerracotta, null, 2))

    let collapseWeatheredTerracotta = {
      "type": "tfc:collapse",
      "ingredient": [
        `tfcflorae:sand/weathered_terracotta/${color}`
      ],
      "result": `tfcflorae:sand/weathered_terracotta/${color}`
    }
    fse.ensureDir(`./data/recipes/collapse/sand/weathered_terracotta`)
    fs.writeFileSync(`./data/recipes/collapse/sand/weathered_terracotta/${color}.json`, JSON.stringify(collapseWeatheredTerracotta, null, 2))

    let lootTableWeatheredTerracotta = {
      "type": "minecraft:block",
      "pools": [
        {
          "name": "loot_pool",
          "rolls": 1,
          "entries": [
            {
              "type": "minecraft:alternatives",
              "children": [
                {
                  "type": "minecraft:item",
                  "name": `tfcflorae:sand/weathered_terracotta_shard/${color}`,
                  "conditions": [
                    {
                      "condition": "tfc:is_isolated"
                    }
                  ]
                },
                {
                  "type": "minecraft:item",
                  "name": `tfcflorae:sand/weathered_terracotta_shard/${color}`,
                  "functions": [
                    {
                      "function": "minecraft:set_count",
                      "count": {
                        "min": 1,
                        "max": 4,
                        "type": "minecraft:uniform"
                      }
                    }
                  ]
                }
              ]
            }
          ],
          "conditions": [
            {
              "condition": "minecraft:survives_explosion"
            }
          ]
        }
      ]
    }
    fse.ensureDir(`./data/loot_tables/blocks/sand/weathered_terracotta`)
    fs.writeFileSync(`./data/loot_tables/blocks/sand/weathered_terracotta/${color}.json`, JSON.stringify(lootTableWeatheredTerracotta, null, 2))

    let craftingWeatheredTerracotta = {
      "type": "minecraft:crafting_shaped",
      "pattern": [
        "XX",
        "XX"
      ],
      "key": {
        "X": {
          "item": `tfcflorae:sand/weathered_terracotta_shard/${color}`
        }
      },
      "result": {
        "item": `minecraft:${color}_terracotta`
      }
    }
    fse.ensureDir(`./data/recipes/crafting/sand/weathered_terracotta`)
    fs.writeFileSync(`./data/recipes/crafting/sand/weathered_terracotta/${color}.json`, JSON.stringify(craftingWeatheredTerracotta, null, 2))

    let barrelWeatheredTerracotta = {
      "type": "tfc:barrel_sealed",
      "input_item": {
        "ingredient": {
          "item": `minecraft:${color}_terracotta`
        }
      },
      "input_fluid": {
        "ingredient": "minecraft:water",
        "amount": 250
      },
      "output_item": {
        "item": `tfcflorae:sand/weathered_terracotta/${color}`,
        "count": 1
      },
      "duration": 1000
    }
    fse.ensureDir(`./data/recipes/barrel/sand/weathered_terracotta`)
    fs.writeFileSync(`./data/recipes/barrel/sand/weathered_terracotta/${color}.json`, JSON.stringify(barrelWeatheredTerracotta, null, 2))
  }

  for(let sandColor in allColors)
  {
    for(let sandRockType in sandRockTypes)
    {
      for(let grassType in grassTypes)
      {
        let blockstateSandGrass = {
          "multipart": [
            {
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/bottom`,
                "x": 90
              }
            },
            {
              "when": {
                "snowy": false
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/top`,
                "x": 270
              }
            },
            {
              "when": {
                "snowy": true
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/snowy_top`,
                "x": 270
              }
            },
            {
              "when": {
                "north": true,
                "snowy": false
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/top`
              }
            },
            {
              "when": {
                "east": true,
                "snowy": false
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/top`,
                "y": 90
              }
            },
            {
              "when": {
                "south": true,
                "snowy": false
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/top`,
                "y": 180
              }
            },
            {
              "when": {
                "west": true,
                "snowy": false
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/top`,
                "y": 270
              }
            },
            {
              "when": {
                "north": true,
                "snowy": true
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/snowy_top`
              }
            },
            {
              "when": {
                "east": true,
                "snowy": true
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/snowy_top`,
                "y": 90
              }
            },
            {
              "when": {
                "south": true,
                "snowy": true
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/snowy_top`,
                "y": 180
              }
            },
            {
              "when": {
                "west": true,
                "snowy": true
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/snowy_top`,
                "y": 270
              }
            },
            {
              "when": {
                "north": false,
                "snowy": false
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/side`
              }
            },
            {
              "when": {
                "east": false,
                "snowy": false
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/side`,
                "y": 90
              }
            },
            {
              "when": {
                "south": false,
                "snowy": false
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/side`,
                "y": 180
              }
            },
            {
              "when": {
                "west": false,
                "snowy": false
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/side`,
                "y": 270
              }
            },
            {
              "when": {
                "north": false,
                "snowy": true
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/snowy_side`
              }
            },
            {
              "when": {
                "east": false,
                "snowy": true
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/snowy_side`,
                "y": 90
              }
            },
            {
              "when": {
                "south": false,
                "snowy": true
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/snowy_side`,
                "y": 180
              }
            },
            {
              "when": {
                "west": false,
                "snowy": true
              },
              "apply": {
                "model": `tfcflorae:block/sand/${grassType}/${sandColor}/snowy_side`,
                "y": 270
              }
            }
          ]
        }
        fse.ensureDir(`./assets/blockstates/sand/${grassType}`)
        fs.writeFileSync(`./assets/blockstates/sand/${grassType}/${sandColor}.json`, JSON.stringify(blockstateSandGrass, null, 2))

        // Models
        let modelBottomSandGrass = {
          "parent": `tfcflorae:block/${grassType}_bottom`,
          "textures": {
            "texture": `${SAND_NAMESPACES[sandColor]}:block/sand/${sandColor}`
          }
        }
        let modelSideSandGrass = {
          "parent": `tfcflorae:block/${grassType}_side`,
          "textures": {
            "texture": `${SAND_NAMESPACES[sandColor]}:block/sand/${sandColor}`
          }
        }
        let modelSideSnowySandGrass = {
          "parent": `tfcflorae:block/${grassType}_snowy_side`,
          "textures": {
            "texture": `${SAND_NAMESPACES[sandColor]}:block/sand/${sandColor}`
          }
        }
        let modelTopSnowySandGrass = {
          "parent": `tfcflorae:block/${grassType}_snowy_top`,
          "textures": {
            "texture": `${SAND_NAMESPACES[sandColor]}:block/sand/${sandColor}`
          }
        }
        let modelTopSandGrass = {
          "parent": `tfcflorae:block/${grassType}_grass_top`,
          "textures": {
            "texture": `${SAND_NAMESPACES[sandColor]}:block/sand/${sandColor}`
          }
        }
        fse.ensureDir(`./assets/models/block/sand/${grassType}/${sandColor}`)
        fs.writeFileSync(`./assets/models/block/sand/${grassType}/${sandColor}/bottom.json`, JSON.stringify(modelBottomSandGrass, null, 2))
        fs.writeFileSync(`./assets/models/block/sand/${grassType}/${sandColor}/side.json`, JSON.stringify(modelSideSandGrass, null, 2))
        fs.writeFileSync(`./assets/models/block/sand/${grassType}/${sandColor}/snowy_side.json`, JSON.stringify(modelSideSnowySandGrass, null, 2))
        fs.writeFileSync(`./assets/models/block/sand/${grassType}/${sandColor}/snowy_top.json`, JSON.stringify(modelTopSnowySandGrass, null, 2))
        fs.writeFileSync(`./assets/models/block/sand/${grassType}/${sandColor}/top.json`, JSON.stringify(modelTopSandGrass, null, 2))

        // Models (Items)
        let modelItemSandGrass = {
          "parent": `${GRASS_NAMESPACES[grassType]}:item/${grassType}_inv`,
          "textures": {
            "block": `${SAND_NAMESPACES[sandColor]}:block/sand/${sandColor}`
          }
        }
        fse.ensureDir(`./assets/models/item/sand/${grassType}`)
        fs.writeFileSync(`./assets/models/item/sand/${grassType}/${sandColor}.json`, JSON.stringify(modelItemSandGrass, null, 2))

        // Loot tables for sand blocks
        let lootTableSandGrass = {
          "type": "minecraft:block",
          "pools": [
            {
              "name": "loot_pool",
              "rolls": 1,
              "entries": [
                {
                  "type": "minecraft:item",
                  "name": `${SAND_NAMESPACES_EXTRA[sandColor]}${sandColor}`
                }
              ],
              "conditions": [
                {
                  "condition": "minecraft:survives_explosion"
                }
              ]
            }
          ]
        }
        fse.ensureDir(`./data/loot_tables/blocks/sand/${grassType}`)
        fs.writeFileSync(`./data/loot_tables/blocks/sand/${grassType}/${sandColor}.json`, JSON.stringify(lootTableSandGrass, null, 2))
      }

      // Rocky sand stuff

      let blockstateSandyRock = {
        "variants": {
          "": {
            "model": `tfcflorae:block/${sandRockType}/${sandColor}/${rockType}`
          }
        }
      }
      fse.ensureDir(`./assets/blockstates/sand/${sandRockType}/${sandColor}`)
      fs.writeFileSync(`./assets/blockstates/sand/${sandRockType}/${sandColor}/${rockType}.json`, JSON.stringify(blockstateSandyRock, null, 2))

      // Models
      let modelSandyRock = {
        "parent": "block/cube_all",
        "textures": {
          "all": `tfcflorae:block/sand/${sandRockType}/${sandColor}/${rockType}`
        }
      }
      fse.ensureDir(`./assets/models/block/sand/${sandRockType}/${sandColor}`)
      fs.writeFileSync(`./assets/models/block/sand/${sandRockType}/${sandColor}/${rockType}.json`, JSON.stringify(modelSandyRock, null, 2))

      // Models (Items)
      let modelItemSandyRock = {
        "parent": `tfcflorae:block/sand/${sandRockType}/${sandColor}/${rockType}`
      }
      fse.ensureDir(`./assets/models/item/sand/${sandRockType}/${sandColor}`)
      fs.writeFileSync(`./assets/models/item/sand/${sandRockType}/${sandColor}/${rockType}.json`, JSON.stringify(modelItemSandyRock, null, 2))

      // Loot tables for sand blocks
      let lootTableSandyRock = {
        "type": "minecraft:block",
        "pools": [
          {
            "name": "loot_pool",
            "rolls": 1,
            "entries": [
              {
                "type": "minecraft:item",
                "name": `tfcflorae:sand/${sandRockType}/${sandColor}/${rockType}`
              }
            ],
            "conditions": [
              {
                "condition": "minecraft:survives_explosion"
              }
            ]
          }
        ]
      }
      fse.ensureDir(`./data/loot_tables/blocks/sand/${sandRockType}/${sandColor}`)
      fs.writeFileSync(`./data/loot_tables/blocks/sand/${sandRockType}/${sandColor}/${rockType}.json`, JSON.stringify(lootTableSandyRock, null, 2))

      let brushingSandyStoneTiles = {
        "type": "tfcflorae:brushing",
        "ingredient": `tfcflorae:sand/sandy_tiles/${sandColor}/${rockType}`,
        "result": `tfcflorae:rock/stone_tiles/${rockType}`,
        "extra_drop": {
          "item": `tfcflorae:sand_pile/${sandColor}`
        }
      }
      let brushingSandierStoneTiles = {
        "type": "tfcflorae:brushing",
        "ingredient": `tfcflorae:sand/sandier_tiles/${sandColor}/${rockType}`,
        "result": `tfcflorae:sand/sandy_tiles/${sandColor}/${rockType}`,
        "extra_drop": {
          "item": `tfcflorae:sand_pile/${sandColor}`
        }
      }
      let brushingSandiestStoneTiles = {
        "type": "tfcflorae:brushing",
        "ingredient": `tfcflorae:sand/sandiest_tiles/${sandColor}/${rockType}`,
        "result": `tfcflorae:sand/sandier_tiles/${sandColor}/${rockType}`,
        "extra_drop": {
          "item": `tfcflorae:sand_pile/${sandColor}`
        }
      }
      fse.ensureDir(`./data/recipes/brushing/sand/sandy_tiles/${sandColor}`)
      fse.ensureDir(`./data/recipes/brushing/sand/sandier_tiles/${sandColor}`)
      fse.ensureDir(`./data/recipes/brushing/sand/sandiest_tiles/${sandColor}`)
      fs.writeFileSync(`./data/recipes/brushing/sand/sandy_tiles/${sandColor}/${rockType}.json`, JSON.stringify(brushingSandyStoneTiles, null, 2))
      fs.writeFileSync(`./data/recipes/brushing/sand/sandier_tiles/${sandColor}/${rockType}.json`, JSON.stringify(brushingSandierStoneTiles, null, 2))
      fs.writeFileSync(`./data/recipes/brushing/sand/sandiest_tiles/${sandColor}/${rockType}.json`, JSON.stringify(brushingSandiestStoneTiles, null, 2))

      for (let sandyRockType in sandyRockTypes)
      {
        let landslideSandySoil = {
          "type": "tfc:landslide",
          "ingredient": [
            `tfcflorae:sand/${sandyRockType}/${sandColor}/${rockType}`
          ],
          "result": `tfcflorae:sand/${sandyRockType}/${sandColor}/${rockType}`
        }
        fse.ensureDir(`./data/recipes/landslide/sand/${sandyRockType}/${sandColor}`)
        fs.writeFileSync(`./data/recipes/landslide/sand/${sandyRockType}/${sandColor}/${rockType}.json`, JSON.stringify(landslideSandySoil, null, 2))
      }
      for (let sandyRockTileType in sandyRockTileTypes)
      {
        let collapseStoneTilesSandy = {
          "type": "tfc:collapse",
          "ingredient": [
            `tfcflorae:sand/${sandyRockTileType}/${sandColor}/${rockType}`
          ],
          "result": `tfcflorae:sand/${sandyRockTileType}/${sandColor}/${rockType}`
        }
        fse.ensureDir(`./data/recipes/collapse/sand/${sandyRockTileType}/${sandColor}`)
        fs.writeFileSync(`./data/recipes/collapse/sand/${sandyRockTileType}/${sandColor}/${rockType}.json`, JSON.stringify(collapseStoneTilesSandy, null, 2))
      }

      for (let grassType in grassTypes)
      {
        let landslideSandySoilGrass = {
          "type": "tfc:landslide",
          "ingredient": [
            `tfcflorae:sand/${grassType}/${sandColor}`
          ],
          "result": `${SAND_NAMESPACES_EXTRA[sandColor]}${sandColor}`
        }
        fse.ensureDir(`./data/recipes/landslide/sand/${grassType}`)
        fs.writeFileSync(`./data/recipes/landslide/sand/${grassType}/${sandColor}.json`, JSON.stringify(landslideSandySoilGrass, null, 2))

        let collapseStoneTilesSandyGrass = {
          "type": "tfc:collapse",
          "ingredient": [
            `tfcflorae:sand/${grassType}/${sandColor}`
          ],
          "result": `${SAND_NAMESPACES_EXTRA[sandColor]}${sandColor}`
        }
        fse.ensureDir(`./data/recipes/collapse/sand/${grassType}`)
        fs.writeFileSync(`./data/recipes/collapse/sand/${grassType}/${sandColor}.json`, JSON.stringify(collapseStoneTilesSandyGrass, null, 2))
      }
    }
  }
}
