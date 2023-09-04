package tfcflorae.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.devices.DeviceBlock;
import net.dries007.tfc.common.blocks.wood.BookshelfBlock;

@Mixin(BookshelfBlock.class)
public abstract class BookshelfBlockMixin extends DeviceBlock
{
    public BookshelfBlockMixin(ExtendedProperties properties)
    {
        super(properties, InventoryRemoveBehavior.DROP);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader world, BlockPos pos)
    {
        return state.getValue(BookshelfBlock.BOOKS_STORED).intValue() * 0.33F;
    }
}
