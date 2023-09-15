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

rockOverlayTypes = ["pebble", "rocky", "rockier", "rockiest",
                    "sandy_tiles", "sandier_tiles", "sandiest_tiles"]


def sandOverlay():
    for overlayType in rockOverlayTypes:
        for sandColor in os.listdir(f"{THISDIR}/sand_base/"):
            for rockType in os.listdir(f"{THISDIR}/base_rock_all/"):
                sandTexture = Image.open(f"{THISDIR}/sand_base/" + sandColor)
                rockTexture = Image.open(
                    f"{THISDIR}/base_rock_all/" + rockType)

                rockLayer = Image.open(
                    f"{THISDIR}/sand_overlay/" + overlayType + ".png")
                rockLayerMerge = blendLayers(
                    rockTexture, rockLayer, BlendType.DESTATOP if overlayType == "pebble" else BlendType.GRAINMERGE)
                rockLayerCut = blendLayers(
                    rockLayerMerge, rockLayer, BlendType.DESTATOP)
                resultOverlay = blendLayers(
                    sandTexture, rockLayerCut, BlendType.NORMAL)

                resultOverlay.save(
                    f"{THISDIR}/result_sand/" + overlayType + "/" + sandColor.split('.')[0] + "/" + rockType)


sandOverlay()


def sandPile():
    for sandColor in os.listdir(f"{THISDIR}/sand_base/"):
        sandTexture = Image.open(f"{THISDIR}/sand_base/" + sandColor)

        foreground = Image.open(f"{THISDIR}/overlay/pile.png")
        cut = Image.open(f"{THISDIR}/overlay/pile.png")
        result = blendLayers(sandTexture, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)

        resultCut.save(
            f"{THISDIR}/result_sand/sand_pile/" + sandColor)


sandPile()


def gravelPile():
    for rockType in os.listdir(f"{THISDIR}/gravel_texture/"):
        gravelTexture = Image.open(f"{THISDIR}/gravel_texture/" + rockType)

        foreground = Image.open(f"{THISDIR}/overlay/gravel_pile.png")
        cut = Image.open(f"{THISDIR}/overlay/gravel_pile.png")
        result = blendLayers(gravelTexture, foreground, BlendType.MULTIPLY)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)

        resultCut.save(
            f"{THISDIR}/result/gravel_layer/" + rockType)


gravelPile()
