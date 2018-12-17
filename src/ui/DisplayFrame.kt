package ui

import java.awt.*
import javax.swing.*
import javax.swing.border.Border

/**
 *
 */
class DisplayFrame(private val thresholdOneSlider: JSlider = JSlider(),
                   private val thresholdTwoSlider: JSlider = JSlider(),
                   private val comboBox: JComboBox<String> = JComboBox(),
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
        val displayPanelSize = Dimension(400,500)
        val adjustmentPanelSize = Dimension(100, 500)

        // Set up the adjustment panel
        val adjustmentPanel = JPanel(GridLayout(2, 2))
        topLevelPanel.add(adjustmentPanel, BorderLayout.NORTH)
        adjustmentPanel.size = adjustmentPanelSize
        adjustmentPanel.background = Color.YELLOW
        adjustmentPanel.add(JLabel("Threshold Slider 2", JLabel.CENTER))
        adjustmentPanel.add(JLabel("Threshold Slider 1", JLabel.CENTER))
        adjustmentPanel.add(thresholdOneSlider)
        adjustmentPanel.add(thresholdTwoSlider)

        // Set up the display panel
//        val displayPanel = JPanel()
//        displayPanel.size = displayPanelSize;
//        topLevelPanel.add(displayPanel, BorderLayout.SOUTH)
//        displayPanel.background = Color.BLUE

        // Set up the frame
        size = frameSize
        isVisible = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
        title = "Edge Detection"
    }

}