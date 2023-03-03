"""Test blendmodes."""

# For Blendmodes: https://github.com/FHPythonUtils/BlendModes/blob/master/documentation/tutorials/README.md

from __future__ import annotations
from blendmodes.blend import BlendType, blendLayers

import sys
import os
import functools
import operator
from pathlib import Path

from imgcompare import imgcompare
from PIL import Image, ImageOps, ImageColor, ImageEnhance, ImageFilter

THISDIR = str(Path(__file__).resolve().parent)
sys.path.insert(0, str(Path(THISDIR).parent))


# def blendRockBricks():
#    for filename in os.listdir(f"{THISDIR}/base/"):
#        background = Image.open(f"{THISDIR}/base/" + filename)
#        background.putalpha(255)
#        converter = ImageEnhance.Color(background)
#        img2 = converter.enhance(1)
#        backgroundBlur = img2.filter(
#            ImageFilter.GaussianBlur(radius=0.1))
#        foreground = Image.open(f"{THISDIR}/overlay/brick.png")
#        cut = Image.open(f"{THISDIR}/overlay/brick.png")
#        result = blendLayers(backgroundBlur, foreground, BlendType.HARDLIGHT)
#        resultCut = blendLayers(result, cut, BlendType.DESTIN)
#
#        resultCut.save(f"{THISDIR}/result/brick/" + filename)
#
#
# blendRockBricks()
#
# def blendImage():
#    for filename in os.listdir(f"{THISDIR}/base/"):
#        background = Image.open(f"{THISDIR}/base/" + filename)
#        background.putalpha(200)
#        converter = ImageEnhance.Color(background)
#        img2 = converter.enhance(1)
#        foreground = Image.open(f"{THISDIR}/overlay/gravel1.png")
#        backgroundBlur = img2.filter(
#            ImageFilter.GaussianBlur(radius=2))
#        result = blendLayers(backgroundBlur, foreground, BlendType.GRAINMERGE)
#        result.save(f"{THISDIR}/result/" + filename)
#
#
# def blendImageMossOverlay():
#    for filename in os.listdir(f"{THISDIR}/base/"):
#        background = Image.open(f"{THISDIR}/base/" + filename)
#        background.putalpha(255)
#        converter = ImageEnhance.Color(background)
#        img2 = converter.enhance(1)
#        foreground = Image.open(f"{THISDIR}/overlay/cobble.png")
#        backgroundBlur = img2.filter(
#            ImageFilter.GaussianBlur(radius=0.1))
#        result = blendLayers(backgroundBlur, foreground, BlendType.HARDLIGHT)
#        overlay = Image.open(
#            f"{THISDIR}/overlay/overlay_mossy_cobble.png")
#        resultOverlay = blendLayers(overlay, result, BlendType.DESTATOP)
#
#        resultOverlay.save(f"{THISDIR}/result/" + filename)


def imageMossOverlay():
    for filename in os.listdir(f"{THISDIR}/base/"):
        background = Image.open(f"{THISDIR}/base/" + filename)
        overlay = Image.open(
            f"{THISDIR}/overlay/overlay_mossy_cobble.png")
        resultOverlay = blendLayers(overlay, background, BlendType.DESTATOP)

        resultOverlay.save(f"{THISDIR}/result/" + filename)


def blendPlanksWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/planks_ash.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)

        result.save(f"{THISDIR}/result_wood/planks/" + filename)


blendPlanksWood()


def blendStrippedLogSideWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/sheet.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)

        result.save(f"{THISDIR}/result_wood/stripped_log_side/" + filename)


blendStrippedLogSideWood()


def blendStrippedLogTopWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/log_stripped_top.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)

        result.save(f"{THISDIR}/result_wood/stripped_log_top/" + filename)


blendStrippedLogTopWood()


def blendScribingTableWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/scribing_table.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)

        result.save(f"{THISDIR}/result_wood/scribing_table/" + filename)


blendScribingTableWood()


def blendLecternSideWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/lectern_sides.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)

        result.save(f"{THISDIR}/result_wood/lectern/sides/" + filename)


blendLecternSideWood()


def blendLecternTopWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/lectern_top.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)

        result.save(f"{THISDIR}/result_wood/lectern/top/" + filename)


blendLecternTopWood()


def blendLecternBaseWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/lectern_base.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/lectern_base_overlay.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(f"{THISDIR}/result_wood/lectern/base/" + filename)


blendLecternBaseWood()


def blendLecternFrontWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/lectern_front.png")
        resultPlanks = blendLayers(
            background, foreground, BlendType.GRAINMERGE)
        bookshelfBack = Image.open(
            f"{THISDIR}/overlay_wood/lectern_front_back.png")
        resultBack = blendLayers(
            background, bookshelfBack, BlendType.HARDLIGHT)
        resultCutBack = blendLayers(
            resultBack, bookshelfBack, BlendType.DESTATOP)
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/lectern_front_overlay.png")
        resultCutBookshelfBack = blendLayers(
            resultCutBack, overlay, BlendType.XOR)  # BlendType.NORMAL
        resultOverlay = blendLayers(
            resultCutBookshelfBack, overlay, BlendType.NORMAL)
        resultCutBackground = blendLayers(
            resultPlanks, resultOverlay, BlendType.SRCATOP)
        resultMerged = blendLayers(
            resultCutBackground, resultOverlay, BlendType.NORMAL)

        resultMerged.save(f"{THISDIR}/result_wood/lectern/front/" + filename)


blendLecternFrontWood()


def blendLumberWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_wood/lumber.png")
        cut = Image.open(f"{THISDIR}/overlay_wood/lumber.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)

        resultCut.save(f"{THISDIR}/result_wood/lumber/" + filename)


blendLumberWood()


def blendTwigWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_wood/twig.png")
        cut = Image.open(f"{THISDIR}/overlay_wood/twig.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)

        resultCut.save(f"{THISDIR}/result_wood/twig/" + filename)


blendTwigWood()


def blendBoatWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_wood/boat2.png")
        cut = Image.open(f"{THISDIR}/overlay_wood/boat2.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)

        resultCut.save(f"{THISDIR}/result_wood/boat/" + filename)


blendBoatWood()


def blendMinecartWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/chest_minecart_chest.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/chest_minecart_cart.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(
            f"{THISDIR}/result_wood/chest_minecart/" + filename)


blendMinecartWood()


def blendSignItemWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_wood/sign_head.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, foreground, BlendType.DESTATOP)

        backgroundLog = Image.open(f"{THISDIR}/base_bark/" + filename)
        foregroundLog = Image.open(
            f"{THISDIR}/overlay_wood/sign_mast.png")
        resultLog = blendLayers(
            backgroundLog, foregroundLog, BlendType.HARDLIGHT)
        resultCutLog = blendLayers(
            resultLog, foregroundLog, BlendType.DESTATOP)

        foregroundTextShadow = Image.open(
            f"{THISDIR}/overlay_wood/sign_head_text_shadow.png")
        resultTextShadow = blendLayers(
            background, foregroundTextShadow, BlendType.GRAINMERGE)
        resultTextShadowCut = blendLayers(
            resultTextShadow, foregroundTextShadow, BlendType.DESTATOP)

        resultMerge = blendLayers(
            resultCutLog, resultCut, BlendType.NORMAL)
        resultMergeTextShadow = blendLayers(
            resultMerge, resultTextShadowCut, BlendType.NORMAL)
        textOverlay = Image.open(f"{THISDIR}/overlay_wood/sign_head_text.png")
        resultMergeText = blendLayers(
            resultMergeTextShadow, textOverlay, BlendType.NORMAL)

        resultMergeText.save(f"{THISDIR}/result_wood/sign/" + filename)


blendSignItemWood()


def blendDoorItemWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/door_item.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/door_item_overlay.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(f"{THISDIR}/result_wood/door/" + filename)


blendDoorItemWood()


def blendDoorUpperWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/door_upper.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/door_upper_overlay.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(f"{THISDIR}/result_wood/door_upper/" + filename)


blendDoorUpperWood()


def blendDoorLowerWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/door_lower.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/door_lower_overlay.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(f"{THISDIR}/result_wood/door_lower/" + filename)


blendDoorLowerWood()


def blendTrapdoorWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/trapdoor.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/trapdoor_overlay.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(f"{THISDIR}/result_wood/trapdoor/" + filename)


blendTrapdoorWood()


def blendWorkbenchFrontWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/planks_ash.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/workbench_front.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(
            f"{THISDIR}/result_wood/workbench/front/" + filename)


blendWorkbenchFrontWood()


def blendWorkbenchSideWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/planks_ash.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/workbench_side.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(f"{THISDIR}/result_wood/workbench/side/" + filename)


blendWorkbenchSideWood()


def blendWorkbenchTopWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/planks_ash.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/workbench_top.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(f"{THISDIR}/result_wood/workbench/top/" + filename)


blendWorkbenchTopWood()


def blendBookshelfWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/lectern_front.png")
        resultPlanks = blendLayers(
            background, foreground, BlendType.GRAINMERGE)
        bookshelfBack = Image.open(
            f"{THISDIR}/overlay_wood/lectern_front_back.png")
        resultBack = blendLayers(
            background, bookshelfBack, BlendType.HARDLIGHT)
        resultCutBack = blendLayers(
            resultBack, bookshelfBack, BlendType.DESTATOP)
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/lectern_front_overlay.png")
        resultCutBookshelfBack = blendLayers(
            resultCutBack, overlay, BlendType.NORMAL)
        resultOverlay = blendLayers(
            resultCutBookshelfBack, overlay, BlendType.NORMAL)
        resultCutBackground = blendLayers(
            resultPlanks, resultOverlay, BlendType.SRCATOP)
        resultMerged = blendLayers(
            resultCutBackground, resultOverlay, BlendType.NORMAL)

        resultMerged.save(f"{THISDIR}/result_wood/bookshelf/" + filename)


def blendBookshelf():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/bookshelf_frame.png")
        resultPlanks = blendLayers(
            background, foreground, BlendType.GRAINMERGE)
        bookshelfBack = Image.open(
            f"{THISDIR}/overlay_wood/bookshelf_back.png")
        resultBack = blendLayers(
            background, bookshelfBack, BlendType.HARDLIGHT)
        resultCutBack = blendLayers(
            resultBack, bookshelfBack, BlendType.DESTATOP)
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/bookshelf.png")
        resultCutBookshelfBack = blendLayers(
            resultCutBack, overlay, BlendType.NORMAL)
        resultOverlay = blendLayers(
            resultCutBookshelfBack, overlay, BlendType.NORMAL)
        resultCutBackground = blendLayers(
            resultPlanks, resultOverlay, BlendType.SRCATOP)
        resultMerged = blendLayers(
            resultCutBackground, resultOverlay, BlendType.NORMAL)

        resultMerged.save(
            f"{THISDIR}/result_wood/bookshelf/" + filename)


def blendChiseledBookshelf0():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_perimeter_0.png")
        resultPlanks = blendLayers(
            background, foreground, BlendType.GRAINMERGE)
        bookshelfBack = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_0.png")
        resultBack = blendLayers(
            background, bookshelfBack, BlendType.HARDLIGHT)
        resultCutBack = blendLayers(
            resultBack, bookshelfBack, BlendType.DESTATOP)
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_0.png")
        resultCutBookshelfBack = blendLayers(
            resultCutBack, resultCutBack, BlendType.NORMAL)
        resultOverlay = blendLayers(
            resultCutBookshelfBack, resultCutBookshelfBack, BlendType.NORMAL)
        resultCutBackground = blendLayers(
            resultPlanks, resultOverlay, BlendType.SRCATOP)
        resultMerged = blendLayers(
            resultCutBackground, resultOverlay, BlendType.NORMAL)

        resultMerged.save(
            f"{THISDIR}/result_wood/chiseled_bookshelf/0/" + filename)


def blendChiseledBookshelf1():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_perimeter_0.png")
        resultPlanks = blendLayers(
            background, foreground, BlendType.GRAINMERGE)
        bookshelfBack = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_0.png")
        resultBack = blendLayers(
            background, bookshelfBack, BlendType.HARDLIGHT)
        resultCutBack = blendLayers(
            resultBack, bookshelfBack, BlendType.DESTATOP)
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_1.png")
        resultCutBookshelfBack = blendLayers(
            resultCutBack, overlay, BlendType.NORMAL)
        resultOverlay = blendLayers(
            resultCutBookshelfBack, overlay, BlendType.NORMAL)
        resultCutBackground = blendLayers(
            resultPlanks, resultOverlay, BlendType.SRCATOP)
        resultMerged = blendLayers(
            resultCutBackground, resultOverlay, BlendType.NORMAL)

        resultMerged.save(
            f"{THISDIR}/result_wood/chiseled_bookshelf/1/" + filename)


def blendChiseledBookshelf2():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_perimeter_0.png")
        resultPlanks = blendLayers(
            background, foreground, BlendType.GRAINMERGE)
        bookshelfBack = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_0.png")
        resultBack = blendLayers(
            background, bookshelfBack, BlendType.HARDLIGHT)
        resultCutBack = blendLayers(
            resultBack, bookshelfBack, BlendType.DESTATOP)
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_2.png")
        resultCutBookshelfBack = blendLayers(
            resultCutBack, overlay, BlendType.NORMAL)
        resultOverlay = blendLayers(
            resultCutBookshelfBack, overlay, BlendType.NORMAL)
        resultCutBackground = blendLayers(
            resultPlanks, resultOverlay, BlendType.SRCATOP)
        resultMerged = blendLayers(
            resultCutBackground, resultOverlay, BlendType.NORMAL)

        resultMerged.save(
            f"{THISDIR}/result_wood/chiseled_bookshelf/2/" + filename)


def blendChiseledBookshelf3():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_perimeter_0.png")
        resultPlanks = blendLayers(
            background, foreground, BlendType.GRAINMERGE)
        bookshelfBack = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_0.png")
        resultBack = blendLayers(
            background, bookshelfBack, BlendType.HARDLIGHT)
        resultCutBack = blendLayers(
            resultBack, bookshelfBack, BlendType.DESTATOP)
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_3.png")
        resultCutBookshelfBack = blendLayers(
            resultCutBack, overlay, BlendType.NORMAL)
        resultOverlay = blendLayers(
            resultCutBookshelfBack, overlay, BlendType.NORMAL)
        resultCutBackground = blendLayers(
            resultPlanks, resultOverlay, BlendType.SRCATOP)
        resultMerged = blendLayers(
            resultCutBackground, resultOverlay, BlendType.NORMAL)

        resultMerged.save(
            f"{THISDIR}/result_wood/chiseled_bookshelf/3/" + filename)


def blendChiseledBookshelf4():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_perimeter_0.png")
        resultPlanks = blendLayers(
            background, foreground, BlendType.GRAINMERGE)
        bookshelfBack = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_0.png")
        resultBack = blendLayers(
            background, bookshelfBack, BlendType.HARDLIGHT)
        resultCutBack = blendLayers(
            resultBack, bookshelfBack, BlendType.DESTATOP)
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_4.png")
        resultCutBookshelfBack = blendLayers(
            resultCutBack, overlay, BlendType.NORMAL)
        resultOverlay = blendLayers(
            resultCutBookshelfBack, overlay, BlendType.NORMAL)
        resultCutBackground = blendLayers(
            resultPlanks, resultOverlay, BlendType.SRCATOP)
        resultMerged = blendLayers(
            resultCutBackground, resultOverlay, BlendType.NORMAL)

        resultMerged.save(
            f"{THISDIR}/result_wood/chiseled_bookshelf/4/" + filename)


def blendChiseledBookshelf5():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_perimeter_0.png")
        resultPlanks = blendLayers(
            background, foreground, BlendType.GRAINMERGE)
        bookshelfBack = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_0.png")
        resultBack = blendLayers(
            background, bookshelfBack, BlendType.HARDLIGHT)
        resultCutBack = blendLayers(
            resultBack, bookshelfBack, BlendType.DESTATOP)
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_5.png")
        resultCutBookshelfBack = blendLayers(
            resultCutBack, overlay, BlendType.NORMAL)
        resultOverlay = blendLayers(
            resultCutBookshelfBack, overlay, BlendType.NORMAL)
        resultCutBackground = blendLayers(
            resultPlanks, resultOverlay, BlendType.SRCATOP)
        resultMerged = blendLayers(
            resultCutBackground, resultOverlay, BlendType.NORMAL)

        resultMerged.save(
            f"{THISDIR}/result_wood/chiseled_bookshelf/5/" + filename)


def blendChiseledBookshelf6():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_perimeter_0.png")
        resultPlanks = blendLayers(
            background, foreground, BlendType.GRAINMERGE)
        bookshelfBack = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_0.png")
        resultBack = blendLayers(
            background, bookshelfBack, BlendType.HARDLIGHT)
        resultCutBack = blendLayers(
            resultBack, bookshelfBack, BlendType.DESTATOP)
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_6.png")
        resultCutBookshelfBack = blendLayers(
            resultCutBack, overlay, BlendType.NORMAL)
        resultOverlay = blendLayers(
            resultCutBookshelfBack, overlay, BlendType.NORMAL)
        resultCutBackground = blendLayers(
            resultPlanks, resultOverlay, BlendType.SRCATOP)
        resultMerged = blendLayers(
            resultCutBackground, resultOverlay, BlendType.NORMAL)

        resultMerged.save(
            f"{THISDIR}/result_wood/chiseled_bookshelf/6/" + filename)


def blendChiseledBookshelfBack():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_back.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)

        result.save(
            f"{THISDIR}/result_wood/chiseled_bookshelf/back/" + filename)


def blendChiseledBookshelfSide():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_side.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)

        result.save(
            f"{THISDIR}/result_wood/chiseled_bookshelf/side/" + filename)


def blendChiseledBookshelfTop():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/1_20_bookshelf/bookshelf_top.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)

        result.save(
            f"{THISDIR}/result_wood/chiseled_bookshelf/top/" + filename)


def blendLogItemWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_wood/log_old_face.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, foreground, BlendType.DESTATOP)

        backgroundLog = Image.open(f"{THISDIR}/base_bark/" + filename)
        foregroundLog = Image.open(
            f"{THISDIR}/overlay_wood/log_old.png")
        resultLog = blendLayers(
            backgroundLog, foregroundLog, BlendType.HARDLIGHT)
        resultCutLog = blendLayers(
            resultLog, foregroundLog, BlendType.DESTATOP)

        resultMerge = blendLayers(resultCutLog, resultCut, BlendType.NORMAL)

        resultMerge.save(f"{THISDIR}/result_wood/log/" + filename)


blendLogItemWood()


def blendStrippedLogItemWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_wood/log_old_face.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, foreground, BlendType.DESTATOP)

        backgroundLog = Image.open(f"{THISDIR}/base_wood/" + filename)
        foregroundLog = Image.open(
            f"{THISDIR}/overlay_wood/log_old_wood.png")
        resultLog = blendLayers(
            backgroundLog, foregroundLog, BlendType.HARDLIGHT)
        resultLog2 = blendLayers(
            resultLog, foregroundLog, BlendType.SOFTLIGHT)
        resultCutLog = blendLayers(
            resultLog, foregroundLog, BlendType.DESTATOP)

        resultMerge = blendLayers(resultCut, resultCutLog, BlendType.DESTATOP)

        resultMerge.save(f"{THISDIR}/result_wood/stripped_log/" + filename)


blendStrippedLogItemWood()


def blendWoodItemWood():
    for filename in os.listdir(f"{THISDIR}/base_bark/"):
        background = Image.open(f"{THISDIR}/base_bark/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/log_old_face_wood.png")
        result = blendLayers(background, foreground, BlendType.HARDLIGHT)
        resultCut = blendLayers(result, foreground, BlendType.DESTATOP)

        backgroundLog = Image.open(f"{THISDIR}/base_bark/" + filename)
        foregroundLog = Image.open(
            f"{THISDIR}/overlay_wood/log_old.png")
        resultLog = blendLayers(
            backgroundLog, foregroundLog, BlendType.HARDLIGHT)
        resultLog2 = blendLayers(
            resultLog, foregroundLog, BlendType.SOFTLIGHT)
        resultCutLog = blendLayers(
            resultLog, foregroundLog, BlendType.DESTATOP)

        resultMerge = blendLayers(resultCut, resultCutLog, BlendType.DESTATOP)

        resultMerge.save(f"{THISDIR}/result_wood/wood/" + filename)


blendWoodItemWood()


def blendStrippedWoodItemWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/log_old_face_wood2.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, foreground, BlendType.DESTATOP)

        backgroundLog = Image.open(f"{THISDIR}/base_wood/" + filename)
        foregroundLog = Image.open(
            f"{THISDIR}/overlay_wood/log_old_wood.png")
        resultLog = blendLayers(
            backgroundLog, foregroundLog, BlendType.HARDLIGHT)
        resultLog2 = blendLayers(
            resultLog, foregroundLog, BlendType.SOFTLIGHT)
        resultCutLog = blendLayers(
            resultLog, foregroundLog, BlendType.DESTATOP)

        resultMerge = blendLayers(resultCut, resultCutLog, BlendType.DESTATOP)

        resultMerge.save(f"{THISDIR}/result_wood/stripped_wood/" + filename)


blendStrippedWoodItemWood()


def blendBoatEntityWood():
    for filename in os.listdir(f"{THISDIR}/base_wood_boat/"):
        background = Image.open(f"{THISDIR}/base_wood_boat/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/boat_entity.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, foreground, BlendType.DESTATOP)

        resultCut.save(f"{THISDIR}/result_wood/boat_entity/" + filename)


blendBoatEntityWood()


def blendSignEntityWood():
    for filename in os.listdir(f"{THISDIR}/base_wood_sign/"):
        background = Image.open(f"{THISDIR}/base_wood_sign/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/sign_block.png")
        logPost = Image.open(f"{THISDIR}/base_bark_sign/" + filename)
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, foreground, BlendType.DESTATOP)
        woodPost = blendLayers(resultCut, logPost, BlendType.NORMAL)

        woodPost.save(f"{THISDIR}/result_wood/sign_entity/" + filename)


blendSignEntityWood()

# def blendImageSoil():
#    for filename in os.listdir(f"{THISDIR}/base/"):
#        background = Image.open(f"{THISDIR}/base/" + filename)
#        background.putalpha(255)
#        converter = ImageEnhance.Color(background)
#        img2 = converter.enhance(1)
#        foreground = Image.open(f"{THISDIR}/overlay/rocky_compact_dirt.png")
#        backgroundBlur = img2.filter(
#            ImageFilter.GaussianBlur(radius=0.25))
#        result = blendLayers(backgroundBlur, foreground, BlendType.DESTIN)
#        result1 = blendLayers(result, foreground, BlendType.HARDLIGHT)
#
#        backgroundSoil = Image.open(f"{THISDIR}/soil/sandy_loam.png")
#        resultSoil = blendLayers(
#            backgroundSoil, result1, BlendType.SRCATOP)
#
#        resultSoil.save(f"{THISDIR}/result/" + filename)

# def blendImage():
#    for baseName in os.listdir(f"{THISDIR}/base/"):
#        background = Image.open(f"{THISDIR}/base/" + baseName)
#        background.putalpha(255)
#
#    for overlayName in os.listdir(f"{THISDIR}/overlay/"):
#        foreground = Image.open(f"{THISDIR}/overlay/" + overlayName)
#
#    backgroundBlur = background.filter(
#        ImageFilter.GaussianBlur(radius=0.5))
#    result = blendLayers(backgroundBlur, foreground, BlendType.HARDLIGHT)
#    result.save(f"{THISDIR}/result/" +
#                overlayName.join(overlayName.split('.')[:-1]) + "/" + baseName)


blendBookshelf()
blendChiseledBookshelf0()
blendChiseledBookshelf1()
blendChiseledBookshelf2()
blendChiseledBookshelf3()
blendChiseledBookshelf4()
blendChiseledBookshelf5()
blendChiseledBookshelf6()
blendChiseledBookshelfBack()
blendChiseledBookshelfSide()
blendChiseledBookshelfTop()
