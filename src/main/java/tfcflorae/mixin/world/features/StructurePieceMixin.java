package tfcflorae.mixin.world.features;

import org.spongepowered.asm.mixin.*;

import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;

import tfcflorae.world.feature.TFCFStructurePiece;

@Mixin(StructurePiece.class)
public abstract class StructurePieceMixin
{
    @Shadow protected BoundingBox boundingBox;
    @Shadow private Direction orientation;

    @Shadow
    protected BlockPos.MutableBlockPos getWorldPos(int pX, int pY, int pZ)
    {
        return new BlockPos.MutableBlockPos(this.getWorldX(pX, pZ), this.getWorldY(pY), this.getWorldZ(pX, pZ));
    }

    @Shadow
    protected int getWorldX(int pX, int pZ)
    {
        Direction direction = this.getOrientation();
        if (direction == null)
        {
            return pX;
        }
        else
        {
            switch(direction)
            {
                case NORTH:
                case SOUTH:
                    return this.boundingBox.minX() + pX;
                case WEST:
                    return this.boundingBox.maxX() - pZ;
                case EAST:
                    return this.boundingBox.minX() + pZ;
                default:
                    return pX;
            }
        }
    }

    @Shadow
    protected int getWorldY(int pY)
    {
        return this.getOrientation() == null ? pY : pY + this.boundingBox.minY();
    }

    @Shadow
    protected int getWorldZ(int pX, int pZ)
    {
        Direction direction = this.getOrientation();
        if (direction == null)
        {
            return pZ;
        }
        else
        {
            switch(direction)
            {
                case NORTH:
                    return this.boundingBox.maxZ() - pZ;
                case SOUTH:
                    return this.boundingBox.minZ() + pZ;
                case WEST:
                case EAST:
                    return this.boundingBox.minZ() + pX;
                default:
                    return pZ;
            }
        }
    }

    @Overwrite(remap = true)
    protected boolean createChest(WorldGenLevel pLevel, BoundingBox pBox, Random pRandom, int pX, int pY, int pZ, ResourceLocation pLoot)
    {
        return TFCFStructurePiece.createChestTFC(pLevel, pBox, pRandom, this.getWorldPos(pX, pY, pZ), pLoot, (BlockState)null);
    }

    @Shadow
    @Nullable
    public Direction getOrientation()
    {
        return this.orientation;
    }
}
