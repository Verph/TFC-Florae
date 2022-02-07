package tfcflorae.client;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import tfcflorae.client.model.animal.ModelSilkMoth;
import tfcflorae.client.render.RenderBoatTFCF;
import tfcflorae.client.render.animal.RendererSilkMoth;
import tfcflorae.objects.entity.EntityBoatTFCF;
import tfcflorae.objects.entity.animal.EntitySilkMoth;

import static tfcflorae.TFCFlorae.MODID;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MODID)
public class ClientEvents 
{
    public static void preInit()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityBoatTFCF.class, RenderBoatTFCF::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySilkMoth.class, RendererSilkMoth.FACTORY);
    }
}
