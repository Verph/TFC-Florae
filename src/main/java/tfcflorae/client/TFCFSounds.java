package tfcflorae.client;

import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import tfcflorae.util.TFCFHelpers;

import static tfcflorae.TFCFlorae.MOD_ID;

public final class TFCFSounds
{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

    // Blocks

    public static final ForgeSoundType MUD = createBlock("mud");
    public static final ForgeSoundType MUD_BRICKS = createBlock("mud_bricks");
    public static final ForgeSoundType PACKED_MUD = createBlock("packed_mud");
    public static final ForgeSoundType MANGROVE_ROOTS = createBlock("mangrove_roots");
    public static final ForgeSoundType MUDDY_MANGROVE_ROOTS = createBlock("muddy_mangrove_roots");

    private static RegistryObject<SoundEvent> create(String name)
    {
        return SOUNDS.register(name, () -> new SoundEvent(TFCFHelpers.identifier(name)));
    }

    private static Optional<Supplier<SoundEvent>> createOptional(String name, boolean present)
    {
        return Optional.ofNullable(present ? create(name) : null);
    }

    private static ForgeSoundType createBlock(String name)
    {
        return new ForgeSoundType(1.0f, 1.0f, create("block.%s.break".formatted(name)), create("block.%s.step".formatted(name)), create("block.%s.place".formatted(name)), create("block.%s.hit".formatted(name)), create("block.%s.fall".formatted(name)));
    }

    private static EntitySound createEntity(String name, boolean attack, boolean sleep)
    {
        return new EntitySound(create("entity.%s.ambient".formatted(name)), create("entity.%s.death".formatted(name)), create("entity.%s.hurt".formatted(name)), create("entity.%s.step".formatted(name)), createOptional("entity.%s.attack".formatted(name), attack), createOptional("entity.%s.sleep".formatted(name), sleep));
    }

    public record EntitySound(Supplier<SoundEvent> ambient, Supplier<SoundEvent> death, Supplier<SoundEvent> hurt, Supplier<SoundEvent> step, Optional<Supplier<SoundEvent>> attack, Optional<Supplier<SoundEvent>> sleep)
    {
        public EntitySound(Supplier<SoundEvent> ambient, Supplier<SoundEvent> death, Supplier<SoundEvent> hurt, Supplier<SoundEvent> step)
        {
            this(ambient, death, hurt, step, Optional.empty(), Optional.empty());
        }
    }
}
