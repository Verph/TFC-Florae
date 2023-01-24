const fs = require('fs-extra')

let ROCK_TYPES = {
  'granite': 'granite',
  'diorite': 'diorite',
  'gabbro': 'gabbro',
  'shale': 'shale',
  'claystone': 'claystone',
  'limestone': 'limestone',
  'conglomerate': 'conglomerate',
  'dolomite': 'dolomite',
  'chert': 'chert',
  'chalk': 'chalk',
  'rhyolite': 'rhyolite',
  'basalt': 'basalt',
  'andesite': 'andesite',
  'dacite': 'dacite',
  'quartzite': 'quartzite',
  'slate': 'slate',
  'phyllite': 'phyllite',
  'schist': 'schist',
  'gneiss': 'gneiss',
  'marble': 'marble',
  'cataclasite': 'cataclasite',
  'porphyry': 'porphyry',
  'red_granite': 'red_granite',
  'laterite': 'laterite',
  'dripstone': 'dripstone',
  'breccia': 'breccia',
  'foidolite': 'foidolite',
  'peridotite': 'peridotite',
  'blaimorite': 'blaimorite',
  'boninite': 'boninite',
  'carbonatite': 'carbonatite',
  'mudstone': 'mudstone',
  'sandstone': 'sandstone',
  'siltstone': 'siltstone',
  'arkose': 'arkose',
  'jaspillite': 'jaspillite',
  'travertine': 'travertine',
  'wackestone': 'wackestone',
  'blackband_ironstone': 'blackband_ironstone',
  'blueschist': 'blueschist',
  'catlinite': 'catlinite',
  'greenschist': 'greenschist',
  'novaculite': 'novaculite',
  'soapstone': 'soapstone',
  'komatiite': 'komatiite',
  'mylonite': 'mylonite'
}

let ROCK_NAMESPACES = {
  'granite': 'tfc',
  'diorite': 'tfc',
  'gabbro': 'tfc',
  'shale': 'tfc',
  'claystone': 'tfc',
  'limestone': 'tfc',
  'conglomerate': 'tfc',
  'dolomite': 'tfc',
  'chert': 'tfc',
  'chalk': 'tfc',
  'rhyolite': 'tfc',
  'basalt': 'tfc',
  'andesite': 'tfc',
  'dacite': 'tfc',
  'quartzite': 'tfc',
  'slate': 'tfc',
  'phyllite': 'tfc',
  'schist': 'tfc',
  'gneiss': 'tfc',
  'marble': 'tfc',
  'cataclasite': 'tfcflorae',
  'porphyry': 'tfcflorae',
  'red_granite': 'tfcflorae',
  'laterite': 'tfcflorae',
  'dripstone': 'tfcflorae',
  'breccia': 'tfcflorae',
  'foidolite': 'tfcflorae',
  'peridotite': 'tfcflorae',
  'blaimorite': 'tfcflorae',
  'boninite': 'tfcflorae',
  'carbonatite': 'tfcflorae',
  'mudstone': 'tfcflorae',
  'sandstone': 'tfcflorae',
  'siltstone': 'tfcflorae',
  'arkose': 'tfcflorae',
  'jaspillite': 'tfcflorae',
  'travertine': 'tfcflorae',
  'wackestone': 'tfcflorae',
  'blackband_ironstone': 'tfcflorae',
  'blueschist': 'tfcflorae',
  'catlinite': 'tfcflorae',
  'greenschist': 'tfcflorae',
  'novaculite': 'tfcflorae',
  'soapstone': 'tfcflorae',
  'komatiite': 'tfcflorae',
  'mylonite': 'tfcflorae'
}

let sandColors = {
  'black': 'black',
  'blue': 'blue',
  'brown': 'brown',
  'gray': 'gray',
  'green': 'green',
  'light_green': 'light_green',
  'orange': 'orange',
  'pink': 'pink',
  'purple': 'purple',
  'red': 'red',
  'white': 'white',
  'yellow': 'yellow'
}

let sandstoneTypes = {
  'layered': 'layered'
}

let dirtTypes = {
  'humus': 'humus',
  'loam': 'loam',
  'sandy_loam': 'sandy_loam',
  'silt': 'silt',
  'silty_loam': 'silty_loam'
}

let rockBlockTypes = {
  'bricks': 'bricks',
  'chiseled': 'chiseled',
  'cobble': 'cobble',
  'cobbled_bricks': 'cobbled_bricks',
  'column': 'column',
  'cracked_bricks': 'cracked_bricks',
  'cracked_flagstone_bricks': 'cracked_flagstone_bricks',
  'flagstone': 'flagstone',
  'flagstone_bricks': 'flagstone_bricks',
  'mossy_cobble': 'mossy_cobble',
  'mossy_flagstone_bricks': 'mossy_flagstone_bricks',
  'gravel': 'gravel',
  'polished_column': 'polished_column',
  'raw': 'raw',
  'smooth': 'smooth',
  'stone_tiles': 'stone_tiles'
}

let soilRockTypes = {
  'dirtiest_stone_tiles': 'dirtiest_stone_tiles',
  'dirtier_stone_tiles': 'dirtier_stone_tiles',
  'dirty_stone_tiles': 'dirty_stone_tiles',
  'pebble_compact_dirt': 'pebble_compact_dirt',
  'rocky_compact_dirt': 'rocky_compact_dirt',
  'rockier_compact_dirt': 'rockier_compact_dirt',
  'rockiest_compact_dirt': 'rockiest_compact_dirt'
}

let decoTypes = {
  'slab': 'slab',
  'stairs': 'stairs',
  'wall': 'wall'
}

let soilRockTypes2 = {
  'dirtiest_stone_tiles': 'dirtiest_stone_tiles',
  'dirtier_stone_tiles': 'dirtier_stone_tiles',
  'dirty_stone_tiles': 'dirty_stone_tiles'
}

fs.ensureDir(`./loot_tables/blocks/deposit/cassiterite`)
fs.ensureDir(`./loot_tables/blocks/deposit/native_copper`)
fs.ensureDir(`./loot_tables/blocks/deposit/native_gold`)
fs.ensureDir(`./loot_tables/blocks/deposit/native_silver`)


for(let sandstoneType in sandstoneTypes)
{
  fs.ensureDir(`./assets/blockstates/sandstone/${sandstoneType}`)
  fs.ensureDir(`./assets/blockstates/sandstone/${sandstoneType}/slab`)
  fs.ensureDir(`./assets/blockstates/sandstone/${sandstoneType}/stairs`)
  fs.ensureDir(`./assets/blockstates/sandstone/${sandstoneType}/wall`)

  fs.ensureDir(`./assets/models/block/sandstone/${sandstoneType}`)
  fs.ensureDir(`./assets/models/block/sandstone/${sandstoneType}/slab/bottom`)
  fs.ensureDir(`./assets/models/block/sandstone/${sandstoneType}/slab/top`)
  fs.ensureDir(`./assets/models/block/sandstone/${sandstoneType}/stairs`)
  fs.ensureDir(`./assets/models/block/sandstone/${sandstoneType}/stairs/inner`)
  fs.ensureDir(`./assets/models/block/sandstone/${sandstoneType}/stairs/outer`)
  fs.ensureDir(`./assets/models/block/sandstone/${sandstoneType}/wall/inventory`)
  fs.ensureDir(`./assets/models/block/sandstone/${sandstoneType}/wall/post`)
  fs.ensureDir(`./assets/models/block/sandstone/${sandstoneType}/wall/side`)
  fs.ensureDir(`./assets/models/block/sandstone/${sandstoneType}/wall/side/tall`)

	fs.ensureDir(`./assets/models/item/sandstone/${sandstoneType}`)
	fs.ensureDir(`./assets/models/item/sandstone/${sandstoneType}/slab`)
	fs.ensureDir(`./assets/models/item/sandstone/${sandstoneType}/stairs`)
	fs.ensureDir(`./assets/models/item/sandstone/${sandstoneType}/wall`)

  fs.ensureDir(`./data/loot_tables/blocks/sandstone/${sandstoneType}`)
  fs.ensureDir(`./data/loot_tables/blocks/sandstone/${sandstoneType}/slab`)
  fs.ensureDir(`./data/loot_tables/blocks/sandstone/${sandstoneType}/stairs`)
  fs.ensureDir(`./data/loot_tables/blocks/sandstone/${sandstoneType}/wall`)

  fs.ensureDir(`./data/recipes/crafting/sandstone/${sandstoneType}`)
  fs.ensureDir(`./data/recipes/crafting/sandstone/${sandstoneType}/slab`)
  fs.ensureDir(`./data/recipes/crafting/sandstone/${sandstoneType}/stairs`)
  fs.ensureDir(`./data/recipes/crafting/sandstone/${sandstoneType}/wall`)

  fs.ensureDir(`./data/recipes/chisel/slab/sandstone/${sandstoneType}`)
  fs.ensureDir(`./data/recipes/chisel/stair/sandstone/${sandstoneType}`)

  fs.ensureDir(`./data/recipes/stonecutting/sandstone/${sandstoneType}/slab`)
  fs.ensureDir(`./data/recipes/stonecutting/sandstone/${sandstoneType}/stairs`)
  fs.ensureDir(`./data/recipes/stonecutting/sandstone/${sandstoneType}/wall`)
}

for(let ROCK_TYPE in ROCK_TYPES)
{
  fs.ensureDir(`./assets/models/block/rock/rock_pile/${ROCK_TYPE}`)
  fs.ensureDir(`./assets/models/block/rock/mossy_rock_pile/${ROCK_TYPE}`)
}

for(let blockType in rockBlockTypes)
{
  fs.ensureDir(`./assets/blockstates/rock/${blockType}`)

  fs.ensureDir(`./assets/models/block/rock/${blockType}`)
  fs.ensureDir(`./assets/models/block/rock/${blockType}/slab/bottom`)
  fs.ensureDir(`./assets/models/block/rock/${blockType}/slab/top`)
  fs.ensureDir(`./assets/models/block/rock/${blockType}/stairs`)
  fs.ensureDir(`./assets/models/block/rock/${blockType}/stairs/inner`)
  fs.ensureDir(`./assets/models/block/rock/${blockType}/stairs/outer`)
  fs.ensureDir(`./assets/models/block/rock/${blockType}/wall/inventory`)
  fs.ensureDir(`./assets/models/block/rock/${blockType}/wall/post`)
  fs.ensureDir(`./assets/models/block/rock/${blockType}/wall/side`)
  fs.ensureDir(`./assets/models/block/rock/${blockType}/wall/side/tall`)

  fs.ensureDir(`./assets/models/item/rock/${blockType}`)

  fs.ensureDir(`./data/recipes/crafting/rock/${blockType}`)

  for(let decoType in decoTypes)
  {
    fs.ensureDir(`./assets/blockstates/rock/${blockType}/${decoType}`)
    fs.ensureDir(`./assets/models/item/rock/${blockType}/${decoType}`)
    fs.ensureDir(`./data/recipes/crafting/rock/${blockType}/${decoType}`)
  }
}

for(let soilRockType in soilRockTypes)
{
  for(let soilType in dirtTypes)
  {
    fs.ensureDir(`./assets/blockstates/${soilRockType}/${soilType}`)

    fs.ensureDir(`./assets/models/block/${soilRockType}/${soilType}`)
    fs.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/slab/bottom`)
    fs.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/slab/top`)
    fs.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/stairs`)
    fs.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/stairs/inner`)
    fs.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/stairs/outer`)
    fs.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/wall/inventory`)
    fs.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/wall/post`)
    fs.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/wall/side`)
    fs.ensureDir(`./assets/models/block/${soilRockType}/${soilType}/wall/side/tall`)

    fs.ensureDir(`./data/recipes/crafting/soil/${soilRockType}/${soilType}`)

    for(let decoType in decoTypes)
    {
      fs.ensureDir(`./assets/blockstates/${soilRockType}/${soilType}/${decoType}`)
      fs.ensureDir(`./assets/models/item/${soilRockType}/${soilType}/${decoType}`)
      fs.ensureDir(`./data/recipes/crafting/soil/${soilRockType}/${soilType}/${decoType}`)
    }
  }
}

for(let soilRockType in soilRockTypes2)
{
  for(let soilType in dirtTypes)
  {
    fs.ensureDir(`./data/recipes/brushing/soil/${soilRockType}/${soilType}`)

    for(let decoType in decoTypes)
    {
      fs.ensureDir(`./data/recipes/brushing/soil/${soilRockType}/${soilType}/${decoType}`)
    }
  }
}

fs.ensureDir(`./data/recipes/chisel/stair/wood`)
fs.ensureDir(`./data/recipes/chisel/slab/wood`)
fs.ensureDir(`./assets/models/block/wood/wood_wall/inventory`)
fs.ensureDir(`./assets/models/block/wood/wood_wall/post`)
fs.ensureDir(`./assets/models/block/wood/wood_wall/side`)
fs.ensureDir(`./assets/models/block/wood/wood_wall/side/tall`)
fs.ensureDir(`./assets/models/item/wood/wood_wall`)
fs.ensureDir(`./assets/models/block/wood/log_wall/inventory`)
fs.ensureDir(`./assets/models/block/wood/log_wall/post`)
fs.ensureDir(`./assets/models/block/wood/log_wall/side`)
fs.ensureDir(`./assets/models/block/wood/log_wall/side/tall`)
fs.ensureDir(`./assets/models/item/wood/log_wall`)
fs.ensureDir(`./assets/blockstates/wood/wood_wall`)
fs.ensureDir(`./assets/blockstates/wood/log_wall`)
fs.ensureDir(`./assets/blockstates/wood/chiseled_bookshelf`)
fs.ensureDir(`./assets/models/block/wood/chiseled_bookshelf/0`)
fs.ensureDir(`./assets/models/block/wood/chiseled_bookshelf/1`)
fs.ensureDir(`./assets/models/block/wood/chiseled_bookshelf/2`)
fs.ensureDir(`./assets/models/block/wood/chiseled_bookshelf/3`)
fs.ensureDir(`./assets/models/block/wood/chiseled_bookshelf/4`)
fs.ensureDir(`./assets/models/block/wood/chiseled_bookshelf/5`)
fs.ensureDir(`./assets/models/block/wood/chiseled_bookshelf/6`)
fs.ensureDir(`./assets/models/item/wood/chiseled_bookshelf`)

let woodBlockTypes = {
  'stripped_wood': 'stripped_wood',
  'stripped_log': 'stripped_log',
  'wood': 'wood',
  'log': 'log',
  'twig': 'twig',
  'barrel': 'barrel',
  'barrel_sealed': 'barrel_sealed',
  'chest': 'chest',
  'fallen_leaves': 'fallen_leaves',
  'leaves': 'leaves',
  'lectern': 'lectern',
  'sapling': 'sapling',
  'scribing_table': 'scribing_table',
  'sluice': 'sluice',
  'support': 'support',
  'horizontal_support': 'horizontal_support',
  'vertical_support': 'vertical_support',
  'trapped_chest': 'trapped_chest',
  'planks': 'planks',
  'bookshelf': 'bookshelf',
  'button': 'button',
  'door': 'door',
  'fence_gate': 'fence_gate',
  'fence': 'fence',
  'log_fence': 'log_fence',
  'loom': 'loom',
  'pressure_plate': 'pressure_plate',
  'sign': 'sign',
  'wall_sign': 'wall_sign',
  'tool_rack': 'tool_rack',
  'trapdoor': 'trapdoor',
  'workbench': 'workbench',
  'potted_sapling': 'potted_sapling'
}
let woodItemTypes = {
  'stripped_wood': 'stripped_wood',
  'stripped_log': 'stripped_log',
  'wood': 'wood',
  'log': 'log',
  'twig': 'twig',
  'trapped_chest': 'trapped_chest',
  'support': 'support',
  'sluice': 'sluice',
  'scribing_table': 'scribing_table',
  'sapling': 'sapling',
  'lumber': 'lumber',
  'lectern': 'lectern',
  'fallen_leaves': 'fallen_leaves',
  'chest_minecart': 'chest_minecart',
  'chest': 'chest',
  'boat': 'boat',
  'barrel': 'barrel',
  'planks': 'planks',
  'bookshelf': 'bookshelf',
  'button': 'button',
  'door': 'door',
  'fence_gate': 'fence_gate',
  'log_fence': 'log_fence',
  'loom': 'loom',
  'pressure_plate': 'pressure_plate',
  'sign': 'sign',
  'slab': 'slab',
  'stairs': 'stairs',
  'tool_rack': 'tool_rack',
  'trapdoor': 'trapdoor',
  'workbench': 'workbench'
}

for(let woodItemType in woodItemTypes)
{
  fs.ensureDir(`./assets/models/item/wood/${woodItemType}`)
	fs.ensureDir(`./data/recipes/crafting/wood/${woodItemType}`)
	fs.ensureDir(`./data/recipes/crafting/wood/lumber_log`)
	fs.ensureDir(`./data/recipes/crafting/wood/lumber_planks`)
	fs.ensureDir(`./data/recipes/crafting/wood/log_wall`)
	fs.ensureDir(`./data/recipes/crafting/wood/wood_wall`)
	fs.ensureDir(`./data/recipes/crafting/wood/chiseled_bookshelf`)
	fs.ensureDir(`./data/loot_tables/blocks/wood/${woodItemType}`)
	fs.ensureDir(`./data/loot_tables/blocks/wood/log_wall`)
	fs.ensureDir(`./data/loot_tables/blocks/wood/wood_wall`)
	fs.ensureDir(`./data/loot_tables/blocks/wood/chiseled_bookshelf`)
}

for(let woodBlockType in woodBlockTypes)
{
  fs.ensureDir(`./assets/models/block/wood/${woodBlockType}`)
  fs.ensureDir(`./assets/models/item/wood/${woodBlockType}`)
  fs.ensureDir(`./assets/blockstates/wood/${woodBlockType}`)
	fs.ensureDir(`./data/advancements/crafting/wood/boat`)
	fs.ensureDir(`./data/advancements/crafting/wood/lumber_log`)
	fs.ensureDir(`./data/advancements/crafting/wood/lumber_planks`)
	fs.ensureDir(`./data/advancements/crafting/wood/${woodBlockType}`)
	fs.ensureDir(`./data/recipes/crafting/wood/${woodBlockType}`)
	fs.ensureDir(`./data/loot_tables/blocks/wood/${woodBlockType}`)
}

for(let decoType in decoTypes)
{
  fs.ensureDir(`./assets/models/block/wood/${decoType}`)
  fs.ensureDir(`./assets/blockstates/wood/${decoType}`)
	fs.ensureDir(`./data/advancements/crafting/wood/${decoType}`)
}