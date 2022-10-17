import pypdn
import matplotlib.pyplot as plt

layeredImage = pypdn.read('Untitled3.pdn')
print(layeredImage)
# Contains width, height, version and layers of the image within the class
# Version being the Paint.NET version that the image was saved with

# Each layer contains the name, visibility boolean, opacity (0-255), isBackground and blendMode
# From what I can tell, the isBackground property is not that useful
# The blend mode is how the layer should be blended with the layers below it
# These attributes are loaded from the PDN file but can be edited in the code as well
print(layeredImage.layers)
layer = layeredImage.layers[0]
layer.visible = True
layer.opacity = 255
layer.blendMode = pypdn.BlendType.Normal

layer = layeredImage.layers[1]
layer.visible = True
layer.opacity = 150
layer.blendMode = pypdn.BlendType.Glow

# Finally, the most useful thing is being able to combine the layers and flattn them into one image
# Call the flatten function to do so
# It will go through each layer and apply them IF the visibility is true!
# The layer opacity and blend mode will be taken into effect
#
# The flattened image is a RGBA Numpy array image
# The asByte parameter determines the data type of the flattened image
# If asByte is True, then the dtype will be uint8, otherwise it will be a float in range (0.0, 1.0)
flatImage = layeredImage.flatten(asByte=True)

plt.figure()
plt.imshow(flatImage)

# Individual layer images can be retrieved as well
# Note: This does NOT apply blending or the layer opacity
# Rather, it is the image data that is saved by Paint.NET for the layer
plt.figure()
plt.imshow(layeredImage.layers[1].image)

plt.show()