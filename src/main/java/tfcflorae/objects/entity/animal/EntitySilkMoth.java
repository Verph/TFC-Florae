package tfcflorae.objects.entity.animal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.capability.egg.CapabilityEgg;
import net.dries007.tfc.api.capability.egg.IEgg;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.IAnimalTFC;
import net.dries007.tfc.api.types.ILivestock;
import net.dries007.tfc.objects.LootTablesTFC;
import net.dries007.tfc.objects.advancements.TFCTriggers;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFC;
import net.dries007.tfc.objects.entity.animal.AnimalGroupingRules;
import net.dries007.tfc.objects.entity.animal.EntityAnimalTFC;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;

import tfcflorae.objects.LootTablesTFCF;
import tfcflorae.objects.entity.EntitiesTFCF;
import tfcflorae.objects.blocks.wood.BlockLeavesTFCF;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.types.TreesTFCF;
import tfcflorae.util.agriculture.SeasonalTrees;

import static tfcflorae.TFCFlorae.MODID;

public class EntitySilkMoth extends EntityAnimalTFC implements ILivestock
{
    private static final int DAYS_TO_ADULTHOOD = 6;
    private static final int DAYS_TO_ELDER = 12;
    private static final int DAYS_TO_DEATH = 20;
    private static final double DEATH_CHANCE = 5;
    private static final int DAYS_TO_HATCH = 1;
    private static final int TICKS_TO_LAY_EGG = 2500;
    private static final DataParameter<Long> LAID = EntityDataManager.createKey(EntitySilkMoth.class, EntitiesTFCF.getLongDataSerializer());

	private BlockPos rotationPos = new BlockPos(0,0,0);
    private BlockPos spawnPosition;
	private boolean clockwise;
	private float distance;

    @SuppressWarnings("unused")
    public EntitySilkMoth(World worldIn)
    {
        this(worldIn, Gender.valueOf(Constants.RNG.nextBoolean()), EntityAnimalTFC.getRandomGrowth(DAYS_TO_ADULTHOOD, DAYS_TO_ELDER));
    }

    public EntitySilkMoth(World worldIn, Gender gender, int birthDay)
    {
        super(worldIn, gender, birthDay);
		this.setSize(0.3F, 0.3F);
		this.moveForward = 0.5F;
		this.clockwise = this.rand.nextBoolean();
		this.distance = 0.5F + 2.0F * this.rand.nextFloat();
	}

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity)
    {
        /*BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) && blockpos.getY() >= WorldTypeTFC.SEALEVEL)
        {
            return (int)(ConfigTFC.Animals.HARE.rarity / 1.4D);
        }
        return 0;*/
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
            (biomeType == BiomeHelper.BiomeType.TEMPERATE_FOREST || biomeType == BiomeHelper.BiomeType.TROPICAL_FOREST))
        {
            return ConfigTFC.Animals.HARE.rarity;
        }
        return 0;
    }

    @Override
    public BiConsumer<List<EntityLiving>, Random> getGroupingRules()
    {
        return AnimalGroupingRules.MALE_AND_FEMALES;
    }

    @Override
    public int getMinGroupSize()
    {
        return 3;
    }

    @Override
    public int getMaxGroupSize()
    {
        return 5;
    }

    @Override
    public float getAdultFamiliarityCap()
    {
        return 0.45F;
    }

    @Override
    public int getDaysToAdulthood()
    {
        return DAYS_TO_ADULTHOOD;
    }

    @Override
    public int getDaysToElderly()
    {
        return DAYS_TO_ELDER;
    }

    @Override
    public Type getType()
    {
        return Type.OVIPAROUS;
    }

    @Override
    public boolean isReadyForAnimalProduct()
    {
        // Is ready for laying eggs?
        return this.getFamiliarity() > 0.15f && hasEggs();
    }

    @Override
    public List<ItemStack> getProducts()
    {
        List<ItemStack> eggs = new ArrayList<>();
        ItemStack egg = new ItemStack(ItemsTFCF.SILK_MOTH_EGG);
        if (this.isFertilized())
        {
            IEgg cap = egg.getCapability(CapabilityEgg.CAPABILITY, null);
            if (cap != null)
            {
                EntitySilkMoth larvae = new EntitySilkMoth(this.world);
                larvae.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
                cap.setFertilized(larvae, DAYS_TO_HATCH + CalendarTFC.PLAYER_TIME.getTotalDays());
            }
        }
        eggs.add(egg);
        return eggs;
    }

    @Override
    public void setProductsCooldown()
    {
        this.setLaidTicks(CalendarTFC.PLAYER_TIME.getTicks());
    }

    @Override
    public long getProductsCooldown()
    {
        return Math.max(0, TICKS_TO_LAY_EGG + getLaidTicks() - CalendarTFC.PLAYER_TIME.getTicks());
    }

    @Override
    public TextComponentTranslation getTooltip()
    {
        if (this.getGender() == Gender.MALE)
        {
            return new TextComponentTranslation(MODID + ".tooltip.animal.product.male_egg");
        }
        else if (this.getAge() == Age.OLD)
        {
            return new TextComponentTranslation(MODID + ".tooltip.animal.product.old", getAnimalName());
        }
        else if (this.getAge() == Age.CHILD)
        {
            return new TextComponentTranslation(MODID + ".tooltip.animal.product.young", getAnimalName());
        }
        else if (getFamiliarity() <= 0.15f)
        {
            return new TextComponentTranslation(MODID + ".tooltip.animal.product.low_familiarity", getAnimalName());
        }
        else if (!hasEggs())
        {
            return new TextComponentTranslation(MODID + ".tooltip.animal.product.no_egg", getAnimalName());
        }
        return null;
    }

    public long getLaidTicks()
    {
        return dataManager.get(LAID);
    }

    protected void setLaidTicks(long ticks)
    {
        dataManager.set(LAID, ticks);
    }

	@Override
	protected float getSoundVolume()
    {
		return 0.1F;
	}

	@Override
	protected float getSoundPitch()
    {
		return super.getSoundPitch() * 0.95F;
	}

	@Nullable
	@Override
	public SoundEvent getAmbientSound()
    {
		return this.rand.nextInt(4) != 0 ? null : SoundEvents.ENTITY_RABBIT_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
		return SoundEvents.ENTITY_RABBIT_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
    {
		return SoundEvents.ENTITY_RABBIT_DEATH;
	}

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity entityIn)
    {
    }

    @Override
    protected void collideWithNearbyEntities()
    {
    }

    @Override
    protected void initEntityAI()
    {
        EntityAnimalTFC.addCommonLivestockAI(this, 1.3D);
        EntityAnimalTFC.addCommonPreyAI(this, 1.3D);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTablesTFCF.ANIMALS_SILK_MOTH;
    }

    protected boolean hasEggs()
    {
        return this.getGender() == Gender.FEMALE && this.getAge() == Age.ADULT && (getLaidTicks() <= 0 || getProductsCooldown() <= 0);
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.world.checkNoEntityCollision(getEntityBoundingBox())
            && this.world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty()
            && !this.world.containsAnyLiquid(getEntityBoundingBox())
            && (this.world.getBlockState(this.getPosition().down()) == BlockLeavesTFCF.get(SeasonalTrees.YELLOW_MULBERRY) || 
                this.world.getBlockState(this.getPosition().down()) == BlockLeavesTFCF.get(SeasonalTrees.ORANGE_MULBERRY) || 
                this.world.getBlockState(this.getPosition().down()) == BlockLeavesTFCF.get(SeasonalTrees.RED_MULBERRY) || 
                this.world.getBlockState(this.getPosition().down()) == BlockLeavesTFC.get(TFCRegistries.TREES.getValue(TreesTFCF.MULBERRY)));
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        getDataManager().register(LAID, 0L);
    }

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt)
    {
		super.readEntityFromNBT(nbt);
        this.setLaidTicks(nbt.getLong("laidTicks"));
		this.rotationPos = new BlockPos(nbt.getInteger("SilkMothRotationX"), nbt.getInteger("SilkMothRotationY"), nbt.getInteger("SilkMothRotationZ"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt)
    {
		super.writeEntityToNBT(nbt);
        nbt.setLong("laidTicks", getLaidTicks());
		nbt.setInteger("SilkMothRotationX", this.rotationPos.getX());
		nbt.setInteger("SilkMothRotationY", this.rotationPos.getY());
		nbt.setInteger("SilkMothRotationZ", this.rotationPos.getZ());
	}

    @Override
    public double getOldDeathChance()
    {
        return DEATH_CHANCE;
    }

	@Override
	public void onUpdate()
    {
		super.onUpdate();

		if(!world.isRemote)
        {
			if(this.ticksExisted >= (DAYS_TO_DEATH * 24000))
            {
				if(!this.hasCustomName()) this.setDead();
			}

			if(this.ticksExisted < 5)
            {
				if(this.rotationPos.getX() == 0 && this.rotationPos.getY() == 0 && this.rotationPos.getZ() == 0)
                {
					this.rotationPos = new BlockPos(Math.floor(this.posX), Math.floor(this.posY), Math.floor(this.posZ));
				}
			}
            else if(this.rand.nextInt(1000) == 0) this.changeRotationPos();

			double x = this.posX - (this.rotationPos.getX() + 0.5D);
			double z = this.posZ - (this.rotationPos.getZ() + 0.5D);
			double alpha;

			if(z == 0) alpha = (x > 0) ? 0 : Math.PI;
			else
            {
				alpha = Math.atan(z / x);
				if(x > 0) alpha += Math.PI;
			}

			double d = Math.sqrt(x * x + z * z);
			d = - this.distance * 2 / (d + this.distance) + 1;

			alpha += (this.clockwise ? d - 1 : 1 - d) * Math.PI / 2;

			this.motionX = this.motionX * 0.5D + Math.cos(alpha) * 0.15D;
			this.motionY = Math.sin(this.ticksExisted / 20.0D) * 0.05D;
			this.motionZ = this.motionZ * 0.5D + Math.sin(alpha) * 0.15D;

			this.rotationYaw = (float) MathHelper.wrapDegrees(180.0D * alpha / Math.PI - 90.0D);
			this.rotationYawHead = 0.0F;
			this.rotationPitch = (float) -this.motionY * 5 * 90;
		}
	}

    @Override
    protected void updateAITasks()
    {
        super.updateAITasks();

        if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1))
        {
            this.spawnPosition = null;
        }

        if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((double)((int)this.posX), (double)((int)this.posY), (double)((int)this.posZ)) < 4.0D)
        {
            this.spawnPosition = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
        }

        double d0 = (double)this.spawnPosition.getX() + 0.5D - this.posX;
        double d1 = (double)this.spawnPosition.getY() + 0.1D - this.posY;
        double d2 = (double)this.spawnPosition.getZ() + 0.5D - this.posZ;
        this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
        this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
        this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
        float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
        float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
        this.moveForward = 0.5F;
        this.rotationYaw += f1;
    }

	private void changeRotationPos()
    {
        int dayTicks = (int) CalendarTFC.CALENDAR_TIME.getTicks();
		boolean isNight = dayTicks >= 12000 && dayTicks <= 23000;

        int x = dayTicks;
		x = (int) this.posX - 5;
		int y = (int) this.posY - 1;
		int z = (int) this.posZ - 5;
		List<BlockPos> listMulberry = new ArrayList<>();
		List<BlockPos> listLight = new ArrayList<>();
		IBlockState state;

		for(int i = 0; i < 11; i++)
        {
			for(int j = 0; j < 11; j++)
            {
				for(int k = 0; k < 3; k++)
                {
					BlockPos pos = new BlockPos(x + i, y + k, z + j);
					state = this.world.getBlockState(pos);
                    Block stateBlock = state.getBlock();
					if(stateBlock == BlockLeavesTFCF.get(SeasonalTrees.YELLOW_MULBERRY) || stateBlock == BlockLeavesTFCF.get(SeasonalTrees.ORANGE_MULBERRY) || stateBlock == BlockLeavesTFCF.get(SeasonalTrees.RED_MULBERRY) || stateBlock == BlockLeavesTFC.get(TFCRegistries.TREES.getValue(TreesTFCF.MULBERRY)))
                    {
						listMulberry.add(pos);
					}
                    else if(isNight)
                    {
						if (state.getLightValue(this.world, pos) >= 14) listLight.add(pos);
					}
				}
			}
		}

		if(!listLight.isEmpty())
        {
			this.rotationPos = listLight.get(rand.nextInt(listLight.size()));
		}
        else if(!listMulberry.isEmpty())
        {
			this.rotationPos = listMulberry.get(rand.nextInt(listMulberry.size()));
		}
        else this.rotationPos = new BlockPos(x + this.rand.nextInt(11), y + this.rand.nextInt(3), z + this.rand.nextInt(11));

		this.distance = 0.5F + 2 * this.rand.nextFloat();
	}

	@Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            return super.attackEntityFrom(source, amount);
        }
    }

	@Override
	protected boolean canTriggerWalking()
    {
		return false;
	}

	@Override
	public void fall(float distance, float damageMultiplier){}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos){}

	@Override
	public boolean doesEntityNotTriggerPressurePlate()
    {
		return true;
	}

	@Override
	public float getEyeHeight()
    {
		return this.height / 2.0F;
	}

	@Override
	public boolean isAIDisabled()
    {
		return false;
	}
}
