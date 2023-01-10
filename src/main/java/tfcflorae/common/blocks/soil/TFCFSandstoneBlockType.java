package tfcflorae.common.blocks.soil;

import java.util.function.Function;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public enum TFCFSandstoneBlockType
{
    LAYERED(color -> BlockBehaviour.Properties.of(Material.STONE, color.getMaterialColor()).strength(0.8f).requiresCorrectToolForDrops());

    private final Function<TFCFSandBlockType, BlockBehaviour.Properties> factory;

    TFCFSandstoneBlockType(Function<TFCFSandBlockType, BlockBehaviour.Properties> factory)
    {
        this.factory = factory;
    }

    public BlockBehaviour.Properties properties(TFCFSandBlockType color)
    {
        return factory.apply(color);
    }
}
