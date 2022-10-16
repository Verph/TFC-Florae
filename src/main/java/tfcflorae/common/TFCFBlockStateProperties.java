package tfcflorae.common;

import net.minecraft.world.level.block.state.properties.EnumProperty;

import tfcflorae.common.blocks.plant.TFCFITallPlant;

public class TFCFBlockStateProperties
{    
    public static final EnumProperty<TFCFITallPlant.Part> TALL_PLANT_PART = EnumProperty.create("part", TFCFITallPlant.Part.class);
}
