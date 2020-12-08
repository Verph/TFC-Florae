const fs = require('fs');

let FRUIT_WOOD_TYPES = {
    'abiu': 'abiu',
    'amla': 'amla',
    'apricot': 'apricot',
    'avocado': 'avocado',
    'bael': 'bael',
    'bay_laurel': 'bay_laurel',
    'ber': 'ber',
    'bergamot': 'bergamot',
    'black_cherry': 'black_cherry',
    'black_pepper': 'black_pepper',
    'blackcurrant': 'blackcurrant',
    'blackthorn': 'blackthorn',
    'buddha_hand': 'buddha_hand',
    'cacao': 'cacao',
    'cherry_plum': 'cherry_plum',
    'citron': 'citron',
    'coconut': 'coconut',
    'coffea': 'coffea',
    'crabapple': 'crabapple',
    'damson_plum': 'damson_plum',
    'date': 'date',
    'elder': 'elder',
    'elderberry': 'elderberry',
    'fig': 'fig',
    'finger_lime': 'finger_lime',
    'grapefruit': 'grapefruit',
    'guava': 'guava',
    'ice_cream_bean': 'ice_cream_bean',
    'jackfruit': 'jackfruit',
    'jujube': 'jujube',
    'juniper': 'juniper',
    'kaki': 'kaki',
    'key_lime': 'key_lime',
    'kluwak': 'kluwak',
    'kumquat': 'kumquat',
    'lime': 'lime',
    'longan': 'longan',
    'loquat': 'loquat',
    'lychee': 'lychee',
    'mamey_sapote': 'mamey_sapote',
    'manderin': 'manderin',
    'mango': 'mango',
    'mangosteen': 'mangosteen',
    'nectarine': 'nectarine',
    'ohia_ai': 'ohia_ai',
    'osage_orange': 'osage_orange',
    'papaya': 'papaya',
    'passion_fruit': 'passion_fruit',
    'pear': 'pear',
    'persimmon': 'persimmon',
    'persian_lime': 'persian_lime',
    'peruvian_pepper': 'peruvian_pepper',
    'plantain': 'plantain',
    'plum': 'plum',
    'pomegranate': 'pomegranate',
    'pomelo': 'pomelo',
    'quince': 'quince',
    'rainier_cherry': 'rainier_cherry',
    'red_banana': 'red_banana',
    'red_currant': 'red_currant',
    'sand_pear': 'sand_pear',
    'sapodilla': 'sapodilla',
    'satsuma': 'satsuma',
    'sour_cherry': 'sour_cherry',
    'soursop': 'soursop',
    'star_anise': 'star_anise',
    'starfruit': 'starfruit',
    'tamarillo': 'tamarillo',
    'tangerine': 'tangerine',
    'tropical_apricot': 'tropical_apricot',
    'wild_cherry': 'wild_cherry',
    'acorn': 'acorn',
    'almond': 'almond',
    'beechnut': 'beechnut',
    'black_walnut': 'black_walnut',
    'brazil_nut': 'brazil_nut',
    'breadnut': 'breadnut',
    'bunya_nut': 'bunya_nut',
    'butternut': 'butternut',
    'candlenut': 'candlenut',
    'cashew': 'cashew',
    'ginkgo_nut': 'ginkgo_nut',
    'hazelnut': 'hazelnut',
    'heartnut': 'heartnut',
    'hickory_nut': 'hickory_nut',
    'kola_nut': 'kola_nut',
    'kukui_nut': 'kukui_nut',
    'macadamia': 'macadamia',
    'mongongo': 'mongongo',
    'monkey_puzzle_nut': 'monkey_puzzle_nut',
    'nutmeg': 'nutmeg',
    'paradise_nut': 'paradise_nut',
    'pecan': 'pecan',
    'pine_nut': 'pine_nut',
    'pistachio': 'pistachio',
    'walnut': 'walnut'
    }

let DRIED_FOOD_TYPES = {
    'abiu': 'abiu',
    'amla': 'amla',
    'apricot': 'apricot',
    'avocado': 'avocado',
    'bael': 'bael',
    'bay_laurel': 'bay_laurel',
    'ber': 'ber',
    'bergamot': 'bergamot',
    'black_cherry': 'black_cherry',
    'black_pepper': 'black_pepper',
    'blackcurrant': 'blackcurrant',
    'blackthorn': 'blackthorn',
    'buddha_hand': 'buddha_hand',
    'cacao': 'cacao',
    'cherry_plum': 'cherry_plum',
    'citron': 'citron',
    'coconut': 'coconut',
    'coffea': 'coffea',
    'crabapple': 'crabapple',
    'damson_plum': 'damson_plum',
    'date': 'date',
    'elder': 'elder',
    'elderberry': 'elderberry',
    'fig': 'fig',
    'finger_lime': 'finger_lime',
    'grapefruit': 'grapefruit',
    'guava': 'guava',
    'ice_cream_bean': 'ice_cream_bean',
    'jackfruit': 'jackfruit',
    'jujube': 'jujube',
    'juniper': 'juniper',
    'kaki': 'kaki',
    'key_lime': 'key_lime',
    'kluwak': 'kluwak',
    'kumquat': 'kumquat',
    'lime': 'lime',
    'longan': 'longan',
    'loquat': 'loquat',
    'lychee': 'lychee',
    'mamey_sapote': 'mamey_sapote',
    'manderin': 'manderin',
    'mango': 'mango',
    'mangosteen': 'mangosteen',
    'nectarine': 'nectarine',
    'ohia_ai': 'ohia_ai',
    'osage_orange': 'osage_orange',
    'papaya': 'papaya',
    'passion_fruit': 'passion_fruit',
    'pear': 'pear',
    'persimmon': 'persimmon',
    'persian_lime': 'persian_lime',
    'peruvian_pepper': 'peruvian_pepper',
    'plantain': 'plantain',
    'plum': 'plum',
    'pomegranate': 'pomegranate',
    'pomelo': 'pomelo',
    'quince': 'quince',
    'rainier_cherry': 'rainier_cherry',
    'red_banana': 'red_banana',
    'red_currant': 'red_currant',
    'sand_pear': 'sand_pear',
    'sapodilla': 'sapodilla',
    'satsuma': 'satsuma',
    'sour_cherry': 'sour_cherry',
    'soursop': 'soursop',
    'star_anise': 'star_anise',
    'starfruit': 'starfruit',
    'tamarillo': 'tamarillo',
    'tangerine': 'tangerine',
    'tropical_apricot': 'tropical_apricot',
    'wild_cherry': 'wild_cherry',
    'banana': 'banana',
    'cherry': 'cherry',
    'olive': 'olive',
    'red_apple': 'red_apple',
    'green_apple': 'green_apple',
    'lemon': 'lemon',
    'orange': 'orange',
    'peach': 'peach',
    'plum': 'plum',
    'black_pepper': 'black_pepper',
    'black_tea': 'black_tea',
    'green_tea': 'green_tea',
    'white_tea': 'white_tea',
    'cannabis_bud': 'cannabis_bud',
    'cannabis_leaf': 'cannabis_leaf',
    'coca_leaf': 'coca_leaf',
    'opium_poppy_bulb': 'opium_poppy_bulb',
    'peyote': 'peyote',
    'tobacco_leaf': 'tobacco_leaf',
    'cocoa_beans': 'cocoa_beans',
    'coffea_cherries': 'coffea_cherries',
    'blackberry': 'blackberry',
    'blueberry': 'blueberry',
    'bunch_berry': 'bunch_berry',
    'cloud_berry': 'cloud_berry',
    'cranberry': 'cranberry',
    'elderberry': 'elderberry',
    'gooseberry': 'gooseberry',
    'raspberry': 'raspberry',
    'snow_berry': 'snow_berry',
    'strawberry': 'strawberry',
    'wintergreen_berry': 'wintergreen_berry'
    }

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

    let MISC_FOOD_TYPES = {
    'allspice': 'allspice',
    'clove': 'clove',
    'curry_leaf': 'curry_leaf',
    'star_anise': 'star_anise',
    'liquorice_root': 'liquorice_root',
    'cassia_cinnamon_bark': 'cassia_cinnamon_bark',
    'ground_cassia_cinnamon': 'ground_cassia_cinnamon',
    'ceylon_cinnamon_bark': 'ceylon_cinnamon_bark',
    'ground_ceylon_cinnamon': 'ground_ceylon_cinnamon',
    'ground_black_pepper': 'ground_black_pepper',
    'black_tea': 'black_tea',
    'green_tea': 'green_tea',
    'white_tea': 'white_tea',
    'cannabis_bud': 'cannabis_bud',
    'cannabis_leaf': 'cannabis_leaf',
    'coca_leaf': 'coca_leaf',
    'opium_poppy_bulb': 'opium_poppy_bulb',
    'peyote': 'peyote',
    'tobacco_leaf': 'tobacco_leaf',
    'raw_eel': 'raw_eel',
    'cooked_eel': 'cooked_eel',
    'raw_crab': 'raw_crab',
    'cooked_crab': 'cooked_crab',
    'raw_clam': 'raw_clam',
    'cooked_clam': 'cooked_clam',
    'raw_scallop': 'raw_scallop',
    'cooked_scallop': 'cooked_scallop',
    'raw_starfish': 'raw_starfish',
    'cooked_starfish': 'cooked_starfish',
    'pumpkin': 'pumpkin',
    'melon': 'melon',
    'cocoa_beans': 'cocoa_beans',
    'roasted_cocoa_beans': 'roasted_cocoa_beans',
    'cocoa_powder': 'cocoa_powder',
    'cocoa_butter': 'cocoa_butter',
    'chocolate': 'chocolate',
    'roasted_coffee_beans': 'roasted_coffee_beans',
    'coffee_powder': 'coffee_powder',
    'amaranth': 'amaranth',
    'amaranth_grain': 'amaranth_grain',
    'amaranth_flour': 'amaranth_flour',
    'amaranth_dough': 'amaranth_dough',
    'amaranth_bread': 'amaranth_bread',
    'buckwheat': 'buckwheat',
    'buckwheat_grain': 'buckwheat_grain',
    'buckwheat_flour': 'buckwheat_flour',
    'buckwheat_dough': 'buckwheat_dough',
    'buckwheat_bread': 'buckwheat_bread',
    'fonio': 'fonio',
    'fonio_grain': 'fonio_grain',
    'fonio_flour': 'fonio_flour',
    'fonio_dough': 'fonio_dough',
    'fonio_bread': 'fonio_bread',
    'millet': 'millet',
    'millet_grain': 'millet_grain',
    'millet_flour': 'millet_flour',
    'millet_dough': 'millet_dough',
    'millet_bread': 'millet_bread',
    'quinoa': 'quinoa',
    'quinoa_grain': 'quinoa_grain',
    'quinoa_flour': 'quinoa_flour',
    'quinoa_dough': 'quinoa_dough',
    'quinoa_bread': 'quinoa_bread',
    'spelt': 'spelt',
    'spelt_grain': 'spelt_grain',
    'spelt_flour': 'spelt_flour',
    'spelt_dough': 'spelt_dough',
    'spelt_bread': 'spelt_bread',
    'wild_rice': 'wild_rice',
    'wild_rice_grain': 'wild_rice_grain',
    'wild_rice_flour': 'wild_rice_flour',
    'wild_rice_dough': 'wild_rice_dough',
    'wild_rice_bread': 'wild_rice_bread',
    'linseed': 'linseed',
    'rape_seed': 'rape_seed',
    'sunflower_seed': 'sunflower_seed',
    'opium_poppy_seed': 'opium_poppy_seed',
    'hash_muffin': 'hash_muffin',
    'rutabaga': 'rutabaga',
    'turnip': 'turnip',
    'mustard': 'mustard',
    'black_eyed_peas': 'black_eyed_peas',
    'green_cayenne_pepper': 'green_cayenne_pepper',
    'red_cayenne_pepper': 'red_cayenne_pepper',
    'ginger': 'ginger',
    'ginseng': 'ginseng',
    'celery': 'celery',
    'lettuce': 'lettuce',
    'peanut': 'peanut',
    'sweet_potato': 'sweet_potato',
    'sugar_beet': 'sugar_beet',
    'linseed_paste': 'linseed_paste',
    'rape_seed_paste': 'rape_seed_paste',
    'sunflower_seed_paste': 'sunflower_seed_paste',
    'opium_poppy_seed_paste': 'opium_poppy_seed_paste',
    'mashed_sugar_beet': 'mashed_sugar_beet',
    'mashed_sugar_cane': 'mashed_sugar_cane',
    'soybean_paste': 'soybean_paste',
    'cow_cheese': 'cow_cheese',
    'goat_cheese': 'goat_cheese',
    'sheep_cheese': 'sheep_cheese',
    'amaranth_bread_sandwich': 'amaranth_bread_sandwich',
    'buckwheat_bread_sandwich': 'buckwheat_bread_sandwich',
    'fonio_bread_sandwich': 'fonio_bread_sandwich',
    'millet_bread_sandwich': 'millet_bread_sandwich',
    'quinoa_bread_sandwich': 'quinoa_bread_sandwich',
    'spelt_bread_sandwich': 'spelt_bread_sandwich',
    'wild_rice_bread_sandwich': 'wild_rice_bread_sandwich',
    'roasted_coffea_cherries': 'roasted_coffea_cherries',
    'dark_chocolate': 'dark_chocolate',
    'milk_chocolate': 'milk_chocolate',
    'white_chocolate': 'white_chocolate',
    'pumpkin_scooped': 'pumpkin_scooped',
    'pumpkin_chunks': 'pumpkin_chunks'
    }

    let CROP_PRODUCT_TYPES = {
    'papyrus_pulp': 'papyrus_pulp',
    'papyrus_fiber': 'papyrus_fiber',
    'papyrus_paper': 'papyrus_paper',
    'agave': 'agave',
    'sisal_fiber': 'sisal_fiber',
    'sisal_string': 'sisal_string',
    'sisal_cloth': 'sisal_cloth',
    'cotton_boll': 'cotton_boll',
    'cotton_yarn': 'cotton_yarn',
    'cotton_cloth': 'cotton_cloth',
    'flax': 'flax',
    'flax_fiber': 'flax_fiber',
    'linen_string': 'linen_string',
    'linen_cloth': 'linen_cloth',
    'hemp': 'hemp',
    'hemp_fiber': 'hemp_fiber',
    'hemp_string': 'hemp_string',
    'hemp_cloth': 'hemp_cloth',
    'indigo': 'indigo',
    'madder': 'madder',
    'weld': 'weld',
    'woad': 'woad',
    'hops': 'hops',
    'rape': 'rape',
    'malt_barley': 'malt_barley',
    'malt_corn': 'malt_corn',
    'malt_rice': 'malt_rice',
    'malt_rye': 'malt_rye',
    'malt_wheat': 'malt_wheat',
    'malt_amaranth': 'malt_amaranth',
    'malt_buckwheat': 'malt_buckwheat',
    'malt_fonio': 'malt_fonio',
    'malt_millet': 'malt_millet',
    'malt_quinoa': 'malt_quinoa',
    'malt_spelt': 'malt_spelt',
    'malt_wild_rice': 'malt_wild_rice',
    'soybean_jute_disc': 'soybean_jute_disc',
    'soybean_silk_disc': 'soybean_silk_disc',
    'soybean_sisal_disc': 'soybean_sisal_disc',
    'soybean_cotton_disc': 'soybean_cotton_disc',
    'soybean_linen_disc': 'soybean_linen_disc',
    'soybean_papyrus_disc': 'soybean_papyrus_disc',
    'soybean_hemp_disc': 'soybean_hemp_disc',
    'linseed_jute_disc': 'linseed_jute_disc',
    'linseed_silk_disc': 'linseed_silk_disc',
    'linseed_sisal_disc': 'linseed_sisal_disc',
    'linseed_cotton_disc': 'linseed_cotton_disc',
    'linseed_linen_disc': 'linseed_linen_disc',
    'linseed_papyrus_disc': 'linseed_papyrus_disc',
    'linseed_hemp_disc': 'linseed_hemp_disc',
    'rape_seed_jute_disc': 'rape_seed_jute_disc',
    'rape_seed_silk_disc': 'rape_seed_silk_disc',
    'rape_seed_sisal_disc': 'rape_seed_sisal_disc',
    'rape_seed_cotton_disc': 'rape_seed_cotton_disc',
    'rape_seed_linen_disc': 'rape_seed_linen_disc',
    'rape_seed_papyrus_disc': 'rape_seed_papyrus_disc',
    'rape_seed_hemp_disc': 'rape_seed_hemp_disc',
    'sunflower_seed_jute_disc': 'sunflower_seed_jute_disc',
    'sunflower_seed_silk_disc': 'sunflower_seed_silk_disc',
    'sunflower_seed_sisal_disc': 'sunflower_seed_sisal_disc',
    'sunflower_seed_cotton_disc': 'sunflower_seed_cotton_disc',
    'sunflower_seed_linen_disc': 'sunflower_seed_linen_disc',
    'sunflower_seed_papyrus_disc': 'sunflower_seed_papyrus_disc',
    'sunflower_seed_hemp_disc': 'sunflower_seed_hemp_disc',
    'opium_poppy_seed_jute_disc': 'opium_poppy_seed_jute_disc',
    'opium_poppy_seed_silk_disc': 'opium_poppy_seed_silk_disc',
    'opium_poppy_seed_sisal_disc': 'opium_poppy_seed_sisal_disc',
    'opium_poppy_seed_cotton_disc': 'opium_poppy_seed_cotton_disc',
    'opium_poppy_seed_linen_disc': 'opium_poppy_seed_linen_disc',
    'opium_poppy_seed_papyrus_disc': 'opium_poppy_seed_papyrus_disc',
    'opium_poppy_seed_hemp_disc': 'opium_poppy_seed_hemp_disc',
    'sugar_beet_jute_disc': 'sugar_beet_jute_disc',
    'sugar_beet_silk_disc': 'sugar_beet_silk_disc',
    'sugar_beet_sisal_disc': 'sugar_beet_sisal_disc',
    'sugar_beet_cotton_disc': 'sugar_beet_cotton_disc',
    'sugar_beet_linen_disc': 'sugar_beet_linen_disc',
    'sugar_beet_papyrus_disc': 'sugar_beet_papyrus_disc',
    'sugar_beet_hemp_disc': 'sugar_beet_hemp_disc',
    'sugar_cane_jute_disc': 'sugar_cane_jute_disc',
    'sugar_cane_silk_disc': 'sugar_cane_silk_disc',
    'sugar_cane_sisal_disc': 'sugar_cane_sisal_disc',
    'sugar_cane_cotton_disc': 'sugar_cane_cotton_disc',
    'sugar_cane_linen_disc': 'sugar_cane_linen_disc',
    'sugar_cane_papyrus_disc': 'sugar_cane_papyrus_disc',
    'sugar_cane_hemp_disc': 'sugar_cane_hemp_disc',
    'olive_silk_disc': 'olive_silk_disc',
    'olive_sisal_disc': 'olive_sisal_disc',
    'olive_cotton_disc': 'olive_cotton_disc',
    'olive_linen_disc': 'olive_linen_disc',
    'olive_papyrus_disc': 'olive_papyrus_disc',
    'olive_hemp_disc': 'olive_hemp_disc',
    'silk_net': 'silk_net',
    'sisal_net': 'sisal_net',
    'cotton_net': 'cotton_net',
    'linen_net': 'linen_net',
    'papyrus_net': 'papyrus_net',
    'hemp_net': 'hemp_net',
    'dirty_silk_net': 'dirty_silk_net',
    'dirty_sisal_net': 'dirty_sisal_net',
    'dirty_cotton_net': 'dirty_cotton_net',
    'dirty_linen_net': 'dirty_linen_net',
    'dirty_papyrus_net': 'dirty_papyrus_net',
    'dirty_hemp_net': 'dirty_hemp_net',
    'silk_disc': 'silk_disc',
    'sisal_disc': 'sisal_disc',
    'cotton_disc': 'cotton_disc',
    'linen_disc': 'linen_disc',
    'papyrus_disc': 'papyrus_disc',
    'hemp_disc': 'hemp_disc',
    'chamomile_head': 'chamomile_head',
    'dandelion_head': 'dandelion_head',
    'labrador_tea_head': 'labrador_tea_head',
    'sunflower_head': 'sunflower_head'
    }

    let CROP_PRODUCT_DRIED_TYPES = {
    'chamomile_head': 'chamomile_head',
    'dandelion_head': 'dandelion_head',
    'labrador_tea_head': 'labrador_tea_head',
    'sunflower_head': 'sunflower_head'
    }

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

for(let woodType of Object.keys(FRUIT_WOOD_TYPES))
{
    generateRecipes(woodType)
}

function generateRecipes(woodType)
{
    let fruitFoodJSON = {
      "parent": "item/handheld",
      "textures": {
        "layer0": `tfcflorae:items/food/${woodType}`
      }
    }
    fs.writeFileSync(`./src/main/resources/assets/tfcflorae/models/item/food/${woodType}.json`, JSON.stringify(fruitFoodJSON, null, 2))
}

for(let driedFoodType of Object.keys(DRIED_FOOD_TYPES))
{
    generateRecipes(driedFoodType)
}

function generateRecipes(driedFoodType)
{
    let driedFoodJSON = {
      "parent": "item/handheld",
      "textures": {
        "layer0": `tfcflorae:items/food/dried/${driedFoodType}`
      }
    }
    fs.writeFileSync(`./src/main/resources/assets/tfcflorae/models/item/food/dried/${driedFoodType}.json`, JSON.stringify(driedFoodJSON, null, 2))
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

for(let miscFoodType of Object.keys(MISC_FOOD_TYPES))
{
    generateRecipes(miscFoodType)
}

function generateRecipes(miscFoodType)
{
    let miscFoodJSON = {
      "parent": "item/handheld",
      "textures": {
        "layer0": `tfcflorae:items/food/${miscFoodType}`
      }
    }
    fs.writeFileSync(`./src/main/resources/assets/tfcflorae/models/item/food/${miscFoodType}.json`, JSON.stringify(miscFoodJSON, null, 2))
}

for(let cropProductType of Object.keys(CROP_PRODUCT_TYPES))
{
    generateRecipes(cropProductType)
}

function generateRecipes(cropProductType)
{
    let cropProductJSON = {
      "parent": "item/handheld",
      "textures": {
        "layer0": `tfcflorae:items/crop/product/${cropProductType}`
      }
    }
    fs.writeFileSync(`./src/main/resources/assets/tfcflorae/models/item/crop/product/${cropProductType}.json`, JSON.stringify(cropProductJSON, null, 2))
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