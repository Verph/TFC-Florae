package tfcelementia;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.GameRuleChangeEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.Constants;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.damage.CapabilityDamageResistance;
import net.dries007.tfc.api.capability.damage.DamageType;
import net.dries007.tfc.api.capability.egg.CapabilityEgg;
import net.dries007.tfc.api.capability.egg.EggHandler;
import net.dries007.tfc.api.capability.food.FoodData;
import net.dries007.tfc.api.capability.food.*;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.ForgeableHeatableHandler;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.player.IPlayerData;
import net.dries007.tfc.api.capability.player.PlayerDataHandler;
import net.dries007.tfc.api.capability.size.CapabilityItemSize;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.ICreatureTFC;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.network.PacketCalendarUpdate;
import net.dries007.tfc.network.PacketFoodStatsReplace;
import net.dries007.tfc.network.PacketPlayerDataUpdate;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.devices.BlockQuern;
import net.dries007.tfc.objects.blocks.metal.BlockAnvilTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockRaw;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.blocks.stone.BlockStoneAnvil;
import net.dries007.tfc.objects.container.CapabilityContainerListener;
import net.dries007.tfc.objects.potioneffects.PotionEffectsTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.CalendarWorldData;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.util.skills.SmithingSkill;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcelementia.objects.blocks.BlocksTFCE;
import tfcelementia.objects.items.food.ItemFoodTFCE;
import tfcelementia.api.capability.food.*;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;


@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
public final class CommonEventHandlerTFCE
{
	// Maybe when adding podzol?
	/*
    @SubscribeEvent
    public static void onUseHoe(UseHoeEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);

        if (state.getBlock() instanceof BlockRockVariant)
        {
            BlockRockVariant blockRock = (BlockRockVariant) state.getBlock();
            if (blockRock.getType() == Rock.Type.GRASS || blockRock.getType() == Rock.Type.DIRT)
            {
                if (!world.isRemote)
                {
                    world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.setBlockState(pos, BlockRockVariant.get(blockRock.getRock(), Rock.Type.FARMLAND).getDefaultState());
                }
                event.setResult(Event.Result.ALLOW);
            }
        }
    }*/
	
    @SubscribeEvent
    public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event)
    {
        ItemStack stack = event.getObject();
        Item item = stack.getItem();
        if (!stack.isEmpty())
        {
            // Size
            if (CapabilityItemSize.getIItemSize(stack) == null)
            {
                ICapabilityProvider sizeHandler = CapabilityItemSize.getCustomSize(stack);
                event.addCapability(CapabilityItemSize.KEY, sizeHandler);
                if (sizeHandler instanceof IItemSize)
                {
                    // Only modify the stack size if the item was stackable in the first place
                    // Note: this is called in many cases BEFORE all custom capabilities are added.
                    int prevStackSize = stack.getMaxStackSize();
                    if (prevStackSize != 1)
                    {
                        item.setMaxStackSize(((IItemSize) sizeHandler).getStackSize(stack));
                    }
                }
            }

            // Food
            if (stack.getItem() instanceof ItemFoodTFCE)
            {
                ICapabilityProvider foodHandler = CapabilityFood.getCustomFood(stack);
                if (foodHandler != null)
                {
                    event.addCapability(CapabilityFood.KEY, foodHandler);
                }
                else
                {
                    foodHandler = new FoodHandlerTFCE(stack.getTagCompound(), new FoodData());
                    event.addCapability(CapabilityFood.KEY, foodHandler);
                }
            }
            
            /*
            // Forge / Metal / Heat. Try forge first, because it's more specific
            ICapabilityProvider forgeHandler = CapabilityForgeable.getCustomForgeable(stack);
            boolean isForgeable = false;
            boolean isHeatable = false;
            if (forgeHandler != null)
            {
                isForgeable = true;
                event.addCapability(CapabilityForgeable.KEY, forgeHandler);
                isHeatable = forgeHandler instanceof IItemHeat;
            }
            // Metal
            ICapabilityProvider metalCapability = CapabilityMetalItem.getCustomMetalItem(stack);
            if (metalCapability != null)
            {
                event.addCapability(CapabilityMetalItem.KEY, metalCapability);
                if (!isForgeable)
                {
                    // Add a forgeable capability for this item, if none is found
                    IMetalItem cap = (IMetalItem) metalCapability;
                    Metal metal = cap.getMetal(stack);
                    if (metal != null)
                    {
                        event.addCapability(CapabilityForgeable.KEY, new ForgeableHeatableHandler(null, metal.getSpecificHeat(), metal.getMeltTemp()));
                        isHeatable = true;
                    }
                }
            }
            // If one of the above is also heatable, skip this
            if (!isHeatable)
            {
                ICapabilityProvider heatHandler = CapabilityItemHeat.getCustomHeat(stack);
                if (heatHandler != null)
                {
                    event.addCapability(CapabilityItemHeat.KEY, heatHandler);
                }
            }

            // Armor
            if (item instanceof ItemArmor)
            {
                ICapabilityProvider damageResistance = CapabilityDamageResistance.getCustomDamageResistance(stack);
                if (damageResistance != null)
                {
                    event.addCapability(CapabilityDamageResistance.KEY, damageResistance);
                }
            }

            // Eggs
            if (stack.getItem() == Items.EGG)
            {
                event.addCapability(CapabilityEgg.KEY, new EggHandler());
            }
            */
        }
    }
}