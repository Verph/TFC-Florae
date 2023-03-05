package tfcflorae.common.blocks.soil;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.soil.FarmlandBlock;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.util.registry.RegistrySoilVariant;

import tfcflorae.common.blockentities.TFCFBlockEntities;

public class TFCFFarmlandBlock extends FarmlandBlock
{
    public TFCFFarmlandBlock(ExtendedProperties properties, Supplier<? extends Block> dirt)
    {
        super(properties, dirt);
    }

    TFCFFarmlandBlock(ExtendedProperties properties, RegistrySoilVariant variant)
    {
        this(properties, variant.getBlock(SoilBlockType.DIRT));
    }

    @Override
    public void addHoeOverlayInfo(Level level, BlockPos pos, BlockState state, List<Component> text, boolean isDebug)
    {
        level.getBlockEntity(pos, TFCFBlockEntities.FARMLAND.get()).ifPresent(farmland -> farmland.addHoeOverlayInfo(level, pos, text, true, true));
    }
}
