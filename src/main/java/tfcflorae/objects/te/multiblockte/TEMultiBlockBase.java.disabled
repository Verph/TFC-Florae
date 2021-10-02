package tfcflorae.objects.te.multiblockte;

import java.util.Objects;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import tfcflorae.objects.blocks.multiblock.MultiBlockBase;
import tfcflorae.objects.blocks.multiblock.MultiBlockController;

public abstract class TEMultiBlockBase extends TileEntity implements TEMultiBlockInterface
{
    private final MultiBlockController block;
    private final Block dummy;

    public TEMultiBlockBase(MultiBlockController block, Block dummy)
	{
		this.block = block;
		this.dummy = dummy;
	}

	private boolean doesControllerOwn(BlockPos pos)
	{
		TileEntity entity = this.world.getTileEntity(pos);

		if (entity instanceof TEMultiBlockBase)
		{
			return true;
		}
		else if (entity instanceof TEMultiBlockDummy)
		{
			TEMultiBlockDummy dummy = (TEMultiBlockDummy) entity;

			return Objects.equals(dummy.getLinkedController(), this.pos);
		}

		return false;
	}

    public void sendUpdatesToClients()
	{
		IBlockState state = this.world.getBlockState(this.pos);

		this.world.notifyBlockUpdate(this.pos, state, state, 3);

		this.markDirty();
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		NBTTagCompound tag = super.getUpdateTag();

		this.writeToNBT(tag);

		return tag;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound compound = this.getUpdateTag();

		return new SPacketUpdateTileEntity(this.pos, 1, compound);
	}

	@Override
	public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.getNbtCompound());
	}
    
	@Override
	public void onDestroyed()
	{
		for (BlockPos.MutableBlockPos pos : this.block.getMultiblockVolumeIterator(this.pos, this.getWorld()))
		{
			if (this.doesControllerOwn(pos))
			{
				this.world.removeTileEntity(pos);
				this.world.setBlockToAir(pos);
			}
		}
	}

    public void rebuild()
	{
		for (BlockPos.MutableBlockPos pos : this.block.getMultiblockVolumeIterator(this.pos, this.getWorld()))
		{
			if (this.pos.equals(pos))
			{
				continue;
			}

			this.world.setBlockState(pos, this.dummy.getDefaultState(), 3);
			this.world.notifyBlockUpdate(pos, Blocks.AIR.getDefaultState(), this.dummy.getDefaultState(), 2);

			TEMultiBlockDummy te = (TEMultiBlockDummy) this.world.getTileEntity(pos);
			te.linkController(new BlockPos(this.pos));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		Iterable<BlockPos.MutableBlockPos> itPos = this.block.getMultiblockVolumeIterator(this.pos, this.world);

		BlockPos min = this.pos;
		BlockPos max = this.pos;

		for (BlockPos.MutableBlockPos pos : itPos)
		{
			max = pos;
		}

		return new AxisAlignedBB(min, max.add(1, 1, 1));
	}
}
