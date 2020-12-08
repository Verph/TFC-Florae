const fs = require('fs');

    let NUT_TYPES = {
    'acorn': 'acorn',
    'allspice': 'allspice',
    'almond': 'almond',
    'beechnut': 'beechnut',
    'black_walnut': 'black_walnut',
    'brazil_nut': 'brazil_nut',
    'breadnut': 'breadnut',
    'bunya_nut': 'bunya_nut',
    'butternut': 'butternut',
    'candlenut': 'acorn',
    'cashew': 'acorn',
    'ginkgo_nut': 'acorn',
    'hazelnut': 'acorn',
    'heartnut': 'acorn',
    'hickory_nut': 'acorn',
    'kola_nut': 'acorn',
    'kukui_nut': 'acorn',
    'macadamia': 'acorn',
    'mongongo': 'acorn',
    'monkey_puzzle_nut': 'acorn',
    'nutmeg': 'acorn',
    'paradise_nut': 'acorn',
    'pecan': 'acorn',
    'pine_nut': 'acorn',
    'pistachio': 'acorn',
    'walnut': 'acorn',
    'acorn_nut': 'acorn_nut',
    'allspice': 'allspice',
    'almond_nut': 'almond_nut',
    'beechnut_nut': 'beechnut_nut',
    'black_walnut_nut': 'black_walnut_nut',
    'brazil_nut_nut': 'brazil_nut_nut',
    'breadnut_nut': 'breadnut_nut',
    'bunya_nut_nut': 'bunya_nut_nut',
    'butternut_nut': 'butternut_nut',
    'candlenut_nut': 'candlenut_nut',
    'cashew_nut': 'cashew_nut',
    'chestnut_nut': 'chestnut_nut',
    'ginkgo_nut_nut': 'ginkgo_nut_nut',
    'hazelnut_nut': 'hazelnut_nut',
    'heartnut_nut': 'heartnut_nut',
    'hickory_nut_nut': 'hickory_nut_nut',
    'kola_nut_nut': 'kola_nut_nut',
    'kukui_nut_nut': 'kukui_nut_nut',
    'macadamia_nut': 'macadamia_nut',
    'mongongo_nut': 'mongongo_nut',
    'monkey_puzzle_nut_nut': 'monkey_puzzle_nut_nut',
    'nutmeg_nut': 'nutmeg_nut',
    'paradise_nut_nut': 'paradise_nut_nut',
    'pecan_nut': 'pecan_nut',
    'pine_nut': 'pine_nut',
    'pistachio_nut': 'pistachio_nut',
    'walnut_nut': 'walnut_nut'
    }

for(let nutType of Object.keys(NUT_TYPES))
{
    generateRecipes(nutType)
}

function generateRecipes(nutType)
{
    let nutJSON = {
      "parent": "item/handheld",
      "textures": {
        "layer0": `tfcflorae:items/food/${nutType}`
      }
    }
    let nutRoastedJSON = {
      "parent": "item/handheld",
      "textures": {
        "layer0": `tfcflorae:items/food/roasted/${nutType}`
      }
    }
    fs.writeFileSync(`./src/main/resources/assets/tfcflorae/models/item/food/${nutType}.json`, JSON.stringify(nutJSON, null, 2))
    fs.writeFileSync(`./src/main/resources/assets/tfcflorae/models/item/food/roasted/${nutType}.json`, JSON.stringify(nutRoastedJSON, null, 2))
}