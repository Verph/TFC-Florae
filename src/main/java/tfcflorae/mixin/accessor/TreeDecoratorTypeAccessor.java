package tfcflorae.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

@Mixin(TreeDecoratorType.class)
public interface TreeDecoratorTypeAccessor
{
    @Invoker("<init>")
    static <P extends TreeDecorator> TreeDecoratorType<P> createTreeDecoratorType(Codec<P> codec)
    {
        throw new UnsupportedOperationException();
    }
}
