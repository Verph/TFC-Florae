package tfcflorae.objects;

import javax.annotation.Nonnull;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

import net.dries007.tfc.api.types.IArmorMaterialTFC;
import net.dries007.tfc.objects.ArmorMaterialTFC;

import static tfcflorae.TFCFlorae.MODID;

public class ArmorMaterialsTFCF implements IArmorMaterialTFC
{
    public static final IArmorMaterialTFC PINEAPPLE_LEATHER = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("pineapple_leather", MODID + ":pineapple_leather", 5, new int[] {1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 2, 2, 5);
    public static final IArmorMaterialTFC BURLAP_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("burlap_cloth", MODID + ":burlap_cloth", 5, new int[] {1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
    public static final IArmorMaterialTFC WOOL_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("wool_cloth", MODID + ":wool_cloth", 5, new int[] {1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
    public static final IArmorMaterialTFC SILK_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("silk_cloth", MODID + ":silk_cloth", 5, new int[] {1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
    public static final IArmorMaterialTFC SISAL_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("sisal_cloth", MODID + ":sisal_cloth", 5, new int[] {1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
    public static final IArmorMaterialTFC COTTON_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("cotton_cloth", MODID + ":cotton_cloth", 5, new int[] {1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
    public static final IArmorMaterialTFC LINEN_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("linen_cloth", MODID + ":linen_cloth", 5, new int[] {1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
    public static final IArmorMaterialTFC HEMP_CLOTH = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("hemp_cloth", MODID + ":hemp_cloth", 5, new int[] {1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);
    public static final IArmorMaterialTFC YUCCA_CANVAS = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("yucca_canvas", MODID + ":yucca_canvas", 5, new int[] {1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F), 1.5f, 1.5f, 4);

    private final float piercingRes, slashingRes, crushingRes;
    private final ArmorMaterial material;

    @SuppressWarnings("WeakerAccess")
    public ArmorMaterialsTFCF(ArmorMaterial material, float piercingRes, float slashingRes, float crushingRes)
    {
        this.material = material;
        this.crushingRes = crushingRes;
        this.piercingRes = piercingRes;
        this.slashingRes = slashingRes;
    }

    @Override
    public float getCrushingModifier()
    {
        return crushingRes;
    }

    @Override
    public float getPiercingModifier()
    {
        return piercingRes;
    }

    @Override
    public float getSlashingModifier()
    {
        return slashingRes;
    }

    @Override
    @Nonnull
    public ArmorMaterial getMaterial()
    {
        return material;
    }
}
