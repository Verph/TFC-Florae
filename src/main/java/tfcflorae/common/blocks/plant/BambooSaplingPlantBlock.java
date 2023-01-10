package tfcflorae.common.blocks.plant;

import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraftforge.common.ToolActions;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.PlantBlock;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.registry.RegistryPlant;

public abstract class BambooSaplingPlantBlock extends PlantBlock
{
    protected static final float SAPLING_AABB_OFFSET = 4.0F;
    protected static final VoxelShape SAPLING_SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D);

    private final Supplier<? extends Block> bambooStem;

    public static BambooSaplingPlantBlock create(RegistryPlant plant, ExtendedProperties properties, Supplier<? extends Block> bambooStem)
    {
        return new BambooSaplingPlantBlock(properties, bambooStem)
        {
            @Override
            public RegistryPlant getPlant()
            {
                return plant;
            }
        };
    }

    protected BambooSaplingPlantBlock(ExtendedProperties properties, Supplier<? extends Block> bambooStem)
    {
        super(properties);

        this.bambooStem = bambooStem;

        this.registerDefaultState(getStateDefinition().any());
    }

    @Override
    public BlockBehaviour.OffsetType getOffsetType()
    {
        return BlockBehaviour.OffsetType.XZ;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        Vec3 vec3 = state.getOffset(level, pos);
        return SAPLING_SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random)
    {
        if (random.nextDouble() < TFCConfig.SERVER.plantGrowthChance.get() && level.isEmptyBlock(pos.above()) && level.getRawBrightness(pos.above(), 0) >= 9)
        {
            this.growBamboo(level, pos);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        return super.canSurvive(state, level, pos) || level.getBlockState(pos.below()).is(BlockTags.BAMBOO_PLANTABLE_ON);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        if (!state.canSurvive(level, currentPos))
        {
            return Blocks.AIR.defaultBlockState();
        }
        else
        {
            if (facing == Direction.UP && facingState.is(bambooStem.get()))
            {
                level.setBlock(currentPos, bambooStem.get().defaultBlockState(), 2);
            }
            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        }
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos)
    {
        return player.getMainHandItem().canPerformAction(ToolActions.SWORD_DIG) ? 1.0F : super.getDestroyProgress(state, player, level, pos);
    }

    public void growBamboo(Level level, BlockPos state)
    {
        level.setBlock(state.above(), bambooStem.get().defaultBlockState().setValue(BambooPlantBlock.LEAVES, BambooLeaves.SMALL), 3);
    }
}
