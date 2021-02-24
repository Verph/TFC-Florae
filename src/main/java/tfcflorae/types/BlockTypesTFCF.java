package tfcflorae.types;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.*;
import net.dries007.tfc.util.Helpers;

import static tfcflorae.types.BlockTypesTFCF.FallingBlockType.*;
import static tfcflorae.TFCFlorae.MODID;
public class BlockTypesTFCF extends IForgeRegistryEntry.Impl<BlockTypesTFCF>
{
    @GameRegistry.ObjectHolder("tfc:granite")
    public static final Rock GRANITE = Helpers.getNull();
    @GameRegistry.ObjectHolder("tfc:basalt")
    public static final Rock BASALT = Helpers.getNull();
    @GameRegistry.ObjectHolder("tfc:rhyolite")
    public static final Rock RHYOLITE = Helpers.getNull();
    @GameRegistry.ObjectHolder("tfc:limestone")
    public static final Rock LIMESTONE = Helpers.getNull();

    private final RockCategory rockCategory;
    private final ResourceLocation textureLocation;
    private final boolean isFluxStone;
    private final boolean isNaturallyGenerating;

    public BlockTypesTFCF(@Nonnull ResourceLocation name, @Nonnull RockCategory rockCategory, boolean isFluxStone, boolean isNaturallyGenerating)
    {
        //noinspection ConstantConditions
        if (rockCategory == null)
            throw new IllegalArgumentException("Rock category is not allowed to be null (on rock " + name + ")");

        setRegistryName(name);
        this.rockCategory = rockCategory;
        this.textureLocation = new ResourceLocation(MODID, "textures/blocks/stonetypes/raw/" + name.getPath() + ".png");
        this.isFluxStone = isFluxStone;
        this.isNaturallyGenerating = isNaturallyGenerating;
    }

    public BlockTypesTFCF(@Nonnull ResourceLocation name, @Nonnull RockCategory rockCategory, boolean isFluxStone)
    {
        this(name, rockCategory, isFluxStone, true);
    }

    public BlockTypesTFCF(@Nonnull ResourceLocation name, @Nonnull ResourceLocation categoryName, boolean isFluxStone)
    {
        //noinspection ConstantConditions
        this(name, TFCRegistries.ROCK_CATEGORIES.getValue(categoryName), isFluxStone, true);
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

    public boolean isNaturallyGenerating()
    {
        return isNaturallyGenerating;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public String toString()
    {
        return getRegistryName().getPath();
    }

    public enum RockTFCF
    {
        MOSSY_RAW(Material.ROCK, FALL_VERTICAL, false),
        MUD_BRICKS(Material.ROCK, FALL_VERTICAL, false),
        MUD(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_DIRT(Material.GROUND, FALL_HORIZONTAL, false),
        PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        LOAMY_SAND(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_LOAMY_SAND(Material.GROUND, FALL_HORIZONTAL, false),
        LOAMY_SAND_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        LOAMY_SAND_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        SANDY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_SANDY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        SANDY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        SANDY_LOAM_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        SANDY_CLAY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_SANDY_CLAY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        SANDY_CLAY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        SANDY_CLAY_LOAM_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        SANDY_CLAY(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_SANDY_CLAY(Material.GROUND, FALL_HORIZONTAL, false),
        SANDY_CLAY_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        SANDY_CLAY_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        LOAM_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        CLAY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_CLAY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        CLAY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        CLAY_LOAM_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        //CLAY(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_CLAY(Material.GROUND, FALL_HORIZONTAL, false),
        //CLAY_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        CLAY_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        SILTY_CLAY(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_SILTY_CLAY(Material.GROUND, FALL_HORIZONTAL, false),
        SILTY_CLAY_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        SILTY_CLAY_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        SILTY_CLAY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_SILTY_CLAY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        SILTY_CLAY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        SILTY_CLAY_LOAM_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        SILT_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_SILT_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        SILT_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        SILT_LOAM_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        SILT(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_SILT(Material.GROUND, FALL_HORIZONTAL, false),
        SILT_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        SILT_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_LOAMY_SAND_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_SANDY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_SANDY_CLAY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_SANDY_CLAY_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_CLAY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_CLAY_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_SILTY_CLAY_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_SILTY_CLAY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_SILT_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_SILT_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        HUMUS(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_HUMUS(Material.GROUND, FALL_HORIZONTAL, false),
        HUMUS_GRASS(Material.GROUND, FALL_HORIZONTAL, true),
        DRY_HUMUS_GRASS(Material.GROUND, FALL_HORIZONTAL, true),
        CLAY_HUMUS(Material.GROUND, FALL_HORIZONTAL, false),
        CLAY_HUMUS_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_CLAY_HUMUS_GRASS(Material.GROUND, FALL_HORIZONTAL, true),
        COARSE_CLAY_HUMUS(Material.GROUND, FALL_HORIZONTAL, false),
        
        // Kaolinite Clays
        KAOLINITE_CLAY(Material.GROUND, FALL_HORIZONTAL, false),
        KAOLINITE_CLAY_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        SANDY_KAOLINITE_CLAY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_SANDY_KAOLINITE_CLAY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        SANDY_KAOLINITE_CLAY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        SANDY_KAOLINITE_CLAY_LOAM_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        SANDY_KAOLINITE_CLAY(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_SANDY_KAOLINITE_CLAY(Material.GROUND, FALL_HORIZONTAL, false),
        SANDY_KAOLINITE_CLAY_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        SANDY_KAOLINITE_CLAY_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        KAOLINITE_CLAY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_KAOLINITE_CLAY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        KAOLINITE_CLAY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        KAOLINITE_CLAY_LOAM_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        COARSE_KAOLINITE_CLAY(Material.GROUND, FALL_HORIZONTAL, false),
        KAOLINITE_CLAY_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        SILTY_KAOLINITE_CLAY(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_SILTY_KAOLINITE_CLAY(Material.GROUND, FALL_HORIZONTAL, false),
        SILTY_KAOLINITE_CLAY_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        SILTY_KAOLINITE_CLAY_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        SILTY_KAOLINITE_CLAY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        COARSE_SILTY_KAOLINITE_CLAY_LOAM(Material.GROUND, FALL_HORIZONTAL, false),
        SILTY_KAOLINITE_CLAY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        SILTY_KAOLINITE_CLAY_LOAM_PODZOL(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_SANDY_KAOLINITE_CLAY_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_KAOLINITE_CLAY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_KAOLINITE_CLAY_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_SILTY_KAOLINITE_CLAY_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        KAOLINITE_CLAY_HUMUS(Material.GROUND, FALL_HORIZONTAL, false),
        KAOLINITE_CLAY_HUMUS_GRASS(Material.GRASS, FALL_HORIZONTAL, true),
        DRY_KAOLINITE_CLAY_HUMUS_GRASS(Material.GROUND, FALL_HORIZONTAL, true),
        COARSE_KAOLINITE_CLAY_HUMUS(Material.GROUND, FALL_HORIZONTAL, false),

        // Farmland
        LOAMY_SAND_FARMLAND(Material.GROUND, FALL_VERTICAL, false),
        SANDY_LOAM_FARMLAND(Material.GROUND, FALL_VERTICAL, false),
        LOAM_FARMLAND(Material.GROUND, FALL_VERTICAL, false),
        SILT_LOAM_FARMLAND(Material.GROUND, FALL_VERTICAL, false),
        SILT_FARMLAND(Material.GROUND, FALL_VERTICAL, false),
        HUMUS_FARMLAND(Material.GROUND, FALL_VERTICAL, false);

        public final Material material;
        public final boolean isGrass;

        private final FallingBlockType gravType;

        RockTFCF(Material material, FallingBlockType gravType, boolean isGrass)
        {
            this.material = material;
            this.gravType = gravType;
            this.isGrass = isGrass;
        }

        public boolean canFall()
        {
            return gravType != NO_FALL;
        }

        public boolean canFallHorizontal()
        {
            return gravType == FALL_HORIZONTAL;
        }

        public RockTFCF getNonGrassVersion()
        {
            if (!isGrass) return this;
            switch (this)
            {
                case DRY_LOAMY_SAND_GRASS:
                case LOAMY_SAND_GRASS:
                case LOAMY_SAND_PODZOL:
                    return LOAMY_SAND;
                case DRY_SANDY_LOAM_GRASS:
                case SANDY_LOAM_GRASS:
                case SANDY_LOAM_PODZOL:
                    return SANDY_LOAM;
                case DRY_SANDY_CLAY_LOAM_GRASS:
                case SANDY_CLAY_LOAM_GRASS:
                case SANDY_CLAY_LOAM_PODZOL:
                    return SANDY_CLAY_LOAM;
                case DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS:
                case SANDY_KAOLINITE_CLAY_LOAM_GRASS:
                case SANDY_KAOLINITE_CLAY_LOAM_PODZOL:
                    return SANDY_KAOLINITE_CLAY_LOAM;
                case DRY_SANDY_CLAY_GRASS:
                case SANDY_CLAY_GRASS:
                case SANDY_CLAY_PODZOL:
                    return SANDY_CLAY;
                case DRY_SANDY_KAOLINITE_CLAY_GRASS:
                case SANDY_KAOLINITE_CLAY_GRASS:
                case SANDY_KAOLINITE_CLAY_PODZOL:
                    return SANDY_KAOLINITE_CLAY;
                case DRY_LOAM_GRASS:
                case LOAM_GRASS:
                case LOAM_PODZOL:
                    return LOAM;
                case DRY_CLAY_LOAM_GRASS:
                case CLAY_LOAM_GRASS:
                case CLAY_LOAM_PODZOL:
                    return CLAY_LOAM;
                case DRY_KAOLINITE_CLAY_LOAM_GRASS:
                case KAOLINITE_CLAY_LOAM_GRASS:
                case KAOLINITE_CLAY_LOAM_PODZOL:
                    return KAOLINITE_CLAY_LOAM;
                case DRY_SILTY_CLAY_GRASS:
                case SILTY_CLAY_GRASS:
                case SILTY_CLAY_PODZOL:
                    return SILTY_CLAY;
                case DRY_SILTY_KAOLINITE_CLAY_GRASS:
                case SILTY_KAOLINITE_CLAY_GRASS:
                case SILTY_KAOLINITE_CLAY_PODZOL:
                    return SILTY_KAOLINITE_CLAY;
                case DRY_SILTY_CLAY_LOAM_GRASS:
                case SILTY_CLAY_LOAM_GRASS:
                case SILTY_CLAY_LOAM_PODZOL:
                    return SILTY_CLAY_LOAM;
                case DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS:
                case SILTY_KAOLINITE_CLAY_LOAM_GRASS:
                case SILTY_KAOLINITE_CLAY_LOAM_PODZOL:
                    return SILTY_KAOLINITE_CLAY_LOAM;
                case DRY_SILT_LOAM_GRASS:
                case SILT_LOAM_GRASS:
                case SILT_LOAM_PODZOL:
                    return SILT_LOAM;
                case DRY_SILT_GRASS:
                case SILT_GRASS:
                case SILT_PODZOL:
                    return SILT;
                case KAOLINITE_CLAY:
                case KAOLINITE_CLAY_GRASS:
                case DRY_KAOLINITE_CLAY_GRASS:
                case KAOLINITE_CLAY_PODZOL:
                    return KAOLINITE_CLAY;
                case HUMUS:
                case HUMUS_GRASS:
                case DRY_HUMUS_GRASS:
                    return HUMUS;
                case CLAY_HUMUS:
                case CLAY_HUMUS_GRASS:
                case DRY_CLAY_HUMUS_GRASS:
                    return CLAY_HUMUS;
                case KAOLINITE_CLAY_HUMUS:
                case KAOLINITE_CLAY_HUMUS_GRASS:
                case DRY_KAOLINITE_CLAY_HUMUS_GRASS:
                    return KAOLINITE_CLAY_HUMUS;
                case DRY_CLAY_GRASS:
                case CLAY_PODZOL:
                case PODZOL:
                    return null; //This is supposed to happen, because it is expected to run getNonGrassVersionTFC after this one
            }
            throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
        }

        public Rock.Type getNonGrassVersionTFC()
        {
            if(!isGrass) return null;
            switch(this)
            {
                case DRY_CLAY_GRASS:
                case CLAY_PODZOL:
                    return Rock.Type.CLAY;
                case PODZOL:
                    return Rock.Type.DIRT;
            }
            throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
        }

        public RockTFCF getGrassVersion(RockTFCF spreader)
        {
            if (!spreader.isGrass) throw new IllegalArgumentException("Non-grass can't spread.");
            switch (this)
            {
                case LOAMY_SAND:
                    return spreader == DRY_LOAMY_SAND_GRASS ? DRY_LOAMY_SAND_GRASS : LOAMY_SAND_GRASS;
                case SANDY_LOAM:
                    return spreader == DRY_SANDY_LOAM_GRASS ? DRY_SANDY_LOAM_GRASS : SANDY_LOAM_GRASS;
                case SANDY_CLAY_LOAM:
                    return spreader == DRY_SANDY_CLAY_LOAM_GRASS ? DRY_SANDY_CLAY_LOAM_GRASS : SANDY_CLAY_LOAM_GRASS;
                case SANDY_CLAY:
                    return spreader == DRY_SANDY_CLAY_GRASS ? DRY_SANDY_CLAY_GRASS : SANDY_CLAY_GRASS;
                case LOAM:
                    return spreader == DRY_LOAM_GRASS ? DRY_LOAM_GRASS : LOAM_GRASS;
                case CLAY_LOAM:
                    return spreader == DRY_CLAY_LOAM_GRASS ? DRY_CLAY_LOAM_GRASS : CLAY_LOAM_GRASS;
                case SILTY_CLAY:
                    return spreader == DRY_SILTY_CLAY_GRASS ? DRY_SILTY_CLAY_GRASS : SILTY_CLAY_GRASS;
                case SILTY_CLAY_LOAM:
                    return spreader == DRY_SILTY_CLAY_LOAM_GRASS ? DRY_SILTY_CLAY_LOAM_GRASS : SILTY_CLAY_LOAM_GRASS;
                case SILT_LOAM:
                    return spreader == DRY_SILT_LOAM_GRASS ? DRY_SILT_LOAM_GRASS : SILT_LOAM_GRASS;
                case SILT:
                    return spreader == DRY_SILT_GRASS ? DRY_SILT_GRASS : SILT_GRASS;
                case HUMUS:
                    return spreader == DRY_HUMUS_GRASS ? DRY_HUMUS_GRASS : HUMUS_GRASS;
                case CLAY_HUMUS:
                    return spreader == DRY_CLAY_HUMUS_GRASS ? DRY_CLAY_HUMUS_GRASS : CLAY_HUMUS_GRASS;

                // Kaolinite Clay
                case KAOLINITE_CLAY:
                    return spreader == DRY_KAOLINITE_CLAY_GRASS ? DRY_KAOLINITE_CLAY_GRASS : KAOLINITE_CLAY_GRASS;
                case SANDY_KAOLINITE_CLAY_LOAM:
                    return spreader == DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS ? DRY_SANDY_KAOLINITE_CLAY_LOAM_GRASS : SANDY_KAOLINITE_CLAY_LOAM_GRASS;
                case SANDY_KAOLINITE_CLAY:
                    return spreader == DRY_SANDY_KAOLINITE_CLAY_GRASS ? DRY_SANDY_KAOLINITE_CLAY_GRASS : SANDY_KAOLINITE_CLAY_GRASS;
                case KAOLINITE_CLAY_LOAM:
                    return spreader == DRY_KAOLINITE_CLAY_LOAM_GRASS ? DRY_KAOLINITE_CLAY_LOAM_GRASS : KAOLINITE_CLAY_LOAM_GRASS;
                case SILTY_KAOLINITE_CLAY:
                    return spreader == DRY_SILTY_KAOLINITE_CLAY_GRASS ? DRY_SILTY_KAOLINITE_CLAY_GRASS : SILTY_KAOLINITE_CLAY_GRASS;
                case SILTY_KAOLINITE_CLAY_LOAM:
                    return spreader == DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS ? DRY_SILTY_KAOLINITE_CLAY_LOAM_GRASS : SILTY_KAOLINITE_CLAY_LOAM_GRASS;
                case KAOLINITE_CLAY_HUMUS:
                    return spreader == DRY_KAOLINITE_CLAY_HUMUS_GRASS ? DRY_KAOLINITE_CLAY_HUMUS_GRASS : KAOLINITE_CLAY_HUMUS_GRASS;
            }
            throw new IllegalArgumentException("You cannot get grass from rock types.");
        }
    }

    public enum FallingBlockType
    {
        NO_FALL,
        FALL_VERTICAL,
        FALL_HORIZONTAL
    }
}
