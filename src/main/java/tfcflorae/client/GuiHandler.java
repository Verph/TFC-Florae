package tfcflorae.client;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;

import net.dries007.tfc.client.gui.*;
import net.dries007.tfc.objects.container.*;

import tfcflorae.TFCFlorae;
import tfcflorae.api.knapping.KnappingTypes;
import tfcflorae.objects.blocks.fruitwood.BlockFruitChestTFCF;
import tfcflorae.objects.items.ceramics.ItemClayKaolinite;
import tfcflorae.objects.items.rock.ItemMud;
import tfcflorae.util.OreDictionaryHelper;

public class GuiHandler implements IGuiHandler
{
    private static final ResourceLocation MUD_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/mud_button.png");
    public static final ResourceLocation MUD_DISABLED_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/mud_button_disabled.png");
    public static final ResourceLocation KAOLINITE_CLAY_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/kaolinite_clay_button.png");
    public static final ResourceLocation KAOLINITE_CLAY_DISABLED_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/kaolinite_clay_button_disabled.png");
    public static final ResourceLocation FLINT_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/flint_button.png");
    public static final ResourceLocation FLINT_DISABLED_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/flint_button_disabled.png");

    public static void openGui(World world, BlockPos pos, EntityPlayer player, Type type)
    {
        player.openGui(TFCFlorae.instance, type.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
    }

    public static void openGui(World world, EntityPlayer player, Type type)
    {
        player.openGui(TFCFlorae.instance, type.ordinal(), world, 0, 0, 0);
    }

    @Override
    @Nullable
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        ItemStack stack = player.getHeldItemMainhand();
        Type type = Type.valueOf(ID);
        switch (type)
        {
            case MUD:
                return new ContainerKnapping(KnappingTypes.MUD, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "mud") ? stack : player.getHeldItemOffhand());
            case KAOLINITE_CLAY:
                return new ContainerKnapping(KnappingTypes.KAOLINITE_CLAY, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "clayKaolinite") ? stack : player.getHeldItemOffhand());
                case FLINT:
                    return new ContainerKnapping(KnappingTypes.FLINT, player.inventory,  OreDictionaryHelper.doesStackMatchOre(stack, "flint") ? stack : player.getHeldItemOffhand());
            case CHEST:
                if (world.getBlockState(pos).getBlock() instanceof BlockFruitChestTFCF)
                {
                    ILockableContainer chestContainer = ((BlockFruitChestTFCF) world.getBlockState(pos).getBlock()).getLockableContainer(world, pos);
                    //noinspection ConstantConditions
                    return new ContainerChestTFC(player.inventory, chestContainer, player);
                }
                return null;
            default:
                return null;
        }
    }

    @Override
    @Nullable
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        Container container = getServerGuiElement(ID, player, world, x, y, z);
        Type type = Type.valueOf(ID);
        switch (type)
        {
            case MUD:
                ItemStack stackMud = player.getHeldItemMainhand();
                stackMud = OreDictionaryHelper.doesStackMatchOre(stackMud, "mud") ? stackMud : player.getHeldItemOffhand();
                ItemMud mud = (ItemMud)(stackMud.getItem());
                return new GuiKnappingTFCF(container, player, KnappingTypes.MUD, mud.getForegroundTexture(), mud.getBackgroundTexture());
            case KAOLINITE_CLAY:
                return new GuiKnappingTFCF(container, player, KnappingTypes.KAOLINITE_CLAY, KAOLINITE_CLAY_TEXTURE);
            case FLINT:
                return new GuiKnappingTFCF(container, player, KnappingTypes.FLINT, FLINT_TEXTURE);
            case CHEST:
                if (container instanceof ContainerChestTFC)
                {
                    return new GuiChestTFC((ContainerChestTFC) container, player.inventory);
                }
                return null;
            default :
                return null;
        }
    }

    public enum Type
    {
        MUD,
        KAOLINITE_CLAY,
        FLINT,
        CHEST,
        NULL;

        private static final Type[] values = values();

        @Nonnull
        public static Type valueOf(int id)
        {
            while (id >= values.length) id -= values.length;
            while (id < 0) id += values.length;
            return values[id];
        }
    }
}