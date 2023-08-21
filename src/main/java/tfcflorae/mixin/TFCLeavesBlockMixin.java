package tfcflorae.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.client.particle.TFCParticles;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.wood.TFCLeavesBlock;
import net.dries007.tfc.util.calendar.Calendars;
import net.dries007.tfc.util.calendar.Season;

import tfcflorae.client.particle.FallingLeafParticle;
import tfcflorae.client.particle.TFCFParticles;

@Mixin(TFCLeavesBlock.class)
public abstract class TFCLeavesBlockMixin extends Block
{
    public TFCLeavesBlockMixin(ExtendedProperties properties)
    {
        super(properties.properties());
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
            if ((random.nextInt(30) == 0 && (pos.getY() > 110 || Calendars.CLIENT.getCalendarMonthOfYear().getSeason() == Season.FALL)) || (FallingLeafParticle.isAcceptedLeaves(state) && random.nextInt(10) == 0))
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
}
