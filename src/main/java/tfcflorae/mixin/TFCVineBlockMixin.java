package tfcflorae.mixin;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.TFCVineBlock;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.Climate;

import tfcflorae.Config;
import tfcflorae.common.blocks.ICooldown;
import tfcflorae.util.TFCFHelpers;

@Mixin(TFCVineBlock.class)
public abstract class TFCVineBlockMixin extends VineBlock implements ICooldown
{
    @Unique private boolean isDead;
    @Unique private long cooldown;

    public TFCVineBlockMixin(ExtendedProperties properties)
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

    @Inject(method = "randomTick", at = @At("TAIL"), cancellable = true)
    private void inject$randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random, CallbackInfo ci)
    {
        double tempThreshold = Config.COMMON.foliageDecayThreshold.get();

        if (!isDead)
        {
            if (Climate.getTemperature(level, pos) < tempThreshold && TFCFHelpers.getAverageDailyTemperature(level, pos) < tempThreshold) // Cold, thus leaves should wilt/die.
            {
                isDead = true;
                level.blockUpdated(pos, state.getBlock());
                return;
            }
            else if (Climate.getTemperature(level, pos) > 0.0F)
            {
                super.randomTick(state, level, pos, random);
            }
        }
        else if (isDead && getCooldown(level, cooldown))
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

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos)
    {
        return isDead ? 1.0F : super.getShadeBrightness(state, level, pos);
    }
}
