const fs = require('fs');
const fse = require('fs-extra')
const getDirName = require('path').dirname;
const mkdirp = require('mkdirp');

let FOOD = {
  'food': 'food'
}

let berries = {
  'blackberry': '',
  'blueberry': '',
  'bunchberry': '',
  'cloudberry': '',
  'cranberry': '',
  'elderberry': '',
  'gooseberry': '',
  'raspberry': '',
  'snowberry': '',
  'strawberry': '',
  'wintergreen_berry': ''
}

let fruits = {
  'banana': '',
  'cherry': '',
  'green_apple': '',
  'lemon': '',
  'olive': '',
  'orange': '',
  'peach': '',
  'plum': '',
  'red_apple': ''
}

let grains = {
  'barley': '',
  'maize': '',
  'oat': '',
  'rye': '',
  'rice': '',
  'wheat': '',
}

let crops = {
  'beet': '',
  'cabbage': '',
  'carrot': '',
  'garlic': '',
  'green_bean': '',
  //'green_bell_pepper': '',
  //'yellow_bell_pepper': '',
  //'red_bell_pepper': '',
  'onion': '',
  'potato': '',
  'soybean': '',
  'sugarcane': '',
  'squash': '',
  'tomato': ''
}

let tfcf_food = {
  'acorn_squash': '',
  'aehobak': '',
  'baobab_fruit': '',
  'barrel_cactus_fruit': '',
  'butternut': '',
  'carambola': '',
  'crookneck': '',
  'cucumber': '',
  'dragonberry': '',
  'fresh_seaweed': '',
  'glow_berry': '',
  'hawthorn': '',
  'holly_berry': '',
  'jabuticaba': '',
  'joshua_fruit': '',
  'juniper': '',
  'kiwi': '',
  'medlar': '',
  'mulberry': '',
  'osage_orange': '',
  'otaheite_apple': '',
  'papaya': '',
  'pear': '',
  'persimmon': '',
  'pink_ivory_drupe': '',
  'pitahaya': '',
  'prickly_pear': '',
  'quince': '',
  'riberry': '',
  'rowan_berry': '',
  'saguaro': '',
  'scallopini': '',
  'sky_fruit': '',
  'sloe': '',
  'sorb_apple': '',
  'tromboncino': '',
  'yew_berry': '',
  'zucchini': ''
}

for(let foodType of Object.keys(FOOD))
{
    generateJSON(foodType)
    console.log("generating for " + foodType.toString())
}

function generateJSON()
{
  for(let berry in berries)
  {
    let recipeRottenBerry = {
      "type": "tfc:damage_inputs_shapeless_crafting",
      "recipe": {
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
          {
            "tag": "tfc:knives"
          },
          {
            "type": "tfcflorae:is_rotten",
            "ingredient": {
              "item": `tfc:food/${berry}`
            }
          }
        ],
        "result": {
          "item": "tfc:rotten_compost",
          "count": 1
        }
      }
    }
    fse.ensureDir(`./data/recipes/seeds`)
    fs.writeFileSync(`./data/recipes/seeds/${berry.toString()}.json`, JSON.stringify(recipeRottenBerry, null, 2))
  }

  for(let fruit in fruits)
  {
    let recipeRottenFruit = {
      "type": "tfc:damage_inputs_shapeless_crafting",
      "recipe": {
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
          {
            "tag": "tfc:knives"
          },
          {
            "type": "tfcflorae:is_rotten",
            "ingredient": {
              "item": `tfc:food/${fruit}`
            }
          }
        ],
        "result": {
          "item": "tfc:rotten_compost",
          "count": 1
        }
      }
    }
    fse.ensureDir(`./data/recipes/seeds`)
    fs.writeFileSync(`./data/recipes/seeds/${fruit}.json`, JSON.stringify(recipeRottenFruit, null, 2))
  }

  for(let grain in grains)
  {
    let recipeRottenGrain = {
      "type": "tfc:extra_products_shapeless_crafting",
      "extra_products": [
        {
          "item": "tfc:rotten_compost"
        }
      ],
      "recipe": {
        "type": "tfc:damage_inputs_shapeless_crafting",
        "recipe": {
          "type": "tfc:advanced_shapeless_crafting",
          "ingredients": [
            {
              "type": "tfcflorae:is_rotten",
              "ingredient": {
                "item": `tfc:food/${grain}`
              }
            },
            {
              "tag": "tfc:knives"
            }
          ],
          "primary_ingredient": {
            "item": `tfc:food/${grain}`
          },
          "result": {
            "stack": {
              "item": `tfc:seeds/${grain}`
            }
          }
        }
      }
    }
    fse.ensureDir(`./data/recipes/seeds`)
    fs.writeFileSync(`./data/recipes/seeds/${grain}.json`, JSON.stringify(recipeRottenGrain, null, 2))
  }

  for(let crop in crops)
  {
    let recipeRottenCrops = {
      "type": "tfc:extra_products_shapeless_crafting",
      "extra_products": [
        {
          "item": "tfc:rotten_compost"
        }
      ],
      "recipe": {
        "type": "tfc:damage_inputs_shapeless_crafting",
        "recipe": {
          "type": "tfc:advanced_shapeless_crafting",
          "ingredients": [
            {
              "type": "tfcflorae:is_rotten",
              "ingredient": {
                "item": `tfc:food/${crop}`
              }
            },
            {
              "tag": "tfc:knives"
            }
          ],
          "primary_ingredient": {
            "item": `tfc:food/${crop}`
          },
          "result": {
            "stack": {
              "item": `tfc:seeds/${crop}`
            }
          }
        }
      }
    }
    fse.ensureDir(`./data/recipes/seeds`)
    fs.writeFileSync(`./data/recipes/seeds/${crop}.json`, JSON.stringify(recipeRottenCrops, null, 2))
  }

  for(let food in tfcf_food)
  {
    let recipeRottenFood = {
      "type": "tfc:damage_inputs_shapeless_crafting",
      "recipe": {
        "type": "minecraft:crafting_shapeless",
        "ingredients": [
          {
            "tag": "tfc:knives"
          },
          {
            "type": "tfcflorae:is_rotten",
            "ingredient": {
              "item": `tfcflorae:food/${food}`
            }
          }
        ],
        "result": {
          "item": "tfc:rotten_compost",
          "count": 1
        }
      }
    }
    fse.ensureDir(`./data/recipes/seeds`)
    fs.writeFileSync(`./data/recipes/seeds/${food}.json`, JSON.stringify(recipeRottenFood, null, 2))
  }
}
