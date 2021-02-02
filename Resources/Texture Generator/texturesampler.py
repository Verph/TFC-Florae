import os
import functools
import operator
from PIL import Image, ImageOps, ImageColor, ImageEnhance


inputColorDir = 'InputColor'
toSampleDir = 'InputSample'
outputDir = 'Output'

imageFormat = 'png'

blackPoint = 70
middlePoint = 105
whitePoint = 200

ImgColorFirst = 20.0
ImgContrastFirst = 300.0
ImgBrightnessFirst = 150.0
ImgContrastLast = 200.0
ImgBrightnessLast = 80.0
ImgColorLast = 80.0

useGrayscaleContrast = True

#Source: https://github.com/python-pillow/Pillow/blob/2d6e51e26c7683f067d29bd69f4fdf8eef003643/src/PIL/ImageOps.py
def _color(color, mode):
    if isinstance(color, str):
        color = ImageColor.getcolor(color, mode)
    return color

#Source: https://github.com/python-pillow/Pillow/blob/2d6e51e26c7683f067d29bd69f4fdf8eef003643/src/PIL/ImageOps.py
def _lut(image, lut):
    if image.mode == "P":
        # FIXME: apply to lookup table, not image data
        raise NotImplementedError("mode P support coming soon")
    elif image.mode in ("L", "RGB"):
        if image.mode == "RGB" and len(lut) == 256:
            lut = lut + lut + lut
        return image.point(lut)
    else:
        raise OSError("not supported for this image mode")

#Source: https://github.com/python-pillow/Pillow/blob/2d6e51e26c7683f067d29bd69f4fdf8eef003643/src/PIL/ImageOps.py
def colorize(image, black, white, mid=None, blackpoint=0, whitepoint=255, midpoint=127):
    if mid is None:
        assert 0 <= blackpoint <= whitepoint <= 255
    else:
        assert 0 <= blackpoint <= midpoint <= whitepoint <= 255

    # Define colors from arguments
    black = _color(black, "RGB")
    white = _color(white, "RGB")
    if mid is not None:
        mid = _color(mid, "RGB")

    # Empty lists for the mapping
    red = []
    green = []
    blue = []

    # Create the low-end values
    for i in range(0, blackpoint):
        red.append(black[0])
        green.append(black[1])
        blue.append(black[2])

    # Create the mapping (2-color)
    if mid is None:

        range_map = range(0, whitepoint - blackpoint)

        for i in range_map:
            red.append(black[0] + i * (white[0] - black[0]) // len(range_map))
            green.append(black[1] + i * (white[1] - black[1]) // len(range_map))
            blue.append(black[2] + i * (white[2] - black[2]) // len(range_map))

    # Create the mapping (3-color)
    else:

        range_map1 = range(0, midpoint - blackpoint)
        range_map2 = range(0, whitepoint - midpoint)

        for i in range_map1:
            red.append(black[0] + i * (mid[0] - black[0]) // len(range_map1))
            green.append(black[1] + i * (mid[1] - black[1]) // len(range_map1))
            blue.append(black[2] + i * (mid[2] - black[2]) // len(range_map1))
        for i in range_map2:
            red.append(mid[0] + i * (white[0] - mid[0]) // len(range_map2))
            green.append(mid[1] + i * (white[1] - mid[1]) // len(range_map2))
            blue.append(mid[2] + i * (white[2] - mid[2]) // len(range_map2))

    # Create the high-end values
    for i in range(0, 256 - whitepoint):
        red.append(white[0])
        green.append(white[1])
        blue.append(white[2])

    # Return converted image
    image = image.convert("RGB")
    return _lut(image, red + green + blue)

def conv(dirname, name, cname):
    im = Image.open(toSampleDir + "/" + dirname + "/" + name).convert("RGBA")

    sampleimg = Image.open(inputColorDir + "/" + cname)
    rgb = sampleimg.convert('RGB')

    #Sample points
    r, g, b = rgb.getpixel((3,7))
    rr, gg, bb = rgb.getpixel((2,5))
    rrr, ggg, bbb = rgb.getpixel((3,3))

    imgray = ImageOps.grayscale(im)

    imcol = colorize(imgray, (rr, gg, bb), (r, g, b), (rrr, ggg, bbb), blackPoint, whitePoint, middlePoint)
    
    enhancer = ImageEnhance.Color(imcol)
    enhanced = enhancer.enhance(ImgColorFirst / 100)
    enhancer = ImageEnhance.Contrast(enhanced)
    enhanced = enhancer.enhance(ImgContrastFirst / 100)
    enhancer = ImageEnhance.Brightness(enhanced)
    enhanced = enhancer.enhance(ImgBrightnessFirst / 100)
    enhancer = ImageEnhance.Contrast(enhanced)
    enhanced = enhancer.enhance(ImgContrastLast / 100)
    enhancer = ImageEnhance.Brightness(enhanced)
    enhanced = enhancer.enhance(ImgBrightnessLast / 100)
    enhancer = ImageEnhance.Color(enhanced)
    enhanced = enhancer.enhance(ImgColorLast / 100)

    #imcol = colorize(enhanced, (rr, gg, bb), (r, g, b), (rrr, ggg, bbb), blackPoint, whitePoint, middlePoint)
    
    #Extracts alpha map and apply it
    alpha = im.split()[-1]
    enhanced.putalpha(alpha)
    imgray.putalpha(alpha)

    if not os.path.isdir(outputDir + '/' + dirname + '_gray'):
        os.mkdir(outputDir + '/' + dirname + '_gray')
    imgray.save(outputDir + "/" + dirname + '_gray' + "/" + cname, "PNG")
    enhanced.save(outputDir + "/" + dirname + "/" + cname, "PNG")


def rec(name, cname):
    if not os.path.isdir(outputDir):
        os.mkdir(outputDir)
    if name is None:
        for filename in os.listdir(toSampleDir):
            if not imageFormat in filename:
                if not os.path.isdir(outputDir + '/' + filename):
                    os.mkdir(outputDir + '/' + filename)
                rec(filename, cname)
    else:
        for filename in os.listdir(toSampleDir + '/' + name):
            if not imageFormat in filename:
                if not os.path.isdir(outputDir + '/' + name):
                    os.mkdir(outputDir + '/' + name)
                rec(name + '/' + filename, cname)
            else:
                if not os.path.isdir(outputDir + '/' + name):
                    os.mkdir(outputDir + '/' + name)
                conv(name, filename, cname)

def tosample():
    for filename in os.listdir(inputColorDir):
        rec(None, filename)
        
tosample()
