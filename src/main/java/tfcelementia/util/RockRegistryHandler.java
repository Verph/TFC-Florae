package tfcelementia.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.objects.ToolMaterialsTFC;
import tfcelementia.TFCElementia;
import tfcelementia.objects.ArmorMaterialsTFCE;
import tfcelementia.objects.ToolMaterialsTFCE;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.types.DefaultRocks.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod.EventBusSubscriber(modid = TFCElementia.MODID)
public final class RockRegistryHandler
{
    public static final ResourceLocation BRECCIA = new ResourceLocation(MOD_ID, "breccia");
    public static final ResourceLocation FOIDOLITE = new ResourceLocation(MOD_ID, "foidolite");
    public static final ResourceLocation PORPHYRY = new ResourceLocation(MOD_ID, "porphyry");
    public static final ResourceLocation PERIDOTITE = new ResourceLocation(MOD_ID, "peridotite");
    public static final ResourceLocation MUDSTONE = new ResourceLocation(MOD_ID, "mudstone");
    public static final ResourceLocation SANDSTONE = new ResourceLocation(MOD_ID, "sandstone");
    public static final ResourceLocation SILTSTONE = new ResourceLocation(MOD_ID, "siltstone");
    public static final ResourceLocation BLUESCHIST = new ResourceLocation(MOD_ID, "blueschist");
    public static final ResourceLocation GREENSCHIST = new ResourceLocation(MOD_ID, "greenschist");
    public static final ResourceLocation NOVACULITE = new ResourceLocation(MOD_ID, "novaculite");
    public static final ResourceLocation SOAPSTONE = new ResourceLocation(MOD_ID, "soapstone");
    public static final ResourceLocation KOMATIITE = new ResourceLocation(MOD_ID, "komatiite");

    @SubscribeEvent
    public static void onPreRegisterRock(TFCRegistryEvent.RegisterPreBlock<Rock> event)
    {
        IForgeRegistry<Rock> r = event.getRegistry();

	    r.register(new Rock(BRECCIA, IGNEOUS_INTRUSIVE, false));
	    r.register(new Rock(FOIDOLITE, IGNEOUS_INTRUSIVE, false));
	    r.register(new Rock(PORPHYRY, IGNEOUS_INTRUSIVE, false));
	    r.register(new Rock(PERIDOTITE, IGNEOUS_EXTRUSIVE, false));
        r.register(new Rock(MUDSTONE, SEDIMENTARY, false));
	    r.register(new Rock(SANDSTONE, SEDIMENTARY, false));
	    r.register(new Rock(SILTSTONE, SEDIMENTARY, false));
	    r.register(new Rock(BLUESCHIST, METAMORPHIC, false));
	    r.register(new Rock(GREENSCHIST, METAMORPHIC, false));
	    r.register(new Rock(NOVACULITE, METAMORPHIC, false));
	    r.register(new Rock(SOAPSTONE, METAMORPHIC, false));
	    r.register(new Rock(KOMATIITE, METAMORPHIC, false));
    }
}





