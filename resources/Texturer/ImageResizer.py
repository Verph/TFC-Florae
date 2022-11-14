from __future__ import annotations
from blendmodes.blend import BlendType, blendLayers

import sys
import os
import functools
import operator
from pathlib import Path

from imgcompare import imgcompare
from PIL import Image, ImageOps, ImageColor, ImageEnhance, ImageFilter, ImageChops

THISDIR = str(Path(__file__).resolve().parent)
sys.path.insert(0, str(Path(THISDIR).parent))


def resizeImage():
    for filename in os.listdir(f"{THISDIR}/base_wood/"):
        img = Image.open(f"{THISDIR}/base_wood/" + filename)
        new_width = 64
        new_height = 32
        img = img.resize((new_width, new_height),
                         Image.Resampling.NEAREST)
        img.save(f"{THISDIR}/base_wood_sign/" + filename)


resizeImage()
