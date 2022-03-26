package tfcflorae.objects.items.devices;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import tfcflorae.objects.items.ItemTFCF;
import tfcflorae.util.OreDictionaryHelper;

public class ItemFloraDensity extends ItemTFCF
{
    private final Size size;
    private final Weight weight;

    public ItemFloraDensity(Size size, Weight weight, Object... oreNameParts) 
    {
        this(size, weight);
        this.setMaxStackSize(1);

        for (Object obj : oreNameParts)
        {
            if (obj instanceof Object[])
                OreDictionaryHelper.register(this, (Object[]) obj);
            else
                OreDictionaryHelper.register(this, obj);
        }

        this.addPropertyOverride(new ResourceLocation("floraDensity"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            double rotation;
            @SideOnly(Side.CLIENT)
            double rota;
            @SideOnly(Side.CLIENT)
            long lastUpdateTick;
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                boolean flag = entityIn != null;
                Entity entity = (Entity)(flag ? entityIn : stack.getItemFrame());

                if (worldIn == null && entity != null)
                {
                    worldIn = entity.world;
                }

                if (worldIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    double d0;

                    if (worldIn.provider.isSurfaceWorld() && entityIn != null)
                    {
                        BlockPos playerPos = entityIn.getPosition();
                        ChunkDataTFC data = ChunkDataTFC.get(worldIn, playerPos);

                        d0 = (double)data.getFloraDensity();
                    }
                    else
                    {
                        d0 = Math.random();
                    }

                    d0 = this.wobble(worldIn, d0);
                    return (float)d0;
                }
            }
            @SideOnly(Side.CLIENT)
            private double wobble(World worldIn, double value)
            {
                if (worldIn.getTotalWorldTime() != this.lastUpdateTick)
                {
                    this.lastUpdateTick = worldIn.getTotalWorldTime();
                    double d0 = value - this.rotation;
                    d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
                    this.rota += d0 * 0.1D;
                    this.rota *= 0.9D;
                    this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
                }

                return this.rotation;
            }
        });
    }

    public ItemFloraDensity(Size size, Weight weight)
    {
        this.size = size;
        this.weight = weight;
        this.setMaxStackSize(1);

        this.addPropertyOverride(new ResourceLocation("floraDensity"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            double rotation;
            @SideOnly(Side.CLIENT)
            double rota;
            @SideOnly(Side.CLIENT)
            long lastUpdateTick;
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                boolean flag = entityIn != null;
                Entity entity = (Entity)(flag ? entityIn : stack.getItemFrame());

                if (worldIn == null && entity != null)
                {
                    worldIn = entity.world;
                }

                if (worldIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    double d0;

                    if (worldIn.provider.isSurfaceWorld())
                    {
                        d0 = (double)ChunkDataTFC.get(worldIn, entityIn.getPosition()).getFloraDensity();
                    }
                    else
                    {
                        d0 = Math.random();
                    }

                    d0 = this.wobble(worldIn, d0);
                    return (float)d0;
                }
            }
            @SideOnly(Side.CLIENT)
            private double wobble(World worldIn, double value)
            {
                if (worldIn.getTotalWorldTime() != this.lastUpdateTick)
                {
                    this.lastUpdateTick = worldIn.getTotalWorldTime();
                    double d0 = value - this.rotation;
                    d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
                    this.rota += d0 * 0.1D;
                    this.rota *= 0.9D;
                    this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
                }

                return this.rotation;
            }
        });
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack)
    {
        return size;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack)
    {
        return weight;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, @Nonnull EnumHand hand)
    {
        if (player != null)
        {
            BlockPos playerPos = player.getPosition();
            ChunkDataTFC data = ChunkDataTFC.get(worldIn, playerPos);
            float floraDensity = data.getFloraDensity();

            if (player.isSneaking())
                player.sendStatusMessage(new TextComponentString("The flora density at this location is " + (Math.round(floraDensity * 100) / 100)), true);
            else
                player.sendStatusMessage(new TextComponentString("The flora density percentage at this location is " + (((Math.round(floraDensity * 100) / 100) / 1.0F) * 100) + "%"), true);
            return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
        }
        else return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(hand));
    }
}
