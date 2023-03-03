package tfcflorae;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

import tfcflorae.common.TFCFTags;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.LooseFlintBlock;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.blocks.soil.TFCFRockSand;
import tfcflorae.common.blocks.soil.TFCFRockSoil;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.common.blocks.spidercave.EggBlock;
import tfcflorae.common.items.TFCFItems;

import com.mojang.logging.LogUtils;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.LooseRockBlock;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SandBlockType;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.util.EnvironmentHelpers;
import net.dries007.tfc.util.Helpers;

import org.slf4j.Logger;

public class TFCFForgeEventHandler
{
    public static void init()
    {
        final IEventBus bus = MinecraftForge.EVENT_BUS;

        bus.addListener(TFCFForgeEventHandler::onPlayerRightClickBlock);
        //bus.addListener(EventPriority.HIGHEST, TFCFForgeEventHandler::breakBlock);
    }

    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        final Level level = event.getWorld();
        final BlockPos pos = event.getPos();
        final BlockState state = level.getBlockState(pos);
        final Block block = state.getBlock();

        if (level.getBlockState(pos).getMaterial().isSolid() && (EnvironmentHelpers.isWorldgenReplaceable(level.getBlockState(pos.above())) || level.getBlockState(pos.above()).isAir()) && (level.getBlockState(pos.above()).getBlock() != TFCFBlocks.LOOSE_FLINT.get() || !(level.getBlockState(pos.above()).getBlock() instanceof LooseRockBlock)) && TFCFBlocks.LOOSE_FLINT.get().defaultBlockState().canSurvive(level, pos.above()))
        {
            if (event.getHand() == InteractionHand.MAIN_HAND && (Helpers.isItem(event.getItemStack(), TFCFTags.Items.FLINT_KNAPPING) || event.getItemStack().getItem() == Items.FLINT || event.getItemStack().getItem() == TFCFBlocks.LOOSE_FLINT.get().asItem()))
            {
                event.getItemStack().shrink(1);
                //event.getPlayer().playSound(SoundType.STONE.getPlaceSound(), 1f, 1f);
                Helpers.playSound(level, pos.above(), SoundType.STONE.getPlaceSound());
                level.setBlockAndUpdate(pos.above(), TFCFBlocks.LOOSE_FLINT.get().defaultBlockState().setValue(LooseFlintBlock.COUNT, 1));
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }

        for (SoilBlockType.Variant variant : SoilBlockType.Variant.values())
        {
            // Mud --> Compact Mud
            if (block == TFCBlocks.SOIL.get(SoilBlockType.MUD).get(variant).get())
            {
                if (event.getHand() == InteractionHand.MAIN_HAND && event.getItemStack().canPerformAction(ToolActions.SHOVEL_FLATTEN))
                {
                    level.setBlockAndUpdate(pos, TFCFBlocks.TFCSOIL.get(TFCFSoil.PACKED_MUD).get(variant).get().defaultBlockState());
                    event.setCancellationResult(InteractionResult.SUCCESS);
                }
            }
            // Soil --> Compact Soil
            if (block == TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant).get())
            {
                if (event.getHand() == InteractionHand.MAIN_HAND && event.getItemStack().canPerformAction(ToolActions.SHOVEL_FLATTEN))
                {
                    level.setBlockAndUpdate(pos, TFCFBlocks.TFCSOIL.get(TFCFSoil.COMPACT_DIRT).get(variant).get().defaultBlockState());
                    event.setCancellationResult(InteractionResult.SUCCESS);
                }
            }
        }
        for (TFCFSoil.TFCFVariant variant : TFCFSoil.TFCFVariant.values())
        {
            // Soil --> Compact Soil
            if (block == TFCFBlocks.TFCFSOIL.get(TFCFSoil.DIRT).get(variant).get())
            {
                if (event.getHand() == InteractionHand.MAIN_HAND && event.getItemStack().canPerformAction(ToolActions.SHOVEL_FLATTEN))
                {
                    level.setBlockAndUpdate(pos, TFCFBlocks.TFCFSOIL.get(TFCFSoil.COMPACT_DIRT).get(variant).get().defaultBlockState());
                    event.setCancellationResult(InteractionResult.SUCCESS);
                }
            }
        }

        // Soil --> Compact Soil
        for (Rock rockTFC : Rock.values())
        {
            // Soil
            for (SoilBlockType.Variant variant : SoilBlockType.Variant.values())
            {
                if (block == TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant).get())
                {
                    if (Helpers.isItem(event.getItemStack(), TFCBlocks.ROCK_BLOCKS.get(rockTFC).get(Rock.BlockType.LOOSE).get().asItem()))
                    {
                        event.getItemStack().shrink(1);
                        final BlockState placedBlock = TFCFBlocks.TFCROCKSOIL.get(TFCFRockSoil.PEBBLE_COMPACT_DIRT).get(variant).get(rockTFC).get().defaultBlockState();
                        level.setBlockAndUpdate(pos, placedBlock);
                        Helpers.playSound(level, pos, SoundType.BASALT.getPlaceSound());
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        break;
                    }
                }
                if (block == TFCFBlocks.ROCK_BLOCKS.get(rockTFC).get(TFCFRock.TFCFBlockType.STONE_TILES).get())
                {
                    if (Helpers.isItem(event.getItemStack(), TFCFItems.SOIL_PILE_TFC.get(variant).get()))
                    {
                        event.getItemStack().shrink(1);
                        final BlockState placedBlock = TFCFBlocks.TFCROCKSOIL.get(TFCFRockSoil.DIRTY_STONE_TILES).get(variant).get(rockTFC).get().defaultBlockState();
                        level.setBlockAndUpdate(pos, placedBlock);
                        Helpers.playSound(level, pos, SoundType.ROOTS.getPlaceSound());
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        break;
                    }
                }
            }
            for (TFCFSoil.TFCFVariant variant : TFCFSoil.TFCFVariant.values())
            {
                if (block == TFCFBlocks.TFCFSOIL.get(TFCFSoil.DIRT).get(variant).get())
                {
                    if (Helpers.isItem(event.getItemStack(), TFCBlocks.ROCK_BLOCKS.get(rockTFC).get(Rock.BlockType.LOOSE).get().asItem()))
                    {
                        event.getItemStack().shrink(1);
                        final BlockState placedBlock = TFCFBlocks.TFCFROCKSOIL.get(TFCFRockSoil.PEBBLE_COMPACT_DIRT).get(variant).get(rockTFC).get().defaultBlockState();
                        level.setBlockAndUpdate(pos, placedBlock);
                        Helpers.playSound(level, pos, SoundType.BASALT.getPlaceSound());
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        break;
                    }
                }
                if (block == TFCFBlocks.ROCK_BLOCKS.get(rockTFC).get(TFCFRock.TFCFBlockType.STONE_TILES).get())
                {
                    if (Helpers.isItem(event.getItemStack(), TFCFItems.SOIL_PILE_TFCF.get(variant).get()))
                    {
                        event.getItemStack().shrink(1);
                        final BlockState placedBlock = TFCFBlocks.TFCFROCKSOIL.get(TFCFRockSoil.DIRTY_STONE_TILES).get(variant).get(rockTFC).get().defaultBlockState();
                        level.setBlockAndUpdate(pos, placedBlock);
                        Helpers.playSound(level, pos, SoundType.ROOTS.getPlaceSound());
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        break;
                    }
                }
            }

            // Sand
            for (SandBlockType sandColor : SandBlockType.values())
            {
                if (block == TFCBlocks.SAND.get(sandColor).get())
                {
                    if (Helpers.isItem(event.getItemStack(), TFCBlocks.ROCK_BLOCKS.get(rockTFC).get(Rock.BlockType.LOOSE).get().asItem()))
                    {
                        event.getItemStack().shrink(1);
                        final BlockState placedBlock = TFCFBlocks.ROCKY_SAND_TFC.get(TFCFRockSand.PEBBLE).get(sandColor).get(rockTFC).get().defaultBlockState();
                        level.setBlockAndUpdate(pos, placedBlock);
                        Helpers.playSound(level, pos, SoundType.BASALT.getPlaceSound());
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        break;
                    }
                }
                if (block == TFCFBlocks.ROCK_BLOCKS.get(rockTFC).get(TFCFRock.TFCFBlockType.STONE_TILES).get())
                {
                    if (Helpers.isItem(event.getItemStack(), TFCFItems.SAND_PILE_TFC.get(sandColor).get()))
                    {
                        event.getItemStack().shrink(1);
                        final BlockState placedBlock = TFCFBlocks.ROCKY_SAND_TFC.get(TFCFRockSand.SANDY_TILES).get(sandColor).get(rockTFC).get().defaultBlockState();
                        level.setBlockAndUpdate(pos, placedBlock);
                        Helpers.playSound(level, pos, SoundType.ROOTS.getPlaceSound());
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        break;
                    }
                }
            }
        }
        for (TFCFRock rockTFCF : TFCFRock.values())
        {
            for (SoilBlockType.Variant variant : SoilBlockType.Variant.values())
            {
                if (block == TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant).get())
                {
                    if (Helpers.isItem(event.getItemStack(), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rockTFCF).get(Rock.BlockType.LOOSE).get().asItem()))
                    {
                        event.getItemStack().shrink(1);
                        final BlockState placedBlock = TFCFBlocks.TFCROCKSOIL2.get(TFCFRockSoil.PEBBLE_COMPACT_DIRT).get(variant).get(rockTFCF).get().defaultBlockState();
                        level.setBlockAndUpdate(pos, placedBlock);
                        Helpers.playSound(level, pos, SoundType.BASALT.getPlaceSound());
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        break;
                    }
                }
                if (block == TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rockTFCF).get(TFCFRock.TFCFBlockType.STONE_TILES).get())
                {
                    if (Helpers.isItem(event.getItemStack(), TFCFItems.SOIL_PILE_TFC.get(variant).get()))
                    {
                        event.getItemStack().shrink(1);
                        final BlockState placedBlock = TFCFBlocks.TFCROCKSOIL2.get(TFCFRockSoil.DIRTY_STONE_TILES).get(variant).get(rockTFCF).get().defaultBlockState();
                        level.setBlockAndUpdate(pos, placedBlock);
                        Helpers.playSound(level, pos, SoundType.ROOTS.getPlaceSound());
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        break;
                    }
                }
            }
            for (TFCFSoil.TFCFVariant variant : TFCFSoil.TFCFVariant.values())
            {
                if (block == TFCFBlocks.TFCFSOIL.get(TFCFSoil.DIRT).get(variant).get())
                {
                    if (Helpers.isItem(event.getItemStack(), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rockTFCF).get(Rock.BlockType.LOOSE).get().asItem()))
                    {
                        event.getItemStack().shrink(1);
                        final BlockState placedBlock = TFCFBlocks.TFCFROCKSOIL2.get(TFCFRockSoil.PEBBLE_COMPACT_DIRT).get(variant).get(rockTFCF).get().defaultBlockState();
                        level.setBlockAndUpdate(pos, placedBlock);
                        Helpers.playSound(level, pos, SoundType.BASALT.getPlaceSound());
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        break;
                    }
                }
                if (block == TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rockTFCF).get(TFCFRock.TFCFBlockType.STONE_TILES).get())
                {
                    if (Helpers.isItem(event.getItemStack(), TFCFItems.SOIL_PILE_TFCF.get(variant).get()))
                    {
                        event.getItemStack().shrink(1);
                        final BlockState placedBlock = TFCFBlocks.TFCFROCKSOIL2.get(TFCFRockSoil.DIRTY_STONE_TILES).get(variant).get(rockTFCF).get().defaultBlockState();
                        level.setBlockAndUpdate(pos, placedBlock);
                        Helpers.playSound(level, pos, SoundType.ROOTS.getPlaceSound());
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        break;
                    }
                }
            }

            // Sand
            for (SandBlockType sandColor : SandBlockType.values())
            {
                if (block == TFCBlocks.SAND.get(sandColor).get())
                {
                    if (Helpers.isItem(event.getItemStack(), TFCFBlocks.TFCF_ROCK_BLOCKS.get(rockTFCF).get(Rock.BlockType.LOOSE).get().asItem()))
                    {
                        event.getItemStack().shrink(1);
                        final BlockState placedBlock = TFCFBlocks.ROCKY_SAND_TFCF.get(TFCFRockSand.PEBBLE).get(sandColor).get(rockTFCF).get().defaultBlockState();
                        level.setBlockAndUpdate(pos, placedBlock);
                        Helpers.playSound(level, pos, SoundType.BASALT.getPlaceSound());
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        break;
                    }
                }
                if (block == TFCFBlocks.TFCF_ROCKTYPE_BLOCKS.get(rockTFCF).get(TFCFRock.TFCFBlockType.STONE_TILES).get())
                {
                    if (Helpers.isItem(event.getItemStack(), TFCFItems.SAND_PILE_TFC.get(sandColor).get()))
                    {
                        event.getItemStack().shrink(1);
                        final BlockState placedBlock = TFCFBlocks.ROCKY_SAND_TFCF.get(TFCFRockSand.SANDY_TILES).get(sandColor).get(rockTFCF).get().defaultBlockState();
                        level.setBlockAndUpdate(pos, placedBlock);
                        Helpers.playSound(level, pos, SoundType.ROOTS.getPlaceSound());
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        break;
                    }
                }
            }
        }
        event.setCancellationResult(InteractionResult.PASS);
    }

    /*public static void breakBlock(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntity() != null && event.getEntity().getType() != EntityType.SPIDER && event.getEntity().getType() != EntityType.CAVE_SPIDER && event.getEntity().getType() != EntityType.SILVERFISH && event.getEntity().getType() != EntityType.ENDERMITE)
        {
            final LivingEntity entity = event.getEntityLiving();
            final BlockPos posEntity = entity.blockPosition().below();
            final Level level = entity.getLevel();

            double mob_speed = entity.getSpeed();

            if (level != null && mob_speed > 0.08f)
            {
                if (level.getBlockState(posEntity).getBlock() instanceof EggBlock)
                {
                    level.destroyBlock(posEntity, true);
                    return;
                }
            }
        }
    }*/
}
