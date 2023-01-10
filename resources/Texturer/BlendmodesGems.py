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

def polishedBlock():
    for filename in os.listdir(f"{THISDIR}/base_rock_all/"):
        background = Image.open(f"{THISDIR}/base_rock_all/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay/polished_deepslate.png")
        backgroundBlur = background.filter(
            ImageFilter.GaussianBlur(radius=1))
        result = blendLayers(backgroundBlur, foreground, BlendType.HARDLIGHT)

        result.save(f"{THISDIR}/result_gem/polished_deepslate/" + filename)


polishedBlock()

def gemBlock():
    for filename in os.listdir(f"{THISDIR}/base_gem/"):
        background = Image.open(f"{THISDIR}/base_gem/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_gem/amethyst_block.png")
        result = blendLayers(background, foreground, BlendType.LUMINOSITY)

        result.save(f"{THISDIR}/result_gem/amethyst_block/" + filename)


gemBlock()

def gemBuddingBlock():
    for filename in os.listdir(f"{THISDIR}/base_gem/"):
        background = Image.open(f"{THISDIR}/base_gem/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_gem/budding_amethyst.png")
        result = blendLayers(background, foreground, BlendType.LUMINOSITY)

        result.save(f"{THISDIR}/result_gem/budding_amethyst/" + filename)


gemBuddingBlock()

def gemCluster():
    for filename in os.listdir(f"{THISDIR}/base_gem/"):
        background = Image.open(f"{THISDIR}/base_gem/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_gem/amethyst_cluster.png")
        cut = Image.open(f"{THISDIR}/overlay_gem/amethyst_cluster.png")
        result = blendLayers(background, foreground, BlendType.LUMINOSITY)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)

        resultCut.save(f"{THISDIR}/result_gem/amethyst_cluster/" + filename)


gemCluster()

def gemLargeBud():
    for filename in os.listdir(f"{THISDIR}/base_gem/"):
        background = Image.open(f"{THISDIR}/base_gem/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_gem/large_amethyst_bud.png")
        cut = Image.open(f"{THISDIR}/overlay_gem/large_amethyst_bud.png")
        result = blendLayers(background, foreground, BlendType.LUMINOSITY)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)

        resultCut.save(f"{THISDIR}/result_gem/large_amethyst_bud/" + filename)


gemLargeBud()

def gemMediumBud():
    for filename in os.listdir(f"{THISDIR}/base_gem/"):
        background = Image.open(f"{THISDIR}/base_gem/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_gem/medium_amethyst_bud.png")
        cut = Image.open(f"{THISDIR}/overlay_gem/medium_amethyst_bud.png")
        result = blendLayers(background, foreground, BlendType.LUMINOSITY)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)

        resultCut.save(f"{THISDIR}/result_gem/medium_amethyst_bud/" + filename)


gemMediumBud()

def gemSmallBud():
    for filename in os.listdir(f"{THISDIR}/base_gem/"):
        background = Image.open(f"{THISDIR}/base_gem/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_gem/small_amethyst_bud.png")
        cut = Image.open(f"{THISDIR}/overlay_gem/small_amethyst_bud.png")
        result = blendLayers(background, foreground, BlendType.LUMINOSITY)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)

        resultCut.save(f"{THISDIR}/result_gem/small_amethyst_bud/" + filename)


gemSmallBud()

def gemShard():
    for filename in os.listdir(f"{THISDIR}/base_gem/"):
        background = Image.open(f"{THISDIR}/base_gem/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_gem/amethyst_shard.png")
        cut = Image.open(f"{THISDIR}/overlay_gem/amethyst_shard.png")
        result = blendLayers(background, foreground, BlendType.LUMINOSITY)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)

        resultCut.save(f"{THISDIR}/result_gem/amethyst_shard/" + filename)


gemShard()

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
#        result1 = blendLayers(result, foreground, BlendType.LUMINOSITY)
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
#    result = blendLayers(backgroundBlur, foreground, BlendType.LUMINOSITY)
#    result.save(f"{THISDIR}/result/" +
#                overlayName.join(overlayName.split('.')[:-1]) + "/" + baseName)
