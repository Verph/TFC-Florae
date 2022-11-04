const fs = require('fs');
const path = require('path');
const getDirName = require('path').dirname;
const mkdirp = require('mkdirp');

const fse = require('fs-extra')
const dir = '/tmp/this/path/does/not/exist'
const desiredMode = 0o2775
const options = {
  mode: 0o2775
}

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
  'dripstone': 'dripstone',
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
  'dripstone': 'tfcflorae',
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

const dirtTypes = [
  "humus",
  "loam",
  "sandy_loam",
  "silt",
  "silty_loam"
];

const rockBlockTypes = [
  "bricks",
  "chiseled",
  "cobble",
  "cobbled_bricks",
  "column",
  "cracked_bricks",
  "cracked_flagstone_bricks",
  "flagstone",
  "flagstone_bricks",
  "mossy_cobble",
  "mossy_flagstone_bricks",
  "gravel",
  "polished_column",
  "raw",
  "smooth",
  "stone_tiles"
];

const soilRockTypes2 = [
  "dirtiest_stone_tiles",
  "dirtier_stone_tiles",
  "dirty_stone_tiles"
];

const soilRockTypes = [
  "dirtiest_stone_tiles",
  "dirtier_stone_tiles",
  "dirty_stone_tiles",
  "pebble_compact_dirt",
  "rocky_compact_dirt",
  "rockier_compact_dirt",
  "rockiest_compact_dirt"
];

const decoTypes = [
  "slab",
  "stairs",
  "walls"
];

for(let rockType of Object.keys(ROCK_TYPES))
{
    generateJSON(rockType)
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
  for(let soilType in dirtTypes)
  {
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
        "ingredient": `tfcflorae:dirty_stone_tiles/${soilType}//${decoType}${rockType}`,
        "result": `tfcflorae:rock/stone_tiles/${decoType}/${rockType}`,
        "extra_drop": {
          "item": `tfcflorae:soil_pile/${soilType}`
        }
      }
      let brushingDirtierStoneTilesDeco = {
        "type": "tfcflorae:brushing",
        "ingredient": `tfcflorae:dirtier_stone_tiles/${soilType}//${decoType}${rockType}`,
        "result": `tfcflorae:dirty_stone_tiles/${soilType}//${decoType}${rockType}`,
        "extra_drop": {
          "item": `tfcflorae:soil_pile/${soilType}`
        }
      }
      let brushingDirtiestStoneTilesDeco = {
        "type": "tfcflorae:brushing",
        "ingredient": `tfcflorae:dirtiest_stone_tiles/${soilType}//${decoType}${rockType}`,
        "result": `tfcflorae:dirtier_stone_tiles/${soilType}//${decoType}${rockType}`,
        "extra_drop": {
          "item": `tfcflorae:soil_pile/${soilType}`
        }
      }
      fs.writeFileSync(`./data/recipes/brushing/soil/dirty_stone_tiles/${soilType}/${decoType}/${rockType}.json`, JSON.stringify(brushingDirtyStoneTilesDeco, null, 2))
      fs.writeFileSync(`./data/recipes/brushing/soil/dirtier_stone_tiles/${soilType}/${decoType}/${rockType}.json`, JSON.stringify(brushingDirtierStoneTilesDeco, null, 2))
      fs.writeFileSync(`./data/recipes/brushing/soil/dirtiest_stone_tiles/${soilType}/${decoType}/${rockType}.json`, JSON.stringify(brushingDirtiestStoneTilesDeco, null, 2))
    }
  }

  for(let blockType in rockBlockTypes)
  {
    fse.ensureDir(`./assets/blockstates/rock/${blockType}`, err => {console.log(err)})

    fse.ensureDir(`./assets/models/block/rock/${blockType}`, err => {console.log(err)})
    fse.ensureDir(`./assets/models/block/rock/${blockType}/slab/bottom`, err => {console.log(err)})
    fse.ensureDir(`./assets/models/block/rock/${blockType}/slab/top`, err => {console.log(err)})
    fse.ensureDir(`./assets/models/block/rock/${blockType}/stairs`, err => {console.log(err)})
    fse.ensureDir(`./assets/models/block/rock/${blockType}/stairs/inner`, err => {console.log(err)})
    fse.ensureDir(`./assets/models/block/rock/${blockType}/stairs/outer`, err => {console.log(err)})
    fse.ensureDir(`./assets/models/block/rock/${blockType}/wall/inventory`, err => {console.log(err)})
    fse.ensureDir(`./assets/models/block/rock/${blockType}/wall/post`, err => {console.log(err)})
    fse.ensureDir(`./assets/models/block/rock/${blockType}/wall/side`, err => {console.log(err)})
    fse.ensureDir(`./assets/models/block/rock/${blockType}/wall/side/tall`, err => {console.log(err)})

    fse.ensureDir(`./assets/models/item/rock/${blockType}`, err => {console.log(err)})

    for(let decoType in decoTypes)
    {
      fse.ensureDir(`./assets/blockstates/rock/${blockType}/${decoType}`, err => {console.log(err)})
      fse.ensureDir(`./assets/models/item/rock/${blockType}/${decoType}`, err => {console.log(err)})
    }
  }

  for(let soilRockType in soilRockTypes)
  {
    for(let soilType in dirtTypes)
    {
      fse.ensureDir(`./assets/blockstates/${soilRockType}/${soilType}`, err => {console.log(err)})

      fse.ensureDir(`./assets/models/block/${soilRockType}/${soilType}`, err => {console.log(err)})
      fse.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/slab/bottom`, err => {console.log(err)})
      fse.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/slab/top`, err => {console.log(err)})
      fse.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/stairs`, err => {console.log(err)})
      fse.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/stairs/inner`, err => {console.log(err)})
      fse.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/stairs/outer`, err => {console.log(err)})
      fse.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/wall/inventory`, err => {console.log(err)})
      fse.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/wall/post`, err => {console.log(err)})
      fse.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/wall/side`, err => {console.log(err)})
      fse.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/wall/side/tall`, err => {console.log(err)})

      fse.ensureDir(`./data/recipes/crafting/soil/${soilRockType}/${soilType}`, err => {console.log(err)})

      for(let decoType in decoTypes)
      {
        fse.ensureDir(`./assets/blockstates/${soilRockType}/${soilType}/${decoType}`, err => {console.log(err)})
        fse.ensureDir(`./assets/models/item/${soilRockType}/${soilType}/${decoType}`, err => {console.log(err)})
        fse.ensureDir(`./data/recipes/crafting/soil/${soilRockType}/${soilType}/${decoType}`, err => {console.log(err)})
      }
    }
  }
  for(let soilRockType in soilRockTypes2)
  {
    for(let soilType in dirtTypes)
    {
      fse.ensureDir(`./data/recipes/brushing/soil/${soilRockType}/${soilType}`, err => {console.log(err)})

      for(let decoType in decoTypes)
      {
        fse.ensureDir(`./data/recipes/brushing/soil/${soilRockType}/${soilType}/${decoType}`, err => {console.log(err)})
      }
    }
  }

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
