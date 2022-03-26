package tfcflorae.client.particle;

import java.util.function.Supplier;
import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import tfcflorae.TFCFlorae;
import tfcflorae.network.PacketSpawnTFCFParticle;

import static tfcflorae.TFCFlorae.MODID;

/**
 * TFC Particles, wrapped up in a nice enum
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MODID)
public enum TFCFParticles
{
    BLOSSOM(new ResourceLocation(MODID, "particle/blossom"), () -> ParticleBlossom::new),
    FALLING_BLOSSOM(new ResourceLocation(MODID, "particle/blossom"), () -> ParticleFallingBlossom::new);

    @SubscribeEvent
    public static void onTextureStitchEvent(TextureStitchEvent.Pre event)
    {
        for (TFCFParticles particle : TFCFParticles.values())
        {
            particle.registerSprite(event.getMap());
        }
    }

    private final ResourceLocation location;
    private final Supplier<IParticleFactoryTFC> factorySupplier;
    private TextureAtlasSprite sprite;

    /**
     * Register a new particle to have it's texture atlas initialized and be ready to be used by TFC
     *
     * @param location        the resource (textures) location
     * @param factorySupplier the supplier (so we can instantiate this class server side) for method on how to create new instances of this particle
     */
    TFCFParticles(ResourceLocation location, Supplier<IParticleFactoryTFC> factorySupplier)
    {
        this.location = location;
        this.factorySupplier = factorySupplier;
    }

    /**
     * Spawns this particle in world
     *
     * @param worldIn  client's world obj
     * @param x        x coord
     * @param y        y coord
     * @param z        z coord
     * @param speedX   speed at x coord which this particle will move
     * @param speedY   speed at y coord which this particle will move
     * @param speedZ   speed at z coord which this particle will move
     * @param duration the duration in ticks this particle will live in client's world
     */
    @SideOnly(Side.CLIENT)
    public void spawn(World worldIn, double x, double y, double z, double speedX, double speedY, double speedZ, int duration)
    {
        Particle particle = factorySupplier.get().createParticle(worldIn, x, y, z, speedX, speedY, speedZ, duration);
        particle.setParticleTexture(sprite);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    /**
     * Send this particle to clients near the coords
     *
     * @param worldIn  server's world obj
     * @param x        x coord
     * @param y        y coord
     * @param z        z coord
     * @param speedX   speed at x coord which this particle will move
     * @param speedY   speed at y coord which this particle will move
     * @param speedZ   speed at z coord which this particle will move
     * @param duration the duration in ticks this particle will live in client's world
     */
    public void sendToAllNear(World worldIn, double x, double y, double z, double speedX, double speedY, double speedZ, int duration)
    {
        final int range = 80;
        PacketSpawnTFCFParticle packet = new PacketSpawnTFCFParticle(this, x, y, z, speedX, speedY, speedZ, duration);
        NetworkRegistry.TargetPoint point = new NetworkRegistry.TargetPoint(worldIn.provider.getDimension(), x, y, z, range);
        TFCFlorae.getNetwork().sendToAllAround(packet, point);
    }

    /**
     * Register textures to be ready for when this particle is spawned
     *
     * @param map the TextureMap, got from {@link TextureStitchEvent.Pre} event
     */
    @SideOnly(Side.CLIENT)
    private void registerSprite(@Nonnull TextureMap map)
    {
        this.sprite = map.registerSprite(location);
    }

    /**
     * A factory interface to be used to create new instances of particles
     */
    public interface IParticleFactoryTFC
    {
        @SideOnly(Side.CLIENT)
        @Nonnull
        Particle createParticle(World world, double x, double y, double z, double speedX, double speedY, double speedZ, int duration);
    }
}