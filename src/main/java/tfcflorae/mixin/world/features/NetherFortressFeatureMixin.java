package tfcflorae.mixin.world.features;

import com.mojang.serialization.Codec;
import java.util.List;

import org.spongepowered.asm.mixin.*;

import net.minecraft.core.QuartPos;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.feature.NetherFortressFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.NetherBridgePieces;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import tfcflorae.Config;

@Mixin(NetherFortressFeature.class)
public class NetherFortressFeatureMixin extends StructureFeature<NoneFeatureConfiguration>
{
    @Shadow public static final WeightedRandomList<MobSpawnSettings.SpawnerData> FORTRESS_ENEMIES = WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.BLAZE, 10, 2, 3), new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 5, 4, 4), new MobSpawnSettings.SpawnerData(EntityType.WITHER_SKELETON, 8, 5, 5), new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 2, 5, 5), new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 3, 4, 4));

    public NetherFortressFeatureMixin(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec, PieceGeneratorSupplier.simple(NetherFortressFeatureMixin::checkLocation, NetherFortressFeatureMixin::generatePieces));
    }

    @Shadow
    private static boolean checkLocation(PieceGeneratorSupplier.Context<NoneFeatureConfiguration> context)
    {
        return context.validBiome().test(context.chunkGenerator().getNoiseBiome(QuartPos.fromBlock(context.chunkPos().getMiddleBlockX()), QuartPos.fromBlock(64), QuartPos.fromBlock(context.chunkPos().getMiddleBlockZ())));
    }

    @Overwrite(remap = true)
    private static void generatePieces(StructurePiecesBuilder builder, PieceGenerator.Context<NoneFeatureConfiguration> context)
    {
        NetherBridgePieces.StartPiece netherbridgepieces$startpiece = new NetherBridgePieces.StartPiece(context.random(), context.chunkPos().getBlockX(2), context.chunkPos().getBlockZ(2));
        builder.addPiece(netherbridgepieces$startpiece);
        netherbridgepieces$startpiece.addChildren(netherbridgepieces$startpiece, builder, context.random());
        List<StructurePiece> list = netherbridgepieces$startpiece.pendingChildren;

        while(!list.isEmpty())
        {
            int i = context.random().nextInt(list.size());
            StructurePiece structurepiece = list.remove(i);
            structurepiece.addChildren(netherbridgepieces$startpiece, builder, context.random());
        }

        if (context.chunkGenerator().getMinY() < -64)
            builder.moveInsideHeights(context.random(), Config.COMMON.minFortressHeight.get(), Config.COMMON.maxFortressHeight.get());
        else
            builder.moveInsideHeights(context.random(), 48, 70);
    }
}
