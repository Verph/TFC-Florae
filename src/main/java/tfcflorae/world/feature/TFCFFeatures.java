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
<<<<<<< Updated upstream
=======

    public static final RegistryObject<PoisonTallPlantFeature> POISON_TALL_PLANT = register("poison_tall_plant", PoisonTallPlantFeature::new, PoisonTallPlantFeature.CODEC);
    public static final RegistryObject<LandEmergentPlantFeature> LAND_EMERGENT_PLANT = register("land_emergent_plant", LandEmergentPlantFeature::new, LandEmergentPlantFeature.CODEC);

    public static final RegistryObject<RockColumnFeature> ROCK_COLUMN = register("rock_column", RockColumnFeature::new, RockColumnConfig.CODEC);
    public static final RegistryObject<RockPillarFeature> ROCK_PILLAR = register("rock_pillar", RockPillarFeature::new, RockPillarConfig.CODEC);
    public static final RegistryObject<SnowAndFreezeFeature> FREEZE_TOP_LAYER = register("freeze_top_layer", SnowAndFreezeFeature::new, NoneFeatureConfiguration.CODEC);
    public static final RegistryObject<UnderwaterMagmaFeature> UNDERWATER_MAGMA = register("underwater_magma", UnderwaterMagmaFeature::new, UnderwaterMagmaConfig.CODEC);
    public static final RegistryObject<DripstoneClusterFeature> DRIPSTONE_CLUSTER = register("dripstone_cluster", DripstoneClusterFeature::new, DripstoneClusterConfiguration.CODEC);
    public static final RegistryObject<LargeDripstoneFeature> LARGE_DRIPSTONE = register("large_dripstone", LargeDripstoneFeature::new, LargeDripstoneConfiguration.CODEC);
    public static final RegistryObject<PointedDripstoneFeature> POINTED_DRIPSTONE = register("pointed_dripstone", PointedDripstoneFeature::new, PointedDripstoneConfiguration.CODEC);
    public static final RegistryObject<LakeFeature> LAKE = register("lake", LakeFeature::new, LakeConfig.CODEC);
>>>>>>> Stashed changes

    private static <C extends FeatureConfiguration, F extends Feature<C>> RegistryObject<F> register(String name, Function<Codec<C>, F> factory, Codec<C> codec)
    {
        return FEATURES.register(name, () -> factory.apply(codec));
    }
}