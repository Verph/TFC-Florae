package tfcflorae.objects;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.dries007.tfc.util.collections.WeightedCollection;

public enum GemTFCF
{
    AMBER(true);

    // list of gems that can drop
    private static final GemTFCF[] RANDOM_DROP_GEMS = Arrays.stream(values()).filter(x -> x.canDrop).toArray(GemTFCF[]::new);

    /**
     * Returns a random gem type according to gem type availabilities
     *
     * @param random Random generator for rolling odds
     * @return a random drop gem type
     */
    public static GemTFCF getRandomDropGem(Random random)
    {
        return RANDOM_DROP_GEMS[random.nextInt(RANDOM_DROP_GEMS.length)];
    }

    // whether this gem can be found as a drop from raw stone
    private final boolean canDrop;

    GemTFCF(boolean canDrop)
    {
        this.canDrop = canDrop;
    }

    public enum Grade
    {
        CHIPPED(16),
        FLAWED(8),
        NORMAL(4),
        FLAWLESS(2),
        EXQUISITE(1);

        private static final Grade[] VALUES = values();
        private static final WeightedCollection<Grade> GRADE_ODDS = new WeightedCollection<>(Arrays.stream(VALUES).collect(Collectors.toMap(k -> k, v -> v.dropWeight)));

        /**
         * Returns a random gem grade according to gem grade weights
         *
         * @param random Random generator for rolling the odds
         * @return a random drop gem grade
         */
        @Nonnull
        public static Grade randomGrade(Random random)
        {
            return GRADE_ODDS.getRandomEntry(random);
        }

        @Nullable
        public static Grade valueOf(int index)
        {
            return index >= 0 && index < VALUES.length ? VALUES[index] : null;
        }

        private final double dropWeight;

        Grade(int dropWeight)
        {
            this.dropWeight = dropWeight;
        }
    }
}