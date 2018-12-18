package ui

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File
import javax.swing.*
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener

/**
 *
 */
class DisplayMainUI(private val thresholdOneSlider: JSlider = JSlider(),
                    private val thresholdTwoSlider: JSlider = JSlider(),
                    private val kernelComboBox: JComboBox<Int> = JComboBox(arrayOf(3, 5, 9, 12)),
                    private var thresholdOneValue: Int = 50,
                    private var thresholdTwoValue: Int = 150,
                    private var kernelSize: Int = 3,
                    private val topLevelPanel: JPanel = JPanel(BorderLayout())) : JFrame() {

    init
    {
        createUI()
        validate()
        repaint()
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
        adjustmentPanel.background = Color.YELLOW

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
        val displayPanel = JPanel()
        displayPanel.background = Color.BLUE
        topLevelPanel.add(displayPanel, BorderLayout.CENTER)
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
        val loadMenuItem = JMenuItem("Load Image")
        val saveMenuItem = JMenuItem("Save Image")
        fileMenu.add(loadMenuItem)
        fileMenu.add(saveMenuItem)
        loadMenuItem.addActionListener(HandleMenuChange(topLevelPanel))
        saveMenuItem.addActionListener(HandleMenuChange(topLevelPanel))
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

    inner class HandleMenuChange(val panel: JPanel): ActionListener
    {
        override fun actionPerformed(p0: ActionEvent?)
        {
            if (p0?.actionCommand.equals("Load Image"))
            {
                System.err.println("Lets choose a file!")
                //Create a file chooser
                val fileChooser = JFileChooser();
                fileChooser.currentDirectory = File("""C:\git\EdgeDetection\trainingData""")
                val path = fileChooser.showOpenDialog(panel)
                System.err.println("Going to load in image $path")
            }
            else if (p0?.actionCommand.equals("Save Image"))
            {

            }
        }
    }

    companion object {
        const val frameHeight: Int = 500
        const val frameWidth: Int = 1000
    }
}