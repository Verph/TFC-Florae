package tfcflorae.world.feature;

import java.util.function.Function;
import java.util.function.Supplier;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import net.dries007.tfc.world.feature.HotSpringConfig;
import net.dries007.tfc.world.feature.tree.ForestConfig;
import net.dries007.tfc.world.feature.tree.RandomTreeConfig;

import tfcflorae.mixin.accessor.TreeDecoratorTypeAccessor;
import tfcflorae.mixin.accessor.TrunkPlacerTypeAccessor;
import tfcflorae.world.feature.plant.*;
import tfcflorae.world.feature.tree.*;
import tfcflorae.world.feature.tree.baobab.*;
import tfcflorae.world.feature.tree.joshua.*;
import tfcflorae.world.feature.tree.mangrove.*;

import static tfcflorae.TFCFlorae.MOD_ID;

@SuppressWarnings("deprecation")
public class TFCFFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, MOD_ID);
    public static final TFCFAbstractDeferredRegistry<TrunkPlacerType<?>> TRUNK_DECOR = TFCFDeferredRegistry.create(Registry.TRUNK_PLACER_TYPES, MOD_ID);
    public static final TFCFAbstractDeferredRegistry<TreeDecoratorType<?>> LEAF_DECOR = TFCFDeferredRegistry.create(Registry.TREE_DECORATOR_TYPES, MOD_ID);

    public static final Supplier<TrunkPlacerType<UpwardBranchingTrunk>> UPWARDS_BRANCHING_TRUNK = TRUNK_DECOR.register("upward_branching_trunk", () -> TrunkPlacerTypeAccessor.createTrunkPlacerType(UpwardBranchingTrunk.CODEC));
    public static final Supplier<TreeDecoratorType<WeightedLeaveVineDecorator>> WEIGHTED_LEAVE_VINE = LEAF_DECOR.register("leave_vine", () -> TreeDecoratorTypeAccessor.createTreeDecoratorType(WeightedLeaveVineDecorator.CODEC));
    public static final Supplier<TreeDecoratorType<AttachedToLeavesDecorator>> ATTACHED_TO_LEAVES = LEAF_DECOR.register("attached_to_leaves", () -> TreeDecoratorTypeAccessor.createTreeDecoratorType(AttachedToLeavesDecorator.CODEC));
    public static final RegistryObject<Feature<RootedTreeConfig>> VANILLA_MANGROVE_TREE = register("vanilla_mangrove_tree", RootedTreeFeature::new, RootedTreeConfig.CODEC);

    public static final RegistryObject<JoshuaTreeFeature> JOSHUA_TREE = register("joshua_tree", JoshuaTreeFeature::new, JoshuaTreeFeature.CODEC);
    public static final RegistryObject<BaobabTreeFeature> BAOBAB_TREE = register("baobab_tree", BaobabTreeFeature::new, DynamicTreeConfig.CODEC);
    public static final RegistryObject<TFCFRandomTreeFeature> RANDOM_TREE = register("random_tree", TFCFRandomTreeFeature::new, RandomTreeConfig.CODEC);

    public static final RegistryObject<PoisonTallPlantFeature> POISON_TALL_PLANT = register("poison_tall_plant", PoisonTallPlantFeature::new, PoisonTallPlantFeature.CODEC);
    public static final RegistryObject<LandEmergentPlantFeature> LAND_EMERGENT_PLANT = register("land_emergent_plant", LandEmergentPlantFeature::new, LandEmergentPlantFeature.CODEC);

    public static final RegistryObject<BlueBambooFeature> BLUE_BAMBOO = register("blue_bamboo", BlueBambooFeature::new, ProbabilityFeatureConfiguration.CODEC);
    public static final RegistryObject<DragonBambooFeature> DRAGON_BAMBOO = register("dragon_bamboo", DragonBambooFeature::new, ProbabilityFeatureConfiguration.CODEC);
    public static final RegistryObject<GoldenBambooFeature> GOLDEN_BAMBOO = register("golden_bamboo", GoldenBambooFeature::new, ProbabilityFeatureConfiguration.CODEC);
    public static final RegistryObject<RedBambooFeature> RED_BAMBOO = register("red_bamboo", RedBambooFeature::new, ProbabilityFeatureConfiguration.CODEC);
    public static final RegistryObject<HotSpringFeature> HOT_SPRING = register("hot_spring", HotSpringFeature::new, HotSpringConfig.CODEC);
    public static final RegistryObject<MonsterRoomFeature> MONSTER_ROOM = register("monster_room", MonsterRoomFeature::new, RockConfig.CODEC);
    public static final RegistryObject<ForestFeature> FOREST = register("forest", ForestFeature::new, ForestConfig.CODEC);
    public static final RegistryObject<ShrublandFeature> SHRUBLAND = register("shrubland", ShrublandFeature::new, ShrublandConfig.CODEC);
    public static final RegistryObject<SubsurfaceForestFeature> SUBSURFACE_FOREST = register("subsurface_forest", SubsurfaceForestFeature::new, ForestConfig.CODEC);
    public static final RegistryObject<GlacierFeature> GLACIER = register("glacier", GlacierFeature::new, BlockStateConfiguration.CODEC);
    public static final RegistryObject<CrystalFeature> CRYSTAL = register("crystal", CrystalFeature::new, BlockStateConfiguration.CODEC);
    public static final RegistryObject<BoulderRockFeature> BOULDER_BLOCK = register("boulder_block", BoulderRockFeature::new, RockConfig.CODEC);
    public static final RegistryObject<BoulderDeepRockFeature> DEEP_BOULDER_BLOCK = register("deep_boulder_block", BoulderDeepRockFeature::new, RockConfig.CODEC);
    public static final RegistryObject<LooseFlintFeature> LOOSE_FLINT = register("loose_flint", LooseFlintFeature::new, NoneFeatureConfiguration.CODEC);
    public static final RegistryObject<RockColumnFeature> ROCK_COLUMN = register("rock_column", RockColumnFeature::new, RockColumnConfig.CODEC);
    public static final RegistryObject<RockPillarFeature> ROCK_PILLAR = register("rock_pillar", RockPillarFeature::new, RockPillarConfig.CODEC);
    public static final RegistryObject<SnowAndFreezeFeature> FREEZE_TOP_LAYER = register("freeze_top_layer", SnowAndFreezeFeature::new, NoneFeatureConfiguration.CODEC);
    public static final RegistryObject<UnderwaterMagmaFeature> UNDERWATER_MAGMA = register("underwater_magma", UnderwaterMagmaFeature::new, UnderwaterMagmaConfig.CODEC);
    public static final RegistryObject<DripstoneClusterFeature> DRIPSTONE_CLUSTER = register("dripstone_cluster", DripstoneClusterFeature::new, DripstoneClusterConfiguration.CODEC);
    public static final RegistryObject<LargeDripstoneFeature> LARGE_DRIPSTONE = register("large_dripstone", LargeDripstoneFeature::new, LargeDripstoneConfiguration.CODEC);
    public static final RegistryObject<PointedDripstoneFeature> POINTED_DRIPSTONE = register("pointed_dripstone", PointedDripstoneFeature::new, PointedDripstoneConfiguration.CODEC);
    public static final RegistryObject<LakeFeature> LAKE = register("lake", LakeFeature::new, LakeConfig.CODEC);
    public static final RegistryObject<SaguaroCactusFeature> SAGUARO_CACTUS_FEATURE = register ("saguaro_cactus",  SaguaroCactusFeature::new, SaguaroCactusFeature.CODEC);
    public static final RegistryObject<ColumnFeature> BLOCK_COLUMN = register ("block_column",  ColumnFeature::new, BlockColumnConfiguration.CODEC);

    private static <C extends FeatureConfiguration, F extends Feature<C>> RegistryObject<F> register(String name, Function<Codec<C>, F> factory, Codec<C> codec)
    {
        return FEATURES.register(name, () -> factory.apply(codec));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> config(String key, F feature, FC configuration)
    {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, MOD_ID + ":" + key, new ConfiguredFeature<>(feature, configuration));
    }
}