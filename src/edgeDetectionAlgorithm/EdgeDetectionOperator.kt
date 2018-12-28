package edgeDetectionAlgorithm

import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import ui.DisplayImage
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte


class EdgeDetectionOperator
{

    fun applyEdgeDetection(thresholdOne: Int, thresholdTwo: Int, kernelSize: Int)
    {
        //TODO whats the kotlin way of loading a library?. Shouldn't need to do this everytime.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

        val img = DisplayImage.originalImage
        if (img == null)
        {
            //TODO use logger here
            System.err.println("Tried to apply edge detection but the display image is null so returning")
            return
        }
        System.err.println("first: $thresholdOne second: $thresholdTwo kernel: $kernelSize")

        val colourMat = convertImageToMatrix(img)
        val grey = Mat()
        val draw = Mat()
        val wide = Mat()

        Imgproc.cvtColor(colourMat, grey, Imgproc.COLOR_BGR2GRAY)
        Imgproc.Canny(grey, wide, 50.0 + thresholdOne, 150.0 + thresholdTwo, kernelSize, false)
        wide.convertTo(draw, CvType.CV_8U)

        val path = """C:/git/EdgeDetection/trainingData/DOGGY.jpg"""
        if (Imgcodecs.imwrite(path, draw))
        {
            System.err.println("Edge is detecting............")
        }

        DisplayImage.edgeDetectedImage = convertMatrixToImage(draw)
    }


    private fun convertMatrixToImage(matrix: Mat): BufferedImage {
        val out: BufferedImage
        val data = ByteArray(matrix.height() * matrix.width() * matrix.channels())
        System.err.println("mat height: ${matrix.height()} mat width: ${matrix.width()} channels: ${matrix.channels()}")
        val type: Int
        matrix.get(0, 0, data)
        if (matrix.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY
        } else {
            type = BufferedImage.TYPE_3BYTE_BGR
        }
        out = BufferedImage(matrix.width(), matrix.height(), type)
        out.raster.setDataElements(0, 0, matrix.width(), matrix.height(), data)

        return out
    }

    private fun convertImageToMatrix(img: BufferedImage): Mat
    {
        val pixels: ByteArray = (img.raster.dataBuffer as DataBufferByte).data
        val m = Mat(img.height, img.width, CvType.CV_8UC3)

        m.put(0, 0, pixels)
        return m
    }
}