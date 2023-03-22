package tfcflorae.common.blocks.rock;

import java.util.Random;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

import net.dries007.tfc.common.blocks.ExtendedProperties;

public abstract class Geyser extends TFCFPointedDripstoneBlock
{
    private final boolean spawnParticles;

    public static Geyser create(ExtendedProperties properties, @Nullable Supplier<? extends Block> thisBlock, boolean spawnParticles)
    {
        return new Geyser(properties, thisBlock, spawnParticles) {};
    }

    public Geyser(ExtendedProperties properties, @Nullable Supplier<? extends Block> thisBlock, boolean spawnParticles)
    {
        super(properties, thisBlock);
        this.spawnParticles = spawnParticles;

        this.registerDefaultState(this.stateDefinition.any().setValue(TIP_DIRECTION, Direction.UP).setValue(THICKNESS, DripstoneThickness.TIP));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random)
    {
        super.animateTick(state, level, pos, random);
        if (this.spawnParticles && state.getValue(THICKNESS) == DripstoneThickness.TIP && state.getValue(TIP_DIRECTION) == Direction.UP)
        {
            int xPos = pos.getX();
            int yPos = pos.getY();
            int zPos = pos.getZ();
            double x = xPos + 0.5D;
            double y = yPos + random.nextDouble() + random.nextDouble();
            double z = zPos + 0.5D;
            double randomSpread = Mth.clamp(random.nextDouble() * 0.1D, 0D, 0.07D);
            boolean offset = random.nextBoolean();

            if (random.nextDouble() < 0.05D - randomSpread)
            {
                level.playLocalSound(x, yPos + 0.5D, z, SoundEvents.SMOKER_SMOKE, SoundSource.BLOCKS, 0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
            }
            level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, true, x + random.nextDouble() / 2.5D * (offset ? 1D : -1D), y, z + random.nextDouble() / 2.5D * (offset ? 1D : -1D), randomSpread, 0.07D + randomSpread, randomSpread);
        }
    }
}
