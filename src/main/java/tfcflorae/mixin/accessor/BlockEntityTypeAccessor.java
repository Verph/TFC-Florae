package tfcflorae.mixin.accessor;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Credit to <a href="https://github.com/eerussianguy">eerussianguy</a>
 */
@Mixin(BlockEntityType.class)
public interface BlockEntityTypeAccessor
{
    @Accessor("validBlocks")
    Set<Block> accessor$getValidBlocks();

    @Accessor("validBlocks")
    @Mutable
    void accessor$setValidBlocks(Set<Block> blocks);

    // And then utilize this somewhere, which I do not know.
    /*private static void modifyBlockEntityType(BlockEntityType<?> type, Stream<Block> extraBlocks)
    {
        TFCFlorae.LOGGER.debug("Modifying block entity type: " + ForgeRegistries.BLOCK_ENTITIES.getKey(type));
        Set<Block> blocks = ((BlockEntityTypeAccessor) (Object) type).accessor$getValidBlocks();
        blocks = new HashSet<>(blocks);
        blocks.addAll(extraBlocks.toList());
        ((BlockEntityTypeAccessor) (Object) type).accessor$setValidBlocks(blocks);
    }*/
}