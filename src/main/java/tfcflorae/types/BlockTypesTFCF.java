package tfcflorae.types;

import java.util.function.Function;
import javax.annotation.Nonnull;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.*;
import net.dries007.tfc.api.types.Rock.Type;
import net.dries007.tfc.util.Helpers;

public enum BlockTypesTFCF
{
    MOSSY_RAW(Material.ROCK, SoundType.STONE, 5, false),
    MUD_BRICK(Material.ROCK, SoundType.STONE, 4, false),
    MUD(Material.GROUND, SoundType.GROUND, 2, true),
    COARSE_DIRT(Material.GROUND, SoundType.GROUND, 2, true),
    PODZOL(Material.GRASS, SoundType.PLANT, 2, true);

    private Material material;
    private SoundType soundType;
    private int hardness;
    private boolean fallable;

    BlockTypesTFCF(Material material, SoundType soundType, int hardness, boolean isFallable)
    {
        this.soundType = soundType;
        this.material = material;
        this.hardness = hardness;
        this.fallable = isFallable;
    }

    public Material getMaterial() 
    {
        return material;
    }

    public SoundType getSoundType() 
    {
        return soundType;
    }

    /* Soundtypes:
    static SoundType	ANVIL
    static SoundType	CLOTH
    static SoundType	GLASS
    static SoundType	GROUND
    static SoundType	LADDER
    static SoundType	METAL
    static SoundType	PLANT
    static SoundType	SAND
    static SoundType	SLIME
    static SoundType	SNOW
    static SoundType	STONE
    static SoundType	WOOD
    */
    
    public int getHardness() 
    {
        return hardness;
    }

    public boolean isFallable() 
    {
        return fallable;
    }
}
