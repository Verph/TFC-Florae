package tfcflorae.client.render.animal;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import tfcflorae.client.model.animal.ModelSilkMoth;
import tfcflorae.objects.entity.animal.EntitySilkMoth;

import static tfcflorae.TFCFlorae.MODID;

public class RendererSilkMoth extends RenderLiving<EntitySilkMoth> implements IRenderFactory
{
	public static final IRenderFactory<EntitySilkMoth> FACTORY = RendererSilkMoth::new;

	private RendererSilkMoth(RenderManager renderManagerIn)
    {
		super(renderManagerIn, new ModelSilkMoth(), 0.1F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySilkMoth entity)
    {
		return new ResourceLocation(MODID, "textures/entity/animal/silk_moth.png");
	}

	@Override
	public Render createRenderFor(RenderManager manager)
    {
		return manager.getEntityClassRenderObject(EntitySilkMoth.class);
	}
}
