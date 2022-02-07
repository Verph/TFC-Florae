package tfcflorae.objects.entity;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.eerussianguy.firmalife.init.FruitTreeFL;
import com.eerussianguy.firmalife.init.PlantsFL;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import mcp.MethodsReturnNonnullByDefault;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.util.agriculture.FruitTree;

import tfcflorae.TFCFlorae;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.items.ItemBoatTFCF;
import tfcflorae.types.TreesTFCF;
import tfcflorae.util.agriculture.SeasonalTrees;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EntityBoatTFCF extends EntityBoat
{
    private static final DataParameter<String> WOOD_NAME = EntityDataManager.createKey(EntityBoatTFCF.class, DataSerializers.STRING);

    public EntityBoatTFCF(World worldIn)
    {
        super(worldIn);
    }

    public EntityBoatTFCF(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Nullable
    public IFruitTree getWood()
    {
        /*for (SeasonalTrees fruitTree : SeasonalTrees.values())
        {
            String name = fruitTree.getName().toLowerCase();

            if (this.dataManager.get(WOOD_NAME) == name)
                return fruitTree;
        }*/

        for (IFruitTree fruitTree : FruitTree.values())
        {
            String name = fruitTree.getName().toLowerCase();

            if (this.dataManager.get(WOOD_NAME) == name)
                return fruitTree;
        }

        if (TFCFlorae.FirmaLifeAdded)
        {
            for (FruitTreeFL fruitTree : FruitTreeFL.values())
            {
                String name = fruitTree.getName().toLowerCase();

                if (this.dataManager.get(WOOD_NAME) == name)
                    return fruitTree;
            }
        }

        return null;
    }

    @Nullable
    public Tree getTree()
    {
        for (int i = 0; i < BlocksTFCF.bamboo.length; i++)
        {
            if (this.dataManager.get(WOOD_NAME) == BlocksTFCF.bamboo[i])
                return BlocksTFCF.bambooTrees[i];
        }

        if (this.dataManager.get(WOOD_NAME) == "cassia_cinnamon")
            return TreesTFCF.CASSIA_CINNAMON_TREE;

        if (this.dataManager.get(WOOD_NAME) == "ceylon_cinnamon")
            return TreesTFCF.CEYLON_CINNAMON_TREE;

        if (TFCFlorae.FirmaLifeAdded)
        {
            if (this.dataManager.get(WOOD_NAME) == "cinnamon")
                return PlantsFL.CINNAMON_TREE;
        }

        return null;
    }

    public void setWood(@Nullable IFruitTree wood)
    {
        String woodName = "";
        if (wood != null)
        {
            //noinspection ConstantConditions
            woodName = wood.getName().toLowerCase();
        }
        this.dataManager.set(WOOD_NAME, woodName);
    }

    public void setTree(@Nullable Tree tree)
    {
        String woodName = "";
        if (tree != null)
        {
            //noinspection ConstantConditions
            woodName = tree.getRegistryName().getPath().toLowerCase();
        }
        this.dataManager.set(WOOD_NAME, woodName);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(WOOD_NAME, "");
    }

    @Override
    public Item getItemBoat()
    {
        IFruitTree wood = getWood();
        if (wood != null)
        {
            return ItemBoatTFCF.get(wood);
        }
        Tree tree = getTree();
        if (tree != null)
        {
            return ItemBoatTFCF.get(tree);
        }
        return super.getItemBoat();
    }

    @Override
    public void onUpdate()
    {
        this.previousStatus = this.status;
        this.status = this.getBoatStatus();

        if (this.status != EntityBoat.Status.UNDER_WATER && this.status != EntityBoat.Status.UNDER_FLOWING_WATER)
        {
            this.outOfControlTicks = 0.0F;
        }
        else
        {
            ++this.outOfControlTicks;
        }

        if (!this.world.isRemote && this.outOfControlTicks >= 60.0F)
        {
            this.removePassengers();
        }

        if (this.getTimeSinceHit() > 0)
        {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }

        if (this.getDamageTaken() > 0.0F)
        {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (!this.world.isRemote)
        {
            this.setFlag(6, this.isGlowing());
        }

        this.onEntityUpdate();
        this.tickLerp();

        if (this.canPassengerSteer())
        {
            if (this.getPassengers().isEmpty() || !(this.getPassengers().get(0) instanceof EntityPlayer))
            {
                this.setPaddleState(false, false);
            }

            this.updateMotion();

            if (this.world.isRemote)
            {
                this.controlBoat();
                this.world.sendPacketToServer(new CPacketSteerBoat(this.getPaddleState(0), this.getPaddleState(1)));
            }

            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        }
        else
        {
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
        }

        for (int i = 0; i <= 1; ++i)
        {
            if (this.getPaddleState(i))
            {
                if (!this.isSilent() && (double) (this.paddlePositions[i] % ((float) Math.PI * 2F)) <= (Math.PI / 4D) && ((double) this.paddlePositions[i] + 0.39269909262657166D) % (Math.PI * 2D) >= (Math.PI / 4D))
                {
                    SoundEvent soundevent = this.getPaddleSound();

                    if (soundevent != null)
                    {
                        Vec3d vec3d = this.getLook(1.0F);
                        double d0 = i == 1 ? -vec3d.z : vec3d.z;
                        double d1 = i == 1 ? vec3d.x : -vec3d.x;
                        this.world.playSound(null, this.posX + d0, this.posY, this.posZ + d1, soundevent, this.getSoundCategory(), 1.0F, 0.8F + 0.4F * this.rand.nextFloat());
                    }
                }

                this.paddlePositions[i] = (float) ((double) this.paddlePositions[i] + 0.39269909262657166D);
            }
            else
            {
                this.paddlePositions[i] = 0.0F;
            }
        }
        this.doBlockCollisions();
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        IFruitTree wood = getWood();
        if (wood != null)
        {
            String name = wood.getName().toLowerCase();
            //noinspection ConstantConditions
            compound.setString("Wood", "wood/fruit_tree/boat/" + wood);
        }
        Tree tree = getTree();
        if (tree != null)
        {
            //noinspection ConstantConditions
            compound.setString("Wood", this.getTree().getRegistryName().getPath().toLowerCase());
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        if (compound.hasKey("Wood"))
        {
            this.dataManager.set(WOOD_NAME, compound.getString("Wood"));
        }
    }
}