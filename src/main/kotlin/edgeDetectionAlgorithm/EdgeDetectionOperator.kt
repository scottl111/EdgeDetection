package main.kotlin.edgeDetectionAlgorithm

import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import main.kotlin.ui.DisplayImage
import utility.ImageUtils

/**
 * Class for applying edge detection.
 *
 * TODO this class can probably be a utility class. Or the method can be moved into to ImageUtils class
 */
class EdgeDetectionOperator
{

    /**
     * Applies grey scale edge detection to the image loaded on the screen image.
     */
    fun applyEdgeDetection(thresholdOne: Int, thresholdTwo: Int, kernelSize: Int)
    {
        //TODO whats the kotlin way of loading a library?. Shouldn't need to do this every time.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

        val img = DisplayImage.originalImage
        if (img == null)
        {
            //TODO use logger here
            System.err.println("Tried to apply edge detection but the display image is null so returning")
            return
        }

        //TODO use logger here as trace level
        System.err.println("first threshold: $thresholdOne second threshold: $thresholdTwo kernel size: $kernelSize")

        val colourMat = ImageUtils.convertImageToMatrix(img)
        val grey = Mat()
        val draw = Mat()
        val wide = Mat()

        Imgproc.cvtColor(colourMat, grey, Imgproc.COLOR_BGR2GRAY)
        Imgproc.Canny(grey, wide, 50.0 + thresholdOne, 100.0 + thresholdTwo, kernelSize, false)
        wide.convertTo(draw, CvType.CV_8U)

        DisplayImage.edgeDetectedImage = ImageUtils.convertMatrixToImage(draw)
    }
}