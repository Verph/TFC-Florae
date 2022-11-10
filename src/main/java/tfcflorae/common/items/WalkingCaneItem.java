package tfcflorae.common.items;

import java.util.UUID;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = "tfcflorae")
public class WalkingCaneItem extends Item implements Vanishable
{
    public static final int USE_TIME = 300;

    public WalkingCaneItem(Properties properties)
    {
        super(properties);
    }

    private static final AttributeModifier MOVEMENT_SPEED = new AttributeModifier(UUID.fromString("995829fa-94c0-41bd-b046-0468c509a488"), "Cane modifier", 0.15D, AttributeModifier.Operation.MULTIPLY_TOTAL);

    @SubscribeEvent
    public static void onLivingTick(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END)
        {
            Player player = event.player;
            if (!player.level.isClientSide)
            {
                AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
                if (movementSpeed != null)
                {
                    if (player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof WalkingCaneItem || player.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof WalkingCaneItem || player.getItemBySlot(EquipmentSlot.OFFHAND).getItem() == TFCFItems.WALKING_CANES.get() || player.getItemBySlot(EquipmentSlot.MAINHAND).getItem() == TFCFItems.WALKING_CANES.get())
                    {
                        if (!movementSpeed.hasModifier(MOVEMENT_SPEED))
                        {
                            movementSpeed.addPermanentModifier(MOVEMENT_SPEED); 
                        }
                    }
                    else if (movementSpeed.hasModifier(MOVEMENT_SPEED))
                    {
                        movementSpeed.removeModifier(MOVEMENT_SPEED);
                    } 
                }
            } 
        } 
    }

    @Override
    public int getUseDuration(ItemStack stack)
    {
        return USE_TIME;
    }
}
