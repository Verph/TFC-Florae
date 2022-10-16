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

	fs.writeFileSync(`./tfc/models/block/wood/wood_wall/inventory/${woodType}.json`, JSON.stringify(woodWallInventoryWoodJSON, null, 2))
	fs.writeFileSync(`./tfc/models/block/wood/wood_wall/post/${woodType}.json`, JSON.stringify(woodWallPostWoodJSON, null, 2))
	fs.writeFileSync(`./tfc/models/block/wood/wood_wall/side/${woodType}.json`, JSON.stringify(woodWallSideWoodJSON, null, 2))
	fs.writeFileSync(`./tfc/models/block/wood/wood_wall/side/tall/${woodType}.json`, JSON.stringify(woodWallPostTallWoodJSON, null, 2))
	fs.writeFileSync(`./tfc/models/item/wood/wood_wall/${woodType}.json`, JSON.stringify(woodWallItemWoodJSON, null, 2))

	fs.writeFileSync(`./tfc/models/block/wood/log_wall/inventory/${woodType}.json`, JSON.stringify(woodWallInventoryLogJSON, null, 2))
	fs.writeFileSync(`./tfc/models/block/wood/log_wall/post/${woodType}.json`, JSON.stringify(woodWallPostLogJSON, null, 2))
	fs.writeFileSync(`./tfc/models/block/wood/log_wall/side/${woodType}.json`, JSON.stringify(woodWallSideWoodJSON, null, 2))
	fs.writeFileSync(`./tfc/models/block/wood/log_wall/side/tall/${woodType}.json`, JSON.stringify(woodWallPostTallWoodJSON, null, 2))
	fs.writeFileSync(`./tfc/models/item/wood/log_wall/${woodType}.json`, JSON.stringify(woodWallItemWoodJSON, null, 2))

	fs.writeFileSync(`./tfc/blockstates/wood/wood_wall/${woodType}.json`, JSON.stringify(woodWallWoodBlockstateJSON, null, 2))
	fs.writeFileSync(`./tfc/blockstates/wood/log_wall/${woodType}.json`, JSON.stringify(woodWallLogBlockstateJSON, null, 2))
}
