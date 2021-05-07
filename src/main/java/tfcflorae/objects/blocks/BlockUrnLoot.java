package tfcflorae.objects.blocks;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.eerussianguy.firmalife.registry.ItemsFL;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import hu.lebeg134.tfc_ph_compat.objects.items.ItemsTPC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tfcflorae.TFCFlorae;
import tfcflorae.objects.items.ItemsTFCF;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Metal.ItemType;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.items.ItemPowder;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.metal.ItemMetal;

@ParametersAreNonnullByDefault
public class BlockUrnLoot extends Block implements IItemSize
{
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1D, 0.875D);

    private static ImmutableList<Item> seedsList = null;
    private static ImmutableList<Metal> metalList = null;

    public BlockUrnLoot()
    {
        super(Material.CIRCUITS);
        setSoundType(SoundType.GLASS);
        setHardness(1.5F);
    }

    private static ImmutableList<Item> getSeeds()
    {
        if (seedsList == null)
        {
            Builder<Item> buildSeeds = ImmutableList.builder();
            for (Item item : ItemsTFC.getAllSimpleItems())
                if (item.getRegistryName().getPath().contains("seeds/"))
                    buildSeeds.add(item);
            for (Item item : ItemsTFCF.getAllSimpleItems())
                if (item.getRegistryName().getPath().contains("seeds/"))
                    buildSeeds.add(item);
                    
            if (TFCFlorae.FirmaLifeAdded)
            {
                for (Item item : ItemsFL.getAllEasyItems())
                    if (item.getRegistryName().getPath().contains("seeds/"))
                        buildSeeds.add(item);
            }
            if (TFCFlorae.TFCPHCompatAdded)
            {
                for (Item item : ItemsTPC.getAllSimpleItems())
                    if (item.getRegistryName().getPath().contains("seeds/"))
                        buildSeeds.add(item);
            }

            seedsList = buildSeeds.build();
        }

        return seedsList;
    }

    private static Metal getRandomMetal(Random rand)
    {
        if (metalList == null)
        {
            Builder<Metal> buildMetal = ImmutableList.builder();
            for(Metal metal : TFCRegistries.METALS.getValuesCollection())
                if (metal.getTier().isAtMost(Metal.Tier.TIER_III))
                    buildMetal.add(metal);

            metalList = buildMetal.build();
        }

        return metalList.get(rand.nextInt(metalList.size()));
    }

    public Item dropOre(Random rand)
    {
        int length = ItemsTFC.getAllOreItems().size();
        int drop = rand.nextInt(length);

        return ItemsTFC.getAllOreItems().get(drop);
    }
    
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

        int chance = rand.nextInt(2);

        if (chance == 0)
        {
            int count = rand.nextInt(2) + 2;
            for (int i = 0; i < count; i++)
            {
                Item item = dropOre(rand);
                int amount = rand.nextInt(5) + 2;

                drops.add(new ItemStack(item, amount, this.damageDropped(state)));
            }

            if (rand.nextInt(4) == 0)
            {
                count = rand.nextInt(2) + 1;
                for (int i = 0; i < count; i++)
                {
                    int amount = rand.nextInt(2) + 1;
                    drops.add(new ItemStack(ItemMetal.get(getRandomMetal(rand), ItemType.INGOT), amount, this.damageDropped(state)));
                }
            }
        }
        else
        {
            int count = rand.nextInt(2) + 1;
            for (int i = 0; i < count; i++)
            {
                ImmutableList<Item> seeds = getSeeds();
                int seedIndex = rand.nextInt(seeds.size());
                int amount = rand.nextInt(5) + 2;

                drops.add(new ItemStack(seeds.get(seedIndex), amount, this.damageDropped(state)));
            }

            count = rand.nextInt(2) + 1;
            for (int i = 0; i < count; i++)
            {
                Item[] dropList = {ItemsTFCF.MADDER, ItemsTFCF.WELD, ItemsTFCF.WOAD, ItemsTFCF.INDIGO, ItemsTFCF.RAPE, ItemsTFCF.HOPS, ItemsTFCF.FLAX, ItemsTFCF.LINEN_STRING, ItemsTFCF.COTTON_BOLL, ItemsTFCF.COTTON_YARN, ItemsTFCF.AGAVE, ItemsTFCF.SISAL_STRING, ItemsTFCF.PAPYRUS_FIBER, ItemsTFC.JUTE, ItemsTFC.JUTE_FIBER, ItemsTFC.SALT, ItemsTFC.MORTAR, ItemsTFC.FIRE_CLAY, Items.CLAY_BALL, ItemsTFC.WOOL, ItemsTFC.WOOL_YARN, ItemPowder.get(Powder.KAOLINITE), ItemPowder.get(Powder.GRAPHITE), ItemPowder.get(Powder.FLUX), ItemPowder.get(Powder.SALTPETER), ItemPowder.get(Powder.LAPIS_LAZULI), ItemPowder.get(Powder.SULFUR), ItemPowder.get(Powder.SALT)};
                int dropIndex = rand.nextInt(dropList.length);
                int amount = rand.nextInt(5) + 2;

                drops.add(new ItemStack(dropList[dropIndex], amount, this.damageDropped(state)));
            }
        }
    }

    @Override
    @Nonnull
    public Size getSize(ItemStack stack)
    {
        return Size.HUGE;
    }

    @Override
    @Nonnull
    public Weight getWeight(ItemStack stack)
    {
        return Weight.VERY_HEAVY; // Stacksize = 1
    }

    @Override
    public boolean canStack(@Nonnull ItemStack stack)
    {
        return stack.getTagCompound() == null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isTopSolid(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isBlockNormalCube(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isNormalCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB.offset(state.getOffset(source, pos));
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!canStay(world, pos))
        {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return canStay(world, pos);
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return false;
    }

    private boolean canStay(IBlockAccess world, BlockPos pos)
    {
        return world.getBlockState(pos.down()).getBlockFaceShape(world, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
    }
}