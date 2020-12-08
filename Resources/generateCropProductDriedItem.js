const fs = require('fs');

    let CROP_PRODUCT_DRIED_TYPES = {
    'chamomile_head': 'chamomile_head',
    'dandelion_head': 'dandelion_head',
    'labrador_tea_head': 'labrador_tea_head',
    'sunflower_head': 'sunflower_head'
    }

for(let cropProductDriedType of Object.keys(CROP_PRODUCT_DRIED_TYPES))
{
    generateRecipes(cropProductDriedType)
}

function generateRecipes(cropProductDriedType)
{
    let cropProductDriedJSON = {
      "parent": "item/handheld",
      "textures": {
        "layer0": `tfcflorae:items/crop/product/dried/${cropProductDriedType}`
      }
    }
    fs.writeFileSync(`./src/main/resources/assets/tfcflorae/models/item/crop/product/dried/${cropProductDriedType}.json`, JSON.stringify(cropProductDriedJSON, null, 2))
}