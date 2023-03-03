package tfcflorae.world.feature.tree;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TrunkVineDecorator;

import tfcflorae.world.feature.TFCFFeatures;

public class BambooLeavesDecorator extends TrunkVineDecorator
{
    public static final Codec<BambooLeavesDecorator> CODEC = BlockStateProvider.CODEC.fieldOf("block_provider").xmap(BambooLeavesDecorator::new, decorator -> {
        return decorator.inputState;
    }).codec();

    private final BlockStateProvider inputState;

    public BambooLeavesDecorator(BlockStateProvider inputState)
    {
        this.inputState = inputState;
    }

    @Override
    protected TreeDecoratorType<?> type()
    {
        return TFCFFeatures.BAMBOO_LEAVES.get();
    }

    @Override
    public void place(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> biConsumer, Random random, List<BlockPos> listBlockPos, List<BlockPos> listBlockPos2)
    {
        BlockPos newpos = listBlockPos.get(listBlockPos.size() - 1);
        BlockPos up1 = new BlockPos(newpos.getX(), newpos.getY() + 5, newpos.getZ());
        BlockPos up2 = new BlockPos(newpos.getX(), newpos.getY() + 6, newpos.getZ());
        BlockPos px1 = new BlockPos(newpos.getX() + 2, newpos.getY(), newpos.getZ());
        BlockPos px2 = new BlockPos(newpos.getX() + 2, newpos.getY(), newpos.getZ() + 1);
        BlockPos px3 = new BlockPos(newpos.getX() + 2, newpos.getY(), newpos.getZ() - 1);
        BlockPos nx1 = new BlockPos(newpos.getX() - 2, newpos.getY(), newpos.getZ());
        BlockPos nx2 = new BlockPos(newpos.getX() - 2, newpos.getY(), newpos.getZ() + 1);
        BlockPos nx3 = new BlockPos(newpos.getX() - 2, newpos.getY(), newpos.getZ() - 1);
        BlockPos pz1 = new BlockPos(newpos.getX(), newpos.getY(), newpos.getZ() + 2);
        BlockPos pz2 = new BlockPos(newpos.getX() + 1, newpos.getY(), newpos.getZ() + 2);
        BlockPos pz3 = new BlockPos(newpos.getX() - 1, newpos.getY(), newpos.getZ() + 2);
        BlockPos nz1 = new BlockPos(newpos.getX(), newpos.getY(), newpos.getZ() - 2);
        BlockPos nz2 = new BlockPos(newpos.getX() + 1, newpos.getY(), newpos.getZ() - 2);
        BlockPos nz3 = new BlockPos(newpos.getX() - 1, newpos.getY(), newpos.getZ() - 2);
        BlockPos px = new BlockPos(newpos.getX() + 1, newpos.getY(), newpos.getZ());
        BlockPos nx = new BlockPos(newpos.getX() - 1, newpos.getY(), newpos.getZ());
        BlockPos pz = new BlockPos(newpos.getX(), newpos.getY(), newpos.getZ() + 1);
        BlockPos nz = new BlockPos(newpos.getX(), newpos.getY(), newpos.getZ() - 1);
        BlockPos pxt = px.above();
        BlockPos nxt = nx.above();
        BlockPos pzt = pz.above();
        BlockPos nzt = nz.above();
        BlockPos pxq = pxt.above();
        BlockPos nxq = nxt.above();
        BlockPos pzq = pzt.above();
        BlockPos nzq = nzt.above();
        BlockPos pxc = pxq.above();
        BlockPos nxc = nxq.above();
        BlockPos pzc = pzq.above();
        BlockPos nzc = nzq.above();
        BlockPos pxz = pxc.above();
        BlockPos nxz = nxc.above();
        BlockPos pzz = pzc.above();
        BlockPos nzz = nzc.above();
        BlockPos pxo = px.below();
        BlockPos nxo = nx.below();
        BlockPos pzo = pz.below();
        BlockPos nzo = nz.below();
        BlockPos pxm = pxo.below();
        BlockPos nxm = nxo.below();
        BlockPos pzm = pzo.below();
        BlockPos nzm = nzo.below();
        BlockPos pxp = pxm.below();
        BlockPos nxp = nxm.below();
        BlockPos pzp = pzm.below();
        BlockPos nzp = nzm.below();

        if (Feature.isAir(levelReader, px1))
            biConsumer.accept(px1, inputState.getState(random, px1)); 
        if (Feature.isAir(levelReader, px2))
            biConsumer.accept(px2,  inputState.getState(random, px2)); 
        if (Feature.isAir(levelReader, px3))
            biConsumer.accept(px3,  inputState.getState(random, px3)); 
        if (Feature.isAir(levelReader, nx1))
            biConsumer.accept(nx1,  inputState.getState(random, nx1)); 
        if (Feature.isAir(levelReader, nx2))
            biConsumer.accept(nx2,  inputState.getState(random, nx2)); 
        if (Feature.isAir(levelReader, nx3))
            biConsumer.accept(nx3,  inputState.getState(random, nx3)); 
        if (Feature.isAir(levelReader, pz1))
            biConsumer.accept(pz1,  inputState.getState(random, pz1)); 
        if (Feature.isAir(levelReader, pz2))
            biConsumer.accept(pz2,  inputState.getState(random, pz2)); 
        if (Feature.isAir(levelReader, pz3))
            biConsumer.accept(pz3,  inputState.getState(random, pz3)); 
        if (Feature.isAir(levelReader, nz1))
            biConsumer.accept(nz1,  inputState.getState(random, nz1)); 
        if (Feature.isAir(levelReader, nz2))
            biConsumer.accept(nz2,  inputState.getState(random, nz2)); 
        if (Feature.isAir(levelReader, nz3))
            biConsumer.accept(nz3,  inputState.getState(random, nz3)); 
        if (Feature.isAir(levelReader, px))
            biConsumer.accept(px,  inputState.getState(random, px)); 
        if (Feature.isAir(levelReader, nx))
            biConsumer.accept(nx,  inputState.getState(random, nx)); 
        if (Feature.isAir(levelReader, pz))
            biConsumer.accept(pz,  inputState.getState(random, pz)); 
        if (Feature.isAir(levelReader, nz))
            biConsumer.accept(nz,  inputState.getState(random, nz)); 
        if (Feature.isAir(levelReader, pxt))
            biConsumer.accept(pxt,  inputState.getState(random, pxt)); 
        if (Feature.isAir(levelReader, nxt))
            biConsumer.accept(nxt,  inputState.getState(random, nxt)); 
        if (Feature.isAir(levelReader, pzt))
            biConsumer.accept(pzt,  inputState.getState(random, pzt)); 
        if (Feature.isAir(levelReader, nzt))
            biConsumer.accept(nzt,  inputState.getState(random, nzt)); 
        if (Feature.isAir(levelReader, pxq))
            biConsumer.accept(pxq,  inputState.getState(random, pxq)); 
        if (Feature.isAir(levelReader, nxq))
            biConsumer.accept(nxq,  inputState.getState(random, nxq)); 
        if (Feature.isAir(levelReader, pzq))
            biConsumer.accept(pzq,  inputState.getState(random, pzq)); 
        if (Feature.isAir(levelReader, nzq))
            biConsumer.accept(nzq,  inputState.getState(random, nzq)); 
        if (Feature.isAir(levelReader, pxc))
            biConsumer.accept(pxc,  inputState.getState(random, pxc)); 
        if (Feature.isAir(levelReader, nxc))
            biConsumer.accept(nxc,  inputState.getState(random, nxc)); 
        if (Feature.isAir(levelReader, pzc))
            biConsumer.accept(pzc,  inputState.getState(random, pzc)); 
        if (Feature.isAir(levelReader, nzc))
            biConsumer.accept(nzc,  inputState.getState(random, nzc)); 
        if (Feature.isAir(levelReader, pxz))
            biConsumer.accept(pxz,  inputState.getState(random, pxz)); 
        if (Feature.isAir(levelReader, nxz))
            biConsumer.accept(nxz,  inputState.getState(random, nxz)); 
        if (Feature.isAir(levelReader, pzz))
            biConsumer.accept(pzz,  inputState.getState(random, pzz)); 
        if (Feature.isAir(levelReader, nzz))
            biConsumer.accept(nzz,  inputState.getState(random, nzz)); 
        if (Feature.isAir(levelReader, up1))
            biConsumer.accept(up1,  inputState.getState(random, up1)); 
        if (Feature.isAir(levelReader, up2))
            biConsumer.accept(up2,  inputState.getState(random, up2)); 
        if (Feature.isAir(levelReader, pxo))
            biConsumer.accept(pxo,  inputState.getState(random, pxo)); 
        if (Feature.isAir(levelReader, nxo))
            biConsumer.accept(nxo,  inputState.getState(random, nxo)); 
        if (Feature.isAir(levelReader, pzo))
            biConsumer.accept(pzo,  inputState.getState(random, pzo)); 
        if (Feature.isAir(levelReader, nzo))
            biConsumer.accept(nzo,  inputState.getState(random, nzo)); 
        if (Feature.isAir(levelReader, pxm))
            biConsumer.accept(pxm,  inputState.getState(random, pxm)); 
        if (Feature.isAir(levelReader, nxm))
            biConsumer.accept(nxm,  inputState.getState(random, nxm)); 
        if (Feature.isAir(levelReader, pzm))
            biConsumer.accept(pzm,  inputState.getState(random, pzm)); 
        if (Feature.isAir(levelReader, nzm))
            biConsumer.accept(nzm,  inputState.getState(random, nzm)); 
        if (Feature.isAir(levelReader, pxp))
            biConsumer.accept(pxp,  inputState.getState(random, pxp)); 
        if (Feature.isAir(levelReader, nxp))
            biConsumer.accept(nxp,  inputState.getState(random, nxp)); 
        if (Feature.isAir(levelReader, pzp))
            biConsumer.accept(pzp,  inputState.getState(random, pzp)); 
        if (Feature.isAir(levelReader, nzp))
            biConsumer.accept(nzp,  inputState.getState(random, nzp));
    }
}
