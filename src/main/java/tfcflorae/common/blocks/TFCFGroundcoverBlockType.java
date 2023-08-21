package tfcflorae.common.blocks;

import java.util.function.Function;
import java.util.function.Supplier;

import net.dries007.tfc.common.blocks.GroundcoverBlock;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

import static net.dries007.tfc.common.TFCItemGroup.EARTH;

public enum TFCFGroundcoverBlockType
{
    SEA_URCHIN(GroundcoverBlock.MEDIUM);

    private final VoxelShape shape;
    @Nullable
    private final Supplier<? extends Item> vanillaItem; // The vanilla item this corresponds to

    TFCFGroundcoverBlockType(VoxelShape shape)
    {
        this(shape, null);
    }

    TFCFGroundcoverBlockType(VoxelShape shape, @Nullable Supplier<? extends Item> vanillaItem)
    {
        this.shape = shape;
        this.vanillaItem = vanillaItem;
    }

    public VoxelShape getShape()
    {
        return shape;
    }

    @Nullable
    public Function<Block, BlockItem> createBlockItem()
    {
        return vanillaItem == null ? block -> new BlockItem(block, new Item.Properties().tab(EARTH)) : null;
    }

    @Nullable
    public Supplier<? extends Item> getVanillaItem()
    {
        return vanillaItem;
    }
}
