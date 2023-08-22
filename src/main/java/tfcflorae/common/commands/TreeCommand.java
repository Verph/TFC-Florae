package tfcflorae.common.commands;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.server.command.EnumArgument;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import tfcflorae.common.blocks.wood.TFCFWood;
import tfcflorae.world.feature.tree.TFCFTreeGrower;

public final class TreeCommand
{
    public static LiteralArgumentBuilder<CommandSourceStack> create()
    {
        return Commands.literal("treetfcf")
            .requires(source -> source.hasPermission(2))
            .then(Commands.argument("pos", BlockPosArgument.blockPos())
                .then(Commands.argument("wood", EnumArgument.enumArgument(TFCFWood.class))
                    .then(Commands.argument("variant", EnumArgument.enumArgument(Variant.class))
                        .executes(context -> placeTree(context.getSource().getLevel(), BlockPosArgument.getLoadedBlockPos(context, "pos"), context.getArgument("wood", TFCFWood.class), context.getArgument("variant", Variant.class)))
                    )
                    .executes(context -> placeTree(context.getSource().getLevel(), BlockPosArgument.getLoadedBlockPos(context, "pos"), context.getArgument("wood", TFCFWood.class), Variant.NORMAL))
                )
            );
    }

    private static int placeTree(ServerLevel world, BlockPos pos, TFCFWood wood, Variant variant)
    {
        TFCFTreeGrower tree = wood.tree();
        Registry<ConfiguredFeature<?, ?>> registry = world.registryAccess().registryOrThrow(Registry.CONFIGURED_FEATURE_REGISTRY);
        ConfiguredFeature<?, ?> feature = variant == Variant.NORMAL ? tree.getNormalFeature(registry) : tree.getOldGrowthFeature(registry);
        feature.place(world, world.getChunkSource().getGenerator(), world.getRandom(), pos);
        return Command.SINGLE_SUCCESS;
    }

    private enum Variant
    {
        NORMAL, LARGE
    }
}