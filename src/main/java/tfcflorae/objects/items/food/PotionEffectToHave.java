package tfcflorae.objects.items.food;

import net.minecraft.potion.Potion;

public final class PotionEffectToHave
{
    public Potion PotionEffect;
    public int Duration;
    public int Power;
    public int chance;

    public PotionEffectToHave(Potion PotionEffect, int EffectDuration, int EffectPower)
    {
        this.PotionEffect = PotionEffect;
        Duration = EffectDuration;
        Power = EffectPower;
        chance = 0;
    }

    
    public PotionEffectToHave(Potion PotionEffect, int EffectDuration, int EffectPower, int chance)
    {
        this.PotionEffect = PotionEffect;
        Duration = EffectDuration;
        Power = EffectPower;
        this.chance = chance;
    }
}