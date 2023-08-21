package tfcflorae.client.model.entity.animation.api;

import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import tfcflorae.util.TFCFHelpers;

@OnlyIn(Dist.CLIENT)
public record Transformation(Target target, Keyframe... keyframes)
{
    public interface Target
    {
        void apply(ModelPart part, Vector3f vector);
    }

    public static class Interpolations
    {
        public static final Interpolation LINEAL = (cache, delta, keyframes, start, end, speed) -> {
            Vector3f frameStart = keyframes[start].target();
            Vector3f frameEnd = keyframes[end].target();
            cache.set(Mth.lerp(delta, frameStart.x(), frameEnd.x()) * speed, Mth.lerp(delta, frameStart.y(), frameEnd.y()) * speed, Mth.lerp(delta, frameStart.z(), frameEnd.z()) * speed);
            return cache;
        };
        public static final Interpolation CATMULL = (cache, delta, keyframes, start, end, speed) -> {
            Vector3f frameStartPoint = keyframes[Math.max(0, start - 1)].target();
            Vector3f frameStart = keyframes[start].target();
            Vector3f frameEnd = keyframes[end].target();
            Vector3f frameEndPoint = keyframes[Math.min(keyframes.length - 1, end + 1)].target();
            cache.set(TFCFHelpers.catmullrom(delta, frameStartPoint.x(), frameStart.x(), frameEnd.x(), frameEndPoint.x()) * speed, TFCFHelpers.catmullrom(delta, frameStartPoint.y(), frameStart.y(), frameEnd.y(), frameEndPoint.y()) * speed, TFCFHelpers.catmullrom(delta, frameStartPoint.z(), frameStart.z(), frameEnd.z(), frameEndPoint.z()) * speed);
            return cache;
        };
    }

    public static class Targets
    {
        public static final Target TRANSLATE = Animated::translate;
        public static final Target ROTATE = Animated::rotate;
        public static final Target SCALE = Animated::scaleY;
    }

    public interface Interpolation
    {
        Vector3f apply(Vector3f cache, float delta, Keyframe[] keyframes, int start, int end, float speed);
    }
}