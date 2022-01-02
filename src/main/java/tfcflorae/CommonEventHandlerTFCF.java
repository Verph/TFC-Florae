package tfcflorae;

import com.eerussianguy.firmalife.init.FoodFL;
import com.eerussianguy.firmalife.registry.BlocksFL;
import com.eerussianguy.firmalife.registry.ItemsFL;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.capability.food.*;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.*;
import net.dries007.tfc.api.types.IAnimalTFC.Age;
import net.dries007.tfc.api.types.Plant.PlantType;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeTrunk;
import net.dries007.tfc.objects.blocks.plants.*;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.container.CapabilityContainerListener;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.skills.SmithingSkill;

import tfcflorae.objects.items.ItemMiscTFCF;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.client.GuiHandler;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.objects.blocks.wood.bamboo.BlockBambooLeaves;
import tfcflorae.objects.blocks.wood.cinnamon.BlockCassiaCinnamonLeaves;
import tfcflorae.objects.blocks.wood.cinnamon.BlockCeylonCinnamonLeaves;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;
import tfcflorae.util.OreDictionaryHelper;
import tfcflorae.util.agriculture.CropTFCF;

import static tfcflorae.TFCFlorae.MODID;

import java.util.List;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public final class CommonEventHandlerTFCF
{
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    @SubscribeEvent
    public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event)
    {
        ItemStack stack = event.getObject();
        Item item = stack.getItem();
        if (!stack.isEmpty());
    }

    @SubscribeEvent
    public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event)
    {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = event.getState();
        Block block = state.getBlock();
        Month month = CalendarTFC.CALENDAR_TIME.getMonthOfYear();

        for (Plant plant : TFCRegistries.PLANTS.getValuesCollection())
        {
            if (plant == TFCRegistries.PLANTS.getValue(DefaultPlants.BARREL_CACTUS) && (month == Month.SEPTEMBER || month == Month.OCTOBER || month == Month.NOVEMBER))
            {
                int chance = Constants.RNG.nextInt(2);
                if (chance == 0)
                {
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(ItemsTFCF.BARREL_CACTUS_FRUIT, 1 + Constants.RNG.nextInt(3)));
                }
            }
        }
        if (TFCFlorae.FirmaLifeAdded)
        {
            EntityPlayer playerHarvest = event.getHarvester();
            ItemStack held = playerHarvest == null ? ItemStack.EMPTY : playerHarvest.getHeldItemMainhand();

            if (block instanceof BlockCassiaCinnamonLeaves || block instanceof BlockCeylonCinnamonLeaves || block instanceof BlockBambooLeaves)
            {
                event.getDrops().add(new ItemStack(ItemsFL.FRUIT_LEAF, 2 + Constants.RNG.nextInt(4)));
            }
            if (block == BlocksFL.MELON_FRUIT && (held.getItem().getHarvestLevel(held, "knife", playerHarvest, state) != -1))
            {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(ItemsFL.getFood(FoodFL.MELON), 2 + Constants.RNG.nextInt(4)));
            }
        }
    }

    @SubscribeEvent
    public static void onBreakProgressEvent(BreakSpeed event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if (player != null)
        {
            ItemStack stack = player.getHeldItemMainhand();
            float skillModifier = SmithingSkill.getSkillBonus(stack, SmithingSkill.Type.TOOLS);
            if (skillModifier > 0)
            {
                // Up to 2x modifier for break speed for skill bonuses on tools
                // New speed, so it will take into account other mods' modifications
                event.setNewSpeed(event.getNewSpeed() + (event.getNewSpeed() * skillModifier));
            }
        }
        if (event.getState().getBlock() instanceof BlockRockVariantTFCF)
        {
            event.setNewSpeed((float) (event.getNewSpeed() / ConfigTFC.General.MISC.rockMiningTimeModifier));
        }
    }

    @SubscribeEvent
    public static void onUseHoe(UseHoeEvent event)
    {
        if (ConfigTFCF.General.WORLD.enableAllBlockTypes)
        {
            World world = event.getWorld();
            BlockPos pos = event.getPos();
            IBlockState state = world.getBlockState(pos);

            if (ConfigTFC.General.OVERRIDES.enableHoeing)
            {
                if (state.getBlock() instanceof BlockRockVariantTFCF)
                {
                    BlockRockVariantTFCF blockRock = (BlockRockVariantTFCF) state.getBlock();
                    if 
                        (
                            blockRock.getType() == RockTFCF.PODZOL ||
                            blockRock.getType() == RockTFCF.SPARSE_GRASS
                        )
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariant.get(blockRock.getRock(), Rock.Type.FARMLAND).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                    else if 
                        (
                            blockRock.getType() == RockTFCF.COARSE_DIRT
                        )
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariant.get(blockRock.getRock(), Rock.Type.DIRT).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                    else if (ConfigTFCF.General.WORLD.enableAllFarmland && ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                        (
                            blockRock.getType() == RockTFCF.LOAMY_SAND || 
                            blockRock.getType() == RockTFCF.LOAMY_SAND_GRASS || 
                            blockRock.getType() == RockTFCF.LOAMY_SAND_PODZOL || 
                            blockRock.getType() == RockTFCF.DRY_LOAMY_SAND_GRASS || 
                            blockRock.getType() == RockTFCF.SPARSE_LOAMY_SAND_GRASS
                        ))
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.LOAMY_SAND_FARMLAND).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                    else if (ConfigTFCF.General.WORLD.enableAllFarmland && ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                        (
                            blockRock.getType() == RockTFCF.SANDY_LOAM || 
                            blockRock.getType() == RockTFCF.SANDY_LOAM_GRASS || 
                            blockRock.getType() == RockTFCF.SANDY_LOAM_PODZOL || 
                            blockRock.getType() == RockTFCF.DRY_SANDY_LOAM_GRASS || 
                            blockRock.getType() == RockTFCF.SPARSE_SANDY_LOAM_GRASS
                        ))
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SANDY_LOAM_FARMLAND).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                    else if (ConfigTFCF.General.WORLD.enableAllFarmland && ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                        (
                            blockRock.getType() == RockTFCF.LOAM || 
                            blockRock.getType() == RockTFCF.LOAM_GRASS || 
                            blockRock.getType() == RockTFCF.LOAM_PODZOL || 
                            blockRock.getType() == RockTFCF.DRY_LOAM_GRASS || 
                            blockRock.getType() == RockTFCF.SPARSE_LOAM_GRASS
                        ))
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.LOAM_FARMLAND).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                    else if (ConfigTFCF.General.WORLD.enableAllFarmland && ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                        (
                            blockRock.getType() == RockTFCF.SILT_LOAM || 
                            blockRock.getType() == RockTFCF.SILT_LOAM_GRASS || 
                            blockRock.getType() == RockTFCF.SILT_LOAM_PODZOL || 
                            blockRock.getType() == RockTFCF.DRY_SILT_LOAM_GRASS || 
                            blockRock.getType() == RockTFCF.SPARSE_SILT_LOAM_GRASS
                        ))
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SILT_LOAM_FARMLAND).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                    else if (ConfigTFCF.General.WORLD.enableAllFarmland && ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                        (
                            blockRock.getType() == RockTFCF.SILT || 
                            blockRock.getType() == RockTFCF.SILT_GRASS || 
                            blockRock.getType() == RockTFCF.SILT_PODZOL || 
                            blockRock.getType() == RockTFCF.DRY_SILT_GRASS || 
                            blockRock.getType() == RockTFCF.SPARSE_SILT_GRASS
                        ))
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SILT_FARMLAND).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                    else if (ConfigTFCF.General.WORLD.enableAllFarmland && ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                        (
                            blockRock.getType() == RockTFCF.HUMUS || 
                            blockRock.getType() == RockTFCF.HUMUS_GRASS || 
                            blockRock.getType() == RockTFCF.DRY_HUMUS_GRASS || 
                            blockRock.getType() == RockTFCF.SPARSE_HUMUS_GRASS
                        ))
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.HUMUS_FARMLAND).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                    else if (ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                        (
                            blockRock.getType() == RockTFCF.COARSE_LOAMY_SAND
                        ))
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.LOAMY_SAND).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                    else if (ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                        (
                            blockRock.getType() == RockTFCF.COARSE_SANDY_LOAM
                        ))
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SANDY_LOAM).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                    else if (ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                        (
                            blockRock.getType() == RockTFCF.COARSE_LOAM
                        ))
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.LOAM).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                    else if (ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                        (
                            blockRock.getType() == RockTFCF.COARSE_SILT_LOAM
                        ))
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SILT_LOAM).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                    else if (ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                        (
                            blockRock.getType() == RockTFCF.COARSE_SILT
                        ))
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SILT).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                    else if (ConfigTFCF.General.WORLD.enableAllSpecialSoil &&
                        (
                            blockRock.getType() == RockTFCF.COARSE_HUMUS
                        ))
                    {
                        if (!world.isRemote)
                        {
                            world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.HUMUS).getDefaultState());
                        }
                        event.setResult(Event.Result.ALLOW);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onContainerOpen(PlayerContainerEvent.Open event)
    {
        if (event.getEntityPlayer() instanceof EntityPlayerMP)
        {
            // Capability Sync Handler
            CapabilityContainerListener.addTo(event.getContainer(), (EntityPlayerMP) event.getEntityPlayer());
        }
    }
}