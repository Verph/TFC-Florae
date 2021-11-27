package tfcflorae.objects.items.food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.api.capability.food.FoodHandler;
import net.dries007.tfc.api.capability.food.FoodHeatHandler;
import net.dries007.tfc.api.capability.food.IItemFoodTFC;

import tfcflorae.objects.items.*;
import tfcflorae.objects.items.food.PotionEffectToHave;
import tfcflorae.util.OreDictionaryHelper;

public class ItemFoodTFCF extends ItemFood implements IItemFoodTFC
{
    public FoodData data;
    ArrayList<PotionEffectToHave> PotionEffects = new ArrayList<PotionEffectToHave>();

    public ItemFoodTFCF(FoodData data, Object... objs)
    {
        super(0, 0.0F, false);
        this.setMaxDamage(0);
        this.data = data;

        for (Object obj : objs)
        {
            if(obj instanceof PotionEffectToHave)
            {
                PotionEffectToHave Effect = (PotionEffectToHave)obj;
                PotionEffects.add(Effect);
            }
            else if (obj instanceof Object[])
                OreDictionaryHelper.register(this, (Object[]) obj);
            else
                OreDictionaryHelper.register(this, obj);
        }
    }

    @Override
    public ICapabilityProvider getCustomFoodHandler()
    {
        return new FoodHeatHandler(null, data, 1.0F, 200.0F);
    }

    private static final Map<ItemTFCF, ItemFoodTFCF> MAP = new HashMap<>();

    public static ItemFoodTFCF get(ItemFoodTFCF food)
    {
        return MAP.get(food);
    }

    public static ItemStack get(ItemTFCF food, int amount)
    {
        return new ItemStack(MAP.get(food), amount);
    }

	@Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if(!PotionEffects.isEmpty())
            for(PotionEffectToHave Effect : PotionEffects)
            {
                if (Constants.RNG.nextInt(Effect.chance) == 0)
                    player.addPotionEffect(new PotionEffect(Effect.PotionEffect, Effect.Duration, Effect.Power));
            }
    }
}