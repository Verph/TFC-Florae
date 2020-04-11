package tfcelementia.api.types;

import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

//import net.dries007.tfc.api.types.*;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.stone.*;
import net.dries007.tfc.objects.items.rock.*;
import net.dries007.tfc.util.Helpers;

import tfcelementia.api.types.*;
import tfcelementia.api.registries.TFCERegistries;
import tfcelementia.objects.blocks.stone.*;
//import tfcelementia.objects.items.rock.*;

import static tfcelementia.TFCElementia.MODID;
import static tfcelementia.api.types.RockTFCE.FallingBlockType.*;

/**
 * todo: document API
 */
public class RockTFCE extends IForgeRegistryEntry.Impl<RockTFCE>
{
    @GameRegistry.ObjectHolder("tfc:granite")
    public static final Rock GRANITE = Helpers.getNull();
    @GameRegistry.ObjectHolder("tfc:basalt")
    public static final Rock BASALT = Helpers.getNull();
    @GameRegistry.ObjectHolder("tfc:rhyolite")
    public static final Rock RHYOLITE = Helpers.getNull();
    @GameRegistry.ObjectHolder("tfc:limestone")
    public static final Rock LIMESTONE = Helpers.getNull();
    
    private final RockCategoryTFCE rockCategory;
    private final ResourceLocation textureLocation;
    private final boolean isFluxStone;

    public RockTFCE(@Nonnull ResourceLocation name, @Nonnull RockCategoryTFCE rockCategory, boolean isFluxStone)
    {
        //noinspection ConstantConditions
        if (rockCategory == null)
            throw new IllegalArgumentException("RockTFCE category is not allowed to be null (on rock " + name + ")");

        setRegistryName(name);
        this.rockCategory = rockCategory;
        this.textureLocation = new ResourceLocation(MODID, "textures/blocks/stonetypes/raw/" + name.getPath() + ".png");
        this.isFluxStone = isFluxStone;
    }

    public RockTFCE(@Nonnull ResourceLocation name, @Nonnull ResourceLocation categoryName, boolean isFluxStone)
    {
        //noinspection ConstantConditions
        this(name, TFCERegistries.ROCK_CATEGORIES_TFCE.getValue(categoryName), isFluxStone);
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

    public RockCategoryTFCE getRockCategory()
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

    /*
    public enum ToolType
    {
        AXE(ItemRockAxe::new, " X   ", "XXXX ", "XXXXX", "XXXX ", " X   "),
        SHOVEL(ItemRockShovel::new, "XXX", "XXX", "XXX", "XXX", " X "),
        HOE(ItemRockHoe::new, "XXXXX", "   XX"),
        KNIFE(ItemRockKnife::new, "X ", "XX", "XX", "XX", "XX"),
        JAVELIN(ItemRockJavelin::new, "XXX  ", "XXXX ", "XXXXX", " XXX ", "  X  "),
        HAMMER(ItemRockHammer::new, "XXXXX", "XXXXX", "  X  ");

        private final Function<RockCategoryTFCE, Item> supplier;
        private final String[] pattern;
        
        ToolType(@Nonnull Function<RockCategoryTFCE, Item> supplier, String... pattern)
        {
            this.supplier = supplier;
            this.pattern = pattern;
        }

        public Item create(RockCategoryTFCE category)
        {
            return supplier.apply(category);
        }

        public String[] getPattern()
        {
            return pattern;
        }
        
    }
    */

    public enum Type
    {
        MOSSY_COBBLE(Material.ROCK, FALL_HORIZONTAL, false),
        CRACKED_BRICKS(Material.ROCK, NO_FALL, false),
        MOSSY_BRICKS(Material.ROCK, NO_FALL, false),
        PODZOL(Material.GRASS, FALL_HORIZONTAL, true);

        public final Material material;
        public final boolean isGrass;

        private final FallingBlockType gravType;
        private final BiFunction<Type, RockTFCE, BlockRockVariantTFCE> supplier;

        Type(Material material, FallingBlockType gravType, boolean isGrass)
        {
            // If no fall + no grass, then normal. If it can fall, then either fallable or fallable + connected (since grass always falls)
            this(material, gravType, isGrass, (gravType == NO_FALL && !isGrass) ? BlockRockVariantTFCE::new :
                (isGrass ? BlockRockVariantConnectedTFCE::new : BlockRockVariantFallableTFCE::new));
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

        public Type getNonPodzolVersion()
        {
            if (!isGrass) return this;
            switch (this)
            {
                case PODZOL:
                    return PODZOL; //Make PODZOL transform into DIRT
            }
            throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
        }

        /*
        public Type getGrassVersion(Type spreader)
        {
            if (!spreader.isGrass) throw new IllegalArgumentException("Non-grass can't spread.");
            switch (this)
            {
                case DIRT:
                    return spreader == DRY_GRASS ? DRY_GRASS : GRASS;
                case CLAY:
                    return CLAY_GRASS;
            }
            throw new IllegalArgumentException("You cannot get grass from rock types.");
        }
        */
    }

    public enum FallingBlockType
    {
        NO_FALL,
        FALL_VERTICAL,
        FALL_HORIZONTAL
    }
}
