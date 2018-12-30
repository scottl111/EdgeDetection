package main.kotlin.ui

/**
 * The enum for representing
 *
 * @author Scott Lockett
 *
 * @param display The string associated with the enum, for use on the UI.
 */
enum class FileEnum(val display: String)
{
    /**
     * Load enum
     */
    LOAD("Load Image"),

    /**
     * Save enum
     */
    SAVE("Save Image")
}