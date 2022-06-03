package tfcflorae.client.render.entity;

import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

import com.mojang.datafixers.util.Pair;

import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.util.Helpers;

public class TFCFBoatRenderer extends BoatRenderer
{
    public static ModelLayerLocation boatName(String name)
    {
        return RenderHelpers.modelIdentifier("boat/" + name);
    }

    private final Pair<ResourceLocation, BoatModel> location;

    public TFCFBoatRenderer(EntityRendererProvider.Context context, String name)
    {
        super(context);
        this.location = Pair.of(Helpers.identifier("textures/entity/boat/" + name + ".png"), new BoatModel(context.bakeLayer(boatName(name))));
    }

    @Override
    public Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat)
    {
        return location;
    }
}
