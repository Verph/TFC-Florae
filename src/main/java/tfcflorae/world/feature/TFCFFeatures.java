package tfcflorae.world.feature;

import java.util.function.Function;

import com.mojang.serialization.Codec;
<<<<<<< Updated upstream
=======
import net.minecraft.core.Registry;
import net.dries007.tfc.world.feature.tree.RandomTreeConfig;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
>>>>>>> Stashed changes
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.world.Codecs;
import net.dries007.tfc.world.feature.tree.RandomTreeConfig;

import tfcflorae.world.feature.tree.DynamicTreeConfig;
<<<<<<< Updated upstream
import tfcflorae.world.feature.tree.baobab.BaobabTreeFeature;
import tfcflorae.world.feature.tree.cypress.CypressTreeFeature;
import tfcflorae.world.feature.tree.mangrove.MangroveTreeFeature;
import tfcflorae.world.feature.tree.willow.BlackWillowTreeFeature;
=======
import tfcflorae.world.feature.tree.RootedTreeConfig;
import tfcflorae.world.feature.tree.TFCFRandomTreeFeature;
import tfcflorae.world.feature.tree.baobab.BaobabTreeFeature;
import tfcflorae.world.feature.tree.joshua.JoshuaTreeFeature;
import tfcflorae.world.feature.tree.mangrove.*;
>>>>>>> Stashed changes

import static tfcflorae.TFCFlorae.MOD_ID;

@SuppressWarnings("unused")
public class TFCFFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MOD_ID);

    public static final RegistryObject<BaobabTreeFeature> BAOBAB_TREE = register("baobab_tree", BaobabTreeFeature::new, DynamicTreeConfig.CODEC);
    public static final RegistryObject<TFCFRandomTreeFeature> RANDOM_TREE = register("random_tree", TFCFRandomTreeFeature::new, RandomTreeConfig.CODEC);

    private static <C extends FeatureConfiguration, F extends Feature<C>> RegistryObject<F> register(String name, Function<Codec<C>, F> factory, Codec<C> codec)
    {
        return FEATURES.register(name, () -> factory.apply(codec));
    }
}