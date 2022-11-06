package tfcflorae;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import tfcflorae.common.blocks.TFCFBlocks;
import tfcflorae.common.blocks.rock.TFCFRock;
import tfcflorae.common.blocks.soil.TFCFRockSoil;
import tfcflorae.common.blocks.soil.TFCFSoil;
import tfcflorae.common.items.TFCFItems;

import com.mojang.logging.LogUtils;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.util.Helpers;

import org.slf4j.Logger;

public class TFCFForgeEventHandler
{
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final BlockHitResult FAKE_MISS = BlockHitResult.miss(Vec3.ZERO, Direction.UP, BlockPos.ZERO);

    public static void init()
    {
        final IEventBus bus = MinecraftForge.EVENT_BUS;

        bus.addListener(TFCFForgeEventHandler::onPlayerRightClickBlock);
    }

    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        final Level level = event.getWorld();
        final BlockPos pos = event.getPos();
        final BlockState state = level.getBlockState(pos);
        final Block block = state.getBlock();

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
        }
        event.setCancellationResult(InteractionResult.PASS);
    }
}
