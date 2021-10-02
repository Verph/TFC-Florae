package tfcflorae.objects.te.multiblockte;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import tfcflorae.TFCFlorae;

public class TEMultiBlockDummy extends TileEntity implements TEMultiBlockInterface
{
	private BlockPos controllerPosOffset;

	@Override
	public boolean onInteract(final EntityPlayer player)
	{
		BlockPos linked = this.getLinkedController();

		if (linked != null)
		{
			final TileEntity entity = this.world.getTileEntity(linked);

			if (entity instanceof TEMultiBlockBase)
			{
				((TEMultiBlockInterface) entity).onInteract(player);

				return true;
			}
			else
			{
                TFCFlorae.getLog().warn("TFCFlorae: TEMultiBlockDummy at " + this.pos.toString() + ", is missing it's linked controller at "
						+ linked.toString());
			}
		}

		return false;
	}

	@Override
	public void onDestroyed()
	{
		BlockPos linked = this.getLinkedController();

		if (linked == null)
		{
			return;
		}

		final TileEntity entity = this.world.getTileEntity(linked);

		if (entity instanceof TEMultiBlockInterface)
		{
			((TEMultiBlockInterface) entity).onDestroyed();
		}
		else
		{
			TFCFlorae.getLog().warn("TileEntityMultiblockDummy at " + this.pos.toString() + ", is missing it's linked controller at "
					+ this.getLinkedController().toString());
		}
	}

	@Override
	public ItemStack getPickedStack(final World world, final BlockPos pos, final IBlockState state)
	{
		BlockPos linked = this.getLinkedController();

		if (linked == null)
		{
			return ItemStack.EMPTY;
		}

		final TileEntity entity = this.world.getTileEntity(linked);

		if (entity instanceof TEMultiBlockInterface)
		{
			return ((TEMultiBlockInterface) entity).getPickedStack(world, pos, state);
		}
		else
		{
			TFCFlorae.getLog().warn("TileEntityMultiblockDummy at " + this.pos.toString() + ", is missing it's linked controller at "
					+ this.getLinkedController().toString());
		}

		return ItemStack.EMPTY;
	}

	protected void linkController(final BlockPos controllerPos)
	{
		this.controllerPosOffset = controllerPos.add(-this.pos.getX(), -this.pos.getY(), -this.pos.getZ());
	}

    @Nullable
	protected BlockPos getLinkedController()
	{
		if (this.controllerPosOffset == null)
		{
			return null;
		}

		return this.pos.add(this.controllerPosOffset);
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
	public void readFromNBT(final NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		if (compound.hasKey("controller"))
		{
			this.controllerPosOffset = readBlockPos(compound.getCompoundTag("controller"));
		}
		else
		{
			this.invalidate();
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		if (this.controllerPosOffset != null)
		{
			compound.setTag("controller", writeBlockPos(this.controllerPosOffset));
		}

		return compound;
	}

	public static BlockPos readBlockPos(final NBTTagCompound tag)
	{
		if (tag == null || tag.getBoolean("_null") || !tag.hasKey("_null"))
		{
			return null;
		}

		return new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"));
	}

	public static NBTTagCompound writeBlockPos(final BlockPos pos)
	{
		final NBTTagCompound tag = new NBTTagCompound();

		if (pos == null)
		{
			tag.setBoolean("_null", true);

			return tag;
		}

		tag.setBoolean("_null", false);

		tag.setInteger("x", pos.getX());
		tag.setInteger("y", pos.getY());
		tag.setInteger("z", pos.getZ());

		return tag;
	}
}
