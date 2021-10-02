package tfcflorae.objects.te.multiblockte;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface TEMultiBlockInterface
{
    /**
     * Called when the corresponding {@link TEMultiBlockBase} is interacted with.
     *
     * @param player The player interacting with the {@link TEMultiBlockBase}
     */
    boolean onInteract(EntityPlayer player);
    
    /**
     * Called when the corresponding {@link TEMultiBlockBase} is destroyed.
     */
    void onDestroyed();
    
    ItemStack getPickedStack(World world, BlockPos pos, IBlockState state);
    
}
