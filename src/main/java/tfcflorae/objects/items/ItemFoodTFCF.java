package tfcflorae.objects.items;

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

import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.api.capability.food.FoodHandler;
import net.dries007.tfc.api.capability.food.IItemFoodTFC;

import tfcflorae.util.OreDictionaryHelper;

final class PotionEffectToHave
{
    Potion PotionEffect;
    int Duration;
    int Power;

    public PotionEffectToHave(Potion PotionEffect, int EffectDuration, int EffectPower)
    {
        this.PotionEffect = PotionEffect;
        Duration = EffectDuration;
        Power = EffectPower;
    }
}

public class ItemFoodTFCF extends ItemFood implements IItemFoodTFC
{
    public FoodData data;
    ArrayList<PotionEffectToHave> PotionEffects = new ArrayList<PotionEffectToHave>();
    /*Potion EffectToUse = null;
    int EffectDuration = 0;
    int EffectPower = 0;*/

    /*public ItemFoodTFCF(FoodData data, String... oreNameParts)
    {
        super(0, 0.0F, false);
        this.setMaxDamage(0);
        this.data = data;

        for (String str : oreNameParts)
        {
            Object obj = str;
            if (obj instanceof Object[])
                OreDictionaryHelper.register(this, (Object[]) obj);
            else
                OreDictionaryHelper.register(this, obj);
        }
    }*/

    //public ItemFoodTFCF(FoodData data, Potion EffectToUse, int EffectDuration, int EffectPower, String... oreNameParts)
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
                /*this.EffectToUse = Effect.PotionEffect;
                this.EffectDuration = Effect.Duration;
                this.EffectPower = Effect.Power;*/
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
        return new FoodHandler(null, data);
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
                player.addPotionEffect(new PotionEffect(Effect.PotionEffect, Effect.Duration, Effect.Power));
    }
}