package tfcflorae.client.model.entity;

import net.minecraft.client.model.geom.ModelPart;

public interface Drawable
{
    boolean skipDraw();

    void setSkipDraw(boolean set);

    static Drawable of(ModelPart part)
    {
        return Drawable.class.cast(part);
    }
}