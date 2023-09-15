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


def sledEntity():
    for filename in os.listdir(f"{THISDIR}/base_wood_all_sled/"):
        background = Image.open(f"{THISDIR}/base_wood_all_sled/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_wood/sled_entity.png")
        cut = Image.open(f"{THISDIR}/overlay_wood/sled_entity.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)
        resultCut.save(f"{THISDIR}/result_wood_sled/sled_entity/" + filename)


sledEntity()


def sledItem():
    for filename in os.listdir(f"{THISDIR}/base_wood_all/"):
        background = Image.open(f"{THISDIR}/base_wood_all/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_wood/sled.png")
        cut = Image.open(f"{THISDIR}/overlay_wood/sled.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)
        resultCut.save(f"{THISDIR}/result_wood_sled/sled/" + filename)


sledItem()
