#!/bin/env python3
import json
import os
import time
import zipfile

os.chdir('cofh/world/')

ROCK_TYPES = [
    'andesite',
    'arkose',
    'basalt',
    'blaimorite',
    'blueschist',
    'boninite',
    'carbonatite',
    'cataclasite',
    'chalk',
    'chert',
    'claystone',
    'conglomerate',
    'dacite',
    'diorite',
    'dolomite',
    'foidolite',
    'gabbro',
    'gneiss',
    'granite',
    'greenschist',
    'jaspillite',
    'limestone',
    'marble',
    'mylonite',
    'phyllite',
    'quartzite',
    'rhyolite',
    'rocksalt',
    'schist',
    'shale',
    'slate',
    'travertine',
    'wackestone',
	'breccia',
    'porphyry',
    'peridotite',
    'mudstone',
    'sandstone',
    'siltstone',
    'catlinite',
    'novaculite',
    'soapstone',
    'komatiite',
]

# ROCK STUFF

for rock_type in ROCK_TYPES:
    {
		"populate":{
			'small_boulder_%s' % rock_type:{
				"distribution":"surface",
				"generator":{
					"type":"boulder",
					"block":[
						{
							"name":'tfc_decoration:mossy_cobble/%s' % rock_type,
							"weight":20
						},
						{
							"name":'tfcflorae:mossy_raw/%s' % rock_type,
							"weight":20
						},
						{
							"name":'tfc:cobble/%s' % rock_type,
							"weight":25
						},
						{
							"name":'tfc:raw/%s' % rock_type,
							"weight":35
						}
					],
					"material":[
						'tfc:grass/%s' % rock_type,
						'tfc:dry_grass/%s' % rock_type,
						'tfc:dirt/%s' % rock_type,
						'tfc:gravel/%s' % rock_type,
						'tfcflorae:podzol/%s' % rock_type
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
					'tfc:grass/%s' % rock_type,
					'tfc:dry_grass/%s' % rock_type,
					'tfc:dirt/%s' % rock_type,
					'tfc:gravel/%s' % rock_type,
					'tfcflorae:podzol/%s' % rock_type
				],
				"follow-terrain":'true',
				"retrogen":'false',
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