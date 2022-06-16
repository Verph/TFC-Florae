package tfcflorae.common.blocks.rock;

import java.util.function.Function;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public enum TFCFSandstoneBlockType
{
    RAW(color -> BlockBehaviour.Properties.of(Material.STONE, color.getMaterialColor()).strength(0.8f).requiresCorrectToolForDrops()),
    SMOOTH(color -> BlockBehaviour.Properties.of(Material.STONE, color.getMaterialColor()).strength(1.2f).requiresCorrectToolForDrops()),
    CUT(color -> BlockBehaviour.Properties.of(Material.STONE, color.getMaterialColor()).strength(1.2f).requiresCorrectToolForDrops());

    private final Function<TFCFSand, BlockBehaviour.Properties> factory;

    TFCFSandstoneBlockType(Function<TFCFSand, BlockBehaviour.Properties> factory)
    {
        this.factory = factory;
    }

    public BlockBehaviour.Properties properties(TFCFSand color)
    {
        return factory.apply(color);
    }
}
