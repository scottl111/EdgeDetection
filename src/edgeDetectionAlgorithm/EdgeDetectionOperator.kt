package edgeDetectionAlgorithm

import ui.DisplayImage
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.ColorConvertOp

class EdgeDetectionOperator {

    fun applyEdgeDetection(){
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
//
//        val path = trainingDataPath + "3.jpg"
//        System.err.println(path)
//
//        val colourMat = Imgcodecs.imread(path)
//        val grey = Mat()
//        val draw = Mat()
//        val wide = Mat()
//        Imgproc.cvtColor(colourMat, grey, Imgproc.COLOR_BGR2GRAY)
//        Imgproc.Canny(grey, wide, 50.0, 150.0, 3, false)
//        wide.convertTo(draw, CvType.CV_8U)
//
//        utility.ImageUtils.displayImage(utility.ImageUtils.convertMatrixToImage(draw))
//        utility.ImageUtils.displayImage(utility.ImageUtils.convertMatrixToImage(colourMat))
//        utility.ImageUtils.displayImage(utility.ImageUtils.convertMatrixToImage(grey))
//
//        if (Imgcodecs.imwrite(trainingDataPath + "4.jpg", draw)){
//            System.err.println("Edge is detecting............")
//        }
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