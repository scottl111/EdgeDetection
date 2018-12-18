import ui.DisplayMainUI
import java.io.File
import javax.swing.JFileChooser
import javax.swing.SwingUtilities



class EdgeDetectionMain {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SwingUtilities.invokeLater {
                val ui = DisplayMainUI()
            }

        }
    }

}