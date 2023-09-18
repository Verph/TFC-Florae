package tfcflorae.mixin;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootContext;

import net.dries007.tfc.client.particle.TFCParticles;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.IForgeBlockExtension;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.plant.fruit.FruitTreeLeavesBlock;
import net.dries007.tfc.common.blocks.wood.ILeavesBlock;
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.fluids.IFluidLoggable;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Season;
import net.dries007.tfc.util.climate.Climate;
import net.dries007.tfc.util.climate.ClimateModel;
import net.dries007.tfc.world.chunkdata.ChunkData;
import net.dries007.tfc.world.chunkdata.ChunkDataProvider;

import tfcflorae.Config;
import tfcflorae.client.particle.FallingLeafParticle;
import tfcflorae.client.particle.TFCFParticles;
import tfcflorae.common.blocks.ICooldown;
import tfcflorae.common.blocks.wood.TFCFLeavesBlock;
import tfcflorae.common.blocks.wood.TFCFMangroveLeavesBlock;
import tfcflorae.util.TFCFHelpers;

@Mixin(TFCLeavesBlock.class)
public abstract class TFCLeavesBlockMixin extends Block implements ILeavesBlock, IForgeBlockExtension, IFluidLoggable, ICooldown
{
    @Unique private boolean isDead;
    @Unique private long cooldown;

    public TFCLeavesBlockMixin(ExtendedProperties properties)
    {
        super(properties.properties());
        this.cooldown = Long.MIN_VALUE;
        this.isDead = false;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void inject$init(CallbackInfo ci)
    {
        this.cooldown = Long.MIN_VALUE;
        this.isDead = false;
    }

    @Overwrite(remap = false)
    public static void doParticles(ServerLevel level, double x, double y, double z, int count)
    {
        level.sendParticles(TFCParticles.LEAF.get(), x, y, z, count, 0, 0, 0, 0.3f);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random)
    {
        if (!state.getValue(TFCLeavesBlock.PERSISTENT))
        {
            if ((random.nextInt(30) == 0 && (pos.getY() > 110 || Calendars.get(level).getCalendarMonthOfYear().getSeason() == Season.FALL)) || (FallingLeafParticle.isAcceptedLeaves(state) && random.nextInt(10) == 0))
            {
                final BlockPos belowPos = pos.below();
                final BlockState belowState = level.getBlockState(belowPos);
                if (belowState.isAir())
                {
                    double d0 = (double)pos.getX() + random.nextDouble();
                    double d1 = (double)pos.getY() - 0.05D;
                    double d2 = (double)pos.getZ() + random.nextDouble();
                    level.addParticle(new BlockParticleOption(TFCFParticles.FALLING_LEAF.get(), state), d0, d1, d2, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Inject(method = "randomTick", at = @At("TAIL"), cancellable = true)
    private void inject$randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random, CallbackInfo ci)
    {
        final Block thisBlock = this.defaultBlockState().getBlock();
        if (Config.COMMON.leavesSaplingPlacementChance.get() > 0 && !(thisBlock instanceof TFCFLeavesBlock || thisBlock instanceof TFCFMangroveLeavesBlock))
        {
            Season currentSeason = Calendars.get(level).getCalendarMonthOfYear().getSeason();
            if ((currentSeason == Season.FALL || currentSeason == Season.SPRING) && level.getBlockState(pos.below()).isAir())
            {
                final ChunkDataProvider provider = ChunkDataProvider.get(level);
                final ChunkData data = provider.get(level, pos);

                final float rainfall = data.getRainfall(pos);
                final float rainfallInverted = ((ClimateModel.MAXIMUM_RAINFALL - rainfall) * 0.25F) + 1F;

                final float actualForestDensity = data.getForestDensity();
                final float forestDensity = actualForestDensity == 0 ? 0.001F : actualForestDensity; // Cannot divide by 0.

                if (random.nextFloat((Config.COMMON.leavesSaplingPlacementChance.get() / forestDensity) * rainfallInverted) == 0)
                {
                    Block sapling = null;
                    for (Wood wood : Wood.values())
                    {
                        if (TFCBlocks.WOODS.get(wood).get(Wood.BlockType.LEAVES).get() != null)
                        {
                            if (TFCBlocks.WOODS.get(wood).get(Wood.BlockType.LEAVES).get().equals(thisBlock))
                            {
                                sapling = TFCBlocks.WOODS.get(wood).get(Wood.BlockType.SAPLING).get();
                                break;
                            }
                        }
                    }

                    if (sapling != null)
                    {
                        int x = pos.getX() + (int) Math.round(random.nextGaussian() * Config.COMMON.leavesSaplingSpreadDistance.get());
                        int z = pos.getZ() + (int) Math.round(random.nextGaussian() * Config.COMMON.leavesSaplingSpreadDistance.get());
                        int y = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

                        BlockPos placementPos = new BlockPos(x, y, z);
                        BlockState placementState = level.getBlockState(placementPos);

                        if ((Helpers.isBlock(placementState, TFCTags.Blocks.PLANTS) || EnvironmentHelpers.isWorldgenReplaceable(placementState)) && level.getMaxLocalRawBrightness(placementPos) >= 9 && sapling.defaultBlockState().canSurvive(level, placementPos))
                        {
                            level.setBlock(placementPos, sapling.defaultBlockState(), Block.UPDATE_ALL);
                        }
                    }
                }
            }
        }

        double tempThreshold = Config.COMMON.foliageDecayThreshold.get();
        int check = random.nextInt(Config.COMMON.fruitingLeavesUpdateChance.get());

        if (!(state.getBlock() instanceof FruitTreeLeavesBlock) && Helpers.isBlock(state, TFCTags.Blocks.SEASONAL_LEAVES))
        {
            if (!isDead && check == 0)
            {
                if (Climate.getTemperature(level, pos) < tempThreshold && TFCFHelpers.getAverageDailyTemperature(level, pos) < tempThreshold) // Cold, thus leaves should wilt/die.
                {
                    isDead = true;
                    level.blockUpdated(pos, state.getBlock());
                    if (level.getBlockState(pos.above()).getBlock() instanceof SnowLayerBlock)
                    {
                        level.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                    }
                    return;
                }
            }
            else if (isDead && getCooldown(level, cooldown) && check == 1)
            {
                if (Climate.getTemperature(level, pos) >= tempThreshold && TFCFHelpers.getAverageDailyTemperature(level, pos) >= tempThreshold) // It's warming up again.
                {
                    setCooldown(level, ICalendar.TICKS_IN_DAY * 3, cooldown);
                    isDead = false;
                    level.blockUpdated(pos, state.getBlock());
                    return;
                }
            }
        }
    }

    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true, remap = true)
    private void inject$entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci)
    {
        if (isDead)
        {
            ci.cancel();
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return isDead ? RenderShape.INVISIBLE : super.getRenderShape(state); // Invisible when "dead"
    }

    @Override
    protected boolean isAir(BlockState state)
    {
        return isDead ? true : super.isAir(state); // Invisible when "dead"
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        return isDead ? Collections.emptyList() : super.getDrops(state, builder);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context)
    {
        return isDead ? true : super.canBeReplaced(state, context);
    }

    @Inject(method = "getShadeBrightness", at = @At("TAIL"), cancellable = true, remap = true)
    private void inject$getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Float> cir)
    {
        if (isDead)
        {
            cir.setReturnValue(1.0F);
        }
        else
        {
            cir.getReturnValue();
        }
    }
}
