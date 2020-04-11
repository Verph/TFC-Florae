package tfcelementia.util;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import org.apache.commons.io.FileUtils;

import net.dries007.tfc.world.classic.worldgen.vein.VeinRegistry;

import static tfcelementia.TFCElementia.MODID;

public enum VeinLoader
{
    INSTANCE;

    private static final String DEFAULT_ORE_SPAWN_LOCATION = "assets/tfcelementia/config/tfc_elementia_ores.json";

    public void preInit(File dir)
    {
        File tfcDir = new File(dir, MODID);
        if (!tfcDir.exists() && !tfcDir.mkdir())
        {
            throw new Error("Problem creating TFC extra config directory.");
        }
        File worldGenFile = new File(tfcDir, "tfc_elementia_ores.json");
        try
        {
            if (worldGenFile.createNewFile())
            {
                FileUtils.copyInputStreamToFile(Objects.requireNonNull(VeinRegistry.class.getClassLoader().getResourceAsStream(DEFAULT_ORE_SPAWN_LOCATION)), worldGenFile);
            }
        }
        catch (IOException e)
        {
            throw new Error("Problem creating default ore vein config file.", e);
        }
    }
}
