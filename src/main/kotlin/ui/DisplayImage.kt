package main.kotlin.ui

import java.awt.image.BufferedImage

/**
 * Singleton kotlin object so we have 1 reference to the original unaltered image and 1 to the edge detection image.
 * NOTE - it might be more appropriate to have this as a resource bundle as opposed to a static format.
 *
 * @author Scott Lockett
 */
object DisplayImage
{
    /**
     * The original unaltered image. We need a reference back to the original image so that edge detection is not
     * applied multiple times.
     */
    var originalImage: BufferedImage? = null

    /**
     * The manipulated image with edge detection applied. This is the image that will be displayed on the UI once edge
     * detection has been applied.
     */
    var edgeDetectedImage: BufferedImage? = null
}