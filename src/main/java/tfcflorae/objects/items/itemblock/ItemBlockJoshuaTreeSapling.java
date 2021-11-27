package tfcflorae.objects.items.itemblock;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import tfcflorae.objects.blocks.wood.BlockJoshuaTreeSapling;

public class ItemBlockJoshuaTreeSapling extends ItemBlockTFC
{
    public ItemBlockJoshuaTreeSapling(BlockJoshuaTreeSapling block)
    {
        super(block);
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        if (!entityItem.world.isRemote && entityItem.getAge() >= entityItem.lifespan && !entityItem.getItem().isEmpty())
        {
            final BlockPos pos = entityItem.getPosition();
            if (placeAndDecreaseCount(entityItem, pos))
            {
                entityItem.setDead();
                return true;
            }
            for (EnumFacing face : EnumFacing.HORIZONTALS)
            {
                final BlockPos offsetPos = pos.offset(face);
                if (placeAndDecreaseCount(entityItem, offsetPos))
                {
                    entityItem.setDead();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean placeAndDecreaseCount(EntityItem entityItem, BlockPos pos) {
        if (entityItem.world.mayPlace(block, pos, false, EnumFacing.UP, null) && entityItem.world.setBlockState(pos, block.getDefaultState()))
        {
            entityItem.getItem().shrink(1);
        }
        return entityItem.getItem().isEmpty();
    }

}