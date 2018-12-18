package ui

import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.*
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
                   private var kernelSize: Int = 3) : JFrame() {

    init
    {
        val topLevelPanel = JPanel(BorderLayout())
        add(topLevelPanel)
        val height = 500
        val width = 1000
        val frameSize = Dimension(width, height)

        // ============================
        // ADJUSTMENT PANEL
        // ============================
        val adjustmentPanel = JPanel(GridLayout(2, 3, 5, 5))
        topLevelPanel.add(adjustmentPanel, BorderLayout.NORTH)
        adjustmentPanel.background = Color.YELLOW

        thresholdOneSlider.addChangeListener(HandleSliderChange())
        thresholdTwoSlider.addChangeListener(HandleSliderChange())

        adjustmentPanel.add(JLabel("Threshold Slider 1", JLabel.CENTER))
        adjustmentPanel.add(JLabel("Kernel Size", JLabel.CENTER))
        adjustmentPanel.add(JLabel("Threshold Slider 2", JLabel.CENTER))

        adjustmentPanel.add(thresholdOneSlider)
        adjustmentPanel.add(kernelComboBox)
        adjustmentPanel.add(thresholdTwoSlider)

        // ============================
        // DISPLAY PANEL
        // ============================
        val displayPanel = JPanel()
        topLevelPanel.add(displayPanel, BorderLayout.CENTER)
        displayPanel.background = Color.BLUE

        // ============================
        // FRAME
        // ============================
        size = frameSize
        isVisible = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
        title = "Edge Detection"

        val operationsMenuBar = JMenuBar()
        val fileMenu = JMenu("File")
        operationsMenuBar.add(fileMenu)
        val saveMenuItem = JMenuItem("Save Image")
        val loadMenuItem = JMenuItem("Load Image")
        fileMenu.add(saveMenuItem)
        fileMenu.add(loadMenuItem)
        jMenuBar = operationsMenuBar

        validate()
        repaint()
    }

    class HandleSliderChange: ChangeListener {

        override fun stateChanged(p0: ChangeEvent?)
        {
            val source = p0?.source
            if (source is JSlider)
            {
                System.err.println(source.value)
            }
        }

    }
}