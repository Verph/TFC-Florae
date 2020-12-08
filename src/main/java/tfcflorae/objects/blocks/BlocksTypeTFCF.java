package tfcflorae.objects.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.api.recipes.ChiselRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.calendar.ICalendar;

import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.blocktype.BlockDecoration;
import tfcflorae.types.BlockTypesTFCF;

import static net.dries007.tfc.objects.fluids.FluidsTFC.*;

@Mod.EventBusSubscriber(modid = TFCFlorae.MODID)
public class BlocksTypeTFCF
{
    private static ArrayList<Block> normalBlocks = new ArrayList<>();
    private static ArrayList<Block> inventoryBlocks = new ArrayList<>();

    public static ArrayList<Block> getNormalBlocks() {
        return normalBlocks;
    }

    public static ArrayList<Block> getInventoryBlocks() {
        return inventoryBlocks;
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> r = event.getRegistry();
        for(Block block : inventoryBlocks)
        {
            ItemBlockTFC itemBlockTFC = new ItemBlockTFC(block);
            itemBlockTFC.setRegistryName(block.getRegistryName());
            itemBlockTFC.setTranslationKey(block.getTranslationKey());
            itemBlockTFC.setCreativeTab(block.getCreativeTab());
            r.register(itemBlockTFC);
        }
        for(Block block : normalBlocks)
        {
            ItemBlockTFC itemBlockTFC = new ItemBlockTFC(block);
            itemBlockTFC.setRegistryName(block.getRegistryName());
            itemBlockTFC.setTranslationKey(block.getTranslationKey());
            itemBlockTFC.setCreativeTab(block.getCreativeTab());
            r.register(itemBlockTFC);
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> r = event.getRegistry();
        for(Rock rock: TFCRegistries.ROCKS.getValuesCollection())
        {
            for(BlockTypesTFCF type: BlockTypesTFCF.values())
            {
                BlockDecoration blockDecoration = registerBlock(r, type.name().toLowerCase()+"/"+rock.getRegistryName().getPath(), rock, type);
                normalBlocks.add(blockDecoration);
            }
        }
    }

    public static BlockDecoration registerBlock(IForgeRegistry<Block> r, String name, Rock rock, BlockTypesTFCF BlockTypesTFCF)
    {
        BlockDecoration block = BlockDecoration.create(rock, BlockTypesTFCF);
        block.setRegistryName(TFCFlorae.MODID, name);
        block.setTranslationKey(TFCFlorae.MODID + "." + name.replace('/', '.'));
        block.setCreativeTab(CreativeTabsTFC.CT_ROCK_BLOCKS);
        r.register(block);
        return block;
    }
}
