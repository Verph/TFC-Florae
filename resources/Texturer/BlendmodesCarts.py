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


def plow():
    for filename in os.listdir(f"{THISDIR}/base_wood_single/"):
        background = Image.open(f"{THISDIR}/base_wood_single/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/plow.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/plow_wheel.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(f"{THISDIR}/result_wood_astikor/plow/" + filename)


plow()


def animal_cart():
    for filename in os.listdir(f"{THISDIR}/base_wood_single/"):
        background = Image.open(f"{THISDIR}/base_wood_single/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/animal_cart.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/animal_cart_wheel.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(
            f"{THISDIR}/result_wood_astikor/animal_cart/" + filename)


animal_cart()


def supply_cart():
    for filename in os.listdir(f"{THISDIR}/base_wood_single/"):
        background = Image.open(f"{THISDIR}/base_wood_single/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/supply_cart.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/supply_cart_wheel.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(
            f"{THISDIR}/result_wood_astikor/supply_cart/" + filename)


supply_cart()


def wheel():
    for filename in os.listdir(f"{THISDIR}/base_wood_single/"):
        background = Image.open(f"{THISDIR}/base_wood_single/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_wood/wheel.png")
        cut = Image.open(f"{THISDIR}/overlay_wood/wheel.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)
        resultCut.save(f"{THISDIR}/result_wood_astikor/wheel/" + filename)


wheel()
