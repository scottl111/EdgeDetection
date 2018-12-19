package utility

//import org.opencv.core.Mat
import java.awt.BorderLayout
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.image.DataBufferByte
import java.io.File

/**
 * Utility class for image manipulation
 */
object ImageUtils{

//    /**
//     * Converts a BufferedImage into an open CV matrix object. Modified from
//     * http://stackoverflow.com/questions/14958643/converting-bufferedimage-to-mat-in-opencv/15339157
//     *
//     * @param img The image to be converted into an open CV matrix. Can not be null.
//     * @return The matrix equivalent of the input buffered image.
//     */
//    fun convertBufferedImageToMatrix(img: BufferedImage, imgType: Int): Mat
//    {
//		// Get the pixel from the image
//        val pixels = (img.raster.dataBuffer as DataBufferByte).data
//
//		// Create the matrix object the height and width of the matrix
//        val m = Mat(img.height, img.width, imgType)
//
//		//  put the values of the pixels into the matrix starting at position [0,0]
//        m.put(0, 0, pixels)
//
//        // return the matrix once the pixel values has been assigned to position within the matrix.
//        return m
//    }
//
//
//    fun convertMatrixToImage(matrix: Mat): BufferedImage {
//        //  Set the size of the image buffer to be the matrix's size
//        val bufferSize = matrix.width() * matrix.height() * matrix.channels()
//
//        // Create the byte array the size of the buffer
//        val b = ByteArray(bufferSize)
//
//        // Get all of the pixel from the matrix starting from position [0,0] and write them to the byte array b.
//        matrix.get(0, 0, b)
//
//        // Determine the number of channels we're going to render.
//        val imageType = if (matrix.channels() == 1) BufferedImage.TYPE_BYTE_GRAY else BufferedImage.TYPE_3BYTE_BGR
//
//        // Create the new image the size of the matrix
//        val image = BufferedImage(matrix.width(), matrix.height(), imageType)
//
//        // get the target pixels from the image as a byte array
//        val targetPixels = (image.raster.dataBuffer as DataBufferByte).data
//
//        // Copy the matrix values from b into the buffered image's target pixels
//        System.arraycopy(b, 0, targetPixels, 0, b.size)
//
//        // once set return the buffered image
//        return image
//    }
//
//    fun convertColorImageToGreyscale(imageToConvert: BufferedImage): BufferedImage {
//        // Start by checking if the image is already greyscale. If so, then return it.
//        val raster = imageToConvert.raster
//
//        // Determine the number of elements within the raster of the image. 1 element means greyscale [0-255] and 3
//        // elements means colour [RGB].
//        val elem = raster.numDataElements
//        if (elem == 1)
//        {
//            return imageToConvert
//        }
//
//        // Create a new image in a greyscale format the same size as the image to convert.
//        val greyImage = BufferedImage(imageToConvert.width, imageToConvert.height, BufferedImage.TYPE_BYTE_GRAY)
//
//        // get the graphics from the
//        val g = greyImage.graphics
//
//         // Draw the original image into the greyscale image's graphic
//        g.drawImage(imageToConvert, 0, 0, null)
//
//         // Tells the GIGO collector that this graphics object can be disposed of now.
//        g.dispose()
//
//        return greyImage
//    }

    fun getImageFromFile(img: BufferedImage) : JLabel
    {
        return JLabel(ImageIcon(img))
    }

    fun validateFileAsImage(fileToValidate: File?): Boolean
    {
        return true // TODO for now. You can go through validating the image later. Note that it could be null.
    }

}