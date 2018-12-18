package ui

import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.JSlider
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener

/**
 *
 */
class DisplayFrame(private val thresholdOneSlider: JSlider = JSlider(),
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
            } else {
                //TODO LOGGER something has gone wrong
            }
        }
    }

    companion object {
        const val frameHeight: Int = 500
        const val frameWidth: Int = 1000
    }
}