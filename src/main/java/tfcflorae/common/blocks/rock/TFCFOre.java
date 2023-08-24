package tfcflorae.common.blocks.rock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.dries007.tfc.common.blocks.ExtendedBlock;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.util.registry.RegistryRock;

public enum TFCFOre
{
    ANTHRACITE(false);

    private final boolean graded;

    TFCFOre(boolean graded)
    {
        this.graded = graded;
    }

    public boolean isGraded()
    {
        return graded;
    }

    public Block create(RegistryRock rock)
    {
        // Same hardness as raw rock
        final BlockBehaviour.Properties properties = Block.Properties.of(Material.STONE, MaterialColor.STONE).sound(SoundType.STONE).strength(rock.category().hardness(6.5f), 10).requiresCorrectToolForDrops();
        if (this == ANTHRACITE)
        {
            return new ExtendedBlock(ExtendedProperties.of(properties).flammable(10, 100));
        }
        return new Block(properties);
    }

    public enum Grade
    {
        POOR, NORMAL, RICH;

        private static final Grade[] VALUES = values();

        public static Grade valueOf(int i)
        {
            return i < 0 || i >= VALUES.length ? NORMAL : VALUES[i];
        }
    }
}
