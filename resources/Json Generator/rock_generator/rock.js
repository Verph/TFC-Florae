const fs = require('fs');
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

let sandstoneTypes = {
  'layered': 'layered'
}

const dirtTypes = [
  "humus",
  "loam",
  "sandy_loam",
  "silt",
  "silty_loam"
];

let decoTypes = {
  'slab': 'slab',
  'stairs': 'stairs',
  'wall': 'wall'
}

let dirtTypes2 = {
  'humus': 'humus',
  'loam': 'loam',
  'sandy_loam': 'sandy_loam',
  'silt': 'silt',
  'silty_loam': 'silty_loam'
}

let rockBlockTypes = {
  'stone_tiles': 'stone_tiles',
  'cobbled_bricks': 'cobbled_bricks',
  'flagstone_bricks': 'flagstone_bricks',
  'mossy_cobbled_bricks': 'mossy_cobbled_bricks',
  'mossy_flagstone_bricks': 'mossy_flagstone_bricks',
  'cracked_flagstone_bricks': 'cracked_flagstone_bricks'
}

let soilRockTypes2 = {
  'dirtiest_stone_tiles': 'dirtiest_stone_tiles',
  'dirtier_stone_tiles': 'dirtier_stone_tiles',
  'dirty_stone_tiles': 'dirty_stone_tiles'
}

let soilRockTypes = {
  'dirtiest_stone_tiles': 'dirtiest_stone_tiles',
  'dirtier_stone_tiles': 'dirtier_stone_tiles',
  'dirty_stone_tiles': 'dirty_stone_tiles',
  'pebble_compact_dirt': 'pebble_compact_dirt',
  'rocky_compact_dirt': 'rocky_compact_dirt',
  'rockier_compact_dirt': 'rockier_compact_dirt',
  'rockiest_compact_dirt': 'rockiest_compact_dirt'
}

let soilCompactRockTypes = {
  'pebble_compact_dirt': 'pebble_compact_dirt',
  'rocky_compact_dirt': 'rocky_compact_dirt',
  'rockier_compact_dirt': 'rockier_compact_dirt',
  'rockiest_compact_dirt': 'rockiest_compact_dirt'
}

let geyserModelTypes = {
  'down_base': 'down_base',
  'down_middle': 'down_middle',
  'down_frustum': 'down_frustum',
  'down_tip': 'down_tip',
  'down_tip_merge': 'down_tip_merge',
  'up_base': 'up_base',
  'up_middle': 'up_middle',
  'up_frustum': 'up_frustum',
  'up_tip': 'up_tip',
  'up_tip_merge': 'up_tip_merge'
}

let rockBoulderModels = {
  'rock_2': 'rock_2',
  'rock_2_ne': 'rock_2_ne',
  'rock_2_none': 'rock_2_none',
  'rock_2_nse': 'rock_2_nse',
  'rock_3': 'rock_3',
  'rock_3_n': 'rock_3_n',
  'rock_3_ne': 'rock_3_ne',
  'rock_3_none': 'rock_3_none',
  'rock_4': 'rock_4',
  'rock_5': 'rock_5',
  'rock_5_ne': 'rock_5_ne',
  'rock_5_none': 'rock_5_none',
  'rock_5_nse': 'rock_5_nse',
  'rock_6': 'rock_6',
  'rock_6_n': 'rock_6_n',
  'rock_6_ne': 'rock_6_ne',
  'rock_6_nse': 'rock_6_nse',
  'rock_7': 'rock_7',
  'rock_7_n': 'rock_7_n',
  'rock_7_ne': 'rock_7_ne',
  'rock_7_none': 'rock_7_none',
  'rock_7_ns': 'rock_7_ns',
  'rock_7_nse': 'rock_7_nse',
  'rock_8': 'rock_8',
  'rock_8_n': 'rock_8_n',
  'rock_8_ne': 'rock_8_ne',
  'rock_8_none': 'rock_8_none'
}

for(let rockType of Object.keys(ROCK_TYPES))
{
    generateJSON(rockType)
    console.log("generating")
}

for(let rockTypeTFCF of Object.keys(ROCK_TYPES_TFCF))
{
  generateJSON1(rockTypeTFCF)
    console.log("generating")
}

function createStairRecipe(blockIn, blockOut) {
  const recipe = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "X  ",
      "XX ",
      "XXX"
    ],
    "key": {
      "X": {
        "item": blockIn
      }
    },
    "result": {
      "item": blockOut,
      "count": 8
    }
  };

  return recipe;
}

function createSlabRecipe(blockIn, blockOut) {
  const recipe = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XXX"
    ],
    "key": {
      "X": {
        "item": blockIn
      }
    },
    "result": {
      "item": blockOut,
      "count": 6
    }
  };

  return recipe;
}

function createWallRecipe(blockIn, blockOut) {
  const recipe = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XXX",
      "XXX"
    ],
    "key": {
      "X": {
        "item": blockIn
      }
    },
    "result": {
      "item": blockOut,
      "count": 6
    }
  };

  return recipe;
}

function generateJSON1(rockTypeTFCF)
{
  let itemModelRockBrick = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcflorae:item/brick/rock/${rockTypeTFCF}`
    }
  }
  fs.writeFileSync(`./assets/models/item/brick/${rockTypeTFCF}.json`, JSON.stringify(itemModelRockBrick, null, 2))
}

function generateJSON(rockType)
{
  // Recipes
  let recipeAllSoilDirt = {};
  recipeAllSoilDirt["pebble_compact_dirt"] = {};
  recipeAllSoilDirt["rocky_compact_dirt"] = {};
  recipeAllSoilDirt["rockier_compact_dirt"] = {};
  recipeAllSoilDirt["rockiest_compact_dirt"] = {};

  const recipeSoulDirtKeys = Object.keys(recipeAllSoilDirt);

  for(let keyIndex in recipeSoulDirtKeys) {
    recipeAllSoilDirt[recipeSoulDirtKeys[keyIndex]].recipeSoilDirt = [];
    recipeAllSoilDirt[recipeSoulDirtKeys[keyIndex]].recipeSoilDirtSlab = [];
    recipeAllSoilDirt[recipeSoulDirtKeys[keyIndex]].recipeSoilDirtStairs = [];
    recipeAllSoilDirt[recipeSoulDirtKeys[keyIndex]].recipeSoilDirtWall = [];
  }

  let recipeAllSoilStoneTiles = {};
  recipeAllSoilStoneTiles["dirty_stone_tiles"] = {};
  recipeAllSoilStoneTiles["dirtier_stone_tiles"] = {};
  recipeAllSoilStoneTiles["dirtiest_stone_tiles"] = {};

  const recipeSoilStoneTileKeys = Object.keys(recipeAllSoilStoneTiles);

  for(let keyIndex in recipeSoilStoneTileKeys) {
    recipeAllSoilStoneTiles[recipeSoilStoneTileKeys[keyIndex]].recipeSoilDirt = [];
    recipeAllSoilStoneTiles[recipeSoilStoneTileKeys[keyIndex]].recipeSoilDirtSlab = [];
    recipeAllSoilStoneTiles[recipeSoilStoneTileKeys[keyIndex]].recipeSoilDirtStairs = [];
    recipeAllSoilStoneTiles[recipeSoilStoneTileKeys[keyIndex]].recipeSoilDirtWall = [];
  }

  let recipeAllRockStoneTiles = {};
  recipeAllRockStoneTiles.block = [];
  recipeAllRockStoneTiles.slab = [];
  recipeAllRockStoneTiles.stairs = [];
  recipeAllRockStoneTiles.wall = [];

  for(let i in dirtTypes) {
    // ROCK - STONE TILES
    recipeAllRockStoneTiles.block.push(
      {
        "type": "minecraft:crafting_shaped",
        "pattern": [
          "XX",
          "XX"
        ],
        "key": {
          "X": {
            "item": `${ROCK_NAMESPACES[rockType]}:rock/chiseled/${rockType}`
          }
        },
        "result": {
          "item": `tfcflorae:rock/stone_tiles/${rockType}`,
          "count": 4
        }
      }
    );

    recipeAllRockStoneTiles.slab.push(
      createSlabRecipe(`tfcflorae:rock/stone_tiles/${rockType}`, `tfcflorae:rock/stone_tiles/slab/${rockType}`)
    );

    recipeAllRockStoneTiles.stairs.push(
      createStairRecipe(`tfcflorae:rock/stone_tiles/${rockType}`, `tfcflorae:rock/stone_tiles/stairs/${rockType}`)
    );

    recipeAllRockStoneTiles.wall.push(
      createWallRecipe(`tfcflorae:rock/stone_tiles/${rockType}`, `tfcflorae:rock/stone_tiles/wall/${rockType}`)
    );


    // PEBBLE
    recipeAllSoilDirt["pebble_compact_dirt"].recipeSoilDirt.push(
      {
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
          {
            "item": `tfcflorae:compact_dirt/${dirtTypes[i]}`
          },
          {
            "item": `${ROCK_NAMESPACES[rockType]}:rock/loose/${rockType}`
          }
        ],
        "result": {
          "item": `tfcflorae:pebble_compact_dirt/${dirtTypes[i]}/${rockType}`
        }
      }
    );

    recipeAllSoilDirt["pebble_compact_dirt"].recipeSoilDirtSlab.push(
      createSlabRecipe(`tfcflorae:pebble_compact_dirt/${dirtTypes[i]}/${rockType}`, `tfcflorae:pebble_compact_dirt/${dirtTypes[i]}/slab/${rockType}`)
    );

    recipeAllSoilDirt["pebble_compact_dirt"].recipeSoilDirtStairs.push(
      createStairRecipe(`tfcflorae:pebble_compact_dirt/${dirtTypes[i]}/${rockType}`, `tfcflorae:pebble_compact_dirt/${dirtTypes[i]}/stairs/${rockType}`)
    );

    recipeAllSoilDirt["pebble_compact_dirt"].recipeSoilDirtWall.push(
      createWallRecipe(`tfcflorae:pebble_compact_dirt/${dirtTypes[i]}/${rockType}`, `tfcflorae:pebble_compact_dirt/${dirtTypes[i]}/wall/${rockType}`)
    );


    // ROCKY
    recipeAllSoilDirt["rocky_compact_dirt"].recipeSoilDirt.push( 
      {
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
          {
            "item": `tfcflorae:compact_dirt/${dirtTypes[i]}`
          },
          {
            "item": `${ROCK_NAMESPACES[rockType]}:rock/loose/${rockType}`
          },
          {
            "item": `${ROCK_NAMESPACES[rockType]}:rock/loose/${rockType}`
          }
        ],
        "result": {
          "item": `tfcflorae:rocky_compact_dirt/${dirtTypes[i]}/${rockType}`
        }
      }
    );

    recipeAllSoilDirt["rocky_compact_dirt"].recipeSoilDirtSlab.push(
      createSlabRecipe(`tfcflorae:rocky_compact_dirt/${dirtTypes[i]}/${rockType}`, `tfcflorae:rocky_compact_dirt/${dirtTypes[i]}/slab/${rockType}`)
    );

    recipeAllSoilDirt["rocky_compact_dirt"].recipeSoilDirtStairs.push(
      createStairRecipe(`tfcflorae:rocky_compact_dirt/${dirtTypes[i]}/${rockType}`, `tfcflorae:rocky_compact_dirt/${dirtTypes[i]}/stairs/${rockType}`)
    );

    recipeAllSoilDirt["rocky_compact_dirt"].recipeSoilDirtWall.push(
      createWallRecipe(`tfcflorae:rocky_compact_dirt/${dirtTypes[i]}/${rockType}`, `tfcflorae:rocky_compact_dirt/${dirtTypes[i]}/wall/${rockType}`)
    );


    // ROCKIER
    recipeAllSoilDirt["rockier_compact_dirt"].recipeSoilDirt.push( 
      {
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
          {
            "item": `tfcflorae:compact_dirt/${dirtTypes[i]}`
          },
          {
            "item": `${ROCK_NAMESPACES[rockType]}:rock/loose/${rockType}`
          },
          {
            "item": `${ROCK_NAMESPACES[rockType]}:rock/loose/${rockType}`
          },
          {
            "item": `${ROCK_NAMESPACES[rockType]}:rock/loose/${rockType}`
          }
        ],
        "result": {
          "item": `tfcflorae:rockier_compact_dirt/${dirtTypes[i]}/${rockType}`
        }
      }
    );

    recipeAllSoilDirt["rockier_compact_dirt"].recipeSoilDirtSlab.push(
      createSlabRecipe(`tfcflorae:rockier_compact_dirt/${dirtTypes[i]}/${rockType}`, `tfcflorae:rockier_compact_dirt/${dirtTypes[i]}/slab/${rockType}`)
    );

    recipeAllSoilDirt["rockier_compact_dirt"].recipeSoilDirtStairs.push(
      createStairRecipe(`tfcflorae:rockier_compact_dirt/${dirtTypes[i]}/${rockType}`, `tfcflorae:rockier_compact_dirt/${dirtTypes[i]}/stairs/${rockType}`)
    );

    recipeAllSoilDirt["rockier_compact_dirt"].recipeSoilDirtWall.push(
      createWallRecipe(`tfcflorae:rockier_compact_dirt/${dirtTypes[i]}/${rockType}`, `tfcflorae:rockier_compact_dirt/${dirtTypes[i]}/wall/${rockType}`)
    );


    // ROCKIEST
    recipeAllSoilDirt["rockiest_compact_dirt"].recipeSoilDirt.push( 
      {
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
          {
            "item": `tfcflorae:compact_dirt/${dirtTypes[i]}`
          },
          {
            "item": `${ROCK_NAMESPACES[rockType]}:rock/loose/${rockType}`
          },
          {
            "item": `${ROCK_NAMESPACES[rockType]}:rock/loose/${rockType}`
          },
          {
            "item": `${ROCK_NAMESPACES[rockType]}:rock/loose/${rockType}`
          },
          {
            "item": `${ROCK_NAMESPACES[rockType]}:rock/loose/${rockType}`
          }
        ],
        "result": {
          "item": `tfcflorae:rockiest_compact_dirt/${dirtTypes[i]}/${rockType}`
        }
      }
    );

    recipeAllSoilDirt["rockiest_compact_dirt"].recipeSoilDirtSlab.push(
      createSlabRecipe(`tfcflorae:rockiest_compact_dirt/${dirtTypes[i]}/${rockType}`, `tfcflorae:rockiest_compact_dirt/${dirtTypes[i]}/slab/${rockType}`)
    );

    recipeAllSoilDirt["rockiest_compact_dirt"].recipeSoilDirtStairs.push(
      createStairRecipe(`tfcflorae:rockiest_compact_dirt/${dirtTypes[i]}/${rockType}`, `tfcflorae:rockiest_compact_dirt/${dirtTypes[i]}/stairs/${rockType}`)
    );

    recipeAllSoilDirt["rockiest_compact_dirt"].recipeSoilDirtWall.push(
      createWallRecipe(`tfcflorae:rockiest_compact_dirt/${dirtTypes[i]}/${rockType}`, `tfcflorae:rockiest_compact_dirt/${dirtTypes[i]}/wall/${rockType}`)
    );


    // DIRTY
    recipeAllSoilStoneTiles["dirty_stone_tiles"].recipeSoilDirt.push( 
      {
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
          {
            "item": `tfc:dirt/${dirtTypes[i]}`
          },
          {
            "item": `tfcflorae:rock/stone_tiles/${rockType}`
          }
        ],
        "result": {
          "item": `tfcflorae:dirty_stone_tiles/${dirtTypes[i]}/${rockType}`
        }
      }
    );

    recipeAllSoilStoneTiles["dirty_stone_tiles"].recipeSoilDirtSlab.push(
      createSlabRecipe(`tfcflorae:dirty_stone_tiles/${dirtTypes[i]}/${rockType}`, `tfcflorae:dirty_stone_tiles/${dirtTypes[i]}/slab/${rockType}`)
    );

    recipeAllSoilStoneTiles["dirty_stone_tiles"].recipeSoilDirtStairs.push(
      createStairRecipe(`tfcflorae:dirty_stone_tiles/${dirtTypes[i]}/${rockType}`, `tfcflorae:dirty_stone_tiles/${dirtTypes[i]}/stairs/${rockType}`)
    );

    recipeAllSoilStoneTiles["dirty_stone_tiles"].recipeSoilDirtWall.push(
      createWallRecipe(`tfcflorae:dirty_stone_tiles/${dirtTypes[i]}/${rockType}`, `tfcflorae:dirty_stone_tiles/${dirtTypes[i]}/wall/${rockType}`)
    );


    // DIRTIER
    recipeAllSoilStoneTiles["dirtier_stone_tiles"].recipeSoilDirt.push( 
      {
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
          {
            "item": `tfc:dirt/${dirtTypes[i]}`
          },
          {
            "item": `tfc:dirt/${dirtTypes[i]}`
          },
          {
            "item": `tfcflorae:rock/stone_tiles/${rockType}`
          }
        ],
        "result": {
          "item": `tfcflorae:dirtier_stone_tiles/${dirtTypes[i]}/${rockType}`
        }
      }
    );

    recipeAllSoilStoneTiles["dirtier_stone_tiles"].recipeSoilDirtSlab.push(
      createSlabRecipe(`tfcflorae:dirtier_stone_tiles/${dirtTypes[i]}/${rockType}`, `tfcflorae:dirtier_stone_tiles/${dirtTypes[i]}/slab/${rockType}`)
    );

    recipeAllSoilStoneTiles["dirtier_stone_tiles"].recipeSoilDirtStairs.push(
      createStairRecipe(`tfcflorae:dirtier_stone_tiles/${dirtTypes[i]}/${rockType}`, `tfcflorae:dirtier_stone_tiles/${dirtTypes[i]}/stairs/${rockType}`)
    );

    recipeAllSoilStoneTiles["dirtier_stone_tiles"].recipeSoilDirtWall.push(
      createWallRecipe(`tfcflorae:dirtier_stone_tiles/${dirtTypes[i]}/${rockType}`, `tfcflorae:dirtier_stone_tiles/${dirtTypes[i]}/wall/${rockType}`)
    );


    // DIRTIEST
    recipeAllSoilStoneTiles["dirtiest_stone_tiles"].recipeSoilDirt.push( 
      {
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
          {
            "item": `tfc:dirt/${dirtTypes[i]}`
          },
          {
            "item": `tfc:dirt/${dirtTypes[i]}`
          },
          {
            "item": `tfc:dirt/${dirtTypes[i]}`
          },
          {
            "item": `tfcflorae:rock/stone_tiles/${rockType}`
          }
        ],
        "result": {
          "item": `tfcflorae:dirtiest_stone_tiles/${dirtTypes[i]}/${rockType}`
        }
      }
    );

    recipeAllSoilStoneTiles["dirtiest_stone_tiles"].recipeSoilDirtSlab.push(
      createSlabRecipe(`tfcflorae:dirtiest_stone_tiles/${dirtTypes[i]}/${rockType}`, `tfcflorae:dirtiest_stone_tiles/${dirtTypes[i]}/slab/${rockType}`)
    );

    recipeAllSoilStoneTiles["dirtiest_stone_tiles"].recipeSoilDirtStairs.push(
      createStairRecipe(`tfcflorae:dirtiest_stone_tiles/${dirtTypes[i]}/${rockType}`, `tfcflorae:dirtiest_stone_tiles/${dirtTypes[i]}/stairs/${rockType}`)
    );

    recipeAllSoilStoneTiles["dirtiest_stone_tiles"].recipeSoilDirtWall.push(
      createWallRecipe(`tfcflorae:dirtiest_stone_tiles/${dirtTypes[i]}/${rockType}`, `tfcflorae:dirtiest_stone_tiles/${dirtTypes[i]}/wall/${rockType}`)
    );
  }

  // Blockstates
  let cobbled_bricksJSON = {
    "variants": {
      "axis=x": {
        "model": `tfcflorae:block/rock/cobbled_bricks/${rockType}`,
        "x": 90,
        "y": 90
      },
      "axis=y": {
        "model": `tfcflorae:block/rock/cobbled_bricks/${rockType}`
      },
      "axis=z": {
        "model": `tfcflorae:block/rock/cobbled_bricks/${rockType}`,
        "x": 90
      }
    }
  }
  let cobbled_bricks_slabJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "variants": {
      "type=bottom": {	
        "model": `tfcflorae:block/rock/cobbled_bricks/slab/bottom/${rockType}`
      },
      "type=top": {
        "model": `tfcflorae:block/rock/cobbled_bricks/slab/top/${rockType}`
      },
      "type=double": {
        "model": `tfcflorae:block/rock/cobbled_bricks/${rockType}`
      }
    }
  }
  let cobbled_bricks_stairsJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "variants": {
      "facing=east,half=bottom,shape=straight": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/${rockType}`
      },
      "facing=west,half=bottom,shape=straight": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=straight": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=straight": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`
      },
      "facing=west,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=west,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`
      },
      "facing=north,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`
      },
      "facing=west,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=west,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`
      },
      "facing=north,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=straight": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=straight": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=straight": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=straight": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=top,shape=outer_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=west,half=top,shape=outer_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=south,half=top,shape=outer_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=north,half=top,shape=outer_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=outer_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=outer_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=outer_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=outer_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=top,shape=inner_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=west,half=top,shape=inner_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=south,half=top,shape=inner_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=north,half=top,shape=inner_right": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=inner_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=inner_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=inner_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=inner_left": {
        "model": `tfcflorae:block/rock/cobbled_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      }
    }
  }
  let cobbled_bricks_wallJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "multipart": [
      {
        "when": {
          "up": "true"
        },
        "apply": {
          "model": `tfcflorae:block/rock/cobbled_bricks/wall/post/${rockType}`
        }
      },
      {
        "when": {
          "north": "low"
        },
        "apply": {
          "model": `tfcflorae:block/rock/cobbled_bricks/wall/side/${rockType}`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "low"
        },
        "apply": {
          "model": `tfcflorae:block/rock/cobbled_bricks/wall/side/${rockType}`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "low"
        },
        "apply": {
          "model": `tfcflorae:block/rock/cobbled_bricks/wall/side/${rockType}`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "low"
        },
        "apply": {
          "model": `tfcflorae:block/rock/cobbled_bricks/wall/side/${rockType}`,
          "y": 270,
          "uvlock": true
        }
      },
      {
        "when": {
          "north": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/rock/cobbled_bricks/wall/side/tall/${rockType}`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/rock/cobbled_bricks/wall/side/tall/${rockType}`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/rock/cobbled_bricks/wall/side/tall/${rockType}`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/rock/cobbled_bricks/wall/side/tall/${rockType}`,
          "y": 270,
          "uvlock": true
        }
      }
    ]
  }

  let columnJSON = {
    "variants": {
      "axis=x": {
        "model": `tfcflorae:block/rock/column/${rockType}`,
        "x": 90,
        "y": 90
      },
      "axis=y": {
        "model": `tfcflorae:block/rock/column/${rockType}`
      },
      "axis=z": {
        "model": `tfcflorae:block/rock/column/${rockType}`,
        "x": 90
      }
    }
  }

  let flagstone_bricksJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "variants": {
      "": {
        "model": `tfcflorae:block/rock/flagstone_bricks/${rockType}`
      }
    }
  }
  let flagstone_bricks_slabJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "variants": {
      "type=bottom": {	
        "model": `tfcflorae:block/rock/flagstone_bricks/slab/bottom/${rockType}`
      },
      "type=top": {
        "model": `tfcflorae:block/rock/flagstone_bricks/slab/top/${rockType}`
      },
      "type=double": {
        "model": `tfcflorae:block/rock/flagstone_bricks/${rockType}`
      }
    }
  }
  let flagstone_bricks_stairsJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "variants": {
      "facing=east,half=bottom,shape=straight": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/${rockType}`
      },
      "facing=west,half=bottom,shape=straight": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=straight": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=straight": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`
      },
      "facing=west,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=west,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`
      },
      "facing=north,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`
      },
      "facing=west,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=west,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`
      },
      "facing=north,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=straight": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=straight": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=straight": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=straight": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=top,shape=outer_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=west,half=top,shape=outer_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=south,half=top,shape=outer_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=north,half=top,shape=outer_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=outer_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=outer_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=outer_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=outer_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/outer/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=top,shape=inner_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=west,half=top,shape=inner_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=south,half=top,shape=inner_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=north,half=top,shape=inner_right": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=inner_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=inner_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=inner_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=inner_left": {
        "model": `tfcflorae:block/rock/flagstone_bricks/stairs/inner/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      }
    }
  }
  let flagstone_bricks_wallJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "multipart": [
      {
        "when": {
          "up": "true"
        },
        "apply": {
          "model": `tfcflorae:block/rock/flagstone_bricks/wall/post/${rockType}`
        }
      },
      {
        "when": {
          "north": "low"
        },
        "apply": {
          "model": `tfcflorae:block/rock/flagstone_bricks/wall/side/${rockType}`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "low"
        },
        "apply": {
          "model": `tfcflorae:block/rock/flagstone_bricks/wall/side/${rockType}`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "low"
        },
        "apply": {
          "model": `tfcflorae:block/rock/flagstone_bricks/wall/side/${rockType}`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "low"
        },
        "apply": {
          "model": `tfcflorae:block/rock/flagstone_bricks/wall/side/${rockType}`,
          "y": 270,
          "uvlock": true
        }
      },
      {
        "when": {
          "north": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/rock/flagstone_bricks/wall/side/tall/${rockType}`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/rock/flagstone_bricks/wall/side/tall/${rockType}`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/rock/flagstone_bricks/wall/side/tall/${rockType}`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/rock/flagstone_bricks/wall/side/tall/${rockType}`,
          "y": 270,
          "uvlock": true
        }
      }
    ]
  }

  let dirtier_stone_tiles_humusJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "variants": {
      "": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`
      }
    }
  }
  let dirtier_stone_tiles_humus_slabJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "variants": {
      "type=bottom": {	
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/slab/bottom/${rockType}`
      },
      "type=top": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/slab/top/${rockType}`
      },
      "type=double": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`
      }
    }
  }
  let dirtier_stone_tiles_humus_stairsJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "variants": {
      "facing=east,half=bottom,shape=straight": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/${rockType}`
      },
      "facing=west,half=bottom,shape=straight": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=straight": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=straight": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`
      },
      "facing=west,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=west,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`
      },
      "facing=north,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`
      },
      "facing=west,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=west,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`
      },
      "facing=north,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=straight": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=straight": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=straight": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=straight": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=top,shape=outer_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=west,half=top,shape=outer_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=south,half=top,shape=outer_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=north,half=top,shape=outer_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=outer_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=outer_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=outer_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=outer_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/outer/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=top,shape=inner_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=west,half=top,shape=inner_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=south,half=top,shape=inner_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=north,half=top,shape=inner_right": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=inner_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=inner_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=inner_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=inner_left": {
        "model": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/inner/${rockType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      }
    }
  }
  let dirtier_stone_tiles_humus_wallJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "multipart": [
      {
        "when": {
          "up": "true"
        },
        "apply": {
          "model": `tfcflorae:block/dirtier_stone_tiles/humus/wall/post/${rockType}`
        }
      },
      {
        "when": {
          "north": "low"
        },
        "apply": {
          "model": `tfcflorae:block/dirtier_stone_tiles/humus/wall/side/${rockType}`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "low"
        },
        "apply": {
          "model": `tfcflorae:block/dirtier_stone_tiles/humus/wall/side/${rockType}`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "low"
        },
        "apply": {
          "model": `tfcflorae:block/dirtier_stone_tiles/humus/wall/side/${rockType}`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "low"
        },
        "apply": {
          "model": `tfcflorae:block/dirtier_stone_tiles/humus/wall/side/${rockType}`,
          "y": 270,
          "uvlock": true
        }
      },
      {
        "when": {
          "north": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/dirtier_stone_tiles/humus/wall/side/tall/${rockType}`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/dirtier_stone_tiles/humus/wall/side/tall/${rockType}`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/dirtier_stone_tiles/humus/wall/side/tall/${rockType}`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/dirtier_stone_tiles/humus/wall/side/tall/${rockType}`,
          "y": 270,
          "uvlock": true
        }
      }
    ]
  }

  // Models (Blocks)
  let model_dirtier_stone_tiles_humusJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/cube_all",
    "textures": {
      "all": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`
    }
  }
  let model_dirtier_stone_tiles_humus_slab_bottomJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/slab",
    "textures": {
      "bottom": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`,
      "top": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`,
      "side": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`
    }
  }
  let model_dirtier_stone_tiles_humus_slab_topJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/slab_top",
    "textures": {
      "bottom": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`,
      "top": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`,
      "side": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`
    }
  }
  let model_dirtier_stone_tiles_humus_stairsJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/stairs",
    "textures": {
      "bottom": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`,
      "top": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`,
      "side": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`
    }
  }
  let model_dirtier_stone_tiles_humus_stairs_innerJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/inner_stairs",
    "textures": {
      "bottom": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`,
      "top": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`,
      "side": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`
    }
  }
  let model_dirtier_stone_tiles_humus_stairs_outerJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/outer_stairs",
    "textures": {
      "bottom": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`,
      "top": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`,
      "side": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`
    }
  }
  let dirtier_stone_tiles_humus_wall_inventoryJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/wall_inventory",
    "textures": {
      "wall": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`
    }
  }
  let dirtier_stone_tiles_humus_wall_postJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/template_wall_post",
    "textures": {
      "wall": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`
    }
  }
  let dirtier_stone_tiles_humus_wall_sideJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/template_wall_side",
    "textures": {
      "wall": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`
    }
  }
  let dirtier_stone_tiles_humus_wall_side_tallJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/template_wall_side_tall",
    "textures": {
      "wall": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`
    }
  }

  let model_cobbled_bricksJSON = {
    "parent": "minecraft:block/cube_column",
    "textures": {
      "end": `tfcflorae:block/rock/cobbled_bricks/${rockType}_top`,
      "side": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`
    }
  }
  let model_cobbled_bricks_slab_bottomJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/slab",
    "textures": {
      "bottom": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`,
      "top": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`,
      "side": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`
    }
  }
  let model_cobbled_bricks_slab_topJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/slab_top",
    "textures": {
      "bottom": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`,
      "top": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`,
      "side": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`
    }
  }
  let model_cobbled_bricks_stairsJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/stairs",
    "textures": {
      "bottom": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`,
      "top": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`,
      "side": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`
    }
  }
  let model_cobbled_bricks_stairs_innerJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/inner_stairs",
    "textures": {
      "bottom": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`,
      "top": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`,
      "side": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`
    }
  }
  let model_cobbled_bricks_stairs_outerJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/outer_stairs",
    "textures": {
      "bottom": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`,
      "top": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`,
      "side": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`
    }
  }
  let model_cobbled_bricks_wall_inventoryJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/wall_inventory",
    "textures": {
      "wall": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`
    }
  }
  let model_cobbled_bricks_wall_postJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/template_wall_post",
    "textures": {
      "wall": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`
    }
  }
  let model_cobbled_bricks_wall_sideJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/template_wall_side",
    "textures": {
      "wall": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`
    }
  }
  let model_cobbled_bricks_wall_side_tallJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/template_wall_side_tall",
    "textures": {
      "wall": `tfcflorae:block/rock/cobbled_bricks/${rockType}_side`
    }
  }

  let model_columnJSON = {
    "parent": "minecraft:block/cube_column",
    "textures": {
      "end": `tfcflorae:block/rock/column/${rockType}_top`,
      "side": `tfcflorae:block/rock/column/${rockType}_side`
    }
  }

  let model_flagstone_bricksJSON = {
    "parent": "minecraft:block/cube_all",
    "textures": {
      "all": `tfcflorae:block/rock/flagstone_bricks/${rockType}`
    }
  }
  let model_flagstone_bricks_slab_bottomJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/slab",
    "textures": {
      "bottom": `tfcflorae:block/rock/flagstone_bricks/${rockType}`,
      "top": `tfcflorae:block/rock/flagstone_bricks/${rockType}`,
      "side": `tfcflorae:block/rock/flagstone_bricks/${rockType}`
    }
  }
  let model_flagstone_bricks_slab_topJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/slab_top",
    "textures": {
      "bottom": `tfcflorae:block/rock/flagstone_bricks/${rockType}`,
      "top": `tfcflorae:block/rock/flagstone_bricks/${rockType}`,
      "side": `tfcflorae:block/rock/flagstone_bricks/${rockType}`
    }
  }
  let model_flagstone_bricks_stairsJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/stairs",
    "textures": {
      "bottom": `tfcflorae:block/rock/flagstone_bricks/${rockType}`,
      "top": `tfcflorae:block/rock/flagstone_bricks/${rockType}`,
      "side": `tfcflorae:block/rock/flagstone_bricks/${rockType}`
    }
  }
  let model_flagstone_bricks_stairs_innerJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/inner_stairs",
    "textures": {
      "bottom": `tfcflorae:block/rock/flagstone_bricks/${rockType}`,
      "top": `tfcflorae:block/rock/flagstone_bricks/${rockType}`,
      "side": `tfcflorae:block/rock/flagstone_bricks/${rockType}`
    }
  }
  let model_flagstone_bricks_stairs_outerJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/outer_stairs",
    "textures": {
      "bottom": `tfcflorae:block/rock/flagstone_bricks/${rockType}`,
      "top": `tfcflorae:block/rock/flagstone_bricks/${rockType}`,
      "side": `tfcflorae:block/rock/flagstone_bricks/${rockType}`
    }
  }
  let model_flagstone_bricks_wall_inventoryJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/wall_inventory",
    "textures": {
      "wall": `tfcflorae:block/rock/flagstone_bricks/${rockType}`
    }
  }
  let model_flagstone_bricks_wall_postJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/template_wall_post",
    "textures": {
      "wall": `tfcflorae:block/rock/flagstone_bricks/${rockType}`
    }
  }
  let model_flagstone_bricks_wall_sideJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/template_wall_side",
    "textures": {
      "wall": `tfcflorae:block/rock/flagstone_bricks/${rockType}`
    }
  }
  let model_flagstone_bricks_wall_side_tallJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": "block/template_wall_side_tall",
    "textures": {
      "wall": `tfcflorae:block/rock/flagstone_bricks/${rockType}`
    }
  }

  // Models (Items)
  let item_model_dirtier_stone_tiles_humusJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/dirtier_stone_tiles/humus/${rockType}`
  }
  let item_model_dirtier_stone_tiles_humus_slabJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/dirtier_stone_tiles/humus/slab/bottom/${rockType}`
  }
  let item_model_dirtier_stone_tiles_humus_stairsJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/dirtier_stone_tiles/humus/stairs/${rockType}`
  }
  let item_model_dirtier_stone_tiles_humus_wallJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/dirtier_stone_tiles/humus/wall/inventory/${rockType}`
  }

  let item_model_columnJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/rock/column/${rockType}`
  }
  let item_model_polished_columnJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/rock/polished_column/${rockType}`
  }

  let item_model_cobbled_bricksJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/rock/cobbled_bricks/${rockType}`
  }
  let item_model_cobbled_bricks_slabJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/rock/cobbled_bricks/slab/bottom/${rockType}`
  }
  let item_model_cobbled_bricks_stairsJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/rock/cobbled_bricks/stairs/${rockType}`
  }
  let item_model_cobbled_bricks_wallJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/rock/cobbled_bricks/wall/inventory/${rockType}`
  }
  
  let bricks_slabJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "variants": {
      "type=bottom": {	
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_slab`
      },
      "type=top": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_slab_top`
      },
      "type=double": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}`
      }
    }
  }
  let bricks_stairsJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "variants": {
      "facing=east,half=bottom,shape=straight": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs`
      },
      "facing=west,half=bottom,shape=straight": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=straight": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=straight": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`
      },
      "facing=west,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "y": 270,
        "uvlock": true
      },
      "facing=west,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "y": 90,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`
      },
      "facing=north,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "y": 180,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`
      },
      "facing=west,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "y": 270,
        "uvlock": true
      },
      "facing=west,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "y": 90,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`
      },
      "facing=north,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "y": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=straight": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=straight": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=straight": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=straight": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=top,shape=outer_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=west,half=top,shape=outer_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=south,half=top,shape=outer_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=north,half=top,shape=outer_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "x": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=outer_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=outer_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=outer_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=outer_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_outer`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=top,shape=inner_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=west,half=top,shape=inner_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=south,half=top,shape=inner_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=north,half=top,shape=inner_right": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "x": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=inner_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=inner_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=inner_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=inner_left": {
        "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs_inner`,
        "x": 180,
        "y": 270,
        "uvlock": true
      }
    }
  }
  let bricks_wallJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "multipart": [
      {
        "when": {
          "up": "true"
        },
        "apply": {
          "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_wall_post`
        }
      },
      {
        "when": {
          "north": "low"
        },
        "apply": {
          "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_wall_side`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "low"
        },
        "apply": {
          "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_wall_side`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "low"
        },
        "apply": {
          "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_wall_side`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "low"
        },
        "apply": {
          "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_wall_side`,
          "y": 270,
          "uvlock": true
        }
      },
      {
        "when": {
          "north": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_wall_side_tall`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_wall_side_tall`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_wall_side_tall`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/rock/mossy_cobble/${rockType}_wall_side_tall`,
          "y": 270,
          "uvlock": true
        }
      }
    ]
  }
  let item_bricks_slabJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/rock/mossy_cobble/${rockType}_slab`
  }
  let item_bricks_stairsJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/rock/mossy_cobble/${rockType}_stairs`
  }
  let item_bricks_wallJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/rock/mossy_cobble/${rockType}_wall_inventory`
  }

  // Loot tables deposits
  let deposit_cassiterite = {
    "__comment__": "This file was automatically created by mcresources",
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
                "name": "tfc:ore/small_cassiterite",
                "conditions": [
                  {
                    "condition": "tfc:is_panned"
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.5
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:rock/loose/${rockType}`,
                "conditions": [
                  {
                    "condition": "tfc:is_panned"
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.5
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": "tfc:gem/pyrite",
                "conditions": [
                  {
                    "condition": "tfc:is_panned"
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.04
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:deposit/cassiterite/${rockType}`,
                "conditions": [
                  {
                    "condition": "minecraft:inverted",
                    "term": {
                      "condition": "tfc:is_panned"
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
  let deposit_native_copper = {
    "__comment__": "This file was automatically created by mcresources",
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
                "name": "tfc:ore/small_native_copper",
                "conditions": [
                  {
                    "condition": "tfc:is_panned"
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.5
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:rock/loose/${rockType}`,
                "conditions": [
                  {
                    "condition": "tfc:is_panned"
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.5
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": "tfc:gem/pyrite",
                "conditions": [
                  {
                    "condition": "tfc:is_panned"
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.04
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:deposit/native_copper/${rockType}`,
                "conditions": [
                  {
                    "condition": "minecraft:inverted",
                    "term": {
                      "condition": "tfc:is_panned"
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
  let deposit_native_gold = {
    "__comment__": "This file was automatically created by mcresources",
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
                "name": "tfc:ore/small_native_gold",
                "conditions": [
                  {
                    "condition": "tfc:is_panned"
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.5
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:rock/loose/${rockType}`,
                "conditions": [
                  {
                    "condition": "tfc:is_panned"
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.5
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": "tfc:gem/pyrite",
                "conditions": [
                  {
                    "condition": "tfc:is_panned"
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.04
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:deposit/native_gold/${rockType}`,
                "conditions": [
                  {
                    "condition": "minecraft:inverted",
                    "term": {
                      "condition": "tfc:is_panned"
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
  let deposit_native_silver = {
    "__comment__": "This file was automatically created by mcresources",
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
                "name": "tfc:ore/small_native_silver",
                "conditions": [
                  {
                    "condition": "tfc:is_panned"
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.5
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:rock/loose/${rockType}`,
                "conditions": [
                  {
                    "condition": "tfc:is_panned"
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.5
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": "tfc:gem/pyrite",
                "conditions": [
                  {
                    "condition": "tfc:is_panned"
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.04
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:deposit/native_silver/${rockType}`,
                "conditions": [
                  {
                    "condition": "minecraft:inverted",
                    "term": {
                      "condition": "tfc:is_panned"
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

  for (let rockBlockType in rockBlockTypes)
  {
    let lootTableRockBlockType = {
      "__comment__": "This file was automatically created by mcresources",
      "type": "minecraft:block",
      "pools": [
        {
          "name": "loot_pool",
          "rolls": 1,
          "entries": [
            {
              "type": "minecraft:item",
              "name": `tfcflorae:rock/${rockBlockType}/${rockType}`
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
    let lootTableRockBlockTypeSlab = {
      "__comment__": "This file was automatically created by mcresources",
      "type": "minecraft:block",
      "pools": [
        {
          "name": "loot_pool",
          "rolls": 1,
          "entries": [
            {
              "type": "minecraft:item",
              "name": `tfcflorae:rock/${rockBlockType}/slab/${rockType}`,
              "functions": [
                {
                  "function": "minecraft:set_count",
                  "conditions": [
                    {
                      "condition": "minecraft:block_state_property",
                      "block": `tfcflorae:rock/${rockBlockType}/slab/${rockType}`,
                      "properties": {
                        "type": "double"
                      }
                    }
                  ],
                  "count": 2,
                  "add": false
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
    let lootTableRockBlockTypeStairs = {
      "__comment__": "This file was automatically created by mcresources",
      "type": "minecraft:block",
      "pools": [
        {
          "name": "loot_pool",
          "rolls": 1,
          "entries": [
            {
              "type": "minecraft:item",
              "name": `tfcflorae:rock/${rockBlockType}/stairs/${rockType}`
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
    let lootTableRockBlockTypeWall = {
      "__comment__": "This file was automatically created by mcresources",
      "type": "minecraft:block",
      "pools": [
        {
          "name": "loot_pool",
          "rolls": 1,
          "entries": [
            {
              "type": "minecraft:item",
              "name": `tfcflorae:rock/${rockBlockType}/wall/${rockType}`
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
    fs.writeFileSync(`./data/loot_tables/blocks/rock/${rockBlockType}/${rockType}.json`, JSON.stringify(lootTableRockBlockType, null, 2))
    fs.writeFileSync(`./data/loot_tables/blocks/rock/${rockBlockType}/slab/${rockType}.json`, JSON.stringify(lootTableRockBlockTypeSlab, null, 2))
    fs.writeFileSync(`./data/loot_tables/blocks/rock/${rockBlockType}/stairs/${rockType}.json`, JSON.stringify(lootTableRockBlockTypeStairs, null, 2))
    fs.writeFileSync(`./data/loot_tables/blocks/rock/${rockBlockType}/wall/${rockType}.json`, JSON.stringify(lootTableRockBlockTypeWall, null, 2))

    let chiselRockBlockTypeStairs = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:rock/${rockBlockType}/${rockType}`,
      "result": `tfcflorae:rock/${rockBlockType}/stairs/${rockType}`,
      "mode": "stair"
    }
    let chiselRockBlockTypeSlab = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:rock/${rockBlockType}/${rockType}`,
      "result": `tfcflorae:rock/${rockBlockType}/slab/${rockType}`,
      "mode": "slab",
      "extra_drop": {
        "item": `tfcflorae:rock/${rockBlockType}/slab/${rockType}`
      }
    }
    fs.writeFileSync(`./data/recipes/chisel/stair/rock/${rockBlockType}/${rockType}.json`, JSON.stringify(chiselRockBlockTypeStairs, null, 2))
    fs.writeFileSync(`./data/recipes/chisel/slab/rock/${rockBlockType}/${rockType}.json`, JSON.stringify(chiselRockBlockTypeSlab, null, 2))

    let stonecuttingRockBlockTypeSlab = {
      "__comment__": "This file was automatically created by mcresources",
      "type": "minecraft:stonecutting",
      "ingredient": {
        "item": `tfcflorae:rock/${rockBlockType}/${rockType}`
      },
      "result": `tfcflorae:rock/${rockBlockType}/slab/${rockType}`,
      "count": 2
    }
    let stonecuttingRockBlockTypeStairs = {
      "__comment__": "This file was automatically created by mcresources",
      "type": "minecraft:stonecutting",
      "ingredient": {
        "item": `tfcflorae:rock/${rockBlockType}/${rockType}`
      },
      "result": `tfcflorae:rock/${rockBlockType}/stairs/${rockType}`,
      "count": 1
    }
    let stonecuttingRockBlockTypeWall = {
      "__comment__": "This file was automatically created by mcresources",
      "type": "minecraft:stonecutting",
      "ingredient": {
        "item": `tfcflorae:rock/${rockBlockType}/${rockType}`
      },
      "result": `tfcflorae:rock/${rockBlockType}/wall/${rockType}`,
      "count": 1
    }
    fs.writeFileSync(`./data/recipes/stonecutting/rock/${rockBlockType}/slab/${rockType}.json`, JSON.stringify(stonecuttingRockBlockTypeSlab, null, 2))
    fs.writeFileSync(`./data/recipes/stonecutting/rock/${rockBlockType}/stairs/${rockType}.json`, JSON.stringify(stonecuttingRockBlockTypeStairs, null, 2))
    fs.writeFileSync(`./data/recipes/stonecutting/rock/${rockBlockType}/wall/${rockType}.json`, JSON.stringify(stonecuttingRockBlockTypeWall, null, 2))

    let craftingChiseledColumn = {
      "type": "tfc:damage_inputs_shapeless_crafting",
      "recipe": {
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
          {
            "item": `tfcflorae:rock/column/${rockType}`
          },
          {
            "tag": "tfc:chisels"
          }
        ],
        "result": {
          "item": `tfcflorae:rock/polished_column/${rockType}`
        }
      }
    }
    let chiselChiseledColumnSmooth = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:rock/column/${rockType}`,
      "result": `tfcflorae:rock/polished_column/${rockType}`,
      "mode": "smooth"
    }
    fs.writeFileSync(`./data/recipes/crafting/rock/polished_column/${rockType}.json`, JSON.stringify(craftingChiseledColumn, null, 2))
    fs.writeFileSync(`./data/recipes/chisel/smooth/rock/polished_column/${rockType}.json`, JSON.stringify(chiselChiseledColumnSmooth, null, 2))

    let craftingRockChest = {
      "type": "minecraft:crafting_shaped",
      "pattern": [
        "YXY",
        "X X",
        "YXY"
      ],
      "key": {
        "X": {
          "item": `tfcflorae:rock/polished_cobbled_bricks/${rockType}`
        },
        "Y": {
          "tag": "tfc:pileable_sheets"
        }
      },
      "result": {
        "item": `tfcflorae:rock/chest/${rockType}`
      }
    }
    let craftingRockTrappedChest = {
      "type": "tfc:damage_inputs_shapeless_crafting",
      "recipe": {
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
          {
            "tag": "tfc:chisels"
          },
          {
            "item": `tfcflorae:rock/chest/${rockType}`
          },
          {
            "item": "minecraft:tripwire_hook"
          }
        ],
        "result": {
          "item": `tfcflorae:wood/trapped_chest/${rockType}`,
          "count": 1
        }
      }
    }
    let itemRockChest = {
      "parent": "item/chest",
      "textures": {
        "particle": `tfcflorae:block/rock/polished_cobbled_bricks/${rockType}`
      }
    }
    let itemRockTrappedChest = {
      "parent": "item/chest",
      "textures": {
        "particle": `tfcflorae:block/rock/polished_cobbled_bricks/${rockType}`
      }
    }
    let modelRockChest = {
      "textures": {
        "particle": `tfcflorae:block/rock/polished_cobbled_bricks/${rockType}`
      }
    }
    let modelRockTrappedChest = {
      "textures": {
        "particle": `tfcflorae:block/rock/polished_cobbled_bricks/${rockType}`
      }
    }
    let blockstateRockChest = {
      "variants": {
        "": {
          "model": `tfcflorae:block/rock/chest/${rockType}`
        }
      }
    }
    let blockstateRockTrappedChest = {
      "variants": {
        "": {
          "model": `tfcflorae:block/rock/trapped_chest/${rockType}`
        }
      }
    }
    fs.writeFileSync(`./data/recipes/crafting/rock/chest/${rockType}.json`, JSON.stringify(craftingRockChest, null, 2))
    fs.writeFileSync(`./data/recipes/crafting/rock/trapped_chest/${rockType}.json`, JSON.stringify(craftingRockTrappedChest, null, 2))
    fs.writeFileSync(`./assets/models/item/rock/chest/${rockType}.json`, JSON.stringify(itemRockChest, null, 2))
    fs.writeFileSync(`./assets/models/item/rock/trapped_chest/${rockType}.json`, JSON.stringify(itemRockTrappedChest, null, 2))
    fs.writeFileSync(`./assets/models/block/rock/chest/${rockType}.json`, JSON.stringify(modelRockChest, null, 2))
    fs.writeFileSync(`./assets/models/block/rock/trapped_chest/${rockType}.json`, JSON.stringify(modelRockTrappedChest, null, 2))
    fs.writeFileSync(`./assets/blockstates/rock/chest/${rockType}.json`, JSON.stringify(blockstateRockChest, null, 2))
    fs.writeFileSync(`./assets/blockstates/rock/trapped_chest/${rockType}.json`, JSON.stringify(blockstateRockTrappedChest, null, 2))

    let blockstateRockBoulderBlock = {
      "variants": {
        "": [
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2_ne`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2_ne`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2_ne`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2_ne`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2_none`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2_none`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2_none`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2_none`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2_nse`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2_nse`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2_nse`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_2_nse`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3_n`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3_n`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3_n`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3_n`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3_ne`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3_ne`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3_ne`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3_ne`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3_none`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3_none`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3_none`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_3_none`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_4`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_4`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_4`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_4`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5_ne`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5_ne`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5_ne`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5_ne`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5_none`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5_none`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5_none`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5_none`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5_nse`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5_nse`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5_nse`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_5_nse`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6_n`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6_n`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6_n`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6_n`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6_ne`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6_ne`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6_ne`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6_ne`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6_nse`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6_nse`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6_nse`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_6_nse`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_n`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_n`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_n`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_n`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_ne`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_ne`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_ne`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_ne`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_none`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_none`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_none`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_none`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_ns`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_ns`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_ns`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_ns`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_nse`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_nse`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_nse`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_7_nse`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8_n`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8_n`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8_n`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8_n`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8_ne`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8_ne`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8_ne`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8_ne`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8_none`
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8_none`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8_none`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/rock_pile/${rockType}/rock_8_none`,
            "y": 270
          }
        ]
      }
    }
    fs.writeFileSync(`./assets/blockstates/rock/rock_pile/${rockType}.json`, JSON.stringify(blockstateRockBoulderBlock, null, 2))

    let blockstateRockMossyBoulderBlock = {
      "variants": {
        "": [
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2_ne`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2_ne`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2_ne`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2_ne`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2_none`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2_none`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2_none`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2_none`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2_nse`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2_nse`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2_nse`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_2_nse`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3_n`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3_n`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3_n`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3_n`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3_ne`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3_ne`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3_ne`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3_ne`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3_none`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3_none`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3_none`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_3_none`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_4`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_4`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_4`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_4`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5_ne`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5_ne`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5_ne`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5_ne`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5_none`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5_none`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5_none`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5_none`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5_nse`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5_nse`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5_nse`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_5_nse`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6_n`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6_n`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6_n`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6_n`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6_ne`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6_ne`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6_ne`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6_ne`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6_nse`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6_nse`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6_nse`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_6_nse`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_n`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_n`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_n`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_n`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_ne`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_ne`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_ne`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_ne`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_none`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_none`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_none`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_none`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_ns`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_ns`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_ns`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_ns`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_nse`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_nse`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_nse`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_7_nse`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8_n`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8_n`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8_n`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8_n`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8_ne`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8_ne`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8_ne`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8_ne`,
            "y": 270
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8_none`
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8_none`,
            "y": 90
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8_none`,
            "y": 180
          },
          {
            "model": `tfcflorae:block/rock/mossy_rock_pile/${rockType}/rock_8_none`,
            "y": 270
          }
        ]
      }
    }
    fs.writeFileSync(`./assets/blockstates/rock/mossy_rock_pile/${rockType}.json`, JSON.stringify(blockstateRockMossyBoulderBlock, null, 2))

    let modelsRockBoulderItem = {
      "parent": "tfcflorae:block/rock/rock_pile/rock_item",
      "textures": {
        "particle": `${ROCK_NAMESPACES[rockType]}:block/rock/raw/${rockType}`,
        "texture": `${ROCK_NAMESPACES[rockType]}:block/rock/raw/${rockType}`
      }
    }
    let modelsRockMossyBoulderItem = {
      "parent": "tfcflorae:block/rock/rock_pile/rock_item",
      "textures": {
        "particle": `tfcflorae:block/rock/mossy_raw/${rockType}`,
        "texture": `tfcflorae:block/rock/mossy_raw/${rockType}`
      }
    }
    fs.writeFileSync(`./assets/models/item/rock/rock_pile/${rockType}.json`, JSON.stringify(modelsRockBoulderItem, null, 2))
    fs.writeFileSync(`./assets/models/item/rock/mossy_rock_pile/${rockType}.json`, JSON.stringify(modelsRockMossyBoulderItem, null, 2))

    for(let rockBoulderModel in rockBoulderModels)
    {
      let modelsRockBoulderBlock = {
        "parent": `tfcflorae:block/rock/rock_pile/${rockBoulderModel}`,
        "textures": {
          "particle": `${ROCK_NAMESPACES[rockType]}:block/rock/raw/${rockType}`,
          "texture": `${ROCK_NAMESPACES[rockType]}:block/rock/raw/${rockType}`
        }
      }
      let modelsRockMossyBoulderBlock = {
        "parent": `tfcflorae:block/rock/rock_pile/${rockBoulderModel}`,
        "textures": {
          "particle": `tfcflorae:block/rock/mossy_raw/${rockType}`,
          "texture": `tfcflorae:block/rock/mossy_raw/${rockType}`
        }
      }
      fs.writeFileSync(`./assets/models/block/rock/rock_pile/${rockType}/${rockBoulderModel}.json`, JSON.stringify(modelsRockBoulderBlock, null, 2))
      fs.writeFileSync(`./assets/models/block/rock/mossy_rock_pile/${rockType}/${rockBoulderModel}.json`, JSON.stringify(modelsRockMossyBoulderBlock, null, 2))
    }

    let lootTableRockBoulderBlock = {
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
                  "name": `tfcflorae:rock/rock_pile/${rockType}`,
                  "conditions": [
                    {
                      "condition": "minecraft:match_tool",
                      "predicate": {
                        "enchantments": [
                          {
                            "enchantment": "minecraft:silk_touch",
                            "levels": {
                              "min": 1
                            }
                          }
                        ]
                      }
                    }
                  ]
                },
                {
                  "type": "minecraft:item",
                  "name": `${ROCK_NAMESPACES[rockType]}:rock/loose/${rockType}`,
                  "functions": [
                    {
                      "function": "minecraft:set_count",
                      "count": {
                        "type": "minecraft:uniform",
                        "min": 1,
                        "max": 4
                      }
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
      ]
    }
    let lootTableRockMossyBoulderBlock = {
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
                  "name": `tfcflorae:rock/mossy_rock_pile/${rockType}`,
                  "conditions": [
                    {
                      "condition": "minecraft:match_tool",
                      "predicate": {
                        "enchantments": [
                          {
                            "enchantment": "minecraft:silk_touch",
                            "levels": {
                              "min": 1
                            }
                          }
                        ]
                      }
                    }
                  ]
                },
                {
                  "type": "minecraft:item",
                  "name": `${ROCK_NAMESPACES[rockType]}:rock/loose/${rockType}`,
                  "functions": [
                    {
                      "function": "minecraft:set_count",
                      "count": {
                        "type": "minecraft:uniform",
                        "min": 1,
                        "max": 4
                      }
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
      ]
    }
    fs.writeFileSync(`./loot_tables/blocks/rock/rock_pile/${rockType}.json`, JSON.stringify(lootTableRockBoulderBlock, null, 2))
    fs.writeFileSync(`./loot_tables/blocks/rock/mossy_rock_pile/${rockType}.json`, JSON.stringify(lootTableRockMossyBoulderBlock, null, 2))
  }

  for(let soilType in dirtTypes2)
  {
    for(let soilrockblocks in soilRockTypes)
    {
      // Loot tables for soil stone block things
      let lootTableSoilRockTypes = {
        "__comment__": "This file was automatically created by mcresources",
        "type": "minecraft:block",
        "pools": [
          {
            "name": "loot_pool",
            "rolls": 1,
            "entries": [
              {
                "type": "minecraft:item",
                "name": `tfcflorae:${soilrockblocks}/${soilType}/${rockType}`
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
      let lootTableSoilRockTypesSlab = {
        "__comment__": "This file was automatically created by mcresources",
        "type": "minecraft:block",
        "pools": [
          {
            "name": "loot_pool",
            "rolls": 1,
            "entries": [
              {
                "type": "minecraft:item",
                "name": `tfcflorae:${soilrockblocks}/${soilType}/slab/${rockType}`,
                "functions": [
                  {
                    "function": "minecraft:set_count",
                    "conditions": [
                      {
                        "condition": "minecraft:block_state_property",
                        "block": `tfcflorae:${soilrockblocks}/${soilType}/slab/${rockType}`,
                        "properties": {
                          "type": "double"
                        }
                      }
                    ],
                    "count": 2,
                    "add": false
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
      let lootTableSoilRockTypesStairs = {
        "__comment__": "This file was automatically created by mcresources",
        "type": "minecraft:block",
        "pools": [
          {
            "name": "loot_pool",
            "rolls": 1,
            "entries": [
              {
                "type": "minecraft:item",
                "name": `tfcflorae:${soilrockblocks}/${soilType}/stairs/${rockType}`
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
      let lootTableSoilRockTypesWall = {
        "__comment__": "This file was automatically created by mcresources",
        "type": "minecraft:block",
        "pools": [
          {
            "name": "loot_pool",
            "rolls": 1,
            "entries": [
              {
                "type": "minecraft:item",
                "name": `tfcflorae:${soilrockblocks}/${soilType}/wall/${rockType}`
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
      fs.writeFileSync(`./data/loot_tables/blocks/soil/${soilrockblocks}/${soilType}/${rockType}.json`, JSON.stringify(lootTableSoilRockTypes, null, 2))
      fs.writeFileSync(`./data/loot_tables/blocks/soil/${soilrockblocks}/${soilType}/slab/${rockType}.json`, JSON.stringify(lootTableSoilRockTypesSlab, null, 2))
      fs.writeFileSync(`./data/loot_tables/blocks/soil/${soilrockblocks}/${soilType}/stairs/${rockType}.json`, JSON.stringify(lootTableSoilRockTypesStairs, null, 2))
      fs.writeFileSync(`./data/loot_tables/blocks/soil/${soilrockblocks}/${soilType}/wall/${rockType}.json`, JSON.stringify(lootTableSoilRockTypesWall, null, 2))
    }
    let brushingDirtyStoneTiles = {
      "type": "tfcflorae:brushing",
      "ingredient": `tfcflorae:dirty_stone_tiles/${soilType}/${rockType}`,
      "result": `tfcflorae:rock/stone_tiles/${rockType}`,
      "extra_drop": {
        "item": `tfcflorae:soil_pile/${soilType}`
      }
    }
    let brushingDirtierStoneTiles = {
      "type": "tfcflorae:brushing",
      "ingredient": `tfcflorae:dirtier_stone_tiles/${soilType}/${rockType}`,
      "result": `tfcflorae:dirty_stone_tiles/${soilType}/${rockType}`,
      "extra_drop": {
        "item": `tfcflorae:soil_pile/${soilType}`
      }
    }
    let brushingDirtiestStoneTiles = {
      "type": "tfcflorae:brushing",
      "ingredient": `tfcflorae:dirtiest_stone_tiles/${soilType}/${rockType}`,
      "result": `tfcflorae:dirtier_stone_tiles/${soilType}/${rockType}`,
      "extra_drop": {
        "item": `tfcflorae:soil_pile/${soilType}`
      }
    }
    fs.writeFileSync(`./data/recipes/brushing/soil/dirty_stone_tiles/${soilType}/${rockType}.json`, JSON.stringify(brushingDirtyStoneTiles, null, 2))
    fs.writeFileSync(`./data/recipes/brushing/soil/dirtier_stone_tiles/${soilType}/${rockType}.json`, JSON.stringify(brushingDirtierStoneTiles, null, 2))
    fs.writeFileSync(`./data/recipes/brushing/soil/dirtiest_stone_tiles/${soilType}/${rockType}.json`, JSON.stringify(brushingDirtiestStoneTiles, null, 2))
    for(let decoType in decoTypes)
    {
      let brushingDirtyStoneTilesDeco = {
        "type": "tfcflorae:brushing",
        "ingredient": `tfcflorae:dirty_stone_tiles/${soilType}/${decoType}/${rockType}`,
        "result": `tfcflorae:rock/stone_tiles/${decoType}/${rockType}`,
        "extra_drop": {
          "item": `tfcflorae:soil_pile/${soilType}`
        }
      }
      let brushingDirtierStoneTilesDeco = {
        "type": "tfcflorae:brushing",
        "ingredient": `tfcflorae:dirtier_stone_tiles/${soilType}/${decoType}/${rockType}`,
        "result": `tfcflorae:dirty_stone_tiles/${soilType}/${decoType}/${rockType}`,
        "extra_drop": {
          "item": `tfcflorae:soil_pile/${soilType}`
        }
      }
      let brushingDirtiestStoneTilesDeco = {
        "type": "tfcflorae:brushing",
        "ingredient": `tfcflorae:dirtiest_stone_tiles/${soilType}/${decoType}/${rockType}`,
        "result": `tfcflorae:dirtier_stone_tiles/${soilType}/${decoType}/${rockType}`,
        "extra_drop": {
          "item": `tfcflorae:soil_pile/${soilType}`
        }
      }
      fs.writeFileSync(`./data/recipes/brushing/soil/dirty_stone_tiles/${soilType}/${decoType}/${rockType}.json`, JSON.stringify(brushingDirtyStoneTilesDeco, null, 2))
      fs.writeFileSync(`./data/recipes/brushing/soil/dirtier_stone_tiles/${soilType}/${decoType}/${rockType}.json`, JSON.stringify(brushingDirtierStoneTilesDeco, null, 2))
      fs.writeFileSync(`./data/recipes/brushing/soil/dirtiest_stone_tiles/${soilType}/${decoType}/${rockType}.json`, JSON.stringify(brushingDirtiestStoneTilesDeco, null, 2))
    }
  }
  for(let soilType in dirtTypes2)
  {
    // Stairs
    let chiselDirtyStoneTilesStair = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:dirty_stone_tiles/${soilType}/${rockType}`,
      "result": `tfcflorae:dirty_stone_tiles/${soilType}/stairs/${rockType}`,
      "mode": "stair"
    }
    let chiselDirtierStoneTilesStair = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:dirtier_stone_tiles/${soilType}/${rockType}`,
      "result": `tfcflorae:dirtier_stone_tiles/${soilType}/stairs/${rockType}`,
      "mode": "stair"
    }
    let chiselDirtiestStoneTilesStair = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:dirtiest_stone_tiles/${soilType}/${rockType}`,
      "result": `tfcflorae:dirtiest_stone_tiles/${soilType}/stairs/${rockType}`,
      "mode": "stair"
    }
    fs.writeFileSync(`./data/recipes/chisel/stair/soil/dirty_stone_tiles/${soilType}/${rockType}.json`, JSON.stringify(chiselDirtyStoneTilesStair, null, 2))
    fs.writeFileSync(`./data/recipes/chisel/stair/soil/dirtier_stone_tiles/${soilType}/${rockType}.json`, JSON.stringify(chiselDirtierStoneTilesStair, null, 2))
    fs.writeFileSync(`./data/recipes/chisel/stair/soil/dirtiest_stone_tiles/${soilType}/${rockType}.json`, JSON.stringify(chiselDirtiestStoneTilesStair, null, 2))

    let chiselRockyCompactDirtStair = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:rocky_compact_dirt/${soilType}/${rockType}`,
      "result": `tfcflorae:rocky_compact_dirt/${soilType}/stairs/${rockType}`,
      "mode": "stair"
    }
    let chiselRockierCompactDirtStair = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:rockier_compact_dirt/${soilType}/${rockType}`,
      "result": `tfcflorae:rockier_compact_dirt/${soilType}/stairs/${rockType}`,
      "mode": "stair"
    }
    let chiselRockiestCompactDirtStair = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:rockiest_compact_dirt/${soilType}/${rockType}`,
      "result": `tfcflorae:rockiest_compact_dirt/${soilType}/stairs/${rockType}`,
      "mode": "stair"
    }
    let chiselPebbleCompactDirtStair = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:pebble_compact_dirt/${soilType}/${rockType}`,
      "result": `tfcflorae:pebble_compact_dirt/${soilType}/stairs/${rockType}`,
      "mode": "stair"
    }
    fs.writeFileSync(`./data/recipes/chisel/stair/soil/rocky_compact_dirt/${soilType}/${rockType}.json`, JSON.stringify(chiselRockyCompactDirtStair, null, 2))
    fs.writeFileSync(`./data/recipes/chisel/stair/soil/rockier_compact_dirt/${soilType}/${rockType}.json`, JSON.stringify(chiselRockierCompactDirtStair, null, 2))
    fs.writeFileSync(`./data/recipes/chisel/stair/soil/rockiest_compact_dirt/${soilType}/${rockType}.json`, JSON.stringify(chiselRockiestCompactDirtStair, null, 2))
    fs.writeFileSync(`./data/recipes/chisel/stair/soil/pebble_compact_dirt/${soilType}/${rockType}.json`, JSON.stringify(chiselPebbleCompactDirtStair, null, 2))

    // Slabs
    let chiselDirtyStoneTilesSlab = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:dirty_stone_tiles/${soilType}/${rockType}`,
      "result": `tfcflorae:dirty_stone_tiles/${soilType}/slab/${rockType}`,
      "mode": "slab",
      "extra_drop": {
        "item": `tfcflorae:dirty_stone_tiles/${soilType}/slab/${rockType}`
      }
    }
    let chiselDirtierStoneTilesSlab = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:dirtier_stone_tiles/${soilType}/${rockType}`,
      "result": `tfcflorae:dirtier_stone_tiles/${soilType}/slab/${rockType}`,
      "mode": "slab",
      "extra_drop": {
        "item": `tfcflorae:dirtier_stone_tiles/${soilType}/slab/${rockType}`
      }
    }
    let chiselDirtiestStoneTilesSlab = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:dirtiest_stone_tiles/${soilType}/${rockType}`,
      "result": `tfcflorae:dirtiest_stone_tiles/${soilType}/slab/${rockType}`,
      "mode": "slab",
      "extra_drop": {
        "item": `tfcflorae:dirtiest_stone_tiles/${soilType}/slab/${rockType}`
      }
    }
    fs.writeFileSync(`./data/recipes/chisel/slab/soil/dirty_stone_tiles/${soilType}/${rockType}.json`, JSON.stringify(chiselDirtyStoneTilesSlab, null, 2))
    fs.writeFileSync(`./data/recipes/chisel/slab/soil/dirtier_stone_tiles/${soilType}/${rockType}.json`, JSON.stringify(chiselDirtierStoneTilesSlab, null, 2))
    fs.writeFileSync(`./data/recipes/chisel/slab/soil/dirtiest_stone_tiles/${soilType}/${rockType}.json`, JSON.stringify(chiselDirtiestStoneTilesSlab, null, 2))

    let chiselRockyCompactDirtSlab = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:rocky_compact_dirt/${soilType}/${rockType}`,
      "result": `tfcflorae:rocky_compact_dirt/${soilType}/slab/${rockType}`,
      "mode": "slab",
      "extra_drop": {
        "item": `tfcflorae:rocky_compact_dirt/${soilType}/slab/${rockType}`
      }
    }
    let chiselRockierCompactDirtSlab = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:rockier_compact_dirt/${soilType}/${rockType}`,
      "result": `tfcflorae:rockier_compact_dirt/${soilType}/slab/${rockType}`,
      "mode": "slab",
      "extra_drop": {
        "item": `tfcflorae:rockier_compact_dirt/${soilType}/slab/${rockType}`
      }
    }
    let chiselRockiestCompactDirtSlab = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:rockiest_compact_dirt/${soilType}/${rockType}`,
      "result": `tfcflorae:rockiest_compact_dirt/${soilType}/slab/${rockType}`,
      "mode": "slab",
      "extra_drop": {
        "item": `tfcflorae:rockiest_compact_dirt/${soilType}/slab/${rockType}`
      }
    }
    let chiselPebbleCompactDirtSlab = {
      "type": "tfc:chisel",
      "ingredient": `tfcflorae:pebble_compact_dirt/${soilType}/${rockType}`,
      "result": `tfcflorae:pebble_compact_dirt/${soilType}/slab/${rockType}`,
      "mode": "slab",
      "extra_drop": {
        "item": `tfcflorae:pebble_compact_dirt/${soilType}/slab/${rockType}`
      }
    }
    fs.writeFileSync(`./data/recipes/chisel/slab/soil/rocky_compact_dirt/${soilType}/${rockType}.json`, JSON.stringify(chiselRockyCompactDirtSlab, null, 2))
    fs.writeFileSync(`./data/recipes/chisel/slab/soil/rockier_compact_dirt/${soilType}/${rockType}.json`, JSON.stringify(chiselRockierCompactDirtSlab, null, 2))
    fs.writeFileSync(`./data/recipes/chisel/slab/soil/rockiest_compact_dirt/${soilType}/${rockType}.json`, JSON.stringify(chiselRockiestCompactDirtSlab, null, 2))
    fs.writeFileSync(`./data/recipes/chisel/slab/soil/pebble_compact_dirt/${soilType}/${rockType}.json`, JSON.stringify(chiselPebbleCompactDirtSlab, null, 2))

    for (let soilCompactRockType in soilCompactRockTypes)
    {
      let landslideRockySoil = {
        "__comment__": "This file was automatically created by mcresources",
        "type": "tfc:landslide",
        "ingredient": [
          `tfcflorae:${soilCompactRockType}/${soilType}/${rockType}`
        ],
        "result": `tfcflorae:${soilCompactRockType}/${soilType}/${rockType}`
      }
      fs.writeFileSync(`./data/recipes/landslide/soil/${soilCompactRockType}/${soilType}/${rockType}.json`, JSON.stringify(landslideRockySoil, null, 2))
    }
    for (let soilRockType in soilRockTypes2)
    {
      let collapseStoneTilesSoil = {
        "__comment__": "This file was automatically created by mcresources",
        "type": "tfc:collapse",
        "ingredient": [
          `tfcflorae:${soilRockType}/${soilType}/${rockType}`
        ],
        "result": `tfcflorae:${soilRockType}/${soilType}/${rockType}`
      }
      fs.writeFileSync(`./data/recipes/collapse/soil/${soilRockType}/${soilType}/${rockType}.json`, JSON.stringify(collapseStoneTilesSoil, null, 2))
    }
  }


  // Sandstone Stuff
  for(let sandstoneType in sandstoneTypes)
  {
    for(let sandColor in sandColors)
    {
      let blockstateSandstone = {
        "__comment__": "This file was automatically created by mcresources",
        "variants": {
          "": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`
          }
        }
      }
      let blockstateSandstoneSlab = {
        "__comment__": "This file was automatically created by mcresources",
        "variants": {
          "type=bottom": {	
            "model": `tfcflorae:block/sandstone/${sandstoneType}/slab/bottom/${sandColor}`
          },
          "type=top": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/slab/top/${sandColor}`
          },
          "type=double": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`
          }
        }
      }
      let blockstateSandstoneStairs = {
        "__comment__": "This file was automatically created by mcresources",
        "variants": {
          "facing=east,half=bottom,shape=straight": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/${sandColor}`
          },
          "facing=west,half=bottom,shape=straight": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/${sandColor}`,
            "y": 180,
            "uvlock": true
          },
          "facing=south,half=bottom,shape=straight": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/${sandColor}`,
            "y": 90,
            "uvlock": true
          },
          "facing=north,half=bottom,shape=straight": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/${sandColor}`,
            "y": 270,
            "uvlock": true
          },
          "facing=east,half=bottom,shape=outer_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`
          },
          "facing=west,half=bottom,shape=outer_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "y": 180,
            "uvlock": true
          },
          "facing=south,half=bottom,shape=outer_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "y": 90,
            "uvlock": true
          },
          "facing=north,half=bottom,shape=outer_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "y": 270,
            "uvlock": true
          },
          "facing=east,half=bottom,shape=outer_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "y": 270,
            "uvlock": true
          },
          "facing=west,half=bottom,shape=outer_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "y": 90,
            "uvlock": true
          },
          "facing=south,half=bottom,shape=outer_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`
          },
          "facing=north,half=bottom,shape=outer_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "y": 180,
            "uvlock": true
          },
          "facing=east,half=bottom,shape=inner_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`
          },
          "facing=west,half=bottom,shape=inner_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "y": 180,
            "uvlock": true
          },
          "facing=south,half=bottom,shape=inner_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "y": 90,
            "uvlock": true
          },
          "facing=north,half=bottom,shape=inner_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "y": 270,
            "uvlock": true
          },
          "facing=east,half=bottom,shape=inner_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "y": 270,
            "uvlock": true
          },
          "facing=west,half=bottom,shape=inner_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "y": 90,
            "uvlock": true
          },
          "facing=south,half=bottom,shape=inner_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`
          },
          "facing=north,half=bottom,shape=inner_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "y": 180,
            "uvlock": true
          },
          "facing=east,half=top,shape=straight": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/${sandColor}`,
            "x": 180,
            "uvlock": true
          },
          "facing=west,half=top,shape=straight": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/${sandColor}`,
            "x": 180,
            "y": 180,
            "uvlock": true
          },
          "facing=south,half=top,shape=straight": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/${sandColor}`,
            "x": 180,
            "y": 90,
            "uvlock": true
          },
          "facing=north,half=top,shape=straight": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/${sandColor}`,
            "x": 180,
            "y": 270,
            "uvlock": true
          },
          "facing=east,half=top,shape=outer_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "x": 180,
            "y": 90,
            "uvlock": true
          },
          "facing=west,half=top,shape=outer_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "x": 180,
            "y": 270,
            "uvlock": true
          },
          "facing=south,half=top,shape=outer_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "x": 180,
            "y": 180,
            "uvlock": true
          },
          "facing=north,half=top,shape=outer_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "x": 180,
            "uvlock": true
          },
          "facing=east,half=top,shape=outer_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "x": 180,
            "uvlock": true
          },
          "facing=west,half=top,shape=outer_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "x": 180,
            "y": 180,
            "uvlock": true
          },
          "facing=south,half=top,shape=outer_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "x": 180,
            "y": 90,
            "uvlock": true
          },
          "facing=north,half=top,shape=outer_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/outer/${sandColor}`,
            "x": 180,
            "y": 270,
            "uvlock": true
          },
          "facing=east,half=top,shape=inner_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "x": 180,
            "y": 90,
            "uvlock": true
          },
          "facing=west,half=top,shape=inner_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "x": 180,
            "y": 270,
            "uvlock": true
          },
          "facing=south,half=top,shape=inner_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "x": 180,
            "y": 180,
            "uvlock": true
          },
          "facing=north,half=top,shape=inner_right": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "x": 180,
            "uvlock": true
          },
          "facing=east,half=top,shape=inner_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "x": 180,
            "uvlock": true
          },
          "facing=west,half=top,shape=inner_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "x": 180,
            "y": 180,
            "uvlock": true
          },
          "facing=south,half=top,shape=inner_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "x": 180,
            "y": 90,
            "uvlock": true
          },
          "facing=north,half=top,shape=inner_left": {
            "model": `tfcflorae:block/sandstone/${sandstoneType}/stairs/inner/${sandColor}`,
            "x": 180,
            "y": 270,
            "uvlock": true
          }
        }
      }
      let blockstateSandstoneWall = {
        "__comment__": "This file was automatically created by mcresources",
        "multipart": [
          {
            "when": {
              "up": "true"
            },
            "apply": {
              "model": `tfcflorae:block/sandstone/${sandstoneType}/wall/post/${sandColor}`
            }
          },
          {
            "when": {
              "north": "low"
            },
            "apply": {
              "model": `tfcflorae:block/sandstone/${sandstoneType}/wall/side/${sandColor}`,
              "uvlock": true
            }
          },
          {
            "when": {
              "east": "low"
            },
            "apply": {
              "model": `tfcflorae:block/sandstone/${sandstoneType}/wall/side/${sandColor}`,
              "y": 90,
              "uvlock": true
            }
          },
          {
            "when": {
              "south": "low"
            },
            "apply": {
              "model": `tfcflorae:block/sandstone/${sandstoneType}/wall/side/${sandColor}`,
              "y": 180,
              "uvlock": true
            }
          },
          {
            "when": {
              "west": "low"
            },
            "apply": {
              "model": `tfcflorae:block/sandstone/${sandstoneType}/wall/side/${sandColor}`,
              "y": 270,
              "uvlock": true
            }
          },
          {
            "when": {
              "north": "tall"
            },
            "apply": {
              "model": `tfcflorae:block/sandstone/${sandstoneType}/wall/side/tall/${sandColor}`,
              "uvlock": true
            }
          },
          {
            "when": {
              "east": "tall"
            },
            "apply": {
              "model": `tfcflorae:block/sandstone/${sandstoneType}/wall/side/tall/${sandColor}`,
              "y": 90,
              "uvlock": true
            }
          },
          {
            "when": {
              "south": "tall"
            },
            "apply": {
              "model": `tfcflorae:block/sandstone/${sandstoneType}/wall/side/tall/${sandColor}`,
              "y": 180,
              "uvlock": true
            }
          },
          {
            "when": {
              "west": "tall"
            },
            "apply": {
              "model": `tfcflorae:block/sandstone/${sandstoneType}/wall/side/tall/${sandColor}`,
              "y": 270,
              "uvlock": true
            }
          }
        ]
      }
      fs.writeFileSync(`./assets/blockstates/sandstone/${sandstoneType}/${sandColor}.json`, JSON.stringify(blockstateSandstone, null, 2))
      fs.writeFileSync(`./assets/blockstates/sandstone/${sandstoneType}/slab/${sandColor}.json`, JSON.stringify(blockstateSandstoneSlab, null, 2))
      fs.writeFileSync(`./assets/blockstates/sandstone/${sandstoneType}/stairs/${sandColor}.json`, JSON.stringify(blockstateSandstoneStairs, null, 2))
      fs.writeFileSync(`./assets/blockstates/sandstone/${sandstoneType}/wall/${sandColor}.json`, JSON.stringify(blockstateSandstoneWall, null, 2))

      // Models
      let modelSandstone = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": "block/cube_all",
        "textures": {
          "all": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`
        }
      }
      let modelSandstoneSlabBottom = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": "block/slab",
        "textures": {
          "bottom": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`,
          "top": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`,
          "side": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`
        }
      }
      let modelSandstoneSlabTop = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": "block/slab_top",
        "textures": {
          "bottom": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`,
          "top": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`,
          "side": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`
        }
      }
      let modelSandstoneStairs = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": "block/stairs",
        "textures": {
          "bottom": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`,
          "top": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`,
          "side": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`
        }
      }
      let modelSandstoneStairsInner = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": "block/inner_stairs",
        "textures": {
          "bottom": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`,
          "top": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`,
          "side": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`
        }
      }
      let modelSandstoneStairsOuter = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": "block/outer_stairs",
        "textures": {
          "bottom": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`,
          "top": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`,
          "side": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`
        }
      }
      let modelSandstoneWallInventory = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": "block/wall_inventory",
        "textures": {
          "wall": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`
        }
      }
      let modelSandstoneWallPost = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": "block/template_wall_post",
        "textures": {
          "wall": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`
        }
      }
      let modelSandstoneWallSide = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": "block/template_wall_side",
        "textures": {
          "wall": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`
        }
      }
      let modelSandstoneWallSideTall = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": "block/template_wall_side_tall",
        "textures": {
          "wall": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`
        }
      }
      fs.writeFileSync(`./assets/models/block/sandstone/${sandstoneType}/${sandColor}.json`, JSON.stringify(modelSandstone, null, 2))
      fs.writeFileSync(`./assets/models/block/sandstone/${sandstoneType}/slab/bottom/${sandColor}.json`, JSON.stringify(modelSandstoneSlabBottom, null, 2))
      fs.writeFileSync(`./assets/models/block/sandstone/${sandstoneType}/slab/top/${sandColor}.json`, JSON.stringify(modelSandstoneSlabTop, null, 2))
      fs.writeFileSync(`./assets/models/block/sandstone/${sandstoneType}/stairs/${sandColor}.json`, JSON.stringify(modelSandstoneStairs, null, 2))
      fs.writeFileSync(`./assets/models/block/sandstone/${sandstoneType}/stairs/inner/${sandColor}.json`, JSON.stringify(modelSandstoneStairsInner, null, 2))
      fs.writeFileSync(`./assets/models/block/sandstone/${sandstoneType}/stairs/outer/${sandColor}.json`, JSON.stringify(modelSandstoneStairsOuter, null, 2))
      fs.writeFileSync(`./assets/models/block/sandstone/${sandstoneType}/wall/inventory/${sandColor}.json`, JSON.stringify(modelSandstoneWallInventory, null, 2))
      fs.writeFileSync(`./assets/models/block/sandstone/${sandstoneType}/wall/post/${sandColor}.json`, JSON.stringify(modelSandstoneWallPost, null, 2))
      fs.writeFileSync(`./assets/models/block/sandstone/${sandstoneType}/wall/side/${sandColor}.json`, JSON.stringify(modelSandstoneWallSide, null, 2))
      fs.writeFileSync(`./assets/models/block/sandstone/${sandstoneType}/wall/side/tall/${sandColor}.json`, JSON.stringify(modelSandstoneWallSideTall, null, 2))

      // Models (Items)
      let modelItemSandstone = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": `tfcflorae:block/sandstone/${sandstoneType}/${sandColor}`
      }
      let modelItemSandstoneSlab = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": `tfcflorae:block/sandstone/${sandstoneType}/slab/bottom/${sandColor}`
      }
      let modelItemSandstoneStairs = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": `tfcflorae:block/sandstone/${sandstoneType}/stairs/${sandColor}`
      }
      let modelItemSandstoneWall = {
        "__comment__": "This file was automatically created by mcresources",
        "parent": `tfcflorae:block/sandstone/${sandstoneType}/wall/inventory/${sandColor}`
      }
      fs.writeFileSync(`./assets/models/item/sandstone/${sandstoneType}/${sandColor}.json`, JSON.stringify(modelItemSandstone, null, 2))
      fs.writeFileSync(`./assets/models/item/sandstone/${sandstoneType}/slab/${sandColor}.json`, JSON.stringify(modelItemSandstoneSlab, null, 2))
      fs.writeFileSync(`./assets/models/item/sandstone/${sandstoneType}/stairs/${sandColor}.json`, JSON.stringify(modelItemSandstoneStairs, null, 2))
      fs.writeFileSync(`./assets/models/item/sandstone/${sandstoneType}/wall/${sandColor}.json`, JSON.stringify(modelItemSandstoneWall, null, 2))

      // Loot tables for sandstone blocks
      let lootTableSandstone = {
        "__comment__": "This file was automatically created by mcresources",
        "type": "minecraft:block",
        "pools": [
          {
            "name": "loot_pool",
            "rolls": 1,
            "entries": [
              {
                "type": "minecraft:item",
                "name": `tfcflorae:sandstone/${sandstoneType}/${sandColor}`
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
      let lootTableSandstoneSlab = {
        "__comment__": "This file was automatically created by mcresources",
        "type": "minecraft:block",
        "pools": [
          {
            "name": "loot_pool",
            "rolls": 1,
            "entries": [
              {
                "type": "minecraft:item",
                "name": `tfcflorae:sandstone/${sandstoneType}/slab/${sandColor}`,
                "functions": [
                  {
                    "function": "minecraft:set_count",
                    "conditions": [
                      {
                        "condition": "minecraft:block_state_property",
                        "block": `tfcflorae:sandstone/${sandstoneType}/slab/${sandColor}`,
                        "properties": {
                          "type": "double"
                        }
                      }
                    ],
                    "count": 2,
                    "add": false
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
      let lootTableSandstoneStairs = {
        "__comment__": "This file was automatically created by mcresources",
        "type": "minecraft:block",
        "pools": [
          {
            "name": "loot_pool",
            "rolls": 1,
            "entries": [
              {
                "type": "minecraft:item",
                "name": `tfcflorae:sandstone/${sandstoneType}/stairs/${sandColor}`
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
      let lootTableSandstoneWall = {
        "__comment__": "This file was automatically created by mcresources",
        "type": "minecraft:block",
        "pools": [
          {
            "name": "loot_pool",
            "rolls": 1,
            "entries": [
              {
                "type": "minecraft:item",
                "name": `tfcflorae:sandstone/${sandstoneType}/wall/${sandColor}`
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
      fs.writeFileSync(`./data/loot_tables/blocks/sandstone/${sandstoneType}/${sandColor}.json`, JSON.stringify(lootTableSandstone, null, 2))
      fs.writeFileSync(`./data/loot_tables/blocks/sandstone/${sandstoneType}/slab/${sandColor}.json`, JSON.stringify(lootTableSandstoneSlab, null, 2))
      fs.writeFileSync(`./data/loot_tables/blocks/sandstone/${sandstoneType}/stairs/${sandColor}.json`, JSON.stringify(lootTableSandstoneStairs, null, 2))
      fs.writeFileSync(`./data/loot_tables/blocks/sandstone/${sandstoneType}/wall/${sandColor}.json`, JSON.stringify(lootTableSandstoneWall, null, 2))

      /*"tfc:raw_sandstone/brown",
      "tfc:raw_sandstone/white",
      "tfc:raw_sandstone/black",
      "tfc:raw_sandstone/red",
      "tfc:raw_sandstone/yellow",
      "tfc:raw_sandstone/green",
      "tfc:raw_sandstone/pink",*/

      // Recipes
      let recipeSandstone = {
        "type": "minecraft:crafting_shaped",
        "pattern": [
          "XY",
          "YX"
        ],
        "key": {
          "X": {
            "item": `tfc:sand/${sandColor}`
          },
          "Y": {
            "item": `tfc:raw_sandstone/${sandColor}`
          }
        },
        "result": {
          "item": `tfcflorae:sandstone/${sandstoneType}/${sandColor}`,
          "count": 4
        }
      }
      let recipeSandstoneSlab = {
        "type": "minecraft:crafting_shaped",
        "pattern": [
          "XXX"
        ],
        "key": {
          "X": {
            "item": `tfcflorae:sandstone/${sandstoneType}/${sandColor}`
          }
        },
        "result": {
          "item": `tfcflorae:sandstone/${sandstoneType}/slab/${sandColor}`,
          "count": 6
        }
      }
      let recipeSandstoneStairs = {
        "type": "minecraft:crafting_shaped",
        "pattern": [
          "X  ",
          "XX ",
          "XXX"
        ],
        "key": {
          "X": {
            "item": `tfcflorae:sandstone/${sandstoneType}/${sandColor}`
          }
        },
        "result": {
          "item": `tfcflorae:sandstone/${sandstoneType}/stairs/${sandColor}`,
          "count": 8
        }
      }
      let recipeSandstoneWall = {
        "type": "minecraft:crafting_shaped",
        "pattern": [
          "XXX",
          "XXX"
        ],
        "key": {
          "X": {
            "item": `tfcflorae:sandstone/${sandstoneType}/${sandColor}`
          }
        },
        "result": {
          "item": `tfcflorae:sandstone/${sandstoneType}/wall/${sandColor}`,
          "count": 6
        }
      }
      fs.writeFileSync(`./data/recipes/crafting/sandstone/${sandstoneType}/${sandColor}.json`, JSON.stringify(recipeSandstone, null, 2))
      fs.writeFileSync(`./data/recipes/crafting/sandstone/${sandstoneType}/slab/${sandColor}.json`, JSON.stringify(recipeSandstoneSlab, null, 2))
      fs.writeFileSync(`./data/recipes/crafting/sandstone/${sandstoneType}/stairs/${sandColor}.json`, JSON.stringify(recipeSandstoneStairs, null, 2))
      fs.writeFileSync(`./data/recipes/crafting/sandstone/${sandstoneType}/wall/${sandColor}.json`, JSON.stringify(recipeSandstoneWall, null, 2))

      let chiselSandstoneSlab = {
        "type": "tfc:chisel",
        "ingredient": `tfcflorae:sandstone/${sandstoneType}/${sandColor}`,
        "result": `tfcflorae:sandstone/${sandstoneType}/slab/${sandColor}`,
        "mode": "slab",
        "extra_drop": {
          "item": `tfcflorae:sandstone/${sandstoneType}/slab/${sandColor}`
        }
      }
      let chiselSandstoneStair = {
        "type": "tfc:chisel",
        "ingredient": `tfcflorae:sandstone/${sandstoneType}/${sandColor}`,
        "result": `tfcflorae:sandstone/${sandstoneType}/stairs/${sandColor}`,
        "mode": "stair"
      }
      fs.writeFileSync(`./data/recipes/chisel/slab/sandstone/${sandstoneType}/${sandColor}.json`, JSON.stringify(chiselSandstoneSlab, null, 2))
      fs.writeFileSync(`./data/recipes/chisel/stair/sandstone/${sandstoneType}/${sandColor}.json`, JSON.stringify(chiselSandstoneStair, null, 2))

      let stonecuttingSandstoneSlab = {
        "type": "minecraft:stonecutting",
        "ingredient": {
          "item": `tfcflorae:sandstone/${sandstoneType}/${sandColor}`
        },
        "result": `tfcflorae:sandstone/${sandstoneType}/slab/${sandColor}`,
        "count": 2
      }
      let stonecuttingSandstoneStairs = {
        "type": "minecraft:stonecutting",
        "ingredient": {
          "item": `tfcflorae:sandstone/${sandstoneType}/${sandColor}`
        },
        "result": `tfcflorae:sandstone/${sandstoneType}/stairs/${sandColor}`,
        "count": 1
      }
      let stonecuttingSandstoneWall = {
        "type": "minecraft:stonecutting",
        "ingredient": {
          "item": `tfcflorae:sandstone/${sandstoneType}/${sandColor}`
        },
        "result": `tfcflorae:sandstone/${sandstoneType}/wall/${sandColor}`,
        "count": 1
      }
      fs.writeFileSync(`./data/recipes/stonecutting/sandstone/${sandstoneType}/slab/${sandColor}.json`, JSON.stringify(stonecuttingSandstoneSlab, null, 2))
      fs.writeFileSync(`./data/recipes/stonecutting/sandstone/${sandstoneType}/stairs/${sandColor}.json`, JSON.stringify(stonecuttingSandstoneStairs, null, 2))
      fs.writeFileSync(`./data/recipes/stonecutting/sandstone/${sandstoneType}/wall/${sandColor}.json`, JSON.stringify(stonecuttingSandstoneWall, null, 2))
    }
  }

  let blockstateGeyser = {
    "variants": {
      "thickness=base,vertical_direction=down": {
        "model": `tfcflorae:block/rock/geyser/${rockType}/down_base`
      },
      "thickness=middle,vertical_direction=down": {
        "model": `tfcflorae:block/rock/geyser/${rockType}/down_middle`
      },
      "thickness=frustum,vertical_direction=down": {
        "model": `tfcflorae:block/rock/geyser/${rockType}/down_frustum`
      },
      "thickness=tip,vertical_direction=down": {
        "model": `tfcflorae:block/rock/geyser/${rockType}/down_tip`
      },
      "thickness=tip_merge,vertical_direction=down": {
        "model": `tfcflorae:block/rock/geyser/${rockType}/down_tip_merge`
      },
      "thickness=base,vertical_direction=up": {
        "model": `tfcflorae:block/rock/geyser/${rockType}/up_base`
      },
      "thickness=middle,vertical_direction=up": {
        "model": `tfcflorae:block/rock/geyser/${rockType}/up_middle`
      },
      "thickness=frustum,vertical_direction=up": {
        "model": `tfcflorae:block/rock/geyser/${rockType}/up_frustum`
      },
      "thickness=tip,vertical_direction=up": {
        "model": `tfcflorae:block/rock/geyser/${rockType}/up_tip`
      },
      "thickness=tip_merge,vertical_direction=up": {
        "model": `tfcflorae:block/rock/geyser/${rockType}/up_tip_merge`
      }
    }
  }
  fs.writeFileSync(`./assets/blockstates/rock/geyser/${rockType}.json`, JSON.stringify(blockstateGeyser, null, 2))

  for (let geyserModelType in geyserModelTypes)
  {
    let modelGeyser = {
      "parent": `tfcflorae:block/rock/geyser/${geyserModelType}`,
      "textures": {
        "side": `tfcflorae:block/rock/geyser_side/${rockType}`,
        "top": `tfcflorae:block/rock/geyser_top/${rockType}`,
        "bottom": `tfcflorae:block/rock/geyser_bottom/${rockType}`,
        "particle": `tfcflorae:block/rock/geyser_side/${rockType}`
      }
    }
    fs.writeFileSync(`./assets/models/block/rock/geyser/${rockType}/${geyserModelType}.json`, JSON.stringify(modelGeyser, null, 2))
  }

  let modelItemGeyser = {
    "parent": `tfcflorae:block/rock/geyser/${rockType}/up_middle`
  }
  fs.writeFileSync(`./assets/models/item/rock/geyser/${rockType}.json`, JSON.stringify(modelItemGeyser, null, 2))

  let lootTableGeyser = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name":  `${ROCK_NAMESPACES[rockType]}:rock/loose/${rockType}`,
            "functions": [
              {
                "function": "minecraft:set_count",
                "count": {
                  "type": "minecraft:uniform",
                  "min": 1,
                  "max": 2
                }
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
  fs.writeFileSync(`./data/loot_tables/blocks/rock/geyser/${rockType}.json`, JSON.stringify(lootTableGeyser, null, 2))

  fs.writeFileSync(`./loot_tables/blocks/deposit/cassiterite/${rockType}.json`, JSON.stringify(deposit_cassiterite, null, 2))
  fs.writeFileSync(`./loot_tables/blocks/deposit/native_copper/${rockType}.json`, JSON.stringify(deposit_native_copper, null, 2))
  fs.writeFileSync(`./loot_tables/blocks/deposit/native_gold/${rockType}.json`, JSON.stringify(deposit_native_gold, null, 2))
  fs.writeFileSync(`./loot_tables/blocks/deposit/native_silver/${rockType}.json`, JSON.stringify(deposit_native_silver, null, 2))

  fs.writeFileSync(`./assets/blockstates/rock/cobbled_bricks/${rockType}.json`, JSON.stringify(cobbled_bricksJSON, null, 2))
  fs.writeFileSync(`./assets/blockstates/rock/cobbled_bricks/slab/${rockType}.json`, JSON.stringify(cobbled_bricks_slabJSON, null, 2))
  fs.writeFileSync(`./assets/blockstates/rock/cobbled_bricks/stairs/${rockType}.json`, JSON.stringify(cobbled_bricks_stairsJSON, null, 2))
  fs.writeFileSync(`./assets/blockstates/rock/cobbled_bricks/wall/${rockType}.json`, JSON.stringify(cobbled_bricks_wallJSON, null, 2))

	fs.writeFileSync(`./assets/blockstates/rock/column/${rockType}.json`, JSON.stringify(columnJSON, null, 2))

	fs.writeFileSync(`./assets/blockstates/rock/flagstone_bricks/${rockType}.json`, JSON.stringify(flagstone_bricksJSON, null, 2))
	fs.writeFileSync(`./assets/blockstates/rock/flagstone_bricks/slab/${rockType}.json`, JSON.stringify(flagstone_bricks_slabJSON, null, 2))
	fs.writeFileSync(`./assets/blockstates/rock/flagstone_bricks/stairs/${rockType}.json`, JSON.stringify(flagstone_bricks_stairsJSON, null, 2))
	fs.writeFileSync(`./assets/blockstates/rock/flagstone_bricks/wall/${rockType}.json`, JSON.stringify(flagstone_bricks_wallJSON, null, 2))

	fs.writeFileSync(`./assets/blockstates/dirtier_stone_tiles/humus/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humusJSON, null, 2))
	fs.writeFileSync(`./assets/blockstates/dirtier_stone_tiles/humus/slab/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_slabJSON, null, 2))
	fs.writeFileSync(`./assets/blockstates/dirtier_stone_tiles/humus/stairs/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_stairsJSON, null, 2))
	fs.writeFileSync(`./assets/blockstates/dirtier_stone_tiles/humus/wall/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_wallJSON, null, 2))

	fs.writeFileSync(`./assets/models/block/dirtier_stone_tiles/humus/${rockType}.json`, JSON.stringify(model_dirtier_stone_tiles_humusJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/dirtier_stone_tiles/humus/slab/bottom/${rockType}.json`, JSON.stringify(model_dirtier_stone_tiles_humus_slab_bottomJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/dirtier_stone_tiles/humus/slab/top/${rockType}.json`, JSON.stringify(model_dirtier_stone_tiles_humus_slab_topJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/dirtier_stone_tiles/humus/stairs/${rockType}.json`, JSON.stringify(model_dirtier_stone_tiles_humus_stairsJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/dirtier_stone_tiles/humus/stairs/inner/${rockType}.json`, JSON.stringify(model_dirtier_stone_tiles_humus_stairs_innerJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/dirtier_stone_tiles/humus/stairs/outer/${rockType}.json`, JSON.stringify(model_dirtier_stone_tiles_humus_stairs_outerJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/dirtier_stone_tiles/humus/wall/inventory/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_wall_inventoryJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/dirtier_stone_tiles/humus/wall/post/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_wall_postJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/dirtier_stone_tiles/humus/wall/side/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_wall_sideJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/dirtier_stone_tiles/humus/wall/side/tall/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_wall_side_tallJSON, null, 2))

	fs.writeFileSync(`./assets/models/block/rock/cobbled_bricks/${rockType}.json`, JSON.stringify(model_cobbled_bricksJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/cobbled_bricks/slab/bottom/${rockType}.json`, JSON.stringify(model_cobbled_bricks_slab_bottomJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/cobbled_bricks/slab/top/${rockType}.json`, JSON.stringify(model_cobbled_bricks_slab_topJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/cobbled_bricks/stairs/${rockType}.json`, JSON.stringify(model_cobbled_bricks_stairsJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/cobbled_bricks/stairs/inner/${rockType}.json`, JSON.stringify(model_cobbled_bricks_stairs_innerJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/cobbled_bricks/stairs/outer/${rockType}.json`, JSON.stringify(model_cobbled_bricks_stairs_outerJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/cobbled_bricks/wall/inventory/${rockType}.json`, JSON.stringify(model_cobbled_bricks_wall_inventoryJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/cobbled_bricks/wall/post/${rockType}.json`, JSON.stringify(model_cobbled_bricks_wall_postJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/cobbled_bricks/wall/side/${rockType}.json`, JSON.stringify(model_cobbled_bricks_wall_sideJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/cobbled_bricks/wall/side/tall/${rockType}.json`, JSON.stringify(model_cobbled_bricks_wall_side_tallJSON, null, 2))

	fs.writeFileSync(`./assets/models/block/rock/flagstone_bricks/${rockType}.json`, JSON.stringify(model_flagstone_bricksJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/flagstone_bricks/slab/bottom/${rockType}.json`, JSON.stringify(model_flagstone_bricks_slab_bottomJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/flagstone_bricks/slab/top/${rockType}.json`, JSON.stringify(model_flagstone_bricks_slab_topJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/flagstone_bricks/stairs/${rockType}.json`, JSON.stringify(model_flagstone_bricks_stairsJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/flagstone_bricks/stairs/inner/${rockType}.json`, JSON.stringify(model_flagstone_bricks_stairs_innerJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/flagstone_bricks/stairs/outer/${rockType}.json`, JSON.stringify(model_flagstone_bricks_stairs_outerJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/flagstone_bricks/wall/inventory/${rockType}.json`, JSON.stringify(model_flagstone_bricks_wall_inventoryJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/flagstone_bricks/wall/post/${rockType}.json`, JSON.stringify(model_flagstone_bricks_wall_postJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/flagstone_bricks/wall/side/${rockType}.json`, JSON.stringify(model_flagstone_bricks_wall_sideJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/rock/flagstone_bricks/wall/side/tall/${rockType}.json`, JSON.stringify(model_flagstone_bricks_wall_side_tallJSON, null, 2))

	fs.writeFileSync(`./assets/models/block/rock/column/${rockType}.json`, JSON.stringify(model_columnJSON, null, 2))
  
	fs.writeFileSync(`./assets/models/item/dirtier_stone_tiles/humus/${rockType}.json`, JSON.stringify(item_model_dirtier_stone_tiles_humusJSON, null, 2))
	fs.writeFileSync(`./assets/models/item/dirtier_stone_tiles/humus/slab/${rockType}.json`, JSON.stringify(item_model_dirtier_stone_tiles_humus_slabJSON, null, 2))
	fs.writeFileSync(`./assets/models/item/dirtier_stone_tiles/humus/stairs/${rockType}.json`, JSON.stringify(item_model_dirtier_stone_tiles_humus_stairsJSON, null, 2))
	fs.writeFileSync(`./assets/models/item/dirtier_stone_tiles/humus/wall/${rockType}.json`, JSON.stringify(item_model_dirtier_stone_tiles_humus_wallJSON, null, 2))

	fs.writeFileSync(`./assets/models/item/rock/column/${rockType}.json`, JSON.stringify(item_model_columnJSON, null, 2))
	fs.writeFileSync(`./assets/models/item/rock/polished_column/${rockType}.json`, JSON.stringify(item_model_polished_columnJSON, null, 2))

	fs.writeFileSync(`./assets/models/item/rock/cobbled_bricks/${rockType}.json`, JSON.stringify(item_model_cobbled_bricksJSON, null, 2))
	fs.writeFileSync(`./assets/models/item/rock/cobbled_bricks/slab/${rockType}.json`, JSON.stringify(item_model_cobbled_bricks_slabJSON, null, 2))
	fs.writeFileSync(`./assets/models/item/rock/cobbled_bricks/stairs/${rockType}.json`, JSON.stringify(item_model_cobbled_bricks_stairsJSON, null, 2))
	fs.writeFileSync(`./assets/models/item/rock/cobbled_bricks/wall/${rockType}.json`, JSON.stringify(item_model_cobbled_bricks_wallJSON, null, 2))

	fs.writeFileSync(`./assets/blockstates/rock/mossy_cobble/slab/${rockType}.json`, JSON.stringify(bricks_slabJSON, null, 2))
	fs.writeFileSync(`./assets/blockstates/rock/mossy_cobble/stairs/${rockType}.json`, JSON.stringify(bricks_stairsJSON, null, 2))
	fs.writeFileSync(`./assets/blockstates/rock/mossy_cobble/wall/${rockType}.json`, JSON.stringify(bricks_wallJSON, null, 2))

	fs.writeFileSync(`./assets/models/item/rock/mossy_cobble/slab/${rockType}.json`, JSON.stringify(item_bricks_slabJSON, null, 2))
	fs.writeFileSync(`./assets/models/item/rock/mossy_cobble/stairs/${rockType}.json`, JSON.stringify(item_bricks_stairsJSON, null, 2))
	fs.writeFileSync(`./assets/models/item/rock/mossy_cobble/wall/${rockType}.json`, JSON.stringify(item_bricks_wallJSON, null, 2))

  for(let keyIndex in recipeSoulDirtKeys) {
    const soilType = recipeSoulDirtKeys[keyIndex];

    for(var i in dirtTypes) {
      fs.writeFileSync(`./data/recipes/crafting/soil/${soilType}/${dirtTypes[i]}/${rockType}.json`, JSON.stringify(recipeAllSoilDirt[soilType].recipeSoilDirt[i], null, 2))
      fs.writeFileSync(`./data/recipes/crafting/soil/${soilType}/${dirtTypes[i]}/slab/${rockType}.json`, JSON.stringify(recipeAllSoilDirt[soilType].recipeSoilDirtSlab[i], null, 2))
      fs.writeFileSync(`./data/recipes/crafting/soil/${soilType}/${dirtTypes[i]}/stairs/${rockType}.json`, JSON.stringify(recipeAllSoilDirt[soilType].recipeSoilDirtStairs[i], null, 2))
      fs.writeFileSync(`./data/recipes/crafting/soil/${soilType}/${dirtTypes[i]}/wall/${rockType}.json`, JSON.stringify(recipeAllSoilDirt[soilType].recipeSoilDirtWall[i], null, 2))
    }
  }

  for(let keyIndex in recipeSoilStoneTileKeys) {
    const stoneType = recipeSoilStoneTileKeys[keyIndex];

    for(var i in dirtTypes) {
      fs.writeFileSync(`./data/recipes/crafting/soil/${stoneType}/${dirtTypes[i]}/${rockType}.json`, JSON.stringify(recipeAllSoilStoneTiles[stoneType].recipeSoilDirt[i], null, 2))
      fs.writeFileSync(`./data/recipes/crafting/soil/${stoneType}/${dirtTypes[i]}/slab/${rockType}.json`, JSON.stringify(recipeAllSoilStoneTiles[stoneType].recipeSoilDirtSlab[i], null, 2))
      fs.writeFileSync(`./data/recipes/crafting/soil/${stoneType}/${dirtTypes[i]}/stairs/${rockType}.json`, JSON.stringify(recipeAllSoilStoneTiles[stoneType].recipeSoilDirtStairs[i], null, 2))
      fs.writeFileSync(`./data/recipes/crafting/soil/${stoneType}/${dirtTypes[i]}/wall/${rockType}.json`, JSON.stringify(recipeAllSoilStoneTiles[stoneType].recipeSoilDirtWall[i], null, 2))
    }
  }

  fs.writeFileSync(`./data/recipes/crafting/rock/stone_tiles/${rockType}.json`, JSON.stringify(recipeAllRockStoneTiles.block[i], null, 2))
  fs.writeFileSync(`./data/recipes/crafting/rock/stone_tiles/slab/${rockType}.json`, JSON.stringify(recipeAllRockStoneTiles.slab[i], null, 2))
  fs.writeFileSync(`./data/recipes/crafting/rock/stone_tiles/stairs/${rockType}.json`, JSON.stringify(recipeAllRockStoneTiles.stairs[i], null, 2))
  fs.writeFileSync(`./data/recipes/crafting/rock/stone_tiles/wall/${rockType}.json`, JSON.stringify(recipeAllRockStoneTiles.wall[i], null, 2))
}
