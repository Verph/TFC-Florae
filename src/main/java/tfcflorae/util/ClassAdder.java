package tfcflorae.util;

import java.io.*;
import java.util.Objects;

import org.apache.commons.io.FileUtils;

import net.minecraft.launchwrapper.Launch;

import tfcflorae.TFCFlorae;

public class ClassAdder
{
    private static final String BIOME_CLASS1_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeDecoratorTFC$1.class";
    private static final String BIOME_CLASS2_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes/BiomeDecoratorTFC.class";
    private static final String PLANT_CLASS1_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockPlantTFC$1.class";
    private static final String PLANT_CLASS2_FROM = "assets/tfcflorae/bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/objects/blocks/plants/BlockPlantTFC.class";
    private static final String DIR = "bansoukou/TerraFirmaCraft-MC1.12.2-1.7.21.179/net/dries007/tfc/world/classic/biomes";

    public static void addClasses(File dir)
    {
        File bansoukouFolder = new File(Launch.minecraftHome, DIR); // Creates a /bansoukou/.../ directory in the %HOME% directory, if it doesn't yet exist.
        if (!bansoukouFolder.exists())
        {
            bansoukouFolder.mkdirs();
        }
        File biomeDest1 = new File(bansoukouFolder, "BiomeDecoratorTFC$1.class");
        File biomeDest2 = new File(bansoukouFolder, "BiomeDecoratorTFC.class");
        File plantDest1 = new File(bansoukouFolder, "BlockPlantTFC$1.class");
        File plantDest2 = new File(bansoukouFolder, "BlockPlantTFC.class");
        try
        {
            if (biomeDest1.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BIOME_CLASS1_FROM)), biomeDest1);
            }
            if (biomeDest2.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(BIOME_CLASS2_FROM)), biomeDest2);
            }
            if (plantDest1.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(PLANT_CLASS1_FROM)), plantDest1);
            }
            if (plantDest2.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(ClassAdder.class.getClassLoader().getResourceAsStream(PLANT_CLASS2_FROM)), plantDest2);
            }
        }
        catch (IOException e)
        {
            throw new Error(TFCFlorae.MODID + ": Sorry, but I couldn't copy the class files into Bansoukou's directory.", e);
        }
    }
}