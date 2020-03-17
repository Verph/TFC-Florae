package tfcelementia.objects;

import net.minecraft.init.SoundEvents;
import net.minecraftforge.common.util.EnumHelper;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.IArmorMaterialTFC;
import net.dries007.tfc.objects.ArmorMaterialTFC;

public final class ArmorMaterialsTFCE
{
    public static final IArmorMaterialTFC ALUMINIUM = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("tfce_aluminium", TerraFirmaCraft.MOD_ID + ":aluminium", 12, new int[] {1, 3, 4, 1}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 10, 13, 6.25f);
    public static final IArmorMaterialTFC MITHRIL = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("tfce_mithril", TerraFirmaCraft.MOD_ID + ":mithril", 24, new int[] {1, 4, 5, 2}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 14, 15, 10);
    public static final IArmorMaterialTFC NICKEL_SILVER = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("tfce_nickel_silver", TerraFirmaCraft.MOD_ID + ":nickel_silver", 32, new int[] {2, 4, 5, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F), 15, 20, 20);
    public static final IArmorMaterialTFC INVAR = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("tfce_invar", TerraFirmaCraft.MOD_ID + ":invar", 40, new int[] {2, 4, 5, 3}, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F), 15, 20, 20);
    public static final IArmorMaterialTFC COBALT = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("tfce_cobalt", TerraFirmaCraft.MOD_ID + ":cobalt", 40, new int[] {2, 5, 6, 2}, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 25, 30, 16.5f);
    public static final IArmorMaterialTFC MANYULLYN = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("tfce_manyullin", TerraFirmaCraft.MOD_ID + ":manyullin", 55, new int[] {2, 6, 6, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F), 40, 42, 35);
    public static final IArmorMaterialTFC TITANIUM = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("tfce_titanium", TerraFirmaCraft.MOD_ID + ":titanium", 78, new int[] {3, 6, 8, 3}, 17, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F), 65, 57, 60);
    public static final IArmorMaterialTFC OSMIUM = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("tfce_osmium", TerraFirmaCraft.MOD_ID + ":osmium", 102, new int[] {3, 6, 8, 3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 4.0F), 55, 57, 62);
    public static final IArmorMaterialTFC TUNGSTEN = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("tfce_tungsten", TerraFirmaCraft.MOD_ID + ":tungsten", 100, new int[] {3, 6, 8, 3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 4.0F), 65, 65, 65);
    public static final IArmorMaterialTFC TUNGSTEN_STEEL = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("tfce_tungsten_steel", TerraFirmaCraft.MOD_ID + ":tungsten_steel", 120, new int[] {3, 6, 8, 3}, 22, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 8.0F), 70, 70, 70);
    public static final IArmorMaterialTFC STAINLESS_STEEL = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("tfce_stainless_steel", TerraFirmaCraft.MOD_ID + ":stainless_steel", 50, new int[] {2, 5, 6, 2}, 17, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F), 45, 50, 35);
    public static final IArmorMaterialTFC TITANIUM_GOLD = new ArmorMaterialTFC(EnumHelper.addArmorMaterial("tfce_titanium_gold", TerraFirmaCraft.MOD_ID + ":titanium_gold", 150, new int[] {6, 9, 11, 6}, 28, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 12.0F), 120, 122.5f, 120);

}
