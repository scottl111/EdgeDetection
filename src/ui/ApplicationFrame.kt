package ui

import edgeDetectionAlgorithm.EdgeDetectionOperator
import utility.ImageUtils
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.image.BufferedImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener

/**
 * Class for displaying the main JFrame of the application.
 *
 * TODO The @params for the attributes.
 *
 * @author Scott Lockett
 */
class ApplicationFrame(private val thresholdOneSlider: JSlider = JSlider(),
                       private val thresholdTwoSlider: JSlider = JSlider(),
                       private val kernelComboBox: JComboBox<Int> = JComboBox(arrayOf(3, 5, 7)),
                       private var thresholdOneValue: Int = 50,
                       private var thresholdTwoValue: Int = 150,
                       private var kernelSize: Int = 3,
                       private val topLevelPanel: JPanel = JPanel(BorderLayout()),
                       private val edgeDetectionOperator: EdgeDetectionOperator = EdgeDetectionOperator(),
                       private val displayPanel: JPanel = JPanel()) : JFrame() {

    /**
     * Creates and updates the JFrame.
     */
    init
    {
        createUI()
        updateUI()
    }

    /**
     * Create the JFrame and all its panels.
     */
    private fun createUI()
    {
        createFrame()
        createImageDisplayPanel()
        createAdjustmentPanel()
        add(topLevelPanel)
    }

    /**
     * Creates the adjustment panel which contains the image adjustment sliders and spinners etc.
     */
    private fun createAdjustmentPanel()
    {
        val adjustmentPanel = JPanel(GridLayout(2, 3, 5, 5))
        adjustmentPanel.background = Color.lightGray

        thresholdOneSlider.addChangeListener(HandleSliderChange())
        thresholdTwoSlider.addChangeListener(HandleSliderChange())
        kernelComboBox.addActionListener(HandleComboBoxChange())

        adjustmentPanel.add(JLabel("Threshold Slider 1", JLabel.CENTER))
        adjustmentPanel.add(JLabel("Kernel Size", JLabel.CENTER))
        adjustmentPanel.add(JLabel("Threshold Slider 2", JLabel.CENTER))

        adjustmentPanel.add(thresholdOneSlider)
        adjustmentPanel.add(kernelComboBox)
        adjustmentPanel.add(thresholdTwoSlider)

        topLevelPanel.add(adjustmentPanel, BorderLayout.NORTH)
    }

    /**
     * Creates the image display panel which displays the image on the user interface.
     */
    private fun createImageDisplayPanel()
    {
        displayPanel.background = Color.lightGray
        topLevelPanel.add(displayPanel, BorderLayout.CENTER)
    }

    /**
     * Sets the display image on the user interface.
     * TODO This needs doing in a better way and not using the file of the image to display the image or not.
     *
     * @param fileOfImage the file location  to be displayed on the user interface.
     */
    private fun setDisplayImage(fileOfImage: File? = null)
    {
        val theImageToDisplay: BufferedImage?

        if (fileOfImage != null)
        {
            val readImage = ImageIO.read(fileOfImage)
            DisplayImage.originalImage = readImage
            theImageToDisplay = readImage
        }
        else
        {
            theImageToDisplay = DisplayImage.edgeDetectedImage
        }

        displayPanel.removeAll()
        displayPanel.add(JLabel(ImageIcon(theImageToDisplay)))
        updateUI()
    }

    /**
     * Creates the JFrame for the application.
     *
     * @param frameTitle The title of the JFrame.
     */
    private fun createFrame(frameTitle: String = "Edge Detection")
    {
        size = Dimension(frameWidth, frameHeight)
        isVisible = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
        title = frameTitle

        val operationsMenuBar = JMenuBar()
        val fileMenu = JMenu("File")
        operationsMenuBar.add(fileMenu)
        val loadMenuItem = JMenuItem(FileEnum.LOAD.display)
        val saveMenuItem = JMenuItem(FileEnum.SAVE.display)
        fileMenu.add(loadMenuItem)
        fileMenu.add(saveMenuItem)
        loadMenuItem.addActionListener(HandleMenuChange())
        saveMenuItem.addActionListener(HandleMenuChange())
        jMenuBar = operationsMenuBar
    }

    /**
     * Inner class for handling changes to a slider box.
     */
    inner class HandleSliderChange: ChangeListener
    {
        /**
         * Handles changes when a slider event is triggered.
         *
         * @param p0 the event.
         */
        override fun stateChanged(p0: ChangeEvent?)
        {
            val source = p0?.source
            if (source is JSlider)
            {
                when (source)
                {
                    thresholdOneSlider -> thresholdOneValue = source.value
                    thresholdTwoSlider -> thresholdTwoValue = source.value
                    else ->
                    {
                        //TODO get a logger and log that a Jslider has been called that doesn't exist
                    }
                }
            }
            edgeDetectionOperator.applyEdgeDetection(thresholdOneValue, thresholdTwoValue, kernelSize)
            setDisplayImage()
        }
    }

    /**
     * Inner class for handling changes to a combo box.
     */
    inner class HandleComboBoxChange: ActionListener
    {
        /**
         * Handles changes to the combo boxes events.
         *
         * @param p0 the event.
         */
        override fun actionPerformed(p0: ActionEvent?)
        {
            val source = p0?.source
            if (source is JComboBox<*> && source.selectedItem is Int)
            {
                kernelSize = source.selectedItem as Int
            }
            else
            {
                //TODO LOGGER something has gone wrong
            }
            edgeDetectionOperator.applyEdgeDetection(thresholdOneValue, thresholdTwoValue, kernelSize)
            setDisplayImage()
        }
    }

    /**
     * Inner class for handling changes to a menu drop down.
     */
    inner class HandleMenuChange: ActionListener
    {

        /**
         * The default string for the path to the initial file location when opening the file chooser.
         */
        private val defaultFileLocation = """C:\git\EdgeDetection\trainingData\"""


        /**
         * Handles selections of the menus on the JFrame.
         *
         * @param p0 the event.
         */
        override fun actionPerformed(p0: ActionEvent?)
        {
            when
            {
                FileEnum.LOAD.display == p0?.actionCommand ->
                {
                    //Create a file chooser
                    val fileChooser = JFileChooser();
                    fileChooser.currentDirectory = File(defaultFileLocation)
                    val returnVal = fileChooser.showOpenDialog(parent)

                    // Determine the file that has been selected.
                    var fileToOpenPath: File? = null
                    if (returnVal == JFileChooser.APPROVE_OPTION)
                    {
                        fileToOpenPath = fileChooser.selectedFile?.absoluteFile
                    }

                    // Make sure that the file is a real image file and if so, set it on the UI.
                    if (ImageUtils.validateFileAsImage(fileToOpenPath))
                    {
                        setDisplayImage(fileToOpenPath!!)
                    }
                    else
                    {
                        // TODO Notification that the image has failed to read into the program.
                        System.err.println("Failed to read the image $fileToOpenPath")
                    }

                    // Finally update the UI in case anything has changed.
                    updateUI()
                }

                FileEnum.SAVE.display == p0?.actionCommand ->
                {
                    val df = SimpleDateFormat("dd-MM-yy_HH-mm-s")
                    val uuid = Date()

                    val outputFile = File(defaultFileLocation + "save_${df.format(uuid)}.jpg")
                    System.err.println(outputFile.absoluteFile)

                    if (ImageIO.write(DisplayImage.edgeDetectedImage, "jpg", outputFile.absoluteFile))
                    {
                        System.err.println("Successfully wrote out image..... ${outputFile.absoluteFile}")
                    }
                }
                else ->
                {
                    //TODO LOGGER
                }
            }
        }
    }

    /**
     * Update the user interface and repaint the frame.
     */
    private fun updateUI()
    {
        revalidate()
        repaint()
    }

    /**
     * Sets the default frames height and width
     */
    companion object
    {
        const val frameHeight: Int = 500
        const val frameWidth: Int = 1000
    }


}