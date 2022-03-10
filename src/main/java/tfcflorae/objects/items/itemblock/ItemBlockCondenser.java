package tfcflorae.objects.items.itemblock;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import net.dries007.tfc.objects.fluids.capability.FluidWhitelistHandlerComplex;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;

import tfcflorae.objects.te.TECondenser;
import tfcflorae.util.OreDictionaryHelper;

import static tfcflorae.TFCFlorae.MODID;
import static tfcflorae.objects.te.TECondenser.MAX_FLUID_TEMPERATURE;

public class ItemBlockCondenser extends ItemBlockTFC
{
    private int tankCapacity = 1000;

    public ItemBlockCondenser(Block block)
    {
        super(block);
        OreDictionaryHelper.register(this, "condenser");
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty())
        {
            IFluidHandler barrelCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (barrelCap != null && barrelCap.drain(1, false) == null)
            {
                BlockPos fluidPos = pos.offset(facing); //Since the clicked facing is the block bellow fluids
                IBlockState state = worldIn.getBlockState(fluidPos);
                IFluidHandler handler = FluidUtil.getFluidHandler(worldIn, fluidPos, facing);
                if (handler != null && handler.drain(Fluid.BUCKET_VOLUME, false) != null)
                {
                    //noinspection ConstantConditions
                    Fluid fluid = handler.drain(Fluid.BUCKET_VOLUME, false).getFluid();
                    if (fluid.getTemperature() < MAX_FLUID_TEMPERATURE)
                    {
                        boolean canCreateSources = false; //default
                        if (state.getBlock() instanceof BlockFluidClassic)
                        {
                            BlockFluidClassic fluidblock = (BlockFluidClassic) worldIn.getBlockState(fluidPos).getBlock();
                            canCreateSources = ObfuscationReflectionHelper.getPrivateValue(BlockFluidClassic.class, fluidblock, "canCreateSources");
                        }
                        else if (state.getBlock() instanceof BlockLiquid)
                        {
                            //Fire the event so other mods that prevent infinite water disable this
                            canCreateSources = ForgeEventFactory.canCreateFluidSource(worldIn, fluidPos, state, state.getMaterial() == Material.WATER);
                        }
                        FluidStack fluidStack = handler.drain(Fluid.BUCKET_VOLUME, true);
                        if (canCreateSources && fluidStack != null)
                        {
                            fluidStack.amount = tankCapacity;
                        }
                        barrelCap.fill(fluidStack, true);
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    @Nonnull
    public String getTranslationKey(@Nonnull ItemStack stack)
    {
        IFluidHandler barrelCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        return barrelCap != null && barrelCap.drain(1, false) != null ? super.getTranslationKey() + ".sealed" : super.getTranslationKey();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        IFluidHandler condenserCapability = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (condenserCapability instanceof ItemCondenserFluidHandler && ((ItemCondenserFluidHandler) condenserCapability).getBarrelContents() != null) // Have either fluid or items stored
        {
            FluidStack fluidStack = condenserCapability.drain(Integer.MAX_VALUE, false);
            ItemStackHandler stackHandler = new ItemStackHandler(3);
            //noinspection ConstantConditions
            stackHandler.deserializeNBT(((ItemCondenserFluidHandler) condenserCapability).getBarrelContents().getCompoundTag("inventory"));
            ItemStack inventory = stackHandler.getStackInSlot(TECondenser.SLOT_ITEM);

            if (fluidStack == null || fluidStack.amount == 0)
            {
                if (inventory.isEmpty())
                {
                    tooltip.add(TextFormatting.BLUE + I18n.format(MODID + ".tooltip.condenser_empty"));
                }
                else
                {
                    tooltip.add(TextFormatting.BLUE + I18n.format(MODID + ".tooltip.condenser_item", inventory.getCount(), inventory.getItem().getItemStackDisplayName(inventory)));
                }
            }
            else
            {
                tooltip.add(TextFormatting.BLUE + I18n.format(MODID + ".tooltip.condenser_fluid", fluidStack.amount, fluidStack.getLocalizedName()));

                if (!inventory.isEmpty())
                {
                    tooltip.add(TextFormatting.BLUE + I18n.format(MODID + ".tooltip.condenser_item_in_fluid", inventory.getCount(), inventory.getItem().getItemStackDisplayName(inventory)));
                }
            }
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty())
        {
            IFluidHandler barrelCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if (barrelCap != null && barrelCap.drain(1, false) == null)
            {
                RayTraceResult rayTrace = rayTrace(worldIn, player, true);
                //noinspection ConstantConditions - ray trace can be null
                if (rayTrace != null && rayTrace.typeOfHit == RayTraceResult.Type.BLOCK)
                {
                    BlockPos pos = rayTrace.getBlockPos();
                    IBlockState state = worldIn.getBlockState(pos);
                    IFluidHandler handler = FluidUtil.getFluidHandler(worldIn, pos, rayTrace.sideHit);
                    if (handler != null && handler.drain(Fluid.BUCKET_VOLUME, false) != null)
                    {
                        boolean canCreateSources = false; //default
                        if (state.getBlock() instanceof BlockFluidClassic)
                        {
                            BlockFluidClassic fluidblock = (BlockFluidClassic) worldIn.getBlockState(pos).getBlock();
                            canCreateSources = ObfuscationReflectionHelper.getPrivateValue(BlockFluidClassic.class, fluidblock, "canCreateSources");
                        }
                        else if (state.getBlock() instanceof BlockLiquid)
                        {
                            //Fire the event so other mods that prevent infinite water disable this
                            canCreateSources = ForgeEventFactory.canCreateFluidSource(worldIn, pos, state, state.getMaterial() == Material.WATER);
                        }
                        FluidStack fluidStack = handler.drain(Fluid.BUCKET_VOLUME, true);
                        if (canCreateSources && fluidStack != null)
                        {
                            fluidStack.amount = tankCapacity;
                        }
                        barrelCap.fill(fluidStack, true);
                        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
                    }
                }
            }
        }
        return super.onItemRightClick(worldIn, player, hand);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new ItemCondenserFluidHandler(stack);
    }

    // This is not an item handler, but still saves items from a sealed barrel
    public static class ItemCondenserFluidHandler extends FluidWhitelistHandlerComplex
    {
        protected ItemCondenserFluidHandler(@Nonnull ItemStack container)
        {
            super(container, 1000, new String[] {"fresh_water", "salt_water", "water"});
        }

        @Override
        public boolean canFillFluidType(FluidStack fluid)
        {
            // Also, accept fluids from recipes
            return super.canFillFluidType(fluid);
        }

        /**
         * Set the contents to be saved in this capability
         * This method assume you have at least one content to save
         */
        public void setBarrelContents(@Nullable FluidStack fluidStack, @Nullable NBTTagCompound inventoryTag, @Nullable NBTTagList surplusTag)
        {
            this.fill(fluidStack, true);
            NBTTagCompound nbt = container.getTagCompound();
            if (nbt == null)
            {
                nbt = new NBTTagCompound();
            }
            if (inventoryTag != null)
            {
                nbt.setTag("inventory", inventoryTag);
            }
            if (surplusTag != null)
            {
                nbt.setTag("surplus", surplusTag);
            }
            container.setTagCompound(nbt);
        }

        /**
         * For convenience, this method only serves as a reference to the item tag, which holds all necessary data.
         */
        @Nullable
        public NBTTagCompound getBarrelContents()
        {
            return container.getTagCompound();
        }

        @Override
        protected void setFluid(@Nonnull FluidStack fluid)
        {
            // Update the sealed tick whenever there is an update in the stored fluid
            if (!container.hasTagCompound())
            {
                container.setTagCompound(new NBTTagCompound());
            }
            NBTTagCompound nbt = container.getTagCompound();
            //noinspection ConstantConditions
            container.setTagCompound(nbt);
            super.setFluid(fluid);
        }

        @Override
        protected void setContainerToEmpty()
        {
            if (container.getTagCompound() != null)
            {
                super.setContainerToEmpty();
                // If not holding any items, we can safely clear the item tag
                if (!container.getTagCompound().hasKey("inventory") && !container.getTagCompound().hasKey("surplus"))
                {
                    container.setTagCompound(null);
                }
            }
        }
    }
}
