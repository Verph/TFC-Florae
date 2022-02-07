package tfcflorae.objects.items.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.damage.DamageType;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import tfcflorae.util.OreDictionaryHelper;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemWalkingStick extends ItemHoe implements IItemSize
{
	public final static UUID MOVEMENT_SPEED_MODIFIER = UUID.fromString("dcd2b961-181e-4141-a891-8c1958670b8c");
    public final ToolMaterial material;

    protected float attackDamage;
	protected float attackSpeed;
    protected float movementSpeed;

    public ItemWalkingStick(ToolMaterial material, float AttackDamage, float AttackSpeed, float MovementSpeed, int Durability, Object... oreNameParts)
    {
	    super(material);
	    this.material = material;
	    this.attackDamage = AttackDamage;
	    this.attackSpeed = AttackSpeed;
	    this.movementSpeed = MovementSpeed;
        this.setMaxDamage(Durability);
        this.setHarvestLevel("hoe", material.getHarvestLevel());

        for (Object obj : oreNameParts)
        {
            if (obj instanceof Object[])
                OreDictionaryHelper.register(this, (Object[]) obj);
            else
                OreDictionaryHelper.register(this, obj);
        }
        OreDictionaryHelper.registerDamageType(this, DamageType.PIERCING);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -3, 0));
            multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(MOVEMENT_SPEED_MODIFIER, "Tool modifier", movementSpeed, 0));
        }
        return multimap;
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack)
    {
        return Size.LARGE; // Stored only in chests
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack)
    {
        return Weight.LIGHT;
    }

    @Override
    public boolean canStack(ItemStack stack)
    {
        return false;
    }
}