const fs = require('fs');
var dir = './tmp';

if (!fs.existsSync(dir)){
    fs.mkdirSync(dir);
}

let ROCK_TYPES = {

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

let ORE_TYPES = {

    'adamantite': 'adamantite',
    'anthracite': 'anthracite',
    'baryte': 'baryte',
    'bastnaesite': 'bastnaesite',
    'bauxite': 'bauxite',
    'bertrandite': 'bertrandite',
    'bismuthinite': 'bismuthinite',
    'bituminous_coal': 'bituminous_coal',
    'borax': 'borax',
    'calaverite': 'calaverite',
	'calcite': 'calcite',
    'carnallite': 'carnallite',
    'cassiterite': 'cassiterite',
    'celestine': 'celestine',
    'chalcocite': 'chalcocite',
    'chalcopyrite': 'chalcopyrite',
    'chengdeite': 'chengdeite',
    'chromite': 'chromite',
    'cinnabar': 'cinnabar',
    'cobaltite': 'cobaltite',
    'columbite': 'columbite',
    'cooperite': 'cooperite',
    'cryolite': 'cryolite',
    'decrespignyite': 'decrespignyite',
    'diomignite': 'diomignite',
    'dzharkenite': 'dzharkenite',
    'erlichmanite': 'erlichmanite',
    'euxenite': 'euxenite',
    'fergusonite': 'fergusonite',
    'fluorite': 'fluorite',
    'gadolinite': 'gadolinite',
    'galena': 'galena',
    'gallite': 'gallite',
    'garnierite': 'garnierite',
    'geode_agate': 'geode_agate',
    'geode_amethyst': 'geode_amethyst',
    'geode_apatite': 'geode_apatite',
    'geode_aquamarine': 'geode_aquamarine',
    'geode_beryl': 'geode_beryl',
    'geode_bromargyrite': 'geode_bromargyrite',
    'geode_citrine': 'geode_citrine',
    'geode_diamond': 'geode_diamond',
    'geode_emerald': 'geode_emerald',
    'geode_garnet': 'geode_garnet',
    'geode_heliodor': 'geode_heliodor',
    'geode_iodargyrite': 'geode_iodargyrite',
    'geode_jade': 'geode_jade',
    'geode_jasper': 'geode_jasper',
    'geode_kyanite': 'geode_kyanite',
    'geode_moldavite': 'geode_moldavite',
    'geode_moonstone': 'geode_moonstone',
    'geode_opal': 'geode_opal',
    'geode_pyromorphite': 'geode_pyromorphite',
    'geode_quartz': 'geode_quartz',
    'geode_ruby': 'geode_ruby',
    'geode_sapphire': 'geode_sapphire',
    'geode_spinel': 'geode_spinel',
    'geode_sunstone': 'geode_sunstone',
    'geode_tanzanite': 'geode_tanzanite',
    'geode_topaz': 'geode_topaz',
    'geode_tourmaline': 'geode_tourmaline',
    'geode_zircon': 'geode_zircon',
    'germanite': 'germanite',
    'graphite': 'graphite',
    'greenockite': 'greenockite',
    'griceite': 'griceite',
    'gypsum': 'gypsum',
    'hafnon': 'hafnon',
    'halite': 'halite',
    'hematite': 'hematite',
    'iodate': 'iodate',
    'iridosmium': 'iridosmium',
    'iwashiroite': 'iwashiroite',
    'jet': 'jet',
    'kaolinite': 'kaolinite',
    'kimberlite': 'kimberlite',
    'kozoite': 'kozoite',
    'lapis_lazuli': 'lapis_lazuli',
    'laurite': 'laurite',
    'lepidolite': 'lepidolite',
    'lignite': 'lignite',
    'limonite': 'limonite',
    'limoriite': 'limoriite',
    'lithiophosphate': 'lithiophosphate',
    'livingstonite': 'livingstonite',
    'loparite': 'loparite',
    'lorandite': 'lorandite',
    'magnesite': 'magnesite',
    'magnetite': 'magnetite',
    'malachite': 'malachite',
    'marshite': 'marshite',
    'miassite': 'miassite',
    'microcline': 'microcline',
    'moissanite': 'moissanite',
    'molybdenite': 'molybdenite',
    'monazite': 'monazite',
    'native_ardite': 'native_ardite',
    'native_copper': 'native_copper',
    'native_gold': 'native_gold',
    'native_platinum': 'native_platinum',
    'native_rhodium': 'native_rhodium',
    'native_silver': 'native_silver',
    'olivine': 'olivine',
    'osmiridium': 'osmiridium',
    'paratooite': 'paratooite',
    'pentlandite': 'pentlandite',
    'petrified_wood': 'petrified_wood',
    'phosphorite': 'phosphorite',
    'pitchblende': 'pitchblende',
    'pollucite': 'pollucite',
    'proshchenkoite': 'proshchenkoite',
    'pyrargyrite': 'pyrargyrite',
    'pyrite': 'pyrite',
    'pyrolusite': 'pyrolusite',
    'radiumbarite': 'radiumbarite',
    'realgar': 'realgar',
    'rheniite': 'rheniite',
    'roquesite': 'roquesite',
    'rutheniridosmium': 'rutheniridosmium',
    'rutile': 'rutile',
    'salammoniac': 'salammoniac',
    'saltpeter': 'saltpeter',
    'samarskite': 'samarskite',
    'satinspar': 'satinspar',
    'selenide': 'selenide',
    'selenite': 'selenite',
    'serpentine': 'serpentine',
    'silica': 'silica',
    'sphalerite': 'sphalerite',
    'spodumene': 'spodumene',
    'stibnite': 'stibnite',
    'sulfur': 'sulfur',
    'sylvite': 'sylvite',
    'tantalite': 'tantalite',
    'tetrahedrite': 'tetrahedrite',
    'thorianite': 'thorianite',
    'thortveitite': 'thortveitite',
    'ulexite': 'ulexite',
    'uraninite': 'uraninite',
    'vanadinite': 'vanadinite',
    'wolframite': 'wolframite',
    'xenotime': 'xenotime',
    'yftisite': 'yftisite',
    'zircon': 'zircon'
}

for(let rockType of Object.keys(ROCK_TYPES))
{
    generateResources(rockType)
}

for(let oreType of Object.keys(ORE_TYPES))
{
    generateResources(oreType)
}

function generateResources(rockType, oreType)
{
    let ore_depositJSON = {
	  "__comment": "Generated by generateResources.py function: blockstate",
	  "forge_marker": 1,
	  "defaults": {
		"model": "item/generated",
		"textures": {
		  "particle": `tfc:blocks/stonetypes/raw/${rockType}`,
		  "texture": `tfc:blocks/stonetypes/raw/${rockType}`,
		  "ore": `tfc:blocks/ores/${oreType}`,
		  "layer0": `tfc:items/ore/${rockType}`
		}
	  },
	  "variants": {
		"normal": [
		  {
			"model": "tfcflorae:surface/ore/rock_1",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_2",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_2_ne",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_2_none",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_2_nse",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_3",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_3_n",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_3_ne",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_3_none",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_4",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_5",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_5_ne",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_5_none",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_5_nse",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_6",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_6_n",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_6_ne",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_6_nse",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_7",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_7_n",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_7_ne",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_7_none",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_7_ns",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_7_nse",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_8",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_8_n",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_8_ne",
			"weight": 20
		  },
		  {
			"model": "tfcflorae:surface/ore/rock_8_none",
			"weight": 20
		  }
		]
	  }
	}

    // Blockstates 
    fs.writeFileSync(`./src/main/resources/assets/tfcflorae/blockstates/surface/ore/${oreType}/${rockType}.json`, JSON.stringify(ore_depositJSON, null, 2))

}