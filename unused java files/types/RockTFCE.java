package tfcelementia.api.types;

import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.api.types.*;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.objects.blocks.stone.*;
import net.dries007.tfc.objects.items.rock.*;
import net.dries007.tfc.util.Helpers;

import tfcelementia.api.types.*;
import tfcelementia.objects.blocks.stone.*;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.Rock.FallingBlockType.*;

/**
 * todo: document API
 */
public class RockTFCE extends IForgeRegistryEntry.Impl<RockTFCE>
{
    @GameRegistry.ObjectHolder("tfc:granite")
    public static final RockTFCE GRANITE = Helpers.getNull();
    @GameRegistry.ObjectHolder("tfc:basalt")
    public static final RockTFCE BASALT = Helpers.getNull();
    @GameRegistry.ObjectHolder("tfc:rhyolite")
    public static final RockTFCE RHYOLITE = Helpers.getNull();

    private final RockCategory rockCategory;
    private final ResourceLocation textureLocation;
    private final boolean isFluxStone;

    public RockTFCE(@Nonnull ResourceLocation name, @Nonnull RockCategory rockCategory, boolean isFluxStone)
    {
        //noinspection ConstantConditions
        if (rockCategory == null)
            throw new IllegalArgumentException("Rock category is not allowed to be null (on rock " + name + ")");

        setRegistryName(name);
        this.rockCategory = rockCategory;
        this.textureLocation = new ResourceLocation(MOD_ID, "textures/blocks/stonetypes/raw/" + name.getPath() + ".png");
        this.isFluxStone = isFluxStone;
    }

    public RockTFCE(@Nonnull ResourceLocation name, @Nonnull ResourceLocation categoryName, boolean isFluxStone)
    {
        //noinspection ConstantConditions
        this(name, TFCRegistries.ROCK_CATEGORIES.getValue(categoryName), isFluxStone);
    }

    /**
     * Used for knapping GUI
     *
     * @return a texture resource location
     */
    public ResourceLocation getTexture()
    {
        return textureLocation;
    }

    public RockCategory getRockCategory()
    {
        return rockCategory;
    }

    public boolean isFluxStone()
    {
        return isFluxStone;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public String toString()
    {
        return getRegistryName().getPath();
    }

    public enum Type
    {
        MOSSY_COBBLE(Material.ROCK, FALL_HORIZONTAL, false),
        MUD(Material.CLAY, FALL_VERTICAL, false),
        PODZOL(Material.GRASS, FALL_VERTICAL, true);

        public final Material material;
        public final boolean isGrass;

        private final FallingBlockType gravType;
        private final BiFunction<Type, RockTFCE, BlockRockVariantTFCE> supplier;

        Type(Material material, FallingBlockType gravType, boolean isGrass)
        {
            // If no fall + no grass, then normal. If it can fall, then either fallable or fallable + connected (since grass always falls)
            this(material, gravType, isGrass, (gravType == NO_FALL && !isGrass) ? BlockRockVariantTFCE::new :
                (isGrass ? BlockRockVariantConnected::new : BlockRockVariantFallable::new));
        }

        Type(Material material, FallingBlockType gravType, boolean isGrass, BiFunction<Type, RockTFCE, BlockRockVariantTFCE> supplier)
        {
            this.material = material;
            this.gravType = gravType;
            this.isGrass = isGrass;
            this.supplier = supplier;
        }

        public boolean canFall()
        {
            return gravType != NO_FALL;
        }

        public boolean canFallHorizontal()
        {
            return gravType == FALL_HORIZONTAL;
        }

        public BlockRockVariantTFCE create(RockTFCE rock)
        {
            return supplier.apply(this, rock);
        }
    }

    public enum FallingBlockType
    {
        NO_FALL,
        FALL_VERTICAL,
        FALL_HORIZONTAL
    }
}