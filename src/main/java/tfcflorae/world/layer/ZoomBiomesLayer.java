package tfcflorae.world.layer;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import net.dries007.tfc.world.layer.framework.Area;
import net.dries007.tfc.world.layer.framework.AreaContext;
import net.dries007.tfc.world.layer.framework.TransformLayer;

import static net.dries007.tfc.world.layer.TFCLayers.*;

public enum ZoomBiomesLayer implements TransformLayer
{
    NORMAL_LAND
    {
        @Override
        public int choose(AreaContext context, int first, int second, int third, int fourth, int center)
        {
            Predicate<IntPredicate> matcher = p -> p.test(first) || p.test(second) || p.test(third) || p.test(fourth);
            if (matcher.test(i -> !isOceanOrMarker(i)))
            {
                if (first == second)
                {
                    return first == third || third != fourth ? first : context.choose(first, third);
                }
                else if (first == third)
                {
                    return second != fourth ? first : context.choose(first, second);
                }
                else if (first == fourth)
                {
                    return second != third ? first : context.choose(first, second);
                }
                else if (second == third || second == fourth)
                {
                    return second;
                }
                else if (third == fourth)
                {
                    return third;
                }
                return context.choose(first, second, third, fourth);
            }
            return center;
        }
    },
    FUZZY_LAND
    {
        @Override
        public int choose(AreaContext context, int first, int second, int third, int fourth, int center)
        {
            Predicate<IntPredicate> matcher = p -> p.test(first) || p.test(second) || p.test(third) || p.test(fourth);
            if (matcher.test(i -> !isOceanOrMarker(i)))
            {
                // Random
                return context.choose(first, second, third, fourth);
            }
            return center;
        }
    },
    NORMAL_OCEAN
    {
        @Override
        public int choose(AreaContext context, int first, int second, int third, int fourth, int center)
        {
            Predicate<IntPredicate> matcher = p -> p.test(first) || p.test(second) || p.test(third) || p.test(fourth);
            if (matcher.test(i -> isOceanOrMarker(i)))
            {
                if (first == second)
                {
                    return first == third || third != fourth ? first : context.choose(first, third);
                }
                else if (first == third)
                {
                    return second != fourth ? first : context.choose(first, second);
                }
                else if (first == fourth)
                {
                    return second != third ? first : context.choose(first, second);
                }
                else if (second == third || second == fourth)
                {
                    return second;
                }
                else if (third == fourth)
                {
                    return third;
                }
                return context.choose(first, second, third, fourth);
            }
            return center;
        }
    },
    FUZZY_OCEAN
    {
        @Override
        public int choose(AreaContext context, int first, int second, int third, int fourth, int center)
        {
            Predicate<IntPredicate> matcher = p -> p.test(first) || p.test(second) || p.test(third) || p.test(fourth);
            if (matcher.test(i -> isOceanOrMarker(i)))
            {
                // Random
                return context.choose(first, second, third, fourth);
            }
            return center;
        }
    };

    @Override
    public int apply(AreaContext context, Area area, int x, int z)
    {
        final int parentX = x >> 1, parentZ = z >> 1;
        final int offsetX = x & 1, offsetZ = z & 1;
        final int northWest = area.get(parentX, parentZ);

        context.setSeed(parentX, parentZ);
        if (offsetX == 0 && offsetZ == 0)
        {
            return northWest;
        }
        else if (offsetX == 0)
        {
            return context.choose(northWest, area.get(parentX, parentZ + 1));
        }
        else if (offsetZ == 0)
        {
            return context.choose(northWest, area.get(parentX + 1, parentZ));
        }
        return choose(context, northWest, area.get(parentX, parentZ + 1), area.get(parentX + 1, parentZ), area.get(parentX + 1, parentZ + 1), area.get(x, z));
    }

    public abstract int choose(AreaContext context, int first, int second, int third, int fourth, int center);
}
