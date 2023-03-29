package tfcflorae.common.blocks.rock;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import tfcflorae.common.items.TFCFItems;

import org.jetbrains.annotations.Nullable;

import net.dries007.tfc.common.blocks.ExtendedBlock;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.util.registry.RegistryRock;

public enum Mineral
{
    SALMIAK(false), // Ammonium Chloride
    ZABUYELITE(false), // Lithium
    CALCITE(false), // Calcium Carbonate
    MAGNESITE(false), // Magnesium Carbonate
    BRIMSTONE(false), // Sulphur
    SPHEROCOBALTITE(false), // Cobalt Carbonate
    ALABANDITE(false), // Manganese Sulfide
    SMITHSONITE(false), // Zinc Carbonate
    GREIGITE(false), // Iron Sulfide
    APATITE(false), // Phosphate mineral
    BASTNASITE(false), // Fluor Carbonate
    SALTPETER(false), // Kalium Nitrate
    SALT(false);

    private final boolean graded;

    Mineral(boolean graded)
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
        final BlockBehaviour.Properties properties = Block.Properties.of(Material.STONE).sound(SoundType.STONE).strength(rock.category().hardness(6.5f), 10).noCollission().requiresCorrectToolForDrops();
        if (this == BRIMSTONE)
        {
            return new ExtendedBlock(ExtendedProperties.of(properties).flammable(5, 120));
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

    @Nullable
    public static Mineral getMineral(ItemStack stack)
    {
        Mineral mineral = Mineral.BRIMSTONE; // Fallback
        if (stack != null)
        {
            for (Mineral minerals : Mineral.values())
            {
                if (stack.getItem().equals(TFCFItems.MINERALS.get(minerals).get()))
                {
                    mineral = minerals;
                    break;
                }
            }
        }
        return mineral;
    }
}
