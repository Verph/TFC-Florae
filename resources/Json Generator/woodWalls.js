const fs = require('fs');

let WOOD_TYPES = {

    'african_padauk': 'african_padauk',
    'alder': 'alder',
    'angelim': 'angelim',
    'argyle_eucalyptus': 'argyle_eucalyptus',
    'bald_cypress': 'bald_cypress',
    'baobab': 'baobab',
    'beech': 'beech',
    'black_walnut': 'black_walnut',
    'black_willow': 'black_willow',
    'brazilwood': 'brazilwood',
    'butternut': 'butternut',
    'buxus': 'buxus',
    'cocobolo': 'cocobolo',
    'common_oak': 'common_oak',
    'cypress': 'cypress',
    'ebony': 'ebony',
    'fever': 'fever',
    'ginkgo': 'ginkgo',
    'greenheart': 'greenheart',
    'hawthorn': 'hawthorn',
    'hazel': 'hazel',
    'hemlock': 'hemlock',
    'holly': 'holly',
    'hornbeam': 'hornbeam',
    'iroko': 'iroko',
    'ironwood': 'ironwood',
    'jabuticabeira': 'jabuticabeira',
    'joshua': 'joshua',
    'juniper': 'juniper',
    'kauri': 'kauri',
    'larch': 'larch',
    'limba': 'limba',
    'locust': 'locust',
    'logwood': 'logwood',
    'maclura': 'maclura',
    'mahoe': 'mahoe',
    'mahogany': 'mahogany',
    'marblewood': 'marblewood',
    'medlar': 'medlar',
    'messmate': 'messmate',
    'mountain_ash': 'mountain_ash',
    'mulberry': 'mulberry',
    'nordmann_fir': 'nordmann_fir',
    'norway_spruce': 'norway_spruce',
    'pear': 'pear',
    'persimmon': 'persimmon',
    'pink_cherry_blossom': 'pink_cherry_blossom',
    'pink_ipe': 'pink_ipe',
    'pink_ivory': 'pink_ivory',
    'poplar': 'poplar',
    'purpleheart': 'purpleheart',
    'purple_ipe': 'purple_ipe',
    'purple_jacaranda': 'purple_jacaranda',
    'quince': 'quince',
    'rainbow_eucalyptus': 'rainbow_eucalyptus',
    'redwood': 'redwood',
    'red_cedar': 'red_cedar',
    'red_cypress': 'red_cypress',
    'red_elm': 'red_elm',
    'red_mangrove': 'red_mangrove',
    'rowan': 'rowan',
    'rubber_fig': 'rubber_fig',
    'sloe': 'sloe',
    'snow_gum_eucalyptus': 'snow_gum_eucalyptus',
    'sorb': 'sorb',
    'sweetgum': 'sweetgum',
    'syzygium': 'syzygium',
    'teak': 'teak',
    'walnut': 'walnut',
    'wenge': 'wenge',
    'whitebeam': 'whitebeam',
    'white_cherry_blossom': 'white_cherry_blossom',
    'white_elm': 'white_elm',
    'white_ipe': 'white_ipe',
    'white_jacaranda': 'white_jacaranda',
    'white_mangrove': 'white_mangrove',
    'yellow_ipe': 'yellow_ipe',
    'yellow_jacaranda': 'yellow_jacaranda',
    'yellow_meranti': 'yellow_meranti',
    'yew': 'yew',
    'zebrawood': 'zebrawood'
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
			"wall": `tfcflorae:block/wood/log/${woodType}`
		}
	}
  let woodWallPostWoodJSON = {
		"__comment__": "This file was automatically created by mcresources",
		"parent": "block/template_wall_post",
		"textures": {
			"wall": `tfcflorae:block/wood/log/${woodType}`
		}
	}
  let woodWallSideWoodJSON = {
		"__comment__": "This file was automatically created by mcresources",
		"parent": "block/template_wall_side",
		"textures": {
			"wall": `tfcflorae:block/wood/log/${woodType}`
		}
	}
  let woodWallPostTallWoodJSON = {
		"__comment__": "This file was automatically created by mcresources",
		"parent": "block/template_wall_side_tall",
		"textures": {
			"wall": `tfcflorae:block/wood/log/${woodType}`
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
			"top": `tfcflorae:block/wood/log_top/${woodType}`,
			"wall": `tfcflorae:block/wood/log/${woodType}`,
			"particle": `tfcflorae:block/wood/log/${woodType}`
		}
	}
  let woodWallPostLogJSON = {
		"__comment__": "This file was automatically created by mcresources",
		"parent": "tfcflorae:block/template_wall_post_log",
		"textures": {
			"top": `tfcflorae:block/wood/log_top/${woodType}`,
			"wall": `tfcflorae:block/wood/log/${woodType}`,
			"particle": `tfcflorae:block/wood/log/${woodType}`
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

	fs.writeFileSync(`./models/block/wood/wood_wall/inventory/${woodType}.json`, JSON.stringify(woodWallInventoryWoodJSON, null, 2))
	fs.writeFileSync(`./models/block/wood/wood_wall/post/${woodType}.json`, JSON.stringify(woodWallPostWoodJSON, null, 2))
	fs.writeFileSync(`./models/block/wood/wood_wall/side/${woodType}.json`, JSON.stringify(woodWallSideWoodJSON, null, 2))
	fs.writeFileSync(`./models/block/wood/wood_wall/side/tall/${woodType}.json`, JSON.stringify(woodWallPostTallWoodJSON, null, 2))
	fs.writeFileSync(`./models/item/wood/wood_wall/${woodType}.json`, JSON.stringify(woodWallItemWoodJSON, null, 2))

	fs.writeFileSync(`./models/block/wood/log_wall/inventory/${woodType}.json`, JSON.stringify(woodWallInventoryLogJSON, null, 2))
	fs.writeFileSync(`./models/block/wood/log_wall/post/${woodType}.json`, JSON.stringify(woodWallPostLogJSON, null, 2))
	fs.writeFileSync(`./models/block/wood/log_wall/side/${woodType}.json`, JSON.stringify(woodWallSideWoodJSON, null, 2))
	fs.writeFileSync(`./models/block/wood/log_wall/side/tall/${woodType}.json`, JSON.stringify(woodWallPostTallWoodJSON, null, 2))
	fs.writeFileSync(`./models/item/wood/log_wall/${woodType}.json`, JSON.stringify(woodWallItemWoodJSON, null, 2))

	fs.writeFileSync(`./tfc/blockstates/wood/wood_wall/${woodType}.json`, JSON.stringify(woodWallWoodBlockstateJSON, null, 2))
	fs.writeFileSync(`./tfc/blockstates/wood/log_wall/${woodType}.json`, JSON.stringify(woodWallLogBlockstateJSON, null, 2))
}
