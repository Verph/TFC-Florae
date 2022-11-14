const fs = require('fs');

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

for(let rockType of Object.keys(ROCK_TYPES))
{
    generateJSON(rockType)
}

function generateJSON(rockType)
{
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

	fs.writeFileSync(`./blockstates/rock/cobbled_bricks/${rockType}.json`, JSON.stringify(cobbled_bricksJSON, null, 2))
	fs.writeFileSync(`./blockstates/rock/cobbled_bricks/slab/${rockType}.json`, JSON.stringify(cobbled_bricks_slabJSON, null, 2))
	fs.writeFileSync(`./blockstates/rock/cobbled_bricks/stairs/${rockType}.json`, JSON.stringify(cobbled_bricks_stairsJSON, null, 2))
	fs.writeFileSync(`./blockstates/rock/cobbled_bricks/wall/${rockType}.json`, JSON.stringify(cobbled_bricks_wallJSON, null, 2))

	fs.writeFileSync(`./blockstates/rock/column/${rockType}.json`, JSON.stringify(columnJSON, null, 2))

	fs.writeFileSync(`./blockstates/rock/flagstone_bricks/${rockType}.json`, JSON.stringify(flagstone_bricksJSON, null, 2))
	fs.writeFileSync(`./blockstates/rock/flagstone_bricks/slab/${rockType}.json`, JSON.stringify(flagstone_bricks_slabJSON, null, 2))
	fs.writeFileSync(`./blockstates/rock/flagstone_bricks/stairs/${rockType}.json`, JSON.stringify(flagstone_bricks_stairsJSON, null, 2))
	fs.writeFileSync(`./blockstates/rock/flagstone_bricks/wall/${rockType}.json`, JSON.stringify(flagstone_bricks_wallJSON, null, 2))

	fs.writeFileSync(`./blockstates/dirtier_stone_tiles/humus/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humusJSON, null, 2))
	fs.writeFileSync(`./blockstates/dirtier_stone_tiles/humus/slab/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_slabJSON, null, 2))
	fs.writeFileSync(`./blockstates/dirtier_stone_tiles/humus/stairs/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_stairsJSON, null, 2))
	fs.writeFileSync(`./blockstates/dirtier_stone_tiles/humus/wall/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_wallJSON, null, 2))

	fs.writeFileSync(`./models/block/dirtier_stone_tiles/humus/${rockType}.json`, JSON.stringify(model_dirtier_stone_tiles_humusJSON, null, 2))
	fs.writeFileSync(`./models/block/dirtier_stone_tiles/humus/slab/bottom/${rockType}.json`, JSON.stringify(model_dirtier_stone_tiles_humus_slab_bottomJSON, null, 2))
	fs.writeFileSync(`./models/block/dirtier_stone_tiles/humus/slab/top/${rockType}.json`, JSON.stringify(model_dirtier_stone_tiles_humus_slab_topJSON, null, 2))
	fs.writeFileSync(`./models/block/dirtier_stone_tiles/humus/stairs/${rockType}.json`, JSON.stringify(model_dirtier_stone_tiles_humus_stairsJSON, null, 2))
	fs.writeFileSync(`./models/block/dirtier_stone_tiles/humus/stairs/inner/${rockType}.json`, JSON.stringify(model_dirtier_stone_tiles_humus_stairs_innerJSON, null, 2))
	fs.writeFileSync(`./models/block/dirtier_stone_tiles/humus/stairs/outer/${rockType}.json`, JSON.stringify(model_dirtier_stone_tiles_humus_stairs_outerJSON, null, 2))
	fs.writeFileSync(`./models/block/dirtier_stone_tiles/humus/wall/inventory/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_wall_inventoryJSON, null, 2))
	fs.writeFileSync(`./models/block/dirtier_stone_tiles/humus/wall/post/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_wall_postJSON, null, 2))
	fs.writeFileSync(`./models/block/dirtier_stone_tiles/humus/wall/side/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_wall_sideJSON, null, 2))
	fs.writeFileSync(`./models/block/dirtier_stone_tiles/humus/wall/side/tall/${rockType}.json`, JSON.stringify(dirtier_stone_tiles_humus_wall_side_tallJSON, null, 2))

	fs.writeFileSync(`./models/block/rock/cobbled_bricks/${rockType}.json`, JSON.stringify(model_cobbled_bricksJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/cobbled_bricks/slab/bottom/${rockType}.json`, JSON.stringify(model_cobbled_bricks_slab_bottomJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/cobbled_bricks/slab/top/${rockType}.json`, JSON.stringify(model_cobbled_bricks_slab_topJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/cobbled_bricks/stairs/${rockType}.json`, JSON.stringify(model_cobbled_bricks_stairsJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/cobbled_bricks/stairs/inner/${rockType}.json`, JSON.stringify(model_cobbled_bricks_stairs_innerJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/cobbled_bricks/stairs/outer/${rockType}.json`, JSON.stringify(model_cobbled_bricks_stairs_outerJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/cobbled_bricks/wall/inventory/${rockType}.json`, JSON.stringify(model_cobbled_bricks_wall_inventoryJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/cobbled_bricks/wall/post/${rockType}.json`, JSON.stringify(model_cobbled_bricks_wall_postJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/cobbled_bricks/wall/side/${rockType}.json`, JSON.stringify(model_cobbled_bricks_wall_sideJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/cobbled_bricks/wall/side/tall/${rockType}.json`, JSON.stringify(model_cobbled_bricks_wall_side_tallJSON, null, 2))

	fs.writeFileSync(`./models/block/rock/flagstone_bricks/${rockType}.json`, JSON.stringify(model_flagstone_bricksJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/flagstone_bricks/slab/bottom/${rockType}.json`, JSON.stringify(model_flagstone_bricks_slab_bottomJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/flagstone_bricks/slab/top/${rockType}.json`, JSON.stringify(model_flagstone_bricks_slab_topJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/flagstone_bricks/stairs/${rockType}.json`, JSON.stringify(model_flagstone_bricks_stairsJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/flagstone_bricks/stairs/inner/${rockType}.json`, JSON.stringify(model_flagstone_bricks_stairs_innerJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/flagstone_bricks/stairs/outer/${rockType}.json`, JSON.stringify(model_flagstone_bricks_stairs_outerJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/flagstone_bricks/wall/inventory/${rockType}.json`, JSON.stringify(model_flagstone_bricks_wall_inventoryJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/flagstone_bricks/wall/post/${rockType}.json`, JSON.stringify(model_flagstone_bricks_wall_postJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/flagstone_bricks/wall/side/${rockType}.json`, JSON.stringify(model_flagstone_bricks_wall_sideJSON, null, 2))
	fs.writeFileSync(`./models/block/rock/flagstone_bricks/wall/side/tall/${rockType}.json`, JSON.stringify(model_flagstone_bricks_wall_side_tallJSON, null, 2))

	fs.writeFileSync(`./models/block/rock/column/${rockType}.json`, JSON.stringify(model_columnJSON, null, 2))
  
	fs.writeFileSync(`./models/item/dirtier_stone_tiles/humus/${rockType}.json`, JSON.stringify(item_model_dirtier_stone_tiles_humusJSON, null, 2))
	fs.writeFileSync(`./models/item/dirtier_stone_tiles/humus/slab/${rockType}.json`, JSON.stringify(item_model_dirtier_stone_tiles_humus_slabJSON, null, 2))
	fs.writeFileSync(`./models/item/dirtier_stone_tiles/humus/stairs/${rockType}.json`, JSON.stringify(item_model_dirtier_stone_tiles_humus_stairsJSON, null, 2))
	fs.writeFileSync(`./models/item/dirtier_stone_tiles/humus/wall/${rockType}.json`, JSON.stringify(item_model_dirtier_stone_tiles_humus_wallJSON, null, 2))

	fs.writeFileSync(`./models/item/rock/column/${rockType}.json`, JSON.stringify(item_model_columnJSON, null, 2))
	fs.writeFileSync(`./models/item/rock/polished_column/${rockType}.json`, JSON.stringify(item_model_polished_columnJSON, null, 2))

	fs.writeFileSync(`./models/item/rock/cobbled_bricks/${rockType}.json`, JSON.stringify(item_model_cobbled_bricksJSON, null, 2))
	fs.writeFileSync(`./models/item/rock/cobbled_bricks/slab/${rockType}.json`, JSON.stringify(item_model_cobbled_bricks_slabJSON, null, 2))
	fs.writeFileSync(`./models/item/rock/cobbled_bricks/stairs/${rockType}.json`, JSON.stringify(item_model_cobbled_bricks_stairsJSON, null, 2))
	fs.writeFileSync(`./models/item/rock/cobbled_bricks/wall/${rockType}.json`, JSON.stringify(item_model_cobbled_bricks_wallJSON, null, 2))

	fs.writeFileSync(`./blockstates/rock/mossy_cobble/slab/${rockType}.json`, JSON.stringify(bricks_slabJSON, null, 2))
	fs.writeFileSync(`./blockstates/rock/mossy_cobble/stairs/${rockType}.json`, JSON.stringify(bricks_stairsJSON, null, 2))
	fs.writeFileSync(`./blockstates/rock/mossy_cobble/wall/${rockType}.json`, JSON.stringify(bricks_wallJSON, null, 2))

	fs.writeFileSync(`./models/item/rock/mossy_cobble/slab/${rockType}.json`, JSON.stringify(item_bricks_slabJSON, null, 2))
	fs.writeFileSync(`./models/item/rock/mossy_cobble/stairs/${rockType}.json`, JSON.stringify(item_bricks_stairsJSON, null, 2))
	fs.writeFileSync(`./models/item/rock/mossy_cobble/wall/${rockType}.json`, JSON.stringify(item_bricks_wallJSON, null, 2))
}
