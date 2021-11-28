# ![](https://media.forgecdn.net/avatars/thumbnails/396/923/64/64/637597280762832131.png) TFC Florae - an expansive world exploration addon to TerraFirmaCraft
Welcome to TFC Florae, an addon for TerraFirmaCraft which adds a plethora of new blocks, items and much more to the world and thereby spices it up a prodigious amount! This mod is especially something for you, if you find the base TerraFirmaCraft world to be bland and boring. Want examples of what this mod adds to the table, then check out [the image page](https://www.curseforge.com/minecraft/mc-mods/tfc-florae/screenshots)!
## Discord
Want to join our Discord? Click this image!
[![Foo](https://i.imgur.com/pbAEkFq.png)](https://discord.gg/knYTg5HAKb)

## Installation
In order to use this mod, you **must** install some mods, otherwise it will **not** work! For the required mods, please see the table right below.
| Mod | What?|
|--|--|
| [SneedASM](https://www.curseforge.com/minecraft/mc-mods/sneedasm) | An essential RAM optimization mod. |
| [Bansōkō 絆創膏](https://www.curseforge.com/minecraft/mc-mods/bansoko) | Mod patcher which allows me to change code within TFC itself. |
| [JustEnoughIDs](https://www.curseforge.com/minecraft/mc-mods/jeid) | TFC Florae ads **a lot** of new blocks, items and so much more. JEID removes the conventional ID limits. |
| [FirmaLife](https://www.curseforge.com/minecraft/mc-mods/firmalife) | This mod is nice and handy, and because I'm lazy this is required. No buts! |
| [TerraFirmaCraft](https://www.curseforge.com/minecraft/mc-mods/tfcraft) | The base TerraFirmaCraft mod. This is obviously required for this *tfc addon*. |
| [TFC Florae Lite Resource Pack](https://www.curseforge.com/minecraft/mc-mods/tfc-florae/files/3372914) | This is a resource pack I've made which *limits* the amount of groundcover rock block variants to one. If you don't have ram issues, then this might not be something for you. |

## CraftTweaker compatibility
TFC Florae adds CraftTweaker support for knapping types introduced with the mod. A list of new possible knapping recipe materials can be seen in the table below.
| Material | String KnappingType|
|--|--|
| Pineapple Leather | *"pineapple_leather"* |
| Burlap Cloth | *"burlap_cloth"* |
| Wool Cloth | *"wool_cloth"* |
| Silk Cloth | *"silk_cloth"* |
| Cotton Cloth | *"cotton_cloth"* |
| Linen Cloth | *"linen_cloth"* |
| Hemp Cloth | *"hemp_cloth"* |
| Yucca Canvas | *"yucca_canvas"* |
| Mud | *"mud"* |
| Flint | *"flint"* |
| Earthenware Clay | *"earthenware_clay"* |
| Kaolinite Clay | *"kaolinite_clay"* |
| Stoneware Clay | *"stoneware_clay"* |

|Method for all the types|
|--|
| `mods.tfcflorae.knapping.add(String KnappingType, String RecipeName, IItemStack Output, String ...pattern);` |
| **Method example** |
| `mods.tfcflorae.knapping.add("pineapple_leather", "pineapple_leather_sling", <tfcthings:sling>, " XXX", " X", " XXXX", "XX ", "X ");` |

## Goal
+ The primary incentive is to add all the elements and metals from the periodic table, and their respective ores and minerals; more tree sorts for broader usability cases and variety; as well as more alloys making more mods useable with TFC.
+ Short term goal is to add more rocktypes (the strata rocks you can find).
+ Mid term and long term goals are to enhance the world of TerraFirmaCraft, by introducing a greater variety of trees, fruit trees, plants, crops, food and more. 
+ Very long term is to add more actual rock types such as mossy and cracked variants of bricks and cobblestone. Podzol is also planned at some point.

## Contributing

+ As per the Github terms of service, you grant us the right to use your contribution
  under the same license as this project.
+ In addition we request that you give us the right to change the license in the future.
  Your pull request may not be accepted if you don't.
  
### Required

+ Import & use the project's Code Style.
+ Reformat & Rearrange your code before you submit a PR.
