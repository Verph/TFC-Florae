package tfcflorae.client;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.util.Helpers;

import tfcflorae.common.entities.Fish;
import tfcflorae.util.TFCFHelpers;

import static tfcflorae.TFCFlorae.MOD_ID;

public final class TFCFSounds
{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

    // Entities
    public static final EntitySound FROG = createEntity("frog", false, false);
    public static final EntitySound TADPOLE = createEntity("tadpole", false, false);
    public static final Map<Fish, FishSound> FRESHWATER_FISHES = Helpers.mapOfKeys(Fish.class, Fish::makeSound);

    // Items
    public static final RegistryObject<SoundEvent> BRUSHING = create("item.brushing.brushing");
    public static final RegistryObject<SoundEvent> BUCKED_EMPTY_TADPOLE = create("item.bucket.empty_tadpole");
    public static final RegistryObject<SoundEvent> BUCKED_FILL_TADPOLE = create("item.bucket.fill_tadpole");

    // Blocks
    public static final ForgeSoundType MUD = createBlock("mud");
    public static final ForgeSoundType MUD_BRICKS = createBlock("mud_bricks");
    public static final ForgeSoundType PACKED_MUD = createBlock("packed_mud");
    public static final ForgeSoundType MANGROVE_ROOTS = createBlock("mangrove_roots");
    public static final ForgeSoundType MUDDY_MANGROVE_ROOTS = createBlock("muddy_mangrove_roots");
    public static final ForgeSoundType FROGLIGHT = createBlock("froglight");
    public static final ForgeSoundType FROGSPAWN = createBlock("frogspawn");
    public static final RegistryObject<SoundEvent> BLOCK_FROGSPAWN_HATCH = create("block.frogspawn.hatch");

    // Sound Events
    public static final RegistryObject<SoundEvent> FROG_EAT = create("entity.frog.eat");
    public static final RegistryObject<SoundEvent> FROG_LAY_SPAWN = create("entity.frog.lay_spawn");
    public static final RegistryObject<SoundEvent> FROG_LONG_JUMP = create("entity.frog.long_jump");
    public static final RegistryObject<SoundEvent> FROG_TONGUE = create("entity.frog.tongue");
    public static final RegistryObject<SoundEvent> TADPOLE_GROW_UP = create("entity.tadpole.grow_up");
    public static final RegistryObject<SoundEvent> TADPOLE_FLOP = create("entity.tadpole.flop");

    public static RegistryObject<SoundEvent> create(String name)
    {
        return SOUNDS.register(name, () -> new SoundEvent(TFCFHelpers.identifier(name)));
    }

    public static Optional<Supplier<SoundEvent>> createOptional(String name, boolean present)
    {
        return Optional.ofNullable(present ? create(name) : null);
    }

    public static ForgeSoundType createBlock(String name)
    {
        return new ForgeSoundType(1.0f, 1.0f, create("block.%s.break".formatted(name)), create("block.%s.step".formatted(name)), create("block.%s.place".formatted(name)), create("block.%s.hit".formatted(name)), create("block.%s.fall".formatted(name)));
    }

    public static EntitySound createEntity(String name, boolean attack, boolean sleep)
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

    public static FishSound createFish(String name)
    {
        return new FishSound(create("entity.%s.ambient".formatted(name)), create("entity.%s.death".formatted(name)), create("entity.%s.hurt".formatted(name)), create("entity.%s.flop".formatted(name)));
    }

    public record FishSound(Supplier<SoundEvent> ambient, Supplier<SoundEvent> death, Supplier<SoundEvent> hurt, Supplier<SoundEvent> flop) {}
}
