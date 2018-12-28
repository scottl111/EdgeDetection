package ui

import java.awt.image.BufferedImage

/**
 * Singleton kotlin object so we have 1 and only 1 display image.
 */
object DisplayImage
{
    var instance: BufferedImage? = null

    var originalImage: BufferedImage? = null

    var edgeDetectedImage: BufferedImage? = null
}