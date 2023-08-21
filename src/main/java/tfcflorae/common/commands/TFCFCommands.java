package tfcflorae.common.commands;

import java.util.function.Supplier;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraftforge.common.util.Lazy;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.suggestion.SuggestionProvider;

import net.dries007.tfc.world.biome.TFCBiomes;

import tfcflorae.util.TFCFHelpers;

public final class TFCFCommands
{
    public static final Supplier<SuggestionProvider<CommandSourceStack>> TFC_BIOMES = register("available_biomes", (context, builder) -> SharedSuggestionProvider.suggestResource(TFCBiomes.getExtensionKeys(), builder));

    public static void registerSuggestionProviders()
    {
        TFC_BIOMES.get();
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        // Register all new commands as sub commands of the `tfcflorae` root
        dispatcher.register(Commands.literal("tfcflorae")
            .then(TreeCommand.create())
        );
    }

    public static <S extends SharedSuggestionProvider> Supplier<SuggestionProvider<S>> register(String id, SuggestionProvider<SharedSuggestionProvider> provider)
    {
        return Lazy.of(() -> SuggestionProviders.register(TFCFHelpers.identifier(id), provider));
    }
}