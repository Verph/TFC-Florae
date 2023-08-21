package tfcflorae.util.registry;

import java.util.function.Supplier;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.util.registry.RegistryRock;

import tfcflorae.common.blocks.rock.TFCFRock;

public interface TFCFRegistryRock extends RegistryRock
{
    RockCategory category();

    @Override
    default MaterialColor color()
    {
        return Material.STONE.getColor();
    }

    /**
     * @return A block of this rock, of the provided type.
     */
    Supplier<? extends Block> getBlockTFC(RegistryRock rock, TFCFRock.TFCFBlockType type);

    /**
     * @return A slab block of this rock and block type.
     */
    Supplier<? extends SlabBlock> getSlabTFC(RegistryRock rock, TFCFRock.TFCFBlockType type);

    /**
     * @return A stair block of this rock and block type.
     */
    Supplier<? extends StairBlock> getStairTFC(RegistryRock rock, TFCFRock.TFCFBlockType type);

    /**
     * @return A wall block of this rock and block type.
     */
    Supplier<? extends WallBlock> getWallTFC(RegistryRock rock, TFCFRock.TFCFBlockType type);

    /**
     * @return A block of this rock, of the provided type.
     */
    Supplier<? extends Block> getBlockTFCF(TFCFRock.TFCFBlockType type);

    /**
     * @return A slab block of this rock and block type.
     */
    Supplier<? extends SlabBlock> getSlabTFCF(TFCFRock.TFCFBlockType type);

    /**
     * @return A stair block of this rock and block type.
     */
    Supplier<? extends StairBlock> getStairTFCF(TFCFRock.TFCFBlockType type);

    /**
     * @return A wall block of this rock and block type.
     */
    Supplier<? extends WallBlock> getWallTFCF(TFCFRock.TFCFBlockType type);
}
