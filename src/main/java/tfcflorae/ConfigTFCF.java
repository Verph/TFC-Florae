package tfcflorae;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.TerraFirmaCraft;

import static tfcflorae.TFCFlorae.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class ConfigTFCF
{
    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MODID))
        {
            TerraFirmaCraft.getLog().warn("Config changed");
            ConfigManager.sync(MODID, Config.Type.INSTANCE);
        }
    }

    @Config(modid = MODID, category = "general", name = "TFCFlorae - General")
    @Config.LangKey("config." + MODID + ".general")
    public static final class General
    {
        @Config.Comment("World generation settings")
        @Config.LangKey("config." + MODID + ".general.world")
        public static final WorldCFG WORLD = new WorldCFG();

        @Config.Comment("Structure generation settings")
        @Config.LangKey("config." + MODID + ".general.structures")
        public static final StructuresCFG STRUCTURES = new StructuresCFG();

        public static final class WorldCFG
        {
            @Config.Comment("The rarity for humus pits to occur. On average 1 / N chunks will have a humus deposit, if the chunk in question is valid for humus to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.humusRarity")
            public int humusRarity = 8;

            @Config.Comment("The rarity for podzol pits to occur. On average 1 / N chunks will have a podzol deposit, if the chunk in question is valid for podzol to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.podzolRarity")
            public int podzolRarity = 1;

            @Config.Comment("The rarity for mud pits to occur. On average 1 / N chunks will have a mud deposit, if the chunk in question is valid for mud to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.mudRarity")
            public int mudRarity = 5;

            @Config.Comment("The rarity for bog iron pits to occur. On average 1 / N chunks will have a bog iron deposit, if the chunk in question is valid for bog iron to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.bogIronRarity")
            public int bogIronRarity = 4;

            @Config.Comment("The rarity for sparse grass to occur. On average 1 / N chunks will have sparse grass, if the chunk in question is valid for sparse grass to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.sparseGrassRarity")
            public int sparseGrassRarity = 1;

            @Config.Comment("The rarity for loamy sand pits to occur. On average 1 / N chunks will have a loamy sand deposit, if the chunk in question is valid for loamy sand to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.loamySandRarity")
            public int loamySandRarity = 8;

            @Config.Comment("The rarity for sandy loam pits to occur. On average 1 / N chunks will have a sandy loam deposit, if the chunk in question is valid for sandy loam to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.sandyLoamRarity")
            public int sandyLoamRarity = 8;

            @Config.Comment("The rarity for loam pits to occur. On average 1 / N chunks will have a loam deposit, if the chunk in question is valid for loam to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.loamRarity")
            public int loamRarity = 8;

            @Config.Comment("The rarity for silt loam pits to occur. On average 1 / N chunks will have a silt loam deposit, if the chunk in question is valid for silt loam to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.siltLoamRarity")
            public int siltLoamRarity = 8;

            @Config.Comment("The rarity for silt pits to occur. On average 1 / N chunks will have a silt deposit, if the chunk in question is valid for silt to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.siltRarity")
            public int siltRarity = 8;

            @Config.Comment("The rarity for sandy clay pits to occur. On average 1 / N chunks will have a sandy clay deposit, if the chunk in question is valid for sandy clay to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.sandyClayRarity")
            public int sandyClayRarity = 40;

            @Config.Comment("The rarity for sandy clay loam pits to occur. On average 1 / N chunks will have a sandy clay loam deposit, if the chunk in question is valid for sandy clay loam to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.sandyClayLoamRarity")
            public int sandyClayLoamRarity = 40;

            @Config.Comment("The rarity for clay loam pits to occur. On average 1 / N chunks will have a clay loam deposit, if the chunk in question is valid for clay loam to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.clayLoamRarity")
            public int clayLoamRarity = 40;

            @Config.Comment("The rarity for silty clay loam pits to occur. On average 1 / N chunks will have a silty clay loam deposit, if the chunk in question is valid for silty clay loam to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.siltyClayLoamRarity")
            public int siltyClayLoamRarity = 40;

            @Config.Comment("The rarity for silty clay pits to occur. On average 1 / N chunks will have a silty clay deposit, if the chunk in question is valid for silty clay to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.siltyClayRarity")
            public int siltyClayRarity = 40;

            @Config.Comment("The rarity for clay humus pits to occur. On average 1 / N chunks will have a clay humus deposit, if the chunk in question is valid for clay humus to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.clayHumusRarity")
            public int clayHumusRarity = 40;

            @Config.Comment("The rarity for kaolinite clay pits to occur. On average 1 / N chunks will have a kaolinite clay deposit, if the chunk in question is valid for kaolinite clay to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.kaoliniteClayRarity")
            public int kaoliniteClayRarity = 80;

            @Config.Comment("The rarity for sandy kaolinite clay pits to occur. On average 1 / N chunks will have a sandy kaolinite clay deposit, if the chunk in question is valid for sandy kaolinite clay to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.sandyKaoliniteClayRarity")
            public int sandyKaoliniteClayRarity = 80;

            @Config.Comment("The rarity for sandy kaolinite clay loam pits to occur. On average 1 / N chunks will have a sandy kaolinite clay loam deposit, if the chunk in question is valid for sandy kaolinite clay loam to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.sandyKaoliniteClayLoamRarity")
            public int sandyKaoliniteClayLoamRarity = 80;

            @Config.Comment("The rarity for kaolinite clay loam pits to occur. On average 1 / N chunks will have a kaolinite clay loam deposit, if the chunk in question is valid for kaolinite clay loam to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.kaoliniteClayLoamRarity")
            public int kaoliniteClayLoamRarity = 80;

            @Config.Comment("The rarity for silty kaolinite clay loam pits to occur. On average 1 / N chunks will have a silty kaolinite clay loam deposit, if the chunk in question is valid for silty kaolinite clay loam to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.siltyKaoliniteClayLoamRarity")
            public int siltyKaoliniteClayLoamRarity = 80;

            @Config.Comment("The rarity for silty kaolinite clay pits to occur. On average 1 / N chunks will have a silty kaolinite clay deposit, if the chunk in question is valid for silty kaolinite clay to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.siltyKaoliniteClayRarity")
            public int siltyKaoliniteClayRarity = 80;

            @Config.Comment("The rarity for kaolinite clay humus pits to occur. On average 1 / N chunks will have a kaolinite clay humus deposit, if the chunk in question is valid for kaolinite clay humus to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.kaoliniteClayHumusRarity")
            public int kaoliniteClayHumusRarity = 80;

            @Config.Comment("The rarity for coarse dirt pits to occur. On average 1 / N chunks will have a coarse dirt deposit, if the chunk in question is valid for coarse dirt to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.coarseDirtRarity")
            public int coarseDirtRarity = 15;

            @Config.Comment("The rarity for coarse loamy sand pits to occur. On average 1 / N chunks will have a coarse loamy sand deposit, if the chunk in question is valid for coarse loamy sand to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.coarseLoamySandRarity")
            public int coarseLoamySandRarity = 15;

            @Config.Comment("The rarity for coarse sandy loam pits to occur. On average 1 / N chunks will have a coarse sandy loam deposit, if the chunk in question is valid for coarse sandy loam to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.coarseSandyLoamRarity")
            public int coarseSandyLoamRarity = 15;

            @Config.Comment("The rarity for coarse loam pits to occur. On average 1 / N chunks will have a coarse loam deposit, if the chunk in question is valid for coarse loam to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.coarseLoamRarity")
            public int coarseLoamRarity = 15;

            @Config.Comment("The rarity for coarse silt loam pits to occur. On average 1 / N chunks will have a coarse silt loam deposit, if the chunk in question is valid for coarse silt loam to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.coarseSiltLoamRarity")
            public int coarseSiltLoamRarity = 15;

            @Config.Comment("The rarity for coarse silt pits to occur. On average 1 / N chunks will have a coarse silt deposit, if the chunk in question is valid for coarse silt to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.coarseSiltRarity")
            public int coarseSiltRarity = 15;

            @Config.Comment("The rarity for coarse humus pits to occur. On average 1 / N chunks will have a coarse humus deposit, if the chunk in question is valid for coarse humus to spawn.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.coarseHumusRarity")
            public int coarseHumusRarity = 15;

            @Config.Comment("The number of attempts per chunk to spawn bones.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.groundcoverBonesFrequency")
            public int groundcoverBonesFrequency = 3;

            @Config.Comment("The number of attempts per chunk to spawn driftwood.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.groundcoverDriftwoodFrequency")
            public int groundcoverDriftwoodFrequency = 4;

            @Config.Comment("The number of attempts per chunk to spawn flint.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.groundcoverFlintFrequency")
            public int groundcoverFlintFrequency = 6;

            @Config.Comment("The number of attempts per chunk to spawn ore deposits.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.groundcoverOreDepositFrequency")
            public int groundcoverOreDepositFrequency = 18;

            @Config.Comment("The number of attempts per chunk to spawn pinecones.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.groundcoverPineconeFrequency")
            public int groundcoverPineconeFrequency = 8;

            @Config.Comment("The number of attempts per chunk to spawn rocks.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.groundcoverRockFrequency")
            public int groundcoverRockFrequency = 10;

            @Config.Comment("The number of attempts per chunk to spawn seashells.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.groundcoverSeashellFrequency")
            public int groundcoverSeashellFrequency = 50;

            @Config.Comment("The number of attempts per chunk to spawn twigs.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.groundcoverTwigFrequency")
            public int groundcoverTwigFrequency = 11;

            @Config.Comment("The rarity for bamboo trees to generate. On average 1 / N chunks will have a bamboo tree, if the chunk in question is valid for bamboo to generate.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.bambooRarity")
            public int bambooRarity = 15;

            @Config.Comment("The rarity for cinnamon trees to generate. On average 1 / N chunks will have a cinnamon tree, if the chunk in question is valid for cinnamon to generate.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.world.cinnamonRarity")
            public int cinnamonRarity = 100;

            @Config.Comment("Enable bone groundcover?")
            @Config.LangKey("config." + MODID + ".general.world.enableGroundcoverBones")
            public boolean enableGroundcoverBones = true;

            @Config.Comment("Enable driftwood groundcover?")
            @Config.LangKey("config." + MODID + ".general.world.enableGroundcoverDriftwood")
            public boolean enableGroundcoverDriftwood = true;

            @Config.Comment("Enable flint groundcover?")
            @Config.LangKey("config." + MODID + ".general.world.enableGroundcoverFlint")
            public boolean enableGroundcoverFlint = true;

            @Config.Comment("Enable ore deposit groundcover? Disabling ore deposits can improve loading times and lower ram usage significantly.")
            @Config.LangKey("config." + MODID + ".general.world.enableGroundcoverOreDeposit")
            public boolean enableGroundcoverOreDeposit = true;

            @Config.Comment("Enable pinecone groundcover?")
            @Config.LangKey("config." + MODID + ".general.world.enableGroundcoverPinecone")
            public boolean enableGroundcoverPinecone = true;

            @Config.Comment("Enable rock groundcover? Disabling rocks can improve loading times and lower ram usage significantly.")
            @Config.LangKey("config." + MODID + ".general.world.enableGroundcoverRock")
            public boolean enableGroundcoverRock = true;

            @Config.Comment("Enable seashell groundcover?")
            @Config.LangKey("config." + MODID + ".general.world.enableGroundcoverSeashell")
            public boolean enableGroundcoverSeashell = true;

            @Config.Comment("Enable twig groundcover?")
            @Config.LangKey("config." + MODID + ".general.world.enableGroundcoverTwig")
            public boolean enableGroundcoverTwig = true;

            @Config.Comment("Enable soil pit world generation?")
            @Config.LangKey("config." + MODID + ".general.world.enableSoilPits")
            public boolean enableSoilPits = true;

            @Config.Comment("Enable bamboo and cinnamon tree world generation?")
            @Config.LangKey("config." + MODID + ".general.world.enableTrees")
            public boolean enableTrees = true;

            @Config.Comment("Enable all special block types? (Loam, humus, silt etc.)")
            @Config.LangKey("config." + MODID + ".general.world.enableAllBlockTypes")
            public boolean enableAllBlockTypes = true;

            @Config.Comment("Enable rock types for every kind of block?")
            @Config.LangKey("config." + MODID + ".general.world.enableAllBlockRockTypes")
            public boolean enableAllBlockRockTypes = false;

            @Config.Comment("Enable all world generation?")
            @Config.LangKey("config." + MODID + ".general.world.enableAllWorldGen")
            public boolean enableAllWorldGen = true;
        }

        public static final class StructuresCFG
        {
            @Config.Comment(value = "Activate structure generation?")
            @Config.LangKey("config." + MODID + ".general.structures.activateStructureGeneration")
            public boolean activateStructureGeneration = true;

            @Config.Comment(value = "This value multiplies the generation chance. E.g. the spawn chance is 1000 and the config says 0.3 ==> 1000 x 0.3 = 300. '300' is now the new generation chance.")
            @Config.RangeDouble(min = 0.1f, max = 10.0f)
            @Config.LangKey("config." + MODID + ".general.structures.generationModifier")
            public float generationModifier = 1.0f;

            @Config.Comment("Generation chance for stone circle ruins. On average 1 / N chunks will have stone circle ruins.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.structures.spawnChanceRuins")
            public int spawnChanceRuins = 500;

            @Config.Comment("Generation chance for moai statues. On average 1 / N chunks will have moai statues.")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID + ".general.structures.spawnChanceMoai")
            public int spawnChanceMoai = 500;
        }
    }
}