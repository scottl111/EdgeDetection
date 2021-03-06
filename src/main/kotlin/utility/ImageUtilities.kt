package main.kotlin.utility

import org.opencv.core.CvType
import org.opencv.core.Mat
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.io.File
import java.nio.file.Files
import javax.imageio.ImageIO

/**
 * Utility class for image manipulation
 */
object ImageUtilities
{

    /**
     * Validates that a file is an image and can be loaded into the system.
     *
     * @param fileToValidate The file to validate
     * @return true if the file is valid and can be loaded, false if not.
     */
    fun validateFileAsImage(fileToValidate: File?): Boolean
    {
        // Creates an input stream of the file in order to validate the file format readers.
        val imageStream = ImageIO.createImageInputStream(fileToValidate)
        val fileFormatIterator = ImageIO.getImageReaders(imageStream)

        return when
        {
            // TODO there's more and better validation that should be done here such as the mime type.
            // This method doesn't take into account the 'actual' file format.
            // Maybe look at using Apache Tika https://tika.apache.org/

            fileToValidate == null -> false
            !fileToValidate.canRead() -> false
            Files.isSymbolicLink(fileToValidate.toPath()) -> false
            // Ughhh - This is an awful way of testing file types but will do for now.
            // NOTE This method will NOT support TIFF imagery. TIFF imagery can be supported using JAI at a later point.
            !fileFormatIterator.hasNext() -> false
            else -> true
        }
    }

    /**
     * Converts an openCV mat into a Buffered image.
     *
     * @param matrixToConvert The matrix to convert into a buffered image.
     * @return The matrix as a buffered image.
     */
    fun convertMatrixToImage(matrixToConvert: Mat): BufferedImage
    {
        val out: BufferedImage
        val data = ByteArray(matrixToConvert.height() * matrixToConvert.width() * matrixToConvert.channels())
        System.err.println("mat height: ${matrixToConvert.height()} mat width: ${matrixToConvert.width()} channels: ${matrixToConvert.channels()}")
        matrixToConvert.get(0, 0, data)

        val type: Int = if (matrixToConvert.channels() == 1)
        {
            BufferedImage.TYPE_BYTE_GRAY
        }
        else
        {
            BufferedImage.TYPE_3BYTE_BGR
        }

        out = BufferedImage(matrixToConvert.width(), matrixToConvert.height(), type)
        out.raster.setDataElements(0, 0, matrixToConvert.width(), matrixToConvert.height(), data)

        return out
    }

    /**
     * Convert a buffered image into an openCV matrix.
     *
     * @param imageToConvert The image to convert into an openCV matrix.
     * @return The image as an openCV Mat
     */
    fun convertImageToMatrix(imageToConvert: BufferedImage): Mat
    {
        val pixels: ByteArray = (imageToConvert.raster.dataBuffer as DataBufferByte).data
        val m = Mat(imageToConvert.height, imageToConvert.width, CvType.CV_8UC3)
        m.put(0, 0, pixels)
        return m
    }

}