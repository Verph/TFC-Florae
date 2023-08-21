package tfcflorae.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blocks.RiverWaterBlock;
import net.dries007.tfc.common.fluids.BucketPickupExtension;
import net.dries007.tfc.common.fluids.TFCFluids;

import tfcflorae.client.particle.TFCFParticles;

@Mixin(RiverWaterBlock.class)
public abstract class RiverWaterBlockMixin extends LiquidBlock implements BucketPickupExtension
{
    public RiverWaterBlockMixin(Properties properties)
    {
        super(TFCFluids.RIVER_WATER, properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random)
    {
        super.animateTick(state, level, pos, random);
        if (Minecraft.useFancyGraphics() && random.nextInt(34) == 0 && level.getBlockState(pos.above()).isAir())
        {
            for (int i = 0; i < 3; i++)
            {
                level.addParticle(TFCFParticles.WATER_FLOW.get(), pos.getX() + random.nextFloat(), pos.getY() + 0.875, pos.getZ() + random.nextFloat(), 0, 0, 0);
            }
        }
    }
}
