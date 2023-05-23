const fs = require('fs');
const fse = require('fs-extra')

let WOOD_TYPES = {

    'african_padauk': 'african_padauk',
    'alder': 'alder',
    'angelim': 'angelim',
    'argyle_eucalyptus': 'argyle_eucalyptus',
    'bald_cypress': 'bald_cypress',
    'baobab': 'baobab',
    'bamboo': 'bamboo',
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
    'ghaf': 'ghaf',
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
    'laurel': 'laurel',
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

let WOOD_TYPES_TFC = {

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

for(let woodTypeTFC of Object.keys(WOOD_TYPES_TFC))
{
    generateJSONTFC(woodTypeTFC)
}

function generateJSON(woodType)
{
  let blockstateStrippedLog = {
    "variants": {
      "axis=y": {
        "model": `tfcflorae:block/wood/stripped_log/${woodType}`
      },
      "axis=z": {
        "model": `tfcflorae:block/wood/stripped_log/${woodType}`,
        "x": 90
      },
      "axis=x": {
        "model": `tfcflorae:block/wood/stripped_log/${woodType}`,
        "x": 90,
        "y": 90
      }
    }
  }
  let blockstateStrippedWood = {
    "variants": {
      "axis=y": {
        "model": `tfcflorae:block/wood/stripped_wood/${woodType}`
      },
      "axis=z": {
        "model": `tfcflorae:block/wood/stripped_wood/${woodType}`,
        "x": 90
      },
      "axis=x": {
        "model": `tfcflorae:block/wood/stripped_wood/${woodType}`,
        "x": 90,
        "y": 90
      }
    }
  }
  let blockstateWood = {
    "variants": {
      "axis=y": {
        "model": `tfcflorae:block/wood/wood/${woodType}`
      },
      "axis=z": {
        "model": `tfcflorae:block/wood/wood/${woodType}`,
        "x": 90
      },
      "axis=x": {
        "model": `tfcflorae:block/wood/wood/${woodType}`,
        "x": 90,
        "y": 90
      }
    }
  }
  let blockstateLog = {
    "variants": {
      "axis=y": {
        "model": `tfcflorae:block/wood/log/${woodType}`
      },
      "axis=z": {
        "model": `tfcflorae:block/wood/log/${woodType}`,
        "x": 90
      },
      "axis=x": {
        "model": `tfcflorae:block/wood/log/${woodType}`,
        "x": 90,
        "y": 90
      }
    }
  }
  let blockstateTwig = {
    "variants": {
      "": [
        {
          "model": `tfcflorae:block/wood/twig/${woodType}`,
          "y": 90
        },
        {
          "model": `tfcflorae:block/wood/twig/${woodType}`
        },
        {
          "model": `tfcflorae:block/wood/twig/${woodType}`,
          "y": 180
        },
        {
          "model": `tfcflorae:block/wood/twig/${woodType}`,
          "y": 270
        }
      ]
    }
  }
  let blockstateBarrel = {
    "variants": {
      "sealed=true": {
        "model": `tfcflorae:block/wood/barrel_sealed/${woodType}`
      },
      "sealed=false": {
        "model": `tfcflorae:block/wood/barrel/${woodType}`
      }
    }
  }
  let blockstateFallenLeaves = {
    "variants": {
      "": [
        {
          "model": `tfcflorae:block/wood/fallen_leaves/${woodType}`,
          "y": 90
        },
        {
          "model": `tfcflorae:block/wood/fallen_leaves/${woodType}`
        },
        {
          "model": `tfcflorae:block/wood/fallen_leaves/${woodType}`,
          "y": 180
        },
        {
          "model": `tfcflorae:block/wood/fallen_leaves/${woodType}`,
          "y": 270
        }
      ]
    }
  }
  let blockstateHorizontalSupport = {
    "multipart": [
      {
        "apply": {
          "model": `tfcflorae:block/wood/support/${woodType}_horizontal`
        }
      },
      {
        "when": {
          "north": true
        },
        "apply": {
          "model": `tfcflorae:block/wood/support/${woodType}_connection`,
          "y": 270
        }
      },
      {
        "when": {
          "east": true
        },
        "apply": {
          "model": `tfcflorae:block/wood/support/${woodType}_connection`
        }
      },
      {
        "when": {
          "south": true
        },
        "apply": {
          "model": `tfcflorae:block/wood/support/${woodType}_connection`,
          "y": 90
        }
      },
      {
        "when": {
          "west": true
        },
        "apply": {
          "model": `tfcflorae:block/wood/support/${woodType}_connection`,
          "y": 180
        }
      }
    ]
  }
  let blockstateLeaves = {
    "variants": {
      "": {
        "model": `tfcflorae:block/wood/leaves/${woodType}`
      }
    }
  }
  let blockstateChest = {
    "variants": {
      "": {
        "model": `tfcflorae:block/wood/chest/${woodType}`
      }
    }
  }
  let blockstateLectern = {
    "variants": {
      "facing=east": {
        "model": `tfcflorae:block/wood/lectern/${woodType}`,
        "y": 90
      },
      "facing=north": {
        "model": `tfcflorae:block/wood/lectern/${woodType}`
      },
      "facing=south": {
        "model": `tfcflorae:block/wood/lectern/${woodType}`,
        "y": 180
      },
      "facing=west": {
        "model": `tfcflorae:block/wood/lectern/${woodType}`,
        "y": 270
      }
    }
  }
  let blockstateSapling = {
    "variants": {
      "": {
        "model": `tfcflorae:block/wood/sapling/${woodType}`
      }
    }
  }
  let blockstateScribingTable = {
    "variants": {
      "facing=east": {
        "model": `tfcflorae:block/wood/scribing_table/${woodType}`,
        "y": 90
      },
      "facing=north": {
        "model": `tfcflorae:block/wood/scribing_table/${woodType}`
      },
      "facing=south": {
        "model": `tfcflorae:block/wood/scribing_table/${woodType}`,
        "y": 180
      },
      "facing=west": {
        "model": `tfcflorae:block/wood/scribing_table/${woodType}`,
        "y": 270
      }
    }
  }
  let blockstateSluice = {
    "variants": {
      "facing=east,upper=true": {
        "model": `tfcflorae:block/wood/sluice/${woodType}_upper`,
        "y": 90
      },
      "facing=north,upper=true": {
        "model": `tfcflorae:block/wood/sluice/${woodType}_upper`,
        "y": 0
      },
      "facing=south,upper=true": {
        "model": `tfcflorae:block/wood/sluice/${woodType}_upper`,
        "y": 180
      },
      "facing=west,upper=true": {
        "model": `tfcflorae:block/wood/sluice/${woodType}_upper`,
        "y": 270
      },
      "facing=east,upper=false": {
        "model": `tfcflorae:block/wood/sluice/${woodType}_lower`,
        "y": 90
      },
      "facing=north,upper=false": {
        "model": `tfcflorae:block/wood/sluice/${woodType}_lower`,
        "y": 0
      },
      "facing=south,upper=false": {
        "model": `tfcflorae:block/wood/sluice/${woodType}_lower`,
        "y": 180
      },
      "facing=west,upper=false": {
        "model": `tfcflorae:block/wood/sluice/${woodType}_lower`,
        "y": 270
      }
    }
  }
  let blockstateTrappedChest = {
    "variants": {
      "": {
        "model": `tfcflorae:block/wood/trapped_chest/${woodType}`
      }
    }
  }
  let blockstateVerticalSupport = {
    "multipart": [
      {
        "apply": {
          "model": `tfcflorae:block/wood/support/${woodType}_vertical`
        }
      },
      {
        "when": {
          "north": true
        },
        "apply": {
          "model": `tfcflorae:block/wood/support/${woodType}_connection`,
          "y": 270
        }
      },
      {
        "when": {
          "east": true
        },
        "apply": {
          "model": `tfcflorae:block/wood/support/${woodType}_connection`
        }
      },
      {
        "when": {
          "south": true
        },
        "apply": {
          "model": `tfcflorae:block/wood/support/${woodType}_connection`,
          "y": 90
        }
      },
      {
        "when": {
          "west": true
        },
        "apply": {
          "model": `tfcflorae:block/wood/support/${woodType}_connection`,
          "y": 180
        }
      }
    ]
  }
  let blockstatePlanks = {
    "variants": {
      "": {
        "model": `tfcflorae:block/wood/planks/${woodType}`
      }
    }
  }
  let blockstateBookshelf = {
    "variants": {
      "": {
        "model": `tfcflorae:block/wood/bookshelf/${woodType}`
      }
    }
  }
  let blockstateButton = {
    "variants": {
      "face=floor,facing=east,powered=false": {
        "model": `tfcflorae:block/wood/button/${woodType}`,
        "y": 90
      },
      "face=floor,facing=west,powered=false": {
        "model": `tfcflorae:block/wood/button/${woodType}`,
        "y": 270
      },
      "face=floor,facing=south,powered=false": {
        "model": `tfcflorae:block/wood/button/${woodType}`,
        "y": 180
      },
      "face=floor,facing=north,powered=false": {
        "model": `tfcflorae:block/wood/button/${woodType}`
      },
      "face=wall,facing=east,powered=false": {
        "model": `tfcflorae:block/wood/button/${woodType}`,
        "uvlock": true,
        "x": 90,
        "y": 90
      },
      "face=wall,facing=west,powered=false": {
        "model": `tfcflorae:block/wood/button/${woodType}`,
        "uvlock": true,
        "x": 90,
        "y": 270
      },
      "face=wall,facing=south,powered=false": {
        "model": `tfcflorae:block/wood/button/${woodType}`,
        "uvlock": true,
        "x": 90,
        "y": 180
      },
      "face=wall,facing=north,powered=false": {
        "model": `tfcflorae:block/wood/button/${woodType}`,
        "uvlock": true,
        "x": 90
      },
      "face=ceiling,facing=east,powered=false": {
        "model": `tfcflorae:block/wood/button/${woodType}`,
        "x": 180,
        "y": 270
      },
      "face=ceiling,facing=west,powered=false": {
        "model": `tfcflorae:block/wood/button/${woodType}`,
        "x": 180,
        "y": 90
      },
      "face=ceiling,facing=south,powered=false": {
        "model": `tfcflorae:block/wood/button/${woodType}`,
        "x": 180
      },
      "face=ceiling,facing=north,powered=false": {
        "model": `tfcflorae:block/wood/button/${woodType}`,
        "x": 180,
        "y": 180
      },
      "face=floor,facing=east,powered=true": {
        "model": `tfcflorae:block/wood/button/${woodType}_pressed`,
        "y": 90
      },
      "face=floor,facing=west,powered=true": {
        "model": `tfcflorae:block/wood/button/${woodType}_pressed`,
        "y": 270
      },
      "face=floor,facing=south,powered=true": {
        "model": `tfcflorae:block/wood/button/${woodType}_pressed`,
        "y": 180
      },
      "face=floor,facing=north,powered=true": {
        "model": `tfcflorae:block/wood/button/${woodType}_pressed`
      },
      "face=wall,facing=east,powered=true": {
        "model": `tfcflorae:block/wood/button/${woodType}_pressed`,
        "uvlock": true,
        "x": 90,
        "y": 90
      },
      "face=wall,facing=west,powered=true": {
        "model": `tfcflorae:block/wood/button/${woodType}_pressed`,
        "uvlock": true,
        "x": 90,
        "y": 270
      },
      "face=wall,facing=south,powered=true": {
        "model": `tfcflorae:block/wood/button/${woodType}_pressed`,
        "uvlock": true,
        "x": 90,
        "y": 180
      },
      "face=wall,facing=north,powered=true": {
        "model": `tfcflorae:block/wood/button/${woodType}_pressed`,
        "uvlock": true,
        "x": 90
      },
      "face=ceiling,facing=east,powered=true": {
        "model": `tfcflorae:block/wood/button/${woodType}_pressed`,
        "x": 180,
        "y": 270
      },
      "face=ceiling,facing=west,powered=true": {
        "model": `tfcflorae:block/wood/button/${woodType}_pressed`,
        "x": 180,
        "y": 90
      },
      "face=ceiling,facing=south,powered=true": {
        "model": `tfcflorae:block/wood/button/${woodType}_pressed`,
        "x": 180
      },
      "face=ceiling,facing=north,powered=true": {
        "model": `tfcflorae:block/wood/button/${woodType}_pressed`,
        "x": 180,
        "y": 180
      }
    }
  }
  let blockstateDoor = {
    "variants": {
      "facing=east,half=lower,hinge=left,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom`
      },
      "facing=south,half=lower,hinge=left,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom`,
        "y": 90
      },
      "facing=west,half=lower,hinge=left,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom`,
        "y": 180
      },
      "facing=north,half=lower,hinge=left,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom`,
        "y": 270
      },
      "facing=east,half=lower,hinge=right,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom_hinge`
      },
      "facing=south,half=lower,hinge=right,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom_hinge`,
        "y": 90
      },
      "facing=west,half=lower,hinge=right,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom_hinge`,
        "y": 180
      },
      "facing=north,half=lower,hinge=right,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom_hinge`,
        "y": 270
      },
      "facing=east,half=lower,hinge=left,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom_hinge`,
        "y": 90
      },
      "facing=south,half=lower,hinge=left,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom_hinge`,
        "y": 180
      },
      "facing=west,half=lower,hinge=left,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom_hinge`,
        "y": 270
      },
      "facing=north,half=lower,hinge=left,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom_hinge`
      },
      "facing=east,half=lower,hinge=right,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom`,
        "y": 270
      },
      "facing=south,half=lower,hinge=right,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom`
      },
      "facing=west,half=lower,hinge=right,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom`,
        "y": 90
      },
      "facing=north,half=lower,hinge=right,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_bottom`,
        "y": 180
      },
      "facing=east,half=upper,hinge=left,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_top`
      },
      "facing=south,half=upper,hinge=left,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_top`,
        "y": 90
      },
      "facing=west,half=upper,hinge=left,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_top`,
        "y": 180
      },
      "facing=north,half=upper,hinge=left,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_top`,
        "y": 270
      },
      "facing=east,half=upper,hinge=right,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_top_hinge`
      },
      "facing=south,half=upper,hinge=right,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_top_hinge`,
        "y": 90
      },
      "facing=west,half=upper,hinge=right,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_top_hinge`,
        "y": 180
      },
      "facing=north,half=upper,hinge=right,open=false": {
        "model": `tfcflorae:block/wood/door/${woodType}_top_hinge`,
        "y": 270
      },
      "facing=east,half=upper,hinge=left,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_top_hinge`,
        "y": 90
      },
      "facing=south,half=upper,hinge=left,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_top_hinge`,
        "y": 180
      },
      "facing=west,half=upper,hinge=left,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_top_hinge`,
        "y": 270
      },
      "facing=north,half=upper,hinge=left,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_top_hinge`
      },
      "facing=east,half=upper,hinge=right,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_top`,
        "y": 270
      },
      "facing=south,half=upper,hinge=right,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_top`
      },
      "facing=west,half=upper,hinge=right,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_top`,
        "y": 90
      },
      "facing=north,half=upper,hinge=right,open=true": {
        "model": `tfcflorae:block/wood/door/${woodType}_top`,
        "y": 180
      }
    }
  }
  let blockstateFence = {
    "multipart": [
      {
        "apply": {
          "model": `tfcflorae:block/wood/fence/${woodType}_post`
        }
      },
      {
        "when": {
          "north": "true"
        },
        "apply": {
          "model": `tfcflorae:block/wood/fence/${woodType}_side`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "true"
        },
        "apply": {
          "model": `tfcflorae:block/wood/fence/${woodType}_side`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "true"
        },
        "apply": {
          "model": `tfcflorae:block/wood/fence/${woodType}_side`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "true"
        },
        "apply": {
          "model": `tfcflorae:block/wood/fence/${woodType}_side`,
          "y": 270,
          "uvlock": true
        }
      }
    ]
  }
  let blockstateFenceGate = {
    "variants": {
      "facing=south,in_wall=false,open=false": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}`,
        "uvlock": true
      },
      "facing=west,in_wall=false,open=false": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}`,
        "uvlock": true,
        "y": 90
      },
      "facing=north,in_wall=false,open=false": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}`,
        "uvlock": true,
        "y": 180
      },
      "facing=east,in_wall=false,open=false": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}`,
        "uvlock": true,
        "y": 270
      },
      "facing=south,in_wall=false,open=true": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}_open`,
        "uvlock": true
      },
      "facing=west,in_wall=false,open=true": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}_open`,
        "uvlock": true,
        "y": 90
      },
      "facing=north,in_wall=false,open=true": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}_open`,
        "uvlock": true,
        "y": 180
      },
      "facing=east,in_wall=false,open=true": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}_open`,
        "uvlock": true,
        "y": 270
      },
      "facing=south,in_wall=true,open=false": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}_wall`,
        "uvlock": true
      },
      "facing=west,in_wall=true,open=false": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}_wall`,
        "uvlock": true,
        "y": 90
      },
      "facing=north,in_wall=true,open=false": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}_wall`,
        "uvlock": true,
        "y": 180
      },
      "facing=east,in_wall=true,open=false": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}_wall`,
        "uvlock": true,
        "y": 270
      },
      "facing=south,in_wall=true,open=true": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}_wall_open`,
        "uvlock": true
      },
      "facing=west,in_wall=true,open=true": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}_wall_open`,
        "uvlock": true,
        "y": 90
      },
      "facing=north,in_wall=true,open=true": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}_wall_open`,
        "uvlock": true,
        "y": 180
      },
      "facing=east,in_wall=true,open=true": {
        "model": `tfcflorae:block/wood/fence_gate/${woodType}_wall_open`,
        "uvlock": true,
        "y": 270
      }
    }
  }
  let blockstateLogFence = {
    "multipart": [
      {
        "apply": {
          "model": `tfcflorae:block/wood/log_fence/${woodType}_post`
        }
      },
      {
        "when": {
          "north": "true"
        },
        "apply": {
          "model": `tfcflorae:block/wood/log_fence/${woodType}_side`,
          "uvlock": true
        }
      },
      {
        "when": {
          "east": "true"
        },
        "apply": {
          "model": `tfcflorae:block/wood/log_fence/${woodType}_side`,
          "y": 90,
          "uvlock": true
        }
      },
      {
        "when": {
          "south": "true"
        },
        "apply": {
          "model": `tfcflorae:block/wood/log_fence/${woodType}_side`,
          "y": 180,
          "uvlock": true
        }
      },
      {
        "when": {
          "west": "true"
        },
        "apply": {
          "model": `tfcflorae:block/wood/log_fence/${woodType}_side`,
          "y": 270,
          "uvlock": true
        }
      }
    ]
  }
  let blockstateLoom = {
    "variants": {
      "facing=east": {
        "model": `tfcflorae:block/wood/loom/${woodType}`,
        "y": 270
      },
      "facing=north": {
        "model": `tfcflorae:block/wood/loom/${woodType}`,
        "y": 180
      },
      "facing=south": {
        "model": `tfcflorae:block/wood/loom/${woodType}`
      },
      "facing=west": {
        "model": `tfcflorae:block/wood/loom/${woodType}`,
        "y": 90
      }
    }
  }
  let blockstatePressurePlate = {
    "variants": {
      "powered=false": {
        "model": `tfcflorae:block/wood/pressure_plate/${woodType}`
      },
      "powered=true": {
        "model": `tfcflorae:block/wood/pressure_plate/${woodType}_down`
      }
    }
  }
  let blockstateSign = {
    "variants": {
      "": {
        "model": `tfcflorae:block/wood/sign/${woodType}`
      }
    }
  }
  let blockstateSlab = {
    "variants": {
      "type=bottom": {
        "model": `tfcflorae:block/wood/slab/${woodType}`
      },
      "type=top": {
        "model": `tfcflorae:block/wood/slab/${woodType}_top`
      },
      "type=double": {
        "model": `tfcflorae:block/wood/planks/${woodType}`
      }
    }
  }
  let blockstateStairs = {
    "variants": {
      "facing=east,half=bottom,shape=straight": {
        "model": `tfcflorae:block/wood/stairs/${woodType}`
      },
      "facing=west,half=bottom,shape=straight": {
        "model": `tfcflorae:block/wood/stairs/${woodType}`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=straight": {
        "model": `tfcflorae:block/wood/stairs/${woodType}`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=straight": {
        "model": `tfcflorae:block/wood/stairs/${woodType}`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`
      },
      "facing=west,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=outer_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "y": 270,
        "uvlock": true
      },
      "facing=west,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "y": 90,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`
      },
      "facing=north,half=bottom,shape=outer_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "y": 180,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`
      },
      "facing=west,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=bottom,shape=inner_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "y": 270,
        "uvlock": true
      },
      "facing=west,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "y": 90,
        "uvlock": true
      },
      "facing=south,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`
      },
      "facing=north,half=bottom,shape=inner_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "y": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=straight": {
        "model": `tfcflorae:block/wood/stairs/${woodType}`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=straight": {
        "model": `tfcflorae:block/wood/stairs/${woodType}`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=straight": {
        "model": `tfcflorae:block/wood/stairs/${woodType}`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=straight": {
        "model": `tfcflorae:block/wood/stairs/${woodType}`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=top,shape=outer_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=west,half=top,shape=outer_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=south,half=top,shape=outer_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=north,half=top,shape=outer_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "x": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=outer_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=outer_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=outer_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=outer_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_outer`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=east,half=top,shape=inner_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=west,half=top,shape=inner_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "x": 180,
        "y": 270,
        "uvlock": true
      },
      "facing=south,half=top,shape=inner_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=north,half=top,shape=inner_right": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "x": 180,
        "uvlock": true
      },
      "facing=east,half=top,shape=inner_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "x": 180,
        "uvlock": true
      },
      "facing=west,half=top,shape=inner_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "x": 180,
        "y": 180,
        "uvlock": true
      },
      "facing=south,half=top,shape=inner_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "x": 180,
        "y": 90,
        "uvlock": true
      },
      "facing=north,half=top,shape=inner_left": {
        "model": `tfcflorae:block/wood/stairs/${woodType}_inner`,
        "x": 180,
        "y": 270,
        "uvlock": true
      }
    }
  }
  let blockstateToolRack = {
    "variants": {
      "facing=east": {
        "model": `tfcflorae:block/wood/tool_rack/${woodType}`,
        "y": 270
      },
      "facing=north": {
        "model": `tfcflorae:block/wood/tool_rack/${woodType}`,
        "y": 180
      },
      "facing=south": {
        "model": `tfcflorae:block/wood/tool_rack/${woodType}`
      },
      "facing=west": {
        "model": `tfcflorae:block/wood/tool_rack/${woodType}`,
        "y": 90
      }
    }
  }
  let blockstateTrapdoor = {
    "variants": {
      "facing=north,half=bottom,open=false": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_bottom`
      },
      "facing=south,half=bottom,open=false": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_bottom`,
        "y": 180
      },
      "facing=east,half=bottom,open=false": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_bottom`,
        "y": 90
      },
      "facing=west,half=bottom,open=false": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_bottom`,
        "y": 270
      },
      "facing=north,half=top,open=false": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_top`
      },
      "facing=south,half=top,open=false": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_top`,
        "y": 180
      },
      "facing=east,half=top,open=false": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_top`,
        "y": 90
      },
      "facing=west,half=top,open=false": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_top`,
        "y": 270
      },
      "facing=north,half=bottom,open=true": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_open`
      },
      "facing=south,half=bottom,open=true": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_open`,
        "y": 180
      },
      "facing=east,half=bottom,open=true": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_open`,
        "y": 90
      },
      "facing=west,half=bottom,open=true": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_open`,
        "y": 270
      },
      "facing=north,half=top,open=true": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_open`,
        "x": 180,
        "y": 180
      },
      "facing=south,half=top,open=true": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_open`,
        "x": 180,
        "y": 0
      },
      "facing=east,half=top,open=true": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_open`,
        "x": 180,
        "y": 270
      },
      "facing=west,half=top,open=true": {
        "model": `tfcflorae:block/wood/trapdoor/${woodType}_open`,
        "x": 180,
        "y": 90
      }
    }
  }
  let blockstateWallSign = {
    "variants": {
      "": {
        "model": `tfcflorae:block/wood/sign/${woodType}`
      }
    }
  }
  let blockstateWorkbench = {
    "variants": {
      "": {
        "model": `tfcflorae:block/wood/workbench/${woodType}`
      }
    }
  }
  let blockstatePottedSapling = {
    "variants": {
      "": {
        "model": `tfcflorae:block/wood/potted_sapling/${woodType}`
      }
    }
  }

  // Block models
  let blockmodelStrippedWood = {
    "parent": "block/cube_column",
    "textures": {
      "end": `tfcflorae:block/wood/stripped_log/${woodType}`,
      "side": `tfcflorae:block/wood/stripped_log/${woodType}`
    }
  }
  let blockmodelStrippedLog = {
    "parent": "block/cube_column",
    "textures": {
      "end": `tfcflorae:block/wood/stripped_log_top/${woodType}`,
      "side": `tfcflorae:block/wood/stripped_log/${woodType}`
    }
  }
  let blockmodelWood = {
    "parent": "block/cube_column",
    "textures": {
      "end": `tfcflorae:block/wood/log/${woodType}`,
      "side": `tfcflorae:block/wood/log/${woodType}`
    }
  }
  let blockmodelLog = {
    "parent": "block/cube_column",
    "textures": {
      "end": `tfcflorae:block/wood/log_top/${woodType}`,
      "side": `tfcflorae:block/wood/log/${woodType}`
    }
  }
  let blockmodelTwig = {
    "parent": "tfc:block/groundcover/twig",
    "textures": {
      "side": `tfcflorae:block/wood/log/${woodType}`,
      "top": `tfcflorae:block/wood/log_top/${woodType}`
    }
  }
  let blockmodelBarrel = {
    "parent": "tfc:block/barrel",
    "textures": {
      "particle": `tfcflorae:block/wood/planks/${woodType}`,
      "planks": `tfcflorae:block/wood/planks/${woodType}`,
      "sheet": `tfcflorae:block/wood/sheet/${woodType}`,
      "hoop": "tfc:block/barrel_hoop"
    }
  }
  let blockmodelBarrelSealed = {
    "parent": "tfc:block/barrel_sealed",
    "textures": {
      "particle": `tfcflorae:block/wood/planks/${woodType}`,
      "planks": `tfcflorae:block/wood/planks/${woodType}`,
      "sheet": `tfcflorae:block/wood/sheet/${woodType}`,
      "hoop": "tfc:block/barrel_hoop"
    }
  }
  let blockmodelChest = {
    "textures": {
      "particle": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelFallenLeaves = {
    "parent": "tfc:block/groundcover/fallen_leaves",
    "textures": {
      "all": `tfcflorae:block/wood/leaves/${woodType}`
    }
  }
  let blockmodelLeaves = {
    "parent": "block/leaves",
    "textures": {
      "all": `tfcflorae:block/wood/leaves/${woodType}`
    }
  }
  let blockmodelLectern = {
    "parent": "minecraft:block/lectern",
    "textures": {
      "bottom": `tfcflorae:block/wood/planks/${woodType}`,
      "base": `tfcflorae:block/wood/lectern/base/${woodType}`,
      "front": `tfcflorae:block/wood/lectern/front/${woodType}`,
      "sides": `tfcflorae:block/wood/lectern/sides/${woodType}`,
      "top": `tfcflorae:block/wood/lectern/top/${woodType}`,
      "particle": `tfcflorae:block/wood/lectern/sides/${woodType}`
    }
  }
  let blockmodelSapling = {
    "parent": "block/cross",
    "textures": {
      "cross": `tfcflorae:block/wood/sapling/${woodType}`
    }
  }
  let blockmodelScribingTable = {
    "parent": "tfc:block/scribing_table",
    "textures": {
      "top": `tfcflorae:block/wood/scribing_table/${woodType}`,
      "leg": `tfcflorae:block/wood/log/${woodType}`,
      "side": `tfcflorae:block/wood/planks/${woodType}`,
      "misc": "tfc:block/wood/scribing_table/scribing_paraphernalia",
      "particle": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelSluiceLower = {
    "parent": "tfc:block/sluice_lower",
    "textures": {
      "texture": `tfcflorae:block/wood/sheet/${woodType}`
    }
  }
  let blockmodelSluiceUpper = {
    "parent": "tfc:block/sluice_upper",
    "textures": {
      "texture": `tfcflorae:block/wood/sheet/${woodType}`
    }
  }
  let blockmodelSupportConnection = {
    "parent": "tfc:block/wood/support/connection",
    "textures": {
      "texture": `tfcflorae:block/wood/sheet/${woodType}`,
      "particle": `tfcflorae:block/wood/sheet/${woodType}`
    }
  }
  let blockmodelSupportHorizontal = {
    "parent": "tfc:block/wood/support/horizontal",
    "textures": {
      "texture": `tfcflorae:block/wood/sheet/${woodType}`,
      "particle": `tfcflorae:block/wood/sheet/${woodType}`
    }
  }
  let blockmodelSupportInventory = {
    "parent": "tfc:block/wood/support/inventory",
    "textures": {
      "texture": `tfcflorae:block/wood/sheet/${woodType}`
    }
  }
  let blockmodelSupportVertical = {
    "parent": "tfc:block/wood/support/vertical",
    "textures": {
      "texture": `tfcflorae:block/wood/sheet/${woodType}`,
      "particle": `tfcflorae:block/wood/sheet/${woodType}`
    }
  }
  let blockmodelTrappedChest = {
    "textures": {
      "particle": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelPlanks = {
    "parent": "block/cube_all",
    "textures": {
      "all": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelBookshelf = {
    "parent": "minecraft:block/bookshelf",
    "textures": {
      "end": `tfcflorae:block/wood/planks/${woodType}`,
      "side": `tfcflorae:block/wood/bookshelf/${woodType}`
    }
  }
  let blockmodelButton = {
    "parent": "block/button",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelButtonInventory = {
    "parent": "block/button_inventory",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelButtonPressed = {
    "parent": "block/button_pressed",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelDoorBottom = {
    "parent": "block/door_bottom",
    "textures": {
      "top": `tfcflorae:block/wood/door/${woodType}_top`,
      "bottom": `tfcflorae:block/wood/door/${woodType}_bottom`
    }
  }
  let blockmodelDoorBottomHinge = {
    "parent": "block/door_bottom_rh",
    "textures": {
      "top": `tfcflorae:block/wood/door/${woodType}_top`,
      "bottom": `tfcflorae:block/wood/door/${woodType}_bottom`
    }
  }
  let blockmodelDoorTop = {
    "parent": "block/door_top",
    "textures": {
      "top": `tfcflorae:block/wood/planks/${woodType}_top`,
      "bottom": `tfcflorae:block/wood/planks/${woodType}_bottom`
    }
  }
  let blockmodelDoorTopHinge = {
    "parent": "block/door_top_rh",
    "textures": {
      "top": `tfcflorae:block/wood/planks/${woodType}_top`,
      "bottom": `tfcflorae:block/wood/planks/${woodType}_bottom`
    }
  }
  let blockmodelFenceGate = {
    "parent": "block/template_fence_gate",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelFenceGateOpen = {
    "parent": "block/template_fence_gate_open",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelFenceGateWall = {
    "parent": "block/template_fence_gate_wall",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelFenceGateWallOpen = {
    "parent": "block/template_fence_gate_wall_open",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelFenceInventory = {
    "parent": "block/fence_inventory",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelFencePost = {
    "parent": "block/fence_post",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelFenceSide = {
    "parent": "block/fence_side",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelLogFenceInventory = {
    "parent": "tfc:block/log_fence_inventory",
    "textures": {
      "log": `tfcflorae:block/wood/log/${woodType}`,
      "planks": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelLogFencePost = {
    "parent": "block/fence_post",
    "textures": {
      "texture": `tfcflorae:block/wood/log/${woodType}`
    }
  }
  let blockmodelLogFenceSide = {
    "parent": "block/fence_side",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelLoom = {
    "parent": "tfc:block/loom",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`,
      "particle": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelPressurePlate = {
    "parent": "block/pressure_plate_up",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelPressurePlateDown = {
    "parent": "block/pressure_plate_down",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelSign = {
    "textures": {
      "particle": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelSlab = {
    "parent": "block/slab",
    "textures": {
      "bottom": `tfcflorae:block/wood/planks/${woodType}`,
      "top": `tfcflorae:block/wood/planks/${woodType}`,
      "side": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelSlabTop = {
    "parent": "block/slab_top",
    "textures": {
      "bottom": `tfcflorae:block/wood/planks/${woodType}`,
      "top": `tfcflorae:block/wood/planks/${woodType}`,
      "side": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelStairs = {
    "parent": "block/stairs",
    "textures": {
      "bottom": `tfcflorae:block/wood/planks/${woodType}`,
      "top": `tfcflorae:block/wood/planks/${woodType}`,
      "side": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelStairsInner = {
    "parent": "block/inner_stairs",
    "textures": {
      "bottom": `tfcflorae:block/wood/planks/${woodType}`,
      "top": `tfcflorae:block/wood/planks/${woodType}`,
      "side": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelStairsOuter = {
    "parent": "block/outer_stairs",
    "textures": {
      "bottom": `tfcflorae:block/wood/planks/${woodType}`,
      "top": `tfcflorae:block/wood/planks/${woodType}`,
      "side": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelToolRack = {
    "parent": "tfc:block/tool_rack",
    "textures": {
      "texture": `tfcflorae:block/wood/planks/${woodType}`,
      "particle": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelTrapdoorBottom = {
    "parent": "block/template_orientable_trapdoor_bottom",
    "textures": {
      "texture": `tfcflorae:block/wood/trapdoor/${woodType}`
    }
  }
  let blockmodelTrapdoorOpen = {
    "parent": "block/template_orientable_trapdoor_open",
    "textures": {
      "texture": `tfcflorae:block/wood/trapdoor/${woodType}`
    }
  }
  let blockmodelTrapdoorTop = {
    "parent": "block/template_orientable_trapdoor_top",
    "textures": {
      "texture": `tfcflorae:block/wood/trapdoor/${woodType}`
    }
  }
  let blockmodelWorkbench = {
    "parent": "minecraft:block/cube",
    "textures": {
      "particle": `tfcflorae:block/wood/workbench/front/${woodType}`,
      "north": `tfcflorae:block/wood/workbench/front/${woodType}`,
      "south": `tfcflorae:block/wood/workbench/side/${woodType}`,
      "east": `tfcflorae:block/wood/workbench/side/${woodType}`,
      "west": `tfcflorae:block/wood/workbench/front/${woodType}`,
      "up": `tfcflorae:block/wood/workbench/top/${woodType}`,
      "down": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let blockmodelPottedSapling = {
    "parent": "minecraft:block/flower_pot_cross",
    "textures": {
      "plant": `tfcflorae:block/wood/sapling/${woodType}`,
      "dirt": "tfc:block/dirt/loam"
    }
  }

  // Item Models
  let itemmodelStrippedWood = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcflorae:item/wood/stripped_wood/${woodType}`
    }
  }
  let itemmodelStrippedLog = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcflorae:item/wood/stripped_log/${woodType}`
    }
  }
  let itemmodelWood = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcflorae:item/wood/wood/${woodType}`
    }
  }
  let itemmodelLog = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcflorae:item/wood/log/${woodType}`
    }
  }
  let itemmodelTwig = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcflorae:item/wood/twig/${woodType}`
    }
  }
  let itemmodelLeaves = {
    "parent": `tfcflorae:block/wood/leaves/${woodType}`
  }
  let itemmodelTrappedChest = {
    "parent": "item/chest",
    "textures": {
      "particle": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let itemmodelSupport = {
    "parent": `tfcflorae:block/wood/support/${woodType}_inventory`
  }
  let itemmodelSluice = {
    "parent": `tfcflorae:block/wood/sluice/${woodType}_lower`
  }
  let itemmodelScribingTable = {
    "parent": `tfcflorae:block/wood/scribing_table/${woodType}`
  }
  let itemmodelSapling = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcflorae:block/wood/sapling/${woodType}`
    }
  }
  let itemmodelLumber = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcflorae:item/wood/lumber/${woodType}`
    }
  }
  let itemmodelLectern = {
    "parent": `tfcflorae:block/wood/lectern/${woodType}`
  }
  let itemmodelFallenLeaves = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfc:item/groundcover/fallen_leaves`
    }
  }
  let itemmodelChestMinecart = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcflorae:item/wood/chest_minecart/${woodType}`
    }
  }
  let itemmodelChest = {
    "parent": "item/chest",
    "textures": {
      "particle": `tfcflorae:block/wood/planks/${woodType}`
    }
  }
  let itemmodelBoat = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcflorae:item/wood/boat/${woodType}`
    }
  }
  let itemmodelBarrel = {
    "parent": `tfcflorae:block/wood/barrel/${woodType}`,
    "overrides": [
      {
        "predicate": {
          "tfc:sealed": 1.0
        },
        "model": `tfcflorae:block/wood/barrel_sealed/${woodType}`
      }
    ]
  }
  let itemmodelPlanks = {
    "parent": `tfcflorae:block/wood/planks/${woodType}`
  }
  let itemmodelBookshelf = {
    "parent": `tfcflorae:block/wood/bookshelf/${woodType}`
  }
  let itemmodelButton = {
    "parent": `tfcflorae:block/wood/button/${woodType}_inventory`
  }
  let itemmodelDoor = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcflorae:item/wood/door/${woodType}`
    }
  }
  let itemmodelFence = {
    "parent": `tfcflorae:block/wood/fence/${woodType}_inventory`
  }
  let itemmodelFenceGate = {
    "parent": `tfcflorae:block/wood/fence_gate/${woodType}`
  }
  let itemmodelLogFence = {
    "parent": `tfcflorae:block/wood/log_fence/${woodType}_inventory`
  }
  let itemmodelLoom = {
    "parent": `tfcflorae:block/wood/loom/${woodType}`
  }
  let itemmodelPressurePlate = {
    "parent": `tfcflorae:block/wood/pressure_plate/${woodType}`
  }
  let itemmodelSlab = {
    "parent": `tfcflorae:block/wood/slab/${woodType}`
  }
  let itemmodelStairs = {
    "parent": `tfcflorae:block/wood/stairs/${woodType}`
  }
  let itemmodelSign = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcflorae:item/wood/sign/${woodType}`
    }
  }
  let itemmodelToolRack = {
    "parent": `tfcflorae:block/wood/tool_rack/${woodType}`
  }
  let itemmodelTrapdoor = {
    "parent": `tfcflorae:block/wood/trapdoor/${woodType}_bottom`
  }
  let itemmodelWorkbench = {
    "parent": `tfcflorae:block/wood/workbench/${woodType}`
  }

  let woodWallInventoryWoodJSON = {
		"parent": "block/wall_inventory",
		"textures": {
			"wall": `tfcflorae:block/wood/log/${woodType}`
		}
	}
  let woodWallPostWoodJSON = {
		"parent": "block/template_wall_post",
		"textures": {
			"wall": `tfcflorae:block/wood/log/${woodType}`
		}
	}
  let woodWallSideWoodJSON = {
		"parent": "block/template_wall_side",
		"textures": {
			"wall": `tfcflorae:block/wood/log/${woodType}`
		}
	}
  let woodWallPostTallWoodJSON = {
		"parent": "block/template_wall_side_tall",
		"textures": {
			"wall": `tfcflorae:block/wood/log/${woodType}`
		}
	}
  let woodWallItemWoodJSON = {
    "parent": `tfcflorae:block/wood/wood_wall/inventory/${woodType}`
  }

  let woodWallInventoryLogJSON = {
		"parent": "tfcflorae:block/wall_inventory_log",
		"textures": {
			"top": `tfcflorae:block/wood/log_top/${woodType}`,
			"wall": `tfcflorae:block/wood/log/${woodType}`,
			"particle": `tfcflorae:block/wood/log/${woodType}`
		}
	}
  let woodWallPostLogJSON = {
		"parent": "tfcflorae:block/template_wall_post_log",
		"textures": {
			"top": `tfcflorae:block/wood/log_top/${woodType}`,
			"wall": `tfcflorae:block/wood/log/${woodType}`,
			"particle": `tfcflorae:block/wood/log/${woodType}`
		}
	}

  let woodWallWoodBlockstateJSON = {
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
  /*let fallingLeavesJSON = {
    "spawnrate": 0.3,
    "isConifer": false
  }*/
  let advancementBarrel = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/lumber/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/barrel/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/barrel/${woodType}`
      ]
    }
  }
  let advancementBoat = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/planks/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/boat/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/boat/${woodType}`
      ]
    }
  }
  let advancementBookshelf = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                "minecraft:book"
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/bookshelf/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/bookshelf/${woodType}`
      ]
    }
  }
  let advancementButton = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/planks/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/button/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/button/${woodType}`
      ]
    }
  }
  let advancementChest = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/lumber/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/chest/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/chest/${woodType}`
      ]
    }
  }
  let advancementDoor = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/lumber/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/door/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/door/${woodType}`
      ]
    }
  }
  let advancementFence = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/lumber/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/fence/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/fence/${woodType}`
      ]
    }
  }
  let advancementFenceGate = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/lumber/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/fence_gate/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/fence_gate/${woodType}`
      ]
    }
  }
  let advancementLectern = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/bookshelf/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/lectern/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/lectern/${woodType}`
      ]
    }
  }
  let advancementLogFence = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/lumber/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/log_fence/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/log_fence/${woodType}`
      ]
    }
  }
  let advancementLoom = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/lumber/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/loom/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/loom/${woodType}`
      ]
    }
  }
  let advancementLumberLog = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/log/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/lumber_log/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/lumber_log/${woodType}`
      ]
    }
  }
  let advancementLumberPlanks = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/planks/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/lumber_planks/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/lumber_planks/${woodType}`
      ]
    }
  }
  let advancementPlanks = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/lumber/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/planks/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/planks/${woodType}`
      ]
    }
  }
  let advancementPressurePlate = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/lumber/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/pressure_plate/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/pressure_plate/${woodType}`
      ]
    }
  }
  let advancementScribingTable = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/planks/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/scribing_table/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/scribing_table/${woodType}`
      ]
    }
  }
  let advancementSign = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/lumber/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/sign/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/sign/${woodType}`
      ]
    }
  }
  let advancementSlab = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/planks/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/slab/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/slab/${woodType}`
      ]
    }
  }
  let advancementSluice = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/lumber/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/sluice/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/sluice/${woodType}`
      ]
    }
  }
  let advancementStairs = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/planks/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/stairs/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/stairs/${woodType}`
      ]
    }
  }
  let advancementSupport = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "tag": "tfc:saws"
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/support/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/support/${woodType}`
      ]
    }
  }
  let advancementToolRack = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/lumber/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/tool_rack/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/tool_rack/${woodType}`
      ]
    }
  }
  let advancementTrapdoor = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/lumber/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/trapdoor/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/trapdoor/${woodType}`
      ]
    }
  }
  let advancementTrappedChest = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/chest/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/trapped_chest/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/trapped_chest/${woodType}`
      ]
    }
  }
  let advancementWood = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/log/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/wood/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/wood/${woodType}`
      ]
    }
  }
  let advancementWorkbench = {
    "parent": "minecraft:recipes/root",
    "criteria": {
      "has_item": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "items": [
                `tfcflorae:wood/planks/${woodType}`
              ]
            }
          ]
        }
      },
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `tfcflorae:crafting/wood/workbench/${woodType}`
        }
      }
    },
    "requirements": [
      [
        "has_item",
        "has_the_recipe"
      ]
    ],
    "rewards": {
      "recipes": [
        `tfcflorae:crafting/wood/workbench/${woodType}`
      ]
    }
  }
  let recipeBarrel = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "X X",
      "X X",
      "XXX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/barrel/${woodType}`
    }
  }
  let recipeBoat = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "X X",
      "XXX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/planks/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/boat/${woodType}`
    }
  }
  let recipeBookshelf = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XXX",
      "YYY",
      "XXX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      },
      "Y": {
        "item": "minecraft:book"
      }
    },
    "result": {
      "item": `tfcflorae:wood/bookshelf/${woodType}`
    }
  }
  let recipeButton = {
    "type": "minecraft:crafting_shapeless",
    "ingredients": [
      {
        "item": `tfcflorae:wood/planks/${woodType}`
      }
    ],
    "result": {
      "item": `tfcflorae:wood/button/${woodType}`
    }
  }
  let recipeChest = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XXX",
      "X X",
      "XXX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/chest/${woodType}`
    }
  }
  let recipeChestMinecart = {
    "type": "minecraft:crafting_shapeless",
    "ingredients": [
      {
        "item": `tfcflorae:wood/chest/${woodType}`
      },
      {
        "item": "minecraft:minecart"
      }
    ],
    "result": {
      "item": `tfcflorae:wood/chest_minecart/${woodType}`
    }
  }
  let recipeDoor = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XX",
      "XX",
      "XX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/door/${woodType}`,
      "count": 2
    }
  }
  let recipeFence = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XYX",
      "XYX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/planks/${woodType}`
      },
      "Y": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/fence/${woodType}`,
      "count": 8
    }
  }
  let recipeFenceGate = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "YXY",
      "YXY"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/planks/${woodType}`
      },
      "Y": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/fence_gate/${woodType}`,
      "count": 2
    }
  }
  let recipeLectern = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XXX",
      " Y ",
      " X "
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      },
      "Y": {
        "item": `tfcflorae:wood/bookshelf/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/lectern/${woodType}`
    }
  }
  let recipeLogFence = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XYX",
      "XYX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/log/${woodType}`
      },
      "Y": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/log_fence/${woodType}`,
      "count": 8
    }
  }
  let recipeLoom = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XXX",
      "XSX",
      "X X"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      },
      "S": {
        "item": "minecraft:stick"
      }
    },
    "result": {
      "item": `tfcflorae:wood/loom/${woodType}`
    }
  }
  let recipeLumberLog = {
    "type": "tfc:damage_inputs_shapeless_crafting",
    "recipe": {
      "type": "minecraft:crafting_shapeless",
      "ingredients": [
        {
          "tag": `tfc:${woodType}_logs`
        },
        {
          "tag": "tfc:saws"
        }
      ],
      "result": {
        "item": `tfcflorae:wood/lumber/${woodType}`,
        "count": 8
      }
    }
  }
  let recipeLumberPlanks = {
    "type": "tfc:damage_inputs_shapeless_crafting",
    "recipe": {
      "type": "minecraft:crafting_shapeless",
      "ingredients": [
        {
          "item": `tfcflorae:wood/planks/${woodType}`
        },
        {
          "tag": "tfc:saws"
        }
      ],
      "result": {
        "item": `tfcflorae:wood/lumber/${woodType}`,
        "count": 4
      }
    }
  }
  let recipePlanks = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XX",
      "XX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/planks/${woodType}`
    }
  }
  let recipePressurePlate = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/pressure_plate/${woodType}`
    }
  }
  let recipeScribingTable = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "F B",
      "XXX",
      "Y Y"
    ],
    "key": {
      "F": {
        "tag": "forge:feathers"
      },
      "B": {
        "item": "minecraft:black_dye"
      },
      "X": {
        "item": `tfcflorae:wood/slab/${woodType}`
      },
      "Y": {
        "item": `tfcflorae:wood/planks/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/scribing_table/${woodType}`
    }
  }
  let recipeSign = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XXX",
      "XXX",
      " Y "
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      },
      "Y": {
        "tag": "forge:rods/wooden"
      }
    },
    "result": {
      "item": `tfcflorae:wood/sign/${woodType}`,
      "count": 3
    }
  }
  let recipeSlab = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XXX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/planks/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/slab/${woodType}`,
      "count": 6
    }
  }
  let recipeSluice = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "  X",
      " XY",
      "XYY"
    ],
    "key": {
      "X": {
        "tag": "forge:rods/wooden"
      },
      "Y": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/sluice/${woodType}`
    }
  }
  let recipeStairs = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "X  ",
      "XX ",
      "XXX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/planks/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/stairs/${woodType}`,
      "count": 8
    }
  }
  let recipeSupport = {
    "type": "tfc:damage_inputs_shapeless_crafting",
    "recipe": {
      "type": "minecraft:crafting_shapeless",
      "ingredients": [
        {
          "tag": `tfc:${woodType}_logs`
        },
        {
          "tag": `tfc:${woodType}_logs`
        },
        {
          "tag": "tfc:saws"
        }
      ],
      "result": {
        "item": `tfcflorae:wood/support/${woodType}`,
        "count": 8
      }
    }
  }
  let recipeToolRack = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XXX",
      "   ",
      "XXX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/tool_rack/${woodType}`
    }
  }
  let recipeTrapdoor = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XXX",
      "XXX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/trapdoor/${woodType}`,
      "count": 3
    }
  }
  let recipeTrappedChest = {
    "type": "minecraft:crafting_shapeless",
    "ingredients": [
      {
        "item": `tfcflorae:wood/chest/${woodType}`
      },
      {
        "item": "minecraft:tripwire_hook"
      }
    ],
    "result": {
      "item": `tfcflorae:wood/trapped_chest/${woodType}`,
      "count": 1
    }
  }
  let recipeWood = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XX",
      "XX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/log/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/wood/${woodType}`,
      "count": 3
    }
  }
  let recipeWorkbench = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XX",
      "XX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/planks/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/workbench/${woodType}`
    }
  }
  let recipeLogWall = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "XYX",
      "XYX"
    ],
    "key": {
      "X": {
        "item": `tfcflorae:wood/log/${woodType}`
      },
      "Y": {
        "item": `tfcflorae:wood/lumber/${woodType}`
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
        "item": `tfcflorae:wood/wood/${woodType}`
      },
      "Y": {
        "item": `tfcflorae:wood/lumber/${woodType}`
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
        "item": `tfcflorae:wood/planks/${woodType}`
      },
      "1": {
        "item": `tfcflorae:wood/planks/${woodType}`
      },
      "2": {
        "item": `tfcflorae:wood/planks/${woodType}`
      },
      "3": {
        "item": `tfcflorae:wood/slab/${woodType}`
      },
      "4": {
        "item": `tfcflorae:wood/slab/${woodType}`
      },
      "5": {
        "item": `tfcflorae:wood/slab/${woodType}`
      },
      "6": {
        "item": `tfcflorae:wood/planks/${woodType}`
      },
      "7": {
        "item": `tfcflorae:wood/planks/${woodType}`
      },
      "8": {
        "item": `tfcflorae:wood/planks/${woodType}`
      }
    },
    "result": {
      "item": `tfcflorae:wood/chiseled_bookshelf/${woodType}`,
      "count": 1
    }
  }
  let lootBarrel = {
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
                "name": `tfcflorae:wood/barrel/${woodType}`,
                "conditions": [
                  {
                    "condition": "minecraft:block_state_property",
                    "block": `tfcflorae:wood/barrel/${woodType}`,
                    "properties": {
                      "sealed": "true"
                    }
                  }
                ],
                "functions": [
                  {
                    "function": "minecraft:copy_name",
                    "source": "block_entity"
                  },
                  {
                    "function": "minecraft:copy_nbt",
                    "source": "block_entity",
                    "ops": [
                      {
                        "source": "",
                        "target": "BlockEntityTag",
                        "op": "replace"
                      }
                    ]
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:wood/barrel/${woodType}`
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
  let lootChest = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/chest/${woodType}`
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
  let lootFallenLeaves = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/fallen_leaves/${woodType}`
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
  let lootHorizontalSupport = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/support/${woodType}`
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
  let lootLeaves = {
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
                "name": `tfcflorae:wood/leaves/${woodType}`,
                "conditions": [
                  {
                    "condition": "minecraft:alternative",
                    "terms": [
                      {
                        "condition": "minecraft:match_tool",
                        "predicate": {
                          "tag": "forge:shears"
                        }
                      },
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
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:wood/sapling/${woodType}`,
                "conditions": [
                  {
                    "condition": "minecraft:survives_explosion"
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.0083
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
      },
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:alternatives",
            "children": [
              {
                "type": "minecraft:item",
                "name": "minecraft:stick",
                "conditions": [
                  {
                    "condition": "minecraft:match_tool",
                    "predicate": {
                      "tag": "tfc:sharp_tools"
                    }
                  },
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.2
                  }
                ],
                "functions": [
                  {
                    "function": "minecraft:set_count",
                    "count": {
                      "min": 1,
                      "max": 2,
                      "type": "minecraft:uniform"
                    }
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": "minecraft:stick",
                "conditions": [
                  {
                    "condition": "minecraft:random_chance",
                    "chance": 0.05
                  }
                ],
                "functions": [
                  {
                    "function": "minecraft:set_count",
                    "count": {
                      "min": 1,
                      "max": 2,
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
  let lootLog = {
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
                "name": "minecraft:stick",
                "conditions": [
                  {
                    "condition": "minecraft:match_tool",
                    "predicate": {
                      "tag": "tfc:hammers"
                    }
                  }
                ],
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
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:wood/log/${woodType}`
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
  let lootPlanks = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/planks/${woodType}`
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
  let lootBookshelf = {
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
                "name": `tfcflorae:wood/bookshelf/${woodType}`,
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
                "name": "minecraft:book",
                "functions": [
                  {
                    "function": "minecraft:set_count",
                    "count": 3
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
  let lootButton = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/button/${woodType}`
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
  let lootDoor = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/door/${woodType}`,
            "conditions": [
              {
                "condition": "minecraft:block_state_property",
                "block": `tfcflorae:wood/door/${woodType}`,
                "properties": {
                  "half": "lower"
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
  let lootFence = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/fence/${woodType}`
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
  let lootFenceGate = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/fence_gate/${woodType}`
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
  let lootPressurePlate = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/pressure_plate/${woodType}`
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
  let lootSign = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/sign/${woodType}`
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
  let lootStairs = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/stairs/${woodType}`
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
  let lootTrapdoor = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/trapdoor/${woodType}`
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
  let lootWorkbench = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/workbench/${woodType}`
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
  let lootSapling = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/sapling/${woodType}`
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
  let lootSluice = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/sluice/${woodType}`,
            "conditions": [
              {
                "condition": "minecraft:block_state_property",
                "block": `tfcflorae:wood/sluice/${woodType}`,
                "properties": {
                  "upper": "true"
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
  let lootStrippedLog = {
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
                "name": "minecraft:stick",
                "conditions": [
                  {
                    "condition": "minecraft:match_tool",
                    "predicate": {
                      "tag": "tfc:hammers"
                    }
                  }
                ],
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
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:wood/stripped_log/${woodType}`
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
  let lootStrippedWood = {
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
                "name": "minecraft:stick",
                "conditions": [
                  {
                    "condition": "minecraft:match_tool",
                    "predicate": {
                      "tag": "tfc:hammers"
                    }
                  }
                ],
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
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:wood/stripped_wood/${woodType}`,
                "conditions": [
                  {
                    "condition": "minecraft:block_state_property",
                    "block": `tfcflorae:wood/stripped_wood/${woodType}`,
                    "properties": {
                      "natural": "false"
                    }
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:wood/stripped_log/${woodType}`
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
  let lootTrappedChest = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/trapped_chest/${woodType}`
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
  let lootTwig = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/twig/${woodType}`
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
  let lootVerticalSupport = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/support/${woodType}`
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
  let lootWood = {
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
                "name": "minecraft:stick",
                "conditions": [
                  {
                    "condition": "minecraft:match_tool",
                    "predicate": {
                      "tag": "tfc:hammers"
                    }
                  }
                ],
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
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:wood/wood/${woodType}`,
                "conditions": [
                  {
                    "condition": "minecraft:block_state_property",
                    "block": `tfcflorae:wood/wood/${woodType}`,
                    "properties": {
                      "natural": "false"
                    }
                  }
                ]
              },
              {
                "type": "minecraft:item",
                "name": `tfcflorae:wood/log/${woodType}`
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
  let lootLectern = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/lectern/${woodType}`
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
  let lootLogFence = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/log_fence/${woodType}`
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
  let lootLoom = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/loom/${woodType}`
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
  let lootSlab = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/slab/${woodType}`,
            "functions": [
              {
                "function": "minecraft:set_count",
                "conditions": [
                  {
                    "condition": "minecraft:block_state_property",
                    "block": `tfcflorae:wood/slab/${woodType}`,
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
  let lootToolRack = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/tool_rack/${woodType}`
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
  let lootPottedSapling = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/sapling/${woodType}`
          }
        ],
        "conditions": [
          {
            "condition": "minecraft:survives_explosion"
          }
        ]
      },
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": "minecraft:flower_pot"
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
  let lootScribingTable = {
    "type": "minecraft:block",
    "pools": [
      {
        "name": "loot_pool",
        "rolls": 1,
        "entries": [
          {
            "type": "minecraft:item",
            "name": `tfcflorae:wood/scribing_table/${woodType}`
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

  let animalCartItemModel = {
    "parent": "minecraft:item/generated",
    "textures": {
        "layer0": `astikorcarts:items/animal_cart/${woodType}`
    }
  }
  let plowItemModel = {
    "parent": "minecraft:item/generated",
    "textures": {
        "layer0": `astikorcarts:items/plow/${woodType}`
    }
  }
  let supplyCartItemModel = {
    "parent": "minecraft:item/generated",
    "textures": {
        "layer0": `astikorcarts:items/supply_cart/${woodType}`
    }
  }
  let wheelItemModel = {
    "parent": "minecraft:item/generated",
    "textures": {
        "layer0": `astikorcarts:items/wheel/${woodType}`
    }
  }
	fs.writeFileSync(`./assets/models/item/animal_cart/${woodType}.json`, JSON.stringify(animalCartItemModel, null, 2))
	fs.writeFileSync(`./assets/models/item/plow/${woodType}.json`, JSON.stringify(plowItemModel, null, 2))
	fs.writeFileSync(`./assets/models/item/supply_cart/${woodType}.json`, JSON.stringify(supplyCartItemModel, null, 2))
	fs.writeFileSync(`./assets/models/item/wheel/${woodType}.json`, JSON.stringify(wheelItemModel, null, 2))

  let recipeAnimalCart = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "ppp",
      "pbp",
      "wrw"
    ],
    "key": {
      "b": {
        "item": "tfc:brass_mechanisms"
      },
      "r": {
        "tag": "forge:rods"
      },
      "p": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      },
      "w": {
        "item": `astikorcarts:wheel/${woodType}`
      }
    },
    "result": {
      "item": `astikorcarts:animal_cart/${woodType}`
    }
  }
  let recipePlow = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "sss",
      "pbp",
      "wrw"
    ],
    "key": {
      "b": {
        "item": "tfc:brass_mechanisms"
      },
      "r": {
        "tag": "forge:rods"
      },
      "p": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      },
      "s": {
        "tag": "forge:rods/wooden"
      },
      "w": {
        "item": `astikorcarts:wheel/${woodType}`
      }
    },
    "result": {
      "item": `astikorcarts:plow/${woodType}`
    }
  }
  let recipeSupplyCart = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "ccc",
      "pbp",
      "wrw"
    ],
    "key": {
      "b": {
        "item": "tfc:brass_mechanisms"
      },
      "r": {
        "tag": "forge:rods"
      },
      "c": {
        "tag": "forge:chests/wooden"
      },
      "p": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      },
      "w": {
        "item": `astikorcarts:wheel/${woodType}`
      }
    },
    "result": {
      "item": `astikorcarts:supply_cart/${woodType}`
    }
  }
  let recipeWheel = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "sss",
      "sps",
      "sss"
    ],
    "key": {
      "p": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      },
      "s": {
        "tag": "forge:rods/wooden"
      }
    },
    "result": {
      "item": `astikorcarts:wheel/${woodType}`
    }
  }
  fs.writeFileSync(`./data/recipes/crafting/animal_cart/${woodType}.json`, JSON.stringify(recipeAnimalCart, null, 2))
  fs.writeFileSync(`./data/recipes/crafting/plow/${woodType}.json`, JSON.stringify(recipePlow, null, 2))
  fs.writeFileSync(`./data/recipes/crafting/supply_cart/${woodType}.json`, JSON.stringify(recipeSupplyCart, null, 2))
  fs.writeFileSync(`./data/recipes/crafting/wheel/${woodType}.json`, JSON.stringify(recipeWheel, null, 2))

  let advancementAnimalCart = {
    "rewards": {
      "recipes": [
        `astikorcarts:crafting/animal_cart/${woodType}`
      ]
    },
    "criteria": {
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `astikorcarts:crafting/animal_cart/${woodType}`
        }
      },
      "has_wheel": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "item": `astikorcarts:wheel/${woodType}`
            }
          ]
        }
      }
    },
    "requirements": [
      [
        "has_the_recipe",
        "has_wheel"
      ]
    ]
  }
  let advancementPlow = {
    "rewards": {
      "recipes": [
        `astikorcarts:crafting/plow/${woodType}`
      ]
    },
    "criteria": {
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `astikorcarts:crafting/plow/${woodType}`
        }
      },
      "has_wheel": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "item": `astikorcarts:wheel/${woodType}`
            }
          ]
        }
      }
    },
    "requirements": [
      [
        "has_the_recipe",
        "has_wheel"
      ]
    ]
  }
  let advancementSupplyCart = {
    "rewards": {
      "recipes": [
        `astikorcarts:crafting/supply_cart/${woodType}`
      ]
    },
    "criteria": {
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `astikorcarts:crafting/supply_cart/${woodType}`
        }
      },
      "has_wheel": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "item": `astikorcarts:wheel/${woodType}`
            }
          ]
        }
      }
    },
    "requirements": [
      [
        "has_the_recipe",
        "has_wheel"
      ]
    ]
  }
  let advancementWheel = {
    "rewards": {
      "recipes": [
        `astikorcarts:crafting/wheel/${woodType}`
      ]
    },
    "criteria": {
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `astikorcarts:crafting/wheel/${woodType}`
        }
      },
      "has_planks": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "tag": "minecraft:planks"
            }
          ]
        }
      }
    },
    "requirements": [
      [
        "has_the_recipe",
        "has_planks"
      ]
    ]
  }
  fs.writeFileSync(`./data/advancements/crafting/animal_cart/${woodType}.json`, JSON.stringify(advancementAnimalCart, null, 2))
  fs.writeFileSync(`./data/advancements/crafting/plow/${woodType}.json`, JSON.stringify(advancementPlow, null, 2))
  fs.writeFileSync(`./data/advancements/crafting/supply_cart/${woodType}.json`, JSON.stringify(advancementSupplyCart, null, 2))
  fs.writeFileSync(`./data/advancements/crafting/wheel/${woodType}.json`, JSON.stringify(advancementWheel, null, 2))

  let chiselWoodBlockTypeStairs = {
    "type": "tfc:chisel",
    "ingredient": `tfcflorae:wood/planks/${woodType}`,
    "result": `tfcflorae:wood/stairs/${woodType}`,
    "mode": "stair"
  }
  let chiselWoodBlockTypeSlab = {
    "type": "tfc:chisel",
    "ingredient": `tfcflorae:wood/planks/${woodType}`,
    "result": `tfcflorae:wood/slab/${woodType}`,
    "mode": "slab",
    "extra_drop": {
      "item": `tfcflorae:wood/slab/${woodType}`
    }
  }
  fs.writeFileSync(`./data/recipes/chisel/stair/wood/${woodType}.json`, JSON.stringify(chiselWoodBlockTypeStairs, null, 2))
  fs.writeFileSync(`./data/recipes/chisel/slab/wood/${woodType}.json`, JSON.stringify(chiselWoodBlockTypeSlab, null, 2))

  // Note: Currently all folders have to be created manually.

	fs.writeFileSync(`./assets/models/block/wood/wood_wall/inventory/${woodType}.json`, JSON.stringify(woodWallInventoryWoodJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/wood_wall/post/${woodType}.json`, JSON.stringify(woodWallPostWoodJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/wood_wall/side/${woodType}.json`, JSON.stringify(woodWallSideWoodJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/wood_wall/side/tall/${woodType}.json`, JSON.stringify(woodWallPostTallWoodJSON, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/wood_wall/${woodType}.json`, JSON.stringify(woodWallItemWoodJSON, null, 2))

	fs.writeFileSync(`./assets/models/block/wood/log_wall/inventory/${woodType}.json`, JSON.stringify(woodWallInventoryLogJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/log_wall/post/${woodType}.json`, JSON.stringify(woodWallPostLogJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/log_wall/side/${woodType}.json`, JSON.stringify(woodWallSideWoodJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/log_wall/side/tall/${woodType}.json`, JSON.stringify(woodWallPostTallWoodJSON, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/log_wall/${woodType}.json`, JSON.stringify(woodWallItemWoodJSON, null, 2))

	fs.writeFileSync(`./assets/blockstates/wood/wood_wall/${woodType}.json`, JSON.stringify(woodWallWoodBlockstateJSON, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/log_wall/${woodType}.json`, JSON.stringify(woodWallLogBlockstateJSON, null, 2))

	fs.writeFileSync(`./assets/blockstates/wood/chiseled_bookshelf/${woodType}.json`, JSON.stringify(chiseledBookshelfBlockstateJSON, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/chiseled_bookshelf/0/${woodType}.json`, JSON.stringify(chiseledBookshelf0JSON, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/chiseled_bookshelf/1/${woodType}.json`, JSON.stringify(chiseledBookshelf1JSON, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/chiseled_bookshelf/2/${woodType}.json`, JSON.stringify(chiseledBookshelf2JSON, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/chiseled_bookshelf/3/${woodType}.json`, JSON.stringify(chiseledBookshelf3JSON, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/chiseled_bookshelf/4/${woodType}.json`, JSON.stringify(chiseledBookshelf4JSON, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/chiseled_bookshelf/5/${woodType}.json`, JSON.stringify(chiseledBookshelf5JSON, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/chiseled_bookshelf/6/${woodType}.json`, JSON.stringify(chiseledBookshelf6JSON, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/chiseled_bookshelf/${woodType}.json`, JSON.stringify(chiseledBookshelfItemJSON, null, 2))

	//fs.writeFileSync(`./data/fallingLeaves/${woodType}.json`, JSON.stringify(fallingLeavesJSON, null, 2))

  // Block Models
	fs.writeFileSync(`./assets/models/block/wood/stripped_wood/${woodType}.json`, JSON.stringify(blockmodelStrippedWood, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/stripped_log/${woodType}.json`, JSON.stringify(blockmodelStrippedLog, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/wood/${woodType}.json`, JSON.stringify(blockmodelWood, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/log/${woodType}.json`, JSON.stringify(blockmodelLog, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/twig/${woodType}.json`, JSON.stringify(blockmodelTwig, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/barrel/${woodType}.json`, JSON.stringify(blockmodelBarrel, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/barrel_sealed/${woodType}.json`, JSON.stringify(blockmodelBarrelSealed, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/chest/${woodType}.json`, JSON.stringify(blockmodelChest, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/fallen_leaves/${woodType}.json`, JSON.stringify(blockmodelFallenLeaves, null, 2))
	//fs.writeFileSync(`./assets/models/block/wood/leaves/${woodType}.json`, JSON.stringify(blockmodelLeaves, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/lectern/${woodType}.json`, JSON.stringify(blockmodelLectern, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/sapling/${woodType}.json`, JSON.stringify(blockmodelSapling, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/scribing_table/${woodType}.json`, JSON.stringify(blockmodelScribingTable, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/sluice/${woodType}_lower.json`, JSON.stringify(blockmodelSluiceLower, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/sluice/${woodType}_upper.json`, JSON.stringify(blockmodelSluiceUpper, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/support/${woodType}_connection.json`, JSON.stringify(blockmodelSupportConnection, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/support/${woodType}_horizontal.json`, JSON.stringify(blockmodelSupportHorizontal, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/support/${woodType}_inventory.json`, JSON.stringify(blockmodelSupportInventory, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/support/${woodType}_vertical.json`, JSON.stringify(blockmodelSupportVertical, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/trapped_chest/${woodType}.json`, JSON.stringify(blockmodelTrappedChest, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/planks/${woodType}.json`, JSON.stringify(blockmodelPlanks, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/bookshelf/${woodType}.json`, JSON.stringify(blockmodelBookshelf, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/button/${woodType}.json`, JSON.stringify(blockmodelButton, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/button/${woodType}_inventory.json`, JSON.stringify(blockmodelButtonInventory, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/button/${woodType}_pressed.json`, JSON.stringify(blockmodelButtonPressed, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/door/${woodType}_bottom.json`, JSON.stringify(blockmodelDoorBottom, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/door/${woodType}_bottom_hinge.json`, JSON.stringify(blockmodelDoorBottomHinge, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/door/${woodType}_top.json`, JSON.stringify(blockmodelDoorTop, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/door/${woodType}_top_hinge.json`, JSON.stringify(blockmodelDoorTopHinge, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/fence_gate/${woodType}.json`, JSON.stringify(blockmodelFenceGate, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/fence_gate/${woodType}_open.json`, JSON.stringify(blockmodelFenceGateOpen, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/fence_gate/${woodType}_wall.json`, JSON.stringify(blockmodelFenceGateWall, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/fence_gate/${woodType}_wall_open.json`, JSON.stringify(blockmodelFenceGateWallOpen, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/fence/${woodType}_inventory.json`, JSON.stringify(blockmodelFenceInventory, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/fence/${woodType}_post.json`, JSON.stringify(blockmodelFencePost, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/fence/${woodType}_side.json`, JSON.stringify(blockmodelFenceSide, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/log_fence/${woodType}_inventory.json`, JSON.stringify(blockmodelLogFenceInventory, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/log_fence/${woodType}_post.json`, JSON.stringify(blockmodelLogFencePost, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/log_fence/${woodType}_side.json`, JSON.stringify(blockmodelLogFenceSide, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/loom/${woodType}.json`, JSON.stringify(blockmodelLoom, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/pressure_plate/${woodType}.json`, JSON.stringify(blockmodelPressurePlate, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/pressure_plate/${woodType}_down.json`, JSON.stringify(blockmodelPressurePlateDown, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/sign/${woodType}.json`, JSON.stringify(blockmodelSign, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/slab/${woodType}.json`, JSON.stringify(blockmodelSlab, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/slab/${woodType}_top.json`, JSON.stringify(blockmodelSlabTop, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/stairs/${woodType}.json`, JSON.stringify(blockmodelStairs, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/stairs/${woodType}_inner.json`, JSON.stringify(blockmodelStairsInner, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/stairs/${woodType}_outer.json`, JSON.stringify(blockmodelStairsOuter, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/tool_rack/${woodType}.json`, JSON.stringify(blockmodelToolRack, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/trapdoor/${woodType}_bottom.json`, JSON.stringify(blockmodelTrapdoorBottom, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/trapdoor/${woodType}_open.json`, JSON.stringify(blockmodelTrapdoorOpen, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/trapdoor/${woodType}_top.json`, JSON.stringify(blockmodelTrapdoorTop, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/workbench/${woodType}.json`, JSON.stringify(blockmodelWorkbench, null, 2))
	fs.writeFileSync(`./assets/models/block/wood/potted_sapling/${woodType}.json`, JSON.stringify(blockmodelPottedSapling, null, 2))

  // Item Models
	fs.writeFileSync(`./assets/models/item/wood/stripped_wood/${woodType}.json`, JSON.stringify(itemmodelStrippedWood, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/stripped_log/${woodType}.json`, JSON.stringify(itemmodelStrippedLog, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/wood/${woodType}.json`, JSON.stringify(itemmodelWood, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/log/${woodType}.json`, JSON.stringify(itemmodelLog, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/twig/${woodType}.json`, JSON.stringify(itemmodelTwig, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/trapped_chest/${woodType}.json`, JSON.stringify(itemmodelTrappedChest, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/support/${woodType}.json`, JSON.stringify(itemmodelSupport, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/sluice/${woodType}.json`, JSON.stringify(itemmodelSluice, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/scribing_table/${woodType}.json`, JSON.stringify(itemmodelScribingTable, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/sapling/${woodType}.json`, JSON.stringify(itemmodelSapling, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/lumber/${woodType}.json`, JSON.stringify(itemmodelLumber, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/lectern/${woodType}.json`, JSON.stringify(itemmodelLectern, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/fallen_leaves/${woodType}.json`, JSON.stringify(itemmodelFallenLeaves, null, 2))
	//fs.writeFileSync(`./assets/models/item/wood/leaves/${woodType}.json`, JSON.stringify(itemmodelLeaves, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/chest_minecart/${woodType}.json`, JSON.stringify(itemmodelChestMinecart, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/chest/${woodType}.json`, JSON.stringify(itemmodelChest, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/boat/${woodType}.json`, JSON.stringify(itemmodelBoat, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/barrel/${woodType}.json`, JSON.stringify(itemmodelBarrel, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/planks/${woodType}.json`, JSON.stringify(itemmodelPlanks, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/bookshelf/${woodType}.json`, JSON.stringify(itemmodelBookshelf, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/button/${woodType}.json`, JSON.stringify(itemmodelButton, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/door/${woodType}.json`, JSON.stringify(itemmodelDoor, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/fence_gate/${woodType}.json`, JSON.stringify(itemmodelFenceGate, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/fence/${woodType}.json`, JSON.stringify(itemmodelFence, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/log_fence/${woodType}.json`, JSON.stringify(itemmodelLogFence, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/loom/${woodType}.json`, JSON.stringify(itemmodelLoom, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/pressure_plate/${woodType}.json`, JSON.stringify(itemmodelPressurePlate, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/sign/${woodType}.json`, JSON.stringify(itemmodelSign, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/slab/${woodType}.json`, JSON.stringify(itemmodelSlab, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/stairs/${woodType}.json`, JSON.stringify(itemmodelStairs, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/tool_rack/${woodType}.json`, JSON.stringify(itemmodelToolRack, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/trapdoor/${woodType}.json`, JSON.stringify(itemmodelTrapdoor, null, 2))
	fs.writeFileSync(`./assets/models/item/wood/workbench/${woodType}.json`, JSON.stringify(itemmodelWorkbench, null, 2))

  // Blockstates
	fs.writeFileSync(`./assets/blockstates/wood/stripped_log/${woodType}.json`, JSON.stringify(blockstateStrippedLog, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/stripped_wood/${woodType}.json`, JSON.stringify(blockstateStrippedWood, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/log/${woodType}.json`, JSON.stringify(blockstateLog, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/wood/${woodType}.json`, JSON.stringify(blockstateWood, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/twig/${woodType}.json`, JSON.stringify(blockstateTwig, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/barrel/${woodType}.json`, JSON.stringify(blockstateBarrel, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/chest/${woodType}.json`, JSON.stringify(blockstateChest, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/fallen_leaves/${woodType}.json`, JSON.stringify(blockstateFallenLeaves, null, 2))
	//fs.writeFileSync(`./assets/blockstates/wood/leaves/${woodType}.json`, JSON.stringify(blockstateLeaves, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/horizontal_support/${woodType}.json`, JSON.stringify(blockstateHorizontalSupport, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/lectern/${woodType}.json`, JSON.stringify(blockstateLectern, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/sapling/${woodType}.json`, JSON.stringify(blockstateSapling, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/scribing_table/${woodType}.json`, JSON.stringify(blockstateScribingTable, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/sluice/${woodType}.json`, JSON.stringify(blockstateSluice, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/trapped_chest/${woodType}.json`, JSON.stringify(blockstateTrappedChest, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/vertical_support/${woodType}.json`, JSON.stringify(blockstateVerticalSupport, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/planks/${woodType}.json`, JSON.stringify(blockstatePlanks, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/bookshelf/${woodType}.json`, JSON.stringify(blockstateBookshelf, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/button/${woodType}.json`, JSON.stringify(blockstateButton, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/door/${woodType}.json`, JSON.stringify(blockstateDoor, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/fence/${woodType}.json`, JSON.stringify(blockstateFence, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/fence_gate/${woodType}.json`, JSON.stringify(blockstateFenceGate, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/log_fence/${woodType}.json`, JSON.stringify(blockstateLogFence, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/loom/${woodType}.json`, JSON.stringify(blockstateLoom, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/pressure_plate/${woodType}.json`, JSON.stringify(blockstatePressurePlate, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/sign/${woodType}.json`, JSON.stringify(blockstateSign, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/slab/${woodType}.json`, JSON.stringify(blockstateSlab, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/stairs/${woodType}.json`, JSON.stringify(blockstateStairs, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/tool_rack/${woodType}.json`, JSON.stringify(blockstateToolRack, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/trapdoor/${woodType}.json`, JSON.stringify(blockstateTrapdoor, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/wall_sign/${woodType}.json`, JSON.stringify(blockstateWallSign, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/workbench/${woodType}.json`, JSON.stringify(blockstateWorkbench, null, 2))
	fs.writeFileSync(`./assets/blockstates/wood/potted_sapling/${woodType}.json`, JSON.stringify(blockstatePottedSapling, null, 2))

  // Advancements
	fs.writeFileSync(`./data/advancements/crafting/wood/barrel/${woodType}.json`, JSON.stringify(advancementBarrel, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/boat/${woodType}.json`, JSON.stringify(advancementBoat, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/bookshelf/${woodType}.json`, JSON.stringify(advancementBookshelf, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/button/${woodType}.json`, JSON.stringify(advancementButton, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/chest/${woodType}.json`, JSON.stringify(advancementChest, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/door/${woodType}.json`, JSON.stringify(advancementDoor, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/fence/${woodType}.json`, JSON.stringify(advancementFence, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/fence_gate/${woodType}.json`, JSON.stringify(advancementFenceGate, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/lectern/${woodType}.json`, JSON.stringify(advancementLectern, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/log_fence/${woodType}.json`, JSON.stringify(advancementLogFence, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/loom/${woodType}.json`, JSON.stringify(advancementLoom, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/lumber_log/${woodType}.json`, JSON.stringify(advancementLumberLog, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/lumber_planks/${woodType}.json`, JSON.stringify(advancementLumberPlanks, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/planks/${woodType}.json`, JSON.stringify(advancementPlanks, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/pressure_plate/${woodType}.json`, JSON.stringify(advancementPressurePlate, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/scribing_table/${woodType}.json`, JSON.stringify(advancementScribingTable, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/sign/${woodType}.json`, JSON.stringify(advancementSign, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/slab/${woodType}.json`, JSON.stringify(advancementSlab, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/sluice/${woodType}.json`, JSON.stringify(advancementSluice, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/stairs/${woodType}.json`, JSON.stringify(advancementStairs, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/support/${woodType}.json`, JSON.stringify(advancementSupport, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/tool_rack/${woodType}.json`, JSON.stringify(advancementToolRack, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/trapdoor/${woodType}.json`, JSON.stringify(advancementTrapdoor, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/trapped_chest/${woodType}.json`, JSON.stringify(advancementTrappedChest, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/wood/${woodType}.json`, JSON.stringify(advancementWood, null, 2))
	fs.writeFileSync(`./data/advancements/crafting/wood/workbench/${woodType}.json`, JSON.stringify(advancementWorkbench, null, 2))

  // Recipes
	fs.writeFileSync(`./data/recipes/crafting/wood/barrel/${woodType}.json`, JSON.stringify(recipeBarrel, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/boat/${woodType}.json`, JSON.stringify(recipeBoat, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/bookshelf/${woodType}.json`, JSON.stringify(recipeBookshelf, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/button/${woodType}.json`, JSON.stringify(recipeButton, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/chest/${woodType}.json`, JSON.stringify(recipeChest, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/chest_minecart/${woodType}.json`, JSON.stringify(recipeChestMinecart, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/door/${woodType}.json`, JSON.stringify(recipeDoor, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/fence/${woodType}.json`, JSON.stringify(recipeFence, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/fence_gate/${woodType}.json`, JSON.stringify(recipeFenceGate, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/lectern/${woodType}.json`, JSON.stringify(recipeLectern, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/log_fence/${woodType}.json`, JSON.stringify(recipeLogFence, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/loom/${woodType}.json`, JSON.stringify(recipeLoom, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/lumber_log/${woodType}.json`, JSON.stringify(recipeLumberLog, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/lumber_planks/${woodType}.json`, JSON.stringify(recipeLumberPlanks, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/planks/${woodType}.json`, JSON.stringify(recipePlanks, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/pressure_plate/${woodType}.json`, JSON.stringify(recipePressurePlate, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/scribing_table/${woodType}.json`, JSON.stringify(recipeScribingTable, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/sign/${woodType}.json`, JSON.stringify(recipeSign, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/slab/${woodType}.json`, JSON.stringify(recipeSlab, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/sluice/${woodType}.json`, JSON.stringify(recipeSluice, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/stairs/${woodType}.json`, JSON.stringify(recipeStairs, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/support/${woodType}.json`, JSON.stringify(recipeSupport, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/tool_rack/${woodType}.json`, JSON.stringify(recipeToolRack, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/trapdoor/${woodType}.json`, JSON.stringify(recipeTrapdoor, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/trapped_chest/${woodType}.json`, JSON.stringify(recipeTrappedChest, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/wood/${woodType}.json`, JSON.stringify(recipeWood, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/workbench/${woodType}.json`, JSON.stringify(recipeWorkbench, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/log_wall/${woodType}.json`, JSON.stringify(recipeLogWall, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/wood_wall/${woodType}.json`, JSON.stringify(recipeWoodWall, null, 2))
	fs.writeFileSync(`./data/recipes/crafting/wood/chiseled_bookshelf/${woodType}.json`, JSON.stringify(recipeChiseledBookshelf, null, 2))

  // Loot Tables
	fs.writeFileSync(`./data/loot_tables/blocks/wood/barrel/${woodType}.json`, JSON.stringify(lootBarrel, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/chest/${woodType}.json`, JSON.stringify(lootChest, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/fallen_leaves/${woodType}.json`, JSON.stringify(lootFallenLeaves, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/horizontal_support/${woodType}.json`, JSON.stringify(lootHorizontalSupport, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/leaves/${woodType}.json`, JSON.stringify(lootLeaves, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/log/${woodType}.json`, JSON.stringify(lootLog, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/planks/${woodType}.json`, JSON.stringify(lootPlanks, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/bookshelf/${woodType}.json`, JSON.stringify(lootBookshelf, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/button/${woodType}.json`, JSON.stringify(lootButton, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/door/${woodType}.json`, JSON.stringify(lootDoor, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/fence/${woodType}.json`, JSON.stringify(lootFence, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/fence_gate/${woodType}.json`, JSON.stringify(lootFenceGate, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/pressure_plate/${woodType}.json`, JSON.stringify(lootPressurePlate, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/sign/${woodType}.json`, JSON.stringify(lootSign, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/stairs/${woodType}.json`, JSON.stringify(lootStairs, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/trapdoor/${woodType}.json`, JSON.stringify(lootTrapdoor, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/workbench/${woodType}.json`, JSON.stringify(lootWorkbench, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/sapling/${woodType}.json`, JSON.stringify(lootSapling, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/sluice/${woodType}.json`, JSON.stringify(lootSluice, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/stripped_log/${woodType}.json`, JSON.stringify(lootStrippedLog, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/stripped_wood/${woodType}.json`, JSON.stringify(lootStrippedWood, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/trapped_chest/${woodType}.json`, JSON.stringify(lootTrappedChest, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/twig/${woodType}.json`, JSON.stringify(lootTwig, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/vertical_support/${woodType}.json`, JSON.stringify(lootVerticalSupport, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/wood/${woodType}.json`, JSON.stringify(lootWood, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/lectern/${woodType}.json`, JSON.stringify(lootLectern, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/log_fence/${woodType}.json`, JSON.stringify(lootLogFence, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/loom/${woodType}.json`, JSON.stringify(lootLoom, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/slab/${woodType}.json`, JSON.stringify(lootSlab, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/tool_rack/${woodType}.json`, JSON.stringify(lootToolRack, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/log_wall/${woodType}.json`, JSON.stringify(lootLogWall, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/wood_wall/${woodType}.json`, JSON.stringify(lootWoodWall, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/potted_sapling/${woodType}.json`, JSON.stringify(lootPottedSapling, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/scribing_Table/${woodType}.json`, JSON.stringify(lootScribingTable, null, 2))
	fs.writeFileSync(`./data/loot_tables/blocks/wood/chiseled_bookshelf/${woodType}.json`, JSON.stringify(lootChiseledBookshelf, null, 2))

  let blockStateBarrelNew = {
    "variants": {
      "facing=up,rack=true,sealed=true": {
        "model": `tfcflorae:block/wood/barrel_sealed/${woodType}`
      },
      "facing=up,rack=true,sealed=false": {
        "model": `tfcflorae:block/wood/barrel/${woodType}`
      },
      "facing=up,rack=false,sealed=true": {
        "model": `tfcflorae:block/wood/barrel_sealed/${woodType}`
      },
      "facing=up,rack=false,sealed=false": {
        "model": `tfcflorae:block/wood/barrel/${woodType}`
      },
      "facing=east,rack=true,sealed=true": {
        "model": `tfcflorae:block/wood/barrel_sealed/${woodType}_side_rack`
      },
      "facing=east,rack=true,sealed=false": {
        "model": `tfcflorae:block/wood/barrel/${woodType}_side_rack`
      },
      "facing=east,rack=false,sealed=true": {
        "model": `tfcflorae:block/wood/barrel_sealed/${woodType}_side`
      },
      "facing=east,rack=false,sealed=false": {
        "model": `tfcflorae:block/wood/barrel/${woodType}_side`
      },
      "facing=west,rack=true,sealed=true": {
        "model": `tfcflorae:block/wood/barrel_sealed/${woodType}_side_rack`,
        "y": 180
      },
      "facing=west,rack=true,sealed=false": {
        "model": `tfcflorae:block/wood/barrel/${woodType}_side_rack`,
        "y": 180
      },
      "facing=west,rack=false,sealed=true": {
        "model": `tfcflorae:block/wood/barrel_sealed/${woodType}_side`,
        "y": 180
      },
      "facing=west,rack=false,sealed=false": {
        "model": `tfcflorae:block/wood/barrel/${woodType}_side`,
        "y": 180
      },
      "facing=south,rack=true,sealed=true": {
        "model": `tfcflorae:block/wood/barrel_sealed/${woodType}_side_rack`,
        "y": 90
      },
      "facing=south,rack=true,sealed=false": {
        "model": `tfcflorae:block/wood/barrel/${woodType}_side_rack`,
        "y": 90
      },
      "facing=south,rack=false,sealed=true": {
        "model": `tfcflorae:block/wood/barrel_sealed/${woodType}_side`,
        "y": 90
      },
      "facing=south,rack=false,sealed=false": {
        "model": `tfcflorae:block/wood/barrel/${woodType}_side`,
        "y": 90
      },
      "facing=north,rack=true,sealed=true": {
        "model": `tfcflorae:block/wood/barrel_sealed/${woodType}_side_rack`,
        "y": 270
      },
      "facing=north,rack=true,sealed=false": {
        "model": `tfcflorae:block/wood/barrel/${woodType}_side_rack`,
        "y": 270
      },
      "facing=north,rack=false,sealed=true": {
        "model": `tfcflorae:block/wood/barrel_sealed/${woodType}_side`,
        "y": 270
      },
      "facing=north,rack=false,sealed=false": {
        "model": `tfcflorae:block/wood/barrel/${woodType}_side`,
        "y": 270
      }
    }
  }
	fs.writeFileSync(`./assets/blockstates/wood/barrel/${woodType}.json`, JSON.stringify(blockStateBarrelNew, null, 2))

  let modelBarrelNew = {
    "parent": "tfc:block/barrel",
    "textures": {
      "particle": `tfcflorae:block/wood/planks/${woodType}`,
      "planks": `tfcflorae:block/wood/planks/${woodType}`,
      "sheet": `tfcflorae:block/wood/sheet/${woodType}`
    }
  }
	fs.writeFileSync(`./assets/models/block/wood/barrel/${woodType}.json`, JSON.stringify(modelBarrelNew, null, 2))

  let modelBarrelSide = {
    "parent": "tfc:block/barrel_side",
    "textures": {
      "particle": `tfcflorae:block/wood/planks/${woodType}`,
      "planks": `tfcflorae:block/wood/planks/${woodType}`,
      "sheet": `tfcflorae:block/wood/sheet/${woodType}`
    }
  }
	fs.writeFileSync(`./assets/models/block/wood/barrel/${woodType}_side.json`, JSON.stringify(modelBarrelSide, null, 2))

  let modelBarrelSideRack = {
    "parent": "tfc:block/barrel_side_rack",
    "textures": {
      "particle": `tfcflorae:block/wood/planks/${woodType}`,
      "planks": `tfcflorae:block/wood/planks/${woodType}`,
      "sheet": `tfcflorae:block/wood/sheet/${woodType}`
    }
  }
	fs.writeFileSync(`./assets/models/block/wood/barrel/${woodType}_side_rack.json`, JSON.stringify(modelBarrelSideRack, null, 2))

  let modelBarrelSealedNew = {
    "parent": "tfc:block/barrel_sealed",
    "textures": {
      "particle": `tfcflorae:block/wood/planks/${woodType}`,
      "planks": `tfcflorae:block/wood/planks/${woodType}`,
      "sheet": `tfcflorae:block/wood/sheet/${woodType}`
    }
  }
	fs.writeFileSync(`./assets/models/block/wood/barrel_sealed/${woodType}.json`, JSON.stringify(modelBarrelSealedNew, null, 2))

  let modelBarrelSealedSide = {
    "parent": "tfc:block/barrel_side_sealed",
    "textures": {
      "particle": `tfcflorae:block/wood/planks/${woodType}`,
      "planks": `tfcflorae:block/wood/planks/${woodType}`,
      "sheet": `tfcflorae:block/wood/sheet/${woodType}`
    }
  }
	fs.writeFileSync(`./assets/models/block/wood/barrel_sealed/${woodType}_side.json`, JSON.stringify(modelBarrelSealedSide, null, 2))

  let modelBarrelSealedSideRack = {
    "parent": "tfc:block/barrel_side_sealed_rack",
    "textures": {
      "particle": `tfcflorae:block/wood/planks/${woodType}`,
      "planks": `tfcflorae:block/wood/planks/${woodType}`,
      "sheet": `tfcflorae:block/wood/sheet/${woodType}`
    }
  }
	fs.writeFileSync(`./assets/models/block/wood/barrel_sealed/${woodType}_side_rack.json`, JSON.stringify(modelBarrelSealedSideRack, null, 2))

  let recipeSledTFCF = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "##S",
      "SSS"
    ],
    "key": {
      "#": {
        "item": `tfcflorae:wood/lumber/${woodType}`
      },
      "S": {
        "tag": "forge:rods/wooden"
      }
    },
    "result": {
      "item": `tfcsleghs:sled/${woodType}`
    }
  }
  fs.writeFileSync(`./data/recipes/crafting/sled/${woodType}.json`, JSON.stringify(recipeSledTFCF, null, 2))

  let modelSledTFCF = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcsleghs:items/wood/sled/${woodType}`
    }
  }
  fs.writeFileSync(`./assets/models/item/wood/sled/${woodType}.json`, JSON.stringify(modelSledTFCF, null, 2))
}

function generateJSONTFC(woodTypeTFC)
{
  let recipeSledTFC = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "##S",
      "SSS"
    ],
    "key": {
      "#": {
        "item": `tfc:wood/lumber/${woodTypeTFC}`
      },
      "S": {
        "tag": "forge:rods/wooden"
      }
    },
    "result": {
      "item": `tfcsleghs:sled/${woodTypeTFC}`
    }
  }
  fs.writeFileSync(`./data/recipes/crafting/sled/${woodTypeTFC}.json`, JSON.stringify(recipeSledTFC, null, 2))

  let modelSledTFC = {
    "parent": "item/generated",
    "textures": {
      "layer0": `tfcsleghs:items/wood/sled/${woodTypeTFC}`
    }
  }
  fs.writeFileSync(`./assets/models/item/wood/sled/${woodTypeTFC}.json`, JSON.stringify(modelSledTFC, null, 2))

  let animalCartItemModelTFC = {
    "parent": "minecraft:item/generated",
    "textures": {
        "layer0": `astikorcarts:items/animal_cart/${woodTypeTFC}`
    }
  }
  let plowItemModelTFC = {
    "parent": "minecraft:item/generated",
    "textures": {
        "layer0": `astikorcarts:items/plow/${woodTypeTFC}`
    }
  }
  let supplyCartItemModelTFC = {
    "parent": "minecraft:item/generated",
    "textures": {
        "layer0": `astikorcarts:items/supply_cart/${woodTypeTFC}`
    }
  }
  let wheelItemModelTFC = {
    "parent": "minecraft:item/generated",
    "textures": {
        "layer0": `astikorcarts:items/wheel/${woodTypeTFC}`
    }
  }
	fs.writeFileSync(`./assets/models/item/animal_cart/${woodTypeTFC}.json`, JSON.stringify(animalCartItemModelTFC, null, 2))
	fs.writeFileSync(`./assets/models/item/plow/${woodTypeTFC}.json`, JSON.stringify(plowItemModelTFC, null, 2))
	fs.writeFileSync(`./assets/models/item/supply_cart/${woodTypeTFC}.json`, JSON.stringify(supplyCartItemModelTFC, null, 2))
	fs.writeFileSync(`./assets/models/item/wheel/${woodTypeTFC}.json`, JSON.stringify(wheelItemModelTFC, null, 2))

  let recipeAnimalCartTFC = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "ppp",
      "pbp",
      "wrw"
    ],
    "key": {
      "b": {
        "item": "tfc:brass_mechanisms"
      },
      "r": {
        "tag": "forge:rods"
      },
      "p": {
        "item": `tfc:wood/lumber/${woodTypeTFC}`
      },
      "w": {
        "item": `astikorcarts:wheel/${woodTypeTFC}`
      }
    },
    "result": {
      "item": `astikorcarts:animal_cart/${woodTypeTFC}`
    }
  }
  let recipePlowTFC = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "sss",
      "pbp",
      "wrw"
    ],
    "key": {
      "b": {
        "item": "tfc:brass_mechanisms"
      },
      "r": {
        "tag": "forge:rods"
      },
      "p": {
        "item": `tfc:wood/lumber/${woodTypeTFC}`
      },
      "s": {
        "tag": "forge:rods/wooden"
      },
      "w": {
        "item": `astikorcarts:wheel/${woodTypeTFC}`
      }
    },
    "result": {
      "item": `astikorcarts:plow/${woodTypeTFC}`
    }
  }
  let recipeSupplyCartTFC = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "ccc",
      "pbp",
      "wrw"
    ],
    "key": {
      "b": {
        "item": "tfc:brass_mechanisms"
      },
      "r": {
        "tag": "forge:rods"
      },
      "c": {
        "tag": "forge:chests/wooden"
      },
      "p": {
        "item": `tfc:wood/lumber/${woodTypeTFC}`
      },
      "w": {
        "item": `astikorcarts:wheel/${woodTypeTFC}`
      }
    },
    "result": {
      "item": `astikorcarts:supply_cart/${woodTypeTFC}`
    }
  }
  let recipeWheelTFC = {
    "type": "minecraft:crafting_shaped",
    "pattern": [
      "sss",
      "sps",
      "sss"
    ],
    "key": {
      "p": {
        "item": `tfc:wood/lumber/${woodTypeTFC}`
      },
      "s": {
        "tag": "forge:rods/wooden"
      }
    },
    "result": {
      "item": `astikorcarts:wheel/${woodTypeTFC}`
    }
  }
  fs.writeFileSync(`./data/recipes/crafting/animal_cart/${woodTypeTFC}.json`, JSON.stringify(recipeAnimalCartTFC, null, 2))
  fs.writeFileSync(`./data/recipes/crafting/plow/${woodTypeTFC}.json`, JSON.stringify(recipePlowTFC, null, 2))
  fs.writeFileSync(`./data/recipes/crafting/supply_cart/${woodTypeTFC}.json`, JSON.stringify(recipeSupplyCartTFC, null, 2))
  fs.writeFileSync(`./data/recipes/crafting/wheel/${woodTypeTFC}.json`, JSON.stringify(recipeWheelTFC, null, 2))

  let advancementAnimalCartTFC = {
    "rewards": {
      "recipes": [
        `astikorcarts:crafting/animal_cart/${woodTypeTFC}`
      ]
    },
    "criteria": {
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `astikorcarts:crafting/animal_cart/${woodTypeTFC}`
        }
      },
      "has_wheel": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "item": `astikorcarts:wheel/${woodTypeTFC}`
            }
          ]
        }
      }
    },
    "requirements": [
      [
        "has_the_recipe",
        "has_wheel"
      ]
    ]
  }
  let advancementPlowTFC = {
    "rewards": {
      "recipes": [
        `astikorcarts:crafting/plow/${woodTypeTFC}`
      ]
    },
    "criteria": {
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `astikorcarts:crafting/plow/${woodTypeTFC}`
        }
      },
      "has_wheel": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "item": `astikorcarts:wheel/${woodTypeTFC}`
            }
          ]
        }
      }
    },
    "requirements": [
      [
        "has_the_recipe",
        "has_wheel"
      ]
    ]
  }
  let advancementSupplyCartTFC = {
    "rewards": {
      "recipes": [
        `astikorcarts:crafting/supply_cart/${woodTypeTFC}`
      ]
    },
    "criteria": {
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `astikorcarts:crafting/supply_cart/${woodTypeTFC}`
        }
      },
      "has_wheel": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "item": `astikorcarts:wheel/${woodTypeTFC}`
            }
          ]
        }
      }
    },
    "requirements": [
      [
        "has_the_recipe",
        "has_wheel"
      ]
    ]
  }
  let advancementWheelTFC = {
    "rewards": {
      "recipes": [
        `astikorcarts:crafting/wheel/${woodTypeTFC}`
      ]
    },
    "criteria": {
      "has_the_recipe": {
        "trigger": "minecraft:recipe_unlocked",
        "conditions": {
          "recipe": `astikorcarts:crafting/wheel/${woodTypeTFC}`
        }
      },
      "has_planks": {
        "trigger": "minecraft:inventory_changed",
        "conditions": {
          "items": [
            {
              "tag": "minecraft:planks"
            }
          ]
        }
      }
    },
    "requirements": [
      [
        "has_the_recipe",
        "has_planks"
      ]
    ]
  }
  fs.writeFileSync(`./data/advancements/crafting/animal_cart/${woodTypeTFC}.json`, JSON.stringify(advancementAnimalCartTFC, null, 2))
  fs.writeFileSync(`./data/advancements/crafting/plow/${woodTypeTFC}.json`, JSON.stringify(advancementPlowTFC, null, 2))
  fs.writeFileSync(`./data/advancements/crafting/supply_cart/${woodTypeTFC}.json`, JSON.stringify(advancementSupplyCartTFC, null, 2))
  fs.writeFileSync(`./data/advancements/crafting/wheel/${woodTypeTFC}.json`, JSON.stringify(advancementWheelTFC, null, 2))
}
