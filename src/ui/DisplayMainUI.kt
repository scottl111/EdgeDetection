package ui

import edgeDetectionAlgorithm.EdgeDetectionOperator
import org.opencv.imgcodecs.Imgcodecs
import utility.ImageUtils
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener
import javax.swing.JFileChooser
import java.text.SimpleDateFormat
import java.text.DateFormat
import java.util.*


class DisplayMainUI(private val thresholdOneSlider: JSlider = JSlider(),
                    private val thresholdTwoSlider: JSlider = JSlider(),
                    private val kernelComboBox: JComboBox<Int> = JComboBox(arrayOf(3, 5, 7)),
                    private var thresholdOneValue: Int = 50,
                    private var thresholdTwoValue: Int = 150,
                    private var kernelSize: Int = 3,
                    private val topLevelPanel: JPanel = JPanel(BorderLayout()),
                    private val edgeDetectionOperator: EdgeDetectionOperator = EdgeDetectionOperator(),
                    private val displayPanel: JPanel = JPanel()) : JFrame(){

    init
    {
        createUI()
        updateUI()
    }

    private fun createUI()
    {
        createFrame()
        createImageDisplayPanel()
        createAdjustmentPanel()
        add(topLevelPanel)
    }

    private fun createAdjustmentPanel()
    {
        val adjustmentPanel = JPanel(GridLayout(2, 3, 5, 5))
        topLevelPanel.add(adjustmentPanel, BorderLayout.NORTH)
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
    }

    private fun createImageDisplayPanel()
    {
        displayPanel.background = Color.lightGray
        topLevelPanel.add(displayPanel, BorderLayout.CENTER)
    }

    private fun setDisplayImage(fileOfImage: File)
    {
        val img = ImageIO.read(fileOfImage)
        displayPanel.removeAll()
        DisplayImage.instance = img
        displayPanel.add(JLabel(ImageIcon(DisplayImage.instance)))
    }

    private fun setDisplayImage()
    {
        displayPanel.removeAll()
        displayPanel.add(JLabel(ImageIcon(DisplayImage.instance)))
    }

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

    inner class HandleSliderChange: ChangeListener
    {
        override fun stateChanged(p0: ChangeEvent?)
        {
            val source = p0?.source
            if (source is JSlider)
            {
                when (source) {
                    thresholdOneSlider -> thresholdOneValue = source.value
                    thresholdTwoSlider -> thresholdTwoValue = source.value
                    else -> {
                        //TODO get a logger and log that a Jslider has been called that doesn't exist
                    }
                }
            }
            edgeDetectionOperator.applyEdgeDetection(thresholdOneValue, thresholdTwoValue, kernelSize)
        }
    }

    inner class HandleComboBoxChange: ActionListener
    {
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
        }
    }

    inner class HandleMenuChange: ActionListener
    {
        private val defaultFileLocation = """C:\git\EdgeDetection\trainingData\"""

        override fun actionPerformed(p0: ActionEvent?)
        {
            when {
                FileEnum.LOAD.display == p0?.actionCommand ->
                {
                    //Create a file chooser
                    val fileChooser = JFileChooser();
                    fileChooser.currentDirectory = File(defaultFileLocation)
                    val returnVal = fileChooser.showOpenDialog(parent)

                    var fileToOpenPath: File? = null
                    if (returnVal == JFileChooser.APPROVE_OPTION)
                    {
                        fileToOpenPath = fileChooser.selectedFile?.absoluteFile
                    }

                    if (ImageUtils.validateFileAsImage(fileToOpenPath))
                    {
                        setDisplayImage(fileToOpenPath!!)
                    }

                    updateUI()
                }

                FileEnum.SAVE.display == (p0?.actionCommand) ->
                {
                   //TODO save the image
                    val df = SimpleDateFormat("dd-MM-yy_HH:mm:ss")
                    val uuid = Date()

                    val outputFile = File(defaultFileLocation + "written_out_" + df.format(uuid) + ".jpg")
                    System.err.println(outputFile.absoluteFile)
                    if (ImageIO.write(DisplayImage.instance, "jpg", outputFile.absoluteFile))
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

    private fun updateUI()
    {
        revalidate()
        repaint()
    }

    companion object
    {
        const val frameHeight: Int = 500
        const val frameWidth: Int = 1000
    }


}