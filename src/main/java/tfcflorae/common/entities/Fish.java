package tfcflorae.common.entities;

import java.util.Locale;

import tfcflorae.client.TFCFSounds;

public enum Fish
{
    //BLUEGILL(0x00658A, 0xE3E184),
    CRAPPIE(true, 0xf542ef, 0xd6d5c5),
    LAKE_TROUT(0x707594, 0x8f816d),
    LARGEMOUTH_BASS(0x619c57, 0x9c7f57),
    RAINBOW_TROUT(0xc928c7, 0xc97a0a),
    //SALMON(10489616, 951412),
    SMALLMOUTH_BASS(0x9c7f57, 0x619c57)
    ;

    private final String serializedName;
    private final boolean isCod;
    private final int color1;
    private final int color2;

    Fish(int color1, int color2)
    {
        this.color1 = color1;
        this.color2 = color2;
        this.isCod = false;
        serializedName = name().toLowerCase(Locale.ROOT);
    }

    Fish(boolean isCod, int color1, int color2)
    {
        this.color1 = color1;
        this.color2 = color2;
        this.isCod = isCod;
        serializedName = name().toLowerCase(Locale.ROOT);
    }

    public String getSerializedName()
    {
        return serializedName;
    }

    public TFCFSounds.FishSound makeSound()
    {
        /*if (this == SALMON)
        {
            return new TFCSounds.FishSound(() -> SoundEvents.SALMON_AMBIENT, () -> SoundEvents.SALMON_DEATH, () -> SoundEvents.SALMON_HURT, () -> SoundEvents.SALMON_FLOP);
        }*/
        return TFCFSounds.createFish(serializedName);
    }

    public float getWidth()
    {
        return 0.7f;
    }

    public float getHeight()
    {
        return 0.4f;
    }

    public int getEggColor2()
    {
        return color2;
    }

    public int getEggColor1()
    {
        return color1;
    }

    public boolean isCod()
    {
        return isCod;
    }
}
