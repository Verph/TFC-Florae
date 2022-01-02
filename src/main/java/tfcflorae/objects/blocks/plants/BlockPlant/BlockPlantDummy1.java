package tfcflorae.objects.blocks.plants.BlockPlant;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import net.dries007.tfc.Constants;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.util.agriculture.CropTFCF;

public class BlockPlantDummy1 extends BlockPlantTFCF
{
    private static final Map<Plant, BlockPlantDummy1> MAP = new HashMap<>();

    public static BlockPlantDummy1 get(Plant plant)
    {
        return MAP.get(plant);
    }

    public BlockPlantDummy1(Plant plant)
    {
        super(plant);
        if (MAP.put(plant, this) != null) throw new IllegalStateException("There can only be one.");

        plant.getOreDictName().ifPresent(name -> OreDictionaryHelper.register(this, name));
    }
}
