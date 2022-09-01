package tfcflorae.world.feature;

import java.util.function.Function;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.world.Codecs;
import net.dries007.tfc.world.feature.tree.RandomTreeConfig;

import tfcflorae.world.feature.tree.DynamicTreeConfig;
import tfcflorae.world.feature.tree.baobab.BaobabTreeFeature;
import tfcflorae.world.feature.tree.cypress.CypressTreeFeature;
import tfcflorae.world.feature.tree.mangrove.MangroveTreeFeature;
import tfcflorae.world.feature.tree.willow.BlackWillowTreeFeature;

import static tfcflorae.TFCFlorae.MOD_ID;

@SuppressWarnings("unused")
public class TFCFFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MOD_ID);

    public static final RegistryObject<BaobabTreeFeature> BAOBAB_TREE = register("baobab_tree", BaobabTreeFeature::new, DynamicTreeConfig.CODEC);
    public static final RegistryObject<CypressTreeFeature> CYPRESS_TREE = register("cypress_tree", CypressTreeFeature::new, DynamicTreeConfig.CODEC);
    public static final RegistryObject<MangroveTreeFeature> MANGROVE_TREE = register("mangrove_tree", MangroveTreeFeature::new, DynamicTreeConfig.CODEC);
    public static final RegistryObject<BlackWillowTreeFeature> BLACK_WILLOW_TREE = register("black_willow_tree", BlackWillowTreeFeature::new, DynamicTreeConfig.CODEC);

    private static <C extends FeatureConfiguration, F extends Feature<C>> RegistryObject<F> register(String name, Function<Codec<C>, F> factory, Codec<C> codec)
    {
        return FEATURES.register(name, () -> factory.apply(codec));
    }
}