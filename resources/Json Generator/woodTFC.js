const fs = require('fs');

let WOOD_TYPES = {

    'acacia': 'acacia',
    'ash': 'ash',
    'aspen': 'aspen',
    'birch': 'birch',
    'blackwood': 'blackwood',
    'chestnut': 'chestnut',
    'douglas_fir': 'douglas_fir',
    'hickory': 'hickory',
    'kapok': 'kapok',
    'maple': 'maple',
    'oak': 'oak',
    'palm': 'palm',
    'pine': 'pine',
    'rosewood': 'rosewood',
    'sequoia': 'sequoia',
    'spruce': 'spruce',
    'sycamore': 'sycamore',
    'white_cedar': 'white_cedar',
    'willow': 'willow'
}

for(let woodType of Object.keys(WOOD_TYPES))
{
    generateJSON(woodType)
}

function generateJSON(woodType)
{
  let woodWallInventoryWoodJSON = {
		"__comment__": "This file was automatically created by mcresources",
		"parent": "block/wall_inventory",
		"textures": {
			"wall": `tfc:block/wood/log/${woodType}`
		}
	}
  let woodWallPostWoodJSON = {
		"__comment__": "This file was automatically created by mcresources",
		"parent": "block/template_wall_post",
		"textures": {
			"wall": `tfc:block/wood/log/${woodType}`
		}
	}
  let woodWallSideWoodJSON = {
		"__comment__": "This file was automatically created by mcresources",
		"parent": "block/template_wall_side",
		"textures": {
			"wall": `tfc:block/wood/log/${woodType}`
		}
	}
  let woodWallPostTallWoodJSON = {
		"__comment__": "This file was automatically created by mcresources",
		"parent": "block/template_wall_side_tall",
		"textures": {
			"wall": `tfc:block/wood/log/${woodType}`
		}
	}
  let woodWallItemWoodJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "parent": `tfcflorae:block/wood/wood_wall/inventory/${woodType}`
  }

  let woodWallInventoryLogJSON = {
		"__comment__": "This file was automatically created by mcresources",
		"parent": "tfcflorae:block/wall_inventory_log",
		"textures": {
			"top": `tfc:block/wood/log_top/${woodType}`,
			"wall": `tfc:block/wood/log/${woodType}`,
			"particle": `tfc:block/wood/log/${woodType}`
		}
	}
  let woodWallPostLogJSON = {
		"__comment__": "This file was automatically created by mcresources",
		"parent": "tfcflorae:block/template_wall_post_log",
		"textures": {
			"top": `tfc:block/wood/log_top/${woodType}`,
			"wall": `tfc:block/wood/log/${woodType}`,
			"particle": `tfc:block/wood/log/${woodType}`
		}
	}

  let woodWallWoodBlockstateJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "multipart": [
      {
        "when": {
          "up": "true"
        },
        "apply": {
          "model": `tfcflorae:block/wood/wood_wall/post/${woodType}`
        }
      },
      {
        "when": {
          "north": "low"
        },
        "apply": {
          "model": `tfcflorae:block/wood/wood_wall/side/${woodType}`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "low"
        },
        "apply": {
          "model": `tfcflorae:block/wood/wood_wall/side/${woodType}`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "low"
        },
        "apply": {
          "model": `tfcflorae:block/wood/wood_wall/side/${woodType}`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "low"
        },
        "apply": {
          "model": `tfcflorae:block/wood/wood_wall/side/${woodType}`,
          "y": 270,
          "uvlock": true
        }
      },
      {
        "when": {
          "north": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/wood/wood_wall/side/tall/${woodType}`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/wood/wood_wall/side/tall/${woodType}`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/wood/wood_wall/side/tall/${woodType}`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/wood/wood_wall/side/tall/${woodType}`,
          "y": 270,
          "uvlock": true
        }
      }
    ]
  }
  let woodWallLogBlockstateJSON = {
    "__comment__": "This file was automatically created by mcresources",
    "multipart": [
      {
        "when": {
          "up": "true"
        },
        "apply": {
          "model": `tfcflorae:block/wood/log_wall/post/${woodType}`
        }
      },
      {
        "when": {
          "north": "low"
        },
        "apply": {
          "model": `tfcflorae:block/wood/log_wall/side/${woodType}`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "low"
        },
        "apply": {
          "model": `tfcflorae:block/wood/log_wall/side/${woodType}`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "low"
        },
        "apply": {
          "model": `tfcflorae:block/wood/log_wall/side/${woodType}`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "low"
        },
        "apply": {
          "model": `tfcflorae:block/wood/log_wall/side/${woodType}`,
          "y": 270,
          "uvlock": true
        }
      },
      {
        "when": {
          "north": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/wood/log_wall/side/tall/${woodType}`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/wood/log_wall/side/tall/${woodType}`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/wood/log_wall/side/tall/${woodType}`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "tall"
        },
        "apply": {
          "model": `tfcflorae:block/wood/log_wall/side/tall/${woodType}`,
          "y": 270,
          "uvlock": true
        }
      }
    ]
  }
  let chiseledBookshelfBlockstateJSON = {
    "variants": {
      "facing=north,books=0": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/0/${woodType}`
      },
      "facing=east,books=0": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/0/${woodType}`,
        "y": 90
      },
      "facing=south,books=0": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/0/${woodType}`,
        "y": 180
      },
      "facing=west,books=0": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/0/${woodType}`,
        "y": 270
      },
      "facing=up,books=0": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/0/${woodType}`,
        "x": 270
      },
      "facing=down,books=0": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/0/${woodType}`,
        "x": 90
      },
      "facing=north,books=1": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/1/${woodType}`
      },
      "facing=east,books=1": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/1/${woodType}`,
        "y": 90
      },
      "facing=south,books=1": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/1/${woodType}`,
        "y": 180
      },
      "facing=west,books=1": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/1/${woodType}`,
        "y": 270
      },
      "facing=up,books=1": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/1/${woodType}`,
        "x": 270
      },
      "facing=down,books=1": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/1/${woodType}`,
        "x": 90
      },
      "facing=north,books=2": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/2/${woodType}`
      },
      "facing=east,books=2": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/2/${woodType}`,
        "y": 90
      },
      "facing=south,books=2": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/2/${woodType}`,
        "y": 180
      },
      "facing=west,books=2": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/2/${woodType}`,
        "y": 270
      },
      "facing=up,books=2": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/2/${woodType}`,
        "x": 270
      },
      "facing=down,books=2": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/2/${woodType}`,
        "x": 90
      },
      "facing=north,books=3": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/3/${woodType}`
      },
      "facing=east,books=3": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/3/${woodType}`,
        "y": 90
      },
      "facing=south,books=3": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/3/${woodType}`,
        "y": 180
      },
      "facing=west,books=3": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/3/${woodType}`,
        "y": 270
      },
      "facing=up,books=3": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/3/${woodType}`,
        "x": 270
      },
      "facing=down,books=3": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/3/${woodType}`,
        "x": 90
      },
      "facing=north,books=4": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/4/${woodType}`
      },
      "facing=east,books=4": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/4/${woodType}`,
        "y": 90
      },
      "facing=south,books=4": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/4/${woodType}`,
        "y": 180
      },
      "facing=west,books=4": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/4/${woodType}`,
        "y": 270
      },
      "facing=up,books=4": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/4/${woodType}`,
        "x": 270
      },
      "facing=down,books=4": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/4/${woodType}`,
        "x": 90
      },
      "facing=north,books=5": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/5/${woodType}`
      },
      "facing=east,books=5": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/5/${woodType}`,
        "y": 90
      },
      "facing=south,books=5": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/5/${woodType}`,
        "y": 180
      },
      "facing=west,books=5": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/5/${woodType}`,
        "y": 270
      },
      "facing=up,books=5": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/5/${woodType}`,
        "x": 270
      },
      "facing=down,books=5": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/5/${woodType}`,
        "x": 90
      },
      "facing=north,books=6": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/6/${woodType}`
      },
      "facing=east,books=6": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/6/${woodType}`,
        "y": 90
      },
      "facing=south,books=6": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/6/${woodType}`,
        "y": 180
      },
      "facing=west,books=6": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/6/${woodType}`,
        "y": 270
      },
      "facing=up,books=6": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/6/${woodType}`,
        "x": 270
      },
      "facing=down,books=6": {
        "model": `tfcflorae:block/wood/chiseled_bookshelf/6/${woodType}`,
        "x": 90
      }
    }
  }
  let chiseledBookshelf0JSON = {
    "parent": "block/cube",
    "textures": {
      "down": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "up": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "north": `tfcflorae:block/wood/chiseled_bookshelf/0/${woodType}`,
      "east": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "south": `tfcflorae:block/wood/chiseled_bookshelf/back/${woodType}`,
      "west": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "particle": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`
    },
    "render_type": "solid"
  }
  let chiseledBookshelf1JSON = {
    "parent": "block/cube",
    "textures": {
      "down": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "up": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "north": `tfcflorae:block/wood/chiseled_bookshelf/1/${woodType}`,
      "east": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "south": `tfcflorae:block/wood/chiseled_bookshelf/back/${woodType}`,
      "west": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "particle": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`
    },
    "render_type": "solid"
  }
  let chiseledBookshelf2JSON = {
    "parent": "block/cube",
    "textures": {
      "down": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "up": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "north": `tfcflorae:block/wood/chiseled_bookshelf/2/${woodType}`,
      "east": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "south": `tfcflorae:block/wood/chiseled_bookshelf/back/${woodType}`,
      "west": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "particle": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`
    },
    "render_type": "solid"
  }
  let chiseledBookshelf3JSON = {
    "parent": "block/cube",
    "textures": {
      "down": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "up": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "north": `tfcflorae:block/wood/chiseled_bookshelf/3/${woodType}`,
      "east": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "south": `tfcflorae:block/wood/chiseled_bookshelf/back/${woodType}`,
      "west": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "particle": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`
    },
    "render_type": "solid"
  }
  let chiseledBookshelf4JSON = {
    "parent": "block/cube",
    "textures": {
      "down": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "up": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "north": `tfcflorae:block/wood/chiseled_bookshelf/4/${woodType}`,
      "east": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "south": `tfcflorae:block/wood/chiseled_bookshelf/back/${woodType}`,
      "west": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "particle": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`
    },
    "render_type": "solid"
  }
  let chiseledBookshelf5JSON = {
    "parent": "block/cube",
    "textures": {
      "down": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "up": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "north": `tfcflorae:block/wood/chiseled_bookshelf/5/${woodType}`,
      "east": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "south": `tfcflorae:block/wood/chiseled_bookshelf/back/${woodType}`,
      "west": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "particle": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`
    },
    "render_type": "solid"
  }
  let chiseledBookshelf6JSON = {
    "parent": "block/cube",
    "textures": {
      "down": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "up": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`,
      "north": `tfcflorae:block/wood/chiseled_bookshelf/6/${woodType}`,
      "east": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "south": `tfcflorae:block/wood/chiseled_bookshelf/back/${woodType}`,
      "west": `tfcflorae:block/wood/chiseled_bookshelf/side/${woodType}`,
      "particle": `tfcflorae:block/wood/chiseled_bookshelf/top/${woodType}`
    },
    "render_type": "solid"
  }
  let chiseledBookshelfItemJSON = {
    "parent": `tfcflorae:block/wood/chiseled_bookshelf/6/${woodType}`
  }
  let recipeLogWall = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XYX",
      "XYX"
    ],
    "key": {
      "X": {
        "item": `tfc:wood/log/${woodType}`
      },
      "Y": {
        "item": `tfc:wood/lumber/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/log_wall/${woodType}`,
      "count": 8
    }
  }
  let recipeWoodWall = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XYX",
      "XYX"
    ],
    "key": {
      "X": {
        "item": `tfc:wood/wood/${woodType}`
      },
      "Y": {
        "item": `tfc:wood/lumber/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/wood_wall/${woodType}`,
      "count": 8
    }
  }
  let recipeChiseledBookshelf = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "012",
      "345",
      "678"
    ],
    "key": {
      "0": {
        "item": `tfc:wood/planks/${woodType}`
      },
      "1": {
        "item": `tfc:wood/planks/${woodType}`
      },
      "2": {
        "item": `tfc:wood/planks/${woodType}`
      },
      "3": {
        "item": `tfc:wood/slab/${woodType}`
      },
      "4": {
        "item": `tfc:wood/slab/${woodType}`
      },
      "5": {
        "item": `tfc:wood/slab/${woodType}`
      },
      "6": {
        "item": `tfc:wood/planks/${woodType}`
      },
      "7": {
        "item": `tfc:wood/planks/${woodType}`
      },
      "8": {
        "item": `tfc:wood/planks/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/chiseled_bookshelf/${woodType}`,
      "count": 1
    }
  }
  let lootLogWall = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/log_wall/${woodType}`
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
  let lootWoodWall = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/wood_wall/${woodType}`
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
  let lootChiseledBookshelf = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/chiseled_bookshelf/${woodType}`
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
  /*let fallingLeavesJSON = {
    "spawnrate": 0.3,
    "isConifer": false
  }*/

	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/wood_wall/inventory/${woodType}.json`, JSON.stringify(woodWallInventoryWoodJSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/wood_wall/post/${woodType}.json`, JSON.stringify(woodWallPostWoodJSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/wood_wall/side/${woodType}.json`, JSON.stringify(woodWallSideWoodJSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/wood_wall/side/tall/${woodType}.json`, JSON.stringify(woodWallPostTallWoodJSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/item/wood/wood_wall/${woodType}.json`, JSON.stringify(woodWallItemWoodJSON, null, 2))

	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/log_wall/inventory/${woodType}.json`, JSON.stringify(woodWallInventoryLogJSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/log_wall/post/${woodType}.json`, JSON.stringify(woodWallPostLogJSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/log_wall/side/${woodType}.json`, JSON.stringify(woodWallSideWoodJSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/log_wall/side/tall/${woodType}.json`, JSON.stringify(woodWallPostTallWoodJSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/item/wood/log_wall/${woodType}.json`, JSON.stringify(woodWallItemWoodJSON, null, 2))

	fs.writeFileSync(`./assets/tfcflorae/blockstates/wood/wood_wall/${woodType}.json`, JSON.stringify(woodWallWoodBlockstateJSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/blockstates/wood/log_wall/${woodType}.json`, JSON.stringify(woodWallLogBlockstateJSON, null, 2))

	fs.writeFileSync(`./data/tfcflorae/recipes/crafting/wood/log_wall/${woodType}.json`, JSON.stringify(recipeLogWall, null, 2))
	fs.writeFileSync(`./data/tfcflorae/recipes/crafting/wood/wood_wall/${woodType}.json`, JSON.stringify(recipeWoodWall, null, 2))
	//fs.writeFileSync(`./data/tfcflorae/recipes/crafting/wood/chiseled_bookshelf/${woodType}.json`, JSON.stringify(recipeChiseledBookshelf, null, 2))

	fs.writeFileSync(`./data/tfcflorae/loot_tables/blocks/wood/log_wall/${woodType}.json`, JSON.stringify(lootLogWall, null, 2))
	fs.writeFileSync(`./data/tfcflorae/loot_tables/blocks/wood/wood_wall/${woodType}.json`, JSON.stringify(lootWoodWall, null, 2))
	//fs.writeFileSync(`./data/tfcflorae/loot_tables/blocks/wood/chiseled_bookshelf/${woodType}.json`, JSON.stringify(lootChiseledBookshelf, null, 2))

	fs.writeFileSync(`./assets/tfcflorae/blockstates/wood/chiseled_bookshelf/${woodType}.json`, JSON.stringify(chiseledBookshelfBlockstateJSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/chiseled_bookshelf/0/${woodType}.json`, JSON.stringify(chiseledBookshelf0JSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/chiseled_bookshelf/1/${woodType}.json`, JSON.stringify(chiseledBookshelf1JSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/chiseled_bookshelf/2/${woodType}.json`, JSON.stringify(chiseledBookshelf2JSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/chiseled_bookshelf/3/${woodType}.json`, JSON.stringify(chiseledBookshelf3JSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/chiseled_bookshelf/4/${woodType}.json`, JSON.stringify(chiseledBookshelf4JSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/chiseled_bookshelf/5/${woodType}.json`, JSON.stringify(chiseledBookshelf5JSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/block/wood/chiseled_bookshelf/6/${woodType}.json`, JSON.stringify(chiseledBookshelf6JSON, null, 2))
	fs.writeFileSync(`./assets/tfcflorae/models/item/wood/chiseled_bookshelf/${woodType}.json`, JSON.stringify(chiseledBookshelfItemJSON, null, 2))

	//fs.writeFileSync(`./data/fallingLeaves/${woodType}.json`, JSON.stringify(fallingLeavesJSON, null, 2))
}
