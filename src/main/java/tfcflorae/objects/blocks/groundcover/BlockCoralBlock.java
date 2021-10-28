package tfcflorae.objects.blocks.groundcover;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

@ParametersAreNonnullByDefault
public class BlockCoralBlock extends Block implements IItemSize
{
    public static final Map<EnumDyeColor, BlockCoralBlock> TUBE_CORAL_BLOCK = new HashMap<>();
    public static final Map<EnumDyeColor, BlockCoralBlock> BRAIN_CORAL_BLOCK = new HashMap<>();
    public static final Map<EnumDyeColor, BlockCoralBlock> BUBBLE_CORAL_BLOCK = new HashMap<>();
    public static final Map<EnumDyeColor, BlockCoralBlock> FIRE_CORAL_BLOCK = new HashMap<>();
    public static final Map<EnumDyeColor, BlockCoralBlock> HORN_CORAL_BLOCK = new HashMap<>();

    public BlockCoralBlock(MapColor blockMapColorIn)
    {
        super(Material.CORAL, blockMapColorIn);
        setSoundType(SoundType.PLANT);
        setHardness(0.5F);
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack)
    {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack)
    {
        return Weight.LIGHT;
    }
}
