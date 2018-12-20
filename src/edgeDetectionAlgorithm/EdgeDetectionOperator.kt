package edgeDetectionAlgorithm

import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import ui.DisplayImage
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.ColorConvertOp
import java.awt.image.DataBufferByte

class EdgeDetectionOperator {

    fun applyEdgeDetection(thresholdOne: Int, thresholdTwo: Int, kernelSize: Int)
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

        val img = DisplayImage.instance ?: return
        System.err.println("first: $thresholdOne second: $thresholdTwo kernel: $kernelSize")

        val colourMat = convertImageToMatrix(img)
        val grey = Mat()
        val draw = Mat()
        val wide = Mat()
        Imgproc.cvtColor(colourMat, grey, Imgproc.COLOR_BGR2GRAY)
        Imgproc.Canny(grey, wide, thresholdOne.toDouble(), thresholdTwo.toDouble(), kernelSize, false)
        wide.convertTo(draw, CvType.CV_8U)

        if (Imgcodecs.imwrite("""C:/git/EdgeDetection/trainingData/edgeDetection.jpg""", draw)){
            System.err.println("Edge is detecting............")
        }

        val edgeDetectedImage = convertMatrixToImage(grey)
        DisplayImage.instance = edgeDetectedImage
    }

    private fun convertMatrixToImage(matrix: Mat): BufferedImage {
        /*
		 * Set the size of the image buffer to be the matrix's size
		 */
        val bufferSize = matrix.width() * matrix.height() * matrix.channels()

        /*
		 * Create the byte array the size of the buffer
		 */
        val b = ByteArray(bufferSize)

        /*
		 * Get all of the pixel from the matrix starting from position [0,0] and
		 * write them to the byte array b.
		 */
        matrix.get(0, 0, b)

        /*
		 * Create the new image the size of the matrix
		 */
        val image = BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR)

        /*
		 * get the target pixels from the image as a byte array
		 */
        val targetPixels = (image.raster.dataBuffer as DataBufferByte).data

        /*
		 * Copy the matrix values from b into the buffered image's target pixels
		 */
        System.arraycopy(b, 0, targetPixels, 0, b.size)

        /*
		 * once set return the buffered image
		 */
        return image
    }

    private fun convertImageToMatrix(img: BufferedImage): Mat
    {
        val pixels: ByteArray = (img.raster.dataBuffer as DataBufferByte).data
        val m = Mat(img.height, img.width, CvType.CV_8UC3)

        m.put(0, 0, pixels)
        return m
    }

    fun applyGreyScale()
    {
        //TODO this is bad for performance - it can be done better by using filters, or openCV
        val img = DisplayImage.instance ?: return

        for (y in 0..(img.height -1))
        {
            for (x in 0..(img.width - 1))
            {
                val currentPixelColour = Color(img.getRGB(x, y))

                val red = currentPixelColour.red
                val green = currentPixelColour.green
                val blue = currentPixelColour.blue

                val average: Int = (red + green + blue) / 3

                img.setRGB(x, y, Color(average, average, average).rgb);
            }
        }

        DisplayImage.instance = img
    }

}