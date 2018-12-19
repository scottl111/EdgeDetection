package edgeDetectionAlgorithm

import java.awt.Color
import java.awt.image.BufferedImage

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

    fun applyGreyScale(img: BufferedImage) : BufferedImage
    {
        for (i in 0..img.height)
        {
            for (j in 0..img.width)
            {
                val currentPixelColour = Color(img.getRGB(i, j))
                val red = (currentPixelColour.red * 0.299).toInt()
                val green = (currentPixelColour.green * 0.587).toInt()
                val blue = (currentPixelColour.blue * 0.114).toInt()

                val total = red+green+blue
                val greyScaleColourPixel = Color(total)

                img.setRGB(i,j, greyScaleColourPixel.rgb);
            }
        }
        return img
    }
}