import os
import functools
import operator
from PIL import Image, ImageOps, ImageColor, ImageEnhance


inputColorDir = 'InputColor'
toSampleDir = 'InputSample'
outputDir = 'Output'

imageFormat = 'png'

def copyImg(dirname, name, cname):
    OutDir = outputDir + '/' + dirname + '_original'
    im = Image.open(toSampleDir + "/" + dirname + "/" + name).convert("RGBA")

    if not os.path.isdir(OutDir):
        os.mkdir(OutDir)
    im.save(OutDir + "/" + cname, "PNG")

def Structure(name, cname):
    if not os.path.isdir(outputDir):
        os.mkdir(outputDir)
    if name is None:
        for filename in os.listdir(toSampleDir):
            if not imageFormat in filename:
                if not os.path.isdir(outputDir + '/' + filename):
                    os.mkdir(outputDir + '/' + filename)
                Structure(filename, cname)
    else:
        for filename in os.listdir(toSampleDir + '/' + name):
            if not imageFormat in filename:
                if not os.path.isdir(outputDir + '/' + name):
                    os.mkdir(outputDir + '/' + name)
                Structure(name + '/' + filename, cname)
            else:
                if not os.path.isdir(outputDir + '/' + name):
                    os.mkdir(outputDir + '/' + name)
                copyImg(name, filename, cname)

def tosample():
    for filename in os.listdir(inputColorDir):
        Structure(None, filename)
        
tosample()
