import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import ui.DisplayFrame

class EdgeDetectionMain {

    companion object {

        const val trainingDataPath = """C:\Users\Lenovo T450\IdeaProjects\EdgeDetection\trainingData\"""

        @JvmStatic
        fun main(args: Array<String>) {
            val ui = DisplayFrame()
//            System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
//
//            val path = trainingDataPath + "3.jpg"
//            System.err.println(path)
//
//            val colourMat = Imgcodecs.imread(path)
//            val grey = Mat()
//            val draw = Mat()
//            val wide = Mat()
//            Imgproc.cvtColor(colourMat, grey, Imgproc.COLOR_BGR2GRAY)
//            Imgproc.Canny(grey, wide, 50.0, 150.0, 3, false)
//            wide.convertTo(draw, CvType.CV_8U)
//
//            utility.ReadImage.displayImage(utility.ReadImage.convertMatrixToImage(draw))
//            utility.ReadImage.displayImage(utility.ReadImage.convertMatrixToImage(colourMat))
//            utility.ReadImage.displayImage(utility.ReadImage.convertMatrixToImage(grey))
//
//            if (Imgcodecs.imwrite(trainingDataPath + "4.jpg", draw)){
//                System.err.println("Edge is detecting............")
//            }

        }
    }

}