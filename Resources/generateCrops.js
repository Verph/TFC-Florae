const fs = require('fs');

    let CROP_TYPES = {
    'amaranth': 'amaranth',
    'buckwheat': 'buckwheat',
    'fonio': 'fonio',
    'millet': 'millet',
    'quinoa': 'quinoa',
    'spelt': 'spelt',
    'wild_rice': 'wild_rice',
    'black_eyed_peas': 'black_eyed_peas',
    'cayenne_pepper': 'cayenne_pepper',
    'celery': 'celery',
    'ginger': 'ginger',
    'ginseng': 'ginseng',
    'lettuce': 'lettuce',
    'peanut': 'peanut',
    'rutabaga': 'rutabaga',
    'turnip': 'turnip',
    'mustard': 'mustard',
    'sweet_potato': 'sweet_potato',
    'sugar_beet': 'sugar_beet',
    'agave': 'agave',
    'coca': 'coca',
    'cotton': 'cotton',
    'flax': 'flax',
    'hemp': 'hemp',
    'hops': 'hops',
    'indigo': 'indigo',
    'madder': 'madder',
    'opium_poppy': 'opium_poppy',
    'rape': 'rape',
    'weld': 'weld',
    'woad': 'woad',
    'tobacco': 'tobacco'
    }

for(let crops of Object.keys(CROP_TYPES))
{
    generateRecipes(crops)
}

function generateRecipes(crops)
{
    let cropSeedsJSON = {
      "parent": "item/handheld",
      "textures": {
        "layer0": `tfcflorae:items/crop/seeds/${crops}`
      }
    }
    fs.writeFileSync(`./src/main/resources/assets/tfcflorae/models/item/crop/seeds/${crops}.json`, JSON.stringify(cropSeedsJSON, null, 2))
}