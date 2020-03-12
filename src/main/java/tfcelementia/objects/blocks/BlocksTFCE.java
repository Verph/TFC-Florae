package tfcelementia.objects.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.*;
import net.dries007.tfc.objects.blocks.BlockFluidTFC;
import net.dries007.tfc.objects.blocks.devices.*;
import net.dries007.tfc.objects.blocks.stone.*;
import net.dries007.tfc.objects.blocks.wood.*;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.items.itemblock.*;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.Rock.Type.*;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;
import static net.dries007.tfc.util.Helpers.getNull;

import tfcelementia.objects.blocks.agriculture.BlockCropDeadTFCE;
import tfcelementia.objects.blocks.agriculture.BlockCropTFCE;
import tfcelementia.objects.items.*;
import tfcelementia.objects.fluids.FluidsTFCE;
import tfcelementia.util.agriculture.FoodTFCE;
import tfcelementia.util.agriculture.CropTFCE;
import tfcelementia.objects.te.*;
import tfcelementia.TFCElementia;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = TFCElementia.MODID)
@GameRegistry.ObjectHolder(MOD_ID)
public final class BlocksTFCE 
{
    private static ImmutableList<BlockCropTFCE> allCropBlocks;
    private static ImmutableList<BlockCropDeadTFCE> allDeadCropBlocks;
    
    private static ImmutableList<BlockFluidBase> allFluidBlocks;
    
    public static ImmutableList<BlockCropTFCE> getAllCropBlocks()
    {
        return allCropBlocks;
    }

    public static ImmutableList<BlockCropDeadTFCE> getAllDeadCropBlocks()
    {
        return allDeadCropBlocks;
    }
    
    public static ImmutableList<BlockFluidBase> getAllFluidBlocks()
    {
        return allFluidBlocks;
    }
    
    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
    	FluidsTFCE.registerFluids();
    	
    	IForgeRegistry<Block> r = event.getRegistry();
    	
    
    	{
            Builder<BlockCropTFCE> b = ImmutableList.builder();

            for (CropTFCE crop : CropTFCE.values())
            {
                b.add(register(r, "crop/" + crop.name().toLowerCase(), crop.createGrowingBlock()));
            }

            allCropBlocks = b.build();
        }

        {
            Builder<BlockCropDeadTFCE> b = ImmutableList.builder();

            for (CropTFCE crop : CropTFCE.values())
            {
                b.add(register(r, "dead_crop/" + crop.name().toLowerCase(), crop.createDeadBlock()));
            }

            allDeadCropBlocks = b.build();
        }
        
        {
            Builder<BlockFluidBase> b = ImmutableList.builder();
            // We always want to register our water variants, as they absolutely need special subclasses
            /*b.add(
                register(r, "fluid/hot_water", new BlockFluidHotWater()),
                register(r, "fluid/fresh_water", new BlockFluidWater(FluidsTFC.FRESH_WATER.get(), Material.WATER, false)),
                register(r, "fluid/salt_water", new BlockFluidWater(FluidsTFC.SALT_WATER.get(), Material.WATER, true))
            );*/
            for (FluidWrapper wrapper : FluidsTFCE.getAllAlcoholsFluids())
            {
                if (wrapper.isDefault())
                {
                    b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
                }
            }
            for (FluidWrapper wrapper : FluidsTFCE.getAllTeaFluids())
            {
                if (wrapper.isDefault())
                {
                    b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
                }
            }
            allFluidBlocks = b.build();
        }

        register(TECropBaseTFCE.class, "crop_base_tfce");
        register(TECropSpreadingTFCE.class, "crop_spreading_tfce");
    }
    
    private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block, CreativeTabs ct)
    {
        block.setCreativeTab(ct);
        return register(r, name, block);
    }
    
    private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block)
    {
        block.setRegistryName(MOD_ID, name);
        block.setTranslationKey(MOD_ID + "." + name.replace('/', '.'));
        r.register(block);
        return block;
    }
    
    private static <T extends TileEntity> void register(Class<T> te, String name)
    {
        TileEntity.register(MOD_ID + ":" + name, te);
    }

}