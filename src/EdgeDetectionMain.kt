import ui.ApplicationFrame
import javax.swing.SwingUtilities

/**
 * The main class of the application containing the main method.
 *
 * @author Scott Lockett
 */
class EdgeDetectionMain
{

    companion object
    {

        @JvmStatic
        fun main(args: Array<String>)
        {
            SwingUtilities.invokeLater{
                val ui = ApplicationFrame()
            }
        }
    }

}