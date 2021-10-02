package tfcflorae.objects.te.multiblockte;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.multiblock.MultiBlockBase;
import tfcflorae.objects.blocks.multiblock.MultiBlockController;

public class TECampfire extends TEMultiBlockBase implements ITickable
{
    private static final int PLAYER_SEARCHING_RADIUS = 2;

	public TECampfire()
	{
		super((MultiBlockController) BlocksTFCF.Campfire, (Block) BlocksTFCF.DummyHalf);
	}

	@Override
	public boolean onInteract(final EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
            return true;
		}

		return false;
	}

	@Override
	public ItemStack getPickedStack(final World world, final BlockPos pos, final IBlockState state)
	{
		return new ItemStack(BlocksTFCF.Campfire);
	}

	@SideOnly(Side.CLIENT)
	public void clientUpdate()
	{
		//AetherCore.PROXY.spawnCampfireParticles(this.world, this.pos.getX() + 1.0D, this.pos.getY(), this.pos.getZ() + 1.0D);
	}

	@Override
	public void update()
	{

		AxisAlignedBB searchingBB = new AxisAlignedBB(
				new BlockPos(this.pos.getX() - PLAYER_SEARCHING_RADIUS, this.pos.getY() - PLAYER_SEARCHING_RADIUS,
						this.pos.getZ() - PLAYER_SEARCHING_RADIUS),
				new BlockPos(this.pos.getX() + PLAYER_SEARCHING_RADIUS + 2.0F, this.pos.getY() + PLAYER_SEARCHING_RADIUS,
						this.pos.getZ() + PLAYER_SEARCHING_RADIUS + 2.0F));

		List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, searchingBB);

		if (this.world.isRemote)
		{
			this.clientUpdate();
		}

		for (EntityPlayer player : players)
		{
			/*PlayerAether playerAether = PlayerAether.getPlayer(player);

			PlayerCampfiresModule campfiresModule = playerAether.getModule(PlayerCampfiresModule.class);

			if (!campfiresModule.hasCampfire(this.posDim))
			{
				campfiresModule.addActivatedCampfire(this.posDim);

				AetherCore.PROXY.spawnCampfireStartParticles(this.world, this.pos.getX() + 1.0D, this.pos.getY(), this.pos.getZ() + 1.0D);
			}*/
		}
	}
}
