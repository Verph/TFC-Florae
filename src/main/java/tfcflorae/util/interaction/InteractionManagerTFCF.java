package tfcflorae.util.interaction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.te.TELogPile;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.interaction.IRightClickBlockAction;
import net.dries007.tfc.util.interaction.IRightClickItemAction;

import tfcflorae.TFCFlorae;

import static net.dries007.tfc.objects.blocks.BlockCharcoalPile.LAYERS;
import static tfcflorae.TFCFlorae.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public final class InteractionManagerTFCF
{
    private static final Map<Predicate<ItemStack>, IRightClickBlockAction> USE_ACTIONS = new HashMap<>();
    private static final Map<Predicate<ItemStack>, IRightClickItemAction> RIGHT_CLICK_ACTIONS = new HashMap<>();
    private static final ThreadLocal<Boolean> PROCESSING_INTERACTION = ThreadLocal.withInitial(() -> false); // avoids stack overflows where some mods post interaction events during onBlockActivated (see #1321)

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        if (!PROCESSING_INTERACTION.get())
        {
            PROCESSING_INTERACTION.set(true);
            IRightClickBlockAction action = InteractionManagerTFCF.findItemUseAction(event.getItemStack());
            if (event.getItemStack().getItem() instanceof ItemSeedsTFC)
            {
                // Use alternative handling
                EnumActionResult result;
                if (event.getSide() == Side.CLIENT)
                {
                    result = ClientInteractionManagerTFCF.processRightClickBlock(event);
                }
                else
                {
                    result = ServerInteractionManagerTFCF.processRightClickBlock(event);
                }
                event.setCancellationResult(result);
                event.setCanceled(true);
            }
            PROCESSING_INTERACTION.set(false);
        }
    }

    @Nullable
    private static IRightClickBlockAction findItemUseAction(ItemStack stack)
    {
        return USE_ACTIONS.entrySet().stream().filter(e -> e.getKey().test(stack)).findFirst().map(Map.Entry::getValue).orElse(null);
    }

    @Nullable
    private static IRightClickItemAction findItemRightClickAction(ItemStack stack)
    {
        return RIGHT_CLICK_ACTIONS.entrySet().stream().filter(e -> e.getKey().test(stack)).findFirst().map(Map.Entry::getValue).orElse(null);
    }
}
