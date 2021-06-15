package tfcflorae.objects.items.rock;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;

import tfcflorae.client.GuiHandler;
import tfcflorae.objects.items.ItemTFCF;
import tfcflorae.util.OreDictionaryHelper;

import static tfcflorae.TFCFlorae.MODID;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemMud extends ItemTFCF implements IRockObject
{
    private static final Map<Rock, ItemMud> MAP = new HashMap<>();

    public static ItemMud get(Rock rock)
    {
        return MAP.get(rock);
    }

    public Rock getRock()
    {
        return rock;
    }

    public static ItemStack get(Rock rock, int amount)
    {
        return new ItemStack(MAP.get(rock), amount);
    }

    private final Rock rock;
    private final ResourceLocation textureForegroundLocation;
    private final ResourceLocation textureBackgroundLocation;

    public ItemMud(Rock rock)
    {
        this.rock = rock;
        this.textureForegroundLocation = new ResourceLocation(MODID, "textures/gui/knapping/mud_button/" + rock + ".png");
        this.textureBackgroundLocation = new ResourceLocation(MODID, "textures/gui/knapping/mud_button_disabled/" + rock + ".png");
        if (MAP.put(rock, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        OreDictionaryHelper.register(this, "mud");
        OreDictionaryHelper.register(this, "mud", rock);
        OreDictionaryHelper.register(this, "mud", rock.getRockCategory());

        if (rock.isFluxStone())
        {
            OreDictionaryHelper.register(this, "mud", "flux");
        }
    }

    @Override
    @Nonnull
    public Rock getRock(ItemStack stack)
    {
        return rock;
    }

    @Override
    @Nonnull
    public RockCategory getRockCategory(ItemStack stack)
    {
        return rock.getRockCategory();
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack)
    {
        return Size.SMALL; // Stored everywhere
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack)
    {
        return Weight.VERY_LIGHT; // Stacksize = 64
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && !player.isSneaking() && stack.getCount() > 2)
        {
            GuiHandler.openGui(world, player.getPosition(), player, GuiHandler.Type.MUD);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Nonnull
    public void onRightClick(PlayerInteractEvent.RightClickItem event)
    {
        EnumHand hand = event.getHand();
        if(OreDictionaryHelper.doesStackMatchOre(event.getItemStack(), "mud") && hand == EnumHand.MAIN_HAND)
        {
            EntityPlayer player = event.getEntityPlayer();
            World world = event.getWorld();
            ItemStack stack = player.getHeldItem(hand);
            if (!world.isRemote && !player.isSneaking() && stack.getCount() > 2)
            {
                GuiHandler.openGui(world, player.getPosition(), player, GuiHandler.Type.MUD);
            }
        }
    }

    public ResourceLocation getForegroundTexture()
    {
        return textureForegroundLocation;
    }
    
    public ResourceLocation getBackgroundTexture()
    {
        return textureBackgroundLocation;
    }
}