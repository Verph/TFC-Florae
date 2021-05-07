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
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeTrunk;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.container.CapabilityContainerListener;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.skills.SmithingSkill;

import tfcflorae.objects.items.ItemMiscTFCF;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.objects.blocks.blocktype.BlockRockVariantTFCF;
import tfcflorae.objects.blocks.wood.bamboo.BlockBambooLeaves;
import tfcflorae.objects.blocks.wood.cinnamon.BlockCassiaCinnamonLeaves;
import tfcflorae.objects.blocks.wood.cinnamon.BlockCeylonCinnamonLeaves;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.types.BlockTypesTFCF.RockTFCF;

import static tfcflorae.TFCFlorae.MODID;

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
    public static void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event)
    {
        final EntityPlayer player = event.getHarvester();
        final ItemStack held = player == null ? ItemStack.EMPTY : player.getHeldItemMainhand();
        final IBlockState state = event.getState();
        final Block block = state.getBlock();
        final Month month = CalendarTFC.CALENDAR_TIME.getMonthOfYear();

        if (block == BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_BARLEY)) && (month == Month.JUNE || month == Month.JULY || month == Month.AUGUST || month == Month.SEPTEMBER))
        {
            event.getDrops().clear();
            int chance = Constants.RNG.nextInt(3);

            switch(chance) {
                case 0:
                    event.getDrops().add(new ItemStack(ItemsTFCF.WILD_BARLEY, 1 + Constants.RNG.nextInt(2)));
                    event.getDrops().add(new ItemStack(ItemSeedsTFC.get(Crop.BARLEY), 1 + Constants.RNG.nextInt(2)));
                break;
                case 1:
                    event.getDrops().add(new ItemStack(ItemsTFCF.WILD_BARLEY, 1 + Constants.RNG.nextInt(2)));
                break;
                case 2:
                    event.getDrops().add(new ItemStack(ItemSeedsTFC.get(Crop.BARLEY), 1 + Constants.RNG.nextInt(2)));
                break;
            }
        }
        if (block == BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_RICE)) && (month == Month.JUNE || month == Month.JULY || month == Month.AUGUST || month == Month.SEPTEMBER))
        {
            event.getDrops().clear();
            int chance = Constants.RNG.nextInt(3);

            switch(chance) {
                case 0:
                    event.getDrops().add(new ItemStack(ItemsTFCF.WILD_RICE, 1 + Constants.RNG.nextInt(2)));
                    event.getDrops().add(new ItemStack(ItemSeedsTFC.get(Crop.RICE), 1 + Constants.RNG.nextInt(2)));
                break;
                case 1:
                    event.getDrops().add(new ItemStack(ItemsTFCF.WILD_RICE, 1 + Constants.RNG.nextInt(2)));
                break;
                case 2:
                    event.getDrops().add(new ItemStack(ItemSeedsTFC.get(Crop.RICE), 1 + Constants.RNG.nextInt(2)));
                break;
            }
        }
        if (block == BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WILD_WHEAT)) && (month == Month.JUNE || month == Month.JULY || month == Month.AUGUST || month == Month.SEPTEMBER))
        {
            event.getDrops().clear();
            int chance = Constants.RNG.nextInt(3);

            switch(chance) {
                case 0:
                    event.getDrops().add(new ItemStack(ItemsTFCF.WILD_WHEAT, 1 + Constants.RNG.nextInt(2)));
                    event.getDrops().add(new ItemStack(ItemSeedsTFC.get(Crop.WHEAT), 1 + Constants.RNG.nextInt(2)));
                break;
                case 1:
                    event.getDrops().add(new ItemStack(ItemsTFCF.WILD_WHEAT, 1 + Constants.RNG.nextInt(2)));
                break;
                case 2:
                    event.getDrops().add(new ItemStack(ItemSeedsTFC.get(Crop.WHEAT), 1 + Constants.RNG.nextInt(2)));
                break;
            }
        }

        if (TFCFlorae.FirmaLifeAdded)
        {
            /*if (block == BlocksFL.MELON_FRUIT)
            {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(ItemsFL.getFood(FoodFL.MELON), 2 + Constants.RNG.nextInt(5)));
            }*/
            if (block instanceof BlockCassiaCinnamonLeaves || block instanceof BlockCeylonCinnamonLeaves || block instanceof BlockBambooLeaves)
            {
                event.getDrops().add(new ItemStack(ItemsFL.FRUIT_LEAF, 2 + Constants.RNG.nextInt(4)));
            }
            /*else if (block instanceof BlockFruitTreeTrunk)
            {
                IFruitTree tree = ((BlockFruitTreeTrunk) block).getTree();
                String poleName = MODID + "wood/fruit_tree/pole" + tree.getName().toLowerCase();
                Item pole = ItemMiscTFCF.getByNameOrId(poleName);
                if (pole != null)
                    event.getDrops().add(new ItemStack(pole));
            }*/
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
                    if (!world.isRemote)
                    {
                        switch(blockRock.getType()) {
                            case RockTFCF.PODZOL:
                            case RockTFCF.SPARSE_GRASS:
                                world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                world.setBlockState(pos, BlockRockVariant.get(blockRock.getRock(), Rock.Type.FARMLAND).getDefaultState());
                            break;
                            case RockTFCF.COARSE_DIRT:
                                world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                world.setBlockState(pos, BlockRockVariant.get(blockRock.getRock(), Rock.Type.DIRT).getDefaultState());
                            break;
                            case RockTFCF.COARSE_DIRT:
                                world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                world.setBlockState(pos, BlockRockVariant.get(blockRock.getRock(), Rock.Type.DIRT).getDefaultState());
                            break;
                        }
                        if (ConfigTFCF.General.WORLD.enableAllSpecialSoil) {
                            if (ConfigTFCF.General.WORLD.enableAllFarmland) {
                                switch(blockRock.getType()) {
                                    case RockTFCF.LOAMY_SAND:
                                    case RockTFCF.LOAMY_SAND_GRASS:
                                    case RockTFCF.LOAMY_SAND_PODZOL:
                                    case RockTFCF.DRY_LOAMY_SAND_GRASS:
                                    case RockTFCF.SPARSE_LOAMY_SAND_GRASS:
                                        world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.LOAMY_SAND_FARMLAND).getDefaultState());
                                    break;
                                    
                                    case RockTFCF.SANDY_LOAM:
                                    case RockTFCF.SANDY_LOAM_GRASS:
                                    case RockTFCF.SANDY_LOAM_PODZOL:
                                    case RockTFCF.DRY_SANDY_LOAM_GRASS:
                                    case RockTFCF.SPARSE_SANDY_LOAM_GRASS:
                                        world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SANDY_LOAM_FARMLAND).getDefaultState());
                                    break;
    
                                    case RockTFCF.LOAM:
                                    case RockTFCF.LOAM_GRASS:
                                    case RockTFCF.LOAM_PODZOL:
                                    case RockTFCF.DRY_LOAM_GRAS:
                                    case RockTFCF.SPARSE_LOAM_GRASS:
                                        world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.LOAM_FARMLAND).getDefaultState());
                                    break;
    
                                    case RockTFCF.SILT_LOAM:
                                    case RockTFCF.SILT_LOAM_GRASS:
                                    case RockTFCF.SILT_LOAM_PODZOL:
                                    case RockTFCF.DRY_SILT_LOAM_GRASS:
                                    case RockTFCF.SPARSE_SILT_LOAM_GRASS:
                                        world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SILT_LOAM_FARMLAND).getDefaultState());
                                    break;
    
                                    case RockTFCF.SILT:
                                    case RockTFCF.SILT_GRASS:
                                    case RockTFCF.SILT_PODZOL:
                                    case RockTFCF.DRY_SILT_GRASS:
                                    case RockTFCF.SPARSE_SILT_GRASS:
                                        world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SILT_FARMLAND).getDefaultState());
                                    break;
    
                                    case RockTFCF.HUMUS:
                                    case RockTFCF.HUMUS_GRASS:
                                    case RockTFCF.DRY_HUMUS_GRAS:
                                    case RockTFCF.SPARSE_HUMUS_GRASS:
                                        world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                        world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.HUMUS_FARMLAND).getDefaultState());
                                    break;
                                }
                            }
                            switch(blockRock.getType()) {
                                case RockTFCF.COARSE_LOAMY_SAND:
                                    world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.LOAMY_SAND).getDefaultState());
                                break;

                                case RockTFCF.COARSE_SANDY_LOAM:
                                    world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SANDY_LOAM).getDefaultState());
                                break;

                                case RockTFCF.COARSE_LOAM:
                                    world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.LOAM).getDefaultState());
                                break;

                                case RockTFCF.COARSE_SILT_LOAM:
                                    world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SILT_LOAM).getDefaultState());
                                break;

                                case RockTFCF.COARSE_SILT:
                                    world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.SILT).getDefaultState());
                                break;

                                case RockTFCF.COARSE_HUMUS:
                                    world.playSound(null, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                    world.setBlockState(pos, BlockRockVariantTFCF.get(blockRock.getRock(), RockTFCF.HUMUS).getDefaultState());
                                break;
                            }
                        }
                    }
                    event.setResult(Event.Result.ALLOW);
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