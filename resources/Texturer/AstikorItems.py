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


def animalCartWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/animal_cart.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/animal_cart_wheel.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(
            f"{THISDIR}/result_wood/animal_cart/" + filename)


animalCartWood()


def plowWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/plow.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/plow_wheel.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(
            f"{THISDIR}/result_wood/plow/" + filename)


plowWood()


def supplyCartWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/supply_cart.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/supply_cart_wheel.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(
            f"{THISDIR}/result_wood/supply_cart/" + filename)


supplyCartWood()


def wheelWood():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        background = Image.open(f"{THISDIR}/base_wood/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_wood/wheel.png")
        cut = Image.open(f"{THISDIR}/overlay_wood/wheel.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)

        resultCut.save(f"{THISDIR}/result_wood/wheel/" + filename)


wheelWood()


def animalCartWoodTFC():
    for filename in os.listdir(f"{THISDIR}/base_wood_tfc/"):
        background = Image.open(f"{THISDIR}/base_wood_tfc/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/animal_cart.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/animal_cart_wheel.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(
            f"{THISDIR}/result_wood/animal_cart/" + filename)


animalCartWoodTFC()


def plowWoodTFC():
    for filename in os.listdir(f"{THISDIR}/base_wood_tfc/"):
        background = Image.open(f"{THISDIR}/base_wood_tfc/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/plow.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/plow_wheel.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(
            f"{THISDIR}/result_wood/plow/" + filename)


plowWoodTFC()


def supplyCartWoodTFC():
    for filename in os.listdir(f"{THISDIR}/base_wood_tfc/"):
        background = Image.open(f"{THISDIR}/base_wood_tfc/" + filename)
        foreground = Image.open(
            f"{THISDIR}/overlay_wood/supply_cart.png")
        overlay = Image.open(
            f"{THISDIR}/overlay_wood/supply_cart_wheel.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCutChest = blendLayers(result, foreground, BlendType.DESTATOP)
        resultOverlay = blendLayers(
            resultCutChest, overlay, BlendType.NORMAL)

        resultOverlay.save(
            f"{THISDIR}/result_wood/supply_cart/" + filename)


supplyCartWoodTFC()


def wheelWoodTFC():
    for filename in os.listdir(f"{THISDIR}/base_wood_tfc/"):
        background = Image.open(f"{THISDIR}/base_wood_tfc/" + filename)
        foreground = Image.open(f"{THISDIR}/overlay_wood/wheel.png")
        cut = Image.open(f"{THISDIR}/overlay_wood/wheel.png")
        result = blendLayers(background, foreground, BlendType.GRAINMERGE)
        resultCut = blendLayers(result, cut, BlendType.DESTIN)

        resultCut.save(f"{THISDIR}/result_wood/wheel/" + filename)


wheelWoodTFC()
