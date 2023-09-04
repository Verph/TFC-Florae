package tfcflorae.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

import tfcflorae.common.blocks.plant.TFCFPlant;

public class SporeParticle extends TextureSheetParticle
{
    protected SporeParticle(ClientLevel level, double x, double y, double z, SpriteSet set, boolean tinted, BlockState state)
    {
        super(level, x, y, z);

        int color = -1;
        for (TFCFPlant plant : TFCFPlant.values())
        {
            if (plant.getSporeColor() != -1)
            {
                color = plant.getSporeColor();
                break;
            }
        }

        if (color != -1)
        {
            setColor(((color >> 16) & 0xFF) / 255F, ((color >> 8) & 0xFF) / 255F, (color & 0xFF) / 255F);
        }
    }

    public record Provider(SpriteSet set, boolean tinted) implements ParticleProvider<BlockParticleOption>
    {
        @Override
        public Particle createParticle(BlockParticleOption type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            SporeParticle particle = new SporeParticle(level, x, y, z, set, tinted, type.getState());
            particle.setLifetime(Mth.randomBetweenInclusive(level.getRandom(), 500, 1000));
            particle.gravity = 0.01F;
            particle.pickSprite(set);
            return particle;
        }
    }

    public ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
}