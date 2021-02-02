const fs = require('fs');

let STONE_TYPES = {
    'andesite': 'andesite',
    'arkose': 'arkose',
    'basalt': 'basalt',
    'blaimorite': 'blaimorite',
    'blueschist': 'blueschist',
    'boninite': 'boninite',
    'carbonatite': 'carbonatite',
    'cataclasite': 'cataclasite',
    'chalk': 'chalk',
    'chert': 'chert',
    'claystone': 'claystone',
    'conglomerate': 'conglomerate',
    'dacite': 'dacite',
    'diorite': 'diorite',
    'dolomite': 'dolomite',
    'foidolite': 'foidolite',
    'gabbro': 'gabbro',
    'gneiss': 'gneiss',
    'granite': 'granite',
    'greenschist': 'greenschist',
    'jaspillite': 'jaspillite',
    'limestone': 'limestone',
    'marble': 'marble',
    'mylonite': 'mylonite',
    'phyllite': 'phyllite',
    'quartzite': 'quartzite',
    'rhyolite': 'rhyolite',
    'rocksalt': 'rocksalt',
    'schist': 'schist',
    'shale': 'shale',
    'slate': 'slate',
    'travertine': 'travertine',
    'wackestone': 'wackestone',
	'breccia': 'breccia',
    'porphyry': 'porphyry',
    'peridotite': 'peridotite',
    'mudstone': 'mudstone',
    'sandstone': 'sandstone',
    'siltstone': 'siltstone',
    'catlinite': 'catlinite',
    'novaculite': 'novaculite',
    'soapstone': 'soapstone',
    'komatiite': 'komatiite'
}


for(let stoneType of Object.keys(STONE_TYPES))
{
    generateJSON(stoneType)
}

function generateJSON(stoneType)
{
    let small_boulderJSON = {
		"populate":{
			`small_boulder_${stoneType}`:{
				"distribution":"surface",
				"generator":{
					"type":"boulder",
					"block":[
						{
							"name":`tfc_decoration:mossy_cobble/${stoneType}`,
							"weight":20
						},
						{
							"name":`tfcflorae:mossy_raw/${stoneType}`,
							"weight":20
						},
						{
							"name":`tfc:cobble/${stoneType}`,
							"weight":25
						},
						{
							"name":`tfc:raw/${stoneType}`,
							"weight":35
						}
					],
					"material":[
						`tfc:grass/${stoneType}`,
						`tfc:dry_grass/${stoneType}`,
						`tfc:dirt/${stoneType}`,
						`tfc:gravel/${stoneType}`,
						`tfcflorae:podzol/${stoneType}`
					],
					"diameter":1,
					"size-variance":1,
					"count":1,
					"count-variance":2
				},
				"chunk-chance":20,
				"cluster-count":10,
				"min-height":50,
				"max-height":170,
				"material":[
					"minecraft:air",
					`tfc:grass/${stoneType}`,
					`tfc:dry_grass/${stoneType}`,
					`tfc:dirt/${stoneType}`,
					`tfc:gravel/${stoneType}`,
					`tfcflorae:podzol/${stoneType}`
				],
				"follow-terrain":true,
				"retrogen":false,
				"biome":{
					"restriction":"whitelist",
					"value":[
						{
							"type":"dictionary",
							"entry":"FROZEN"
						},
						{
							"type":"dictionary",
							"entry":"SNOWY"
						},
						{
							"type":"dictionary",
							"entry":"SANDY"
						},
						{
							"type":"dictionary",
							"entry":"FOREST"
						},
						{
							"type":"dictionary",
							"entry":"MOUNTAIN"
						},
						{
							"type":"dictionary",
							"entry":"CONIFEROUS"
						},
						{
							"type":"dictionary",
							"entry":"PLAINS"
						},
						{
							"type":"dictionary",
							"entry":"SWAMP"
						},
						{
							"type":"dictionary",
							"entry":"LUSH"
						},
						{
							"type":"dictionary",
							"entry":"HILLS"
						}
					]
				},
				"dimension":{
					"restriction":"blacklist",
					"value":[
						-1,
						1
					]
				}
			}
		}
	}
    let medium_boulderJSON = {
		"populate": {	
			`medium_boulder_${stoneType}`: {
				"distribution": "surface",
				"generator": {
					"type": "boulder",
					"block": [
						{
							"name": `tfc_decoration:mossy_cobble/${stoneType}`
							"weight": 20
						},
						{
							"name": `tfcflorae:mossy_raw/${stoneType}`,
							"weight": 20
						},
						{
							"name": `tfc:cobble/${stoneType}`,
							"weight": 25
						},
						{
							"name": `tfc:raw/${stoneType}`,
							"weight": 35
						}
					],
					"material": [
						`tfc:grass/${stoneType}`,
						`tfc:dry_grass/${stoneType}`,
						`tfc:dirt/${stoneType}`,
						`tfc:gravel/${stoneType}`,
						`tfcflorae:podzol/${stoneType}`
					],
					"diameter": 2,
					"size-variance": 1,
					"count": 1,
					"count-variance": 2
				},
				"chunk-chance": 30,
				"cluster-count": 10,
				"min-height": 50,
				"max-height": 170,
				"material": [
					"minecraft:air",
					`tfc:grass/${stoneType}`,
					`tfc:dry_grass/${stoneType}`,
					`tfc:dirt/${stoneType}`,
					`tfc:gravel/${stoneType}`,
					`tfcflorae:podzol/${stoneType}`
				],
				"follow-terrain": true,
				"retrogen": false,
				"biome": {
					"restriction": "whitelist",
					"value": [
						{
							"type": "dictionary",
							"entry": "FROZEN"
						}, 
						{
							"type": "dictionary",
							"entry": "SNOWY"
						},
						{
							"type": "dictionary",
							"entry": "SANDY"
						},
						{
							"type": "dictionary",
							"entry": "FOREST"
						}, 
						{
							"type": "dictionary",
							"entry": "MOUNTAIN"
						}, 
						{
							"type": "dictionary",
							"entry": "CONIFEROUS"
						}, 
						{
							"type": "dictionary",
							"entry": "PLAINS"
						}, 
						{
							"type": "dictionary",
							"entry": "SWAMP"
						}, 
						{
							"type": "dictionary",
							"entry": "LUSH"
						}, 
						{
							"type": "dictionary",
							"entry": "HILLS"
						}
					]
				},
				"dimension": {
					"restriction": "blacklist",
					"value": [
						-1,
						1
					]
				}
			}
		}
	}
    let large_boulderJSON = {
		"populate": {	
			`large_boulder_${stoneType}`: {
				"distribution": "surface",
				"generator": {
					"type": "boulder",
					"block": [
						{
							"name": `tfc_decoration:mossy_cobble/${stoneType}`
							"weight": 20
						},
						{
							"name": `tfcflorae:mossy_raw/${stoneType}`,
							"weight": 20
						},
						{
							"name": `tfc:cobble/${stoneType}`,
							"weight": 25
						},
						{
							"name": `tfc:raw/${stoneType}`,
							"weight": 35
						}
					],
					"material": [
						`tfc:grass/${stoneType}`,
						`tfc:dry_grass/${stoneType}`,
						`tfc:dirt/${stoneType}`,
						`tfc:gravel/${stoneType}`,
						`tfcflorae:podzol/${stoneType}`
					],
					"diameter": 2,
					"size-variance": 1,
					"count": 1,
					"count-variance": 2
				},
				"chunk-chance": 40,
				"cluster-count": 10,
				"min-height": 50,
				"max-height": 170,
				"material": [
					"minecraft:air",
					`tfc:grass/${stoneType}`,
					`tfc:dry_grass/${stoneType}`,
					`tfc:dirt/${stoneType}`,
					`tfc:gravel/${stoneType}`,
					`tfcflorae:podzol/${stoneType}`
				],
				"follow-terrain": true,
				"retrogen": false,
				"biome": {
					"restriction": "whitelist",
					"value": [
						{
							"type": "dictionary",
							"entry": "FROZEN"
						}, 
						{
							"type": "dictionary",
							"entry": "SNOWY"
						},
						{
							"type": "dictionary",
							"entry": "SANDY"
						},
						{
							"type": "dictionary",
							"entry": "FOREST"
						}, 
						{
							"type": "dictionary",
							"entry": "MOUNTAIN"
						}, 
						{
							"type": "dictionary",
							"entry": "CONIFEROUS"
						}, 
						{
							"type": "dictionary",
							"entry": "PLAINS"
						}, 
						{
							"type": "dictionary",
							"entry": "SWAMP"
						}, 
						{
							"type": "dictionary",
							"entry": "LUSH"
						}, 
						{
							"type": "dictionary",
							"entry": "HILLS"
						}
					]
				},
				"dimension": {
					"restriction": "blacklist",
					"value": [
						-1,
						1
					]
				}
			}
		}
	}
	
    // Boulders
    fs.writeFileSync(`./cofh/world/boulder/small/${stoneType}.json`, JSON.stringify(small_boulderJSON, null, 2))
    fs.writeFileSync(`./cofh/world/boulder/medium/${stoneType}.json`, JSON.stringify(medium_boulderJSON, null, 2))
    fs.writeFileSync(`./cofh/world/boulder/large/${stoneType}.json`, JSON.stringify(large_boulderJSON, null, 2))
}