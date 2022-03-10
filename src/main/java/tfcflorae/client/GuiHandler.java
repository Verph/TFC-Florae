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
import net.dries007.tfc.util.Helpers;

import tfcflorae.TFCFlorae;
import tfcflorae.api.knapping.KnappingTypes;
import tfcflorae.client.gui.GuiBag;
import tfcflorae.client.gui.GuiCondenser;
import tfcflorae.client.gui.GuiCrate;
import tfcflorae.client.gui.GuiSack;
import tfcflorae.client.gui.GuiUrn;
import tfcflorae.objects.blocks.wood.fruitwood.BlockFruitChestTFCF;
import tfcflorae.objects.container.ContainerBag;
import tfcflorae.objects.container.ContainerCondenser;
import tfcflorae.objects.container.ContainerCrate;
import tfcflorae.objects.container.ContainerSack;
import tfcflorae.objects.container.ContainerUrn;
import tfcflorae.objects.items.ItemBag;
import tfcflorae.objects.items.ItemSack;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.objects.items.ceramics.ItemClayKaolinite;
import tfcflorae.objects.items.rock.ItemMud;
import tfcflorae.objects.te.TECondenser;
import tfcflorae.objects.te.TECrate;
import tfcflorae.objects.te.TEUrn;
import tfcflorae.util.OreDictionaryHelper;

import static tfcflorae.TFCFlorae.MODID;
public class GuiHandler implements IGuiHandler
{
    public static final ResourceLocation SACK_INVENTORY_BACKGROUND = new ResourceLocation(TFCFlorae.MODID, "textures/gui/sack_inventory.png");
    public static final ResourceLocation BAG_INVENTORY_BACKGROUND = new ResourceLocation(TFCFlorae.MODID, "textures/gui/bag_inventory.png");
    public static final ResourceLocation PINEAPPLE_LEATHER_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/pineapple_leather_button.png");
    public static final ResourceLocation BURLAP_CLOTH_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/blocks/devices/loom/product/burlap.png");
    public static final ResourceLocation WOOL_CLOTH_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/blocks/devices/loom/product/wool.png");
    public static final ResourceLocation SILK_CLOTH_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/blocks/devices/loom/product/silk.png");
    public static final ResourceLocation SISAL_CLOTH_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/blocks/devices/loom/product/sisal.png");
    public static final ResourceLocation COTTON_CLOTH_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/blocks/devices/loom/product/cotton.png");
    public static final ResourceLocation LINEN_CLOTH_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/blocks/devices/loom/product/linen.png");
    public static final ResourceLocation HEMP_CLOTH_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/blocks/devices/loom/product/hemp.png");
    public static final ResourceLocation YUCCA_CANVAS_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/blocks/devices/loom/product/yucca.png");
    public static final ResourceLocation MUD_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/mud_button.png");
    public static final ResourceLocation MUD_DISABLED_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/mud_button_disabled.png");
    public static final ResourceLocation EARTHENWARE_CLAY_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/earthenware_clay_button.png");
    public static final ResourceLocation EARTHENWARE_CLAY_DISABLED_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/earthenware_clay_button_disabled.png");
    public static final ResourceLocation KAOLINITE_CLAY_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/kaolinite_clay_button.png");
    public static final ResourceLocation KAOLINITE_CLAY_DISABLED_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/kaolinite_clay_button_disabled.png");
    public static final ResourceLocation STONEWARE_CLAY_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/stoneware_clay_button.png");
    public static final ResourceLocation STONEWARE_CLAY_DISABLED_TEXTURE = new ResourceLocation(TFCFlorae.MODID, "textures/gui/knapping/stoneware_clay_button_disabled.png");
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
            case SACK:
                return new ContainerSack(player.inventory, stack.getItem() instanceof ItemSack ? stack : player.getHeldItemOffhand());
            case BAG:
                return new ContainerBag(player.inventory, stack.getItem() instanceof ItemBag ? stack : player.getHeldItemOffhand());
            case PINEAPPLE_LEATHER:
                return new ContainerKnapping(KnappingTypes.PINEAPPLE_LEATHER, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "leatherPineapple") ? stack : player.getHeldItemOffhand());
            case BURLAP_CLOTH:
                return new ContainerKnapping(KnappingTypes.BURLAP_CLOTH, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "clothBurlap") ? stack : player.getHeldItemOffhand());
            case WOOL_CLOTH:
                return new ContainerKnapping(KnappingTypes.WOOL_CLOTH, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "clothWool") ? stack : player.getHeldItemOffhand());
            case SILK_CLOTH:
                return new ContainerKnapping(KnappingTypes.SILK_CLOTH, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "clothSilk") ? stack : player.getHeldItemOffhand());
            case SISAL_CLOTH:
                return new ContainerKnapping(KnappingTypes.SISAL_CLOTH, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "clothSisal") ? stack : player.getHeldItemOffhand());
            case COTTON_CLOTH:
                return new ContainerKnapping(KnappingTypes.COTTON_CLOTH, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "clothCotton") ? stack : player.getHeldItemOffhand());
            case LINEN_CLOTH:
                return new ContainerKnapping(KnappingTypes.LINEN_CLOTH, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "clothLinen") ? stack : player.getHeldItemOffhand());
            case HEMP_CLOTH:
                return new ContainerKnapping(KnappingTypes.HEMP_CLOTH, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "clothHemp") ? stack : player.getHeldItemOffhand());
            case YUCCA_CANVAS:
                return new ContainerKnapping(KnappingTypes.YUCCA_CANVAS, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "canvasYucca") ? stack : player.getHeldItemOffhand());
            case MUD:
                return new ContainerKnapping(KnappingTypes.MUD, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "mud") ? stack : player.getHeldItemOffhand());
            case EARTHENWARE_CLAY:
                return new ContainerKnapping(KnappingTypes.EARTHENWARE_CLAY, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "clayEarthenware") ? stack : player.getHeldItemOffhand());
            case KAOLINITE_CLAY:
                return new ContainerKnapping(KnappingTypes.KAOLINITE_CLAY, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "clayKaolinite") ? stack : player.getHeldItemOffhand());
            case STONEWARE_CLAY:
                return new ContainerKnapping(KnappingTypes.STONEWARE_CLAY, player.inventory, OreDictionaryHelper.doesStackMatchOre(stack, "clayStoneware") ? stack : player.getHeldItemOffhand());
            case FLINT:
                return new ContainerKnapping(KnappingTypes.FLINT, player.inventory,  OreDictionaryHelper.doesStackMatchOre(stack, "flint") ? stack : player.getHeldItemOffhand());
            case URN:
                return new ContainerUrn(player.inventory, Helpers.getTE(world, pos, TEUrn.class));
            case CRATE:
                return new ContainerCrate(player.inventory, Helpers.getTE(world, pos, TECrate.class));
            case CHEST:
                if (world.getBlockState(pos).getBlock() instanceof BlockFruitChestTFCF)
                {
                    ILockableContainer chestContainer = ((BlockFruitChestTFCF) world.getBlockState(pos).getBlock()).getLockableContainer(world, pos);
                    //noinspection ConstantConditions
                    return new ContainerChestTFC(player.inventory, chestContainer, player);
                }
                return null;
            case CONDENSER:
                return new ContainerCondenser(player.inventory, Helpers.getTE(world, pos, TECondenser.class));
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
        BlockPos pos = new BlockPos(x, y, z);
        switch (type)
        {
            case SACK:
                return new GuiSack(container, player.inventory, SACK_INVENTORY_BACKGROUND);
            case BAG:
                return new GuiBag(container, player.inventory, BAG_INVENTORY_BACKGROUND);
            case PINEAPPLE_LEATHER:
                return new GuiKnappingTFCF(container, player, KnappingTypes.PINEAPPLE_LEATHER, PINEAPPLE_LEATHER_TEXTURE);
            case BURLAP_CLOTH:
                return new GuiKnappingTFCF(container, player, KnappingTypes.BURLAP_CLOTH, BURLAP_CLOTH_TEXTURE);
            case WOOL_CLOTH:
                return new GuiKnappingTFCF(container, player, KnappingTypes.WOOL_CLOTH, WOOL_CLOTH_TEXTURE);
            case SILK_CLOTH:
                return new GuiKnappingTFCF(container, player, KnappingTypes.SILK_CLOTH, SILK_CLOTH_TEXTURE);
            case SISAL_CLOTH:
                return new GuiKnappingTFCF(container, player, KnappingTypes.SISAL_CLOTH, SISAL_CLOTH_TEXTURE);
            case COTTON_CLOTH:
                return new GuiKnappingTFCF(container, player, KnappingTypes.COTTON_CLOTH, COTTON_CLOTH_TEXTURE);
            case LINEN_CLOTH:
                return new GuiKnappingTFCF(container, player, KnappingTypes.LINEN_CLOTH, LINEN_CLOTH_TEXTURE);
            case HEMP_CLOTH:
                return new GuiKnappingTFCF(container, player, KnappingTypes.HEMP_CLOTH, HEMP_CLOTH_TEXTURE);
            case YUCCA_CANVAS:
                return new GuiKnappingTFCF(container, player, KnappingTypes.YUCCA_CANVAS, YUCCA_CANVAS_TEXTURE);
            case MUD:
                ItemStack stackMud = player.getHeldItemMainhand();
                stackMud = OreDictionaryHelper.doesStackMatchOre(stackMud, "mud") ? stackMud : player.getHeldItemOffhand();
                ItemMud mud = (ItemMud)(stackMud.getItem());
                return new GuiKnappingTFCF(container, player, KnappingTypes.MUD, mud.getForegroundTexture(), mud.getBackgroundTexture());
            case EARTHENWARE_CLAY:
                return new GuiKnappingTFCF(container, player, KnappingTypes.EARTHENWARE_CLAY, EARTHENWARE_CLAY_TEXTURE);
            case KAOLINITE_CLAY:
                return new GuiKnappingTFCF(container, player, KnappingTypes.KAOLINITE_CLAY, KAOLINITE_CLAY_TEXTURE);
            case STONEWARE_CLAY:
                return new GuiKnappingTFCF(container, player, KnappingTypes.STONEWARE_CLAY, STONEWARE_CLAY_TEXTURE);
            case FLINT:
                return new GuiKnappingTFCF(container, player, KnappingTypes.FLINT, FLINT_TEXTURE);
            case URN:
                return new GuiUrn(container, player.inventory, Helpers.getTE(world, pos, TEUrn.class), world.getBlockState(new BlockPos(x, y, z)).getBlock().getTranslationKey());
            case CRATE:
                return new GuiCrate(container, player.inventory, Helpers.getTE(world, pos, TECrate.class), world.getBlockState(new BlockPos(x, y, z)).getBlock().getTranslationKey());
            case CHEST:
                if (container instanceof ContainerChestTFC)
                {
                    return new GuiChestTFC((ContainerChestTFC) container, player.inventory);
                }
                return null;
            case CONDENSER:
                return new GuiCondenser(container, player.inventory, Helpers.getTE(world, pos, TECondenser.class), world.getBlockState(new BlockPos(x, y, z)).getBlock().getTranslationKey());
            default:
                return null;
        }
    }

    public enum Type
    {
        SACK,
        BAG,
        PINEAPPLE_LEATHER,
        BURLAP_CLOTH,
        WOOL_CLOTH,
        SILK_CLOTH,
        SISAL_CLOTH,
        COTTON_CLOTH,
        LINEN_CLOTH,
        HEMP_CLOTH,
        YUCCA_CANVAS,
        MUD,
        EARTHENWARE_CLAY,
        KAOLINITE_CLAY,
        STONEWARE_CLAY,
        FLINT,
        CHEST,
        URN,
        CRATE,
        CONDENSER,
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